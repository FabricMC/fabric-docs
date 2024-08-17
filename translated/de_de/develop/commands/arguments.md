---
title: Befehlsargumente
description: Lerne, wie man Befehle mit komplexen Parametern erstellt.
---

# Befehlsargumente {#command-arguments}

Parameter werden in den meisten Befehlen verwendet. Manchmal sind sie optional, das heißt, wenn du diesen Parameter nicht angibst, wird der Befehl dennoch ausgeführt. Ein Knoten kann mehrere Parametertypen haben, aber es ist zu beachten, dass die Möglichkeit einer
Mehrdeutigkeit besteht, die vermieden werden sollte.

@[code lang=java highlight={3} transcludeWith=:::command_with_arg](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)
@[code lang=java transcludeWith=:::execute_command_with_arg](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

In diesem Fall musst du nach dem Befehlstext `/command_with_arg` eine ganze Zahl eingeben. Wenn du zum Beispiel `/command_with_arg 3` ausführst, erhältst du eine Rückmeldungsnachricht:

> Aufruf von /command_with_arg mit Wert = 3

Wenn du `/command_with_arg` ohne Argumente eingibst, kann der Befehl nicht korrekt geparst werden.

Dann fügen wir ein optionales zweites Argument hinzu:

@[code lang=java highlight={3,5} transcludeWith=:::command_with_two_args](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)
@[code lang=java transcludeWith=:::execute_command_with_two_args](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Jetzt kannst du eine oder zwei ganze Zahlen eingeben. Wenn du eine ganze Zahl eingibst, wird ein Feedback-Text mit einem einzigen Wert ausgegeben. Wenn du zwei Ganzzahlen angibst, wird ein Feedback-Text mit zwei Werten ausgegeben.

Du kannst es unnötig finden, ähnliche Ausführungen zweimal anzugeben. Daher können wir eine Methode erstellen, die in beiden Ausführungen verwendet wird.

@[code lang=java highlight={4,6} transcludeWith=:::command_with_common_exec](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)
@[code lang=java transcludeWith=:::execute_common](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

## Benutzerdefinierte Argumenttypen {#custom-argument-types}

Wenn Vanilla nicht den von dir benötigten Argumenttyp verfügt, kannst du deinen eigenen erstellen. Dazu musst du eine Klasse erstellen, die das Interface `ArgumentType<T>` erbt, wobei `T` der Typ des Arguments ist.

Du musst die Methode "parse" implementieren, die die Eingabezeichenfolge zu dem gewünschten Typ parst.

Du kannst zum Beispiel einen Argumenttyp erstellen, der eine `BlockPos` aus einer Zeichenkette mit dem folgenden Format parst: `{x, y, z}`

@[code lang=java highlight={3,5,6,7} transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

### Benutzerdefinierte Argumenttypen registrieren {#registering-custom-argument-types}

:::warning
Du musst den benutzerdefinierten Argumenttyp sowohl im Server als auch im Client registrieren, sonst wird der Befehl nicht funktionieren!
:::

Du kannst deinen benutzerdefinierten Argumenttyp in der Methode `onInitialize` deines Mod-Initialisierers mit der Klasse `ArgumentTypeRegistry` registrieren:

@[code lang=java transcludeWith=:::register_custom_arg](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

### Benutzerdefinierte Argumenttypen verwenden {#using-custom-argument-types}

Wir können unseren benutzerdefinierten Argumenttyp in einem Befehl verwenden, indem wir eine Instanz davon an die Methode `.argument` im Builder des Befehls übergeben.

@[code lang=java highlight={3} transcludeWith=:::custom_arg_command](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)
@[code lang=java highlight={2} transcludeWith=:::execute_custom_arg_command](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Durch das Ausführen des Befehls, können wir testen, ob der Argumenttyp funktioniert oder nicht:

![Ungültiges Argument](/assets/develop/commands/custom-arguments_fail.png)

![Gültiges Argument](/assets/develop/commands/custom-arguments_valid.png)

![Ergebnis des Befehls](/assets/develop/commands/custom-arguments_result.png)
