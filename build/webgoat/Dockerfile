# Upstream's Dockerfile (app/Dockerfile) with one change: upstream copies a jar built beforehand
# on the host (target/webgoat-*.jar); here a first stage builds it from app/ the way upstream's
# release workflow does (versions:set to the release version, then the build without tests).
# The runtime stage is upstream's, unchanged.
FROM docker.io/eclipse-temurin:25-jdk-noble AS build
WORKDIR /src
COPY app/ /src
RUN ./mvnw --batch-mode --no-transfer-progress versions:set -DnewVersion=2026.4 -DgenerateBackupPoms=false \
 && ./mvnw --batch-mode --no-transfer-progress package -DskipTests

# We need JDK as some of the lessons needs to be able to compile Java code
FROM docker.io/eclipse-temurin:25-jdk-noble

LABEL name="WebGoat: A deliberately insecure Web Application"
LABEL maintainer="WebGoat team"

RUN \
  useradd -ms /bin/bash webgoat && \
  chgrp -R 0 /home/webgoat && \
  chmod -R g=u /home/webgoat

USER webgoat

COPY --from=build --chown=webgoat /src/target/webgoat-*.jar /home/webgoat/webgoat.jar

EXPOSE 8080
EXPOSE 9090

ENV TZ=Europe/Amsterdam

WORKDIR /home/webgoat
ENTRYPOINT [ "java", \
   "-Duser.home=/home/webgoat", \
   "-Dfile.encoding=UTF-8", \
   "--add-opens", "java.base/java.lang=ALL-UNNAMED", \
   "--add-opens", "java.base/java.util=ALL-UNNAMED", \
   "--add-opens", "java.base/java.lang.reflect=ALL-UNNAMED", \
   "--add-opens", "java.base/java.text=ALL-UNNAMED", \
   "--add-opens", "java.desktop/java.beans=ALL-UNNAMED", \
   "--add-opens", "java.desktop/java.awt.font=ALL-UNNAMED", \
   "--add-opens", "java.base/sun.nio.ch=ALL-UNNAMED", \
   "--add-opens", "java.base/java.io=ALL-UNNAMED", \
   "--add-opens", "java.base/java.util=ALL-UNNAMED", \
   "--add-opens", "java.base/sun.nio.ch=ALL-UNNAMED", \
   "--add-opens", "java.base/java.io=ALL-UNNAMED", \
   "-Drunning.in.docker=true", \
   "-jar", "webgoat.jar", "--server.address=0.0.0.0" ]

HEALTHCHECK --interval=5s --timeout=3s \
  CMD curl --fail http://localhost:8080/WebGoat/actuator/health || exit 1
