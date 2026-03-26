---
title: Spawn-Eier
description: Erfahre, wie du ein Spawn-Ei Item registrierst.
authors:
  - Earthcomputer
  - JaaiDead
  - cassiancc
  - Fellteros
  - skycatminepokie
  - VatinMc
---

<!---->

:::info VORAUSSETZUNGEN

Du musst zunächst verstehen, [wie man ein Item erstellt](./first-item), das du dann in ein Spawn-Ei verwandeln kannst.

Dieser Artikel bezieht sich auch auf die Mini-Golem Entität aus [deine erste Entität erstellen](../entities/first-entity). Falls du diese Anleitung nicht befolgt hast, kannst du anstelle von `ModEntityTypes.MINI_GOLEM` eine Vanilla Entität wie `EntityType.FROG` verwenden.

:::

Spawn-Eier sind spezielle Items, die bei der Verwendung das entsprechende Mob erschaffen können. Du kannst eines mit der Methode `register` aus deiner [Item Klasse](./first-item#preparing-your-items-class) registrieren, indem du `SpawnEggItem::new` an diese übergibst.

@[code transcludeWith=:::custom_entity_spawn_egg](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Bevor es fertig ist, müssen noch ein paar Dinge erledigt werden: Du musst eine Textur, ein Item-Modell, ein Client Item und einen Namen hinzufügen und das Spawn-Ei zum entsprechenden Kreativtab hinzufügen.

## Eine Textur hinzufügen {#adding-a-texture}

Erstelle die 16x16-Item-Textur im Verzeichnis `assets/example-mod/textures/item` mit dem gleichen Dateinamen wie die ID des Items: `mini_golem_spawn_egg.png`. Eine Beispiel-Textur ist unten aufgeführt.

<DownloadEntry visualURL="/assets/develop/entity/mini_golem_spawn_egg.png" downloadURL="/assets/develop/entity/mini_golem_spawn_egg_small.png">Textur</DownloadEntry>

## Ein Modell hinzufügen {#adding-a-model}

Erstelle das 16x16-Modell im Verzeichnis `assets/example-mod/models/item` mit dem gleichen Dateinamen wie die Id des Items: `mini_golem_spawn_egg.png`.

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/mini_golem_spawn_egg.json)

## Ein Client Item erstellen {#creating-the-client-item}

Erstelle das Client Item JSON in`assets/example-mod/items` mit dem gleichen Dateinamen wie die ID des Itemmodells: `mini_golem_spawn_egg.png`.

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/mini_golem_spawn_egg.json)

![Spawn-Ei Item mit Client Item](/assets/develop/entity/mini_golem_spawned.png)

## Das Spawn-Ei benennen {#naming-the-spawn-egg}

Um das Spawn-Ei zu benennen, muss dem Übersetzungsschlüssel `item.example-mod.mini_golem_spawn_egg` ein Wert zugewiesen werden. Dieser Vorgang ähnelt dem unter [benennen des Items](./first-item#naming-the-item) beschriebenen.

Erstelle oder bearbeite die JSON-Datei unter: `src/main/resources/assets/example-mod/lang/en_us.json` und füge den Übersetzungsschlüssel und dessen Wert ein:

```json
{
  "item.example-mod.mini_golem_spawn_egg": "Mini Golem Spawn Egg"
}
```

## Zu einem Kreativtab hinzufügen {#adding-to-a-creative-mode-tab}

Das Spawn-Ei wird zum Spawn-Ei `CreativeModeTab` in der Methode `initialize()` der [Item-Klasse](./first-item#preparing-your-items-class) hinzugefügt.

@[code transcludeWith=:::spawn_egg_creative_tab](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

![Spawn-Ei mit Name und Kreativtab](/assets/develop/entity/spawn_egg_in_creative.png)

Weitere Informationen findest du unter [hinzufügen des Items zu einem Kreativtab](./first-item#adding-the-item-to-a-creative-tab).
