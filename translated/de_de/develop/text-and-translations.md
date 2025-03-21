---
title: Text und Übersetzungen
description: Umfassende Dokumentation für Minecraft's Umgang mit formatiertem Text und Übersetzungen.
authors:
  - IMB11
  - LordEnder-Kitty
---

<!-- markdownlint-configure-file { MD033: { allowed_elements: [br, ColorSwatch, u] } } -->

Wann immer Minecraft Text im Spiel anzeigt, wird dieser wahrscheinlich mit einem `Text`-Objekt definiert.
Dieser benutzerdefinierte Typ wird anstelle eines `String` verwendet, um eine erweiterte Formatierung zu ermöglichen, einschließlich Farben, Fettdruck, Verschleierung und Klickereignisse. Sie ermöglichen auch einen einfachen Zugriff auf das Übersetzungssystem, so dass beliebige Elemente der Benutzeroberfläche problemlos in verschiedene Sprachen übersetzt werden können.

Wenn du schon einmal mit Datapacks oder Funktionen gearbeitet hast, siehst du vielleicht Parallelen zum JSON-Textformat, das unter anderem für Anzeigenamen, Bücher und Schilder verwendet wird. Wie du dir vermutlich denken kannst, handelt es sich dabei nur um eine JSON-Darstellung eines `Text`-Objekts, welche mit Hilfe eines `Text.Serializer` umgewandelt werden kann.

Bei der Erstellung eines Mods ist es im Allgemeinen vorzuziehen, die `Text`-Objekte direkt im Code zu konstruieren und dabei nach Möglichkeit Übersetzungen zu verwenden.

## Text-Literale {#text-literals}

Der einfachste Weg, ein `Text`-Objekt zu erzeugen, ist die Erstellung eines Literals. Dies ist nur eine Zeichenkette, die standardmäßig ohne Formatierung angezeigt wird.

Diese werden mit den Methoden `Text.of` oder `Text.literal` erstellt, die beide leicht unterschiedlich funktionieren. `Text.of` akzeptiert null als Eingabe, und wird eine `Text` Instanz zurückgeben. Im Gegensatz dazu sollte `Text.literal` keine Nulleingabe erhalten, sondern einen `MutableText` zurückgeben, der eine Unterklasse von `Text` ist, die leicht gestylt und verkettet werden kann. Mehr dazu später.

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

Wenn du in der Lage sein willst, Variablen in der Übersetzung zu verwenden, ähnlich wie Todesnachrichten es erlauben, die beteiligten Spieler und Items in der Übersetzung zu verwenden, kannst du diese Variablen als Parameter hinzufügen. Du kannst so viele Parameter hinzufügen, wie du willst.

```java
Text translatable = Text.translatable("my_mod.text.hello", player.getDisplayName());
```

Du kannst diese Variablen in der Übersetzung wie folgt referenzieren:

```json
{
  "my_mod.text.hello": "%1$s said hello!"
}
```

Im Spiel wird %1\$s durch den Namen des Spielers ersetzt, auf den du im Code verwiesen hast. Die Verwendung von `player.getDisplayName()` bewirkt, dass zusätzliche Informationen über die Entität in einem Tooltip erscheinen, wenn der Mauszeiger über den Namen in der Chat-Nachricht bewegt wird, im Gegensatz zur Verwendung von `player.getName()`, die zwar den Namen ermittelt, aber keine zusätzlichen Details anzeigt. Ähnliches kann mit ItemStacks gemacht werden, indem `stack.toHoverableText()` verwendet wird.

Was %1\$s überhaupt bedeutet, musst du nur wissen, dass die Zahl der Variablen entspricht, die du zu verwenden versuchst. Nehmen wir an, du hast drei Variablen, die du verwendest.

```java
Text translatable = Text.translatable("my_mod.text.whack.item", victim.getDisplayName(), attacker.getDisplayName(), itemStack.toHoverableText());
```

Wenn du darauf verweisen willst, was in unserem Fall der Angreifer ist, würdest du %2\$s verwenden, weil es die zweite Variable ist, die wir übergeben haben. Ebenso bezieht sich %3\$s auf den ItemStack. Eine Übersetzung mit so vielen zusätzlichen Parametern könnte wie folgt aussehen:

```json
{
  "my_mod.text.whack.item": "%1$s was whacked by %2$s using %3$s"
}
```

## Serialisieren von Text {#serializing-text}

