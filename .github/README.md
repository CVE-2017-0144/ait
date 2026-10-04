<div align="center">

<img src="../promo/logo.png" alt="AIT" width="600" />

[![build](https://github.com/CVE-2017-0144/ait/actions/workflows/neoforge.yml/badge.svg)](https://github.com/CVE-2017-0144/ait/actions/workflows/neoforge.yml)
[![upstream](https://github.com/CVE-2017-0144/ait/actions/workflows/upstream.yml/badge.svg)](https://github.com/CVE-2017-0144/ait/actions/workflows/upstream.yml)

</div>

Unofficial NeoForge 1.21.1 port of [Adventures in Time](https://github.com/amblelabs/ait) by AmbleLabs, same content as the Fabric 1.20.1 version.

Port bugs go here, not upstream. If it happens on Fabric too, it's an upstream bug.

### Download

Public builds go to [releases](https://github.com/CVE-2017-0144/ait/releases) once the port is out of beta. Until then there are only dev builds, beta locked like upstream's and handed out to AmbleLabs beta testers.

Needs NeoForge 21.1.x (MC 1.21.1) and [YACL](https://modrinth.com/mod/yacl) 3.8+. [Immersive Portals](https://modrinth.com/mod/immersive-portals-neoforge) 6.0.7 is optional, with it the doors are real portals. Works with the NeoForge builds of [Sodium](https://modrinth.com/mod/sodium) and [Iris](https://modrinth.com/mod/iris).

### Building

Java 21, `./gradlew runData build`, the jar ends up in `build/libs`.

### Upstream

`main` mirrors amblelabs/ait. Every few hours `tools/sync` merges new upstream commits into `neoforge` and opens a `sync/<commit>` PR, what doesn't merge becomes an issue.

### License

LGPL-3.0 like upstream.
