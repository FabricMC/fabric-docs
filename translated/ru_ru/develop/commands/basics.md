---
title: Создание команд
description: Создавайте команды со сложными аргументами и действиями.
authors:
  - atakku
  - dicedpixels
  - haykam
  - i509VCB
  - Juuxel
  - MildestToucan
  - modmuss50
  - mschae23
  - natanfudge
  - pyrofab
  - solidblock
  - technici4n
  - treeways
  - xpple
resources:
  https://github.com/Mojang/brigadier: Исходный код Brigadier
---

Создание команд позволяет разработчику мода добавлять функционал, который может быть использован при вызове команд. Это руководство научит вас регистрировать команды и общую структуру команд Brigadier.

::: info

[Brigadier](https://github.com/Mojang/brigadier) — парсер и диспетчер команд с открытым исходным кодом, написанный Mojang для Minecraft. Это библиотека для команд, с которой вы создаёте дерево команд и аргументов.

:::

## Интерфейс `Command` {#the-command-interface}

`com.mojang.brigadier.Command` — это функциональный интерфейс, который запускает конкретный код и вызывает ошибку `CommandSyntaxException` в определённых случаях. Он имеет аргумент типа `S`, который определяет тип _источника команды_.
Источник команды предоставляет контекст, в котором была запущена команда. В Minecraft, источником команды чаще всего является `CommandSourceStack`, который может представлять сервер, командный блок, удалённое соединение (RCON), игрока или сущность.

Единственный метод в `Command`, `run(CommandContext<S>)`, принимает `CommandContext<S>` в качестве единственного аргумента и возвращает целое число. Контекст команды содержит источник вашей команды как `S` и позволяет получить аргументы, посмотреть на спарсенные узлы команды и увидеть вводные данные, используемые в этой команде.

Как и другие функциональные интерфейсы, этот обычно используется как лямбда или ссылка на метод:

```java
Command<CommandSourceStack> command = context -> {
    return 0;
};
```

Целое число можно считать результатом команды. Обычно значения меньше или равные нулю означают, что команда не выполнена и ничего не сделает. Положительные значения означают, что команда успешно выполнилась и что-то выполнила. Brigadier предоставляет константу для обозначения успеха: `Command#SINGLE_SUCCESS`.

### Что может делать `CommandSourceStack`? {#what-can-the-servercommandsource-do}

`CommandSourceStack` предоставляет дополнительный контекст при выполнении команды. Это включает в себя возможность получить сущность, выполнившую команду, мир, в котором была запущена команда, или сервер, на котором была запущена команда.

Вы можете получить источник команды из командного контекста при вызове `getSource()` в экземпляре `CommandContext`.

```java
Command<CommandSourceStack> command = context -> {
    CommandSourceStack source = context.getSource();
    return 0;
};
```

## Регистрация простейшей команды {#registering-a-basic-command}

Команды регистрируются внутри `CommandRegistrationCallback`, предоставляемом Fabric API.

::: info

Для информации о регистрации обратных вызовов прочитайте руководство по [событиям](../events).

:::

Событие должно быть зарегистрировано в вашем [инициализаторе мода](../getting-started/project-structure#entrypoints).

Обратный вызов имеет три параметра:

- `CommandDispatcher<CommandSourceStack> dispatcher` — Используется для регистрации, парсинга и выполнения команд. `S` — это тип источника команд, поддерживаемый диспетчером команд.
- `CommandBuildContext registryAccess` — Предоставляет абстракцию реестров, которую можно передать некоторым методам аргументов команды
- `Commands.CommandSelection environment` — Определяет тип сервера, на котором регистрируются команды.

В инициализаторе мода мы просто регистрируем простую команду:

@[code lang=java transcludeWith=:::test_command](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

В методе`sendSuccess()` первый аргумент — это текст для отправки, который является `Supplier<Component>` во избежание создания экземпляров `Component` вне необходимости.

Второй параметр определяет, следует ли транслировать обратную связь другим модераторам. Чаще всего, если команда предназначена для запроса чего-либо без затрагивания мира, например, запросить текущее время или счёт игрока, он должен быть `false`. Если команда делает что-либо, например, изменение времени или изменение чьего-либо счёта, он должно быть `true`.

Если команда не выполняется, вместо вызова `sendFeedback()` вы можете напрямую выбросить любое исключение, и сервер или клиент справится с этим соответствующим образом.

`CommandSyntaxException` обычно выбрасывается для обозначения синтаксических ошибок в командах или в аргументах. Вы можете также реализовать своё исключение.

Чтобы выполнить эту команду, вы должны ввести `/test_command` с учётом регистра.

::: info

С этого момента мы будем извлекать логику, написанную в лямбде, передаваемой в сборщики `.executes()`, в отдельные методы. После этого мы можем передать ссылку на метод в `.executes()`. Это сделано для ясности.

:::

### Среда регистрации {#registration-environment}

При желании вы также можете сделать так, чтобы команда регистрировалась только при определённых условиях, например, только в среде выделенного сервера:

@[code lang=java highlight={2} transcludeWith=:::dedicated_command](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

@[code lang=java transcludeWith=:::execute_dedicated_command](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

### Требования к команде {#command-requirements}

Допустим, что у вас есть команда и вы хотите, чтобы её могли выполнять только модераторы. В этом случае можно использовать метод `requires()`. Метод `requires()` имеет один аргумент `Predicate<S>`, который будет предоставлять `CommandSourceStack` для проверки возможности `CommandSource` выполнить команду.

@[code lang=java highlight={3} transcludeWith=:::required_command](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

@[code lang=java transcludeWith=:::execute_required_command](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

Эта команда выполнится только в том случае, если источник команды является как минимум модератором, что также включает командные блоки. В противном случае команда не зарегистрируется.

Побочным эффектом этого является то, что эта команда не показывается в <kbd>Tab</kbd>-автозаполнении для всех игроков, не являющихся модераторами. Это также объясняет, почему вы не можете <kbd>Tab</kbd>-дополнить большинство команд, когда не включены читы.

### Подкоманды {#sub-commands}

Чтобы добавить подкоманду, первый литеральный узел команды регистрируется как обычно. Чтобы получить подкоманду, вы должны добавить следующий литеральный узел к уже существующему узлу.

@[code lang=java highlight={3} transcludeWith=:::sub_command_one](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

@[code lang=java transcludeWith=:::execute_sub_command_one](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

Подобно аргументам, узлы подкоманд могут также быть необязательными. В следующем случае будут допустимы как `/command_two`, так и `/command_two sub_command_two`.

@[code lang=java highlight={2,8} transcludeWith=:::sub_command_two](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

@[code lang=java transcludeWith=:::execute_command_sub_command_two](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

## Клиентские команды {#client-commands}

Аналогично, Fabric API предоставляет событие `ClientCommandRegistrationCallback` в пакете `net.fabricmc.fabric.api.cient.command.v2`, которое может быть использовано для регистрации клиентских команд, заменяя ванильный класс `Commands` на эквивалентный ему `ClientCommands`. Этот код должен существовать только на стороне клиента.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/client/command/ExampleModClientCommands.java)

## Перенаправления команд {#command-redirects}

Перенаправления команд, также известное как псевдонимы — это способ перенаправить функционал одной команды к другой. Это полезно, если вы хотите изменить название команды, но всё равно хотите поддерживать старое название.

::: warning

Brigadier [будет перенаправлять только командные узлы с аргументами](https://github.com/Mojang/brigadier/issues/46). Если вы хотите перенаправить командный узел без аргументов, предоставьте конструктор `.executes()` со ссылкой на ту же логику, что описана в примере.

:::

@[code lang=java transcludeWith=:::redirect_command](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

@[code lang=java transcludeWith=:::execute_redirected_by](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

## ЧАВО {#faq}

### Почему мой код не компилируется? {#why-does-my-code-not-compile}

- Словите или выбросите `CommandSyntaxException` — `CommandSyntaxException` не является `RuntimeException`. Если вы его выбросите, то это должно быть сделано в методах, в сигнатурах которых указано выбрасывание `CommandSyntaxException`, или его нужно будет поймать.
  Brigadier обработает проверяемые исключения и отправит вам сообщение об ошибке в игре.

- Проблемы с параметрами типов — иногда у вас могут возникать проблемы с параметрами типов. Если вы регистрируете серверные команды (в большинстве случаев), убедитесь, что вы используете `Commands.literal` или `Commands.argument` вместо `LiteralArgumentBuilder.literal` или `RequiredArgumentBuilder.argument`.

- Проверьте метод `sendFeedback()` — возможно, вы забыли указать логическое значение как второй аргумент. Также помните, что, начиная с версии Minecraft 1.20, первым аргументом должен быть `Supplier<Component>` вместо `Component`.

- Команда должна возвращать целое число — при регистрации команд метод `executes()` принимает объект `Command`, который обычно является лямбдой. Лямбда должна возвращать целое число, а не какой-то другой тип.

### Могу ли я зарегистрировать команды во время игры? {#can-i-register-commands-at-runtime}

::: warning

Это сделать возможно, но не рекомендуется. Вам бы потребовалось получить `Commands` с сервера и добавить что угодно, связанное с командами, в его `CommandDispatcher`.

После этого вам нужно снова отправить дерево команд всем игрокам с помощью `Commands.sendCommands(ServerPlayer)`.

Это необходимо, поскольку клиент локально кэширует дерево команд, получаемую во время входа (или когда отправляются пакеты модераторов) для локальных сообщений об ошибках с автозаполнением.

:::

### Могу я отменить регистрацию команд во время игры? {#can-i-unregister-commands-at-runtime}

::: warning

Это тоже сделать возможно, но это намного нестабильнее, чем регистрация команд во время игры, и может вызвать нежелательные побочные эффекты.

Проще говоря, вам нужно использовать рефлексию на Brigadier и удалить узлы дерева. После этого вам необходимо снова отправить дерево команд каждому игроку с помощью `sendCommands(ServerPlayer)`.

Если вы не отправите новое дерево команд, клиент может всё ещё думать, что команда существует, хотя сервер и провалит её выполнение.

:::

<!---->
