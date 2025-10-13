---
title: Generierung von Tags
description: Ein Leitfaden zur Einrichtung der Generierung von Tags mit dem Datengenerator.
authors:
  - skycatminepokie
  - IMB11
  - Spinoscythe
authors-nogithub:
  - mcrafterzz
---

:::info VORAUSSETZUNGEN
Stelle sicher, dass du den Prozess der [Einrichtung der Datengenerierung](./setup) zuerst abgeschlossen hast.
:::

## Einrichten {#setup}

Erstelle zunächst eine eigene Klasse, die `extends FabricTagProvider<T>`, wobei `T` der Typ der Sache ist, für die du ein Tag bereitstellen möchtest. Dies ist dein **Provider**. Hier werden wir zeigen, wie man `Item` Tags erstellt, aber das gleiche Prinzip lässt sich auf alle anderen Sachen anwenden. Lass deiner IDE den erforderlichen Code ausfüllen und ersetze dann den Konstruktorparameter `registryKey` durch den `RegistryKey` für deinen Typ:

@[code lang=java transcludeWith=:::datagen-tags:provider](@/reference/1.21/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java)

:::info HINWEIS
Du wirst für jeden Tag-Typ einen anderen Provider benötigen (z. B. einen `FabricTagProvider<EntityType<?>>` und einen `FabricTagProvider<Item>`).
:::

Um die Einrichtung abzuschließen, füge den Provider zu deinem `DataGeneratorEntrypoint` in der `onInitializeDataGenerator` Methode hinzu.

@[code lang=java transclude={28-28}](@/reference/1.21/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## Erstellen eines Tags {#creating-a-tag}

Jetzt, nachdem du den Provider erstellt hast, lasst uns ein Tag zu diesem hinzufpgen. Zuerst, erstelle ein `TagKey<T>`:

@[code lang=java transcludeWith=:::datagen-tags:tag-key](@/reference/1.21/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java)

Als nächstes, rufe `getOrCreateTagBuilder` innerhalb deiner Provider `configure` Methode auf. Von dort aus kannst du einzelne Items oder andere Tags hinzufügen oder diese Tags bereits vorhandene Tags ersetzen lassen.

Wenn du ein Tag hinzufügen willst, verwende `addOptionalTag`, da der Inhalt des Tags während der Datengenerierung möglicherweise nicht geladen wird. Wenn du sicher bist, dass der Tag geladen ist, rufe `addTag` auf.

Um zwangsweise ein Tag hinzuzufügen und das defekte Format zu ignorieren, verwende `forceAddTag`.

@[code lang=java transcludeWith=:::datagen-tags:build](@/reference/1.21/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java)
