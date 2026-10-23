#!/usr/bin/env sh
set -eu
BASE="${HOME}/.gradle/smartpantry-distributions"
DIST="$BASE/gradle-8.2.1"
if [ ! -x "$DIST/bin/gradle" ]; then
  mkdir -p "$BASE"
  curl -fL "https://services.gradle.org/distributions/gradle-8.2.1-bin.zip" -o "$BASE/gradle-8.2.1-bin.zip"
  unzip -q -o "$BASE/gradle-8.2.1-bin.zip" -d "$BASE"
  rm "$BASE/gradle-8.2.1-bin.zip"
fi
exec "$DIST/bin/gradle" -p "$(cd "$(dirname "$0")" && pwd)" "$@"
