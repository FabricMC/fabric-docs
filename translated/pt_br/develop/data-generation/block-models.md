---
title: Geração de Modelo de Bloco
description: Um guia para gerar modelos de bloco e estados de bloco via datagen.
authors:
  - CelDaemon
  - Fellteros
  - IMB11
  - its-miroma
  - natri0
resources:
  https://www.blockbench.net/: Download do Blockbench
  https://github.com/FabricMC/fabric/blob/1.21.11/fabric-data-generation-api-v1/src/: Testes de Exemplo do Fabric API
  https://github.com/Fellteros/vanillablocksplus: Vanilla+ Blocks por Fellteros
  https://github.com/Fellteros/vanillavsplus: Vanilla+ Verticals por Fellteros
---

<!---->

:::info PRÉ-REQUISITOS

Tenha certeza que você completou o processo de [configuração do datagen](./setup) primeiro.

:::

## Configuração {#setup}

Primeiro, nós precisaremos criar o nosso ModelProvider. Crie uma classe que estenda `FabricModelProvider`. Implemente esses dois métodos abstratos: `generateBlockStateModels` e `generateItemModels`.
Por último, crie um construtor que combine ao super.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#provider

Registre essa classe em seu `DataGeneratorEntrypoint` dentro do método `onInitializeDataGenerator`.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_models_register

## Modelo de Blocos e Estados de Bloco {#blockstates-and-block-models}

```java
@Override
public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
}
```

Para modelos de bloco, nós focaremos primeiro no método `generateBlockStateModels`. Note que o parâmetro `BlockStateModelGenerator blockStateModelGenerator` - esse objeto será responsável por gerar todos os arquivos JSON.
Aqui temos alguns exemplos úteis que você pode usar para gerar os modelos desejados:

### Cubo Inteiro Simples {#simple-cube-all}

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#cube_all

Essa é a função mais comumente usada. Ela gera um arquivo modelo JSON para um modelo de bloco normal `cube_all`. Uma textura é usada para todos os seis lados, nesse caso usamos `steel_block`.

<<< @/reference/latest/src/main/generated/assets/example-mod/models/block/steel_block.json

Ela também gera um arquivo JSON de estado de bloco. Como nós não temos propriedades do estado de bloco (ex.: Eixos, Faces, etc), uma variante é suficiente, e será usada toda vez que o bloco é colocado.

<<< @/reference/latest/src/main/generated/assets/example-mod/blockstates/steel_block.json

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/steel_block_big.png" downloadURL="/assets/develop/data-generation/block-model/steel_block.png">Textura do Bloco de Aço</DownloadEntry>

### Singletons {#singletons}

O método `registerSingleton` fornece arquivos de modelo JSON baseado no `TextureModel` fornecido e uma única variante de estado de bloco.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#cube_top_for_ends

Esse método irá gerar modelos para um cubo normal, que usa o arquivo de textura `pipe_block` para os lados e a textura `pipe_block_top` para a face superior e inferior.

<<< @/reference/latest/src/main/generated/assets/example-mod/models/block/pipe_block.json

::: tip

