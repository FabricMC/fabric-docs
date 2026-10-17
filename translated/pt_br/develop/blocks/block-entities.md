---
title: Bloco-Entidades
description: Aprenda a criar bloco-entidades para seus blocos personalizados.
authors:
  - CelDaemon
  - natri0
resources:
  https://docs.neoforged.net/docs/blockentities/: Entidades de Bloco - Documentação do NeoForge
---

Bloco-entidades são um jeito de armazenar dados adicionais para um bloco, isso não sendo parte do Bloco de Estado: inventário de conteúdo, nome personalizado etc.
Minecraft utiliza bloco-entidades para blocos como baús, fornalhas e blocos de comando.

Como exemplo, nós iremos criar um bloco que conta quantas vezes ele foi ativo.

## Criando o Bloco-Entidade {#creating-the-block-entity}

Para fazer o Minecraft reconhecer e carregar o novo Bloco-entidade nós iremos criar um tipo de bloco-entidade. Isso é feito com extensão de classe `BlockEntity` e registrando como uma nova classe `ModBlockEntities`.

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java#block_entity

Registrando uma `BlockEntity` concede a `BlockEntityType` como o `COUNTER_BLOCK_ENTITY` nós usamos anteriormente:

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/ModBlockEntities.java#register_block_entity

::: tip

Note como a construção do `CounterBlockEntity` tem dois parâmetros, mas a construção de `BlockEntity` tem três: o `BlockEntityType`, o `BlockPos`, e `BlockState`.
Se nós não fizermos uma codificação rígida em `BlocoEntityType`, a classe `ModBlockEntities` não iria compilar! Isso porque o `FabricBlockEntityTypeBuilder.Factory`, cujo é a interface funcional, descreve uma função que leva dois parâmetros, que nem a nossa construção.

:::

## Criando o Bloco {#creating-the-block}

A seguir, para realmente usar o bloco-entidade, nós precisamos de um bloco que implemente o 'EntityBlock'. Vamos criar um e chamá-lo de 'CounterBlock'.

::: tip

Existem duas formas de fazer isso:

- criar um bloco que estende `BaseEntityBlock` e implementa o método `newBlockEntity`
- criar um bloco que implementa `EntityBlock` sozinho e substitui o método `newBlockEntity`

Vamos usar o primeiro método nesse exemplo, desde que `BaseEntityBlock` também nos dá boas utilidades.

:::

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/CounterBlock.java#block

Usando `BaseEntityBlock` como a classe-pai significa que também temos que implementar o método `createCodec` que é bastante fácil.

Diferente de blocos, que são singletons, uma nova entidade de bloco é criada para cada instância de bloco. Isso é feito com o método `newBlockEntity`, que toma a posição e o `BlockState`, e retorna a `BlockEntity`, or `null` se lá não há algum.

Não se esqueça de adicionar a chave a `ModBlockItemIds`, assim como no guia [Criando Seu Primeiro Bloco](../blocks/first-block):

<<< @/reference/latest/src/main/java/com/example/docs/block/ModBlockItemIds.java#counter_block

<<< @/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java#counter_block

## Usando o Bloco-Entidade {#using-the-block-entity}

Agora que nós temos uma bloco-entidade, nós podemos usar para armazenar o número de vezes que o bloco foi utilizado. Para fazermos isso iremos adicionar o campo `clicks` a classe `CounterBlockEntity`:

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java#clicks

O método `setChanged`, utilizado em `icrementClicks` diz ao jogo que a informação da entidade foi atualizada; isso será útil quando nós adicionarmos métodos para serializar o contador e carregar de volta dos arquivos salvos.

A seguir, nós precisamos incrementar esse campo toda vez que o bloco é utilizado. Isso pode ser feito sobrescrevendo o método `useWithoutItem` na classe `CounterBlock`:

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/CounterBlock.java#use

Desde que `BlockEntity` não é passada para o método, nós usamos `level.getBlockEntity(pos)`, e se o `BlockEntity` não é válido, retorna para o método.

!["Você usou o block pela 6a vez" mensagem na tela após usar](/assets/develop/blocks/block_entities_1.png)

## Salvando e Carregando Dados {#saving-loading}

