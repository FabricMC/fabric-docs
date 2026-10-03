---
title: Contêineres de Blocos
description: Aprenda a como adicionar contêineres a suas entidades de blocos.
authors:
  - natri0
resources:
  https://docs.neoforged.net/docs/inventories/container/: Contêineres - Documentação do NeoForge
---

Implementar `Container` é uma boa prática ao criar blocos que podem armazenar itens, como baús e fornalhas. Isso torna possível, por exemplo, interagir com o bloco usando funis.

Nesse tutorial, nós criaremos um bloco que usa os seus contêineres para duplicar quaisquer itens colocados dentro dele.

## Criando o Bloco {#creating-the-block}

Isso deve ser familiar ao leitor caso tenha seguido os guias [Criando o Seu Primeiro Bloco](../blocks/first-block) e [Blocos de Entidade](../blocks/block-entities). Nós criaremos um `DuplicatorBlock` que estende `BaseEntityBlock` e implementa `EntityBlock`.

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/DuplicatorBlock.java#block

Então, nós precisamos criar um `DuplicatorBlockEntity`, que precisa implementar a interface `Container`. Assim como a maioria dos contêineres geralmente funcionam da mesma maneira, você pode copiar e colar essa interface auxiliar chamada `ImplementedContainer` que faz a maior parte do trabalho, nos deixando apenas com alguns métodos a implementar.

:::details Mostrar `ImplementedContainer`

<<< @/reference/latest/src/main/java/com/example/docs/container/ImplementedContainer.java

:::

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DuplicatorBlockEntity.java#be

A lista `Items` é onde os conteúdos do contêiner são armazenados. Para este bloco, definimos o tamanho do inventário de entrada como 1 slot.

Não esqueça de registrar o bloco e o bloco de entidade em suas respectivas classes!

### Salvando e Carregando {#saving-loading}

Se nós quisermos que os conteúdos persistam entre recarregamentos de jogo como um `BlockEntity` vanilla, nós precisamos salvá-lo como NBT. Felizmente, a Mojang fornece uma classe auxiliar chamada `ContainerHelper` com toda a lógica necessária.

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DuplicatorBlockEntity.java#save

## Interagindo com o Contêiner {#interacting-with-the-container}

Tecnicamente, o contêiner já está funcional. No entanto, para inserir itens, no momento precisamos usar funis. Vamos fazer com que nós possamos inserir itens no bloco clicando com o botão direito do mouse.

Para fazer isso, nós precisamos sobrescrever o método `useItemOn` no `DuplicatorBlock`:

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/DuplicatorBlock.java#useon

Aqui, se o jogador está segurando um item e há um slot vazio, nós movemos o item da mão do jogador para o contêiner do bloco e retornamos `InteractionResult.SUCCESS`.

Agora quando você clica o botão direito do mouse no bloco com um item, você não terá mais ele! Se você executar `/data get block` no bloco, você verá o item no campo `items` no NBT.

![O Duplicator block e a saída de /data get block mostrando o item no contêiner](/assets/develop/blocks/container_1.png)

### Duplicando Items {#duplicating-items}

Agora vamos fazer com que o bloco duplique a pilha de itens que você jogou nele, mas apenas dois itens por vez. E vamos fazer com que ele espere um segundo toda vez para não encher o jogador de itens!

Para fazer isso, nós adicionaremos uma função `tick` ao `DuplicatorBlockEntity`, e um campo para armazenar a quantidade de tempo que nós esperamos:

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DuplicatorBlockEntity.java#tick

Agora o `DuplicatorBlock` deve ter um método `getTicker` que retorna uma referência para o `DuplicatorBlockEntity::tick`.

<VideoPlayer src="/assets/develop/blocks/container_2.mp4">Duplicator block duplicando um tronco de carvalho</VideoPlayer>

## Worldly Containers {#worldly-containers}

Por padrão, você pode inserir e extrair itens do contêiner a partir de qualquer lado. No entanto, às vezes esse comportamento não é desejado: por exemplo, uma fornalha apenas aceita combustível a partir das laterais e itens por cima.

Para criar esse comportamento, nós precisamos implementar a interface `WorldlyContainer` no `BlockEntity`. Essa interface possui três métodos:

- `getSlotsForFace(Direction)` permite que você controle quais slots podem ser interagidos a partir de um determinado lado.
- `canPlaceItemThroughFace(int, ItemStack, Direction)` permite que você controle se um item pode ser inserido em um slot a partir de um determinado lado.
- `canTakeItemThroughFace(int, ItemStack, Direction)` permite que você controle se um item pode ser extraído a partir de um determinado lado.

Vamos modificar o `DuplicatorBlockEntity` para que apenas aceite items da parte de cima:

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DuplicatorBlockEntity.java#accept

O `getSlotsForFace` retorna um array com os _índices_ dos slots que podem ser interagidos a partir do lado determinado. Nesse caso, nós temos apenas um único slot (`0`), então nós retornamos um array com apenas aquele índice.

Da mesma forma, nós devemos modificar o método `useItemOn` para que ele realmente respeite o novo comportamento:

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/DuplicatorBlock.java#place

Agora, se nós tentarmos inserir itens a partir das laterais ao invés da parte cima, não vai funcionar!

<VideoPlayer src="/assets/develop/blocks/container_3.webm">O Duplicador ativando apenas ao interagir com a parte de cima</VideoPlayer>

## Menus {#menus}

Para acessar o novo bloco de contêiner pelo menu, assim como você faz com um baú, consulte o guia [Menus de Contêiner](./container-menus).
