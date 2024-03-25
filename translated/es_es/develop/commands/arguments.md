---
title: Argumentos de Comandos
description: Aprende a crear comandos con argumentos complejos.
---

# Argumentos de Comandos

Los argumentos son usados en la mayoría de los comandos. Algunas veces pueden ser opcionales, lo que significa que el usuario no tiene que dar un argumento para que el comando corra. Un nodo puede tener múltiples tipos de argumentos, pero ten cuidado de no crear ambigüedades.

@[code lang=java highlight={3} transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

En este caso, después del texto del comando `/argtater`, debes escribir un número entero. Por ejemplo, si corres `/argtater 3`, obtendrás el mensaje de respuesta `Called /argtater with value = 3`. Si escribes `/argtater` sin argumentos, el comando no puede ser leído y analizado correctamente.

Ahora añadimos un segundo argumento opcional:

@[code lang=java highlight={3,13} transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Ahora puedes escribir uno o dós números enteros. Si le das un número entero, se mostrará un mensaje de respuesta con un solo valor. Si das dos números enteros, se mostrará un mensaje de respuesta con dos valores.

Puede que sientas que sea innecesario tener que especificar ejecuciones similares dos veces. Para ello, crearemos un método que se usará para ambas ejecuciones.

@[code lang=java highlight={3,5,6,7} transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

## Tipos de Argumentos Personalizados

Si el juego vanilla no tiene el tipo de argumento que necesitas, puedes crear tu propio tipo de argumento. Para esto, puedes crear una clase que implemente la interfaz `ArgumentType<T>`, donde `T` es el tipo del argumento.

Necesitarás implementar el método `parse`, el cual leerá y analizará la cadena de caracteres entrada por el usuario a el tipo de argumento deseado.

Por ejemplo, puedes crear un tipo de argumento que lea un `BlockPos` a partir de una cadena de caracteres con el siguiente formato: `{x, y, z}`

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/command/BlockPosArgumentType.java)

### Registrar Tipos de Argumentos Personalizados

:::warning
¡Necesitas registrar el tipo de comando personalizado tanto en el servidor como en el cliente, de lo contrario, el comando no funcionará!
:::

Puedes registrar tu propio tipo de argumento personalizado en el método `onInitialize` del inicializador de tu mod usando la clase `ArgumentTypeRegistry`:

@[code lang=java transcludeWith=:::11](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

### Usar Argumentos de Comandos Personalizados

Podemos usar nuestro tipo de argumento personalizado en un comando pasando una instancia de él al método `.argument` en el constructor del comando.

@[code lang=java transcludeWith=:::10 highlight={3}](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Al correr el comando, podemos verificar que el tipo de argumento funciona:

![Argumento inválido.](/assets/develop/commands/custom-arguments_fail.png)

![Argumento válido.](/assets/develop/commands/custom-arguments_valid.png)

![Resultado del comando.](/assets/develop/commands/custom-arguments_result.png)
