---
title: Generierung von Tags
description: Ein Leitfaden zur Einrichtung der Generierung von Tags mit dem Datengenerator.
authors:
  - CelDaemon
  - IMB11
  - skycatminepokie
  - Spinoscythe
authors-nogithub:
  - mcrafterzz
---

<!---->

:::info VORAUSSETZUNGEN

Stelle sicher, dass du den Prozess der [Einrichtung des Datengenerators](./setup) zuerst abgeschlossen hast.

:::

## Einrichtung {#setup}

Hier werden wir zeigen, wie man `Item` Tags erstellt, aber das gleiche Prinzip lässt sich auch auf alle anderen Dinge anwenden.

Fabric bietet mehrere Hilfs-Tag-Provider, darunter einen für Items: `FabricTagProvider.ItemTagProvider`. Wir werden für dieses Beispiel diese Hilfsklasse verwenden.

Du kannst deine eigene Klasse erstellen, die von `FabricTagProvider<T>` erbt, wobei `T` der Typ ist, für den du ein Tag bereitstellen möchtest. Dies ist dein **Provider**.

Lass deiner IDE den erforderlichen Code ausfüllen und ersetze dann den Konstruktorparameter `resourceKey` durch den `ResourceKey` für deinen Typ:

@[code lang=java transcludeWith=:::datagen-tags:provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java)

::: tip

Du wirst für jeden Tag-Typ einen anderen Provider benötigen (z. B. einen `FabricTagProvider<EntityType<?>>` und einen `FabricTagProvider<Item>`).

:::

Um die Einrichtung abzuschließen, füge den Provider zu deinem `DataGeneratorEntrypoint` in der `onInitializeDataGenerator` Methode hinzu.

@[code lang=java transcludeWith=:::datagen-tags:register](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## Erstellen eines Tags {#creating-a-tag}

Jetzt, nachdem du den Provider erstellt hast, lasst uns ein Tag zu diesem hinzufpgen. Zuerst, erstelle ein `TagKey<T>`:

@[code lang=java transcludeWith=:::datagen-tags:tag-key](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java)

Als nächstes, rufe `valueLookupBuilder` innerhalb der `configure`-Methode deines Providers auf. Von dort aus kannst du einzelne Items oder andere Tags hinzufügen oder diese Tags bereits vorhandene Tags ersetzen lassen.

Wenn du ein Tag hinzufügen willst, verwende `addOptionalTag`, da der Inhalt des Tags während der Datengenerierung möglicherweise nicht geladen wird. Wenn du sicher bist, dass der Tag geladen ist, rufe `addTag` auf.

Um zwangsweise ein Tag hinzuzufügen und das defekte Format zu ignorieren, verwende `forceAddTag`.

@[code lang=java transcludeWith=:::datagen-tags:build](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java)
