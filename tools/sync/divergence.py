# usage: divergence.py HASH [--codemod]
import collections
import difflib
import os
import re
import sys

import sync
from regress import HASH, JAVA, MM, X, body

PATTERNS = [
    ('item nbt', r'ItemNbt|getOrCreateTag|getTag\(\)|setTag\(|CUSTOM_DATA|DataComponents|CustomData'),
    ('networking', r'AitNetworking|PacketByteBuf|FriendlyByteBuf|RegistryFriendlyByteBuf|PlayNetworking|CustomPacketPayload|StreamCodec|PacketSender'),
    ('resourcelocation', r'ResourceLocation\.(fromNamespaceAndPath|withDefaultNamespace|parse)|new ResourceLocation'),
    ('registry/holder', r'Holder|BuiltInRegistries|registryAccess|lookupOrThrow|getHolder'),
    ('render api', r'VertexConsumer|BufferBuilder|PoseStack|RenderSystem|addVertex|setColor|setUv|setNormal|Tesselator'),
    ('events/platform', r'dev\.amble\.lib\.platform|NeoForge\.EVENT_BUS|@SubscribeEvent'),
    ('enchant/effects', r'Enchantment|MobEffect|Holder<'),
    ('codec', r'MapCodec|Codec<|codec\(\)'),
]

mod = '--codemod' in sys.argv
x = X if os.path.isdir(X) else None
st = collections.Counter()
pf = []
for root, _, files in os.walk(MM):
    for name in files:
        if not name.endswith('.java'):
            continue
        rel = os.path.relpath(os.path.join(root, name), MM).replace(os.sep, '/')
        port = sync.read(os.path.join(JAVA, rel))
        if port is None:
            st['missing in port'] += 1
            continue
        up = sync.upstream_text(MM, x, HASH, sync.JAVA_ROOT + rel) if mod else sync.read(os.path.join(root, name))
        up, po = body(up), body(port)
        if up == po:
            st['identical body'] += 1
            continue
        ch = [l for l in difflib.unified_diff(up, po, lineterm='', n=0) if l[:1] in '+-' and not l.startswith(('+++', '---'))]
        st['differs'] += 1
        pf.append((len(ch), rel))
        txt = '\n'.join(ch)
        hit = False
        for key, pat in PATTERNS:
            if re.search(pat, txt):
                st['hunks:' + key] += 1
                hit = True
        if not hit:
            st['hunks:other'] += 1

for k, v in sorted(st.items()):
    print(f'{k}: {v}')
pf.sort(reverse=True)
print('changed lines total:', sum(n for n, _ in pf))
print('top:')
for n, rel in pf[:15]:
    print(f'  {n:5d} {rel}')
