---
title: Geração de Progresso
description: Um guia para configurar geração de progresso com datagen.
authors:
  - cassiancc
  - CelDaemon
  - MattiDragon
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

Primeiro, nós precisamos criar o nosso provedor. Crie uma classe que estende `FabricAdvancementProvider` e preencha os métodos base:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen_advancements_provider_start

Para finalizar a configuração, adicione esse provedor ao seu `DataGeneratorEntrypoint` dentro do método `onInitializeDataGenerator`.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_advancements_register

## Estrutura das Conquistas {#advancement-structure}

Uma conquista é feita de alguns componentes variados. Juntamente aos requisitos, chamado "criterion" (critérios), ele pode ter:

- Algum `DisplayInfo` que diz ao jogo como mostrar a conquista aos jogadores,
- `AdvancementRequirements`, cujo é a lista das listas de critério, exigindo no mínimo um critério de cada sub-lista para ser completado,
- `AdvancementRewards` que o jogador recebe por completar a conquista.
- O `Strategy` que diz à conquista como lidar com múltiplos critérios e
- Um `Advancement` mãe, que organiza a hierarquia que você vê na tela de "Advancements".

## Conquistas Simples {#simple-advancements}

Aqui temos uma conquista simples por pegar um bloco de terra:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen_advancements_simple_advancement

:::details Saída de JSON

<<< @/reference/latest/src/main/generated/data/example-mod/advancement/get_dirt.json

:::

## Classes Mães {#parents}

Para criar ou estender uma árvore de conquistas, nós podemos configurar uma classe mãe para a nossa conquista. Para fazer isso, chame `Advancement.Builder#parent(...)` e passe uma referência para a conquista mãe.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#reference_parent

Caso não haja nenhuma referência direta disponível para a conquista mãe (por exemplo, usando uma conquista vanilla como uma conquista mãe), um espaço reservado pode ser criado usando um identificador.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#placeholder_parent

Agora as suas conquistas devem ser mostradas como uma árvore no menu de conquistas.

![Árvore de Conquista](/assets/develop/data-generation/advancement_tree.png)

## Critérios Múltiplos {#multiple-criteria}

Para ter mais condições de conquistas nas nossas conquistas, nós podemos chamar `Advancement.Builder#addCriteria(...)` mais de uma vez com critérios adicionais.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#multiple_criteria

Por padrão, todos os critérios devem ser atendidos para completar a conquista. Nós podemos mudar esse comportamento fornecendo uma estratégia diferente.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#requirements_strategy

## Recompensas {#rewards}

Nós podemos anexar recompensas às nossas conquistas, que serão concedidas quando um jogador completa a conquista. Nós podemos fazer isso chamando `Advancement.Builder#rewards(...)` com as recompensas que queremos adicionar.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#experience_reward

Há múltiplos tipos de recompensas disponíveis:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#reward_types

## Critério Personalizado {#custom-criteria}

::: warning

Enquanto a datagen pode estar do lado do cliente, os `Criterion`s e `Predicate`s estão no conjunto de fonte principal (ambos os lados), já que o servidor precisa acioná-los e avaliá-los.

:::

### Definições {#definitions}

