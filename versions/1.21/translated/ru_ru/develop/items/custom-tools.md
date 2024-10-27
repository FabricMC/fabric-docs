---
title: Оружие и инструменты
description: Узнайте, как создавать собственные инструменты и настраивать их свойства.
authors:
  - IMB11

search: false---

# Инструменты {#tools}

Инструменты необходимы для выживания и развития, позволяя игрокам собирать ресурсы, строить здания и защищаться.

## Создание Материала Инструмента {#creating-a-tool-material}

::: info
If you're creating multiple tool materials, consider using an `Enum` to store them. Vanilla does this in the `ToolMaterials` class, which stores all the tool materials that are used in the game.

Этот класс также можно использовать для определения свойств материала вашего инструмента по отношению к классическим материалам инструмента.
:::

"Вы можете создать материал для инструмента, создав новый класс, который его наследует — в этом примере я буду создавать инструменты «Guidite»:

@[code transcludeWith=:::1](@/reference/1.21/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

Материал инструмента сообщает игре следующую информацию:

- ### Прочность - `getDurability()` {#durability}

  Сколько раз можно использовать инструмент, прежде чем он сломается.

  **Пример**

  @[code transcludeWith=:::2](@/reference/1.21/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

- ### Скорость добычи - `getMiningSpeedMultiplier()` {#mining-speed}

  Если инструмент используется для разрушения блоков, с какой скоростью он должен их разрушать?

  Для справки: скорость добычи алмазного инструмента составляет 8,0F, а скорость добычи каменного инструмента — 4,0F.

  **Пример**

  @[code transcludeWith=:::3](@/reference/1.21/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

- ### Урон от атаки - `getAttackDamage()` {#attack-damage}

  Сколько единиц урона должен наносить инструмент при использовании его в качестве оружия против другого существа?

  **Пример**

  @[code transcludeWith=:::4](@/reference/1.21/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

- ### Обратный тег - `getMiningLevel()` {#inverse-tag}

  Обратный тег показывает, что инструмент _**не может**_ добывать. Например, использование тега `BlockTags.INCORRECT_FOR_WOODEN_TOOL` запрещает инструмент добывать определенные блоки:

  ```json
  {
    "values": [
      "#minecraft:needs_diamond_tool",
      "#minecraft:needs_iron_tool",
      "#minecraft:needs_stone_tool"
    ]
  }
  ```

  Это означает, что инструмент не может добывать блоки, для которых требуется алмазный, железный или каменный инструмент.

  **Пример**

  Мы воспользуемся тегом железного инструмента. Это не позволяет инструментам Guidite добывать блоки, для которых требуется инструмент прочнее железа.

  @[code transcludeWith=:::5](@/reference/1.21/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

  Если вы хотите использовать пользовательский тег, вы можете использовать `TagKey.of(...)` для создания пользовательского ключа тега.

- ### Зачарование - `getEnchantability()` {#enchantability}

  Насколько легко получить лучшие и более высокие уровни чар с помощью этого предмета? Для справки: зачаровываемость золота составляет 22, в то время как зачаровываемость незерита — 15.

  **Пример**

  @[code transcludeWith=:::6](@/reference/1.21/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

- ### Ингредиент(ы) для восстановления - `getRepairIngredient()` {#repair-ingredient}

  Какой предмет или предметы используются для ремонта инструмента?

  **Пример**

  @[code transcludeWith=:::7](@/reference/1.21/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

После того как вы создали материал для инструмента и настроили его по своему вкусу, вы можете создать его экземпляр для использования в конструкторах предметов инструмента.

@[code transcludeWith=:::8](@/reference/1.21/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

## Создание инструментов {#creating-tool-items}

Используя ту же вспомогательную функцию, что и в руководстве [Создание вашего первого элемента](./first-item), вы можете создавать свои инструменты:

@[code transcludeWith=:::7](@/reference/1.21/src/main/java/com/example/docs/item/ModItems.java)

Не забудьте добавить их в группу предметов, если вы хотите получить к ним доступ из творческого инвентаря!

@[code transcludeWith=:::8](@/reference/1.21/src/main/java/com/example/docs/item/ModItems.java)

Вам также придется добавить текстуру, перевод предмета и модель предмета. Однако для модели элемента вам следует использовать модель `item/handheld` в качестве родительской.

В этом примере я буду использовать следующую модель и текстуру для предмета «Меч Guidite»:

@[code](@/reference/1.21/src/main/resources/assets/fabric-docs-reference/models/item/guidite_sword.json)

<DownloadEntry type="Texture" visualURL="/assets/develop/items/tools_0.png" downloadURL="/assets/develop/items/tools_0_small.png" />

---

Вот и все! Если вы зайдете в игру, вы увидите свой(и) инструмент(ы) на вкладке инструментов в меню творческого инвентаря.

![Finished tools in inventory](/assets/develop/items/tools_1.png)
