---
title: Tham Số Câu Lệnh
description: Học cách tạo ra câu lệnh với tham số phức tạp.

search: false
---

# Tham Số Câu Lệnh

Hầu hết các câu lệnh đều có tham số. Nhiều khi tham số đó không bắt buộc, bạn không cần phải đưa vào câu lệnh nhưng nó vẫn chạy. Một node có thể có nhiều loại tham số, nhưng bạn nên tránh xảy ra trường hợp kiểu dữ liệu của tham số không rõ.

@[code lang=java highlight={3} transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Trong trường hợp này, sau `/argtater`, bạn cần đưa ra một số nguyên làm tham số. Chẳng hạn, khi bạn chạy `/argtater 3`, bạn sẽ nhận được thông báo `Called /argtater with value = 3`. Khị bạn nhập `/argtater` mà không đưa ra tham số, câu lệnh trên sẽ không chạy được.

Chúng ta có thể thêm vào đối số không bắt buộc thứ hai:

@[code lang=java highlight={3,13} transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Giờ đây, bạn có thể nhập một hoặc hai số nguyên làm tham số. Nếu bạn đưa vào một số nguyên, bạn sẽ nhận được thông báo với một giá trị. Nếu là hai số nguyên, thông báo sẽ đưa ra hai giá trị.

Có thể bạn thấy việc viết hai quy trình xử lý dữ liệu giống nhau là không cần thiết. Ta có thể tạo một method sử dụng trong cả hai tham số.

@[code lang=java highlight={3,5,6,7} transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

## Kiểu Tham Số Tùy Chỉnh

Nếu bạn không tìm thấy kiểu tham số bạn cần trong vanilla, bạn có thể tự tạo kiểu của riêng mình. Bạn cần tạo một class mà kế thừa interface `ArgumentType<T>`, với `T` là kiểu tham số.

Bạn cần phải thêm method `parse` để xử lý tham số từ kiểu xâu ký tự sang kiểu tham số mà bạn cần.

Giả sử bạn cần một kiểu tham số cho ra `BlockPos` khi người dùng nhập: `{x, y, z}`

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/command/BlockPosArgumentType.java)

### Đăng Ký Kiểu Tham Số Tùy Chỉnh

:::warning
Câu lệnh của bạn sẽ không hoạt động nếu bạn không đăng ký kiểu tham số tùy chỉnh trên cả máy chủ lẫn máy khách!
:::

Bạn có thể đăng ký kiểu tham số tùy chỉnh trong khi mod của bạn đang khởi động trong method `onInitialize`, sử dụng class `ArgumentTypeRegistry`:

@[code lang=java transcludeWith=:::11](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

### Sử Dụng Kiểu Tham Số Tùy Chỉnh

Bạn có thể sử dụng kiểu tham số tùy chỉnh trong một câu lệnh bằng cách đưa một instance của nó vào method `.argument` khi đang xây dựng một câu lệnh.

@[code lang=java transcludeWith=:::10 highlight={3}](@/reference/latest/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Thử chạy câu lệnh xem kiểu tham số của chúng ta có hoạt động không:

![Tham số không hợp lệ](/assets/develop/commands/custom-arguments_fail.png)

![Tham số hợp lệ](/assets/develop/commands/custom-arguments_valid.png)

![Kết quả câu lệnh](/assets/develop/commands/custom-arguments_result.png)
