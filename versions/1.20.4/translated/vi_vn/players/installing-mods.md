---
title: Cài Đặt Mod
description: Bộ hướng dẫn từng bước để cài đặt mod cho Fabric.
authors:
  - IMB11

search: false
---

# Cài Đặt Mod

Bài hướng dẫn này sẽ giúp bạn cài đặt các bản mod Fabric cho trình Launcher Minecraft chính thức.

Đối với các trình launcher bên thứ ba, bạn nên tham khảo tài liệu của họ.

## 1. Tải Mod

:::warning
Bạn chỉ nên tải mod từ các nguồn uy tín. Để biết thêm thông tin về cách tìm mod, hãy tham khảo tài liệu [Tìm Mod](./finding-mods).
:::

Phần lớn các bản mod đều yêu cầu Fabric API, thứ mà có thể được tải từ [Modrinth](https://modrinth.com/mod/fabric-api) hoặc [CurseForge](https://curseforge.com/minecraft/mc-mods/fabric-api)

Khi chuẩn bị tải mod, cần lưu ý:

- Chúng phải hỗ trợ phiên bản Minecraft bạn muốn chơi. Ví dụ một bản mod mà hoạt động tốt ở bản 1.20, có thể không hoạt động được ở bản 1.20.2.
- Chúng phải dùng được cho Fabric và không phải là các trình mod khác.
- Thêm vào đó, chúng phải đúng phiên bản Minecraft (Phiên bản Java).

## 2. Di chuyển tệp mod vào trong thư mục `mods`

Thư mục mods có thể được tìm thấy ở các địa chỉ bên dưới ứng với mỗi hệ điều hành.

Bạn có thể dán những đường dẫn đó vào thanh địa chỉ của explorer để nhanh chóng điều hướng đến thư mục.

::: code-group

```:no-line-numbers [Windows]
%appdata%\.minecraft\mods
```

```:no-line-numbers [macOS]
~/Library/Application Support/minecraft/mods
```

```:no-line-numbers [Linux]
~/.minecraft/mods
```

:::

Khi bạn đã tìm thấy thư mục `mods`, bạn có thể di chuyển tệp mod `.jar` vào trong đó.

![Cài đặt mod ở thư mục mods](/assets/players/installing-mods.png)

## 3. Bạn đã hoàn tất!

Sau khi bạn đã di chuyển tệp mod vào trong thư mục `mods`, bạn có thể mở trình Launcher Minecraft và chọn hồ sơ Fabric từ danh sách thả xuống ở góc trái bên dưới và bấm nút chơi!

![Trình Launcher Minecraft với hồ sơ Fabric được chọn](/assets/players/installing-fabric/launcher-screen.png)

## Khắc Phục Sự Cố

Nếu bạn gặp bất cứ vấn đề nào khi đang làm theo bài hướng dẫn này, bạn có thể xin sự trợ giúp từ kênh `#player-support` trong [Discord Fabric](https://discord.gg/v6v4pMv).

Bạn cũng có thể tự sửa chữa lỗi bằng cách tham khảo các trang khắc phục sự cố sau:

- [Báo Cáo Dừng Đột Ngột](./troubleshooting/crash-reports)
- [Tải Lên Tệp Log](./troubleshooting/uploading-logs)
