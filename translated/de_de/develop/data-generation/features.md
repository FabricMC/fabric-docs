---
title: Generierung von Merkmalen
description: Ein Leitfaden zur Generierung von Merkmalen in der Welt mit dem Datengenerator.
authors:
  - cassiancc
  - its-miroma
  - Wind292
---

<!---->

:::info VORAUSSETZUNGEN

Stelle sicher, dass du den Prozess der [Einrichtung des Datengenerators](./setup) zuerst abgeschlossen hast.

:::

Die Generierung für Merkmale in Minecraft-Welten gliedert sich in drei Teile:

- **Konfigurierte Merkmale**: Diese definieren was ein Merkmal ist; zum Beispiel ein einzelner Baum
- **Platzierte Merkmale**: Diese definieren, wie die Merkmale ausgelegt werden sollen, in welche Richtung, an welcher relativen Position usw.; zum Beispiel die Platzierung von Bäumen in einem Wald
- **Biom-Anpassungen**: Diese defineiren, wo die Merkmale in der Welt platziert werden; zum Beispiel die Koordinaten des gesamten Waldes

::: info

Merkmale in Minecraft sind natürliche oder generierte Muster in der Welt, wie Bäume, Blumen, Erze oder Seen. Merkmale unterscheiden sich von Strukturen (z. B. Dörfern, Tempeln…), die mit dem Befehl `/locate` gefunden werden können.

:::

## Einrichtung {#setup}

Zuerst müssen wir unseren Provider erstellen. Erstelle eine Klasse, die von `FabricDynamicRegistryProvider` erbt und fülle die Basismethoden aus:

@[code lang=java transcludeWith=:::datagen-world:provider](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldgenProvider.java)

Füge dann den Provider zu deinem `DataGeneratorEntrypoint` in der `onInitializeDataGenerator` Methode hinzu:

