---
title: 물약
description: 여러 상태 효과를 부여하는 사용자 정의 물약을 만드는 방법을 알아보세요.
authors:
  - dicedpixels
  - Drakonkinst
  - JaaiDead
  - PandoricaVi
---

물약은 엔티티에게 효과를 주는 소모품입니다. 플레이어는 양조대에서 물약을 제조하거나 여러 게임 메커니즘을 통해 아이템을 얻을 수 있습니다.

## 사용자 정의 물약 {#custom-potions}

아이템과 블록들처럼, 물약도 등록이 필요합니다.

### 물약 만들기 {#creating-the-potion}

`Potion` 인스턴스를 저장할 필드를 만들며 시작해 봅시다. 이를 보관하기 위해 `ModInitializer` 구현 클래스를 직접 사용하겠습니다.

@[code lang=java transclude={18-27}](@/reference/latest/src/main/java/com/example/docs/potion/ExampleModPotions.java)

`StatusEffectInstance` 인스턴스에는 세 가지 매개 변수를 입력해야 합니다.

- `RegistryEntry<StatusEffect> type` - 효과. 여기에선 사용자 정의 효과를 사용해볼 것입니다. 대신, 바닐라의 `StatusEffects` 클래스를 통해 바닐라 효과에 접근할 수도 있습니다.
- `int duration` - 효과의 지속 시간(틱).
- `int amplifier` - 효과의 세기. 예를 들어, 성급함 II는 1을 세기로 가지게 됩니다.

:::info
사용자 정의 물약 효과를 만드려면, [상태 효과](../entities/effects) 가이드를 참조하세요.
:::

### 물약 등록하기 {#registering-the-potion}

이니셜라이저에서 `BrewingRecipeRegistry.registerPotionRecipe` 메서드를 사용해 물약 효과를 등록하기 위해 `FabricBrewingRecipeRegistryBuilder.BUILD` 이벤트를 사용할 것입니다.

@[code lang=java transclude={29-42}](@/reference/latest/src/main/java/com/example/docs/potion/ExampleModPotions.java)

`registerPotionRecipe`는 세 가지 매개변수를 가집니다.

- `RegistryEntry<Potion> input` - 시작 물약의 레지스트리 항목. 일반적으로 물병 또는 어색한 물약이 사용됩니다.
- `Item item` - 물약의 기본 재료가 될 아이템.
- `RegistryEntry<Potion> output` - 결과 물약의 레지스트리 항목.

등록을 완료했다면, 이제 감자를 통해 Tater 물약을 만들 수 있습니다.

![플레이어 인벤토리에서 보여지는 효과](/assets/develop/tater-potion.png)
