---
title: Codecs
description: Ein umfassender Leitfaden zum Verständnis und zur Verwendung von Mojangs Codec-System zum Serialisieren und Deserialisieren von Objekten.
authors:
  - enjarai
  - Syst3ms

search: false
---

# Codecs

Ein Codec ist ein System zur einfachen Serialisierung von Java-Objekten und ist in Mojangs DataFixerUpper (DFU)
Bibliothek enthalten, die in Minecraft enthalten ist. In einem Modding-Kontext können sie als Alternative zu GSON und Jankson verwendet werden, wenn man benutzerdefinierte JSON-Dateien liest und schreibt, wobei sie mehr und mehr an Bedeutung gewinnen, da Mojang eine Menge alten Code umschreibt, um Codecs zu verwenden.

Codecs werden in Verbindung mit einer anderen API von DFU, `DynamicOps`, verwendet. Ein Codec definiert die Struktur eines Objekts, während dynamische Ops verwendet werden, um ein Format zu definieren, in das und aus dem serialisiert werden soll, zum Beispiel JSON oder NBT. Das bedeutet, dass jeder Codec mit allen dynamischen Ops verwendet werden kann und umgekehrt, was eine große Flexibilität ermöglicht.

## Verwenden von Codecs

### Serialisierung und Deserialisierung

Die grundlegende Verwendung eines Codecs ist die Serialisierung und Deserialisierung von Objekten in und aus einem bestimmten Format.

Da einige Vanilla-Klassen bereits Codecs definiert haben, können wir diese als Beispiel verwenden. Mojang hat uns außerdem standardmäßig zwei dynamische Ops-Klassen zur Verfügung gestellt, `JsonOps` und `NbtOps`, die die meisten Anwendungsfälle abdecken.

Nehmen wir nun an, wir wollen eine `BlockPos` nach JSON und zurück serialisieren. Wir können dies machen, indem wir den Codec, der statisch in `BlockPos.CODEC` gespeichert ist, mit den Methoden `Codec#encodeStart` bzw.

```java
BlockPos pos = new BlockPos(1, 2, 3);

// Serialisieren der BlockPos zu einem JsonElement
DataResult<JsonElement> result = BlockPos.CODEC.encodeStart(JsonOps.INSTANCE, pos);
```

Bei Verwendung eines Codecs werden die Werte in Form eines `DataResult` zurückgegeben. Dies ist ein Wrapper, der entweder einen Erfolg oder einen Misserfolg darstellen kann. Wir können dies auf verschiedene Weise nutzen: Wenn wir nur unseren serialisierten Wert haben wollen, gibt `DataResult#result` einfach ein `Optional` zurück, das unseren Wert enthält, während `DataResult#resultOrPartial` uns auch die Möglichkeit gibt, eine Funktion zu liefern, die eventuell aufgetretene Fehler behandelt. Letzteres ist besonders nützlich für benutzerdefinierte Datapack-Ressourcen, bei denen wir Fehler protokollieren wollen, ohne dass sie an anderer Stelle Probleme verursachen.

Nehmen wir also unseren serialisierten Wert und verwandeln ihn zurück in eine `BlockPos`:

```java
// Wenn du einen Mod schreibst, musst du natürlich mit leeren Optionals richtig umgehen
JsonElement json = result.resultOrPartial(LOGGER::error).orElseThrow();

// Hier haben wir unseren JSON-Wert, der `[1, 2, 3]` entsprechen sollte,
// da dies das vom BlockPos-Codec verwendete Format ist.
LOGGER.info("Serialized BlockPos: {}", json);

// Jetzt werden wir wir das JsonElement zurück in eine BlockPos deserialisieren
DataResult<BlockPos> result = BlockPos.CODEC.parse(JsonOps.INSTANCE, json);

// Auch hier holen wir uns den Wert einfach aus dem Ergebnis
BlockPos pos = result.resultOrPartial(LOGGER::error).orElseThrow();

// Und wir können sehen, dass wir unsere BlockPos erfolgreich serialisiert und deserialisiert haben!
LOGGER.info("Deserialized BlockPos: {}", pos);
```

### Eingebaute Codecs

