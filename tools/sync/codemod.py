import re

IMPORT = re.compile(r'^import\s+(static\s+)?[\w.*]+\s*;\s*$')


def _args(t, start):
    sp, d, i, s, q = [], 1, start, start, None
    while i < len(t):
        c = t[i]
        if q:
            if c == '\\':
                i += 2
                continue
            if c == q:
                q = None
        elif c in '"\'':
            q = c
        elif c in '([{':
            d += 1
        elif c in ')]}':
            d -= 1
            if d == 0:
                sp.append((s, i))
                return sp, i
        elif c == ',' and d == 1:
            sp.append((s, i))
            s = i + 1
        i += 1
    return None, -1


def _bend(text, i):
    d = 0
    while i < len(text):
        if text[i] == '{':
            d += 1
        elif text[i] == '}':
            d -= 1
            if d == 0:
                return i
        i += 1
    return -1


def _rl(text):
    out, i = [], 0
    pat = re.compile(r'new\s+ResourceLocation\s*\(')
    while True:
        m = pat.search(text, i)
        if not m:
            out.append(text[i:])
            return ''.join(out)
        sp, end = _args(text, m.end())
        n = len(sp) if sp and text[m.end():end].strip() else 0
        out.append(text[i:m.start()])
        out.append({1: 'ResourceLocation.parse(', 2: 'ResourceLocation.fromNamespaceAndPath('}.get(n, m.group(0)))
        i = m.end()


SIMPLE = [
    (r'@Environment\(\s*(?:value\s*=\s*)?EnvType\.(CLIENT|SERVER)\s*\)', lambda m: '@OnlyIn(Dist.' + ('CLIENT' if m.group(1) == 'CLIENT' else 'DEDICATED_SERVER') + ')'),
    (r'\bBlockBehaviour\.Properties\.copy\(', 'BlockBehaviour.Properties.ofFullCopy('),
    (r'\bFabricBlockSettings\.copy(?:Of)?\(', 'BlockBehaviour.Properties.ofFullCopy('),
    (r'\bFabricBlockSettings\.create\(\)', 'BlockBehaviour.Properties.of()'),
    (r'(?<![\w.])FabricBlockSettings\b', 'BlockBehaviour.Properties'),
    (r'\bPacketByteBufs\.(?:create|empty)\(\)', 'AitNetworking.buf()'),
    (r'\bPacketByteBufs\.copy\((\w+)\)', r'new RegistryFriendlyByteBuf(\1.copy(), \1.registryAccess())'),
    (r'\bServerPlayNetworking\.registerGlobalReceiver\(', 'AitNetworking.registerServerReceiver('),
    (r'\bClientPlayNetworking\.registerGlobalReceiver\(', 'AitNetworking.registerClientReceiver('),
    (r'\b(?:Server|Client)PlayNetworking\.send\(', 'AitNetworking.send('),
    (r'\bServerPlayNetworking\.canSend\((\w+), ([\w.]+)\)', r'\1.connection.hasChannel(new AitNetworking.Payload(\2, new byte[0]))'),
    (r'\bServerPlayNetworking\.PlayChannelHandler\b', 'AitNetworking.ServerHandler'),
    (r'\.getFrameTime\(\)', '.getTimer().getGameTimeDeltaPartialTick(true)'),
    (r'\bFabricLoader\.getInstance\(\)\.getEnvironmentType\(\)\s*==\s*EnvType\.CLIENT|\bEnvType\.CLIENT\s*==\s*FabricLoader\.getInstance\(\)\.getEnvironmentType\(\)', 'Platform.isClient()'),
    (r'\bFabricLoader\.getInstance\(\)\.getEnvironmentType\(\)\s*!=\s*EnvType\.CLIENT|\bEnvType\.CLIENT\s*!=\s*FabricLoader\.getInstance\(\)\.getEnvironmentType\(\)', '!Platform.isClient()'),
    (r'\bFabricLoader\.getInstance\(\)\.isDevelopmentEnvironment\(\)', '!FMLEnvironment.production'),
    (r'(\w+)\.getEyePosition\(\)\.distanceToSqr\((\w+)\.getCenter\(\)\)\s*>\s*ServerGamePacketListenerImpl\.MAX_INTERACTION_DISTANCE', r'!\1.canInteractWithBlock(\2, 1.0)'),
    (r'(\w+)\.getEyePosition\(\)\.distanceToSqr\((\w+)\.getCenter\(\)\)\s*<=\s*ServerGamePacketListenerImpl\.MAX_INTERACTION_DISTANCE', r'\1.canInteractWithBlock(\2, 1.0)'),
    (r'\.get\(\)\.ifLeft\(', '.ifSuccess('),
    (r'\)\.ifRight\(', ').ifError('),
    (r'\bgetPart\(\)', 'root()'),
    (r'\bsaveWithoutMetadata\(\)', 'saveWithoutMetadata(registries)'),
    (r'public CompoundTag getUpdateTag\(\)', 'public CompoundTag getUpdateTag(HolderLookup.Provider registries)'),
    (r'\bsuper\.getUpdateTag\(\)', 'super.getUpdateTag(registries)'),
    (r'public void load\(CompoundTag (\w+)\)', r'public void loadAdditional(CompoundTag \1, HolderLookup.Provider registries)'),
    (r'\bsuper\.load\((\w+)\)', r'super.loadAdditional(\1, registries)'),
    (r'\b(public|protected) void saveAdditional\(CompoundTag (\w+)\)', r'\1 void saveAdditional(CompoundTag \2, HolderLookup.Provider registries)'),
    (r'\bsuper\.saveAdditional\((\w+)\)', r'super.saveAdditional(\1, registries)'),
    (r'\bnoParticlesOnBreak\(\)', 'noTerrainParticles()'),
    (r'\.hasCustomHoverName\(\)', '.has(DataComponents.CUSTOM_NAME)'),
    (r'\.resetHoverName\(\);', '.remove(DataComponents.CUSTOM_NAME);'),
    (r'\.setHoverName\(', '.set(DataComponents.CUSTOM_NAME, '),
    (r'\.getEffect\(\)\.getColor\(\)', '.getEffect().value().getColor()'),
    (r'\bimplements HudRenderCallback\b', 'implements HudRenderEvents.HudRender'),
    (r'\bHudRenderCallback\.EVENT\b', 'HudRenderEvents.HUD'),
    (r'public void onHudRender\(GuiGraphics (\w+), float (\w+)\)', r'public void onHudRender(GuiGraphics \1, DeltaTracker \2)'),
    (r'(?<![\w.])FabricDataGenerator\b', 'PlatformDataGenerator'),
    (r'(?<![\w.])FabricDataOutput\b', 'PlatformDataOutput'),
    (r'(?<![\w.])FrameType\b', 'AdvancementType'),
    (r'\.forceAddTag\(', '.addTag('),
]

