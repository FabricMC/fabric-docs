---
title: Tingimento de Bloco
description: Aprenda a como tingir um bloco dinamicamente.
authors:
  - cassiancc
  - dicedpixels
---

Às vezes você pode querer que a aparência dos blocos seja gerenciada de forma especial dentro do jogo. Por exemplo, alguns blocos, como a grama, recebem um tingimento.

Vamos dar uma olhada em como nós podemos manipular a aparência de um bloco.

Para esse exemplo, vamos registrar um bloco. Se você não estiver familiar com esse processo, por favor leia sobre [registro de bloco](./first-block) primeiro.

<<< @/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java#waxcap_tinting

Certifique-se de adicionar:

- Um [estado de bloco](./blockstates) no `/blockstates/waxcap.json`
- Um [modelo](./block-models) no `/models/block/waxcap.json`
- Uma [textura](./first-block#models-and-textures) no `/textures/block/waxcap.png`

Se tudo estiver correto, você conseguirá ver o bloco dentro do jogo.

![Aparência Correta do Bloco](/assets/develop/transparency-and-tinting/block_appearance_1.png)

## Fontes de Tingimento de Blocos {#block-tint-sources}

Apesar que o nosso bloco parece bem dentro do jogo, a sua textura está na escala de cinza. Nós poderíamos aplicar uma tonalidade de cor dinamicamente, assim como as Folhas na versão Vanilla mudam de cor baseando-se nos biomas.

O Fabric API fornece `BlockColorRegistry` para registrar uma lista de `BlockTintSource`s, cujo será usado para colorir o bloco dinamicamente.

Vamos usar essa API para registrar a tonalidade de modo que, ao colocar o nosso bloco de Cogumelo-de-cera sobre a grama, ele parecerá verde, caso contrário, parecerá marrom.

No seu **inicializador do cliente**, registre o seu bloco ao `ColorProviderRegistry`, junto à lógica apropriada.

<<< @/reference/latest/src/client/java/com/example/docs/appearance/ExampleModAppearanceClient.java#color_provider

Agora, o bloco será tingido baseado no lugar em que é colocado.

![Bloco Com Provedor de Cor](/assets/develop/transparency-and-tinting/block_appearance_2.png)
