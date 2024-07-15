---
title: Fabric 和模組簡介
description: "Minecraft: Java Edition 中 Fabric 和模組的簡單介紹。"
authors:
  - IMB11
  - itsmiir
authors-nogithub:
  - basil4088
---

# Fabric和模組開發簡介 {#introduction-to-fabric-and-modding}

## 先決條件 {#prerequisites}

在開始學習之前，你應該對 Java 有基本的了解，並對物件導向程式設計 (OOP) 有所理解。

如果你不熟悉這些概念，你可能需要再開始開發模組前先了解一些關於 Java 和 OOP 的教學。以下是一些可以用來學習 Java 和 OOP 的資源:

- [W3: Java Tutorials](https://www.w3schools.com/java/)
- [Codecademy: Learn Java](https://www.codecademy.com/learn/learn-java)
- [W3: Java OOP](https://www.w3schools.com/java/java_oop.asp)
- [Medium: Introduction to OOP](https://medium.com/@Adekola_Olawale/beginners-guide-to-object-oriented-programming-a94601ea2fbd)

### 術語 {#terminology}

在開始前，先來看看使用 Fabric 開發模組時會遇到的一些術語:

- **Mod**: 對遊戲進行修改，增加或改變現有的功能。
- **Mod Loader**: 將模組載入遊戲的工具，例如 Fabric Loader。
- **Mixin**: 在運行時修改遊戲程式碼的工具 - 更多資信請參閱 [Mixin Introduction](https://fabricmc.net/wiki/tutorial:mixin_introduction)。
- **Gradle**: 用於構建和編譯模組的自動化構建工具，Fabric 用它來構建模組。
- **Mappings**: 將被混淆的程式碼轉化為人類可讀程式碼的映射集合。
- **Obfuscation**: 讓程式碼變得難以理解的過程，Mojang 用它來保護 Minecraft 的原始碼。
- **Remapping**: 將被混淆的程式碼映射成人類可讀程式碼的過程。

## 甚麼是 Fabric？ {#what-is-fabric}

Fabric 是用於 Minecraft: Java Edition 的輕量級模組開發工具鏈。

它被設計成一個簡單易用的模組開發平台。 Fabric 是由社群驅動的專案，而且是開源的，這代表任何人都可以為此專案做出貢獻。

你應該了解組成 Fabric 的四個主要部分：

- **Fabric Loader**: 一個靈活、跨平台的模組載入器，專為 Minecraft 和其他遊戲及應用程式設計。
- **Fabric Loom**: Gradle 插件，能讓開發者輕鬆的開發模組和進行除錯。
- **Fabric API**: 一套給模組開發者創建模組時使用的 API 和工具。
- **Yarn**: 一套開放的 Minecraft 映射表，在 Creative Commons Zero 授權條款下讓所有人使用。

## 為什麼開發 Minecraft 模組需要 Fabric ? {#why-is-fabric-necessary-to-mod-minecraft}

> Modding 是指修改遊戲以改變其行為或增加新功能的過程，以 Minecraft 來說，這可以是加入新物品、方塊或實體，改變遊戲機制或增加遊戲模式等。

Minecraft: Java Edition 被 Mojang 混淆，因此很難單獨修改。 不過，在模組開發工具 (例如 Fabric ) 的幫助下，修改變得容易許多。 有幾個映射系統可以協助進行這個過程。

Loom 使用這些映射將被混淆的程式碼重新映射成人類看得懂的格式，讓模組開發者更容易理解和修改遊戲程式碼。 在這部分， Yarn 是一個受歡迎且優秀的映射選擇，但也有其它的選擇。 每個映射表專案都有自己的優勢和專注的地方。

Loom 可以讓你輕鬆的開發和編譯模組，針對重新映射的程式碼進行操作，而 Fabric Loader 則能讓你將這些模組加載到遊戲中。

## Fabric API 提供哪些功能，為甚麼需要它? {#what-does-fabric-api-provide-and-why-is-it-needed}

> Fabric API 是一套給模組開發者在創建模組時使用的 API 和工具。

Fabric API 在 Minecraft 現有功能的基礎上提供了一系列使開發更方便的 API。例如，提供新的 Hook 和事件供開發者使用，或提供新的實用程式和工具讓修改遊戲內容變得更容易，例如訪問加寬器 (Access Wideners) 和訪問內部註冊表 (如可堆肥物品註冊表) 的能力。

雖然 Fabric API 提供了強大的功能，但有些任務，例如基本的方塊註冊表，可以在不使用 Fabric API 的情況下完成。
