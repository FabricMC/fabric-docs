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

You might want to have completed the [translation generation](./data-generation/translations) first, but it is not required.

:::

Game rules act as world-specific configuration options that the player can change in-game with a command. These variables usually control some function of the world, for example `pvp`, `spawn_monsters`, and `advance_time`.

## Creating a Game Rule {#creating-a-game-rule}

To create a custom game rule, first create a `GameRules` class; this is where we are going to declare our game rules. Inside this class, declare two constants: a game rule identifier and the rule itself.

@[code lang=java transcludeWith=:::gameruleClass](@/reference/latest/src/main/java/com/example/docs/gamerule/ModGamerules.java)

The category argument (`.category(GameRuleCategory.MISC)`) determines which category the gamerule falls under in the world creation screen. In this example, we have created a boolean game rule with a default value of `false` and an id of `bad_vision`. The stored values in game rules are not limited to booleans; other valid types include `double`s, `integer`s, and `enum`s.

Example of a game rule storing a double:

@[code lang=java transcludeWith=:::double](@/reference/latest/src/main/java/com/example/docs/gamerule/ModGamerules.java)

## Accessing a Game Rule {#accessing-a-game-rule}

Now that we have a game rule and its `Identifier`, you can access it anywhere with the `serverWorld.getGameRules().get(GAMERULE)` method, where the argument to the `.get()` is your game rule constant and not the game rule id.

@[code lang=java transcludeWith=:::access](@/reference/latest/src/main/java/com/example/docs/gamerule/ModGamerules.java)

You can also use this to access the values of vanilla game rules

@[code lang=java transcludeWith=:::vanilla](@/reference/latest/src/main/java/com/example/docs/gamerule/ModGamerules.java)

For example, for a rule that applies blindness to every player when true, the implementation would be:

@[code lang=java transcludeWith=:::badvision](@/reference/latest/src/main/java/com/example/docs/gamerule/ModGamerules.java)

## Translations {#translations}

Now we need to add our gamerule in datagen to give it a display name. So in your English language provider add:

@[code lang=java transcludeWith=:::gamerule-name](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnglishLangProvider.java)

Lastly we need to give our gamerule a description. So in your English language provider add:

@[code lang=java transcludeWith=:::gamerule-description](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnglishLangProvider.java)

::: info

This translation key is used when displaying text in the game rules screen.

:::

## Changing Game Rules In-Game {#changing-game-rules-in-game}

Now, you should be able to change the value of your rule in-game with the `/gamerule` command as such:

``` command
/gamerule example-mod:bad_vision true
```
