---
title: Генерація досягнень
description: Посібник із налаштування генерації досягнень за допомогою datagen.
authors:
  - CelDaemon
  - MattiDragon
  - skycatminepokie
  - Spinoscythe
authors-nogithub:
  - jmanc3
  - mcrafterzz
---

<!---->

:::info ПЕРЕДУМОВИ

Спершу переконайтеся, що ви виконали процес [налаштування datagen](./setup).

:::

## Налаштування {#setup}

По-перше, нам потрібно створити свого постачальника. Створіть клас, який розширює `FabricAdvancementProvider` та заповніть базові методи:

@[code lang=java transcludeWith=:::datagen-advancements:provider-start](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java)

Щоб завершити налаштування, додайте цього постачальника до своєї `DataGeneratorEntrypoint` у методі `onInitializeDataGenerator`.

@[code lang=java transcludeWith=:::datagen-advancements:register](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## Структура досягнення {#advancement-structure}

Досягнення складається з кількох різних компонентів. Разом із вимогами, які називаються «критерієм», він може мати:

- `DisplayInfo`, який повідомляє грі, як показувати до досягнення гравцям,
- `AdvancementRequirements`, які є списками списків критеріїв, які вимагають заповнення принаймні одного критерію з кожного підсписку,
- `AdvancementRewards`, які гравець отримує за виконання досягнення.
- `Strategy`, який повідомляє досягненню, як обробляти кілька критеріїв, і
- Батьківський `Advancement`, який організовує ієрархію, яку ви бачите на екрані «Досягнення».

## Прості досягнення {#simple-advancements}

Ось просте досягнення для отримання ґрунту:

@[code lang=java transcludeWith=:::datagen-advancements:simple-advancement](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java)

::: warning

Під час створення записів про досягнення пам’ятайте, що функція приймає `Identifier` досягнення у форматі `String`!

:::

:::details Вивід JSON

@[code lang=json](@/reference/latest/src/main/generated/data/example-mod/advancement/get_dirt.json)

:::

## Ще один приклад {#one-more-example}

Щоб зрозуміти, додамо ще одне досягнення. Ми попрактикуємося додавати нагороди, використовувати кілька критеріїв і призначати батьківські досягнення:

@[code lang=java transcludeWith=:::datagen-advancements:second-advancement](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java)

## Власні критерії {#custom-criteria}

::: warning

У той час як datagen може бути на стороні клієнта, `Criterion`s і `Predicate`s знаходяться в основному вихідному наборі (обидві сторони), оскільки сервер повинен ініціювати та оцінювати їх.

:::

### Визначення {#definitions}

**criterion** (у множині: criteria) — це те, що гравець може зробити (або що може статися з гравцем), що може бути зараховано для досягнення. У грі є багато [критеріїв](https://minecraft.wiki/w/Advancement_definition#List_of_triggers), які можна знайти в пакеті `net.minecraft.advancements.criterion`. Як правило, вам знадобиться новий критерій, лише якщо ви запровадите в гру спеціальну механіку.

**Умови** оцінюються за критеріями. Критерій зараховується, лише якщо виконуються всі відповідні умови. Умови зазвичай виражаються присудком.

**Присудок** — це те, що приймає значення та повертає `boolean`. Наприклад, `Predicate<Item>` може повернути `true`, якщо предмет є діамантом, тоді як `Predicate<LivingEntity>` може повернути `true`, якщо сутність не є ворожою до селян.

### Створення власних критеріїв {#creating-custom-criteria}

По-перше, нам знадобиться нова механіка для впровадження. Скажімо гравцеві, який інструмент він використовував щоразу, коли ламав блок.

@[code lang=java transcludeWith=:::datagen-advancements:entrypoint](@/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java)

Зауважте, що цей код дійсно поганий. `HashMap` не зберігається ніде постійно, тому він буде скидатися кожного разу, коли гра перезапускається. Це просто для того, щоб похизуватися `критеріями`. Почніть гру та спробуйте!

Далі створимо наш спеціальний критерій, `UseToolCriterion`. Йому знадобиться власний клас `Conditions`, тому ми створимо їх обидва одночасно:

@[code lang=java transcludeWith=:::datagen-advancements:criterion-base](@/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java)

Вау, це багато! Розберімо це.

- `UseToolCriterion` – це `SimpleCriterionTrigger`, до якого можуть застосовуватися `Conditions`.
- `Conditions` мають поле `playerPredicate`. Усі `Conditions` повинні мати присудок гравця (технічно `LootContextPredicate`).
- `Conditions` також мають `CODEC`. Цей `кодек` є просто кодеком для його одного поля, `playerPredicate`, з додатковими інструкціями для перетворення між ними (`xmap`).

::: info

Щоб дізнатися більше про кодеки, перегляньте сторінку [кодеків](../codecs).

:::

Нам знадобиться спосіб перевірити, чи виконуються умови. Нумо додаймо допоміжний метод до `Conditions`:

@[code lang=java transcludeWith=:::datagen-advancements:conditions-test](@/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java)

Тепер, коли ми маємо критерій і його умови, нам потрібен спосіб його запустити. Додайте метод запуску до `UseToolCriterion`:

@[code lang=java transcludeWith=:::datagen-advancements:criterion-trigger](@/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java)

Майже готово! Далі нам потрібен екземпляр нашого критерію для роботи. Помістімо його в новий клас під назвою `ModCriteria`.

@[code lang=java transcludeWith=:::datagen-advancements:mod-criteria](@/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java)

Щоб переконатися, що наші критерії ініціалізуються в потрібний час, додайте порожній метод `init`:

@[code lang=java transcludeWith=:::datagen-advancements:mod-criteria-init](@/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java)

І викличте це у своєму ініціалізаторі мода:

@[code lang=java transcludeWith=:::datagen-advancements:call-init](@/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java)

Нарешті, нам потрібно запустити наші критерії. Додайте це туди, де ми надіслали повідомлення гравцеві в головному класі мода.

@[code lang=java transcludeWith=:::datagen-advancements:trigger-criterion](@/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java)

Ваш новий блискучий критерій готовий до використання! Нумо додамо до нашого постачальника:

@[code lang=java transcludeWith=:::datagen-advancements:custom-criteria-advancement](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java)

Запустіть завдання datagen ще раз, і ви отримаєте нове досягнення, з яким можна грати!

## Умови з параметрами {#conditions-with-parameters}

Це все добре, але що, якщо ми хочемо надати досягнення лише після виконання певної роботи 5 разів? А чому б не ще один у 10 разів? Для цього нам потрібно надати умові параметр. Ви можете залишитися з `UseToolCriterion`, або ви можете слідувати разом із новим `ParameterizedUseToolCriterion`. На практиці ви повинні мати лише параметризований, але ми збережемо обидва для цього підручника.

Попрацюймо знизу вгору. Нам потрібно буде перевірити, чи виконуються вимоги, тому відредагуємо наш метод `Conditions#requirementsMet`:

@[code lang=java transcludeWith=:::datagen-advancements:new-requirements-met](@/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java)

`requiredTimes` не існує, тому зробіть його параметром `Conditions`:

@[code lang=java transcludeWith=:::datagen-advancements:new-parameter](@/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java)

Тепер наш кодек неправильний. Напишімо новий кодек для нових змін:

@[code lang=java transcludeWith=:::datagen-advancements:new-codec](@/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java)

Рухаючись далі, тепер нам потрібно виправити наш метод `trigger`:

@[code lang=java transcludeWith=:::datagen-advancements:new-trigger](@/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java)

Якщо ви створили новий критерій, нам потрібно додати його до `ModCriteria`

@[code lang=java transcludeWith=:::datagen-advancements:new-mod-criteria](@/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java)

І назвіть це в нашому головному класі, там, де й старий:

@[code lang=java transcludeWith=:::datagen-advancements:trigger-new-criterion](@/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java)

Додайте досягнення до свого постачальника:

@[code lang=java transcludeWith=:::datagen-advancements:new-custom-criteria-advancement](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java)

Запустіть datagen ще раз, і ви нарешті закінчили!