DROP = [
    'net.fabricmc.api.Environment',
    'net.fabricmc.api.EnvType',
    'net.fabricmc.loader.api.FabricLoader',
    'net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings',
    'net.fabricmc.fabric.api.networking.v1.PacketByteBufs',
    'net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking',
    'net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking',
    'net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback',
    'net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator',
    'net.fabricmc.fabric.api.datagen.v1.FabricDataOutput',
    'net.minecraft.advancements.FrameType',
]

ENSURE = [
    ('net.neoforged.api.distmarker.OnlyIn', r'@OnlyIn\('),
    ('net.neoforged.api.distmarker.Dist', r'\bDist\.'),
    ('net.neoforged.fml.loading.FMLEnvironment', r'\bFMLEnvironment\.'),
    ('net.minecraft.advancements.AdvancementType', r'\bAdvancementType\b'),
    ('dev.amble.lib.platform.datagen.PlatformDataGenerator', r'\bPlatformDataGenerator\b'),
    ('dev.amble.lib.platform.datagen.PlatformDataOutput', r'\bPlatformDataOutput\b'),
    ('net.minecraft.util.FastColor', r'\bFastColor\.'),
    ('dev.amble.ait.core.util.ItemNbt', r'\bItemNbt\.'),
    ('dev.amble.ait.core.net.AitNetworking', r'\bAitNetworking\.'),
    ('dev.amble.lib.platform.Platform', r'\bPlatform\.isClient\('),
    ('dev.amble.lib.platform.render.HudRenderEvents', r'\bHudRenderEvents\.'),
    ('net.minecraft.client.DeltaTracker', r'\bDeltaTracker\b'),
    ('net.minecraft.core.HolderLookup', r'\bHolderLookup\.'),
    ('net.minecraft.core.component.DataComponents', r'\bDataComponents\.'),
    ('net.minecraft.network.RegistryFriendlyByteBuf', r'\bRegistryFriendlyByteBuf\b'),
    ('net.minecraft.world.item.Item', r'\bItem\.TooltipContext\b'),
    ('net.minecraft.world.level.block.state.BlockBehaviour', r'\bBlockBehaviour\.'),
    ('net.minecraft.world.InteractionHand', r'\bInteractionHand\.'),
    ('net.minecraft.network.syncher.SynchedEntityData', r'\bSynchedEntityData\.'),
]


