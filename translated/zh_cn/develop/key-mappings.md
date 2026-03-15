---
title: 按键映射
description: 创建按键映射并进行反应。
authors:
  - cassiancc
  - dicedpixels
---

Minecraft 使用按键映射来处理来自像键盘、鼠标之类的外围设置的用户输入，
许多这些按键映射都可以通过设置菜单来配置。

借助 Fabric API 可以创建自己的自定义按键映射，并在自己的模组中进行反应。

按键映射仅存在于客户端， 这意味着按键映射的注册和反应都仅应在客户端完成。 因此可以使用**客户端初始化器**（client initializer）。

## 创建按键映射 {#creating-a-key-mapping}

按键映射包含两部分：按键的映射，以及其属于的分类。

先开始创建一个分类。 分类定义了一组会在设置菜单中显示在一起的按键映射。

@[code lang=java transcludeWith=:::category](@/reference/latest/src/client/java/com/example/docs/keymapping/ExampleModKeyMappingsClient.java)

然后，创建一个按键映射。 我们使用 Fabric API 的 `KeyBindingHelper` 以同时注册我们的按键映射。

@[code lang=java transcludeWith=:::key_mapping](@/reference/latest/src/client/java/com/example/docs/keymapping/ExampleModKeyMappingsClient.java)

::: info

注意按键的名称（`GLFW.GLFW_KEY_*`）会假定我们使用的是[标准美式布局](https://upload.wikimedia.org/wikipedia/commons/d/da/KB_United_States.svg)。

这意味着如果使用的是 AZERTY 布局，按下 <kbd>A</kbd> 可能会产生 `GLFW.GLFW_KEY_Q`。

:::

也可以传入 `ToggleKeyMapping` 而非 `KeyMapping`，从而使用 `KeyBindingHelper` 创建粘滞键。

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

## 对按键映射作出反应{#reacting-to-key-mappings}

现在有了按键映射，就可以使用客户端刻事件对其反应。

@[code lang=java transcludeWith=:::client_tick_event](@/reference/latest/src/client/java/com/example/docs/keymapping/ExampleModKeyMappingsClient.java)

这会在每次按下被映射的键时，游戏内聊天栏就会输出“Key Pressed!”。 记住，按住此键会反复向聊天栏输出消息，所以如果这个逻辑只需要触发一次，可能需要实现保护机制。

![聊天栏内的消息](/assets/develop/key-mappings/key_mapping_pressed.png)
