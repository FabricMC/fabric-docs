---
title: Game Rules
description: A guide for adding custom game rules.
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

::: info PREREQUISITES

You might want to have completed the [translation generation](./data-generation/translations) first, but it is not required.

:::

Game rules act as world-specific configuration options that the player can change in-game with a command. These variables usually control some function of the world, for example `pvp`, `spawn_monsters`, and `advance_time` control whether PvP is enabled, monster spawning, and time passing.

## Creating a Game Rule {#creating-a-game-rule}

To create a custom game rule, first create a `GameRules` class; this is where we are going to declare our game rules. Inside this class, declare two constants: a game rule identifier and the rule itself.

<<< @/reference/latest/src/main/java/com/example/docs/gamerule/ExampleModGameRules.java#gamerule-class

The category argument (`.category(GameRuleCategory.MISC)`) determines which category the gamerule falls under in the world creation screen. This example uses the Miscellaneous category provided by vanilla, but additional categories can be added via `GameRuleCategory.register`. In this example, we have created a boolean game rule with a default value of `false` and an id of `bad_vision`. The stored values in game rules are not limited to booleans; other valid types include `Double`s, `Integer`s, and `Enum`s.

Example of a game rule storing a double:

<<< @/reference/latest/src/main/java/com/example/docs/gamerule/ExampleModGameRules.java#double

## Accessing a Game Rule {#accessing-a-game-rule}

Now that we have a game rule and its `Identifier`, you can access it anywhere with the `serverLevel.getGameRules().get(GAMERULE)` method, where the argument to the `.get()` is your game rule constant and not the game rule id.

<<< @/reference/latest/src/main/java/com/example/docs/gamerule/ExampleModGameRules.java#badvision-get

You can also use this to access the values of vanilla game rules:

<<< @/reference/latest/src/main/java/com/example/docs/gamerule/ExampleModGameRules.java#vanilla

For example, for a rule that applies blindness to every player when true, the implementation would be:

<<< @/reference/latest/src/main/java/com/example/docs/gamerule/ExampleModGameRules.java#badvision-implement

## Translations {#translations}

Now, we need to give our game rule a display name so it can be easily understood from the Game Rules screen. To do this via data generation, add the following lines to your language provider:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnglishLangProvider.java#gamerule-name

Lastly we need to give our gamerule a description. To do this via data generation, add the following lines to your language provider:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnglishLangProvider.java#gamerule-description

::: info

These translation keys are used when displaying text in the game rules screen. If you do not use data generation, you can also write them by hand in your `assets/example-mod/lang/en_us.json`.

```json
"example-mod.bad_vision": "Bad Vision",
"gamerule.example-mod.bad_vision": "Gives every player the blindness effect",
```

:::

## Changing Game Rules In-Game {#changing-game-rules-in-game}

Now, you should be able to change the value of your rule in-game with the `/gamerule` command as such:

```mcfunction
/gamerule example-mod:bad_vision true
```

The game rule also now shows up in the Miscellaneous category in the Edit Game Rules screen.

![The world creation screen showing the Bad Vision game rule](/assets/develop/game-rules/world-creation.png)
