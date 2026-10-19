---
title: Портування до 26.2
description: Посібник з портування до 26.2, останньої версії Minecraft.
authors:
  - cassiancc
  - ChampionAsh5357
resources:
  https://fabricmc.net/2026/06/15/262.html: Fabric для Minecraft 26.2
  https://minecraft.wiki/w/Java_Edition_26.2: Java Edition 26.2 — Вікі Minecraft
  https://docs.neoforged.net/primer/docs/26.2/: Праймери міграції 26.1 -> 26.2 від ChampionAsh5357
---

Minecraft — це гра, яка постійно розвивається, нові версії змінюють гру таким чином, що впливає на розробників модів. Ця стаття охоплює загальні кроки, які можна виконати, щоб оновити свій мод до найновішої стабільної версії Minecraft.

::: info

Тут обговорюється міграція з **26.1** до **26.2**. Якщо ви шукаєте іншу міграцію, перейдіть до цільової версії за допомогою спадного меню у верхньому правому куті.

:::

## Оновлення скриптів збірки {#build-script}

Почніть з оновлення `gradle/wrapper/gradle-wrapper.properties`, `gradle.properties` і `build.gradle` вашого мода до останніх версій. Якщо у вас виникли проблеми, подумайте про [приклад мода Fabric](https://github.com/FabricMC/fabric-example-mod/tree/26.2).

1. Оновіть Gradle до останньої версії, виконавши таку команду: `./gradlew wrapper --gradle-version latest`
2. Оновіть Minecraft, Завантажувач Fabric, Fabric Loom і Fabric API у `gradle.properties` (рекомендовано) або `build.gradle`. Знайдіть рекомендовані версії компонентів Fabric на [сайті розробки Fabric](https://fabricmc.net/develop/).
3. Оновіть Gradle за допомогою кнопки оновлення у верхньому правому куті IntelliJ IDEA. Якщо цю кнопку не видно, ви можете примусово очистити кеші, запустивши `./gradlew --refresh-dependencies`.

## Оновлення коду {#porting-guides}

Після оновлення сценарію збірки до версії 26.2 ви можете переглянути свій мод і оновити будь-який змінений код, щоб зробити його сумісним зі знімком.

- [Fabric для Minecraft 26.2 у блозі Fabric](https://fabricmc.net/2026/06/15/262.html) містить пояснення високого рівня змін, внесених у Fabric API у 26.2.
- [_Minecraft: Java Edition 26.2_ у блозі Minecraft](https://www.minecraft.net/en-us/article/minecraft-java-edition-26-2) є офіційним оглядом функцій, представлених у 26.2.
- [_Java Edition 26.2_ на Вікі Minecraft](https://minecraft.wiki/w/Java_Edition_26.2) — це неофіційний короткий виклад вмісту оновлення.
- [Праймер NeoForge міграції мода Minecraft із версії 26.1 -> 26.2](https://docs.neoforged.net/primer/docs/26.2/) охоплює процес переходу з версії 26.1 на 26.2, зосереджуючись виключно на змінах у стандартному коді.

<!---->
