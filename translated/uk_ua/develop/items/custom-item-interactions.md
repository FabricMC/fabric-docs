---
title: Власні інтерактивні предмети
description: Прочитайте про те, як створювати предмети з використанням вбудованих ваніллою подій.
authors:
  - IMB11
---

Базові предмети можуть зайти лише так далеко - зрештою вам знадобиться предмет, який взаємодіє зі світом, коли його використовують.

Є кілька ключових класів, які ви повинні зрозуміти, перш ніж дивитися на події ванілльних предметів.

## TypedActionResult {#typedactionresult}

Для предметів найпоширеніший `TypedActionResult`, який ви побачите, це `ItemStacks` - цей клас повідомляє грі, що замінити стіс предметів (чи не замінити) після того, як відбулася подія.

Якщо в події нічого не відбулося, вам слід використати метод `TypedActionResult#pass(stack)`, де `stack` — поточний стіс предмета.

Ви можете отримати поточний стіс предметів, отримавши стіс в руках гравця. Зазвичай події, які потребують `TypedActionResult`, передають руку методу події.

```java
TypedActionResult.pass(user.getStackInHand(hand))
```

Якщо ви передаєте поточний стек, нічого не зміниться, незалежно від того, чи ви оголосите подію невдалою, пройденою/ігнорованою чи успішною.

Якщо ви хочете видалити поточний стіс, вам слід передати порожній. Те саме можна сказати про декрементування, ви отримуєте поточний стіс і зменшуєте його на потрібну кількість:

```java
ItemStack heldStack = user.getStackInHand(hand);
heldStack.decrement(1);
TypedActionResult.success(heldStack);
```

## ActionResult {#actionresult}

Подібним чином `ActionResult` повідомляє грі статус події, незалежно від того, чи була вона пройдена/проігнорована, невдала чи успішна.

## Перевизначені події {#overridable-events}

На щастя, у класі Item є багато методів, які можна замінити, щоб додати до ваших предметів додаткові функції.

:::info
Чудовий приклад використання цих подій можна знайти на сторінці [відтворення звукових подій](../sounds/using-sounds), яка використовує подію `useOnBlock` для відтворення звуку, коли гравець натискає ПКМ по блоку.
:::

| Метод           | Інформація                                                                  |
| --------------- | --------------------------------------------------------------------------- |
| `postHit`       | Запускається, коли гравець влучає в сутність.               |
| `postMine`      | Запускається, коли гравець видобуває блок.                  |
| `inventoryTick` | Запускається кожного такту, поки предмет був в інвентарі.   |
| `onCraft`       | Запускається, коли предмет створено.                        |
| `onCraft`       | Виконується, коли гравець натискає ПКМ по блоку предметом.  |
| `use`           | Запускається, коли гравець натискає ПКМ по блоку предметом. |

## Подія `use()` {#use-event}

Скажімо, ви хочете створити предмет, який викликає блискавку перед гравцем – вам потрібно буде створити спеціальний клас.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/custom/LightningStick.java)

Подія `використання` є, мабуть, найкориснішою з усіх - ви можете використовувати цю подію, щоб створити нашу блискавку, вам слід створити її за 10 блоків перед гравцями, спрямованими обличчям.

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/custom/LightningStick.java)

Як зазвичай, вам слід зареєструвати свою річ, додати модель і текстуру.

Як бачите, блискавка повинна створитися в 10 блоках від вас - гравцем.

<VideoPlayer src="/assets/develop/items/custom_items_0.webm">Використання палиці блискавки</VideoPlayer>
