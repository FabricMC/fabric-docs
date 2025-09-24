---
title: Створення вашого першого предмету
description: Дізнайтеся, як зареєструвати простий предмет, як текстурувати, моделювати та називати його.
authors:
  - dicedpixels
  - Earthcomputer
  - IMB11
  - RaphProductions
---

Ця сторінка познайомить вас з деякими ключовими поняттями, пов’язаними з предметами, а також з тим, як їх можна реєструвати, текстурувати, моделювати та називати.

Якщо ви не знаєте, все в Minecraft зберігається в реєстрах, і предмети не є винятком.

## Клас підготовки предметів {#preparing-your-items-class}

Щоб спростити реєстрацію предметів, ви можете створити метод, який приймає ідентифікатор рядка, деякі параметри предмета та фабрику для створення екземпляра `Item`.

Цей метод створить предмет із наданим ідентифікатором і зареєструє його в реєстрі предметів гри.

Ви можете помістити цей метод у клас під назвою `ModItems` (або будь-яку іншу назву класу).

Mojang також робить це зі своїми предметами! Щоб отримати натхнення, перегляньте клас `Items`.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Зверніть увагу на використання інтерфейсу [`функції`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/function/Function.html) для фабрики, який згодом дозволить нам указати, як ми хочемо створити наш елемент із налаштувань елемента за допомогою `Item::new`.

## Реєстрація предмета {#registering-an-item}

Тепер ви можете зареєструвати товар за допомогою методу зараз.

Метод реєстратору приймає екземпляр класу `Item.Settings` як параметр. Цей клас дозволяє вам налаштовувати властивості предмета за допомогою різних методів конструктора.

::: tip
If you want to change your item's stack size, you can use the `maxCount` method in the `Item.Settings` class.

Це не спрацює, якщо ви позначили предмет як такий, що може бути пошкоджений, оскільки розмір стосу завжди дорівнює 1 для предметів, які можна пошкодити, щоб запобігти повторюваним експлойтам.
:::

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

`Item::new` повідомляє функції реєстратору створити екземпляр `Item` з `Item.Settings` шляхом виклику конструктора `Item` (`new Item(...)`), який приймає `Item.Settings` як параметр.

Однак, якщо ви зараз спробуєте запустити модифікований клієнт, ви побачите, що наш предмет ще не існує в грі! Це тому, що ви не ініціалізували клас статично.

Для цього ви можете додати загальнодоступний статичний метод ініціалізації до свого класу та викликати його з класу [ініціалізатора мода](../getting-started/project-structure#entrypoints). Наразі цей метод не потребує нічого всередині.

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/ExampleModItems.java)

Виклик методу в класі статично ініціалізує його, якщо він не був попередньо завантажений - це означає, що всі `статичні` поля оцінюються. Ось для чого цей фіктивний метод `ініціалізації`.

## Додавання предмету до групи товарів {#adding-the-item-to-an-item-group}

:::info
Якщо ви хочете додати предмет до спеціальної `ItemGroup`, перегляньте сторінку [власні групи предметів](./custom-item-groups) для отримання додаткової інформації.
:::

Для прикладу ми додамо цей предмет до інгредієнтів `ItemGroup`, вам потрібно буде використовувати події групи предметів Fabric API, зокрема `ItemGroupEvents.modifyEntriesEvent`

Це можна зробити за допомогою методу `ініціалізації` вашого класу предметів.

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Завантажуючись у гру, ви бачите, що наш предмет зареєстровано та знаходиться в групі предметів «Інгредієнти»:

![Предмет у групі інгредієнтів](/assets/develop/items/first_item_0.png)

Однак у ньому відсутнє таке:

- Модель предмету
- Текстура
- Переклад (назва)

## Назва предмета {#naming-the-item}

Предмет наразі не має перекладу, тому його потрібно буде додати. Ключ перекладу вже надав Minecraft: `item.mod_id.suspicious_substance`.

Створіть новий файл JSON за адресою: `src/main/resources/assets/mod-id/lang/en_us.json` і введіть ключ перекладу та його значення:

```json
{
  "item.mod_id.suspicious_substance": "Suspicious Substance"
}
```

Ви можете перезапустити гру або створити свій мод і натиснути <kbd>F3</kbd>+<kbd>T</kbd>, щоб застосувати зміни.

