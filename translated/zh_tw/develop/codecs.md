---
title: Codec
description: 一份理解和使用 Mojang 的編解碼系統來序列化和反序列化物件的綜合指南。
authors:
  - enjarai
  - Syst3ms
---

Codec 是一個方便序列化 Java 物件的系統，包含在 Mojang 的 DataFixerUpper (DFU) 函式庫中，而 DFU 包含在 Minecraft 中。 在模組製作的環境中，它們可以作為 GSON 和 Jankson 的替代方案，用於讀取和寫入自訂的 json 檔案。不過，它們正變得越來越重要，因為 Mojang 正在重寫許多舊程式碼來使用 Codec。

Codec 與 DFU 的另一個 API `DynamicOps` 結合使用。 Codec 定義了物件的結構，而 dynamic ops 用於定義序列化和反序列化的格式，例如 json 或 NBT。 這表示任何 codec 都可以與任何 dynamic ops 一起使用，反之亦然，從而提供極大的靈活性。

## 使用 Codec {#using-codec}

### 序列化和反序列化 {#serializing-and-deserializing}

Codec 的基本用法是將物件序列化和反序列化為特定格式。

由於一些原版類別已經定義了 codec，我們可以將它們作為範例。 Mojang 也預設提供了兩個 dynamic ops 類別，`JsonOps` 和 `NbtOps`，它們通常涵蓋了大部分使用情況。

現在，假設我們想將 `BlockPos` 序列化為 json 並再反序列化回來。 我們可以使用靜態儲存在 `BlockPos.CODEC` 的 codec，以及 `Codec#encodeStart` 和 `Codec#parse` 方法來分別完成這件事。

```java
BlockPos pos = new BlockPos(1, 2, 3);

// 將 BlockPos 序列化為 JsonElement
DataResult<JsonElement> result = BlockPos.CODEC.encodeStart(JsonOps.INSTANCE, pos);
```

當使用 codec 時，數值會以 `DataResult` 的形式回傳。 這是一個可以表示成功或失敗的包裝器。 我們可以透過幾種方式使用它：如果我們只想要序列化的數值，`DataResult#result` 將只會回傳一個包含我們數值的 `Optional`，而 `DataResult#resultOrPartial` 也讓我們提供一個函數來處理可能發生的任何錯誤。 後者對於自訂資料包資源特別有用，因為我們想在不影響其他地方的情況下記錄錯誤。

所以讓我們取得序列化的數值，並將它轉換回 `BlockPos`：

```java
// 在實際編寫模組時，你會希望正確地處理空的 Optionals
JsonElement json = result.resultOrPartial(LOGGER::error).orElseThrow();

// 在這裡我們有 json 數值，它應該對應到 `[1, 2, 3]`，
// 因為這是 BlockPos codec 使用的格式。
LOGGER.info("序列化的 BlockPos：{}", json);

// 現在我們將 JsonElement 反序列化回 BlockPos
DataResult<BlockPos> result = BlockPos.CODEC.parse(JsonOps.INSTANCE, json);

// 同樣地，我們只會從結果中取得數值
BlockPos pos = result.resultOrPartial(LOGGER::error).orElseThrow();

// 我們可以看到我們已經成功地序列化和反序列化了我們的 BlockPos！
LOGGER.info("反序列化的 BlockPos：{}", pos);
```

### 內建 Codec {#built-in-codec}

如前所述，Mojang 已經為幾個原版和標準 Java 類別定義了 codec，包括但不限於 `BlockPos`、`BlockState`、`ItemStack`、`Identifier`、`Text` 和規則運算式 `Pattern`。 Mojang 自己的類別的 codec 通常會以靜態欄位 `CODEC` 的形式出現在類別本身，而大部分其他的則儲存在 `codec` 類別中。 還應該注意的是，所有原版登錄都包含一個 `getCodec()` 方法，例如，您可以使用 `Registries.BLOCK.getCodec()` 取得一個序列化為方塊 ID 並返回的 `Codec<Block>`。

