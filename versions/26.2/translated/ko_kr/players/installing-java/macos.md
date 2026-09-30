---
title: macOS에 Java 설치
description: macOS에 Java를 설치하는 방법에 대한 단계별 가이드.
authors:
  - dexman545
  - ezfe
next: false
---

이 가이드는 macOS에 Java 21을 설치하는 방법을 안내합니다.

Minecraft 런처에서 자체적인 Java 설치를 제공하기 때문에, 이 단락은 `.jar` 기반의 Fabric 설치기를 사용하고자 하거나, Minecraft 서버 `.jar`를 사용하고자 할 때에만 유용합니다.

## 1. 이미 Java가 설치되었는지 확인하기 {#1-check-if-java-is-already-installed}

터미널(`/Applications/Utilities/Terminal.app`에 위치함) 아래 명령어를 입력하고 <0>엔터</0> 를 누릅니다:

```sh
$(/usr/libexec/java_home -v 21)/bin/java --version
```

다음과 같은 내용이 표시되어야 합니다.

```text:no-line-numbers
openjdk 21.0.9 2025-10-21 LTS
OpenJDK Runtime Environment Temurin-21.0.9+10 (build 21.0.9+10-LTS)
OpenJDK 64-Bit Server VM Temurin-21.0.9+10 (build 21.0.9+10-LTS, mixed mode, sharing)
```

버전 번호를 확인합니다: 이 예시에선 `21.0.9`라고 적혀있습니다.

::: warning

Minecraft: Java Edition 1.21.11을 플레이 하려면, 적어도 Java 21 이상의 Java가 설치되어야 합니다.

만약 이 명령어가 Java 21보다 낮은 버전을 표시한다면, 기존의 Java 설치를 업데이트해야 합니다.

:::

## 2. Java 21 다운로드하고 설치하기 {#2-downloading-and-installing-java}

[Adoptium OpenJDK 21](https://adoptium.net/temurin/releases?version=21&os=mac&arch=any&mode=filter) 설치를 권장합니다:

![Temurin Java 다운로드 홈페이지](/assets/players/installing-java/macos-download-java.png)

"21 - LTS" 버전을 선택하고 `.PKG` 인스톨러 포맷을 고릅니다.
시스템 아키텍처에 맞는 빌드를 선택해야 합니다:

- Apple M 시리즈 칩이 탑재된 기기는, `aarch64` 선택
- 인텔 프로세서가 탑재된 기기는 `x64` 선택
- Mac의 프로세서가 사용되었는지 알아내려면 [이 패이지를 참조하세요.](https://support.apple.com/en-us/116943)

`.pkg` 파일 다운로드가 완료되었다면 파일을 열고 설치를 진행하세요:

![Temurin Java Installer](/assets/players/installing-java/macos-installer.png)

설치를 진행하려면 관리자 비밀번호를 입럭합니다.

![macOS 비밀번호 입력창](/assets/players/installing-java/macos-password-prompt.png)

### Homebrew로 설치하기 {#using-homebrew}

[Homebrew](https://brew.sh)가 이미 설치되어 있다면 아래와 같이 명령어를 실행하여 설치할 수도 있습니다.

```sh
brew install --cask temurin@21
```

## 3. Java 21이 올바르게 설치되었는지 확인하기 {#3-verify-that-java-is-installed}

설치가 완료되면 터미널 창을 다시 열고 명령어 `$(/usr/libexec/java_home -v 21)/bin/java --version`을 실행하여 Java가 잘 설치되었는지 확인할 수 있습니다.

명령어가 성공적으로 실행되었다면, 아래와 같이 표시됩니다:

```text:no-line-numbers
openjdk 21.0.9 2025-10-21 LTS
OpenJDK Runtime Environment Temurin-21.0.9+10 (build 21.0.9+10-LTS)
OpenJDK 64-Bit Server VM Temurin-21.0.9+10 (build 21.0.9+10-LTS, mixed mode, sharing)
```

문제가 발생했다면, 자유롭게 [Fabric 디스코드(영어)](https://discord.gg/v6v4pMv)의 `#player-support` 채널에서 도움을 요청할 수 있습니다.
