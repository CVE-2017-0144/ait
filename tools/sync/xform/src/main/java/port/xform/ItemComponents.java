package port.xform;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.openrewrite.Cursor;
import org.openrewrite.ExecutionContext;
import org.openrewrite.ScanningRecipe;
import org.openrewrite.SourceFile;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.AddImport;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.JavaTemplate;
import org.openrewrite.java.MethodMatcher;
import org.openrewrite.java.tree.Expression;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.JavaType;
import org.openrewrite.java.tree.Space;
import org.openrewrite.java.tree.Statement;
import org.openrewrite.java.tree.TypeUtils;
import org.openrewrite.text.PlainText;

public class ItemComponents extends ScanningRecipe<ItemComponents.Acc> {

    static final MethodMatcher GET_OR_CREATE = new MethodMatcher("net.minecraft.world.item.ItemStack getOrCreateTag()");
    static final MethodMatcher GET = new MethodMatcher("net.minecraft.world.item.ItemStack getTag()");
    static final String COMPONENTS = "dev.amble.ait.core.AITDataComponents";
    static final List<MethodMatcher> TAG_KEY_APIS = List.of(
            new MethodMatcher("net.minecraft.world.item.ItemStack removeTagKey(java.lang.String)"),
            new MethodMatcher("net.minecraft.world.item.ItemStack getTagElement(java.lang.String)"),
            new MethodMatcher("net.minecraft.world.item.ItemStack getOrCreateTagElement(java.lang.String)"),
            new MethodMatcher("net.minecraft.world.item.ItemStack addTagElement(java.lang.String, ..)"));
    static final List<MethodMatcher> WHOLE_TAG_APIS = List.of(
            new MethodMatcher("net.minecraft.world.item.ItemStack hasTag()"),
            new MethodMatcher("net.minecraft.world.item.ItemStack setTag(..)"));

    static final Map<String, String> TYPES = Map.ofEntries(
            Map.entry("Double", "Double"), Map.entry("Int", "Integer"), Map.entry("Float", "Float"),
            Map.entry("Long", "Long"), Map.entry("Boolean", "Boolean"), Map.entry("String", "String"),
            Map.entry("UUID", "UUID"), Map.entry("Byte", "Byte"), Map.entry("Short", "Short"));

    static final Map<String, String> DEFAULTS = Map.of(
            "Double", "0.0", "Integer", "0", "Float", "0.0f", "Long", "0L", "Boolean", "false",
            "String", "\"\"", "Byte", "(byte) 0", "Short", "(short) 0");

    static final Map<String, String[]> CODECS = Map.of(
            "Double", new String[] {"Codec.DOUBLE", "ByteBufCodecs.DOUBLE"},
            "Integer", new String[] {"Codec.INT", "ByteBufCodecs.VAR_INT"},
            "Float", new String[] {"Codec.FLOAT", "ByteBufCodecs.FLOAT"},
            "Long", new String[] {"Codec.LONG", "ByteBufCodecs.VAR_LONG"},
            "Boolean", new String[] {"Codec.BOOL", "ByteBufCodecs.BOOL"},
            "String", new String[] {"Codec.STRING", "ByteBufCodecs.STRING_UTF8"},
            "UUID", new String[] {"UUIDUtil.CODEC", "UUIDUtil.STREAM_CODEC"},
            "Byte", new String[] {"Codec.BYTE", "ByteBufCodecs.BYTE"},
            "Short", new String[] {"Codec.SHORT", "ByteBufCodecs.SHORT"});

    public static class Acc {
        final Map<String, String> constants = new HashMap<>();
        final Map<String, Set<String>> keyTypes = new TreeMap<>();
        final Set<String> tainted = new HashSet<>();
        final Set<String> hits = new HashSet<>();
        final Map<String, Set<String>> returns = new HashMap<>();
        final Set<String> dynCalls = new HashSet<>();
        final Map<String, Set<String>> ctorLits = new HashMap<>();
        boolean dynKeys;
        final Map<String, Set<String>> escapes = new TreeMap<>();
        final Map<String, Set<String>> keysByCls = new HashMap<>();
        final Map<String, Set<String>> litsByCls = new HashMap<>();
        final Set<String> constUses = new HashSet<>();
        final Map<String, Set<String>> writers = new HashMap<>();
    }

