---
title: Створення вашого першого предмета
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

Щоб спростити реєстрацію предметів, ви можете створити метод, який приймає ідентифікатор рядка, деякі властивості предмета та фабрику для створення екземпляра `Item`.

Цей метод створить предмет із наданим ідентифікатором і зареєструє його в реєстрі предметів гри.

Ви можете помістити цей метод у клас під назвою `ModItems` (або будь-яку іншу назву класу).

Mojang також робить це зі своїми предметами! Щоб отримати натхнення, перегляньте клас `Items`.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Зверніть увагу, як ми використовуємо `GenericItem`, який дозволяє нам використовувати той самий метод `register` для реєстрації будь-якого типу предмета, який розширює `Item`. Ми також використовуємо інтерфейс [`Function`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/function/Function.html) для фабрики, який дозволяє нам вказати, як ми хочемо створити наш предмет відповідно до властивостей предмета.

## Реєстрація предмета {#registering-an-item}

Тепер ви можете зареєструвати товар за допомогою методу зараз.

Метод реєстратору приймає екземпляр класу `Item.Properties` як параметр. Цей клас дозволяє вам налаштовувати властивості предмета за допомогою різних методів конструктора.

::: tip

Якщо ви хочете змінити розмір стосу свого предмета, ви можете скористатися методом `stacksTo` у класі `Item.Properties`.

Це не спрацює, якщо ви позначили предмет як такий, що може бути пошкоджений, оскільки розмір стосу завжди дорівнює 1 для предметів, які можна пошкодити, щоб запобігти повторюваним експлойтам.

:::

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

`Item::new` повідомляє функції реєстратору створити екземпляр `Item` з `Item.Properties` шляхом виклику конструктора `Item` (`new Item(...)`), який приймає `Item.Properties` як параметр.

Однак, якщо ви зараз спробуєте запустити модифікований клієнт, ви побачите, що наш предмет ще не існує в грі! Це тому, що ви не ініціалізували клас статично.

Для цього ви можете додати загальнодоступний статичний метод ініціалізації до свого класу та викликати його з класу [ініціалізатора мода](../getting-started/project-structure#entrypoints). Наразі цей метод не потребує нічого всередині.

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/ExampleModItems.java)

Виклик методу в класі статично ініціалізує його, якщо він не був попередньо завантажений — це означає, що всі `статичні` поля оцінюються. Ось для чого цей фіктивний метод `ініціалізації`.

## Додавання предмета до вкладки творчості {#adding-the-item-to-a-creative-tab}

::: info

Якщо ви хочете додати предмет до спеціальної `CreativeModeTab`, перегляньте сторінку [власних вкладок творчості](./custom-item-groups) для отримання додаткової інформації.

:::

Для прикладу ми додамо цей предмет до інгредієнтів `CreativeModeTab`, вам потрібно буде використовувати події вкладки творчості Fabric API, зокрема `ItemGroupEvents.modifyEntriesEvent`

Це можна зробити за допомогою методу `ініціалізації` вашого класу предметів.

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Завантажуючись у гру, ви можете побачити, що наш предмет зареєстровано та знаходиться на вкладці «Інгредієнти»:

![Предмет у групі інгредієнтів](/assets/develop/items/first_item_0.png)

Однак у ньому відсутнє таке:

- Модель предмета
- Текстура
- Переклад (назва)

## Назва предмета {#naming-the-item}

Предмет наразі не має перекладу, тому його потрібно буде додати. Ключ перекладу вже надав Minecraft: `item.example-mod.suspicious_substance`.

Створіть новий файл JSON у: `src/main/resources/assets/example-mod/lang/en_us.json` (для української створіть `uk_ua.json`) і введіть ключ перекладу та його значення:

```json
{
  "item.example-mod.suspicious_substance": "Suspicious Substance"
}
```

Ви можете перезапустити гру або створити свій мод і натиснути <kbd>F3</kbd>+<kbd>T</kbd>, щоб застосувати зміни.

## Додавання клієнтського предмета, текстури та моделі {#adding-a-client-item-texture-and-model}

Щоб ваш товар мав належний зовнішній вигляд, необхідно:

