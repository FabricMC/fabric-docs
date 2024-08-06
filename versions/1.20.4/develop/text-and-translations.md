---
title: Text and Translations
description: Comprehensive documentation for Minecraft's handling of formatted text and translations.
authors:
  - IMB11

search: false
---

# Text and Translations {#text-and-translations}

Whenever Minecraft displays text ingame, it's probably defined using a `Text` object.
This custom type is used instead of a `String` to allow for more advanced formatting,
including colors, boldness, obfuscation, and click events. They also allow easy access
to the translation system, making it simple to translate any interface elements into
different languages.

If you've worked with datapacks or functions before, you may see parallels with the
json text format used for displayNames, books, and signs among other things. As you
can probably guess, this is just a json representation of a `Text` object, and can be
converted to and from using `Text.Serializer`.

When making a mod, it is generally preferred to construct your `Text` objects directly
in code, making use of translations whenever possible.

## Text Literals {#text-literals}

The simplest way to create a `Text` object is to make a literal. This is just a string
that will be displayed as-is, by default without any formatting.

These are created using the `Text.of` or `Text.literal` methods, which both act slightly
differently. `Text.of` accepts nulls as input, and will return a `Text` instance. In
contrast, `Text.literal` should not be given a null input, but returns a `MutableText`,
this being a subclass of `Text` that can be easily styled and concatenated. More about
this later.

```java
Text literal = Text.of("Hello, world!");
MutableText mutable = Text.literal("Hello, world!");
// Keep in mind that a MutableText can be used as a Text, making this valid:
Text mutableAsText = mutable;
```

## Translatable Text {#translatable-text}

When you want to provide multiple translations for the same string of text, you can use the `Text.translatable` method to reference a translation key in any language file. If the key doesn't exist, the translation key is converted to a literal.

```java
Text translatable = Text.translatable("my_mod.text.hello");

// Similarly to literals, translatable text can be easily made mutable.
MutableText mutable = Text.translatable("my_mod.text.bye");
```

The language file, `en_us.json`, looks like the following:

```json
{
  "my_mod.text.hello": "Hello!",
  "my_mod.text.bye": "Goodbye :("
}
```

## Serializing Text {#serializing-text}

<!-- NOTE: These have been put into the reference mod as they're likely to be updated to codecs in the next few updates. -->

As mentioned before, you can serialize text to JSON using the following serializer class:

@[code transcludeWith=:::1](@/reference/1.20.4/src/client/java/com/example/docs/rendering/TextTests.java)

This produces JSON that can be used datapacks, commands and other places that accept the JSON format of text instead of literal or translatable text.

## Deserializing Text {#deserializing-text}

Furthermore, to deserialize a JSON text object into an actual `Text` class, you can use the `fromJson` method:

@[code transcludeWith=:::2](@/reference/1.20.4/src/client/java/com/example/docs/rendering/TextTests.java)

## Formatting Text {#formatting-text}

You may be familiar with Minecraft's formatting standards:

You can apply these formattings using the `Formatting` enum on the `MutableText` class:

```java
MutableText result = Text.literal("Hello World!")
  .formatted(Formatting.AQUA, Formatting.BOLD, Formatting.UNDERLINE);
```

<table>
    <tr><th>Color</th><th>Name</th><th>Chat Code</th><th>MOTD Code</th><th>Hex Code</th></tr>
    <tr><td><ColorSwatch color="#000000" /></td><td>Black (black)</td><td>§0</td><td>\u00A70</td><td>#000000</td></tr>
    <tr><td><ColorSwatch color="#0000AA" /></td><td>Dark Blue (dark_blue)</td><td>§1</td><td>\u00A71</td><td>#0000AA</td></tr>
    <tr><td><ColorSwatch color="#00AA00" /></td><td>Dark Green (dark_green)</td><td>§2</td><td>\u00A72</td><td>#00AA00</td></tr>
    <tr><td><ColorSwatch color="#00AAAA" /></td><td>Dark Aqua (dark_aqua)</td><td>§3</td><td>\u00A73</td><td>#00AAAA</td></tr>
    <tr><td><ColorSwatch color="#AA0000" /></td><td>Dark Red (dark_red)</td><td>§4</td><td>\u00A74</td><td>#AA0000</td></tr>
    <tr><td><ColorSwatch color="#AA00AA" /></td><td>Dark Purple (dark_purple)</td><td>§5</td><td>\u00A75</td><td>#AA00AA</td></tr>
    <tr><td><ColorSwatch color="#FFAA00" /></td><td>Gold (gold)</td><td>§6</td><td>\u00A76</td><td>#FFAA00</td></tr>
    <tr><td><ColorSwatch color="#AAAAAA"/></td><td>Gray (gray)</td><td>§7</td><td>\u00A77</td><td>#AAAAAA</td></tr>
    <tr><td><ColorSwatch color="#555555" /></td><td>Dark Gray (dark_gray)</td><td>§8</td><td>\u00A78</td><td>#555555</td></tr>
    <tr><td><ColorSwatch color="#5555FF" /></td><td>Blue (blue)</td><td>§9</td><td>\u00A79</td><td>#5555FF</td></tr>
    <tr><td><ColorSwatch color="#55FF55" /></td><td>Green (green)</td><td>§a</td><td>\u00A7a</td><td>#55FF55</td></tr>
    <tr><td><ColorSwatch color="#55FFFF" /></td><td>Aqua (aqua)</td><td>§b</td><td>\u00A7b</td><td>#55FFFF</td></tr>
    <tr><td><ColorSwatch color="#FF5555" /></td><td>Red (red)</td><td>§c</td><td>\u00A7c</td><td>#FF5555</td></tr>
    <tr><td><ColorSwatch color="#FF55FF" /></td><td>Light Purple (light_purple)</td><td>§d</td><td>\u00A7d</td><td>#FF55FF</td></tr>
    <tr><td><ColorSwatch color="#FFFF55" /></td><td>Yellow (yellow)</td><td>§e</td><td>\u00A7e</td><td>#FFFF55</td></tr>
    <tr><td><ColorSwatch color="#FFFFFF" /></td><td>White (white)</td><td>§f</td><td>\u00A7f</td><td>#FFFFFF</td></tr>
    <tr><td></td><td>Reset</td><td>§r</td><td></td><td></td></tr>
    <tr><td></td><td><b>Bold</b></td><td>§l</td><td></td><td></td></tr>
    <tr><td></td><td><s>Strikethrough</s></td><td>§m</td><td></td><td></td></tr>
    <tr><td></td><td><u>Underline</u></td><td>§n</td><td></td><td></td></tr>
    <tr><td></td><td><i>Italic</i></td><td>§o</td><td></td><td></td></tr>
    <tr><td></td><td>Obfuscated</td><td>§k</td><td></td><td></td></tr>
</table>
