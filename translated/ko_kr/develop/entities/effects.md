---
title: 상태 효과
description: 사용자 정의 상태 효과를 만드는 방법을 알아보세요.
authors:
  - dicedpixels
  - Friendly-Banana
  - Manchick0
  - SattesKrokodil
  - FireBlast
  - YanisBft
authors-nogithub:
  - siglong
  - tao0lu
---

이펙트, 효과로도 알려진 상태 효과는 엔티티에게 영향을 줄 수 있는 조건을 의미합니다. 이는 자연적으로 좋거나, 나쁘거나, 중립적일 수 있습니다. 기본 게임은 이러한 효과를 음식, 물약 등 다양한 방법으로 적용합니다.

`/effect` 명령어를 통해 엔티티에게 효과를 부여할 수도 있습니다.

## 사용자 정의 상태 효과 {#custom-status-effects}

이 튜토리얼에서는, 매 틱마다 플레이어에게 경험 포인트를 주는 새로운 사용자 정의 효과 _Tater_ 를 만들어 보겠습니다.

### `StatusEffect` 확장 {#extend-statuseffect}

모든 효과의 기본이 되는 `StatusEffect` 클래스의 사용자 정의 확장 클래스를 만들어 봅시다.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/effect/TaterEffect.java)

### 사용자 정의 효과 등록하기 {#registering-your-custom-effect}

블록, 아이템 등록처럼, `Registry.register`를 통해 `STATUS_EFFECT` 레지스트리에 사용자 정의 효과를 등록할 수 있습니다. 이는 모드 초기화 단계에서 완료되어야 합니다.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/effect/ExampleModEffects.java)

### 텍스쳐 {#texture}

상태 효과의 아이콘은 18x18의 PNG 입니다. 사용자 정의 아이콘을 다음 폴더에 넣어 적용할 수 있습니다:

```:no-line-numbers
resources/assets/example-mod/textures/mob_effect/tater.png
```

<DownloadEntry visualURL="/assets/develop/tater-effect.png" downloadURL="/assets/develop/tater-effect-icon.png">예시 텍스처</DownloadEntry>

### 번역 {#translations}

다른 현지화처럼, 간단히 언어 파일에 `"effect.<mod-id>.<effect-identifier>": "값"` 포맷의 엔트리 ID를 추가하기만 하면 됩니다.

```json
{
  "effect.example-mod.tater": "Tater"
}
```

### 테스트 {#testing}

일반적으로 엔티티에 상태 효과를 적용하는 방법을 살펴보는 것이 좋습니다.

::: tip
For a quick test, it might be a better idea to use the previously mentioned `/effect` command:

```mcfunction
effect give @p example-mod:tater
```

:::

효과를 내부적으로 적용하려면, `StatusEffectInstance`를 효과가 제대로 적용되었는지 여부를 지정하는 부울 값을 반환하는 `LivingEntity#addStatusEffect` 메서드를 사용하는 것이 좋습니다.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/ReferenceMethods.java)

| 매개변수        | 유형                            | 설명                                                                                                                                                                                                             |
| ----------- | ----------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `effect`    | `RegistryEntry<StatusEffect>` | 효과를 나타내는 레지스트리 항목입니다.                                                                                                                                                                          |
| `duration`  | `int`                         | 초 단위가 **아닌** **틱 단위** 효과 길이입니다                                                                                                                                                                                 |
| `amplifier` | `int`                         | 효과의 세기를 선택합니다. 효과의 **세기**에 대응하지 않고, 오히려 위에 추가됩니다. 그러므로, `4`의 `amplifier` => `5`의 세기                                                                                            |
| `ambient`   | `boolean`                     | 꽤 까다로운 문제입니다. 기본적으로 효과가 환경 (예: **신호기**)에 의해 추가되었음을 지정하며 직접적인 원인이 없습니다. 만일 `true`라면, HUD에 있는 효과의 아이콘이 청록색 오버레이와 함께 나타날 것입니다. |
| `particles` | `boolean`                     | 입자 표시 여부를 선택합니다.                                                                                                                                                                               |
| `icon`      | `boolean`                     | HUD에 효과 아이콘 표시 여부를 선택합니다. 이 플래그와 관계 없이 효과는 보관함에 표시될 것입니다.                                                                                                                      |

:::info
::: info
사용자 정의 효과를 부여하는 물약을 만드는 방법은 [물약](../items/potions) 가이드를 참조하세요.
:::