Codec API 本身也包含一些用於原始類型的 codec，例如 `Codec.INT` 和 `Codec.STRING`。 這些可以作為 `Codec` 類別上的靜態變數使用，並且通常用作更複雜的 codec 的基礎，如下所述。

## 建構 codec {#building-codec}

現在我們已經了解了如何使用 codec，讓我們來看看如何建立自己的 codec。 假設我們有以下類別，並且我們想從 json 檔案中反序列化它的實例：

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

對應的 json 檔案可能看起來像這樣：

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

我們可以將多個較小的 codec 組合到一個較大的 codec 中，來為這個類別建立 codec。 在這種情況下，我們需要每一個欄位的 codec：

- 一個 `Codec<Integer>`
- 一個 `Codec<Item>`
- 一個 `Codec<List<BlockPos>>`

我們可以從前面提到的 `Codec` 類別中的原始 codec 取得第一個，具體來說就是 `Codec.INT`。 而第二個可以從 `Registries.ITEM` 登錄取得，它有一個 `getCodec()` 方法，該方法會回傳一個 `Codec<Item>`。 我們沒有用於 `List<BlockPos>` 的預設 codec，但我們可以從 `BlockPos.CODEC` 建立一個。

### 清單 {#lists}

`Codec#listOf` 可用於建立任何 codec 的清單版本：

```java
Codec<List<BlockPos>> listCodec = BlockPos.CODEC.listOf();
```

