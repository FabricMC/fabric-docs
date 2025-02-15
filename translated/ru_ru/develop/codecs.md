---
title: Кодеки
description: Исчерпывающее руководство по пониманию и использованию системы кодеков Mojang для сериализации и десериализации объектов.
authors:
  - enjarai
  - Syst3ms
---

Codec — это система для простой сериализации объектов Java, входящая в библиотеку DataFixerUpper (DFU) от Mojang, которая включена в Minecraft. В контексте разработки модов они могут использоваться в качестве альтернативы GSON и Jankson при чтении и записи пользовательских JSON-файлов и их значение со временем растёт, поскольку Mojang переписывает множество старого кода с использованием Codec.

Кодеки используются вместе с другим API из DFU — `DynamicOps`. Кодек определяет структуру объекта, в то время как динамические операции (dynamic ops) используются для определения формата, в который и из которого происходит сериализация, например JSON или NBT. Это означает, что любой кодек может быть использован с любыми динамическими операциями и наоборот, что обеспечивает большую гибкость.

## Использование кодеков {#using-codecs}

### Сериализация и десериализация {#serializing-and-deserializing}

Основное использование кодека — это сериализация и десериализация объектов в определённый формат и из него.

Поскольку для некоторых стандартных классов уже определены кодеки, мы можем использовать их в качестве примера. Mojang также предоставила нам два класса динамических операций по умолчанию: `JsonOps` и `NbtOps`, которые охватывают большинство случаев использования.

Итак, допустим, мы хотим сериализовать `BlockPos` в JSON и обратно. Для этого мы можем использовать кодек, статически хранящийся в `BlockPos.CODEC`, используя такие его методы как `Codec#encodeStart` и `Codec#parse`.

```java
BlockPos pos = new BlockPos(1, 2, 3);

// Сериализуем BlockPos в JsonElement
DataResult<JsonElement> result = BlockPos.CODEC.encodeStart(JsonOps.INSTANCE, pos);
```

Когда мы используем кодек, возвращаемые данные будут в виде `DataResult`. Это оболочка, которая может означать как успешное, так и неудачное выполнение. Мы можем пойти разными путями: если нам просто нужно наше сериализованное значение, `DataResult#result` просто вернёт `Optional`, содержащий наше значение, в то время как `DataResult#resultOrPartial` также позволяет нам добавить обработку ошибок, которые могли бы произойти в процессе. Последнее особенно полезно для своих ресурсов для наборов данных, где мы хотим журналировать ошибки без создания проблем в других местах.

Итак, теперь возьмём сериализованное нами значение и превратим его обратно в `BlockPos`:

```java
// При реальном написании мода вам, конечно, нужно правильно обрабатывать пустые Optionals
JsonElement json = result.resultOrPartial(LOGGER::error).orElseThrow();

// Здесь у нас есть наше JSON-значение, которое должно соответствовать `[1, 2, 3]`,
// так как это формат, используемый кодеком BlockPos.
LOGGER.info("Serialized BlockPos: {}", json);

// Теперь десериализуем JsonElement обратно в BlockPos
DataResult<BlockPos> result = BlockPos.CODEC.parse(JsonOps.INSTANCE, json);

// Снова просто получаем наше значение из результата
BlockPos pos = result.resultOrPartial(LOGGER::error).orElseThrow();

// И мы видим, что успешно сериализовали и десериализовали наш BlockPos!
LOGGER.info("Десериализованный BlockPos: {}", pos);
```

### Встроенные кодеки {#built-in-codecs}

Как упоминалось ранее, Mojang уже определила кодеки для нескольких стандартных и Java-классов, включая, но не ограничиваясь, `BlockPos`, `BlockState`, `ItemStack`, `Identifier`, `Text` и шаблоны (`Pattern`) регулярных выражений. Кодеки для собственных классов Mojang обычно можно найти в статических полях с именем `CODEC` в самом классе, в то время как большинство других кодеков содержатся в классе `Codecs`. Также следует отметить, что все стандартные реестры содержат метод `getCodec()`. Например, вы можете использовать `Registries.BLOCK.getCodec()`, чтобы получить `Codec<Block>`, который сериализует идентификатор блока и обратно.

