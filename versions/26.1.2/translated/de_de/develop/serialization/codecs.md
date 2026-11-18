---
title: Codecs
description: Ein umfassender Leitfaden zum Verständnis und zur Verwendung von Mojangs Codec-System zum Serialisieren und Deserialisieren von Objekten.
authors:
  - CelDaemon
  - enjarai
  - Syst3ms
resources:
  https://docs.neoforged.net/docs/datastorage/codecs/: Codecs - NeoForge Docs
  https://docs.neoforged.net/docs/networking/streamcodecs/: Stream Codecs - NeoForge Docs
  https://kvverti.github.io/Documented-DataFixerUpper/snapshot/com/mojang/serialization/Codec: Inoffizielle DFU JavaDocs
  https://forge.gemwire.uk/wiki/Codecs: Codecs - Forge Community Wiki
---

Ein Codec ist ein System zur einfachen Serialisierung von Java-Objekten und ist in Mojangs DataFixerUpper (DFU)
Bibliothek enthalten, die in Minecraft enthalten ist. In einem Modding-Kontext können sie als Alternative zu GSON und Jankson verwendet werden, wenn man benutzerdefinierte JSON-Dateien liest und schreibt, wobei sie mehr und mehr an Bedeutung gewinnen, da Mojang eine Menge alten Code umschreibt, um Codecs zu verwenden.

Codecs werden in Verbindung mit einer anderen API von DFU, `DynamicOps`, verwendet. Ein Codec definiert die Struktur eines Objekts, während dynamische Ops verwendet werden, um ein Format zu definieren, in das und aus dem serialisiert werden soll, zum Beispiel JSON oder NBT. Das bedeutet, dass jeder Codec mit allen dynamischen Ops verwendet werden kann und umgekehrt, was eine große Flexibilität ermöglicht.

## Verwenden von Codecs {#using-codecs}

### Serialisierung und Deserialisierung {#serializing-and-deserializing}

Die grundlegende Verwendung eines Codecs ist die Serialisierung und Deserialisierung von Objekten in und aus einem bestimmten Format.

Da einige Vanilla-Klassen bereits Codecs definiert haben, können wir diese als Beispiel verwenden. Mojang hat uns außerdem standardmäßig zwei dynamische Ops-Klassen zur Verfügung gestellt, `JsonOps` und `NbtOps`, die die meisten Anwendungsfälle abdecken.

Nehmen wir nun an, wir wollen eine `BlockPos` nach JSON und zurück serialisieren. Wir können dies machen, indem wir den Codec, der statisch in `BlockPos.CODEC` gespeichert ist, mit den Methoden `Codec#encodeStart` bzw. `Codec#parse` verwenden.

::: code-group

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#encode_blockpos[Java]

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/codec_examples/serialize_blockpos.json[Output]

:::

Bei Verwendung eines Codecs werden die Werte in Form eines `DataResult` zurückgegeben. Dies ist ein Wrapper, der entweder einen Erfolg oder einen Fehler darstellen kann. Wir können dies auf verschiedene Weise nutzen: Wenn wir nur unseren serialisierten Wert haben wollen, gibt `DataResult#result` einfach ein `Optional` zurück, der unseren Wert enthält, während `DataResult#resultOrPartial` uns auch die Möglichkeit gibt, eine Funktion zu liefern, die eventuell aufgetretene Fehler behandelt. Letzteres ist besonders nützlich für benutzerdefinierte Datapack-Ressourcen, bei denen wir Fehler protokollieren wollen, ohne dass sie an anderer Stelle Probleme verursachen.

Nehmen wir also unseren serialisierten Wert und verwandeln ihn zurück in eine `BlockPos`:

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#parse_blockpos

### Eingebaute Codecs {#built-in-codecs}

Wie bereits erwähnt, hat Mojang bereits Codecs für mehrere Vanilla- und Standard-Java-Klassen definiert, einschließlich, aber nicht beschränkt auf `BlockPos`, `BlockState`, `ItemStack`, `Identifier`, `Component` und Regex `Pattern`. Codecs für Mojangs eigene Klassen sind normalerweise als statische Felder mit dem Namen `CODEC` in der Klasse selbst zu finden, während die meisten anderen in der Klasse `Codecs` untergebracht sind. Es sollte auch beachtet werden, dass alle Vanilla-Registries eine Methode zum Abrufen eines `Codec` enthalten. Du kannst beispielsweise `BuiltInRegistries.BLOCK.byNameCodec()` verwenden, um einen `Codec<Block>` abzurufen, der zur Block-ID und zurück serialisiert, und `holderByNameCodec()`, um einen `Codec<Holder<Block>>` abzurufen.

