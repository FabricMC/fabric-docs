---
title: Geração de Encantamentos
description: Um guia para gerar encantamentos via datagen.
authors:
  - CelDaemon
---

<!---->

:::info PRÉ-REQUISITOS

Tenha certeza que você completou o processo de [configuração do datagen](./setup) primeiro.

:::

## Configuração {#setup}

Antes de implementar o gerador, crie o pacote `enchantment` no conjunto de fonte principal e adicione a classe `ModEnchantments` dentro dele. Então adicione o método `key` nessa nova classe.

<<< @/reference/latest/src/main/java/com/example/docs/enchantment/ModEnchantments.java#key_helper

Use esse método para criar um `ResourceKey` para o seu encantamento.

<<< @/reference/latest/src/main/java/com/example/docs/enchantment/ModEnchantments.java#register_enchantment

Agora estamos prontos para adicionar o gerador. No pacote datagen, crie uma classe que estenda `FabricDynamicRegistryProvider`. Nessa classe recém-criada, adicione um construtor que combine com `super` e implemente os métodos `configure` e `getName`.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java#provider

Então, adicione o método ajudante `register` na classe recém-criada.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java#register_helper

Agora adicione o método `bootstrap`. Aqui, nós registraremos os encantamentos que nós queremos adicionar ao jogo.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java#bootstrap

No seu `DataGeneratorEntrypoint`, sobrescreva o método `buildRegistry` e registre o nosso método bootstrap.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_enchantments_bootstrap

Finalmente, certifique-se que o seu novo gerador está registrado dentro do método `onInitializeDataGenerator`.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_enchantments_register

## Criando o Encantamento {#creating-the-enchantment}

Para criar a definição para o nosso encantamento personalizado, nós usaremos o método `register` na nossa classe geradora.

Registre o seu encantamento no método `bootstrap` do gerador, usando o encantamento registrado em `ModEnchantments`.

Nesse exemplo, nós usaremos o efeito de encantamento criado no [Efeitos de Encantamento Personalizados](../items/custom-enchantment-effects), mas você pode também usar os [efeitos de encantamento vanilla](https://minecraft.wiki/w/Enchantment_definition#Effect_components).

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java#register_enchantment

Agora simplesmente execute a geração de dados e o seu novo encantamento estará disponível no jogo!

## Condições de Efeitos {#effect-conditions}

A maior parte dos tipos de efeitos de encantamento são condicionais. Ao adicionar esses efeitos, é possível passar condições à chamada `withEffect`.

::: info

Para ter uma visão geral dos tipos de condições disponíveis e dos seus usos, dê uma olhada na [classe `Enchantments`](https://mcsrc.dev/#1/1.21.11_unobfuscated/net/minecraft/world/item/enchantment/Enchantments#L126).

:::

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java#effect_conditions

## Efeitos Múltiplos {#multiple-effects}

O `withEffect` pode ser encadeado para adicionar múltiplos efeitos a um único encantamento. No entanto, esse método exige que você especifique as condições de efeito para todos os efeitos.

No lugar de compartilhar as condições definidas e os alvos com múltiplos efeitos. `AllOf` pode ser usado para combinar eles em um único efeito.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentGenerator.java#multiple_effects

Note que o método a ser usado depende do tipo de efeito sendo adicionado. Por exemplo, `EnchantmentValueEffect` exige `AnyOf.valueEffects`. Diferir tipos de efeitos ainda exige chamadas `withEffect` adicionais.

## Mesa de Encantamentos {#enchanting-table}

Mesmo que nós tenhamos especificado o peso do encantamento (ou a chance) na nossa definição de encantamento, ele não aparecerá na mesa de encantamentos por padrão. Para permitir que o nosso encantamento seja negociado por aldeões e que ele apareça na mesa de encantamentos, nós precisamos adicioná-lo à tag `non_treasure`.

Para fazer isso, nós podemos criar um provedor de tag. Crie uma classe que estende `FabricTagProvider<Enchantment>` no pacote `datagen`. Então implemente o construtor com `Registries.ENCHANTMENT` como o parametro `registryKey` para `super`, e então crie o método `addTags`.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentTagProvider.java#provider

Agora nós podemos adicionar o nosso encantamento ao `EnchantmentTags.NON_TREASURE` chamando o construtor de dentro do método `addTags`.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentTagProvider.java#non_treasure_tag

## Maldições {#curses}

Maldições também são implementadas usando tags. Nós podemos usar o provedor de tags da [sessão da Mesa de Encantamentos](#enchanting-table).

No método `addTags`, simplesmente adicione os seus encantamentos na tag `CURSE` para marcá-los como uma maldição.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModEnchantmentTagProvider.java#curse_tag
