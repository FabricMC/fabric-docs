---
title: Java Bytecode
description: Lerne den Java-Bytecode kennen, damit du Mixins effektiv schreiben kannst.
authors:
  - Earthcomputer
  - its-miroma
---

Mixins arbeiten mit Java-Bytecode, daher muss man deren Grundlagen verstehen, um sie zu begreifen.

Um herauszufinden, wie du den Bytecode einer Klasse in deiner IDE anzeigen kannst, lies bitte den Abschnitt Anzeigen von Bytecode auf der Seite [Tipps und Tricks](../getting-started/tips-and-tricks).

## Namen und Symbole {#names-and-symbols}

Viele Elemente im Bytecode, wie Klassen, Felder und Methoden, werden nach wie vor anhand ihres Namens identifiziert (und anhand des Deskriptors für Felder und Methoden, darauf kommen wir noch zu sprechen), genau wie im Quellcode. Das genaue Format dieser Namen unterscheidet sich jedoch ein wenig.

### Klassennamen {#class-names}

Klassen werden im Allgemeinen mit ihrem _internen Namen_ bezeichnet, der in etwa dem vollqualifizierten Klassennamen (vollständiger Name einschließlich des Pakets) entspricht, wobei alle Punkte `.` durch Schrägstriche `/` ersetzt werden. Zum Beispiel, der interne Name von der Klasse `java.lang.Object` ist `java/lang/Object`.

Verschachtelte Klassen verwenden das Symbol `$`, um ihren Namen von den umgebenden Klassen zu trennen. Beispiel:

```java
package pkg;
class Foo {
    class Bar {
    }
}
```

... der interne Name von `Bar` wäre `pkg/Foo$Bar`.

Anonyme Klassen verwenden Zahlen anstelle von Namen. Wenn es beispielsweise zwei anonyme Klassen in der Klasse `Foo` aus dem obigen Codeblock gäbe, würden ihre internen Namen `pkg/Foo$1` und `pkg/Foo$2` lauten.

Lokale Klassen (Klassen, die innerhalb einer Methode definiert sind) haben eine Nummer, gefolgt von ihrem Namen. Ein lokaler Klassenname könnte beispielsweise wie folgt aussehen: `pkg/Foo$1Local`.

### Typ-Deskriptoren {#type-descriptors}

Wenn Bytecode auf primitive Typen oder Arrays verweisen muss, werden _Typ-Deskriptoren_ verwendet. Hier ist eine Tabelle mit Datentypen und ihren jeweiligen Typ-Deskriptoren:

| Typ       | Deskriptor                                                                     |
| --------- | ------------------------------------------------------------------------------ |
| `boolean` | `Z`                                                                            |
| `byte`    | `B`                                                                            |
| `char`    | `C`                                                                            |
| `double`  | `D`                                                                            |
| `float`   | `F`                                                                            |
| `int`     | `I`                                                                            |
| `long`    | `J`                                                                            |
| `short`   | `S`                                                                            |
| `void`    | `V`                                                                            |
| Arrays    | `[` + der Elementtyp: `int[]` -> `[I`                          |
| Objekte   | `L` + der interne Name + `;`: `String` -> `Ljava/lang/String;` |

### Feld- und Methoden-Deskriptoren {#field-and-method-descriptors}

In Bytecode werden Felder und Methoden durch die Kombination ihres Namens und des Deskriptors identifiziert. Bei Feldern ist dies die Beschreibung deren Datentyps.

Methoden hingegen erhalten ihren Namen durch die Kombination der Parametertypen und des Rückgabetyps. Beispielsweise die folgende Methode:

```java
void drawText(int x, int y, String text, int color) {
    // ...
}
```

... hat den Deskriptor `(IILjava/lang/String;I)V`.

Die Deskriptoren für die Parametertypen werden ohne Trennzeichen direkt miteinander verkettet. In diesem Fall gibt es `I` für `int` zweimal (für sowohl `x` als auch `y`), dann `Ljava/lang/String;` für `String` (`text`) und ein weiteres `I` für das letzte `int` (`color`).

### Konstruktoren und statische Initialisierer {#constructors-and-static-initializers}

Auf Bytecode-Ebene sind Konstruktoren nur eine weitere Methode: Die genauen Unterschiede zwischen den beiden würden den Rahmen dieser Übersicht sprengen.

