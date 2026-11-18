---
title: Rendererizador do Bloco-Entidade
description: Aprendendo como apimentar a renderização com renderizador do bloco-entidade.
authors:
  - natri0
resources:
  https://docs.neoforged.net/docs/blockentities/ber/: BlockEntityRenderer - Documentação do NeoForge
---

Às vezes, usar o modelo do Minecraft não é o suficiente. Se você precisa adicionar renderização dinâmica ao visual do seu bloco, você precisa usar `BlockEntityRenderer`.

Por exemplo, vamos fazer o Bloco Contador do artigo [Bloco-Entidade](../blocks/block-entities) mostrar o número de ativações na sua face superior.

## Criando a BlockEntityRender {#creating-a-blockentityrenderer}

A renderização de bloco-entidade usa um sistema de envio/renderização onde você primeiramente envia os dados necessários para renderizar um objeto na tela, o jogo então renderiza os objetos usando o seu estado enviado.

Quando criando a `BlockEntityRenderer` para o `CounterBlockEntity`, é importante colocar a classe no Source Set apropriado, como `src/client/` se seu projeto usa sources separados para client e servidor. Acessar classes relacionadas a renderização diretamente no source set `src/main/` não é seguro porque essas classes podem não estar carregadas em um servidor.

Primeiro, nós precisamos criar um `BlockEntityRenderState` para que o nosso `CounterBlockEntity` registre os dados que serão usados para a renderização. Nesse caso, nós precisaremos que os `clicks` estejam disponíveis durante a renderização.

<<< @/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderState.java#render_state

Então nós criamos um `BlockEntityRenderer` para o nosso `CounterBlockEntity`.

<<< @/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java#renderer_structure

A nossa classe tem um construtor com `BlockEntityRendererProvider.Context` como um parâmetro. O `Context` têm algumas utilidades de renderização úteis, como o `ItemRenderer` ou `Font`.
Também, ao incluir o construtor dessa forma, torna-se possível o uso do construtor como a própria interface funcional `BlockEntityRendererProvider`:

<<< @/reference/latest/src/client/java/com/example/docs/ExampleModBlockEntityRenderer.java#register_block_entity_renderer

Nós vamos sobrescrever alguns métodos para configurar o estado de renderização com o método `submit` onde a lógica de renderização será configurada.

`createRenderState` pode ser usado para inicializar o estado de renderização.

<<< @/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java#create_render_state

`extractRenderState` pode ser usado para atualizar o estado de renderização com os dados da entidade.

<<< @/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java#extract_render_state

Você deve registrar a renderização bloco-entidade na sua classe `ClientModInitializer`.

`BlockEntityRenderers` é um registro que mapeia cada `BlockEntityType` com um código de renderização customizado para o seu respectivo `BlockEntityRenderer`.

## Desenhando nos Blocos {#drawing-on-blocks}

Agora que temos uma renderização, nós podemos desenhar. O método `submit` é chamado a cada frame, e é aqui que a mágica da renderização acontece.

### Movimentando {#moving-around}

Primeiro, nós precisamos deslocar e rodar o texto para que esteja no topo do bloco.

::: info

Como o nome sugere, o `PoseStack` é uma _pilha_, que significa que você pode empilhar ou desempilhar transformações.
Uma boa regra geral é empilhar uma nova no começo do método `submit` e desempilhar ela no final, assim a renderização de um bloco não afetará os demais.

Mais informações sobre o `PoseStack` pode ser encontrado no [artigo de Conceitos de Renderização Básicos](../rendering/basic-concepts).

:::

Para fazer as translações e rotações mais fáceis de entender, vamos visualizá-las. Nessa imagem, o bloco verde está onde o texto seria renderizado, por padrão no canto mais distante à esquerda do bloco:

![Posição de renderização padrão](/assets/develop/blocks/block_entity_renderer_1.png)

Primeiro nós precisamos mover o texto até o meio do bloco nos eixos X e Z, e então movê-lo para o topo do bloco no eixo Y:

![Bloco verde no ponto central mais alto](/assets/develop/blocks/block_entity_renderer_2.png)

Isso é feito com uma única chamada `translate`:

<<< @/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java#translate

A _translação_ está concluída, restam _rotação_ e _escala_.

Por padrão, o texto é renderizado no plano XY, então nós precisamos rotacioná-lo 90 graus ao redor do eixo X para deixar a face virada para cima no plano ZX:

![Bloco verde no ponto central mais alto, face para cima](/assets/develop/blocks/block_entity_renderer_3.png)

O `PoseStack` não possui uma função `rotate`, ao invés disso, nós precisamos usar `mulPose` e `Axis.XP`:

<<< @/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java#rotate

Agora o texto está na posição correta, mas é muito grande. O `BlockEntityRenderer` mapeia o bloco inteiro para um cubo `[-0.5, 0.5]`, enquanto um `Font` usa as coordenadas Y de `[0, 9]`. Como tal, nós precisamos diminuí-lo por um fator de 18:

<<< @/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java#scale

Agora, a transformação toda se parece com isso:

<<< @/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java#transformations

### Renderizando Texto {#drawing-text}

Como mencionado anteriormente, o `Context` fornecido para o construtor da nossa renderização possui a fonte que nós podemos usar para medir o texto (`width`), o que é útil para centralização.

Para renderizar o texto, nós vamos fornecer os dados necessários para a fila de renderização. Já que estamos renderizando alguns textos, nós podemos usar o método `submitText` provido pela instância `SubmitNodeCollector` fornecido para o método `submit`.

<<< @/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java#drawing_text

O método `submitText` recebe vários parâmetros, mas o mais importante deles são:

- o `FormattedCharSequence` para renderizar;
- as suas coordenadas `x` e `y`;
- o valor RGB de `color`;
- o `PoseStack` descrevendo como ele deve ser transformado.

E após tudo isso, aqui está o resultado:

![Bloco Contador com um número no topo](/assets/develop/blocks/block_entity_renderer_4.png)
