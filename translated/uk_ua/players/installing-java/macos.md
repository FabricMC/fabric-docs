---
title: Установлення Java на macOS
description: Покрокова інструкція щодо встановлення Java на macOS.
authors:
  - dexman545
  - ezfe
next: false
---

Цей посібник допоможе вам установити Java 21 на macOS.

Minecraft Launcher постачається з власною інсталяцією Java, тому цей розділ актуальний, лише якщо ви хочете використовувати встановлювач на основі Fabric `.jar` або якщо ви хочете використовувати сервер Minecraft `.jar`.

## 1. Перевірте, чи Java уже встановлено {#1-check-if-java-is-already-installed}

У терміналі (розташованому в `/Applications/Utilities/Terminal.app`) введіть наступне та натисніть <kbd>Enter</kbd>:

```sh
$(/usr/libexec/java_home -v 21)/bin/java --version
```

Ви повинні побачити щось на зразок наступного:

```text:no-line-numbers
openjdk 21.0.9 2025-10-21 LTS
OpenJDK Runtime Environment Temurin-21.0.9+10 (build 21.0.9+10-LTS)
OpenJDK 64-Bit Server VM Temurin-21.0.9+10 (build 21.0.9+10-LTS, mixed mode, sharing)
```

Зверніть увагу на номер версії: у наведеному вище прикладі це `21.0.9`.

::: warning

Щоб використовувати Minecraft 1.21.11, вам потрібно встановити принаймні Java 21.

Якщо ця команда показує будь-яку версію, нижчу за 21, вам потрібно буде оновити наявну інсталяцію Java; продовжуйте читати цю сторінку.

:::

## 2. Завантажте встановлювач Java 21 {#2-downloading-and-installing-java}

Ми рекомендуємо використовувати [збірку OpenJDK 21 від Adoptium](https://adoptium.net/temurin/releases?version=21&os=mac&arch=any&mode=filter):

![Сторінка завантаження Temurin Java](/assets/players/installing-java/macos-download-java.png)

Переконайтеся, що вибрано версію «21 — LTS» і виберіть формат установлювача `.PKG`.
Ви також повинні вибрати правильну архітектуру залежно від архітектури вашої системи:

- Якщо у вас чип Apple серії M, виберіть `aarch64` (усталений)
- Якщо у вас чип Intel, виберіть `x64`
- Виконайте ці [інструкції, щоб дізнатися, який чип встановлено у вашому Mac](https://support.apple.com/uk-ua/116943)

Після завантаження встановлювача `.pkg` відкрийте його та дотримуйтеся вказівок:

![Установлювач Temurin Java](/assets/players/installing-java/macos-installer.png)

Вам потрібно буде ввести пароль адміністратора, щоб завершити встановлення:

![Запит пароля macOS](/assets/players/installing-java/macos-password-prompt.png)

### Використання Homebrew {#using-homebrew}

Якщо у вас уже встановлено [Homebrew](https://brew.sh), ви можете встановити Java 21 за допомогою `brew`:

```sh
brew install --cask temurin@21
```

## 3. Перевірте, чи встановлено Java 21 {#3-verify-that-java-is-installed}

Після завершення встановлення ви можете переконатися, що Java 21 активна, знову відкривши термінал і ввівши `$(/usr/libexec/java_home -v 21)/bin/java --version`.

Якщо команда виконана успішно, ви повинні побачити щось на зразок цього:

```text:no-line-numbers
openjdk 21.0.9 2025-10-21 LTS
OpenJDK Runtime Environment Temurin-21.0.9+10 (build 21.0.9+10-LTS)
OpenJDK 64-Bit Server VM Temurin-21.0.9+10 (build 21.0.9+10-LTS, mixed mode, sharing)
```

Якщо у вас виникнуть проблеми, не соромтеся звертатися по допомогу до [Fabric Discord](https://discord.fabricmc.net/) на каналі `#player-support`.
