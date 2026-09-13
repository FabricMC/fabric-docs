---
title: 按键映射
description: 创建按键映射并进行反应。
authors:
  - cassiancc
  - dicedpixels
  - its-miroma
  - NotNightSky
resources:
  https://upload.wikimedia.org/wikipedia/commons/d/da/KB_United_States.svg: 标准美式键盘布局
---

Minecraft 使用按键映射来处理来自像键盘、鼠标之类的外围设置的用户输入，
许多这些按键映射都可以通过设置菜单来配置。

借助 Fabric API 可以创建自己的自定义按键映射，并在自己的模组中进行反应。

按键映射仅存在于客户端， 这意味着按键映射的注册和反应都仅应在客户端完成。 因此可以使用**客户端初始化器**（client initializer）。

## 创建按键映射 {#creating-a-key-mapping}

按键映射包含两部分：按键的映射，以及其属于的分类。

先开始创建一个分类。 分类定义了一组会在设置菜单中显示在一起的按键映射。

<<< @/reference/latest/src/client/java/com/example/docs/keymapping/ExampleModKeyMappingsClient.java#category

然后，创建一个按键映射。 我们将使用 Fabric API 的 `KeyMappingHelper` 来注册按键映射。

<<< @/reference/latest/src/client/java/com/example/docs/keymapping/ExampleModKeyMappingsClient.java#key_mapping

::: info

请注意，按键标识符（`InputConstants.KEY_*`）的名称假定使用[标准美式键盘布局](https://upload.wikimedia.org/wikipedia/commons/d/da/KB_United_States.svg)。

这意味着如果你使用的是 AZERTY 布局，按下 <kbd>A</kbd> 键将返回 `InputConstants.KEY_Q`。

:::

也可以通过传递 `ToggleKeyMapping` 实例而不是 `KeyMapping` 实例，使用 `KeyMappingHelper` 创建粘滞键。

一旦注册，就可以在 _选择_ > _控制_ > _按键绑定_ 中找到你的按键映射。

![未翻译的按键分类和映射](/assets/develop/key-mappings/untranslated.png)

## 翻译 {#translations}

你会需要为按键映射以及分类提供翻译。

分类名称的翻译键是 `key.category.<namespace>.<path>` 的形式。 创建按键映射时，按键映射的翻译键会是你提供的。

可以手动添加翻译键，也可借助[数据生成](./data-generation/translations)。

```json
{
  "key.category.example-mod.custom_category": "Example Mod Custom Category",
  "key.example-mod.send_to_chat": "Send to Chat"
}
```

![翻译的按键分类和映射](/assets/develop/key-mappings/translated.png)

## 对世界内按键映射做出反应 {#reacting-to-key-mappings-in-world}

现在我们已经有了按键映射，如果想在游戏运行时对其做出反应，我们可以使用客户端刻事件：

<<< @/reference/latest/src/client/java/com/example/docs/keymapping/ExampleModKeyMappingsClient.java#client_tick_event

每次按下映射的按键时，都会在游戏内聊天中打印“
Key press detected in the world”的信息。 记住，按住此键会反复向聊天栏输出消息，所以如果这个逻辑只需要触发一次，可能需要实现保护机制。

![聊天栏内的消息](/assets/develop/key-mappings/key_mapping_pressed.png)

## 对图形用户界面中的按键映射做出反应 {#reacting-to-key-mappings-in-gui}

我们还可以根据屏幕内的按键映射做出反应，无论游戏世界是否打开。

<<< @/reference/latest/src/client/java/com/example/docs/keymapping/ExampleModKeyMappingsClient.java#screen_before_init_event

然后添加这两个处理程序：

<<< @/reference/latest/src/client/java/com/example/docs/keymapping/ExampleModKeyMappingsClient.java#helper_methods

这会检查当前屏幕是 `TitleScreen` 还是 `CreativeModeInventoryScreen`。 如果是这样，我们会根据身处世界内还是世界外来实现两种截然不同的行为：

- 换句话说，当玩家不在游戏世界中时，它会将“ Key press detected in the title screen”记录到控制台。
- 否则，如果在游戏世界中按下该键，它会向游戏内聊天发送“ Key press detected in the GUI with a world open, closing screen”的消息，并关闭屏幕。

<VideoPlayer src="/assets/develop/key-mappings/in_screen_key_map.webm">在打开世界的情况下，在图形用户界面中按下按键</VideoPlayer>

::: info

由于之前注册了 `clientTickEvents` 事件监听器，因此向聊天发送了第二个“ Key press detected in the world”消息。

:::

::: tip

在生存模式下，`screen` 将是 `InventoryScreen` 的一个实例；而在创造模式下，它将是 `CreativeModeInventoryScreen` 的一个实例。

如果需要，您可以移除 `screen instanceof` 检查，以便将事件监听器连接到所有屏幕。

:::

<!---->
