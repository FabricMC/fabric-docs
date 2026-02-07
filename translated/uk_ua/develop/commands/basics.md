---
title: Створення команд
description: Створюйте команди зі складними аргументами та діями.
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
---

Створення команд може дозволити розробнику мода додати функціональні можливості, які можна використовувати за допомогою команди. Ця стаття навчить вас реєструвати команди та загальну командну структуру Brigadier.

::: info

Brigadier — це аналізатор і диспетчер команд, написаний Mojang для Minecraft. Це деревоподібна бібліотека команд, де ви створюєте дерево команд і аргументів.

Brigadier має відкритий вихідний код: <https://github.com/Mojang/brigadier>

:::

## Інтерфейс `Command` {#the-command-interface}

`com.mojang.brigadier.Command` — це функціональний інтерфейс, який запускає певний код і створює `CommandSyntaxException` у певних випадках. Він має загальний тип `S`, який визначає тип _джерела команд_.
Джерело команди надає певний контекст, у якому була виконана команда. У Minecraft джерелом команди зазвичай є `CommandSourceStack`, яке може представляти сервер, командний блок, віддалене з’єднання (RCON), гравець або сутність.

Єдиний метод у `Command`, `run(CommandContext<S>)` приймає `CommandContext<S>` як єдиний параметр і повертає ціле число. Контекст команди містить джерело вашої команди `S` і дозволяє отримати аргументи, подивіться на розбір командні ноди та перегляньте вхідні дані, які використовуються в цій команді.

Як і інші функціональні інтерфейси, він зазвичай використовується як лямбда або посилання на метод:

```java
Command<CommandSourceStack> command = context -> {
    return 0;
};
```

Ціле число можна вважати результатом виконання команди. Зазвичай значення, менше або дорівнює нулю, означають, що команда не вдалася і нічого не буде робити. Позитивні значення означають, що команда була успішною та щось зробила. Brigadier надає константу для вказівки
успішність; `Команда#SINGLE_SUCCESS`.

### Що може зробити `CommandSourceStack`? {#what-can-the-servercommandsource-do}

`CommandSourceStack` надає деякий додатковий специфічний контекст під час виконання команди. Це охоплює в собі можливість отримати сутність, яка виконала команду, світ, у якому була виконана команда, або сервер, на якому була виконана команда.

Ви можете отримати доступ до джерела команд із контексту команди, викликавши `getSource()` в екземплярі `CommandContext`.

```java
Command<CommandSourceStack> command = context -> {
    CommandSourceStack source = context.getSource();
    return 0;
};
```

## Реєстрація звичайних команд {#registering-a-basic-command}

Команди реєструються в `CommandRegistrationCallback`, наданому Fabric API.

::: info

Щоб отримати інформацію про реєстрацію зворотних викликів, перегляньте посібник [подій](../events).

:::

