---
title: Текст і переклади
description: Повна документація щодо роботи Minecraft з форматованим текстом і перекладами.
authors:
  - IMB11
  - LordEnder-Kitty
---

<!-- markdownlint-configure-file { MD033: { allowed_elements: [br, ColorSwatch, u] } } -->

Щоразу, коли Minecraft промальовує текст у грі, він, ймовірно, визначається за допомогою об’єкта `Component`.
Цей настроюваний тип використовується замість `String`, щоб уможливити розширене форматування, включаючи кольори, жирність, обфускацію та події натискання. Вони також забезпечують легкий доступ
до системи перекладу, що полегшує переклад будь-якого елемента інтерфейсу
різними мовами.

Якщо ви раніше працювали з пакетами даних або функціями, ви можете побачити паралелі з текстовим форматом json, який використовується серед іншого для показуваних імен, книг і табличок. Як ви, мабуть, здогадалися, це лише json-представлення об’єкта `Component`, і його можна перетворити в і з нього за допомогою `Component.Serializer`.

Коли створюєте мод, зазвичай краще створювати об’єкти `Component` безпосередньо у коді, використовуючи переклади, коли це можливо.

## Буквені текстові компоненти {#literal-text-components}

Найпростіший спосіб створити об’єкт `Component` — створити літерал. Це просто рядок, який промальовуватиметься як є, усталено без жодного форматування.

Вони створюються за допомогою методів `Component.nullToEmpty або `Component.literal`, які обидва діють трохи по різному. `Component.nullToEmpty`приймає нульові значення як вхідні дані та повертає екземпляр`Component`. На відміну від цього, `Component.literal`не повинен мати нульовий вхід, але повертає`MutableComponent`, це підклас `Component\`, який можна легко стилізувати та об’єднувати. Про це пізніше.

```java
Component literal = Component.nullToEmpty("Hello, world!");
MutableComponent mutable = Component.literal("Hello, world!");
// Keep in mind that a MutableComponent can be used as a Component, making this valid:
Component mutableAsText = mutable;
```

## Перекладувані текстові компоненти {#translatable-text-components}

Якщо ви хочете надати кілька перекладів для одного рядка тексту, ви можете використовувати метод `Component.translatable` для посилання на ключ перекладу в будь-якому мовному файлі. Якщо ключ не існує, ключ перекладу перетворюється на літерал.

```java
Component translatable = Component.translatable("example-mod.text.hello");

// Similarly to literals, translatable text can be easily made mutable.
MutableComponent mutable = Component.translatable("example-mod.text.bye");
```

Мовний файл `en_us.json`, виглядає так:

```json
{
  "example-mod.text.hello": "Hello!",
  "example-mod.text.bye": "Goodbye :("
}
```

Якщо ви хочете мати можливість використовувати змінні в перекладі, подібно до того, як повідомлення про смерть дозволяють використовувати залучених гравців і предмети в перекладі, ви можете додати зазначені змінні як параметри. Ви можете додати скільки завгодно параметрів.

```java
Component translatable = Component.translatable("example-mod.text.hello", player.getDisplayName());
```

Ви можете посилатися на ці змінні в перекладі так:

```json
{
  "example-mod.text.hello": "%1$s said hello!"
}
```

У грі `%1$s` буде замінено іменем гравця, якого ви згадали в коді. Використання `player.getDisplayName()` зробить так, що додаткова інформація про сутність з’явиться у спливній підказці під час наведення курсора на ім’я в повідомленні чату, на відміну від використання `player.getName()`, який все одно отримає назву; однак він не показуватиме додаткових деталей. Подібне можна зробити з itemStacks, використовуючи `stack.getDisplayName()`.

Щодо того, що взагалі означає `%1$s`, все, що вам справді потрібно знати, це те, що число відповідає змінній, яку ви намагаєтеся використати. Припустімо, у вас є три змінні, які ви використовуєте.

```java
Component translatable = Component.translatable("example-mod.text.whack.item", victim.getDisplayName(), attacker.getDisplayName(), itemStack.toHoverableText());
```

Якщо ви хочете вказати, хто в нашому випадку є зловмисником, ви б використовували `%2$s`, оскільки це друга змінна, яку ми передали. Так само `%3$s` посилається на itemStack. Переклад із такою кількістю додаткових параметрів може виглядати так:

```json
{
  "example-mod.text.whack.item": "%1$s was whacked by %2$s using %3$s"
}
```

## Серіалізація тексту {#serializing-text}

<!-- NOTE: These have been put into the example mod as they're likely to be updated to codecs in the next few updates. -->

Як згадувалося раніше, ви можете серіалізувати текст у JSON за допомогою текстового кодека. Додаткову інформацію про кодеки див. сторінку [кодеків](./codecs).

@[code transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/TextTests.java)

Це створює JSON, який можна використовувати в пакетах даних, командах та інших місцях, які приймають формат тексту JSON замість літерального або перекладаного тексту.

## Десеріалізація тексту {#deserializing-text}

Крім того, щоб десеріалізувати текстовий об’єкт JSON у справжній клас `Component`, знову скористайтеся кодеком.

@[code transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/TextTests.java)

## Форматування тексту {#formatting-text}

Ви можете бути знайомі зі стандартами форматування Minecraft:

Ви можете застосувати ці стилі форматування за допомогою переліку `ChatFormatting` у класі `MutableComponent`:

```java
MutableComponent result = Component.literal("Hello World!")
  .withStyle(ChatFormatting.AQUA, ChatFormatting.BOLD, ChatFormatting.UNDERLINE);
