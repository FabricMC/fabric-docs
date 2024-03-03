---
title: Creando Partículas Personalizadas
description: Aprende a crear partículas personalizadas usando el Fabric API.
authors:
  - Superkat32
---

# Creando Partículas Personalizadas

Las partículas son una poderosa herramienta. Pueden agregar atmósfera a una hermosa escena, o agregar tensión durante una pelea contra un jefe. ¡Agreguemos una!

## Registrar una partícula personalizada

Para agregar una partícula personalizada, primero necesitaremos registrar un `ParticleType` (Tipo de Partícula) en tu inicializador de mod usando tu Id de mod.

@[code lang=java transcludeWith=#particle_register_main](@/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java)

El texto "my_particle" en letras minúsculas es el JSON path (dirección JSON) para la textura de la partícula. Crearás un archivo JSON con ese mismo nombre más adelante.

## Registración en el Cliente

Después de registrar la partícula en la entrada de `ModInitializer`, también necesitarás registrar la partícula en la entrada de `ClientModInitializer` (Inicializador del Mod de Cliente).

@[code lang=java transcludeWith=#particle_register_client](@/reference/latest/src/client/java/com/example/docs/FabricDocsReferenceClient.java)

En este ejemplo, estamos registrando nuestra partícula en el lado del cliente. Después estamos dándole movimiento usando la fábrica de partículas de la vara del end.

Puedes reemplazar la fábrica con otra fábrica de otra partícula, o si quieres, tu propia fábrica.

## Crear el archivo JSON y añadir texturas

Necesitarás crear 3 folders en tu folder de `resources` (recursos).

Empecemos creando los folders necesarios para la(s) texturas de la partícula. Agrega los nuevos folders `resources/assets/<mod id here>/textures/particle` en tu directorio. Coloca las texturas de la partícula que quieres usar en el folder de `particle`.

Para este ejemplo, solo pondremos una textura, llamada "myparticletexture.png".

Después, crea el folder `particles` en el folder `resources/assets/<mod id here>/particles`, y crea un archivo json llamado `my_particle.json` - este archivo le hará saber a Minecraft que texturas tu partícula debería usar.

@[code lang=json](@/reference/latest/src/main/resources/assets/fabric-docs-reference/particles/my_particle.json)

Puedes agregar más texturas al array de `texturas` para crear una animación de partícula. La partícula rotará las texturas en el array, empezando con la primera textura.

## Probando la nueva partícula

Una vez completado el archivo json y guardado tu trabajo, ya puedes cargar Minecraft y probar que todo esté bien.

Puedes ver si todo ha funcionado correctamente con el siguiente comando:

```
/particle <mod id here>:my_particle ~ ~1 ~
```

Esta partícula se generará en la cabeza del jugador con este comando, asique puede que tengas que caminar hacia atrás para verla. También puedes usar un bloque de comandos para convocar la partícula con el mismo comando.
