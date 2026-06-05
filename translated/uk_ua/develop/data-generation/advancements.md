---
title: Генерація досягнень
description: Посібник із налаштування генерації досягнень за допомогою генерації даних.
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

Спершу переконайтеся, що ви виконали процес [налаштування генерації даних](./setup).

:::

## Налаштування {#setup}

По-перше, нам потрібно створити свого постачальника. Створіть клас, який розширює `FabricAdvancementProvider` та заповніть базові методи:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen_advancements_provider_start

Щоб завершити налаштування, додайте цього постачальника до своєї `DataGeneratorEntrypoint` у методі `onInitializeDataGenerator`.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_advancements_register

## Структура досягнення {#advancement-structure}

Досягнення складається з кількох різних компонентів. Разом із вимогами, які називаються «критерієм», він може мати:

- `DisplayInfo`, який повідомляє грі, як показувати до досягнення гравцям,
- `AdvancementRequirements`, які є списками списків критеріїв, які вимагають заповнення принаймні одного критерію з кожного підсписку,
- `AdvancementRewards`, які гравець отримує за виконання досягнення.
- `Strategy`, який повідомляє досягненню, як обробляти кілька критеріїв, і
- Батьківський `Advancement`, який організовує ієрархію, яку ви бачите на екрані «Досягнення».

## Прості досягнення {#simple-advancements}

Ось просте досягнення для отримання ґрунту:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen_advancements_simple_advancement

:::details Вивід JSON

<<< @/reference/latest/src/main/generated/data/example-mod/advancement/get_dirt.json

:::

## Батьківські {#parents}

Щоб створити або розширити дерево досягнень, ми можемо встановити батьківське досягнення для нашого досягнення. Для цього викличте `Advancement.Builder#parent(...)` і передайте посилання на батьківське досягнення.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#reference_parent

Якщо немає прямого посилання на батьківське досягнення (наприклад, використання стандартного досягнення як батьківського), заповнювач можна створити за допомогою ідентифікатора.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#placeholder_parent

Тепер ваші досягнення мають показуватися у вигляді дерева в меню досягнень.

![Дерево досягнень](/assets/develop/data-generation/advancement_tree.png)

## Кілька критеріїв {#multiple-criteria}

Щоб мати більш розширені умови в наших досягненнях, ми можемо викликати `Advancement.Builder#addCriteria(...)` кілька разів із додатковими критеріями.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#multiple_criteria

Усталено всі критерії мають бути виконані для завершення досягнення. Ми можемо змінити цю поведінку, запропонувавши іншу стратегію.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#requirements_strategy

## Винагороди {#rewards}

Ми можемо додавати винагороди до наших досягнень, які будуть надані, коли гравець завершить досягнення. Ми можемо зробити це, викликавши `Advancement.Builder#rewards(...)` з винагородами, які ми хочемо додати.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#experience_reward

Існує кілька інших типів винагород:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#reward_types

## Власні критерії {#custom-criteria}

::: warning

У той час як генерація даних може бути на стороні клієнта, `Criterion` і `Predicate` знаходяться в основному початковому наборі (обидві сторони), оскільки сервер повинен ініціювати та оцінювати їх.

:::

### Визначення {#definitions}

