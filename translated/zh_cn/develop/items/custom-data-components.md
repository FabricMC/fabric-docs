---
title: 自定义数据组件
description: 学习如何使用 1.20.5 新的组件系统为你的物品添加自定义数据
authors:
  - Romejanic
---

# 自定义数据组件{#custom-data-components}

物品越来越复杂，你会发现自己需要存储与每个物品关联的自定义数据。 游戏需要你将持久的数据存储在 `ItemStack`（物品堆）中，在 1.20.5 中，方法就是使用**数据组件**。

数据组件替代了之前版本中的 NBT 数据，替换成能应用在 `ItemStack` 的结构化的数据，从而存储物品堆的持久数据。 数据组件是有命名空间的，也就是说，我们可以实现自己的数据组件，存储 `ItemStack` 的自定义数据，并稍后再访问。 所有原版可用的数据组件可以见于此 [Minecraft wiki 页面](https://zh.minecraft.wiki/w/%E7%89%A9%E5%93%81%E5%A0%86%E5%8F%A0%E7%BB%84%E4%BB%B6#%E7%BB%84%E4%BB%B6%E6%A0%BC%E5%BC%8F)。

除了注册自定义组件外，本页还介绍了组件 API 的一般用法，这些也可用于原版的组件。 你可以在 `DataComponentTypes` 类中查看并访问所有原版组件的定义。

## 注册组件{#registering-a-component}

就你模组中的其他东西一样，你需要使用 `ComponentType` 注册自定义的组件。 这个组件类型接受一个泛型参数，包含你的组件的值的类型。 之后在讲[基本](#basic-data-components)和[高级](#advanced-data-components)组件时会更深入研究。

把这个组件放到一个合理的类中。 对于这个例子，我们创建一个新的包，叫做 `compoennt`，以及一个类，叫做 `ModComponents`，包含我们所有的组件类型。 确保在模组的初始化器中调用 `ModComponent.initialize()`。

@[code transcludeWith=::1](@/reference/latest/src/main/java/com/example/docs/component/ModComponents.java)

这是注册一个组件类型的基本模板：

```java
public static final ComponentType<?> MY_COMPONENT_TYPE = Registry.register(
    Registries.DATA_COMPONENT_TYPE,
    Identifier.of(FabricDocsReference.MOD_ID, "my_component"),
    ComponentType.<?>builder().codec(null).build()
);
```

有几点需要注意。 在第一行，你看到了一个 `?`， 这将被替换成你的组件的值的类型， 我们稍后完成。

其次，你需要提供一个 `Identifier`，包含你的组件的 ID， 其命名空间就是你的模组的 ID。

最后，我们有一个 `ComponentType.Builder`，创建一个需要注册的实际`ComponentType` 实例。 这包含我们会需要讨论的另一个重要细节：你的组件的 `Codec`。 现在还是 `null`，但我们也会稍后完成。

## 基本数据组件{#basic-data-components}

基本数据组件（例如 `minecraft:damagae`）包含单个数据值，例如 `int`、`float`、`boolean` 或 `String`。

例如，我们创建一个 `Integer` 值，追踪玩家手持我们的物品右键点击了多少次。 参照下面的代码，更新刚刚注册组件的代码。

@[code transcludeWith=::2](@/reference/latest/src/main/java/com/example/docs/component/ModComponents.java)

可以提到，我们这里将 `<Integer>` 传入作为我们的泛型类型，表示这个组件会存储为单个的 `int` 值。 对于我们的 codec，直接使用提供的 `Codec.INC` codec 就可以了。 对于这样简单的组件，可以使用基本的 codec，但是更加复杂的情形可能需要自定义的 codec（后面就会讲到）。

如果开始游戏，就可以输入像这样的命令：

![/give 命令显示自定义的组件](/assets/develop/items/custom_component_0.png)

运行命令时，你应该能收到一个包含组件的物品。 但是，现在还没使用这个组件做些有用的事情。 先开始以我们能看到的方式读取组件的值吧。

## 读取组件的值{#reading-component-value}

添加新物品，每次右键点击时都会增加计数器。 可以阅读[自定义物品交互](./custom-item-interactions)页面以了解我们在这个教程中使用的技巧。

@[code transcludeWith=::1](@/reference/latest/src/main/java/com/example/docs/item/custom/CounterItem.java)

记得要和平时一样，在 `ModItems` 类中注册物品。

```java
public static final Item COUNTER = register(new CounterItem(
    new Item.Settings()
), "counter");
```

我们会添加一些物品提示，在物品栏中鼠标悬浮在物品上时，显示点击次数的当前值。 可以使用 `ItemStack` 的 `get()` 方法获取组件的值：

```java
int clickCount = stack.get(ModComponents.CLICK_COUNT_COMPONENT);
```

这会返回组件的当前值，其类型为我们注册组件时定义的类型。 可以将这个值添加到物品提示中。 在 `CounterItem` 类中，把这一行添加到 `appendTooltip` 方法：

```java
public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
    int count = stack.get(ModComponents.CLICK_COUNT_COMPONENT);
    tooltip.add(Text.translatable("item.fabric-docs-reference.counter.info", count).formatted(Formatting.GOLD));
}
```

不要忘记更新你的语言文件（`/assets/<mod id>/lang/en_us.json` 和 `/assets/<0>/lang/zh_cn.json`），并添加这两行：

```json
{
    "item.fabric-docs-reference.counter": "Counter",
    "item.fabric-docs-reference.counter.info": "Used %1$s times",
}
```

启动游戏，运行这个命令，给自己一个计数为 5 的新的计数器物品。

```mcfunction
/give @p fabric-docs-reference:counter[fabric-docs-reference:click_count=5]
```

在物品栏内鼠标悬停在这个物品上时，你可以看到物品提示中显示了计数！

![显示“使用了 5 次”的物品提示](/assets/develop/items/custom_component_1.png)

但是，如果给自己一个_没有_自定义组件的新的计数器物品，当你在物品栏内选中物品时游戏会崩溃。 崩溃报告中，会提示这样的信息：

```log
java.lang.NullPointerException: Cannot invoke "java.lang.Integer.intValue()" because the return value of "net.minecraft.item.ItemStack.get(net.minecraft.component.ComponentType)" is null
        at com.example.docs.item.custom.CounterItem.appendTooltip(LightningStick.java:45)
        at net.minecraft.item.ItemStack.getTooltip(ItemStack.java:767)
```

这是因为 `ItemStack` 当前没有包含我们的自定义组件的实例，因此对于我们的组件类型，调用 `stack.get()` 会返回 `null`。

解决这个问题有三种方法。

### 设置默认组件值{#setting-default-value}

当你注册你的物品并传递 `Item.Settings` 对象到你的物品构造器中，你还可以提供应用于所有新物品的默认组件的列表。 如果回到我们的 `ModItems` 类，注册 `CounterItem` 的地方，就可以为我们的自定义组件添加默认值。 添加这个，这样新物品会显示计数为 `0`。

@[code transcludeWith=::_13](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

创建了新物品后，就会自动为我们的自定义组件应用给定的值。

:::warning
使用命令有可能会从 `ItemStack` 中移除默认组件。 当组件不存在于新的物品时，可以参考接下来两个章节，以适当处理这种情况。
:::

### 带有默认值读取{#reading-default-value}

此外，添加组件值时，可以对 `ItemStack` 对象使用 `getOrDefault()` 方法，当组件不存在于物品堆中时，返回指定的默认值。 这可以避免任何由缺失组件导致的错误。 我们可以像这样调整物品提示代码：

```java
int clickCount = stack.getOrDefault(ModComponents.CLICK_COUNT_COMPONENT, 0);
```

正如你所见，方法接受两个参数：和之前一样的组件类型，还有一个默认值，组件不存在时就返回默认值。

### 检查组件是否存在{#checking-if-component-exists}

你也可以使用 `contains()` 方法检查 `ItemStack` 中是否存在特定的组件。 这会接收一个组件类型作为参数，并返回 `true` 或 `false`，取决于物品堆是否包含组件。

```java
boolean exists = stack.contains(ModComponents.CLICK_COUNT_COMPONENT);
```

### 修复错误{#fixing-the-error}

我们现在以第三个选项开始。 添加了默认的组件值，还需要检测组件是否存在于物品堆中，只有存在时才显示提示。

@[code transcludeWith=::3](@/reference/latest/src/main/java/com/example/docs/item/custom/CounterItem.java)

再次启动游戏，并将鼠标悬停在没有组件的物品上，你应该看到显示的是“使用了 0 次”并不再崩溃游戏。

![显示“使用了 0 次”的物品提示](/assets/develop/items/custom_component_2.png)

尝试给予自己一个不含自定义组件的计数器。 可以运行如下命令：

```mcfunction
/give @p fabric-docs-reference:counter[!fabric-docs-reference:click_count]
```

鼠标悬浮在物品上，应该不会有提示。

![没有提示的计数器物品](/assets/develop/items/custom_component_7.png)

## 更新组件值{#setting-component-value}

现在尝试更新我们的组件值。 我们尝试在每次使用我们的计数器物品时增加点击次数。 要改变 `ItemStack` 的组件的值，使用 `set()` 方法，就像这样：

```java
stack.set(ModComponents.CLICK_COUNT_COMPONENT, newValue);
```

这会接收我们的组件类型，以及需要设置的值， 在这种情况下为新的点击次数。 这个方法也会返回组件原来的值（如果有的话），有些情况下可能会有用。 例如：

```java
int oldValue = stack.set(ModComponents.CLICK_COUNT_COMPONENT, newValue);
```

我们调用 `use()` 方法，先读旧的点击次数，增加一，然后设置新的点击次数。

@[code transcludeWith=::2](@/reference/latest/src/main/java/com/example/docs/item/custom/CounterItem.java)

尝试启动游戏并右键点击手中的计数器物品。 如果打开物品栏并再次看看物品，应该就会发现，使用次数随点击的次数增加了。

![显示“使用了 8 次”的物品提示](/assets/develop/items/custom_component_3.png)

## 移除组件值{#removing-component-value}

如果不再需要组件，可以将其从 `ItemStack` 移除， 方法就是使用 `remove()` 方法，接收你的组件的类型。

```java
stack.remove(ModComponents.CLICK_COUNT_COMPONENT);
```

这个方法也会返回组件移除之前的值，所以可以像这样使用它：

```java
int oldCount = stack.remove(ModComponents.CLICK_COUNT_COMPONENT);
```

## 高级数据组件{#advanced-data-components}

你可能需要将多个属性存储在单个组件中。 原版有些例子，比如 `minecraft:food` 组件存在几个和食品相关的值，例如 `nutrition`（营养）、`saturation`（饱和度）、`eat_seconds`（食用所需秒数）等。 本教程中我们将其称为“合成”组件。

对于合成组件，需要创建一个 `record`（记录）类来存储数据。 这是我们需要给组件类型注册的类型，与这个 `ItemStack` 交互时进行读写。 先在之前创建的 `component` 包中创建新的 record 类。

```java
public record MyCustomComponent() {
}
```

注意类名后有一组括号。 这里我们定义组件需要有的一系列的属性。 先添加一个浮点数和布尔值，分别叫做 `temperature` 和 `burnt`。

@[code transcludeWith=::1](@/reference/latest/src/main/java/com/example/docs/component/MyCustomComponent.java)

因为我们定义自定义的数据结构，所以这种情况下不会像[基本组件](#basic-data-components)那样有预先存在的 `Codec`。 也就是说，需要创建自己的 codec。 我们在 record 类中使用 `RecordCodecBuilder` 定义一个，这样在注册组件时就可以引用。 关于使用 `RecordCodecBuilder` 的更多细节，请参考 [Codec 页面的这个章节](../codecs#merging-codecs-for-record-like-classes)。

@[code transcludeWith=::2](@/reference/latest/src/main/java/com/example/docs/component/MyCustomComponent.java)

你可以看到我们基于原始的 `Codec` 类型定义了一系列的自定义字段。 我们用 `fieldOf()` 来说明我们的字段叫做什么，然后用 `forGetter()` 告诉游戏怎样从记录中获取该字段。

你也可以使用 `optionalFieldOf()` 来定义可选的字段，并在第二个参数传入默认值。 任何没有被标记为可选的字段使用 `/give` 设置组件时都是必需的，所以确保在创建我们的 codec 时，将任何可选的参数都标记为可选。

最后，调用 `apply()` 并传递这个 record 的构造方法。 关于构造 codec 的更多细节以及更深入的用法，可阅读 [Codecs](../codecs) 页面。

注册“合成”组件和之前类似， 就只需要传入我们的 record 类作为泛型类型，以及给 `codec()` 方法传入自定义的 `Codec`。

@[code transcludeWith=::3](@/reference/latest/src/main/java/com/example/docs/component/ModComponents.java)

现在启动游戏， 使用 `/give` 命令，尝试应用组件。 合成组件的值传入时是作为对象，用 `{}` 包围起来。 如果用空的花括号，会看到错误，提示你缺少必需的键 `temperature`。

![give 命令，显示缺少必需的键“temperature”](/assets/develop/items/custom_component_4.png)

使用语法 `temperature:8.2`，从而给对象添加 temperature 值。 你还可以为字段 `burnt` 也传入一个值，使用相同语法，不过是 `true` 或 `false`。 现在应该可以看到命令是有效的，会给你包含这个组件的物品。

![有效的 give 命令，显示两个属性](/assets/develop/items/custom_component_5.png)

### 获取、设置和移除高级的组件{#getting-setting-removing-advanced-comps}

在代码中使用组件和之前都是一样的。 使用 `stack.get()` 会返回你的 `record` 类的实例，可以用于读取值。 不过由于 `record` 都是只读的，所以要更新值时，需要创建你的 record 的新的实例。

```java
// read values of component
MyCustomComponent comp = stack.get(ModComponents.MY_CUSTOM_COMPONENT);
float temp = comp.temperature();
boolean burnt = comp.burnt();

// set new component values
stack.set(ModComponents.MY_CUSTOM_COMPONENT, new MyCustomComponent(8.4f, true));

// check for component
if (stack.contains(ModComponents.MY_CUSTOM_COMPONENT)) {
    // do something
}

// remove component
stack.remove(ModComponents.MY_CUSTOM_COMPONENT);
```

你也可以在你的 `Item.Settings` 中传入组件对象来为合成组件设置默认的值。 例如：

```java
public static final Item COUNTER = register(new CounterItem(
    new Item.Settings().component(ModComponents.MY_CUSTOM_COMPONENT, new MyCustomComponent(0.0f, false))
), "counter");
```

现在你就能够在 `ItemStack` 中存储自定义的数据。 好好使用吧！

![显示计数数量、温度和燃烧的物品提示的物品](/assets/develop/items/custom_component_6.png)
