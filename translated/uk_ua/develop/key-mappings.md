---
title: Призначення клавіш
description: Створення призначень клавіш та реагування на них.
authors:
  - cassiancc
  - dicedpixels
---

Minecraft обробляє введення користувачами з периферійних пристроїв, таких як клавіатура та миша, використовуючи призначення клавіш.
Багато з цих призначень клавіш можна налаштувати через меню налаштувань.

За допомогою Fabric API ви можете створювати власні користувацькі призначення клавіш і реагувати на них у своєму моді.

Призначення клавіш існують лише на стороні клієнта. Це означає, що реєстрація та реакція на призначення клавіш має виконуватися на стороні клієнта. Для цього можна використовувати **ініціалізатор клієнта**.

## Створення призначень клавіш {#creating-a-key-mapping}

Призначення клавіш складається з двох частин: призначення ключа та категорії, до якої воно належить.

Почнімо зі створення категорії. Категорія визначає групу призначень клавіш, які видно в меню налаштувань.

@[code lang=java transcludeWith=:::category](@/reference/latest/src/client/java/com/example/docs/keymapping/ExampleModKeyMappingsClient.java)

Далі, ми маємо створити призначення клавіші. Ми будемо використовувати API Fabric `KeyBindingHelper`, щоб одночасно зареєструвати наше призначення клавіш.

@[code lang=java transcludeWith=:::key_mapping](@/reference/latest/src/client/java/com/example/docs/keymapping/ExampleModKeyMappingsClient.java)

::: info

Зауважте, що назви маркерів ключів (`GLFW.GLFW_KEY_*`) припускаються [стандартний макет США](https://upload.wikimedia.org/wikipedia/commons/d/da/KB_United_States.svg).

Це означає, що якщо ви використовуєте макет AZERTY, натискання <kbd>A</kbd> призведе до
`GLFW.GLFW_KEY_Q`.

:::

Закріплені клавіші також можна створити за допомогою `KeyBindingHelper`, передавши екземпляр `ToggleKeyMapping` замість `KeyMapping`.

Після реєстрації ви зможете знайти призначення клавіші у _Налаштування_ > _Керування_ > _Призначення клавіш_.

![Неперекладені призначення та категорія клавіш](/assets/develop/key-mappings/untranslated.png)

## Переклад {#translations}

Вам потрібно буде надати переклади як для призначення, так і для категорії.

Ключ перекладу імені категорії приймає форму `key.category.<namespace>.<path>`. Ключ перекладу призначення клавіші буде тим, який ви надали під час створення призначення.

Переклади можна додати вручну або за допомогою [генерації даних](./data-generation/translations).

```json
{
  "key.category.example-mod.custom_category": "Example Mod Custom Category",
  "key.example-mod.send_to_chat": "Send to Chat"
}
```

![Перекладені призначення та категорія клавіш](/assets/develop/key-mappings/translated.png)

## Реагування на призначення клавіші {#reacting-to-key-mappings}

Тепер, коли у нас є призначення клавіші ми можемо реагувати на нього за допомогою події клієнта.

@[code lang=java transcludeWith=:::client_tick_event](@/reference/latest/src/client/java/com/example/docs/keymapping/ExampleModKeyMappingsClient.java)

Це надрукує «Клавіша натиснута!» до чату в грі кожного разу, коли натискається відповідна клавіша. Майте на увазі, що утримування клавіші призведе до повторного друку повідомлення в чаті, тому ви можете застосувати запобіжники, якщо ця логіка має спрацювати лише один раз.

![Повідомлення в чаті](/assets/develop/key-mappings/key_mapping_pressed.png)
