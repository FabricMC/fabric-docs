---
title: Кодеки
description: Повний посібник для розуміння і використання системи кодеків Mojang для серіалізації та десеріалізації об'єктів.
authors:
  - enjarai
  - Syst3ms
---

Кодек є системою для легкої серіалізації Java об'єктів, та є частиною бібліотеки Mojang's DataFixerUpper (DFU), яка постачається разом з Minecraft. У контексті модифікації їх можна використовувати як альтернативу
до GSON і Jankson під час читання та запису користувацьких файлів JSON, хоча вони починають ставати все більш актуальними, оскільки Mojang переписує багато старого коду для використання кодеків.

Кодеки використовуються разом з іншим API DFU, `DynamicOps`. Кодек визначає структуру об'єкта, а динамічні операції використовуються для визначення формату для серіалізації в і з, наприклад JSON або NBT. Це означає, що будь-який кодек може бути використовується з «dynamic ops», що забезпечує велику гнучкість.

## Використання кодеків {#using-codecs}

### Серіалізація та десеріалізація {#serializing-and-deserializing}

Основним використанням кодека є серіалізація та десеріалізація об’єктів у певний формат і з нього.

Оскільки кілька усталених класів вже мають визначені кодеки, ми можемо використати їх як приклад. Mojang також надав нам із двома динамічними класами ops усталено, `JsonOps` і `NbtOps`, які зазвичай охоплюють більшість випадків використання.

Тепер, припустімо, ми хочемо серіалізувати `BlockPos` у JSON і назад. Ми можемо зробити це за допомогою статично збереженого кодека у `BlockPos.CODEC` за допомогою методів `Codec#encodeStart` і `Codec#parse` відповідно.

```java
BlockPos pos = new BlockPos(1, 2, 3);

// Серіалізація BlockPos до JsonElement
DataResult<JsonElement> result= BlockPos.CODEC.encodeStart(JsonOps.INSTANCE, pos);
```

Під час використання кодека значення повертаються у формі `DataResult`. Це обгортка, яка може представляти або успіх чи невдача. Ми можемо використовувати це кількома способами: якщо нам просто потрібно наше серіалізоване значення, `DataResult#result` буде просто поверніть `Optional`, що містить наше значення, тоді як `DataResult#resultOrPartial` також дозволяє нам надати функцію для усунення будь-яких помилок, які могли виникнути. Останнє особливо корисно для власних ресурсів пакетів даних, де ми б хотіли реєструвати помилки, не створюючи проблем деінде.

Тож візьмімо наше серіалізоване значення та перетворимо його назад на `BlockPos`:

```java
// Під час написання мода ви, звичайно, захочете правильно обробляти порожні необов'язкові
JsonElement json = result.resultOrPartial(LOGGER::error).orElseThrow();

// Тут ми маємо наше значення JSON, яке має відповідати `[1, 2, 3]`,
// оскільки це формат, який використовує кодек BlockPos.
LOGGER.info("Serialized BlockPos: {}", json);

// Тепер ми десеріалізуємо JsonElement назад у BlockPos
DataResult<BlockPos> result = BlockPos.CODEC.parse(JsonOps.INSTANCE, json);

// Знову ж таки, ми просто візьмемо наше значення з результату
BlockPos pos = result.resultOrPartial(LOGGER::error).orElseThrow();

// І ми бачимо, що ми успішно серіалізували і десеріалізували наш BlockPos!
LOGGER.info("Deserialized BlockPos: {}", pos);
```

### Вбудовані кодеки {#built-in-codecs}

Як згадувалося раніше, Mojang уже визначив кодеки для кількох стандартних класів Java, включаючи, але не обмежуючись `BlockPos`, `BlockState`, `ItemStack`, `Identifier`, `Component` і регулярні вирази `Pattern`. Власні кодеки для Mojang класи зазвичай знаходяться як статичні поля з назвою `CODEC` у самому класі, тоді як більшість інших зберігаються в клас `Codecs`. Слід також зазначити, що всі усталені реєстри містять метод `Codec`, наприклад, ви можна використовувати `BuiltInRegistries.BLOCK.byNameCodec()`, щоб отримати `Codec<Block>`, який серіалізується до ID блока та назад і `holderByNameCodec()`, щоб отримати `Codec<Holder<Block>>`.

Сам Codec API також містить деякі кодеки для примітивних типів, як-от `Codec.INT` і `Codec.STRING`. Вони доступні як статика в класі `Codec` і зазвичай використовуються як основа для складніших кодеків, як пояснюється нижче.

