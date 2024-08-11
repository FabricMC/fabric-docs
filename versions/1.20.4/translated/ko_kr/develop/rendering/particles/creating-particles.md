---
title: 사용자 정의 입자 만들기
description: Fabric API를 통해 사용자 정의 입자를 만드는 방법을 알아보세요.
authors:
  - Superkat32

search: false
---

# 사용자 정의 입자 만들기

입자는 강력한 도구입니다. 아름다운 장면에 분위기를 더할 수도 있고, 보스와의 전투에 긴장감을 더할 수도 있습니다. 그럼 직접 한번 만들어 봅시다!

## 사용자 정의 입자 등록하기

이 튜토리얼에서는 엔드 막대기 입자처럼 움직이는 새로운 스파클 입자를 추가해 볼 예정입니다.

먼저, 모드 초기화 클래스에 모드 ID로 `ParticleType` 을 등록해 봅시다.

@[code lang=java transcludeWith=#particle_register_main](@/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java)

소문자로 "sparkle_particle"은 이후 입자의 텍스쳐를 위한 JSON 파일의 이름이 되게 됩니다. 곧 JSON 파일을 어떻게 생성하는지 알아볼 것입니다.

## 클라이언트측에서 등록하기

`ModInitializer` 엔트리포인트에 입자를 등록했다면, `ClientModInitializer`의 엔트리포인트에도 입자를 등록해야 합니다.

@[code lang=java transcludeWith=#particle_register_client](@/reference/latest/src/client/java/com/example/docs/FabricDocsReferenceClient.java)

위 예시는 입자를 클라이언트측에 등록하는 방법입니다. 이제 엔드 막대기 입자 팩토리를 통해 입자에 움직임을 줘보겠습니다. 이렇게 하면 입자가 엔드 막대기 입자와 똑같이 움직이게 됩니다.

::: tip
You can see all the particle factories by looking at all the implementations of the `ParticleFactory` interface. This is helpful if you want to use another particle's behaviour for your own particle.

- IntelliJ 단축 키: Ctrl+Alt+B
- VSCode 단축 키: Ctrl+F12
:::

## JSON 파일을 만들고 텍스쳐 추가하기

먼저, 모드 소스의 `resources/assets/<mod id here>/` 폴더에 두 가지 새로운 폴더를 생성해야 합니다.

| 폴더 경로                | 설명                                                              |
| -------------------- | --------------------------------------------------------------- |
| `/textures/particle` | `/textures/particle` 폴더는 입자의 모든 텍스쳐를 담습니다.      |
| `/particles`         | `particles` 폴더는 입자의 정보에 대한 모든 JSON 파일을 담고 있습니다. |

예를 들어, `textures/particle` 폴더에는 "sparkle_particle_texture.png" 라는 이름의 텍스쳐 이미지를 넣겠습니다.

그리고, `particles` 폴더에는 ParticleType에 등록한 것과 같은 이름의 소문자를 이름으로 가지는 JSON 파일을 생성합니다. 이 예제에서는, `sparkle_particle.json` 으로 생성하겠습니다. 이 파일은 Minecraft가 입자에 어떠한 텍스쳐를 사용해야 하는지 정의하기 때문에 매우 중요합니다.

@[code lang=json](@/reference/latest/src/main/resources/assets/fabric-docs-reference/particles/sparkle_particle.json)

:::tip
입자에 애니메이션을 적용하고 싶다면 `textures` 배열 노드에 더 많은 텍스쳐를 추가하면 됩니다. 입자는 배열의 순서대로 반복될 것입니다.
:::

## 새 입자 테스트하기

위의 모든 작업을 완료했다면, 이제 Minecraft에서 입자를 테스트해볼 차례입니다.

다음의 명령어를 입력하여 입자가 정상적으로 작동하는지 확인할 수 있습니다.

```mcfunction
/particle <mod id here>:sparkle_particle ~ ~1 ~
```

![입자 쇼케이스](/assets/develop/rendering/particles/sparkle-particle-showcase.png)

:::info
이 명령어를 사용하면 입자가 플레이어 안에 생성될 것입니다. 입자를 보려면 뒤로 몇 걸음 걸어야할 수도 있습니다.
:::

대신, 같은 명령어로 명령 블록을 통해 입자를 생성할 수도 있습니다.
