---
title: Game Rules
description: A guide for adding custom game rules.
authors:
  - Jummit
  - modmuss50
  - Wind292
authors-nogithub:
  - mysterious_dev
  - solacekairos
---

<!---->

::: info PREREQUISITES

You might want to have completed the [translation generation](data-generation/translations) first, but it is not required.

:::

Gamerules act as global variables that the player can change in-game with a command. These variables usually change something function of the world, for example `pvp`, `spawn_monsters`, and `advance_time`.

## Creating a Game Rule {#creating-a-game-rule}

To create a custom gamerule first create a `GameRules` class; this is where we are going to declare our gamerules. Inside this class declare two constants, a gamerule identifier and the gamerule itself.

@[code lang=java transcludeWith=:::gameruleClass](@/reference/latest/src/main/java/com/example/docs/gamerule/ModGamerules.java)

::: info

The category argument (`.category(GameRuleCategory.MISC)`) mostly just changes what category the gamerule falls under in the world creation screen

:::

In this example we have created a boolean gamerule with a default value of false and the ID of `bad_vision`. The stored values in gamerules are not limited to booleans, you can have:

- Booleans
- Doubles
- Integers
- Enums

Example of a gamerule storing a double:

@[code lang=java transcludeWith=:::double](@/reference/latest/src/main/java/com/example/docs/gamerule/ModGamerules.java)

## Accessing a Gamerule {#accessing-a-gamerule}

Now that we have a gamerule and its identifier you can access it anywhere with the `serverworld.getGameRules().get(GAMERULE)` method. Where the argument to the `.get()` is your gamerule constant and not the gamerule identifier.

@[code lang=java transcludeWith=:::access](@/reference/latest/src/main/java/com/example/docs/gamerule/ModGamerules.java)

You can also use this to access the state of vanilla gamerules

@[code lang=java transcludeWith=:::vanilla](@/reference/latest/src/main/java/com/example/docs/gamerule/ModGamerules.java)

For example, for a gamerule that applies blindness to every player when true, the implementation would be:

@[code lang=java transcludeWith=:::badvision](@/reference/latest/src/main/java/com/example/docs/gamerule/ModGamerules.java)

## Translations {#translations}

Now we need to add our gamerule in datagen to give it a display name. So in your English language provider add:

@[code lang=java transcludeWith=:::gamerule-name](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnglishLangProvider.java)

Lastly we need to give our gamerule a description. So in your English language provider add:

@[code lang=java transcludeWith=:::gamerule-description](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnglishLangProvider.java)

::: info

Both of these mainly just change what is displayed in the world creation menu.

:::

## Changing Gamerules In-Game {#changing-gamerules-in-game}

Now you should be able to change your gamerule in-game with `/gamerule` command, so for our example:

``` command
/gamerule bad_vision true
```
