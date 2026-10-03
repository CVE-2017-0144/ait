#!/usr/bin/env bash
# Switches the working tree to one benchmark arm and rebuilds.
#
#   arm.sh branch     the perf branch as it stands
#   arm.sh main       BASE=<port commit>, plus the harness commands and nothing else
#   arm.sh restore    back to the branch, discarding any arm tree
#
# The main arm needs the harness back-ported when BASE predates it, there is no perf-spawn and no
# profile-client, so there is no way to drive a scenario or take a dump on it. What is added is the
# two command classes, the packet identifier and the client side receiver that calls the same
# debugClientMetricsStart F3+L calls. No renderer, model or render layer is touched, so what gets
# measured is BASE's rendering.
set -u
cd "$(dirname "$0")/../.." || exit 1

MODE=${1:-}

case "$MODE" in
  branch|restore)
    git restore --source=HEAD --staged --worktree -- src/main || exit 1
    echo "tree: perf branch at $(git rev-parse --short HEAD)"
    ;;

  main)
    : "${BASE:?BASE=<port commit> for the main arm}"
    # restore drops what BASE doesn't have, so nothing branch-only gets compiled in and measured
    git restore --source="$BASE" --staged --worktree -- src/main || exit 1

    git checkout HEAD -- \
        src/main/java/dev/amble/ait/core/commands/PerfScenarioCommand.java \
        src/main/java/dev/amble/ait/core/commands/ProfileClientCommand.java || exit 1

    python - <<'PYEOF' || exit 1
import io, re

# The packet identifier and the command registrations, into BASE's AITMod.
p = 'src/main/java/dev/amble/ait/AITMod.java'
s = io.open(p, encoding='utf-8').read()

if 'PROFILE_CLIENT' not in s:
    m = re.search(r'^(\s*)public static final ResourceLocation ', s, re.M)
    assert m, 'no ResourceLocation constant to anchor to in AITMod'
    s = s[:m.start()] + m.group(1) + 'public static final ResourceLocation PROFILE_CLIENT = AITMod.id("profile_client");\n' + s[m.start():]

if 'ProfileClientCommand.register' not in s:
    # Any existing command registration in the same lambda is a valid anchor.
    m = re.search(r'^(\s*)(\w+Command\.register\(dispatcher\);)', s, re.M)
    assert m, 'no command registration to anchor to in AITMod'
    s = s[:m.start()] + m.group(1) + 'ProfileClientCommand.register(dispatcher);\n' \
        + m.group(1) + 'PerfScenarioCommand.register(dispatcher);\n' + s[m.start():]

io.open(p, 'w', encoding='utf-8').write(s)

# The client receiver, into BASE's AITModClient.
p = 'src/main/java/dev/amble/ait/client/AITModClient.java'
s = io.open(p, encoding='utf-8').read()

if 'PROFILE_CLIENT' not in s:
    m = re.search(r'^(\s*)AitNetworking\.registerClientReceiver\(', s, re.M)
    assert m, 'no AitNetworking receiver to anchor to in AITModClient'
    block = (m.group(1) + 'AitNetworking.registerClientReceiver(AITMod.PROFILE_CLIENT, (client, handler, buf, responseSender) ->\n'
             + m.group(1) + '        client.execute(() -> client.debugClientMetricsStart(\n'
             + m.group(1) + '                text -> AITMod.LOGGER.info("[ait-profile] {}", text.getString()))));\n\n')
    s = s[:m.start()] + block + s[m.start():]
    io.open(p, 'w', encoding='utf-8').write(s)

print('harness back-ported onto BASE')
PYEOF
    echo "tree: $BASE at $(git rev-parse --short "$BASE") plus harness"
    ;;

  *)
    echo "usage: $0 branch|main|restore"; exit 2
    ;;
esac

echo "== building"
if ! ./gradlew build -q -x test > /tmp/arm_build.log 2>&1; then
  echo "BUILD FAILED"; grep -E 'error:' /tmp/arm_build.log | sort -u | head -10; exit 3
fi
echo "== built"
