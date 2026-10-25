---
title: Generierung von Merkmalen
description: Ein Leitfaden zur Generierung von Merkmalen in der Welt mit dem Datengenerator.
authors:
  - cassiancc
  - CelDaemon
  - its-miroma
  - JonyBoy19
  - Wind292
---

<!---->

:::info VORAUSSETZUNGEN

Stelle sicher, dass du den Prozess der [Einrichtung des Datengenerators](./setup) zuerst abgeschlossen hast.

:::

Merkmale in Minecraft sind natürliche oder generierte Muster in der Welt, wie Bäume, Blumen, Erze oder Seen. Merkmale unterscheiden sich von Strukturen (z. B. Dörfern, Tempeln…), die mit dem Befehl `/locate` gefunden werden können.

Die Generierung für Merkmale in Minecraft-Welten gliedert sich in drei Teile:

- **Konfigurierte Merkmale**: Diese definieren was ein Merkmal ist; zum Beispiel ein einzelner Baum
- **Platzierte Merkmale**: Diese definieren, wie die Merkmale ausgelegt werden sollen, in welche Richtung, an welcher relativen Position usw.; zum Beispiel die Platzierung von Bäumen in einem Wald
- **Biom-Anpassungen**: Diese defineiren, wo die Merkmale in der Welt platziert werden; zum Beispiel die Koordinaten des gesamten Waldes

## Einrichtung {#setup}

Zuerst müssen wir unseren Provider erstellen. Erstelle eine Klasse im `main` Paket, die von `FabricDynamicRegistryProvider` erbt und fülle die Basismethoden aus:

<<< @/reference/26.1.2/src/main/java/com/example/docs/worldgen/ExampleModWorldgenProvider.java#datagen_world_provider

In der Methode `configure` werden wir `addAll` aufrufen, um sicherzustellen, dass alle Dateien für unsere Features generiert werden.

<<< @/reference/26.1.2/src/main/java/com/example/docs/worldgen/ExampleModWorldgenProvider.java#worldgen_add_entries

Füge dann den Provider zu deinem `DataGeneratorEntrypoint` in der `onInitializeDataGenerator` Methode hinzu:

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#add_worldgen_provider

Erstelle als Nächstes eine Klasse für deine konfigurierten Merkmale und eine Klasse für deine platzierten Merkmale. Diese müssen nichts erweitern.

Sowohl die Klasse für die konfigurierten Merkmale als auch die platzierten Merkmale sollten über eine öffentliche Methode verfügen, mit der du deine Merkmale registrieren und definieren kannst. Sein Argument, das wir `context` genannt haben, sollte für das konfigurierte Merkmal ein `BootstrapContext<ConfiguredFeature<?, ?>>` sein oder für das platzierte Merkmal ein `BootstrapContext<PlacedFeature>`.

Füge in deiner Klasse `DataGeneratorEntrypoint` die folgenden Zeilen in deine Methode `buildRegistry` ein und ersetze dabei den Methodennamen durch den von dir gewählten:

<<< @/reference/26.1.2/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_world_registries

Wenn du die Methode `buildRegistry` noch nicht hast, erstelle sie und versehe sie mit der Annotation `@Override`.

## Konfigurierte Merkmale {#configured-features}

Damit ein Merkmal in unserer Welt auf natürliche Weise erscheint, sollten wir zunächst ein konfiguriertes Merkmal in unserer Klasse für konfigurierte Merkmale definieren.

Bevor wir etwas machen können, lasst uns die Klasse für das konfigurierte Merkmal in dem Paket `main` erstellen und eine Methode `configure` deklarieren:

<<< @/reference/26.1.2/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java#datagen_world_configure_features_class

Lasst uns ein benutzerdefiniertes konfiguriertes Merkmal für eine Diamanterz-Ader hinzufügen. Registriere zunächst den Schlüssel für das `ConfiguredFeature` in deiner konfigurierten Merkmal-Klasse:

<<< @/reference/26.1.2/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java#datagen_world_configured_key

::: tip

Das zweite Argument für den `Identifier` (in diesem Beispiel `diamond_block_vein`) ist das, was du verwenden würdest, um die Struktur mit dem Befehl `/place` zu platzieren, was beim Debuggen hilfreich ist.

:::

### Erze {#ores}

Als Nächstes erstellen wir einen `RuleTest` in der Methode `configure`, der festlegt, welche Blöcke dein Merkmal ersetzen kann. Dieser `RuleTest` ermöglicht beispielsweise das Ersetzen jedes Blocks mit dem Tag `DEEPSLATE_ORE_REPLACEABLES`:

<<< @/reference/26.1.2/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java#datagen_world_ruletest

Als Nächstes müssen wir, auch in der Methode `configure`, eine `OreConfiguration` erstellen, die dem Spiel sagt, mit was Blöcke ersetzt werden sollen.

<<< @/reference/26.1.2/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java#datagen_world_ore_feature_config

In der Liste können mehrere Einträge für verschiedene Varianten enthalten sein. Lasst und zum Beispiel für Stein und Tiefenschiefer jeweils eine andere Variante setzen:

<<< @/reference/26.1.2/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java#datagen_world_multi_ore_feature_config

Zuletzt müssen wir unser konfiguriertes Merkmal innerhalb der Methode `configure` in unserem Spiel registrieren!

<<< @/reference/26.1.2/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java#datagen_world_conf_feature_register

### Bäume {#trees}

Um einen benutzerdefinierten Baum zu erstellen, musst du zunächst eine `TreeConfiguration` in der Methode `configure` erstellen:

