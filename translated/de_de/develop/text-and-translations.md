---
title: Text und Übersetzungen
description: Umfassende Dokumentation für Minecraft's Umgang mit formatiertem Text und Übersetzungen.
authors:
  - IMB11
---

# Text und Übersetzungen {#text-and-translations}

Wann immer Minecraft Text im Spiel anzeigt, wird dieser wahrscheinlich mit einem `Text`-Objekt definiert.
Dieser benutzerdefinierte Typ wird anstelle eines `String` verwendet, um eine erweiterte Formatierung zu ermöglichen, einschließlich Farben, Fettdruck, Verschleierung und Klickereignisse. Sie ermöglichen auch einen einfachen Zugriff auf das Übersetzungssystem, so dass beliebige Elemente der Benutzeroberfläche problemlos in verschiedene Sprachen übersetzt werden können.

Wenn du schon einmal mit Datapacks oder Funktionen gearbeitet hast, siehst du vielleicht Parallelen zum JSON-Textformat, das unter anderem für Displaynamen, Bücher und Schilder verwendet wird. Wie du vermutlich denken kannst, handelt es sich dabei um eine Json-Darstellung eines `Text`-Objekts, welche mit Hilfe eines `Text.Serializer` umgewandelt werden kann.

Bei der Erstellung eines Mods ist es im Allgemeinen vorzuziehen, die `Text`-Objekte direkt im Code zu konstruieren und dabei nach Möglichkeit Übersetzungen zu verwenden.

## Text-Literale {#text-literals}

Der einfachste Weg, ein `Text`-Objekt zu erzeugen, ist die Erstellung eines Literals. Dies ist nur eine Zeichenkette, die standardmäßig ohne Formatierung angezeigt wird.

Diese werden mit den Methoden `Text.of` oder `Text.literal` erstellt, die beide leicht unterschiedlich funktionieren. `Text.of` akzeptiert null als Eingabe, und wird eine `Text` Instanz zurückgeben. Im Gegensatz dazu sollte `Text.literal` keine Nulleingabe erhalten, sondern einen `MutableText` zurückgeben, der eine Unterklasse von `Text` ist, die leicht gestylt und verkettet werden kann. Mehr darüber später.

```java
Text literal = Text.of("Hello, world!");
MutableText mutable = Text.literal("Hello, world!");
// Keep in mind that a MutableText can be used as a Text, making this valid:
Text mutableAsText = mutable;
```

## Übersetzbarer Text {#translatable-text}

Wenn du mehrere Übersetzungen für dieselbe Textzeichenfolge bereitstellen willst, kannst du die Methode `Text.translatable` verwenden, um auf einen Übersetzungsschlüssel in einer beliebigen Sprachdatei zu verweisen. Wenn der Schlüssel nicht existiert, wird der Übersetzungsschlüssel in ein Literal umgewandelt.

```java
Text translatable = Text.translatable("my_mod.text.hello");

// Similarly to literals, translatable text can be easily made mutable.
MutableText mutable = Text.translatable("my_mod.text.bye");
```

Die Sprachdatei `en_us.json` sieht wie folgt aus:

```json
{
  "my_mod.text.hello": "Hello!",
  "my_mod.text.bye": "Goodbye :("
}
```

## Text serialisieren {#serializing-text}

<!-- NOTE: These have been put into the reference mod as they're likely to be updated to codecs in the next few updates. -->

Wie bereits erwähnt, kann Text mit dem Text Codec in JSON serialisiert werden. Weitere Informationen über Codecs findest du auf der Seite [Codec](./codecs).

@[code transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/TextTests.java)

Dadurch wird JSON erzeugt, das in Datenpaketen, Befehlen und an anderen Stellen verwendet werden kann, die das JSON-Format von Text anstelle von literalen oder übersetzbarem Text akzeptieren.

## Text deserialisieren {#deserializing-text}

Um ein JSON-Textobjekt in eine tatsächliche `Text`-Klasse zu deserialisieren, ist ebenfalls der Codec zu verwenden.

@[code transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/TextTests.java)

## Text formatieren {#formatting-text}

