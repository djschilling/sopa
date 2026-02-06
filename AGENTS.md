# AGENTS.md

Guidelines for contributors and coding agents working in this repository.

## Project Snapshot

- Android game project (`app`) with a vendored AndEngine module (`AndEngine`).
- Build system: Gradle wrapper + Android Gradle Plugin.
- Current toolchain baseline:
  - JDK: 21 recommended (17+ required by AGP)
  - Gradle wrapper: 8.5
  - Android Gradle Plugin: 8.3.2

## Important Repository Facts

- `AndEngine` is **not** a git submodule anymore. Treat `/AndEngine` as normal source inside this repo.
- Do not try `git submodule update --init --recursive` for `AndEngine`.
- Keep changes in `AndEngine` minimal and targeted; prefer compatibility fixes over large refactors.

## Local Setup

1. Use JDK 21 in Android Studio (`Gradle JDK`).
2. Ensure SDK platform for API 34 is installed.
3. Build from repository root.

## Common Commands

- Debug build:
  - `./gradlew assembleDebug`
- Unit tests:
  - `./gradlew testDebugUnitTest`
- Full release artifact:
  - `./gradlew clean :app:assembleRelease`
- JaCoCo report:
  - `./gradlew jacocoTestReport`

## Coding Notes

- The app and engine modules currently compile Java sources with `sourceCompatibility`/`targetCompatibility` set to Java 8 bytecode.
- Keep AndroidX migrations or major API changes out of incidental fixes unless explicitly requested.
- Preserve package names (`de.sopa`, `org.andengine`) to avoid manifest/resource/package regressions.
