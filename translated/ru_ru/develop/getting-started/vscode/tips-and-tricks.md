---
title: Советы и рекомендации по работе с VS Code
description: Полезные советы и рекомендации, которые облегчат вашу работу.
authors:
  - dicedpixels
prev:
  text: Генерация исходных текстов в VS Code
  link: ./generating-sources
next: false
---

Важно научиться работать с генерируемыми исходниками, чтобы отладить и понять внутреннюю работу Minecraft. Здесь мы расскажем о некоторых распространенных вариантах использования IDE.

## Поиск класса Minecraft {#searching-for-a-minecraft-class}

После создания источников. вы должны иметь возможность искать или просматривать классы Minecraft.

### Просмотр определений классов {#viewing-class-definitions}

**Быстрое открытие** (<kbd>Ctrl</kbd>+<kbd>P</kbd>): Введите `#`, за которым следует имя класса (например, `#Identifier`).

![Быстрое открытие](/assets/develop/getting-started/vscode/quick-open.png)

**Перейти к определению** (<kbd>F12</kbd>): В исходном коде перейдите к определению класса, нажав <kbd>Ctrl</kbd> + щелкнув на его имени, или щелкнув ПКМ и выбрав "Go to Definition".

![Перейти к определению](/assets/develop/getting-started/vscode/go-to-definition.png)

### Поиск ссылок {#finding-references}

Вы можете найти все случаи использования класса, щелкнув ПКМ на имени класса и нажав **Find All References**.

![Найти все ссылки](/assets/develop/getting-started/vscode/find-all-references.png)

:::info
Если вышеуказанные функции не работают так, как ожидалось, скорее всего, источники подключены неправильно. Обычно это можно исправить, очистив кэш рабочего пространства.

- Нажмите кнопку **Show Java Status Menu** в строке состояния.

![Показать состояние Java](/assets/develop/getting-started/vscode/java-ready.png)

- В открывшемся меню нажмите **Clean Workspace Cache...** и подтвердите операцию.

![Освободить рабочее пространство](/assets/develop/getting-started/vscode/clear-workspace.png)

- Закройте и снова откройте проект.

:::

## Просмотр байткода {#viewing-bytecode}

Просмотр байткода необходим при написании миксинов. Однако в Visual Studio Code отсутствует встроенная поддержка просмотра байткода, и те немногие расширения, которые ее добавляют, могут не работать.

В этом случае для просмотра байткода можно использовать встроенную в Java программу `javap`.

- **Найдите путь к Minecraft JAR:**

  Откройте вид Проводника, разверните раздел **Java Projects**. Раскройте узел **Reference Libraries** в дереве проекта и найдите JAR с `minecraft-` в имени. Щелкните ПКМ на JAR и скопируйте полный путь.

  Это может выглядеть примерно так:

  ```:no-line-numbers
  C:/project/.gradle/loom-cache/minecraftMaven/net/minecraft/minecraft-merged-503b555a3d/1.21.8-net.fabricmc.yarn.1_21_8.1.21.8+build.1-v2/minecraft-merged-503b555a3d-1.21.8-net.fabricmc.yarn.1_21_8.1.21.8+build.1-v2.jar
  ```

![Путь копирования](/assets/develop/getting-started/vscode/copy-path.png)

- **Запустить `javap`:**

  Затем вы можете запустить `javap`, указав указанный выше путь в качестве `cp` (путь к классу) и полное имя класса в качестве последнего аргумента.

  ```sh
  javap -cp C:/project/.gradle/loom-cache/minecraftMaven/net/minecraft/minecraft-merged-503b555a3d/1.21.8-net.fabricmc.yarn.1_21_8.1.21.8+build.1-v2/minecraft-merged-503b555a3d-1.21.8-net.fabricmc.yarn.1_21_8.1.21.8+build.1-v2.jar -c -private net.minecraft.util.Identifier
  ```

  Это выведет байткод в вывод терминала.

![javap](/assets/develop/getting-started/vscode/javap.png)
