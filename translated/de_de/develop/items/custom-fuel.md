---
title: Benutzerdefinierter Brennstoff
description: Lerne, wie du deine eigenen Brennstoff Items erstellst.
authors:
  - its-miroma
  - NotNightSky
---

Brenstoffe sind Items, die in einem Ofen verwendet werden können, um Erze zu schmelzen oder Essen zu kochen. Lasst uns ansehen, wie wir unseren eigenen benutzerdefinierten Brenstoff erstellen können.

## Das Item erstellen {#creating-the-item}

Lasst uns ein Brennstoff Item namens "Quark-Gloun Plasma" (Quark-Gluon-Plasma) erstellen. Zunächst [erstellen wir wie gewohnt ein Item](./first-item) und wenden dann die Komponente `CookingFuel` darauf an, entweder direkt über `.component` oder über die Hilfsmethode `.cookingFuel`, wenn du auf einen `ResourceKey<ContextIntProvider>` verweist:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#quark_gluon_plasma_resource

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#quark_gluon_plasma

Alternativ können wir, falls wir nicht selbst das Item registrieren, das Event `DefaultItemComponentEvents.MODIFY` aus der Fabric Item-API verwenden:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#fuel_item

Vergiss wie üblich nicht, eine Textur, eine Übersetzung, einen Kreativtab usw. hinzuzufügen. Hier ist eine Beispiel Textur:

<DownloadEntry visualURL="/assets/develop/items/quark_gluon_plasma_big.png" downloadURL="/assets/develop/items/quark_gluon_plasma.png">Textur</DownloadEntry>

So sieht es aus, wenn man es als Brennstoff im Ofen verwendet:

![Das Quark-Gluon-Plasma als Brennstoff verwenden](/assets/develop/items/fuel_in_furnace.webm)
