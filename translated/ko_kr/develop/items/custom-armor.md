---
title: 사용자 지정 갑옷
description: 갑옷을 추가하는 방법에 대해 알아보세요.
authors:
  - IMB11
---

갑옷은 몹과 다른 플레이어의 공격으로부터 플레이어에게 추가적인 방어력을 제공합니다.

## 갑옷 재료 클래스 만들기 {#creating-an-armor-materials-class}

기술적으로, 갑옷 재료를 위해 새로운 클래스를 필수적으로 만들어야 하는 것은 아니지만, 필요한 정적 필드를 고려했을 때 좋은 습관이라고 할 수 있습니다.

예제에서는, `GuiditeArmorMaterial` 클래스에 정적 필드를 저장합니다.

### 기본 내구도 {#base-durability}

이 상수 값은 갑옷 아이템을 추가할 때 `Item.Settings#maxDamage(int damageValue)` 메소드에 사용됩니다. 이후 `ArmorMaterial` 객체를 생성할 때 생성자의 매개 변수에도 입력해야 합니다.

@[code transcludeWith=:::base_durability](@/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

균형 잡힌 내구성을 결정하는 것이 어렵다면, `ArmorMaterials` 인터페이스를 통해 찾을 수 있는 바닐라 갑옷 재료 인스턴스를 참고해 보아도 됩니다.

### 장비 어셋 레지스트리 키 {#equipment-asset-registry-key}

굳이 `ArmorMaterial`을 다른 레지스트리에 등록할 필요까진 없지만, 레지스트리 키를 상수 필드로 가지는 것은 좋은 습관입니다. 게임이 갑옷에 맞는 텍스처를 찾을 때 사용할 수 있기 때문입니다.

@[code transcludeWith=:::material_key](@/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

레지스트리 키는 나중에 `ArmorMaterial` 생성자에 입력할 것입니다.

### `ArmorMaterial` 인스턴스 {#armormaterial-instance}

재료를 추가하려면, 새로운 `ArmorMaterial` 레코드 인스턴스를 생성해야 합니다. 기본 내구도와 재료 레지스트리 키가 여기에 입력됩니다.

@[code transcludeWith=:::guidite_armor_material](@/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

`ArmorMaterial` 생성자는 순서대로 다음 매개변수를 입력받습니다:

| 매개 변수                 | 설명                                                                                                                                                       |
| --------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `durability`          | 이 재료로 만들어진 갑옷의 기본 내구도. 이후 각 방어구의 총 내구도를 계산할 때 사용됩니다. 이전에 만들어 두었던 기본 내구도 상수가 입력되어야 합니다.                   |
| `defense`             | `EquipmentType` 열거형(방어구 슬롯을 지정할 때 사용합니다)과 정수 값이 서로 연결된 맵. 해당 방어구 슬롯에 갑옷을 착용했을 때 증가할 방어력을 설정할 때 사용합니다. |
| `enchantmentValue`    | 이 재료로 만들어진 갑옷 아이템의 "마법 부여성."                                                                                                             |
| `equipSound`          | 이 재료로 만들어진 갑옷을 착용했을 때 재생될 소리 이벤트의 레지스트리 항목. 소리에 대한 자세한 내용은, [사용자 지정 소리](../sounds/custom)를 참조하십시오.                       |
| `toughness`           | 갑옷 재료의 "방어 강도" 속성을 나타내는 실수 값. 사실상 갑옷이 흡수하는 피해량입니다.                                                                       |
| `knockbackResistance` | 이 재료로 만들어진 갑옷을 착용했을 때 증가할 밀치기 저항 실수 값.                                                                                                   |
| `repairIngredient`    | 모루에서 이 재료로 만들어진 갑옷을 수리할 때 사용할 수 있는 아이템의 태그.                                                                                              |
| `assetId`             | 레지스트리 키는 나중에 `ArmorMaterial` 생성자에 입력할 것입니다.                                                                                              |

매개 변수의 값을 결정하는 것이 어렵다면, 위에서 언급한 것 처럼 `ArmorMaterials` 인터페이스에 있는 바닐라 `ArmorMaterial` 인스턴스를 참고할 수 있습니다.

## 갑옷 아이템 추가하기 {#creating-the-armor-items}

이제 재료를 등록했으니, `ModItems` 클래스에 갑옷 아이템을 추가할 수 있습니다:

물론, 모든 종류의 갑옷을 만들 필요는 없습니다. 부츠나 레깅스만 있어도 되죠. 거북 등딱지는 투구만 있기 때문에, 이러한 상황의 좋은 예시라고 할 수 있습니다.

`ToolMaterial`과는 다르게, `ArmorMaterial`은 아이템의 내구도에 대한 정보는 저장되지 않습니다. 때문에 갑옷 아이템을 등록할 때 `Item.Settings`에 직접 기본 내구도를 입력해야 합니다.

이전에 생성해 두었던 `BASE_DURABILITY` 상수 필드를 `Item.Settings#maxDamage`에 입력하여 내구도를 설정할 수 있습니다.

@[code transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

추가한 갑옷을 크리에이티브 보관함에서 찾으려면 **아이템을 아이템 그룹에 추가해야** 합니다.

모든 아이템이 그렇듯, 번역 키도 추가해야 합니다.

## 텍스처와 모델 {#textures-and-models}

아이템에 사용할 텍스쳐와 더불어, "인간형" 개체(플레이어, 좀비, 스켈레톤 등)가 갑옷을 착용했을 때 보여질 실제 텍스쳐도 추가해야 합니다.

### 아이템 텍스처와 모델 {#item-textures-and-model}

이 텍스처는 다른 아이템과 마찬가지로, [첫 번째 아이템 만들기](./first-item#adding-a-texture-and-model)에서 설명한 것 처럼 텍스처를 만들고, 기본 아이템 모델을 만들어야 합니다.

예제에서는, 아래 텍스처와 JSON 모델을 사용하겠습니다.

<DownloadEntry visualURL="/assets/develop/items/armor_0.png" downloadURL="/assets/develop/items/example_armor_item_textures.zip">아이템 텍스처</DownloadEntry>

:::info
헬멧 뿐만 아니라, 모든 아이템에 대한 JSON 모델 파일을 만들어야 합니다. 다른 아이템 모델에도 똑같이 적용됩니다.
:::

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/models/item/guidite_helmet.json)

보시다시피, 인게임에서 방어구 아이템은 적절한 모델을 가져야 합니다.

![갑옷 아이템 모델](/assets/develop/items/armor_1.png)

### 갑옷 텍스처 {#armor-textures}

개체가 갑옷을 착용하더라도, 아무것도 보이지 않을 것입니다. 착용 모델 정의와 텍스처가 없기 때문입니다.

![착용 모델이 적용되지 않은 플레이어](/assets/develop/items/armor_2.png)

갑옷 텍스처는 두 가지 레이어로 이루어지며, 두 레이어 모두 존재해야 합니다.

위에서 `ArmorMaterial` 생성자에 입력했던 `RegistryKey<EquipmentAsset>` 필드 `GUIDITE_ARMOR_MATERIAL_KEY`를 사용할 때입니다. 재료와 비슷한 이름으로 설정하는 것이 좋기 때문에, 예제에서는 `guidite.png`로 설정합니다.

- `assets/mod_id/textures/entity/equipment/humanoid/guidite.png` - 상의와 부츠 텍스처가 저장됩니다.
- `assets/mod_id/textures/entity/equipment/humanoid_leggings/guidite.png` - 레깅스의 텍스처가 저장됩니다.

<DownloadEntry downloadURL="/assets/develop/items/example_armor_layer_textures.zip">Guidite 갑옷 모델 텍스처</DownloadEntry>

:::tip
모드를 1.21.4로 업데이트하고 있다면, `humanoid` 폴더는 `layer0.png` 갑옷 텍스처, `humanoid_leggings` 폴더는 `layer1.png` 갑옷 텍스처가 저장된다고 할 수 있습니다.
:::

이제, 착용 모델 정의를 만들 차례입니다. `assets/mod_id/equipment` 폴더에 추가할 것입니다.

위에서 생성했던 `RegistryKey<EquipmentAsset>` 상수가 JSON 파일의 이름을 결정하게 됩니다. 예제에서는, `guidite.json`입니다.

예제에서는 "인간형" 갑옷(헬멧, 흉갑, 레깅스, 부츠 등)만 추가했기 때문에, 모델 정의는 다음과 같은 형태로 이루어집니다:

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/equipment/guidite.json)

텍스처와 착용 모델 정의를 모두 추가했다면, 개체가 갑옷을 착용하면 다음과 같이 보일 것입니다:

![갑옷 모델이 작동중인 플레이어](/assets/develop/items/armor_3.png)

<!-- TODO: A guide on creating equipment for dyeable armor could prove useful. -->
