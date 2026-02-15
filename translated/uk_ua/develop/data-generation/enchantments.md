---
title: Генерація зачарувань
description: Дізнайтеся як генерувати зачарування через datagen.
authors:
  - CelDaemon
---

<!---->

:::info ПЕРЕДУМОВИ

Спершу переконайтеся, що ви виконали процес [налаштування datagen](./setup).

:::

## Налаштування {#setup}

Перед реалізацією генератора створіть пакет `enchantment` в основному вихідному наборі та додайте до нього клас `ModEnchantments`. Потім додайте метод `key` до цього нового класу.

@[code transcludeWith=:::key-helper](@/reference/latest/src/main/java/com/example/docs/enchantment/ModEnchantments.java)

Використовуйте цей метод, аби створити `ResourceKey` для свого зачарування.

@[code transcludeWith=:::register-enchantment](@/reference/latest/src/main/java/com/example/docs/enchantment/ModEnchantments.java)

Тепер ми готові додати генератор. У пакеті datagen створіть клас, який розширює `FabricDynamicRegistryProvider`. До цього щойно створеного класу додайте конструктор, який відповідає `super`, і реалізуйте методи `configure` і `getName`.

@[code transcludeWith=:::provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

Потім додайте допоміжний метод `register` до новоствореного класу.

@[code transcludeWith=:::register-helper](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

Тепер додайте метод `bootstrap`. Тут ми будемо реєструвати зачарування, які хочемо додати до гри.

@[code transcludeWith=:::bootstrap](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

У вашій `DataGeneratorEntrypoint` перевизначте метод `buildRegistry` і зареєструйте наш початковий метод.

@[code transcludeWith=:::datagen-enchantments:bootstrap](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

Нарешті переконайтеся, що ваш новий генератор зареєстровано в методі `onInitializeDataGenerator`.

@[code transcludeWith=:::datagen-enchantments:register](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## Створення зачарування {#creating-the-enchantment}

Щоб створити визначення для нашого спеціального зачарування, ми використаємо метод `register` у нашому класі генератора.

Зареєструйте своє зачарування в методі `bootstrap` генератора, використовуючи зачарування, зареєстроване в `ModEnchantments`.

У цьому прикладі ми будемо використовувати ефект зачарування, створений у [власних ефектах зачарування](../items/custom-enchantment-effects), але ви також можете використовувати [стандартні ефекти зачарування](https://minecraft.wiki/w/Enchantment_definition#Effect_components).

@[code transcludeWith=:::register-enchantment](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

Тепер просто запустіть генерацію даних, і ваше нове зачарування стане доступним у грі!

## Умови ефекту {#effect-conditions}

Більшість типів ефектів зачарування є умовними. Додаючи ці ефекти, можна передати умови виклику `withEffect`.

::: info

Щоб отримати огляд доступних типів умов та їх використання, перегляньте [клас `Enchantments`](https://mcsrc.dev/#1/1.21.11_unobfuscated/net/minecraft/world/item/enchantment/Enchantments#L126).

:::

@[code transcludeWith=:::effect-conditions](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

## Кілька ефектів {#multiple-effects}

`withEffect` можна об'єднати, щоб додати кілька ефектів зачарування до одного зачарування. Однак цей метод вимагає від вас вказати умови ефекту для кожного ефекту.

Щоб натомість поділитися визначеними умовами та цілями між кількома ефектами, можна використати `AllOf`, щоб об’єднати їх в один ефект.

@[code transcludeWith=:::multiple-effects](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

Зауважте, що спосіб використання залежить від типу ефекту, який додається. Наприклад, `EnchantmentValueEffect` вимагає натомість `AnyOf.valueEffects`. Різні типи ефектів все ще потребують додаткових викликів `withEffect`.

## Стіл зачарування {#enchanting-table}

Хоча ми вказали вагу зачарування (або ймовірність) у нашому визначенні зачарування, воно не показуватиметься в столі зачарувань усталено. Аби нашими зачаруванням могли торгувати селяни та з’являтися в столі зачарування, нам потрібно додати їх до теґу `non_treasure`.

Для цього ми можемо створити постачальник теґів. Створіть клас, який розширює `FabricTagProvider<Enchantment>` в пакеті `datagen`. Потім запровадьте конструктор із `Registries.ENCHANTMENT` як параметр `registryKey` до `super` і створіть метод `addTags`.

@[code transcludeWith=:::provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentTagProvider.java)

Тепер ми можемо додати наше зачарування до `EnchantmentTags.NON_TREASURE`, викликавши конструктор із методу `addTags`.

@[code transcludeWith=:::non-treasure-tag](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentTagProvider.java)

## Прокляття {#curses}

Прокляття також реалізуються за допомогою теґів. Ми можемо використати постачальник теґів із [розділу стола зачарування](#enchanting-table).

У методі `addTags` просто додайте своє зачарування до теґу `CURSE`, щоб позначити його як прокляття.

@[code transcludeWith=:::curse-tag](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentTagProvider.java)
