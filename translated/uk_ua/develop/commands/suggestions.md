---
title: Пропозиції команд
description: Дізнайтеся як пропонувати користувачам можливі значення аргументу команди.
authors:
  - IMB11
---

Minecraft має потужну систему пропозицій команд, що використовується у багатьох місцях, таких як команда `/give`. Ця система дозволяє пропонувати гравцю значення аргументу, які вони можуть вибрати, що є гарним засобом створення більш зручних та ергономічних команд.

## Постачальники пропозицій {#suggestion-providers}

Клас `SuggestionProvider` використовується для створення списку пропозицій, які будуть надіслані гравцю. Постачальник пропозицій — це функція, що приймає об'єкти `CommandContext` та `SuggestionBuilder`, та повертає об'єкт `Suggestions`. `SuggestionProvider` повертає об'єкт `CompletableFuture` тому, що пропозиції можуть бути не доступні відразу.

## Використання постачальників пропозицій {#using-suggestion-providers}

Для використання постачальника пропозицій треба викликати метод `suggests` в об'єкта `ArgumentBuilder`. Цей метод приймає об'єкт `SuggestionProvider` та повертає свій `ArgumentBuilder` з доданим постачальником пропозицій.

@[code java highlight={4} transcludeWith=:::command_with_suggestions](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

@[code java transcludeWith=:::execute_command_with_suggestions](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

## Вбудовані постачальники пропозицій {#built-in-suggestion-providers}

Minecraft надає декілька вбудованих постачальників пропозицій:

| Постачальники пропозицій                  | Опис                                                             |
| ----------------------------------------- | ---------------------------------------------------------------- |
| `SuggestionProviders.SUMMONABLE_ENTITIES` | Пропонує всі сутності, що можуть бути викликані. |
| `SuggestionProviders.AVAILABLE_SOUNDS`    | Пропонує всі звуки, що можуть бути зіграні.      |
| `LootCommand.SUGGESTION_PROVIDER`         | Пропонує всі доступні таблиці здобичі.           |
| `SuggestionProviders.ALL_BIOMES`          | Пропонує всі доступні біоми.                     |

## Створити власний постачальник пропозицій {#creating-a-custom-suggestion-provider}

Якщо вбудовані постачальники пропозицій не підходять, ви можете створити свій постачальник. Для цього треба створити клас, який реалізує інтерфейс `SuggestionProvider` і перевизначає метод `getSuggestions`.

Для цього прикладу ми зробимо постачальник, який пропонує імена гравців на сервері.

@[code java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/command/PlayerSuggestionProvider.java)

Щоб скористатися цим постачальником, треба просто передати його об'єкт у метод `.suggests` у `ArgumentBuilder`.

@[code java highlight={4} transcludeWith=:::command_with_custom_suggestions](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

@[code java transcludeWith=:::execute_command_with_custom_suggestions](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

Звісно, що постачальники пропозицій можуть бути складнішими, оскільки вони також можуть зчитувати контекст команди та надавати пропозиції на основі її стану — наприклад, аргументи, що вже були надані.

Це може також зчитувати інвентар гравця та пропонувати предмети, або сутностей, які неподалік від гравця.
