---
title: Атрибуты сущности
description: Узнайте, как добавлять пользовательские атрибуты сущностям.
authors:
  - cassiancc
  - cprodhomme
resources:
  https://minecraft.wiki/w/Attribute: Атрибуты — Minecraft Wiki
  https://docs.neoforged.net/docs/entities/attributes: Атрибуты — Документация NeoFoge (кроме изменений Neo)
---

Атрибуты определяют свойства, которыми может обладать ваша сущность из мода. Используя Fabric, вы можете создавать собственные которые улучшают игровые механики и также используют ванильные.

## Создание пользовательского атрибута {#creating-a-custom-attribute}

Давайте создадим пользовательский атрибут и назовем его `AGGRO_RANGE`. Этот атрибут будет управлять дистанцией, на которой сущность может обнаруживать потенциальные угрозы и реагировать на них.

### Определение класса атрибута {#define-the-attribute-class}

Начните с создания Java-класса для управления определением и регистрацией ваших атрибутов в структуре кода вашего мода. В данном примере будут созданы следующие функции в классе с именем ModAttributes:

Сначала создайте базовый вспомогательный метод для регистрации атрибутов вашего мода. Этот метод будет принимать следующие параметры и регистрировать атрибут:

- `String` – имя вашего атрибута
- `double` – значение по умолчанию
- `double` – минимальное возможное значение
- `double` – максимальное возможное значение
- `boolean` – значение, определяющее, будет ли атрибут синхронизироваться с клиентами

@[code lang=java transcludeWith=:::register](@/reference/latest/src/main/java/com/example/docs/entity/attribute/ModAttributes.java)

Затем мы зарегистрируем атрибут с именем `AGGRO_RANGE` и идентификатором `aggro_range`, значением по умолчанию `8.0`, минимальным значением `0` и максимальным значением, установленным на предел типа double. Этот атрибут не будет синхронизироваться с игроками.

@[code lang=java transcludeWith=:::attributes](@/reference/latest/src/main/java/com/example/docs/entity/attribute/ModAttributes.java)

### Перевод пользовательских атрибутов {#attribute-translation}

Чтобы отобразить название атрибута в удобном для чтения формате, необходимо внести следующие изменения в файл `assets/example-mod/lang/en_us.json`:

```json
{
  "attribute.name.example-mod.aggro_range": "Aggro Range"
}
```

### Инициализация {#initialization}

Чтобы убедиться в правильной регистрации атрибута, вам необходимо инициализировать его при запуске мода. Для этого добавьте публичный статический метод инициализации в ваш класс и вызовите его из [главного класса инициализатора](../getting-started/project-structure#entrypoints) вашего мода. На данный момент этот метод может оставаться пустым.

@[code lang=java transcludeWith=:::initialize](@/reference/latest/src/main/java/com/example/docs/entity/attribute/ModAttributes.java)

@[code lang=java transcludeWith=:::init](@/reference/latest/src/main/java/com/example/docs/entity/attribute/ExampleModAttributes.java)

Статический вызов метода класса инициализирует его, если он не был загружен ранее — это означает, что все статические поля будут вычислены. Именно для этого и нужен пустой метод `initialize`.

## Применение атрибутов {#apply-the-attribute}

Чтобы атрибуты начали действовать, их необходимо прикрепить к сущности. Обычно это делается в методе, где создаются или изменяются атрибуты сущности.

Ванильная игра также предоставляет атрибуты, включая [максимальное здоровье](https://minecraft.wiki/w/Attribute#Max_health), [скорость передвижения](https://minecraft.wiki/w/Attribute#Movement_speed) и [урон от атаки](https://minecraft.wiki/w/Attribute#Attack_damage). Полный список можно найти в ванильном классе `Attributes` и на [Minecraft Wiki](https://minecraft.wiki/w/Attribute).

В качестве демонстрации мы добавим максимальное здоровье, скорость передвижения, урон от атаки и созданный ранее атрибут дальности агрессии.

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

## Чтение и изменение атрибутов {#reading-modifying-attributes}

Атрибут сам по себе — это просто данные, привязанные к сущности. Чтобы он приносил пользу, нам нужно уметь читать и записывать его значения. Для этого есть два основных способа: получение экземпляра атрибута (`AttributeInstance`) сущности или получение значения напрямую.

```java
entity.getAttribute(ModAttributes.AGGRO_RANGE) // returns an `AttributeInstance`
entity.getAttributeValue(ModAttributes.AGGRO_RANGE) // returns a double with the current value
entity.getAttributeBaseValue(ModAttributes.AGGRO_RANGE) // returns a double with the base value
```

Экземпляр атрибута (`AttributeInstance`) дает больше гибкости. Например, он позволяет установить модификатор атрибута (`AttributeModifier`), используя одну из [трех ванильных операций модификации](https://minecraft.wiki/w/Attribute#Operations). Модификаторы могут быть постоянными (сохраняются в NBT) или временными (не сохраняются в NBT) и добавляются через методы добавления модификаторов – `addPermanentModifier` и `addTransitiveModifier` соответственно.

```java
attribute.addPermanentModifier(
    new AttributeModifier(
        Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "increased_range"), // the ID of your modifier, should be static so it can be removed
        8, // how much to modify it
        AttributeModifier.Operation.ADD_VALUE // what operator to use, see the wiki page linked above
    ));
```

Как только вы получите доступ к значению атрибута, вы сможете использовать его в ИИ (искусственном интеллекте) вашей сущности.
