---
title: Генерация исходных текстов в IntelliJ IDEA
description: Руководство по генерации исходных текстов Minecraft в IntelliJ IDEA.
authors:
  - dicedpixels
prev:
  text: Запуск игры в IntelliJ IDEA
  link: ./launching-the-game
next:
  text: Сборка мода в IntelliJ IDEA
  link: ./building-a-mod
---

Набор инструментов Fabric позволяет получить доступ к исходному коду Minecraft, сгенерировав его локально, а для удобной навигации по нему можно использовать IntelliJ IDEA. Чтобы сгенерировать исходные тексты, необходимо запустить задачу Gradle `genSources`.

Это можно сделать через панель Gradle, запустив задачу `genSources` в **Tasks** > **`fabric`**:
![Задача genSources в панели Gradle](/assets/develop/getting-started/intellij/gradle-gensources.png)

Также вы можете выполнить команду из терминала:

```sh:no-line-numbers
./gradlew genSources
```

![Задача genSources в терминале](/assets/develop/getting-started/intellij/terminal-gensources.png)

## Прикрепление источников {#attaching-sources}

IntelliJ требует еще одного дополнительного шага - прикрепления сгенерированных исходных текстов к проекту.

Чтобы сделать это, откройте любой класс Minecraft. Вы можете <kbd>Ctrl</kbd> + Клик, чтобы перейти к определению, которое открывает класс, или использовать "Поиск везде", чтобы открыть класс.

Давайте откроем `MinecraftServer.class` в качестве примера. Теперь вверху должен появиться синий баннер со ссылкой "**Выберите источники...**".

![Выберите источники](/assets/develop/getting-started/intellij/choose-sources.png)

Нажмите на кнопку "**Выбрать источники...**", чтобы открыть диалог выбора файлов. По умолчанию этот диалог открывается в правильном месте расположения сгенерированных источников.

Выберите файл, который заканчивается на **`-sources`**, и нажмите **Открыть**, чтобы подтвердить выбор.

![Диалоговое окно выбора источников](/assets/develop/getting-started/intellij/choose-sources-dialog.png)

Теперь у вас должна быть возможность искать ссылки на использование. Если вы используете набор маппингов, содержащий Javadoc, например [Parchment](https://parchmentmc.org/) (для Mojang Mappings) или Yarn, теперь вы также будете видеть Javadoc.

![Комментарии Javadoc в исходных текстах](/assets/develop/getting-started/intellij/javadoc.png)