Der Name einer Konstruktormethode lautet `<init>` (mit den spitzen Klammern `<>`) und der Rückgabetyp in deren Deskriptor ist `V` (`void`). Alle nicht statischen initialisierungen von Feldern befinden sich nach der Kompilierung innerhalb der `<init>`-Methoden.

Andererseits sind statische Initialisierer (der Block `static {}` im Quellcode sowie statische Initialisierer von Feldern mit einigen Ausnahmen) ebenfalls nur eine weitere Methode, die beim Laden einer Klasse ausgeführt wird: Deren Name lautet `<clinit>` und deren Deskriptor ist `()V`.

## Lokale Variablen {#local-variables}

Im Quellcode werden lokale Variablen anhand ihres Namens identifiziert. Im Bytecode werden sie stattdessen durch eine Nummer oder einen Index in der lokalen Variablentabelle (LVT) identifiziert. Methodenparameter sind im LVT enthalten, ebenso wie das Objekt `this` in nicht-statischen Methoden.

Betrachte die folgende Methode als Beispiel:

::: code-group

```java [Source Code]
public int getX(int offset) {
    int result = this.x + offset;
    return result;
}
```

```bytecode [Bytecode]
public getX (I)I
  aload 0  // this
  getfield x
  iload 1  // offset
  iadd
  istore 2  // result

  iload 2  // result
  ireturn
```

:::

Im Bytecode erhält `this` den Index 0, `offset` den Index 1 und `result` den Index 2.

Statische Methoden haben kein `this` im LVT, daher erhält der erste Parameter statischer Methoden direkt den Index 0.

Longs und Doubles belegen 2 Indizes im LVT. Beispielsweise in der folgenden statischen Methode:

::: code-group

```java [Source Code]
public static double add(double x, double y, double z) {
    return x + y + z;
}
```

```bytecode [Bytecode]
static add (DDD)D
  dload 0  // x
  dload 2  // y
  dadd
  dload 4  // z
  dadd
  dreturn
```

:::

... der Parameter `x` erhält den Index 0, der Parameter `y` den Index 2 und der Parameter `z` den Index 4.

::: info

Wir haben gesehen, dass Bytecode die Namen lokaler Variablen nicht benötigt, da er sie anhand ihres LVT-Index identifiziert. Trotzdem behalten viele Bibliotheken Debugging-Informationen bei, darunter auch die Namen lokaler Variablen, um das Debugging zu vereinfachen und dir zu ermöglichen, bei der Entwicklung von Mixins lokale Variablen anhand deren Namen anzusprechen.

