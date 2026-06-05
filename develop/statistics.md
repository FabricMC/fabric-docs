---
title: Statistics
description: Learn how to create and use custom player statistics.
authors:
  - bemoty
  - cassiancc
  - jummit
  - Tenneb22
---

Statistics track time spend doing something, distance traveled or jumped, and how often the player interacted with blocks or performed certain actions.

## Creating a Statistic {#creating-a-statistic}

To add a custom statistic, create an identifier which will be used to register and increase the stat:

<<< @/reference/latest/src/main/java/com/example/docs/stats/ModStats.java#stat

### Registration {#registration}

Then register the stat:

<<< @/reference/latest/src/main/java/com/example/docs/stats/ModStats.java#register

When adding the stat to the statistic screen via `Stats.CUSTOM.get()`, you can also specify the stat formatter. It determines how the number is shown in the stat list. You can use `DEFAULT`, `DIVIDE_BY_TEN`, `DISTANCE` or `TIME`.

## Using The Statistic {#using-the-statistic}

To increment the statistic, for example when a player interacts with a block, you can use `Player#awardStat`:

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/StatisticsBlock.java#interact

You can also use `Player#awardStat(stat, amount)` to increase the stat by an arbitrary amount.
