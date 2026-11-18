---
title: Міграція мапінгів за допомогою Ravel
description: Дізнайтеся, як мігрувати обфусковані мапінги вашого мода за допомогою Ravel.
authors:
  - cassiancc
  - deirn
---

[Ravel](https://github.com/badasintended/ravel) — це плаґін для IntelliJ IDEA для переналаштування вихідних файлів на основі [PSI IntelliJ](https://plugins.jetbrains.com/docs/intellij/psi.html) і [Mapping-IO](https://github.com/FabricMC/mapping-io). Він підтримує переналаштування Java, Kotlin, міксинів (написані на Java), Class Tweaker і розширювачі доступу.

Установіть його з [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/28938-ravel-remapper) або завантажте ZIP-файл з [випусків GitHub](https://github.com/badasintended/ravel/releases) і встановіть його, натиснувши значок шестірні в налаштуваннях плаґіна та натиснувши **встановити плаґін з носія**.

![Установлення плаґіна IDEA з носія(/assets/develop/misc/migrating-mappings/idea_local_plugin.png)

## Міграція мапінгів {#migrating-mappings}

::: warning

Унесіть будь-які зміни, перш ніж намагатися переналаштувати свої джерела! **Поки що не змінюйте свій `gradle.properties` або `build.gradle`!**

:::

Далі натисніть ПКМ по файлу, відкритий у редакторі, і виберіть **Refactor** > **Remap Using Ravel**\*

![ПКМ у меню](/assets/develop/misc/migrating-mappings/ravel_right_click.png)

Відкриється таке діалогове вікно. Ви також можете відкрити діалогове вікно, натиснувши **Refactor** у верхньому меню.

![Діалог Ravel](/assets/develop/misc/migrating-mappings/ravel_dialog.png)

Далі додайте мапінги, натиснувши значок +. Натисніть опцію завантаження, якщо у вас їх ще немає.

::: info

Якщо ви не бачите кнопку завантаження, оновіть Ravel до 0.3 або вище.

:::

- Для переходу від мапінги Yarn до Mojang спочатку додайте файл Yarn `mappings.tiny`, виберіть `named` як **source** простір імен і `official` як **destination** простір імен. Потім додайте файл Mojang `client.txt` і виберіть `target` як **source** простір імен і `source` як **destination** простір імен.
- Для переходу від мапінгів Mojang до Yarn спочатку додайте `client.txt` Mojang, цього разу вибравши `source` як **source** і `target` як **destination**. Потім додайте Yarn `mappings.tiny` і виберіть `official` як **source** і `named` як **destination**.

Потім виберіть модулі, які потрібно змінити, натиснувши значок + або значок ліворуч, щоб додати всі модулі.

Потім натисніть **OK** і дочекайтеся завершення ремапінгу.

### Оновлення Gradle {#updating-gradle}

Після завершення ремапування замініть свої мапінги в `build.gradle` вашого мода.

```groovy
dependencies {
    mappings "net.fabricmc:yarn:${project.yarn_mappings}:v2" // [!code --]
    mappings loom.officialMojangMappings() // [!code ++]
    // Or the reverse if you're migrating from Mojang Mappings to Yarn
}
```

Також оновіть свій `gradle.properties`, видаліть параметр `yarn_mappings` або оновіть його до того, який ви використовуєте.

```properties
yarn_mappings=1.21.11+build.3 # [!code --]
```

### Кінцеві зміни {#final-changes}

Ось і основна частина роботи виконана! Тепер ви захочете переглянути вихідний код, щоб перевірити потенційно застарілі цілі міксина або код, який не було переназначено.

Для проблем, які виявляє Ravel, ви можете виконати пошук (<kbd>Ctrl</kbd>+<kbd>Shift</kbd>+<kbd>F</kbd>) за запитом `TODO(Ravel)`.

![Пошук завдань Ravel](/assets/develop/misc/migrating-mappings/ravel_todo.png)

Такі інструменти, як [mappings.dev](https://mappings.dev/) або [Linkie](https://linkie.shedaniel.dev/mappings?namespace=yarn&translateMode=ns&translateAs=mojang_raw&search=&version=1.21.11), допоможуть ознайомитись із вашими новими мапінгами.
