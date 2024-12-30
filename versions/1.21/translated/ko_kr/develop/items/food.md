---
title: 음식 아이템
description: 아이템을 먹을 수 있고, 설정할 수 있게 하기 위해 아이템에 FoodComponent를 추가하는 방법을 알아보세요.
authors:
  - IMB11

search: false
---

# 음식 아이템 {#food-items}

음식은 Minecraft에서 생존하는 데 주요한 면입니다. 그래서 먹을 수 있는 아이템을 만들 때, 다른 먹을 수 있는 아이템과의 용도를 고려해야 합니다.

만약, 모드를 사용해 사기적인 아이템을 만들 것이 아니라면, 다음을 고려해야 합니다:

- 먹을 아이템이 얼마나 많은 배고픔 수치를 추가하거나 뺄 것인가
- 어떤 물약 효과를 부여할 것인가
- 게임 초반에 얻을 수 있는가 아니면 후반에 얻을 수 있는가

## 음식 요소 추가하기 {#adding-the-food-component}

음식 요소를 추가하기 위해, `Item.Settings` 인스턴스에 전달할 수 있습니다:

```java
new Item.Settings().food(new FoodComponent.Builder().build())
```

지금은, 이 아이템을 그저 먹을 수만 있게 합니다.

`FoodComponent.Builder` 클래스는 플레이어가 음식을 먹을 때 무슨 일이 일어나는지 조정할 수 있게 해주는 많은 메서드를 가지고 있습니다.

| 메서드                  | 설명                                                                                                                                                             |
| -------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `nutrition`          | 아이템이 채울 배고픔의 양을 정합니다.                                                                                                                          |
| `saturationModifier` | 아이템이 추가할 포만감의 양을 정합니다.                                                                                                                         |
| `alwaysEdible`       | 배고픔의 양에 상관없이 항상 먹을 수 있게 합니다.                                                                                                                   |
| `snack`              | 아이템을 간식으로서 정하게 합니다.                                                                                                                            |
| `statusEffect`       | 아이템을 먹을 때 상태 효과를 추가합니다. 일반적으로 상태 효과 인스턴스와 확률이 이 메서드에 전달됩니다. 여기서, 확률은 백분율 (`1f = 100%`) 입니다. |

원하는 대로 빌더를 수정한 후, `FoodComponent`를 가지기 위해 `build()` 메서드를 호출할 수 있습니다.

@[code transcludeWith=:::5](@/reference/1.21/src/main/java/com/example/docs/item/ModItems.java)

[Creating Your First Item](./first-item) 페이지의 예시와 비슷하게 위의 구성 요소를 사용하겠습니다:

@[code transcludeWith=:::poisonous_apple](@/reference/1.21/src/main/java/com/example/docs/item/ModItems.java)

이렇게 하면 아이템이 다음과 같이 됩니다:

- 배고픔의 양에 상관없이 항상 먹을 수 있습니다.
- "간식"입니다.
- 항상 먹을 때마다 독 II를 6초 부여합니다.

<VideoPlayer src="/assets/develop/items/food_0.webm" title="Eating the Suspicious Substance" />