def _net_buffers(text):
    if 'AitNetworking' not in text:
        return text
    return re.sub(r'(?<![\w.])FriendlyByteBuf\b(?!::)', 'RegistryFriendlyByteBuf', text)


STACK = r'[\w.]+(?:\(\))?'
MUTATE = r'(?:put\w*|remove)'
READ = r'\.(?:get\w*|contains|has\w*|size|isEmpty|getAllKeys)\('
READER = r'(?:get|read|has|is|contains|find)'


def _nbt(text):
    if 'Tag' not in text:
        return text
    text = re.sub(rf'^([ \t]*)({STACK})\.getOrCreateTag\(\)\.({MUTATE})\((.*)\);[ \t]*$',
                  r'\1ItemNbt.edit(\2, tag -> tag.\3(\4));', text, flags=re.M)
    text = re.sub(rf'^([ \t]*)({STACK})\.removeTagKey\((.*)\);[ \t]*$', r'\1ItemNbt.edit(\2, tag -> tag.remove(\3));', text, flags=re.M)

    # get() hands out a copy, write it back
    out, live, depth = [], [], 0
    for ln in text.split('\n'):
        m = re.match(rf'^([ \t]*)((?:final\s+)?(?:CompoundTag|var)) (\w+) = ({STACK})\.(getOrCreateTag|getTag)\(\);[ \t]*$', ln)
        if m:
            fn = 'get' if m.group(5) == 'getOrCreateTag' else 'getNullable'
            ln = f'{m.group(1)}{m.group(2)} {m.group(3)} = ItemNbt.{fn}({m.group(4)});'
            live.append((m.group(3), m.group(4), depth))
        out.append(ln)
        w = re.match(rf'^(\s*)(\w+)\.{MUTATE}\(.*\);\s*$', ln)
        if w:
            for var, st, _ in reversed(live):
                if var == w.group(2):
                    out.append(f'{w.group(1)}ItemNbt.set({st}, {var});')
                    break
        depth += ln.count('{') - ln.count('}')
        live = [v for v in live if v[2] <= depth]

    text = '\n'.join(out)
    text = re.sub(rf'({STACK})\.getOrCreateTag\(\)(?={READ})', r'ItemNbt.get(\1)', text)
    text = re.sub(rf'({STACK})\.getTag\(\)(?={READ}|\s*[!=]=\s*null)', r'ItemNbt.getNullable(\1)', text)
    text = re.sub(rf'\b({READER}\w*)\(({STACK})\.getOrCreateTag\(\)\)', r'\1(ItemNbt.get(\2))', text)
    text = re.sub(rf'\b({READER}\w*)\(({STACK})\.getTag\(\)\)', r'\1(ItemNbt.getNullable(\2))', text)
    text = re.sub(rf'({STACK})\.hasTag\(\)', r'\1.has(DataComponents.CUSTOM_DATA)', text)
    text = re.sub(rf'({STACK})\.setTag\((.*?)\);', r'ItemNbt.set(\1, \2);', text)
    return text


ONE = re.compile(r'^1(?:\.0*)?[fF]?$')
OVERLAY = re.compile(r'(?i)overlay|uv')
RGBA_SIG = re.compile(r'(\bvoid (?:renderToBuffer|render)\(PoseStack \w+, VertexConsumer \w+, int \w+, int \w+),'
                      r'\s*float (\w+),\s*float (\w+),\s*float (\w+),\s*float (\w+)\)')


