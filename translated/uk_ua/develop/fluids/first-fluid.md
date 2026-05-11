---
title: Створення вашої першої рідини
description: Дізнайтеся, як створювати власні рідини в Minecraft.
authors:
  - AlbanischeWurst
  - AlexiyOrlov
  - cassiancc
  - CelDaemon
  - Clomclem
  - comp500
  - Daomephsta
  - Earthcomputer
  - florensie
  - Fusion-Flux
  - InfinityChances
  - Kilip1000
  - MaxURhino
  - SolidBlock-cn
  - SuperSoupr
  - Virtuoel
  - UpcraftLP
authors-nogithub:
  - alfiejfs
  - salvopelux
---

<!---->

:::info ПЕРЕДУМОВИ

Ви повинні спочатку зрозуміти, як створити [блок](../blocks/first-block) та [предмет](../items/first-item).

:::

Цей приклад охоплює створення рідини кислоти, яка завдає шкоду сутності та ослаблює й осліплює її, які в рідині. Для цього нам знадобляться два екземпляри рідини для джерела та станів рідини, блок рідини, предмет відра та теґ рідини.

## Створення клас рідини {#creating-the-fluid-class}

Ми почнемо зі створення абстрактного класу, у цьому випадку під назвою `AcidFluid`, який розширює базовий клас `FlowingFluid`. Потім ми замінимо будь-які методи, які мають бути однаковими як для джерела, так і для текучої рідини.

Зверніть особливу увагу на такі методи:

- `animateTick` використовується для показу частинок і звуку. Поведінка, показана нижче, заснована на воді, яка відтворює звук, коли тече, і містить частинки підводні бульбашки.
- `entityInside` використовується для обробки того, що має статися, коли сутність торкається рідини. Ми будемо базувати його на воді та гасити будь-який вогонь на сутностях, але також змусимо його завдати шкоди сутності та ослаблювати й осліплювати її всередині — адже це кислота.
- `canBeReplacedWith` обробляє деяку поточну логіку — зауважте, що `ModFluidTags.ACID` ще не визначено, ми розглянемо це в кінці.

Зібравши все це разом, ми отримаємо наступний клас:

@[code transcludeWith=:::abstractFluid](@/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java)

Усередині `AcidFluid` ми створимо два підкласи для рідин `Source` і `Flowing`.

@[code transcludeWith=:::fluidSubclasses](@/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java)

### Реєстрація рідин {#registering-fluids}

Далі ми створимо клас для реєстрації всіх екземплярів рідини. Ми назвемо це `ModFluids`.

@[code transcludeWith=:::register](@/reference/latest/src/main/java/com/example/docs/fluid/ModFluids.java)

