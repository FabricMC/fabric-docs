---
title: Crear Comandos
description: Crea comandos con argumentos y acciones complejas.
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

# Crear Comandos

La creación de comandos le permite a desarrolladores de mods añadir funcionalidad que puede ser usada mediante un comando. Este tutorial te enseñará como registrar comandos y la estructura general de comandos de Brigadier.

:::info
Brigadier es un analizador y despachador de comandos escrito por Mojang para Minecraft. Es una libraría basada en un una estructura de árbol de comandos, donde construyes un árbol de comandos y argumentos. Brigadier es de fuente abierta: <https://github.com/Mojang/brigadier>
:::

## La interfaz `Comand` (Comando)

`com.mojang.brigadier.Command` es una interfaz funcional, la cual corre un código específico, y tira una excepción de `CommandSyntaxException` (Excepción de Syntax de Comando) en algunos casos. Tiene un tipo genérico `S`, el cual define el tipo de la _fuente de comando_.
La fuente de comando nos dá el contexto en el que se corrió un comando. En Minecraft, la fuente de comando es típicamente un `ServerCommandSource` (Fuente de Comando de Servidor) el cual puede representar un servidor, un bloque de comandos, una conexión remota (RCON), un jugador o una entidad.

El método `run(CommandContext<S>)` en `Command` tiene un `CommandContext<S>` como el único parámetro y retorna un número entero. El contexto del comando contiene la fuente de comando de `S` y te permite obtener los argumentos, ver los nodos de comando analizados y ver el valor entrado en este comando.

Al igual que otras interfaces funcionales, puedes usar una expresión Lambda o una referencia de método:

```java
Command<ServerCommandSource> command = context -> {
    return 0;
};
```

El número entero retornado puede ser considerado el resultado del comando. Los valores iguales o menores a 0 típicamente significan que el comando ha fallado y no hará nada. Valores positivos indican que el comando fue exitoso e hizo algo. Brigadier provee una constante para indicar éxito; `Command#SINGLE_SUCCESS` (Éxito Único).

### ¿Qué puede hacer el `ServerCommandSource`?

Un `ServerCommandSource` nos da contexto específico de implementación adicional cuando el comando es corrido. Esto incluye la habilidad de obtener la entidad que ejecutó el comando, el mundo o el servidor en el que el comando fue ejecutado.

Puedes acceder la fuente del comando desde un contexto de comando llamando `getSource()` en la instancia de `CommandContext`.

```java
Command<ServerCommandSource> command = context -> {
    ServerCommandSource source = context.getSource();
    return 0;
};
```

## Registrar un Comando Básico

Los comandos son registrados mediante la clase `CommandRegistrationCallback` (Callback de Registración de Comandos) proveída por el Fabric API.

:::info
Para información sobre como registrar callbacks, por favor visita la guía de [Eventos](../events).
:::

El evento debería ser registrado en tu inicializador de mod.

El callback tiene tres parámetros:

- `CommandDispatcher<ServerCommandSource> dispatcher` (Despachador de comandos) - Usado para registrar, analizar y ejecutar comandos. `S` es el tipo de la fuente del comando que el despachador usa.
- `CommandRegistryAccess registryAccess` (Acceso de registro de comandos) - Provee una abstracción sobre los registros que pueden ser dados a ciertos métodos de argumentos de comandos
- `CommandManager.RegistrationEnvironment environment` (Ambiente de Registración) - Identifica el tipo de servidor en el que los comandos se están registrando en.

En el inicializador de mod, solo registramos un comando simple:

