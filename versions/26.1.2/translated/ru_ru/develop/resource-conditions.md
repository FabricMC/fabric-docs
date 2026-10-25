---
title: Условная загрузка ресурсов
description: Руководство по настройке условной загрузки данных вашего мода.
authors:
  - cassiancc
resources:
  https://github.com/FabricMC/fabric-api/blob/26.1.2/fabric-data-generation-api-v1/src/testmod/java/net/fabricmc/fabric/test/datagen/DataGeneratorTestEntrypoint.java: Пример мода Fabric API для генерации данных
---

При разработке интеграций с другими модами часто возникает необходимость определить, при каких условиях должны загружаться ресурсы вашего мода. Для этих целей Fabric API предлагает механизм условной загрузки ресурсов (Resource Conditions).

По умолчанию этот API можно использовать с рецептами, достижениями, таблицами добычи, предикатами и модификаторами предметов.

Условную загрузку ресурсов можно добавлять как через [генерацию данных](./data-generation/setup), так и при написании JSON-файлов вручную. Дополнительную информацию о том, как добавлять условную загрузку ресурсов через datagen, см. в документации по генерации данных.

Условия загрузки добавляются в корень JSON-файла.

:::details Рецепт с условием, благодаря которому он загружается только тогда, когда тег заполнен (не пуст).

<<< @/reference/26.1.2/src/main/generated/data/example-mod/recipe/sand.json

:::

## Встроенные условия {#built-in}

Fabric API предоставляет девять встроенных условий, которые вы можете использовать в своем моде. Условия могут выполняться успешно (возвращать `true`) или неуспешно (возвращать `false`).

### Операторы {#operators}

Это стандартные логические (булевы) операторы.

#### Истина (True) {#true}

Всегда возвращает `true`:

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/resource_condition_examples/true.json

#### Ложь (False) {#false}

Всегда возвращает `false`:

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/resource_condition_examples/false.json

#### Не (Not) {#not}

Инвертирует условие загрузки, указанное в `value`. Например, следующее условие вернёт `false`:

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/resource_condition_examples/not.json

#### Или (Or) {#or}

Выполняется успешно, если успешно выполняется хотя бы одно из условий в `values`. Например, следующее условие вернёт `true`:

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/resource_condition_examples/or.json

#### И (And) {#and}

Возвращает `true`, если выполняются абсолютно все условия в `values`. Например, следующее условие вернёт `false`:

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/resource_condition_examples/and.json

### Загружены все моды {#all-mods-loaded}

Возвращает `true`, если все моды в `values` загружены. Например, следующее условие выполнится успешно если, и `example-mod`, и `another-mod` загружены:

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/resource_condition_examples/all_mods_loaded.json

### Загружен любой мод {#any-mods-loaded}

Возвращает `true`, если хотя бы один из модов в `values` загружен. Например, следующее условие выполнится успешно, если загружен либо `example-mod`, либо `another-mod`, либо оба:

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/resource_condition_examples/any_mods_loaded.json

### Теги заполнены (Tags Populated) {#tags-populated}

Возвращает `true`, если указанный регистр содержит все теги из `values`. Например, следующее условие выполнится успешно, если загружен тег предметов `example-mod:smelly_items`:

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/resource_condition_examples/tags_populated.json

### Включённые функции (Features Enabled) {#features-enabled}

Возвращает `true`, если все [feature-флаги](https://minecraft.wiki/w/Experiments#Java_Edition) в `features` включены. Например, следующее условие выполнится успешно, если включены и `minecraft:vanilla`, и `minecraft:redstone_experiments`:

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/resource_condition_examples/features_enabled.json

### Регистр содержит (Registry Contains) {#registry-contains}

Возвращает `true`, если регистр содержит все идентификаторы из `values`. Например, следующее условие выполнится успешно, если в регистре существует идентификатор `minecraft:cobblestone`:

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/resource_condition_examples/registry_contains.json

## Пользовательские условия (Custom Conditions) {#custom-conditions}

:::info ПРЕДВАРИТЕЛЬНЫЕ ТРЕБОВАНИЯ

Прежде чем создавать собственное условное условие загрузки ресурсов, вы должны сначала разобраться в [создании кодеков](./codecs).

:::

Fabric API также предоставляет гибкость для создания собственных условий загрузки ресурсов.

Чтобы продемонстрировать это, мы создадим условие, которое проверяет текущую дату. Его можно использовать для особого поведения в праздники, например на Хэллоуин или 1 апреля.

### Подготовка вашего условия {#preparing-your-condition}

Для простоты мы создадим вспомогательный метод, который создаёт экземпляр вашего условия по имени и [`MapCodec`](./codecs#mapcodec). Поместите этот метод в класс `ModResourceConditions` (или другим именем, которым захотите его назвать).

::: tip

Fabric делает то же самое со своими встроенными условиями. Вы можете обратиться к классу `DefaultResourceConditionTypes`, чтобы увидеть, как это работает.

:::

<<< @/reference/26.1.2/src/main/java/com/example/docs/conditions/ModResourceConditions.java#create

### Создание вашего условия {#creating-your-condition}

Условие загрузки ресурса состоит из трёх частей:

- Конструктор, принимающий значения.
- `MapCodec` для сериализации этих значений.
- Метод `test`, использующий эти значения для определения, должно ли условие считаться успешным.

Мы создадим новый класс для условия ресурса, назвав его `DateMatchesResourceCondition`. Сначала создайте новую запись (`record`), которая принимает `int` для месяца и `int` для дня:

<<< @/reference/26.1.2/src/main/java/com/example/docs/conditions/DateMatchesResourceCondition.java#record

Затем добавьте `MapCodec`, который отражает то, что принимает конструктор:

<<< @/reference/26.1.2/src/main/java/com/example/docs/conditions/DateMatchesResourceCondition.java#codec

:::details Что такое `validate`?

Этот кодек использует метод `.validate`, чтобы убедиться, что указанная дата может существовать, используя логику вспомогательного метода, также называемого `validate`:

<<< @/reference/26.1.2/src/main/java/com/example/docs/conditions/DateMatchesResourceCondition.java#validate

Такая валидация актуальна только для данного конкретного примера.

:::

Далее добавим метод `test`, который проверяет текущую дату. Этот пример основан на логике из самой ванильной игры — класса `SpecialDates`.

<<< @/reference/26.1.2/src/main/java/com/example/docs/conditions/DateMatchesResourceCondition.java#test

### Регистрация вашего условия {#registering-your-condition}

Вернёмся в `ModResourceConditions`. Теперь мы можем зарегистрировать наше условие ресурса:

<<< @/reference/26.1.2/src/main/java/com/example/docs/conditions/ModResourceConditions.java#register

Затем на этот тип условия можно будет ссылаться и из `DateMatchesResourceCondition`:

<<< @/reference/26.1.2/src/main/java/com/example/docs/conditions/DateMatchesResourceCondition.java#type

Не забудьте вызвать `ModResourceConditions.register()` в [инициализаторе вашего мода](./getting-started/project-structure#entrypoints):

<<< @/reference/26.1.2/src/main/java/com/example/docs/conditions/ExampleModResourceConditions.java#init

### Использование вашего условия {#using-your-condition}

Теперь у нас есть условие, которое выполняется успешно, если системная дата совпадает с датой, указанной в условии загрузки ресурса. Например, это условие будет выполняться только 1 апреля:

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/resource_condition_examples/date_matches.json
