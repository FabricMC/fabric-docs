---
title: Ігрові правила
description: Посібник з додання власних ігрових правил.
authors:
  - cassiancc
  - Jummit
  - modmuss50
  - Wind292
authors-nogithub:
  - mysterious_dev
  - solacekairos
---

<!---->

:::info ПЕРЕДУМОВИ

Можливо, ви захочете спершу завершити [генерацію перекладу](./data-generation/translations), але це не обов’язково.

:::

Ігрові правила діють як специфічні для світу параметри налаштування, які гравець може змінити в грі за допомогою команди. Ці змінні зазвичай керують певною функцією світу, наприклад, `pvp`, `spawn_monsters` і `advance_time` керують тим, чи ввімкнено можуть битися гравці, породжуватися монстри та чи є плин часу.

## Створення ігрового правила {#creating-a-game-rule}

Щоб створити власне ігрове правило, спочатку створіть клас `GameRules`; тут ми збираємося оголосити наші правила. У цьому класі оголосите дві константи: ідентифікатор та саме правило.

@[code lang=java transcludeWith=:::gameruleClass](@/reference/latest/src/main/java/com/example/docs/gamerule/ExampleModGameRules.java)

Аргумент категорії (`.category(GameRuleCategory.MISC)`) визначає, до якої категорії відноситься ігрове правило на екрані створення світу. У цьому прикладі використовується категорія «Інше», надану стандартною грою, але додаткові категорії можна додати за допомогою `GameRuleCategory.register`. У цьому прикладі ми створили логічне правило з усталеним значенням `false` та ID `bad_vision`. Збережені значення в правилах не обмежуються логічними значеннями; інші допустимі типи включають `Double`, `Integer` і `Enum`.

Приклад правила, що зберігає подвійне:

@[code lang=java transcludeWith=:::double](@/reference/latest/src/main/java/com/example/docs/gamerule/ExampleModGameRules.java)

## Доступ до ігрового правила {#accessing-a-game-rule}

Тепер, коли у нас є правило та його `Identifier`, ви можете отримати доступ до нього будь-де за допомогою методу `serverLevel.getGameRules().get(GAMERULE)`, де аргументом `.get()` є константа правила, а не ID правила.

@[code lang=java transclude={44-44}](@/reference/latest/src/main/java/com/example/docs/gamerule/ExampleModGameRules.java)

Ви також можете використовувати це для доступу до значень правил стандартної гри:

@[code lang=java transcludeWith=:::vanilla](@/reference/latest/src/main/java/com/example/docs/gamerule/ExampleModGameRules.java)

Наприклад, для правила, яке застосовує сліпоту до кожного гравця, коли воно ввімкнене, реалізація буде такою:

@[code lang=java transcludeWith=:::badvision](@/reference/latest/src/main/java/com/example/docs/gamerule/ExampleModGameRules.java)

## Переклад {#translations}

Тепер нам потрібно дати нашому правилу показувану назву, щоб її було легко зрозуміти на екрані ігрових правил. Щоб зробити це за допомогою генерації даних, додайте такі рядки до свого постачальника мови:

@[code lang=java transcludeWith=:::gamerule-name](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnglishLangProvider.java)

Нарешті, нам потрібно дати опис нашому правилу. Щоб зробити це за допомогою генерації даних, додайте такі рядки до свого постачальника мови:

@[code lang=java transcludeWith=:::gamerule-description](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnglishLangProvider.java)

::: info

Ці ключі перекладу використовуються під час показу тексту на екрані ігрових правил. Якщо ви не використовуєте генерацію даних, ви також можете записати їх вручну у свій `assets/example-mod/lang/en_us.json` (`uk_ua.json` для української).

```json
"example-mod.bad_vision": "Bad Vision",
"gamerule.example-mod.bad_vision": "Gives every player the blindness effect",
```

:::

## Зміна правил в грі {#changing-game-rules-in-game}

Тепер ви зможете змінити значення свого правила в грі за допомогою команди `/gamerule`:

```mcfunction
/gamerule example-mod:bad_vision true
```

Правило також тепер видно в категорії «Інше» на екрані «Редагувати ігрові правила».

![Екран створення світу, на якому показано правило Bad Vision](/assets/develop/game-rules/world-creation.png)
