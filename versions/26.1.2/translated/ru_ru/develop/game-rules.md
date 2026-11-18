---
title: Игровые правила
description: Руководство по добавлению пользовательских игровых правил.
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

:::info ТРЕБОВАНИЯ

Возможно, вам стоит сначала завершить [генерацию переводов](./data-generation/translations), но это не обязательно.

:::

Игровые правила (game rules) действуют как специфические для мира параметры конфигурации, которые игрок может изменять в игре с помощью команды. Эти переменные обычно управляют некоторыми функциями мира, например `pvp`, `spawn_monsters` и `advance_time` определяют, включён ли PvP, спавнятся ли монстры и активен ли временной цикл.

## Создание игрового правила {#creating-a-game-rule}

Чтобы создать пользовательское игровое правило, сначала создайте класс `GameRules`. В нём мы объявим наши правила. Внутри этого класса объявите две константы: идентификатор игрового правила и само правило.

<<< @/reference/26.1.2/src/main/java/com/example/docs/gamerule/ExampleModGameRules.java#gamerule_class

Аргумент (`.category(GameRuleCategory.MISC)`) определяет, в какую категорию правило попадает в экране создания мира. В этом примере используется категория «Разное» (Miscellaneous) из ванильной игры, но можно добавлять и свои категории с помощью `GameRuleCategory.register`. В примере мы создали булево правило со значением по умолчанию `false` и идентификатором `bad_vision`. Хранимые значения в игровых правилах не ограничены булевыми; другие допустимые типы включают `Double`, `Integer` и перечисления (`Enum`).

Пример игрового правила, хранящего значение типа double:

<<< @/reference/26.1.2/src/main/java/com/example/docs/gamerule/ExampleModGameRules.java#double

## Доступ к игровому правилу {#accessing-a-game-rule}

Теперь, когда у нас есть игровое правило и его идентификатор, вы можете получить к нему доступ в любом месте с помощью метода `serverLevel.getGameRules().get(GAMERULE)`, где аргумент в `.get()` — это ваша константа игрового правила, а НЕ его строковый идентификатор.

<<< @/reference/26.1.2/src/main/java/com/example/docs/gamerule/ExampleModGameRules.java#badvision_get

Также через этот метод можно получить значения ванильных игровых правил:

<<< @/reference/26.1.2/src/main/java/com/example/docs/gamerule/ExampleModGameRules.java#vanilla

Например, для правила, которое накладывает слепоту на каждого игрока, если оно установлено в `true`, реализация будет такой:

<<< @/reference/26.1.2/src/main/java/com/example/docs/gamerule/ExampleModGameRules.java#badvision_implement

## Переводы {#translations}

Теперь нужно задать нашему игровому правилу отображаемое имя, чтобы оно было понятно в экране игровых правил. Чтобы сделать это через генерацию данных, добавьте следующие строки в ваш провайдер языков:

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModEnglishLangProvider.java#gamerule_name

Наконец, нужно дать нашему правилу описание. Чтобы сделать это через генерацию данных, добавьте следующие строки в ваш провайдер языков:

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModEnglishLangProvider.java#gamerule_description

::: info

Эти ключи перевода используются при отображении текста на экране игровых правил. Если вы не используете генерацию данных, вы можете также прописать их вручную в файле `assets/example-mod/lang/en_us.json`:

```json
"example-mod.bad_vision": "Bad Vision",
"gamerule.example-mod.bad_vision": "Gives every player the blindness effect",
```

:::

## Изменение игровых правил в игре {#changing-game-rules-in-game}

Теперь вы сможете изменить значение вашего правила в игре с помощью команды `/gamerule` следующим образом:

```mcfunction
/gamerule example-mod:bad_vision true
```

Игровое правило также появится в категории «Разное» на экране «Изменить игровые правила».

![Экран создания мира с новым игровым правилом «Плохое зрение» (Bad Vision)](/assets/develop/game-rules/world-creation.png)
