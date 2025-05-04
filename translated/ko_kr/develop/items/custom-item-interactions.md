---
title: 사용자 지정 아이템 상호 작용
description: 바닐라에 내장된 이벤트를 사용하는 아이템을 추가하는 방법을 알아보세요.
authors:
  - IMB11
---

결국에는 사용했을 때 세계와 상호 작용하는 아이템이 필요할 것입니다.

바닐라 아이템 이벤트를 알아보기 전에 먼저 이해해야 할 몇 가지 주요 클래스가 있습니다.

## `TypecActionResult` {#typedactionresult}

아이템의 경우, 가장 일반적인 `TypedActionResult`는 `ItemStacks`입니다. 이 클래스는 이벤트가 발생한 후 아이템 스택을 교체해야 하는지(또는 교체하지 않아야 하는지) 게임에 알려줍니다.

만약 이벤트에서 아무런 일도 발생하지 않는다면, `TypedActionResult#pass(stack)` 메소드에 `stack` 매개 변수로 현재 아이템 스택을 입력해야 합니다.

현재 아이템 스택은 플레이어의 손에서 받아올 수 있습니다. 일반적으로 `TypedActionResult`가 필요한 이벤트는 호출 메소드에 손을 전달합니다.

```java
TypedActionResult.pass(user.getStackInHand(hand))
```

만약 현재 스택을 반환하면, 이벤트의 성공 여부에 관계 없이 아무 일도 일어나지 않을 것입니다.

현재 스택을 비우고 싶다면, 빈 스택을 반환하면 됩니다. 차감에도 똑같은 방법을 사용할 수 있으며, 다음과 같이 현재 스택을 페치하고 원하는 만큼 차감한 뒤 반환하면 됩니다:

```java
ItemStack heldStack = user.getStackInHand(hand);
heldStack.decrement(1);
TypedActionResult.success(heldStack);
```

## `ActionResult` {#actionresult}

`ActionResult`도 게임에 이벤트의 상태를 알려주는 클래스입니다.

## 오버라이드 가능한 이벤트 {#overridable-events}

다행히도, 아이템 클래스에는 아이템에 추가 기능을 만들 때 오버라이드할 수 있는 여러 메소드가 있습니다.

:::info
이 이벤트를 사용하는 훌륭한 예시는 [소리 이벤트 재생하기](../sounds/using-sounds)에서 찾을 수 있으며, 플레이어가 블록을 오른쪽 마우스로 클릭했을 때 `useOnBlock` 이벤트에서 소리를 재생합니다.
:::

| 메소드             | 용도                                            |
| --------------- | --------------------------------------------- |
| `postHit`       | 플레이어가 개체를 공격한 후 실행.           |
| `postMine`      | 플레이어가 블록을 채굴한 후 실행.           |
| `inventoryTick` | 아이템이 보관함에 있을 때 매 틱 마다 실행.     |
| `onCraft`       | 아이템이 제작되었을 때 실행.              |
| `useOnBlock`    | 아이템을 들고 블록에 오른쪽 마우스로 클릭하면 실행. |
| `use`           | 아이템을 들고 오른쪽 마우스로 클릭하면 실행.     |

## `use()` 이벤트 {#use-event}

플레이어 앞에 벼락을 생성하는 아이템을 만들고 싶다고 가정해봅시다. 새로운 클래스를 만들어야 할 것입니다.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/custom/LightningStick.java)

아마도 `use` 이벤트가 필요한 때인 것 같습니다. 이 이벤트를 사용해 벼락을 생성할 수 있으며, 예제에서는 플레이어가 보고 있는 방향으로부터 10 블록 떨어진 위치에 생성하겠습니다.

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/custom/LightningStick.java)

평소처럼, 아이템을 등록하고, 모델과 텍스처, 번역 키도 추가해야 합니다.

보시다시피, 플레이어의 10블록 앞에 벼락이 생성되는 것을 확인할 수 있습니다.

<VideoPlayer src="/assets/develop/items/custom_items_0.webm">벼락 막대기를 사용하는 모습</VideoPlayer>
