---
title: Текст и переводы
description: Подробная документация по работе Minecraft с форматированным текстом и переводами.
authors:
  - IMB11
  - LordEnder-Kitty
resources:
  https://docs.neoforged.net/docs/resources/client/i18n/: Интернационализация и локализация - Документация NeoForge (кроме datagen)
---

<!-- markdownlint-configure-file { MD033: { allowed_elements: [br, ColorSwatch, u] } } -->

Всякий раз, когда Minecraft отображает текст в игре, он, вероятно, определяется с помощью объекта `Component`.
Этот пользовательский тип используется вместо `String` для обеспечения более сложного форматирования,
включая цвета, выделение жирным шрифтом, обфускации и событий нажатия (click). Они также обеспечивают легкий доступ
в систему перевода, что упрощает перевод любого элемента пользовательского интерфейса в
разные языки.

Если вы раньше работали с пакетами данных (датапаками) или функциями, вы можете увидеть параллели с
текстовым форматом json, используемым для отображения названий, книг, табличек и другого. Как вы, скорее всего, догадались, это просто JSON-представление объекта `Component`, и для взаимного преобразования между ними можно использовать `Component.Serializer`.

При создании мода, предпочтительнее конструировать объекты `Component` прямо в коде, используя перевод, когда это возможно.

## Компоненты текстовых литералов {#literal-text-components}

Самый простой способ создать объект `Component` — это сделать литерал (простой текст). По сути это строка которая будет отображена как есть, без форматирования по умолчанию.

Они создаются с помощью методов `Component.nullToEmpty` и `Component.literal`, которые действуют немного по-разному. `Component.nullToEmpty` принимает значения `null` и возвращает экземпляр класса `Component`. `Component.literal` же наоборот не сможет принять значение null, и вернёт `MutableComponent`, являющийся наследуемым классом от `Component`, который можно легко стилизовать и соединять с другими. Подробнее об этом будет сказано позже.

```java
Component literal = Component.nullToEmpty("Hello, world!");
MutableComponent mutable = Component.literal("Hello, world!");
// Keep in mind that a MutableComponent can be used as a Component, making this valid:
Component mutableAsText = mutable;
```

## Компоненты переводимого текста {#translatable-text-components}

Если вы хотите предоставить несколько вариантов перевода для одной и той же строки текста, вы можете использовать метод `Component.translatable`, чтобы сослаться на ключ перевода в любом языковом файле. Если ключ не существует, ключ перевода конвертируется в литерал.

```java
Component translatable = Component.translatable("example-mod.text.hello");

// Similarly to literals, translatable text can be easily made mutable.
MutableComponent mutable = Component.translatable("example-mod.text.bye");
```

Файл языка, `en_us.json`, выглядит как то так:

```json
{
  "example-mod.text.hello": "Hello!",
  "example-mod.text.bye": "Goodbye :("
}
```

Если вы хотите иметь возможность использовать переменные в переводе, подобно тому, как сообщения о смерти позволяют вам использовать задействованных игроков и предметы в переводе, вы можете добавить указанные переменные в качестве параметров. Вы можете добавить сколько угодно параметров.

```java
Component translatable = Component.translatable("example-mod.text.hello", player.getDisplayName());
```

Вы можете ссылаться на эти переменные в переводе следующим образом:

```json
{
  "example-mod.text.hello": "%1$s said hello!"
}
```

В игре `%1$s` будет заменено на имя игрока, на которого вы сослались в коде. Использование `player.getDisplayName()` приведет к тому, что дополнительная информация об объекте появится во всплывающей подсказке при наведении курсора на имя в сообщении чата, в отличие от использования `player.getName()`, которое все равно получит имя; однако дополнительные детали не отображаются. Аналогичное можно сделать с itemStacks, используя `stack.getDisplayName()`.

Что касается того, что вообще означает `%1$s`, всё, что вам действительно нужно знать, это то, что это число соответствует переменной, которую вы пытаетесь использовать. Допустим, у вас есть три переменные, которые вы используете.

```java
Component translatable = Component.translatable("example-mod.text.whack.item", victim.getDisplayName(), attacker.getDisplayName(), itemStack.toHoverableText());
```

Если вы хотите сослаться на то, что в нашем случае является атакующим (attacker), вам нужно использовать `%2$s`, так как это вторая переданная нами переменная. Точно так же `%3$s` ссылается на itemStack. Перевод с таким количеством дополнительных параметров может выглядеть так:

