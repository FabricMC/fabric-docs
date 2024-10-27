---
title: Типы урона
description: Узнайте, как добавлять пользовательские типы урона.
authors:
  - dicedpixels
  - hiisuuii
  - mattidragon

search: false---

# Типы урона {#damage-types}

Типы урона определяют типы повреждений, которые могут получить сущности. Начиная с Minecraft 1.19.4, создание новых типов урона стало управляться данными, то есть они создаются с использованием файлов JSON.

## Создание типа урона {#creating-a-damage-type}

Давайте создадим собственный тип повреждений под названием _Tater_. Начнем с создания JSON-файла для вашего индивидуального типа урона. Этот файл
будет
помещён в директорию вашего мода: `data`, в подпапку с названием `damage_type`.

```:no-line-numbers
resources/data/fabric-docs-reference/damage_type/tater.json
```

Он имеет следующую структуру:

@[code lang=json](@/reference/1.21/src/main/generated/data/fabric-docs-reference/damage_type/tater.json)

Этот пользовательский тип урона увеличивает [истощение голодом](https://minecraft.wiki/w/Hunger#Exhaustion_level_increase) на 0,1 каждый раз, когда игрок получает урон, когда урон наносится живым, неигровым источником (например, блоком). Кроме того, количество нанесенного урона будет масштабироваться в зависимости от сложности мира

::: info

Все возможные ключи и значения можно найти на [Minecraft Wiki](https://minecraft.wiki/w/Damage_type#JSON_format).

:::

### Доступ к типам урона через код {#accessing-damage-types-through-code}

Когда нам нужно получить доступ к нашему пользовательскому типу урона через код, мы будем использовать его `RegistryKey` для создания экземпляра `DamageSource`.

`RegistryKey` можно получить следующим образом:

@[code lang=java transcludeWith=:::1](@/reference/1.21/src/main/java/com/example/docs/damage/FabricDocsReferenceDamageTypes.java)

### Использование типов урона {#using-damage-types}

Чтобы продемонстрировать использование пользовательских типов урона, мы будем использовать пользовательский блок под названием _Tater Block_. Давайте сделаем так, что когда живое существо наступает на _Tater Block_, оно наносит _Tater_ урон.

Вы можете переопределить `onSteppedOn`, чтобы нанести этот урон.

Начнем с создания `DamageSource` нашего пользовательского типа урона.

@[code lang=java transclude={21-24}](@/reference/1.21/src/main/java/com/example/docs/damage/TaterBlock.java)

Затем мы вызываем `entity.damage()` с нашим `DamageSource` и суммой.

@[code lang=java transclude={25-25}](@/reference/1.21/src/main/java/com/example/docs/damage/TaterBlock.java)

Полная реализация блока:

@[code lang=java transcludeWith=:::1](@/reference/1.21/src/main/java/com/example/docs/damage/TaterBlock.java)

Теперь, когда живое существо наступит на наш блок, оно получит 5 единиц урона (2,5 сердца) с использованием нашего типа урона.

### Пользовательское сообщение о смерти {#custom-death-message}

Вы можете определить сообщение о смерти для типа урона в формате `death.attack.<message_id>` в файле `en_us.json`.

@[code lang=json transclude={4-4}](@/reference/1.21/src/main/resources/assets/fabric-docs-reference/lang/en_us.json)

После смерти от нашего типа урона вы увидите следующее сообщение о смерти:

![Effect in player inventory](/assets/develop/tater-damage-death.png)

### Теги типа урона {#damage-type-tags}

Некоторые типы урона могут обходить броню, обходить эффекты сущности и т.д. Теги используются для управления этими свойствами
типов урона.

Существующие теги типов урона можно найти в `data/minecraft/tags/damage_type`.

::: info

Полный список тегов типов урона см. на [Minecraft Wiki](https://minecraft.wiki/w/Tag#Damage_types).

:::

Давайте добавим наш тип урона Tater к тегу типа урона `bypasses_armor`.

Чтобы добавить наш тип урона к одному из этих тегов, мы создаем JSON-файл в пространстве имен `minecraft`.

```:no-line-numbers
data/minecraft/tags/damage_type/bypasses_armor.json
```

Со следующим содержанием:

@[code lang=json](@/reference/1.21/src/main/generated/data/minecraft/tags/damage_type/bypasses_armor.json)

Убедитесь, что ваш тег не заменяет существующий тег, установив ключ `replace` в значение `false`.