    abstract static class LocalsVisitor extends JavaIsoVisitor<ExecutionContext> {
        Map<String, String> locals = Map.of();
        Set<String> safe = Set.of();

        @Override
        public J.MethodDeclaration visitMethodDeclaration(J.MethodDeclaration method, ExecutionContext ctx) {
            if (method.getBody() == null)
                return method;
            Map<String, String> pl = locals;
            Set<String> ps = safe;
            locals = tagLocals(method);
            safe = contained(method, locals);
            J.MethodDeclaration m = super.visitMethodDeclaration(method, ctx);
            locals = pl;
            safe = ps;
            return m;
        }
    }

    @Override
    public String getDisplayName() {
        return "item nbt to data components";
    }

    @Override
    public String getDescription() {
        return "item nbt keys with local constant accesses to data components";
    }

    @Override
    public Acc getInitialValue(ExecutionContext ctx) {
        return new Acc();
    }

    static String keySym(Expression e) {
        if (e instanceof J.Literal lit && lit.getValue() instanceof String s)
            return "'" + s;
        JavaType.Variable v = null;
        if (e instanceof J.Identifier id)
            v = id.getFieldType();
        else if (e instanceof J.FieldAccess fa)
            v = fa.getName().getFieldType();
        if (v == null || !(v.getOwner() instanceof JavaType.FullyQualified owner))
            return null;
        return owner.getFullyQualifiedName() + "#" + v.getName();
    }

    static String[] accessor(String name) {
        if (name.equals("contains") || name.equals("hasUUID"))
            return new String[] {"has", null};
        if (name.equals("remove"))
            return new String[] {"remove", null};
        for (String p : new String[] {"get", "put"}) {
            if (name.startsWith(p) && TYPES.containsKey(name.substring(p.length())))
                return new String[] {p, TYPES.get(name.substring(p.length()))};
        }
        return null;
    }

    static boolean isStackTag(Expression e) {
        return e instanceof J.MethodInvocation m && (GET_OR_CREATE.matches(m) || GET.matches(m))
                && m.getSelect() instanceof J.Identifier;
    }

    static boolean isNbt(Expression e) {
        return e != null && TypeUtils.isOfClassType(e.getType(), "net.minecraft.nbt.CompoundTag");
    }

    static boolean isItem(JavaType t) {
        return t != null && TypeUtils.isAssignableTo("net.minecraft.world.item.Item", t);
    }

    static void ctorLits(Acc acc, JavaType type, List<Expression> args) {
        for (Expression arg : args)
            if (arg instanceof J.Literal lit && lit.getValue() instanceof String str)
                for (JavaType.FullyQualified t = TypeUtils.asFullyQualified(type); t != null; t = t.getSupertype())
                    acc.ctorLits.computeIfAbsent(t.getFullyQualifiedName(), k -> new HashSet<>()).add(str);
    }

    static Map<String, String> tagLocals(J.MethodDeclaration method) {
        Map<String, String> out = new HashMap<>();
        Map<String, Integer> cnt = new HashMap<>();
        new JavaIsoVisitor<Map<String, String>>() {
            @Override
            public J.VariableDeclarations.NamedVariable visitVariable(J.VariableDeclarations.NamedVariable v, Map<String, String> acc) {
                super.visitVariable(v, acc);
                cnt.merge(v.getSimpleName(), 1, Integer::sum);
                if (v.getInitializer() != null && isStackTag(v.getInitializer()))
                    acc.put(v.getSimpleName(), ((J.Identifier) ((J.MethodInvocation) v.getInitializer()).getSelect()).getSimpleName());
                return v;
            }
        }.visit(method, out);
        out.keySet().removeIf(n -> cnt.get(n) > 1);
        return out;
    }

