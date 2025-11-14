---
title: Зілля
description: Узнайте як додавати власні зілля із різноманітними ефектами.
authors:
  - dicedpixels
  - Drakonkinst
  - JaaiDead
  - PandoricaVi
---

Зілля — це витратні матеріали, які надають сутності ефект. Гравець може варити зілля за допомогою варильної стійки або отримати їх як предмети через різні інші ігрові механіки.

## Власні зілля {#custom-potions}

Так само, як предмети та блоки, зілля потрібно зареєструвати.

### Створення зілля {#creating-the-potion}

Почнімо з оголошення поля для зберігання вашого екземпляра `Potion`. Ми будемо безпосередньо використовувати клас, що реалізує `ModInitializer` тримай це.

@[code lang=java transclude={18-27}](@/reference/latest/src/main/java/com/example/docs/potion/ExampleModPotions.java)

Ми передаємо екземпляр `StatusEffectInstance`, який приймає 3 параметри:

- `RegistryEntry<StatusEffect> type` - ефект. Тут ми використовуємо наш спеціальний ефект. Крім того, ви можете отримати доступ до ванілльних ефектів
  через клас ванілли `StatusEffects`.
- `int duration` - тривалість ефекту в ігрових тактах.
- `int amplifier` - підсилювач для ефекту. Наприклад, квапливість II мав би підсилювач 1.

:::info
Щоб створити власний ефект зілля, перегляньте посібник [ефекти](../entities/effects).
:::

### Реєстрація зілля {#registering-the-potion}

У нашому ініціалізаторі ми будемо використовувати подію `FabricBrewingRecipeRegistryBuilder.BUILD`, щоб зареєструвати наше зілля за допомогою методу `BrewingRecipeRegistry.registerPotionRecipe`.

@[code lang=java transclude={29-40}](@/reference/latest/src/main/java/com/example/docs/potion/ExampleModPotions.java)

`registerPotionRecipe` приймає 3 параметри:

- `RegistryEntry<Potion> input` – початковий запис реєстру зілля. Зазвичай це може бути пляшка з водою або незграбне зілля.
- `Item item` - предмет, який є основним інгредієнтом зілля.
- `RegistryEntry<Potion> output` – результуючий запис реєстру зілля.

Після реєстрації ви можете варити зілля Tater з картоплі.

![Ефект в інвентарі гравця](/assets/develop/tater-potion.png)
