---
title: Переход на версию 26.1
description: Рекомендации по переносу на Minecraft 26.1 - последнюю версию игры.
authors:
  - cassiancc
  - ChampionAsh5357
resources:
  https://fabricmc.net/2026/03/14/261.html: Fabric для Minecraft 26.1
  ./26.1/fabric-api: Руководство по портированию под Fabric API 26.1
  https://minecraft.wiki/w/Java_Edition_26.1: Java Edition 26.1 - Minecraft Wiki
  https://github.com/neoforged/.github/blob/main/primers/26.1/index.md: Основы миграции 1.21.11 -> 26.1 от ChampionAsh5357
---

Версия Minecraft 26.1 не обфусцирована, как и её предварительные версии. Учитывая это, вам нужно будет сделать больше изменений в ваших скриптах сборки, чем обычно, чтобы обновиться под неё.

::: info

В этих документах говорится о миграция с версии **1.21.11** на **26.1**. Если вы ищете другую миграцию, переключитесь на целевую версию с помощью выпадающего списка в правом верхнем углу.

:::

## Предварительные условия {#prerequisites}

Если ваш мод всё ещё использует маппинги Yarn от Fabric, сначала необходимо [перевести ваш мод на официальные маппинги Mojang](../../../develop/porting/mappings/) перед портированием до 26.1.

Если вы используете IntelliJ IDEA, вам также нужно будет обновить её до версии `2025.3` или выше для полной поддержки Java 25.

## Обновление скрипта сборки {#build-script}

Для начала обновите ваши `gradle/wrapper/gradle-wrapper.properties`, `gradle.properties` и `build.gradle` до новейших версий. Если у вас возникнут проблемы, рекомендуется обратиться к [Экземпляру Fabric мода](https://github.com/FabricMC/fabric-example-mod/tree/26.1).

1. Обновите Gradle до новейшей версии, выполнив следующую команду: `./gradlew wrapper --gradle-version latest`
2. Обновите Minecraft, Fabric Loader, Fabric Loom и Fabric API внутри `gradle.properties` (рекомендовано) или внутри `build.gradle`. Рекомендованные версии компонентов Fabric можно найти на [сайте разработки Fabric](https://fabricmc.net/develop/).
3. Вверху `build.gradle` измените используемую версию Loom с `id "fabric-loom"` на `id "net.fabricmc.fabric-loom"`. Если Loom указан в `settings.gradle`, замените его и там.
4. Уберите строку с `mappings` из секции зависимостей внутри `build.gradle`.
5. Замените любые экземпляры `modImplementation`, `modCompileOnly` или`modApi` на `implementation`, `compileOnly` и`api`.
6. Удалите или замените все моды под версии ниже 26.1 с версиями, совместимыми с этим обновлением.
   - Все моды под Minecraft 1.21.11 или ниже не будут работать на 26.1, даже как зависимости для компиляции.
7. При необходимости обновите заголовок вашего [расширителя доступа или модификатора классов](../class-tweakers/) заменив `named` на`official`.
8. Установить совместимость Java на 25, вместе 21.
9. Замените все упоминания `remapJar` на `jar`.
10. Обновить проект Gradle с помощью кнопки обновления в правом верхнем углу IntelliJ IDEA. Если эта кнопка не отображается, вы можете очистить кэш, выполнив `./gradlew --refresh-dependencies`.

## Обновление кода {#porting-guides}

После того, как вы обновили скрипт сборки до 26.1, вы можете пройтись по вашему моду и обновить изменённый код, чтобы сделать его совместимым со снапшотом.

- Fabric для Minecraft 26.1 на блоге Fabric'а](https://fabricmc.net/2026/03/14/261.html) содержит объяснения изменений высокого уровня, сделанных для Fabric API 26.1.
- [Руководство по портированию под Fabric API](./fabric-api) перечисляет переименования Fabric API в снапшотах 26.1 для соответствия названиям Mojang.
- [_Java Edition 26.1_ на Minecraft Wiki](https://minecraft.wiki/w/Java_Edition_26.1) — неофициальное краткое описание содержания обновления.
- [_ Основы миграции модов с Minecraft 1.21.11 до 26.1_ от NeoForge](https://github.com/neoforged/.github/blob/main/primers/26.1/index.md) описывает миграцию с 1.21.11 до 26.1, обращая внимание только на изменениях в ванильном коде.

<!---->
