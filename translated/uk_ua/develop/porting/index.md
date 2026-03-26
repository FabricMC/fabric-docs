---
title: Портування до 1.21.11
description: Посібник з портування до 1.21.11, останньої версії Minecraft.
authors:
  - cassiancc
resources:
  https://fabricmc.net/2025/12/05/12111.html: Анонс Fabric API 1.21.11
  https://www.minecraft.net/en-us/article/minecraft-java-edition-1-21-11: Java Edition 1.21.11 — Minecraft.net
  https://minecraft.wiki/w/Java_Edition_1.21.11: Java Edition 1.21.11 — Вікі Minecraft
  https://www.youtube.com/watch?v=5yY25GoWQhs&pp=0gcJCSkKAYcqIYzv: Новини slicedlime про пакети ресурсів та даних для 1.21.11
  https://docs.neoforged.net/primer/docs/1.21.11/: Праймер міграції 1.21.10 -> 1.21.11 — NeoForge
---

Minecraft — це гра, яка постійно розвивається, нові версії змінюють гру таким чином, що впливає на розробників модів. Ця стаття охоплює загальні кроки, які можна виконати, щоб оновити свій мод до найновішої стабільної версії Minecraft.

::: info

Тут обговорюється міграція з **1.21.10** до **1.21.11**. Якщо ви шукаєте іншу міграцію, перейдіть до цільової версії за допомогою спадного меню у верхньому правому куті.

:::

## Оновлення скриптів збірки {#build-script}

Почніть з оновлення `gradle/wrapper/gradle-wrapper.properties`, `gradle.properties` і `build.gradle` вашого мода до останніх версій:

1. Оновіть Gradle до останньої версії, виконавши таку команду: `./gradlew wrapper --gradle-version latest`
2. Оновіть Minecraft, Завантажувач Fabric, Fabric Loom і Fabric API у `gradle.properties` (рекомендовано) або `build.gradle`. Знайдіть рекомендовані версії компонентів Fabric на [сайті розробки Fabric](https://fabricmc.net/develop/).
3. Оновіть Gradle за допомогою кнопки оновлення у верхньому правому куті IntelliJ IDEA. Якщо цю кнопку не видно, ви можете примусово очистити кеші, запустивши `./gradlew --refresh-dependencies`.

## Оновлення коду {#porting-guides}

Після оновлення сценарію збірки до версії 1.21.11 ви можете переглянути свій мод і оновити будь-який змінений код, щоб зробити його сумісним з новою версією.

Щоб допомогти вам з оновленням, розробники модів задокументують зміни, на які вони натрапили, у статтях, як-от у блозі Fabric та в праймерах портування NeoForge.

- [_Fabric для Minecraft 1.21.11_ у блозі Fabric](https://fabricmc.net/2025/12/05/12111.html) містить пояснення високого рівня змін, внесених у Fabric API у 1.21.11.
- [_Minecraft: Java Edition 1.21.11_ у блозі Minecraft](https://www.minecraft.net/en-us/article/minecraft-java-edition-1-21-11) є офіційним оглядом функцій, представлених у 1.21.11.
- [_Java Edition 1.21.11_ на Вікі Minecraft](https://minecraft.wiki/w/Java_Edition_1.21.11) — це неофіційний короткий виклад вмісту оновлення.
- [Новини пакетів даних і ресурсів від slicedlime у Minecraft 1.21.11](https://www.youtube.com/watch?v=5yY25GoWQhs&pp=0gcJCSkKAYcqIYzv) охоплює інформацію, пов’язану з оновленням даних вашого мода та вмісту пакета ресурсів.
- [Праймер NeoForge міграції мода _Minecraft 1.21.10 -> 1.21.11_](https://docs.neoforged.net/primer/docs/1.21.11/) охоплює перехід з 1.21.10 до 1.21.11, зосереджуючись лише на змінах коду.
