---
title: Атрибути сутності
description: Дізнайтеся, як додавати власні атрибути до сутностей.
authors:
  - cassiancc
  - cprodhomme
  - Tenneb22
resources:
  https://minecraft.wiki/w/Attribute: Атрибути — Вікі Minecraft
  https://docs.neoforged.net/docs/entities/attributes: Атрибути — Документація NeoForge (крім ексклюзивів Neo)
---

Атрибути визначають властивості, якими може володіти ваша модифікована сутність. Використовуючи Fabric, ви можете створювати власні атрибути, які покращують ігрову механіку, а також застосовувати ванільні атрибути.

## Створення власного атрибута {#creating-a-custom-attribute}

Створімо налаштовуваний атрибут під назвою `AGGRO_RANGE`. Цей атрибут контролюватиме відстань, на якій об’єкт може виявити потенційні загрози та реагувати на них.

### Визначення класу атрибута {#define-the-attribute-class}

Почніть зі створення класу Java для керування визначенням і реєстрацією ваших атрибутів у структурі коду вашого мода. У цьому прикладі буде створено такі функції в класі під назвою `ModAttributes`.

Спочатку почніть з основного допоміжного методу, щоб зареєструвати атрибути мода. Цей метод прийматиме такі параметри та реєструватиме атрибут.

- `String` буде назвою вашого атрибута
- `double`, яке буде усталеним значенням атрибута
- `double`, що буде найменшим значенням, якого досягне ваш атрибут
- `double`, що буде найвищим значенням, якого досягне ваш атрибут
- Логічне значення, яке визначає, чи буде атрибут синхронізовано з клієнтами

<<< @/reference/latest/src/main/java/com/example/docs/entity/attribute/ModAttributes.java#register

Потім ми зареєструємо атрибут під назвою `AGGRO_RANGE` з назвою `aggro_range`, усталеним значенням `8.0`, мінімальним значенням `0` і максимальним значенням, встановленим якомога вищим. Цей атрибут не буде синхронізовано з гравцями.

<<< @/reference/latest/src/main/java/com/example/docs/entity/attribute/ModAttributes.java#attributes

### Переклад спеціальних атрибутів {#attribute-translation}

Щоб показати назву атрибута в зручному для читання форматі, потрібно змінити `assets/example-mod/lang/en_us.json` (`uk_ua.json` для української), щоб включити:

```json
{
  "attribute.name.example-mod.aggro_range": "Aggro Range"
}
```

### Ініціалізація {#initialization}

Щоб переконатися, що атрибут зареєстровано належним чином, вам потрібно переконатися, що він ініціалізований під час запуску мода. Це можна зробити, додавши загальнодоступний статичний метод ініціалізації до свого класу та викликавши його з класу [ініціалізатора мода](../getting-started/project-structure#entrypoints). Наразі цей метод не потребує нічого всередині.

<<< @/reference/latest/src/main/java/com/example/docs/entity/attribute/ModAttributes.java#initialize

<<< @/reference/latest/src/main/java/com/example/docs/entity/attribute/ExampleModAttributes.java#init

Виклик методу в класі статично ініціалізує його, якщо він не був попередньо завантажений — це означає, що всі `static` поля оцінюються. Ось для чого цей фіктивний метод `initialize`.

## Застосування атрибутів {#apply-the-attribute}

Атрибути мають бути прикріплені до сутності, щоб набули чинності. Зазвичай це робиться в методі, де будуються або змінюються атрибути сутності.

Стандартна гра також надає атрибути, зокрема [максимальне здоров’я](https://minecraft.wiki/w/Attribute#Max_health), [швидкість руху](https://minecraft.wiki/w/Attribute#Movement_speed) і [шкода при атаці](https://minecraft.wiki/w/Attribute#Attack_damage). Щоб отримати повний список, дивіться стандартний клас `Attributes` і [Вікі Minecraft](https://minecraft.wiki/w/Attribute).

Це приклад того, як додати стандартні атрибути та раніше створений атрибут `AGGRO_RANGE` до сутності мініґолема з посібника [створення вашої першої сутності](./first-entity).

<<< @/reference/latest/src/main/java/com/example/docs/entity/MiniGolemEntity.java#attributes

## Читання та зміна атрибутів {#reading-modifying-attributes}

Атрибут сам по собі є просто даними, приєднаними до сутності. Щоб він був корисним, ми повинні мати можливість читати з нього та писати в нього. Є два основних способи зробити це: отримати `AttributeInstance` для сутності або отримати значення безпосередньо.

<<< @/reference/latest/src/gametest/java/com/example/docs/entity/EntityAttributesGameTest.java#reading_entity_attributes

`AttributeInstance` забезпечує більшу гнучкість, як-от встановлення `AttributeModifier` для атрибута, використовуючи одну з [трьох операцій модифікатора атрибутів](https://minecraft.wiki/w/Attribute#Operations). Модифікатори можуть бути постійними (збереженими в NBT) або транзитивними (не збереженими в NBT) і додаються за допомогою `addPermanentModifier` або `addTransitiveModifier` відповідно.

<<< @/reference/latest/src/gametest/java/com/example/docs/entity/EntityAttributesGameTest.java#modifying_entity_attributes

Отримавши доступ до значення атрибута, ви можете використовувати його в ШІ вашої сутності.
