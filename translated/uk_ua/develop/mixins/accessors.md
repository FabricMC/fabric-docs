---
title: Міксин доступу
description: Дізнайтеся, як отримати доступ до методів і полів за допомогою засобів доступу та викликів міксина.
authors:
  - Bawnorton
  - bluebear94
  - cassiancc
  - MildestToucan
---

Міксини зазвичай використовуються для модифікації наявного коду для створення та налаштування поведінки. Однак Mixin також надає інструменти доступу до недоступних полів і методів у вигляді міксинів доступу.

[Твікери класу](../class-tweakers) надає подібний інструмент у формі [розширювачів доступу](../class-tweakers/access-widening), але міксини доступу не вимагають перезавантаження Gradle і можуть застосовуватися до цілей, які не належать до Minecraft.

Розширення доступу все ще необхідне для перевизначення кінцевих методів або підкласів кінцевих класів, або для посилання на приватні класи, оскільки засоби доступу можуть орієнтуватися лише на поля та методи.

## Створення інтерфейсу доступу {#creating-the-accessor-interface}

Міксини доступу мають завжди бути інтерфейсом і містити лише методи, анотовані `@Accessor` або `@Invoker`. Інтерфейс має бути анотований `@Mixin`, як і інші класи міксинів.

Інтерфейси доступу традиційно називаються за цільовим класом із суфіксом `Accessor` і розміщуються у підпакеті `accessor` у вашому пакеті міксинів.
Тобто `ваш.пакет.міксинів.доступу`

## Поля доступу {#field-accessors}

Доступ до полів можна отримати за допомогою методів анотованих геттерів та/або сеттерів за допомогою `@Accessor`:

::: tabs

== Поле екземпляра

Синтаксис геттера/сеттера:

Методи доступу до екземплярів повинні мати префікс ID вашого мода та роздільника (зазвичай `$` або `_`), щоб переконатися, що він не конфліктує з будь-яким іншим методом.

```java
@Accessor("<field name>")
FieldType example_mod$getFieldName();

@Accessor("<field name>")
void example_mod$setFieldName(FieldType value);
```

Приклад:

<<< @/reference/latest/src/client/java/com/example/docs/mixin/client/accessor/GuiAccessor.java#mixin_accessors_instance_field_accessor_example

Використання:

<<< @/reference/latest/src/client/java/com/example/docs/accessor_usage/client/ExampleModAccessorUsageClient.java#mixin_accessors_instance_field_accessor_example_usage

== Статичне поле

Синтаксис геттера/сеттера:

Для статичних полів префікс ID мода непотрібний, оскільки статичні методи не можуть конфліктувати один з одним.

Щоб задовольнити компілятор Java, ми робимо тіла статичних методів доступу завжди викиданими, хоча вони завжди будуть реалізовані міксином під час застосування.

```java
@Accessor("<field name>")
static FieldType getFieldName() {
    throw new AssertionError("Untransformed @Accessor");
}

@Accessor("<field name>")
static void setFieldName(FieldType value) {
}
```

Приклад:

<<< @/reference/latest/src/main/java/com/example/docs/mixin/accessor/ClientboundCustomPayloadPacketAccessor.java#mixin_accessors_static_field_accessor_example

Використання:

<<< @/reference/latest/src/main/java/com/example/docs/accessor_usage/ExampleModAccessorUsage.java#mixin_accessors_static_field_accessor_example_usage

:::

### Налаштування кінцевих полів {#setting-final-fields}

Якщо цільове поле є остаточним, додайте до методу налаштування засобу доступу `@Mutable`, щоб видалити позначку кінцевого під час застосування:

<<< @/reference/latest/src/main/java/com/example/docs/mixin/accessor/VillagerAccessor.java#mixin_accessors_mutable_setter_example

## Викликачі метода {#method-invokers}

Щоб викликати недоступні методи або конструктори, створіть метод, який відповідає сигнатурі, анотованій `@Invoker`:

::: tabs

== Метод екземпляра

Синтаксис:

Методи виклику екземплярів повинні мати префікс ID вашого мода та роздільника (зазвичай `$` або `_`), щоб переконатися, що він не конфліктує з будь-яким іншим методом.

Методи виклику зазвичай називаються безпосередньо після цільового методу, `invoke` або `call`, за якими йде назва методу.

```java
@Invoker("<method name>")
MethodReturnType example_mod$methodName(/* matching parameters */)
```

Приклад:

<<< @/reference/latest/src/main/java/com/example/docs/mixin/accessor/InventoryAccessor.java#mixin_accessors_instance_invoker_example

Використання:

<<< @/reference/latest/src/main/java/com/example/docs/accessor_usage/ExampleModAccessorUsage.java#mixin_accessors_instance_invoker_example_usage

== Статичний метод

Синтаксис:

Для статичних методів префікс ID мода непотрібний, оскільки статичні методи не можуть конфліктувати один з одним.

Щоб задовольнити компілятор Java, ми робимо тіла статичних методів виклику завжди викиданими, хоча вони завжди будуть реалізовані міксином під час застосування.

```java
@Invoker("<method name>")
static MethodReturnType invokeMethodName(/* matching parameters */) {
    throw new AssertionError("Untransformed @Accessor");
}
```

Приклад:

<<< @/reference/latest/src/main/java/com/example/docs/mixin/accessor/ShulkerBoxBlockAccessor.java#mixin_accessors_static_invoker_example

Використання:

<<< @/reference/latest/src/main/java/com/example/docs/accessor_usage/ExampleModAccessorUsage.java#mixin_accessors_static_invoker_example_usage

== Конструктор

Синтаксис:

Методи виклику конструктора зазвичай мають назви `createTargetClass` або `newTargetClass`, завжди є `static` і завжди використовують `<init>` як значення анотації `@Invoker`.

```java
@Invoker("<init>")
static TargetClass newTargetClass(/* matching parameters */)
```

Приклад:

<<< @/reference/latest/src/main/java/com/example/docs/mixin/accessor/IdentifierAccessor.java#mixin_accessors_constructor_invoker_example

Використання:

<<< @/reference/latest/src/main/java/com/example/docs/accessor_usage/ExampleModAccessorUsage.java#mixin_accessors_constructor_invoker_example_usage

:::

## Використання засобів доступу для кінцевих класів {#accessors-for-final-classes}

Оскільки компілятор Java суворіший щодо кінцевих класів, він не дозволяє нам безпосередньо передати його екземпляри до інтерфейсу доступу.

Щоб обійти це, нам потрібно лише спочатку привести до `Object`, а потім до інтерфейсу доступу:

```java
((TargetClassAccessor) (Object) targetClassInstance).example_mod$accessorMethod(/* ... */)
```
