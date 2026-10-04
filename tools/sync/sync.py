# wrote this after a day without sleep. don't review it, don't fix anything here, if you really want to then good luck
import argparse
import hashlib
import io
import os
import re
import shutil
import subprocess
import tarfile
import tempfile

import codemod

HERE = os.path.dirname(os.path.abspath(__file__))
ROOT = os.path.dirname(os.path.dirname(HERE))
UPSTREAM = PORT = ROOT
WORK = os.path.join(HERE, 'work')
MOJMAP = 'net.minecraft:mappings:1.20.1'
JAVA_ROOT = 'src/main/java/'
# newer commits need java 25 (loom 1.18)
PIN = 'c991a9655'
BUILD = ('build.gradle', 'gradle.properties', 'settings.gradle', 'versioning.gradle', 'gradlew', 'gradlew.bat', 'gradle')
REMAP_PROJ = os.path.join(WORK, 'yarnproj')
MM_PROJ = os.path.join(WORK, 'mmproj')
XFORM = os.path.join(HERE, 'xform', 'build', 'install', 'xform', 'bin', 'xform.bat' if os.name == 'nt' else 'xform')
XFORM_JAR = os.path.join(HERE, 'xform', 'build', 'install', 'xform', 'lib', 'xform.jar')
CLASSPATH = os.path.join(MM_PROJ, 'build', 'classpath.txt')
COMPONENTS = JAVA_ROOT + 'dev/amble/ait/core/AITDataComponents.java'

RESOURCE_RENAMES = [
    ('/structures/', '/structure/'),
    ('/tags/blocks/', '/tags/block/'),
    ('/tags/items/', '/tags/item/'),
    ('/tags/entity_types/', '/tags/entity_type/'),
    ('/tags/fluids/', '/tags/fluid/'),
    ('/tags/game_events/', '/tags/game_event/'),
    ('/loot_tables/', '/loot_table/'),
    ('/recipes/', '/recipe/'),
    ('/advancements/', '/advancement/'),
    ('/predicates/', '/predicate/'),
    ('/item_modifiers/', '/item_modifier/'),
    ('/functions/', '/function/'),
]

BUILD_FILES = re.compile(r'^(\.github/|gradle/|gradlew(\.bat)?$|(build|settings|versioning)\.gradle$|gradle\.properties$|flake\.(nix|lock)$|jitpack\.yml$)')
BINARY = ('.png', '.jpg', '.jpeg', '.gif', '.webp', '.ogg', '.wav', '.nbt', '.ttf', '.otf', '.zip', '.jar', '.dat')

MARK = '//@@sync-imports@@'
IMPORT = codemod.IMPORT

SECTIONS = (
    ('error', 'failed, nothing written for these'),
    ('components', 'xform components changed or missing in the port, check AITDataComponents and the item files'),
    ('conflict', 'conflicts to resolve by hand'),
    ('exists', 'added upstream but already in the port, diff by hand'),
    ('added', 'new upstream files, port them to 1.21/NeoForge'),
    ('fabric', 'merged but now reference net.fabricmc'),
    ('missing', 'changed upstream, absent in the port'),
    ('drift', 'dependency versions ahead of work/yarnproj, bump them there if new api stays unmapped'),
    ('build', 'upstream build/ci files, not synced (the port has its own build)'),
    ('kept', 'deleted upstream, modified in the port (kept)'),
    ('deleted', 'deleted'),
    ('merged', 'merged cleanly'),
)


def run(cmd, cwd=None, check=True, env=None):
    r = subprocess.run(cmd, cwd=cwd, capture_output=True, text=True, encoding='utf-8', errors='replace', env=env)
    if check and r.returncode not in (0, 1):
        raise SystemExit(f'{cmd}: {r.stderr.strip()}')
    return r


def git(*args):
    return run(['git', *args], cwd=UPSTREAM)


def rev(ref):
    return git('rev-parse', '--short=9', ref).stdout.strip()


def extract(commit, path, *names):
    buf = subprocess.run(['git', 'archive', '--format=tar', commit, *names], cwd=UPSTREAM, capture_output=True, check=True).stdout
    with tarfile.open(fileobj=io.BytesIO(buf)) as t:
        t.extractall(path, filter='data')


def worktree(commit):
    p = os.path.join(WORK, commit)
    if not os.path.isdir(p) or not os.listdir(p):
        os.makedirs(p, exist_ok=True)
        extract(commit, p)
    return p