Minecraft 1.21.11 bietet dies jedoch standardmäßig nicht und gilt daher als verschleiert. Beachte, dass [zukünftige Versionen von Minecraft nicht verschleiert werden](../migrating-mappings/#whats-going-on-with-mappings).

:::

## Der Operandenstapel {#the-operand-stack}

Genau wie native Assembler-Sprachen Prozessorregister verwenden, nutzt Java-Bytecode den Operandenstapel, um temporäre Werte zu speichern.

Wie bei jedem [Stapel](https://en.wikipedia.org/wiki/Stack_\(abstract_data_type\)) werden Werte oben auf den Stapel gelegt ("gepusht") und oben vom Stack entfernt ("gepoppt"). Stellen dir das wie einen Stapel Teller vor: Wenn du einen Teller auf den Stapel legst, legst du ihn oben drauf, und wenn du einen brauchst, nehme den obersten. Eine solche Datenstruktur wird als _Last-In, First-Out_ bezeichnet, da die zuletzt auf den Stapel geschobene "Platte" als erste wieder entfernt wird.

Schauen wir uns noch einmal das vorherige Beispiel `getX` an:

::: code-group

```java [Source Code]
public int getX(int offset) {
    int result = this.x + offset;
    return result;
}
```

```bytecode [Bytecode]
public getX (I)I
  aload 0  // this
  getfield x
  iload 1  // offset
  iadd
  istore 2  // result

  iload 2  // result
  ireturn
```

:::

Stellen wir uns vor, dass `getX(5)` aufgerufen wird, wenn `this.x` den Wert 42 hat, und verfolgen wir Schritt für Schritt, was passiert:

::: tabs

== Start

| Index | Lokale Variablentabelle     | Operandenstapel |
| ----- | --------------------------- | --------------- |
| 2     |                             |                 |
| 1     | `offset`: 5 |                 |
| 0     | `this`                      |                 |

Navigiere durch die Anweisungen, indem du auf die Schaltfläche oben klickst.

Das obige Diagramm zeigt den Zustand der lokalen Variablentabelle und des Operandenstapels nach der Anweisung.

Beachte, dass der LVT-Slot 0 `this` enthält: Das liegt daran, dass `getX` keine statische Methode ist.

== aload 0

| Index | Lokale Variablentabelle     | Operandenstapel |
| ----- | --------------------------- | --------------- |
| 2     |                             |                 |
| 1     | `offset`: 5 |                 |
| 0     | `this`                      | `this`          |

Lädt die Variable aus dem LVT-Slot 0 (`this`) und schiebt ihren Wert auf den Operandenstapel.

== getfield x

| Index | Lokale Variablentabelle     | Operandenstapel |
| ----- | --------------------------- | --------------- |
| 2     |                             |                 |
| 1     | `offset`: 5 |                 |
| 0     | `this`                      | 42              |

Entfernt den obersten Wert aus dem Operandenstapel, ruft den Wert seines Feldes `x` ab (der, wie gesagt, 42 ist) und schiebt ihn auf den Operandenstapel.

== iload 1

| Index | Lokale Variablentabelle     | Operandenstapel |
| ----- | --------------------------- | --------------- |
| 2     |                             |                 |
| 1     | `offset`: 5 | 5               |
| 0     | `this`                      | 42              |

Lädt die Variable aus dem LVT-Slot 1 (`offset`) und schiebt ihren Wert auf den Operandenstapel.

== iadd

| Index | Lokale Variablentabelle     | Operandenstapel |
| ----- | --------------------------- | --------------- |
| 2     |                             |                 |
| 1     | `offset`: 5 |                 |
| 0     | `this`                      | 47              |

Entfernt die beiden obersten Werte vom Operandenstapel, addiert sie und schiebt die Summe auf den Operandenstapel.

== istore 2

| Index | Lokale Variablentabelle      | Operandenstapel |
| ----- | ---------------------------- | --------------- |
| 2     | `result`: 47 |                 |
| 1     | `offset`: 5  |                 |
| 0     | `this`                       |                 |

Entfernt den obersten Wert aus dem Operandenstapel und weist ihn der lokalen Variablen im LVT-Slot 2 zu (`result`, eine [lokale Variable](#variable-instructions)).

== iload 2

| Index | Lokale Variablentabelle      | Operandenstapel |
| ----- | ---------------------------- | --------------- |
| 2     | `result`: 47 |                 |
| 1     | `offset`: 5  |                 |
| 0     | `this`                       | 47              |

Lädt die Variable aus dem LVT-Slot 2 (`result`) und schiebt ihren Wert auf den Operandenstapel.

== ireturn

Holt den obersten Wert des Operandenstapels und gibt ihn zurück.

Die Methode gibt den Wert 47 zurück.

:::

## Bedingte Anweisungen {#conditional-instructions}

Wir haben gesehen, wie die JVM Anweisungen nacheinander sequenziell ausführt. Bestimmte Anweisungen weisen die JVM jedoch an, zu einem anderen Punkt im Bytecode zu springen:

- `goto`: Springt immer zu der referenzierten Anweisung
- `ifeq`: Entfernt den obersten Wert vom Operandenstapel und springt, wenn dieser gleich 0 ist, zur referenzierten Anweisung
- `ifne`: Entfernt den obersten Wert vom Operandenstapel und springt, wenn dieser ungleich 0 ist, zur referenzierten Anweisung
- `if_icmpXX`: Entnimmt die beiden obersten Werte aus dem Operandenstapel und vergleicht sie. Wenn der Vergleich zutrifft, springt die JVM zur referenzierten Anweisung. Zum Beispiel:
  - `if_icmpeq` (`==`): Erfolgreich, wenn die beiden Werte gleich sind
  - `if_icmpgt` (`>`): Erfolgreich, wenn der erste Wert größer als der zweite ist
  - `if_icmple` (`<=`): Erfolgreich, wenn das erste Element kleiner oder gleich dem zweiten Element ist

Betrachte beispielsweise die folgende Methode:

::: code-group

```java [Source Code]
static String makeFoobar(boolean cond) {
    String result;
    if (cond) {
        result = "foo";
    } else {
        result = "bar";
    }
    return result;
}
```

```bytecode [Bytecode]
static makeFoobar (Z)Ljava/lang/String;
  iload 0  // cond
  ifeq L1
  ldc "foo"
  astore 1  // result
  goto L2
L1
  ldc "bar"
  astore 1  // result
L2
  aload 1  // result
  areturn
```

:::

Beachte, dass Sprungziele mit `L*` gekennzeichnet sind.

Die Anweisung `ifeq` vergleicht den Wert oben auf dem Operandenstapel (der aufgrund der vorherigen Anweisung `iload` `cond` ist) mit 0 und springt zu `L1`, wenn er gleich 0 ist (`false`).

Wenn er nicht gleich 0 ist, was bedeutet, dass `cond` `true` ist, wird mit der nächsten Anweisungen fortgefahren, bis man zur Anweisung `goto` gelangt, die dann zu `L2` springt.

Der `if`-Block besteht im Wesentlichen aus den Zeilen von `ifeq L1` bis `L1`, während der `else`-Block aus `L1`-`L2` besteht. Die bedingten "Sprungbefehle", die an die Programmierung der [`goto`-Ära](https://xkcd.com/292/) erinnern, sind die Art und Weise, wie `if`-Anweisungen, Schleifen, Ternäre usw. kompiliert werden.

Die Kompilierung kann zu einer komplexen Logik führen, die nicht nur schwer zu lesen ist, sondern auch schwer mit Mixins zu adressieren ist. Betrachte das folgende klassische Beispiel:

::: code-group

```java [Source Code]
static void doSomething(boolean cond1, boolean cond2) {
    if (cond1) {
        if (cond2) {
            System.out.println("Something is being done");
        }
        // inject here? // [!code highlight]
    }
}
```

```bytecode [Bytecode]
static doSomething (ZZ)V
  iload 0  // cond1
  ifeq L1
  iload 1  // cond2
  ifeq L1
  getstatic System.out
  invokevirtual println
L1
  return
```

:::

Da der Bytecode für beide `if`-Bedingungen genau zum gleichen Label springt, gibt es im Bytecode keine Stelle, die dem Kommentar `// inject here?` entspricht, was bedeutet, dass Workarounds verwendet werden müssen, um ihn mit Mixins anzusprechen.

## Häufige Bytecode-Muster {#common-bytecode-patterns}

Hier findest du eine Übersicht über die gängigsten Bytecode-Anweisungen und -Muster, denen du bei der Entwicklung von Mixins begegnen wirst. Eine vollständige Liste der Anweisungen findest du unter der [Liste der Java-Bytecode-Anweisungen auf Wikipedia](https://en.wikipedia.org/wiki/List_of_Java_bytecode_instructions).

### Konstanten {#constant-instructions}

Konstante Anweisungen schieben einen konstanten Wert auf den Operandenstapel.

- `iconst_m1`, `iconst_0`, `iconst_1`, ..., `iconst_5`: Die literalen Integers `-1` bis `5`
- `lconst_0`, `dconst_1`, `fconst_2` und dergleichen: Die literalen Zahlen, jeweils als `long`, `double` und `float`
- `bipush`, `sipush`: Pusht größere Integer Konstanten
- `ldc`: Kann verschiedene Arten von Konstanten pushen, darunter Strings und sogar größere Integer

### Variablen {#variable-instructions}

Ladeanweisungen lesen einen [Wert aus den LVT](#local-variables) und schieben ihn auf den Operandenstapel.

Speicheranweisungen entfernen den obersten Wert aus dem Operandenstapel und schreiben ihn in eine lokale Variable.

- `iload`, `istore`: Lädt oder speichert Variablen des Typs `int`, `boolean`, `byte`, `char`, and `short`
- `lload`, `lstore`: Lädt oder speichert Variablen des Typs `long`
- `fload`, `fstore`: Lädt oder speichert Variablen des Typs `float`
- `dload`, `dstore`: Lädt oder speichert Variablen des Typs `double`
- `aload`, `astore`: Lädt oder speichert Variablen von nicht primitiven Typen

### Felder {#field-instructions}

- `getfield`: Liest aus einem nicht statischem Feld
- `putfield`: Schreibt in ein nicht statisches Feld
- `getstatic`: Liest aus einem statischem Feld
- `putstatic`: Schreibt in ein statisches Feld

### Methodenaufrufe {#method-instructions}

- `invokestatic`: Ruft eine statische Methode auf
- `invokevirtual`: Ruft eine nicht statische Methode auf. Berücksichtigt Polymorphismus und Vererbung und ruft gegebenenfalls die überschriebene Version auf
- `invokespecial`: Ruft eine nicht statische Methode auf, genau die deklarierte, ohne Polymorphismus/Vererbung zu berücksichtigen. Verwendungszwecke umfassen das Aufrufen von Konstruktoren und Methoden der Oberklasse
- `invokeinterface`: Ruft eine nicht statische Methode eines Interface auf

### Bedingungen {#conditional-instructions-2}

Siehe [bedingte Anweisungen](#conditional-instructions).

### Operatoren {#operator-instructions}

Operatoranweisungen nehmen in der Regel zwei Werte aus dem Operandenstapel, führen eine Operation durch und schieben das Ergebnis zurück. Hier ist eine Liste einiger gängiger Operatoranweisungen:

- `iadd`, `ladd`, `fadd`, `dadd`: Addition
- `isub`, `lsub`, `fsub`, `dsub`: Subtraktion
- `imul`, `lmul`, `fmul`, `dmul`: Multiplikation
- `idiv`, `ldiv`, `fdiv`, `ddiv`: Division
- `irem`, `lrem`, `frem`, `drem`: Modulo
- `ineg`, `lneg`, `fneg`, `dneg`: Negation. Entfernt nur einen Wert vom Stapel

Die Präfixe `i`, `l`, `f`, `d` bestimmen, wie bei den [Variablenanweisungen](#variable-instructions) zu sehen, den Datentyp, auf den der Operator angewendet werden soll.

### Rückgaben {#return-instructions}

Rückgabeanweisungen schließen den Methodenaufruf und geben den Wert oben im Operandenstapel zurück (außer bei `void`-Methoden).

Wenn vorangestellt mit `i`, `l`, `f`, `d` und `a`, genau wie bei [Variablenanweisungen](#variable-instructions), gibt die Methode einen Wert dieses Typs zurück. Die Anweisung für `void`-Methoden lautet einfach `return`.

### Erstellung neuer Objekte {#new-object-creation}

Im Quellcode erstellt die Schreibweise `new MyClass()` eine neue Instanz von `MyClass` und ruft deren Konstruktor auf. Im Bytecode werden diese beiden Schritte zu unterschiedlichen Operationen. Nehmen Sie beispielsweise den folgenden Code:

::: code-group

```java [Source Code]
static Creeper createCreeper(Level level) {
    return new Creeper(level);
}
```

```bytecode [Bytecode]
static createCreeper (Lnet/minecraft/world/level/Level;)Lnet/mineraft/world/entity/monster/Creeper;
  new net/minecraft/world/entity/monster/Creeper
  dup
  aload 0  // level
  invokespecial net/minecraft/world/entity/monster/Creeper.<init> (Lnet/minecraft/world/level/Level;)V
  areturn
```

:::

Schauen wir uns einmal an, was auf dem Operandenstapel passiert.

::: tabs

== Start

| Index | Lokale Variablentabelle | Operandenstapel |
| ----- | ----------------------- | --------------- |
| 2     |                         |                 |
| 1     |                         |                 |
| 0     | `level`                 |                 |

LVT-Slot 0 enthält `level`. Es enthält nicht `this`, da die Methode statisch ist.

== new Creeper

| Index | Lokale Variablentabelle | Operandenstapel  |
| ----- | ----------------------- | ---------------- |
| 2     |                         |                  |
| 1     |                         |                  |
| 0     | `level`                 | uninit `Creeper` |

Weist eine nicht initialisierte Instanz von `Creeper` zu und pusht eine Referenz darauf auf den Operandenstapel.

== dup

| Index | Lokale Variablentabelle | Operandenstapel  |
| ----- | ----------------------- | ---------------- |
| 2     |                         |                  |
| 1     |                         | uninit `Creeper` |
| 0     | `level`                 | uninit `Creeper` |

Dupliziert den obersten Wert des Operandenstapels.

Der Operandenstapel muss zwei Zeiger auf dasselbe Objekt enthalten, da einer von `invokespecial <init>` verbraucht und der andere von `areturn` zurückgegeben wird.

== aload 0

| Index | Lokale Variablentabelle | Operandenstapel  |
| ----- | ----------------------- | ---------------- |
| 2     |                         | `level`          |
| 1     |                         | uninit `Creeper` |
| 0     | `level`                 | uninit `Creeper` |

Lädt die Variable aus dem LVT-Slot 0 (`level`) und pusht ihren Wert auf den Operandenstapel.

<!-- markdownlint-disable no-inline-html -->

== invokespecial <init>

<!-- markdownlint-enable no-inline-html -->

| Index | Lokale Variablentabelle | Operandenstapel |
| ----- | ----------------------- | --------------- |
| 2     |                         |                 |
| 1     |                         |                 |
| 0     | `level`                 | `Creeper`       |

Entfernt die Parameter, die in `Creeper.<init>` (in diesem Fall `level`) eingegeben werden, und den Zeiger auf das Objekt, das initialisiert wird, aus dem Operandenstapel und ruft dann den Konstruktor auf.

Da [Konstruktoren `void` zurückgeben](#constructors-and-static-initializers), verliert der Operandenstapel einen der beiden Zeiger auf `Creeper`.

== areturn

Holt den obersten Wert des Operandenstapels und gibt ihn zurück.
Die Methode gibt den Zeiger auf die Instanz des `Creeper` zurück.

:::

### Lambdas {#lambdas}

Lambda-Ausdrücke werden zu einer separaten Methode kompiliert, die dann von einer Lambda-Instanz aufgerufen wird, die durch eine `invokedynamic`-Anweisung instanziiert wurde.

Die Details der Anweisung `invokedynamic` würden den Rahmen dieser Übersicht sprengen, aber es ist hilfreich zu wissen, welche Art von Code zu erwarten ist. Einige `invokedynamic`-Operanden wurden in diesem Abschnitt der Einfachheit halber weggelassen.

Hier ein Beispiel:

::: code-group

```java [Source Code]
static void hello() {
    Runnable r = () -> System.out.println("Hello, World!");
    r.run();
}
```

```bytecode [Bytecode]
static hello ()V
  invokedynamic run ()Ljava/lang/Runnable; java/lang/invoke/LambdaMetafactory.metafactory ()V lambda$hello$1 ()V
  astore 0  // r

  aload 0  // r
  invokeinterface run

  return

static lambda$hello$1 ()V
  getstatic System.out
  ldc "Hello, World!"
  invokevirtual println

  return
```

:::

Hier siehst du, dass der Inhalt des Lambda in eine separate Methode verschoben wurde, in diesem Fall `lambda$hello$1`.

Wenn du den Inhalt eines Lambda mit Mixins ansprechen möchtest, ist dies die Methode, die du verwenden solltest. Die Lambda-Instanz wird dann mit der Anweisung `invokedynamic` erstellt und anschließend in der Variablen `r` gespeichert.

Wenn das Lambda-Ausdruck Variablen erfasst, werden diese Variablen zu Parametern für die Lambda-Methoden. Zum Beispiel:

::: code-group

```java [Source Code]
static void hello(String name) {
    Runnable r = () -> System.out.println("Hello, " + name + "!");
    r.run();
}
```

```bytecode [Bytecode]
static hello (Ljava/lang/String;)V
  aload 0  // name
  invokedynamic run (Ljava/lang/String;)Ljava/lang/Runnable; java/lang/invoke/LambdaMetafactory.metafactory ()V lambda$hello$1 (Ljava/lang/String;)V ()V
  astore 1  // r

  aload 1  // r
  invokeinterface run

  return

static lambda$hello$1 (Ljava/lang/String;)V
  getstatic System.out
  aload 0  // name
  invokedynamic makeConcatWithConstants (Ljava/lang/String;)Ljava/lang/String; java/lang/invoke/StringConcatFactory.makeConcatWithConstants "Hello, \1!"
  invokevirtual println

  return
```

:::

Hier wird der Parameter `name` als Parameter an die Lambda-Funktion übergeben. Beachte auch, wie die String Verkettung mit `invokedynamic` implementiert wird.
