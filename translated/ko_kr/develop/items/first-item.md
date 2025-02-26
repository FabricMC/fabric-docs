---
title: 첫 번째 아이템 만들기
description: 간단한 아이템을 등록하고, 텍스처와 모델, 이름을 적용하는 방법에 대해 알아보세요.
authors:
  - IMB11
  - dicedpixels
  - RaphProductions
---

이 페이지에서는 아이템과 관련된 주요 개념과 등록 방법, 그리고 어떻게 텍스처와 모델, 이름을 적용하는지 설명합니다.

모르셨을 수도 있지만, Minecraft의 모든 것은 레지스트리에 저장되며, 아이템도 예외는 아닙니다.

## 아이템 클래스 준비하기 {#preparing-your-items-class}

아이템의 등록을 단순화하기 위하여, 아이템의 인스턴스와 문자열 식별자를 입력받는 메소드를 만들 수 있습니다.

이 메소드는 입력된 식별자로 아이템을 만들어 게임의 아이템 레지스트리에 등록할 것입니다.

이 메소드를 `ModItems` 클래스에 한 번 추가해 봅시다. (클래스 이름은 마음대로 지어도 됩니다)

Mojang에서도 아이템에 이러한 작업을 수행합니다! 영감을 얻으려면 `Items` 클래스를 참조하세요.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

## 아이템 등록하기 {#registering-an-item}

이제 메소드를 사용하여 아이템을 등록할 수 있습니다.

아이템 생성자는 `Items.Settings` 클래스의 인스턴스를 매개변수로 입력받습니다. 이 클래스는 다양한 빌더 메소드를 사용하여 아이템의 속성을 구성할 수 있도록 합니다.

::: tip
If you want to change your item's stack size, you can use the `maxCount` method in the `Items.Settings`/`FabricItemSettings` class.

다만 아이템을 손상 가능하게(damageable) 설정하면 작동하지 않습니다. 손상 가능한 아이템은 복사 취약점을 막기 위하여 항상 최대 스택 크기가 1로 고정되기 때문입니다.
:::

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

하지만, 변경 사항이 적용된 클라이언트를 실행하려고 해도, 아직 아이템이 게임에 존재하지 않는 것을 확인할 수 있을 것입니다! 이는 클래스가 정적으로 초기화되지 않았기 때문입니다.

이를 위해선, 아이템 클래스에 정적 초기화 메소드를 추가하고 [모드 진입점](../getting-started/project-structure#entrypoints) 클래스에서 호출해야 합니다. 지금은, 이 메소드는 내부에 아무것도 필요로 하지 않습니다.

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/FabricDocsReferenceItems.java)

이전에 로드되지 않은 클래스의 메소드를 호출하면 클래스가 정적으로 초기화됩니다. 다시 말하자면 모든 `static` 필드가 실행되게 됩니다. 이것이 더미 `initialize` 메소드가 필요한 이유입니다.

## 아이템 그룹에 아이템 추가하기 {#adding-the-item-to-an-item-group}

:::info
아이템을 사용자 지정 `ItemGroup`에 추가하고자 한다면, 자세한 내용은 [사용자 지정 아이템 그룹](./custom-item-groups)을 참조하십시오.
:::

예제에서는 아이템을 재료 `ItemGroup`에 추가할 것이며, 이를 위해선 Fabric API 아이템 그룹 이벤트(정확히는 `ItemGroupEvents.modifyEntriesEvent`)를 이용해야 합니다.

이는 아이템 클래스의 `initialize` 메소드에서 실행될 수 있습니다.

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

게임을 실행하면, 아이템에 등록되고 재료 아이템 그룹에 추가된 것을 확인할 수 있을 것입니다.

![재료 아이템 그룹에 아이템이 추가된 모습](/assets/develop/items/first_item_0.png)

하지만, 몇 가지 빠진 것을 확인할 수 있습니다:

- 아이템 모델
- 텍스처
- 번역 (이름)

## 아이템 이름 짓기 {#naming-the-item}

아이템에 아직 번역이 없기 때문에, 번역을 추가해야 합니다. 번역 키는 이미 Minecraft에서 제공하고 있으며, 예제의 경우에는 `item.mod_id.suspicious_substance` 입니다.

`src/main/resources/assets/mod-id/lang/en_us.json`에 새로운 JSON 파일을 생성하고, 다음과 같이 번역 키를 추가합니다:

```json
{
  "item.mod_id.suspicious_substance": "Suspicious Substance"
}
```

게임을 재시작하거나 모드를 빌드한 다음 <kbd>F3</kbd> + <kbd>T</kbd>키를 눌러 변경 사항을 적용할 수 있습니다.

## 텍스처와 모델 적용하기 {#adding-a-texture-and-model}

