---
title: 自訂畫面元件
description: 了解如何為你的畫面建立自訂畫面元件。
authors:
  - IMB11
---

# 自訂畫面元件 {#custom-widgets}

畫面元件就是包裝過的圖形，他們可以被加到畫面上，也可以讀取來自玩家的互動（例如：點選，按鍵等）。

## 新增一個畫面元件 {#creating-a-widget}

創建一個新的畫面元件類別有許多方法，例如繼承 (extend) `ClickableWidget`。 這個類別提供了很多實用的功能（例如：改變長關、位置、處理事件），他包含了 `Drawable`、`Element`、和 `Selectable`介面（interface）。

- `Drawable` （繪製）： 需要使用 `addDrawableChild` 來繪製出這個畫面元件。
- `Element` （事件）： 如果有需要讀取玩家互動（點選、按鍵等）。
- `Narratable` （無障礙功能）： 如果有需要使用文字朗讀和其他無障礙功能。
- `Selectable` （選擇）： 如果想要讓畫面元件變得可以透過<kbd>Tab</kbd>選擇，這也有助於無障礙功能。

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomWidget.java)

## 讓元件顯示在畫面上 {#adding-the-widget-to-the-screen}

就像其他畫面元件一樣，你會需要用 `Screen` 類別提供ㄉ的 `addDrawableChild` 函式來將元件顯示在畫面上。 請確保這是在 `init` 函式裡執行的。

@[code lang=java transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomScreen.java)

![顯示自訂元件在畫面上](/assets/develop/rendering/gui/custom-widget-example.png)

## 畫面元件事件 {#widget-events}

你可以透過重寫 `onMouseClicked`、`onMouseReleased`、`onKeyPressed`等函式來讀取事件。

舉例來說，你可以透過 `ClickableWidget` 類別的 `isHovered` 函式來讓元件在滑鼠停留時改變顏色。

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomWidget.java)

![滑鼠停留事件範例](/assets/develop/rendering/gui/custom-widget-events.webp)
