---
title: Атрибуты сущности
description: Узнайте, как добавлять пользовательские атрибуты сущностям.
authors:
  - cassiancc
  - cprodhomme
  - Tenneb22
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

<<< @/reference/26.1.2/src/main/java/com/example/docs/entity/attribute/ModAttributes.java#register

Затем мы зарегистрируем атрибут с именем `AGGRO_RANGE` и идентификатором `aggro_range`, значением по умолчанию `8.0`, минимальным значением `0` и максимальным значением, установленным на предел типа double. Этот атрибут не будет синхронизироваться с игроками.

<<< @/reference/26.1.2/src/main/java/com/example/docs/entity/attribute/ModAttributes.java#attributes

### Перевод пользовательских атрибутов {#attribute-translation}

Чтобы отобразить название атрибута в удобном для чтения формате, необходимо внести следующие изменения в файл `assets/example-mod/lang/en_us.json`:

```json
{
  "attribute.name.example-mod.aggro_range": "Aggro Range"
}
```

### Инициализация {#initialization}

Чтобы убедиться в правильной регистрации атрибута, вам необходимо инициализировать его при запуске мода. Для этого добавьте публичный статический метод инициализации в ваш класс и вызовите его из [главного класса инициализатора](../getting-started/project-structure#entrypoints) вашего мода. На данный момент этот метод может оставаться пустым.

<<< @/reference/26.1.2/src/main/java/com/example/docs/entity/attribute/ModAttributes.java#initialize

<<< @/reference/26.1.2/src/main/java/com/example/docs/entity/attribute/ExampleModAttributes.java#init

Статический вызов метода класса инициализирует его, если он не был загружен ранее — это означает, что все статические поля будут вычислены. Именно для этого и нужен пустой метод `initialize`.

## Применение атрибутов {#apply-the-attribute}

Чтобы атрибуты начали действовать, их необходимо прикрепить к сущности. Обычно это делается в методе, где создаются или изменяются атрибуты сущности.

В Ваниле также предусмотрены атрибуты, включая [максимальное здоровье](https://minecraft.wiki/w/Attribute#Max_health), [скорость передвижения](https://minecraft.wiki/w/Attribute#Movement_speed) и [урон от атаки](https://minecraft.wiki/w/Attribute#Attack_damage). Полный список см. в ванильном классе `Attributes` и на [Minecraft Wiki](https://minecraft.wiki/w/Attribute).

Это пример того, как добавить ванильные атрибуты и ранее созданный атрибут `AGGRO_RANGE` к сущности «Мини-голем» из руководства [Создание вашей первой сущности](./first-entity).

<<< @/reference/26.1.2/src/main/java/com/example/docs/entity/MiniGolemEntity.java#attributes

## Чтение и изменение атрибутов {#reading-modifying-attributes}

Атрибут сам по себе — это просто данные, привязанные к сущности. Чтобы он приносил пользу, нам нужно уметь читать и записывать его значения. Для этого есть два основных способа: получение экземпляра атрибута (`AttributeInstance`) сущности или получение значения напрямую.

<<< @/reference/26.1.2/src/gametest/java/com/example/docs/entity/EntityAttributesGameTest.java#reading_entity_attributes

Экземпляр атрибута (`AttributeInstance`) дает больше гибкости. Например, он позволяет установить модификатор атрибута (`AttributeModifier`), используя одну из [трех ванильных операций модификации](https://minecraft.wiki/w/Attribute#Operations). Модификаторы могут быть постоянными (сохраняются в NBT) или временными (не сохраняются в NBT) и добавляются через методы добавления модификаторов – `addPermanentModifier` и `addTransitiveModifier` соответственно.

<<< @/reference/26.1.2/src/gametest/java/com/example/docs/entity/EntityAttributesGameTest.java#modifying_entity_attributes

Как только вы получите доступ к значению атрибута, вы сможете использовать его в ИИ (искусственном интеллекте) вашей сущности.
