---
title: Умови ресурсів
description: Дізнайтеся, як дозволити вашу моду завантажувати дані з умовами.
authors:
  - cassiancc
resources:
  https://github.com/FabricMC/fabric-api/blob/26.1.2/fabric-data-generation-api-v1/src/testmod/java/net/fabricmc/fabric/test/datagen/DataGeneratorTestEntrypoint.java: Тестовий мод генерації даних API Fabric
---

Під час проєктування інтеграції з іншими модами типовою потребою є спосіб визначення часу завантаження ресурсів вашого мода. З цієї причини Fabric API пропонує умови ресурсів.

Усталено, цей API можна використовувати з рецептами, досягненнями, таблицями здобичі, предикатами та модифікаторами предметів.

Умови ресурсів можна додати через [генерацію даних](./data-generation/setup) або під час написання JSON уручну. Щоб отримати додаткові відомості про те, як додати умови ресурсів за допомогою генерації даних, перегляньте документацію щодо генерації даних.

Умови завантаження додаються в корінь файлу JSON.

:::details Рецепт з умовою, за якою він завантажується лише тоді, коли теґ заповнюється.

<<< @/reference/latest/src/main/generated/data/example-mod/recipe/sand.json

:::

## Убудовані умови {#built-in}

API Fabric надає дев’ять убудованих умов для використання вашим модом.

### Оператори {#operators}

Це стандартні логічні оператори.

#### Так {#true}

Завжди виконується:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/true.json

#### Хиба {#false}

Завжди не вдається:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/false.json

#### Ні {#not}

Інвертує умову завантаження, указану в `value`. Наприклад, наступне не вдасться:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/not.json

#### Або {#or}

Удасться, якщо принаймні одна з умов у `value` виконується. Наприклад, наступне вдасться:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/or.json

#### Також {#and}

Успішно виконується, якщо виконується кожна умова в `value`. Наприклад, наступне не вдасться:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/and.json

### Усі моди завантажені {#all-mods-loaded}

Удасться, якщо завантажено всі моди у `value`. Наприклад, наступне виконується успішно, лише якщо завантажено і `example-mod`, і `another-mod`:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/all_mods_loaded.json

### Будь-який мод завантажено {#any-mods-loaded}

Удасться, якщо завантажено принаймні один із модів у `value`. Наприклад, наступне вдасться, якщо завантажено `example-mod`, або `another-mod`, або обидва:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/any_mods_loaded.json

### Теґи заповнені {#tags-populated}

Удасться, якщо вказаний `реєстр` містить усі теґи в `values`. Наприклад, наступне вдаватиметься, якщо завантажено теґ предметів `example-mod:smelly_items`:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/tags_populated.json

### Увімкнено функції {#features-enabled}

Удасться, якщо всі [прапорці функцій](https://minecraft.wiki/w/Experiments#Java_Edition) у `features` увімкнено. Наприклад, наступне вдаватиметься, якщо ввімкнути `minecraft:vanilla` та `minecraft:redstone_experiments`:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/features_enabled.json

### Реєстр містить {#registry-contains}

Удасться, якщо реєстр містить усі ідентифікатори в `values`. Наприклад, наступне удаватиметься, якщо `minecraft:cobblestone` існує в реєстрі:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/registry_contains.json

## Власні умови {#custom-conditions}

:::info ПЕРЕДУМОВИ

Ви повинні спочатку зрозуміти, [як створити кодек](./codecs), перш ніж створювати власну умову ресурсів.

:::

Fabric API також забезпечує гнучкість створення власних умов ресурсів.

Щоб продемонструвати це, ми створимо умову, яка перевіряє поточну дату. Це можна використовувати для особливої ​​поведінки на свята, як-от Гелловін або Перше квітня.

### Підготовка вашої умови {#preparing-your-condition}

Для простоти ми створимо допоміжний метод, який створить екземпляр вашої умови ресурсів з назвою та [`MapCodec`](./codecs#mapcodec). Вам слід помістити цей метод у клас під назвою `ModResourceConditions` (або як ви хочете його назвати).

::: tip

Fabric робить те саме зі своїми вбудованими умовами; ви можете звернутися до класу `DefaultResourceConditionTypes`, щоб побачити це в дії.

:::

<<< @/reference/latest/src/main/java/com/example/docs/conditions/ModResourceConditions.java#create

### Створення вашої умови {#creating-your-condition}

Умова ресурсів складається з трьох частин:

- Конструктор, який приймає значення.
- `MapCodec` для серіалізації цих значень.
- Метод `test`, який використовує значення, щоб визначити, чи має виконуватися умова.

Ми створимо новий клас для умови ресурсів під назвою `DateMatchesResourceCondition`. Спочатку створіть новий `record`, який приймає `int` для місяця та `int` для дня:

<<< @/reference/latest/src/main/java/com/example/docs/conditions/DateMatchesResourceCondition.java#record

Далі додайте `MapCodec`, який показує те, що приймає конструктор:

<<< @/reference/latest/src/main/java/com/example/docs/conditions/DateMatchesResourceCondition.java#codec

:::details Що є `validate`?

Цей кодек використовує метод `.validate`, щоб гарантувати наявність наданої дати, використовуючи логіку допоміжного методу, який також називається `validate`:

<<< @/reference/latest/src/main/java/com/example/docs/conditions/DateMatchesResourceCondition.java#validate

Це стосується лише цього прикладу.

:::

Далі ми додамо метод `test`, який перевіряє поточну дату. Цей приклад базується на логіці самої гри, у `SpecialDates`.

<<< @/reference/latest/src/main/java/com/example/docs/conditions/DateMatchesResourceCondition.java#test

### Реєстрація вашої умови {#registering-your-condition}

Повернувшись до `ModResourceConditions`, тепер ми можемо зареєструвати нашу умову ресурсів:

<<< @/reference/latest/src/main/java/com/example/docs/conditions/ModResourceConditions.java#register

На цей тип умови також можна посилатися з `DateMatchesResourceCondition`:

<<< @/reference/latest/src/main/java/com/example/docs/conditions/DateMatchesResourceCondition.java#type

Обов’язково викликайте `ModResourceConditions.register` у своєму [ініціалізаторі мода](./getting-started/project-structure#entrypoints):

<<< @/reference/latest/src/main/java/com/example/docs/conditions/ExampleModResourceConditions.java#init

### Використання вашої умови {#using-your-condition}

Тепер у нас є умова, яка виконується успішно, якщо системна дата збігається з датою, зазначеною в умові ресурсів. Наприклад, ця умова буде виконана лише Першого квітня:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/date_matches.json
