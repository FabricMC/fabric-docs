---
title: Запуск игры в VS Code
description: Узнайте, как запустить лаунчер Minecraft из Visual Studio Code.
authors:
  - dicedpixels
prev:
  text: Открытие проекта в VS Code
  link: ./opening-a-project
next:
  text: Генерация исходных текстов в VS Code
  link: ./generating-sources
---

Инструментарий Fabric интегрируется с Visual Studio Code и предоставляет удобный способ запуска экземпляра игры для тестирования и отладки вашего мода.

## Генерация целей запуска {#generating-launch-targets}

Чтобы запустить игру с включенной поддержкой отладки, вам нужно сгенерировать цели запуска, выполнив задачу `vscode` Gradle.

Это можно сделать из Gradle View внутри Visual Studio Code: откройте его и перейдите к задаче `vscode` в разделе **Задачи** > **`ide`**. Дважды щелкните или воспользуйтесь кнопкой **Запустить задачу**, чтобы выполнить задание.

![vscode Задача в представлении Gradle](/assets/develop/getting-started/vscode/gradle-vscode.png)

Также вы можете использовать терминал напрямую: откройте новый терминал через **Терминал** > **Новый терминал** и запустите его:

```sh:no-line-numbers
./gradlew vscode
```

![vscode Задание в терминале](/assets/develop/getting-started/vscode/terminal-vscode.png)

### Использование целей запуска {#using-launch-targets}

После того как цели запуска сгенерированы, их можно использовать, открыв представление **Запуск и отладка**, выбрав нужную цель и нажав кнопку **Начать отладку** (<kbd>F5</kbd>).

![Цели запуска](/assets/develop/getting-started/vscode/launch-targets.png)
