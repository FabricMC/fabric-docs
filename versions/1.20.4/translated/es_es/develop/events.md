---
title: Eventos
description: Una guía para usar los eventos ofrecidos por el Fabric API.
authors:
  - dicedpixels
  - mkpoli
  - daomephsta
  - solidblock
  - draylar
  - jamieswhiteshirt
  - PhoenixVX
  - Juuxel
  - YanisBft
  - liach
  - natanfudge
authors-nogithub:
  - stormyfabric

search: false
---

# Eventos

El Fabric API provee un sistema que le permite a los mods reaccionar a ciertas acciones u ocurrencias, definidas como _eventos_ que ocurren en el juego.

Los eventos son enganches que satisfacen usos comunes y/o proveen mejor compatibilidad y rendimiento entre diferentes mods que se enganchan a las mismas áreas del código. El uso de eventos substituye el uso de mixins.

El Fabric API provee eventos para áreas importantes en el código fuente de Minecraft, las cuales son de interés a desarrolladores de mods para engancharse en.

Los eventos son representados por instancias de `net.fabricmc.fabric.api.event.Event`, el cual guarda y también llama _callbacks_. Usualmente solo hay una instancia del evento para un callback, el cual es almacenado en un miembro estático `EVENT` en la interfaz del callback, pero también hay otros patrones. Por ejemplo, `ClientTickEvents` agrupa varios eventos relacionados juntos.

## Callbacks

Un callback es una porción de código pasada como un argumento a un evento. Cuando el evento es activado por el juego, el código pasado será ejecutado.

### Interfaces de Callbacks

Cada evento tiene su propia interfaz de callback, convencionalmente llamada `<EventName>Callback`. Los callbacks son registrados llamando el método `register()` en una instancia del evento, con una instancia de la interfaz del callback como el argumento.

Todas las interfaces de callbacks para eventos proveídas por el Fabric API pueden ser encontradas en el paquete `net.fabricmc.fabric.api.event`.

## Detectando Eventos

### Un Ejemplo Sencillo

Este ejemplo registra un `AttackBlockCallback` para atacar el jugador cuando este golpea bloques que no sueltan un item cuando son minados con la mano.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/event/FabricDocsReferenceEvents.java)

### Añadiendo Items a Loot Tables Existentes

A veces, uno desea añadir items a loot tables (o tablas que determinan los items soltados por un objeto, como bloque o entidad). Por ejemplo, añadir tus drops a un bloque o entidad vanilla.

La solución más simple, la cual es reemplazar el archivo del loot table, puede romper otros mods. ¿Qué tal si otros mods también quierer cambiar el loot table? Echaremos un vistazo a como puedes agregar items a los loot tables sin tener que anularla.

Agregaremos huevos al loot table del bloque de mena de hierro.

#### Detectando el Cargado de Loot Tables

El Fabric API tiene un evento que es llamado cuando los loot tables son cargados, llamado `LootTableEvents.MODIFY`. Puedes registrar un callback para el evento en tu inicializador de mod. Verifiquemos también que el loot table actual sea el del bloque de mena de hierro.

@[code lang=java transclude={38-40}](@/reference/latest/src/main/java/com/example/docs/event/FabricDocsReferenceEvents.java)

#### Agregando Items al Loot Table

En los loot tableas, los items son almacenados en _loot pool entries_ (o entradas en el grupo de loots), y las entradas son guardadas en _loot pools_ (o grupos de loot). Para agregar un item, necesitaremos agregar un grupo con una entrada de item al loot table.

Podemos crear un grupo con `LootPool#builder`, y agregarlo al loot table.

Nuestro grupo no tiene ningún item, asique haremos una entrada de item usando `ItemEntry#builder`, y la agregaremos al grupo.

@[code highlight={6-7} transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/event/FabricDocsReferenceEvents.java)

## Eventos Personalizados

Algunas áreas del juego no tienen enganches proveídos por el Fabric API, así que puedes usar un mixin o puedes crear tu propio evento.

Veremos como crear un evento que es llamado cuando una oveja es esquilada. El proceso de creación de un evento es:

- Crear la interfaz de callback del evento
- Activando el evento desde un mixin
- Crear una implementación de prueba

### Crear la interfaz de callback del evento

La interfaz de callback describe lo que debe ser implementado por los usuarios del evento que detectarán tu evento. La interfaz del callback también describe como el evento será llamado desde nuestro mixin. Es convencional poner un objecto `Event` como un miembro en la interfaz de callback, el cual identificará nuestro evento.

Para nuestra implementación de `Event`, escogeremos usar un evento respaldado por un array (array-backed event). El array contendrá todos los escuchadores de evento que están detectando este evento.

Nuestra implementación llamará los escuchadores de evento en orden hasta que uno de ellos no retorne `ActionResult.PASS`. Esto signfica que un usuario puede decir "_cancela esto_", "_aprueba esto_" o "_no me importa, déjaselo al siguiente escuchador del evento_" usando el valor retornado.

Usar `ActionResult` como valor de retorno es una manera convencional para hacer que los diferentes usuarios del evento cooperen de esta manera.

Necesitarás crear una interfaz que tiene una instancia de `Event` y un método para la implementación de la respuesta. Una configuración básica para nuestro callback de esquilado de oveja es:

@[code lang=java transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/event/SheepShearCallback.java)

Veamos este códigdo con más detalle. Cuando el invocador es llamado, iteramos sobre todos los escuchadores:

@[code lang=java transclude={21-22}](@/reference/latest/src/main/java/com/example/docs/event/SheepShearCallback.java)

Entonces llamamos nuestro método (en este caso, `interact`), en el escuchador para obtener su respuesta:

@[code lang=java transclude={33-33}](@/reference/latest/src/main/java/com/example/docs/event/SheepShearCallback.java)

Si el escuchador dice que tenemos que cancelar (`ActionResult.FAIL`) o terminar completamente (`ActionResult.SUCCESS`), el callback retorna el resultado y termina el loop. `ActionResult.PASS` prosigue al siguiente escuchador, y en la mayoría de los casos debería resultar en éxito si no hay más escuchadores registrados:

@[code lang=java transclude={25-30}](@/reference/latest/src/main/java/com/example/docs/event/SheepShearCallback.java)

Podemos agregar Javadocs por encima de las clases de callback para documentar que es lo que hace cada `ActionResult`. En nuestro caso, puede ser:

@[code lang=java transclude={9-16}](@/reference/latest/src/main/java/com/example/docs/event/SheepShearCallback.java)

### Activando el Evento Desde un Mixin

Ya tenemos el esqueleto básico de nuestro evento, pero necesitamos llamarlo o activarlo. Ya que queremos que nuestro evento sea llamado cuando un jugador trata de esquilar una oveja, podemos llamado el invocador `invoker` del evento en `SheepEntity#interactMob`, cuando el método `sheared()` es llamado (osea que la oveja puede ser esquilada, y el jugador está sosteniendo tijeras):

@[code lang=java transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/mixin/event/SheepEntityMixin.java)

### Creando una Implementación de Prueba

Ahora necesitamos probar nuestro evento. Puedes registrar un escuchador en tu método de inicialización (o en otro lugar, si lo prefieres) y poner tu propio código ahí. En este ejemplo, cuando la oveja es esquilada, suelta un diamante en vez de lana:

@[code lang=java transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/event/FabricDocsReferenceEvents.java)

Si entras al juego y esquilas una oveja, un diamante debería ser soltado en vez de lana.