    static Set<String> contained(J.MethodDeclaration method, Map<String, String> locals) {
        Set<String> esc = new HashSet<>();
        new JavaIsoVisitor<Set<String>>() {
            @Override
            public J.Identifier visitIdentifier(J.Identifier id, Set<String> acc) {
                if (!locals.containsKey(id.getSimpleName()))
                    return id;
                Object par = getCursor().getParentTreeCursor().getValue();
                if (par instanceof J.VariableDeclarations.NamedVariable nv && nv.getName() == id)
                    return id;
                if (par instanceof J.MethodInvocation call && call.getSelect() == id) {
                    String[] a = accessor(call.getSimpleName());
                    if (a != null && !call.getArguments().isEmpty() && keySym(call.getArguments().get(0)) != null)
                        return id;
                }
                acc.add(id.getSimpleName());
                return id;
            }
        }.visit(method.getBody(), esc);
        Set<String> ok = new HashSet<>(locals.keySet());
        ok.removeAll(esc);
        return ok;
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getScanner(Acc acc) {
        return new LocalsVisitor() {
            @Override
            public J.VariableDeclarations visitVariableDeclarations(J.VariableDeclarations decl, ExecutionContext ctx) {
                J.ClassDeclaration cls = getCursor().firstEnclosing(J.ClassDeclaration.class);
                boolean field = getCursor().getParentTreeCursor().getValue() instanceof J.Block block
                        && getCursor().getParentTreeCursor().getParentTreeCursor().getValue() instanceof J.ClassDeclaration;
                if (field && cls != null && cls.getType() != null && TypeUtils.isString(decl.getType())) {
                    for (J.VariableDeclarations.NamedVariable v : decl.getVariables()) {
                        if (v.getInitializer() instanceof J.Literal lit && lit.getValue() instanceof String s)
                            acc.constants.put(cls.getType().getFullyQualifiedName() + "#" + v.getSimpleName(), s);
                    }
                }
                return super.visitVariableDeclarations(decl, ctx);
            }

            private boolean safeArg(Cursor c) {
                Cursor par = c.getParentTreeCursor();
                if (par.getValue() instanceof J.FieldAccess)
                    par = par.getParentTreeCursor();
                if (!(par.getValue() instanceof J.MethodInvocation call) || call.getArguments().isEmpty())
                    return false;
                Object first = call.getArguments().get(0);
                boolean isFirst = first == c.getValue() || first instanceof J.FieldAccess fa && fa.getName() == c.getValue();
                Expression recv = call.getSelect();
                boolean ok = isStackTag(recv) || recv instanceof J.Identifier rid && safe.contains(rid.getSimpleName());
                return isFirst && isNbt(recv) && ok && accessor(call.getSimpleName()) != null;
            }

            @Override
            public J.FieldAccess visitFieldAccess(J.FieldAccess fa, ExecutionContext ctx) {
                String key = keySym(fa);
                if (key != null && !safeArg(getCursor()))
                    acc.constUses.add(key);
                return super.visitFieldAccess(fa, ctx);
            }

            @Override
            public J.Identifier visitIdentifier(J.Identifier id, ExecutionContext ctx) {
                Object par = getCursor().getParentTreeCursor().getValue();
                boolean dn = par instanceof J.VariableDeclarations.NamedVariable nv && nv.getName() == id;
                if (!(par instanceof J.FieldAccess) && !dn) {
                    String key = keySym(id);
                    if (key != null && !safeArg(getCursor()))
                        acc.constUses.add(key);
                }
                if (locals.containsKey(id.getSimpleName()) && !safe.contains(id.getSimpleName()) && !dn)
                    escape(getCursor(), id.getSimpleName() + " (local)");
                return super.visitIdentifier(id, ctx);
            }

            private void escape(Cursor c, String w) {
                J.CompilationUnit cu = c.firstEnclosing(J.CompilationUnit.class);
                acc.escapes.computeIfAbsent(topClass(c), k -> new TreeSet<>())
                        .add((cu == null ? "?" : cu.getSourcePath().toString()) + ": " + w);
            }

            @Override
            public J.Return visitReturn(J.Return ret, ExecutionContext ctx) {
                J.MethodDeclaration m = getCursor().firstEnclosing(J.MethodDeclaration.class);
                String key = ret.getExpression() == null ? null : keySym(ret.getExpression());
                if (m != null && key != null)
                    acc.returns.computeIfAbsent(m.getSimpleName(), k -> new HashSet<>()).add(key);
                return super.visitReturn(ret, ctx);
            }

            @Override
            public J.Literal visitLiteral(J.Literal lit, ExecutionContext ctx) {
                if (lit.getValue() instanceof String s)
                    acc.litsByCls.computeIfAbsent(topClass(getCursor()), k -> new HashSet<>()).add(s);
                return super.visitLiteral(lit, ctx);
            }

            @Override
            public J.NewClass visitNewClass(J.NewClass nc, ExecutionContext ctx) {
                ctorLits(acc, nc.getType(), nc.getArguments());
                return super.visitNewClass(nc, ctx);
            }

            @Override
            public J.MethodInvocation visitMethodInvocation(J.MethodInvocation call, ExecutionContext ctx) {
                if (WHOLE_TAG_APIS.stream().anyMatch(m -> m.matches(call)))
                    escape(getCursor(), (call.getSelect() == null ? "" : call.getSelect().printTrimmed(getCursor()) + ".") + call.getSimpleName() + "()");
                if (TAG_KEY_APIS.stream().anyMatch(m -> m.matches(call))) {
                    String key = keySym(call.getArguments().get(0));
                    if (key != null)
                        acc.tainted.add(key);
                }
                J.ClassDeclaration owner = getCursor().firstEnclosing(J.ClassDeclaration.class);
                if ((call.getSimpleName().equals("super") || call.getSimpleName().equals("this")) && owner != null)
                    ctorLits(acc, owner.getType(), call.getArguments());
                if (GET_OR_CREATE.matches(call) || GET.matches(call)) {
                    Object par = getCursor().getParentTreeCursor().getValue();
                    boolean accRecv = par instanceof J.MethodInvocation p && p.getSelect() == call
                            && accessor(p.getSimpleName()) != null && !p.getArguments().isEmpty()
                            && keySym(p.getArguments().get(0)) != null;
                    boolean locInit = par instanceof J.VariableDeclarations.NamedVariable;
                    if (!accRecv && !locInit)
                        escape(getCursor(), (call.getSelect() == null ? "" : call.getSelect().printTrimmed(getCursor()) + ".") + call.getSimpleName() + "() as " + par.getClass().getSimpleName());
                }
                String[] a = accessor(call.getSimpleName());
                if (isNbt(call.getSelect()) && !call.getArguments().isEmpty()) {
                    Expression arg = call.getArguments().get(0);
                    String key = keySym(arg);
                    Expression recv = call.getSelect();
                    boolean sr = isStackTag(recv)
                            || recv instanceof J.Identifier id && safe.contains(id.getSimpleName());
                    boolean stkRecv = sr || recv instanceof J.Identifier id2 && locals.containsKey(id2.getSimpleName());
                    if (key == null) {
                        if (stkRecv) {
                            if (arg instanceof J.MethodInvocation dyn && dyn.getArguments().stream().allMatch(x -> x instanceof J.Empty))
                                acc.dynCalls.add(dyn.getSimpleName());
                            else
                                acc.dynKeys = true;
                        }
                    } else if (sr && a != null) {
                        if (a[0].equals("put") && owner != null && isItem(owner.getType()))
                            acc.writers.computeIfAbsent(key, x -> new TreeSet<>()).add(owner.getType().getFullyQualifiedName());
                        acc.hits.add(key);
                        acc.keysByCls.computeIfAbsent(topClass(getCursor()), c -> new HashSet<>()).add(key);
                        if (a[1] != null)
                            acc.keyTypes.computeIfAbsent(key, k -> new HashSet<>()).add(a[1]);
                    } else {
                        acc.tainted.add(key);
                    }
                }
                return super.visitMethodInvocation(call, ctx);
            }
        };
    }

    static String topClass(Cursor c) {
        J.ClassDeclaration top = null;
        for (Cursor x = c; x != null; x = x.getParent())
            if (x.getValue() instanceof J.ClassDeclaration cd)
                top = cd;
        return top == null || top.getType() == null ? "?" : top.getType().getFullyQualifiedName();
    }

    static String literal(Acc acc, String key) {
        return key.startsWith("'") ? key.substring(1) : acc.constants.get(key);
    }

    static boolean dynamic(Acc acc) {
        return acc.dynKeys || acc.hits.stream().anyMatch(k -> !k.startsWith("'") && literal(acc, k) == null);
    }

    static Set<String> dynLits(Acc acc) {
        Set<String> out = new TreeSet<>();
        if (!dynamic(acc))
            return out;
        if (acc.dynKeys)
            acc.ctorLits.values().forEach(out::addAll);
        for (String k : acc.hits)
            if (!k.startsWith("'") && literal(acc, k) == null)
                out.addAll(acc.ctorLits.getOrDefault(k.substring(0, k.indexOf('#')), Set.of()));
        return out;
    }

    static Map<String, String> convertible(Acc acc) {
        Map<String, Set<String>> lt = new HashMap<>();
        Set<String> bad = new HashSet<>();
        for (String k : acc.tainted)
            if (literal(acc, k) != null)
                bad.add(literal(acc, k));
        for (String k : acc.constUses)
            if (!k.startsWith("'") && literal(acc, k) != null)
                bad.add(literal(acc, k));
        for (String call : acc.dynCalls)
            for (String k : acc.returns.getOrDefault(call, Set.of()))
                if (literal(acc, k) != null)
                    bad.add(literal(acc, k));
        bad.addAll(dynLits(acc));
        for (String cls : acc.escapes.keySet()) {
            bad.addAll(acc.litsByCls.getOrDefault(cls, Set.of()));
            for (String k : acc.keysByCls.getOrDefault(cls, Set.of()))
                if (literal(acc, k) != null)
                    bad.add(literal(acc, k));
        }
        for (String k : acc.hits) {
            String lit = literal(acc, k);
            if (lit != null)
                lt.computeIfAbsent(lit, x -> new HashSet<>()).addAll(acc.keyTypes.getOrDefault(k, Set.of()));
        }
        Map<String, String> out = new LinkedHashMap<>();
        for (String k : acc.hits) {
            String lit = literal(acc, k);
            if (lit == null || bad.contains(lit))
                continue;
            Set<String> types = lt.get(lit);
            if (types == null || types.size() != 1 || !lit.matches("[a-z0-9_]+"))
                continue;
            String name = lit.toUpperCase();
            out.put(k, name.equals("UUID") ? "ITEM_UUID" : name);
        }
        return out;
    }

    static Set<String> owners(Acc acc, Map<String, String> conv) {
        Set<String> out = new TreeSet<>();
        for (String k : conv.keySet())
            out.addAll(acc.writers.getOrDefault(k, Set.of()));
        return out;
    }

    static String typeOf(Acc acc, String lit) {
        for (String k : acc.hits)
            if (lit.equals(literal(acc, k)) && acc.keyTypes.containsKey(k))
                return acc.keyTypes.get(k).iterator().next();
        return null;
    }

    @Override
    public Collection<? extends SourceFile> generate(Acc acc, ExecutionContext ctx) {
        if (acc.hits.isEmpty())
            throw new IllegalStateException("no typed ItemStack tag access found, classpath broken");
        Map<String, String> conv = convertible(acc);
        Map<String, String> fields = new TreeMap<>();
        for (String k : conv.keySet())
            fields.put(conv.get(k), literal(acc, k));

        StringBuilder sb = new StringBuilder();
        sb.append("package dev.amble.ait.core;\n\n");
        sb.append("import java.util.UUID;\n");
        sb.append("import java.util.function.BiFunction;\n\n");
        sb.append("import com.mojang.serialization.Codec;\n");
        for (String imp : new TreeSet<>(List.of(
                "net.minecraft.core.Registry", "net.minecraft.core.UUIDUtil",
                "net.minecraft.core.component.DataComponentType", "net.minecraft.core.component.DataComponents",
                "net.minecraft.core.registries.BuiltInRegistries", "net.minecraft.nbt.CompoundTag",
                "net.minecraft.network.RegistryFriendlyByteBuf", "net.minecraft.network.codec.ByteBufCodecs",
                "net.minecraft.network.codec.StreamCodec", "net.minecraft.world.item.Item",
                "net.minecraft.world.item.ItemStack", "net.minecraft.world.item.component.CustomData")))
            sb.append("import ").append(imp).append(";\n");
        sb.append("\n");
        sb.append("import dev.amble.ait.AITMod;\n");
        for (String owner : owners(acc, conv))
            sb.append("import ").append(owner).append(";\n");
        sb.append("\n");
        sb.append("public final class AITDataComponents {\n\n");
        for (Map.Entry<String, String> f : fields.entrySet()) {
            String type = typeOf(acc, f.getValue());
            String[] c = CODECS.get(type);
            sb.append("    public static final DataComponentType<").append(type).append("> ").append(f.getKey())
                    .append(" = register(\"").append(f.getValue()).append("\", ").append(c[0]).append(", ").append(c[1]).append(");\n");
        }
        sb.append("\n    private AITDataComponents() {}\n\n");
        sb.append("    public static void init() {}\n\n");

        Map<String, List<String[]>> byOwn = new TreeMap<>();
        for (String k : conv.keySet()) {
            String lit = literal(acc, k);
            for (String owner : acc.writers.getOrDefault(k, Set.of()))
                byOwn.computeIfAbsent(owner, o -> new ArrayList<>()).add(new String[] {lit, conv.get(k), typeOf(acc, lit)});
        }
        sb.append("    public static void migrate(ItemStack stack) {\n");
        sb.append("        if (!stack.has(DataComponents.CUSTOM_DATA))\n            return;\n\n");
        sb.append("        Item item = stack.getItem();\n");
        boolean first = true;
        for (Map.Entry<String, List<String[]>> e : byOwn.entrySet()) {
            sb.append(first ? "\n        if" : " else if").append(" (item instanceof ").append(e.getKey().substring(e.getKey().lastIndexOf('.') + 1)).append(") {\n");
            Set<String> seen = new HashSet<>();
            for (String[] f : e.getValue()) {
                if (!seen.add(f[1]))
                    continue;
                String read = switch (f[2]) {
                    case "UUID" -> "CompoundTag::getUUID";
                    case "Integer" -> "CompoundTag::getInt";
                    default -> "CompoundTag::get" + f[2];
                };
                sb.append("            move(stack, \"").append(f[0]).append("\", ").append(f[1]).append(", ").append(read).append(");\n");
            }
            sb.append("        }");
            first = false;
        }
        sb.append(first ? "" : "\n").append("    }\n\n");
        sb.append("    private static <T> void move(ItemStack stack, String key, DataComponentType<T> type, BiFunction<CompoundTag, String, T> read) {\n");
        sb.append("        CustomData data = stack.get(DataComponents.CUSTOM_DATA);\n");
        sb.append("        if (data == null || !data.contains(key))\n            return;\n\n");
        sb.append("        stack.set(type, read.apply(data.copyTag(), key));\n");
        sb.append("        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.remove(key));\n");
        sb.append("    }\n\n");
        sb.append("    private static <T> DataComponentType<T> register(String name, Codec<T> codec,\n");
        sb.append("            StreamCodec<? super RegistryFriendlyByteBuf, T> stream) {\n");
        sb.append("        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, AITMod.id(name),\n");
        sb.append("                DataComponentType.<T>builder().persistent(codec).networkSynchronized(stream).build());\n");
        sb.append("    }\n}\n");

        StringBuilder rep = new StringBuilder("converted:\n");
        fields.forEach((f, lit) -> rep.append("  ").append(lit).append(" -> ").append(f).append(" : ").append(typeOf(acc, lit)).append('\n'));
        rep.append("kept on custom_data (unsafe access somewhere):\n");
        Set<String> kept = new TreeSet<>();
        for (String k : acc.hits)
            if (!conv.containsKey(k))
                kept.add(literal(acc, k) == null ? k : literal(acc, k) + " (" + k + ")");
        acc.dynCalls.forEach(c -> rep.append("  dynamic key via ").append(c).append("() -> ").append(acc.returns.get(c)).append('\n'));
        Set<String> vc = dynLits(acc);
        if (!vc.isEmpty())
            rep.append("  dynamic key: tainted constructor literals ").append(vc).append('\n');
        kept.forEach(k -> rep.append("  ").append(k).append('\n'));
        rep.append("whole-tag escapes (class: site), converted keys touched in that class:\n");
        acc.escapes.forEach((cls, sites) -> {
            Set<String> touched = new TreeSet<>();
            for (String k : acc.keysByCls.getOrDefault(cls, Set.of()))
                if (conv.containsKey(k))
                    touched.add(literal(acc, k));
            rep.append("  ").append(cls).append(" ").append(touched).append('\n');
            sites.forEach(site -> rep.append("      ").append(site).append('\n'));
        });

        List<SourceFile> out = new ArrayList<>();
        out.add(PlainText.builder().sourcePath(Path.of("dev/amble/ait/core/AITDataComponents.java")).text(sb.toString()).build());
        out.add(PlainText.builder().sourcePath(Path.of("item-components-report.txt")).text(rep.toString()).build());
        return out;
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor(Acc acc) {
        Map<String, String> conv = convertible(acc);
        return new LocalsVisitor() {
            @Override
            public J.MethodInvocation visitMethodInvocation(J.MethodInvocation call, ExecutionContext ctx) {
                J.MethodInvocation m = super.visitMethodInvocation(call, ctx);
                String[] a = accessor(m.getSimpleName());
                if (a == null || m.getArguments().isEmpty() || !isNbt(m.getSelect()))
                    return m;
                String key = keySym(m.getArguments().get(0));
                if (key == null || !conv.containsKey(key))
                    return m;
                String stack;
                if (isStackTag(m.getSelect()))
                    stack = ((J.Identifier) ((J.MethodInvocation) m.getSelect()).getSelect()).getSimpleName();
                else if (m.getSelect() instanceof J.Identifier id && safe.contains(id.getSimpleName()))
                    stack = locals.get(id.getSimpleName());
                else
                    return m;

                String comp = "AITDataComponents." + conv.get(key);
                String type = typeOf(acc, literal(acc, key));
                Expression v = a[0].equals("put") ? m.getArguments().get(1) : null;
                String lit = floatLiteral(type, v);
                String code = switch (a[0]) {
                    case "get" -> type.equals("UUID") ? stack + ".get(" + comp + ")"
                            : stack + ".getOrDefault(" + comp + ", " + DEFAULTS.get(type) + ")";
                    case "put" -> stack + ".set(" + comp + ", " + (lit != null ? lit : cast(type, v) + "#{any()}") + ")";
                    case "has" -> stack + ".has(" + comp + ")";
                    default -> stack + ".remove(" + comp + ")";
                };
                doAfterVisit(new AddImport<>(COMPONENTS, null, false));
                maybeRemoveImport("net.minecraft.nbt.CompoundTag");
                JavaTemplate t = JavaTemplate.builder(code).build();
                return v != null && lit == null
                        ? t.apply(getCursor(), m.getCoordinates().replace(), v)
                        : t.apply(getCursor(), m.getCoordinates().replace());
            }

            @Override
            public J.Block visitBlock(J.Block block, ExecutionContext ctx) {
                J.Block b = super.visitBlock(block, ctx);
                List<Statement> kept = new ArrayList<>();
                Space carry = null;
                for (Statement st : b.getStatements()) {
                    if (deadLocal(st, b)) {
                        if (carry == null)
                            carry = st.getPrefix();
                        continue;
                    }
                    if (carry != null) {
                        st = st.withPrefix(carry);
                        carry = null;
                    }
                    kept.add(st);
                }
                return kept.size() == b.getStatements().size() ? b : b.withStatements(kept);
            }

            private boolean deadLocal(Statement s, J.Block b) {
                if (!(s instanceof J.VariableDeclarations vd) || vd.getVariables().size() != 1)
                    return false;
                J.VariableDeclarations.NamedVariable v = vd.getVariables().get(0);
                if (v.getInitializer() == null || !isStackTag(v.getInitializer()))
                    return false;
                String name = v.getSimpleName();
                boolean[] used = {false};
                new JavaIsoVisitor<Integer>() {
                    @Override
                    public J.Identifier visitIdentifier(J.Identifier id, Integer p) {
                        if (id.getSimpleName().equals(name)
                                && !(getCursor().getParentTreeCursor().getValue() instanceof J.VariableDeclarations.NamedVariable nv && nv.getName() == id))
                            used[0] = true;
                        return id;
                    }
                }.visit(b, 0);
                return !used[0];
            }
        };
    }

    // int literal won't box into Double or Float
    static String floatLiteral(String type, Expression v) {
        if (!(v instanceof J.Literal l) || !(l.getValue() instanceof Integer n))
            return null;
        return switch (type) {
            case "Double" -> n + ".0";
            case "Float" -> n + ".0F";
            default -> null;
        };
    }

    static String cast(String type, Expression arg) {
        String prim = switch (type) {
            case "Double" -> "double";
            case "Float" -> "float";
            case "Long" -> "long";
            case "Integer" -> "int";
            case "Byte" -> "byte";
            case "Short" -> "short";
            default -> null;
        };
        if (prim == null)
            return "";
        String kind = null;
        if (arg.getType() instanceof JavaType.Primitive p)
            kind = p.getKeyword();
        else if (arg.getType() instanceof JavaType.FullyQualified fq)
            kind = switch (fq.getFullyQualifiedName()) {
                case "java.lang.Integer" -> "int";
                case "java.lang.Long" -> "long";
                case "java.lang.Short" -> "short";
                case "java.lang.Byte" -> "byte";
                case "java.lang.Float" -> "float";
                case "java.lang.Double" -> "double";
                default -> null;
            };
        return kind == null || kind.equals(prim) ? "" : "(" + prim + ") ";
    }
}
