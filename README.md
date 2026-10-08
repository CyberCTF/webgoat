# OWASP WebGoat

[OWASP WebGoat](https://owasp.org/www-project-webgoat/) by the WebGoat team: a deliberately
insecure application that teaches web application security through guided lessons, with WebWolf,
a companion that plays the attacker's server. This repository runs it with
[Isoloom](https://www.isoloom.com): [`isoloom.yml`](isoloom.yml) describes the machine, and the
upstream source in [`build/webgoat/app/`](build/webgoat/app) is built by Maven inside its own
Dockerfile.

| Machine | Service |
| --- | --- |
| webgoat | WebGoat on port 8080, WebWolf on port 9090 |

## Run it

```bash
isoloom generate
isoloom run docker
```

Then open http://localhost:8080/WebGoat, register a user, and use the same account on WebWolf at
http://localhost:9090/WebWolf. The lessons link to WebWolf at `127.0.0.1:9090`, so keep both ports
published as they are. The same spec runs as Docker on a local VM (`docker-vm`), on a cloud VM
(`cloud-docker`) or on Kubernetes. Lab guide: the lessons themselves and the
[WebGoat wiki](https://github.com/WebGoat/WebGoat/wiki).

Upstream version and commit: [UPSTREAM.md](UPSTREAM.md).

## Licence

GPL-2.0-or-later, as WebGoat ([LICENSE](LICENSE), [COPYRIGHT.txt](COPYRIGHT.txt)). This
application is deliberately vulnerable: keep it isolated.