<<< @/reference/26.1.2/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java#datagen_world_tree_feature_config

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

<<< @/reference/26.1.2/src/main/java/com/example/docs/worldgen/ExampleModWorldConfiguredFeatures.java#datagen_world_tree_register

## Platzierte Merkmale {#placement-features}

Der nächste Schritt beim Hinzufügen eines Merkmal zum Spiel ist die Erstellung der entsprechenden platzierten Merkmal.

Lasst uns die Klasse für das platzierte Merkmal in dem Paket `main` erstellen und ihm eine Methode `configure` wie zuvor geben:

<<< @/reference/26.1.2/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java#datagen_world_placed_features_class

Erstelle in der Methode `configure` deiner Klasse des platzierten Merkmal eine Variable wie die folgende:

<<< @/reference/26.1.2/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java#datagen_world_conf_feature_register

Definiere in deiner Klasse für platzierte Merkmale den Schlüssel für dein platziertes Merkmal:

<<< @/reference/26.1.2/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java#datagen_world_placed_key

### Platzierungsmodifikatoren {#placement-modifiers}

Als Nächstes müssen wir unsere Platzierungsmodifikatoren innerhalb der Methode `configure` definieren. Dabei handelt es sich um Attribute, die beim Erzeugen des Merkmal festgelegt werden. Das kann alles Mögliche sein: Von der Häufigkeit der Erzeugung bis hin zur Startposition auf der `y`-Achse. Du kannst so wenige oder so viele Modifikatoren verwenden, wie du möchtest.

<<< @/reference/26.1.2/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java#datagen_world_placement_modifiers

Die einzelnen Modifikatoren haben folgende Funktionen:

- **`CountPlacement`**: Ungefähr die Anzahl der Vorkommen dieses Merkmals (in diesem Fall Adern) pro Chunk
- **`BiomeFilter`**: Erlaubt und die Kontrolle, in welchen Biomen/Dimensionnen es erschaffen wird (darauf gehen wir später noch näher ein)
- **`InSquarePlacement`**: Verteilt die Merkmale pseudozufälliger
- **`HeightRangePlacement`**: Gibt den Bereich der `y`-Koordinaten an, in dem ein Merkmal erzeugt werden kann; es unterstützt drei Hauptverteilungsarten:
  1. **Uniform**:
     Alle `y`-Werte innerhalb dieses Bereichs weisen mit gleicher Wahrscheinlichkeit das Merkmal auf. Wenn du dir unsicher bist, nimm einfach diese hier.

  2. **Trapezoid**:
     `y`-Werte, die näher am Medianwert von `y` liegen, weisen eine höhere Wahrscheinlichkeit auf, das Merkmal zu enthalten.

  3. **Biased-Bottom**:

     Verwendet eine logarithmische Skala, bei der niedrigere `y`-Werte mit höherer Wahrscheinlichkeit das Merkmal erhalten. Es erhält eine Startkoordinate `y`, unterhalb derer das Markmal niemals erzeugt wird. Das zweite Argument ist die maximale Höhe, in der das Merkmal erscheinen kann. Das dritte Argument definiert einen Bereich in Blöcken, über den sich die maximale Wahrscheinlichkeit erstreckt.

::: tip

Bäume und andere Oberflächenstrukturen sollten den Modifikator `PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP` anstelle von `HeightRangePlacement` enthalten, um sicherzustellen, dass der Baum auf der Oberfläche erscheint.

:::

Da wir nun die Modifikatoren haben, können wir unser platziertes Merkmal in der Methode `configure` registrieren:

<<< @/reference/26.1.2/src/main/java/com/example/docs/worldgen/ExampleModWorldPlacedFeatures.java#datagen_world_register_placed_feature

## Biom Modifikationen {#biome-modifications}

Zuletzt müssen wir unser platziertes Merkmal während der Mod-Initialisierung zu `BiomeModifications` hinzufügen. Wir können dies machen, indem wir folgendes zu unserem [Mod Initialisierer](../getting-started/project-structure#entrypoints) hinzufügen:

<<< @/reference/26.1.2/src/main/java/com/example/docs/ExampleMod.java#datagen_world_biome_modifications

::: tip

Bei Bäumen sollte der zweite Parameter auf `GenerationStep.Decoration.VEGETAL_DECORATION` gesetzt werden.

:::

### Biomspeifische Generierung {#biome-specific-generation}

Durch Ändern des Arguments `BiomeSelectors` können wir erreichen, dass unser Merkmal nur in einem bestimmten Biomtyp erscheint:

<<< @/reference/26.1.2/src/main/java/com/example/docs/ExampleMod.java#datagen_world_selective_biome_modifications

Dieses würde nur in Biomen erscheinen, die mit dem Biom-Tag `minecraft:is_forest` versehen sind.

## Den Datengenerator ausführen {#running-datagen}

Wenn du jetzt den Datengenerator ausführst, solltest du für jedes konfigurierte Merkmal, das du hinzugefügt hast, eine `.json`-Datei unter `src/main/generated/data/example-mod/worldgen/configured_feature` finden, und für jedes platzierte Merkmal eine Datei unter `src/main/generated/data/example-mod/worldgen/placed_feature`!

:::details Generierte Datei für das konfigurierte Merkmal

<<< @/reference/26.1.2/src/main/generated/data/example-mod/worldgen/configured_feature/diamond_block_vein.json

:::

:::details Generierte Datei für das platzierte Merkmal

<<< @/reference/26.1.2/src/main/generated/data/example-mod/worldgen/placed_feature/diamond_block_ore_placed.json

:::

<!---->
