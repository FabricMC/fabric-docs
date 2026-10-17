---
title: Criando seu Primeiro Bloco
description: Aprenda a criar seu primeiro bloco personalizado no Minecraft.
authors:
  - bluebear94
  - cassiancc
  - CelDaemon
  - Earthcomputer
  - IMB11
  - its-miroma
  - xEobardThawne
  - NotNightSky
resources:
  https://docs.neoforged.net/docs/blocks/: Blocos - Documentação do NeoForge
---

Blocos são os blocos de construção do Minecraft (sem intenção de piada) - assim como tudo no Minecraft, eles são armazenados nos registros.

## Preparando as Classes de ID do seu Bloco {#preparing-your-block-id-classes}

Se você completou a página [Criando o Seu Primeiro Item](../items/first-item), esse processo vai parecer extremamente familiar, você precisará criar classes para armazenar os identificadores dos seus `Block`s. Os IDs para blocos que possuem itens são armazenados como `BlockItemId`s, enquanto aqueles não possuem itens são armazenados como `ResouceKey`.

Essas referências para o bloco são usadas para [tags de blocos geradoras de dados](../data-generation/tags).

:::: tabs

== Blocos Que Possuem Itens

Nós colocaremos um método em uma classe chamada `ModBlockItemIds` (ou qualquer seja o nome escolhido para a classe). Essa classe contém quaisquer blocos que possuem itens.

<<< @/reference/latest/src/main/java/com/example/docs/block/ModBlockItemIds.java#first_block

::: tip

A Mojang também faz isso com os seus blocos! Dê uma olhada na classe `BlockItemIds` para se inspirar.

:::

== Blocos Que Não Possuem Itens

Nós colocaremos um método que cria um `ResourceKey` em uma classe chamada `ModBlockIds` (ou qualquer seja o nome escolhido para a classe). Essa classe contém quaisquer blocos que não possuem itens.

<<< @/reference/latest/src/main/java/com/example/docs/block/ModBlockIds.java#first_block

::: tip

A Mojang também faz isso com os seus blocos! Dê uma olhada na classe `BlockIds` para se inspirar.

:::

::::

## Preparando a Classe do seu Bloco {#preparing-your-blocks-class}

