---
title: Criando Comandos
description: Crie comandos com argumentos e ações complexas.
authors:
  - Atakku
  - dicedpixels
  - haykam821
  - i509VCB
  - Juuxel
  - MildestToucan
  - modmuss50
  - mschae23
  - natanfudge
  - Pyrofab
  - SolidBlock-cn
  - Technici4n
  - Treeways
  - xpple
resources:
  https://github.com/Mojang/brigadier: Código Fonte para o Brigadier
---

Criar comandos pode permitir que o desenvolvedor de mods adicione funcionalidade que pode ser utilizada através de um comando. Este tutorial lhe ensinará como registrar comandos e a estrutural geral de comandos do Brigadier.

::: info

[Brigadier](https://github.com/Mojang/brigadier) é um comando parser de código aberto e um dispatcher escrito pela Mojang para o Minecraft. Ele é uma biblioteca de comandos baseados em árvore onde você constrói uma árvore de comandos e argumentos.

:::

## A Interface do `Command` {#the-command-interface}

`com.mojang.brigadier.Command` é uma interface funcional, que executa alguns códigos específicos e lança um `CommandSyntaxException` em certos casos. Ela possui um tipo genérico `S`, que define o tipo do _command source_.
A origem do comando fornece algum contexto em que o comando foi executado. No Minecraft, a fonte do comando é tipicamente um `CommandSourceStack` que pode representar um servidor, um bloco de comando, uma conexão remota (RCON), um jogador ou uma entidade.

O único método em `Command`, `run(CommandContext<S>)` leva um `CommandContext<S>` como o único parametro e retorna um inteiro. O contexto do comando segura a fonte do seu comando do `S` e te permite obter argumentos, dê uma olhada nos nós de comando analisados e veja a entrada usada nesse comando.

Como outras interfaces funcionais, ele é normalmente usado como um lambda ou uma referência de método:

```java
Command<CommandSourceStack> command = context -> {
    return 0;
};
```

O inteiro pode ser considerado o resultado do comando. Tipicamente, valores abaixo ou iguais a zero significam que um comando falhou ou não fará nada. Valores positivos significam que o comando foi executado com sucesso e fez alguma coisa. O Brigadier fornece uma constante para indicar sucesso; `Command#SINGLE_SUCCESS`.

### O que o `CommandSourceStack` Consegue Fazer? {#what-can-the-servercommandsource-do}

Um `CommandSourceStack` fornece algum contexto específico da implementação adicional quando um comando é executado. Isso inclui a capacidade de obter a entidade que executou o comando, o mundo onde o comando foi executado, ou o servidor onde o comando foi executado.

Você pode acessar a fonte do comando a partir de um contexto de comando chamado `getSource()` na instância de `CommandContext`.

```java
Command<CommandSourceStack> command = context -> {
    CommandSourceStack source = context.getSource();
    return 0;
};
```

## Registrando um Comando Básico {#registering-a-basic-command}

Comandos são registrados dentro do `CommandRegistrationCallback` fornecido pela Fabric API.

::: info

Para informações sobre como registrar callbacks, por favor, consulte o guia de [Eventos](../events).

:::

O evento deve ser registrado no [inicializador do seu mod](../getting-started/project-structure#entrypoints).

O callback possui três parâmetros:

- `CommandDispatcher<CommandSourceStack> dispatcher` - Usado para registrar, analisar e executar comandos. `S`é o tipo de fonte de comando que o command dispatcher suporta.
- `CommandBuildContext registryAccess` - Fornece uma abstração para registros que podem ser passados para certos métodos de argumento de comando
- `Commands.CommandSelection environment` - Identifica o tipo de servidor onde os comandos estão sendo registrados.

No inicializador do mod, nós apenas registramos um comando simples:

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#test_command

No método `sendSuccess()`, o primeiro parâmetro é o texto a ser enviado, que é um `Supplier<Component>` para evitar a instanciação de objetos `Component` quando não for necessário.

O segundo parâmetro determina se o feedback deve ser transmitido para outros moderadores. Geralmente, se o comando for para consultar algo sem realmente afetar o mundo, como verificar a hora atual ou a pontuação de algum jogador, ele deve ser `false`. Se o comando fizer algo, como mudar a hora, ou modificar a pontuação de um jogador, ele deve ser `true`.

Se o comando falhar, em vez de chamar `sendSuccess()`, você pode lançar diretamente qualquer exceção e o servidor ou cliente a tratará de forma apropriada.

`CommandSyntaxException` é geralmente lançada para indicar erros de sintaxe em comandos ou argumentos. Você também pode implementar sua própria exceção.

Para executar este comando, você deve digitar `/test_command`, que diferencia maiúsculas e minúsculas.

::: info

Deste ponto em diante, estaremos extraindo a lógica escrita no lambda passada para os construtores `.executes()` para métodos individuais. Poderemos então passar uma referência de método para `.executes()`. Isso é feito para maior clareza.

:::

### Ambiente de Registro {#registration-environment}

Se desejar, você também pode garantir que um comando seja registrado apenas sob circunstâncias específicas, por exemplo, apenas no ambiente dedicado:

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#dedicated_command{2}

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#execute_dedicated_command

### Requisitos de Comando {#command-requirements}

Digamos que você tenha um comando que você quer que apenas moderadores possam executar. É aqui que o método `requires()` entra em jogo. O método `requires()` tem um argumento do tipo `Predicate<S>`, que fornecerá um `CommandSourceStack` para testar e determinar se a `CommandSource` pode executar o comando.

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#required_command{3}

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#execute_required_command

Esse comando só será executado se a fonte do comando for no mínimo um moderador, incluindo blocos de comando. Caso contrário, o comando não é registrado.

Isso tem o efeito colateral de não mostrar este comando no preenchimento automático <kbd>Tab</kbd> para quem não for um moderador. É também por isso que você não consegue preencher a maioria dos comandos com a tecla <kbd>Tab</kbd> quando trapaças não forem habilitadas.

### Subcomandos {#sub-commands}

Para adicionar um subcomando, você registra o primeiro nó literal do comando normalmente. Para ter um subcomando, você deve anexar o próximo nó literal ao nó existente.

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#sub_command_one{3}

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#execute_sub_command_one

Similar aos argumentos, os nós de subcomando também podem ser definidos como opcionais. No caso a seguir, tanto `/command_two` quanto `/command_two sub_command_two` serão validos.

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#sub_command_two{2,8}

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#execute_command_sub_command_two

## Comandos do Cliente {#client-commands}

Similarmente, a Fabric API fornece o evento `ClientCommandRegistrationCallback` no pacote `net.fabricmc.fabric.api.client.command.v2` que pode ser usado para registrar comandos do lado do cliente, substituindo a classe vanilla `Commands` com o equivalente `ClientCommands`. O código deve existir apenas no código do lado do cliente.

<<< @/reference/latest/src/client/java/com/example/docs/client/command/ExampleModClientCommands.java#register_command

## Redirecionamentos de Comando {#command-redirects}

Redirecionamentos de Comando - também conhecidos como aliases, ou apelidos - são uma forma de redirecionar a funcionalidade de um comando para outro. Isso é útil quando você deseja alterar o nome de um comando, mas ainda quer manter o suporte ao nome antigo.

::: warning

O Brigadier [só irá redirecionar nós de comando que possuam argumentos](https://github.com/Mojang/brigadier/issues/46). Se você quiser redirecionar um nó de comando sem argumentos, forneça um construtor `.executes()` com uma referência à mesma lógica, conforme descrito no exemplo.

:::

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#redirect_command

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#execute_redirected_by

## Perguntas Frequentes {#faq}

### Por Que Meu Código Não Compila? {#why-does-my-code-not-compile}

- Capture ou lance uma `CommandSyntaxException` - `CommandSyntaxException` não é uma `RuntimeException`. Se você a lança, isso deve ser feito em métodos que declaram a `CommandSyntaxException` em métodos assinaturas, ou ela deve ser capturada.
  O Brigadier vai lidar com as exceções checadas e encaminhará a mensagem de erro apropriada no jogo para você.

- Problemas com generics - Você pode ter um problema com generics ocasionalmente. Se você estiver registrando comandos de servidor (o que é maioria dos casos), certifique-se de estar usando`Command.literal` ou `Commands.argument` em vez de `LiteralArgumentBuilder.literal` ou `RequiredArgumentBuilder.argument`.

- Verifique o método `sendSuccess()` - Você pode ter esquecido de fornecer um booleano como segundo argumento. Lembre-se também que, desde o Minecraft 1.20, o primeiro parâmetro é `Supplier<Component>` em vez de `Component`.

- Um Comando deve retornar um inteiro - Ao registrar comandos, o método `executes()` aceita um objeto `Command`, que geralmente é um lambda. O lambda deve retornar um valor inteiro, em vez de outros tipos.

### Posso Registar Comandos em Tempo de Execução? {#can-i-register-commands-at-runtime}

::: warning

Você pode fazer isso, mas não é recomendado. Você pegaria o `Commands` do servidor e adicionaria comandos a qualquer coisa que você deseja ao `CommandDispatcher` dele.

Depois disso, você precisa enviar a árvore de comandos para todos os jogadores novamente usando `Commands.sendCommands(ServerPlayer)`.

Isso é necessário porque o cliente armazena em cache localmente a árvore de comandos que recebe durante o login (ou quando pacotes de moderador são enviados) para mensagens de erro ricas em preenchimentos locais.

:::

### Posso Desregistrar Comandos em Tempo de Execução? {#can-i-unregister-commands-at-runtime}

::: warning

Você também pode fazer isso, no entanto, isso é muito menos estável do que registrar comandos durante o tempo de execução e poderia causar efeitos colaterais indesejados.

Para manter as coisas simples, você precisa usar reflection no Brigadier e remover nós. Depois disso, você precisa enviar a árvore de comandos para cada jogar novamente usando `sendCommands(ServerPlayer)`.

Se você não enviar a árvore de comandos atualizada, o cliente pode pensar que um comando ainda existe, mesmo que o servidor falhe na execução.

:::

<!---->