應該注意的是，以這種方式建立的 codec 將始終反序列化為 `ImmutableList`。 如果你需要一個可變的清單，你可以使用 [xmap](#mutually-convertible-types) 在反序列化期間進行轉換。

### 合併用於 Record 類別的 Codec {#merging-codec-for-record-like-classes}

現在我們有每個欄位的單獨 codec，我們可以將它們合併為一個用於我們的類別的 codec，使用 `RecordCodecBuilder`。 這假設我們的類別有一個包含我們要序列化的每個欄位的建構子，並且每個欄位都有一個對應的 getter 方法。 這使得它非常適合與 record 一起使用，但它也可以與一般類別一起使用。

讓我們看看如何為我們的 `CoolBeansClass` 建立編碼器：

```java
public static final Codec<CoolBeansClass> CODEC = RecordCodecBuilder.create(instance -> instance.group(
    Codec.INT.fieldOf("beans_amount").forGetter(CoolBeansClass::getBeansAmount),
    Registries.ITEM.getCodec().fieldOf("bean_type").forGetter(CoolBeansClass::getBeanType),
    BlockPos.CODEC.listOf().fieldOf("bean_positions").forGetter(CoolBeansClass::getBeanPositions)
    // 這裡最多可以宣告 16 個欄位
).apply(instance, CoolBeansClass::new));
```

群組中的每一行都指定了一個編碼器、一個欄位名稱和一個 getter 方法。 `Codec#fieldOf` 呼叫用於將編碼器轉換為 [map 編碼器](#mapcodec)，而 `forGetter` 呼叫指定用於從類別實例中取得欄位值的 getter 方法。 同時，`apply` 呼叫指定用於建立新實例的建構函數。 請注意，群組中欄位的順序應與建構函數中引數的順序相同。

您也可以在此上下文中使用 `Codec#optionalFieldOf` 來使欄位成為可選欄位，如 [可選欄位](#optional-fields) 區段中所述。

### MapCodec，不要與 Codec&amp;lt;Map&amp;gt; 混淆 {#mapcodec}

呼叫 `Codec#fieldOf` 會將 `Codec<T>` 轉換為 `MapCodec<T>`，這是 `Codec<T>` 的變體，但不是直接實作。 `MapCodec`，顧名思義，保證序列化為鍵到值的映射，或其在使用的 `DynamicOps` 中的等效項。 某些函數可能需要一個而不是普通的編碼器。

這種建立 `MapCodec` 的特殊方式，基本上將來源編碼器的值封裝在一個映射中，並將指定的欄位名稱作為鍵。 例如，`Codec<BlockPos>` 在序列化為 JSON 時將如下所示：

```json
[1, 2, 3]
```

但當使用 `BlockPos.CODEC.fieldOf("pos")` 將其轉換為 `MapCodec<BlockPos>` 時，它將如下所示：

```json
{
  "pos": [1, 2, 3]
}
```

雖然 map 編碼器最常見的用途是與其他 map 編碼器合併，以建構一個用於完整類別欄位的編碼器，如上面 [合併類似紀錄的類別的編碼器](#merging-codec-for-record-like-classes) 區段中所述，但它們也可以使用 `MapCodec#codec` 轉換回一般編碼器，這將保留封裝其輸入值的相同行為。

#### 可選欄位 {#optional-fields}

`Codec#optionalFieldOf` 可以用來建立可選的 map 編碼器。 如果在反序列化期間，指定的欄位不存在於容器中，則它將被反序列化為空的 `Optional` 或指定的預設值。

```java
// 沒有預設值
MapCodec<Optional<BlockPos>> optionalCodec = BlockPos.CODEC.optionalFieldOf("pos");

// 有預設值
MapCodec<BlockPos> optionalCodec = BlockPos.CODEC.optionalFieldOf("pos", BlockPos.ORIGIN);
```

請注意，可選欄位將會靜默忽略反序列化期間可能發生的任何錯誤。 這表示如果欄位存在，但值無效，則該欄位將始終反序列化為預設值。

**自 1.20.2 起**，Minecraft 本身（不是 DFU！） 確實提供了 `codec#createStrictOptionalFieldCodec`，如果欄位值無效，則會完全無法反序列化。

### 常數、約束和組合 {#constants-constraints-composition}

#### 單元 {#unit}

`Codec.unit` 可以用來建立一個編碼器，該編碼器始終反序列化為一個常數值，無論輸入如何。 在序列化時，它不會執行任何操作。

```java
Codec<Integer> theMeaningOfCodec = Codec.unit(42);
```

#### 數值範圍 {#numeric-ranges}

`Codec.intRange` 及其同伴 `Codec.floatRange` 和 `Codec.doubleRange` 可以用來建立一個編碼器，該編碼器僅接受指定**包含**範圍內的數值。這適用於序列化和反序列化。 這適用於序列化和反序列化。

```java
// 不能超過 2
Codec<Integer> amountOfFriendsYouHave = Codec.intRange(0, 2);
```

#### 配對 {#pair}

`Codec.pair` 將兩個編碼器 `Codec<A>` 和 `Codec<B>` 合併為 `Codec<Pair<A, B>>`。 請記住，它僅在序列化為特定欄位的編碼器上才能正常運作，例如 [轉換後的 `MapCodec`](#mapcodec) 或 [紀錄編碼器](#merging-codec-for-record-like-classes)。
產生的編碼器將序列化為一個結合兩個使用編碼器欄位的映射。

例如，執行以下程式碼：

```java
// 建立兩個獨立的已封裝編碼器
Codec<Integer> firstCodec = Codec.INT.fieldOf("i_am_number").codec();
Codec<Boolean> secondCodec = Codec.BOOL.fieldOf("this_statement_is_false").codec();

// 將它們合併為配對編碼器
Codec<Pair<Integer, Boolean>> pairCodec = Codec.pair(firstCodec, secondCodec);

// 使用它來序列化資料
DataResult<JsonElement> result = pairCodec.encodeStart(JsonOps.INSTANCE, Pair.of(23, true));
```

將輸出以下 JSON：

```json
{
  "i_am_number": 23,
  "this_statement_is_false": true
}
```

#### Either {#either}

`Codec.either` 將兩個 codec `Codec<A>` 和 `Codec<B>` 合併到一個 `Codec<Either<A, B>>` 中。 產生的編碼器將在反序列化期間嘗試使用第一個編碼器，且**僅當該編碼器失敗時**，才會嘗試使用第二個編碼器。
如果第二個編碼器也失敗，則會回傳**第二個**編碼器的錯誤。

#### Maps {#maps}

為了處理具有任意鍵的 maps，例如 `HashMap`s，可以使用 `Codec.unboundedMap`。 這會為指定的 `Codec<K>` 和 `Codec<V>` 回傳一個 `Codec<Map<K, V>>`。 結果 codec 將序列化為 json 物件或目前 dynamic ops 可用的任何等效物件。

由於 json 和 nbt 的限制，使用的鍵 codec _必須_ 序列化為字串。 這包括非字串類型，但序列化為字串的 codec，例如 `Identifier.CODEC`。 請參閱以下範例：

```java
// 建立一個從 identifiers 到 integers 的 map 的 codec
Codec<Map<Identifier, Integer>> mapCodec = Codec.unboundedMap(Identifier.CODEC, Codec.INT);

// 使用它來序列化資料
DataResult<JsonElement> result = mapCodec.encodeStart(JsonOps.INSTANCE, Map.of(
    new Identifier("example", "number"), 23,
    new Identifier("example", "the_cooler_number"), 42
));
```

這將輸出以下 json：

```json
{
  "example:number": 23,
  "example:the_cooler_number": 42
}
```

你可以看到，這有效是因為 `Identifier.CODEC` 直接序列化為字串值。 對於不序列化為字串的簡單物件，也可以透過使用 [xmap & friends](#mutually-convertible-types) 轉換它們來實現類似的效果。

### 相互轉換的類型 {#mutually-convertible-types}

#### `xmap` {#xmap}

假設我們有兩個可以互相轉換的類別，但沒有父子關係。 例如，一個原版的 `BlockPos` 和 `Vec3d`。 如果我們有一個 codec，我們可以透過指定每個方向的轉換函數，使用 `Codec#xmap` 來為另一個類別建立 codec。

`BlockPos` 已經有一個 codec，但讓我們假裝它沒有。 我們可以透過基於 `Vec3d` 的 codec 來為它建立一個：

```java
Codec<BlockPos> blockPosCodec = Vec3d.CODEC.xmap(
    // 將 Vec3d 轉換為 BlockPos
    vec -> new BlockPos(vec.x, vec.y, vec.z),
    // 將 BlockPos 轉換為 Vec3d
    pos -> new Vec3d(pos.getX(), pos.getY(), pos.getZ())
);

// 當以這種方式將現有類別（例如 `X`）
// 轉換為你自己的類別 (`Y`) 時，最好
// 在 `Y` 中新增 `toX` 和 static `fromX` 方法，並在你的 `xmap`
// 呼叫中使用方法參考。
```

#### flatComapMap、comapFlatMap 和 flatXMap {#flatcomapmap-comapflatmap-flatxmap}

`Codec#flatComapMap`、`Codec#comapFlatMap` 和 `flatXMap` 類似於 xmap，但它們允許一個或兩個轉換函數回傳 DataResult。 這在實踐中很有用，因為特定的物件實例可能不一律適用於轉換。

以原版的 `Identifier`s 為例。 雖然所有的 identifiers 都可以轉換為字串，但並非所有的字串都是有效的 identifiers，所以使用 xmap 會意味著在轉換失敗時拋出難看的例外。
因此，它的內建 codec 實際上是 `Codec.STRING` 上的 `comapFlatMap`，很好地說明了如何使用它：

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
            return DataResult.error("Not a valid resource location: " + id + " " + e.getMessage());
        }
    }

    // ...
}
```

雖然這些方法真的很有幫助，但它們的名稱有點令人困惑，所以這裡有一個表格可以幫助你記住要使用哪一個：

| 方法                      | A -> B 一律有效嗎？ | B -> A 一律有效嗎？ |
| ----------------------- | ------------- | ------------- |
| `Codec<A>#xmap`         | 是             | 是             |
| `Codec<A>#comapFlatMap` | 否             | 是             |
| `Codec<A>#flatComapMap` | 是             | 否             |
| `Codec<A>#flatXMap`     | 否             | 否             |

### 登錄分派 {#registry-dispatch}

`Codec#dispatch` 讓我們定義一個 codecs 的登錄，並根據序列化資料中的欄位值分派到特定的 codec。 這在反序列化根據其類型具有不同欄位的物件時非常有用，但仍然代表同一件事。

例如，假設我們有一個抽象的 `Bean` 介面，有兩個實作類別：`StringyBean` 和 `CountingBean`。 要使用登錄分派序列化它們，我們需要幾件事：

- 每個 bean 類型的單獨 codec。
- 一個 `BeanType<T extends Bean>` 類別或 record，它代表 bean 的類型，並且可以回傳它的 codec。
- `Bean` 上的一個函數，用於檢索它的 `BeanType<?>`。
- 一個 map 或登錄，用於將 `Identifier`s 對應到 `BeanType<?>`s。
- 一個基於此登錄的 `Codec<BeanType<?>>`。 如果你使用 `net.minecraft.registry.Registry`，可以使用 `Registry#getCodec` 輕鬆建立一個。

有了所有這些，我們可以為 beans 建立一個登錄分派 codec：

@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/Bean.java)
@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/BeanType.java)
@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/StringyBean.java)
@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/CountingBean.java)
@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/BeanTypes.java)

