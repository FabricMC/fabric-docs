---
title: Текст і переклади
description: Повна документація щодо роботи Minecraft з форматованим текстом і перекладами.
authors:
  - IMB11
  - LordEnder-Kitty
---

Щоразу, коли Minecraft промальовує текст у грі, він, ймовірно, визначається за допомогою об’єкта `Text`.
Цей власний тип використовується замість `String` для більш розширеного форматування,
включаючи кольори, жирний, зашифрований і події натискання. Вони також забезпечують легкий доступ
до системи перекладу, що полегшує переклад будь-якого елемента інтерфейсу
різними мовами.

Якщо ви раніше працювали з пакетами даних або функціями, ви можете побачити паралелі з текстовим форматом
json, який використовується для показу імен, книг та табличок, і не тільки. Як ти
мабуть, можна здогадатися, це просто представлення json об’єкта `Text`, і його можна
конвертувати в і з використанням `Text.Serializer`.

Коли створюєте мод, зазвичай краще створювати об’єкти `Текст` безпосередньо
у коді, використовуючи переклади, коли це можливо.

## Текстові літерали {#text-literals}

Найпростіший спосіб створити об’єкт `Текст` — створити літерал. Це просто рядок
який промальовуватиме як є, за замовчуванням без жодного форматування.

Вони створюються за допомогою методів `Text.of` або `Text.literal`, які обидва діють трохи
інакше. `Text.of` приймає нульові значення як вхідні дані та повертає екземпляр `Text`. На відміну від цього, `Text.literal` не має мати значення null, але повертає `MutableText`,
це підклас `Text`, який можна легко стилізувати та об'єднувати. Детальніше про
це пізніше.

```java
Text literal = Text.of("Hello, world!");
MutableText mutable = Text.literal("Hello, world!");
// Keep in mind that a MutableText can be used as a Text, making this valid:
Text mutableAsText = mutable;
```

## Можливість перекладати {#translatable-text}

Якщо ви хочете надати кілька перекладів для одного рядка тексту, ви можете використовувати метод `Text.translatable` для посилання на ключ перекладу в будь-якому мовному файлі. Якщо ключ не існує, ключ перекладу перетворюється на літерал.

```java
Text translatable = Text.translatable("my_mod.text.hello");

// Similarly to literals, translatable text can be easily made mutable.
MutableText mutable = Text.translatable("my_mod.text.bye");
```

Мовний файл `en_us.json` (для української треба `uk_ua.json`) виглядає так:

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

У грі %1\$s буде замінено іменем гравця, якого ви згадали в коді. Використання `player.getDisplayName()` зробить так, що додаткова інформація про сутність з’явиться у спливаючій підказці під час наведення вказівника мишки на ім’я в повідомленні чату, на відміну від використання `player.getName()`, який все одно отримає назву; однак він не відображатиме додаткових деталей. Подібне можна зробити з itemStacks, використовуючи `stack.toHoverableText()`.

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

Як згадувалося раніше, ви можете серіалізувати текст у JSON за допомогою текстового кодека. Додаткову інформацію про кодеки див. на сторінці [Codec](./codecs).

@[code transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/TextTests.java)

Це створює JSON, який можна використовувати в пакетах даних, командах та інших місцях, які приймають формат тексту JSON замість літерального або перекладаного тексту.

## Десеріалізація тексту {#deserializing-text}

Крім того, щоб десеріалізувати текстовий об’єкт JSON у фактичний клас `Text`, знову скористайтеся кодеком.

@[code transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/TextTests.java)

## Форматування тексту {#formatting-text}

Ви можете бути знайомі зі стандартами форматування Minecraft:

Ви можете бути знайомі зі стандартами форматування Minecraft:

```java
MutableText result = Text.literal("Hello World!")
  .formatted(Formatting.AQUA, Formatting.BOLD, Formatting.UNDERLINE);
```

<table>
    <tbody><tr><th>Колір</th><th>Назва</th><th>Чат-код</th><th>Код MOTD</th><th>Hex код</th></tr>
    <tr><td><ColorSwatch color="#000000" /></td><td>Чорний (black)</td><td>§0</td><td>\u00A70</td><td>#000000</td></tr>
    <tr><td><ColorSwatch color="#0000AA" /></td><td>Темно-синій (dark_blue)</td><td>§1</td><td>\u00A71</td><td>#0000AA</td></tr>
    <tr><td><ColorSwatch color="#00AA00" /></td><td>Темно-зелений (dark_green)</td><td>§2</td><td>\u00A72</td><td>#00AA00</td></tr>
    <tr><td><ColorSwatch color="#00AAAA" /></td><td>Темно-водянистий (dark_aqua)</td><td>§3</td><td>\u00A73</td><td>#00AAAA</td></tr>
    <tr><td><ColorSwatch color="#AA0000" /></td><td>Багряний (dark_red)</td><td>§4</td><td>\u00A74</td><td>#AA0000</td></tr>
    <tr><td><ColorSwatch color="#AA00AA" /></td><td>Темно-фіолетовий (dark_purple)</td><td>§5</td><td>\u00A75</td><td>#AA00AA</td></tr>
    <tr><td><ColorSwatch color="#FFAA00" /></td><td>Золотий (gold)</td><td>§6</td><td>\u00A76</td><td>#FFAA00</td></tr>
    <tr><td><ColorSwatch color="#AAAAAA"/></td><td>Сірий (gray)</td><td>§7</td><td>\u00A77</td><td>#AAAAAA</td></tr>
    <tr><td><ColorSwatch color="#555555" /></td><td>Темно-сірий (dark_gray)</td><td>§8</td><td>\u00A78</td><td>#555555</td></tr>
    <tr><td><ColorSwatch color="#5555FF" /></td><td>Синій (blue)</td><td>§9</td><td>\u00A79</td><td>#5555FF</td></tr>
    <tr><td><ColorSwatch color="#55FF55" /></td><td>Зелений (green)</td><td>§a</td><td>\u00A7a</td><td>#55FF55</td></tr>
    <tr><td><ColorSwatch color="#55FFFF" /></td><td>Водянистий (aqua)</td><td>§b</td><td>\u00A7b</td><td>#55FFFF</td></tr>
    <tr><td><ColorSwatch color="#FF5555" /></td><td>Червоний (red)</td><td>§c</td><td>\u00A7c</td><td>#FF5555</td></tr>
    <tr><td><ColorSwatch color="#FF55FF" /></td><td>Світло-фіолетовий (light_purple)</td><td>§d</td><td>\u00A7d</td><td>#FF55FF</td></tr>
    <tr><td><ColorSwatch color="#FFFF55" /></td><td>Жовтий (yellow)</td><td>§e</td><td>\u00A7e</td><td>#FFFF55</td></tr>
    <tr><td><ColorSwatch color="#FFFFFF" /></td><td>Білий (white)</td><td>§f</td><td>\u00A7f</td><td>#FFFFFF</td></tr>
    <tr><td></td><td>Скидання форматування</td><td>§r</td><td></td><td></td></tr>
    <tr><td></td><td><b>Жирний</b></td><td>§l</td><td></td><td></td></tr>
    <tr><td></td><td><s>Зачеркнутий</s></td><td>§m</td><td></td><td></td></tr>
    <tr><td></td><td><u>Підкреслений</u></td><td>§n</td><td></td><td></td></tr>
    <tr><td></td><td><i>Курсив</i></td><td>§o</td><td></td><td></td></tr>
    <tr><td></td><td>Зашифрований</td><td>§k</td><td></td><td></td></tr>
</tbody></table>
