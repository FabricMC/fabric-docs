---
title: Типи шкоди
description: Навчіться додавати власні типи шкоди.
authors:
  - dicedpixels
  - hiisuuii
  - mattidragon
---

# Типи шкоди {#damage-types}

Типи шкоди визначають типи шкоди, яку можуть отримати сутності. Починаючи з Minecraft 1.19.4, з'явилося створення нових типів пошкоджень
стають керованими даними, тобто вони створюються за допомогою файлів JSON.

## Створення типу шкоди {#creating-a-damage-type}

Створімо спеціальний тип шкоди під назвою _Tater_. Почнімо з того, що створимо JSON файл для вашого типу шкоди. Цей файл
буде розміщено в каталозі `data` вашого мода, у підкаталозі з назвою `damage_type`.

```:no-line-numbers
resources/data/fabric-docs-reference/damage_type/tater.json
```

Він має таку структуру:

@[code lang=json](@/reference/latest/src/main/generated/data/fabric-docs-reference/damage_type/tater.json)

Цей спеціальний тип шкоди збільшує 0,1
у [виснаженні від голоду](https://minecraft.wiki/w/Hunger#Exhaustion_level_increase) кожного разу, коли гравець отримує шкоду, коли
шкоду нанесено живим джерелом, проте не є гравцем (наприклад, блоком). Крім того, сума завданої шкоди буде збільшуватися
зі складністю у світі

::: info

Зверніться до [Minecraft Wiki](https://minecraft.wiki/w/Damage_type#JSON_format), щоб дізнатися про всі можливі ключі та значення.

:::

### Доступ до типів шкоди за допомогою коду {#accessing-damage-types-through-code}

Коли нам потрібно отримати доступ до нашого спеціального типу пошкодження через код, ми будемо використовувати його `RegistryKey` для створення екземпляра
з `DamageSource`.

`RegistryKey` можна отримати таким чином:

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/damage/FabricDocsReferenceDamageTypes.java)

### Використання типів шкоди {#using-damage-types}

Щоб продемонструвати використання власних типів шкоди, ми використаємо власний блок, нехай це буде _Tater Block_. Зробімо так, що
коли жива істота наступає на _Tater Block_, вона отримує тип шкоди _Tater _.

Ви можете перевизначити `onSteppedOn`, щоб завдати цієї шкоди.

Ми починаємо зі створення `DamageSource` нашого спеціального типу шкоди.

@[code lang=java transclude={22-26}](@/reference/latest/src/main/java/com/example/docs/damage/TaterBlock.java)

Потім ми викликаємо `entity.damage()` з нашим `DamageSource` і сумою.

@[code lang=java transclude={27-27}](@/reference/latest/src/main/java/com/example/docs/damage/TaterBlock.java)

Повна реалізація блоку:

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/damage/TaterBlock.java)

Тепер щоразу, коли жива істота наступає на наш спеціальний блок, вона отримує 5 шкоди (2,5 серця), нашим типом шкоди.

### Власне сповіщення про смерть

Ви можете визначити повідомлення про смерть для типу шкоди у форматі `death.attack.<message_id>` у нашому
файл `en_us.json`(це англійська, для української створіть `uk_ua.json`) мода.

```json
{
  // ...
  "death.attack.tater": "%1$s died from Tater damage!",
  // ...
}
```

Після смерті від нашого типу шкоди ви побачите таке повідомлення про смерть:

![Ефект в інвентарі гравця](/assets/develop/tater-damage-death.png)

### Теґи типу шкоди {#damage-type-tags}

Деякі типи шкоди можуть обійти обладунки, ефекти тощо. Теґи використовуються для керування такими властивостями типів шкоди.

Ви можете знайти наявні теґи типу шкоди в `data/minecraft/tags/damage_type`.

::: info

Зверніться до [Minecraft Wiki](https://minecraft.wiki/w/Tag#Damage_types), щоб отримати повний список теґів типу шкоди.

:::

Додамо наш тип шкоди Tater до теґу типу шкоди `bypasses_armor`.

Щоб додати наш тип шкоди до одного з цих теґів, ми створюємо файл JSON у просторі імен `minecraft`.

```:no-line-numbers
data/minecraft/tags/damage_type/bypasses_armor.json
```

З таким змістом:

@[code lang=json](@/reference/latest/src/main/generated/data/minecraft/tags/damage_type/bypasses_armor.json)

Переконайтеся, що ваш теґ не замінює наявний теґ, встановивши для ключа `replace` значення `false`.