```java
// 現在我們可以基於先前建立的登錄，建立一個用於 bean 類型的 codec
Codec<BeanType<?>> beanTypeCodec = BeanType.REGISTRY.getCodec();

// 基於此，這是我們用於 beans 的登錄分派 codec！
// 第一個參數是 bean 類型的欄位名稱。
// 預設情況下，它將預設為 "type"。
Codec<Bean> beanCodec = beanTypeCodec.dispatch("type", Bean::getType, BeanType::codec);
```

我們的新 codec 將 beans 序列化為 json 像這樣，只抓取與其特定類型相關的欄位：

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

### 遞迴 Codec {#recursive-codecs}

有時，擁有一個使用 _自身_ 來解碼特定欄位的 codec 很有用，例如在處理某些遞迴資料結構時。 在原版程式碼中，這用於 `Text` 物件，這些物件可以儲存其他 `Text`s 作為子物件。 可以使用 `Codec#recursive` 建構這樣的 codec。

例如，讓我們嘗試序列化一個單向連結的清單。 這種表示清單的方式由一堆節點組成，這些節點既儲存一個值，又儲存對清單中下一個節點的引用。 然後，清單由其第一個節點表示，並且遍歷清單是透過跟隨下一個節點直到沒有剩餘的節點來完成的。 這是一個儲存 integers 的節點的簡單實作。

