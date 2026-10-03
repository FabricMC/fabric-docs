---
title: 战利品表修改
description: 关于使用 Fabric API 提供的事件修改战利品表的指南。
authors:
  - NotNightSky
  - its-miroma
  - cassiancc
---

战利品表系统决定了当破坏方块、杀死实体或打开箱子时掉落哪些物品。 Fabric API 提供了多种方式，可以在加载过程中修改、替换和后处理战利品表，以及在运行时调整最终的掉落物。

Fabric Loot API 通过 `LootTableEvents` 类提供了多个事件：

- [`LootTableEvents.MODIFY`](#modifying-loot-tables)：保留原有的战利品表并添加奖池与条目。
- [`LootTableEvents.REPLACE`](#replacing-loot-tables)：丢弃原有的战利品表并提供一个新的战利品表。
- [`LootTableEvents.MODIFY_DROPS`](#modifying-loot-table-drops)：在战利品生成后修改最终的 `ItemStack` 掉落物列表。
- [`LootTableEvents.ALL_LOADED`](#loot-table-post-processing)：在加载完成后检查或验证所有战利品表。

这些事件在战利品表加载过程中按特定顺序发生：

1. `REPLACE`
2. `MODIFY`
3. `ALL_LOADED`
4. `MODIFY_DROPS`

`MODIFY_DROPS` 是唯一在运行时发生的事件，而其他事件发生在世界加载期间。

使用这些事件时，记住这个顺序非常重要，因为它会影响您的修改与其他事件以及原始战利品表之间的相互作用。

## 修改战利品表 {#modifying-loot-tables}

当您想要修改现有的战利品表，同时保留其原始内容时，请使用 `LootTableEvents.MODIFY`。回调会提供一个 `LootTable.Builder`，因此您可以添加新奖池或向现有奖池添加条目，而无需重新构建整个表。

这通常是将物品添加到原版或数据包表中的最佳选择，例如向方块的现有掉落物中添加自定义物品。您可以检查 `source` 参数，了解该表是来自内置资源、数据包，还是来自另一个替换事件。

当原始战利品表应大部分保持不变时，请使用 `MODIFY`。

例如，让我们使用 `MODIFY` 事件并通过 `LootItemEntityPropertyCondition` [谓词](#predicates)，使用钻石剑杀死白羊时掉落一颗钻石。

<<< @/reference/latest/src/main/java/com/example/docs/event/ExampleModEvents.java#loot_table_modify_event

上述示例的效果：

<VideoPlayer src="/assets/develop/events/modify_event_example.webm">修改事件示例</VideoPlayer>

## 替换战利品表 {#replacing-loot-tables}

当您想要丢弃现有的战利品表并提供一个新的战利品表时，请使用 `LootTableEvents.REPLACE`。

回调会接收到原始的 `LootTable`。返回一个新的 `LootTable` 来替换它，或者返回 `null` 以保持原样。一旦有监听器替换了战利品表，就不再对其调用其他替换监听器。

当原始战利品表与您的模组行为不兼容，且修改单个战利品池比直接创建一个新表更复杂时，该事件非常有用。

例如，让我们使用 `REPLACE` 事件并结合 `LootItemEntityPropertyCondition` [谓词](#predicates)，将棕色羊的战利品表替换为一个新表：当用金剑杀死它时掉落金锭。

<<< @/reference/latest/src/main/java/com/example/docs/event/ExampleModEvents.java#loot_table_replace_event

::: warning

如果您不替换战利品表，请务必返回 `null`。直接返回原始表仍会将战利品表标记为已替换，这会导致后续的替换监听器无法运行，并导致 `isBuiltIn()` 检查失败。

:::

上述示例的效果：

<VideoPlayer src="/assets/develop/events/replace_event_example.webm">替换事件示例</VideoPlayer>

## 修改战利品表掉落物 {#modifying-loot-table-drops}

`LootTableEvents.MODIFY_DROPS` 会在运行时生成战利品之后才运行。在以下情况下，该事件非常有用：

- 战利品表数量未知或非常庞大。
- 相同的规则需要应用到多个战利品表。
- 您想要检查 `LootContext`，例如实体、工具或伤害来源。
- 为每个战利品表都添加自定义战利品函数过于繁琐。

可以通过添加、移除或修改物品堆来直接修改掉落物列表。请注意，由于该事件在战利品生成后运行，因此它无法更改战利品表的奖池、条目或条件。

::: info

如果战利品表要求特定的堆叠数量，掉落物可能已经被拆分为多个物品堆。

:::

例如，让我们使用 `MODIFY_DROPS` 事件，使破坏石头方块时掉落两个石头方块而不是圆石。

<<< @/reference/latest/src/main/java/com/example/docs/event/ExampleModEvents.java#loot_table_modify_drops_event

上述示例的效果：

<VideoPlayer src="/assets/develop/events/modify_drops_event_example.webm">修改掉落物事件示例</VideoPlayer>

## 战利品表后处理 {#loot-table-post-processing}

在所有战利品表加载完毕且 `REPLACE` 和 `MODIFY` 事件触发之后需要执行的操作，请使用 `LootTableEvents.ALL_LOADED`。

该事件提供了服务器的 `ResourceManager` 和完整的战利品表注册表。这使其非常适合检查已加载的表、验证表、收集信息或执行依赖于所有可用表的其他配置。

::: info

此事件不能用于在战利品生成期间添加掉落物。若要修改表，请使用 [`MODIFY`](#modifying-loot-tables) 或 [`REPLACE`](#replacing-loot-tables)。若要修改生成的物品堆，请使用 [`MODIFY_DROPS`](#modifying-loot-table-drops)。

:::

<<< @/reference/latest/src/main/java/com/example/docs/event/ExampleModEvents.java#loot_table_all_loaded_event

## 谓词 {#predicates}

战利品条件（在内部被称为谓词）用于控制战利品池、条目或函数是否可以使用。它们在与 `MODIFY` 和 `REPLACE` 配合使用时尤为有效，可以让您有条件地添加或替换掉落物，而无需在 Java 代码中手动处理每一种情况。请参考上面 `MODIFY` 和 `REPLACE` 示例中的 `.when(...)` 调用。设计替换表时相同的条件也能派上用场，而 `MODIFY_DROPS` 则需要在事件回调中执行等效的检查。

以下是常用谓词示例，按用途进行分组。有关完整列表，请查阅 `net.minecraft.world.level.storage.loot.predicates` 包以及 [Minecraft Wiki 谓词列表](https://minecraft.wiki/w/Predicate)：

### 逻辑谓词 {#logic-predicates}

这些谓词用于组合或反转其他条件。

- `AllOfCondition`：所有条件都必须通过。当您需要执行 **与（AND）** 逻辑检查时使用此项。
- `AnyOfCondition`：至少有一个条件必须通过。当您需要执行 **或（OR）** 逻辑检查时使用此项。
- `InvertedLootItemCondition`：翻转另一个条件，使其仅在原条件失败时通过。当您需要执行 **非（NOT）** 逻辑时使用此项。

::: tip

这些逻辑谓词可以嵌套使用以创建复杂的条件。例如，您可以组合使用 `AllOfCondition` 和 `AnyOfCondition`，在要求多个检查通过的同时，对部分条件保留一定的灵活性。

:::

### 世界状态谓词 {#world-state-predicates}

这些谓词用于检查生成战利品的世界或具体位置信息。

- `WeatherCheck`：检查当前是否下雨或打雷。
- `TimeCheck`：检查一天中的时间或特定时间段。
- `LocationCheck`：检查掉落发生的位置，例如 Y 轴高度或其他位置数据。
- `EnvironmentAttributeCheck`：检查与世界特定的环境规则或属性。

### 方块、工具和实体谓词 {#block-tool-and-entity-predicates}

这些谓词用于检查被破坏的方块、所使用的工具，或产生战利品的实体。

- `ExplosionCondition`：使战利品奖池或条目仅在掉落物未被爆炸摧毁时生效。
- `MatchTool`：检查用于破坏方块的工具是否匹配指定的物品或物品谓词。
- `LootItemBlockStatePropertyCondition`：检查方块破坏前的方块状态，这对于农作物和其他带状态的方块非常有用。
- `LootItemKilledByPlayerCondition`：要求实体必须是由玩家击杀的。
- `DamageSourceCondition`：检查伤害来源的详细信息，例如攻击是直接还是间接击中。

### 基于概率的谓词 {#chance-based-predicates}

这些谓词根据概率或魔咒等级来决定掉落。

- `LootItemRandomChanceCondition`：为掉落物提供固定概率。
- `LootItemRandomChanceWithEnchantedBonusCondition`：根据魔咒等级改变掉落概率。
- `BonusLevelTableCondition`：用于处理随魔咒等级变化的战利品概率的辅助工具（例如时运）。

::: warning

不应在同一个奖池中同时使用 `LootItemRandomChanceWithEnchantedBonusCondition` 和 `LootItemRandomChanceCondition`，因为二者都会定义基础概率，这可能会导致意外行为。

:::

### 共享谓词引用 {#shared-predicate-references}

- `ConditionReference`：指向在其他地方定义并可在多个表中复用的数据驱动战利品条件。
