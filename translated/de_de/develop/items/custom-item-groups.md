---
title: Benutzerdefinierte Kreativtabs
description: Lerne, wie du deinen eigenen Kreativtab erstellst und Items ihm hinzufügst.
authors:
  - CelDaemon
  - IMB11
---

Kreativtabs, auch bekannt als Item Gruppen, sind Tabs im Kreativmenü, die Items speichern. Du kannst deine eigenen Kreativtabs erstellen und deine Items in einem speraten Tab speichern. Das ist ziemlich nützlich, wenn dein Mod viele Items hinzufügt und du sie an einem Ort organisieren möchtest, damit deine Spieler leicht darauf zugreifen können.

## Den Kreativtab erstellen {#creating-the-creative-tab}

Einen Kreativtab zu erstellen ist recht einfach. Erstelle einfach ein neues statisches finales Feld in deiner Item-Klasse, um den Kreativtab und einen Ressourcenschlüssel dafür zu speichern. Du kannst dann den `FabricItemGroup.builder` verwenden, um den Kreativtab zu erstellen und Items zu ihm hinzuzufügen:

@[code transcludeWith=:::9](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

@[code transcludeWith=:::\_12](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Du solltest sehen, dass die Kreativgruppe jetzt im Kreativinventar ist. Es ist jedoch unübersetzt - du musst deiner Übersetzungsdatei einen Übersetzungsschlüssel hinzufügen - ähnlich wie du dein erstes Item übersetzt hast.

![Kreativtab ohne Übersetzung im Kreativmenü](/assets/develop/items/itemgroups_0.png)

## Einen Übersetzungsschlüssel hinzufügen {#adding-a-translation-key}

Wenn du `Component.translatable` für die Methode `title` des Kreativtab Builders verwendet hast, musst du die Übersetzung zu deiner Sprachdatei hinzufügen.

```json
{
  "itemGroup.example-mod": "Example Mod"
}
```

Wie du sehen kannst, sollte der Kreativtab nun korrekt benannt sein:

![Vollständig fertiggestellter Kreativtab mit Übersetzung und Items](/assets/develop/items/itemgroups_1.png)
