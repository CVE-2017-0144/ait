package port.xform;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.openrewrite.ExecutionContext;
import org.openrewrite.InMemoryExecutionContext;
import org.openrewrite.LargeSourceSet;
import org.openrewrite.PrintOutputCapture;
import org.openrewrite.Recipe;
import org.openrewrite.RecipeRun;
import org.openrewrite.Result;
import org.openrewrite.SourceFile;
import org.openrewrite.internal.InMemoryLargeSourceSet;
import org.openrewrite.java.JavaParser;
import org.openrewrite.java.JavaParsingException;
import org.openrewrite.tree.ParseError;

public final class Main {

    // javadoc parser chokes on {@return} + @throws
    static final Set<String> SKIP = Set.of("dev/amble/ait/core/commands/argument/json/StringJsonReader.java");

    public static void main(String[] args) throws Exception {
        Path src = Path.of(args[0]);
        List<Path> cp = Files.readAllLines(Path.of(args[1])).stream()
                .filter(s -> !s.isBlank()).map(Path::of).toList();
        List<Path> miss = cp.stream().filter(p -> !Files.exists(p)).toList();
        if (cp.isEmpty() || !miss.isEmpty()) {
            System.err.println("xform: bad classpath " + args[1] + (cp.isEmpty() ? ": empty" : ": missing " + miss));
            System.exit(1);
        }
        Path out = Path.of(args[2]);

        List<Path> files;
        try (Stream<Path> w = Files.walk(src)) {
            files = w.filter(p -> p.toString().endsWith(".java")).toList();
        }

        AtomicInteger attr = new AtomicInteger(), errs = new AtomicInteger();
        long t0 = System.currentTimeMillis();
        List<SourceFile> parsed = JavaParser.fromJavaVersion()
                .classpath(cp)
                .logCompilationWarningsAndErrors(false)
                .build()
                .parse(files, src, new InMemoryExecutionContext(t -> {
                    if (t instanceof JavaParsingException && String.valueOf(t.getMessage()).startsWith("Failed symbol entering or attribution"))
                        attr.incrementAndGet();
                    System.err.println("xform: " + t);
                }))
                .toList();
        System.out.println("parsed " + parsed.size() + " files in " + (System.currentTimeMillis() - t0) + " ms");

        int bad = attr.get();
        for (SourceFile s : parsed) {
            if (!(s instanceof ParseError))
                continue;
            boolean skip = SKIP.contains(s.getSourcePath().toString().replace('\\', '/'));
            bad += skip ? 0 : 1;
            System.err.println("xform: " + (skip ? "skipped unparsable " : "unparsable ") + s.getSourcePath());
        }
        if (bad > 0) {
            System.err.println("xform: " + bad + " parse errors");
            System.exit(1);
        }

        ExecutionContext ctx = new InMemoryExecutionContext(t -> {
            errs.incrementAndGet();
            System.err.println("xform: " + t);
        });
        Recipe rec = new ItemComponents();
        LargeSourceSet set = new InMemoryLargeSourceSet(parsed);
        RecipeRun run = rec.run(set, ctx);
        if (errs.get() > 0) {
            System.err.println("xform: " + errs.get() + " errors");
            System.exit(1);
        }
        int n = 0;
        for (Result r : run.getChangeset().getAllResults()) {
            if (r.getAfter() == null)
                continue;
            Path dst = out.resolve(r.getAfter().getSourcePath());
            Files.createDirectories(dst.getParent());
            Files.writeString(dst, r.getAfter().printAll(new PrintOutputCapture<>(0, PrintOutputCapture.MarkerPrinter.SANITIZED)));
            n++;
        }
        System.out.println(rec.getName() + ": " + n + " files");
    }
}
