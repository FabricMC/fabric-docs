# Tài Liệu Hướng Dẫn Cách Đóng Góp Fabric

Trang này sử dụng [VitePress](https://vitepress.dev/) để tạo văn bản HTML tĩnh từ nhiều tệp markdown khác nhau. Bạn nên làm quen với những tiện ích markdown mà VitePress hỗ trợ tại [đây](https://vitepress.dev/guide/markdown#features).

## Mục Lục

- [Tài Liệu Hướng Dẫn Cách Đóng Góp Fabric](#tài-liệu-hướng-dẫn-cách-đóng-góp-fabric)
  - [Cách Đóng Góp](#cách-đóng-góp)
  - [Đóng Góp Framework](#đóng-góp-framework)
  - [Đóng Góp Nội Dung](#đóng-góp-nội-dung)
    - [Phong Cách Ghi Tài Liệu](#phong-cách-ghi-tài-liệu)
    - [Hướng Dẫn Mở Rộng](#hướng-dẫn-mở-rộng)
    - [Xác Minh Nội Dung](#xác-minh-nội-dung)
    - [Làm Gọn](#làm-gọn)

## Cách Đóng Góp

Chúng tôi khuyên bạn nên tạo một nhánh mới trên bản fork của kho lưu trữ cho mỗi lần tạo pull request. Điều này sẽ giúp bạn dễ quản lý khi thực hiện nhiều pull request cùng một lúc.

**Nếu bạn muốn xem trước những thay đổi bạn tạo trên máy nội bộ của bạn, bạn cần phải cài đặt [Node.js 18+](https://nodejs.org/en/)**

Trước khi chạy bất kì lệnh nào, hãy đảm bảo chạy lệnh `npm install` để cài đầy đủ các gói hỗ trợ.

**Chạy máy chủ phát triển nội bộ:**

Điều này sẽ cho phép bạn xem trước các thay đổi bạn đã làm trên thiết bị của bạn tại địa chỉ `localhost:3000` và sẽ tự động tải lại trang khi bạn thực hiện các thay đổi.

```sh
npm run dev
```

**Xây dựng trang web:**

Lệnh dưới đây sẽ biên dịch toàn bộ tập tin markdown thành những tệp HTML tĩnh và đặt chúng trong `.vitepress/dist`

```sh
npm run build
```

**Xem trước trang web đã tạo:**

Lệnh dưới sẽ khởi động một máy chủ nội bộ trên cổng 3000 để hiển thị nội dụng được tìm thấy ở `.vitepress/dist`

```sh
npm run preview
```

## Đóng Góp Framework

Framework ám chỉ cấu trúc bên trong của một trang web, bất kì pull request nào chỉnh sử framework của trang web nên được đánh dấu với nhãn `framework`.

Bạn thật sự chỉ nên tạo các pull request về framework sau khi tham khảo ý kiến của đội ngũ biên soạn tài liệu trong [Discord Fabric](https://discord.gg/v6v4pMv) hoặc thông qua trang issue.

**Chú ý: Việc chỉnh sửa các tập tin thanh bên và thanh định hướng không được tính là một pull request của framework.**

## Đóng Góp Nội Dung

Đóng góp nội dung là cách chính để đóng góp vào Tài liệu Fabric.

Tất cả các nội dung nên theo phong cách ghi tài liệu của chúng tôi.

### Phong Cách Ghi Tài Liệu

Tất cả các trang trên trang web bộ tài liệu Fabric nên thống nhất về một phong cách ghi. Nếu bạn không chắc điều gì, bạn có thể hỏi tại [Discord Fabric](https://discord.gg/v6v4pMv) hoặc thông qua mục Discussions của Github.

Quy định về phong cách như sau:

1. Tất cả trang phải có tiêu đề và dòng mô tả theo định dạng frontmatter.

   ```md
   ---
   title: Đây là tiêu đề của trang
   description: Đây là dòng mô tả của trang
   authors:
     - TênNgườiDùngGithub
   ---

   # ...
   ```

2. Nếu bạn muốn tạo hoặc chỉnh sửa đoạn mã của trang, hãy đặt đoạn mã ở một vị trí thích hợp ở trong bản mod có liên quan (được đặt ở thư mục `/reference` của kho lưu trữ). Sau đó, sử dụng [tính năng tạo định nghĩa cho mã được cung cấp bởi VitePress](https://vitepress.dev/guide/markdown#import-code-snippets) để nhúng mã, hoặc nếu bạn cần nhiều sự kiểm soát hơn, bạn có thể sử dụng tính năng transclude từ [`markdown-it-vuepress-code-snippet-enhanced`](https://github.com/fabioaanthony/markdown-it-vuepress-code-snippet-enhanced).

   **Ví dụ:**

   ```md
   <<< @/reference/1.21/src/main/java/com/example/docs/FabricDocsReference.java{15-21 java}
   ```

   Điều này sẽ nhúng mã từ dòng 15 đến 21 của tệp `FabricDocsReference.java` trong tập tin mod có liên quan.

   Kết quả sau khi tạo định nghĩa cho mã sẽ như này:

   ```java
     @Override
     public void onInitialize() {
       // This code runs as soon as Minecraft is in a mod-load-ready state.
       // However, some things (like resources) may still be uninitialized.
       // Proceed with mild caution.

       LOGGER.info("Hello Fabric world!");
     }
   ```

   **Ví Dụ Về Transclude:**

   ```md
   @[code transcludeWith=#test_transclude](@/reference/.../blah.java)
   ```

   Mã trên sẽ nhúng phần được chọn của `blah.java` mà đã được đánh dấu với nhãn `#test_transclude`.

   Ví dụ:

   ```java
   public final String test = "Bye World!"

   // #test_transclude
   public void test() {
     System.out.println("Hello World!");
   }
   // #test_transclude
   ```

   Chỉ có phần mã ở giữa nhãn `#test_transclude` mới được nhúng.

   ```java
   public void test() {
     System.out.println("Hello World!");
   }
   ```

3. Chúng tôi tuân theo ngữ pháp tiếng Anh. Mặc dù bạn cũng có thể sử dụng công cụ [LanguageTool](https://languagetool.org/) để kiểm tra ngữ pháp khi viết, nhưng đừng quá căng thẳng về điều đó. Đội ngũ biên soạn của chúng tôi sẽ luôn xem xét và sửa lại chúng trong bước làm gọn. Tuy vậy, bạn nên dành gia một chút thời gian kiểm tra từ đầu và điều đó giúp chúng tôi tiết kiệm thêm thời gian.

4. Nếu bạn đang tạo một mục mới, bạn nên tạo thêm một thanh bên mới trong thư mục `.vitepress/sidebars` và thêm nó trong tệp `config.mts`. Nếu bạn cần sự hỗ trợ để làm điều này, vui lòng hỏi trong kênh `#wiki` ở [Discord Fabric](https://discord.gg/v6v4pMv).

5. Khi tạo một trang mới, bạn nên thêm nó vào thanh bên nằm ở thư mục `.vitepress/sidebars`. Một lần nữa, nếu bạn cần sự trợ giúp, hãy hỏi ở kênh `#wiki` trong Discord Fabric.

6. Tât cả hình ảnh nên được đặt ở thư mục `/assets` phù hợp.

7. ⚠️ **Khi liên kết các trang khác, hãy sử dụng liên kết tương đối.** ⚠️

   Điều này là do hệ thống lập phiên bản tại chỗ, sẽ xử lý các liên kết để thêm phiên bản trước. Nếu bạn sử dụng liên kết tuyệt đối, số phiên bản sẽ không được thêm vào liên kết.

   Ví dụ, đối với trang ở thư mục `/players`, để liên kết trang `installing-fabric` được tạo ở `/players/installing-fabric.md`, bạn sẽ phải làm như sau:

   ```md
   [Đây là liên kết dẫn đến trang khác](./installing-fabric)
   ```

   Bạn **KHÔNG ĐƯỢC** làm như sau:

   ```md
   [Đây là liên kết dẫn đến trang khác](/players/installing-fabric)
   ```

Tất cả sự đóng góp nội dung sẽ lần lượt đi qua ba bước:

1. Hướng dẫn mở rộng (nếu có thể)
2. Xác Minh Nội Dung
3. Làm gọn (ngữ pháp, ...)

### Hướng Dẫn Mở Rộng

Nếu đội ngũ biên soạn nghĩ rằng bạn có thể mở rộng thêm trong pull request của bạn, một thành viên của đội ngũ sẽ thêm nhãn `expansion` cho pull request của bạn cùng với một bình luận giải thích những thứ mà bạn có thể mở rộng thêm nữa. Nếu bạn đồng ý sự đề xuất này, bạn có thể mở rộng thêm pull request của bạn.

**Cũng đừng cảm thấy quá áp lực khi mở rộng thêm pull request của bạn.** Nếu bạn không muốn mở rộng thêm, bạn có thể yêu cầu xoá nhãn `expansion`.

Nếu bạn không muốn mở rộng thêm pull request của bạn, nhưng bạn sẵn sàng cho người khác làm điều đó vào lúc sau, điều tốt nhất là hãy tạo một issue trong [trang Issues](https://github.com/FabricMC/fabric-docs/issues) và giải thích những gì mà bạn nghĩ có thể mở rộng thêm.

### Xác Minh Nội Dung

Tất cả pull requests mà thêm nội dung đều sẽ trải qua bước xác minh nội dung, đây là bước quan trọng nhất vì nó đảm bảo tất cả nội dung phải chính xác và tuân theo quy định về phong cách ghi Tài liệu Fabric.

### Làm Gọn

Đây là giai đoạn mà đội ngũ biên soạn sẽ sửa chữa bất kỳ lỗi ngữ pháp nào được tìm thấy và thực hiện bất kỳ thay đổi nào khác mà họ cho là cần thiết trước khi hợp nhất pull request!
