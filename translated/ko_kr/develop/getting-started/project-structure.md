---
title: 프로젝트의 구조
description: Fabric 모드 프로젝트의 구조에 대한 개요입니다.
authors:
  - IMB11
---

이 페이지에서는 Fabric 모드 프로젝트의 구조와 프로젝트의 각 파일과 폴더의 목적에 대해 알아봅니다.

## `fabric.mod.json` {#fabric-mod-json}

`fabric.mod.json` 파일은 Fabric 로더에 모드를 설명하는 주요 파일입니다. 여기에는 모드의 식별자(ID), 버전, 종속성 등의 정보가 담깁니다.

여기 `fabric.mod.json` 파일에서 가장 중요한 필드 몇 가지가 있습니다:

- `depends`: 모드가 종속하는 다른 모드의 식별자.
- `name`: 모드의 이름.
- `environment`: 모드가 동작할 환경. `client` 또는 `server`, 어디서나 동작한다면 `*`을 입력할 수 있습니다.
- `entrypoints`: 모드에서 제공하는 진입점.
- `depends`: 모드가 종속하는 다른 모드의 식별자.
- `mixins`: 모드가 제공하는 Mixin.

아래는 `fabric.mod.json`의 예시입니다. 앞으로 모든 설명서는 아래 `fabric.mod.json`으로 정의된 모드를 예시로 사용할 것입니다.

:::details 리퍼런스 프로젝트의 `fabric.mod.json`
@[code lang=json](@/reference/latest/src/main/resources/fabric.mod.json)
:::

## 진입점 {#entrypoints}

위에서 언급했듯이, `fabric.mod.json` 파일에는 `entrypoint`라는 필드가 있습니다. 이 필드는 모드가 제공하는 진입점을 특정할 때 사용됩니다.

템플릿 모드 생성기는 `main`과 `client` 두 가지 진입점을 기본으로 생성하며, 각 진입점은 다음과 같은 역할을 합니다:

- `main` 진입점은 게임의 메커니즘처럼 일반적인 코드에 사용되며, `ModInitializer`를 구현하는 클래스가 입력됩니다.
- `client` 진입점은 GUI처럼 클라이언트 전용 코드에 사용되며, `ClientModInitializer`를 구현하는 클래스가 입력됩니다.

이러한 진입점은 게임이 시작될 때 각각 실행됩니다.

아래는 게임이 시작되었을 때 메시지를 로그하는 간단한 `main` 진입점 구현체입니다.

@[code lang=java transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/ExampleMod.java)

## `src/main/resources` {#src-main-resources}

`src/main/resources` 폴더는 텍스쳐, 모델, 소리처럼 모드에서 사용하는 리소스가 저장됩니다.

`fabric.mod.json` 파일과 모드에서 사용할 Mixin 구성 파일도 여기에 저장됩니다.

어셋 파일은 리소스 팩 파일의 구조 그대로 저장됩니다. 예를 들어, `mod_block` 블록의 텍스처는 `assets/mod_id/textures/block/mod_block.png`에 저장될 수 있습니다.

## `src/client/resources` {#src-client-resources}

`src/client/resources` 폴더에는 UI 텍스처처럼 클라이언트에서만 사용되는 리소스가 저장됩니다.

## `src/main/java` {#src-main-java}

`src/main/java` 폴더에는 클라이언트, 서버 모두에서 작동하는 Java 소스 코드가 저장됩니다.

## `src/client/java` {#src-client-java}

`src/client/java` 폴더에는 렌더링, 블록 색상처럼 클라이언트에서만 작동하는 Java 소스 코드가 저장됩니다.
