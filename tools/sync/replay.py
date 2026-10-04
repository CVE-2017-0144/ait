# usage: replay.py FROM TO REF [sync.py flags]
import io
import os
import shutil
import subprocess
import sys
import tarfile

import sync

base, new, ref = sys.argv[1:4]
fl = sys.argv[4:]
out = os.path.join(sync.WORK, 'replay')
shutil.rmtree(out, ignore_errors=True)
os.makedirs(out)
tar = subprocess.run(['git', 'archive', ref + '~1'], cwd=sync.PORT, capture_output=True, check=True).stdout
with tarfile.open(fileobj=io.BytesIO(tar)) as t:
    t.extractall(out, filter='data')
if subprocess.run([sys.executable, os.path.join(sync.HERE, 'sync.py'), base, new, '--port', out, *fl]).returncode:
    sys.exit(1)


def norm(s):
    ls = [l.rstrip() for l in s.split('\n')]
    return sorted(l for l in ls if sync.IMPORT.match(l)), [l for l in ls if l.strip() and not sync.IMPORT.match(l)]


sec, files = None, {}
for ln in open(os.path.join(sync.HERE, 'report.md'), encoding='utf-8'):
    if ln.startswith('## '):
        sec = ln[3:].split(':')[0]
        files[sec] = []
    elif ln.startswith('- ') and sec:
        files[sec].append(ln[2:].strip().split(' (')[0])

for title in ('merged cleanly', 'conflicts to resolve by hand'):
    same = imports = differ = 0
    smp = []
    for path in files.get(title, []):
        dst = sync.port_path(path)
        r = subprocess.run(['git', 'show', f'{ref}:{dst}'], cwd=sync.PORT, capture_output=True)
        tst = sync.read(os.path.join(out, dst))
        if r.returncode or tst is None:
            continue
        ti, tb = norm(tst)
        ri, rb = norm(r.stdout.decode('utf-8', 'surrogateescape').replace('\r\n', '\n'))
        if (ti, tb) == (ri, rb):
            same += 1
        elif tb == rb:
            imports += 1
        else:
            differ += 1
            smp.append(dst)
    print(f'{title}: identical {same}, imports differ {imports}, body differs {differ}')
    for p in smp[:40]:
        print('  ', p)
