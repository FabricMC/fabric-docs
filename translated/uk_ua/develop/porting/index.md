---
title: Портування до 26.1
description: Посібник з портування до 26.1, останньої версії Minecraft.
authors:
  - cassiancc
  - ChampionAsh5357
resources:
  https://fabricmc.net/2026/03/14/261.html: Fabric для Minecraft 26.1
  ./26.1/fabric-api: Посібник з портування Fabric API 26.1
  https://minecraft.wiki/w/Java_Edition_26.1: Java Edition 26.1 — Вікі Minecraft
  https://github.com/neoforged/.github/blob/main/primers/26.1/index.md: Праймери міграції 1.21.11 -> 26.1 від ChampionAsh5357
---

Версія Minecraft 26.1 не обфускована, як і її знімки. Зважаючи на це, вам потрібно буде внести більше змін у свої сценарії збірки, ніж зазвичай, щоб перейти на нього.

::: info

Тут обговорюється міграція з **1.21.11** до **26.1**. Якщо ви шукаєте іншу міграцію, перейдіть до цільової версії за допомогою спадного меню у верхньому правому куті.

:::

## Передумови {#prerequisites}

Якщо ваш мод досі використовує мапінги Yarn Fabric, вам спочатку потрібно буде [перенести свій мод до офіційних мапінгів Mojang](../../../develop/porting/mappings/) перед портуванням на 26.1.

Якщо ви використовуєте IntelliJ IDEA, вам також потрібно оновити його до `2025.3` або новішої версії для повної підтримки Java 25.

## Оновлення скриптів збірки {#build-script}

Почніть з оновлення `gradle/wrapper/gradle-wrapper.properties`, `gradle.properties` і `build.gradle` вашого мода до останніх версій, потім виконайте наведені нижче дії. Якщо у вас виникли проблеми, подумайте про [приклад мода Fabric](https://github.com/FabricMC/fabric-example-mod/tree/26.1).

1. Оновіть Gradle до останньої версії, виконавши таку команду: `./gradlew wrapper --gradle-version latest`
2. Оновіть Minecraft, Завантажувач Fabric, Fabric Loom і Fabric API у `gradle.properties` (рекомендовано) або `build.gradle`. Знайдіть рекомендовані версії компонентів Fabric на [сайті розробки Fabric](https://fabricmc.net/develop/).
3. У верхній частині `build.gradle` змініть версію Loom, яку ви використовуєте, з `id "fabric-loom"` на `id "net.fabricmc.fabric-loom"`. Якщо ви вказали Loom у `settings.gradle`, змініть його також там.
4. Видаліть рядок `mappings` із розділу залежностей `build.gradle`.
5. Замініть будь-які випадки `modImplementation` або `modCompileOnly` на `implementation` і `compileOnly`.
6. Видаліть або замініть будь-які моди, створені для версій до 26.1, на версії, сумісні з цим оновленням.
   - Жодні наявні моди для 1.21.11 або старіших версій Minecraft не працюватимуть 26.1, навіть як залежність лише від компіляції.
7. За потреби оновіть заголовок вашого [розширювача доступу або твікера класу](../class-tweakers/), щоб замінити `named` на `official`.
8. Установіть сумісність з Java на 25 замість 21.
9. Замініть усі згадки `remapJar` на `jar`.
10. Оновіть Gradle за допомогою кнопки оновлення у верхньому правому куті IntelliJ IDEA. Якщо цю кнопку не видно, ви можете примусово очистити кеші, запустивши `./gradlew --refresh-dependencies`.

## Оновлення коду {#porting-guides}

Після оновлення сценарію збірки до версії 26.1 ви можете переглянути свій мод і оновити будь-який змінений код, щоб зробити його сумісним зі знімком.

- [Fabric для Minecraft 26.1 у блозі Fabric](https://fabricmc.net/2026/03/14/261.html) містить пояснення високого рівня змін, внесених у Fabric API у 26.1.
- [Посібник з портування Fabric API 26.1](./fabric-api) містить список перейменувань, зроблених у Fabric API у знімках 26.1, щоб відповідати іменам Mojang.
- [_Java Edition 26.1_ на Вікі Minecraft](https://minecraft.wiki/w/Java_Edition_26.1) — це неофіційний короткий виклад вмісту оновлення.
- [Праймер NeoForge міграції мода Minecraft 1.21.11 -> 26.1](https://github.com/neoforged/.github/blob/main/primers/26.1/index.md) охоплює перехід з 1.21.11 на 26.1, зосереджуючись лише на змінах коду.

<!---->