def gradle(proj, *args, cwd=None):
    gw = os.path.join(proj, 'gradlew.bat' if os.name == 'nt' else 'gradlew')
    r = run([gw, *args, '--console=plain', '-q'], cwd=cwd or proj, check=False)
    if r.returncode != 0:
        raise SystemExit(f'{" ".join(args)} failed in {cwd or proj}:\n{r.stdout[-2000:]}\n{r.stderr[-2000:]}')


def project(path):
    if not os.path.exists(os.path.join(path, 'build.gradle')):
        extract(PIN, path, *BUILD)
    return path


def tools():
    if not os.path.exists(CLASSPATH):
        project(MM_PROJ)
        gf = os.path.join(MM_PROJ, 'build.gradle')
        text = read(gf)
        text = text.replace('    accessWidenerPath = file("src/main/resources/ait.accesswidener")\n', '')
        text = text.replace('mappings "net.fabricmc:yarn:${project.yarn_mappings}:v2"', 'mappings loom.officialMojangMappings()')
        text += '\ntasks.register("dumpClasspath") {\n    doLast {\n        file("build/classpath.txt").text = sourceSets.main.compileClasspath.files.join("\\n")\n    }\n}\n'
        write(gf, text)
        gradle(MM_PROJ, 'dumpClasspath')
    if not os.path.exists(XFORM_JAR):
        gradle(ROOT, '-p', os.path.join(HERE, 'xform'), 'installDist', cwd=ROOT)


def remapped(commit):
    out = os.path.join(WORK, commit + '-mm')
    if os.path.isdir(out) and os.listdir(out):
        return out
    wt = worktree(commit)
    project(REMAP_PROJ)
    shutil.rmtree(out + '-x', ignore_errors=True)
    # mercury: lambda bodies lose bindings without fabric.mod.json
    md = os.path.join(REMAP_PROJ, 'src', 'main')
    shutil.rmtree(md, ignore_errors=True)
    shutil.copytree(os.path.join(wt, 'src', 'main', 'java'), os.path.join(md, 'java'))
    os.makedirs(os.path.join(md, 'resources'))
    for name in ('fabric.mod.json', 'ait.accesswidener'):
        shutil.copy(os.path.join(wt, 'src', 'main', 'resources', name), os.path.join(md, 'resources'))
    gw = os.path.join(REMAP_PROJ, 'gradlew.bat' if os.name == 'nt' else 'gradlew')
    # first run fails after shared cache rebuild
    for _ in range(2):
        r = run([gw, 'migrateMappings', '--mappings', MOJMAP, '--input', 'src/main/java', '--output', out,
                 '--console=plain', '-q'], cwd=REMAP_PROJ, check=False)
        if r.returncode == 0:
            break
    if r.returncode != 0:
        raise SystemExit(f'migrateMappings failed for {commit}:\n{r.stdout[-2000:]}\n{r.stderr[-2000:]}')
    return out


def transformed(commit):
    mm = remapped(commit)
    out = mm + '-x'
    tools()
    h = hashlib.sha1()
    for p in (XFORM_JAR, CLASSPATH):
        with open(p, 'rb') as f:
            h.update(f.read())
    h = h.hexdigest()
    if read(os.path.join(out, '.stamp')) == h:
        return out
    shutil.rmtree(out, ignore_errors=True)
    env = dict(os.environ, JAVA_OPTS='-Xmx6g')
    r = run([XFORM, mm, CLASSPATH, out], check=False, env=env)
    if r.returncode != 0:
        raise SystemExit(f'xform failed for {commit}:\n{r.stdout[-2000:]}\n{r.stderr[-2000:]}')
    write(os.path.join(out, '.stamp'), h)
    return out


def converted(report):
    m = re.search(r'^converted:\n((?:[ \t].*\n?)*)', report or '', re.M)
    return set(re.findall(r'^\s+(\S+) -> (\w+) : (\w+)\s*$', m.group(1), re.M)) if m else set()


def registered(java):
    return {(k, f, t) for t, f, k in re.findall(r'DataComponentType<(\w+)> (\w+) = register\("([^"]+)"', java or '')}


def drift(commit):
    def kv(text):
        return dict(re.findall(r'^\s*(\w+_version)\s*=\s*(\S+)', text or '', re.M))
    ours = kv(read(os.path.join(project(REMAP_PROJ), 'gradle.properties')))
    theirs = kv(show(commit, 'gradle.properties'))
    return [f'{k}: {ours.get(k)} -> {v}' for k, v in theirs.items() if k != 'loom_version' and ours.get(k) != v]


def read(path):
    if path is None or not os.path.exists(path):
        return None
    with open(path, encoding='utf-8', errors='surrogateescape', newline='') as f:
        return f.read().replace('\r\n', '\n')


