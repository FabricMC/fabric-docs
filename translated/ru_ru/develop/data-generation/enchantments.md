---
title: Генерация зачарований
description: Руководство по созданию зачарований с помощью datagen.
authors:
  - CelDaemon
---

<!---->

:::info ТРЕБОВАНИЯ

Сначала убедитесь, что вы [установили datagen](./setup).

:::

## Установка {#setup}

Перед реализацией генератора данных создайте пакет `enchantment` в основном наборе исходных кодов (main source set) и добавьте в него класс `ModEnchantments`. Затем добавьте метод `key` в этот новый класс.

@[code transcludeWith=:::key-helper](@/reference/latest/src/main/java/com/example/docs/enchantment/ModEnchantments.java)

Используйте этот метод для создания `ResourceKey` для вашего зачарования.

@[code transcludeWith=:::register-enchantment](@/reference/latest/src/main/java/com/example/docs/enchantment/ModEnchantments.java)

Теперь все готово к добавлению генератора данных. В пакете datagen создайте класс, который расширяет (extends) `FabricDynamicRegistryProvider`.  В созданном классе добавьте конструктор, соответствующий суперклассу (super), а также реализуйте методы `configure` и `getName`.

@[code transcludeWith=:::provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

Затем добавьте вспомогательный метод `register` в этот созданный класс.

@[code transcludeWith=:::register-helper](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

Теперь добавьте метод `bootstrap`. Здесь мы будем регистрировать зачарования, которые хотим добавить в игру.

@[code transcludeWith=:::bootstrap](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

В вашем классе `DataGeneratorEntrypoint` переопределите (override) метод `buildRegistry` и зарегистрируйте наш метод bootstrap.

@[code transcludeWith=:::datagen-enchantments:bootstrap](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

Наконец, убедитесь, что ваш новый генератор зарегистрирован внутри метода `onInitializeDataGenerator`.

@[code transcludeWith=:::datagen-enchantments:register](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## Создание зачарования {#creating-the-enchantment}

Чтобы создать определение для нашего зачарования, мы будем использовать метод `register` в нашем классе генератора.

Зарегистрируйте зачарование в методе `bootstrap` генератора, используя зачарование, зарегистрированное в `ModEnchantments`.

В данном примере мы будем использовать эффект зачарования, созданный в разделе ["Пользовательские эффекты чар"](../items/custom-enchantment-effects), но вы также можете использовать [ванильные эффекты зачарований](https://minecraft.wiki/w/Enchantment_definition#Effect_components).

@[code transcludeWith=:::register-enchantment](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

Теперь просто запустите генерацию данных, и ваше новое зачарование появится в игре!

## Условия эффектов {#effect-conditions}

Большинство типов эффектов зачарований являются условными эффектами (conditional effects). При добавлении таких эффектов можно передавать условия в вызов метода `withEffect`.

::: info

Обзор доступных типов условий и способов их использования приведен в [классе `Enchantments`](https://mcsrc.dev/#1/1.21.11_unobfuscated/net/minecraft/world/item/enchantment/Enchantments#L126).

:::

@[code transcludeWith=:::effect-conditions](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

## Множественные эффекты {#multiple-effects}

Вызовы `withEffect` можно объединять в цепочку (chain), чтобы добавить несколько эффектов к одному зачарованию. Однако при таком подходе вам придется указывать условия эффекта для каждого вызова отдельно.

Чтобы использовать общие условия и цели для нескольких эффектов, можно объединить их в один эффект с помощью `AllOf`.

@[code transcludeWith=:::multiple-effects](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java)

Обратите внимание, что выбор метода зависит от типа добавляемого эффекта. Например, для `EnchantmentValueEffect` вместо этого требуется использовать `AnyOf.valueEffects`. Для эффектов разных типов все равно понадобятся отдельные вызовы `withEffect`.

## Стол зачарования {#enchanting-table}

Хотя мы указали вес (или шанс) зачарования в его определении, по умолчанию оно не появится в столе зачарования. Чтобы наше зачарование могло предлагаться жителями для торговли и появлялось в столе зачарования, нам нужно добавить его в тег `non_treasure`.

Для этого мы можем создать провайдер тегов. В пакете datagen создайте класс, который расширяет `FabricTagProvider<Enchantment>`. Затем реализуйте конструктор, передав `Registries.ENCHANTMENT` в качестве параметра `registryKey` в конструктор суперкласса (`super`), и создайте метод `addTags`.

@[code transcludeWith=:::provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentTagProvider.java)

Теперь мы можем добавить наше зачарование в тег `EnchantmentTags.NON_TREASURE`, вызвав строитель (builder) внутри метода `addTags`.

@[code transcludeWith=:::non-treasure-tag](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentTagProvider.java)

## Проклятия {#curses}

Проклятия также реализуются с помощью тегов. Мы можем использовать тот же провайдер тегов из предыдущего раздела ["Стол зачарования"](#enchanting-table).

В методе `addTags` просто добавьте ваше зачарование в тег `CURSE`, чтобы пометить его как проклятие.

@[code transcludeWith=:::curse-tag](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentTagProvider.java)
