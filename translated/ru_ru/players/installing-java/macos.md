---
title: Установка Java на macOS
description: Пошаговое руководство по установке Java на macOS.
authors:
  - dexman545
  - ezfe
next: false
---

Это руководство поможет вам установить Java 21 на macOS.

Minecraft Launcher поставляется с собственной установкой Java, поэтому этот раздел актуален только в том случае, если вы хотите использовать установщик Fabric на основе `.jar` или Minecraft Server `.jar`.

## 1. Проверьте, установлена ли Java {#1-check-if-java-is-already-installed}

В Терминале (расположенном в `/Applications/Utilities/Terminal.app`) введите следующее и нажмите <kbd>Enter</kbd>:

```sh
$(/usr/libexec/java_home -v 21)/bin/java --version
```

Вы должны увидеть что-то вроде следующего:

```text:no-line-numbers
openjdk 21.0.9 2025-10-21 LTS
OpenJDK Runtime Environment Temurin-21.0.9+10 (build 21.0.9+10-LTS)
OpenJDK 64-Bit Server VM Temurin-21.0.9+10 (build 21.0.9+10-LTS, mixed mode, sharing)
```

Обратите внимание на номер версии: в приведенном выше примере это `21.0.9`.

::: warning

Чтобы использовать Minecraft 1.21.11, вам понадобится как минимум Java 21.

Если эта команда отображает версию ниже 21, вам необходимо обновить существующую Java; продолжайте читать эту страницу.

:::

## 2. Загрузка и установка Java 21 {#2-downloading-and-installing-java}

Мы рекомендуем использовать [сборку OpenJDK 21 от Adoptium](https://adoptium.net/temurin/releases?version=21&os=mac&arch=any&mode=filter):

![Страница загрузки Java для Temurin](/assets/players/installing-java/macos-download-java.png)

Обязательно выберите версию «21 — LTS» и формат установщика `.PKG`.
Вы также должны выбрать правильную архитектуру в зависимости от чипа вашей системы:

- Если у вас чип Apple серии M, выберите `aarch64` (по умолчанию)
- Если у вас процессор Intel, выберите `x64`
- Следуйте этим [инструкциям, чтобы узнать, какой чип установлен в вашем Mac](https://support.apple.com/en-us/116943)

После загрузки установщика `.pkg` откройте его и следуйте инструкциям:

![Установщик Java от Temurin](/assets/players/installing-java/macos-installer.png)

Для завершения установки необходимо ввести пароль администратора:

![Запрос пароля macOS](/assets/players/installing-java/macos-password-prompt.png)

### Использование Homebrew {#using-homebrew}

Если у вас уже установлен [Homebrew](https://brew.sh), вы можете установить Java 21 с помощью `brew`:

```sh
brew install --cask temurin@21
```

## 3. Убедитесь, что Java 21 установлена {#3-verify-that-java-is-installed}

После завершения установки вы можете убедиться, что Java 21 активна, открыв терминал снова и введя `$(/usr/libexec/java_home -v 21)/bin/java --version`.

Если команда выполнена успешно, вы должны увидеть примерно следующее:

```text:no-line-numbers
openjdk 21.0.9 2025-10-21 LTS
OpenJDK Runtime Environment Temurin-21.0.9+10 (build 21.0.9+10-LTS)
OpenJDK 64-Bit Server VM Temurin-21.0.9+10 (build 21.0.9+10-LTS, mixed mode, sharing)
```

Если у вас возникнут проблемы, не стесняйтесь обращаться за помощью в [Fabric Discord](https://discord.fabricmc.net/) в канале `#player-support`.