O registro de blocos também é semelhante ao [registro de itens](../items/first-item#registering-an-item): agora nós vamos criar duas sobrecargas de um método `register()` que registra os seus blocos, um para os blocos que possuem itens e outro para os que não possuem.

Você deve colocar esses métodos na classe chamada `ModBlocks` (ou qualquer seja o nome escolhido).

A Mojang faz algo extremamente similar com os blocos vanilla; você pode se referir a classe `Blocks` para ver como eles fazem.

::: warning

Ambas as sobrecargas do`register()` mostradas abaixo são **requeridas** na sua classe `ModBlocks`. A sobrecarga de blocos que possuem itens chama internamente a sobrecarga de blocos que não possuem itens. Se uma das sobrecargas estiverem faltando, o seu código não compilará.

:::

::: tabs

== Blocos Que Possuem Itens

Blocos que possuem itens usam um parametro `BlockItemIds` que contém o ID tanto para o bloco quanto para o seu item.

<<< @/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java#first_block_item

== Blocos Que Não Possuem Itens

Blocos que não possuem itens usam um parametro `ResourceKey` que contém o ID para o bloco.

<<< @/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java#first_block

:::

Assim como itens, você precisa garantir que a classe está carregada, para que os campos de estatística contendo as instâncias do seu bloco inicie.

Você pode fazer isso criando um método `initialize` vazio, que pode ser chamado no [iniciador do seu mod](../getting-started/project-structure#entrypoints) para disparar a inicialização estática.

::: info

Caso você não estiver ciente sobre o que é uma inicialização estática, ela é o processo de inicializar campos estáticos em uma classe. Isso é feito quando a classe é carregada pelo JVM, sendo feito antes da criação de instâncias da classe.

:::

<<< @/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java#static_initialization

<<< @/reference/latest/src/main/java/com/example/docs/block/ExampleModBlocks.java#initialize

## Criando e Registrando o Seu Bloco {#creating-and-registering-your-block}

De forma similar a itens, blocos levam a classe `BlockBehaviour.Properties` em seus construtores, que especifica as propriedades do bloco, como o seu efeito sonoro e nível de mineração.

Nós não iremos abordar todas as opções aqui: você mesmo pode visualizar a classe para ver as várias opções, que deve ser auto-explicativo.

Para via de exemplo, nós iremos criar um simples bloco com propriedades de terra, mas sendo um material diferente.

- Nós criamos a configuração do nosso bloco de um modo semelhante à maneira em que criamos as configurações de itens no tutorial de itens.
- Nós diremos ao método `register` para criar uma instância `Block` através das configurações do bloco chamando o construtor `Block`.

::: tip

Você também pode usar `BlockBehaviour.Properties.ofFullCopy(BlockBehaviour block)` para copiar as configurações de um bloco existente, neste caso, nós poderíamos ter usado `Blocks.DIRT` para copiar as configurações da terra, mas para questões de exemplo nós usaremos o construtor.

:::

<<< @/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java#condensed_dirt

### Adicionando o Item do Seu Bloco a uma Aba do Modo Criativo {#adding-your-block-s-item-to-a-creative-tab}

Como o `BlockItem` é automaticamente criado e registrado, para adicioná-lo em uma aba do modo criativo, você deve usar o método `Block.asItem()` para conseguir a instância `BlockItem`.

Para esse exemplo, nós vamos adicionar o bloco na aba `BUILDING_BLOCKS`. Para adicionar o bloco em uma aba do modo criativo customizada, veja [Aba do Modo Criativo Customizada](../items/custom-creative-tabs).

<<< @/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java#add_to_creative_tab

Você deve colocar isso dentro da função `initialize()` da sua classe.

Agora você deve notar que seu bloco está no inventário do modo criativo e pode ser colocado no mundo!

![Bloco no mundo sem modelo ou textura adequada](/assets/develop/blocks/first_block_0.png)

Há alguns problemas no entanto, o item do bloco não está nomeado, e o bloco não possui textura, modelo de bloco ou modelo de item.

## Adicionando Traduções de Bloco {#adding-block-translations}

Para adicionar uma tradução, você deve criar uma chave de tradução no seu arquivo de tradução - `assets/example-mod/lang/en_us.json`.

O Minecraft irá usar essa tradução no inventário do modo criativo e em outros lugares onde o nome do bloco é exibido, assim como um feedback de comando.

```json
{
  "block.example-mod.condensed_dirt": "Condensed Dirt"
}
```

Você pode tanto reiniciar o jogo ou construir o seu mod e apertar <kbd>F3</kbd>+<kbd>T</kbd> para aplicar as mudanças - e você deve ver que o bloco tem um nome no inventário do modo criativo e nos outros lugares como na tela de estatísticas.

## Modelos e Texturas {#models-and-textures}

Todas texturas de blocos podem ser encontradas na pasta `assets/example-mod/textures/block` - uma textura de exemplo para o bloco "Condensed Dirt" é grátis para uso.

<DownloadEntry visualURL="/assets/develop/blocks/first_block_1.png" downloadURL="/assets/develop/blocks/first_block_1_small.png">Textura</DownloadEntry>

Para fazer a textura aparecer no jogo, você deve criar um modelo de bloco que pode ser encontrado no arquivo `assets/example-mod/models/block/condensed_dirt.json` para o bloco "Condensed Dirt". Para esse bloco, nós usaremos o tipo de modelo `block/cube_all`.

<<< @/reference/latest/src/main/generated/assets/example-mod/models/block/condensed_dirt.json

Para que o bloco apareça no seu inventário, você precisará criar um [Item do Cliente](../items/first-item#creating-the-client-item) que aponta para o seu modelo de bloco. Para esse exemplo, o item do cliente para o bloco "Condensed Dirt" pode ser encontrado em `assets/example-mod/items/condensed_dirt.json`.

<<< @/reference/latest/src/main/generated/assets/example-mod/items/condensed_dirt.json

::: tip

Você apenas precisa criar um item do cliente se você registrou um `BlockItem` junto ao seu bloco!

:::

Quando você carregar o jogo, você pode notar que a textura ainda está faltando. Isso é porque você precisa adicionar uma definição de estado do bloco.

## Criando a Definição de Estado de Bloco {#creating-the-block-state-definition}

A definição de estado do bloco é usada para instruir o jogo sobre qual modelo renderizar baseado no estado atual do bloco.

Para o bloco exemplo, que não tem um estado de bloco complexo, apenas precisa de uma entrada na definição.

Esse arquivo deve ser encontrado na pasta `assets/example-mod/blockstates`, e seu nome deve combinar com o ID do bloco usado ao registrar o seu bloco na classe `ModBlocks`. Por exemplo, se o ID do bloco é `condensed_dirt`, o arquivo deve ser nomeado `condensed_dirt.json`.

<<< @/reference/latest/src/main/generated/assets/example-mod/blockstates/condensed_dirt.json

::: tip

Estados de bloco são incrivelmente complexos, por isso eles serão abordados a seguir na sua [própria página](./blockstates).

:::

Reiniciando o jogo, ou recarregando com <kbd>F3</kbd>+<kbd>T</kbd> para aplicar as mudanças - você deve conseguir ver a textura do bloco no inventário e fisicamente no mundo:

![Bloco no mundo com modelo ou textura adequada](/assets/develop/blocks/first_block_4.png)

## Adicionando Drops de Bloco {#adding-block-drops}

Ao quebrar um bloco no modo sobrevivência, você verá que o bloco não dropa - você pode querer essa funcionalidade, então, para fazer o seu bloco dropar como um item ao quebrar, você deve implementar a sua tabela de item - o arquivo da tabela de item deve ser colocado na pasta `data/example-mod/loot_table/blocks/`.

::: info

Para um melhor entendimento das tabelas de itens, você pode verificar a página [Minecraft Wiki - Tabelas de Itens](https://minecraft.wiki/w/Loot_table).

:::

<<< @/reference/latest/src/main/resources/data/example-mod/loot_tables/blocks/condensed_dirt.json

Essa tabela de item fornece um único drop para o item do bloco quando o bloco é quebrado e quando ele é explodido.

## Recomendando uma Ferramenta de Coleta {#recommending-a-harvesting-tool}

Você também pode querer que o seu bloco seja coletado por uma ferramenta específica - por exemplo, você pode querer que o bloco seja coletado mais rapidamente com uma pá.

Todas as tags de ferramenta devem ser colocadas na pasta `data/minecraft/tags/block/mineable/` - onde o nome do arquivo depende do tipo de ferramenta usada, uma das seguintes opções:

- `hoe.json` (Enxada)
- `axe.json` (Machado)
- `pickaxe.json` (Picareta)
- `shovel.json` (Pá)

Os conteúdos do arquivo são bem simples - é uma lista de itens que devem ser adicionados a tag.

Esse exemplo adiciona o bloco "Condensed Dirt" na tag `shovel`.

<<< @/reference/latest/src/main/resources/data/minecraft/tags/mineable/shovel.json

Se você deseja uma ferramenta como requisito para minerar o bloco, você precisará anexar `.requiresCorrectToolForDrops()` as configurações do seu bloco, assim como adicionar a tag de nível apropriado de mineração.

## Níveis de Mineração {#mining-levels}

De modo semelhante, a tag do nível de mineração pode ser encontrada na pasta `data/minecraft/tags/block/`, e segue o seguinte formato:

- `needs_stone_tool.json` - O nível mínimo para ferramentas de pedra
- `needs_iron_tool.json` - O nível mínimo para ferramentas de ferro
- `needs_diamond_tool.json` - O nível mínimo para ferramentas de diamante.

O arquivo tem o mesmo formato do arquivo de ferramenta de coleta - uma lista de itens para serem adicionados a tag.

## Notas Extras {#extra-notes}

Se você está adicionando múltiplos blocos ao seu mod, você pode considerar usar [Geração de Dados](../data-generation/setup) para automatizar o processo de criar blocos e modelos de itens, definições de estado de bloco e tabelas de item.
