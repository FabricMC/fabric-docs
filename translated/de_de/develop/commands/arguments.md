---
title: Befehlsargumente
description: Lerne, wie man Befehle mit komplexen Parametern erstellt.
---

Parameter werden in den meisten Befehlen verwendet. Manchmal sind sie optional, das heißt, wenn du diesen Parameter nicht angibst, wird der Befehl dennoch ausgeführt. Ein Knoten kann mehrere Parametertypen haben, aber es ist zu beachten, dass die Möglichkeit einer
Mehrdeutigkeit besteht, die vermieden werden sollte.

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#command_with_arg{3}

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#execute_command_with_arg

In diesem Fall musst du nach dem Befehlstext `/command_with_arg` eine ganze Zahl eingeben. Wenn du zum Beispiel `/command_with_arg 3` ausführst, erhältst du eine Rückmeldungsnachricht:

> Aufruf von /command_with_arg mit Wert = 3

Wenn du `/command_with_arg` ohne Argumente eingibst, kann der Befehl nicht korrekt geparst werden.

Dann fügen wir ein optionales zweites Argument hinzu:

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#command_with_two_args{3,5}

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#execute_command_with_two_args

Jetzt kannst du eine oder zwei ganze Zahlen eingeben. Wenn du eine ganze Zahl eingibst, wird ein Feedback-Text mit einem einzigen Wert ausgegeben. Wenn du zwei Ganzzahlen angibst, wird ein Feedback-Text mit zwei Werten ausgegeben.

Du kannst es unnötig finden, ähnliche Ausführungen zweimal anzugeben. Daher können wir eine Methode erstellen, die in beiden Ausführungen verwendet wird.

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#command_with_common_exec{4,6}

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#execute_common

## Benutzerdefinierte Argumenttypen {#custom-argument-types}

Wenn Vanilla nicht den von dir benötigten Argumenttyp verfügt, kannst du deinen eigenen erstellen. Dazu musst du eine Klasse erstellen, die das Interface `ArgumentType<T>` erbt, wobei `T` der Typ des Arguments ist.

Du musst die Methode "parse" implementieren, die die Eingabezeichenfolge zu dem gewünschten Typ parst.

Du kannst zum Beispiel einen Argumenttyp erstellen, der eine `BlockPos` aus einer Zeichenkette mit dem folgenden Format parst: `{x, y, z}`

<<< @/reference/latest/src/main/java/com/example/docs/command/BlockPosArgumentType.java#custom_argument_types

### Benutzerdefinierte Argumenttypen registrieren {#registering-custom-argument-types}

::: warning

Du musst den benutzerdefinierten Argumenttyp sowohl im Server als auch im Client registrieren, sonst wird der Befehl nicht funktionieren!

:::

Du kannst deinen benutzerdefinierten Argumenttyp in der Methode `onInitialize` deines [Mod-Initialisierers](../getting-started/project-structure#entrypoints) mit der Klasse `ArgumentTypeRegistry` registrieren:

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#register_custom_arg

### Benutzerdefinierte Argumenttypen verwenden {#using-custom-argument-types}

Wir können unseren benutzerdefinierten Argumenttyp in einem Befehl verwenden, indem wir eine Instanz davon an die Methode `.argument` im Builder des Befehls übergeben.

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#custom_arg_command{3}

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#execute_custom_arg_command{2}

Durch das Ausführen des Befehls, können wir testen, ob der Argumenttyp funktioniert oder nicht:

![Ungültiges Argument](/assets/develop/commands/custom-arguments_fail.png)

![Gültiges Argument](/assets/develop/commands/custom-arguments_valid.png)

![Ergebnis des Befehls](/assets/develop/commands/custom-arguments_result.png)
