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

@[code lang=java transcludeWith=:::datagen-advancements:provider-start](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java)

Чтобы завершить настройку, добавьте этот провайдер к вашей `DataGeneratorEntrypoint` в методе `onInitializeDataGenerator`.

@[code lang=java transcludeWith=:::datagen-advancements:register](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## Структура достижений {#advancement-structure}

Достижение состоит из нескольких различных компонентов. Помимо требований, называемых "критериями" (criterion), оно может содержать:

- `DisplayInfo` — указывает игре, как именно отображать достижение игрокам.
- `AdvancementRequirements` — списки списков критериев; для выполнения достижения требуется завершить как минимум один критерий из каждого подсписка.
- `AdvancementRewards` — награды, которые игрок получает за выполнение достижения.
- `Strategy` — стратегия, которая определяет, как достижение обрабатывает несколько критериев.
- `Parent` — родительское достижение. Организует иерархию, которую можно увидеть на экране "Достижения".

## Простые достижения {#simple-advancements}

Вот пример простого достижения за получение блока земли:

@[code lang=java transcludeWith=:::datagen-advancements:simple-advancement](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java)

:::details Вывод JSON

<<< @/reference/latest/src/main/generated/data/example-mod/advancement/get_dirt.json

:::

## Родительские достижения (Parents) {#parents}

Чтобы создать или расширить дерево достижений, мы можем указать родителя для нашего достижения. Для этого вызовите метод `Advancement.Builder#parent(...)` и передайте ссылку на родительское достижение:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#reference-parent

Если прямой ссылки на родительское достижения нет (например, в качестве родителя используется ванильное достижение), можно создать плейсхолдер с помощью static метода класса `Identifier`.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#placeholder-parent

Теперь ваши достижения будут отображаться в виде древовидной структуры в меню достижений.

![Дерево достижений](/assets/develop/data-generation/advancement_tree.png)

## Множественные критерии (Multiple Criteria) {#multiple-criteria}

Чтобы использовать более сложные условия в наших достижениях, мы можем вызывать метод `Advancement.Builder#addCriteria(...)` несколько раз, добавляя дополнительные критерии:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#multiple-criteria

По умолчанию для выполнения достижения должны быть выполнены все указанные критерии. Мы можем изменить это поведение, задав другую стратегию требований (`AdvancementRequirements.Strategy`):

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#requirements-strategy

## Награды (Rewards) {#rewards}

Мы можем привязывать награды к нашим достижениям, которые будут выдаваться игроку сразу после их выполнения. Для этого нужно вызвать метод `Advancement.Builder#rewards(...)` и передать туда награды, которые мы хотим добавить:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#experience-reward

Также доступны и другие типы наград:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#reward-types

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

@[code lang=java transcludeWith=:::datagen-advancements:entrypoint](@/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java)

Обратите внимание, что этот код очень плохой. Так как `HashMap` не хранится в постоянном месте, она будет сбрасываться при каждом перезапуске игры. Это сделано исключительно для демонстрации критериев (`Criterions`). Начните игру и попробуйте!

Далее создадим наш пользовательский критерий `UseToolCriterion`. Для него понадобится собственный класс `Conditions`, поэтому мы создадим их оба сразу:

@[code lang=java transcludeWith=:::datagen-advancements:criterion-base](@/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java)

Ух, как много всего! Давайте во всём разберёмся.

- `UseToolCriterion` — это класс-наследник `SimpleCriterionTrigger`, к которому могут применяться условия `Conditions`.
- В `Conditions` есть поле `playerPredicate`. Все условия `Conditions` должны содержать предикат игрока (технически — `LootContextPredicate`).
- У `Conditions` также есть `CODEC`. Этот `Codec` - просто кодек для одного поля, `playerPredicate`, с дополнительными инструкциями для преобразования между ними (`xmap`).

::: info

Чтобы узнать больше о кодеках, см. страницу [Codecs](../codecs).

:::

Нам понадобится способ проверить, выполнены ли условия. Давайте добавим вспомогательный метод в `Conditions`:

@[code lang=java transcludeWith=:::datagen-advancements:conditions-test](@/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java)

Теперь, когда у нас есть критерий и его условия, нам нужно найти способ запустить его. Добавьте метод триггера к `UseToolCriterion`:

@[code lang=java transcludeWith=:::datagen-advancements:criterion-trigger](@/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java)

Почти готово! Далее нам нужен экземпляр нашего критерия для работы. Давайте поместим его в новый класс, названный `ModCriteria`.

@[code lang=java transcludeWith=:::datagen-advancements:mod-criteria](@/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java)

Чтобы убедиться, что наши критерии инициализируются в нужное время, добавьте пустой метод `init`:

@[code lang=java transcludeWith=:::datagen-advancements:mod-criteria-init](@/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java)

И вызовите его в инициализаторе вашего мода:

@[code lang=java transcludeWith=:::datagen-advancements:call-init](@/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java)

Наконец, нам нужно запустить наши критерии. Добавьте это туда, где мы отправили сообщение игроку в основном классе мода.

@[code lang=java transcludeWith=:::datagen-advancements:trigger-criterion](@/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java)

Теперь ваш новый блестящий критерий готов к использованию! Давайте добавим его в наш провайдер:

@[code lang=java transcludeWith=:::datagen-advancements:custom-criteria-advancement](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java)

Запустите задачу datagen снова — ваше новое достижение готово к использованию!

## Условия с параметрами {#conditions-with-parameters}

Всё это хорошо, но что если мы хотим выдавать достижение только после 5-кратного выполнения условия? А почему не еще один в 10 раз? Для этого нам нужно задать условию параметр. Вы можете продолжать использовать `UseToolCriterion` или перейти на новый `ParameterizedUseToolCriterion`. На практике вам нужно иметь только параметризованный, но в этом уроке мы оставим оба.

Давайте работать снизу вверх. Нам нужно проверить, выполнены ли требования, поэтому давайте отредактируем наш метод `Conditions#requirementsMet`:

@[code lang=java transcludeWith=:::datagen-advancements:new-requirements-met](@/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java)

`requiredTimes` не существует, поэтому сделайте его параметром `Conditions`:

@[code lang=java transcludeWith=:::datagen-advancements:new-parameter](@/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java)

Теперь наш кодек выдает ошибку. Давайте напишем новый кодек для новых изменений:

@[code lang=java transcludeWith=:::datagen-advancements:new-codec](@/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java)

Двигаемся дальше, теперь нам нужно исправить наш метод `trigger`:

@[code lang=java transcludeWith=:::datagen-advancements:new-trigger](@/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java)

Если вы создали новый критерий, нам нужно добавить его в `ModCriteria`

@[code lang=java transcludeWith=:::datagen-advancements:new-mod-criteria](@/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java)

И вызываем его в нашем основном классе, прямо там, где был старый вариант:

@[code lang=java transcludeWith=:::datagen-advancements:trigger-new-criterion](@/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java)

Добавьте достижение в ваш `provider`:

@[code lang=java transcludeWith=:::datagen-advancements:new-custom-criteria-advancement](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java)

Запустите datagen еще раз, и вы наконец-то закончили!
