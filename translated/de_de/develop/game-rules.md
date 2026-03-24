---
title: Spielregeln
description: Ein Leitfaden zum Hinzufügen benutzerdefinierter Spielregeln.
authors:
  - cassiancc
  - Jummit
  - modmuss50
  - Wind292
authors-nogithub:
  - mysterious_dev
  - solacekairos
---

<!---->

:::info VORAUSSETZUNGEN

Möglicherweise möchtest du zunächst die [Generierung von Übersetzungen](./data-generation/translations) abschließen, dies ist jedoch nicht erforderlich.

:::

Spielregeln verhalten sich wie weltenspezifische Konfigurationsoptionen, die der Spieler während des Spiels mit einem Befehl ändern kann. Diese Variablen steuern in der Regel bestimmte Funktionen der Welt, zum Beispiel steuern `pvp`, `spawn_monsters` und `advance_time`, ob PvP aktiviert ist, Monster erscheinen und ob die Zeit verstreicht.

## Eine Spielregel erstellen {#creating-a-game-rule}

Um eine benutzerdefinierte Spielregel zu erstellen, erstelle zunächst eine Klasse `GameRules`; dort werden wir unsere Spielregeln definieren. In dieser Klasse, deklariere zwei Konstanten: Eine Bezeichnung für die Spielregel und die Regel selbst.

@[code lang=java transcludeWith=:::gameruleClass](@/reference/latest/src/main/java/com/example/docs/gamerule/ExampleModGameRules.java)

Das Argument der Kategorie (`.category(GameRuleCategory.MISC)`) legt fest, unter welche Kategorie die Spielregel in der Oberfläche zur Welterstellung fällt. Dieses Beispiel verwendet die durch Vanilla bereitgestellte Kategorie Verschiedenes, aber zusätzliche Kategorien können über `GameRuleCategory.register` hinzugefügt werden. In diesem Beispiel haben wir eine boolsche Spielregel mit dem Standardwert `false` und der ID `bad_vision` erstellt. Die in Spielregeln gespeicherten Werte sind nicht auf boolesche Werte beschränkt; weitere zulässige Typen sind `Double`s, `Integer`s und `Enum`s.

Beispiel einer Spielregel, die einen double speichert:

@[code lang=java transcludeWith=:::double](@/reference/latest/src/main/java/com/example/docs/gamerule/ExampleModGameRules.java)

## Auf eine Spielregel zugreifen {#accessing-a-game-rule}

Da wir jetzt eine Spielregel und deren `Identifier` haben, kannst du überall mit der Methode `serverLevel.getGameRules().get(GAMERULE)` darauf zugreifen, wobei das Argument für `.get()` deine Spielregelkonstante ist und nicht die ID der Spielregel.

@[code lang=java transclude={44-44}](@/reference/latest/src/main/java/com/example/docs/gamerule/ExampleModGameRules.java)

Du kannst dies auch verwenden, um auf die Werte von Vanilla Spielregeln zuzugreifen:

@[code lang=java transcludeWith=:::vanilla](@/reference/latest/src/main/java/com/example/docs/gamerule/ExampleModGameRules.java)

Beispielsweise würde die Implementierung für eine Regel, die bei dem Wert true allen Spielern Blindheit gibt, wie folgt aussehen:

@[code lang=java transcludeWith=:::badvision](@/reference/latest/src/main/java/com/example/docs/gamerule/ExampleModGameRules.java)

## Übersetzungen {#translations}

Jetzt müssen wir unserer Spielregel einen Anzeigenamen geben, damit sie in der Oberfläche für Spielregeln leicht verständlich ist. Um dies über die Datengenerierung zu erledigen, füge die folgenden Zeilen zu deinem Sprach-Provider hinzu:

@[code lang=java transcludeWith=:::gamerule-name](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnglishLangProvider.java)

Zuletzt müssen wir unserer Spielregel eine Beschreibung hinzufügen. Um dies über die Datengenerierung zu erledigen, füge die folgenden Zeilen zu deinem Sprach-Provider hinzu:

@[code lang=java transcludeWith=:::gamerule-description](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnglishLangProvider.java)

::: info

Diese Übersetzungsschlüssel werden bei der Anzeige von Text in der Oberfläche für Spielregeln verwendet. Wenn du die automatische Datengenerierung nicht verwendest, kannst du diese auch manuell in deiner Datei `assets/example-mod/lang/en_us.json` eintragen.

```json
"example-mod.bad_vision": "Bad Vision",
"gamerule.example-mod.bad_vision": "Gives every player the blindness effect",
```

:::

## Spielregeln im Spiel ändern {#changing-game-rules-in-game}

Jetzt solltest du in der Lage sein, den Wert deiner Regel im Spiel mit dem Befehl `/gamerule` wie folgt zu ändern:

```mcfunction
/gamerule example-mod:bad_vision true
```

Die Spielregel wird nun auch in der Kategorie Verschiedenes in der Oberfläche Spielregeln bearbeiten angezeigt.

![Die Oberfläche zur Welterstellung mit der Spielregel Bad Vision](/assets/develop/game-rules/world-creation.png)
