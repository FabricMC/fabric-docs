---
title: Argumentos de Comandos
description: Aprender a crear comandos con argumentos complejos.
---

# Command Arguments

Los argumentos son usados en la mayoría de los comandos. A veces pueden ser opcionales, en caso tal no necesitas proveer el argumento para correr el comando. Un nodo puede contener múltiples tipos de argumentos, pero ten en cuenta que existe la posibilidad de ambigüedades, que deberían ser evitadas.

@[code lang=java highlight={3} transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

En este caso, después del texto del comando 'argtater', deberías escribir un número entero. Por ejemplo, si corres el comando '/argtater 3', obtendrás el mensaje de respuesta 'Called /argtater with value = 3' (Se llamó /argtater con valor = 3). Si escribes '/argtater' sin los argumentos, el comando no puede ser leído y analizado correctamente.

Ahora añadimos un segundo argumento opcional:

@[code lang=java highlight={3,13} transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Ahora puedes escribir uno o dos números enteros. Si lo corres con un número, el mensaje de respuesta muestra el único valor dado. Si le dás dos números enteros, el mensaje de respuesta contiene ambos valores dados.

Puede que encuentres innecesario tener que especificar ejecuciones similares dos veces. Por lo tanto, podemos crear un método que será usado para ambas ejecuciones.

@[code lang=java highlight={3,5,6,7} transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

## Custom Argument Types

If vanilla does not have the argument type you need, you can create your own. To do this, you need to create a class that inherits the `ArgumentType<T>` interface where `T` is the type of the argument.

You will need to implement the `parse` method, which will parse the input string into the desired type.

For example, you can create an argument type that parses a `BlockPos` from a string with the following format: `{x, y, z}`

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/command/BlockPosArgumentType.java)

### Registering Custom Argument Types

:::warning
You need to register the custom argument type on both the server and the client or else the command will not work!
:::

You can register your custom argument type in the `onInitialize` method of your mod initializer using the `ArgumentTypeRegistry` class:

@[code lang=java transcludeWith=:::11](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

### Using Custom Argument Types

We can use our custom argument type in a command - by passing an instance of it into the `.argument` method on the command builder.

@[code lang=java transcludeWith=:::10 highlight={3}](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Running the command, we can test whether or not the argument type works:

![Invalid argument.](../../assets/develop/commands/custom-arguments_fail.png)

![Valid argument.](../../assets/develop/commands/custom-arguments_valid.png)

![Command result.](../../assets/develop/commands/custom-arguments_result.png)
