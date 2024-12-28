---
title: Fabric 與模組簡介
description: Minecraft Java 版中 Fabric 和模組開發的簡要介紹。
authors:
  - IMB11
  - itsmiir
authors-nogithub:
  - basil4088
---

# Fabric 與模組開發簡介 {#introduction-to-fabric-and-modding}

## 先決條件 {#prerequisites}

在開始之前，您應該具備 Java 開發的基礎知識，以及對物件導向程式設計 (OOP) 的理解。

如果您不熟悉這些概念，建議您在開始模組開發之前，先參考一些關於 Java 和 OOP 的教學資源，以下是一些您可以用來學習 Java 和 OOP 的資源：

- [W3：Java 教學](https://www.w3schools.com/java/)
- [Codecademy：學習 Java](https://www.codecademy.com/learn/learn-java)
- [W3：Java OOP](https://www.w3schools.com/java/java_oop.asp)
- [Medium：OOP 簡介](https://medium.com/@Adekola_Olawale/beginners-guide-to-object-oriented-programming-a94601ea2fbd)

### 術語 {#terminology}

在我們開始之前，讓我們先來了解一些您在使用 Fabric 進行模組開發時會遇到的術語：

- **模組 (Mod)**：對遊戲的修改，可以加入新功能或變更現有功能。
- **模組載入器 (Mod Loader)**：將模組載入到遊戲中的工具，例如 Fabric Loader。
- **Mixin**：一種在執行時修改遊戲程式碼的工具。想要了解更多資訊，可以參考 [Mixin 簡介](https://fabricmc.net/wiki/tutorial:mixin_introduction)。
- **Gradle**：一種用於構建和編譯模組的構建自動化工具，Fabric 使用它來構建模組。
- **映射 (Mappings)**：一組將混淆程式碼映射到人類可讀程式碼的映射。
- **混淆 (Obfuscation)**：使程式碼難以理解的過程，Mojang 使用它來保護 Minecraft 的程式碼。
- **重新映射 (Remapping)**：將混淆程式碼映射到人類可讀程式碼的過程。

## 什麼是 Fabric？ {#what-is-fabric}

Fabric 是一個輕量級的 Minecraft Java 版模組開發工具鏈。

它被設計成一個簡單易用的模組開發平台。 Fabric 是一個社群驅動的專案，並且是開源的，這表示任何人都可以為該專案做出貢獻。

您應該了解 Fabric 的四個主要組成部分：

- **Fabric Loader**：一個靈活的、平台無關的模組載入器，專為 Minecraft 和其他遊戲和應用程式設計。
- **Fabric Loom**：一個 Gradle 插件，使開發人員能夠輕鬆地開發和除錯模組。
- **Fabric API**：一組供模組開發人員在建立模組時使用的 API 和工具。
- **Yarn**：一組開放的 Minecraft 映射，任何人都可以在 Creative Commons Zero 授權條款下免費使用。

## 為什麼需要 Fabric 來修改 Minecraft？ {#why-is-fabric-necessary-to-mod-minecraft}

> 模組開發是指修改遊戲以改變其行為或添加新功能的過程。對於 Minecraft 來說，這涵蓋的範圍很廣，從加入新的物品、方塊或實體，到改變遊戲的機制或加入新的遊戲模式，都在其中。

Minecraft Java 版的程式碼被 Mojang 混淆，使得單獨修改變得困難。 然而，借助 Fabric 等模組開發工具，模組開發變得更加容易。 有幾個映射系統可以協助這個過程。

Loom 使用這些映射將混淆的程式碼重新映射為人類可讀的格式，使模組開發人員更容易理解和修改遊戲的程式碼。 Yarn 是一個流行且優秀的映射選擇，但也有其他的選擇。 每個映射專案可能有其自身的優勢或重點。

Loom 允許您輕鬆地針對重新映射的程式碼開發和編譯模組，而 Fabric Loader 允許您將這些模組載入到遊戲中。

## Fabric API 提供了什麼，為什麼需要它？ {#what-does-fabric-api-provide-and-why-is-it-needed}

> Fabric API 是一組供模組開發人員在建立模組時使用的 API 和工具。

Fabric API 提供了大量的 API，這些 API 建構在 Minecraft 現有功能的基礎上。例如，它為模組開發人員提供新的勾點和事件以供使用，或提供新的實用程式和工具以使模組開發更容易，例如可傳遞的存取擴展器 (Transitive Access Wideners) 以及存取內部登錄檔的能力，例如可堆肥物品登錄檔。

雖然 Fabric API 提供了強大的功能，但有些任務，例如基本的方塊登錄，可以使用原版 API 完成，而無需使用 Fabric API。