@[code lang=java transclude={67-67}](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

Erstelle als Nächstes eine Klasse für deine konfigurierten Merkmale und eine Klasse für deine platzierten Merkmale. Diese müssen nichts erweitern.

Sowohl die Klasse für die konfigurierten Merkmale als auch die platzierten Merkmale sollten über eine öffentliche Methode verfügen, mit der du deine Merkmale registrieren und definieren kannst. Sein Argument, das wir `context` genannt haben, sollte für das konfigurierte Merkmal ein `BootstrapContext<ConfiguredFeature<?, ?>>` sein oder für das platzierte Merkmal ein `BootstrapContext<PlacedFeature>`.

Füge in deiner Klasse `DataGeneratorEntrypoint` die folgenden Zeilen in deine Methode `buildRegistry` ein und ersetze dabei den Methodennamen durch den von dir gewählten:

@[code lang=java transcludeWith=:::datagen-world:registries](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

Wenn du die Methode `buildRegistry` noch nicht hast, erstelle sie und versehe sie mit der Annotation `@Override`.

## Konfigurierte Merkmale {#configured-features}

Damit ein Merkmal in unserer Welt auf natürliche Weise erscheint, sollten wir zunächst ein konfiguriertes Merkmal in unserer Klasse für konfigurierte Merkmale definieren. Lasst uns ein benutzerdefiniertes konfiguriertes Merkmal für eine Diamanterz-Ader hinzufügen.

Registriere zunächst den Schlüssel für das `ConfiguredFeature` in deiner konfigurierten Merkmal-Klasse:

@[code lang=java transcludeWith=:::datagen-world:configured-key](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java)

::: tip

Das zweite Argument für den `Identifier` (in diesem Beispiel `diamond_block_vein`) ist das, was du verwenden würdest, um die Struktur mit dem Befehl `/place` zu platzieren, was beim Debuggen hilfreich ist.

:::

### Erze {#ores}

Als Nächstes erstellen wir einen `RuleTest`, der festlegt, welche Blöcke deine Funktion ersetzen kann. Dieser `RuleTest` ermöglicht beispielsweise das Ersetzen jedes Blocks mit dem Tag `DEEPSLATE_ORE_REPLACEABLES`:

@[code lang=java transcludeWith=:::datagen-world:ruletest](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java)

Als Nächstes müssen wir eine `OreConfiguration` erstellen, die dem Spiel sagt, mit was Blöcke ersetzt werden sollen.

@[code lang=java transcludeWith=:::datagen-world:ore-feature-config](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java)

In der Liste können mehrere Einträge für verschiedene Varianten enthalten sein. Lasst und zum Beispiel für Stein und Tiefenschiefer jeweils eine andere Variante setzen:

@[code lang=java transcludeWith=:::datagen-world:multi-ore-feature-config](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java)

Zuletzt müssen wir unser konfiguriertes Merkmal in unserem Spiel registrieren!

@[code lang=java transcludeWith=:::datagen-world:conf-feature-register](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java)

### Bäume {#trees}

Um einen benutzerdefinierten Baum zu erstellen, musst du zunächst eine `TreeConfiguration` anlegen:

@[code lang=java transcludeWith=:::datagen-world:tree-feature-config](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java)

Dies ist, was jedes Argument macht:

1. Gibt den Blocktyp für den Baumstamm an; zum Beispiel Diamantblock
2. Konfiguriert die Form und das Höhenverhalten des Stammes mithilfe eines Stammplatzierers
3. Gibt den Blocktyp für die Blätter des Baums an; zum Beispiel Goldblöcke
4. Definiert die Form und Größe des Laubs mithilfe eines Laubplatzierers
5. Steuert, wie sich der Baumstamm in verschiedenen Höhen verjüngt, vor allem bei größeren Stämmen

::: tip

Wir empfehlen dir _dringend_, mit diesen Werten zu experimentieren, um einen individuellen Baum zu erstellen, mit dem **du** zufrieden bist!

Du kannst die integrierten Platzierer für den Stamm und das Laub der Standardbäume als Orientierung verwenden.

:::

Als Nächstes müssen wir unseren Baum registrieren, indem wir die folgende Zeile zur Methode `configure` von `ExampleModWorldConfiguredFeatures` hinzufügen.

@[code lang=java transcludeWith=:::datagen-world:tree-register](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java)

## Platzierte Merkmale {#placement-features}

Der nächste Schritt beim Hinzufügen eines Merkmal zum Spiel ist die Erstellung der entsprechenden platzierten Merkmal.

Erstelle in der Methode `configure` deiner Klasse des platzierten Merkmal eine Variable wie die folgende:

@[code lang=java transcludeWith=:::datagen-world:conf-feature-register](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java)

Definiere in deiner Klasse für platzierte Merkmale den Schlüssel für dein platziertes Merkmal.

@[code lang=java transcludeWith=:::datagen-world:placed-key](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java)

### Platzierungsmodifikatoren {#placement-modifiers}

Als Nächstes müssen wir unsere Platzierungsmodifikatoren definieren. Dabei handelt es sich um Attribute, die beim Erzeugen des Merkmal festgelegt werden. Das kann alles Mögliche sein: Von der Häufigkeit der Erzeugung bis hin zur Startposition auf der `y`-Achse. Du kannst so wenige oder so viele Modifikatoren verwenden, wie du möchtest.

@[code lang=java transcludeWith=:::datagen-world:placement-modifier](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java)

Die einzelnen Modifikatoren haben folgende Funktionen:

- **CountPlacement**: Ungefähr die Anzahl der Vorkommen dieses Merkmals (in diesem Fall Adern) pro Chunk
- **BiomeFilter**: Erlaubt und die Kontrolle, in welchen Biomen/Dimensionnen es erschaffen wird (darauf gehen wir später noch näher ein)
- **InSquarePlacement**: Verteilt die Merkmale pseudozufälliger
- **HeightRangePlacement**: Gibt den Bereich der `y`-Koordinaten an, in dem ein Merkmal erzeugt werden kann; es unterstützt drei Hauptverteilungsarten:
  1. **Uniform**:
     Alle `y`-Werte innerhalb dieses Bereichs weisen mit gleicher Wahrscheinlichkeit das Merkmal auf. Wenn du dir unsicher bist, nimm einfach diese hier.

  2. **Trapezoid**:
     `y`-Werte, die näher am Medianwert von `y` liegen, weisen eine höhere Wahrscheinlichkeit auf, das Merkmal zu enthalten.

  3. **Biased-Bottom**:
     Verwendet eine logarithmische Skala, bei der niedrigere `y`-Werte mit höherer Wahrscheinlichkeit das Merkmal erhalten. Es erhält eine Startkoordinate `y`, unterhalb derer das Markmal niemals erzeugt wird. Das zweite Argument ist die maximale Höhe, in der das Merkmal erscheinen kann. Das dritte Argument definiert einen Bereich in Blöcken, über den sich die maximale Wahrscheinlichkeit erstreckt.

::: tip

Bäume und andere Oberflächenstrukturen sollten den Modifikator `PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP` anstelle von `HeightRangePlacement` enthalten, um sicherzustellen, dass der Baum auf der Oberfläche erscheint.

:::

Da wir nun die Modifikatoren haben, können wir unser platziertes Merkmal registrieren:

@[code lang=java transcludeWith=:::datagen-world:register-placed-feature](@/reference/latest/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java)

## Biom Modifikationen {#biome-modifications}

Zuletzt müssen wir unser platziertes Merkmal während der Mod-Initialisierung zu `BiomeModifications` hinzufügen. Das können wir tun, indem wir Folgendes in unseren Mod-Initialisierer einfügen:

@[code lang=java transcludeWith=:::datagen-world:biome-modifications](@/reference/latest/src/main/java/com/example/docs/ExampleMod.java)

::: tip

Bei Bäumen sollte der zweite Parameter auf `GenerationStep.Decoration.VEGETAL_DECORATION` gesetzt werden.

:::

### Biomspeifische Generierung {#biome-specific-generation}

Durch Ändern des Arguments `BiomeSelectors` können wir erreichen, dass unser Merkmal nur in einem bestimmten Biomtyp erscheint:

@[code lang=java transcludeWith=:::datagen-world:selective-biome-modifications](@/reference/latest/src/main/java/com/example/docs/ExampleMod.java)

Dieses würde nur in Biomen erscheinen, die mit dem Biom-Tag `minecraft:is_forest` versehen sind.

## Den Datengenerator ausführen {#running-datagen}

Wenn du jetzt den Datengenerator ausführst, solltest du für jedes konfigurierte Merkmal, das du hinzugefügt hast, eine `.json`-Datei unter `src/main/generated/data/example-mod/worldgen/configured_feature` finden, und für jedes platzierte Merkmal eine Datei unter `src/main/generated/data/example-mod/worldgen/placed_feature`!

:::details Generierte Datei für das konfigurierte Merkmal

@[code lang=json](@/reference/latest/src/main/generated/data/example-mod/worldgen/configured_feature/diamond_block_vein.json)

:::

:::details Generierte Datei für das platzierte Merkmal

@[code lang=json](@/reference/latest/src/main/generated/data/example-mod/worldgen/placed_feature/diamond_block_ore_placed.json)

:::

<!---->
