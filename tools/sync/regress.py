# usage: regress.py HASH [--save [--force]] [--show PAT]
import difflib
import os
import re
import sys

import sync

HASH = sys.argv[1]
MM = os.path.join(sync.WORK, HASH + '-mm')
X = MM + '-x'
JAVA = os.path.join(sync.PORT, 'src', 'main', 'java')
BASE = os.path.join(sync.HERE, 'baseline.txt')


def body(text):
    return [l.rstrip() for l in text.split('\n') if l.strip() and not sync.IMPORT.match(l)]


def pairs():
    x = X if os.path.isdir(X) else None
    for root, _, files in os.walk(MM):
        for name in files:
            if not name.endswith('.java'):
                continue
            rel = os.path.relpath(os.path.join(root, name), MM).replace(os.sep, '/')
            port = sync.read(os.path.join(JAVA, rel))
            if port is not None:
                yield rel, body(sync.upstream_text(MM, x, HASH, sync.JAVA_ROOT + rel)), body(port)


if __name__ == '__main__':
    if '--show' in sys.argv:
        pat = re.compile(sys.argv[sys.argv.index('--show') + 1])
        n = 0
        for rel, a, b in pairs():
            sm = difflib.SequenceMatcher(None, a, b, autojunk=False)
            for op, i1, i2, j1, j2 in sm.get_opcodes():
                if op != 'equal' and pat.search('\n'.join(a[i1:i2] + b[j1:j2])) and n < 60:
                    n += 1
                    print('=====', rel)
                    for l in a[i1:i2]:
                        print('  -', l.strip()[:160])
                    for l in b[j1:j2]:
                        print('  +', l.strip()[:160])
        sys.exit()

    same, lines = set(), 0
    for rel, a, b in pairs():
        if a == b:
            same.add(rel)
        else:
            lines += sum(1 for l in difflib.unified_diff(a, b, lineterm='', n=0) if l[:1] in '+-' and not l.startswith(('+++', '---')))
    old = set(open(BASE).read().replace('\\', '/').split('\n')) - {''} if os.path.exists(BASE) else set()
    print(f'identical {len(same)} (baseline {len(old)}), differing lines {lines}')
    lost = sorted(old - same)
    if lost:
        print('LOST:', *lost, sep='\n  ')
    if '--save' in sys.argv:
        if lost and '--force' not in sys.argv:
            sys.exit('not saved, files above lost their match, --force to accept')
        with open(BASE, 'w') as f:
            f.write('\n'.join(sorted(same)))
        print('baseline saved')
    elif lost:
        sys.exit(1)