Як і з блоками, вам потрібно переконатися, що клас завантажено, щоб усі статичні поля, що містять екземпляри вашої рідини, були ініціалізовані. Ви можете зробити це, створивши фіктивний метод `initialize`, який можна викликати в [ініціалізаторі мода](../getting-started/project-structure#entrypoints), щоб запустити статичну ініціалізацію.

Тепер поверніться до класу `AcidFluid` і додайте ці методи, щоб зв’язати зареєстровані екземпляри рідини з цією рідиною:

@[code transcludeWith=:::sources](@/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java)

Наразі ми зареєстрували вихідний стан рідини та її текучий стан. Далі нам потрібно буде зареєструвати відро та `LiquidBlock` для нього.

### Реєстрація блоків рідини {#fluid-blocks}

Тепер додаймо блок рідини для нашої рідини. Це потрібно деяким командам, таким як `setblock`, щоб ваша рідина могла існувати у світі. Якщо ви ще цього не зробили, вам слід поглянути на те, [як створити свій перший блок](../blocks/first-block).

Відкрийте свій клас `ModBlocks` і зареєструйте наступний `LiquidBlock`:

@[code transcludeWith=:::acid](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

Потім змініть цей метод у `AcidFluid`, щоб пов’язати ваш блок із рідиною:

@[code transcludeWith=:::legacyBlock](@/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java)

### Реєстрація відер {#buckets}

Рідини в Minecraft зазвичай переносяться у відрах, тому подивімося, як ми можемо додати предмет для відра кислоти. Якщо ви ще цього не зробили, вам слід поглянути на те, [як створити свій перший предмет](../items/first-item).

Відкрийте свій клас `ModItems` і зареєструйте наступний `BucketItem`:

@[code transcludeWith=:::acid_bucket](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Потім змініть цей метод у `AcidFluid`, щоб пов’язати ваше відро з рідиною:

@[code transcludeWith=:::bucket](@/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java)

Не забувайте, що предмети вимагають перекладу, [текстури](../items/first-item#adding-a-texture), [моделі](../items/first-item#adding-a-model) та [клієнтського предмета](../items/first-item#creating-the-client-item) з назвою `acid_bucket` для правильного рендера. Приклад текстури наведено нижче.

<DownloadEntry visualURL="/assets/develop/fluids/acid_bucket.png" downloadURL="/assets/develop/fluids/acid_bucket_small.png">Текстура</DownloadEntry>

Також рекомендовано додати відро вашого мода до теґу предмета `ConventionalItemTags.BUCKET`, щоб інші моди могли правильно його обробляти, або [вручну](#tagging), або через [генерацію даних](../data-generation/tags).

## Додання до теґу вашої рідини {#tagging}

::: info

Користувачі [генерації даних](../data-generation/tags) можуть забажати зареєструвати теґи через `FabricTagProvider.FluidTagProvider`, а не писати їх вручну.

:::

Оскільки рідина вважається двома окремими блоками в її поточному та нерухомому станах, теґ часто використовується для перевірки обох станів разом. Ми створимо теґ рідини в `data/example-mod/tags/fluid/acid.json`:

<<< @/reference/latest/src/main/generated/data/example-mod/tags/fluid/acid.json

::: tip

Minecraft також надає інші теґи для контролю поведінки рідин:

- Якщо вам потрібно, щоб рідина вашого мода поводилася як вода (водяний туман, поглинається губками, плавання та уповільнення сутностей…), подумайте про те, щоб додати її до теґу рідини `minecraft:water`.
- Якщо вам потрібно, щоб вона поводилася як лава (лавовий туман, придатний для плавання блукачів та ґастів, уповільнює сутностей…), додайте її до теґу рідини `minecraft:lava`.
- Якщо вам потрібні лише _деякі_ з цих речей, ви можете використовувати міксини, щоб точно контролювати поведінку.

:::

Для цієї демонстрації ми також додамо теґ рідини кислоти до теґу рідини води, `data/minecraft/tags/fluid/water.json`.

<<< @/reference/latest/src/main/generated/data/minecraft/tags/fluid/water.json

## Текстурування вашої рідини {#textures}

Щоб створити текстуру вашої рідини, вам слід використовувати `FluidRenderHandlerRegistry` Fabric API.

::: tip

Для простоти ця демонстрація використовує `BlockTintSources.constant`, щоб застосувати постійний зелений відтінок до текстури стандартної води. Для подробиць про `BlockTintSource`, див. [відтінки блока](../blocks/block-tinting).

:::

Додайте наступні рядки до свого `ClientModInitializer`, щоб створити `FluidModel.Unbaked`, який приймає два `Material` для текстур — один для нерухомого джерела та один для текучої рідини, а також відтінок кольору блока джерела, яким його потрібно відтінити.

@[code transcludeWith=:::fluid_texture](@/reference/latest/src/client/java/com/example/docs/appearance/ExampleModAppearanceClient.java)

Наразі у нас є все, що нам потрібно, щоб побачити кислоту у грі! Ви можете використовувати `setblock` або предмет відра кислоти, щоб розмістити кислоту у світі.

![Знімок екрана зеленої рідини кислоти у світі](/assets/develop/fluids/acid.png)
