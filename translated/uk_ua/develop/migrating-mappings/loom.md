---
title: Міграція мапінгів за допомогою Loom
description: Дізнайтеся, як мігрувати обфусковані мапінги вашого мода за допомогою Loom.
authors:
  - asiekierka
  - cassiancc
  - florensie
  - Friendly-Banana
  - IMB11
  - jamierocks
  - JamiesWhiteShirt
  - MildestToucan
  - modmuss50
  - natanfudge
  - Spinoscythe
  - UpcraftLP
authors-nogithub:
  - basil4088
---

<!---->

::: warning

Для отримання найкращих результатів рекомендується оновити Loom до версії 1.13 або новішої, оскільки це дозволяє переносити міксини, розширювачі доступу і вихідні набори клієнта.

:::

## Міграція до мапінгів Mojang {#migrating-to-mojmap}

По-перше, вам потрібно запустити команду `migrateMappings`, яка перенесе ваші поточні мапінги в мапінги Mojang. Наприклад, така команда буде для міграції до мапінгів Mojang для 1.21.11:

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat migrateMappings --mappings "net.minecraft:mappings:1.21.11"
```

```sh:no-line-numbers [macOS/Linux]
./gradlew migrateMappings --mappings "net.minecraft:mappings:1.21.11"
```

```sh:no-line-numbers [IntelliJ]
migrateMappings --mappings "net.minecraft:mappings:1.21.11"
```

:::

Ви можете замінити `1.21.11` версією Minecraft, з якої ви переходите. Це має бути та сама версія Minecraft, яку ви зараз використовуєте. **Поки що не змінюйте свій `gradle.properties` або `build.gradle`!**

### Редагування ваших джерел {#editing-sources-mojmap}

Усталено, мігрований вихідний код з’явиться в `remappedSrc`, а не перезапише ваш наявний проєкт. Вам потрібно буде скопіювати джерела з `remappedSrc` до оригінальної теки. Про всяк випадок обов’язково зробіть резервну копію оригінальних джерел.

Якщо ви використовуєте Loom 1.13 або новішої версії, ви можете використати програмний аргумент `--overrideInputsIHaveABackup`, щоб безпосередньо замінити свої джерела.

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat migrateMappings --mappings "net.minecraft:mappings:1.21.11" --overrideInputsIHaveABackup
```

```sh:no-line-numbers [macOS/Linux]
./gradlew migrateMappings --mappings "net.minecraft:mappings:1.21.11" --overrideInputsIHaveABackup
```

```sh:no-line-numbers [IntelliJ]
migrateMappings --mappings "net.minecraft:mappings:1.21.11" --overrideInputsIHaveABackup
```

:::

### Оновлення Gradle {#updating-gradle-mojmap}

Якщо ви використовуєте Yarn, тепер ви можете замінити свої мапінги в розділі залежностей `build.gradle` на мапінги Mojang.

```groovy
dependencies {
    [...]
    mappings "net.fabricmc:yarn:${project.yarn_mappings}:v2" // [!code --]
    mappings loom.officialMojangMappings() // [!code ++]
}
```

Тепер ви можете оновити проєкт Gradle в IDE, щоб застосувати зміни.

### Кінцеві зміни {#final-changes-mojmap}

Ось і основна частина роботи виконана! Тепер ви захочете переглянути вихідний код, щоб перевірити потенційно застарілі цілі міксина або код, який не було переназначено.