@[code lang=java transcludeWith=:::_1](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

En el método `sendFeedback()`, el primer parámetro es el texto a ser enviado, el cual está en un `Supplier<Text>` (Proveedor) para no tener que instanciar nuevos objetos `Text` (Texto) cuando no es necesario.

El segundo parámetro determina si deberíamos transmitir el feedback a otros operadores. Generalmente, si el comando solo es para verificar o consultar algo sin afectar realmente el mundo, como verificar el tiempo actual o el puntaje de un jugador, este parametro debería ser `false` (falso). Si el comando hace algo, como cambiar el tiempo o modificar el puntaje de alguien, entonces debería ser `true` (verdadero).

Si el comando falla, en vez de llamar `sendFeedback()`, puedes directamente tirar una excepción y el servidor o cliente lo manejará apropiadamente.

`CommandSyntaxException` es generalmente tirada para indicar errores en la sintaxis del comando o sus argumentos. También puedes implementar tus propias excepciones.

Para ejecutar este comando, debes escribir `/foo`; aquí importan las mayúsculas y minúsculas.

### Ambiente de Registración

Si se desea, también puedes asegurarte que un comando solo es registrado bajo ciertas circunstancias específicas, por ejemplo, solo en el ambiente dedicado:

@[code lang=java highlight={2} transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

### Requerimientos de Comandos

Digamos que tienes un comando y que quieres que solo los operadores puedan ejecutarlo. Aquí entra el método `requires()`. El método `requieres()` tiene un argumento de un `Predicate<S>` (Condición) el cual proveerá un `ServerCommandSource` el cual será probado con la condición data para determinar si el `CommandSource` puede ejecutar el comando.

@[code lang=java highlight={3} transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Este comando solo se ejecutará si la fuente del comando es un operador nivel 2 como mínimo, incluyendo bloques de comandos. De lo contrario, el comando no es registrado.

Esto tiene el efecto secundario de que el comando no se muestra con la auto completación con la tecla Tab a personas que no tienen operador nivel 2. Esta también es la razón por la cual no puedes autocompletar comandos cuando no tienes los trucos activados.

### Sub Comandos

Para agregar un sub comando, registras el primer nodo de comando normalmente. Para tener un sub comando, tienes que adjuntar el siguiente literal de nodo de comando al nodo existente.

@[code lang=java highlight={3} transcludeWith=:::7](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Similarmente a los argumentos, los nodos de sub comandos pueden ser opcionales. En el siguiente caso ambos comandos `/subtater` y `/subtater subcommand` serán válidos.

@[code lang=java highlight={2,8} transcludeWith=:::8](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

## Comandos de Cliente

El Fabric API tiene una clase `ClientCommandManager` en el paquete de `net.fabricmc.fabric.api.client.command.v2` que puede ser usado para registrar comandos en el lado del cliente. Este código solo debe existir en el lado del cliente.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/client/command/FabricDocsReferenceClientCommands.java)

## Redireccionando Comandos

Redireccionadores de comandos - también llamados aliases - son una manea de redireccionar la funcionalidad de un comando a otro. Esto es útil cuando quieres cambiar el nombre de un comando, pero todavía quieres mantener soporte para el nombre viejo.

@[code lang=java transcludeWith=:::12](@/reference/latest/src/client/java/com/example/docs/client/command/FabricDocsReferenceClientCommands.java)

## Preguntas Frequentes

<br>

### ¿Por qué mi código no compila?

- Atrapa o tira una excepción `CommandSyntaxException` - `CommandSyntaxException` no es una `RuntimeException` (Excepción en Tiempo de Ejecución). Si la tiras, debe ser en métodos que tiren un `CommandSyntaxException` en el signature (firma) del método, o ser atrapadas.
  Brigadier manejará las excepciones checked (comprobadas) y mandará el mensaje de error correspondiente en el juego para ti.

- Problemas con genéricos - Puede que tengas problemas con genéricos de vez en cuando. Si estás registrando comandos de servidor (el cual es mayormente el caso), asegúrate de usar `CommandManager.literal` o `CommandManager.argument` en vez de `LiteralArgumentBuilder.literal` o `RequiredArgumentBuilder.argument`.

- Verifica el método de `sendFeedback()` - Puede que te hayas olvidado de dar el valor booleano al segundo argumento. También recurda que, a partir de Minecraft 1.20, el primer parámetro es un `Supplier<Text>` en vez de `Text`.

- Un comando debería retornar un número entero - Cuando registres comandos, el método `executes()` acepta un objeto `Command`, el cual usualmente es una expresión Lambda. El Lambda debería retornar un número entero, en lugar de otros tipos de valores.

### ¿Puedo registrar comandos durante la ejecución?

::: warning
You can do this, but it is not recommended. You would get the `CommandManager` from the server and add anything commands
you wish to its `CommandDispatcher`.

Después de eso, debes enviar el árbol de comandos a cada jugador de nuevo usando `CommandManager.sendCommandTree(ServerPlayerEntity)`.

Esto es necesario porque el cliente almacena en un caché el árbol de comandos que recibe durante inicio de sesión (o cuando paquetes de operador son enviados) para mensajes de error con completaciones locales.
:::

### ¿Puedo des-registrar comandos durante la ejecución?

::: warning
You can also do this, however, it is much less stable than registering commands at runtime and could cause unwanted side
effects.

Para mantener las cosas simples, vas a tener que usar reflexión en Brigadier para remover nodos. Después de esto, necesitas enviar el árbol de comandos de nuevo a cada jugador usando `sendCommandTree(ServerPlayerEntity)`.

Si no envias el árbol de comandos actualizado, el cliente pensará que un comando existe, aunque el servidor no podrá ejecutarlo.
:::
