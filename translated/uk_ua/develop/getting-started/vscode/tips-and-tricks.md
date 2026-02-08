---
title: Поради та підказки VS Code
description: Корисні поради та підказки, які полегшать вашу роботу.
authors:
  - dicedpixels
prev:
  text: Генерація джерел у VS Code
  link: ./generating-sources
next: false
---

Важливо навчитися проходити джерела генерації, щоб ви могли налагодити та отримати розуміння внутрішньої роботи Minecraft. Тут ми описуємо деякі загальні використання IDE.

## Пошук класів Minecraft {#searching-for-a-minecraft-class}

Після генерації джерел. Ви повинні мати можливість шукати або переглядати класи Minecraft.

### Перегляд визначень класів {#viewing-class-definitions}

**Швидке відкриття** (<kbd>Ctrl</kbd>+<kbd>P</kbd>): введіть `#`, а потім назву класу (наприклад, `#Identifier`).

![Швидке відриття](/assets/develop/getting-started/vscode/quick-open.png)

**Перейти до визначення** (<kbd>F12</kbd>): у вихідному коді перейдіть до визначення класу, натиснувши <kbd>Ctrl</kbd> і натиснувши його назву, або натиснувши по ньому ПКМ та вибравши «Перейти до визначення».

![Перейти до визначення](/assets/develop/getting-started/vscode/go-to-definition.png)

### Пошук посилань {#finding-references}

Ви можете знайти всі використання класу, Н\натиснувши ПКМ назву класу та вибравши **Знайти всі посилання**.

![Знайти всі посилання](/assets/develop/getting-started/vscode/find-all-references.png)

::: info

Якщо наведені вище функції не працюють належним чином, ймовірно, джерела не приєднані належним чином. Зазвичай це можна виправити, очистивши кеш робочої області.

- Натисніть кнопку **Показати меню стану Java** у рядку стану.

![Показати стан Java](/assets/develop/getting-started/vscode/java-ready.png)

- У щойно відкритому меню натисніть **Очистити кеш робочої області…** і підтвердьте операцію.

![Очистити робочій простір](/assets/develop/getting-started/vscode/clear-workspace.png)

- Закрийте та заново відкрийте проєкт.

:::

## Перегляд байт-коду {#viewing-bytecode}

Перегляд байт-коду необхідний під час написання міксинів. Однак Visual Studio Code не має вбудованої підтримки для перегляду байт-коду, і кілька розширень, які його додають, можуть не працювати.

У такому випадку ви можете використовувати вбудований Javap `javap` для перегляду байт-коду.

- **Знайдіть шлях до Minecraft JAR:**

  Відкрийте вікно провідника, розгорніть розділ **Проєкти Java**. Розгорніть вузол **Довідкові бібліотеки** в дереві проєкту та знайдіть файл JAR із «minecraft-» у його імені. Натисніть ПКМ по JAR і скопіюйте повний шлях.

  Це може виглядати приблизно так:

  ```:no-line-numbers
  C:/project/.gradle/loom-cache/minecraftMaven/net/minecraft/minecraft-merged-503b555a3d/1.21.8-net.fabricmc.yarn.1_21_8.1.21.8+build.1-v2/minecraft-merged-503b555a3d-1.21.8-net.fabricmc.yarn.1_21_8.1.21.8+build.1-v2.jar
  ```

![Копіювати шлях](/assets/develop/getting-started/vscode/copy-path.png)

- **Запустіть `javap`:**

  Потім ви можете запустити `javap`, вказавши вищевказаний шлях як `cp` (шлях до класу) і повну назву класу як останній аргумент.

  ```sh
  javap -cp C:/project/.gradle/loom-cache/minecraftMaven/net/minecraft/minecraft-merged-503b555a3d/1.21.8-net.fabricmc.yarn.1_21_8.1.21.8+build.1-v2/minecraft-merged-503b555a3d-1.21.8-net.fabricmc.yarn.1_21_8.1.21.8+build.1-v2.jar -c -private net.minecraft.util.Identifier
  ```

  Це виведе байт-код у вивід вашого термінала.

![javap](/assets/develop/getting-started/vscode/javap.png)