Такі інструменти, як [mappings.dev](https://mappings.dev/) або [Linkie](https://linkie.shedaniel.dev/mappings?namespace=yarn&translateMode=ns&translateAs=mojang_raw&search=), допоможуть ознайомитись із вашими новими мапінгами.

## Міграція до Yarn {#migrating-to-yarn}

::: warning

1.21.11 — це останній випуск, у якому будуть доступні мапінги Yarn. Якщо ви плануєте оновити свій мод до версії 26.1 або новішої, він має бути на мапінгах Mojang.

:::

По-перше, вам потрібно запустити команду `migrateMappings`, яка перетворить ваші поточні мапінги на мапінги Yarn. Це можна знайти на [сайті розробки](https://fabricmc.net/develop) у розділі «Міграція мапінгів». Наприклад:

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat migrateMappings --mappings "1.21.11+build.3"
```

```sh:no-line-numbers [macOS/Linux]
./gradlew migrateMappings --mappings "1.21.11+build.3"
```

```sh:no-line-numbers [IntelliJ]
migrateMappings --mappings "1.21.11+build.3"
```

:::

Ви можете замінити `1.21.11` версією Minecraft, з якої ви переходите. Це має бути та сама версія Minecraft, яку ви зараз використовуєте. **Поки що не змінюйте свій `gradle.properties` або `build.gradle`!**

### Редагування ваших джерел {#editing-sources-yarn}

Усталено, мігрований вихідний код з’явиться в `remappedSrc`, а не перезапише ваш наявний проєкт. Вам потрібно буде скопіювати джерела з `remappedSrc` до оригінальної теки. Про всяк випадок обов’язково зробіть резервну копію оригінальних джерел.

Якщо ви використовуєте Loom 1.13 або новішої версії, ви можете використати програмний аргумент `--overrideInputsIHaveABackup`, щоб безпосередньо замінити свої джерела.

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat migrateMappings --mappings "1.21.11+build.3" --overrideInputsIHaveABackup
```

```sh:no-line-numbers [macOS/Linux]
./gradlew migrateMappings --mappings "1.21.11+build.3" --overrideInputsIHaveABackup
```

```sh:no-line-numbers [IntelliJ]
migrateMappings --mappings "1.21.11+build.3" --overrideInputsIHaveABackup
```

:::

### Оновлення Gradle {#updating-gradle-yarn}

Якщо ви переходите з мапінгів Mojang, тепер ви можете замінити свої мапінги в розділі залежностей `build.gradle` на мапінги Yarn. Обов’язково також оновіть свій файл gradle.properties версією Yarn, указаною на [сайті розробки](https://fabricmc.net/develop).

**`gradle.properties`**

```properties
yarn_mappings=1.21.11+build.3
```

**`build.gradle`**

```groovy
dependencies {
    [...]
    mappings loom.officialMojangMappings() // [!code --]
    mappings "net.fabricmc:yarn:${project.yarn_mappings}:v2" // [!code ++]
}
```

Тепер ви можете оновити проєкт Gradle в IDE, щоб застосувати зміни.

### Кінцеві зміни {#final-changes-yarn}

Ось і основна частина роботи виконана! Тепер ви захочете переглянути вихідний код, щоб перевірити потенційно застарілі цілі міксина або код, який не було переназначено.

Такі інструменти, як [mappings.dev](https://mappings.dev/) або [Linkie](https://linkie.shedaniel.dev/mappings?namespace=mojang_raw&translateMode=ns&translateAs=yarn&search=), допоможуть ознайомитись із вашими новими мапінгами.

## Додаткові налаштування {#additional-configurations}

### Перенесення розділених джерел {#migrating-split-sources}

У Loom 1.13 додано нове завдання `migrateClientMappings`, яке можна використовувати для міграції вашого клієнтського набору джерел до ваших нових мапінгів. Наприклад, щоб перейти до мапінгів Mojang:

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat migrateClientMappings --mappings "net.minecraft:mappings:1.21.11"
```

```sh:no-line-numbers [macOS/Linux]
./gradlew migrateClientMappings --mappings "net.minecraft:mappings:1.21.11"
```

```sh:no-line-numbers [IntelliJ]
migrateClientMappings --mappings "net.minecraft:mappings:1.21.11"
```

:::

Якщо ви використовуєте старішу версію Loom, див. [інші налаштування](#other-configurations).

### Перенесення розширювачів доступу {#migrating-access-wideners}

У Loom 1.13 додано нове завдання `migrateClassTweakerMappings`, яке можна використовувати для перенесення розширювачів доступу до ваших нових мапінгів. Наприклад, щоб перейти до мапінгів Mojang:

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat migrateClassTweakerMappings --mappings "net.minecraft:mappings:1.21.11"
```

```sh:no-line-numbers [macOS/Linux]
./gradlew migrateClassTweakerMappings --mappings "net.minecraft:mappings:1.21.11"
```

```sh:no-line-numbers [IntelliJ]
migrateClassTweakerMappings --mappings "net.minecraft:mappings:1.21.11"
```

:::

### Інші налаштування {#other-configurations}

- Укажіть, звідки взяти вихідні файли Java, за допомогою `--input path/to/source`. Усталено: `src/main/java`. Ви можете використати це для міграції набору вихідних кодів клієнта, передавши `--input src/client/java`.
- Укажіть, куди виводити переназначене джерело за допомогою `--output path/to/output`. Усталено: `remappedSrc`. Ви можете використати `src/main/java` тут, щоб перезаписати наявні джерела, але переконайтеся, що у вас є резервна копія.
- Укажіть спеціальне місце для отримання мапінгів за допомогою `--mappings some_group:some_artifact:some_version:some_qualifier`. Усталено: `net.fabricmc:yarn:<version-you-inputted>:v2`. Використовуйте `net.minecraft:mappings:<minecraft-version>`, щоб перейти на офіційні мапінги Mojang.

Наприклад, щоб перенести набір джерел клієнта на мапінги Mojang на місці (перезаписуючи наявні джерела):

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat migrateMappings --input "src/client/java" --output "src/client/java" --mappings "net.minecraft:mappings:1.21.11"
```

```sh:no-line-numbers [macOS/Linux]
./gradlew migrateMappings --input "src/client/java" --output "src/client/java" --mappings "net.minecraft:mappings:1.21.11"
```

```sh:no-line-numbers [IntelliJ]
migrateMappings --input "src/client/java" --output "src/client/java" --mappings "net.minecraft:mappings:1.21.11"
```

:::

<!---->
