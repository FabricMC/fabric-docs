---
title: Geração de Tabelas de Itens
description: Um guia para configurar geração de tabela de item com datagen.
authors:
  - CelDaemon
  - JustinHuPrime
  - LunarTremor
  - MarcusElg
  - matthewperiut
  - skycatminepokie
  - Spinoscythe
authors-nogithub:
  - jmanc3
---

<!---->

:::info PRÉ-REQUISITOS

Tenha certeza que você completou o processo de [configuração do datagen](./setup) primeiro.

:::

Você precisará de diferentes provedores (classes) para blocos, baús e entidades. Lembre-se de adicionar todos ao seu `DataGeneratorEntrypoint` dentro do método `onInitializeDataGenerator`.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_loot_tables_register

## Tabela de Itens Explicado {#loot-tables-explained}

**Tabela de Itens** define o que você obtém ao quebrar um bloco (não incluindo conteúdos, como em baús), matar uma entidade ou abrir um contêiner recém-gerado. Cada tabela têm **pools** de quais cada itens são selecionados. Tabelas também têm **funções**, quais modificam o resultado do espólio de alguma forma.

As tabelas de itens possuem **entradas**, **condições**, funções, **rolls** e **rolls bônus**. Entradas são grupos, sequências ou possibilidades de itens, ou apenas itens. Condições são coisas testadas em um mundo, como encantamentos em uma ferramenta ou uma chance aleatória. O número mínimo de números de entrada escolhidas por uma pool são chamadas rolls, e qualquer coisa além isso é chamada roll bônus.

## Blocos {#blocks}

A fim de blocos droparem itens, incluindo eles mesmos, nós precisamos fazer uma tabela de itens. Crie uma classe que estenda `FabricBlockLootSubProvider`:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModBlockLootTableProvider.java#datagen_loot_tables_block_provider

Certifique-se de adicionar esse provedor ao seu pacote!

Existem vários métodos ajudantes disponíveis para lhe ajudar a criar as suas tabelas de itens. Nós não cobriremos todos eles, portanto, certifique-se de verificá-los no seu IDE.

Vamos adicionar alguns drops no método `generate`:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModBlockLootTableProvider.java#datagen_loot_tables_block_drops

## Chests {#chests}

Saques de baús são um pouco mais complicados do que saques de blocos. Crie uma classe que estende `SimpleFabricLootTableSubProvider`, parecido com o exemplo abaixo **e adicione-a ao seu pacote**.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModChestLootTableProvider.java#datagen_loot_tables_chest_provider

Nós precisaremos de um `ResourceKey<LootTable>` para a nossa tabela de item. Vamos colocá-lo em uma nova classe chamada `ModLootTables`. Certifique-se que esse está no seu conjunto de origem `main` se você estiver usando fontes divididas.

<<< @/reference/latest/src/main/java/com/example/docs/ModLootTables.java#datagen_loot_tables_mod_loot_tables

Então, nós podemos gerar uma tabela de item dentro do método `generate` do seu provedor.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModChestLootTableProvider.java#datagen_loot_tables_chest_loot

## Condições de Recurso {#resource-conditions}

Para aplicar uma [condição de recurso](../resource-conditions) a uma tabela de itens gerada a partir de dados, chame `withConditions` e forneça qualquer condição de recurso que você gostaria de aplicar, então chame um método da tabela de itens, como, por exemplo, `dropSelf`. Isso gerará então uma tabela de itens que possui condições de recurso aplicadas:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModBlockLootTableProvider.java#datagen_loot_tables_conditions