Se você está preso escolhendo qual `TextureModel` você deve usar, abra a classe `TextureModel` e dê uma olhada nos [`TextureMapping`s](#using-texture-map)!

:::

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/pipe_block_textures_big.png" downloadURL="/assets/develop/data-generation/block-model/pipe_block_textures.zip">Texturas do Bloco de Cano</DownloadEntry>

### Pool de Textura de Bloco {#block-texture-pool}

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#block_texture_pool_normal

O `family` é outro método útil, ele define a textura fornecendo ao "bloco base" e acrescentando "filhos", que vão ter as mesmas texturas.
Nesse caso, nós fornecemos ao `RUBY_BLOCK`, então as escadas, lajes e cercas irão usar a textura `RUBY_BLOCK`.

::: warning

Ele também gerará um [modelo JSON cubo simples](#simple-cube-all) para o "bloco base" para garantir que ele tenha um modelo de bloco.

Tenha consciência que se você está mudando o modelo desse bloco em particular, isso resultará em um erro.

:::

Você pode acrescentar um `BlockFamily`, cujo vai garantir modelos para todos seus "filhos".

<<< @/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java#family_declaration

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#block_texture_pool_family

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/ruby_block_big.png" downloadURL="/assets/develop/data-generation/block-model/ruby_block.png">Textura do Bloco de Rubi</DownloadEntry>

### Portas e Alçapões {#doors-and-trapdoors}

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#door_and_trapdoor

Portas e Alçapões são um pouco diferentes. Aqui você deve fazer três novas texturas - duas para a porta e uma para o alçapão.

1. A porta:
   - Ela tem duas partes - a parte de cima e a de baixo. **Cada uma precisa da própria texura:** nesse caso `ruby_door_top` para a parte de cima e `ruby_door_bottom` para a parte de baixo.
   - O método `registerDoor()` irá criar modelos para as orientações da porta, tanto aberta quando fechada.
   - **Você também vai precisar de uma textura de item!** Coloque-a na pasta `assets/mod-id/textures/item/`.
2. O alçapão:
   - Aqui, você precisa de uma única textura, nesse caso `ruby_trapdoor`. Ela será usada em todos os lados.
   - Como a `TrapdoorBlock` possui uma propriedade `FACING`, você pode usar esse método para gerar um arquivo modelo com texturas rotacionadas = o alçapão será "orientável." Caso contrário, ele irá parecer o mesmo independente da direção.

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/ruby_door_trapdoor_big.png" downloadURL="/assets/develop/data-generation/block-model/ruby_door_trapdoor_textures.zip">Porta e Alçapão de Rubi</DownloadEntry>

## Modelos Personalizados de Bloco {#custom-block-models}

Nessa seção, nós iremos criar modelos para uma Laje de Tronco de Carvalho Vertical, com as texturas do Tronco de Carvalho.

Todos os campos e texturas para essa parte do tutorial estão declarados em uma classe interna estática chamada `CustomBlockStateModelGenerator`.

:::details Mostrar `CustomBlockStateModelGenerator`

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#custom_blockstate_model_generator

:::

### Classe de Bloco Personalizado {#custom-block-class}

Crie um bloco `VerticalSlab` com a propriedade `FACING` e a propriedade booliana `SINGLE`, como no tutorial de [Estados de Bloco](../blocks/blockstates). `SINGLE` indicará se as duas lajes estão presentes.
Então você deve sobrescrever `getShape` e `getCollisionShape` para que o contorno renderize corretamente e que o bloco tenha o formato correto de colisão.

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java#custom_voxels

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java#custom_collision

Sobrescreva igualmente o método `canBeReplaced()`, caso contrário você não poderá fazer um bloco inteiro com as lajes.

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java#custom_replace

E está pronto! Você pode testar o bloco e colocá-lo dentro do jogo.

### Modelo de Bloco Parente {#parent-block-model}

Agora, vamos criar um modelo de bloco parente. Ele irá determinar o tamanho, a posição na mão ou em outros slots e as coordenadas `x` e `y` da textura.
É recomendado usar um editor como [Blockbench](https://www.blockbench.net/) para isso, pois criá-lo manualmente é um processo realmente tedioso. Ele deve se parecer com isso:

<<< @/reference/latest/src/main/resources/assets/example-mod/models/block/vertical_slab.json

Veja [como estados de bloco são formatados](https://pt.minecraft.wiki/w/Defini%C3%A7%C3%A3o_de_estados_de_bloco/formato) para mais informações.
Note as palavras-chave `#bottom`, `#top`, `#side`. Elas agem como variáveis que podem ser escolhidas por modelos que têm esse como parente:

```json
{
  "parent": "minecraft:block/cube_bottom_top",
  "textures": {
    "bottom": "minecraft:block/sandstone_bottom",
    "side": "minecraft:block/sandstone",
    "top": "minecraft:block/sandstone_top"
  }
}
```

O valor `bottom` irá substituir o espaço reservado `#bottom` e assim por diante. **Coloque-o na pasta `resources/assets/mod_id/models/block/`.**

### Modelo Personalizado {#custom-model}

Outra coisa que precisará de uma instância é a classe `Model`. Ela representará o verdadeiro [modelo de bloco parente](#parent-block-model) dentro do nosso mod.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#custom_model

O método `block()` cria um novo `Model`, apontando para o arquivo `vertical_slab.json` dentro da pasta `resources/assets/mod_id/models/block/`.
As `TextureSlot`s representam os "espaços reservados" (`#bottom`, `#top`, ...) como um Objeto.

### Usando Mapa de Textura {#using-texture-map}

O que `TextureMap` faz? Ele, na verdade, fornece aos identificadores que apontam para as texturas. Ele tecnicamente se comporta como um mapa normal - você associa uma `TextureSlot` (chave) com um `Identifier` (valor).

Você pode tanto usar os mapas vanillas, como `TextureMapping.cube()` (que associa todas as `TextureSlot`s com o mesmo `Identifier`), ou criar uma nova, criando uma instância e usando `.put()` para associar as chaves com os valores.

::: tip

`TextureMapping.cube()` associa todas as `TextureSlot`s com o mesmo `Identifier`, não importa quantas delas existam!

:::

Nós queremos usar as texturas do Tronco de Carvalho, porém temos as `TextureSlot`s \`BOTTOM`, `TOP`e`SIDE\`\`. Para isso, precisamos criar chaves novas.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#custom_texture_map

As faces `bottom` e `top` vão usar `oak_log_top.png`, os lados irão usar `oak_log.png`.

::: warning

Todas as `TextureSlot`s no TextureMapping **devem** combinar com todas as `TextureSlot`s no seu modelo de bloco parente!

:::

### Método `BlockModelDefinitionGenerator` Personalizado {#custom-supplier-method}

O `BlockModelDefinitionGenerator` contém todas as variantes dos estados de bloco, as suas rotações e outras opções como o UV lock.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#custom_supplier

Primeiramente, nós criaremos um novo `BlockModelDefinitionGenerator` usando o `MultiVariantGenerator.dispatch()`.
Então nós criamos um novo `PropertyDispatch` que contém os parâmetros para todas as variantes do bloco, nesse caso `FACING` e `SINGLE`, e que os passa para o `MultiVariantGenerator`.
Especifique qual modelo e qual transformação (UV lock, rotação) são usados ao utilizar `.register()`.
Por exemplo:

- Linha 9: _single_ slab voltada para o norte, então nós usaremos o modelo sem rotação
- Linha 9: _single_ slab voltada para o oeste, então nós vamos rotacionar o modelo no eixo Y a 270°
- Linhas 10-13: _non-single_ slab, que parece um bloco inteiro e que não precisamos rotacionar

### Método Datagen Personalizado {#custom-datagen-method}

O último passo - criar um método que de fato vai chamar e gerar os JSONs.
Mas para que servem os parâmetros?

1. `BlockModelGenerators generator` o mesmo que foi passado para o `generateBlockStateModels`.
2. `Block vertSlabBlock` é o bloco para qual vamos gerar os JSONs.
3. `Block fullBlock` - é o modelo usado quando a propriedade `SINGLE` é falsa = a laje se parece com um bloco inteiro.
4. `TextureMapping textures` define as verdadeiras texturas que o modelo usa. Veja o capítulo [Usando Mapa de Textura](#using-texture-map).

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#custom_gen

Primeiro, nós pegamos o `Identifier` do modelo de laje única usando `VERTICAL_SLAB.create()`. Então, nós pegamos o `Identifier` do bloco inteiro usando `ModelLocationUtils.getModelLocation()`.

Nós então passamos esses dois modelos para o `createVerticalSlabBlockStates`, que ele mesmo é passado para o consumer `blockStateOutput`, que gera os arquivos JSON para os modelos.

Finalmente, nós criamos um modelo para o item de laje vertical usando `BlockModelGenerators.registerSimpleItemModel()`.

Isso é tudo! Agora tudo que nos resta a fazer é chamar o nosso método no nosso `ModelProvider`:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#custom_method_call