Подія повинна бути зареєстрована у вашому [ініціалізаторі мода](../getting-started/project-structure#entrypoints).

Зворотний виклик має три параметри:

- `CommandDispatcher<CommandSourceStack> диспетчер` — використовується для реєстрації, аналізу та виконання команд. `S` — це тип джерела команди, який підтримує диспетчер команд.
- `CommandBuildContext registryAccess` — надає абстракцію до реєстрів, які можна передати певній команді
- `Commands.CommandSelection environment` — визначає тип сервера, на якому реєструються команди.

В ініціалізаторі мода ми просто реєструємо просту команду:

@[code lang=java transcludeWith=:::test_command](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

У методі `sendSuccess()` першим параметром є текст, який потрібно надіслати, тобто `Supplier<Component>`, щоб уникнути створення екземплярів об’єктів `Component`, коли вони не потрібні.

Другий параметр визначає, чи транслювати зворотний зв'язок іншим
модератори. Загалом, якщо команда має запитувати щось, фактично не впливаючи на світ, наприклад поточний час або рахунок якогось гравця, має бути `false`. Якщо команда щось виконує, наприклад змінює час або зміна чийогось рахунку, має бути `true`.

Якщо команда не виконується, замість виклику `sendSuccess()`, ви можете безпосередньо викликати будь-який виняток, і сервер або клієнт обробить його належним чином.

`CommandSyntaxException` зазвичай викидається, щоб вказати на синтаксичні помилки в командах або аргументах. Ви також можете реалізувати ваш власний виняток.

Щоб виконати цю команду, ви повинні ввести `/test_command`, яка чутлива до реєстру.

::: info

З цього моменту ми будемо видобувати логіку, написану в лямбда-виразі, переданому в конструктори `.executes()` в окремі методи. Потім ми можемо передати посилання на метод до `.executes()`. Це зроблено для наочності.

:::

### Середовище реєстрації {#registration-environment}

Якщо потрібно, ви також можете переконатися, що команда зареєстрована лише за певних обставин, наприклад, лише в виділене середовище:

@[code lang=java highlight={2} transcludeWith=:::dedicated_command](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

@[code lang=java transcludeWith=:::execute_dedicated_command](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

### Вимоги до команд {#command-requirements}

Припустімо, у вас є команда, яку ви хочете, щоб її могли виконувати лише модератори. Тут знаходиться метод `requires()` вступає в гру. Метод `requires()` має один аргумент `Predicate<S>`, який забезпечить `CommandSourceStack` для перевірки та визначення, чи може `CommandSource` виконати команду.

@[code lang=java highlight={3} transcludeWith=:::required_command](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

@[code lang=java transcludeWith=:::execute_required_command](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

Ця команда виконуватиметься, лише якщо джерелом команди є принаймні модератор, включаючи командні блоки. Інакше команда не реєструється.

Побічним ефектом цього є те, що ця команда не показуватиметься у <kbd>Tab</kbd> під час завершення нікому, хто не є модератором. Ось чому ви не можете <kbd>Tab</kbd>-завершити більшість команд, якщо ви не ввімкнули команди.

### Підкоманди {#sub-commands}

Щоб додати підкоманду, ви зазвичай реєструєте перший вузол літералу команди. Щоб мати підкоманду, вам потрібно додати наступний вузол літералу до наявного нода.

@[code lang=java highlight={3} transcludeWith=:::sub_command_one](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

@[code lang=java transcludeWith=:::execute_sub_command_one](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

Подібно до аргументів, ноди підкоманд також можна встановити необов’язковими. У наступному випадку обидва `/command_two` і `/command_two sub_command_two` буде дійсним.

@[code lang=java highlight={2,8} transcludeWith=:::sub_command_two](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

@[code lang=java transcludeWith=:::execute_command_sub_command_two](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

## Клієнтські команди {#client-commands}

Fabric API містить ClientCommandManager у пакеті net.fabricmc.fabric.api.client.command.v2, який можна використовувати для реєстрації команд на стороні клієнта. Код має існувати лише в коді на стороні клієнта.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/client/command/ExampleModClientCommands.java)

## Перенаправлення команд {#command-redirects}

Переспрямування команд — також відоме як псевдоніми — це спосіб переспрямувати функціональність однієї команди на іншу. Це корисно, коли ви хочете змінити назву команди, але все одно бажаєте підтримувати стару назву.

::: warning

Brigadier [перенаправлятиме лише командні вузли з аргументами](https://github.com/Mojang/brigadier/issues/46). Якщо ви хочете перенаправити командний вузол без аргументів, надайте конструктор `.executes()` із посиланням на ту саму логіку, що описана в прикладі.

:::

@[code lang=java transcludeWith=:::redirect_command](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

@[code lang=java transcludeWith=:::execute_redirected_by](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

## Часті питання {#faq}

### Чому мій код не компілюється? {#why-does-my-code-not-compile}

- Перехопити або викинути `CommandSyntaxException` - `CommandSyntaxException` не є `RuntimeException`. Якщо кинути, це повинно бути в методах, які викидають `CommandSyntaxException` в сигнатурах методу, або його слід перехопити.
  Brigadier обробить перевірені винятки та перешле вам відповідне повідомлення про помилку в грі.

- Проблеми з генериками. Час від часу у вас можуть виникати проблеми з генериками. Якщо ви реєструєте команди сервера (а це найчастіше), переконайтеся, що ви використовуєте `Commands.literal` або `Commands.argument` замість `LiteralArgumentBuilder.literal` або `RequiredArgumentBuilder.argument`.

- Перевірте метод `sendSuccess()` — можливо, ви забули вказати логічне значення як другий аргумент. Також пам’ятайте, що, починаючи з Minecraft 1.20, першим параметром є `Supplier<Component>` замість `Component`.

- Команда має повертати ціле число. Під час реєстрації команд метод `executes()` приймає об’єкт `Command`, який зазвичай є лямбда. Лямбда має повертати ціле число замість інших типів.

### Чи можу я зареєструвати команди під час виконання? {#can-i-register-commands-at-runtime}

::: warning

Ви можете це зробити, але не рекомендується. Ви отримаєте `Команди` від сервера та додасте будь-які потрібні команди до його `CommandDispatcher`.

Після цього потрібно знову відправити дерево команд кожному гравцеві
за допомогою `Commands.sendCommands(ServerPlayer)`.

Це потрібно, оскільки клієнт локально кешує дерево команд, яке він отримує під час входу в систему (або коли надсилаються пакети модератора) для локальних повідомлень про помилки, повних завершень.

:::

### Чи можу я скасувати реєстрацію команд під час виконання? {#can-i-unregister-commands-at-runtime}

::: warning

Ви також можете це зробити, однак це набагато менш стабільно, ніж реєстрація команд під час виконання, і може спричинити небажані побічні ефекти.

Щоб все було просто, вам потрібно використовувати показ на Brigadier і видалити ноди. Після цього вам потрібно надіслати
дерево команд кожному гравцеві за допомогою `sendCommands(ServerPlayer)`.

Якщо ви не надішлете оновлене дерево команд, клієнт може вважати, що команда все ще існує, навіть якщо сервер не зможе її виконати.

:::

<!---->
