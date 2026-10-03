---
title: Estados do Bloco
description: Saiba o porque os estados dos blocos são uma boa maneira de adicionar funcionalidade visual para seus blocos.
authors:
  - IMB11
resources:
  https://minecraft.wiki/w/Tutorial:Models#Block_states: Estados de Blocos - Minecraft Wiki
  https://docs.neoforged.net/docs/blocks/states/: Estados de Bloco - Documentação do NeoForge
---

Um estado do bloco é um pedaço de dado anexado a um bloco singular no mundo do Minecraft contendo a informação na forma de propriedades — alguns exemplos de propriedades vanilla guardado nos estados:

- Rotação: Usado principalmente em troncos ou outros blocos naturais.
- Ativado: Fortemente usado em componentes de redstone, e em blocos como a fornalha ou o defumador.
- Idade (Age): usado em plantações, plantas, mudas, algas etc.

Você provavelmente pode ver porque eles são úteis — eles evitam que você armazene dados NBT em um bloco-entidade — reduzindo o tamanho do mundo e prevenindo problemas de TPS!

As definições do estado do bloco são encontrados na pasta `assets/example-mod/blockstates`.

## Exemplo: Pilar {#pillar-block}

<!-- Note: This example could be used for a custom recipe types guide, a condensor machine block with a custom "Condensing" recipe? -->

Minecraft já tem algumas classes personalizadas que permitem você criar certos tipos de blocos — este exemplo vai pela criação do bloco com a propriedade `axis` por criar um bloco como o "Tronco de carvalho condensado".

A classe vanilla `RotatedPillarBlock` permite que o bloco seja colocado no eixo X, Y ou Z.

<<< @/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java#condensed_oak_log

Blocos de pilar tem duas texturas, topo e lado — eles usam o modelo `block/cube_column`.

Como sempre, com toda textura de bloco, o arquivo da textura pode ser encontrado em `assets/example-mod/textures/block`

<DownloadEntry visualURL="/assets/develop/blocks/blockstates_0_large.png" downloadURL="/assets/develop/blocks/condensed_oak_log_textures.zip">Texturas</DownloadEntry>

Como o bloco de pilar tem duas posições, horizontal e vertical, devemos fazer dois arquivos de modelos separados:

- `condensed_oak_log_horizontal.json` que estende o modelo `block/cube_column_horizontal`.
- `condensed_oak_log.json` que estende o modelo `block/cube_column`.

Um exemplo do arquivo `condensed_oak_log_horizontal.json`:

<<< @/reference/latest/src/main/generated/assets/example-mod/models/block/condensed_oak_log_horizontal.json

::: info

Lembre-se, os arquivos do estado do bloco podem ser encontrados na pasta `assets/example-mod/blockstates`, o nome do arquivo deve corresponder com o ID do bloco usado ao registrar o seu bloco na classe `ModBlocks`. Por exemplo, se o ID do bloco é `condensed_oak_log`, o arquivo deve ser nomeado `condensed_oak_log.json`.

