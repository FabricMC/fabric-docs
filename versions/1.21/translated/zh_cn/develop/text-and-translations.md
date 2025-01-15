---
title: 文本和翻译
description: Minecraft 处理格式化文本和翻译的全面文档。
authors:
  - IMB11
  - LordEnder-Kitty
---

# 文本和翻译{#text-and-translations}

Minecraft 在游戏内显示文本，不论何时，都是使用 `Text` 对象定义的。
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

语言文件，`en_us.json`，看上去应该像这样：

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

@[code transcludeWith=:::1](@/reference/1.21/src/client/java/com/example/docs/rendering/TextTests.java)

这会产生可用于数据包、命令和其他接受 JSON 格式文本而非字面或可翻译文本的地方的 JSON。

## 反序列化文本{#deserializing-text}

要将 JSON 文本对象反序列化为 `Text` 类，还是使用 codec。

@[code transcludeWith=:::2](@/reference/1.21/src/client/java/com/example/docs/rendering/TextTests.java)

## 格式化文本{#formatting-text}

你应该熟悉 Minecraft 的格式化标准：

可以对 `MutableText` 类使用 `Formatting` 枚举以应用这些格式。

```java
MutableText result = Text.literal("Hello World!")
  .formatted(Formatting.AQUA, Formatting.BOLD, Formatting.UNDERLINE);
```

<table>
    <tbody><tr><th>颜色</th><th>名称</th><th>聊天代码</th><th>MOTD 代码</th><th>十六进制代码</th></tr>
    <tr><td><ColorSwatch color="#000000" /></td><td>黑色（black）</td><td>§0</td><td>\u00A70</td><td>#000000</td></tr>
    <tr><td><ColorSwatch color="#0000AA" /></td><td>深蓝色（dark_blue）</td><td>§1</td><td>\u00A71</td><td>#0000AA</td></tr>
    <tr><td><ColorSwatch color="#00AA00" /></td><td>深绿色（dark_green）</td><td>§2</td><td>\u00A72</td><td>#00AA00</td></tr>
    <tr><td><ColorSwatch color="#00AAAA" /></td><td>深青色（dark_aqua）</td><td>§3</td><td>\u00A73</td><td>#00AAAA</td></tr>
    <tr><td><ColorSwatch color="#AA0000" /></td><td>深红色（dark_red）</td><td>§4</td><td>\u00A74</td><td>#AA0000</td></tr>
    <tr><td><ColorSwatch color="#AA00AA" /></td><td>深紫色（dark_purple）</td><td>§5</td><td>\u00A75</td><td>#AA00AA</td></tr>
    <tr><td><ColorSwatch color="#FFAA00" /></td><td>金色（gold）</td><td>§6</td><td>\u00A76</td><td>#FFAA00</td></tr>
    <tr><td><ColorSwatch color="#AAAAAA"/></td><td>灰色（gray）</td><td>§7</td><td>\u00A77</td><td>#AAAAAA</td></tr>
    <tr><td><ColorSwatch color="#555555" /></td><td>深灰色（dark_gray）</td><td>§8</td><td>\u00A78</td><td>#555555</td></tr>
    <tr><td><ColorSwatch color="#5555FF" /></td><td>蓝色（blue）</td><td>§9</td><td>\u00A79</td><td>#5555FF</td></tr>
    <tr><td><ColorSwatch color="#55FF55" /></td><td>绿色（green）</td><td>§a</td><td>\u00A7a</td><td>#55FF55</td></tr>
    <tr><td><ColorSwatch color="#55FFFF" /></td><td>青色（aqua）</td><td>§b</td><td>\u00A7b</td><td>#55FFFF</td></tr>
    <tr><td><ColorSwatch color="#FF5555" /></td><td>红色（red）</td><td>§c</td><td>\u00A7c</td><td>#FF5555</td></tr>
    <tr><td><ColorSwatch color="#FF55FF" /></td><td>淡紫色（light_purple）</td><td>§d</td><td>\u00A7d</td><td>#FF55FF</td></tr>
    <tr><td><ColorSwatch color="#FFFF55" /></td><td>黄色（yellow）</td><td>§e</td><td>\u00A7e</td><td>#FFFF55</td></tr>
    <tr><td><ColorSwatch color="#FFFFFF" /></td><td>白色（white）</td><td>§f</td><td>\u00A7f</td><td>#FFFFFF</td></tr>
    <tr><td></td><td>重置</td><td>§r</td><td></td><td></td></tr>
    <tr><td></td><td><b>加粗</b></td><td>§l</td><td></td><td></td></tr>
    <tr><td></td><td><s>删除线</s></td><td>§m</td><td></td><td></td></tr>
    <tr><td></td><td><u>下划线</u></td><td>§n</td><td></td><td></td></tr>
    <tr><td></td><td><i>斜体</i></td><td>§o</td><td></td><td></td></tr>
    <tr><td></td><td>混淆</td><td>§k</td><td></td><td></td></tr>
</tbody></table>