Die Codec API selbst enthält auch einige Codecs für primitive Typen wie `Codec.INT` und `Codec.STRING`. Diese sind als statische Felder der Klasse `Codec` verfügbar und werden in der Regel als Basis für komplexere Codecs verwendet, wie im Folgenden erläutert.

## Erstellen von Codecs {#building-codecs}

Nachdem wir nun gesehen haben, wie man Codecs verwendet, wollen wir uns ansehen, wie wir unsere eigenen erstellen können. Angenommen, wir haben die folgende Klasse und wollen Instanzen davon aus JSON-Dateien deserialisieren:

<<< @/reference/26.1.2/src/main/java/com/example/docs/codec/CoolBeansClass.java#bean_class

Die entsprechende JSON-Datei könnte etwa so aussehen:

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/codec_examples/cool_beans.json

Wir können einen Codec für diese Klasse erstellen, indem wir mehrere kleinere Codecs zu einem größeren zusammenfügen. In diesem Fall brauchen wir einen für jedes Feld:

- ein `Codec<Integer>`
- ein `Codec<Item>`
- ein `Codec<List<BlockPos>>`

Den ersten können wir aus den oben erwähnten primitiven Codecs in der Klasse `Codec` beziehen, insbesondere aus `Codec.INT`. Der zweite kann aus der Registry `BuiltInRegistries.ITEM` bezogen werden, die eine Methode `byNameCodec()` hat, die einen `Codec<Item>` zurückgibt. Wir haben keinen Standard-Codec für `List<BlockPos>`, aber wir können einen aus `BlockPos.CODEC` erstellen.

### Listen {#lists}

`Codec#listOf` kann verwendet werden, um eine Listenversion eines beliebigen Codecs zu erstellen:

::: code-group

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#list_codec[Codec]

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#list_codec_data[Input]

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/codec_examples/list_codec.json[Output]

:::

