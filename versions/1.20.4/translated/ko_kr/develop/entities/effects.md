---
title: 상태 효과
description: 사용자 정의 상태 효과를 만드는 방법을 알아보세요.
authors:
  - dicedpixels
  - YanisBft
  - FireBlast
  - Friendly-Banana
  - SattesKrokodil
authors-nogithub:
  - siglong
  - tao0lu

search: false
---

# 상태 효과

이펙트, 효과로도 알려진 상태 효과는 엔티티에게 영향을 줄 수 있는 조건을 의미합니다. 이는 자연적으로 좋거나, 나쁘거나, 중립적일 수 있습니다. 기본 게임은 이러한 효과를 음식, 물약 등 다양한 방법으로 적용합니다.

`/effect` 명령어를 통해 엔티티에게 효과를 부여할 수도 있습니다.

## 사용자 정의 상태 효과

이 튜토리얼에서는, 매 틱마다 플레이어에게 경험 포인트를 주는 새로운 사용자 정의 효과 _Tater_ 를 만들어 보겠습니다.

### `StatusEffect` 확장

모든 효과의 기본이 되는 `StatusEffect` 클래스의 사용자 정의 확장 클래스를 만들어 봅시다.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/effect/TaterEffect.java)

### 사용자 정의 효과 등록하기

블록, 아이템 등록처럼, `Registry.register`를 통해 `STATUS_EFFECT` 레지스트리에 사용자 정의 효과를 등록할 수 있습니다. 이는 모드 초기화 단계에서 완료되어야 합니다.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/effect/FabricDocsReferenceEffects.java)

### 현지화와 텍스쳐

사용자 정의 상태 효과에 플레이어의 인벤토리 화면에 보여질 텍스쳐 아이콘과 이름을 지정할 수 있습니다.

#### 텍스쳐

상태 효과의 아이콘은 18x18의 PNG 입니다. 사용자 정의 아이콘을 다음 폴더에 넣어 적용할 수 있습니다:

```:no-line-numbers
resources/assets/fabric-docs-reference/textures/mob_effect/tater.png
```

![플레이어 인벤토리에서 보여지는 효과](/assets/develop/tater-effect.png)

#### 현지화

다른 현지화처럼, 간단히 언어 파일에 `"effect.<mod-id>.<effect-identifier>": "값"` 포맷의 엔트리 ID를 추가하기만 하면 됩니다.

::: code-group

```json[assets/fabric-docs-reference/lang/en_us.json]
{
  "effect.fabric-docs-reference.tater": "Tater"
}
```

### 테스트

`/effect give @s fabric-docs-reference:tater` 명령어를 사용해 직접 Tater 효과를 부여해 보세요. `/effect clear`로 효과를 제거할 수 있습니다.

::: info
사용자 정의 효과를 부여하는 물약을 만드는 방법은 [물약](../items/potions) 가이드를 참조하세요.
:::