def write(path, text):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    if os.path.exists(path):
        with open(path, 'rb') as f:
            if b'\r\n' in f.read():
                text = text.replace('\n', '\r\n')
    with open(path, 'w', encoding='utf-8', errors='surrogateescape', newline='') as f:
        f.write(text)


def blob(commit, path):
    r = subprocess.run(['git', 'show', f'{commit}:{path}'], cwd=UPSTREAM, capture_output=True)
    return r.stdout if r.returncode == 0 else None


def show(commit, path):
    b = blob(commit, path)
    return None if b is None else b.decode('utf-8', 'surrogateescape').replace('\r\n', '\n')


def binary(path, data=None):
    return path.lower().endswith(BINARY) or (data is not None and b'\0' in data[:8000])


def port_path(path):
    if path.startswith(JAVA_ROOT):
        return path
    for old, new in RESOURCE_RENAMES:
        path = path.replace(old, new)
    return path


def upstream_text(mm, x, commit, path):
    if not (path.endswith('.java') and path.startswith(JAVA_ROOT)):
        return show(commit, path)
    rel = path[len(JAVA_ROOT):]
    src = os.path.join(x, rel) if x else None
    text = read(src if src and os.path.exists(src) else os.path.join(mm, rel))
    text = show(commit, path) if text is None else text
    return None if text is None else codemod.apply(text)


def splitimp(text):
    lines = text.split('\n')
    imps, body, block, first = [], [], [], None
    for i, ln in enumerate(lines):
        if IMPORT.match(ln):
            imps.append(ln.strip())
            block.append(ln)
            if first is None:
                first = len(body)
                body.append(MARK)
            continue
        if first is not None and ln.strip() == '' and i + 1 < len(lines) and IMPORT.match(lines[i + 1]):
            block.append(ln)
            continue
        body.append(ln)
    return imps, '\n'.join(body), block


def layout(block, imports):
    keep = set(imports)
    out = [l for l in block if not IMPORT.match(l) or l.strip() in keep]
    have = {l.strip() for l in out if IMPORT.match(l)}
    for i in imports:
        if i not in have:
            out = codemod.place_import(out, i)
    while out and not out[0].strip():
        out.pop(0)
    while out and not out[-1].strip():
        out.pop()
    return [l for i, l in enumerate(out) if l.strip() or (i and out[i - 1].strip())]


def mimp(ours, base, theirs):
    added = [i for i in theirs if i not in base]
    gone = set(base) - set(theirs)
    out = [i for i in ours if i not in gone]
    out += [i for i in added if i not in out]
    return out


def joinimp(imports, body):
    if MARK in body:
        return body.replace(MARK, '\n'.join(imports)) if imports else body.replace(MARK + '\n', '', 1)
    if not imports:
        return body
    return re.sub(r'^(package [^\n]+\n)', lambda m: m.group(1) + '\n' + '\n'.join(imports) + '\n', body, count=1, flags=re.M)


def merge3(ours, base, theirs, java):
    if java:
        oi, ob, oblock = splitimp(ours)
        bi, bb, _ = splitimp(base)
        ti, tb, _ = splitimp(theirs)
    else:
        ob, bb, tb = ours, base, theirs
    with tempfile.TemporaryDirectory() as d:
        ps = []
        for name, text in (('ours', ob), ('base', bb), ('theirs', tb)):
            p = os.path.join(d, name)
            with open(p, 'w', encoding='utf-8', errors='surrogateescape', newline='') as f:
                f.write(text)
            ps.append(p)
        r = run(['git', 'merge-file', '-p', '-L', 'port', '-L', 'upstream-old', '-L', 'upstream-new', *ps], check=False)
        if r.returncode < 0 or r.returncode > 127:
            raise RuntimeError(f'merge-file failed: {r.stderr.strip()}')
        text, bad = r.stdout, r.returncode != 0
    if java:
        mi = mimp(oi, bi, ti)
        text = joinimp(layout(oblock, mi) if oblock else mi, text)
    return text, bad


def sync_binary(status, old, new, port, base_c, new_c, dry, report):
    src, dst = os.path.join(port, port_path(old)), os.path.join(port, port_path(new))
    ours = open(src, 'rb').read() if os.path.exists(src) else None
    base = None if status == 'A' else blob(base_c, old)
    if status == 'D':
        if ours is not None:
            report['deleted' if ours == base else 'kept'].append(old)
            if ours == base and not dry:
                os.remove(src)
        return
    theirs = blob(new_c, new)
    if ours == theirs and src == dst:
        return
    if ours is None and status != 'A':
        report['missing'].append(new)
        return
    if ours is not None and ours not in (base, theirs):
        report['exists' if status == 'A' else 'conflict'].append(new + ' (binary, port copy kept)')
        return
    report['added' if ours is None else 'merged'].append(new)
    if not dry:
        os.makedirs(os.path.dirname(dst), exist_ok=True)
        with open(dst, 'wb') as f:
            f.write(theirs)
        if dst != src and os.path.exists(src):
            os.remove(src)