Para uma busca mais profunda nos modificadores disponíveis nos arquivos de estados do bloco, veja a página [Minecraft Wiki - Modelos (Estado do Bloco)](https://minecraft.wiki/w/Tutorial:Models#Block_states).

:::

A seguir, precisamos criar um Estado de Bloco, aonde a mágica acontece. Pilares tem três eixos, então nós utilizamos modelos específicos para as seguintes situações:

- `axis=x` - quando o bloco é colocado junto ao axis X, vamos rotacionar o modelo para virar a direção X positiva.
- `axis=y` - Quando o bloco é colocado junto ao axis Y, usaremos o modelo vertical normal.
- `axis=z` - Quando o bloco é colocado junto ao eixo Z, nós vamos rotacionar o modelo na direção Z positiva.

<<< @/reference/latest/src/main/generated/assets/example-mod/blockstates/condensed_oak_log.json

Como sempre, você deverá criar uma tradução para seu bloco, o modelo de um item que os parentes são qualquer um dos dois modelos.

![Exemplo de um bloco de pilar dentro do jogo](/assets/develop/blocks/blockstates_1.png)

## Estados do bloco personalizado {#custom-block-states}

Customizar Estado de Bloco é bom se seu bloco tem propriedades únicas — às vezes você pode encontrar um modo do bloco reutilizar propriedades do vanilla.

Esse exemplo criará uma única propriedade booliana chamada `activated` quando um jogador clica com o botão direito no bloco, ele irá de `activated=false` para `activated=ture` — mudando sua estrutura de acordo.

### Criando a Propriedade {#creating-the-property}

Primeiramente, você precisará criar a propriedade em si - já que isso é um valor booliano, nós usaremos o método `BooleanProperty.create`.

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java#block

Em seguida, nós precisamos acrescentar a propriedade ao gerenciador do estado do bloco no método `createBlockStateDefinition`. Você precisará substituir o método para acessar a construção:

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java#block_state_definition

Você também terá que escolher um estado padrão para a propriedade `activated` na construção do seu bloco personalizado.

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java#constructor

### Usando a Propriedade {#using-the-property}

Esse exemplo inverte a propriedade booliana `activated` quando o jogador interage com o bloco. Nós não podemos sobrescrever o método `useWithoutItem` para isso:

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java#on_use

### Visualizando a Propriedade {#visualizing-the-property}

Antes de criar o arquivo de Estado de Bloco, você precisará prover textura para ambos estados do bloco ativo e desativo, também como o modelo do bloco.

<DownloadEntry visualURL="/assets/develop/blocks/blockstates_2_large.png" downloadURL="/assets/develop/blocks/prismarine_lamp_textures.zip">Texturas</DownloadEntry>

Usando seu conhecimento de modelos de blocos para criar dois modelos para o bloco: um quando estiver estado ativado e outro para o estado desativado. Uma vez feito, você pode começar criando o arquivo de Estado de Bloco.

Desde que você criou uma propriedade, você irá precisar atualizar o arquivo de Estado de Bloco para o bloco contar com aquela propriedade.

Se você tem múltiplas propriedades no bloco, você precisará contar com todas as possíveis combinações. Por exemplo, `activated` e `axis` levaria a 6 possíveis combinações (dois possíveis valores para `activated` e três possíveis valores para `axis`).

Desde que esse bloco tenha duas diferentes variantes, só é possível uma propriedade (`activated`), o Estado de Bloco JSON irá parecer algo como:

<<< @/reference/latest/src/main/generated/assets/example-mod/blockstates/prismarine_lamp.json

::: tip

Não esqueça de adicionar o [Item do Cliente](../items/first-item#creating-the-client-item) ao bloco para ele ser mostrado no inventário!

:::

Desde que o bloco de exemplo é uma lâmpada, nós também precisamos fazer que ele emita luz quando a propriedade `activated` é verdadeira. Isso pode ser feito através das configurações de bloco passadas a construção quando registramos o bloco.

Você pode usar o método `lightLevel` para definir o nível de luz emitido pelo bloco, nós podemos criar um método estático na classe `PrismarineLampBlock` para retornar o nível de luz baseado na propriedade `activated`, e passar ele como uma referência para o método `lightLevel`:

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/PrismarineLampBlock.java#get_luminance

<<< @/reference/latest/src/main/java/com/example/docs/block/ModBlockItemIds.java#prismarine_lamp

<<< @/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java#prismarine_lamp

<!-- Note: This block can be a great starter for a redstone block interactivity page, maybe triggering the blockstate based on redstone input? -->

Assim que você completou tudo, o resultado final deve se parecer algo como:

<VideoPlayer src="/assets/develop/blocks/blockstates_3.webm">Bloco de Lanterna do Mar em jogo</VideoPlayer>
