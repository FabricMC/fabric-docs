---
title: 动态注册表
description: 动态注册表简介——介绍其定义、适用场景以及如何使用 Fabric API 创建属于您自己的动态注册表。
authors:
  - Jimmy474
---

注册表是一个集中式的“电话簿”，负责将唯一的 ID（如 `minecraft:items`）映射到特定的对象。

有两种类型的注册表：静态注册表（如方块和物品注册表）在启动期间被冻结；而动态注册表（或自定义注册表）则在运行时通过数据包中的 JSON 文件填充。

它们在许多方面都非常有用：

- 将逻辑与内容分离。
- 其他模组开发者可以通过数据包添加新内容，而无需修改您的代码。
- 玩家可以通过替换数据包中的数据条目，覆盖诸如魔力消耗或升级价格等默认值。
- 动态注册表数据与特定世界绑定。它在世界打开时加载，并在世界关闭时清除。
- 动态注册表解决了“硬编码内容”的问题。与其将每个技能、任务或升级通过枚举或静态列表直接写死在 Java 代码中，不如在代码中定义模板，并让实际内容来自数据文件。

让我们为魔法技能系统创建一个动态注册表。

## 类设置 {#class-setup}

首先，创建代表注册表条目的类。它是一个简单的数据容器，用于保存每个魔法技能关联的值（如名称、魔力消耗等）。需要一个 [`Codec`](./codecs) 来对条目进行编码和解码。

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/MagicSkillsRegistryEntry.java#main