**criterion** (у множині: criteria) — це те, що гравець може зробити (або що може статися з гравцем), що може бути зараховано для досягнення. У грі є багато [критеріїв](https://minecraft.wiki/w/Advancement_definition#List_of_triggers), які можна знайти в пакеті `net.minecraft.advancements.criterion`. Як правило, вам знадобиться новий критерій, лише якщо ви запровадите в гру спеціальну механіку.

**Умови** оцінюються за критеріями. Критерій зараховується, лише якщо виконуються всі відповідні умови. Умови зазвичай виражаються присудком.

**Предикат** — це те, що приймає значення та повертає `boolean`. Наприклад, `Predicate<Item>` може повернути `true`, якщо предмет є діамантом, тоді як `Predicate<LivingEntity>` може повернути `true`, якщо сутність не є ворожою до селян.

### Створення власних критеріїв {#creating-custom-criteria}

По-перше, нам знадобиться нова механіка для впровадження. Скажімо гравцеві, який інструмент він використовував щоразу, коли ламав блок.

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java#datagen_advancements_entrypoint

Зауважте, що цей код дійсно поганий. `HashMap` не зберігається ніде постійно, тому він буде скидатися кожного разу, коли гра перезапускається. Це просто для того, щоб похизуватися `критеріями`. Почніть гру та спробуйте!

Далі створимо наш спеціальний критерій, `UseToolCriterion`. Йому знадобиться власний клас `Conditions`, тому ми створимо їх обидва одночасно:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java#datagen_advancements_criterion_base

Вау, це багато! Розберімо це.

- `UseToolCriterion` – це `SimpleCriterionTrigger`, до якого можуть застосовуватися `Conditions`.
- `Conditions` мають поле `playerPredicate`. Усі `Conditions` повинні мати предикат гравця (технічно `LootContextPredicate`).
- `Conditions` також мають `CODEC`. Цей `Codec` є просто кодеком для його одного поля, `playerPredicate`, з додатковими інструкціями для перетворення між ними (`xmap`).

::: info

Щоб дізнатися більше про кодеки, перегляньте сторінку [кодеків](../codecs).

:::

Нам знадобиться спосіб перевірити, чи виконуються умови. Нумо додаймо допоміжний метод до `Conditions`:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java#datagen_advancements_conditions_test

Тепер, коли ми маємо критерій і його умови, нам потрібен спосіб його запустити. Додайте метод запуску до `UseToolCriterion`:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java#datagen_advancements_criterion_trigger

Майже готово! Далі нам потрібен екземпляр нашого критерію для роботи. Помістімо його в новий клас під назвою `ModCriteria`.

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java#datagen_advancements_mod_criteria

Щоб переконатися, що наші критерії ініціалізуються в потрібний час, додайте порожній метод `init`:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java#datagen_advancements_mod_criteria_init

І викличте це у своєму ініціалізаторі мода:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java#datagen_advancements_call_init

Нарешті, нам потрібно запустити наші критерії. Додайте це туди, де ми надіслали повідомлення гравцеві в головному класі мода.

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java#datagen_advancements_trigger_criterion

Ваш новий блискучий критерій готовий до використання! Нумо додамо до нашого постачальника:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen_advancements_custom_criteria_advancement

Запустіть завдання генерації даних ще раз, і ви отримаєте нове досягнення, з яким можна грати!

## Умови з параметрами {#conditions-with-parameters}

Це все добре, але що, якщо ми хочемо надати досягнення лише після виконання певної роботи 5 разів? А чому б не ще один у 10 разів? Для цього нам потрібно надати умові параметр. Ви можете залишитися з `UseToolCriterion`, або ви можете слідувати разом із новим `ParameterizedUseToolCriterion`. На практиці ви повинні мати лише параметризований, але ми збережемо обидва для цього посібника.

Попрацюймо знизу вгору. Нам потрібно буде перевірити, чи виконуються вимоги, тому відредагуємо наш метод `Conditions#requirementsMet`:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java#datagen_advancements_new_requirements_met

`requiredTimes` не існує, тому зробіть його параметром `Conditions`:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java#datagen_advancements_new_parameter

Тепер наш кодек неправильний. Напишімо новий кодек для нових змін:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java#datagen_advancements_new_codec

Рухаючись далі, тепер нам потрібно виправити наш метод `trigger`:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java#datagen_advancements_new_trigger

Якщо ви створили новий критерій, нам потрібно додати його до `ModCriteria`

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java#datagen_advancements_new_mod_criteria

І назвіть це в нашому головному класі, там, де й старий:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java#datagen_advancements_trigger_new_criterion

Додайте досягнення до свого постачальника:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen_advancements_new_custom_criteria_advancement

Запустіть генерацію даних знову, і ви нарешті закінчили!

## Умови ресурсів {#resource-conditions}

Щоб застосувати [умову ресурсів](../resource-conditions) до досягнення, згенерованого на основі даних, оберніть споживача за допомогою `withConditions` і вкажіть будь-які умови ресурсів, які ви хочете застосувати. Тоді це створить досягнення із застосованими умовами ресурсів:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen_advancements_conditions
