---
title: Menus de Contêineres
description: Um guia explicando como criar um simples menu para um bloco de contêiner.
authors:
  - bluebear94
  - cassiancc
  - ChampionAsh5357
  - CelDaemon
  - Tenneb22
resources:
  https://docs.neoforged.net/docs/inventories/menus: Menus - Documentação do NeoForge
---

<!---->

:::info PRÉ-REQUISITOS

Você deve primeiro ler [Contêineres de Blocos](./block-containers) se familiarizar com a criação de um contêiner de bloco entidade.

:::

Ao abrir um contêiner, como um baú, duas coisas importantes são necessárias para exibir o seu conteúdo:

- uma `Screen` que cuida da renderização dos conteúdos e do plano de fundo na exibição.
- um `Menu` que cuida na lógica de clicar com o Shift pressionado e a sincronização entre o servidor e o cliente.

Nesse guia, nós vamos criar um baú de terra com um contêiner 3x3 que pode ser acessado ao clicar com o botão direito, abrindo uma tela.

## Criando o Bloco {#creating-the-block}

Primeiro, nós precisamos criar um bloco e um bloco entidade/; leia mais sobre no guia [Contêineres de Blocos](./block-containers#creating-the-block).

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/DirtChestBlock.java#block

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DirtChestBlockEntity.java#be

Em adição aos métodos de bloco entidade normais, nós precisamos sobrescrever o método `stillValid`. Esse método será chamado a cada tick para verificar se o jogador deve ser forçado a sair do menu.
Nós vamos usar a implementação padrão desse método do `ContainerHelper`, que verifica se o nosso bloco entidade ainda existe e se o jogador está dentro do alcance de interação.

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DirtChestBlockEntity.java#container_still_valid

Após implementar o nosso menu, ele se fechará automaticamente quando o jogador é empurrado para longe.

<VideoPlayer src="/assets/develop/blocks/menu_still_valid.webm">O menu do contêiner se fecha quando o jogador sai do alcance</VideoPlayer>

### Abrindo o Menu {#opening-the-screen}

Nós queremos conseguir abrir o menu de alguma maneira, então vamos cuidar disso com o método `useWithoutItem`:

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/DirtChestBlock.java#use

### Implementando o MenuProvider {#implementing-menuprovider}

Para adicionar a funcionalidade de menu, precisamos agora implementar o `MenuProvider` no bloco entidade:

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DirtChestBlockEntity.java#menu

O método `getDisplayName` retorna o nome do bloco, que será exibido no topo da tela.

## Criando o Menu {#creating-the-menu}

O `createMenu` quer que nós retornamos um menu, mas nós ainda não criamos um para o nosso bloco. Para fazer isso, nós criaremos uma classe `DirtChestMenu` que estende `AbstractContainerMenu`:

<<< @/reference/latest/src/main/java/com/example/docs/menu/custom/DirtChestMenu.java#menu

O construtor do lado do cliente será chamado ao cliente quando o servidor querer abrir um menu. Ele cria um contêiner vazio que sincroniza então automaticamente com o verdadeiro contêiner no servidor.

O construtor do lado do cliente é chamado no servidor, e por ele saber o conteúdo do contêiner, ele pode o passar diretamente como um argumento.

`quickMovesStack` lida com os itens movidos ao clicar com o Shift pressionado dentro do menu. Esse exemplo replica o comportamento dos menus vanilla, como os baús e ejetores.

Então precisamos registrar o menu em uma nova classe `ModMenuTypes`:

<<< @/reference/latest/src/main/java/com/example/docs/menu/ModMenuTypes.java#register_menu

Nós podemos agora definir o valor do `createMenu` no bloco entidade para usar o nosso menu:

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DirtChestBlockEntity.java#provider_implemented

::: info

O método `createMenu` é chamado apenas no servidor, assim nós chamamos o construtor do lado do servidor e passamos `this` (o bloco entidade) como um parâmetro de contêiner.

:::

## Criando a Tela {#creating-the-screen}

Para realmente exibir o conteúdo do contêiner no lado do cliente, nós também precisamos criar uma tela para o nosso menu.
Nós criaremos uma nova classe que estende`AbstractContainerScreen`:

<<< @/reference/latest/src/client/java/com/example/docs/rendering/screens/inventory/DirtChestScreen.java#screen

Para o plano de fundo dessa tela, nós vamos apenas usar a textura padrão do Ejetor, pois o nosso baú de terra usa o mesmo layout de slots. Você pode alternativamente fornecer a sua própria textura para `CONTAINER_TEXTURE`.

Por isso ser uma tela para um menu, nós também precisamos registrá-lo no cliente com o método `MenuScreens#register()`:

<<< @/reference/latest/src/client/java/com/example/docs/ExampleModScreens.java#register_screens

Ao carregar o seu jogo, você agora deve possuir um bloco de terra que pode ser aberto clicando com o botão direito para abrir um menu e armazenar itens dentro.

![Menu do Baú de Terra dentro do jogo](/assets/develop/blocks/container_menus_0.png)
