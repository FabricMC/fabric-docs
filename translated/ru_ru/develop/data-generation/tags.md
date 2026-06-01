---
title: Генератор Тегов
description: Руководство по настройке генерации тегов с помощью datagen.
authors:
  - CelDaemon
  - IMB11
  - skycatminepokie
  - Spinoscythe
authors-nogithub:
  - mcrafterzz
---

<!---->

:::info ТРЕБОВАНИЯ

Сначала убедитесь, что вы завершили процесс [datagen setup](./setup).

:::

## Настройка{#setup}

Здесь мы покажем, как создавать теги `Item`, но тот же принцип применим и для других вещей.

Fabric предоставляет несколько вспомогательных провайдеров тегов, включая один для элементов; `FabricTagsProvider.ItemTagsProvider`. Мы будем использовать этот вспомогательный класс в данном примере.

Вы можете создать свой собственный класс, который расширяет `FabricTagsProvider<T>`, где `T` - это тип вещи, для которой вы хотите предоставить тег. Он будет вашим **провайдером**.

Позвольте вашей IDE заполнить необходимый код, а затем замените параметр конструктора `resourceKey` на `ResourceKey` для вашего типа:

@[code lang=java transcludeWith=:::datagen-tags:provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java)

::: tip

Вам понадобится отдельный провайдер для каждого типа тегов (например, один `FabricTagsProvider<EntityType<?>>` и один `FabricTagsProvider<Item>`).

:::

Чтобы завершить настройку, добавьте этот провайдер к вашей `DataGeneratorEntrypoint` в методе `onInitializeDataGenerator`.

@[code lang=java transcludeWith=:::datagen-tags:register](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## Создание тега {#creating-a-tag}

Теперь, когда вы создали провайдера, давайте добавим к нему тег. Сначала создайте `TagKey<T>`:

@[code lang=java transcludeWith=:::datagen-tags:tag-key](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java)

Затем вызовите `valueLookupBuilder` в методе `configure` вашего провайдера. Отсюда можно добавить отдельные элементы, добавить другие теги или сделать так, чтобы этот тег заменял уже существующие.

Если вы хотите добавить тег, используйте `addOptionalTag`, так как содержимое тега может быть не загружено во время датагена. Если вы уверены, что тег загружен, вызовите `addTag`.

Чтобы принудительно добавить тег и игнорировать сломанный формат, используйте `forceAddTag`.

@[code lang=java transcludeWith=:::datagen-tags:build](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java)
