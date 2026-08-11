---
title: Создание достижений
description: Руководство по настройке генерации достижений через datagen.
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

:::info ТРЕБОВАНИЯ

Сначала убедитесь, что вы [установили datagen](./setup).

:::

## Установка {#setup}

Для начала нам нужно создать провайдера. Создайте класс, который расширяет `FabricAdvancementProvider` и заполните базовые методы:

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen_advancements_provider_start

Чтобы завершить настройку, добавьте этот провайдер к вашей `DataGeneratorEntrypoint` в методе `onInitializeDataGenerator`.

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_advancements_register

## Структура достижений {#advancement-structure}

Достижение состоит из нескольких различных компонентов. Помимо требований, называемых "критериями" (criterion), оно может содержать:

- `DisplayInfo` — указывает игре, как именно отображать достижение игрокам.
- `AdvancementRequirements` — списки списков критериев; для выполнения достижения требуется завершить как минимум один критерий из каждого подсписка.
- `AdvancementRewards` — награды, которые игрок получает за выполнение достижения.
- `Strategy` — стратегия, которая определяет, как достижение обрабатывает несколько критериев.
- `Parent` — родительское достижение. Организует иерархию, которую можно увидеть на экране "Достижения".

## Простые достижения {#simple-advancements}

Вот пример простого достижения за получение блока земли:

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen_advancements_simple_advancement

:::details Вывод JSON

<<< @/reference/26.1.2/src/main/generated/data/example-mod/advancement/get_dirt.json

:::

## Родительские достижения (Parents) {#parents}

Чтобы создать или расширить дерево достижений, мы можем указать родителя для нашего достижения. Для этого вызовите метод `Advancement.Builder#parent(...)` и передайте ссылку на родительское достижение:

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#reference_parent

Если прямой ссылки на родительское достижения нет (например, в качестве родителя используется ванильное достижение), можно создать плейсхолдер с помощью static метода класса `Identifier`.

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#placeholder_parent

Теперь ваши достижения будут отображаться в виде древовидной структуры в меню достижений.

![Дерево достижений](/assets/develop/data-generation/advancement_tree.png)

## Множественные критерии (Multiple Criteria) {#multiple-criteria}

Чтобы использовать более сложные условия в наших достижениях, мы можем вызывать метод `Advancement.Builder#addCriteria(...)` несколько раз, добавляя дополнительные критерии:

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#multiple_criteria

По умолчанию для выполнения достижения должны быть выполнены все указанные критерии. Мы можем изменить это поведение, задав другую стратегию требований (`AdvancementRequirements.Strategy`):

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#requirements_strategy

## Награды (Rewards) {#rewards}

Мы можем привязывать награды к нашим достижениям, которые будут выдаваться игроку сразу после их выполнения. Для этого нужно вызвать метод `Advancement.Builder#rewards(...)` и передать туда награды, которые мы хотим добавить:

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#experience_reward

Также доступны и другие типы наград:

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#reward_types

## Пользовательские критерии (Custom Criteria) {#custom-criteria}

::: warning

Хотя генерация данных (datagen) может выполняться на стороне клиента, сами классы критериев (`Criterion`) и предикатов (`Predicate`) должны находиться в основном наборе исходного кода (main source set), доступном для обеих сторон, поскольку именно сервер должен отслеживать и проверять их выполнение.

:::

### Определения {#definitions}

