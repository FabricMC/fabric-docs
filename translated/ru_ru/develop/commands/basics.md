---
title: Создание команд
description: Создавайте команды со сложными аргументами и действиями.
authors:
  - dicedpixels
  - i509VCB
  - pyrofab
  - natanfudge
  - Juuxel
  - solidblock
  - modmuss50
  - technici4n
  - atakku
  - haykam
  - mschae23
  - treeways
  - xpple
---

# Создание команд

Создание команд позволяет разработчику мода добавлять функционал, который может быть использован при вызове команд. Это руководство научит вас регистрировать команды и общую структуру команд Brigadier.

::: info
Brigadier is a command parser and dispatcher written by Mojang for Minecraft. It is a tree-based command library where
you build a tree of commands and arguments.

Исходный код библиотеки Brigadier: https://github.com/Mojang/brigadier
:::

## Интерфейс `Command` {#the-command-interface}

`com.mojang.brigadier.Command` это функциональный интерфейс, который запускает конкретный код, и исключает `CommandSyntaxException` в определённых случаях. Он имеет общий тип `S`, который определяет тип _источник команды_.
Источник команды предоставляет контекст, в котором была запущена команда. В Майнкрафт, источником команды является `ServerCommandSource` который может представлять сервер, командному блоку, удалённому соединению(RCON), игроку или сущности.

Единственный метод в `Command`, это `run(CommandContext<S>)` он берёт `CommandContext<S>` в качестве единственного аргумента и возвращает целое число. Командный контекст содержит источник вашей команды как `S` и позволяет получить аргументы, посмотрите на разобранные командные ноды и увидите вводные данные, используемые в этой команде.

Как и другие функциональные интерфейсы, этот используется постоянно как лямбда или ссылка на метод:

```java
Command<ServerCommandSource> command = context -> {
    return 0;
};
```

Целое число может быть результатом команды. Обычно значения меньше или равные нулю означают, что команда не выполнена и ничего не сделает. Позитивные значения означают, что команда успешно выполнилась и что-то выполнила. Brigadier предоставляет константу для обозначения успеха; `Command#SINGLE_SUCCESS`.

### Что может делать \`ServerCommandSource?

`ServerCommandSource` предоставляет дополнительный контекст когда команда выполняется. Это добавляет возможность получить сущность которая выполнила команду, мир в котором команда выполнилась команда или сервер на котором запустилась команда.

Вы можете получить командный источник из командного контекста при вызове `getSource()` в экземпляре `CommandContext`.

```java
Command<ServerCommandSource> command = context -> {
    ServerCommandSource source = context.getSource();
    return 0;
};
```

## Регистрация основной команды {#registering-a-basic-command}

Команды регистрируются в `CommandRegistrationCallback`, предоставляемый Fabric API.

:::info
Сведения о регистрации обратных вызовов смотрите в [События](../events).
:::

Событие следует регистрировать в инициализаторе вашего мода.

Обратный вызов имеет три аргумента:

- `CommandDispatcher<ServerCommandSource> dispatcher` - используется для регистрации, парсинга и выполнения команд. `S` - это тип источника команд, который поддерживает диспатчер команд.
- `CommandRegistryAccess  registryAccess` - предоставляет абстракцию   которую можно передать определённой команде   методы аргументов
- `CommandManager.RegistrationEnvironment environment` - Определяет тип сервера,   на котором регистрируются команды.

В инициализации мода, мы просто регистрируем простую команду:

