---
title: Генерація джерел в IntelliJ IDEA
description: Посібник зі створення джерел Minecraft в IntelliJ IDEA.
authors:
  - dicedpixels
prev:
  text: Запуск гри в IntelliJ IDEA
  link: ./launching-the-game
next:
  text: Поради та підказки IntelliJ IDEA
  link: ./tips-and-tricks
---

Інструментарій Fabric дає вам доступ до вихідного коду Minecraft, генеруючи його локально, і ви можете використовувати IntelliJ IDEA для зручної навігації по ньому. Щоб згенерувати джерела, потрібно запустити завдання Gradle `genSources`.

Це можна зробити з Gradle View, як описано вище, запустивши завдання `genSources` у **Tasks** > **`fabric`**:
![Завдання genSources в Gradle View](/assets/develop/getting-started/vscode/gradle-gensources.png)

Або ви також можете запустити команду з термінала:

```sh:no-line-numbers
./gradlew genSources
```

![Завдання genSources в терміналі](/assets/develop/getting-started/intellij/terminal-gensources.png)

## Додавання джерел {#attaching-sources}

Для IntelliJ потрібен один додатковий крок — приєднання згенерованих джерел до проєкту.

Для цього відкрийте будь-який клас Minecraft. Ви можете <kbd>Ctrl</kbd> + натиснути, щоб перейти до визначення, яке відкриває клас, або скористайтеся функцією «Шукати всюди», щоб відкрити клас.

Відкриймо `MinecraftServer.class` як приклад. Тепер ви повинні побачити синій банер угорі з посиланням «**Вибрати джерела…**».

![Вибір джерел](/assets/develop/getting-started/intellij/choose-sources.png)

Натисніть «**Вибрати джерел…**», щоб відкрити діалогове вікно вибору файлів. Усталено це діалогове вікно відкриється у правильному місці згенерованих джерел.

Виберіть файл, який закінчується на **`-sources`**, і натисніть **Відкрити**, щоб підтвердити вибір.

![Діалогове вікно вибору джерел](/assets/develop/getting-started/intellij/choose-sources-dialog.png)

Тепер ви повинні мати можливість шукати посилання. Якщо ви використовуєте набір мапінгів, який містить Javadocs, як-от [Parchment](https://parchmentmc.org/) (для мапінгів Mojang) або Yarn, тепер ви також повинні бачити Javadoc.

![Коментарі Javadoc у джерелах](/assets/develop/getting-started/intellij/javadoc.png)
