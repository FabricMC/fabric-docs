---
title: Генерация переводов
description: Руководство по настройке генерации переводов с помощью datagen.
authors:
  - CelDaemon
  - IMB11
  - MattiDragon
  - skycatminepokie
  - Spinoscythe
authors-nogithub:
  - jmanc3
  - mcrafterzz
  - sjk1949
---

<!---->

:::info ТРЕБОВАНИЯ

Сначала убедитесь, что вы [установили datagen](./setup).

:::

## Установка {#setup}

Сначала мы создадим нашего **провайдера**. Помните, что именно провайдеры генерируют данные для нас. Создайте класс, который расширяет `FabricLanguageProvider` и заполните базовые методы:

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModEnglishLangProvider.java#datagen_translations_provider

::: tip

Вам понадобится отдельный провайдер для каждого языка, который вы хотите сгенерировать (например, один `ExampleEnglishLangProvider` и один `ExamplePirateLangProvider`).

:::

Чтобы завершить настройку, добавьте этот провайдер к вашей `DataGeneratorEntrypoint` в методе `onInitializeDataGenerator`.

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_translations_register

## Создание переводов {#creating-translations}

Наряду с созданием необработанных (raw) переводов, переводов на основе идентификаторов (`Identifier`) и их копированием из уже существующего файла (путем передачи пути `Path`), существуют вспомогательные методы для перевода предметов (items), блоков (blocks), тегов (tags), статистик (stats), сущностей (entities), эффектов мобов (mob effects), вкладок творческого режима (creative tabs), атрибутов сущностей (entity attributes) и зачарований (enchantments). Просто вызовите `add` на `translationBuilder` с указанием того, что вы хотите перевести и на что он должен перевести:

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModEnglishLangProvider.java#datagen_translations_build

## Использование переводов {#using-translations}

Генерируемые переводы заменяют многие переводы, добавленные в других руководствах, но вы также можете использовать их везде, где используется объект `Component`. В нашем примере, если бы мы хотели разрешить ресурсным пакетам переводить наше приветствие, мы бы использовали `Component.translatable` вместо `Component.literal`:

```java
ChatComponent chatHud = Minecraft.getInstance().gui.getChat();
chatHud.addMessage(Component.literal("Hello there!")); // [!code --]
chatHud.addMessage(Component.translatable("text.example-mod.greeting")); // [!code ++]
```