<!-- NOTE: These have been put into the reference mod as they're likely to be updated to codecs in the next few updates. -->

Wie bereits erwähnt, kann Text mit dem Text Codec in JSON serialisiert werden. Weitere Informationen über Codecs findest du auf der Seite [Codec](./codecs).

@[code transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/TextTests.java)

Dadurch wird JSON erzeugt, das in Datenpaketen, Befehlen und an anderen Stellen verwendet werden kann, die das JSON-Format von Text anstelle von literalen oder übersetzbarem Text akzeptieren.

## Deserialisieren von Text {#deserializing-text}

Um ein JSON-Textobjekt in eine tatsächliche `Text`-Klasse zu deserialisieren, ist ebenfalls der Codec zu verwenden.

@[code transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/TextTests.java)

## Formatieren von Text {#formatting-text}

Du bist vielleicht mit den Formatierungsstandards von Minecraft vertraut:

Du kannst diese Formatierungen mit Hilfe des Enum `Formatting` auf die Klasse `MutableText` anwenden:

```java
MutableText result = Text.literal("Hello World!")
  .formatted(Formatting.AQUA, Formatting.BOLD, Formatting.UNDERLINE);
```

|              Farbe              | Name                                                      | Chat Code |  MOTD Code |  Hex Code |
| :-----------------------------: | --------------------------------------------------------- | :-------: | :--------: | :-------: |
| <ColorSwatch color="#000000" /> | Schwarz<br />`black`                                      |    `§0`   | `\u00A70` | `#000000` |
| <ColorSwatch color="#0000AA" /> | Dunkelblau<br />`dark_blue`                               |    `§1`   | `\u00A71` | `#0000AA` |
| <ColorSwatch color="#00AA00" /> | Dunkelgrün<br />`dark_green`                              |    `§2`   | `\u00A72` | `#00AA00` |
| <ColorSwatch color="#00AAAA" /> | Dunkles Aquamarin<br />\\`dark_aqua |    `§3`   | `\u00A73` | `#00AAAA` |
| <ColorSwatch color="#AA0000" /> | Dunkelrot<br />`dark_red`                                 |    `§4`   | `\u00A74` | `#AA0000` |
| <ColorSwatch color="#AA00AA" /> | Dunkelviolett<br />`dark_purple`                          |    `§5`   | `\u00A75` | `#AA00AA` |
| <ColorSwatch color="#FFAA00" /> | Gold<br />`gold`                                          |    `§6`   | `\u00A76` | `#FFAA00` |
| <ColorSwatch color="#AAAAAA" /> | Grau<br />`gray`                                          |    `§7`   | `\u00A77` | `#AAAAAA` |
| <ColorSwatch color="#555555" /> | Dunkelgrau<br />`dark_gray`                               |    `§8`   | `\u00A78` | `#555555` |
| <ColorSwatch color="#5555FF" /> | Blau<br />`blue`                                          |    `§9`   | `\u00A79` | `#5555FF` |
| <ColorSwatch color="#55FF55" /> | Grün<br />`green`                                         |    `§a`   | `\u00A7a` | `#55FF55` |
| <ColorSwatch color="#55FFFF" /> | Aquamarin<br />`aqua`                                     |    `§b`   | `\u00A7b` | `#55FFFF` |
| <ColorSwatch color="#FF5555" /> | Rot<br />`red`                                            |    `§c`   | `\u00A7c` | `#FF5555` |
| <ColorSwatch color="#FF55FF" /> | Hellviolett<br />`light_purple`                           |    `§d`   | `\u00A7d` | `#FF55FF` |
| <ColorSwatch color="#FFFF55" /> | Gelb<br />`yellow`                                        |    `§e`   | `\u00A7e` | `#FFFF55` |
| <ColorSwatch color="#FFFFFF" /> | Weiß<br />`white`                                         |    `§f`   | `\u00A7f` | `#FFFFFF` |
|                                 | Zurücksetzen                                              |    `§r`   |            |           |
|                                 | **Fett**                                                  |    `§l`   |            |           |
|                                 | ~~Durchgestrichen~~                                       |    `§m`   |            |           |
|                                 | <u>Unterstrichen</u>                                      |    `§n`   |            |           |
|                                 | _Kursiv_                                                  |    `§o`   |            |           |
|                                 | Verschleiert                                              |    `§k`   |            |           |
