---
title: Relatórios de Travamento
description: Aprenda o que fazer com os relatórios de travamento e como lê-los.
authors:
  - IMB11
---

<!---->

::: tip

Se você estiver com dificuldades em encontrar a causa do travamento, você pode pedir ajuda no [Discord do Fabric](https://discord.fabricmc.net/) nos canais `#player-support` ou `#server-admin-support`.

:::

Relatórios de travamento são uma parte muito importante para a resolução de problemas dentro do seu jogo ou servidor. Eles contêm muitas informações sobre o travamento e podem ajudar a encontrar a causa do mesmo.

## Encontrando os Relatórios de Travamento {#finding-crash-reports}

Os Relatórios de travamento estão guardados no repositório `crash-reports` dentro do seu diretório do jogo. Se você estiver usando um servidor, eles estão guardados no repositório `crash-reports` no diretório do Servidor.

Para outros iniciadores, você deveria consultar suas respectivas documentações em onde encontrar os Relatórios de Travamento.

Os Relatórios de Travamento podem ser encontrados nas seguintes localizações:

::: code-group

```text:no-line-numbers [Windows]
%appdata%\.minecraft\crash-reports
```

```text:no-line-numbers [macOS]
~/Library/Application Support/minecraft/crash-reports
```

```text:no-line-numbers [Linux]
~/.minecraft/crash-reports
```

:::

## Lendo os Relatórios de Travamento {#reading-crash-reports}

Os Relatórios de Travamento são muito longos e podem ser muito confusos de ler. Entretanto, eles contêm muitas informações sobre o travamento e podem ajudar a encontrar a causa do mesmo.

Para esse guia, usaremos [esse relatório de travamento](/assets/players/crash-report-example.log).

:::details Mostrar o Relatório de Travamento

<<< @/public/assets/players/crash-report-example.log

:::

### Seções do Relatório de Travamento {#crash-report-sections}

Relatórios de Travamento consistem em muitas seções, cada uma separada por um cabeçalho:

- `---- Minecraft Crash Report ----`, O resumo do Relatório. Essa seção irá conter o principal erro que pode ter causado o travamento, a hora que ocorreu e o Stack Trace (Rastreamento de Pilha). Essa é a seção é a mais importante do Relatório de Travamento, já que o Rastreamento de Pilha pode, normalmente, conter referências ao mod que causou o Travamento.
- `-- Last Reload --`, essa seção não é tão útil, a não ser que o travamento tenha ocorrido durante o reiniciamento de um recurso (<kbd>F3</kbd>+<kbd>T</kbd>). Essa seção irá contar a hora do último reiniciamento e o Rastreamento de Pilha de quaisquer erros que possa ter ocorrido durante o processo de reiniciamento. Esses erros são, normalmente, causados por Pacotes de Recursos e podem ser ignorados a não ser que estejam causando problemas dentro do jogo.
- `-- System Details --`, essa seção contém informação sobre o seu sistema. Como, por exemplo, a versão do Java em execução e a quantidade de memória alocada para o jogo. Essa seção é útil para determinar se você está usando a versão correta do Java e se você está alocando memória suficiente para o jogo.
  - Nessa seção, o Fabric terá incluído uma linha personalizada dizendo `Fabric Mods:`, seguida de uma lista com todos os mods que você instalou. Essa seção é útil para determinar se qualquer conflito pode ocorrer entre mods.

### Entendendo melhor o Relatório de Travamento {#breaking-down-the-crash-report}

Agora que sabemos o que cada seção do Relatório de Travamento é, podemos começar a entendê-lo melhor e achar a causa do travamento.

Usando o exemplo acima, podemos analisar o Relatório de Travamento e achar sua causa. Incluindo os mods que podem ter levado ao travamento.

O Rastreamento de Pilha na seção `---- Minecraft Crash Report ----` é a parte mais importante nesse caso. Já que ele contém o principal erro que causou o travamento.

:::details Mostrar Erro

<<< @/public/assets/players/crash-report-example.log{7}

:::

Com a quantidade de mods mencionada no Rastreamento de Pilha, pode ser difícil achar o causador do problema. Mas, a primeira coisa a se fazer é olhar para o mod que causou o travamento.

Nesse caso, o mod que causou o travamento é `snownee`, já que é o primeiro mod mencionado no Rastreamento de Pilha.

Entretanto, com a quantidade mencionada, poderia significar haver algum problema de compatibilidade entre os mods, e que o mod que causou o travamento pode não ser o culpado pelo mesmo. Nesse caso, é melhor reportar, para o autor do mod, e deixá-lo investigar o travamento.

## Travamentos de Mixin {#mixin-crashes}

::: info

Mixins são formas dos mods modificar o jogo sem ter que modificar o código-fonte do jogo. Elas são usadas por muitos mods e são ferramentas muito poderosas para os desenvolvedores de mods.

:::

Quando uma Mixin trava, normalmente, será mencionada no Rastreamento de Pilha e a classe que a Mixin está modificando.

O Processo de uma Mixin é mostrado como `mod-id$handlerName` no Rastreamento de Pilha, onde `mod-id` é o ID do mod e `handlerName` é o nome do Gerenciador da Mixin.

```text:no-line-numbers
... net.minecraft.class_2248.method_3821$$$mod-id$handlerName() ... // [!code focus]
```

Você pode usar essa informação para encontrar o mod que está causando o travamento e relatá-lo para o autor do mod.

## O que fazer com os Relatórios de Travamento {#what-to-do-with-crash-reports}

A melhor coisa a se fazer com os Relatórios de Travamento é subi-lo para um Site de Armazenamento (Paste Sites) e então compartilhá-lo com o autor do mod. Tanto para os seus rastreadores de problemas, quanto através de outras formas de comunicação (Discord, etc.).

Isso irá permitir que o autor do mod investigue o travamento, potencialmente reproduzi-lo e resolver o que o causou.

Sites de Armazenamento mais comuns são:

- [GitHub Gist](https://gist.github.com/)
- [mclo.gs](https://mclo.gs/)
- [Pastebin](https://pastebin.com/)
