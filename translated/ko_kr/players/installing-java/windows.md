---
title: Windows에 Java 설치하기
description: Windows에 Java를 설치하는 방법에 대한 단계별 가이드입니다.
authors:
  - IMB11
---

이 가이드는 Windows에 Java 21을 설치하는 과정을 안내해 줄 것입니다.

Minecraft 런처에서 자체적인 Java 설치를 제공하기 때문에, 이 단락은 `.jar` 기반의 Fabric 설치기를 사용하고자 하거나, Minecraft 서버 `.jar`를 사용하고자 할 때에만 유용합니다.

## 1. 이미 Java가 설치되었는지 확인하기 {#1-check-if-java-is-already-installed}

Java가 설치되어 있는지 확인하려면, 먼저 명령 프롬프트를 시작해야 합니다.

<kbd>Win 로고 키</kbd>와 <kbd>R 키</kbd>를 동시에 누른 다음, 왼쪽 아래에 표시된 실행 창에 `cmd`를 입력하여 명령 프롬프트를 시작할 수 있습니다.

![입력란에 "cmd.exe"가 입력된 Windows 실행 다이얼로그](/assets/players/installing-java/windows-run-dialog.png)

명령 프롬프트를 시작하는 데 성공했다면, `java -version`을 입력하고 <kbd>엔터 키</kbd>를 누릅니다.

명령어가 성공적으로 실행되었다면, 아래와 같이 표시될 것입니다. 명령어가 실행되지 않았다면, 다음 단계로 바로 이동하십시오.

!["java -version"이 입력된 명령 프롬프트](/assets/players/installing-java/windows-java-version.png)

:::warning
Minecraft: Java Edition 1.21을 플레이 하려면, 적어도 Java 21 이상의 Java가 설치되어야 합니다. 만약 이 명령어가 Java 21보다 낮은 버전을 표시한다면, 기존의 Java 설치를 업데이트해야 합니다.
:::

## 2. Java 21 설치기 다운로드 {#2-download-the-java-installer}

Java 21을 설치하려면, 먼저 [Adoptium](https://adoptium.net/en-GB/temurin/releases/?os=windows\&package=jdk\&version=21)에서 설치기를 다운로드해야 합니다.

아래와 같이 `Windows Installer (.msi)`를 선택하여 주세요:

!["Windows Installer (.msi)" 선택이 강조된 Adoptium 다운로드 페이지](/assets/players/installing-java/windows-download-java.png)

32비트 Windows를 사용하고 계시다면 `x86`에서 선택하고, 64비트 Windows를 사용하고 계시다면 `x64`에서 선택하여 주세요.

대부분의 최신 PC(Windows 10/11)는 64비트 운영 체제를 사용합니다. 잘 모르겠다면, 64비트를 다운로드해 보세요.

## 3. 설치기 실행하기 {#3-run-the-installer}

Java 21을 설치하려면 다음 단계를 수행하세요. 설치기에서 아래와 같은 단계에 도달했다면, 아래 기능을 "Entire feature will be installed on local hard drive"로 설정해야 합니다:

- `Set JAVA_HOME environment variable` - 선택하면 Java 실행 파일이 PATH에 추가됩니다.
- `JavaSoft (Oracle) registry keys` - 선택하면 지금 설치되는 Java를 기본 실행으로 설정합니다.

!["Set JAVA\_HOME variable" 기능이 강조 표시된 Java 21 설치기](/assets/players/installing-java/windows-wizard-screenshot.png)

기능을 모두 선택했다면, `Next`를 클릭하여 설치를 재개할 수 있습니다.

## 4. Java 21이 올바르게 설치되었는지 확인하기 {#4-verify-that-java-is-installed}

설치를 완료했다면, "다시" 명령 프롬프트를 열고 `java -version`을 입력하여 Java 21이 올바르게 설치되었는지 확인할 수 있습니다.

명령어가 성공적으로 실행되었다면, 아래와 같이 Java 버전이 표시된 것을 확인할 수 있을 것입니다:

!["java -version"이 입력된 명령 프롬프트](/assets/players/installing-java/windows-java-version.png)

문제가 발생했다면, 자유롭게 [Fabric Discord (영어)](https://discord.gg/v6v4pMv)의 `#player-support` 채널에서 도움을 요청할 수 있습니다.
