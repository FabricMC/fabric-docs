---
title: 사용자 정의 파티클 만들기
description: Fabric API를 통해 사용자 정의 입자를 만드는 방법을 알아보세요.
authors:
  - Superkat32
---

# 사용자 정의 파티클 만들기

입자는 강력한 도구입니다. 아름다운 장면에 분위기를 더할 수도 있고, 보스와의 전투에 긴장감을 더할 수도 있습니다. 그럼 직접 한번 만들어 봅시다!

## 사용자 정의 입자 등록하기

이제 엔드 막대기 입자 팩토리를 통해 입자에 움직임을 줘보겠습니다.

사용자 정의 입자를 추가하려면, 먼저 모드 초기화 클래스에서 모드 ID로 `ParticleType`을 등록해야 합니다.

@[code lang=java transcludeWith=#particle_register_main](@/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java)

"my_particle"의 소문자는 입자 텍스쳐의 JSON 파일 이름이 됩니다. 곧 JSON 파일을 어떻게 생성하는지 알아볼 것입니다.

## 클라이언트측에서 등록하기

`ModInitializer` 엔트리포인트에 입자를 등록했다면, `ClientModInitializer`의 엔트리포인트에도 입자를 등록해야 합니다.

@[code lang=java transcludeWith=#particle_register_client](@/reference/latest/src/client/java/com/example/docs/FabricDocsReferenceClient.java)

위 예시는 입자를 클라이언트측에 등록하는 방법입니다. 이제 생성한 `particles` 폴더에 사용할 입자의 텍스쳐 PNG를 넣으십시오. 입자가 플레이어 안에 생성되므로, 입자를 보려면 뒤로 이동해야 할 수 있습니다.

::: tip
You can see all the particle factories by looking at all the implementations of the `ParticleFactory` interface. This is helpful if you want to use another particle's behaviour for your own particle.

IntelliJ 단축 키: Ctrl+Alt+B\
VSCode 단축 키: Ctrl+F12
:::

## JSON 파일을 만들고 텍스쳐 추가하기

모드의 resources 폴더에 `assets/<mod id here>/textures/particle` 폴더를 생성합니다.

| 폴더 경로                | 설명                                              |
| -------------------- | ----------------------------------------------- |
| `/textures/particle` | 입자 텍스쳐에 필요한 폴더를 만드는 것부터 시작해 봅시다.                |
| `/particles`         | `particles` 폴더는 입자의 정보에 대한 모든 JSON 파일을 담고 있습니다. |

이 튜토리얼에서는 "myparticletexture.png" 이름의 텍스쳐 1개만 사용하겠습니다.

그리고, `particles` 폴더에는 ParticleType에 등록한 것과 같은 이름의 소문자를 이름으로 가지는 JSON 파일을 생성합니다. 이 예제에서는, `sparkle_particle.json` 으로 생성하겠습니다. 이 파일은 Minecraft가 입자를 위해 어떤 텍스쳐를 사용해야 할지 알려줄 것입니다.

@[code lang=json](@/reference/latest/src/main/resources/assets/fabric-docs-reference/particles/my_particle.json)

:::tip
입자에 애니메이션을 적용하고 싶다면 `textures` 배열 노드에 더 많은 텍스쳐를 추가하면 됩니다. 입자는 배열의 순서대로 반복될 것입니다.
:::

## 새 입자 테스트하기

JSON 파일까지 작업을 완료했다면, 이제 Minecraft를 시작해 입자를 실험해볼 수 있습니다.

다음의 명령어를 입력하여 입자가 정상적으로 작동하는지 확인할 수 있습니다.

```
/particle <mod id here>:my_particle ~ ~1 ~
```

![입자 쇼케이스!](/assets/develop/rendering/particles/sparkle-particle-showcase.png)

:::info
이 명령어를 사용하면 입자가 플레이어 안에 생성될 것입니다. 입자를 보려면 뒤로 몇 걸음 걸어야할 수도 있습니다.
:::

위 명령어를 사용해 명령 블록으로 입자를 생성할 수도 있습니다.
