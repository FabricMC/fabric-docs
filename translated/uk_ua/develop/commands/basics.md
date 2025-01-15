---
title: Створення команд
description: Створюйте команди зі складними аргументами та діями.
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

# Створення команд {#creating-commands}

Створення команд може дозволити розробнику мода додати функціональні можливості, які можна використовувати за допомогою команди. Ця стаття
навчить вас реєструвати команди та загальну командну структуру Brigadier.

::: info
Brigadier is a command parser and dispatcher written by Mojang for Minecraft. It is a tree-based command library where
you build a tree of commands and arguments.

Brigadier має відкритий вихідний код: <0>
:::

## Інтерфейс `Command` {#the-command-interface}

`com.mojang.brigadier.Command` — це функціональний інтерфейс, який запускає певний код і створює
`CommandSyntaxException` у певних випадках. Він має загальний тип `S`, який визначає тип _джерела команд_.
Команда
джерело надає певний контекст, у якому була виконана команда. У Minecraft джерелом команди зазвичай є a
`ServerCommandSource`, який може представляти сервер, командний блок, віддалене з’єднання (RCON), гравця або сутність.

Єдиний метод у `Command`, `run(CommandContext<ServerCommandSource>)` приймає `CommandContext<0>` як єдиний параметр і повертає ціле число. Контекст команди містить джерело вашої команди `S` і дозволяє отримати аргументи, подивіться на розбір
командні вузли та перегляньте вхідні дані, які використовуються в цій команді.

Як і інші функціональні інтерфейси, він зазвичай використовується як лямбда або посилання на метод:

```java
Command<0> command = context -> {
    return 0;
};
```

Ціле число можна вважати результатом виконання команди. Зазвичай значення, менше або дорівнює нулю, означають, що команда не вдалася і буде
нічого не робити. Позитивні значення означають, що команда була успішною та щось зробила. Brigadier надає константу для вказівки
успішність; `Команда#SINGLE_SUCCESS`.

### Що може робити `ServerCommandSource`? {#what-can-the-servercommandsource-do}

`ServerCommandSource` надає деякий додатковий специфічний контекст під час виконання команди. Це охоплює в собі
можливість отримати сутність, яка виконала команду, світ, у якому була виконана команда, або сервер, на якому була виконана команда.

Ви можете отримати доступ до джерела команд із контексту команди, викликавши `getSource()` в екземплярі `CommandContext`.

```java
Command<0> command = context -> {
    ServerCommandSource source = context.getSource();
    return 0;
};
```

## Реєстрація звичайних команд {#registering-a-basic-command}

Команди реєструються в `CommandRegistrationCallback`, наданому Fabric API.

:::info
Щоб отримати інформацію про реєстрацію зворотних викликів, перегляньте посібник [Події](../events).
:::

