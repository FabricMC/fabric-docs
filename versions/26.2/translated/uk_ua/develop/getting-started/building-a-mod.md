---
title: Збирання мода
description: Дізнайтеся, як зібрати мод для Minecraft, яким можна ділитися або тестувати у робочому середовищі.
authors:
  - cassiancc
  - cputnam-a11y
  - gdude2002
  - Scotsguy
---

Коли ваш мод буде готовий до тестування, ви зможете експортувати його у файл JAR, яким можна поділитися на вебсайтах хостингу модів або використовувати для тестування свого мода у виробництві разом з іншими модами.

## Виберіть ваш IDE {#choose-your-ide}

<ChoiceComponent :choices="[
{
 name: 'IntelliJ IDEA',
 href: './intellij-idea/building-a-mod',
 icon: 'simple-icons:intellijidea',
 color: '#FE2857',
},
{
 name: 'Visual Studio Code',
 icon: 'codicon:vscode',
 color: '#007ACC',
},
]" />

## Збирання в терміналі {#terminal}

::: warning

Використання термінала для збирання мода, а не IDE, може спричинити проблеми, якщо стандартна інсталяція Java не відповідає вимогам проєкту. Для більш надійних збірок розгляньте можливість використання IDE, яка дозволяє легко вказати правильну версію Java.

:::

Відкрийте термінал із того самого каталогу, що й каталог проєкту мода, і виконайте таку команду:

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat build
```

```sh:no-line-numbers [macOS/Linux]
./gradlew build
```

:::

Файли JAR мають з’явитися в теці `build/libs` вашого проєкту. Використовуйте файл JAR із найкоротшою назвою поза розробкою.

## Установлення та розповсюдження {#installing-and-sharing}

Звідти мод можна [встановити як зазвичай](../../players/installing-mods) або завантажити на надійні сайти розміщення модів, такі як [CurseForge](https://www.curseforge.com/minecraft) і [Modrinth](https://modrinth.com/discover/mods).
