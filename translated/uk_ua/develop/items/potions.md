---
title: Зілля
description: Дізнайтеся як додавати власні зілля із різноманітними ефектами.
authors:
  - cassiancc
  - dicedpixels
  - Drakonkinst
  - JaaiDead
  - PandoricaVi
---

Зілля — це витратні предмети, що надають сутності ефект. Гравець може варити зілля за допомогою варильної стійки або отримати їх як предмети через різні інші ігрові механіки.

## Власні зілля {#custom-potions}

Так само як предмети та блоки, зілля потрібно зареєструвати.

### Створення зілля {#creating-the-potion}

Почнімо з оголошення поля для тримання вашого екземпляра `Potion`. Ми будемо безпосередньо використовувати клас, що реалізує `ModInitializer` тримай це. Зверніть увагу на використання `Registry.registerForHolder`, оскільки, як і ефекти моба, більшість стандартних методів, які використовують зілля, віддають перевагу їм як голдерам.

@[code lang=java transclude={18-27}](@/reference/latest/src/main/java/com/example/docs/potion/ExampleModPotions.java)

Ми передаємо екземпляр `MobEffectInstance`, який приймає 3 параметри:

- `Holder<MobEffect> type` — ефект, представлений як голдер. Тут ми використовуємо наш спеціальний ефект. Крім того, ви можете отримати доступ до усталених ефектів
  через усталений клас `MobEffects`.
- `int duration` — тривалість ефекту в ігрових тактах.
- `int amplifier` — підсилювач для ефекту. Наприклад, квапливість II мав би підсилювач 1.

::: info

Щоб створити власний ефект зілля, перегляньте посібник [ефектів](../entities/effects).

:::

### Реєстрація зілля {#registering-the-potion}

У нашому ініціалізаторі ми будемо використовувати подію `FabricBrewingRecipeRegistryBuilder.BUILD`, щоб зареєструвати наше зілля за допомогою методу `BrewingRecipeRegistry.registerPotionRecipe`.

@[code lang=java transclude={29-40}](@/reference/latest/src/main/java/com/example/docs/potion/ExampleModPotions.java)

`registerPotionRecipe` приймає 3 параметри:

- `Holder<Potion> input` — початкове зілля, представлене голдером. Зазвичай це може бути пляшка з водою або незграбне зілля.
- `Item item` — предмет, який є основним інгредієнтом зілля.
- `Holder<Potion> output` — отримане зілля, представлене голдером.

Після реєстрації ви можете варити зілля Tater з картоплі.

![Ефект в інвентарі гравця](/assets/develop/tater-potion.png)
