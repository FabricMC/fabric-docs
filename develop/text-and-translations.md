---
title: Text and Translations
description: Comprehensive documentation for Minecraft's handling of formatted text and translations.
authors:
  - IMB11
  - LordEnder-Kitty
---

<!-- markdownlint-configure-file { MD033: { allowed_elements: [br, ColorSwatch, u] } } -->

Whenever Minecraft displays text in-game, it's probably defined using a `Text` object.
This custom type is used instead of a `String` to allow for more advanced formatting,
including colors, boldness, obfuscation, and click events. They also allow easy access
to the translation system, making it simple to translate any UI element into
different languages.

If you've worked with datapacks or functions before, you may see parallels with the
json text format used for displayNames, books, and signs among other things. As you
can probably guess, this is just a json representation of a `Component` object, and can be
converted to and from using `Component.Serializer`.

When making a mod, it is generally preferred to construct your `Component` objects directly
in code, making use of translations whenever possible.

## Literal Text Components {#text-literals}

The simplest way to create a `Component` object is to make a literal. This is just a string
that will be displayed as-is, by default without any formatting.

These are created using the `Component.nullToEmpty` or `Component.literal` methods, which both act slightly
differently. `Component.nullToEmpty` accepts nulls as input, and will return a `Component` instance. In
contrast, `Component.literal` should not be given a null input, but returns a `MutableComponent`,
this being a subclass of `Component` that can be easily styled and concatenated. More about
this later.

```java
Component literal = Component.nullToEmpty("Hello, world!");
MutableComponent mutable = Component.literal("Hello, world!");
// Keep in mind that a MutableComponent can be used as a Component, making this valid:
Component mutableAsText = mutable;
```

## Translatable Text Components {#translatable-text}

When you want to provide multiple translations for the same string of text, you can use the `Component.translatable` method to reference a translation key in any language file. If the key doesn't exist, the translation key is converted to a literal.

```java
Component translatable = Component.translatable("my_mod.text.hello");

// Similarly to literals, translatable text can be easily made mutable.
MutableComponent mutable = Component.translatable("my_mod.text.bye");
```

The language file, `en_us.json`, looks like the following:

```json
{
  "my_mod.text.hello": "Hello!",
  "my_mod.text.bye": "Goodbye :("
}
```

If you wish to be able to use variables in the translation, similar to how death messages allow you to use the involved players and items in the translation, you may add said variables as parameters. You may add however many parameters you like.

```java
Component translatable = Component.translatable("my_mod.text.hello", player.getDisplayName());
```

You may reference these variables in the translation like so:

```json
{
  "my_mod.text.hello": "%1$s said hello!"
}
```

In the game, %1\$s will be replaced with the name of the player you referenced in the code. Using `player.getDisplayName()` will make it so that additional information about the entity will appear in a tooltip when hovering over the name in the chat message as opposed to using `player.getName()`, which will still get the name; however, it will not show the extra details. Similar can be done with itemStacks, using `stack.getDisplayName()`.

As for what %1\$s even means, all you really need to know is that the number corresponds to which variable you are trying to use. Let's say you have three variables that you are using.

```java
Component translatable = Component.translatable("my_mod.text.whack.item", victim.getDisplayName(), attacker.getDisplayName(), itemStack.toHoverableText());
```

If you want to reference what, in our case, is the attacker, you would use %2\$s because it's the second variable that we passed in. Likewise, %3\$s refers to the itemStack. A translation with this many additional parameters might look like this:

```json
{
  "my_mod.text.whack.item": "%1$s was whacked by %2$s using %3$s"
}
```

## Serializing Text {#serializing-text}

<!-- NOTE: These have been put into the example mod as they're likely to be updated to codecs in the next few updates. -->

As mentioned before, you can serialize text to JSON using the text codec. For more information on codecs, see the [Codec](./codecs) page.

@[code transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/TextTests.java)

This produces JSON that can be used datapacks, commands and other places that accept the JSON format of text instead of literal or translatable text.

## Deserializing Text {#deserializing-text}

Furthermore, to deserialize a JSON text object into an actual `Text` class, again, use the codec.

@[code transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/TextTests.java)

## Formatting Text {#formatting-text}

You may be familiar with Minecraft's formatting standards:

You can apply these formatting styles using the `Formatting` enum on the `MutableComponent` class:

```java
MutableComponent result = Component.literal("Hello World!")
  .withStyle(ChatFormatting.AQUA, ChatFormatting.BOLD, ChatFormatting.UNDERLINE);
```

| Color                           | Name                             | Chat Code | MOTD Code | Hex Code  |
|:-------------------------------:|----------------------------------|:---------:|:---------:|:---------:|
| <ColorSwatch color="#000000" /> | Black<br />`black`               | `§0`      | `\u00A70` | `#000000` |
| <ColorSwatch color="#0000AA" /> | Dark Blue<br />`dark_blue`       | `§1`      | `\u00A71` | `#0000AA` |
| <ColorSwatch color="#00AA00" /> | Dark Green<br />`dark_green`     | `§2`      | `\u00A72` | `#00AA00` |
| <ColorSwatch color="#00AAAA" /> | Dark Aqua<br />`dark_aqua`       | `§3`      | `\u00A73` | `#00AAAA` |
| <ColorSwatch color="#AA0000" /> | Dark Red<br />`dark_red`         | `§4`      | `\u00A74` | `#AA0000` |
| <ColorSwatch color="#AA00AA" /> | Dark Purple<br />`dark_purple`   | `§5`      | `\u00A75` | `#AA00AA` |
| <ColorSwatch color="#FFAA00" /> | Gold<br />`gold`                 | `§6`      | `\u00A76` | `#FFAA00` |
| <ColorSwatch color="#AAAAAA" /> | Gray<br />`gray`                 | `§7`      | `\u00A77` | `#AAAAAA` |
| <ColorSwatch color="#555555" /> | Dark Gray<br />`dark_gray`       | `§8`      | `\u00A78` | `#555555` |
| <ColorSwatch color="#5555FF" /> | Blue<br />`blue`                 | `§9`      | `\u00A79` | `#5555FF` |
| <ColorSwatch color="#55FF55" /> | Green<br />`green`               | `§a`      | `\u00A7a` | `#55FF55` |
| <ColorSwatch color="#55FFFF" /> | Aqua<br />`aqua`                 | `§b`      | `\u00A7b` | `#55FFFF` |
| <ColorSwatch color="#FF5555" /> | Red<br />`red`                   | `§c`      | `\u00A7c` | `#FF5555` |
| <ColorSwatch color="#FF55FF" /> | Light Purple<br />`light_purple` | `§d`      | `\u00A7d` | `#FF55FF` |
| <ColorSwatch color="#FFFF55" /> | Yellow<br />`yellow`             | `§e`      | `\u00A7e` | `#FFFF55` |
| <ColorSwatch color="#FFFFFF" /> | White<br />`white`               | `§f`      | `\u00A7f` | `#FFFFFF` |
|                                 | Reset                            | `§r`      |           |           |
|                                 | **Bold**                         | `§l`      |           |           |
|                                 | ~~Strikethrough~~                | `§m`      |           |           |
|                                 | <u>Underline</u>                 | `§n`      |           |           |
|                                 | _Italic_                         | `§o`      |           |           |
|                                 | Obfuscated                       | `§k`      |           |           |
