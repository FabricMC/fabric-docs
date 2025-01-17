---
title: Создание вашего первого предмета
description: Узнайте, как зарегистрировать предмет, добавить текстуру, модель и название.
authors:
  - IMB11
  - dicedpixels
  - RaphProductions
---

# Создание вашего первого предмета{#creating-your-first-item}

На этой странице вы познакомитесь с некоторыми ключевыми концепциями, касающимися предметов, а также с тем, как их регистрировать, задавать текстуры, моделировать и давать названия.

Если вы не знали, то все в Майнкрафте хранится в реестрах, и предметы не стали исключением.

## Подготовка класса вашего предмета {#preparing-your-items-class}

Для упрощения регистрации предмета вы можете создать метод, принимающий экземпляр предмета и строковый идентификатор.

Этот метод будет создавать предмет с данным идентификатором и регистрировать его с помощью реестра предметов игры.

Вы можете поместить этот метод в класс под именем `ModItems` (или как вы его назовете).

Mojang уже сделали это со своими предметами! Загляните в класс `Items` для вдохновения.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

## Регистрация предмета{#registering-an-item}

Теперь вы можете зарегистрировать предмет, используя метод.

Конструктор предмета принимает экземпляр класса `Items.Settings` в качестве параметра. Этот класс позволяет вам настраивать параметры предмета через различные методы.

::: tip
If you want to change your item's stack size, you can use the `maxCount` method in the `Items.Settings`/`FabricItemSettings` class.

Это не сработает, если вы пометили предмет как повреждаемый, потому что размер для повреждаемых предметов всегда равен 1, для предотвращения эксплойтов дублирования.
:::

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Однако, если вы сейчас попытаетесь запустить измененный клиент, то увидите, что наш предмет еще не существует в игре! Это происходит потому, что вы не инициализировали класс статически.

Чтобы сделать это, вы можете добавить в свой класс общедоступный статический метод инициализации и вызвать его из вашего класса [инициализатор мода] (./getting-started/project-structure#entrypoints). В настоящее время этот метод не принимает никаких аргументов.

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/FabricDocsReferenceItems.java)

Вызов метода класса статически инициализирует его, если он не был ранее загружен — это означает, что оцениваются все `статические` поля. Вот для чего нужен этот фиктивный метод `initialize`.

## Добавление предмета к группе предметов{#adding-the-item-to-an-item-group}

:::info
Если вы хотите добавить предмет в собственную "ItemGroup", ознакомьтесь со страницей [Собственные группы предмета](./custom-item-groups) для получения дополнительной информации.
:::

Для примера мы добавим этот элемент в ингредиенты `ItemGroup`, вам нужно будет использовать события группы элементов Fabric API, а именно `ItemGroupEvents.modifyEntriesEvent`

Это может быть сделано в методе `initialize` вашего класса.

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Запустив игру, вы можете увидеть, что наш предмет зарегистрирован и находится в категории предметов "ингредиенты":

![Предмет в категории ингридиентов](/assets/develop/items/first_item_0.png)

Однако, не хватает следующего:

- Модель предмета
- Текстура
- Перевод(название)

## Наименование предмета {#naming-the-item}

Сейчас у предмета нет перевода, поэтому вам необходимо его добавить. Ключ перевода уже предоставлен Minecraft: `item.mod_id.suspicious_substance`.

Создайте новый файл JSON: `src/main/resources/assets/<mod id here>/lang/en_us.json` и введите ключ перевода, а также его значение:

```json
{
  "item.mod_id.suspicious_substance": "Suspicious Substance"
}
```

Вы можете перезапустить игру или создать свой мод и нажать <kbd>F3</kbd>+<kbd>T</kbd>, чтобы применить изменения.

## Добавление текстуры и модели{#adding-a-texture-and-model}

Чтобы задать вашему предмету текстуру и модель, просто создайте изображение текстуры 16x16 для вашего предмета и сохраните его по адресу`assets/<mod id here>/textures/item`. Назовите файл текстуры, так же как предмет, но c расширением `.png`.