- [Текстура предмета](https://minecraft.wiki/w/Textures#Items)
- [Модель предмета](https://minecraft.wiki/w/Model#Item_models)
- [Клієнтський предмет](https://minecraft.wiki/w/Items_model_definition)

### Додання текстури {#adding-a-texture}

::: info

Для отримання додаткової інформації з цієї теми перегляньте сторінку [моделей предметів](./item-models).

:::

Щоб надати вашому предмету текстуру та модель, просто створіть зображення текстури 16x16 для свого предмета та збережіть його в теці `assets/example-mod/textures/item`. Назвіть файл текстури так само, як ідентифікатор предмета, але з розширенням `.png`.

Для прикладу ви можете використовувати цей приклад текстури для `suspicious_substance.png`

<DownloadEntry visualURL="/assets/develop/items/first_item_1.png" downloadURL="/assets/develop/items/first_item_1_small.png">Текстура</DownloadEntry>

### Додання моделі {#adding-a-model}

Під час перезапуску/перезавантаження гри ви повинні побачити, що предмет все ще не має текстури, тому що вам потрібно буде додати модель, яка використовує цю текстуру.

Ви збираєтеся створити просту модель `item/generated`, яка приймає вхідну текстуру і нічого більше.

Створіть модель JSON у теці `assets/example-mod/models/item` з такою самою назвою, як і предмет; `suspicious_substance.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/suspicious_substance.json)

#### Розбираємо модель JSON {#breaking-down-the-model-json}

- `parent`: це батьківська модель, яку ця модель успадкує. У цьому випадку це модель `item/generated`.
- `textures`: тут ви визначаєте текстури для моделі. Ключ `layer0` — це текстура, яку використовуватиме модель.

Більшість предметів використовуватимуть модель `item/generated` як батьківську, оскільки це проста модель, яка лише показує текстуру.

Існують альтернативи, такі як `item/handheld`, який використовується для предметів, які "тримаються" в руці гравця, наприклад інструментів.

### Створення клієнтського предмета {#creating-the-client-item}

Minecraft не визначає автоматично, де можна знайти файли моделей ваших предметів, нам потрібно надати клієнтський предмет.

Створіть JSON клієнтського предмета в `assets/example-mod/items` з тим же ім’ям файлу, що й ідентифікатор предмета: `suspicious_substance.json`.

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/suspicious_substance.json)

#### Розбір JSON клієнтського предмета {#breaking-down-the-client-item-json}

- `model`: це властивість, яка містить посилання на нашу модель.
  - `type`: це тип нашої моделі. Для більшості предметів це має бути `minecraft:модель`
  - `model`: це ідентифікатор моделі. Він має мати таку форму: `example-mod:item/item_name`

Тепер ваш предмет у грі має виглядати так:

![Предмет із правильною моделлю](/assets/develop/items/first_item_2.png)

## Зробити предмет компостованим або паливом {#making-the-item-compostable-or-a-fuel}

Fabric API надає різні реєстри, які можна використовувати для додавання додаткових властивостей до вашого предмета.

Наприклад, якщо ви хочете зробити свій предмет компостованим, ви можете використати `CompostingChanceRegistry`:

@[code transcludeWith=:::\_10](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Крім того, якщо ви хочете зробити свій предмет паливом, ви можете використати подію `FuelRegistryEvents.BUILD`:

@[code transcludeWith=:::\_11](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

## Додання звичайних рецептів майстрування {#adding-a-basic-crafting-recipe}

<!-- In the future, an entire section on recipes and recipe types should be created. For now, this suffices. -->

Якщо ви хочете додати рецепт виготовлення для свого предмета, вам потрібно буде розмістити JSON-файл рецепту в теці `data/example-mod/recipe`.

Щоб дізнатися більше про формат рецепта, перегляньте ці ресурси:

- [Генератор JSON рецептів](https://crafting.thedestruc7i0n.ca/)
- [Вікі Minecraft— рецепт JSON](https://minecraft.wiki/w/Recipe#JSON_Format)

## Спеціальні підказки {#custom-tooltips}

Якщо ви хочете, щоб ваш предмет мав спеціальну підказку, вам потрібно буде створити клас, який розширює `Item` і замінює метод `appendHoverText`.

::: info

У цьому прикладі використовується клас `LightningStick`, створений на сторінці [власних інтерактивних предметів](./custom-item-interactions).

:::

@[code lang=java transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/custom/LightningStick.java)

Кожен виклик `accept()` додаватиме один рядок до підказки.

![Показ підказки](/assets/develop/items/first_item_3.png)