def _colors(text):
    if 'render' not in text:
        return text
    pos = 0
    while True:
        m = RGBA_SIG.search(text, pos)
        if not m:
            break
        head = m.group(1) + ', int color)'
        br = text.find('{', m.end())
        end = _bend(text, br) if br >= 0 else -1
        if end < 0:
            pos = m.end()
            continue
        r, g, b, a = m.group(2, 3, 4, 5)
        body = re.sub(rf'\b{r},\s*{g},\s*{b},\s*{a}\b', 'color', text[br:end])
        text = text[:m.start()] + head + text[m.end():br] + body + text[end:]
        pos = m.start() + len(head)

    out, pos = [], 0
    call = re.compile(r'\.(?:renderToBuffer|render)\(')
    while True:
        m = call.search(text, pos)
        if not m:
            out.append(text[pos:])
            return ''.join(out)
        sp, end = _args(text, m.end())
        # other 8-arg renders must stay untouched
        if not sp or len(sp) != 8 or not OVERLAY.search(text[sp[3][0]:sp[3][1]]):
            out.append(text[pos:m.end()])
            pos = m.end()
            continue
        argv = [text[s:e].strip() for s, e in sp]
        head = text[pos:sp[4][0]]
        if all(ONE.match(x) for x in argv[4:]):
            color = ' 0xFFFFFFFF'
        else:
            color = f' FastColor.ARGB32.colorFromFloat({argv[7]}, {argv[4]}, {argv[5]}, {argv[6]})'
        out.append(head + color + ')')
        pos = end + 1


TOOLTIP = re.compile(r'public void appendHoverText\(ItemStack (\w+), @Nullable (?:Level|BlockGetter) (\w+), List<Component> (\w+), TooltipFlag (\w+)\)')


def _tooltip(text):
    pos = 0
    while True:
        m = TOOLTIP.search(text, pos)
        if not m:
            return text
        stack, lv, tip, flag = m.groups()
        head = f'public void appendHoverText(ItemStack {stack}, Item.TooltipContext tooltipContext, List<Component> {tip}, TooltipFlag {flag})'
        semi, br = text.find(';', m.end()), text.find('{', m.end())
        end = _bend(text, br) if br >= 0 and (semi < 0 or br < semi) else -1
        if end < 0:
            text = text[:m.start()] + head + text[m.end():]
            pos = m.start() + len(head)
            continue
        body = text[br:end].replace(f'super.appendHoverText({stack}, {lv}, {tip}, {flag})',
                                    f'super.appendHoverText({stack}, tooltipContext, {tip}, {flag})')
        body = re.sub(rf'(?<![\w.]){lv}\b', 'tooltipContext.level()', body)
        text = text[:m.start()] + head + text[m.end():br] + body + text[end:]
        pos = m.start() + len(head)


VERTEX = [('vertex', 'addVertex'), ('color', 'setColor'), ('uv', 'setUv'), ('normal', 'setNormal')]
BY_ARITY = [('uv2', 'setLight', 'setUv2'), ('overlayCoords', 'setOverlay', 'setUv1')]


def _vtx(text):
    def fix(m):
        s = m.group(0).replace('.endVertex()', '')
        for old, new in VERTEX:
            s = re.sub(rf'\.{old}\(', f'.{new}(', s)
        for old, one, two in BY_ARITY:
            out, pos = [], 0
            for c in re.finditer(rf'\.{old}\(', s):
                sp, _ = _args(s, c.end())
                out.append(s[pos:c.start()] + f'.{one if sp and len(sp) == 1 else two}(')
                pos = c.end()
            s = ''.join(out) + s[pos:]
        return s
    return re.sub(r'[^;{}]*\.endVertex\(\)\s*;', fix, text) if '.endVertex()' in text else text


USE = re.compile(r'public InteractionResult use\(BlockState (\w+), Level (\w+), BlockPos (\w+), Player (\w+), InteractionHand (\w+),(\s*)BlockHitResult (\w+)\)')