Wie bereits erwähnt, hat Mojang bereits Codecs für mehrere Vanilla- und Standard-Java-Klassen definiert, einschließlich, aber nicht beschränkt auf `BlockPos`, `BlockState`, `ItemStack`, `Identifier`, `Text` und Regex `Pattern`. Codecs für Mojangs eigene Klassen sind normalerweise als statische Attribute mit dem Namen `CODEC` in der Klasse selbst zu finden, während die meisten anderen in der Klasse `Codecs` untergebracht sind. Es sollte auch beachtet werden, dass alle Vanilla-Registries eine `getCodec()`-Methode enthalten, zum Beispiel kann man `Registries.BLOCK.getCodec()` verwenden, um einen `Codec<Block>` zu erhalten, der in die Block-ID und zurück serialisiert wird.

Die Codec API selbst enthält auch einige Codecs für primitive Typen wie `Codec.INT` und `Codec.STRING`. Diese sind als statische Attribute der Klasse "Codec" verfügbar und werden in der Regel als Basis für komplexere Codecs verwendet, wie im Folgenden erläutert.

## Erstellen von Codecs

Nachdem wir nun gesehen haben, wie man Codecs verwendet, wollen wir uns ansehen, wie wir unsere eigenen erstellen können. Angenommen, wir haben die folgende Klasse und wollen Instanzen davon aus JSON-Dateien deserialisieren:

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

Die entsprechende JSON-Datei könnte etwa so aussehen:

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

Wir können einen Codec für diese Klasse erstellen, indem wir mehrere kleinere Codecs zu einem größeren zusammenfügen. In diesem Fall brauchen wir einen für jedes Feld:

- ein `Codec<Integer>`
- ein `Codec<Item>`
- ein `Codec<List<BlockPos>>`

Den ersten können wir aus den oben erwähnten primitiven Codecs in der Klasse `Codec` beziehen, insbesondere aus `Codec.INT`. Der zweite kann aus dem Register `Registries.ITEM` bezogen werden, das eine Methode `getCodec()` hat, die einen `Codec<Item>` zurückgibt. Wir haben keinen Standard-Codec für `List<BlockPos>`, aber wir können einen aus `BlockPos.CODEC` erstellen.

### Listen

`Codec#listOf` kann verwendet werden, um eine Listenversion eines beliebigen Codecs zu erstellen:

```java
Codec<List<BlockPos>> listCodec = BlockPos.CODEC.listOf();
```

