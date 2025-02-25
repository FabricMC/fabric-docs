---
title: 사용자 지정 도구와 무기
description: 도구를 만들고 속성을 설정하는 방법에 대해 알아보세요.
authors:
  - IMB11
---

도구는 플레이어가 자원을 얻고, 건축하고, 방어할 수 있게 하는, 생존과 발전에 필수적인 요소입니다.

## 도구 재료 만들기 {#creating-a-tool-material}

새로운 `ToolMaterial` 객체를 인스턴스화 하여 도구의 재료를 만들 수 있습니다. 이후 이를 재료로 사용하는 도구를 만들기 위하여 필드에 저장합시다.

@[code transcludeWith=:::guidite_tool_material](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

`ToolMaterial` 생성자는 순서대로 다음 매개변수를 입력받습니다:

| 매개 변수                     | 설명                                                                                                           |
| ------------------------- | ------------------------------------------------------------------------------------------------------------ |
| `incorrectBlocksForDrops` | 블록이 이 태그에 포함되어 있다면, 플레이어가 이 `ToolMaterial`로 만들어진 도구를 해당 블록에 사용했을 때, 블록이 아무런 아이템도 떨구지 않게 됩니다. |
| `durability`              | 이 `ToolMaterial`로 만들어진 도구의 내구성을 설정합니다.                                                       |
| `speed`                   | 이 `ToolMaterial`로 만들어진 도구의 채굴 속도를 설정합니다.                                                     |
| `attackDamageBonus`       | 이 `ToolMaterial`로 만들어진 도구가 가지게 될 추가 공격 피해를 설정합니다.                                            |
| `enchantmentValue`        | 이 `ToolMaterial`로 만들어진 도구의 "마법 부여성"을 설정합니다.                                                  |
| `repairItems`             | 이 태그에 포함된 아이템은 모루에서 `ToolMaterial`로 만들어진 도구를 수리할 때 사용할 수 있습니다.                               |

숫자가 입력되는 매개 변수의 균형 잡힌 값을 결정하기 어렵다면, `ToolMaterial.STONE`이나 `ToolMaterial.DIAMOND`와 같이 기본 도구 재료를 참조하는 것도 좋습니다.

## 도구 아이템 만들기 {#creating-tool-items}

[첫 번째 아이템 만들기](./first-item)에서 만들었던 유틸리티 메소드를 통해, 다음과 같이 아이템을 추가할 수 있습니다:

@[code transcludeWith=:::7](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

두 실수 값 (`1f, 1f`) 은 각각 도구의 공격 피해와 공격 속도를 의미합니다.

크리에이티브 보관함에서 아이템을 찾고 싶다면 아이템 그룹에 추가해야 한다는 사실을 잊지 마세요!

@[code transcludeWith=:::8](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

아이템 번역과 텍스처, 모델도 추가해야 합니다. 다만, 아이템 모델을 만들 때에는, `item/generated` 대신 `item/handheld`를 부모 모델로 사용해야 합니다.

예제에서는, 아래 모델과 텍스처를 "Guidite Sword" 아이템에 사용하겠습니다:

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/models/item/guidite_sword.json)

<DownloadEntry visualURL="/assets/develop/items/tools_0.png" downloadURL="/assets/develop/items/tools_0_small.png">텍스처</DownloadEntry>

거의 다 됐습니다! 게임에 접속하고, 크레에이티브 보관함에서 도구 탭을 선택하면 도구 아이템이 표시될 것입니다.

![도구를 들고 있는 플레이어](/assets/develop/items/tools_1.png)
