# AGENTS.md

Guidelines for contributors and coding agents working in this repository.

## Project Snapshot

- Android game project (`app`) with a vendored AndEngine module (`AndEngine`).
- Build system: Gradle wrapper + Android Gradle Plugin.
- Current toolchain baseline:
  - JDK: 21 recommended (17+ required by AGP)
  - Gradle wrapper: 9.1.0
  - Android Gradle Plugin: 9.0.0
  - compileSdkVersion: 35 (app + AndEngine)
  - targetSdkVersion: 35 (app + AndEngine)
  - minSdkVersion: 24 (app + AndEngine)
  - app namespace / applicationId: `com.sopaapp`

## Important Repository Facts

- `AndEngine` is **not** a git submodule anymore. Treat `/AndEngine` as normal source inside this repo.
- Do not try `git submodule update --init --recursive` for `AndEngine`.
- Keep changes in `AndEngine` minimal and targeted; prefer compatibility fixes over large refactors.

## Local Setup

1. Use JDK 21 in Android Studio (`Gradle JDK`).
2. Ensure SDK platform for API 35 is installed.
3. Build from repository root.

## Common Commands

- Debug build:
  - `./gradlew assembleDebug`
- Unit tests:
  - `./gradlew testDebugUnitTest`
- Full release artifact:
  - `./gradlew clean :app:bundleRelease`
- JaCoCo report:
  - `./gradlew jacocoTestReport`

Release bundle output:
- `app/build/outputs/bundle/release/app-release.aab`

Release signing:
- Configure local `keystore.properties` in repo root with:
  - `storeFile=...`
  - `keyAlias=...`
  - `storePassword=...`
  - `keyPassword=...`
- Do not commit `keystore.properties`.
- If keystore path points to iCloud (`/Users/<user>/Library/Mobile Documents/...`), Gradle may fail with
  permission errors. Use a local path (e.g. `/Users/<user>/keys/...`).

## Coding Notes

- The app and engine modules currently compile Java sources with `sourceCompatibility`/`targetCompatibility` set to Java 8 bytecode.
- Keep AndroidX migrations or major API changes out of incidental fixes unless explicitly requested.
- Preserve package names (`com.sopaapp`, `org.andengine`) to avoid manifest/resource/package regressions.
- `versionCode` must be incremented for each Play Console upload.
