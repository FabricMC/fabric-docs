---
title: Poções
description: Aprenda a adicionar poções customizadas para vários efeitos de estado.
authors:
  - dicedpixels
  - PandoricaVi
  - Drakonkinst

search: false
---

# Poções

Poções são consumíveis que concedem efeitos a uma entidade. Um jogador pode preparar poções usando um Suporte de Poções ou obtê-las como itens através de várias outras mecânicas do jogo.

## Poções Personalizadas

Adicionar uma poção é um processo similar ao de adicionar um item. Você criará uma instância de sua poção e a registrará chamando `BrewingRecipeRegistry.registerPotionRecipe`.

:::info
Quando o Fabric API está presente, `BrewingRecipeRegistry.registerPotionRecipe` se torna acessível através de um Acess Widener.
:::

### Criando a Poção

Vamos começar declarando um campo para armazenar sua instância de `Potion`. Utilizaremos a classe inicializadora diretamente para segurar isso.

@[code lang=java transclude={18-27}](@/reference/latest/src/main/java/com/example/docs/potion/FabricDocsReferencePotions.java)

Passamos uma instância de `StatusEffectInstance`, que leva 3 parâmetros:

- `StatusEffect type` - Um efeito. Usamos nosso efeito personalizado aqui. Você também pode acessar efeitos vanilla através de `net.minecraft.entity.effect.StatusEffects`.
- `int duration` - Duração do efeito em ticks do jogo.
- `int amplifier` - Um amplificador para o efeito. Por exemplo, Pressa II teria um amplificador de 1.

:::info
Para criar seu próprio efeito, consulte o guia de [Efeitos](../entities/effects).
:::

### Registrando a Poção

No nosso inicializador, chamamos `BrewingRecipeRegistry.registerPotionRecipe`.

@[code lang=java transclude={30-30}](@/reference/latest/src/main/java/com/example/docs/potion/FabricDocsReferencePotions.java)

`registerPotionRecipe` leva 3 parâmetros:

- `Potion input` - A poção inicial. Normalmente isso pode ser uma Garrafa de Água ou uma Poção Estranha.
- `Item item` - O item que é o ingrediente principal da poção.
- `Potion output` - A poção resultante.

Se você usa a Fabric API, o invocador de mixin não é necessário e uma chamada direta do `BrewingRecipeRegistry.registerPotionRecipe` pode ser feita.

O exemplo completo:

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/potion/FabricDocsReferencePotions.java)

Assim que registrada, você pode preparar uma poção Tater usando uma batata.

![Efeito no inventário do jogador](/assets/develop/tater-potion.png)

:::info Registrando Poções usando um `Ingredient`

Com ajuda do Fabric API, é possível registrar uma poção usando um `Ingredient` em vez de um `Item` usando `net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistry`.
:::

### Registrando a Poção Sem o Fabric API

Sem o Fabric API, `BrewingRecipeRegistry.registerPotionRecipe` será privado. Para acessar este método, use o seguinte invocador de mixin ou use um Acess Widener.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/mixin/potion/BrewingRecipeRegistryInvoker.java)
