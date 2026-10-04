import json
import os
import re
import subprocess
import sys

import sync

REPO = os.environ['GH_REPO']
UP = 'https://redirect.github.com/amblelabs/ait'
MARKER = os.path.join(sync.HERE, 'upstream')
LABELS = {
    'upstream': ('1d76db', 'came from amblelabs/ait'),
    'conflict': ('b60205', 'upstream change did not merge clean'),
    'needs port': ('d93f0b', 'port it to neoforge by hand'),
    'needs check': ('fbca04', 'merged or skipped, look at it'),
    'deps': ('0052cc', 'upstream build or dependency change'),
    'broken build': ('e11d21', 'sync pr does not build'),
}
KIND = {'conflict': 'conflict', 'fabric': 'needs port', 'added': 'needs check', 'exists': 'needs check',
        'missing': 'needs check', 'kept': 'needs check', 'error': 'needs check', 'build': 'deps'}
SKIP = re.compile(r'^(\.github/|flake\.|jitpack\.yml$)')
DIFF = 8000
BODY = 60000


def sh(*cmd, input=None):
    r = subprocess.run(cmd, cwd=sync.ROOT, input=input, capture_output=True, text=True, encoding='utf-8', errors='replace')
    if r.returncode:
        raise SystemExit(f'{" ".join(cmd[:3])}: {r.stderr.strip()}')
    return r.stdout


def gh(*args, input=None):
    return sh('gh', *args, '--repo', REPO, input=input)


def find(key, state='all'):
    out = json.loads(gh('issue', 'list', '--state', state, '--search', f'"{key}" in:title', '--json', 'number,title,state'))
    return next((i for i in out if i['title'].startswith(key)), None)


def issue(key, title, body, lb):
    i = find(key)
    if i:
        return i['number'], i['state'] == 'OPEN'
    return num(gh('issue', 'create', '--title', title, '--body-file', '-', *sum((['--label', l] for l in lb), []), input=body)), True


def num(url):
    return int(url.strip().rsplit('/', 1)[1])


def change(sha, path):
    out = sh('git', 'diff', '-M', f'{sha}^1', sha, '--', path).strip()
    if not out or '\0' in out or 'Binary files' in out:
        return None
    return out if len(out) <= DIFF else out[:DIFF] + '\n... cut'


def todo(base, new):
    if base == new:
        return False
    n9 = new[:9]
    if json.loads(gh('pr', 'list', '--state', 'all', '--head', f'sync/{n9}', '--json', 'number')):
        return False
    return find(f'sync blocked at {n9}', 'open') is None