Um **critério** (em inglês criterion, para plural criteria) é algo que o jogador pode fazer (ou pode acontecer com o jogador) que pode ser contado para o progresso. O jogo vem com vários [critérios](https://minecraft.wiki/w/Advancement_definition#List_of_triggers), cujo podem ser encontrados no pacote `net.minecraft.advancements.criterion`. Geralmente, você precisa apenas de um novo critério se acrescentar uma mecânica personalizada ao jogo.

**Condições** são avaliadas pelos critérios. Um critério só é contado se todas as condições relevantes são atendidas. Condições são normalmente expressas com um predicate.

Um **predicate** (predicado, em português) é algo que toma um valor e retorna uma `boolean`. Por exemplo, o `Predicate<Item>` pode retornar `true` se o item é um diamante, enquanto o `Predicate<LivingEntity>` pode retornar `true` se a entidade não é hostil com aldeões.

### Criando Critérios Personalizados {#creating-custom-criteria}

Primeiro, precisamos de uma nova mecânica para implementar. Vamos dizer ao jogador qual ferramenta ele usou toda vez que ele quebra um bloco.

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java#datagen_advancements_entrypoint

Note que esse código é muito ruim. O `HashMap` não está armazenado em nenhum lugar persistente, então ele irá reniciar toda vez que o jogo é reniciado. É apenas para mostrar os `Criterion`s. Comece o jogo e teste!

A seguir, vamos criar nosso critério personalizado, `UseToolCriterion`. Ele vai precisar sua própria classe `Conditions` para acompanhar, então nós vamos fazer as duas de uma vez:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java#datagen_advancements_criterion_base

Ufa, é muita coisa! Vamos por partes.

- `UseToolCriterion` é um `SimpleCriterionTrigger`, cujo pode se aplicar `Conditions`.
- `Conditions` têm um campo `playerPredicate`. Todas as `Conditions` devem ter um predicado de jogador (tecnicamente um `LootContextPredicate`).
- `Conditions` também possuem um `CODEC`. Esse `Codec` é simplesmente o codec para esse único campo `playerPredicate`, com instruções extras para converter entre eles (`xmap`).

::: info

Para aprender mais sobre codecs, veja a página [Codecs](../codecs).

:::

Nós vamos precisar de uma forma de verificar se as condições foram atendidas. Vamos adicionar um método ajudante em `Conditions`:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java#datagen_advancements_conditions_test

Agora que nós temos um critério e suas condições, nós vamos precisar de um jeito de ativá-lo. Adicione um método gatilho para `UseToolCriterion`:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java#datagen_advancements_criterion_trigger

Quase lá! A seguir, nós vamos precisar de uma instância do nosso critério para trabalhar. Vamos colocar ela em uma nova classe, chamada `ModCriteria`, com um método ajudante para registrar facilmente o novo critério.

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java#datagen_advancements_mod_criteria

Para ter certeza que nossos critérios foram iniciados no tempo certo, adicione um método `init` vazio:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java#datagen_advancements_mod_criteria_init

E você pode chamar no seu iniciador de mod:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java#datagen_advancements_call_init

Finalmente nós podemos ativar nossos critérios. Adicione isso no lugar que nós enviamos a mensagem para o jogador na classe principal do mod.

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java#datagen_advancements_trigger_criterion

Seu critério novinho em folha está pronto para ser usado! Vamos adicioná-lo ao nosso provedor:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen_advancements_custom_criteria_advancement

Execute a tarefa datagen novamente, e agora você tem sua nova conquista pronta!

## Condições com Parâmetros {#conditions-with-parameters}

Está tudo bem e tal, mas se por acaso nós quisermos garantir uma conquista somente quando algo for feito 5 vezes? E por que não outro após 10 vezes? Para isso, nós precisamos dar um parametro para a nossa condição. Você pode continuar com `UseToolCriterion`, ou você pode seguir com o novo `ParameterizedUseToolCriterion`. Na prática, você deve apenas ter o parâmetrizado, mas nós vamos manter ambos para esse tutorial.

Vamos começar de baixo para cima. Nós precisaremos verificar se os requisitos foram atendidos, então vamos editar o nosso método `Conditions#requirementsMet`:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java#datagen_advancements_new_requirements_met

`requiredTimes` não existe, então vamos transformá-lo em um parâmetro de `Conditions`:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java#datagen_advancements_new_parameter

Agora nosso codec está falhando. Vamos escrever um novo codec para as novas mudanças:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java#datagen_advancements_new_codec

Seguindo em frente, agora precisamos consertar o método `trigger`:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java#datagen_advancements_new_trigger

Se você fez um novo critério, nós precisamos adicioná-lo ao `ModCriteria`

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java#datagen_advancements_new_mod_criteria

E precisamos chamar na classe principal, onde estava a antiga:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java#datagen_advancements_trigger_new_criterion

Adicione a conquista ao seu provedor:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen_advancements_new_custom_criteria_advancement

Execute o datagen novamente e finalmente está pronto!

## Condições de Recurso {#resource-conditions}

Para aplicar uma [condição de recurso](../resource-conditions) a uma conquista gerada por dados, envolva o consumer com `withConditions` e forneça qualquer condição de recurso que você deseja aplicar. Isso gerará então uma conquista que possui condições de recurso aplicadas:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen_advancements_conditions
