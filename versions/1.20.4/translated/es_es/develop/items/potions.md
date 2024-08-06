---
title: Pociones
description: Aprende a agregar pociones personalizadas para varios efectos de estado.
authors:
  - dicedpixels
  - PandoricaVi
  - Drakonkinst

search: false
---

# Pociones

Las pociones son items consumibles que le dan un efecto a una entidad. Un jugador puede crear una poción usando un Soporte para Pociones u obtenerlas como items a partir de varias mecánicas del juego.

## Pociones Personalizadas

Agregar una poción involucra un patrón similar a añadir un item. Crearás una instancia de tu poción y la registrarás llamando `BrewingRecipeRegistry.registerPotionRecipe`.

:::info
Cuando Fabric API está presente, `BrewingRecipeRegistry.registerPotionRecipe` se hace accesible mediante un Access Widener (Ampliador de Acceso).
:::

### Crear la Poción

Empecemos declarando un miembro para almacenar la instancia de tu `Potion`. Estaremos usando la clase inicializadora para hacer esto.

@[code lang=java transclude={18-27}](@/reference/latest/src/main/java/com/example/docs/potion/FabricDocsReferencePotions.java)

Pasamos una instancia de `StatusEffectInstance` (Instancia de Efecto de Estado), el cual tiene 3 parámetros:

- `StatusEffect type` - Un efecto. Aquí usamos nuestro efecto personalizado. También puedes usar efectos vanila mediante la clase `net.minecraft.entity.effect.StatusEffects`.
- `int duration` - La duración del efecto medido en ticks del juego.
- `int amplifier` - Un amplificador para el efecto. Por ejemplo, Prisa Minera II tendría un amplificador de 1.

:::info
Para crear tu propio efecto, por favor visita la guía sobre [Efectos](../entities/effects).
:::

### Registrar la Poción

En nuestro inicializador, llamamos `BrewingRecipeRegistry.registerPotionRecipe`.

@[code lang=java transclude={30-30}](@/reference/latest/src/main/java/com/example/docs/potion/FabricDocsReferencePotions.java)

`registerPotionRecipe` (registrar receta de poción) tiene 3 parámetros:

- `Potion input` - La poción inicial. Usualmente esto puede ser una Botella de Agua o una Poción Rara.
- `Item item` - El item que es el ingrediente principal de esta poción.
- `Potion output`- La poción resultante.

Si usas Fabric APi, el invocador de mixin no es necesario y se puede llamar `BrewingRecipeRegistry.registerPotionRecipe` directamente.

El ejemplo completo:

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/potion/FabricDocsReferencePotions.java)

Una vez registrado, puedes crear una poción de Tater usando una patata.

![Efecto en el inventario del jugador](/assets/develop/tater-potion.png)

::: info
**Registering Potions Using an `Ingredient`**

Con la ayuda de Fabric API, es posible registrar una poción usando un `Ingredient` (Ingrediente) en vez de `Item` usando `net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistry`.
:::

### Registrar la Poción sin Fabric API

Sin Fabric API, el método `BrewingRecipeRegistry.registerPotionRecipe` será privado. Para acceder este método, puedes usar el siguiente invocador de mixin o usar un Ampliador de Acceso.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/mixin/potion/BrewingRecipeRegistryInvoker.java)
