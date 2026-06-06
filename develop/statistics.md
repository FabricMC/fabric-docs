---
title: Statistics
description: Learn how to create and use custom player statistics.
authors:
  - bemoty
  - cassiancc
  - jummit
  - Tenneb22
---

Statistics track time spent performing actions in the world. Vanilla includes statistics for common actions like jumping, traveling in boats, as well as counting interactions with blocks or usage of items. It's also possible to add your own statistic to count custom interactions.

## Creating a Statistic {#creating-a-statistic}

To add a custom statistic, create an `Identifier` which will be used to register and increase the stat:

<<< @/reference/latest/src/main/java/com/example/docs/stats/ModStats.java#stat

Then register the statistic:

<<< @/reference/latest/src/main/java/com/example/docs/stats/ModStats.java#register

Don't forget to initialize the `ModStats` class in your [mod's initializer](./getting-started/project-structure#entrypoints).

When adding the stat to the Statistics screen via `Stats.CUSTOM.get()`, you can also specify the stat formatter, which determines how the number is displayed in the stat list. Vanilla provides the following formatters:

- `DEFAULT`: Displays the number directly.
- `DIVIDE_BY_TEN`: Displays the number as a decimal, divided by ten.
- `DISTANCE`: Displays the number as distance: Depending on the size of the number, this will be shown in centimeters, meters, or kilometers.
- `TIME`: Displays the number as time. Depending on the size of the number, this will be shown in seconds, minutes, hours, or days.

## Using the Statistic {#using-the-statistic}

In this example, we will increase the statistic whenever the friends block is placed. We use the `Player#awardStat(stat, amount)` method to increment it by the amount of neighbors the block has when placed.

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/FriendsBlock.java#friends_block

You can also use `Player#awardStat(stat)` or `Player#resetStat(stat)` to increase the stat by 1 or reset it to 0, respectively.
