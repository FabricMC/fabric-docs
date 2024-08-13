---
title: Befehle erstellen
description: Befehle mit komplexen Parametern und Aktionen erstellen.
authors:
  - dicedpixels
  - i509VCB
  - pyrofab
  - natanfudge
  - Juuxel
  - solidblock
  - modmuss50
  - technici4n
  - atakku
  - haykam
  - mschae23
  - treeways
  - xpple

search: false
---

# Befehle erstellen

Durch das Erstellen von Befehlen kann ein Mod-Entwickler Funktionen hinzufügen, die durch einen Befehl verwendet werden können. Dieses Tutorial wird dir erklären, wie man Befehle registriert und die allgemeine Befehlsstruktur von Brigadier.

:::info
Brigadier ist ein Befehlsparser und Dispatcher, der von Mojang für Minecraft entwickelt wurde. Es ist eine baumbasierte Befehlsbibliothek, in der du einen Baum von Befehlen und Argumenten aufbaust. Brigadier ist Open Source: <https://github.com/Mojang/brigadier>
:::

## Das Interface `Command`

`com.mojang.brigadier.Command` ist ein funktionales Interface, das einen bestimmten Code ausführt und in bestimmten Fällen eine `CommandSyntaxException` auslöst. Er hat einen generischen Typ `S`, der den Typ der _Befehlsquelle_ definiert.
Die Befehlsquelle liefert einen Kontext, in dem ein Befehl ausgeführt wurde. In Minecraft ist die Befehlsquelle normalerweise ein `ServerCommandSource`, die einen Server, einen Befehlsblock, eine Remote-Verbindung (RCON), einen Spieler oder eine Entität darstellen kann.

Die einzige Methode in `Command`, `run(CommandContext<S>)`, nimmt einen `CommandContext<S>` als einzigen Parameter und gibt eine ganze Zahl zurück. Der Befehlskontext enthält die Befehlsquelle von `S` und ermöglicht es dir, Argumente zu erhalten, die geparsten Befehlsknoten zu betrachten und die in diesem Befehl verwendete Eingabe zu sehen.

Wie andere funktionale Interfaces wird es in der Regel als Lambda oder als Methodenreferenz verwendet:

```java
Command<ServerCommandSource> command = context -> {
    return 0;
};
```

Die Ganzzahl kann als Ergebnis des Befehls betrachtet werden. Normalerweise bedeuten Werte kleiner oder gleich Null, dass ein Befehl fehlgeschlagen ist und nichts machen wird. Positive Werte bedeuten, dass der Befehl erfolgreich war und etwas gemacht hat. Brigadier bietet eine Konstante zur Anzeige von Erfolg; `Befehl#SINGLE_SUCCESS`.

### Was kann die `ServerCommandSource` machen?

Eine "ServerCommandSource" liefert einen zusätzlichen implementierungsspezifischen Kontext, wenn ein Befehl ausgeführt wird. Dazu gehört die Möglichkeit, die Entität, die den Befehl ausgeführt hat, die Welt, in der der Befehl ausgeführt wurde, oder den Server, auf dem der Befehl ausgeführt wurde, zu ermitteln.

Du kannst auf die Befehlsquelle von einem Befehlskontext aus zugreifen, indem du `getSource()` für die Instanz `CommandContext` aufrufst.

```java
Command<ServerCommandSource> command = context -> {
    ServerCommandSource source = context.getSource();
    return 0;
};
```

## Registrieren eines einfachen Befehls

Befehle werden innerhalb des `CommandRegistrationCallback` registriert, der von der Fabric API bereitgestellt wird.

:::info
Informationen zur Registrierung von Callbacks findest du in der Anleitung [Events](../events).
:::

Das Event sollte im Initialisierer deines Mods registriert werden.

Der Callback hat drei Parameter:

- `CommandDispatcher<ServerCommandSource> dispatcher` - Dient zum Registrieren, Parsen und Ausführen von Befehlen. `S` ist der Typ
  der Befehlsquelle, die der Command Dispatcher unterstützt.
- `CommandRegistryAccess registryAccess` - Bietet eine Abstraktion zu Registrys, die an bestimmte Befehlsargumente übergeben werden können
  Argument-Methoden
- `CommandManager.RegistrationEnvironment environment` - Identifiziert den Typ des Servers, auf dem die Befehle registriert werden.

Im Mod-Initialisierer registrieren wir nur einen einfachen Befehl:

