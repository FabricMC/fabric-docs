---
title: Предметы еды
description: Узнайте, как добавить FoodComponent к предмету, чтобы сделать его съедобным, и как его настроить.
authors:
  - IMB11
---

Еда — это ключевой аспект выживания в Minecraft, поэтому при создании съедобных предметов вам следует учитывать их использование с другими съедобными предметами.

Если вы не создаете мод с очень мощными предметами, вам следует учесть:

- Насколько сильное чувство голода добавляет или убирает ваш съедобный продукт.
- Какой эффект(ы) зелья оно дает?
- Доступно ли оно на ранней или конечной стадии игры?

## Добавляем компонент еды {#adding-the-food-component}

Чтобы добавить пищевой компонент к элементу, мы можем передать его экземпляру `Item.Settings`:

```java
new Item.Settings().food(new FoodComponent.Builder().build())
```

На данный момент это просто делает продукт съедобным и ничего более.

Класс `FoodComponent.Builder` имеет множество методов, которые позволяют вам изменять то, что происходит, когда игрок съедает ваш предмет:

| Метод                | Описание                                                                                                                               |
| -------------------- | -------------------------------------------------------------------------------------------------------------------------------------- |
| `nutrition`          | Устанавливает количество очков голода, которое восполнит ваш предмет.                                                  |
| `saturationModifier` | Устанавливает количество точек насыщенности, которые добавит ваш элемент.                                              |
| `alwaysEdible`       | Позволяет съесть ваш предмет независимо от уровня голода.                                                              |
| `snack`              | Указывает, что ваш предмет — закуска.                                                                                  |
| `statusEffect`       | Adds a status effect when you eat your item. Добавляет эффект статуса, когда вы съедаете свой предмет. |

После того как вы изменили конструктор по своему вкусу, вы можете вызвать метод `build()`, чтобы получить `FoodComponent`.

@[code transcludeWith=:::5](@/reference/1.21/src/main/java/com/example/docs/item/ModItems.java)

Подобно примеру на странице [Создание вашего первого элемента](./first-item), я буду использовать указанный выше компонент:

@[code transcludeWith=:::poisonous_apple](@/reference/1.21/src/main/java/com/example/docs/item/ModItems.java)

Это делает предмет:

- Всегда съедобным, может быть съеден независимо от уровня голода.
- «Закуска».
- Всегда дающим Отравление II на 6 секунд когда съеден.

<VideoPlayer src="/assets/develop/items/food_0.webm" title="Eating the Suspicious Substance" />
