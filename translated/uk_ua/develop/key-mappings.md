---
title: Призначення клавіш
description: Створення призначень клавіш та реагування на них.
authors:
  - cassiancc
  - dicedpixels
  - its-miroma
  - NotNightSky
resources:
  https://upload.wikimedia.org/wikipedia/commons/d/da/KB_United_States.svg: Стандартна американська розкладка клавіатури
---

Minecraft обробляє введення користувачами з периферійних пристроїв, таких як клавіатура та миша, використовуючи призначення клавіш.
Багато з цих призначень клавіш можна налаштувати через меню налаштувань.

За допомогою Fabric API ви можете створювати власні користувацькі призначення клавіш і реагувати на них у своєму моді.

Призначення клавіш існують лише на стороні клієнта. Це означає, що реєстрація та реакція на призначення клавіш має виконуватися на стороні клієнта. Для цього можна використовувати **ініціалізатор клієнта**.

## Створення призначень клавіш {#creating-a-key-mapping}

Призначення клавіш складається з двох частин: призначення ключа та категорії, до якої воно належить.

Почнімо зі створення категорії. Категорія визначає групу призначень клавіш, які видно в меню налаштувань.

<<< @/reference/latest/src/client/java/com/example/docs/keymapping/ExampleModKeyMappingsClient.java#category

Далі, ми маємо створити призначення клавіші. Ми будемо використовувати API Fabric `KeyMappingHelper`, щоб одночасно зареєструвати наше призначення клавіш.

<<< @/reference/latest/src/client/java/com/example/docs/keymapping/ExampleModKeyMappingsClient.java#key_mapping

::: info

Зауважте, що назви маркерів ключів (`InputConstants.KEY_*`) припускаються [стандартному макету США](https://upload.wikimedia.org/wikipedia/commons/d/da/KB_United_States.svg).

Це означає, що якщо ви використовуєте розкладку AZERTY, натискання клавіші <kbd>A</kbd> дасть `InputConstants.KEY_Q`.

:::

Закріплені клавіші також можна створити за допомогою `KeyMappingHelper`, передавши екземпляр `ToggleKeyMapping` замість `KeyMapping`.

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

## Дія призначення клавіші у світі {#reacting-to-key-mappings-in-world}

Тепер, коли ми маємо призначення клавіш, ми можемо запускати дію прямо в ігровому процесі, використовуючи подію клієнтського такту:

<<< @/reference/latest/src/client/java/com/example/docs/keymapping/ExampleModKeyMappingsClient.java#client_tick_event

Це виводитиме повідомлення «Key press detected in the world» у внутрішньоігровий чат щоразу, коли натискається призначена клавіша. Майте на увазі, що утримування клавіші призведе до повторного друку повідомлення в чаті, тому ви можете застосувати запобіжники, якщо ця логіка має спрацювати лише один раз.

![Повідомлення в чаті](/assets/develop/key-mappings/key_mapping_pressed.png)

## Дія призначення клавіші в інтерфейсі {#reacting-to-key-mappings-in-gui}

Ми також можемо запускати дію у межах екранів — як коли світ відкрито, так і коли ні.

<<< @/reference/latest/src/client/java/com/example/docs/keymapping/ExampleModKeyMappingsClient.java#screen_before_init_event

І додайте два обробники:

<<< @/reference/latest/src/client/java/com/example/docs/keymapping/ExampleModKeyMappingsClient.java#helper_methods

Це перевіряє, чи є поточний екран `TitleScreen` або `CreativeModeInventoryScreen`. Якщо це так, ми реалізуємо дві різні моделі поведінки залежно від того, перебуваємо ми всередині світу чи за його межами:

- Коли гравець перебуває поза межами світу — тобто коли не існує сутності гравця,— у консоль виводиться повідомлення «Key press detected in the title screen».
- В іншому разі, якщо натиснути клавішу під час перебування у світі, у внутрішньоігровий чат надсилається повідомлення «Key press detected in the GUI with a world open, closing screen», а екран закривається.

<VideoPlayer src="/assets/develop/key-mappings/in_screen_key_map.webm">Натискання клавіші в інтерфейсі при відкритому світі</VideoPlayer>

::: info

Друге повідомлення «Key press detected in the world» надсилається в чат через попередньо зареєстрований слухач події `clientTickEvents`.

:::

::: tip

У режимі виживання `screen` буде екземпляром `InventoryScreen`, тоді як у творчості це буде `CreativeModeInventoryScreen`.

За потреби ви можете видалити перевірку `screen instanceof`, щоб прив'язати слухач подій до всіх екранів.

:::

<!---->