- `name` 是技能的名称。
- `manaCost` 是技能的魔力消耗。
- `onUseMcFunction` 是一个 [函数](https://minecraft.wiki/w/Function_(Java_Edition))，服务器可以在使用技能时执行它。将其保留在注册表中可以让其他数据包自定义任何技能的逻辑，或者通过自定义函数添加新技能。

## 注册注册表 {#registering-the-registry}

每个注册表都是通过能唯一标识它的键来注册的，因此让我们创建该键以及持有它的类。我们将这个类命名为 `ExampleModRegistries`：

::: tip

建议在一个公共类中声明注册表键，因为这样可以更容易地管理多个注册表。

:::

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#main

从您的 [模组初始化类](./getting-started/project-structure#entrypoints) 中调用 `ExampleModRegistries.initialize()`。

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModDynamicRegistries.java#main

然后将其注册到 Fabric API 的 `DynamicRegistries` 中，后者提供了两种不同的策略：`DynamicRegistries.register()` 或 `DynamicRegistries.registerSynced()`。

### 使用 `register()` {#using-register}

`DynamicRegistries.register()` 用于创建一个未同步的注册表。它仅在服务端加载，在客户端不可用。当客户端不需要读取该注册表时请使用此选项。

这与我们的示例无关，但以下是操作方法：

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#simple

### 使用 `registerSynced()` {#using-register-synced}

::: info

以下方法中使用的键的创建方式与创建 [`MAGIC_SKILLS_REGISTRY_KEY`](#registering-the-registry) 相同，只是名称不同。

:::

`DynamicRegistries.registerSynced()` 用于创建一个已同步的注册表。当客户端加入世界时，服务端会自动将该注册表的数据同步到客户端。当客户端需要该数据进行渲染、UI 显示、工具提示或其他客户端逻辑时请使用此选项。

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#synced

`DynamicRegistries.registerSynced()` 拥有一个重载，允许传入第二个用来在客户端进行解码的编解码器（Codec）。如果客户端不需要服务端完整条目中的所有字段，这将非常有用。

在我们的示例中，客户端只需要 [`name` 和 `manaCost` 字段](#class-setup)，因此让我们创建一个不包含 `onUseMcFunction` 的 [`Codec`](./codecs)，并将该编解码器传递给 `registerSynced`：

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/MagicSkillsRegistryEntry.java#client_codec

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#double_codec

### `SyncOption` {#sync-option}

`DynamicRegistries.registerSynced()` 的两个重载都在末尾接受 `SyncOption` 参数，用以配置同步行为。唯一可用的选项是：

- `SKIP_WHEN_EMPTY`：仅在注册表包含条目时才进行同步。这有助于提高那些可能不需要该注册表的客户端的兼容性。

示例：

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#with_option

## 填充注册表 {#populating-the-registry}

JSON 文件用于创建注册表条目。 JSON 结构必须与 [`MagicSkillsRegistryEntry`](#class-setup) 相匹配。在本例中，我们的条目类有三个字段，因此 `healing_skill` 条目的 JSON 文件可能如下所示：

<<< @/reference/latest/src/main/generated/data/example-mod/example-mod/magic_skills_synced_registry/healing_skill.json

条目 JSON 文件保存在 `src/main/resources/data/example-mod/example-mod/magic_skills_registry/` 路径下。

::: info

重复的 `example-mod/example-mod` 并非写错。

第一个 `example-mod` 是被添加条目的命名空间。第二个 `example-mod` 来自注册表 ID 本身。两者都使用您的模组 ID 是很普遍的，这也允许其他模组或数据包在其自己的命名空间下向您的注册表添加条目。

例如，`another-mod` 可能想要为我们的 `magic_skills_registry` 添加元素，它可以将文件存放在 `src/main/resources/data/another-mod/example-mod/magic_skills_registry/` 路径下来实现。

:::

### 条目 ID {#entry-id}

条目 ID 是每个条目的唯一键，便于从注册表中访问特定条目。它由文件名和 [注册表键](#registering-the-registry) 组成。例如，由于我们的条目 JSON 文件名为 `healing_skill.json`，因此条目 ID 为：

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#entry_id

## 访问注册表数据 {#accessing-the-registry-data}

动态注册表随世界一起加载，可以通过 `RegistryAccess` 类并传入您的注册表键来访问。可以从许多类中获取 `RegistryAccess` 的实例，但最常见的是 `MinecraftServer`、`ServerLevel`、`ClientLevel`、`Entity` 等。

:::warning 重要

当从仅限客户端的类（如 `ClientLevel`）访问 `RegistryAccess` 实例时，仅 [已同步的注册表](#using-register-synced) 可用。

:::

### 获取整个注册表 {#get-the-entire-registry}

可以使用 `RegistryAccess` 的 `lookup` 方法来访问注册表，该方法返回一个 `Optional<Registry<T>>`，其中 `T` 为注册表的类型。

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#get_registry

### 获取特定条目 {#get-a-specific-entry}

可以使用 `RegistryAccess` 的 `get` 方法来访问特定条目，该方法返回一个 `Optional<Holder.Reference<T>>`，其中 `T` 为注册表的类型。

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#get_specific_registry_entry

阅读 [条目 ID](#entry-id) 了解如何获取 `HEALING_SKILL_ENTRY_ID`。

在我们的示例中，可以使用该方法获取用户在服务端使用的魔法技能条目，然后提取 [`onUseMcFunction`](#class-setup) 字段以执行 mcfunction。

### 遍历所有条目 {#iterate-over-all-entries}

注册表条目可以被遍历，以用于各种目的（例如填充 UI 界面）。在我们的示例中，可以使用该方法为屏幕填充自定义控件，如下所示：

<<< @/reference/latest/src/client/java/com/example/docs/dynamic_registries/screens/ExampleModMagicSkillsScreen.java#iterate_over_registry_entries

:::details 从注册表填充数据的自定义屏幕

![魔法技能屏幕示例](/assets/develop/dynamic_registry/magic_skills_screen.png)

了解更多有关创建 [自定义屏幕](./rendering/gui/custom-screens) 和 [自定义控件](./rendering/gui/custom-widgets) 的内容。

:::

## 自定义注册表条目的标签 {#tags-for-custom-registry-entries}

标签是一种将多个条目分组在一起的方式。例如，我们可以创建诸如 _attack_（攻击）和 _defense_（防御）等标签，将类似的魔法技能归类在一起。

例如，攻击标签将定义在 `data/example-mod/tags/example-mod/magic_skills_registry/attacking_skills.json` 文件中：

<<< @/reference/latest/src/main/generated/data/example-mod/tags/example-mod/magic_skills_synced_registry/attacking_skills.json

### 在代码中使用标签 {#using-tags-in-code}

为标签创建一个标签键（TagKey），用于检查条目是否存在于该标签中。

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModTags.java#tag

我们可以使用这种方法来检查某个技能是否为攻击型技能。

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModTags.java#tag_usage
