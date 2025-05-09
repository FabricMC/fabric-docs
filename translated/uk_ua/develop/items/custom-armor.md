---
title: Власні обладунки
description: Навчіться створювати власні обладунки.
authors:
  - IMB11
---

Обладунки забезпечує гравцеві підвищений захист від атак мобів та інших гравців.

## Створення класу матеріалів обладунків {#creating-an-armor-materials-class}

Технічно вам не потрібен спеціальний клас для вашого матеріалу обладунку, але це все одно хороша практика з кількістю статичних полів, які вам знадобляться.

Для цього прикладу ми створимо клас `GuiditeArmorMaterial` для зберігання наших статичних полів.

### Основна міцність {#base-durability}

Ця константа буде використовуватися в методі `Item.Settings#maxDamage(int damageValue)` під час створення наших предметів обладунків, вона також потрібна як параметр у конструкторі `ArmorMaterial`, коли ми створимо наш об’єкт `ArmorMaterial` пізніше.

@[code transcludeWith=:::base_durability](@/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

Якщо вам важко визначити збалансовану базову міцність, ви можете звернутися до екземплярів матеріалу обладунків ванілльного кольору, знайденого в інтерфейсі `ArmorMaterials`.

### Ключ реєстру активів обладнання {#equipment-asset-registry-key}

Попри те, що нам не потрібно реєструвати наш `ArmorMaterial` в жодних реєстрах, загалом добре зберігати будь-які ключі реєстру як константи, оскільки гра використовуватиме це для пошуку відповідних текстур для наших обладунків.

@[code transcludeWith=:::material_key](@/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

Пізніше ми передамо це конструктору `ArmorMaterial`.

### Екземпляр `ArmorMaterial` {#armormaterial-instance}

Щоб створити наш матеріал, нам потрібно створити новий екземпляр запису `ArmorMaterial`, тут використовуватимуться базова міцність і константи ключа реєстру матеріалів.

@[code transcludeWith=:::guidite_armor_material](@/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

Конструктор `ArmorMaterial` приймає такі параметри в такому конкретному порядку:

| Параметр              | Опис                                                                                                                                                                                                                                                                      |
| --------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `durability`          | Базова міцність усіх частин обладунків, вона використовується при розрахунку загальної міцності кожної окремої частини обладунків, для якої використовується цей матеріал. Це має бути базова константа міцності, яку ви створили раніше. |
| `defense`             | Мапування `EquipmentType` (переліку, що представляє кожен слот для обладунків) на ціле значення, яке вказує на захисну цінність матеріалу при використанні у відповідному слоті для обладунків.                                        |
| `enchantmentValue`    | «Зачаровуваність» предмета обладунків, які використовують цей матеріал.                                                                                                                                                                                   |
| `equipSound`          | Запис у реєстрі звукової події, яка відтворюється, коли ви одягаєте частину обладунків, яка використовує цей матеріал. Щоб дізнатися більше про звуки, перегляньте сторінку [Власні звуки](../sounds/custom).                             |
| `toughness`           | Плаваюче значення, яке представляє атрибут "міцності" матеріалу обладунків - по суті, наскільки добре обладунки поглинають пошкодження.                                                                                                                   |
| `knockbackResistance` | Плаваюче значення, яке представляє ступінь опору до відкидання, який матеріал обладунків надає власнику.                                                                                                                                                  |
| `repairIngredient`    | Теґ предмета, який представляє всі предмети, які можна використовувати для лагодження предметів обладунків з цього матеріалу в ковадлі.                                                                                                                   |
| `assetId`             | Ключ реєстру `EquipmentAsset`, це має бути константа ключа реєстру активів обладнання, яку ви створили раніше.                                                                                                                                            |

Якщо вам важко визначити значення для будь-якого з параметрів, ви можете проконсультуватися з екземплярами `ArmorMaterial`, які можна знайти в інтерфейсі `ArmorMaterials`.

## Створення предмету обладунків {#creating-the-armor-items}

Тепер, коли ви зареєстрували матеріал, ви можете створювати предмети обладунків у своєму класі `ModItems`:

Очевидно, що комплект обладунків не потребує кожного типу, щоб бути задоволеним, ви можете мати комплект із лише чоботами, наголінниками тощо. Панцир черепахи (шолом) ванілли є гарним прикладом комплекту обладунків з відсутніми слотами.

На відміну від `ToolMaterial`, `ArmorMaterial` не зберігає жодної інформації про міцність предметів. З цієї причини базову міцність необхідно вручну додати до `Item.Settings` предметів обладунків під час їх реєстрації.

Це досягається шляхом передачі створеної нами раніше константи `BASE_DURABILITY` в метод `maxDamage` у класі `Item.Settings`.

@[code transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Вам також потрібно буде **додати предмети до групи предметів**, якщо ви хочете, щоб вони були доступні з творчого інвентарю.

Як і для всіх предметів, для них також слід створити ключі перекладу.

## Текстура і модель {#textures-and-models}

Вам потрібно буде створити набір текстур для предметів і набір текстур для фактичних обладунків, коли її носить «гуманоїдна» сутність (гравці, зомбі, скелети тощо).

### Модель і текстура предмета {#item-textures-and-model}

Ці текстури нічим не відрізняються від інших предметів– ви повинні створити текстури та створити загальну згенеровану модель предмета, про що йдеться в [створення вашого першого предмета](./first-item#adding-a-texture-and-model).

Для прикладу ви можете використовувати наведені нижче текстури та модель JSON як еталон.

<DownloadEntry visualURL="/assets/develop/items/armor_0.png" downloadURL="/assets/develop/items/example_armor_item_textures.zip">Текстура предмета</DownloadEntry>

:::info
Вам знадобляться файли моделі JSON для всіх предметів, а не лише для шолома, це той самий принцип, що й інші моделі предметів.
:::

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/models/item/guidite_helmet.json)

Як бачите, в грі предмети обладунків повинні мати відповідні моделі:

![Модель предмета обладунків](/assets/develop/items/armor_1.png)

### Текстура обладунків {#armor-textures}

Коли сутність носить ваші обладунки, нічого не буде показано. Це тому, що вам не вистачає текстур і визначень моделі обладнання.

![Модель зламаних обладунків на гравці](/assets/develop/items/armor_2.png)

Є два шари для текстури обладунків, обидва повинні бути присутніми.

Раніше ми створили константу `RegistryKey<EquipmentAsset>` під назвою `GUIDITE_ARMOR_MATERIAL_KEY`, яку ми передали в наш конструктор `ArmorMaterial`. Рекомендується так само назвати текстуру, тому в нашому випадку це `guidite.png`

- `assets/mod-id/textures/entity/equipment/humanoid/guidite.png` – містить текстури верхньої частини тіла та черевиків.
- `assets/mod-id/textures/entity/equipment/humanoid_leggings/guidite.png` - Містить текстури наголінників.

<DownloadEntry downloadURL="/assets/develop/items/example_armor_layer_textures.zip">Текстури моделі обладунків Guidite</DownloadEntry>

:::tip
Якщо ви оновлюєте старішу версію гри до 1.21.4, у теці `humanoid` буде ваша текстура обладунків `layer0.png`, а в теці `humanoid_leggings` — ваша текстура обладунків `layer1.png`.
:::

Далі вам потрібно буде створити пов’язане визначення моделі обладнання. Вони знаходяться в теці `/assets/mod-id/equipment/`.

Константа `RegistryKey<EquipmentAsset>`, яку ми створили раніше, визначатиме назву файлу JSON. У цьому випадку це буде `guidite.json`.

Оскільки ми плануємо додати лише «гуманоїда» (шолом, нагрудник, наголінники, чоботи тощо) обладунків, наше визначення моделі обладнання виглядатиме так:

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/equipment/guidite.json)

З наявністю текстур і визначення моделі спорядження ви зможете бачити свої обладунки на сутностях, які її носять:

![Робоча модель обладунків на гравці](/assets/develop/items/armor_3.png)

<!-- TODO: A guide on creating equipment for dyeable armor could prove useful. -->
