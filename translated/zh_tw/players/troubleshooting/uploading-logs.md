---
title: 上傳記錄檔
description: 如何上傳記錄檔以進行疑難排解。
authors:
  - IMB11
---

# 上傳記錄檔 {#uploading-logs}

在進行疑難排解時，通常需要提供記錄檔以幫助尋找問題的原因。

## 為什麼我應該上傳記錄檔？ {#why-should-i-upload-logs}

上傳記錄檔而不是只在論壇貼文中貼上記錄檔，讓其他人更快地解決你的問題。 它還允許你與他人分享你的記錄檔，而無需複製和貼上它們。

某些文字分享網站還會對記錄檔進行語法突顯，使其更易於閱讀，並且可能會對敏感資訊（例如你的使用者名稱或系統資訊）進行審查。

## 崩潰報告

當遊戲崩潰時，會自動產生崩潰報告。 它們只包含崩潰資訊，而不包含遊戲的實際記錄檔。 它們位於遊戲目錄中的 `crash-reports` 資料夾中。 它們只包含崩潰資訊，而不包含遊戲的實際記錄檔。 它們位於遊戲目錄中的 `crash-reports` 資料夾中。

有關崩潰報告的更多資訊，請參閱[崩潰報告](./crash-reports)。

## 尋找記錄檔 {#locating-logs}

本指南涵蓋了官方 Minecraft 啟動器（通常稱為「原版啟動器」），對於第三方啟動器，你應該參閱其文件。

記錄檔位於遊戲目錄中的 logs 資料夾中，遊戲目錄可以在以下位置找到，具體取決於你的作業系統：

::: code-group

```:no-line-numbers [Windows]
%appdata%\.minecraft
```

```:no-line-numbers [macOS]
~/Library/Application Support/minecraft
```

```:no-line-numbers [Linux]
~/.minecraft
```

:::

最新的記錄檔名為 `latest.log`，以及先前的記錄檔使用命名模式 `yyyy-mm-dd_number.log.gz`。

## 將記錄檔上傳至線上服務 {#uploading-logs-online}

記錄檔可以上傳到各種服務，例如：

- [Pastebin](https://pastebin.com/)
- [GitHub Gist](https://gist.github.com/)
- [mclo.gs](https://mclo.gs/)