Du bist vielleicht mit den Formatierungsstandards von Minecraft vertraut:

Du kannst diese Formatierungen mit Hilfe des Enum `Formatting` auf die Klasse `MutableText` anwenden:

```java
MutableText result = Text.literal("Hello World!")
  .formatted(Formatting.AQUA, Formatting.BOLD, Formatting.UNDERLINE);
```

<table>
    <tbody><tr><th>Farbe</th><th>Name</th><th>Chat Code</th><th>MOTD Code</th><th>Hec Code</th></tr>
    <tr><td><ColorSwatch color="#000000" /></td><td>Schwarz (black)</td><td>§0</td><td>\u00A70</td><td>#000000</td></tr>
    <tr><td><ColorSwatch color="#0000AA" /></td><td>Dunkelblau (dark_blue)</td><td>§1</td><td>\u00A71</td><td>#0000AA</td></tr>
    <tr><td><ColorSwatch color="#00AA00" /></td><td>Dunkengrün (dark_green)</td><td>§2</td><td>\u00A72</td><td>#00AA00</td></tr>
    <tr><td><ColorSwatch color="#00AAAA" /></td><td>Dunkles Aquamarin (dark_aqua)</td><td>§3</td><td>\u00A73</td><td>#00AAAA</td></tr>
    <tr><td><ColorSwatch color="#AA0000" /></td><td>Dunkelrot (dark_red)</td><td>§4</td><td>\u00A74</td><td>#AA0000</td></tr>
    <tr><td><ColorSwatch color="#AA00AA" /></td><td>Dunkelviolett (dark_purple)</td><td>§5</td><td>\u00A75</td><td>#AA00AA</td></tr>
    <tr><td><ColorSwatch color="#FFAA00" /></td><td>Gold (gold)</td><td>§6</td><td>\u00A76</td><td>#FFAA00</td></tr>
    <tr><td><ColorSwatch color="#AAAAAA"/></td><td>Grau (gray)</td><td>§7</td><td>\u00A77</td><td>#AAAAAA</td></tr>
    <tr><td><ColorSwatch color="#555555" /></td><td>Dunkelgrau (dark_gray)</td><td>§8</td><td>\u00A78</td><td>#555555</td></tr>
    <tr><td><ColorSwatch color="#5555FF" /></td><td>Blau (blue)</td><td>§9</td><td>\u00A79</td><td>#5555FF</td></tr>
    <tr><td><ColorSwatch color="#55FF55" /></td><td>Grün (green)</td><td>§a</td><td>\u00A7a</td><td>#55FF55</td></tr>
    <tr><td><ColorSwatch color="#55FFFF" /></td><td>Aquamarin (aqua)</td><td>§b</td><td>\u00A7b</td><td>#55FFFF</td></tr>
    <tr><td><ColorSwatch color="#FF5555" /></td><td>Rot (red)</td><td>§c</td><td>\u00A7c</td><td>#FF5555</td></tr>
    <tr><td><ColorSwatch color="#FF55FF" /></td><td>Hellviolett (light_purple)</td><td>§d</td><td>\u00A7d</td><td>#FF55FF</td></tr>
    <tr><td><ColorSwatch color="#FFFF55" /></td><td>Gelb (yellow)</td><td>§e</td><td>\u00A7e</td><td>#FFFF55</td></tr>
    <tr><td><ColorSwatch color="#FFFFFF" /></td><td>Weiß (white)</td><td>§f</td><td>\u00A7f</td><td>#FFFFFF</td></tr>
    <tr><td></td><td>Zurücksetzen</td><td>§r</td><td></td><td></td></tr>
    <tr><td></td><td><b>Fett</b></td><td>§l</td><td></td><td></td></tr>
    <tr><td></td><td><s>Durchgestrichen</s></td><td>§m</td><td></td><td></td></tr>
    <tr><td></td><td><u>Unterstrichen</u></td><td>§n</td><td></td><td></td></tr>
    <tr><td></td><td><i>Kursiv</i></td><td>§o</td><td></td><td></td></tr>
    <tr><td></td><td>Verschleiert</td><td>§k</td><td></td><td></td></tr>
</tbody></table>
