---
title: Рендеры сущностей блока
description: Узнайте, как разнообразить рендеринг с помощью блочных рендереров сущностей.
authors:
  - natri0
resources:
  https://docs.neoforged.net/docs/blockentities/ber/: Рендерер блочных сущностей — документация NeoForge
---

Иногда использования формата моделей Minecraft недостаточно. Если вам нужно добавить динамический рендеринг к визуальным эффектам блока, вам нужно использовать `BlockEntityRenderer`.

Например, давайте сделаем так, чтобы блок Counter из статьи [Block Entities](../blocks/block-entities) показывал кол-во кликов на своей верхней стороне.

## Создание BlockEntityRenderer {#creating-a-blockentityrenderer}

Рендеринг блоков, сущностей использует систему submit/render, в которой вы сначала отправляете данные, необходимые для рендеринга объекта на экран, а затем игра рендерит объект, используя его отправленное состояние.

При создании `BlockEntityRenderer` для `CounterBlockEntity` важно поместить класс в соответствующий набор исходных текстов, например `src/client/`, если ваш проект использует раздельные наборы исходных текстов для клиента и сервера. Обращение к классам, связанным с рендерингом, непосредственно в наборе исходных текстов `src/main/` небезопасно, поскольку эти классы могут быть загружены на сервере.

Во-первых, нам нужно создать `BlockEntityRenderState` для нашего `CounterBlockEntity`, чтобы хранить данные, которые будут использоваться для рендеринга. В этом случае нам нужно, чтобы `клики` были доступны во время рендеринга.

@[code transcludeWith=::render-state](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderState.java)

Затем мы создадим `BlockEntityRenderer` для нашего `CounterBlockEntity`.

@[code transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java)

Новый класс имеет конструктор с `BlockEntityRendererProvider.Context` в качестве параметра. У `Context` есть несколько полезных утилит рендеринга, например `ItemRenderer` или `Font`.
Также, включение такого конструктора позволяет использовать конструктор в качестве функционального интерфейса `BlockEntityRendererProvider`:

@[code transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/ExampleModBlockEntityRenderer.java)

Мы переопределим несколько методов, чтобы настроить состояние рендера, а также метод `submit`, в котором будет реализована логика рендеринга.

`createRenderState` можно использовать для инициализации состояния рендеринга.

@[code transclude={31-34}](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java)

`extractRenderState` может быть использован для обновления состояния рендеринга с помощью данных о сущностях.

@[code transclude={36-42}](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java)

Вы должны зарегистрировать рендеринг блочных сущностей в классе `ClientModInitializer`.

`BlockEntityRenderers` - это реестр, который сопоставляет каждый `BlockEntityType` с пользовательским кодом рендеринга с соответствующим `BlockEntityRenderer`.

## Рисование на блоках {#drawing-on-blocks}

Теперь, когда у нас есть рендерер, мы можем рисовать. Метод `submit` вызывается каждый кадр — именно здесь происходит вся магия рендеринга.

### Передвижение {#moving-around}

Сначала нам нужно сместить и повернуть текст так, чтобы он находился на верхней стороне блока.

::: info

Как следует из названия, `PoseStack` — это _стек_, то есть вы можете добавлять (push) и удалять (pop) преобразования.
Хорошее правило — делать push в начале метода `submit` и pop в конце, чтобы рендер одного блока не влиял на другие.

Больше информации о `PoseStack` можно найти в статье [Основы рендеринга](../rendering/basic-concepts).

:::

Чтобы упростить понимание необходимых смещений и вращений, давайте визуализируем их. На этом изображении зелёный блок показывает, где будет отрисован текст — по умолчанию в самой дальней нижней левой точке блока:

![Позиция рендеринга по умолчанию](/assets/develop/blocks/block_entity_renderer_1.png)

Сначала нужно сдвинуть текст на половину блока по осям X и Z, а затем поднять его до верхней грани блока по оси Y:

![Зелёный блок в верхней центральной точке](/assets/develop/blocks/block_entity_renderer_2.png)

Это делается одним вызовом `translate`:

```java
matrices.translate(0.5, 1, 0.5);
```

Смещение выполнено, остаются вращение и масштабирование.

По умолчанию текст рисуется в плоскости XY, поэтому нужно повернуть его на 90 градусов вокруг оси X, чтобы он был направлен вверх в плоскости XZ:

![Зелёный блок в верхней центральной точке, направленный вверх](/assets/develop/blocks/block_entity_renderer_3.png)

У `PoseStack` нет функции `rotate`, вместо этого нужно использовать `mulPose` и `Axis.XP`:

```java
matrices.mulPose(Axis.XP.rotationDegrees(90));
```

Теперь текст находится в правильной позиции, но он слишком большой. `BlockEntityRenderer` отображает весь блок в кубе `[-0.5, 0.5]`, тогда как `Font` использует координаты Y в диапазоне `[0, 9]`. Следовательно, его нужно уменьшить в 18 раз:

```java
matrices.scale(1/18f, 1/18f, 1/18f);
```

Теперь всё преобразование выглядит так:

@[code transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java)

### Отрисовка текста {#drawing-text}

Как упоминалось ранее, `Context`, передаваемый в конструктор нашего рендерера, содержит `Font`, который можно использовать для измерения текста (`width`), что полезно для центрирования.

Чтобы отрисовать текст, мы будем отправлять необходимые данные в очередь рендеринга. Поскольку мы рисуем текст, можно использовать метод `submitText`, предоставляемый через экземпляр `SubmitNodeCollector`, переданный в метод `submit`.

@[code transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java)

Метод `submitText` принимает множество параметров, но наиболее важные из них:

- `FormattedCharSequence`, который нужно отрисовать;
- его координаты `x` и `y`;
- значение цвета `RGB`;
- `PoseStack`, описывающий, как его следует преобразовать.

И после всей этой работы вот результат:

![Блок счётчика с числом сверху](/assets/develop/blocks/block_entity_renderer_4.png)
