---
title: Аргументы команд
description: Узнайте, как создавать команды со сложными аргументами.
---

Большинство команд используют аргументы. Иногда они могут быть необязательными, что означает, что команда выполнится, даже если вы не предоставите этот аргумент. Один узел может иметь несколько типов аргументов, но будьте внимательны, чтобы избежать неоднозначности.

<<< @/reference/26.1.2/src/main/java/com/example/docs/command/ExampleModCommands.java#command_with_arg{3}

<<< @/reference/26.1.2/src/main/java/com/example/docs/command/ExampleModCommands.java#execute_command_with_arg

В этом случае после текста команды `/command_with_arg` следует ввести целое число. Например, если вы запустите `/command_with_arg 3`, вы получите сообщение:

> Вызывается /command_with_arg со значением = 3

Если ввести /command_with_arg без аргументов, команду не удастся правильно обработать.

Далее мы добавим необязательный второй аргумент:

<<< @/reference/26.1.2/src/main/java/com/example/docs/command/ExampleModCommands.java#command_with_two_args{3,5}

<<< @/reference/26.1.2/src/main/java/com/example/docs/command/ExampleModCommands.java#execute_command_with_two_args

Теперь вы можете указать одно или два целых числа. Если вы укажете одно число, будет выведено сообщение с одним значением. Если вы укажете два числа, будет выведено сообщение с двумя значениями.

Возможно, вам покажется излишним дважды указывать схожие исполнения. Поэтому мы можем создать метод, который будет использоваться в обоих случаях.

<<< @/reference/26.1.2/src/main/java/com/example/docs/command/ExampleModCommands.java#command_with_common_exec{4,6}

<<< @/reference/26.1.2/src/main/java/com/example/docs/command/ExampleModCommands.java#execute_common

## Собственные типы аргументов {#custom-argument-types}

Если в стандартной библиотеке нет нужного вам типа аргументов, вы можете создать свой. Для этого нужно создать класс, который наследуется от интерфейса `ArgumentType<T>`, где `T` — это тип аргумента.

Вам нужно будет реализовать метод `parse`, который преобразует входную строку в нужный тип.

Например, вы можете создать тип аргумента, который преобразует строку в `BlockPos` с форматом: `{x, y, z}`

<<< @/reference/26.1.2/src/main/java/com/example/docs/command/BlockPosArgumentType.java#custom_argument_types

### Регистрация своих типов аргументов {#registering-custom-argument-types}

::: warning

Cвой тип аргументов должен быть зарегистрирован как на сервере, так и на клиенте, иначе команда не будет работать!

:::

Вы можете зарегистрировать свой собственный тип аргумента в методе `onInitialize` вашего [инициализатора мода](../getting-started/project-structure#entrypoints), используя класс `ArgumentTypeRegistry`:

<<< @/reference/26.1.2/src/main/java/com/example/docs/command/ExampleModCommands.java#register_custom_arg

### Использование своих типов аргументовs {#using-custom-argument-types}

Мы можем использовать наш собственный тип аргумента в команде, передав его экземпляр в метод `.argument` при создании команды.

<<< @/reference/26.1.2/src/main/java/com/example/docs/command/ExampleModCommands.java#custom_arg_command{3}

<<< @/reference/26.1.2/src/main/java/com/example/docs/command/ExampleModCommands.java#execute_custom_arg_command{2}

Выполнив команду, мы можем проверить, работает ли наш тип аргумента:

![Недопустимый аргумент](/assets/develop/commands/custom-arguments_fail.png)

![Допустимый аргумент](/assets/develop/commands/custom-arguments_valid.png)

![Результат команды](/assets/develop/commands/custom-arguments_result.png)
