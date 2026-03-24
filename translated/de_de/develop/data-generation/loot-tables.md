---
title: Generierung von Beutetabellen
description: Ein Leitfaden zur Einrichtung der Generierung von Beutetabellen mit dem Datengenerator.
authors:
  - Alphagamer47
  - CelDaemon
  - JustinHuPrime
  - matthewperiut
  - skycatminepokie
  - Spinoscythe
authors-nogithub:
  - jmanc3
  - mcrafterzz
---

<!---->

:::info VORAUSSETZUNGEN

Stelle sicher, dass du den Prozess der [Einrichtung des Datengenerators](./setup) zuerst abgeschlossen hast.

:::

Du wirst unterschiedliche Provider (Klassen) für Blöcke, Truhen und Entitäten benötigen. Vergesse nicht, alle diese zu deinem Pack in deinem `DataGeneratorEntrypoint` innerhalb der `onInitializeDataGenerator` Methode hinzuzufügen.

@[code lang=java transcludeWith=:::datagen-loot-tables:register](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## Beutetabellen erklärt {#loot-tables-explained}

**Beutetabellen** definieren, was du erhältst, wenn du einen Block abbaust (ohne den Inhalt, wie bei Truhen), eine Entität tötest oder einen neu erzeugten Container öffnest. Jede Beutetabelle hat einen **Pool** aus welchem Items ausgewählt werden. Beutetabellen haben außerdem **Funktionen**, welche die resultierenden Beute auf irgendeine Art verändern.

Beutepools haben **Einträge**, **Bedingungen**, Funktionen, **Rollen** und **Bonusrollen**. Einträge sind Gruppen, Sequenzen, Möglichkeiten an Items oder einfach Items. Bedingungen sind Dinge, die in der Welt getestet werden, wie z. B. Verzauberungen auf einem Werkzeug oder eine zufällige Chance. Die minimale Anzahl an Einträgen, welche von einem Pool gewählt werden, nennen sich Rollen und alles darüber nennt sich eine Bonusrolle.

## Blöcke {#blocks}

Damit Blöcke Items - auch sich selbst - fallen lassen können, müssen wir eine Beutetabelle erstellen. Erstelle eine Klasse, die von `FabricBlockLootTableProvider` erbt:

@[code lang=java transcludeWith=:::datagen-loot-tables:block-provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModBlockLootTableProvider.java)

Füge diesen Provider unbedingt zu deinem Pack hinzu!

Es gibt eine Reihe von Hilfsmethoden, die dir bei der Erstellung deiner Beutetabellen unterstützen. Wir werden sie nicht alle aufzählen, aber du solltest sie in deiner IDE ansehen.

Lasst uns ein paar Drops in der Methode `generate` hinzufügen:

@[code lang=java transcludeWith=:::datagen-loot-tables:block-drops](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModBlockLootTableProvider.java)

## Truhen {#chests}

Beute von Truhen sind ein wenig komplizierter als Beute von Blöcken. Erstelle eine Klasse, die von `SimpleFabricLootTableProvider` erbt, ähnlich zu dem Beispiel unterhalb **und füge sie zu deinem Pack hinzu**.

@[code lang=java transcludeWith=:::datagen-loot-tables:chest-provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModChestLootTableProvider.java)

Wir werden einen `ResourceKey<LootTable>` für unsere Beutetabelle benötigen. Lasst uns dies in eine neue Klasse mit dem Namen `ModLootTables` packen. Stelle sicher, dass dies dein `main` Quellenverzeichnis ist, wenn du geteilte Quellen nutzt.

@[code lang=java transcludeWith=:::datagen-loot-tables:mod-loot-tables](@/reference/latest/src/main/java/com/example/docs/ModLootTables.java)

Dann können wir eine Beutetabelle innerhalb der `generate` Methode unseres Providers generieren.

@[code lang=java transcludeWith=:::datagen-loot-tables:chest-loot](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModChestLootTableProvider.java)
