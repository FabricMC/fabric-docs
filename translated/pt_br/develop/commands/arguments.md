---
title: Argumentos de comandos
description: Aprenda como criar comandos com argumentos complexos.
---

Argumentos são usados na maioria dos comandos. Algumas vezes eles podem ser opcionais, o que significa que se você não colocar esse argumento, o comando ainda vai rodar. Um nó pode incluir vários tipos de argumentos, mas é preciso ter cuidado para evitar ambiguidades, que devem ser evitadas.

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#command_with_arg{3}

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#execute_command_with_arg

Nesse caso, após o texto do comando `/command_with_arg`, você deve digitar um número inteiro. Por exemplo, se você executar `/command_with_arg 3`, você vai receber a mensagem de feedback:

> Called /command_with_arg with value = 3

Se você digitar `/command_with_arg` sem argumentos, o comando não poderá ser analisado corretamente.

Então, adicionamos um segundo argumento opcional:

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#command_with_two_args{3,5}

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#execute_command_with_two_args

Agora você pode digitar um ou dois números inteiros. Se você fornecer um inteiro, um texto de feedback com um único valor será impresso. Se você fornecer dois inteiros, um texto de feedback com dois valores será impresso.

Você pode achar desnecessário especificar execuções semelhantes duas vezes. Portanto, podemos criar um método que será usado em ambas as execuções.

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#command_with_common_exec{4,6}

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#execute_common

## Tipos de Argumento Personalizados {#custom-argument-types}

Se o vanilla não tiver o tipo de argumento que você precisa, você pode criar o seu próprio. Para fazer isso, é necessário criar uma classe que herde da interface `ArgumentType<T>`, onde `T` é o tipo de argumento.

Você precisará implementar o método `parse`, que irá analisar a string de entrada para o tipo desejado.

Por exemplo, você pode criar um tipo de argumento que analise um `BlockPos` a partir de uma string no seguinte formato: {x, y, z}\`

<<< @/reference/latest/src/main/java/com/example/docs/command/BlockPosArgumentType.java#custom_argument_types

### Registrando Tipos de Argumento Personalizados {#registering-custom-argument-types}

::: warning

Você precisa registrar o tipo de argumento personalizado tanto no servidor quanto no cliente, caso contrário, o comando não funcionará!

:::

Você pode registrar seu tipo de argumento personalizado no método `onInitialize` do [inicializador do seu mod](../getting-started/project-structure#entrypoints), usando a classe `ArgumentTypeRegistry`:

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#register_custom_arg

### Usando Tipos de Argumento Personalizados {#using-custom-argument-types}

Podemos usar nosso tipo de argumento personalizado em um comando — basta passar uma instância dele par o método `.argument` no builder do comando.

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#custom_arg_command{3}

<<< @/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java#execute_custom_arg_command{2}

Ao executar o comando, podemos testar se o tipo de argumento funciona:

![Argumento invalido](/assets/develop/commands/custom-arguments_fail.png)

![Argumento válido](/assets/develop/commands/custom-arguments_valid.png)

![Resultado do comando](/assets/develop/commands/custom-arguments_result.png)
