---
title: Efectos de Estado
description: Aprende a añadir efectos de estado personalizados.
authors:
  - dicedpixels
  - YanisBft
  - FireBlast
  - Friendly-Banana
  - SattesKrokodil
authors-nogithub:
  - siglong
  - tao0lu

search: false
---

# Efectos de Estado

Los efectos de estado, o simplemente estados, son una condición que puede afectar a una entidad. Pueden ser positivos, negativos o neutrales en naturaleza. El juego base aplica estos efectos de varias maneras como comida, pociones, etc.

El comando `/effect` puede ser usado para aplicar efectos en una entidad.

## Efectos de Estado Personalizados

En este tutorial añadiremos un nuevo efecto de estado personalizado llamado _Tater_, el cual te da un punto de experiencia por cada tick del juego.

### Extiende `StatusEffect`

Vamos a crear una para nuestro efecto de estado personalizado extendiendo la clase `StatusEffect`, el cual es la clase base para todos los efectos.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/effect/TaterEffect.java)

### Registrar tu Efecto Personalizado

Similar a la registración de bloques e items, usamos `Registry.register` para registrar nuestro efecto personalizado en el registro de `STATUS_EFFECT`. Esto se puede hacer en nuestro inicializador.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/effect/FabricDocsReferenceEffects.java)

### Traducciones y Texturas

Puedes asignar un nombre a tu efecto de estado y proveer una textura de ícono para que aparezca en la pantalla de inventario.

#### Textura

El ícono del efecto de estado es una imagen PNG de 18x18 pixeles. Coloca tu ícono personalizado en:

```:no-line-numbers
resources/assets/fabric-docs-reference/textures/mob_effect/tater.png
```

![Efecto en el inventario del jugador](/assets/develop/tater-effect.png)

#### Traducciones

Como cualquier otra traducción, puedes agregar una entrada con el formato de ID `"effect.<mod-id>.<effect-identifier>": "Valor"` al archivo de idioma.

::: code-group

```json[assets/fabric-docs-reference/lang/en_us.json]
{
  "effect.fabric-docs-reference.tater": "Tater"
}
```

### Probando

Usa el comando `/effect give @p fabric-docs-reference:tater` para darle al jugador nuestro efecto Tater. Usa el comando `/effect clear` para remover el efecto.

::: info
Para crear una poción que use este efecto, por favor visita la guía sobre [Pociones](../items/potions).
:::
