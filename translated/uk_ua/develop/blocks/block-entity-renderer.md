---
title: Рендер блока-сутності
description: Дізнайтеся, як покращити рендер за допомогою рендера блока-сутности.
authors:
  - natri0
---

Іноді використання формату моделі Minecraft недостатньо. Якщо вам потрібно додати динамічний рендер до візуальних елементів вашого блока, вам потрібно буде використовувати `BlockEntityRenderer`.

Наприклад, нумо зробимо так, щоб блок лічильника зі [статті про блоки-сутності](../blocks/block-entities) показував кількість натискань у зверху.

## Створення BlockEntityRenderer {#creating-a-blockentityrenderer}

Рендер блока-сутності використовує систему надсилання/рендера, де ви спочатку надсилаєте дані, необхідні для рендера об’єкта на екрані, а потім гра рендерить об’єкт, використовуючи його поданий стан.

Створюючи `BlockEntityRenderer` для `CounterBlockEntity`, важливо помістити клас у відповідний вихідний набір, наприклад `src/client/`, якщо ваш проєкт використовує розділені вихідні набори для клієнта та сервера. Доступ до пов’язаних із рендером класів безпосередньо у вихідному наборі `src/main/` небезпечний, оскільки ці класи можуть бути завантажені на сервер.

По-перше, нам потрібно створити `BlockEntityRenderState` для нашого `CounterBlockEntity`, щоб зберігати дані, які використовуватимуться для рендера. У цьому випадку нам знадобиться, щоб \`clicks' були доступні під час рендера.

@[code transcludeWith=::render-state](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderState.java)

Потім ми створюємо `BlockEntityRenderer` для нашого `CounterBlockEntity`.

@[code transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java)

Новий клас має конструктор із `BlockEntityRendererProvider.Context` як параметр. У `Context` є кілька корисних штук рендера, наприклад `ItemRenderer` або `TextRenderer`.
Крім того, включивши такий конструктор, стає можливим використовувати конструктор як сам функціональний інтерфейс `BlockEntityRendererProvider`:

@[code transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/ExampleModBlockEntityRenderer.java)

Ми перевизначимо кілька методів для налаштування стану рендера разом із методом `render`, де буде налаштовано логіку рендера.

`createRenderState` можна використовувати для ініціалізації стану рендера.

@[code transclude={31-34}](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java)

`extractRenderState` можна використовувати для оновлення стану рендера за допомогою даних сутності.

@[code transclude={36-42}](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java)

Ви маєте зареєструвати рендер блока-сутності у своєму класі `ClientModInitializer`.

`BlockEntityRenderers` — це реєстр, який зіставляє кожен `BlockEntityType` зі спеціальним кодом рендера на відповідний `BlockEntityRenderer`.

## Малювання на блоках {#drawing-on-blocks}

Тепер, коли у нас є рендер, ми можемо малювати. Метод `render` викликається кожним кадром, і саме в ньому відбувається магія рендера.

### Переміщення {#moving-around}

По-перше, нам потрібно змістити та повернути текст так, щоб він знаходився вище блока.

::: info

Як випливає з назви, `PoseStack` є _стеком_, що означає, що ви можете надсилати та витягувати перетворення.
Гарне емпіричне правило полягає в тому, щоб додати новий блок на початку методу `render` і відкрити його в кінці, щоб рендер одного блока не впливав на інші.

Більше інформації про `PoseStack` можна знайти в статті про [основні концепції рендера](../rendering/basic-concepts).

:::

Щоб полегшити розуміння необхідних перекладів і поворотів, візуалізуємо їх. На цьому зображенні зелений блок — це місце, де буде намальовано текст, усталено у найдальшій нижній лівій точці блока:

![Усталена позиція рендера](/assets/develop/blocks/block_entity_renderer_1.png)

Отже, спочатку нам потрібно перемістити текст наполовину блока на осях X і Z, а потім перемістити його вгору до верхньої частини блока на осі Y:

![Зелений блок у верхній центральній точці](/assets/develop/blocks/block_entity_renderer_2.png)

Це робиться за допомогою одного виклику `translate`:

```java
matrices.translate(0.5, 1, 0.5);
```

Ось і _переклад_ зроблено, залишаються _обертання_ і _масштаб_.

Усталено, текст малюється на площині X-Y, тому нам потрібно повернути його на 90 градусів навколо осі X, щоб він був спрямований вгору на площині X-Z:

![Зелений блок у верхній центральній точці, спрямований догори] (/assets/develop/blocks/block_entity_renderer_3.png)

`PoseStack` не має функції `rotate`, натомість нам потрібно використовувати `multiply` і `Axis.XP`:

```java
matrices.multiply(Axis.XP.rotationDegrees(90));
```

Тепер текст у правильному положенні, але він завеликий. `BlockEntityRenderer` промальовує весь блок на куб `[-0.5, 0.5]`, тоді як `TextRenderer` використовує координати Y `[0, 9]`. Таким чином, нам потрібно зменшити його в 18 разів:

```java
matrices.scale(1/18f, 1/18f, 1/18f);
```

Тепер усе перетворення виглядає так:

@[code transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java)

### Малювання тексту {#drawing-text}

Як згадувалося раніше, `Context`, переданий у конструктор нашого рендера, має `TextRenderer`, який ми можемо використовувати для вимірювання тексту (`width`), який корисний для центрування.

Щоб намалювати текст, ми передамо необхідні дані в чергу рендера. Оскільки ми малюємо деякий текст, ми можемо використати метод `submitText`, наданий через екземпляр `OrderedRenderCommandQueue`, який передається в метод `render`.

@[code transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java)

Метод `submitText` приймає багато параметрів, але найважливіші з них:

- `FormattedCharSequence` для малювання;
- його координати `x` і `y`;
- RGB-значення `color`;
- `Matrix4f`, що описує, як його слід трансформувати (щоб отримати один із `PoseStack`, ми можемо використати `.last().pose()`, щоб отримати `Matrix4f` для самого верхнього запису).

І після всієї цієї роботи ось результат:

![Блок лічильника з числом зверху](/assets/develop/blocks/block_entity_renderer_4.png)
