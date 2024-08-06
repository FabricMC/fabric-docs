---
title: 물약
description: 여러 상태 효과를 부여하는 사용자 정의 물약을 만드는 방법을 알아보세요.
authors:
  - dicedpixels
  - PandoricaVi
  - Drakonkinst

search: false
---

# 물약

물약은 엔티티에게 효과를 주는 소모품입니다. 플레이어는 양조대에서 물약을 제조하거나 여러 게임 메커니즘을 통해 아이템을 얻을 수 있습니다.

## 사용자 정의 물약

물약을 추가하는 방법은 아이템을 추가하는 방법과 비슷합니다. 물약의 인스턴스를 만들고 `BrewingRecipeRegistry.registerPotionRecipe` 를 호출해 등록해 보겠습니다.

:::info
Fabric API를 사용중이라면, 액세스 위더너를 통해 `BrewingRecipeRegistry.registerPotionRecipe`에 접근할 수 있습니다.
:::

### 포션 만들기

`Potion` 인스턴스를 저장할 필드를 만들며 시작해 봅시다. 클래스의 생성자를 사용해 바로 저장할 것입니다.

@[code lang=java transclude={18-27}](@/reference/latest/src/main/java/com/example/docs/potion/FabricDocsReferencePotions.java)

`StatusEffectInstance` 인스턴스에는 세 가지 매개 변수를 입력해야 합니다.

- `StatusEffect type` - 물약이 부여할 효과. 여기에선 사용자 정의 효과를 사용해볼 것입니다. `net.minecraft.entity.effect.StatusEffects`를 통해 바닐라 효과에 접근할 수도 있습니다.
- `int duration` - 효과의 지속 시간(틱).
- `int amplifier` - 효과의 세기. 예를 들어, 성급함 II는 1을 세기로 가지게 됩니다.

:::info
사용자 정의 효과를 만드려면, [상태 효과](../entities/effects) 가이드를 참조하세요.
:::

### 포션 등록

초기화 단계에서, `BrewingRecipeRegistry.registerPotionRecipe`를 호출해 봅시다.

@[code lang=java transclude={30-30}](@/reference/latest/src/main/java/com/example/docs/potion/FabricDocsReferencePotions.java)

`registerPotionRecipe`는 세 가지 매개변수를 가집니다.

- `Potion input` - 재료가 될 물약. 일반적으로 물병 또는 어색한 물약이 사용됩니다.
- `Item item` - 물약의 기본 재료가 될 아이템.
- `Potion output` - 결과가 될 물약.

Fabric API를 사용 중이라면, `BrewingRecipeRegistry.registerPotionRecipe`를 호출해 바로 등록할 수 있기에 Mixin Invoker를 쓸 필요는 없습니다.

전체 예시는 다음과 같습니다.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/potion/FabricDocsReferencePotions.java)

등록을 완료했다면, 이제 감자를 통해 Tater 물약을 만들 수 있습니다.

![플레이어 인벤토리에서 보여지는 효과](/assets/develop/tater-potion.png)

::: info
**Registering Potions Using an `Ingredient`**

Fabric API를 통해 `net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistry`의 `Item`을 사용하는 대신 `Ingredient`를 사용해 물약을 등록할 수 있습니다.
:::

### Fabric API 없이 물약 등록

Fabric API가 없으면, `BrewingRecipeRegistry.registerPotionRecipe`는 Private 접근 제한자를 가집니다. 이렇게 되면, Mixin Invoker 또는 Access Widener를 통해 이 메소드에 접근해야 합니다.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/mixin/potion/BrewingRecipeRegistryInvoker.java)
