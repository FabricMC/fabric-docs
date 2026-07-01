---
title: Генерація зачарувань
description: Дізнайтеся, як генерувати зачарування через генерацію даних.
authors:
  - CelDaemon
---

<!---->

:::info ПЕРЕДУМОВИ

Спершу переконайтеся, що ви виконали процес [налаштування генерації даних](./setup).

:::

## Налаштування {#setup}

Перед реалізацією генератора створіть пакет `enchantment` в основному початковому наборі та додайте до нього клас `ModEnchantments`. Потім додайте метод `key` до цього нового класу.

<<< @/reference/26.1.2/src/main/java/com/example/docs/enchantment/ModEnchantments.java#key_helper

Використовуйте цей метод, щоб створити `ResourceKey` для свого зачарування.

<<< @/reference/26.1.2/src/main/java/com/example/docs/enchantment/ModEnchantments.java#register_enchantment

Тепер ми готові додати генератор. У пакеті генерації даних створіть клас, який розширює `FabricDynamicRegistryProvider`. До цього щойно створеного класу додайте конструктор, який відповідає `super`, і реалізуйте методи `configure` і `getName`.

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java#provider

Потім додайте допоміжний метод `register` до новоствореного класу.

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java#register_helper

Тепер додайте метод `bootstrap`. Тут ми будемо реєструвати зачарування, які хочемо додати до гри.

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java#bootstrap

У вашій `DataGeneratorEntrypoint` перевизначте метод `buildRegistry` і зареєструйте наш початковий метод.

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_enchantments_bootstrap

Нарешті переконайтеся, що ваш новий генератор зареєстровано в методі `onInitializeDataGenerator`.

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_enchantments_register

## Створення зачарування {#creating-the-enchantment}

Щоб створити визначення для нашого спеціального зачарування, ми використаємо метод `register` у нашому класі генератора.

Зареєструйте своє зачарування в методі `bootstrap` генератора, використовуючи зачарування, зареєстроване в `ModEnchantments`.

У цьому прикладі ми будемо використовувати ефект зачарування, створений у [власних ефектах зачарування](../items/custom-enchantment-effects), але ви також можете використовувати [стандартні ефекти зачарування](https://minecraft.wiki/w/Enchantment_definition#Effect_components).

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java#register_enchantment

Тепер просто запустіть генерацію даних, і ваше нове зачарування стане доступним у грі!

## Умови ефекту {#effect-conditions}

Більшість типів ефектів зачарування є умовними. Додаючи ці ефекти, можна передати умови виклику `withEffect`.

::: info

Щоб отримати огляд доступних типів умов та їх використання, перегляньте [клас `Enchantments`](https://mcsrc.dev/#1/1.21.11_unobfuscated/net/minecraft/world/item/enchantment/Enchantments#L126).

:::

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java#effect_conditions

## Кілька ефектів {#multiple-effects}

`withEffect` можна об'єднати, щоб додати кілька ефектів зачарування до одного зачарування. Однак цей метод вимагає від вас вказати умови ефекту для кожного ефекту.

Щоб натомість поділитися визначеними умовами та цілями між кількома ефектами, можна використати `AllOf`, щоб об’єднати їх в один ефект.

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java#multiple_effects

Зауважте, що спосіб використання залежить від типу ефекту, який додається. Наприклад, `EnchantmentValueEffect` вимагає натомість `AnyOf.valueEffects`. Різні типи ефектів все ще потребують додаткових викликів `withEffect`.

## Стіл зачарування {#enchanting-table}

Хоча ми вказали вагу зачарування (або ймовірність) у нашому визначенні зачарування, воно не показуватиметься в столі зачарувань усталено. Щоб нашими зачаруванням могли торгувати селяни та з’являтися в столі зачарування, нам потрібно додати їх до теґу `non_treasure`.

Для цього ми можемо створити постачальник теґів. Створіть клас, який розширює `FabricTagProvider<Enchantment>` в пакеті `datagen`. Потім запровадьте конструктор із `Registries.ENCHANTMENT` як параметр `registryKey` до `super` і створіть метод `addTags`.

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModEnchantmentTagProvider.java#provider

Тепер ми можемо додати наше зачарування до `EnchantmentTags.NON_TREASURE`, викликавши конструктор із методу `addTags`.

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModEnchantmentTagProvider.java#non_treasure_tag

## Прокляття {#curses}

Прокляття також реалізуються за допомогою теґів. Ми можемо використати постачальник теґів із [розділу стола зачарування](#enchanting-table).

У методі `addTags` просто додайте своє зачарування до теґу `CURSE`, щоб позначити його як прокляття.

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModEnchantmentTagProvider.java#curse_tag
