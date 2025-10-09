---
title: 文本和翻译
description: Minecraft 处理格式化文本和翻译的全面文档。
authors:
  - IMB11
  - LordEnder-Kitty
---

<!-- markdownlint-configure-file { MD033: { allowed_elements: [br, ColorSwatch, u] } } -->

Minecraft 不论何时在游戏内显示文本，都是使用 `Text` 对象定义的。
使用这种自定义的类型而非 `String`，是为了允许更多高级的格式化，包括颜色、加粗、混淆和点击事件。 这样还能够容易地访问翻译系统，使得将任何 UI 元素翻译成不同语言都变得容易。

如果以前有做过数据包和函数，应该看到用于displayName、书、告示牌等内容的就是用的 json 文本格式。 不难猜到，这就是 `Text` 对象的 json 呈现，可以使用 `Text.Serializer` 互相转换。

制作模组时，最好直接在代码中构造你的 `Text` 对象，并随时利用翻译。

## 字面文本{#text-literals}

这是创建 `Text` 对象最简单的方式，创建字面值。 这就是会照样显示的字符串，默认没有任何格式化。

这些是使用 `Text.of` 或 `Text.literal` 方法创建的，这两个行为有点不同。 `Text.of` 接受 null 输入，返回 `Text` 实例。 `Text.literal` 不同，不能有空输入，返回的是 `MutableText`，是 `Text` 的子类，可以轻易地格式化和连接。 这个后面会有更多。

```java
Text literal = Text.of("Hello, world!");
MutableText mutable = Text.literal("Hello, world!");
// Keep in mind that a MutableText can be used as a Text, making this valid:
Text mutableAsText = mutable;
```

## 可翻译文本{#translatable-text}

给相同的文本字符串提供多个翻译时，可以使用 `Text.translatable` 方法，引用语言文件中的任意翻译键。 如果翻译键不存在，则字面转换翻译键。

```java
Text translatable = Text.translatable("my_mod.text.hello");

// Similarly to literals, translatable text can be easily made mutable.
MutableText mutable = Text.translatable("my_mod.text.bye");
```

语言文件 `en_us.json`（简体中文为 `zh_cn.json`），看上去应该像这样：

```json
{
  "my_mod.text.hello": "Hello!",
  "my_mod.text.bye": "Goodbye :("
}
```

如果想在翻译中使用变量（类似于在死亡消息中可以在翻译中使用涉及的玩家和物品），可以将这些变量作为参数添加进去。 想添加多少参数都可以。

```java
Text translatable = Text.translatable("my_mod.text.hello", player.getDisplayName());
```

在翻译中，可以像这样引用这些变量：

```json
{
  "my_mod.text.hello": "%1$s said hello!"
}
```

在游戏中，%1\$s 会被替换为你在代码中引用的玩家的名字。 使用 `player.getDisplayName()` 会使用鼠标悬停在聊天消息中的名字上时，以提示框形式出现实体的额外信息，相比之下使用 `player.getName()` 只会得到名字但不显示额外细节。 对物品堆也是类似，使用 `stack.toHoverableText()`。

至于 %1\$s 都是指什么，你要知道的就是数字对应的你尝试使用的哪个变量。 比如说你有使用三个变量。

```java
Text translatable = Text.translatable("my_mod.text.whack.item", victim.getDisplayName(), attacker.getDisplayName(), itemStack.toHoverableText());
```

如果要引用，比如在这里是引用谁是攻击者，应该使用 %2\$s，因为这是我们传入的第二个变量。 类似地，%3\$s 引用的是物品堆。 有这些额外参数的翻译可能会像这样：

```json
{
  "my_mod.text.whack.item": "%1$s was whacked by %2$s using %3$s"
}
```

## 序列化文本{#serializing-text}

<!-- NOTE: These have been put into the reference mod as they're likely to be updated to codecs in the next few updates. -->

前面提到过，可以使用 text codec 将文本序列化为 JSON。 更多关于 codec 的信息，请看 [Codec](./codecs) 页面。

@[code transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/TextTests.java)

这会产生可用于数据包、命令和其他接受 JSON 格式文本而非字面或可翻译文本的地方的 JSON。

## 反序列化文本{#deserializing-text}

要将 JSON 文本对象反序列化为 `Text` 类，还是使用 codec。

@[code transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/TextTests.java)

## 格式化文本{#formatting-text}

你应该熟悉 Minecraft 的格式化标准：

可以对 `MutableText` 类使用 `Formatting` 枚举以应用这些格式样式：

```java
MutableText result = Text.literal("Hello World!")
  .formatted(Formatting.AQUA, Formatting.BOLD, Formatting.UNDERLINE);
```

|                颜色               | 名称                                     | 聊天代码 |   MOTD 代码  |   十六进制代码  |
| :-----------------------------: | -------------------------------------- | :--: | :--------: | :-------: |
| <ColorSwatch color="#000000" /> | 黑色（black）                              |  §0  | `\u00A70` |  #000000  |
| <ColorSwatch color="#0000AA" /> | 深蓝色（dark_blue）    |  §1  | `\u00A71` | `#0000AA` |
| <ColorSwatch color="#00AA00" /> | 深绿色（dark_green）   |  §2  | `\u00A72` |  #00AA00  |
| <ColorSwatch color="#00AAAA" /> | 深青色（dark_aqua）    |  §3  | `\u00A73` |  #00AAAA  |
| <ColorSwatch color="#AA0000" /> | 深红色（dark_red）     |  §4  | `\u00A74` |  #AA0000  |
| <ColorSwatch color="#AA00AA" /> | 深紫色（dark_purple）  |  §5  | `\u00A75` |  #AA00AA  |
| <ColorSwatch color="#FFAA00" /> | 金色（gold）                               |  §6  | `\u00A76` |  #FFAA00  |
| <ColorSwatch color="#AAAAAA" /> | 灰色（gray）                               |  §7  | `\u00A77` |  #AAAAAA  |
| <ColorSwatch color="#555555" /> | 深灰色（dark_gray）    |  §8  | `\u00A78` |  #555555  |
| <ColorSwatch color="#5555FF" /> | 蓝色（blue）                               |  §9  | `\u00A79` |  #5555FF  |
| <ColorSwatch color="#55FF55" /> | 绿色（green）                              |  §a  | `\u00A7a` |  #55FF55  |
| <ColorSwatch color="#55FFFF" /> | 青色（aqua）                               |  §b  | `\u00A7b` |  #55FFFF  |
| <ColorSwatch color="#FF5555" /> | 红色（red）                                |  §c  | `\u00A7c` |  #FF5555  |
| <ColorSwatch color="#FF55FF" /> | 淡紫色（light_purple） |  §d  | `\u00A7d` |  #FF55FF  |
| <ColorSwatch color="#FFFF55" /> | 黄色（yellow）                             |  §e  | `\u00A7e` |  #FFFF55  |
| <ColorSwatch color="#FFFFFF" /> | 白色（white）                              |  §f  | `\u00A7f` |  #FFFFFF  |
|                                 | 重置                                     |  §r  |            |           |
|                                 | **粗体**                                 |  §l  |            |           |
|                                 | ~~删除线~~                                |  §m  |            |           |
|                                 | <u>下划线</u>                             |  §n  |            |           |
|                                 | _斜体_                                   |  §o  |            |           |
|                                 | 混淆                                     |  §k  |            |           |