Es sollte beachtet werden, dass Codecs, die auf diese Weise erstellt werden, immer in eine `ImmutableList` deserialisiert werden. Wenn du stattdessen eine veränderbare Liste benötigst, kannst du [xmap](#wechselseitig-konvertierbare-typen) verwenden, um sie während der Deserialisierung in eine solche zu konvertieren.

### Zusammenführung von Codecs für Record-ähnliche Klassen

Da wir nun für jedes Feld einen eigenen Codec haben, können wir sie mit einem `RecordCodecBuilder` zu einem Codec für unsere Klasse kombinieren. Dies setzt voraus, dass unsere Klasse einen Konstruktor hat, der jedes Feld enthält, das wir serialisieren wollen, und dass jedes Feld eine entsprechende Getter-Methode hat. Dies macht es perfekt für die Verwendung in Verbindung mit Records, aber es kann auch mit normalen Klassen verwendet werden.

Schauen wir uns an, wie wir einen Codec für unsere `CoolBeansClass` erstellen können:

```java
public static final Codec<CoolBeansClass> CODEC = RecordCodecBuilder.create(instance -> instance.group(
    Codec.INT.fieldOf("beans_amount").forGetter(CoolBeansClass::getBeansAmount),
    Registries.ITEM.getCodec().fieldOf("bean_type").forGetter(CoolBeansClass::getBeanType),
    BlockPos.CODEC.listOf().fieldOf("bean_positions").forGetter(CoolBeansClass::getBeanPositions)
    // Hier können bis zu 16 Attribute deklariert werden
).apply(instance, CoolBeansClass::new));
```

Jede Zeile in der Gruppe gibt einen Codec, einen Attributname und eine Getter-Methode an. Der Aufruf `Codec#fieldOf` wird verwendet, um den Codec in einen [MapCodec](#mapcodec) zu konvertieren, und der Aufruf `forGetter` spezifiziert die Getter-Methode, die verwendet wird, um den Wert des Attributs von einer Instanz der Klasse abzurufen. In der Zwischenzeit gibt der Aufruf `apply` den Konstruktor an, der zur Erzeugung neuer Instanzen verwendet wird. Beachte, dass die Reihenfolge der Attribute in der Gruppe dieselbe sein sollte wie die Reihenfolge der Argumente im Konstruktor.

Du kannst auch `Codec#optionalFieldOf` in diesem Zusammenhang verwenden, um ein Feld optional zu machen, wie in dem Abschnitt [Optionale Attribute](#optionale-attribute) erklärt.

### MapCodec, nicht zu verwechseln mit Codec&lt;Map&gt; {#mapcodec}

Der Aufruf von `Codec#fieldOf` wird einen `Codec<T>` in einen `MapCodec<T>` umwandeln, der eine Variante, aber keine direkte Implementierung von `Codec<T>` ist. `MapCodec`s werden, wie ihr Name schon sagt, garantiert in eine Schlüssel-zu-Wert-Map oder deren Äquivalent in den verwendeten `DynamicOps` serialisiert. Einige Funktionen können einen solchen Codec über einen normalen Codec erfordern.

Diese besondere Art der Erstellung eines `MapCodec` verpackt im Wesentlichen den Wert des Quellcodecs in eine Map ein, wobei der angegebene Attributname als Schlüssel dient. Zum Beispiel würde ein `Codec<BlockPos>`, wenn er in JSON serialisiert wird, wie folgt aussehen:

```json
[1, 2, 3]
```

Bei der Umwandlung in einen `MapCodec<BlockPos>` unter Verwendung von `BlockPos.CODEC.fieldOf("pos")` würde es jedoch wie folgt aussehen:

```json
{
  "pos": [1, 2, 3]
}
```

Während die gebräuchlichste Verwendung für Map-Codecs darin besteht, mit anderen Map-Codecs zusammengeführt zu werden, um einen Codec für eine ganze Klasse von Felder zu konstruieren, wie im Abschnitt [Zusammenführung von Codecs für Record-ähnliche Klassen](#Zusammenführung-von-Codecs-für-Record-ähnliche-Klassen) oben erklärt wurde, können sie auch mit `MapCodec#codec` in reguläre Codecs zurückverwandelt werden, die das gleiche Verhalten beibehalten, nämlich ihren Eingabewert verpacken.

#### Optionale Attribute

`Codec#optionalFieldOf` kann verwendet werden, um einen optionalen Mapcodec zu erstellen. Wenn das angegebene Feld bei der Deserialisierung nicht im Container vorhanden ist, wird es entweder als leeres `Optional` oder als angegebener Standardwert deserialisiert.

```java
// Ohne einem Standardwert
MapCodec<Optional<BlockPos>> optionalCodec = BlockPos.CODEC.optionalFieldOf("pos");

// Mit einem Standardwert
MapCodec<BlockPos> optionalCodec = BlockPos.CODEC.optionalFieldOf("pos", BlockPos.ORIGIN);
```

Beachte, dass optionale Felder alle Fehler, die bei der Deserialisierung auftreten können, ignorieren. Das heißt, wenn das Feld vorhanden ist, aber der Wert ungültig ist, wird das Feld immer als Standardwert deserialisiert.

**Seit 1.20.2**, Minecraft selbst (nicht DFU!) bietet jedoch `Codecs#createStrictOptionalFieldCodec`, das die Deserialisierung fehlschlägt, wenn der Feldwert ungültig ist.

### Konstanten, Beschränkungen und Komposition

#### Einheit

`Codec.unit` kann verwendet werden, um einen Codec zu erstellen, der immer zu einem konstanten Wert deserialisiert, unabhängig von der Eingabe. Bei der Serialisierung wird nichts getan.

```java
Codec<Integer> theMeaningOfCodec = Codec.unit(42);
```

#### Zahlenbereiche

`Codec.intRange` und seine Kollegen `Codec.floatRange` und `Codec.doubleRange` können verwendet werden, um einen Codec zu erstellen, der nur Zahlenwerte innerhalb eines bestimmten **inklusiven** Bereichs akzeptiert. Dies gilt sowohl für die Serialisierung als auch für die Deserialisierung.

```java
// Kann nicht mehr als 2 sein
Codec<Integer> amountOfFriendsYouHave = Codec.intRange(0, 2);
```

#### Paar

`Codec.pair` fasst zwei Codecs, `Codec<A>` und `Codec<B>`, zu einem `Codec<Pair<A, B>` zusammen. Denk daran, dass dies nur richtig mit Codecs funktioniert, die in ein bestimmtes Attribut serialisiert werden, wie zum Beispiel [konvertierte `MapCodec`s](#mapcodec) oder [Record Codecs](#Zusammenführung-von-Codecs-für-Record-ähnliche-Klassen).
Der resultierende Codec wird zu einer Map serialisiert, die die Attribute der beiden verwendeten Codecs kombiniert.

Beispielsweise wird beim Ausführen dieses Codes:

```java
// Erstellen von zwei separaten verpackten Codecs
Codec<Integer> firstCodec = Codec.INT.fieldOf("i_am_number").codec();
Codec<Boolean> secondCodec = Codec.BOOL.fieldOf("this_statement_is_false").codec();

// Sie zu einem Paar-Codec zusammenführen
Codec<Pair<Integer, Boolean>> pairCodec = Codec.pair(firstCodec, secondCodec);

// Zum Serialisieren von Daten verwenden
DataResult<JsonElement> result = pairCodec.encodeStart(JsonOps.INSTANCE, Pair.of(23, true));
```

Folgendes JSON generiert:

```json
{
  "i_am_number": 23,
  "this_statement_is_false": true
}
```

#### Entweder-Oder-Kombination

`Codec.either` kombiniert zwei Codecs, `Codec<A>` und `Codec<B>`, zu einem `Codec<Either<A, B>>`. Der resultierende Codec wird bei der Deserialisierung versuchen, den ersten Codec zu verwenden, und _nur wenn das fehlschlägt_, versuchen, den zweiten Codec zu verwenden.
Wenn der zweite Codec ebenfalls fehlschlägt, wird der Fehler des _zweiten_ Codecs zurückgegeben.

#### Maps

Für die Verarbeitung von Maps mit beliebigen Schlüsseln, wie zum Beispiel `HashMap`s, kann `Codec.unboundedMap` verwendet werden. Dies gibt einen `Codec<Map<K, V>>` für einen gegebenen `Codec<K>` und `Codec<V>` zurück. Der resultierende Codec wird zu einem JSON-Objekt serialisiert oder oder ein gleichwertiges Objekt, das für die aktuellen dynamische Ops verfügbar ist.

Aufgrund der Einschränkungen von JSON und NBT _muss_ der verwendete Schlüsselcodec zu einer Zeichenkette serialisiert werden. Dazu gehören auch Codecs für Typen, die selbst keine Strings sind, aber zu ihnen serialisiert werden, wie zum Beispiel `Identifier.CODEC`. Siehe folgendes Beispiel:

```java
// Erstellen eines Codecs für eine Abbildung von Bezeichnern auf Ganzzahlen
Codec<Map<Identifier, Integer>> mapCodec = Codec.unboundedMap(Identifier.CODEC, Codec.INT);

// Zum Serialisieren von Daten verwenden
DataResult<JsonElement> result = mapCodec.encodeStart(JsonOps.INSTANCE, Map.of(
    new Identifier("example", "number"), 23,
    new Identifier("example", "the_cooler_number"), 42
));
```

Dadurch wird dieses JSON ausgegeben:

```json
{
  "example:number": 23,
  "example:the_cooler_number": 42
}
```

Wie du sehen kannst, funktioniert dies, weil `Identifier.CODEC` direkt zu einem String-Wert serialisiert wird. Einen ähnlichen Effekt kann man für einfache Objekte, die nicht in Strings serialisiert werden, erreichen, indem [Wechselseitig konvertierbare Typen](#wechselseitig-konvertierbare-typen) verwendet werden, um um sie zu konvertieren.

### Wechselseitig konvertierbare Typen

#### `xmap`

Angenommen, wir haben zwei Klassen, die ineinander umgewandelt werden können, aber keine Eltern-Kind-Beziehung haben. Zum Beispiel, eine einfache `BlockPos` und `Vec3d`. Wenn wir einen Codec für eine Richtung haben, können wir mit `Codec#xmap` einen Codec für die andere Richtung erstellen, indem wir eine Konvertierungsfunktion für jede Richtung angeben.

B`BlockPos` hat bereits einen Codec, aber tun wir mal so, als ob er keinen hätte. Wir können einen solchen Codec erstellen, indem wir ihn auf den Codec für `Vec3d` stützen, etwa so:

```java
Codec<BlockPos> blockPosCodec = Vec3d.CODEC.xmap(
    // Konvertiert Vec3d zu BlockPos
    vec -> new BlockPos(vec.x, vec.y, vec.z),
    // Konvertiert BlockPos zu Vec3d
    pos -> new Vec3d(pos.getX(), pos.getY(), pos.getZ())
);

// Bei der Konvertierung einer bestehenden Klasse (zum Beispiel `X`)
// in deine eigene Klasse (`Y`), kann es sinnvoll sein
// die Methode `toX` und die statische Methode `fromX` zu `Y` und
// Methodenreferenzen in deinem `xmap`-Aufruf hinzufügen.
```

#### flatComapMap, comapFlatMap und flatXMap

`Codec#flatComapMap`, `Codec#comapFlatMap` und `flatXMap` sind ähnlich wie xmap, erlauben aber, dass eine oder beide der Konvertierungsfunktionen ein DataResult zurückgeben. Dies ist in der Praxis nützlich, da eine bestimmte Objektinstanz möglicherweise nicht nicht immer für die Konvertierung gültig ist.

Nimm zum Beispiel Vanille `Identifier` her. Während alle Bezeichner in Zeichenketten umgewandelt werden können, sind nicht alle Zeichenketten gültige Bezeichner, Daher würde die Verwendung von xmap hässliche Exceptions werfen, wenn die Umwandlung fehlschlägt.
Aus diesem Grund ist der eingebaute Codec eigentlich eine `comapFlatMap` auf `Codec.STRING`, was sehr schön veranschaulicht, wie man ihn verwendet:

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

Diese Methoden sind zwar sehr hilfreich, aber ihre Namen sind etwas verwirrend, deshalb hier eine Tabelle, damit du merken kannst, welche zu verwenden ist:

| Methode                 | A -> B immer gültig? | B -> A immer gültig? |
| ----------------------- | -------------------- | -------------------- |
| `Codec<A>#xmap`         | Ja                   | Ja                   |
| `Codec<A>#comapFlatMap` | Nein                 | Ja                   |
| `Codec<A>#flatComapMap` | Ja                   | Nein                 |
| `Codec<A>#flatXMap`     | Nein                 | Nein                 |

### Registry Dispatch

`Codec#dispatch` ermöglicht die Definition eines Registry von Codecs und die Abfertigung eines bestimmten Codecs auf der Grundlage des Wertes eines
Attributs in den serialisierten Daten. Dies ist sehr nützlich bei der Deserialisierung von Objekten, die je nach Typ unterschiedliche Attribute haben, aber dennoch dasselbe Objekt darstellen.

Nehmen wir an, wir haben ein abstraktes `Bean`-Interface mit zwei implementierenden Klassen: `StringyBean` und `CountingBean`. Um diese mit einem Registry Dispatch zu serialisieren, benötigen wir einige Dinge:

- Separate Codecs für jede Art von Bohnen.
- Eine `BeanType<T extends Bean>`-Klasse oder ein Datensatz, der den Typ der Bohne repräsentiert und den Codec für sie zurückgeben kann.
- Eine Funktion für `Bean` zum Abrufen ihres `BeanType<?>`.
- Eine Map oder Registry, um `Identifier` auf `BeanType<?>` abzubilden.
- Ein `Codec<BeanType<?>>`, der auf dieser Registry basiert. Wenn du eine `net.minecraft.registry.Registry` verwendest, kann eine solche einfach mit `Registry#getCodec` erstellt werden.

Mit all dem können wir einen Registry Dispatch Codec für Bohnen erstellen:

@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/Bean.java)
@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/BeanType.java)
@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/StringyBean.java)
@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/CountingBean.java)
@[code transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/codec/BeanTypes.java)

```java
// Jetzt können wir einen Codec für Bohnentypen erstellen
// auf der Grundlage des zuvor erstellten Registry
Codec<BeanType<?>> beanTypeCodec = BeanType.REGISTRY.getCodec();

// Und darauf aufbauend, hier ist unser Registry Dispatch Codec für Bohnen!
// Das erste Argument ist der Argumentname für den Bohnen-Typ.
// Wenn du das Attribut weglässt, wird es standardmäßig auf "type" gesetzt.
Codec<Bean> beanCodec = beanTypeCodec.dispatch("type", Bean::getType, BeanType::getCodec);
```

Unser neuer Codec serialisiert Bohnen zu JSON und erfasst dabei nur die Felder, die für ihren spezifischen Typ relevant sind:

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

### Rekursive Codecs

Manchmal ist es nützlich, einen Codec zu haben, der _sich selbst_ verwendet, um bestimmte Felder zu dekodieren, zum Beispiel wenn es um bestimmte rekursive Datenstrukturen geht. Im Vanilla-Code wird dies für `Text`-Objekte verwendet, die andere `Text`e als Kinder speichern können. Ein solcher Codec kann mit `Codecs#createRecursive` erstellt werden.

Versuchen wir zum Beispiel, eine einfach verknüpfte Liste zu serialisieren. Diese Art der Darstellung von Listen besteht aus einem Bündel von Knoten, die sowohl einen Wert als auch einen Verweis auf den nächsten Knoten in der Liste enthalten. Die Liste wird dann durch ihren ersten Knoten repräsentiert, und das Durchlaufen der Liste erfolgt durch Verfolgen des nächsten Knotens, bis keiner mehr übrig ist. Hier ist eine einfache Implementierung von Knoten, die ganze Zahlen speichern.

```java
public record ListNode(int value, ListNode next) {}
```

Wir können dafür keinen Codec mit normalen Mitteln konstruieren, denn welchen Codec würden wir für das Attribut `next` verwenden? Wir bräuchten einen `Codec<ListNode>`, und den sind wir gerade dabei zu konstruieren! Mit `Codecs#createRecursive` können wir das mit einem magisch aussehenden Lambda erreichen:

```java
Codec<ListNode> codec = Codecs.createRecursive(
  "ListNode", // Ein Name für den Codec
  selfCodec -> {
    // Hier repräsentiert `selfCodec` den `Codec<ListNode>`, als ob er bereits konstruiert wäre
    // Dieses Lambda sollte den Codec zurückgeben, den wir von Anfang an verwenden wollten,
    // der sich durch `selfCodec` auf sich selbst bezieht
    return RecordCodecBuilder.create(instance ->
      instance.group(
        Codec.INT.fieldOf("value").forGetter(ListNode::value),
         // das Attribut `next` wird rekursiv mit dem eigenen Codec behandelt
        Codecs.createStrictOptionalFieldCodec(selfCodec, "next", null).forGetter(ListNode::next)
      ).apply(instance, ListNode::new)
    );
  }
);
```

Ein serialisierter `ListNode` kann dann wie folgt aussehen:

```json
{
  "value": 2,
  "next": {
    "value": 3,
    "next" : {
      "value": 5
    }
  }
}
```

## Referenzen

- Eine viel umfassendere Dokumentation von Codecs und verwandten APIs findest du in der [Inoffiziellen DFU JavaDoc](https://kvverti.github.io/Documented-DataFixerUpper/snapshot/com/mojang/serialization/Codec).
- Die allgemeine Struktur dieses Leitfadens wurde stark von dem [Forge Community Wiki's Seiten zu Codecs](https://forge.gemwire.uk/wiki/Codecs) inspiriert, einer eher Forge-spezifischen Darstellung desselben Themas.