**Критерий** (Criterion / мн. ч. Criteria) — это действие, которое может совершить игрок (или событие, которое может с ним произойти), засчитывающееся для прогресса достижения. В игре уже есть много [стандартных критериев](https://minecraft.wiki/w/Advancement_definition#List_of_triggers), которые находятся в пакете `net.minecraft.advancements.criterion`. Как правило, новый критерий нужен только в том случае, если вы добавляете в игру свою уникальную механику.

**Условия** (Conditions) — это параметры, которые проверяются критерием. Критерий засчитывается только в том случае, если выполнены все связанные условия. Обычно условия выражаются с помощью предикатов.

**Предикат** (Predicate) — это функциональный интерфейс, принимающий входное значение и возвращающий результат типа boolean(логическое значение). Например, предикат `Predicate<Item>` может возвращать true, если предмет является алмазом, а предикат `Predicate<LivingEntity>` — если существо не агрессивно по отношению к деревенским жителям.

### Создание пользовательских критериев {#creating-custom-criteria}

Во-первых, нам понадобится новая механика. Давайте сообщать игроку, какой инструмент он использовал, каждый раз, когда он разбивает блок.

<<< @/reference/26.1.2/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java#datagen_advancements_entrypoint

Обратите внимание, что этот код очень плохой. Так как `HashMap` не хранится в постоянном месте, она будет сбрасываться при каждом перезапуске игры. Это сделано исключительно для демонстрации критериев (`Criterions`). Начните игру и попробуйте!

Далее создадим наш пользовательский критерий `UseToolCriterion`. Для него понадобится собственный класс `Conditions`, поэтому мы создадим их оба сразу:

<<< @/reference/26.1.2/src/main/java/com/example/docs/advancement/UseToolCriterion.java#datagen_advancements_criterion_base

Ух, как много всего! Давайте во всём разберёмся.

- `UseToolCriterion` — это класс-наследник `SimpleCriterionTrigger`, к которому могут применяться условия `Conditions`.
- В `Conditions` есть поле `playerPredicate`. Все условия `Conditions` должны содержать предикат игрока (технически — `LootContextPredicate`).
- У `Conditions` также есть `CODEC`. Этот `Codec` - просто кодек для одного поля, `playerPredicate`, с дополнительными инструкциями для преобразования между ними (`xmap`).

::: info

Чтобы узнать больше о кодеках, см. страницу [Codecs](../codecs).

:::

Нам понадобится способ проверить, выполнены ли условия. Давайте добавим вспомогательный метод в `Conditions`:

<<< @/reference/26.1.2/src/main/java/com/example/docs/advancement/UseToolCriterion.java#datagen_advancements_conditions_test

Теперь, когда у нас есть критерий и его условия, нам нужно найти способ запустить его. Добавьте метод триггера к `UseToolCriterion`:

<<< @/reference/26.1.2/src/main/java/com/example/docs/advancement/UseToolCriterion.java#datagen_advancements_criterion_trigger

Почти готово! Далее нам нужен экземпляр нашего критерия для работы. Давайте поместим его в новый класс, названный `ModCriteria`.

<<< @/reference/26.1.2/src/main/java/com/example/docs/advancement/ModCriteria.java#datagen_advancements_mod_criteria

Чтобы убедиться, что наши критерии инициализируются в нужное время, добавьте пустой метод `init`:

<<< @/reference/26.1.2/src/main/java/com/example/docs/advancement/ModCriteria.java#datagen_advancements_mod_criteria_init

И вызовите его в инициализаторе вашего мода:

<<< @/reference/26.1.2/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java#datagen_advancements_call_init

Наконец, нам нужно запустить наши критерии. Добавьте это туда, где мы отправили сообщение игроку в основном классе мода.

<<< @/reference/26.1.2/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java#datagen_advancements_trigger_criterion

Теперь ваш новый блестящий критерий готов к использованию! Давайте добавим его в наш провайдер:

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen_advancements_custom_criteria_advancement

Запустите задачу datagen снова — ваше новое достижение готово к использованию!

## Условия с параметрами {#conditions-with-parameters}

Всё это хорошо, но что если мы хотим выдавать достижение только после 5-кратного выполнения условия? А почему не еще один в 10 раз? Для этого нам нужно задать условию параметр. Вы можете продолжать использовать `UseToolCriterion` или перейти на новый `ParameterizedUseToolCriterion`. На практике вам нужно иметь только параметризованный, но в этом уроке мы оставим оба.

Давайте работать снизу вверх. Нам нужно проверить, выполнены ли требования, поэтому давайте отредактируем наш метод `Conditions#requirementsMet`:

<<< @/reference/26.1.2/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java#datagen_advancements_new_requirements_met

`requiredTimes` не существует, поэтому сделайте его параметром `Conditions`:

<<< @/reference/26.1.2/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java#datagen_advancements_new_parameter

Теперь наш кодек выдает ошибку. Давайте напишем новый кодек для новых изменений:

<<< @/reference/26.1.2/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java#datagen_advancements_new_codec

Двигаемся дальше, теперь нам нужно исправить наш метод `trigger`:

<<< @/reference/26.1.2/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java#datagen_advancements_new_trigger

Если вы создали новый критерий, нам нужно добавить его в `ModCriteria`

<<< @/reference/26.1.2/src/main/java/com/example/docs/advancement/ModCriteria.java#datagen_advancements_new_mod_criteria

И вызываем его в нашем основном классе, прямо там, где был старый вариант:

<<< @/reference/26.1.2/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java#datagen_advancements_trigger_new_criterion

Добавьте достижение в ваш `provider`:

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen_advancements_new_custom_criteria_advancement

Запустите datagen еще раз, и вы наконец-то закончили!

## Условная загрузка ресурсов {#resource-conditions}

Чтобы применить [условную загрузку ресурсов](../resource-conditions) к динамически генерируемому достижению, оберните объект-потребитель (consumer) методом `withConditions` и укажите любые условия ресурсов, которые вы хотите применить. В результате будет создано достижение с примененными условиями ресурсов:

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen_advancements_conditions