Сам API Codec также содержит некоторые кодеки для примитивных типов, такие как `Codec.INT` и `Codec.STRING`. Они доступны как статические поля в классе Codec и обычно используются как основа для более сложных кодеков, как объясняется ниже.

## Сборка кодеков {#building-codecs}

Теперь, когда мы увидели, как использовать кодеки, давайте посмотрим, как мы можем собрать свои собственные. Предположим, у нас есть следующий класс, и мы хотим десериализовать его экземпляры из JSON-файлов:

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

Соответствующий JSON-файл может выглядеть примерно так:

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

Мы можем создать кодек для этого класса, объединив несколько меньших кодеков в один большой. В данном случае нам понадобится по одному кодеку для каждого поля:

- `Codec<Integer>`
- `Codec<Item>`
- `Codec<List<BlockPos>>`

Мы можем получить первый из упомянутых ранее примитивных кодеков в классе `Codec`, а именно `Codec.INT`. Второй можно получить из реестра `Registries.ITEM`, который имеет метод `getCodec()`, возвращающий `Codec<Item>`. У нас нет стандартного кодека для `List<BlockPos>`, но мы можем создать его из `BlockPos.CODEC`.

### Списки {#lists}

Метод `Codec#listOf` можно использовать для создания версии кодека для списка любого типа:

```java
Codec<List<BlockPos>> listCodec = BlockPos.CODEC.listOf();
```