```java
public record ListNode(int value, ListNode next) {}
```

我們不能透過普通方式為此建構一個 codec，因為我們會為 `next` 欄位使用什麼 codec 呢？ 我們需要一個 `Codec<ListNode>`，這正是我們正在建構的！ `Codec#recursive` 讓我們使用一個看起來很神奇的 lambda 來實現這一點：

```java
Codec<ListNode> codec = Codec.recursive(
  "ListNode", // codec 的名稱
  selfCodec -> {
    // 在這裡，`selfCodec` 代表 `Codec<ListNode>`，就像它已經被建構好一樣
    // 這個 lambda 應該回傳我們從一開始就想使用的 codec，
    // 這個 codec 會透過 `selfCodec` 引用自身
    return RecordCodecBuilder.create(instance ->
      instance.group(
        Codec.INT.fieldOf("value").forGetter(ListNode::value),
         // `next` 欄位將使用 self-codec 遞迴處理
        Codecs.createStrictOptionalFieldCodec(selfCodec, "next", null).forGetter(ListNode::next)
      ).apply(instance, ListNode::new)
    );
  }
);
```

序列化的 `ListNode` 可能看起來像這樣：

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

## 參考文獻 {#references}

- 可以在[非官方 DFU JavaDoc](https://kvverti.github.io/Documented-DataFixerUpper/snapshot/com/mojang/serialization/Codec) 中找到有關 Codec 和相關 API 的更全面的文件。
- 本指南的一般結構在很大程度上受到 [Forge Community Wiki 的 Codecs 頁面](https://forge.gemwire.uk/wiki/Codecs)的啟發，這是一個更特定於 Forge 的相同主題的視角。