```json
{
  "example-mod.text.whack.item": "%1$s was whacked by %2$s using %3$s"
}
```

## Десериализация Текста {#deserializing-text}

<!-- NOTE: These have been put into the example mod as they're likely to be updated to codecs in the next few updates. -->

Как было упомянуто выше, вы можете сериализовать текст в JSON используя текстовый кодек. Для большей информации об кодеках, посмотрите страницу [Codec](./codecs).

<<< @/reference/26.1.2/src/client/java/com/example/docs/rendering/TextTests.java#component_to_string

В результате получается JSON, который можно использовать в датапаках, командах и других местах которые принимают JSON формат текста вместо буквального или переводимого текста.

## Сериализация Текста {#serializing-text}

Кроме того, чтобы десериализовать текстовый объект в реальный класс `Component`, опять же, используйте кодек.

<<< @/reference/26.1.2/src/client/java/com/example/docs/rendering/TextTests.java#string_to_component

## Форматирование Текста {#formatting-text}

Вы можете быть знакомы со стандартом форматирования в Minecraft:

Вы можете применить эти стили форматирования, используя перечисление `ChatFormatting` в классе `MutableComponent`:

```java
MutableComponent result = Component.literal("Hello World!")
  .withStyle(ChatFormatting.AQUA, ChatFormatting.BOLD, ChatFormatting.UNDERLINE);
```

|               Цвет              | Название                                                               | Код в чате | Код для MOTD | HEX-код |
| :-----------------------------: | ---------------------------------------------------------------------- | :--------: | :----------: | :-----: |
| <ColorSwatch color="#000000" /> | Чёрный (black)                                      |     §0     |    \u00A70   | #000000 |
| <ColorSwatch color="#0000AA" /> | Тёмно-синий (dark_blue)        |     §1     |    \u00A71   | #0000AA |
| <ColorSwatch color="#00AA00" /> | Тёмно-зелёный (dark_green)     |     §2     |    \u00A72   | #00AA00 |
| <ColorSwatch color="#00AAAA" /> | Тёмно-голубой (dark_aqua)      |     §3     |    \u00A73   | #00AAAA |
| <ColorSwatch color="#AA0000" /> | Тёмно-красный (dark_red)       |     §4     |    \u00A74   | #AA0000 |
| <ColorSwatch color="#AA00AA" /> | Тёмно-фиолетовый (dark_purple) |     §5     |    \u00A75   | #AA00AA |
| <ColorSwatch color="#FFAA00" /> | Золотой (gold)                                      |     §6     |    \u00A76   | #FFAA00 |
| <ColorSwatch color="#AAAAAA" /> | Серый (gray)                                        |     §7     |    \u00A77   | #AAAAAA |
| <ColorSwatch color="#555555" /> | Тёмно-серый (dark_gray)        |     §8     |    \u00A78   | #555555 |
| <ColorSwatch color="#5555FF" /> | Синий (blue)                                        |     §9     |    \u00A79   | #5555FF |
| <ColorSwatch color="#55FF55" /> | Зелёный (green)                                     |     §a     |    \u00A7a   | #55FF55 |
| <ColorSwatch color="#55FFFF" /> | Аква<br />`aqua`                                                       |     §b     |    \u00A7b   | #55FFFF |
| <ColorSwatch color="#FF5555" /> | Красный (red)                                       |     §c     |    \u00A7c   | #FF5555 |
| <ColorSwatch color="#FF55FF" /> | Фиолетовый (light_purple)      |     §d     |    \u00A7d   | #FF55FF |
| <ColorSwatch color="#FFFF55" /> | Жёлтый (yellow)                                     |     §e     |    \u00A7e   | #FFFF55 |
| <ColorSwatch color="#FFFFFF" /> | Белый (white)                                       |     §f     |    \u00A7f   | #FFFFFF |
|                                 | Сброс форматирования                                                   |     §r     |              |         |
|                                 | **Жирный**                                                             |     §l     |              |         |
|                                 | ~~Зачеркивание~~                                                       |     §m     |              |         |
|                                 | <u>Подчёркнутый</u>                                                    |     §n     |              |         |
|                                 | _курсив_                                                               |     §o     |              |         |
|                                 | Зашифрованный                                                          |     §k     |              |         |
