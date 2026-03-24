---
title: Атрибути сутності
description: Дізнайтеся, як додавати власні атрибути до сутностей.
authors:
  - cassiancc
  - cprodhomme
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

@[code lang=java transcludeWith=:::register](@/reference/latest/src/main/java/com/example/docs/entity/attribute/ModAttributes.java)

Потім ми зареєструємо атрибут під назвою `AGGRO_RANGE` з назвою `aggro_range`, усталеним значенням `8.0`, мінімальним значенням `0` і максимальним значенням, встановленим якомога вищим. Цей атрибут не буде синхронізовано з гравцями.

@[code lang=java transcludeWith=:::attributes](@/reference/latest/src/main/java/com/example/docs/entity/attribute/ModAttributes.java)

### Переклад спеціальних атрибутів {#attribute-translation}

Щоб показати назву атрибута в зручному для читання форматі, потрібно змінити `assets/example-mod/lang/en_us.json` (`uk_ua.json` для української), щоб включити:

```json
{
  "attribute.name.example-mod.aggro_range": "Aggro Range"
}
```

### Ініціалізація {#initialization}

Щоб переконатися, що атрибут зареєстровано належним чином, вам потрібно переконатися, що він ініціалізований під час запуску мода. Це можна зробити, додавши загальнодоступний статичний метод ініціалізації до свого класу та викликавши його з класу [ініціалізатора мода](../getting-started/project-structure#entrypoints). Наразі цей метод не потребує нічого всередині.

@[code lang=java transcludeWith=:::initialize](@/reference/latest/src/main/java/com/example/docs/entity/attribute/ModAttributes.java)

@[code lang=java transcludeWith=:::init](@/reference/latest/src/main/java/com/example/docs/entity/attribute/ExampleModAttributes.java)

Виклик методу в класі статично ініціалізує його, якщо він не був попередньо завантажений — це означає, що всі `статичні` поля оцінюються. Ось для чого цей фіктивний метод `ініціалізації`.

## Застосування атрибутів {#apply-the-attribute}

Атрибути мають бути прикріплені до сутності, щоб набули чинності. Зазвичай це робиться в методі, де будуються або змінюються атрибути сутності.

Стандартна гра також надає атрибути, зокрема [максимальне здоров’я](https://minecraft.wiki/w/Attribute#Max_health), [швидкість руху](https://minecraft.wiki/w/Attribute#Movement_speed) і [шкода від атаки](https://minecraft.wiki/w/Attribute#Attack_damage), як показано нижче. Щоб отримати повний список, дивіться стандартний клас `Attributes` і [вікі Minecraft](https://minecraft.wiki/w/Attribute).

У якості демонстрації ми включимо максимальне здоров’я, швидкість руху, шкоду від атаки та атрибут агродальності, створений раніше.

<!-- TODO: move to the reference mod -->

```java
public static AttributeSupplier.Builder createEntityAttributes() {
    return Mob.createMobAttributes()
        .add(Attributes.MAX_HEALTH, 25.0)
        .add(Attributes.MOVEMENT_SPEED, 0.22)
        .add(Attributes.ATTACK_DAMAGE, 3.0)
        .add(ModAttributes.AGGRO_RANGE, 8.0);
}
```

## Читання та зміна атрибутів {#reading-modifying-attributes}

Атрибут сам по собі є просто даними, приєднаними до сутності. Щоб він був корисним, ми повинні мати можливість читати з нього та писати в нього. Є два основних способи зробити це: отримати `AttributeInstance` для сутності або отримати значення безпосередньо.

```java
entity.getAttribute(ModAttributes.AGGRO_RANGE) // returns an `AttributeInstance`
entity.getAttributeValue(ModAttributes.AGGRO_RANGE) // returns a double with the current value
entity.getAttributeBaseValue(ModAttributes.AGGRO_RANGE) // returns a double with the base value
```

`AttributeInstance` забезпечує більшу гнучкість, як-от встановлення `AttributeModifier` для атрибута, використовуючи одну з [трьох операцій модифікатора атрибутів](https://minecraft.wiki/w/Attribute#Operations). Модифікатори можуть бути постійними (збереженими в NBT) або транзитивними (не збереженими в NBT) і додаються за допомогою `addPermanentModifier` або `addTransitiveModifier` відповідно.

```java
attribute.addPermanentModifier(
    new AttributeModifier(
        Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "increased_range"), // the ID of your modifier, should be static so it can be removed
        8, // how much to modify it
        AttributeModifier.Operation.ADD_VALUE // what operator to use, see the wiki page linked above
    ));
```

Отримавши доступ до значення атрибута, ви можете використовувати його в ШІ вашої сутності.
