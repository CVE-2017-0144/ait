import re
import subprocess
import sys


def git(*a):
    return subprocess.run(['git', *a], capture_output=True, text=True, encoding='utf-8').stdout.strip()


def clean(s):
    return re.sub(r'\s*\(#\d+\)$', '', s).replace('@', '')


tag = sys.argv[1]
if git('cat-file', '-t', tag) == 'tag':
    msg = git('tag', '-l', '--format=%(contents:subject)%0a%0a%(contents:body)', tag).strip()
    if msg:
        print(msg)
        sys.exit()

up = git('show', f'{tag}:tools/sync/upstream')[:9]
prev = git('describe', '--tags', '--abbrev=0', '--match', 'v*', f'{tag}^')
if not prev:
    print(f'first neoforge build, upstream at {up}')
    sys.exit()

ours, syncs = [], []
for s in git('log', '--reverse', '--first-parent', '--format=%s', f'{prev}..{tag}').splitlines():
    m = re.match(r'sync upstream (\w+)\.\.(\w+)', s)
    if m:
        syncs.append(m.groups())
    else:
        ours.append(clean(s))
out = [f'- {s}' for s in ours]
if syncs:
    a, b = syncs[0][0], syncs[-1][1]
    ups = [clean(s) for s in git('log', '--reverse', '--first-parent', '--format=%s', f'{a}..{b}').splitlines()
           if not s.startswith(('docs: update CHANGELOG', 'Merge ')) and not re.match(r'Update [\w./-]+$', s)]
    out += ['', f'upstream {a}..{b}:'] + [f'- {s}' for s in ups]
print('\n'.join(out).strip() or f'nothing new since {prev}, upstream at {up}')
