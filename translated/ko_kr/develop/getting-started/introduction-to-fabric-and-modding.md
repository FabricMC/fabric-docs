---
title: Fabric과 모딩 소개
description: "Fabric과 Minecraft: Java Edition의 모딩에 대한 간략한 설명입니다."
authors:
  - IMB11
  - itsmiir
authors-nogithub:
  - basil4088
---

## 먼저 알아야 하는 것 {#prerequisites}

Fabric으로 모딩을 시작하려면, 먼저 Java를 이용한 개발에 대한 간단한 이해와 객체 지향 프로그래밍(OOP)에 대한 이해가 필요합니다.

이 개념들에 대해 익숙하지 않다면, Java와 OOP에 대한 튜토리얼을 읽어 보는 것이 좋습니다. 여기 몇 가지 Java와 OOP에 대해 학습할 수 있는 자료가 있습니다:

- [W3: Java Tutorials](https://www.w3schools.com/java/)
- [Codecademy: Learn Java](https://www.codecademy.com/learn/learn-java)
- [W3: Java OOP](https://www.w3schools.com/java/java_oop.asp)
- [Medium: Introduction to OOP](https://medium.com/@Adekola_Olawale/beginners-guide-to-object-oriented-programming-a94601ea2fbd)

### 용어 {#terminology}

시작하기 전에, Fabric으로 모딩하여 만나게 될 몇 가지 용어에 대해 알아봅시다:

- **모드 (Mod)**: 게임에 대한 '수정 사항(Modifications)'. 새로운 기능을 추가하거나 기존 기능을 수정하는 모든 행위를 일컫습니다.
- **모드 로더(Mod Loader):** Fabric 로더처럼 게임에 모드를 적용하는 도구를 의미합니다.
- **Mixin**: 게임의 코드를 런타임에서 수정하는 도구를 의미합니다. [Mixin 소개](https://fabricmc.net/wiki/tutorial:mixin_introduction)에서 자세한 내용을 확인할 수 있습니다.
- **Gradle**: Fabric에서 모드를 컴파일하고 빌드하기 위해 사용하는 빌드 도구입니다.
- **매핑 (Mappings)**: 난독화된(obfuscated) 코드와 해석된 코드가 서로 연결되어 있는 맵의 집합입니다.
- **난독화 (Obfuscation)**: 코드를 읽기 어렵게 만드는 과정으로, Mojang이 Minecraft의 코드를 보호하기 위해 사용하고 있습니다.
- **재매핑 (Remapping)**: 난독화된 코드를 해석하는 과정을 의미합니다.

## Fabric이란? {#what-is-fabric}

Fabric은 Minecraft: Java Edition용 가벼운 모딩 툴체인입니다.

간단하고 사용하기 쉽게 설계되었습니다. Fabric은 커뮤니티 주도 오픈소스 프로젝트로, 누구나 프로젝트에 기여할 수 있습니다.

Fabric은 크게 네 가지의 주요 구성 요소로 이루어집니다:

- **Fabric 로더**: Minecraft 및 기타 게임, 응용 프로그램을 위해 설계된 유연한 플랫폼 독립 모드 로더입니다.
- **Fabric Loom**: 개발자가 쉽게 모드를 개발하고 디버깅할 수 있도록 도와주는 Gradle 플러그인입니다.
- **Fabric API**: 모드를 개발하기 위해 사용되는 API와 도구 모음입니다.
- **Yarn**: 오픈 소스 Minecraft 매핑으로, 크리에이티브 커먼즈 제로 라이선스 아래 누구나 사용할 수 있습니다.

## Fabric이 필요한 이유 {#why-is-fabric-necessary-to-mod-minecraft} {#why-is-fabric-necessary-to-mod-minecraft}

> '모딩'은 게임의 작동 방식을 수정하고 새로운 기능을 추가하는 과정입니다. Minecraft에서는 아이템, 블록, 엔티티를 추가하거나 게임 메커니즘을 수정하고 새로운 게임 모드를 추가하는 등의 행위를 말합니다.

Mojang은 Minecraft: Java Edition의 코드를 난독화하고 있어, 단순히 수정하기는 어렵습니다. 하지만, Fabric과 같은 모딩 도구만 있다면 모딩이 훨씬 더 쉬워집니다. 모딩을 도울 수 있는 여러 매핑 도구가 포함되어 있기 때문이죠.

Loom은 난독화된 코드를 해석하여, 모더가 코드를 더 쉽게 이해하고 수정할 수 있도록 합니다. Yarn이 이 과정에 가장 최적화된 매핑이라고 할 수 있지만, 다른 매핑 프로젝트도 있습니다. 매핑 프로젝트가 가지는 장점과 추구하는 목표가 다르기 때문입니다.

Loom은 리맵된 코드로 모드를 쉽게 개발하고 컴파일할 수 있게 하며, Fabric 로더가 이를 게임에 적용합니다.

## Fabric API가 제공하는 것과, 필요한 이유 {#what-does-fabric-api-provide-and-why-is-it-needed} {#what-does-fabric-api-provide-and-why-is-it-needed}

> Fabric API는 모더가 모드를 개발할 때 사용하는 API와 도구의 모음입니다.

Fabric API는 Minecraft 기존 기능 위에서 만들어진 광범위한 API 세트를 제공합니다. 모드가 사용할 수 있는 새로운 후크와 이벤트, 그리고 접근 제한자를 해제할 수 있게 돕는 도구와 내부 레지스트리에 접근할 수 있게 돕는 도구 등 여러 도구와 유틸리티도 제공하고 있습니다.

Fabric API에서 여러 강력한 기능을 제공하고 있지만, 기본적인 블록의 추가처럼 간단한 작업은 바닐라 API를 사용해도 충분합니다.
