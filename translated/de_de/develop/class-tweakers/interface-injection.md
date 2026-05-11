---
title: Interface-Injektion
description: Lerne, wie du Interfaces in Minecraft-Klassen im dekompilierten Quellcode implementierst.
authors-nogithub:
  - salvopelux
authors:
  - Daomephsta
  - CelDaemon
  - Earthcomputer
  - its-miroma
  - Juuxel
  - MildestToucan
  - Sapryx
  - SolidBlock-cn
---

Die Interface-Injektion ist eine Art der [Klassenoptimierung](../class-tweakers/), mit der Implementierungen von Interfaces zu Minecraft-Klassen
im dekompilierten Quellcode hinzugefügt werden.

Da die Implementierung im dekompilierten Quellcode der Klasse sichtbar ist, ist es nicht mehr erforderlich, zur Nutzung der Methoden der Schnittstelle eine Casting durchzuführen.

Darüber hinaus können Interface-Injektionen [transitiv](../classtweakers/index#transitive-entries) sein, wodurch Bibliotheken ihre hinzugefügten Methoden leichter
für Mods verfügbar machen können, die von ihnen abhängen.

Um die Interface-Injektion zu veranschaulichen, verwenden die Codeausschnitte auf dieser Seite ein Beispiel, in dem wir eine neue Hilfsmethode zu `FlowingFluid` hinzufügen.

## Das Interface erstellen {#creating-the-interface}

Erstelle in einem Paket, das nicht dein Mixin-Paket ist, das Interface, das du injizieren möchtest:

<<< @/reference/latest/src/main/java/com/example/docs/interface_injection/BucketEmptySoundGetter.java#interface-injection-example-interface

In unserem Fall wird standardmäßig ein Ausnahmetyp ausgelöst, da wir die Methode über ein Mixin implementieren wollen.

::: warning

Die Methoden des injizierten Interface müssen alle auf `default` gesetzt sein, damit sie mit einem Klassenoptimierer injiziert werden können, selbst wenn du vorhast, die Methoden in der Zielklasse mithilfe eines Mixin zu implementieren.

Methoden sollten zusätzlich mit deiner Mod-ID und einem Trennzeichen wie `$` oder `_` beginnen, damit es nicht zu Konflikten mit Methoden anderer Mods kommt.

:::

## Das Interface implementieren {#implementing-the-interface}

::: tip

Wenn die Methoden des Interface vollständig mit den `default`-Werten des Interface implementiert sind, musst du kein Mixin verwenden, um das Interface einzubinden; der [Klassenoptimierer-Eintrag](#making-the-class-tweaker-entry) reicht aus.

:::

Um in der Zielklasse Überschreibungen der Methoden des Interface zu erstellen, solltest du ein Mixin verwenden, das das Interface implementiert und auf die Klasse abzielt, in die du das Interface einbinden möchtest.

<<< @/reference/latest/src/main/java/com/example/docs/mixin/class_tweakers/FlowingFluidMixin.java#interface-injection-example-mixin

Die Überschreibungen werden der Zielklasse zur Laufzeit hinzugefügt, erscheinen jedoch nicht im dekompilierten Quellcode, selbst wenn du den Klassenoptimierer verwendest, um die Implementierung des Interface sichtbar zu machen.

## Den Eintrag für den Klassenoptimierer erstellen {#making-the-class-tweaker-entry}

Die Interface-Injektion verwendet den folgenden Syntax:

```:no-line-numbers
inject-interface    <targetClassName>    <injectedInterfaceName>
```

Bei der Optimierung von Klassen verwenden Klassen und Interfaces ihre [internen Namen](../mixins/bytecode#class-names).

Für unser Beispielinterface würde der Eintrag wie folgt lauten:

<<< @/reference/latest/src/main/resources/example-mod.classtweaker#interface-injection-example-entry{classtweaker:no-line-numbers}

### Generische Interfaces {#generic-interfaces}

Wenn dein Interface generische Typen enthält, kannst du diese im Eintrag angeben. Füge dazu am Ende des Interfacename spitze Klammern `<>` ein und setze die generischen Typen im Java-Bytecode-Signaturformat zwischen die Klammern.

Das Signaturformat ist:

| Beschreibung                      | Java Beispiel            | Syntax                                                                             | Beispiel für das Signaturformat |
| --------------------------------- | ------------------------ | ---------------------------------------------------------------------------------- | ------------------------------- |
| Klassentyp                        | `java.lang.String`       | [Bezeichnerformat](../mixins/bytecode#type-descriptors)                            | `Ljava/lang/String;`            |
| Arraytyp                          | `java.lang.String[]`     | [Bezeichnerformat](../mixins/bytecode#type-descriptors)                            | `[Ljava/lang/String;`           |
| Primitiver Typ                    | `boolean`                | [Bezeichnerzeichen](../mixins/bytecode#type-descriptors)                           | `Z`                             |
| Typvariable                       | `T`                      | `T` + Name + `;`                                                                   | `TT;`                           |
| Generischer Klassentyp            | `java.util.List<T>`      | L + [interner Name](../mixins/bytecode#class-names) + `<` + generischer Typ + `>;` | `Ljava/util/List<TT;>;`         |
| Wildcard                          | `?`, `java.util.List<?>` | `*`-Zeichen                                                                        | `*`, `java/util/List<*>;`       |
| Erweitert die Grenze des Wildcard | `? extends String`       | `+` + die Grenze                                                                   | `+Ljava/lang/String;`           |
| Super Grenze des Wildcard         | `? super String`         | `-` + die Grenze                                                                   | `-Ljava/lang/String;`           |

Um ein Interface zu injizieren:

<<< @/reference/latest/src/main/java/com/example/docs/interface_injection/GenericInterface.java#interface-injection-generic-interface

mite den generischen Typen `<? extends String, Boolean[]>`

Der Eintrag des Klassenoptimierer wäre:

<<< @/reference/latest/src/main/resources/example-mod.classtweaker#interface-injection-generic-interface-entry{classtweaker:no-line-numbers}

## Änderungen anwenden {#applying-changes}

Damit deine Implementierung des Interface übernommen wird, musst du dein Gradle-Projekt aktualisieren und die [Quellen neu generieren](../getting-started/generating-sources).
Wenn die Änderungen nicht angezeigt werden, kannst du versuchen, die Datei zu [validieren](../class-tweakers/index#validating-the-file) und zu prüfen, ob Fehler auftreten.

Die hinzugefügten Methoden können nun auf Instanzen der Klasse angewendet werden, in die das Interface injiziert wurde:

<<< @/reference/latest/src/main/java/com/example/docs/interface_injection/ExampleModInterfaceInjection.java#interface-injection-using-added-method

Bei Bedarf kannst du die Methoden in Unterklassen des Interface-Injektionsziels überschreiben.
