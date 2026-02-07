---
title: Запуск гри в VS Code
description: Дізнайтеся, як запустити екземпляр Minecraft із Visual Studio Code.
authors:
  - dicedpixels
prev:
  text: Відкриття проєкту у VS Code
  link: ./opening-a-project
next:
  text: Генерація джерел у VS Code
  link: ./generating-sources
---

Інструментарій Fabric інтегрується з Visual Studio Code, щоб забезпечити зручний спосіб запуску екземпляра гри для тестування та налагодження вашого мода.

## Генерація цілей запуску {#generating-launch-targets}

Щоб запустити гру з увімкненою підтримкою налагодження, вам потрібно буде створити цілі запуску, запустивши завдання Gradle `vscode`.

Це можна зробити з Gradle View у Visual Studio Code: відкрийте його та перейдіть до завдання `vscode` у **Завдання** > **`ide`**. Двічі натисніть або скористайтеся кнопкою **Виконати завдання**, щоб виконати завдання.

![Завдання vscode у Gradle View](/assets/develop/getting-started/vscode/gradle-vscode.png)

Крім того, ви можете використовувати термінал безпосередньо: відкрийте новий термінал через **Термінал** > **Новий термінал** і запустіть:

```sh:no-line-numbers
./gradlew vscode
```

![Завдання vscode в терміналіl](/assets/develop/getting-started/vscode/terminal-vscode.png)

### Використання цілей запуску {#using-launch-targets}

Після генерації цілей запуску ви можете використовувати їх, відкривши вікно **Запуск і налагодження**, вибравши потрібну ціль і натиснувши кнопку **Почати налагодження** (<kbd>F5</kbd>).

![Цілі запуску](/assets/develop/getting-started/vscode/launch-targets.png)
