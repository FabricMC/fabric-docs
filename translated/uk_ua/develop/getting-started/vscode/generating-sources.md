---
title: Генерація джерел у VS Code
description: Посібник зі створення джерел Minecraft у Visual Studio Code.
authors:
  - dicedpixels
prev:
  text: Запуск гри в VS Code
  link: ./launching-the-game
next:
  text: Поради та підказки VS Code
  link: ./tips-and-tricks
---

Інструментарій Fabric дає вам доступ до вихідного коду Minecraft, генеруючи його локально, і ви можете використовувати Visual Studio Code для зручної навігації по ньому. Щоб згенерувати джерела, потрібно запустити завдання Gradle `genSources`.

Це можна зробити з Gradle View, як описано вище, запустивши завдання `genSources` у **Tasks** > **`fabric`**:
![Завдання genSources в Gradle View](/assets/develop/getting-started/vscode/gradle-gensources.png)

Або ви також можете запустити команду з термінала:

```sh:no-line-numbers
./gradlew genSources
```

![Завдання genSources в терміналі](/assets/develop/getting-started/vscode/terminal-gensources.png)
