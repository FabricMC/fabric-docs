---
title: Текст и переводы
description: Подробная документация по работе Minecraft с форматированным текстом и переводами.
authors:
  - IMB11
---

# Текст и переводы {#text-and-translations}

Всякий раз, когда Minecraft отображает текст в игре, он, в большинстве случаев, определяется с помощью объекта "Text".
Этот пользовательский тип используется вместо `String` для обеспечения более сложного форматирования,
включая цвета, выделение жирным шрифтом, обфускации и событий нажатия (click). Они также обеспечивают легкий доступ
к системе перевода, упрощая локализацию любых элементов интерфейса на
разные языки.

Если вы раньше работали с пакетами данных (датапаками) или функциями, вы можете увидеть параллели с
текстовым форматом json, используемым для отображения названий, книг, табличек и другого. Как вы, возможно, догадываетесь, это на самом деле json-представление объекта `Text`, и его можно
преобразовать в `Text.Serializer` и обратно с помощью Text.Serializer\`.

При создании мода, предпочтительнее создавать свои `Text` объекты непосредственно в коде, используя перевод, когда это возможно.

## Текстовые литералы {#text-literals}

Самый простой способ создать объект `Text` это использовать литерал. По сути это строка которая будет отображена как есть, без форматирования по умолчанию.

Они создаются с помощью методов `Text.of` и `Text.literal`, которые действуют немного по-разному. `Text.of` принимает значения `null` в себя и возвращает экземпляр класса `Text`. `Text.literal` же наоборот не сможет принять значение null, и вернёт`MutableText` являющийся наследуемым классом от `Text`, который можно легко стилизовать и конкатенировать. Подробнее об этом будет сказано позже.

```java
Text literal = Text.of("Hello, world!");
MutableText mutable = Text.literal("Hello, world!");
// Keep in mind that a MutableText can be used as a Text, making this valid:
Text mutableAsText = mutable;
```

## Переводимый Текст {#translatable-text}

Когда вы хотите предоставить несколько переводов для одной и той же строчки текста, вы можете использовать метод `Text.translatable` для ссылки на ключ перевода в любом языковом файле. Если ключ не существует, ключ перевода конвертируется в литерал.

```java
Text translatable = Text.translatable("my_mod.text.hello");

// Similarly to literals, translatable text can be easily made mutable.
MutableText mutable = Text.translatable("my_mod.text.bye");
```

Файл языка, `en_us.json`, выглядит как то так:

```json
{
  "my_mod.text.hello": "Hello!",
  "my_mod.text.bye": "Goodbye :("
}
```

## Сериализация Текста {#serializing-text}

<!-- NOTE: These have been put into the reference mod as they're likely to be updated to codecs in the next few updates. -->

Как было упомянуто выше, вы можете сериализовать текст в JSON используя текстовый кодек. Для большей информации об кодеках, посмотрите страницу [Codec](./codecs).

@[code transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/TextTests.java)

В результате получается JSON, который можно использовать в датапаках, командах и других местах которые принимают JSON формат текста вместо буквального или переводимого текста.

## Десериализация Текста {#deserializing-text}

Кроме того, чтобы десериализовать текстовый объект в реальный класс `Text`, опять же, используйте кодек.

@[code transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/TextTests.java)

## Форматирование Текста {#formatting-text}

Вы можете быть знакомы со стандартом форматирования в Minecraft:

Вы можете применить эти форматирования с помощью перечисления `Formatting` в классе `MutableText`:

```java
MutableText result = Text.literal("Hello World!")
  .formatted(Formatting.AQUA, Formatting.BOLD, Formatting.UNDERLINE);
```

<table>
    <tbody><tr><th>Цвет</th><th>Название</th><th>Код в чате</th><th>Код для MOTD</th><th>Hex код</th></tr>
    <tr><td><ColorSwatch color="#000000" /></td><td>Чёрный (black)</td><td>§0</td><td>\u00A70</td><td>#000000</td></tr>
    <tr><td><ColorSwatch color="#0000AA" /></td><td>Тёмно-синий (dark_blue)</td><td>§1</td><td>\u00A71</td><td>#0000AA</td></tr>
    <tr><td><ColorSwatch color="#00AA00" /></td><td>Тёмно-зелёный (dark_green)</td><td>§2</td><td>\u00A72</td><td>#00AA00</td></tr>
    <tr><td><ColorSwatch color="#00AAAA" /></td><td>Тёмно-голубой (dark_aqua)</td><td>§3</td><td>\u00A73</td><td>#00AAAA</td></tr>
    <tr><td><ColorSwatch color="#AA0000" /></td><td>Тёмно-красный (dark_red)</td><td>§4</td><td>\u00A74</td><td>#AA0000</td></tr>
    <tr><td><ColorSwatch color="#AA00AA" /></td><td>Тёмно-фиолетовый (dark_purple)</td><td>§5</td><td>\u00A75</td><td>#AA00AA</td></tr>
    <tr><td><ColorSwatch color="#FFAA00" /></td><td>Золотой (gold)</td><td>§6</td><td>\u00A76</td><td>#FFAA00</td></tr>
    <tr><td><ColorSwatch color="#AAAAAA"/></td><td>Серый (gray)</td><td>§7</td><td>\u00A77</td><td>#AAAAAA</td></tr>
    <tr><td><ColorSwatch color="#555555" /></td><td>Тёмно-серый (dark_gray)</td><td>§8</td><td>\u00A78</td><td>#555555</td></tr>
    <tr><td><ColorSwatch color="#5555FF" /></td><td>Синий (blue)</td><td>§9</td><td>\u00A79</td><td>#5555FF</td></tr>
    <tr><td><ColorSwatch color="#55FF55" /></td><td>Зелёный (green)</td><td>§a</td><td>\u00A7a</td><td>#55FF55</td></tr>
    <tr><td><ColorSwatch color="#55FFFF" /></td><td>Голубой (aqua)</td><td>§b</td><td>\u00A7b</td><td>#55FFFF</td></tr>
    <tr><td><ColorSwatch color="#FF5555" /></td><td>Красный (red)</td><td>§c</td><td>\u00A7c</td><td>#FF5555</td></tr>
    <tr><td><ColorSwatch color="#FF55FF" /></td><td>Фиолетовый (light_purple)</td><td>§d</td><td>\u00A7d</td><td>#FF55FF</td></tr>
    <tr><td><ColorSwatch color="#FFFF55" /></td><td>Жёлтый (yellow)</td><td>§e</td><td>\u00A7e</td><td>#FFFF55</td></tr>
    <tr><td><ColorSwatch color="#FFFFFF" /></td><td>Белый (white)</td><td>§f</td><td>\u00A7f</td><td>#FFFFFF</td></tr>
    <tr><td></td><td>Сброс форматирования</td><td>§r</td><td></td><td></td></tr>
    <tr><td></td><td><b>Жирный</b></td><td>§l</td><td></td><td></td></tr>
    <tr><td></td><td><s>Зачёркнутый</s></td><td>§m</td><td></td><td></td></tr>
    <tr><td></td><td><u>Подчёркнутый</u></td><td>§n</td><td></td><td></td></tr>
    <tr><td></td><td><i>Курсив</i></td><td>§o</td><td></td><td></td></tr>
    <tr><td></td><td>Зашифрованный</td><td>§k</td><td></td><td></td></tr>
</tbody></table>
