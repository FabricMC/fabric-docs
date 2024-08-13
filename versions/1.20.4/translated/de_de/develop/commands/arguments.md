---
title: Befehlsargumente
description: Lerne, wie man Befehle mit komplexen Parametern erstellt.

search: false
---

# Befehlsargumente

Parameter werden in den meisten Befehlen verwendet. Manchmal sind sie optional, das heißt, wenn du diesen Parameter nicht angibst, wird der Befehl dennoch ausgeführt. Ein Knoten kann mehrere Parametertypen haben, aber es ist zu beachten, dass die Möglichkeit einer
Mehrdeutigkeit besteht, die vermieden werden sollte.

@[code lang=java highlight={3} transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

In diesem Fall musst du nach dem Befehlstext `/argtater` eine ganze Zahl eingeben. Zum Beispiel, wenn du `/argtater 3` ausführst, erhaltest du die Rückmeldung `Called /argtater with value = 3`. Wenn du `/argtater` ohne Argumente eingibst, kann der Befehl nicht korrekt geparst werden.

Dann fügen wir ein optionales zweites Argument hinzu:

@[code lang=java highlight={3,13} transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Jetzt kannst du eine oder zwei ganze Zahlen eingeben. Wenn du eine ganze Zahl eingibst, wird ein Feedback-Text mit einem einzigen Wert ausgegeben. Wenn du zwei Ganzzahlen angibst, wird ein Feedback-Text mit zwei Werten ausgegeben.

Du kannst es unnötig finden, ähnliche Ausführungen zweimal anzugeben. Daher können wir eine Methode erstellen, die in beiden Ausführungen verwendet wird.

@[code lang=java highlight={3,5,6,7} transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

## Benutzerdefinierte Argumenttypen

Wenn Vanilla nicht den von dir benötigten Argumenttyp verfügt, kannst du deinen eigenen erstellen. Dazu musst du eine Klasse erstellen, die das Interface `ArgumentType<T>` erbt, wobei `T` der Typ des Arguments ist.

Du musst die Methode "parse" implementieren, die die Eingabezeichenfolge zu dem gewünschten Typ parst.

Du kannst zum Beispiel einen Argumenttyp erstellen, der eine `BlockPos` aus einer Zeichenkette mit dem folgenden Format parst: `{x, y, z}`

@[code lang=java highlight={3,5,6,7} transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

### Benutzerdefinierte Argumenttypen registrieren

:::warning
Du musst den benutzerdefinierten Argumenttyp sowohl im Server als auch im Client registrieren, sonst wird der Befehl nicht funktionieren!
:::

Du kannst deinen benutzerdefinierten Argumenttyp in der Methode `onInitialize` deines Mod-Initialisierers mit der Klasse `ArgumentTypeRegistry` registrieren:

@[code lang=java transcludeWith=:::11](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

### Benutzerdefinierte Argumenttypen verwenden

Wir können unseren benutzerdefinierten Argumenttyp in einem Befehl verwenden, indem wir eine Instanz davon an die Methode `.argument` im Builder des Befehls übergeben.

@[code lang=java transcludeWith=:::10 highlight={3}](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Durch das Ausführen des Befehls, können wir testen, ob der Argumenttyp funktioniert oder nicht:

![Ungültiges Argument](/assets/develop/commands/custom-arguments_fail.png)

![Gültiges Argument](/assets/develop/commands/custom-arguments_valid.png)

![Ergebnis des Befehls](/assets/develop/commands/custom-arguments_result.png)
