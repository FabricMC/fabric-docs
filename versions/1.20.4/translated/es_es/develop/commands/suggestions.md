---
title: Sugerencias de Comandos
description: Aprende a sugerir valores para argumentos de comandos a usuarios.
authors:
  - IMB11

search: false
---

# Sugerencias de Comandos

Minecraft tiene un poderoso sistema de sugerencias de comandos que es usado en muchos lugares, como en el comanado de `/give`. Este sistema te permite sugerir valores para argumentos de comandos al usuario, donde este puede escoger de estos valores - es una buena manera de hacer tus comandos más ergonómicos y amigables al usuario.

## Proveedores de Sugerencias

Un `SuggestionProvider` (Proveedor de Sugerencias) es usado para crear una lista de sugerencias que serán enviadas al cliente. Un proveedor de sugerencias es una interfaz funcional que tiene un parámetro de `CommandContext` y un `SuggestionBuilder` (Constructor de Sugerencias), y retorna algunos `Suggestions` (Sugerencias). El `SuggestionProvider` retorna un `CompletableFuture` (Completador a Futuro) ya que las sugerencias pueden no estar disponibles inmediatamente.

## Usar Proveedores de Sugerencias

Para usar un proveedor de sugerencias, tienes que llamar el método `suggests` en el constructor del argumento. Este método tiene un parámetro `SuggestionProvider` y retorna un nuevo constructor de argumento con el proveedor de sugerencias adjuntado.

@[code java transcludeWith=:::9 highlight={4}](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

## Proveedores de Sugerencias Incluidos

Hay algunos proveedores de sugerencias incluidos que puedes usar:

| Proveedor de Sugerencias                  | Descripción                                                                                              |
| ----------------------------------------- | -------------------------------------------------------------------------------------------------------- |
| `SuggestionProviders.SUMMONABLE_ENTITIES` | Sugiere todas las entidades que pueden ser convocadas.                                   |
| `SuggestionProviders.AVAILABLE_SOUNDS`    | Sugiere todos los efectos de sonido que pueden ser reproducidos.                         |
| `LootCommand.SUGGESTION_PROVIDER`         | Sugiere todas las loot tables (tablas de loot) que están disponibles. |
| `SuggestionProviders.ALL_BIOMES`          | Sugiere todos los biomas disponibles.                                                    |

## Crear un Proveedor de Sugerencias Personalizado

Si un proveedor incluido no suficiente para tus necesidades, puedes crear tu propio proveedor de sugerencias. Para esto, debes crear una clase que implemente la interfaz `SuggestionProvider` y que anule el método `getSuggestions`.

Para este ejemplo, haremos un proveedor de sugerencias que sugiere todos los nombres de usuario de los jugadores en el servidor.

@[code java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/command/PlayerSuggestionProvider.java)

Para usar este proveedor de sugerencias, simplemente pasa una instancia de él en el método `.suggests` en el constructor del argumento.

Obviamente, los proveedores de sugerencias pueden ser más complejos, ya que también el contexto del comando para proveer sugerencias basadas en el estado del comando - como los argumentos que ya han sido dados.

Esto puede ser en la forma de leer el inventario del jugador y sugerir items, o entidades que estén cerca del jugador.