Следует отметить, что кодеки, созданные таким образом, всегда будут десериализоваться в `ImmutableList`. Если вам нужна изменяемая (mutable) коллекция, вы можете воспользоваться [xmap](#mutually-convertible-types) для преобразования её при десериализации.

### Объединение кодеков для классов, похожих на Record {#merging-codecs-for-record-like-classes}

Теперь, когда у нас есть отдельные кодеки для каждого поля, мы можем объединить их в один кодек для нашего класса, используя `RecordCodecBuilder`. Это предполагает, что наш класс имеет конструктор, содержащий каждое поле, которое мы хотим сериализовать, и что для каждого поля есть соответствующий метод доступа (геттер). Это делает его идеальным для использования вместе с записями (records), но он также может быть использован с обычными классами.

Давайте посмотрим, как создать кодек для нашего `CoolBeansClass`:

```java
public static final Codec<CoolBeansClass> CODEC = RecordCodecBuilder.create(instance -> instance.group(
    Codec.INT.fieldOf("beans_amount").forGetter(CoolBeansClass::getBeansAmount),
    Registries.ITEM.getCodec().fieldOf("bean_type").forGetter(CoolBeansClass::getBeanType),
    BlockPos.CODEC.listOf().fieldOf("bean_positions").forGetter(CoolBeansClass::getBeanPositions)
    // Здесь можно объявить до 16 полей
).apply(instance, CoolBeansClass::new));
```

Каждая строка в группе указывает кодек, имя поля и метод доступа. Вызов `Codec#fieldOf` используется для преобразования кодека в [MapCodec](#mapcodec), а `forGetter` указывает метод, используемый для получения значения поля из экземпляра класса. Между тем, вызов `apply` указывает конструктор, используемый для создания новых экземпляров. Обратите внимание, что порядок полей в группе должен совпадать с порядком аргументов в конструкторе.

Вы также можете использовать `Codec#optionalFieldOf` в этом контексте, чтобы сделать поле необязательным, как объясняется в разделе [«Необязательные поля»](#optional-fields).

### MapCodec, не путать с Codec&amp;lt;Map&amp;gt; {#mapcodec}

Вызов `Codec#fieldOf` преобразует `Codec<T>` в `MapCodec<T>`, который является вариантом, но не прямой реализацией `Codec<T>`. `MapCodec`, как следует из названия, гарантированно сериализуется в карту «ключ-значение» или её эквивалент в используемых `DynamicOps`. Некоторые функции могут требовать именно MapCodec вместо обычного кодека.

Этот способ создания `MapCodec` по сути оборачивает значение исходного кодека внутри карты, используя указанное имя поля в качестве ключа. Например, `Codec<BlockPos>`, когда сериализуется в JSON, выглядел бы так:

```json
[1, 2, 3]
```

Но при преобразовании в `MapCodec<BlockPos>` с помощью `BlockPos.CODEC.fieldOf("pos")` он будет выглядеть так:

```json
{
  "pos": [1, 2, 3]
}
```

Хотя наиболее распространённое использование MapCodec — объединение с другими MapCodec для построения кодека для полного класса полей, как объясняется в разделе [«Объединение кодеков для классов, похожих на Record»](#merging-codecs-for-record-like-classes) выше, они также могут быть преобразованы обратно в обычные кодеки с помощью `MapCodec#codec`, который сохранит то же поведение оборачивания их входного значения.

#### Необязательные поля {#optional-fields}

`Codec#optionalFieldOf` можно использовать для создания необязательного MapCodec. Это означает, что когда указанное поле отсутствует в контейнере при десериализации, оно будет десериализовано как пустой Optional или заданное значение по умолчанию.

```java
// Без значения по умолчанию
MapCodec<Optional<BlockPos>> optionalCodec = BlockPos.CODEC.optionalFieldOf("pos");

// Со значением по умолчанию
MapCodec<BlockPos> optionalCodec = BlockPos.CODEC.optionalFieldOf("pos", BlockPos.ORIGIN);
```

Обратите внимание, что необязательные поля будут молча игнорировать любые ошибки, которые могут возникнуть при десериализации. Это означает, что если поле присутствует, но значение недопустимо, поле всегда будет десериализовано как значение по умолчанию.

**Начиная с версии 1.20.2**, сам Minecraft (не DFU!) предоставляет `Codecs#createStrictOptionalFieldCodec`, который вовсе не удаётся десериализовать, если значение поля недопустимо.

### Константы, ограничения и композиция {#constants-constraints-composition}

#### Unit {#unit}

`Codec.unit` можно использовать для создания кодека, который всегда десериализуется в постоянное значение, независимо от входных данных. При сериализации он ничего не будет делать.

```java
Codec<Integer> theMeaningOfCodec = Codec.unit(42);
```

#### Числовые диапазоны {#numeric-ranges}

`Codec.intRange` и его аналоги, `Codec.floatRange` и `Codec.doubleRange`, можно использовать для создания кодека, который принимает только числовые значения в заданном **включающем** диапазоне. Это применяется как к сериализации, так и к десериализации.

```java
// Не может быть больше 2
Codec<Integer> amountOfFriendsYouHave = Codec.intRange(0, 2);
```

#### Пары {#pair}

`Codec.pair` объединяет два кодека, `Codec<A>` и `Codec<B>`, в `Codec<Pair<A, B>>`. Имейте в виду, что это работает правильно только с кодеками, которые сериализуются в определённое поле, такие как [преобразованные `MapCodec`](#mapcodec) или [record-кодеки](#merging-codecs-for-record-like-classes).
Полученный кодек будет сериализоваться в карту, объединяющую поля обоих используемых кодеков.

Например, запуск такого кода:

```java
// Создаём два отдельных коробочных кодека
Codec<Integer> firstCodec = Codec.INT.fieldOf("i_am_number").codec();
Codec<Boolean> secondCodec = Codec.BOOL.fieldOf("this_statement_is_false").codec();

// Объединяем их в пару кодеков
Codec<Pair<Integer, Boolean>> pairCodec = Codec.pair(firstCodec, secondCodec);

// Используем её для сериализации данных
DataResult<JsonElement> result = pairCodec.encodeStart(JsonOps.INSTANCE, Pair.of(23, true));
```

Выведет следующий JSON:

```json
{
  "i_am_number": 23,
  "this_statement_is_false": true
}
```

#### Either {#either}

`Codec.either` объединяет два кодека, `Codec<A>` и `Codec<B>`, в `Codec<Either<A, B>>`. Полученный кодек при десериализации попытается использовать первый кодек, и _только если это не удастся_, попытается использовать второй.
Если второй также не справится, будет возвращена ошибка _второго_ кодека.

#### Карты {#maps}

Для обработки карт с произвольными ключами, таких как `HashMap`, можно использовать `Codec.unboundedMap`. Это возвращает `Codec<Map<K, V>>` для заданных `Codec<K>` и `Codec<V>`. Полученный кодек будет сериализоваться в объект JSON или любой эквивалент, доступный для текущих динамических операций.

Из-за ограничений JSON и NBT, используемый кодек ключа _должен_ сериализоваться в строку. Это включает кодеки для типов, которые сами по себе не являются строками, но сериализуются в них, такие как `Identifier.CODEC`. Смотрите пример ниже:

```java
// Создаём кодек для карты идентификаторов к целым числам
Codec<Map<Identifier, Integer>> mapCodec = Codec.unboundedMap(Identifier.CODEC, Codec.INT);

// Используем его для сериализации данных
DataResult<JsonElement> result = mapCodec.encodeStart(JsonOps.INSTANCE, Map.of(
    new Identifier("example", "number"), 23,
    new Identifier("example", "the_cooler_number"), 42
));
```

Это выведет следующий JSON:

```json
{
  "example:number": 23,
  "example:the_cooler_number": 42
}
```

Как вы можете видеть, это работает, потому что `Identifier.CODEC` сериализуется непосредственно в строковое значение. Похожий эффект можно достичь для простых объектов, которые не сериализуются в строки, используя [xmap и другие функции](#mutually-convertible-types) для их преобразования.

### Взаимно преобразуемые типы {#mutually-convertible-types}

#### `xmap` {#xmap}

Предположим, у нас есть два класса, которые могут быть преобразованы друг в друга, но не имеют отношения наследования. Например, стандартные `BlockPos` и `Vec3d`. Если у нас есть кодек для одного, мы можем использовать `Codec#xmap`, чтобы создать кодек для другого, указав функцию преобразования для каждого направления.

`BlockPos` уже имеет кодек, но предположим, что его нет. Мы можем создать его, основываясь на кодеке для `Vec3d`, следующим образом:

```java
Codec<BlockPos> blockPosCodec = Vec3d.CODEC.xmap(
    // Преобразование Vec3d в BlockPos
    vec -> new BlockPos(vec.x, vec.y, vec.z),
    // Преобразование BlockPos в Vec3d
    pos -> new Vec3d(pos.getX(), pos.getY(), pos.getZ())
);

// При преобразовании существующего класса (например, `X`)
// в ваш собственный класс (`Y`) таким образом, может быть полезно
// добавить методы `toX` и статический `fromX` в `Y` и использовать
// ссылки на методы в вашем вызове `xmap`.
```

#### flatComapMap, comapFlatMap и flatXMap {#flatcomapmap-comapflatmap-flatxmap}

`Codec#flatComapMap`, `Codec#comapFlatMap` и `flatXMap` похожи на xmap, но они позволяют одной или обеим функциям преобразования возвращать DataResult. Это полезно на практике, потому что конкретный экземпляр объекта может быть не всегда допустим для преобразования.

Возьмём, например, стандартные идентификаторы (`Identifier`). Хотя все идентификаторы можно превратить в строки, не все строки являются допустимыми идентификаторами, поэтому при неудачном преобразовании использование xmap приведёт к тому, что вы столкнётесь с некрасивыми исключениями.
Из-за этого его встроенный кодек на самом деле является `comapFlatMap` на `Codec.STRING`, что хорошо иллюстрирует, как его использовать:

```java
public class Identifier {
    public static final Codec<Identifier> CODEC = Codec.STRING.comapFlatMap(
        Identifier::validate, Identifier::toString
    );

    // ...

    public static DataResult<Identifier> validate(String id) {
        try {
            return DataResult.success(new Identifier(id));
        } catch (InvalidIdentifierException e) {
            return DataResult.error("Недопустимое местоположение ресурса: " + id + " " + e.getMessage());
        }
    }

    // ...
}
```

Хотя эти методы действительно полезны, их названия немного сбивают с толку, поэтому вот таблица, которая поможет вам запомнить, какой из них использовать:

| Метод                   | A → B всегда допустимо? | B → A всегда допустимо? |
| ----------------------- | ----------------------- | ----------------------- |
| `Codec<A>#xmap`         | Да                      | Да                      |
| `Codec<A>#comapFlatMap` | Нет                     | Да                      |
| `Codec<A>#flatComapMap` | Да                      | Нет                     |
| `Codec<A>#flatXMap`     | Нет                     | Нет                     |

### Диспетчер реестров {#registry-dispatch}

`Codec#dispatch` позволяет нам определить реестр кодеков и направлять к конкретному кодеку на основе значения поля в сериализованных данных. Это очень полезно при десериализации объектов, которые имеют разные поля в зависимости от их типа, но всё же представляют одно и то же.

Например, предположим, у нас есть абстрактный интерфейс `Bean` с двумя реализующими классами: `StringyBean` и `CountingBean`. Чтобы сериализовать их с помощью диспетчера реестров, нам понадобится несколько вещей:

- Отдельные кодеки для каждого типа бобов (beans; как мы их назвали ранее);
- Класс или запись `BeanType<T extends Bean>`, представляющий тип боба и способный возвращать кодек для него;
- Функция в `Bean` для получения его `BeanType<?>`;
- Карта или реестр для сопоставления `идентификаторов` с `BeanType<?>`;
- `Codec<BeanType<?>>`, основанный на этом реестре. Если вы используете `net.minecraft.registry.Registry`, его можно легко создать с помощью `Registry#getCodec`.

Имея всё это, мы можем создать кодек диспетчера реестров для бобов:

@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/Bean.java)
@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/BeanType.java)
@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/StringyBean.java)
@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/CountingBean.java)
@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/BeanTypes.java)

```java
// Теперь мы можем создать кодек для типов бобов
// на основе ранее созданного реестра
Codec<BeanType<?>> beanTypeCodec = BeanType.REGISTRY.getCodec();

// И на основе этого, вот наш кодек диспетчера реестра для бобов!
// Первый аргумент — это имя поля для типа боба.
// Если его не указать, по умолчанию будет «type».
Codec<Bean> beanCodec = beanTypeCodec.dispatch("type", Bean::getType, BeanType::codec);
```

Наш новый кодек будет сериализовать наши бобы в JSON следующим образом, захватывая только поля, которые относятся к их конкретному типу:

```json
{
  "type": "example:stringy_bean",
  "stringy_string": "Этот боб — строка!"
}
```

```json
{
  "type": "example:counting_bean",
  "counting_number": 42
}
```

### Рекурсивные кодеки {#recursive-codecs}

Иногда бывает полезно иметь кодек, который использует сам себя для декодирования определённых полей, например, при работе с некоторыми рекурсивными структурами данных. В стандартном коде это используется для объектов `Text`, которые могут хранить другие `Text` как дочерние. Такой кодек можно построить с помощью `Codec#recursive`.

Например, давайте попробуем сериализовать односвязный список. Этот способ представления списков состоит из набора узлов, которые хранят значение и ссылку на следующий узел в списке. Список представлен своим первым узлом, и обход списка осуществляется путём следования по следующему узлу, пока они не закончатся. Вот простая реализация узлов, которые хранят целые числа:

```java
public record ListNode(int value, ListNode next) {}
```

Мы не можем создать кодек для этого обычными средствами, потому что какой кодек мы будем использовать для поля `next`? Нам нужен `Codec<ListNode>`, который мы как раз и пытаемся создать! `Codec#recursive` позволяет нам достичь этого, используя «волшебную» лямбду:

```java
Codec<ListNode> codec = Codec.recursive(
  "ListNode", // имя для кодека
  selfCodec -> {
    // Здесь `selfCodec` представляет `Codec<ListNode>`, как если бы он уже был создан
    // Эта лямбда должна возвращать кодек, который мы хотим использовать, и который ссылается на себя через `selfCodec`
    return RecordCodecBuilder.create(instance ->
      instance.group(
        Codec.INT.fieldOf("value").forGetter(ListNode::value),
        // поле `next` будет обрабатываться рекурсивно с помощью `selfCodec`
        Codecs.createStrictOptionalFieldCodec(selfCodec, "next", null).forGetter(ListNode::next)
      ).apply(instance, ListNode::new)
    );
  }
);
```

Сериализованный `ListNode` может выглядеть так:

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

## Ссылки {#references}

- Более полную документацию по кодекам и связанным API можно найти в [неофициальной Java-документации DFU](https://kvverti.github.io/Documented-DataFixerUpper/snapshot/com/mojang/serialization/Codec);
- Общая структура этого руководства была сильно вдохновлена [страницей о кодеках на Forge Community Wiki](https://forge.gemwire.uk/wiki/Codecs), более специфичным для Forge подходом к той же теме.
