---
title: Geração de Receita
description: Um guia para configurar geração de receita com datagen.
authors:
  - CelDaemon
  - skycatminepokie
  - Spinoscythe
authors-nogithub:
  - jmanc3
  - mcrafterzz
---

<!---->

:::info PRÉ-REQUISITOS

Tenha certeza que você completou o processo de [configuração do datagen](./setup) primeiro.

:::

## Configuração {#setup}

Primeiro, nós precisamos do nosso provedor. Crie uma classe que estenda `FabricRecipeProvider`. Toda a nossa geração de receitas acontecerá dentro do método `buildRecipes` do nosso provedor.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_provider

Para finalizar a configuração, adicione esse provedor ao seu `DataGeneratorEntrypoint` dentro do método `onInitializeDataGenerator`.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_recipes_register

## Receitas Sem Formato {#shapeless-recipes}

Receitas sem formato são bem diretas. Apenas as adicione ao método `buildRecipes` dentro do seu provedor:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_shapeless

### Receitas de Corante {#dye-recipes}

Receitas de corante são usadas para tingir itens no seu inventário.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_dye

## Receitas com Formato {#shaped-recipes}

Para uma receita com formato, você deve definir a forma usando um `String`, e depois definir o que cada `char` dentro do `String` representa.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_shaped

::: tip

Existem vários métodos ajudantes para criar receitas comuns. Dê uma olhada no que `RecipeProvider` tem a oferecer! Use <kbd>Alt</kbd>+<kbd>7</kbd> no IntelliJ para abrir a estrutura de uma classe, incluindo uma lista de método.

:::

## Outras Receitas {#other-recipes}

Outras receitas funcionam de forma similar, porém requerem alguns parâmetros adicionais. Por exemplo, as receitas de fundição precisam saber o quanto de experiência elas devem recompensar.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_smelting

Defumar é um pouco diferente, ela não usa o mesmo gerador de receita dos blocos de fundição e afins.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_smoking

## Condições de Recurso {#resource-conditions}

Para aplicar uma [condição de recurso](../resource-conditions) a uma receita gerada por dados, envolva a saída com `withConditions` e forneça qualquer condição de recurso que você deseja aplicar. Isso gerará então uma receita e uma conquista que possui condições de recurso aplicadas:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_conditions
