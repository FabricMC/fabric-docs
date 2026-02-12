---
title: Типи шкоди
description: Навчіться додавати власні типи шкоди.
authors:
  - dicedpixels
  - hiisuuii
  - MattiDragon
---

Типи шкоди визначають яку шкоду, яку можуть отримати сутності. Починаючи з Minecraft 1.19.4, з'явилося створення нових типів шкоди стають керованими даними, тобто вони створюються за допомогою файлів JSON.

## Створення типу шкоди {#creating-a-damage-type}

Створімо спеціальний тип шкоди під назвою _Tater_. Почнімо з того, що створимо JSON файл для вашого типу шкоди. Цей файл буде розміщено в каталозі `data` вашого мода, у підкаталозі з назвою `damage_type`.

```text:no-line-numbers
resources/data/example-mod/damage_type/tater.json
```

Він має таку структуру:

@[code lang=json](@/reference/latest/src/main/generated/data/example-mod/damage_type/tater.json)

Цей спеціальний тип шкоди збільшує 0,1 [виснаження від голоду](https://uk.minecraft.wiki/w/%D0%93%D0%BE%D0%BB%D0%BE%D0%B4#%D0%97%D0%B1%D1%96%D0%BB%D1%8C%D1%88%D0%B5%D0%BD%D0%BD%D1%8F_%D1%80%D1%96%D0%B2%D0%BD%D1%8F_%C2%AB%D0%B2%D0%B8%D1%81%D0%BD%D0%B0%D0%B6%D0%B5%D0%BD%D0%BD%D1%8F%C2%BB) кожного разу, коли гравець отримує шкоду від живого джерела, проте не є гравцем (наприклад, блоком). Крім того, сума завданої шкоди буде збільшуватися зі складністю у світі

::: info

Зверніться до [вікі Minecraft](https://minecraft.wiki/w/Damage_type#JSON_format), щоб дізнатися про всі можливі ключі та значення.

:::

### Доступ до типів шкоди за допомогою коду {#accessing-damage-types-through-code}

Коли нам потрібно отримати доступ до нашого спеціального типу шкоди через код, ми будемо використовувати його `ResourceKey` для створення екземпляра з `DamageSource`.

`ResourceKey` можна отримати таким чином:

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/damage/ExampleModDamageTypes.java)

### Використання типів шкоди {#using-damage-types}

Щоб продемонструвати використання власних типів шкоди, ми використаємо власний блок, нехай це буде _Tater Block_. Зробімо так, що коли жива сутність наступає на _Tater Block_, вона отримує тип шкоди _Tater_.

Ви можете перевизначити `stepOn`, щоб завдати цієї шкоди.

Ми починаємо зі створення `DamageSource` нашого спеціального типу шкоди.

@[code lang=java transclude={22-26}](@/reference/latest/src/main/java/com/example/docs/damage/TaterBlock.java)

Потім ми викликаємо `entity.damage()` з нашим `DamageSource` і сумою.

@[code lang=java transclude={27-27}](@/reference/latest/src/main/java/com/example/docs/damage/TaterBlock.java)

Повна реалізація блока:

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/damage/TaterBlock.java)

Тепер щоразу, коли жива істота наступає на наш спеціальний блок, вона отримує 5 шкоди (2,5 серця), нашим типом шкоди.

### Власне сповіщення про смерть {#custom-death-message}

Ви можете визначити повідомлення про смерть для типу шкоди у форматі `death.attack.message_id` у нашому файл `en_us.json` (це англійська, для української створіть `uk_ua.json`) мода.

```json
{
  "death.attack.tater": "%1$s died from Tater damage!"
}
```

Після смерті від нашого типу шкоди ви побачите таке повідомлення про смерть:

![Ефект в інвентарі гравця](/assets/develop/tater-damage-death.png)

### Теґи типу шкоди {#damage-type-tags}

Деякі типи шкоди можуть обійти обладунки, ефекти тощо. Теґи використовуються для керування такими властивостями типів шкоди.

Ви можете знайти наявні теґи типу шкоди в `data/minecraft/tags/damage_type`.

::: info

Зверніться до [вікі Minecraft](https://uk.minecraft.wiki/w/%D0%A2%D0%B5%D2%91_%D1%82%D0%B8%D0%BF%D1%96%D0%B2_%D1%88%D0%BA%D0%BE%D0%B4%D0%B8_\(Java_Edition\)), щоб отримати повний список теґів типу шкоди.

:::

Додамо наш тип шкоди Tater до теґу типу шкоди `bypasses_armor`.

Щоб додати наш тип шкоди до одного з цих теґів, ми створюємо файл JSON у просторі імен `minecraft`.

```text:no-line-numbers
data/minecraft/tags/damage_type/bypasses_armor.json
```

З таким змістом:

@[code lang=json](@/reference/latest/src/main/generated/data/minecraft/tags/damage_type/bypasses_armor.json)

Переконайтеся, що ваш теґ не замінює наявний теґ, встановивши для ключа `replace` значення `false`.
