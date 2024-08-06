---
title: Creando Partículas Personalizadas
description: Aprende a crear partículas personalizadas usando el Fabric API.
authors:
  - Superkat32

search: false
---

# Creando Partículas Personalizadas

Las partículas son una poderosa herramienta. Pueden agregar atmósfera a una hermosa escena, o agregar tensión durante una pelea contra un jefe. ¡Agreguemos una!

## Registrar una partícula personalizada

Agregaremos una nueva partícula brillante cuyo movimiento se asemejara al de la partícula de una vara del end.

Primero tenemos que registrar un `ParticleType` (Tipo de Partícula) en nuestra clase inicializadora de mod usando nuestro _mod id_.

@[code lang=java transcludeWith=#particle_register_main](@/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java)

El "sparkle_particle" en letras minúsculas es la dirección JSON para la textura de la partícula. Crearás un archivo JSON con ese mismo nombre más adelante.

## Registración en el Cliente

Después de registrar la partícula en la entrada de `ModInitializer`, también necesitarás registrar la partícula en la entrada de `ClientModInitializer` (Inicializador del Mod de Cliente).

@[code lang=java transcludeWith=#particle_register_client](@/reference/latest/src/client/java/com/example/docs/FabricDocsReferenceClient.java)

En este ejemplo, estamos registrando nuestra partícula en el lado del cliente. Después estamos dándole movimiento usando la fábrica de partículas de la vara del end. Esto significa que nuestra partícula se moverá exactamente como una partícula de vara del end.

::: tip
You can see all the particle factories by looking at all the implementations of the `ParticleFactory` interface. This is helpful if you want to use another particle's behaviour for your own particle.

- Tecla de acceso rápido de IntelliJ: Ctrl+Alt+B
- Tecla de acceso rápido de Visual Studio Code: Ctrl+F12
:::

## Crear el archivo JSON y añadir texturas

Tienes que crear 2 folders in tu folder de `resources/assets/<mod id here>/`.

| Dirección del Folder | Explicación                                                                                                                         |
| -------------------- | ----------------------------------------------------------------------------------------------------------------------------------- |
| `/textures/particle` | El folder de `particle` (partícula) contendrá todas las texturas para todas tus partículas.      |
| `/particles`         | El folder de `particles` (partículas) contendrá todos los archivos json de todas tus partículas. |

Para este ejemplo, solo tendremos una textura en `textures/particle` llamada "sparkle_particle_texture.png".

Después, creamos un nuevo archivo JSON en `particles` con el mismo nombre que la dirección JSON que usaste para registrar tu `ParticleType`. Para este ejemplo, tendremos que crear `sparkle_particle.json`. Este archivo es importante, ya que le hace saber a Minecraft que texturas debería usar para nuestra partícula.

@[code lang=json](@/reference/latest/src/main/resources/assets/fabric-docs-reference/particles/sparkle_particle.json)

:::tip
Puedes agregar más texturas al array de `texturas` para crear una animación de partícula. La partícula rotará las texturas en el array, empezando con la primera textura.
:::

## Probando la nueva partícula

¡Una vez que termines tu archivo JSON y hayas guardado tu trabajo, ya puedes iniciar Minecraft y probar que todo esté bien!

Puedes ver si todo ha funcionado correctamente con el siguiente comando:

```mcfunction
/particle <mod id here>:sparkle_particle ~ ~1 ~
```

![La partícula siendo mostrada](/assets/develop/rendering/particles/sparkle-particle-showcase.png)

:::info
La partícula aparecerá dentro de la cabeza del jugador con este comando. Probablemente tengas que caminar para atrás para poder verla.
:::

También puedes usar un bloque de comandos para invocar la partícula con el mismo comando.