## Додання текстури та моделі {#adding-a-texture-and-model}

Щоб надати вашому предмету текстуру та модель, просто створіть зображення текстури 16x16 для свого предмета та збережіть його в теці `assets/mod-id/textures/item`. Назвіть файл текстури так само, як ідентифікатор предмета, але з розширенням `.png`.

Для прикладу ви можете використовувати цей приклад текстури для `suspicious_substance.png`

<DownloadEntry visualURL="/assets/develop/items/first_item_1.png" downloadURL="/assets/develop/items/first_item_1_small.png">Текстура</DownloadEntry>

Під час перезавантаження/перезавантаження гри ви повинні побачити, що предмет все ще не має текстури, тому що вам потрібно буде додати модель, яка використовує цю текстуру.

Ви збираєтеся створити просту модель `item/generated`, яка приймає вхідну текстуру і нічого більше.

Створіть модель JSON в теці `assets/mod-id/models/item` з такою самою назвою, як у предмета; `suspicious_substance.json`

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/models/item/suspicious_substance.json)

### Розбираємо модель JSON {#breaking-down-the-model-json}

- `parent`: це батьківська модель, яку ця модель успадкує. У цьому випадку це модель `item/generated`.
- `textures`: тут ви визначаєте текстури для моделі. Ключ `layer0` — це текстура, яку використовуватиме модель.

Більшість предметів використовуватимуть модель `item/generated` як батьківську, оскільки це проста модель, яка лише показує текстуру.

Існують альтернативи, такі як `item/handheld`, який використовується для предметів, які "тримаються" в руці гравця, наприклад інструментів.

## Створення опису моделі предмета {#creating-the-item-model-description}

Minecraft не визначає автоматично, де можна знайти файли моделей ваших предметів, нам потрібно надати опис моделі предмета.

Створіть JSON опису предмета в `assets/mod-id/items` з тією ж назвою файлу, що й ідентифікатор предмета: `suspicious_substance.json`.

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/items/suspicious_substance.json)

### Розбір опису моделі предмета JSON {#breaking-down-the-item-model-description-json}

- `model`: це властивість, яка містить посилання на нашу модель.
  - `type`: це тип нашої моделі. Для більшості предметів це має бути `minecraft:модель`
  - `model`: це ідентифікатор моделі. Він повинен мати таку форму: `mod-id:item/item_name`

Тепер ваш предмет у грі має виглядати так:

![Предмет із правильною моделлю](/assets/develop/items/first_item_2.png)

## Зробити предмет компостованим або паливом {#making-the-item-compostable-or-a-fuel}

API Fabric надає різні реєстри, які можна використовувати для додавання додаткових властивостей до вашого предмета.

Наприклад, якщо ви хочете зробити свій предмет компостованим, ви можете використати `CompostableItemRegistry`:

@[code transcludeWith=:::_10](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Крім того, якщо ви хочете зробити свій предмет паливом, ви можете використати подію `FuelRegistryEvents.BUILD`:

@[code transcludeWith=:::_11](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

## Додання звичайних рецептів майстрування {#adding-a-basic-crafting-recipe}

<!-- In the future, an entire section on recipes and recipe types should be created. For now, this suffices. -->

Якщо ви хочете додати рецепт виготовлення для свого предмета, вам потрібно буде розмістити JSON-файл рецепту в теці `data/mod-id/recipe`.

Щоб дізнатися більше про формат рецепта, перегляньте ці ресурси:

- [Генератор JSON рецептів](https://crafting.thedestruc7i0n.ca/)
- [Minecraft Wiki - рецепт JSON](https://minecraft.wiki/w/Recipe#JSON_Format)

## Спеціальні підказки {#custom-tooltips}

Якщо ви хочете, щоб ваш предмет мав спеціальну підказку, вам потрібно буде створити клас, який розширює `Item` і перевизначає метод `appendTooltip`.

:::info
У цьому прикладі використовується клас `LightningStick`, створений на сторінці [власні інтерактивні предмети](./custom-item-interactions).
:::

@[code lang=java transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/custom/LightningStick.java)

Кожен виклик `add()` додаватиме один рядок до підказки.

![Вітрина підказки](/assets/develop/items/first_item_3.png)