@[code lang=java transcludeWith=:::_1](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

In der Methode `sendFeedback()` ist der erste Parameter der zu sendende Text, der ein `Supplier<Text>` ist, um zu vermeiden, dass Text-Objekte instanziert werden, wenn sie nicht benötigt werden.

Der zweite Parameter bestimmt, ob die Rückmeldung an andere Operatoren gesendet werden soll. Im Allgemeinen sollte der Befehl `false` sein, wenn er etwas abfragen soll, ohne die Welt tatsächlich zu beeinflussen, wie zum Beispiel die aktuelle Zeit oder den Punktestand eines Spielers. die Zeit zu ändern oder den Spielstand einer Person zu ändern, sollte er `true` sein.

Wenn der Befehl fehlschlägt, kannst du, anstatt `sendFeedback()` aufzurufen, direkt eine beliebige Ausnahme auslösen, die vom Server oder Client entsprechend behandelt wird.

Die `CommandSyntaxException` wird im Allgemeinen ausgelöst, um Syntaxfehler in Befehlen oder Argumenten aufzuzeigen. Du kannst auch deine eigene Exception implementieren.

Um diesen Befehl auszuführen, musst du `/foo` eingeben, wobei die Groß- und Kleinschreibung zu beachten ist.

### Umgebung der Registrierung

Falls gewünscht, kannst du auch dafür sorgen, dass ein Befehl nur unter bestimmten Umständen registriert wird, zum Beispiel nur in der dedizierten Umgebung:

@[code lang=java highlight={2} transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

### Befehlsanforderungen

Angenommen, du hast einen Befehl, den nur Operatoren ausführen können sollen. An dieser Stelle kommt die Methode `requires()` ins Spiel. Die Methode `requires()` hat ein Argument eines `Predicate<S>`, das eine `ServerCommandSource` liefert, mit der getestet werden kann, ob die `CommandSource` den Befehl ausführen kann.

@[code lang=java highlight={3} transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Dieser Befehl wird nur ausgeführt, wenn die Quelle des Befehls mindestens ein Operator der Ebene 2 ist, einschließlich Befehlsblöcke. Andernfalls ist der Befehl nicht registriert.

Dies hat den Nebeneffekt, dass dieser Befehl in der Tab-Vervollständigung für alle, die nicht Level 2 Operator sind, nicht angezeigt wird. Das ist auch der Grund, warum du die meisten Befehle nicht mit der Tabulatortaste vervollständigen kannst, wenn du keine Cheats aktivierst.

### Unterbefehle

Um einen Unterbefehl hinzuzufügen, registriere den ersten buchstäblichen Knoten des Befehls ganz normal. Um einen Unterbefehl zu haben, musst du den nächsten buchstäblichen Knoten an den bestehenden Knoten anhängen.

@[code lang=java highlight={3} transcludeWith=:::7](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Ähnlich wie die Argumente können auch die Unterbefehlsknoten auf optional gesetzt werden. Im folgenden Fall sind sowohl `/subtater` als auch `/subtater subcommand` gültig.

@[code lang=java highlight={2,8} transcludeWith=:::8](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

## Client-Befehle

Die Fabric API verfügt über einen `ClientCommandManager` im Paket `net.fabricmc.fabric.api.client.command.v2`, der zur Registrierung clientseitiger Befehle verwendet werden kann. Der Code sollte nur im clientseitigen Code vorhanden sein.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/client/command/FabricDocsReferenceClientCommands.java)

## Befehlsumleitungen

Befehlsumleitungen - auch bekannt als Aliase - sind eine Möglichkeit, die Funktionalität eines Befehls auf einen anderen umzuleiten. Dies ist nützlich, wenn du den Namen eines Befehls ändern möchtest, aber den alten Namen beibehalten willst.

@[code lang=java transcludeWith=:::12](@/reference/latest/src/client/java/com/example/docs/client/command/FabricDocsReferenceClientCommands.java)

## FAQ

<br>

### Warum kompiliert mein Code nicht?

- Abfangen oder Auslösen einer `CommandSyntaxException` - `CommandSyntaxException` ist keine `RuntimeException`. Wenn du sie auslöst, sollte sie in Methoden ausgelöst werden, die `CommandSyntaxException` in den Methodensignaturen auslösen, oder sie sollte abgefangen werden.
  Brigadier wird die checked Exceptions behandeln und die entsprechende Fehlermeldung im Spiel für dich weiterleiten.

- Probleme mit generischen Typen - Es kann sein, dass du hin und wieder ein Problem mit generischen Typen hast. Wenn du Serverbefehle registrierst (was in den meisten Fällen der Fall ist), stelle sicher, dass du `CommandManager.literal` oder `CommandManager.argument` anstelle von `LiteralArgumentBuilder.literal` oder `RequiredArgumentBuilder.argument` benutzt.

- Überprüfe die Methode `sendFeedback()` - Du hast vielleicht vergessen, einen booleschen Wert als zweites Argument anzugeben. Denke auch daran dass seit Minecraft 1.20 der erste Parameter `Supplier<Text>` anstelle von `Text` ist.

- Ein Befehl sollte eine ganze Zahl zurückgeben - Bei der Registrierung von Befehlen akzeptiert die Methode `executes()` ein `Command` Objekt, das normalerweise ein Lambda ist. Das Lambda sollte eine ganze Zahl zurückgeben, anstelle anderen Typen.

### Kann ich Befehle zur Laufzeit registrieren?

::: warning
You can do this, but it is not recommended. You would get the `CommandManager` from the server and add anything commands
you wish to its `CommandDispatcher`.

Danach musst du den Befehlsbaum mit `CommandManager.sendCommandTree(ServerPlayerEntity)` erneut an jeden Spieler senden.

Dies ist erforderlich, da der Client den Befehlsbaum, den er bei der Anmeldung (oder beim Senden von Operator-Paketen) erhält, lokal zwischenspeichert, um Fehlermeldungen zu vervollständigen.
:::

### Kann ich die Registrierung von Befehlen während der Laufzeit aufheben?

::: warning
You can also do this, however, it is much less stable than registering commands at runtime and could cause unwanted side
effects.

Um die Dinge einfach zu halten, musst du Reflection auf Brigadier anwenden und Knoten entfernen. Danach musst du den Befehlsbaum erneut an jeden Spieler mit `sendCommandTree(ServerPlayerEntity)` senden.

Wenn du den aktualisierten Befehlsbaum nicht sendest, kann es sein, dass der Client denkt, dass der Befehl noch existiert, obwohl die Ausführung am Server fehlschlägt.
:::
