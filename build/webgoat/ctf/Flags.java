/*
 * SPDX-FileCopyrightText: Copyright © 2023 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.lessons.challenges;

// CyberCTF overlay: replaces upstream's Flags.java in the build stage (the vendored source in
// app/ is unchanged). Upstream draws every challenge flag as a random UUID at start. Here the
// flags of the challenges CyberCTF checks come from /home/webgoat/.ctf-flags, a file
// ctf-entrypoint.sh writes at every start from the player's values ("number=flag" per line).
// The file is read once and deleted; any other flag stays a random UUID as upstream.

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Flags {
  private static final Path CTF_FLAGS = Path.of("/home/webgoat/.ctf-flags");
  private final Map<Integer, Flag> FLAGS = new HashMap<>();

  public Flags() {
    Map<Integer, String> given = new HashMap<>();
    try {
      if (Files.exists(CTF_FLAGS)) {
        for (String line : Files.readAllLines(CTF_FLAGS, StandardCharsets.UTF_8)) {
          int eq = line.indexOf('=');
          if (eq > 0) {
            given.put(Integer.parseInt(line.substring(0, eq).trim()), line.substring(eq + 1));
          }
        }
        Files.delete(CTF_FLAGS);
      }
    } catch (Exception e) {
      // keep upstream's behaviour: random flags
    }
    IntStream.range(1, 10)
        .forEach(
            i -> FLAGS.put(i, new Flag(i, given.getOrDefault(i, UUID.randomUUID().toString()))));
  }

  public Flag getFlag(int flagNumber) {
    return FLAGS.get(flagNumber);
  }
}