def _block_use(text):
    pos = 0
    while True:
        m = USE.search(text, pos)
        if not m:
            return text
        state, lv, bp, pl, hand, gap, hit = m.groups()
        head = f'public InteractionResult useWithoutItem(BlockState {state}, Level {lv}, BlockPos {bp}, Player {pl},{gap}BlockHitResult {hit})'
        br = text.find('{', m.end())
        end = _bend(text, br) if br >= 0 else -1
        if end < 0:
            return text[:m.start()] + head + text[m.end():]
        body = re.sub(rf'\n[ \t]*if \({hand} != InteractionHand\.MAIN_HAND\)\s*return [^;]+;[ \t]*(?=\n)', '', text[br:end])
        body = re.sub(rf'\bsuper\.use\((\w+), (\w+), (\w+), (\w+), {hand}, (\w+)\)', r'super.useWithoutItem(\1, \2, \3, \4, \5)', body)
        body = re.sub(rf'(?<![\w.]){hand}\b', 'InteractionHand.MAIN_HAND', body)
        text = text[:m.start()] + head + text[m.end():br] + body + text[end:]
        pos = m.start() + len(head)


def _synched_data(text):
    m = re.search(r'\b(public|protected) void defineSynchedData\(\)\s*\{', text)
    if not m:
        return text
    end = _bend(text, m.end() - 1)
    if end < 0:
        return text
    body = re.sub(r'\b(?:this\.)?entityData\.define\(', 'builder.define(', text[m.end():end])
    body = body.replace('super.defineSynchedData();', 'super.defineSynchedData(builder);')
    return text[:m.start()] + m.group(1) + ' void defineSynchedData(SynchedEntityData.Builder builder) {' + body + text[end:]


def _fqn(line):
    return line.strip()[len('import '):].rstrip(';').strip()


def place_import(block, line):
    imp = line.strip()
    static = imp.startswith('import static ')
    name = _fqn(imp)
    segs = name.split('.')
    cand = [i for i, l in enumerate(block) if l.strip().startswith('import ') and l.strip().startswith('import static ') == static]
    if not cand:
        if static:
            return [imp, ''] + block
        return block + ([''] if block else []) + [imp]

    def common(i):
        n = 0
        for a, b in zip(_fqn(block[i]).split('.'), segs):
            if a != b:
                break
            n += 1
        return n

    mx = max(common(i) for i in cand)
    near = [i for i in cand if common(i) == mx]
    lo = [i for i in near if _fqn(block[i]) < name]
    at = lo[-1] + 1 if lo else near[0]
    return block[:at] + [imp] + block[at:]


def _code(text):
    return re.sub(r'"(?:\\.|[^"\\\n])*"|\'(?:\\.|[^\'\\\n])*\'|//[^\n]*|/\*.*?\*/', ' ', '\n'.join(l for l in text.split('\n') if not IMPORT.match(l)), flags=re.S)


def _drop_imports(text):
    src = _code(text)
    for old in DROP:
        if not re.search(rf'\b{old.rsplit(".", 1)[1]}\b', src):
            text = re.sub(r'^import\s+' + re.escape(old) + r'\s*;[^\S\n]*\n?', '', text, flags=re.M)
    return text


def _ensure_import(text, fqn, use):
    pkg, simple = fqn.rsplit('.', 1)
    if not re.search(use, _code(text)):
        return text
    if re.search(rf'^package\s+{re.escape(pkg)}\s*;', text, re.M):
        return text
    if re.search(rf'^import\s+(?:{re.escape(pkg)}\.\*|[\w.]+\.{simple})\s*;', text, re.M):
        return text
    ls = text.split('\n')
    idx = [i for i, l in enumerate(ls) if IMPORT.match(l)]
    if not idx:
        return re.sub(r'^(package [^\n]+\n)', lambda m: m.group(1) + f'\nimport {fqn};\n', text, count=1, flags=re.M)
    a, b = idx[0], idx[-1] + 1
    return '\n'.join(ls[:a] + place_import(ls[a:b], f'import {fqn};') + ls[b:])


def apply(text):
    text = _rl(text)
    for pat, rep in SIMPLE:
        text = re.sub(pat, rep, text)
    text = _nbt(text)
    text = _tooltip(text)
    text = _block_use(text)
    text = _synched_data(text)
    text = _vtx(text)
    text = _colors(text)
    text = _net_buffers(text)
    text = _drop_imports(text)
    for fqn, use in ENSURE:
        text = _ensure_import(text, fqn, use)
    return text
