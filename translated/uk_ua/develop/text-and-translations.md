---
title: Текст і переклади
description: Повна документація щодо роботи Minecraft з форматованим текстом і перекладами.
authors:
  - IMB11
  - LordEnder-Kitty
---

<!-- markdownlint-configure-file { MD033: { allowed_elements: [br, ColorSwatch, u] } } -->

Щоразу, коли Minecraft промальовує текст у грі, він, ймовірно, визначається за допомогою об’єкта `Text`.
Цей настроюваний тип використовується замість `String`, щоб уможливити розширене форматування, включаючи кольори, жирність, обфускацію та події натискання. Вони також забезпечують легкий доступ
до системи перекладу, що полегшує переклад будь-якого елемента інтерфейсу
різними мовами.

Якщо ви раніше працювали з пакетами даних або функціями, ви можете побачити паралелі з текстовим форматом json, який використовується серед іншого для показуваних імен, книг і табличок. Як ви, мабуть, здогадалися, це лише json-представлення об’єкта `Text`, і його можна перетворити в і з нього за допомогою `Text.Serializer`.

Коли створюєте мод, зазвичай краще створювати об’єкти `Текст` безпосередньо у коді, використовуючи переклади, коли це можливо.

## Текстові літерали {#text-literals}

Найпростіший спосіб створити об’єкт `Text` — створити літерал. Це просто рядок, який промальовуватиметься як є, за замовчуванням без жодного форматування.

Вони створюються за допомогою методів `Text.of` або `Text.literal`, які обидва діють трохи по різному. `Text.of` приймає нульові значення як вхідні дані та повертає екземпляр `Text`. На відміну від цього, `Text.literal` не повинен мати нульовий вхід, але повертає `MutableText`, це підклас `Text`, який можна легко стилізувати та об’єднувати. Про це пізніше.

```java
Text literal = Text.of("Hello, world!");
MutableText mutable = Text.literal("Hello, world!");
// Keep in mind that a MutableText can be used as a Text, making this valid:
Text mutableAsText = mutable;
```

## Текст, який можна перекласти {#translatable-text}

Якщо ви хочете надати кілька перекладів для одного рядка тексту, ви можете використовувати метод `Text.translatable` для посилання на ключ перекладу в будь-якому мовному файлі. Якщо ключ не існує, ключ перекладу перетворюється на літерал.

```java
Text translatable = Text.translatable("my_mod.text.hello");

// Similarly to literals, translatable text can be easily made mutable.
MutableText mutable = Text.translatable("my_mod.text.bye");
```

Мовний файл `en_us.json`, виглядає так:

```json
{
  "my_mod.text.hello": "Hello!",
  "my_mod.text.bye": "Goodbye :("
}
```

Якщо ви хочете мати можливість використовувати змінні в перекладі, подібно до того, як повідомлення про смерть дозволяють використовувати залучених гравців і предмети в перекладі, ви можете додати зазначені змінні як параметри. Ви можете додати скільки завгодно параметрів.

```java
Text translatable = Text.translatable("my_mod.text.hello", player.getDisplayName());
```

Ви можете посилатися на ці змінні в перекладі так:

```json
{
  "my_mod.text.hello": "%1$s said hello!"
}
```

У грі %1\$s буде замінено іменем гравця, якого ви згадали в коді. Використання `player.getDisplayName()` зробить так, що додаткова інформація про сутність з’явиться у спливній підказці під час наведення курсора на ім’я в повідомленні чату, на відміну від використання `player.getName()`, який все одно отримає назву; однак він не показуватиме додаткових деталей. Подібне можна зробити з itemStacks, використовуючи `stack.toHoverableText()`.

Щодо того, що взагалі означає %1\$s, все, що вам справді потрібно знати, це те, що число відповідає змінній, яку ви намагаєтеся використати. Припустімо, у вас є три змінні, які ви використовуєте.

```java
Text translatable = Text.translatable("my_mod.text.whack.item", victim.getDisplayName(), attacker.getDisplayName(), itemStack.toHoverableText());
```

Якщо ви хочете вказати, хто в нашому випадку є зловмисником, ви б використовували %2\$s, оскільки це друга змінна, яку ми передали. Так само %3\$s посилається на itemStack. Переклад із такою кількістю додаткових параметрів може виглядати так:

```json
{
  "my_mod.text.whack.item": "%1$s was whacked by %2$s using %3$s"
}
```

## Серіалізація тексту {#serializing-text}

<!-- NOTE: These have been put into the reference mod as they're likely to be updated to codecs in the next few updates. -->

Як згадувалося раніше, ви можете серіалізувати текст у JSON за допомогою текстового кодека. Додаткову інформацію про кодеки див. на сторінці [Кодек](./codecs).

@[code transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/TextTests.java)

Це створює JSON, який можна використовувати в пакетах даних, командах та інших місцях, які приймають формат тексту JSON замість літерального або перекладаного тексту.

## Десеріалізація тексту {#deserializing-text}

Крім того, щоб десеріалізувати текстовий об’єкт JSON у справжній клас `Text`, знову скористайтеся кодеком.

@[code transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/TextTests.java)

## Форматування тексту {#formatting-text}

Ви можете бути знайомі зі стандартами форматування Minecraft:

Ви можете застосувати ці стилі форматування за допомогою переліку `Formatting` класу `MutableText`:

```java
MutableText result = Text.literal("Hello World!")
  .formatted(Formatting.AQUA, Formatting.BOLD, Formatting.UNDERLINE);
```

|              Колір              | Назва                                               | Код чату |  MOTD-код  |  Hex-код  |
| :-----------------------------: | --------------------------------------------------- | :------: | :--------: | :-------: |
|              <br />             | Чорний<0/>`black`          |   `§0`   | `\u00A70` | `#000000` |
| <ColorSwatch color="#0000AA" /> | Темно-синій<0/>`dark_blue` |   `§1`   | `\u00A71` | `#0000AA` |
| <ColorSwatch color="#00AA00" /> | Темно-зелений<br />`dark_green`                     |   `§2`   | `\u00A72` | `#00AA00` |
| <ColorSwatch color="#00AAAA" /> | Темно-водянистий                                    |   `§3`   | `\u00A73` | `#00AAAA` |
| <ColorSwatch color="#AA0000" /> | Темно-червоний<br />`dark_red`                      |   `§4`   | `\u00A74` | `#AA0000` |
| <ColorSwatch color="#AA00AA" /> | Темно-<br />`dark_purple`                           |   `§5`   | `\u00A75` | `#AA00AA` |
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
|                                 | ~~Закреслення~~                                     |   `§m`   |            |           |
|                                 | <u>Підкреслений</u>                                 |   `§n`   |            |           |
|                                 | _Курсив_                                            |   §o\`   |            |           |
|                                 | Зашифрований                                        |   `§k`   |            |           |