## Побудова кодеків {#building-codecs}

Тепер, коли ми побачили, як використовувати кодеки, подивімося, як ми можемо створювати власні. Припустимо, ми маємо наступний клас, і ми хочемо десеріалізувати його екземпляри з файлів JSON:

```java
public class CoolBeansClass {

    private final int beansAmount;
    private final Item beanType;
    private final List<BlockPos> beanPositions;

    public CoolBeansClass(int beansAmount, Item beanType, List<BlockPos> beanPositions) {...}

    public int getBeansAmount() { return this.beansAmount; }
    public Item getBeanType() { return this.beanType; }
    public List<BlockPos> getBeanPositions() { return this.beanPositions; }
}
```

Відповідний файл JSON може виглядати приблизно так:

```json
{
  "beans_amount": 5,
  "bean_type": "beanmod:mythical_beans",
  "bean_positions": [
    [1, 2, 3],
    [4, 5, 6]
  ]
}
```

Ми можемо створити кодек для цього класу, об’єднавши декілька менших кодеків у більший. У цьому випадку нам знадобиться один для кожного поля:

- `Codec<Integer>`
- `Codec<Item>`
- `Codec<List<BlockPos>>`

Ми можемо отримати перший з вищезгаданих примітивних кодеків у класі `Codec`, зокрема `Codec.INT`. Поки другий можна отримати з реєстру `BuiltInRegistries.ITEM`, який має метод `getCodec()`, який повертає `Codec<Item>`. У нас немає стандартного кодека для `List<BlockPos>`, але ми можемо створити його з `BlockPos.CODEC`.

### Списки {#lists}

`Codec#listOf` можна використовувати для створення версії списку будь-якого кодека:

```java
Codec<List<BlockPos>> listCodec = BlockPos.CODEC.listOf();
```

