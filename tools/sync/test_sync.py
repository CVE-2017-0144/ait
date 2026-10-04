import os
import tempfile

import codemod
import sync


def merges():
    t = 'package p;\n\npublic interface A {}\n'
    assert sync.merge3(t, t, t, True) == (t, False)
    head = '/* header */\npackage p;\n\nclass X { a.A a; }\n'
    theirs = '/* header */\npackage p;\n\nimport b.B;\n\nclass X { a.A a; b.B b; }\n'
    assert sync.merge3(head, head, theirs, True) == ('/* header */\npackage p;\n\nimport b.B;\n\nclass X { a.A a; b.B b; }\n', False)
    had = 'package p;\n\nimport a.A;\n\npublic class X {}\n'
    assert sync.merge3(had, had, 'package p;\n\npublic class X {}\n', True) == ('package p;\n\npublic class X {}\n', False)
    ours = 'package p;\n\nimport java.util.List;\n\nimport net.minecraft.world.item.Item;\n\nclass X {}\n'
    out, bad = sync.merge3(ours, ours, ours.replace('Item;', 'Item;\nimport net.minecraft.world.item.ItemStack;'), True)
    assert not bad and 'import net.minecraft.world.item.Item;\nimport net.minecraft.world.item.ItemStack;\n' in out


def parsing():
    assert sync.binary('a/b.PNG') and sync.binary('t.webp') and sync.binary('x.json', b'{\0}') and not sync.binary('x.json', b'{}')
    rep = 'converted:\n  au_level -> AU_LEVEL : Double\n  uuid -> ITEM_UUID : UUID\nkept on custom_data:\n  fuel (x)\n'
    assert sync.converted(rep) == {('au_level', 'AU_LEVEL', 'Double'), ('uuid', 'ITEM_UUID', 'UUID')}
    assert sync.converted('nothing') == set()
    java = 'DataComponentType<Double> AU_LEVEL = register("au_level", Codec.DOUBLE, ByteBufCodecs.DOUBLE);'
    assert sync.registered(java) == {('au_level', 'AU_LEVEL', 'Double')}
    assert sync.port_path('src/main/resources/data/ait/tags/blocks/x.json') == 'src/main/resources/data/ait/tags/block/x.json'