@[code lang=java transcludeWith=:::_1](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

В методе`sendFeedback()`, первый аргумент это текст для отправки, который является `Supplier<Text>`, чтобы избежать создание экземпляров текстовых объектов когда они не нужны.

Второй аргумент определяет, следует ли транслировать обратную связь с другими операторами. Обычно, если команда предназначена для запроса чего-либо без затрагивания мира, например запросить текущее время или счёт игрока, оно должно быть `false`. Если команда делает что-либо, например изменение времени или изменение чего-либо счёт, оно должно быть `true`.

Если команда не выполняется, вместо вызова `sendFeedback()`, вы напрямую можете выбросить любое исключение и сервер или клиент справится с этим соответствующим образом.

`CommandSyntaxException` обычно выбрасывается для обозначения синтаксических ошибок в команде или в аргументах. Вы можете так же имплементировать своё исключение.

Чтобы выполнить эту команды, вы должны ввести `/foo` с учётом регистра.

### Регистрационная среда {#registration-environment}

При желании вы так же можете сделать так, чтобы когда команда регистрировалась с определёнными условиями, например только в выделенной среде:

@[code lang=java highlight={2} transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

### Требования к команде {#command-requirements}

Допустим, что у вас есть команда и вы хотите чтобы её могли выполнять только операторы. Здесь метод `requires()` вступает в игру. Метод `requires()` имеет один аргумент `Predicate<S>` который будет предоставлять `ServerCommandSource` для проверки возможности `CommandSource` выполнять команду.

@[code lang=java highlight={3} transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Команда выполнится только, если источник команды имеет второй уровень минимально, включая команду блоков. Иначе, команда не зарегистрируется.

Побочным эффектом этого является то, что эта команда не показывается при завершение всем игрокам кто не имеет второй уровень оператора. Это также объясняет почему вы не можете выполнить большинство команда, когда не включены читы.

### Подкоманды {#sub-commands}

Чтобы добавить подкоманду, вы должны зарегистрировать первый литеральный нод команды. Чтобы иметь подкоманду, вы должны добавить следующий литеральный нод к существующему ноду.

@[code lang=java highlight={3} transcludeWith=:::7](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Подобно аргументам, ноды подкоманд могут также быть опциональными. В следующем случае оба `/subtater` и `/subtater subcommand` будут правильными.

@[code lang=java highlight={2,8} transcludeWith=:::8](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

## Клиентские команды {#client-commands}

Fabric API имеет `ClientCommandManager` в пакете`net.fabricmc.fabric.api.client.command.v2` который можно использовать для регистрации команд на клиентской стороне. Код должен существовать только в коде клиентской стороны.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/client/command/FabricDocsReferenceClientCommands.java)

## Перенаправление команд {#command-redirects}

Перенаправление команд - также известное как псевдонимы - это способ перенаправить функционал одной команды к другой. Это полезно, если вы хотите изменить название команды, но всё равно хотите поддерживать старое название.

@[code lang=java transcludeWith=:::12](@/reference/latest/src/client/java/com/example/docs/client/command/FabricDocsReferenceClientCommands.java)

## FAQ {#faq}

### Почему мой код не компилируется? {#why-does-my-code-not-compile}

- Словите или выбросите `CommandSyntaxException` - `CommandSyntaxException`- это не `RuntimeException`. Если вы выбросите это,   то оно должно быть в методах, которые выбрасывают `CommandSyntaxException` в сигнатурном методе, или его нужно будет поймать.
  Brigadier обработает проверенные исключение и отправит вам сообщение об ошибке в игре.

- Время от времени у вас могут возникать проблемы с джинериками. Если вы регистрируете серверные  команды  (в большинстве случаев), убедитесь, что вы используете `CommandManager.literal`  или `CommandManager.argument` вместо `LiteralArgumentBuilder.literal` или `RequiredArgumentBuilder.argument`.

- Проверьте метод `sendFeedback()` - Возможно вы забыли указать логическое значение как второй аргумент. Также помните  что, начиная с версии Майнкрафта 1.20, первым аргументом должен быть `Supplier<Text>` вместо `Text`.

- Команда должна возвращать целое число - При регистрации команд, метод `executes()` принимает  объект `Command`,  обычно это лямбда. Лямбда должна возвращать только целое число.

### Могу я зарегистрировать команды во время выполнения? {#can-i-register-commands-at-runtime}

::: warning
You can do this, but it is not recommended. You would get the `CommandManager` from the server and add anything commands
you wish to its `CommandDispatcher`.

После этого вам нужно опять отправить команду tree ко всем игрокам используя `CommandManager.sendCommandTree(ServerPlayerEntity)`.

Это необходимо, поскольку клиент локально кэширует команду tree, которую он получает во время логина (или когда пакеты оператора отправлены) для локальных сообщений об ошибках с большим дополнением.
:::

### Могу я отменить регистрацию команд во время выполнения? {#can-i-unregister-commands-at-runtime}

::: warning
You can also do this, however, it is much less stable than registering commands at runtime and could cause unwanted side
effects.

Для простоты, вы должны использовать отражение Brigadier и удалить ноды. После этого, вам нужно отправить команду tree каждому игроку заново используя `sendCommandTree(ServerPlayerEntity)`.

Если вы не будете отправлять обновления команде tree, клиент может всё ещё подумать, что команда существует, хотя сервер провалит его выполнение.
:::
