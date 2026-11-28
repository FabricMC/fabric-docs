---
title: Benutzerdefiniertes kompostierbares Item
description: Lerne, wie du deine eigenen kompostierbaren Items erstellst.
authors:
  - its-miroma
  - NotNightSky
---

Kompostierbare Items sind solche, die sich in Knochenmehl umwandeln, wenn sie in einen Komposter gegeben werden. Lasst uns ansehen, wie wir unser eigenes, benutzerdefiniertes kompostierbares Item erstellen können.

## Das Item erstellen {#creating-the-item}

Lasst uns ein kompostierbares Item namens "Bone Marrow" (Knochenmark) erstellen. Zunächst [erstellen wir wie gewohnt ein Item](./first-item) und wenden dann die Komponente `Compostable` darauf an, entweder direkt über `.component` oder über die Hilfsmethode `.compostable`, wenn du auf einen `ResourceKey<ContextIntProvider>` verweist:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#bone_marrow_resource

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#bone_marrow

Alternativ können wir, falls wir nicht selbst das Item registrieren, das Event `DefaultItemComponentEvents.MODIFY` aus der Fabric Item-API verwenden:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#compostable_item

Vergiss wie üblich nicht, eine Textur, eine Übersetzung, einen Kreativtab usw. hinzuzufügen. Hier ist eine Beispiel Textur:

<DownloadEntry visualURL="/assets/develop/items/bone_marrow_big.png" downloadURL="/assets/develop/items/bone_marrow.png">Textur</DownloadEntry>

So sieht es aus, wenn man es im Komposter verwendet:

![Verwendung des Knochenmark als kompostierbares Item](/assets/develop/items/using_bone_marrow.webm)