Слід зазначити, що створені таким чином кодеки завжди десеріалізуються до `ImmutableList`. Якщо замість цього вам потрібен змінний список, ви можете використати [xmap](#mutually-convertible-types), щоб перетворити його під час десеріалізації.

### Об’єднання кодеків для класів, подібних до записів {#merging-codecs-for-record-like-classes}

Тепер, коли у нас є окремі кодеки для кожного поля, ми можемо об’єднати їх в один кодек для нашого класу за допомогою `RecordCodecBuilder`. Це передбачає, що наш клас має конструктор, який містить усі поля, які ми хочемо серіалізувати, і що кожне поле має відповідний метод отримання. Це робить його ідеальним для використання разом із записами, але він може також використовувати під час регулярних занять.

Розгляньмо, як створити кодек для нашого `CoolBeansClass`:

```java
public static final Codec<CoolBeansClass> CODEC = RecordCodecBuilder.create(instance -> instance.group(
    Codec.INT.fieldOf("beans_amount").forGetter(CoolBeansClass::getBeansAmount),
    BuiltInRegistries.ITEM.byNameCodec().fieldOf("bean_type").forGetter(CoolBeansClass::getBeanType),
    BlockPos.CODEC.listOf().fieldOf("bean_positions").forGetter(CoolBeansClass::getBeanPositions)
    // Тут можна оголосити до 16 полів
).apply(instance, CoolBeansClass::new));
```

Кожен рядок у групі визначає кодек, назву поля та метод отримання. Для перетворення використовується виклик `Codec#fieldOf` кодек у [кодеку мапи](#mapcodec), а виклик `forGetter` визначає метод отримання, який використовується для отримання значення поля з екземпляра класу. Водночас виклик `apply` визначає конструктор, який використовується для створення нових екземплярів. Зауважте, що порядок полів у групі має бути таким самим, як порядок аргументів у конструкторі.

Ви також можете використовувати `Codec#optionalFieldOf` у цьому контексті, щоб зробити поле необов’язковим, як пояснюється в розділі [необов’язкових полів](#optional-fields).

### MapCodec, не плутати з Codec&lt;Map&gt; {#mapcodec}

Виклик `Codec#fieldOf` перетворить `Codec<T>` на `MapCodec<T>`, який є варіантом, але не прямої реалізація `Codec<T>`. `MapCodec`s, як випливає з їх назви, гарантовано серіалізуються в ключ до мапи значень або його еквівалент у `DynamicOps`. Для деяких функцій може знадобитися використання звичайного кодека.

Цей особливий спосіб створення `MapCodec` по суті розміщує значення вихідного кодека всередині мапи, із вказаною назвою поля як ключем. Наприклад `Codec<BlockPos>` після серіалізації в JSON виглядатиме так:

```json
[1, 2, 3]
```

Але після перетворення на `MapCodec<BlockPos>` за допомогою `BlockPos.CODEC.fieldOf("pos")` це виглядатиме так:

```json
{
  "pos": [1, 2, 3]
}
```

Хоча найпоширенішим використанням кодеків мап є об’єднання з іншими кодеками мап для створення кодека для повного класу полів, як пояснюється в розділі [злиття кодеків для класів, подібних до записів](#merging-codecs-for-record-like-classes) вище, їх також можна повернути назад у звичайні кодеки за допомогою `MapCodec#codec`, який збереже таку саму поведінку коробки свого вхідного значення.

#### Необов'язкові поля {#optional-fields}

`Codec#optionalFieldOf` можна використовувати для створення додаткової мапи кодека. Це буде, коли вказане поле відсутнє у контейнері під час десеріалізації або бути десеріалізованим як порожній `необов’язковий` або вказане усталене значення.

```java
// Без усталеного значення
MapCodec<Optional<BlockPos>> optionalCodec = BlockPos.CODEC.optionalFieldOf("pos");

// З усталеним значенням
MapCodec<BlockPos> optionalCodec = BlockPos.CODEC.optionalFieldOf("pos", BlockPos.ZERO);
```

Зауважте, що якщо поле присутнє, але значення недійсне, поле не вдається десеріалізувати взагалі, якщо значення поля недійсне.

### Константи, обмеження та композиція {#constants-constraints-composition}

#### Юніт {#unit}

`MapCodec.unitCodec` можна використовувати для створення кодека, який завжди десеріалізується до постійного значення, незалежно від вхідних даних. Під час серіалізації це нічого не робитиме.

```java
Codec<Integer> theMeaningOfCodec = MapCodec.unitCodec(42);
```

#### Числові діапазони {#numeric-ranges}

`Codec.intRange` та його друзі, `Codec.floatRange` і `Codec.doubleRange` можна використовувати для створення кодека, який приймає тільки числові значення в межах зазначеного **включеного** діапазону. Це стосується як серіалізації, так і десеріалізації.

```java
// Не може бути понад 2
Codec<Integer> amountOfFriendsYouHave = Codec.intRange(0, 2);
```

#### Пара {#pair}

`Codec.pair` об’єднує два кодеки, `Codec<A>` і `Codec<B>`, у `Codec<Pair<A, B>>`. Майте на увазі, що він працює належним чином лише з кодеками, які серіалізуються в певне поле, наприклад [перетворені `MapCodec`s](#mapcodec) або [кодеки запису](#merging-codecs-for-record-like-classes).
Отриманий кодек буде серіалізовано в мапу, що поєднує поля обох використаних кодеків.

Наприклад, запустіть цей код:

```java
// Створіть два окремих коробкових кодека
Codec<Integer> firstCodec = Codec.INT.fieldOf("i_am_number").codec();
Codec<Boolean> secondCodec = Codec.BOOL.fieldOf("this_statement_is_false").codec();

// Об’єднайте їх у парний кодек
Codec<Pair<Integer, Boolean>> pairCodec = Codec.pair(firstCodec, secondCodec);

// Використовуйте його для серіалізації даних
DataResult<JsonElement> result = pairCodec.encodeStart(JsonOps.INSTANCE, Pair.of(23, true));
```

Виведе цей JSON:

```json
{
  "i_am_number": 23,
  "this_statement_is_false": true
}
```

#### Кожен {#either}

`Codec.either` поєднує два кодеки, `Codec<A>` і `Codec<B>`, у `Codec<Either<A, B>>`. Отриманий кодек під час десеріалізації спробує використати перший кодек і _тільки якщо це не вдасться_, спробує використати другий.
Якщо другий також не вдається, буде повернено помилку _другого_ кодека.

#### Мапи {#maps}

Для обробки мап із довільними ключами, такими як `HashMap`s, можна використовувати `Codec.unboundedMap`. Це повертає `Codec<Map<K, V>>` для заданих `Codec<K>` і `Codec<V>`. Отриманий кодек буде серіалізовано в об’єкт JSON або будь-який еквівалент, доступний для поточних динамічних операцій.

Через обмеження JSON і NBT використовуваний ключовий кодек _має_ серіалізуватися в рядок. Це включає кодеки для типів, які
самі по собі не є рядками, але їх серіалізують, наприклад `Identifier.CODEC`. Дивіться приклад нижче:

```java
// Створіть кодек для перетворення ідентифікаторів на цілі числа
Codec<Map<Identifier, Integer>> mapCodec = Codec.unboundedMap(Identifier.CODEC, Codec.INT);

// Викоритстовуйте для серіалізації даних
DataResult<JsonElement> result = mapCodec.encodeStart(JsonOps.INSTANCE, Map.of(
    Identifier.fromNamespaceAndPath("example", "number"), 23,
    Identifier.fromNamespaceAndPath("example", "the_cooler_number"), 42
));
```

Це виведе цей JSON:

```json
{
  "example:number": 23,
  "example:the_cooler_number": 42
}
```

Як бачите, це працює, оскільки `Identifier.CODEC` серіалізується безпосередньо до рядкового значення. Подібного ефекту можна досягти для простих об’єктів, які не серіалізуються в рядки, використовуючи [xmap & friends](#mutually-convertible-types) для їх перетворення.

### Взаємно конвертовані типи {#mutually-convertible-types}

#### `xmap` {#xmap}

Скажімо, у нас є два класи, які можна конвертувати один в одного, але не мають стосунків «батьківський-дочірній». Наприклад, усталені `BlockPos` і `Vec3d`. Якщо у нас є кодек для одного, ми можемо використати `Codec#xmap`, щоб створити кодек для іншого, визначення функції перетворення для кожного напрямку.

`BlockPos` вже має кодек, але припустимо, що його немає. Ми можемо створити один для нього, базуючи його на кодек для `Vec3d` ось так:

```java
Codec<BlockPos> blockPosCodec = Vec3d.CODEC.xmap(
    // Перетворення Vec3d у BlockPos
    vec -> new BlockPos(vec.x, vec.y, vec.z),
    // Перетворення BlockPos у Vec3d
    pos -> new Vec3d(pos.getX(), pos.getY(), pos.getZ())
);

// Під час перетворення наявного класу (наприклад, `X`)
// до вашого власного класу (`Y`) таким чином, це може бути добре
// додати `toX` і статичні методи `fromX` до `Y` і використовувати
// посилання на методи у вашому виклику `xmap`.
```

#### flatComapMap, comapFlatMap, і flatXMap {#flatcomapmap-comapflatmap-flatxmap}

`Codec#flatComapMap`, `Codec#comapFlatMap` і `flatXMap` схожі на xmap, але вони дозволяють одній або обом функціям перетворення повертати DataResult. Це корисно на практиці, оскільки конкретний екземпляр об’єкта може бути не таким завжди дійсні для перетворення.

Візьмемо, наприклад, стандартний `Identifier`. Хоча всі ідентифікатори можна перетворити на рядки, не всі рядки є дійсними ідентифікаторами, тому використання xmap означало б створювати неприємні винятки, коли перетворення не вдається.
Через це його вбудований кодек насправді є `comapFlatMap` на `Codec.STRING`, гарно ілюструючи, як ним користуватися:

```java
public class Identifier {
    public static final Codec<Identifier> CODEC = Codec.STRING.comapFlatMap(
        Identifier::validate, Identifier::toString
    );

    // …

    public static DataResult<Identifier> validate(String id) {
        try {
            return DataResult.success(Identifier.parse(id));
        } catch (InvalidIdentifierException e) {
            return DataResult.error("Not a valid identifier: " + id + " " + e.getMessage());
        }
    }

    // …
}
```

Хоча ці методи дійсно корисні, їхні назви дещо заплутані, тому ось таблиця, яка допоможе вам запам’ятати, який з них використовувати:

| Метод                   | A —> B усі дійсні? | B —> A усі дійсні? |
| ----------------------- | ------------------ | ------------------ |
| `Codec<A>#xmap`         | Так                | Так                |
| `Codec<A>#comapFlatMap` | Ні                 | Так                |
| `Codec<A>#flatComapMap` | Так                | Ні                 |
| `Codec<A>#flatXMap`     | Ні                 | Ні                 |

### Розсилка реєстру {#registry-dispatch}

`Codec#dispatch` дозволяє нам визначити реєстр кодеків і відправити до певного на основі значення у серіалізованих даних. Це дуже корисно під час десеріалізації об’єктів, які мають різні поля залежно від свого типу, але все ще представляють те саме.

Наприклад, скажімо, у нас є абстрактний інтерфейс Bean із двома класами реалізації: `StringyBean` і `CountingBean`. Серіалізувати ці з надсиланням реєстру, нам знадобиться кілька речей:

- Окремі кодеки для кожного типу bean.
- Клас або запис `BeanType<T extends Bean>`, який представляє тип bean і може повертати кодек для нього.
- Функція на `Bean` для отримання його `BeanType<?>`.
- Мапа або реєстр для зіставлення `Identifier` з `BeanType<?>`.
- `Codec<BeanType<?>>` на основі цього реєстру. Якщо ви використовуєте `net.minecraft.core.Registry`, його можна легко створити за допомогою `Registry#getCodec`.

З усім цим ми можемо створити кодек відправки реєстру для компонентів:

@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/Bean.java)

@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/BeanType.java)

@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/StringyBean.java)

@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/CountingBean.java)

@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/BeanTypes.java)

```java
// Тепер ми можемо створити кодек для типів bean
// на основі раніше створеного реєстру
Codec<BeanType<?>> beanTypeCodec = BeanType.REGISTRY.getCodec();

// І виходячи з цього, ось наш кодек відправлення реєстру для beans!
// Перший аргумент — це ім’я поля для типу компонента.
// Якщо пропущено, усталено буде «тип».
Codec<Bean> beanCodec = beanTypeCodec.dispatch("type", Bean::getType, BeanType::codec);
```

Наш новий кодек серіалізує bean-файли в JSON таким чином, захоплюючи лише ті поля, які відповідають їх конкретному типу:

```json
{
  "type": "example:stringy_bean",
  "stringy_string": "This bean is stringy!"
}
```

```json
{
  "type": "example:counting_bean",
  "counting_number": 42
}
```

### Рекурсивні кодеки {#recursive-codecs}

Іноді корисно мати кодек, який використовує _себе_ для декодування певних полів, наприклад, при роботі з певними рекурсивними структурами даних. У звичайному коді це використовується для об’єктів `Component`, які можуть зберігати інші `Component` як дочірні. Такий кодек можна створити за допомогою `Codec#recursive`.

Наприклад, спробуймо серіалізувати однозв'язний список. Цей спосіб представлення списків складається з групи вузлів, які містять як значення, так і посилання на наступний вузол у списку. Потім список представлено його першим вузлом, і перехід за списком здійснюється шляхом переходу за наступним вузлом, доки не залишиться жодного. Ось проста реалізація вузлів, які зберігають цілі числа.

```java
public record ListNode(int value, ListNode next) {}
```

Ми не можемо створити кодек для цього звичайними засобами, оскільки який кодек ми використаємо для поля `next`? Нам потрібен `Codec<ListNode>`, який ми зараз розробляємо! `Codec#recursive` дозволяє нам досягти цього за допомогою магічної на вигляд лямбди:

```java
Codec<ListNode> codec = Codec.recursive(
  "ListNode", // назва для кодеку
  selfCodec -> {
    // Тут `selfCodec` представляє `Codec<ListNode>`, ніби він уже створений
    // Цей лямбда повинен повернути кодек, який ми хотіли використовувати з самого початку,
    // який посилається на себе через `selfCodec`
    return RecordCodecBuilder.create(instance ->
      instance.group(
        Codec.INT.fieldOf("value").forGetter(ListNode::value),
         // поле `next` оброблятиметься рекурсивно за допомогою власного кодека
        Codecs.createStrictOptionalFieldCodec(selfCodec, "next", null).forGetter(ListNode::next)
      ).apply(instance, ListNode::new)
    );
  }
);
```

Серіалізований `ListNode` може виглядати так:

```json
{
  "value": 2,
  "next": {
    "value": 3,
    "next": {
      "value": 5
    }
  }
}
```

## Список літератури {#references}

- Набагато вичерпнішу документацію щодо кодеків і пов’язаних API можна знайти на [неофіційному DFU JavaDoc](https://kvverti.github.io/Documented-DataFixerUpper/snapshot/com/mojang/serialization/Codec).
- Загальна структура цього посібника була значною мірою натхненна
  [сторінкою Forge Community Wiki про кодеки](https://forge.gemwire.uk/wiki/Codecs), більш специфічний для Forge погляд на те саме тема.