Подія має бути зареєстрована у вашому [ініціалізаторі мода](./getting-started/project-structure#entrypoints).

Зворотний виклик має три параметри:

- `CommandDispatcher<0> dispatcher` - використовується для реєстрації, аналізу та виконання команд. `S` - це тип
  джерела команди, яке підтримує диспетчер команд.
- `CommandRegistryAccess registryAccess` - надає абстракцію до реєстрів, які можна передати певній команді
  методи аргументу
- `CommandManager.RegistrationEnvironment environment` - визначає тип сервера, на якому реєструються команди
  на.

В ініціалізаторі мода ми просто реєструємо просту команду:

@[code lang=java transcludeWith=:::test_command](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

У методі `sendFeedback()` першим параметром є текст для надсилання, який є `Supplier<Text>`, щоб уникнути
створення екземплярів текстових об’єктів, коли вони не потрібні.

Другий параметр визначає, чи транслювати зворотний зв'язок іншим
операторам. Загалом, якщо команда має запитувати щось, фактично не впливаючи на світ, наприклад
поточний час або рахунок якогось гравця, має бути `false`. Якщо команда щось виконує, наприклад змінює
час або зміна чийогось рахунку, має бути `true`.

Якщо команда не виконується, замість виклику `sendFeedback()` ви можете безпосередньо викликати будь-який виняток і сервер або клієнт
впорається з цим належним чином.

`CommandSyntaxException` зазвичай викидається, щоб вказати на синтаксичні помилки в командах або аргументах. Ви також можете реалізувати
ваш власний виняток.

Щоб виконати цю команду, ви повинні ввести `/test_command`, яка чутлива до регістру.

:::info
З цього моменту ми будемо видобувати логіку, написану в лямбда-виразі, переданому в конструктори `.execute()` в окремі методи. Потім ми можемо передати посилання на метод до `.execute()`. Це зроблено для наочності.
:::

### Середовище реєстрації {#registration-environment}

Якщо потрібно, ви також можете переконатися, що команда зареєстрована лише за певних обставин, наприклад, лише в
виділене середовище:

@[code lang=java highlight={2} transcludeWith=:::dedicated_command](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)
@[code lang=java transcludeWith=:::execute_dedicated_command](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

### Вимоги до команд {#command-requirements}

Нехай, у вас є команда, і ви хочете, щоб її могли виконувати лише оператори. Тут знаходиться метод `requires()`
вступає в гру. Метод `requires()` має один аргумент `Predicate<S>`, який забезпечить `ServerCommandSource`
щоб перевірити та визначити, чи може `CommandSource` виконати команду.

@[code lang=java highlight={3} transcludeWith=:::required_command](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)
@[code lang=java transcludeWith=:::execute_required_command](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Ця команда виконуватиметься, лише якщо команду використав оператор принаймні рівня 2, включаючи командні
блоки. Інакше команда не реєструється.

Побічним ефектом цього є те, що ця команда не показується у tab під час завершення нікому, хто не є оператором рівня 2. Ось
чому ви не можете завершити більшість команд, якщо ви не ввімкнули команди.

### Підкоманди {#sub-commands}

Щоб додати підкоманду, ви зазвичай реєструєте перший вузол літералу команди. Щоб мати підкоманду, вам потрібно додати наступний вузол літералу до існуючого вузла.

@[code lang=java highlight={3} transcludeWith=:::sub_command_one](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)
@[code lang=java transcludeWith=:::execute_sub_command_one](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Подібно до аргументів, вузли підкоманд також можна встановити необов’язковими. У наступному випадку обидва `/command_two`
і `/command_two sub_command_two` буде дійсним.

@[code lang=java highlight={2,8} transcludeWith=:::sub_command_two](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)
@[code lang=java transcludeWith=:::execute_command_sub_command_two](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

## Клієнтські команди {#client-commands}

Fabric API містить ClientCommandManager у пакеті net.fabricmc.fabric.api.client.command.v2, який можна використовувати для реєстрації команд на стороні клієнта. Код має існувати лише в коді на стороні клієнта.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/client/command/FabricDocsReferenceClientCommands.java)

## Перенаправлення команд {#command-redirects}

Переспрямування команд — також відоме як псевдоніми — це спосіб переспрямувати функціональність однієї команди на іншу. Це корисно, коли ви хочете змінити назву команди, але все одно бажаєте підтримувати стару назву.

:::warning
Brigadier [перенаправлятиме лише командні вузли з аргументами](https://github.com/Mojang/brigadier/issues/46). Якщо ви хочете перенаправити командний вузол без аргументів, надайте конструктор `.executes()` із посиланням на ту саму логіку, що описана в прикладі.
:::

@[code lang=java transcludeWith=:::redirect_command](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)
@[code lang=java transcludeWith=:::execute_redirected_by](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

## Часті питання {#faq}

### Чому мій код не компілюється? {#why-does-my-code-not-compile}

- Перехопити або викинути `CommandSyntaxException` - `CommandSyntaxException` не є `RuntimeException`. Якщо кинути,
  це повинно бути в методах, які викидають `CommandSyntaxException` в сигнатурах методу, або його слід перехопити.
  Brigadier обробить перевірені винятки та перешле вам відповідне повідомлення про помилку в грі.

- Проблеми з генериками. Час від часу у вас можуть виникати проблеми з генериками. Якщо ви реєструєте серверні
  команди (які в більшості випадків), переконайтеся, що ви використовуєте `CommandManager.literal`
  або `CommandManager.argument` замість `LiteralArgumentBuilder.literal` або `RequiredArgumentBuilder.argument`.

- Перевірте метод sendFeedback() – можливо, ви забули вказати логічне значення як другий аргумент. Також запам'ятайте
  що, починаючи з Minecraft 1.20, першим параметром є `Supplier<0>` замість `Text`.

- Команда має повертати ціле число. Під час реєстрації команд метод `executes()` приймає об’єкт `Command`,
  який зазвичай є лямбда. Лямбда має повертати ціле число замість інших типів.

### Чи можу я зареєструвати команди під час виконання? {#can-i-register-commands-at-runtime}

::: warning
You can do this, but it is not recommended. You would get the `CommandManager` from the server and add anything commands
you wish to its `CommandDispatcher`.

Після цього потрібно знову відправити дерево команд кожному гравцеві
за допомогою `CommandManager.sendCommandTree(ServerPlayerEntity)`.

Це потрібно, оскільки клієнт локально кешує дерево команд, яке отримує під час входу в систему (або коли пакети оператора
надсилаються) для локальних повідомлень про помилки, повних завершень.
:::

### Чи можу я скасувати реєстрацію команд під час виконання? {#can-i-unregister-commands-at-runtime}

::: warning
You can also do this, however, it is much less stable than registering commands at runtime and could cause unwanted side
effects.

Щоб все було просто, вам потрібно використовувати показ на Brigadier і видалити вузли. Після цього вам потрібно надіслати
дерево команд кожному гравцеві за допомогою `sendCommandTree(ServerPlayerEntity)`.

Якщо ви не надішлете оновлене дерево команд, клієнт може вважати, що команда все ще існує, навіть якщо сервер
не зможе її виконати.
:::
