---
title: Кодеки
description: Повний посібник для розуміння і використання системи кодеків Mojang для серіалізації та десеріалізації об'єктів.
authors:
  - CelDaemon
  - enjarai
  - Syst3ms
resources:
  https://docs.neoforged.net/docs/datastorage/codecs/: Кодеки — Документація NeoForge
  https://docs.neoforged.net/docs/networking/streamcodecs/: Потокові кодеки — Документація NeoForge
  https://kvverti.github.io/Documented-DataFixerUpper/snapshot/com/mojang/serialization/Codec: Неофіційний DFU JavaDoc
  https://forge.gemwire.uk/wiki/Codecs: Кодеки — Вікі Спільноти Forge
---

Кодек є системою для легкої серіалізації об'єктів Java, та є частиною бібліотеки Mojang DataFixerUpper (DFU), яка постачається разом з Minecraft. У контексті модифікації їх можна використовувати як альтернативу до GSON і Jankson під час читання та запису користувацьких файлів JSON, хоча вони починають ставати все більш актуальними, оскільки Mojang переписує багато старого коду для використання кодеків.

Кодеки використовуються разом з іншим API DFU, `DynamicOps`. Кодек визначає структуру об'єкта, а динамічні операції використовуються для визначення формату для серіалізації в і з, наприклад JSON або NBT. Це означає, що будь-який кодек може бути використовується з «dynamic ops», що забезпечує велику гнучкість.

## Використання кодеків {#using-codecs}

### Серіалізація та десеріалізація {#serializing-and-deserializing}

Основним використанням кодека є серіалізація та десеріалізація об’єктів у певний формат і з нього.

Оскільки кілька усталених класів вже мають визначені кодеки, ми можемо використати їх як приклад. Mojang також надав нам із двома динамічними класами ops усталено, `JsonOps` і `NbtOps`, які зазвичай охоплюють більшість випадків використання.

Тепер, припустімо, ми хочемо серіалізувати `BlockPos` у JSON і назад. Ми можемо зробити це за допомогою статично збереженого кодека у `BlockPos.CODEC` за допомогою методів `Codec#encodeStart` і `Codec#parse` відповідно.

::: code-group

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#encode-blockpos [Java]

<<< @/reference/latest/src/main/generated/reports/example-mod/codec_examples/serialize_blockpos.json [Output]

:::

Під час використання кодека значення повертаються у формі `DataResult`. Це обгортка, яка може представляти або успіх чи невдача. Ми можемо використовувати це кількома способами: якщо нам просто потрібно наше серіалізоване значення, `DataResult#result` буде просто поверніть `Optional`, що містить наше значення, тоді як `DataResult#resultOrPartial` також дозволяє нам надати функцію для усунення будь-яких помилок, які могли виникнути. Останнє особливо корисно для власних ресурсів пакетів даних, де ми б хотіли журналювати помилки, не створюючи проблем деінде.

Тож візьмімо наше серіалізоване значення та перетворимо його назад на `BlockPos`:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#parse-blockpos

### Вбудовані кодеки {#built-in-codecs}

Як згадувалося раніше, Mojang уже визначив кодеки для кількох стандартних класів Java, включаючи, але не обмежуючись `BlockPos`, `BlockState`, `ItemStack`, `Identifier`, `Component` і регулярні вирази `Pattern`. Власні кодеки для Mojang класи зазвичай знаходяться як статичні поля з назвою `CODEC` у самому класі, тоді як більшість інших зберігаються в клас `Codecs`. Слід також зазначити, що всі усталені реєстри містять метод `Codec`, наприклад, ви можна використовувати `BuiltInRegistries.BLOCK.byNameCodec()`, щоб отримати `Codec<Block>`, який серіалізується до ID блока та назад і `holderByNameCodec()`, щоб отримати `Codec<Holder<Block>>`.

Сам Codec API також містить деякі кодеки для примітивних типів, як-от `Codec.INT` і `Codec.STRING`. Вони доступні як статика в класі `Codec` і зазвичай використовуються як основа для складніших кодеків, як пояснюється нижче.

## Побудова кодеків {#building-codecs}

Тепер, коли ми побачили, як використовувати кодеки, подивімося, як ми можемо створювати власні. Припустимо, ми маємо наступний клас, і ми хочемо десеріалізувати його екземпляри з файлів JSON:

<<< @/reference/latest/src/main/java/com/example/docs/codec/CoolBeansClass.java#bean-class

Відповідний файл JSON може виглядати приблизно так:

<<< @/reference/latest/src/main/generated/reports/example-mod/codec_examples/cool_beans.json

