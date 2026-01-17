---
title: Генерация исходных текстов в VS Code
description: Руководство по созданию исходников Minecraft в Visual Studio Code.
authors:
  - dicedpixels
prev:
  text: Запуск игры в VS Code
  link: ./launching-the-game
next:
  text: Советы и рекомендации по работе с VS Code
  link: ./tips-and-tricks
---

Набор инструментов Fabric позволяет получить доступ к исходному коду Minecraft, сгенерировав его локально, а для удобной навигации по нему можно использовать Visual Studio Code. Чтобы сгенерировать исходные тексты, необходимо запустить задачу Gradle `genSources`.

Это можно сделать из Gradle View, как показано выше, запустив задачу `genSources` в **Задачи** > **`fabric`**:
![Задача genSources в представлении Gradle](/assets/develop/getting-started/vscode/gradle-gensources.png)

Также вы можете выполнить команду из терминала:

```sh:no-line-numbers
./gradlew genSources
```

![Задача genSources в терминале](/assets/develop/getting-started/vscode/terminal-gensources.png)