def rules():
    a = codemod.apply
    assert 'ResourceLocation.fromNamespaceAndPath("a", "b")' in a('x = new ResourceLocation("a", "b");')
    assert 'ResourceLocation.parse(s)' in a('x = new ResourceLocation(s);')
    model = 'm.render(stack, vc, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);'
    assert 'm.render(stack, vc, light, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);' in a(model)
    other = 'h.render(null, player, stack, matrices, consumers, light, overlay, 0);'
    assert other in a(other)
    tip = ('package p;\n\nimport x.Y;\n\nclass B {\n    public void appendHoverText(ItemStack stack, @Nullable BlockGetter world, List<Component> tooltip, TooltipFlag f) {\n'
           '        super.appendHoverText(stack, world, tooltip, f);\n        use(world);\n    }\n}\n')
    out = a(tip)
    assert 'Item.TooltipContext tooltipContext' in out and 'use(tooltipContext.level());' in out
    assert 'super.appendHoverText(stack, tooltipContext, tooltip, f)' in out and 'import net.minecraft.world.item.Item;' in out
    be = ('package p;\n\nimport x.Y;\n\nclass B {\n    public void saveAdditional(CompoundTag nbt) { super.saveAdditional(nbt); }\n'
          '    public void load(CompoundTag nbt) { super.load(nbt); }\n}\n')
    out = a(be)
    assert 'public void saveAdditional(CompoundTag nbt, HolderLookup.Provider registries)' in out
    assert 'public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries)' in out
    assert 'super.saveAdditional(nbt, registries)' in out and 'super.loadAdditional(nbt, registries)' in out
    assert 'import net.minecraft.core.HolderLookup;' in out
    assert a('if (EnvType.CLIENT == FabricLoader.getInstance().getEnvironmentType()) x();').startswith('if (Platform.isClient())')
    assert a('if (FabricLoader.getInstance().getEnvironmentType() != EnvType.CLIENT) x();').startswith('if (!Platform.isClient())')
    net = 'AitNetworking.buf(); buf.readList(FriendlyByteBuf::readResourceLocation); FriendlyByteBuf b;'
    assert 'readList(FriendlyByteBuf::readResourceLocation)' in a(net) and 'RegistryFriendlyByteBuf b;' in a(net)
    assert '.get(0).has(DataComponents.CUSTOM_NAME)' in a('p.get(0).hasCustomHoverName()')
    assert 's.set(DataComponents.CUSTOM_NAME, c)' in a('s.setHoverName(c)')
    out = a('package p;\n\nimport a.B;\n\nclass X { boolean f(ItemStack s) { return s.hasTag(); } }\n')
    assert 's.has(DataComponents.CUSTOM_DATA)' in out and 'import net.minecraft.core.component.DataComponents;' in out
    fbs = 'package p;\n\nimport net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;\n\nclass X { Object s = FabricBlockSettings.copy(B); }\n'
    out = a(fbs)
    assert 'import net.minecraft.world.level.block.state.BlockBehaviour;' in out and 'fabricmc' not in out
    assert 'BlockBehaviour.Properties.ofFullCopy(B)' in out
    left = 'package p;\n\nimport net.fabricmc.fabric.api.networking.v1.PacketByteBufs;\n\nclass X { Object b = PacketByteBufs.slice(x); Object c = PacketByteBufs.create(); }\n'
    out = a(left)
    assert 'import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;' in out and 'import dev.amble.ait.core.net.AitNetworking;' in out
    dup = ('package p;\n\nimport net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;\nimport net.fabricmc.fabric.api.networking.v1.PacketByteBufs;\n\n'
           'class X { void f() { ServerPlayNetworking.send(p, id, PacketByteBufs.create()); } }\n')
    out = a(dup)
    assert out.count('import dev.amble.ait.core.net.AitNetworking;') == 1 and 'fabricmc' not in out
    env = ('package p;\n\nimport net.fabricmc.api.EnvType;\nimport net.fabricmc.api.Environment;\n\n'
           '// the Environment of the client\n@Environment(EnvType.CLIENT)\nclass X {}\n')
    out = a(env)
    assert 'import net.neoforged.api.distmarker.OnlyIn;' in out and 'import net.neoforged.api.distmarker.Dist;' in out and 'fabricmc' not in out
    dg = 'package p;\n\nimport net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;\n\nclass X { FabricDataOutput o; }\n'
    out = a(dg)
    assert 'import dev.amble.lib.platform.datagen.PlatformDataOutput;' in out and 'fabricmc' not in out and 'PlatformDataOutput o;' in out
    vc = 'vc.vertex(m, 0f, 1f, 2f).color(k).uv(0f, 1f).overlayCoords(o).uv2(light).normal(0f, 1f, 0f).endVertex();'
    assert a(vc) == 'vc.addVertex(m, 0f, 1f, 2f).setColor(k).setUv(0f, 1f).setOverlay(o).setLight(light).setNormal(0f, 1f, 0f);'
    assert a('style.color(x);') == 'style.color(x);'
    use = ('public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,\n'
           '        BlockHitResult hit) {\n    if (hand != InteractionHand.MAIN_HAND) return InteractionResult.PASS;\n'
           '    player.getItemInHand(hand);\n    return super.use(state, world, pos, player, hand, hit);\n}')
    out = a(use)
    assert 'useWithoutItem(BlockState state, Level world, BlockPos pos, Player player,\n        BlockHitResult hit)' in out
    assert 'getItemInHand(InteractionHand.MAIN_HAND)' in out and 'super.useWithoutItem(state, world, pos, player, hit)' in out
    assert 'MAIN_HAND != ' not in out
    for mod in ('protected', 'public'):
        sd = f'{mod} void defineSynchedData() {{\n    super.defineSynchedData();\n    this.entityData.define(A, 0);\n}}'
        assert a(sd) == (f'{mod} void defineSynchedData(SynchedEntityData.Builder builder) {{\n    super.defineSynchedData(builder);\n'
                         '    builder.define(A, 0);\n}')
    assert 'AdvancementType.TASK' in a('x(FrameType.TASK)') and 'tag(t).addTag(b)' in a('tag(t).forceAddTag(b)')
    nbt = ('    var n = stack.getOrCreateTag();\n    n.putInt("a", 1);\n\n    stack.getOrCreateTag().putInt("b", 2);\n\n'
           '    stack.getTag().putInt("c", 3);\n    int d = stack.getOrCreateTag().getInt("d");\n')
    out = a(nbt)
    assert '    var n = ItemNbt.get(stack);\n    n.putInt("a", 1);\n    ItemNbt.set(stack, n);\n' in out
    assert 'ItemNbt.edit(stack, tag -> tag.putInt("b", 2));\n\n' in out
    assert 'stack.getTag().putInt("c", 3);' in out and 'int d = ItemNbt.get(stack).getInt("d");' in out


def files():
    sv = sync.blob
    bl = {('B', 'a.png'): b'old', ('N', 'a.png'): b'new', ('B', 'b.png'): b'old', ('N', 'b.png'): b'new',
          ('B', 'old.png'): b'same', ('N', 'new.png'): b'same'}
    sync.blob = lambda c, p: bl.get((c, p))
    try:
        with tempfile.TemporaryDirectory() as port:
            for name, data in (('a.png', b'old'), ('b.png', b'mine'), ('old.png', b'same')):
                with open(os.path.join(port, name), 'wb') as f:
                    f.write(data)
            rep = {k: [] for k, _ in sync.SECTIONS}
            sync.sync_binary('M', 'a.png', 'a.png', port, 'B', 'N', False, rep)
            sync.sync_binary('M', 'b.png', 'b.png', port, 'B', 'N', False, rep)
            sync.sync_binary('R', 'old.png', 'new.png', port, 'B', 'N', False, rep)
            assert open(os.path.join(port, 'a.png'), 'rb').read() == b'new'
            assert open(os.path.join(port, 'b.png'), 'rb').read() == b'mine' and rep['conflict'] == ['b.png (binary, port copy kept)']
            assert not os.path.exists(os.path.join(port, 'old.png')) and open(os.path.join(port, 'new.png'), 'rb').read() == b'same'
            assert rep['merged'] == ['a.png', 'new.png']
            crlf = os.path.join(port, 'c.txt')
            with open(crlf, 'wb') as f:
                f.write(b'a\r\nb\r\n')
            sync.write(crlf, 'a\nc\n')
            assert open(crlf, 'rb').read() == b'a\r\nc\r\n'
    finally:
        sync.blob = sv


merges()
parsing()
rules()
files()
print('ok')