아이템에 텍스처와 모델을 적용하려면, 간단히 16x16 텍스처 이미지를 만든 다음 `src/main/resources/assets/mod-id/textures/item` 폴더에 저장합니다. 텍스처 파일의 이름을 아이템의 식별자와 같게 지정하고, 파일 확장자는 `.png`로 설정합니다.

예제에서는, 텍스처 파일의 이름을 `suspicious_substance.png`로 설정하였습니다.

<DownloadEntry visualURL="/assets/develop/items/first_item_1.png" downloadURL="/assets/develop/items/first_item_1_small.png">텍스처</DownloadEntry>

게임을 재시작/다시로드 하더라도 아이템에 텍스처가 적용되지 않은 것을 확인할 수 있습니다. 텍스처를 사용하기 위해서는 모델이 필요하기 때문입니다.

텍스처 입력만 받는, 간단한 `item/generated` 모델을 만들어 봅시다.

`src/main/resources/assets/mod-id/models/item` 폴더에, 아이템과 같은 식별자(예제의 경우 `suspicious_substance.json`) 이름의 JSON 파일을 생성한 다음 아래와 같이 입력합니다:

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/models/item/suspicious_substance.json)

### 모델 JSON 분석하기 {#breaking-down-the-model-json}

- `parent`: 이 모델이 상속받은 부모 모델의 식별자. 예제의 경우에는, `item/generated` 모델을 사용합니다.
- `textures`: 모델에 사용할 텍스처. 예제의 경우에는, `layer0`이 모델에서 사용할 텍스처입니다.

대부분의 아이템은 부모 모델로 `item/generated` 모델을 사용하며, 이는 텍스처를 표시하기만 하는 간단한 모델입니다.

도구와 같이, 플레이어의 손에 "들어지는" 아이템에 사용되는 `item/handheld` 모델과 같이, 다른 모델도 사용할 수 있습니다.

## 아이템 모델 설명 만들기 {#creating-the-item-model-description}

마인크래프트는 아이템의 모델 파일을 어디서 찾는지 자동으로 알지 못하므로, 아이템 모델 설명을 만들어야 합니다.

`src/main/resources/assets/mod-id/items` 폴더에, 아이템과 같은 식별자(`suspicious_substance.json`)를 이름으로 가지는 아이템 설명 JSON 파일을 생성한 다음, 다음과 같이 입력합니다:

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/items/suspicious_substance.json)

### 아이템 모델 설명 JSON 분석하기 {#breaking-down-the-item-model-description-json}

- `model`: 모델에 대한 리퍼런스가 포함되는 속성.
  - `type`: 모델의 형태. 대부분의 아이템은, `minecraft:model`이어야 합니다.
  - `model`: 모델 식별자. `mod_id:item/item_name`과 같은 형태여야 합니다.

이제 인게임에서 아래와 같이 아이템이 표시될 것입니다:

![올바른 모델이 적용된 아이템](/assets/develop/items/first_item_2.png)

## 아이템을 퇴비 또는 연료로 사용할 수 있게 하기 {#making-the-item-compostable-or-a-fuel}

Fabric API는 아이템 속성에 추가적인 속성을 적용할 수 있는 다양한 레지스트리를 제공하고 있습니다.

예를 들어, 아이템을 퇴비통에 사용할 수 있게 만들고 싶다면, 다음과 같이 `CompostableItemRegistry`를 사용할 수 있습니다:

@[code transcludeWith=:::_10](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

또는, 아이템을 연료로 사용할 수 있게 만드려면, 다음과 같이 `FuelRegistryEvents.BUILD` 이벤트를 사용할 수도 있습니다:

@[code transcludeWith=:::_11](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

## 기본적인 제작법 추가하기 {#adding-a-basic-crafting-recipe}

<!-- In the future, an entire section on recipes and recipe types should be created. For now, this suffices. -->

아이템에 제작법을 추가하고자 한다면, `src/main/resources/data/mod-id/recipe` 폴더에 제작법 JSON을 추가해야 합니다.

제작법 포맷에 관한 자세한 내용은 다음 리소스를 참조하여 주십시오:

- [Recipe JSON Generator](https://crafting.thedestruc7i0n.ca/)
- [Recipe: JSON Format - Minecraft Wiki](https://minecraft.wiki/w/Recipe#JSON_Format)

## 사용자 지정 도구 설명 {#custom-tooltips}

아이템에 사용자 지정 도구 설명을 추가하려면, `Item` 클래스를 상속하는 클래스를 만들고 `appendTooltip` 메소드를 오버라이드(Override)해야 합니다.

:::info
이 예제에서는 [사용자 지정 아이템 상호 작용](./custom-item-interactions)에서 만든 `LightningStick` 클래스를 사용합니다.
:::

@[code lang=java transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/custom/LightningStick.java)

`add()` 메소드를 호출하며 도구 설명에 새로운 줄을 추가할 수 있습니다.

![사용자 지정 도구 설명이 표시되는 모습](/assets/develop/items/first_item_3.png)