Agora que temos um bloco funcional, nós podemos fazer que o contador não reinicie entre reinícios de jogo. Isso será feito serializando isso entre NBT quando o jogo salva, e deserializando quando é carregado.

NBT é salvo pelos `ValueInput`s and `ValueOutput`s. Essas views são responsáveis por armazenar erros da codificação/decodificação e acompanhar os registros pelo processo de serialização.

Você pode ler de um `ValueInput` usando o método `read` e passando um `Codec`para o tipo desejado. Do mesmo modo, você pode escrever um `ValueOutput` usando o método `store`, passando um Codec para o tipo e o valor.

Também há métodos para primitivos, como `getInt`, `getShort`, `getBoolean` etc. para leitura e `putInt`, `putShort`, `putBoolean` etc. para escrita. O View também fornece métodos para trabalhar com listas, tipos anuláveis e objetos aninhados.

A serialização é feita com o método `saveAdditional`:

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java#saving

Aqui, nós adicionamos os campos que devem ser salvos no `ValueOutput` passado: no caso de um bloco contador, esse será o campo `clicks`.

Para ler é parecido, você pega os valores do `ValueInput` que você salvou anteriormente e os salva nos campos do BlockEntity:

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java#loading

Agora, se nós salvarmos e recarregarmos o jogo, o Bloco Contador deve continuar de onde foi deixado quando foi salvo.

Enquanto `saveAdditional` and `loadAdditional` lidam com o salvamento e o carregamento de dados no disco, ainda há um problema:

- O servidor sabe o valor correto de `clicks`.
- O client não recebe o valor quando baixando um chunk.

Para consertar isso, nós sobrescrevemos `getUpdateTag`:

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java#get_update_tag

Agora, quando um jogador entra ou se move em um chunk onde o bloco existe, ele irá ver o valor correto.

## Sincronizando Dados {#syncing-data}

Enquanto novos jogadores que estão carregando no bloco verão a contagem correta, o contador não atualizará para os outros jogadores que estão vendo a interação. Esse fenômeno é chamado dessincronização, e ele ocorre quando o servidor atualiza o seu estado, mas o cliente não.

Para resolver isso, nós podemos usar os pacotes de atualização de entidade de bloco. Sobrescreva o método `getUpdatePacket` e retorne um pacote contendo os dados do bloco do nosso `getUpdateTag`.

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java#update_packet

Então, sobrescreva `setChanged` para transmitir os dados sempre que a entidade de bloco mudar.

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java#broadcast_update

Agora outros jogadores também devem conseguir ver o contador mudar.

## Tickers {#tickers}

A interface `EntityBlock` também define um método chamado `getTicker`, cujo pode ser usando para rodar um código todo tick para cada instância do bloco. Nós podemos implementar isso criando o método estátistica que vai ser usado como `BlockEntityTicker`:

O método `getTicker` também deve checar se passado o `BlockEntityType` é o mesmo ao que nós estamos usando, e se é, retornar para a função que vai ser chamada todo tick. Felizmente, há uma função utilitária que realiza a verificação em `BaseEntityBlock`:

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/CounterBlock.java#tickers

`CounterBlockEntity::tick` é uma referência ao método estatística `tick` que nós devemos criar na classe `CounterBlockEntity`. Estruturar assim não é necessário, mas é uma boa prática para manter o código limpo e organizado.

Vamos dizer que nós queremos fazer o contador aumentar apenas a cada 10 ticks (2 vezes por segundo). Nós podemos fazer isso adicionando o campo `ticksSinceLast`a classe `CounterBlockEntity`, aumentando-o a cada tick:

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java#tickers

Não se esqueça de serializar e desserializar esse campo!

Agora nós podemos usar `ticksSinceLast` para checar se o contador pode ser aumentado em `incrementClicks`:

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/CounterBlockEntity.java#ticks_since_last

::: tip

Se o bloco-entidade não parece responder ao tick, cheque o código de registo! Isso deve passar os blocos válidos para essa entidade em `BlockEntityType.Builder`, caso contrário irá dar um aviso no console:

```log
[13:27:55] [Server thread/WARN] (Minecraft) Block entity example-mod:counter @ BlockPos{x=-29, y=125, z=18} state Block{example-mod:counter_block} invalid for ticking:
```

:::

<!---->
