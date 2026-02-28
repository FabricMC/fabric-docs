---
title: 리눅스에 Fabric 설치
description: 리눅스에 Fabric을 설치하기 위한 단계별 가이드
authors:
  - Benonardo
  - ezfe
  - IMB11
  - modmuss50
next: false
---

<!---->

:::info 전제 조건

jar 파일을 실행하려면 [Java가 설치](../installing-java/linux)되어 있어야 합니다.

:::

<!-- #region common -->

## 1. Fabric 설치기 다운로드 {#1-download-the-fabric-installer}

Fabric 설치기를 [Fabric 웹사이트](https://fabricmc.net/use/)에 접속한 후, `설치기 다운로드 (Universal/.JAR)` 버튼을 눌러 다운로드하세요. 다운로드하려는 파일 형식은 `.jar` 입니다.

## 2. Fabric 설치기 시작 {#2-run-the-fabric-installer}

설치기를 시작하기 전에, 먼저 모든 Minecraft 인스턴스와 Minecrft 런처를 종료해야 합니다.

터미널을 열고 아래 명령어를 실행하세요:

```sh
java -jar fabric-installer.jar
```

설치기를 실행하면, 아래 사진과 같이 실행됩니다:

!["Install"을 강조 표시한 Fabric 설치기](/assets/players/installing-fabric/installer-screen.png)

<!-- #endregion common -->

원하는 마인크래프트 버전을 선택하고 설치합니다. `프로필 생성`이 켜져 있는지 확인하세요.

## 3. 마무리 {#3-finish-setup}

설치가 끝나면, 마인크래프트 런처를 실행하세요. 그리고 버전 선택 목록에서 Fabric 프로필을 선택하고 플레이 버튼을 누르새요.

![Fabric 프로필이 선택된 Minecraft 런처](/assets/players/installing-fabric/launcher-screen.png)

이제 게임에 모드를 적용할 수 있습니다. [안전한 모드 탐색](../finding-mods)에서 자세한 정보를 확인해보세요.

문제가 발생했다면, 자유롭게 [Fabric 디스코드(영어)](https://discord.gg/v6v4pMv)의 `#player-support` 채널에서 도움을 요청할 수 있습니다.
