---
title: Statistics
description: Learn how to create and use custom player statistics.
authors:
  - bemoty
  - cassiancc
  - CelDaemon
  - its-miroma
  - jummit
  - Tenneb22
---

Statistics (or Stats) are a way to keep track of certain actions or time that the player spends in the world. Vanilla tracks statistics for common actions like jumping, traveling in boats, interactions with blocks, usage of items and much more. It's also possible to add your own statistic to track some custom interaction.

## Creating a Statistic {#creating-a-statistic}

To add a custom statistic, create an `Identifier` which will be used to register and increase the stat:

<<< @/reference/latest/src/main/java/com/example/docs/stats/ModStats.java#stat

Then register the statistic:

<<< @/reference/latest/src/main/java/com/example/docs/stats/ModStats.java#register

When adding the stat to the Statistics screen via `Stats.CUSTOM.get()`, you can also specify the stat formatter, which determines how the number is displayed in the stat list. Vanilla provides the following formatters:

- `DEFAULT`: Displays the number directly.
- `DIVIDE_BY_TEN`: Displays the number as a decimal, divided by ten.
- `DISTANCE`: Displays the number as distance: Depending on the size of the number, this will be shown in centimeters, meters, or kilometers.
- `TIME`: Displays the number as time. Depending on the size of the number, this will be shown in seconds, minutes, hours, or days.

Don't forget to initialize the `ModStats` class in your [mod's initializer](./getting-started/project-structure#entrypoints):

<<< @/reference/latest/src/main/java/com/example/docs/stats/ModStats.java#initialize

<<< @/reference/latest/src/main/java/com/example/docs/stats/ExampleModStats.java#initialize

## Using the Statistic {#using-the-statistic}

For this example, we will create a Friends block, which makes friends with its neighbors. We shall track how many friendships the player has formed with the block.

To do this, we will use the `Player#awardStat(stat, amount)` method to increment the stat by the amount of neighbors the block has when placed:

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/FriendsBlock.java#friends_block

You can also use `Player#awardStat(stat)` to increment the stat by 1.

Since Friends block are very attached to each other, breaking one means breaking all friendships. Let's make it so that breaking a Friends block will reset the player's stat back to 0 by using `Player#resetStat()`:

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/FriendsBlock.java#break_friendships
