---
title: Tipos de Daño
description: Aprende a agregar tipos de daño personalizados.
authors:
  - dicedpixels
  - hiisuuii
  - mattidragon

search: false
---

# Tipos de Daño

Los tipos de daño definen los tipos de daño que pueden tomar las entidades. Desde Minecraft 1.19.4, la creación de nuevos tipos de daño se ha vuelto basada en datos, lo que significa que se crean mediante archivos JSON.

## Creando un Tipo de Daño

Procedamos a crear un nuevo tipo de daño llamado _Tater_. Empezaremos creando el archivo JSON para tu tipo de daño. Este archivo será puesto en el folder de `data` de tu mod, en un sub-folder llamado `damage_types`.

```:no-line-numbers
resources/data/fabric-docs-reference/damage_type/tater.json
```

Tiene la siguiente estructura:

@[code lang=json](@/reference/latest/src/main/generated/data/fabric-docs-reference/damage_type/tater.json)

Este tipo de daño personalizado causa un aumento de 0.1 en [cansancio de hambre](https://minecraft.wiki/w/Hunger#Exhaustion_level_increase) cada vez que el jugador toma daño, cuando el daño es ocasionado por una fuente viviente que no sea otro jugador (por ejemplo, un bloque). Adicionalmente, la cantidad de daño dado dependerá de la dificultad del mundo

::: info

Verifica la [Wiki de Minecraft](https://minecraft.wiki/w/Damage_type#JSON_format) para ver todas las posibles llaves y valores.

:::

### Accediendo los Tipos de Daño en El Código

Cuando necesitamos acceder nuestro tipo de daño en el código, necesitaremos usar su `RegistryKey` (Llave de Registro) para crear una instancia de `DamageSource` (Fuente de Daño).

El `RegistryKey` puede ser obtenida de la siguiente manera:

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/damage/FabricDocsReferenceDamageTypes.java)

### Usando los Tipos de Daño

Para demostrar el uso de tipos de daño personalizados, usaremos un bloque personalizado llamado **Tater Block**. Hagamos que cuando una entidad viviente pise sobre un **Bloque de Tater**, dará un daño de _Tater_.

Puedes anular el método `onSteppedOn` para dar este daño.

Empezamos creando un `DamageSource` de nuestro tipo de daño personalizado.

@[code lang=java transclude={21-24}](@/reference/latest/src/main/java/com/example/docs/damage/TaterBlock.java)

Después, llamamos `entity.damage()` con nuestro `DamageSource` y una cantidad.

@[code lang=java transclude={25-25}](@/reference/latest/src/main/java/com/example/docs/damage/TaterBlock.java)

La implementación del bloque completa:

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/damage/TaterBlock.java)

Ahora cuando una entidad viviente pise sobre nuestro bloque personalizado, tomará 5 de daño (2.5 corazones) usando nuestro tipo de daño personalizado.

### Mensaje de Muerte Personalizado

Puedes definir un mensje de muerte para el tipo de daño en el formato de `death.attack.<message_id>` en nuestro archivo `en_us.json` de nuestro mod.

@[code lang=json transclude={4-4}](@/reference/latest/src/main/resources/assets/fabric-docs-reference/lang/en_us.json)

Cuando suceda una muerta por nuestro tipo de daño, verás el siguiente mensaje:

![Efecto en el inventario del jugador](/assets/develop/tater-damage-death.png)

### Etiquetas de Tipo de Daño

Algunos tipos de daño pueden pasar por la armadura, efectos de estado, y similares. Los Tags (Etiquetas) controlan estos tipos de propiedades de los tipos de daño.

Puedes encontrar los tags de tipos de daño existentes en `data/minecraft/tags/damage_type`.

::: info

Verifica la [Wiki de Minecraft](https://minecraft.wiki/w/Tag#Damage_types) para una lista completa de los tags de tipo de daño.

:::

Agreguemos nuestro tipo de daño de Tater al tag de `bypasses_armor`.

Para agregar nuestro tipo de daño a uno de estos tags, crearemos un archivo JSON bajo el namespace de `minecraft`.

```:no-line-numbers
data/minecraft/tags/damage_type/bypasses_armor.json
```

Con el siguiente contenido:

@[code lang=json](@/reference/latest/src/main/generated/data/minecraft/tags/damage_type/bypasses_armor.json)

Asegúrate que tu tag no reemplace el tag existente dándole un valor de `false` a la llave de `replace`.
