---
title: Аргументы команд
description: Узнайте, как создавать команды со сложными аргументами.

search: false
---

# Аргументы команд

Большинство команд используют аргументы. Иногда они могут быть необязательными, что означает, что команда выполнится, даже если вы не предоставите этот аргумент. Один узел может иметь несколько типов аргументов, но будьте внимательны, чтобы избежать неоднозначности.

@[code lang=java highlight={3} transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

В этом примере после текста команды `/argtater` следует указать целое число. Например, если вы выполните команду `/argtater 3`, вы получите сообщение «Вызвано /argtater с значением = 3». Если вы выполните `/argtater` без аргументов, команда не будет правильно распознана.

Далее мы добавим необязательный второй аргумент:

@[code lang=java highlight={3,13} transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Теперь вы можете указать одно или два целых числа. Если вы укажете одно число, будет выведено сообщение с одним значением. Если вы укажете два числа, будет выведено сообщение с двумя значениями.

Возможно, вам покажется излишним дважды указывать схожие исполнения. Поэтому мы можем создать метод, который будет использоваться в обоих случаях.

@[code lang=java highlight={3,5,6,7} transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

## Собственные типы аргументов

Если в стандартной библиотеке нет нужного вам типа аргументов, вы можете создать свой. Для этого нужно создать класс, который наследуется от интерфейса `ArgumentType<T>`, где `T` — это тип аргумента.

Вам нужно будет реализовать метод `parse`, который преобразует входную строку в нужный тип.

Например, вы можете создать тип аргумента, который преобразует строку в `BlockPos` с форматом: `{x, y, z}`

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/command/BlockPosArgumentType.java)

### Регистрация своих типов аргументов

:::warning
Необходимо зарегистрировать свой тип аргументов как на сервере, так и на клиенте, иначе команда не будет работать!
:::

Вы можете зарегистрировать свой тип аргументов в методе `onInitialize` вашего инициализатора мода, используя класс `ArgumentTypeRegistry`:

@[code lang=java transcludeWith=:::11](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

### Использование своих типов аргументов

Мы можем использовать наш собственный тип аргумента в команде, передав его экземпляр в метод `.argument` при создании команды.

@[code lang=java transcludeWith=:::10 highlight={3}](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Выполнив команду, мы можем проверить, работает ли наш тип аргумента:

![Недопустимый аргумент](/assets/develop/commands/custom-arguments_fail.png)

![Допустимый аргумент](/assets/develop/commands/custom-arguments_valid.png)

![Результат команды](/assets/develop/commands/custom-arguments_result.png)
