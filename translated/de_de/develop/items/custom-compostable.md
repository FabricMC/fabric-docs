---
title: Benutzerdefiniertes kompostierbares Item
description: Lerne, wie du deine eigenen kompostierbaren Items erstellst.
authors:
  - NotNightSky
  - its-miroma
---

Kompostierbare Items sind solche, die sich in Knochenmehl umwandeln, wenn sie in einen Komposter gegeben werden. Lasst uns ansehen, wie wir unser eigenes, benutzerdefiniertes kompostierbares Item erstellen können.

## Das Item erstellen {#creating-the-item}

Lasst uns ein kompostierbares Item namens "Bone Marrow" (Knochenmark) erstellen. Wir beginnen wie gewohnt damit, [ein Item zu erstellen](./first-item):

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#bone_marrow_resource

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#bone_marrow

Um es kompostierbar zu machen, werden wir es der Registry `CompostableRegistry.INSTANCE` aus der Fabric Registry API hinzufügen:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#compostable_item

Vergiss wie üblich nicht, eine Textur, eine Übersetzung, einen Kreativtab usw. hinzuzufügen. Hier ist eine Beispiel Textur:

<DownloadEntry visualURL="/assets/develop/items/bone_marrow_big.png" downloadURL="/assets/develop/items/bone_marrow.png">Textur</DownloadEntry>

So sieht es aus, wenn man es im Komposter verwendet:

<VideoPlayer src="/assets/develop/items/using_bone_marrow.webm">Verwendung des Knochenmark als kompostierbares Item</VideoPlayer>