Es sollte beachtet werden, dass Codecs, die auf diese Weise erstellt werden, immer in eine `ImmutableList` deserialisiert werden. Wenn du stattdessen eine veränderbare Liste benötigst, kannst du [xmap](#wechselseitig-konvertierbare-typen) verwenden, um sie während der Deserialisierung in eine solche zu konvertieren.

### Zusammenführung von Codecs für Record-ähnliche Klassen {#merging-codecs-for-record-like-classes}

Da wir nun für jedes Feld einen eigenen Codec haben, können wir sie mit einem `RecordCodecBuilder` zu einem Codec für unsere Klasse kombinieren. Dies setzt voraus, dass unsere Klasse einen Konstruktor hat, der jedes Feld enthält, das wir serialisieren wollen, und das jedes Feld eine entsprechende Getter-Methode hat. Dies macht es perfekt für die Verwendung in Verbindung mit Records, aber es kann auch mit normalen Klassen verwendet werden.

Schauen wir uns an, wie wir einen Codec für unsere `CoolBeansClass` erstellen können:

::: code-group

<<< @/reference/26.1.2/src/main/java/com/example/docs/codec/CoolBeansClass.java#bean_codec[Codec]

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#bean_codec_data[Input]

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/codec_examples/cool_beans.json[Output]

:::

Jede Zeile in der Gruppe gibt einen Codec, einen Attributname und eine Getter-Methode an. Der Aufruf `Codec#fieldOf` wird verwendet, um den Codec in einen [MapCodec](#mapcodec) zu konvertieren, und der Aufruf `forGetter` spezifiziert die Getter-Methode, die verwendet wird, um den Wert des Attributs von einer Instanz der Klasse abzurufen. In der Zwischenzeit gibt der Aufruf `apply` den Konstruktor an, der zur Erzeugung neuer Instanzen verwendet wird. Beachte, dass die Reihenfolge der Attribute in der Gruppe dieselbe sein sollte wie die Reihenfolge der Argumente im Konstruktor.

Du kannst auch `Codec#optionalFieldOf` in diesem Zusammenhang verwenden, um ein Feld optional zu machen, wie in dem Abschnitt [Optionale Attribute](#optional-fields) erklärt.

### MapCodec, nicht verwechseln mit Codec&lt;Map&gt; {#mapcodec}

Der Aufruf von `Codec#fieldOf` wird einen `Codec<T>` in einen `MapCodec<T>` umwandeln, der eine Variante, aber keine direkte Implementierung von `Codec<T>` ist. `MapCodec`s werden, wie ihr Name schon sagt, garantiert in eine Schlüssel-zu-Wert-Map oder deren Äquivalent in den verwendeten `DynamicOps` serialisiert. Einige Funktionen können einen solchen Codec über einen normalen Codec erfordern.

Diese besondere Art der Erstellung eines `MapCodec` verpackt im Wesentlichen den Wert des Quellcodecs in eine Map ein, wobei der angegebene Attributname als Schlüssel dient. Zum Beispiel würde ein `Codec<BlockPos>`, wenn er in JSON serialisiert wird, wie folgt aussehen:

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/codec_examples/plain_codec.json

Bei der Umwandlung in einen `MapCodec<BlockPos>` unter Verwendung von `BlockPos.CODEC.fieldOf("pos")` würde es jedoch wie folgt aussehen:

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/codec_examples/map_codec.json

Während die gebräuchlichste Verwendung für Map-Codecs darin besteht, mit anderen Map-Codecs zusammengeführt zu werden, um einen Codec für eine ganze Klasse von Felder zu konstruieren, wie im Abschnitt [Zusammenführung von Codecs für Record-ähnliche Klassen](#Zusammenführung-von-Codecs-für-Record-ähnliche-Klassen) oben erklärt wurde, können sie auch mit `MapCodec#codec` in reguläre Codecs zurückverwandelt werden, die das gleiche Verhalten beibehalten, nämlich ihren Eingabewert verpacken.

#### Optionale Felder {#optional-fields}

`Codec#optionalFieldOf` kann verwendet werden, um einen optionalen Mapcodec zu erstellen. Wenn das angegebene Feld bei der Deserialisierung nicht im Container vorhanden ist, wird es entweder als leerer `Optional` oder als angegebener Standardwert deserialisiert.

::: code-group

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#optional_field[Codec]

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#optional_field_data[Input]

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/codec_examples/optional_field.json[Output]

:::

Um den Standardwert hinzuzufügen, können wir ihn als zweiten Parameter an die Methode `optionalFieldOf` übergeben.

::: code-group

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#default_field[Codec]

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#default_field_data[Input]

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/codec_examples/default_field.json[Output]

:::

Beachte, dass das Feld, wenn es vorhanden ist, aber der Wert ungültig ist, überhaupt nicht deserialisiert werden kann, wenn der Feldwert ungültig ist.

### Konstanten, Beschränkungen und Komposition {#constants-constraints-composition}

#### Einheit {#unit}

`MapCodec.MapCodec` kann verwendet werden, um einen Codec zu erstellen, der immer zu einem konstanten Wert deserialisiert, unabhängig von der Eingabe. Bei der Serialisierung wird nichts getan.

::: code-group

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#unit_codec[Codec]

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/codec_examples/unit.json[Output]

:::

#### Zahlenbereiche {#numeric-ranges}

`Codec.intRange` und seine Freunde `Codec.floatRange` und `Codec.doubleRange` können verwendet werden, um einen Codec zu erstellen, der nur Zahlenwerte innerhalb eines bestimmten **inklusiven** Bereichs akzeptiert. Dies gilt sowohl für die Serialisierung als auch für die Deserialisierung.

::: code-group

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#numeric_range[Codec]

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#numeric_range_data[Input]

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/codec_examples/numeric_range.json[Output]

:::

#### Paar {#pair}

`Codec.pair` fasst zwei Codecs, `Codec<A>` und `Codec<B>`, zu einem `Codec<Pair<A, B>` zusammen. Beachte, dass dies nur mit Codecs funktioniert, die zu einem bestimmten Feld serialisieren, wie z. B. [konvertierte `MapCodec`s](#mapcodec) oder
[Record-Codecs](#merging-codecs-for-record-like-classes).
Der resultierende Codec wird zu einer Map serialisiert, die die Attribute der beiden verwendeten Codecs kombiniert.

::: code-group

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#pair_codec[Codec]

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#pair_codec_data[Input]

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/codec_examples/pair.json[Output]

:::

#### Either {#either}

`Codec.either` kombiniert zwei Codecs, `Codec<A>` und `Codec<B>`, zu einem `Codec<Either<A, B>>`. Der resultierende Codec wird bei der Deserialisierung versuchen, den ersten Codec zu verwenden, und _nur wenn das fehlschlägt_, versuchen, den zweiten Codec zu verwenden.
Wenn der zweite Codec ebenfalls fehlschlägt, wird der Fehler des _zweiten_ Codecs zurückgegeben.

#### Maps {#maps}

Für die Verarbeitung von Maps mit beliebigen Schlüsseln, wie zum Beispiel `HashMap`s, kann `Codec.unboundedMap` verwendet werden. Dies gibt einen `Codec<Map<K, V>>` für einen gegebenen `Codec<K>` und `Codec<V>` zurück. Der resultierende Codec wird zu einem JSON oder gleichwertigem Objekt serialisiert, das für die aktuellen dynamische Ops verfügbar ist.

Aufgrund der Einschränkungen von JSON und NBT _muss_ der verwendete Schlüsselcodec zu einer Zeichenkette serialisiert werden. Dazu gehören auch Codecs für Typen, die selbst keine Strings sind, aber zu ihnen serialisiert werden, wie zum Beispiel `Identifier.CODEC`. Siehe folgendes Beispiel:

::: code-group

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#map_codec[Codec]

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#map_codec_data[Input]

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/codec_examples/map.json[Output]

:::

Wie du sehen kannst, funktioniert dies, weil `Identifier.CODEC` direkt zu einem String-Wert serialisiert wird. Einen ähnlichen Effekt kann man für einfache Objekte, die nicht in Strings serialisiert werden, erreichen, indem [Wechselseitig konvertierbare Typen](#wechselseitig-konvertierbare-typen) verwendet werden, um um sie zu konvertieren.

### Wechselseitig konvertierbare Typen {#mutually-convertible-types}

#### `xmap` {#xmap}

Angenommen, wir haben zwei Klassen, die ineinander umgewandelt werden können, aber keine Eltern-Kind-Beziehung haben. Zum Beispiel, eine einfache `BlockPos` und `Vec3d`. Wenn wir einen Codec für eine Richtung haben, können wir mit `Codec#xmap` einen Codec für die andere Richtung erstellen, indem wir eine Konvertierungsfunktion für jede Richtung angeben.

B`BlockPos` hat bereits einen Codec, aber tun wir mal so, als ob er keinen hätte. Wir können einen solchen Codec erstellen, indem wir ihn auf den Codec für `Vec3d` stützen, etwa so:

::: code-group

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#convert_xmap[Codec]

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#convert_xmap_data[Input]

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/codec_examples/xmap.json[Output]

:::

#### flatComapMap, comapFlatMap und flatXMap {#flatcomapmap-comapflatmap-flatxmap}

`Codec#flatComapMap`, `Codec#comapFlatMap` und `flatXMap` sind ähnlich wie xmap, erlauben aber, dass eine oder beide der Konvertierungsfunktionen ein DataResult zurückgeben. Dies ist in der Praxis nützlich, da eine bestimmte Objektinstanz möglicherweise nicht nicht immer für die Konvertierung gültig ist.

Nimm zum Beispiel die `Identifier` von Vanilla her. Während alle Identifier in Zeichenketten umgewandelt werden können, sind nicht alle Zeichenketten gültige Identifier. Daher würde die Verwendung von xmap hässliche Exceptions werfen, wenn die Umwandlung fehlschlägt.
Aus diesem Grund ist der eingebaute Codec eigentlich eine `comapFlatMap` auf `Codec.STRING`, was sehr schön veranschaulicht, wie man ihn verwendet:

<<< @/reference/26.1.2/src/main/java/com/example/docs/codec/Identifier.java#identifier_flatmap

Diese Methoden sind zwar sehr hilfreich, ihre Namen sind jedoch etwas verwirrend. Daher findest du hier eine Tabelle, die dir dabei hilft, dich daran zu erinnern, welche Methode du verwenden solltest:

| Methode        | Decodierung immer gültig? | En­co­die­rung immer gültig? |
| -------------- | ------------------------- | ---------------------------- |
| `xmap`         | Ja                        | Ja                           |
| `comapFlatMap` | Nein                      | Ja                           |
| `flatComapMap` | Ja                        | Nein                         |
| `flatXMap`     | Nein                      | Nein                         |

### Registry Dispatch {#registry-dispatch}

`Codec#dispatch` ermöglicht die Definition eines Registry von Codecs und die Abfertigung eines bestimmten Codecs auf der Grundlage des Wertes eines
Attributs in den serialisierten Daten. Dies ist sehr nützlich, wenn Objekte deserialisiert werden, die je nach Typ unterschiedliche Felder haben, aber dennoch dasselbe darstellen.

Nehmen wir an, wir haben ein abstraktes `Bean`-Interface mit zwei implementierenden Klassen: `StringyBean` und `CountingBean`. Um diese mit einem Registry Dispatch zu serialisieren, benötigen wir einige Dinge:

- Separate Codecs für jede Art von Bohnen.
- Eine `BeanType<T extends Bean>`-Klasse oder ein Datensatz, der den Typ der Bohne repräsentiert und den Codec für sie zurückgeben kann.
- Eine Funktion für `Bean` zum Abrufen ihres `BeanType<?>`.
- Eine Map oder Registry, um `Identifier` auf `BeanType<?>` abzubilden.
- Ein `Codec<BeanType<?>>`, der auf dieser Registry basiert. Wenn du eine `net.minecraft.core.Registry` verwendest, kann eine solche einfach mit `Registry#byNameCodec` erstellt werden.

Mit all dem können wir einen Registry Dispatch Codec für Bohnen erstellen:

::: code-group

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#registry_dispatch[Codec]

<<< @/reference/26.1.2/src/main/java/com/example/docs/codec/Bean.java#bean_interface[Bean]

<<< @/reference/26.1.2/src/main/java/com/example/docs/codec/BeanType.java#bean_type_record[BeanType]

<<< @/reference/26.1.2/src/main/java/com/example/docs/codec/StringyBean.java#stringy_bean_class[StringyBean]

<<< @/reference/26.1.2/src/main/java/com/example/docs/codec/CountingBean.java#counting_bean_class[CountingBean]

<<< @/reference/26.1.2/src/main/java/com/example/docs/codec/BeanTypes.java#bean_types_class[BeanTypes]

:::

Unser neuer Codec serialisiert Bohnen zu JSON und erfasst dabei nur die Felder, die für ihren spezifischen Typ relevant sind:

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/codec_examples/stringy_bean.json

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/codec_examples/counting_bean.json

### Rekursive Codecs {#recursive-codecs}

Manchmal ist es nützlich, einen Codec zu haben, der _sich selbst_ verwendet, um bestimmte Felder zu dekodieren, zum Beispiel wenn es um bestimmte rekursive Datenstrukturen geht. Im Vanilla-Code wird dies für `ResourceLocation`-Objekte verwendet, die andere `Component`s als Kinder speichern können. Ein solcher Codec kann mit `Codec#recursive` konstruiert werden.

Versuchen wir zum Beispiel, eine einfach verknüpfte Liste zu serialisieren. Diese Art der Darstellung von Listen besteht aus einem Bündel von Knoten, die sowohl einen Wert als auch einen Verweis auf den nächsten Knoten in der Liste enthalten. Die Liste wird dann durch ihren ersten Knoten repräsentiert, und das Durchlaufen der Liste erfolgt durch Verfolgen des nächsten Knotens, bis keiner mehr übrig ist. Hier ist eine einfache Implementierung von Knoten, die ganze Zahlen speichern.

<<< @/reference/26.1.2/src/main/java/com/example/docs/codec/ListNode.java#node_record

Wir können dafür keinen Codec mit normalen Mitteln konstruieren, denn welchen Codec würden wir für das Attribut `next` verwenden? Wir bräuchten einen `Codec<ListNode>`, und den sind wir gerade dabei zu konstruieren! Mit `Codec#recursive` können wir das mit einem magisch aussehendem Lambda erreichen:

::: code-group

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#recursive_codec[Codec]

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModCodecExampleProvider.java#recursive_codec_data[Input]

<<< @/reference/26.1.2/src/main/generated/reports/example-mod/codec_examples/recursive.json[Output]

:::

<!---->
