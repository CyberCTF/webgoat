# Upstream

| | |
| --- | --- |
| Project | OWASP WebGoat |
| Repository | https://github.com/WebGoat/WebGoat |
| Version | v2026.4 |
| Commit | 872d6149d4ef29e4929c2f4eda279f7fbedc52e8 |
| Licence | GPL-2.0-or-later |

`build/webgoat/app/` is that commit, unchanged, without its Git history. `build/webgoat/Dockerfile`
is upstream's Dockerfile with one change: upstream copies a jar built beforehand on the host, so a
first stage builds it from `app/` the way upstream's release workflow does (Maven `versions:set`
to 2026.4, then `package` without tests). Maven resolves the exact versions in `pom.xml`. To
update, replace `build/webgoat/app/` with a newer release, then change this table and the version
in the Dockerfile.
