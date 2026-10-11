#!/bin/sh
# CyberCTF per-player flags, added by the overlay (the vendored WebGoat is unchanged).
# Upstream draws the challenge flags at random at every start. At every start this wrapper
# writes the player's flags (given by the launcher; else the development values) to a file the
# overlay's Flags.java reads once and deletes, drops them from the environment, then starts
# WebGoat exactly as before.
#   challenge 1 Admin lost password   CTF_FLAG_ADMIN_LOST_PASSWORD
#   challenge 5 Without password      CTF_FLAG_WITHOUT_PASSWORD
#   challenge 7 Admin password reset  CTF_FLAG_MAIN
#   challenge 8 Without account       CTF_FLAG_WITHOUT_ACCOUNT
f=/home/webgoat/.ctf-flags
d1='FLAG{dev-webgoat-admin-lost-password}'
d5='FLAG{dev-webgoat-without-password}'
d7='FLAG{dev-webgoat-admin-password-reset}'
d8='FLAG{dev-webgoat-without-account}'
umask 077
{
  printf '1=%s\n' "${CTF_FLAG_ADMIN_LOST_PASSWORD:-$d1}"
  printf '5=%s\n' "${CTF_FLAG_WITHOUT_PASSWORD:-$d5}"
  printf '7=%s\n' "${CTF_FLAG_MAIN:-$d7}"
  printf '8=%s\n' "${CTF_FLAG_WITHOUT_ACCOUNT:-$d8}"
} > "$f"
unset CTF_FLAG_ADMIN_LOST_PASSWORD CTF_FLAG_WITHOUT_PASSWORD CTF_FLAG_MAIN CTF_FLAG_WITHOUT_ACCOUNT
exec "$@"