def sync_text(status, old, new, port, trees, dry, report):
    (base_mm, base_x, base_c), (new_mm, new_x, new_c) = trees
    java = new.endswith('.java') and new.startswith(JAVA_ROOT)
    src, dst = os.path.join(port, port_path(old)), os.path.join(port, port_path(new))
    ours = read(src)
    if status == 'D':
        if ours is not None:
            base = upstream_text(base_mm, base_x, base_c, old)
            report['deleted' if ours == base else 'kept'].append(old)
            if ours == base and not dry:
                os.remove(src)
        return
    theirs = upstream_text(new_mm, new_x, new_c, new)
    if theirs is None:
        raise RuntimeError('no upstream text')
    if status == 'A':
        if ours is None:
            report['added'].append(new)
            if not dry:
                write(dst, theirs)
        elif ours != theirs:
            report['exists'].append(new)
        return
    if ours is None:
        report['missing'].append(new)
        return
    base = upstream_text(base_mm, base_x, base_c, old)
    if base is None:
        raise RuntimeError('no upstream base')
    text, bad = merge3(ours, base, theirs, java)
    report['conflict' if bad else 'merged'].append(new)
    if java and any(l.startswith('import net.fabricmc') and l not in ours for l in text.splitlines()):
        report['fabric'].append(new)
    if not dry:
        if dst != src and os.path.exists(src):
            os.remove(src)
        write(dst, text)


def sync(base, new, port=PORT, dry=False, xform=True):
    base_c, new_c = rev(base), rev(new)
    base_mm, new_mm = remapped(base_c), remapped(new_c)
    base_x, new_x = (transformed(base_c), transformed(new_c)) if xform else (None, None)
    report = {key: [] for key, _ in SECTIONS}
    report['drift'] = drift(new_c)

    if new_x:
        rf = 'item-components-report.txt'
        was, now = (converted(read(os.path.join(x, rf))) for x in (base_x, new_x))
        miss = now - registered(read(os.path.join(port, COMPONENTS)))
        report['components'] = [f'{"+" if c in now else "-"} {c[0]} -> {c[1]} : {c[2]}' for c in sorted(was ^ now)]
        report['components'] += [f'not in the port: {c[0]} -> {c[1]} : {c[2]}' for c in sorted(miss)]
        if miss and not dry:
            raise SystemExit('register these in AITDataComponents first:\n' + '\n'.join(report['components']))

    trees = ((base_mm, base_x, base_c), (new_mm, new_x, new_c))
    for line in git('diff', '--name-status', '-M', base_c, new_c).stdout.splitlines():
        parts = line.split('\t')
        status, old = parts[0][0], parts[1]
        new = parts[2] if status == 'R' else parts[1]
        if BUILD_FILES.match(new):
            report['build'].append(new)
            continue
        try:
            if binary(new, blob(base_c, old) if status == 'D' else blob(new_c, new)):
                sync_binary(status, old, new, port, base_c, new_c, dry, report)
            else:
                sync_text(status, old, new, port, trees, dry, report)
        except Exception as e:
            report['error'].append(f'{new}: {e}')

    lines = [f'# upstream {base_c}..{new_c} -> {port}{" (dry)" if dry else ""}', '']
    for key, title in SECTIONS:
        lines.append(f'## {title}: {len(report[key])}')
        lines += [f'- {p}' for p in report[key]]
        lines.append('')
    with open(os.path.join(HERE, 'report.md'), 'w', encoding='utf-8') as f:
        f.write('\n'.join(lines))
    print('\n'.join(l for l in lines if l.startswith('#')))
    return report


def main():
    ap = argparse.ArgumentParser(prog='sync.py', allow_abbrev=False)
    ap.add_argument('base', metavar='FROM')
    ap.add_argument('new', metavar='TO')
    ap.add_argument('--port', default=PORT)
    ap.add_argument('--dry', action='store_true')
    ap.add_argument('--no-xform', action='store_true')
    args = ap.parse_args()
    sync(args.base, args.new, args.port, args.dry, not args.no_xform)


if __name__ == '__main__':
    main()
