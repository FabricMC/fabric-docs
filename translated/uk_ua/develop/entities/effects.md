---
title: Ефекти моба
description: Тут ви зможете навчитися додавати власні ефекти.
authors:
  - dicedpixels
  - Friendly-Banana
  - Manchick0
  - SattesKrokodil
  - TheFireBlast
  - YanisBft
authors-nogithub:
  - siglong
  - tao0lu
---

Ефекти моба, також відомі як ефекти статусу або просто ефекти, є станом, який може вплинути на сутність. Вони можуть бути позитивні, негативні або нейтральні. Базова гра застосовує ці ефекти різними способами, такими як їжа, зілля тощо.

Команда `/effect` може бути використана для застосування ефектів до сутності.

## Власні ефекти моба {#custom-mob-effects}

Тут ми додамо новий спеціальний ефект під назвою _Tater_, який дає вам одне очко досвіду за кожен ігровий такт.

### Розширення `MobEffect` {#extend-mobeffect}

Створімо спеціальний клас ефекту, розширивши `MobEffect`, який є базовим класом для всіх ефектів.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/effect/TaterEffect.java)

### Реєстрація вашого власного ефекту {#registering-your-custom-effect}

Подібно до реєстрації блоків і предметів, ми використовуємо `Registry.register` для реєстрації нашого власного ефекту в реєстрі `MOB_EFFECT`. Це можна зробити в нашому ініціалізаторі.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/effect/ExampleModEffects.java)

### Текстура {#texture}

Значок ефекту моба — це PNG розміром 18x18, який з’явиться на екрані інвентарю гравця. Розмістіть ваш значок у:

```text:no-line-numbers
resources/assets/example-mod/textures/mob_effect/tater.png
```

<DownloadEntry visualURL="/assets/develop/tater-effect.png" downloadURL="/assets/develop/tater-effect-icon.png">Приклад текстури</DownloadEntry>

### Переклад {#translations}

Як і будь-який інший переклад, ви можете додати запис із форматом ID `"effect.example-mod.effect-identifier": "Value"` до мовного файлу.

```json
{
  "effect.example-mod.tater": "Tater"
}
```

### Застосування ефекту {#applying-the-effect}

Варто поглянути на те, як ви зазвичай застосовуєте ефект до сутності.

::: tip

Для швидкого тестування краще використати згадану раніше команду `/effect`:

```mcfunction
effect give @p example-mod:tater
```

:::

Щоб застосувати внутрішній ефект, потрібно використати метод `LivingEntity#addMobEffect`, який приймає `MobEffectInstance` і повертає логічне значення, яке вказує, чи було успішно застосовано ефект.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/ReferenceMethods.java)

| Аргумент    | Тип                 | Опис                                                                                                                                                                                                                                                                 |
| ----------- | ------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `effect`    | `Holder<MobEffect>` | Голдер, який представляє ефект.                                                                                                                                                                                                                      |
| `duration`  | `int`               | Тривалість ефекту **в тактах**; **не** секунд                                                                                                                                                                                                                        |
| `amplifier` | `int`               | Підсилювач до рівня ефекту. Він не відповідає **рівню** ефекту, а додається зверху. Отже, `amplifier` `4` => рівень `5`                                                                                                              |
| `ambient`   | `boolean`           | Це складно. Це в основному вказує, що ефект був доданий середовищем (наприклад, **маяк**) і не має прямої причини. Якщо `true`, значок ефекту в HUD буде показане накладення водянистого кольору. |
| `particles` | `boolean`           | Чи слід показувати частинки.                                                                                                                                                                                                                         |
| `icon`      | `boolean`           | Чи показувати значок ефекту в HUD. Ефект буде показано в інвентарі незалежно від цього прапорця.                                                                                                                                     |

::: info

Щоб створити зілля, яке використовує цей ефект, перегляньте [зілля](../items/potions).

:::

<!---->