```

|              Колір              | Назва                                               | Код чату |  MOTD-код  |  Hex-код  |
| :-----------------------------: | --------------------------------------------------- | :------: | :--------: | :-------: |
| <ColorSwatch color="#000000" /> | Чорний<0/>`black`          |   `§0`   | `\u00A70` | `#000000` |
| <ColorSwatch color="#0000AA" /> | Темно-синій<0/>`dark_blue` |   `§1`   | `\u00A71` | `#0000AA` |
| <ColorSwatch color="#00AA00" /> | Темно-зелений<br />`dark_green`                     |   `§2`   | `\u00A72` | `#00AA00` |
| <ColorSwatch color="#00AAAA" /> | Темно-водянистий                                    |   `§3`   | `\u00A73` | `#00AAAA` |
| <ColorSwatch color="#AA0000" /> | Темно-червоний<br />`dark_red`                      |   `§4`   | `\u00A74` | `#AA0000` |
| <ColorSwatch color="#AA00AA" /> | Темно-фіолетовий<br />`dark_purple`                 |   `§5`   | `\u00A75` | `#AA00AA` |
| <ColorSwatch color="#FFAA00" /> | Золотий<br />`gold`                                 |   `§6`   | `\u00A76` | `#FFAA00` |
| <ColorSwatch color="#AAAAAA" /> | Сірий<br />`gray`                                   |   `§7`   | `\u00A77` | `#AAAAAA` |
| <ColorSwatch color="#555555" /> | Темно-сірий<br />`dark_gray`                        |   `§8`   | `\u00A78` | `#555555` |
| <ColorSwatch color="#5555FF" /> | Синій<br />`blue`                                   |   `§9`   | `\u00A79` | `#5555FF` |
| <ColorSwatch color="#55FF55" /> | Зелений<br />`green`                                |   `§a`   | `\u00A7a` | `#55FF55` |
| <ColorSwatch color="#55FFFF" /> | Водянистий<br />`aqua`                              |   `§b`   | `\u00A7b` | `#55FFFF` |
| <ColorSwatch color="#FF5555" /> | Червоний<br />`red`                                 |   `§c`   | `\u00A7c` | `#FF5555` |
| <ColorSwatch color="#FF55FF" /> | Світло-фіолетовий<br />`light_purple`               |   `§d`   | `\u00A7d` | `#FF55FF` |
| <ColorSwatch color="#FFFF55" /> | Жовтий<br />`yellow`                                |   `§e`   | `\u00A7e` | `#FFFF55` |
| <ColorSwatch color="#FFFFFF" /> | Білий<br />`white`                                  |   `§f`   | `\u00A7f` | `#FFFFFF` |
|                                 | Скидання форматування                               |   `§r`   |            |           |
|                                 | **Жирний**                                          |   `§l`   |            |           |
|                                 | ~~Закреслений~~                                     |   `§m`   |            |           |
|                                 | <u>Підкреслений</u>                                 |   `§n`   |            |           |
|                                 | _Курсив_                                            |   `§o`   |            |           |
|                                 | Зашифрований                                        |   `§k`   |            |           |
