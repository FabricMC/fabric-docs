---
title: Benutzerdefinierter Brennstoff
description: Lerne, wie du deine eigenen Brennstoff Items erstellst.
authors:
  - NotNightSky
  - its-miroma
---

Brenstoffe sind Items, die in einem Ofen verwendet werden können, um Erze zu schmelzen oder Essen zu kochen. Lasst uns ansehen, wie wir unseren eigenen benutzerdefinierten Brenstoff erstellen können.

## Das Item erstellen {#creating-the-item}

Lasst uns ein Brennstoff Item namens "Quark-Gloun Plasma" (Quark-Gluon-Plasma) erstellen. Wir beginnen wie gewohnt damit, [ein Item zu erstellen](./first-item):

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#quark_gluon_plasma_resource

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#quark_gluon_plasma

Um einen Brenstoff zu erstellen, werden wir das Event `FuelValueEvents.BUILD` der Fabric Content Registries API verwenden:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#fuel_item

Vergiss wie üblich nicht, eine Textur, eine Übersetzung, einen Kreativtab usw. hinzuzufügen. Hier ist eine Beispiel Textur:

<DownloadEntry visualURL="/assets/develop/items/quark_gluon_plasma_big.png" downloadURL="/assets/develop/items/quark_gluon_plasma.png">Textur</DownloadEntry>

So sieht es aus, wenn man es als Brennstoff im Ofen verwendet:

<VideoPlayer src="/assets/develop/items/fuel_in_furnace.webm">Verwenung des Quark-Gluon-Plasma als Brennstoff</VideoPlayer>
