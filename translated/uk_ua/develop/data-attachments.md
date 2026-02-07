---
title: Укладення даних
description: Посібник із базовим використанням нового API вкладення даних Fabric.
authors:
  - cassiancc
  - DennisOchulor
---

Data Attachment API є нещодавнім експериментальним доповненням до Fabric API. Це дозволяє розробникам легко приєднувати довільні дані до сутностей, блоків-сутностей, рівнів та чанків. Укладені дані можна зберігати та синхронізувати за допомогою кодеків [кодеків](./codecs) і потоків кодеків, тому вам слід ознайомитися з ними перед використанням.

## Створення вкладення даних {#creating-attachments}

Ви почнете з виклику `AttachmentRegistry.create`. У наведеному нижче прикладі створюється базове вкладення даних, яке не синхронізується та не зберігається під час перезапусків.

@[code lang=java transcludeWith=:::string](@/reference/latest/src/main/java/com/example/docs/attachment/ExampleModAttachments.java)

`AttachmentRegistry` містить кілька методів для створення основних укладених даних, зокрема:

- `AttachmentRegistry.create()`: створює вкладення даних. Перезапуск гри очистить вкладення.
- `AttachmentRegistry.createPersistent()`: створює вкладення даних, яке зберігатиметься між перезапусками гри.
- `AttachmentRegistry.createDefaulted()`: створює вкладення даних з усталеним значенням яке можна зчитати за допомогою `getAttachedOrCreate`. Перезапуск гри очистить вкладення.

Поведінку кожного методу також можна відтворити та додатково налаштувати за допомогою параметра `builder` для `create`, застосовуючи [шаблон ланцюжка методів](https://en.wikipedia.org/wiki/Method_chaining).

### Синхронізація вкладених даних {#syncing-attachments}

Якщо вам потрібно, щоб вкладення даних було постійним і синхронізованим між сервером і клієнтами, ви можете встановити таку поведінку за допомогою методу `create`, який дозволяє налаштовувати через ланцюжок `builder`. Наприклад:

@[code lang=java transcludeWith=:::pos](@/reference/latest/src/main/java/com/example/docs/attachment/ExampleModAttachments.java)

Наведений вище приклад синхронізується з кожним гравцем, але це може не відповідати вашому випадку використання. Ось деякі інші усталені предикати, але ви також можете створити власні, посилаючись на клас `AttachmentSyncPredicate`.

- `AttachmentSyncPredicate.all()`: синхронізує вкладення з усіма клієнтами.
- `AttachmentSyncPredicate.targetOnly()`: синхронізує вкладення лише з метою, до якої його прикріплено. Зауважте, що синхронізація можлива, лише якщо метою є гравець.
- `AttachmentSyncPredicate.allButTarget()`: синхронізує вкладення з кожним клієнтом, окрім цілі, до якої воно прикріплене. Зауважте, що виняток може застосовуватися, лише якщо ціль — гравець.

### Постійні вкладення даних {#persisting-attachments}

Також можна налаштувати збереження вкладених даних під час перезапуску гри, викликавши метод `persistent` у ланцюжку конструктора. Він використовує `кодек`, щоб гра знала, як серіалізувати дані.

За допомогою методу `copyOnDeath` вони можуть діяти навіть після смерті або [перетворення](https://minecraft.wiki/w/Mob_conversion) цілі.

@[code lang=java transcludeWith=:::persistent](@/reference/latest/src/main/java/com/example/docs/attachment/ExampleModAttachments.java)

## Зчитування з вкладених даних {#reading-attachments}

Методи для зчитування з вкладення даних було введено в класи `Entity`, `BlockEntity`, `ServerLevel` і `ChunkAccess`. Використовувати його так само просто, як викликати один із методів, які повертають значення вкладених даних.

```java
// Checks if the given AttachmentType has attached data, returning a boolean.
entity.hasAttached(EXAMPLE_STRING_ATTACHMENT);

// Gets the data associated with the given AttachmentType, or `null` if it doesn't exist.
entity.getAttached(EXAMPLE_STRING_ATTACHMENT);

// Gets the data associated with the given AttachmentType, throwing a `NullPointerException` if it doesn't exist.
entity.getAttachedOrThrow(EXAMPLE_STRING_ATTACHMENT);

// Gets the data associated with the given AttachmentType, setting the value if it doesn't exist.
entity.getAttachedOrSet(EXAMPLE_STRING_ATTACHMENT, "basic");
entity.getAttachedOrSet(EXAMPLE_BLOCK_POS_ATTACHMENT, new BlockPos(0, 0, 0););

// Gets the data associated with the given AttachmentType, returning the provided value if it doesn't exist.
entity.getAttachedOrElse(EXAMPLE_STRING_ATTACHMENT, "basic");
entity.getAttachedOrElse(EXAMPLE_BLOCK_POS_ATTACHMENT, new BlockPos(0, 0, 0););
```

## Уписання у вкладення даних {#writing-attachments}

Методи для вписування у вкладення даних було введено в класи `Entity`, `BlockEntity`, `ServerLevel` і `ChunkAccess`. Виклик одного з наведених нижче методів оновить значення вкладених даних і поверне попереднє значення (або `null`, якщо його не було).

```java
// Sets the data associated with the given AttachmentType, returning the previous value.
entity.setAttached(EXAMPLE_STRING_ATTACHMENT, "new value");

// Modifies the data associated with the given AttachmentType in place, returning the currently attached value. Note that currentValue is null if there is no previously attached data.
entity.modifyAttached(EXAMPLE_STRING_ATTACHMENT, currentValue -> "The length was " + (currentValue == null ? 0 : currentValue.length()));

// Removes the data associated with the given AttachmentType, returning the previous value.
entity.removeAttached(EXAMPLE_STRING_ATTACHMENT);
```

::: warning

Ви завжди повинні використовувати значення незмінних типів для вкладення даних, а також оновлювати їх лише за допомогою методів API. В іншому випадку вкладені дані можуть не зберігатися або синхронізуватися належним чином.

:::

## Більші вкладення {#larger-attachments}

Хоча вкладення даних можуть зберігати будь-яку форму даних, для якої можна написати кодек, вони сяють під час синхронізації окремих значень. Це пояснюється тим, що вкладення даних є незмінним: зміна частини його значення (наприклад, одного поля об’єкта) означає його повну заміну, що запускає повну синхронізацію для кожного клієнта, який його відстежує.

Натомість ви можете створити складніші вкладення, розділивши їх на кілька полів і впорядкувавши їх за допомогою допоміжного класу. Наприклад, якщо вам потрібні два поля, пов’язані з витривалістю гравця, ви можете створити щось на зразок цього:

@[code lang=java transcludeWith=:::stamina](@/reference/latest/src/main/java/com/example/docs/attachment/Stamina.java)

Потім цей допоміжний клас можна використовувати так:

```java
Player player = getPlayer();
Stamina.get(player).getCurrentStamina();
```