В качестве примера вы можете использовать этот пример текстуры для `suspicious_substance.png`

<DownloadEntry visualURL="/assets/develop/items/first_item_1.png" downloadURL="/assets/develop/items/first_item_1_small.png">Текстура</DownloadEntry>

При перезапуске/перезагрузке игры вы должны увидеть, что у предмета по-прежнему нет текстуры, это потому, что вам нужно будет добавить модель, использующую эту текстуру.

Вы собираетесь создать простую модель «item/generated», которая принимает на входе только текстуру и ничего больше.

Создайте модель JSON в папке `assets/<mod id here>/models/item` с тем же именем, что и у элемента; `suspicious_substance.json`

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/models/item/suspicious_substance.json)

### Разбор модели JSON {#breaking-down-the-model-json}

- `parent`: Это родительская модель, от которой будет унаследована данная модель. В данном случае это модель `item/generated`.
- `textures`: Здесь вы определяете текстуры для модели. Ключ `layer0` — это текстура, которую будет использовать модель.

Большинство элементов будут использовать модель `item/generated` в качестве родительской, поскольку это простая модель, которая просто отображает текстуру.

Существуют альтернативы, такие как `item/handheld`, которые используются для предметов, которые "удерживаются" в руке игрока, таких как инструменты.

## Создание описания модели предмета {#creating-the-item-model-description}

Minecraft автоматически не узнает, где можно найти файлы модели вашего предмета, вам нужно предоставить описание модели предмета.

Создайте JSON-файл описания предмета в `assets/<mod id here>/items` с тем же именем файла, что и идентификатор предмета; `suspicious_substance.json`.

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/items/suspicious_substance.json)

### Разбивка описания модели предмета JSON {#breaking-down-the-item-model-description-json}

- `model`: это свойство, содержащее ссылку на нашу модель.
  - `type`: это тип нашей модели. Для большинства предметов это должно быть `minecraft:model`
  - `model`: это идентификатор модели. Он должен иметь следующую форму: `<mod id here>:item/<item name here>`

Теперь ваш предмет в игре должен выглядеть так:

![Item with correct model](/assets/develop/items/first_item_2.png)

## Сделать предмет компостируемым или топливом {#making-the-item-compostable-or-a-fuel}

API Fabric предоставляет различные реестры, которые можно использовать для добавления дополнительных свойств к вашему элементу.

Например, если вы хотите сделать свой предмет компостируемым, то вы можете использовать `CompostableItemRegistry`:

@[code transcludeWith=:::_10](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

В качестве альтернативы, если вы хотите превратить свой предмет в топливо, вы можете использовать событие `FuelRegistryEvents.BUILD`:

@[code transcludeWith=:::_11](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

## Добавление рецепта создания {#adding-a-basic-crafting-recipe}

<!-- In the future, an entire section on recipes and recipe types should be created. For now, this suffices. -->

Если вы хотите добавить рецепт создания вашего предмета, вам необходимо поместить JSON-файл рецепта в папку `data/<mod id here>/recipe`.

Для получения дополнительной информации о формате рецепта, посетите эти ресурсы:

- [Recipe JSON Generator](https://crafting.thedestruc7i0n.ca/
- [Minecraft Wiki - Recipe JSON](https://minecraft.wiki/w/Recipe#JSON_Format)

## Пользовательские подсказки {#custom-tooltips}

Если вы хотите, чтобы у вашего элемента была настраиваемая подсказка, вам нужно будет создать класс, расширяющий `Item`, и переопределить метод `appendTooltip`.

:::info
В этом примере используется класс `LightningStick`, созданный на странице [Взаимодействия пользовательских элементов](./custom-item-interactions).
:::

@[code lang=java transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/custom/LightningStick.java)

Каждый вызов `add()` добавляет одну строку в подсказку.

![Tooltip Showcase](/assets/develop/items/first_item_3.png)
