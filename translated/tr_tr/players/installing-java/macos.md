---
title: MacOS'a Java yükleme
description: MacOS'a Java'yı nasıl kuracağınıza dair adım adım bir rehber.
authors:
  - dexman545
  - ezfe
next: false
---

Bu kılavuz, macOS'a Java 25'i yükleme sürecinde size yol gösterecektir.

Minecraft Başlatıcısı kendi Java kurulumuyla birlikte gelir, bu nedenle bu bölüm yalnızca Fabric `.jar` tabanlı yükleyiciyi veya Minecraft Sunucusu `.jar` dosyasını kullanmak istiyorsanız geçerlidir.

## 1. Java'nın Zaten Yüklü Olup Olmadığını Kontrol Et {#1-check-if-java-is-already-installed}

Terminal'de (`/Applications/Utilities/Terminal.app` konumunda) aşağıdakileri yazın ve <kbd>Enter</kbd> tuşuna basın:

```sh
$(/usr/libexec/java_home -v 25)/bin/java --version
```

Şuna benzer bir şey görmelisiniz:

```text:no-line-numbers
openjdk 25.0.2 2026-01-20 LTS
OpenJDK Runtime Environment Temurin-25.0.2+10 (build 25.0.2+10-LTS)
OpenJDK 64-Bit Server VM Temurin-25.0.2+10 (build 25.0.2+10-LTS, mixed mode, sharing)
```

Sürüm numarasına dikkat edin: yukarıdaki örnekte `25.0.9`'dur.

::: warning

Minecraft 26.1'i kullanmak için en az Java 25'in yüklü olması gerekir.

Bu komut 25'ten düşük bir sürüm gösteriyorsa, mevcut Java kurulumunuzu güncellemeniz gerekecektir; bu sayfayı okumaya devam edin.

:::

## 2. Java 25'i İndirmek ve Kurmak {#2-downloading-and-installing-java}

[Adoptium'un OpenJDK 25 sürümünü](https://adoptium.net/temurin/releases?version=25&os=mac&arch=any&mode=filter) kullanmanızı öneririz:

![Temurin Java İndirme Sayfası](/assets/players/installing-java/macos-download-java.png)

"25 - LTS" sürümünü seçtiğinizden ve `.PKG` yükleyici formatını tercih ettiğinizden emin olun.
Sisteminizin çipine bağlı olarak doğru mimariyi de seçmelisiniz:

- Eğer Apple M serisi bir çipiniz varsa, `aarch64`'ü (varsayılan) seçin
- Intel işlemciniz varsa, `x64` seçeneğini seçin
- Mac'inizde hangi çipin olduğunu öğrenmek için şu talimatları izleyin: [https://support.apple.com/en-us/116943](https://support.apple.com/en-us/116943)

`.pkg` kurulum dosyasını indirdikten sonra açın ve talimatları izleyin:

![Temurin Java Yükleyici](/assets/players/installing-java/macos-installer.png)

Kurulumu tamamlamak için yönetici parolanızı girmeniz gerekecek:

![macOS Parola Talimatı](/assets/players/installing-java/macos-password-prompt.png)

### Homebrew Kullanımı {#using-homebrew}

Eğer [Homebrew](https://brew.sh) zaten kuruluysa, Java 25'i `brew` kullanarak kurabilirsiniz:

```sh
brew install --cask temurin@25
```

## 3. Java 25'in Yüklü Olduğunu Doğrulayın {#3-verify-that-java-is-installed}

Kurulum tamamlandıktan sonra, Terminal'i tekrar açıp `$(/usr/libexec/java_home -v 25)/bin/java --version` yazarak Java 25'in etkin olup olmadığını doğrulayabilirsiniz.

Komut başarılı olursa, şöyle bir şey görmelisiniz:

```text:no-line-numbers
openjdk 25.0.2 2026-01-20 LTS
OpenJDK Runtime Environment Temurin-25.0.2+10 (build 25.0.2+10-LTS)
OpenJDK 64-Bit Server VM Temurin-25.0.2+10 (build 25.0.2+10-LTS, mixed mode, sharing)
```

Herhangi bir sorunla karşılaşırsanız, [Fabric Discord](https://discord.fabricmc.net/) adresindeki `#player-support` kanalından yardım isteyebilirsiniz.