Ми можемо створити кодек для цього класу, об’єднавши декілька менших кодеків у більший. У цьому випадку нам знадобиться один для кожного поля:

- `Codec<Integer>`
- `Codec<Item>`
- `Codec<List<BlockPos>>`

Ми можемо отримати перший з вищезгаданих примітивних кодеків у класі `Codec`, зокрема `Codec.INT`. Поки другий можна отримати з реєстру `BuiltInRegistries.ITEM`, який має метод `getCodec()`, який повертає `Codec<Item>`. У нас немає стандартного кодека для `List<BlockPos>`, але ми можемо створити його з `BlockPos.CODEC`.

### Списки {#lists}

`Codec#listOf` можна використовувати для створення версії списку будь-якого кодека:

::: code-group

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#list-codec [Codec]

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#list-codec-data [Input]

<<< @/reference/latest/src/main/generated/reports/example-mod/codec_examples/list_codec.json [Output]

:::

Слід зазначити, що створені таким чином кодеки завжди десеріалізуються до `ImmutableList`. Якщо замість цього вам потрібен змінний список, ви можете використати [xmap](#mutually-convertible-types), щоб перетворити його під час десеріалізації.

### Об’єднання кодеків для класів, подібних до записів {#merging-codecs-for-record-like-classes}

Тепер, коли у нас є окремі кодеки для кожного поля, ми можемо об’єднати їх в один кодек для нашого класу за допомогою `RecordCodecBuilder`. Це передбачає, що наш клас має конструктор, який містить усі поля, які ми хочемо серіалізувати, і що кожне поле має відповідний метод отримання. Це робить його ідеальним для використання разом із записами, але він може також використовувати під час регулярних занять.

Розгляньмо, як створити кодек для нашого `CoolBeansClass`:

::: code-group

<<< @/reference/latest/src/main/java/com/example/docs/codec/CoolBeansClass.java#bean-codec [Codec]

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#bean-codec-data [Input]

<<< @/reference/latest/src/main/generated/reports/example-mod/codec_examples/cool_beans.json [Output]

:::

Кожен рядок у групі визначає кодек, назву поля та метод отримання. Для перетворення використовується виклик `Codec#fieldOf` кодек у [кодеку мапи](#mapcodec), а виклик `forGetter` визначає метод отримання, який використовується для отримання значення поля з екземпляра класу. Водночас виклик `apply` визначає конструктор, який використовується для створення нових екземплярів. Зауважте, що порядок полів у групі має бути таким самим, як порядок аргументів у конструкторі.

Ви також можете використовувати `Codec#optionalFieldOf` у цьому контексті, щоб зробити поле необов’язковим, як пояснюється в розділі [необов’язкових полів](#optional-fields).

### MapCodec, не плутати з Codec&lt;Map&gt; {#mapcodec}

Виклик `Codec#fieldOf` перетворить `Codec<T>` на `MapCodec<T>`, який є варіантом, але не прямої реалізація `Codec<T>`. `MapCodec`, як випливає з їх назви, гарантовано серіалізуються в ключ до мапи значень або його еквівалент у `DynamicOps`. Для деяких функцій може знадобитися використання звичайного кодека.

Цей особливий спосіб створення `MapCodec` по суті розміщує значення початкового кодека всередині мапи, з указаною назвою поля як ключем. Наприклад `Codec<BlockPos>` після серіалізації в JSON виглядатиме так:

<<< @/reference/latest/src/main/generated/reports/example-mod/codec_examples/plain_codec.json

Але після перетворення на `MapCodec<BlockPos>` за допомогою `BlockPos.CODEC.fieldOf("pos")` це виглядатиме так:

<<< @/reference/latest/src/main/generated/reports/example-mod/codec_examples/map_codec.json

Хоча найпоширенішим використанням кодеків мап є об’єднання з іншими кодеками мап для створення кодека для повного класу полів, як пояснюється в розділі [злиття кодеків для класів, подібних до записів](#merging-codecs-for-record-like-classes) вище, їх також можна повернути назад у звичайні кодеки за допомогою `MapCodec#codec`, який збереже таку саму поведінку коробки свого вхідного значення.

#### Необов'язкові поля {#optional-fields}

`Codec#optionalFieldOf` можна використовувати для створення додаткової мапи кодека. Це буде, коли вказане поле відсутнє у контейнері під час десеріалізації або бути десеріалізованим як порожній `необов’язковий` або вказане усталене значення.

::: code-group

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#optional-field [Codec]

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#optional-field-data [Input]

<<< @/reference/latest/src/main/generated/reports/example-mod/codec_examples/optional_field.json [Output]

:::

Щоб додати усталене значення, ми можемо передати його як другий параметр у методі `optionalFieldOf`.

::: code-group

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#default-field [Codec]

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#default-field-data [Input]

<<< @/reference/latest/src/main/generated/reports/example-mod/codec_examples/default_field.json [Output]

:::

Зауважте, що якщо поле присутнє, але значення недійсне, поле не вдається десеріалізувати взагалі, якщо значення поля недійсне.

### Константи, обмеження та композиція {#constants-constraints-composition}

#### Юніт {#unit}

`MapCodec.unitCodec` можна використовувати для створення кодека, який завжди десеріалізується до постійного значення, незалежно від вхідних даних. Під час серіалізації це нічого не робитиме.

::: code-group

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#unit-codec [Codec]

<<< @/reference/latest/src/main/generated/reports/example-mod/codec_examples/unit.json [Output]

:::

#### Числові діапазони {#numeric-ranges}

`Codec.intRange` та його друзі, `Codec.floatRange` і `Codec.doubleRange` можна використовувати для створення кодека, який приймає тільки числові значення в межах зазначеного **включеного** діапазону. Це стосується як серіалізації, так і десеріалізації.

::: code-group

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#numeric-range [Codec]

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#numeric-range-data [Input]

<<< @/reference/latest/src/main/generated/reports/example-mod/codec_examples/numeric_range.json [Output]

:::

#### Пара {#pair}

`Codec.pair` об’єднує два кодеки, `Codec<A>` і `Codec<B>`, у `Codec<Pair<A, B>>`. Майте на увазі, що він працює належним чином лише з кодеками, які серіалізуються в певне поле, наприклад [перетворені `MapCodec`](#mapcodec) або [кодеки запису](#merging-codecs-for-record-like-classes).
Отриманий кодек буде серіалізовано в мапу, що поєднує поля обох використаних кодеків.

::: code-group

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#pair-codec [Codec]

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#pair-codec-data [Input]

<<< @/reference/latest/src/main/generated/reports/example-mod/codec_examples/pair.json [Output]

:::

#### Кожен {#either}

`Codec.either` поєднує два кодеки, `Codec<A>` і `Codec<B>`, у `Codec<Either<A, B>>`. Отриманий кодек під час десеріалізації спробує використати перший кодек і _тільки якщо це не вдасться_, спробує використати другий.
Якщо другий також не вдається, буде повернено помилку _другого_ кодека.

#### Мапи {#maps}

Для обробки мап із довільними ключами, такими як `HashMap`, можна використовувати `Codec.unboundedMap`. Це повертає `Codec<Map<K, V>>` для заданих `Codec<K>` і `Codec<V>`. Отриманий кодек буде серіалізовано в об’єкт JSON або будь-який еквівалент, доступний для поточних динамічних операцій.

Через обмеження JSON і NBT використовуваний ключовий кодек _має_ серіалізуватися в рядок. Це включає кодеки для типів, які
самі по собі не є рядками, але їх серіалізують, наприклад `Identifier.CODEC`. Дивіться приклад нижче:

::: code-group

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#map-codec [Codec]

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#map-codec-data [Input]

<<< @/reference/latest/src/main/generated/reports/example-mod/codec_examples/map.json [Output]

:::

Як бачите, це працює, оскільки `Identifier.CODEC` серіалізується безпосередньо до рядкового значення. Подібного ефекту можна досягти для простих об’єктів, які не серіалізуються в рядки, використовуючи [xmap & friends](#mutually-convertible-types) для їх перетворення.

### Взаємно конвертовані типи {#mutually-convertible-types}

#### `xmap` {#xmap}

Скажімо, у нас є два класи, які можна конвертувати один в одного, але не мають стосунків «батьківський-дочірній». Наприклад, усталені `BlockPos` і `Vec3d`. Якщо у нас є кодек для одного, ми можемо використати `Codec#xmap`, щоб створити кодек для іншого, визначення функції перетворення для кожного напрямку.

`BlockPos` вже має кодек, але припустимо, що його немає. Ми можемо створити один для нього, базуючи його на кодек для `Vec3d` ось так:

::: code-group

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#convert-xmap [Codec]

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#convert-xmap-data [Input]

<<< @/reference/latest/src/main/generated/reports/example-mod/codec_examples/xmap.json [Output]

:::

#### flatComapMap, comapFlatMap, і flatXMap {#flatcomapmap-comapflatmap-flatxmap}

`Codec#flatComapMap`, `Codec#comapFlatMap` і `flatXMap` схожі на xmap, але вони дозволяють одній або обом функціям перетворення повертати DataResult. Це корисно на практиці, оскільки конкретний екземпляр об’єкта може бути не таким завжди дійсні для перетворення.

Візьмемо, наприклад, стандартний `Identifier`. Хоча всі ідентифікатори можна перетворити на рядки, не всі рядки є дійсними ідентифікаторами, тому використання xmap означало б створювати неприємні винятки, коли перетворення не вдається.
Через це його вбудований кодек насправді є `comapFlatMap` на `Codec.STRING`, гарно ілюструючи, як ним користуватися:

<<< @/reference/latest/src/main/java/com/example/docs/codec/Identifier.java#identifier-flatmap

Хоча ці методи дійсно корисні, їхні назви дещо заплутані, тому ось таблиця, яка допоможе вам запам’ятати, який з них використовувати:

| Метод                   | A —> B усі дійсні? | B —> A усі дійсні? |
| ----------------------- | ------------------ | ------------------ |
| `Codec<A>#xmap`         | Так                | Так                |
| `Codec<A>#comapFlatMap` | Ні                 | Так                |
| `Codec<A>#flatComapMap` | Так                | Ні                 |
| `Codec<A>#flatXMap`     | Ні                 | Ні                 |

### Відправлення реєстру {#registry-dispatch}

`Codec#dispatch` дозволяє нам визначити реєстр кодеків і відправити до певного на основі значення у серіалізованих даних. Це дуже корисно під час десеріалізації об’єктів, які мають різні поля залежно від свого типу, але все ще представляють те саме.

Наприклад, скажімо, у нас є абстрактний інтерфейс Bean із двома класами реалізації: `StringyBean` і `CountingBean`. Серіалізувати ці з надсиланням реєстру, нам знадобиться кілька речей:

- Окремі кодеки для кожного типу bean.
- Клас або запис `BeanType<T extends Bean>`, який представляє тип bean і може повертати кодек для нього.
- Функція на `Bean` для отримання його `BeanType<?>`.
- Мапа або реєстр для зіставлення `Identifier` з `BeanType<?>`.
- `Codec<BeanType<?>>` на основі цього реєстру. Якщо ви використовуєте `net.minecraft.core.Registry`, його можна легко створити за допомогою `Registry#byNameCodec`.

З усім цим ми можемо створити кодек відправлення реєстру для компонентів:

::: code-group

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#registry-dispatch [Codec]

<<< @/reference/latest/src/main/java/com/example/docs/codec/Bean.java#bean-interface [Bean]

<<< @/reference/latest/src/main/java/com/example/docs/codec/BeanType.java#bean-type-record [BeanType]

<<< @/reference/latest/src/main/java/com/example/docs/codec/StringyBean.java#stringy-bean-class [StringyBean]

<<< @/reference/latest/src/main/java/com/example/docs/codec/CountingBean.java#counting-bean-class [CountingBean]

<<< @/reference/latest/src/main/java/com/example/docs/codec/BeanTypes.java#bean-types-class [BeanTypes]

:::

Наш новий кодек серіалізує bean-файли в JSON таким чином, захоплюючи лише ті поля, які відповідають їх конкретному типу:

<<< @/reference/latest/src/main/generated/reports/example-mod/codec_examples/stringy_bean.json

<<< @/reference/latest/src/main/generated/reports/example-mod/codec_examples/counting_bean.json

### Рекурсивні кодеки {#recursive-codecs}

Іноді корисно мати кодек, який використовує _себе_ для декодування певних полів, наприклад, при роботі з певними рекурсивними структурами даних. У звичайному коді це використовується для об’єктів `Component`, які можуть зберігати інші `Component` як дочірні. Такий кодек можна створити за допомогою `Codec#recursive`.

Наприклад, спробуймо серіалізувати однозв'язний список. Цей спосіб представлення списків складається з групи нодів, які містять як значення, так і посилання на наступний нод у списку. Потім список представлено його першим нодом, і перехід по списку здійснюється шляхом переходу за наступним нодом доки не залишиться жодного. Ось проста реалізація нодів, які зберігають цілі числа.

<<< @/reference/latest/src/main/java/com/example/docs/codec/ListNode.java#node-record

Ми не можемо створити кодек для цього звичайними засобами, оскільки який кодек ми використаємо для поля `next`? Нам потрібен `Codec<ListNode>`, який ми зараз розробляємо! `Codec#recursive` дозволяє нам досягти цього за допомогою магічної на вигляд лямбди:

::: code-group

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#recursive-codec [Codec]

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#recursive-codec-data [Input]

<<< @/reference/latest/src/main/generated/reports/example-mod/codec_examples/recursive.json [Output]

:::

<!---->
