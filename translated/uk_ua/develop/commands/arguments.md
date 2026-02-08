---
title: Аргументи команд
description: Дізнайтеся, як створювати команди зі складними аргументами.
---

Аргументи використовуються в більшості команд. Іноді вони можуть бути необов’язковими, тобто якщо ви не надасте аргумент, команда також буде виконана. Один вузол може мати кілька типів аргументів, але майте на увазі, що існує можливість двозначність, якої слід уникати.

@[code lang=java highlight={3} transcludeWith=:::command_with_arg](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

@[code lang=java transcludeWith=:::execute_command_with_arg](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

У цьому випадку після тексту команди `/command_with_arg` слід ввести ціле число. Наприклад, якщо ви
запустіть `/command_with_arg 3`, ви отримаєте повідомлення зворотного зв'язку:

> Викликається /command_with_arg зі значенням = 3

Якщо ввести `/command_with_arg` без аргументів, команду неможливо правильно проаналізувати.

Потім ми додаємо необов'язковий другий аргумент:

@[code lang=java highlight={3,5} transcludeWith=:::command_with_two_args](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

@[code lang=java transcludeWith=:::execute_command_with_two_args](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

Зараз ви можете написати одне або два цілих числа. Якщо ви надаєте одне число — відповідь з одним значенням буде надрукована. Якщо ви надаєте два чиста — відповідь з двома значеннями буде надрукована.

Ви можете вважати непотрібним вказувати подібні виконання двічі. Таким чином, ми можемо створити метод, який буде використовуватися в
обидва виконання.

@[code lang=java highlight={4,6} transcludeWith=:::command_with_common_exec](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

@[code lang=java transcludeWith=:::execute_common](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

## Власні типи аргументу {#custom-argument-types}

Якщо в грі не має потрібного типу аргументу, ви можете створити свій власний. Для цього вам потрібно створити клас, який успадковує інтерфейс `ArgumentType<T>`, де `T` є типом аргументу.

Вам потрібно буде реалізувати метод `parse`, який аналізуватиме вхідний рядок у потрібний тип.

Наприклад, ви можете створити тип аргументу, який аналізує `BlockPos` із рядка в такому форматі: `{x, y, z}`

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/command/BlockPosArgumentType.java)

### Реєстрація спеціальних типів аргументів {#registering-custom-argument-types}

::: warning

Вам потрібно зареєструвати тип спеціального аргументу як на сервері, так і на клієнті, інакше команда не працюватиме!

:::

Ви можете зареєструвати власний тип аргументу в методі `onInitialize` вашого [ініціалізатора мода](../getting-started/project-structure#entrypoints) за допомогою класу `ArgumentTypeRegistry`:

@[code lang=java transcludeWith=:::register_custom_arg](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

### Використання спеціальних типів аргументів {#using-custom-argument-types}

Ми можемо використовувати наш власний тип аргументу в команді, передавши його екземпляр у метод `.argument` у конструкторі команд.

@[code lang=java highlight={3} transcludeWith=:::custom_arg_command](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

@[code lang=java highlight={2} transcludeWith=:::execute_custom_arg_command](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

Виконуючи команду, ми можемо перевірити, чи працює тип аргументу:

![Недійсний аргумент](/assets/develop/commands/custom-arguments_fail.png)

![Дійсний аргумент](/assets/develop/commands/custom-arguments_valid.png)

![Результат команди](/assets/develop/commands/custom-arguments_result.png)
