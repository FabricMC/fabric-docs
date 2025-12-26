---
title: 개발 환경 설정하기
description: Fabric을 이용하여 모드를 만들기 위해 개발 환경을 설정하는 방법에 대한 단계적 설명서입니다.
authors:
  - 2xsaiko
  - andrew6rant
  - asiekierka
  - daomephsta
  - falseresync
  - IMB11
  - liach
  - mkpoli
  - modmuss50
  - natanfudge
  - SolidBlock-cn
  - telepathicgrunt
authors-nogithub:
  - siglong
---

Fabric으로 모드를 개발하려면, 먼저 개발 환경을 설정해야 합니다. 이 설명서는 JetBrains의 IntelliJ IDEA를 이용한 방법을 설명합니다.

## JDK 21 설치 {#installing-jdk-21}

Minecraft 1.21.4의 모드를 개발하려면, JDK(Java Development Kit, Java 개발 키트) 21이상이 필요합니다.

Java 설치에 대한 자세한 방법은 [플레이어 설명서](../../players/index)를 참조하십시오.

## IntelliJ IDEA 설치 {#installing-intellij-idea}

:::info
Eclipse나 Visual Studio Code처럼 다른 IDE를 사용할 수도 있지만, Fabric 문서의 모든 설명서는 IntelliJ IDEA를 예시로 사용하고 있습니다. 다른 IDE를 사용하는 경우 해당 IDE의 문서를 참조해야 합니다.
:::

IntelliJ IDEA가 아직 설치되어 있지 않다면, [공식 웹사이트](https://www.jetbrains.com/idea/download/)에서 다운로드할 수 있습니다. 웹사이트에서 제공하는 운영 체제별 설치 과정을 따라주시기 바랍니다.

IntelliJ IDEA Community Edition은 무료 오픈소스로, Fabric 모딩에 권장하는 IDE입니다.

Community Edition을 찾으려면 페이지를 조금 내려야 할 수 있으며, 아래와 같이 표시됩니다.

![IntelliJ IDEA Community Edition 다운로드 프롬프트](/assets/develop/getting-started/idea-community.png)

## IDEA 플러그인 다운로드 {#installing-idea-plugins}

이 플러그인은 필수적이지는 않지만, Fabric 모딩을 훨씬 더 쉽게 만들기 때문에 설치를 고려하는 것이 좋습니다.

### Minecraft Development {#minecraft-development}

Minecraft Development 플러그인은 Fabric 모딩 지원을 제공하는 플러그인으로, 설치해야 할 가장 중요한 플러그인입니다.

IntelliJ IDEA를 열고, 네비게이션 바에서 `File > Settings > Plugins > Marketplace 탭`으로 이동하여 `Minecraft Development`를 검색한 후, `Install` 버튼을 클릭하여 설치할 수 있습니다.

또는, [플러그인 페이지](https://plugins.jetbrains.com/plugin/8327-minecraft-development)에서 파일을 직접 다운로드하여 `File > Settings > Plugins > Install Plugin From Disk`로 이동하여 설치할 수도 있습니다.
