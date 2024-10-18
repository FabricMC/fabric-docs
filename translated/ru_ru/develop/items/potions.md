---
title: Зелья
description: Узнайте, как добавить собственное зелье для различных статусных эффектов.
authors:
  - dicedpixels
  - PandoricaVi
  - Drakonkinst
  - JaaiDead
---

# Зелья {#potions}

Зелья — это расходные материалы, которые дают сущности определенный эффект. Игрок может варить зелья, используя Варочную Стойку, или получать их как предметы с помощью различных других игровых механик.

## Пользовательские зелья {#custom-potions}

Так же, как предметы и блоки, зелья необходимо регистрировать.

### Создание зелья {#creating-the-potion}

Начнем с объявления поля для хранения вашего экземпляра `Potion`. Мы будем напрямую использовать класс инициализатора для хранения этого.

@[code lang=java transclude={19-27}](@/reference/latest/src/main/java/com/example/docs/potion/FabricDocsReferencePotions.java)

Мы передаем экземпляр `StatusEffectInstance`, который принимает 3 параметра:

- `RegistryEntry<StatusEffect> type` - Эффект. Здесь мы используем наш собственный эффект. В качестве альтернативы вы можете получить доступ к эффектам классического Minecraft
  через класс `StatusEffects`.
- `int duration` - Длительность эффекта в игровых тиках.
- `int amplifier` - Усилитель эффекта. Например, Haste II будет иметь усилитель 1.

:::info
Чтобы создать свой собственный эффект зелья, ознакомьтесь с руководством [Эффекты](../entities/effects).
:::

### Регистрация зелья {#registering-the-potion}

В нашем инициализаторе мы будем использовать событие `FabricBrewingRecipeRegistryBuilder.BUILD` для регистрации нашего зелья с помощью метода `BrewingRecipeRegistry.registerPotionRecipe`.

@[code lang=java transclude={29-42}](@/reference/latest/src/main/java/com/example/docs/potion/FabricDocsReferencePotions.java)

`registerPotionRecipe` принимает 3 параметра:

- `RegistryEntry<Potion> input` - Запись реестра для стартового зелья. Обычно это может быть бутылка с водой или мутное зелье.
- `Предмет предмета` - Предмет, являющийся основным ингредиентом зелья.
- `RegistryEntry<Potion> output` - Результирующая запись реестра зелья.

После регистрации вы сможете сварить зелье «Tater» из картофеля.

![Эффект в инвентаре игрока](/assets/develop/tater-potion.png)