def main():
    base, new = sys.argv[1], sys.argv[2]
    if '--check' in sys.argv:
        print(f'run={int(todo(base, new))}')
        return
    b9, n9 = base[:9], new[:9]
    for name, (color, desc) in LABELS.items():
        gh('label', 'create', name, '--color', color, '--description', desc, '--force')

    try:
        rep = sync.sync(base, new)
    except SystemExit as e:
        msg = str(e)
        if 'AITDataComponents' in msg:
            t = f'sync blocked at {n9}'
            issue(t, t, f'```\n{msg[-BODY:]}\n```\n', ['needs port'])
        raise

    rn = {}
    for line in sync.git('diff', '--name-status', '-M', base, new).stdout.splitlines():
        p = line.split('\t')
        if p[0][0] == 'R':
            rn[p[2]] = p[1]

    st = {}
    for key, label in KIND.items():
        for e in rep[key]:
            p = e.split(': ')[0] if key == 'error' else e.split(' (')[0]
            if key == 'added' and not p.endswith('.java') or key == 'build' and SKIP.match(p):
                continue
            st.setdefault(p, label)

    ps = set()
    for p in rep['merged'] + rep['added'] + rep['deleted']:
        ps.add(sync.port_path(p))
        if p in rn:
            ps.add(sync.port_path(rn[p]))
    trk = set(sh('git', 'ls-files', '-z').split('\0'))
    ps = sorted(p for p in ps if p in trk or os.path.exists(os.path.join(sync.ROOT, p)))
    with open(MARKER, 'w', newline='\n') as f:
        f.write(new + '\n')
    ps.append(os.path.relpath(MARKER, sync.ROOT).replace(os.sep, '/'))
    sh('git', 'add', '-A', '--pathspec-from-file=-', '--pathspec-file-nul', input='\0'.join(ps))
    who = [l.split('\x1f') for l in sh('git', 'log', '--format=%cn%x1f%ce').splitlines()]
    name, mail = next(w for w in who if w[0] != 'GitHub')
    sh('git', '-c', f'user.name={name}', '-c', f'user.email={mail}', 'commit', '-qm', f'sync upstream {b9}..{n9}')
    head = f'sync/{n9}'
    sh('git', 'push', '-fq', 'origin', f'HEAD:refs/heads/{head}')

    lines, cl, left = [], [], []
    log = sh('git', 'log', '--reverse', '--first-parent', '--format=%H%x1f%an%x1f%s', f'{base}..{new}')
    for row in log.splitlines():
        sha, author, subj = row.split('\x1f')
        m = re.search(r'\s*\(#(\d+)\)$', subj)
        title = subj[:m.start()] if m else subj
        link = f'[amblelabs/ait#{m.group(1)}]({UP}/pull/{m.group(1)})' if m else f'[{sha[:9]}]({UP}/commit/{sha})'
        files = sh('git', 'diff', '--name-only', '-M', f'{sha}^1', sha).splitlines()
        items = [(p, st[p]) for p in files if p in st]
        line = f'- `{title.replace("`", "")}` {link}'
        if m or items:
            body = [f'{link} by {author.replace("@", "")}', '']
            body += [f'- [ ] `{p}` {k}' for p, k in items]
            size = sum(map(len, body))
            for p, k in items:
                d = change(sha, p)
                if d and size + len(d) < BODY:
                    body += ['', f'<details><summary>{p}</summary>', '', '````diff', d, '````', '', '</details>']
                    size += len(d)
            key = f'upstream {sha[:9]}'
            n, still = issue(key, f'{key}: {title}'[:250], '\n'.join(body) + '\n', ['upstream'] + sorted({k for _, k in items}))
            line += f' #{n}'
            if items:
                left.append(n)
            elif still:
                cl.append(n)
        lines.append(line)

    cnt = ', '.join(f'{k} {len(rep[k])}' for k in ('merged', 'added', 'deleted', 'conflict', 'exists', 'missing', 'kept', 'error') if rep[k])
    txt = [f'upstream {b9}..{n9}', '', *lines, '', cnt or 'nothing to merge']
    if rep['drift'] or rep['components']:
        txt += ['', '```', *rep['drift'], *rep['components'], '```']
    if left:
        txt += ['', 'by hand: ' + ' '.join(f'#{n}' for n in left)]
    txt += [''] + [f'closes #{n}' for n in cl]
    body = '\n'.join(txt) + '\n'
    if len(body) > BODY:
        body = '\n'.join(txt[:1] + [f'{len(lines)} commits, see the issues'] + txt[2 + len(lines):]) + '\n'

    prs = json.loads(gh('pr', 'list', '--state', 'open', '--json', 'number,headRefName,isCrossRepository'))
    mine = next((p['number'] for p in prs if p['headRefName'] == head), None)
    if mine:
        gh('pr', 'edit', str(mine), '--body-file', '-', input=body)
    else:
        mine = num(gh('pr', 'create', '--base', 'neoforge', '--head', head, '--title', f'sync upstream {b9}..{n9}', '--body-file', '-', input=body))
    for p in prs:
        if p['headRefName'].startswith('sync/') and not p['isCrossRepository'] and p['number'] != mine:
            gh('pr', 'close', str(p['number']), '--comment', f'superseded by #{mine}', '--delete-branch')


if __name__ == '__main__':
    main()
