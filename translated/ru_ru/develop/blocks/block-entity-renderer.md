---
title: Рендеры сущностей блока
description: Узнайте, как разнообразить рендеринг с помощью блочных рендереров сущностей.
authors:
  - natri0
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

Новый класс имеет конструктор с `BlockEntityRendererProvider.Context` в качестве параметра. В `Context` есть несколько полезных утилит рендеринга, например, `ItemRenderer` или `TextRenderer`.
Также, включение такого конструктора позволяет использовать конструктор в качестве функционального интерфейса `BlockEntityRendererProvider`:

@[code transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/ExampleModBlockEntityRenderer.java)

Мы переопределим несколько методов для настройки состояния рендеринга, а также метод `render`, в котором будет настроена логика рендеринга.

`createRenderState` можно использовать для инициализации состояния рендеринга.

@[code transclude={31-34}](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java)

`extractRenderState` может быть использован для обновления состояния рендеринга с помощью данных о сущностях.

@[code transclude={36-42}](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java)

Вы должны зарегистрировать рендеринг блочных сущностей в классе `ClientModInitializer`.

`BlockEntityRenderers` - это реестр, который сопоставляет каждый `BlockEntityType` с пользовательским кодом рендеринга с соответствующим `BlockEntityRenderer`.

## Рисование на блоках {#drawing-on-blocks}

Теперь, когда у нас есть рендерер, мы можем рисовать. Метод `render` вызывается каждый кадр, и именно в нем происходит магия рендеринга.

### Передвижение {#moving-around}

Сначала нам нужно сместить и повернуть текст так, чтобы он находился на верхней стороне блока.

:::info
Как следует из названия, `PoseStack` - это _стек_, что означает, что вы можете вставлять и вынимать преобразования.
Хорошим правилом является создание нового блока в начале метода `render` и его удаление в конце, чтобы рендеринг одного блока не повлиял на другие.

Более подробную информацию о `PoseStack` можно найти в статье [Основные концепции рендеринга](../rendering/basic-concepts).
:::

Чтобы было проще понять, какие переводы и вращения нужны, давайте их визуализируем. На этом рисунке зеленый блок - это место, где будет нарисован текст, по умолчанию в самой дальней нижней левой точке блока:

![Позиция рендеринга по умолчанию](/assets/develop/blocks/block_entity_renderer_1.png)

Итак, сначала нам нужно переместить текст на половину блока по осям X и Z, а затем переместить его в верхнюю часть блока по оси Y:

![Зеленый блок в самой верхней центральной точке](/assets/develop/blocks/block_entity_renderer_2.png)

Это делается с помощью одного вызова `translate`:

```java
matrices.translate(0.5, 1, 0.5);
```

На этом _перевод_ закончен, остаются _вращение_ и _масштаб_.

По умолчанию текст рисуется на плоскости XY, поэтому нам нужно повернуть его на 90 градусов вокруг оси X, чтобы он был направлен вверх на плоскости XZ:

![Зеленый блок в самой верхней центральной точке, лицом вверх](/assets/develop/blocks/block_entity_renderer_3.png)

В `PoseStack` нет функции `rotate`, вместо неё нужно использовать `multiply` и `Axis.XP`:

```java
matrices.multiply(Axis.XP.rotationDegrees(90));
```

Теперь текст находится в правильном положении, но он слишком большой. В `BlockEntityRenderer` весь блок отображается на куб `[-0.5, 0.5]`, а в `TextRenderer` используются координаты Y `[0, 9]`. Поэтому нам нужно уменьшить масштаб в 18 раз:

```java
matrices.scale(1/18f, 1/18f, 1/18f);
```

Теперь вся трансформация выглядит следующим образом:

@[code transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java)

### Текст для рисования {#drawing-text}

Как упоминалось ранее, `Context`, передаваемый в конструктор нашего рендерера, имеет `TextRenderer`, который мы можем использовать для измерения текста (`width`), что полезно для центрирования.

Чтобы нарисовать текст, мы отправим необходимые данные в очередь рендеринга. Поскольку мы рисуем текст, мы можем использовать метод `submitText, предоставляемый через экземпляр `OrderedRenderCommandQueue`, передаваемый в метод `render\`.

@[code transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java)

Метод `submitText` принимает множество параметров, но наиболее важными являются следующие:

- `FormattedCharSequence` для рисования
- координаты `x` и `y`;
- значение RGB `цвета`;
- `Matrix4f`, описывающий, как он должен быть преобразован (чтобы получить его из `PoseStack`, мы можем использовать `.last().pose()`, чтобы получить `Matrix4f` для самой верхней записи).

И после всей этой работы вот результат:

![Блок счетчиков с номером на вершине](/assets/develop/blocks/block_entity_renderer_4.png)
