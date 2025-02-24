---
title: 음식 아이템
description: 아이템을 섭취할 수 있고, 다양한 효과를 구성할 수 있게 FoodComponent에 추가하는 방법을 알아보세요.
authors:
  - IMB11
---

음식은 Minecraft 생존의 핵심 요소로, 섭취 가능한 아이템을 만들 때에는 다른 아이템의 용도를 고려해야 합니다.

모드를 이용하여 사기적인 아이템을 만드는 것이 아니라면, 다음을 고려해야 합니다:

- 배고픔 수치를 얼마나 추가하거나 빼낼 것인가
- 어떤 물약 효과를 부여할 것인가
- 초반부에 획득 가능한가, 후반부에 획득 가능한가?

## 음식 요소 추가하기 {#adding-the-food-component}

아이템에 음식 요소를 추가하려면, `Item.Settings` 인스턴스에 다음과 같이 전달할 수 있습니다:

```java
new Item.Settings().food(new FoodComponent.Builder().build())
```

지금으로썬, 이렇게 하면 아이템을 그저 섭취할 수만 있게 합니다.

`FoodComponent.Builder` 클래스에는 음식이 섭취되었을 때 발생하는 일을 조정할 수 있게 해주는 여러 메소드가 있습니다.

| 메소드                  | 설명                                        |
| -------------------- | ----------------------------------------- |
| `nutrition`          | 아이템이 채울 배고픔의 양을 설정합니다.    |
| `saturationModifier` | 아이템이 채울 포만감의 양을 설정합니다.    |
| `alwaysEdible`       | 배고픔의 상관없이 항상 먹을 수 있게 합니다. |

원하는 대로 빌더를 수정했으면, `build()` 메소드를 호출하며 `FoodComponent`를 생성할 수 있습니다.

플레이어가 아이템을 섭취했을 때 물약 효과가 부여되도록 만들고 싶다면, `FoodComponent` 클래스와 함께 다음과 같이 `ConsumableComponent`도 사용해야 합니다:

@[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

[첫 번째 아이템 만들기](./first-item)의 예시와 유사하게, 위의 구성 요소를 사용하여 아이템을 추가해 봅시다:

@[code transcludeWith=:::poisonous_apple](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

이렇게 하면 아이템에 다음과 같은 효과가 적용됩니다:

- 배고픔의 양에 상관없이 항상 먹을 수 있습니다.
- 항상 먹을 때마다 독 II를 6초 부여합니다.

<VideoPlayer src="/assets/develop/items/food_0.webm">독사과를 섭취하는 모습</VideoPlayer>
