---
title: Estações de trabalho
description: Aprenda a como criar estações de trabalho.
authors:
  - cassiancc
  - ekulxam
  - skippyall
---

<!---->

:::info PRÉ-REQUISITOS

Essa estação de trabalho usa um tipo de receita customizada, que pode ser encontrada em [Tipos de Receita Customizadas](../recipes/custom-recipe-types).

:::

Esse tutorial fornecerá instruções sobre como criar uma estação de trabalho customizada. Diferente de baús, estações de trabalho não precisam necessariamente reter o seu inventário depois que a UI é fechada (blocos como a Bancada de Trabalho não salvam o seu inventário, mas há outros que salvam, como, por exemplo, fornalhas). Para demonstrar, nós não usaremos um BlockEntity.

## Criando um Menu {#creating-a-menu}

::: info

Para mais detalhes sobre a criação de menus, verifique o guia [Menus de Contêineres](./container-menus).

:::

Para que possamos craftar a nossa receita na GUI, nós criaremos um bloco com o Menu. Para abrir o menu, nós precisaremos sobrescrever alguns métodos na nossa classe `Block`:

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/UpgradingBlock.java#openmenu

Após isso, nós estamos prontos para criar o menu.

<<< @/reference/latest/src/main/java/com/example/docs/menu/custom/UpgradingMenu.java#menu

Para acompanhar esse menu, nós também precisaremos de um resultado customizado `Slot`.

<<< @/reference/latest/src/main/java/com/example/docs/menu/custom/UpgradingResultSlot.java#slot

Tem bastante coisa para explicar aqui! Esse menu tem dois slots de entrada e uma saída `UpgradingResultSlot`.

O contêiner de entrada é uma subclasse anonima de `SimpleContainer`, que chama o método `slotsChanged` do menu quando os seus itens mudam. Em `slotsChanged`, nós então criamos uma instância da nossa classe de receita de entrada.

Para ver se alguma receita corresponde, nós vamos primeiro assegurar que nós estamos no nível servidor, assim que os clientes não sabem quais receitas existem. Então, nós vamos buscar pelo `RecipeManager` via `serverLevel.recipeAccess()`.

:::details Uma observação: Sincronia de Receitas

> Se o cliente não sabe quais receitas existem, então como o livro de receitas funciona?

Fico feliz que perguntou. O servidor conta ao cliente quais receitas existem baseado em quais receitas você desbloqueou (feito ao atender certos critérios descritos no Advancement JSON da receita, como, por exemplo, obter um item ou entrar na água (para barcos)). No entando, isso é meio incomodativo para mods visualizadores de receitas, que idealmente gostariam de ver todas as receitas disponíveis, mas podem ver apenas as receitas que o cliente recebe do servidor. Para contornar isso, nós podemos [usar o Fabric API para sincronizar nossas receitas](../recipes/custom-recipe-types#recipe-synchronization).

:::

Nós chamaremos `serverLevel.recipeAccess().getRecipeFor` com a nossa entrada de receita para obter uma receita que corresponde as entradas. Caso uma receita for encontrada, nós podemos adicionar ou remover o resultado do contêiner de resultado.

Para detectara quando o usuário retira o resultado, nós usamos a sobrescrita `onTake` do `UpgradingResultSlot`. O método `onTake` do nosso menu então incrementa os itens de entrada.

Para garantir que o jogador está dentro do alcance de interação do bloco, nós sobrescrevemos `stillValid`.

::: warning

Certifique-se que o `Block` que você passa como um argumento para `stillValid` é o bloco abrindo o menu! Caso contrário, o menu e a tela podem abrir e em seguida fechar sozinhas imediatamente.

:::

Finalmente, para prevenir a exclusão de itens, é importante devolver os itens fornecidos quando a tela é fechada, assim como é mostrado no método `removed`.

::: info

Você pode ter percebido que múltiplos métodos contém uma chamada `ContainerLevelAccess#execute`. Essa é uma classe wrapper usada pela Mojang para assegurar que o `Level` e a posição correta estejam em uso quando a interação ocorre, e previne que os jogadores acessem os contêineres que eles não deveriam. Note que o `ContainerLevelAccess`, especial `NULL`, não performa nenhuma ação quando `execute` é chamado nele.

:::

O método `mayPlace` do `Slot` retorna `false` para que jogadores não possam inserir itens dentro do slot de resultado, e o método `isFake` avisa a `Screen` que a pilha que ela contém não possui um dono (ainda).

Você também precisa adicionar o menu ao registro:

<<< @/reference/latest/src/main/java/com/example/docs/menu/ModMenuTypes.java#upgrading_menu_registration

Finalmente, nós precisamos registrar o nosso bloco:

<<< @/reference/latest/src/main/java/com/example/docs/block/ModBlockItemIds.java#workstation

<<< @/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java#workstation

### Implementando `quickMoveStack` {#implementing-quick-move-stack}

::: info

Veja também: [Menus de Contêineres: Criando o Menu](./container-menus#creating-the-menu)

:::

O Quick Move é chamado em qualquer momento que um clique com o botão esquerdo segurando shift é realizado em um Menu.

<<< @/reference/latest/src/main/java/com/example/docs/menu/custom/SuperiorUpgradingMenu.java#quickMove

Uau, tem um monte de códigos de novo. Vamos tentar refletir sobre o que está acontecendo.

Normalmente, ao realizar o quick move sobre uma pilha da área do inventário, o menu primeiro verifica se o slot clicado é o slot de resultado (com o índex 0). Caso positivo, o menu tenta mover a pilha de resultado para dentro do inventário, mas caso falhe, nada acontece.

Próximo, o menu verifica se o slot clicado pertence ao inventário. Caso positivo, então o menu tenta mover a pilha para dentro das entradas. Se isso falhar, nós tentamos mover a pilha para dentro do inventário (slots clicados na barra rápida (hotbar) movem suas pilhas para os outros 27 slots do inventário e vice-versa).

Se o slot clicado não era o slot de resultado ou não estava dentro do inventário, o slot então é quase garantido de ter sido um dos nossos dois slots de entrada, então nós gostaríamos de mover as suas pilhas de volta ao inventário.

### A Tela {#screen}

::: info

Veja também: [Menu de Contêineres: Criando a Tela](./container-menus#creating-the-screen)

:::

Por enquanto, nós podemos só pegar a textura do plano de fundo vanilla da bigorna emprestada.

<<< @/reference/latest/src/client/java/com/example/docs/rendering/screens/inventory/UpgradingScreen.java#screen

Não esqueça de vincular o seu tipo de menu à tela dentro do seu `ClientModInitializer`, da seguinte forma:

<<< @/reference/latest/src/client/java/com/example/docs/ExampleModRecipesClient.java#register_with_menu

## Restos de Receitas {#recipe-remainders}

Quer fazer uma receita que suporte itens restantes? Nós recomendamos dar uma olhada no `net.minecraft.world.inventory.ResultSlot#getRemainingItems`. A bancada de trabalho usa isso como um slot de resultado, várias similaridades entre os documentos podem ser encontradas, mas também há algumas diferenças.
