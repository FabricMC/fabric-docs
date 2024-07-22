---
title: Benutzerdefinierte Itemgruppen
description: Lerne, wie du deine eigenen Itemgruppen erstellst und Items hinzufügst.
authors:
  - IMB11
---

# Benutzerdefinierte Itemgruppen {#custom-item-groups}

Itemgruppen sind die Registerkarten im kreativen Inventar, in denen Items gespeichert werden. Du kannst deine eigenen Itemgruppen erstellen und deine Items in einem speraten Tab speichern. Das ist ziemlich nützlich, wenn dein Mod viele Items hinzufügt und du sie an einem Ort organisieren möchtest, damit deine Spieler leicht darauf zugreifen können.

## Die Itemgruppe erstellen {#creating-the-item-group}

Es ist überaschend einfach eine Itemgruppe zu erstellen. Erstell einfach ein neues statisches finales Feld in deiner Itemklasse, um die Itemgruppe und einen Registrierungsschlüssel dafür zu speichern, dann kannst du das Itemgruppen-Ereignis ähnlich verwenden, wie du deine Items zu den Vanilla Itemgruppen hinzugefügt hast:

@[code transcludeWith=:::9](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

@[code transcludeWith=:::_12](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

<hr />

Du solltest sehen, dass die Itemgruppe jetzt im kreativen Inventar ist. Es ist jedoch unübersetzt - du musst deiner Übersetzungsdatei einen Übersetzungsschlüssel hinzufügen - ähnlich wie du dein erstes Item übersetzt hast.

![Itemgruppe ohne Übersetzungsschlüssel im kreativen Menü](/assets/develop/items/itemgroups_0.png)

## Einen Übersetzungsschlüssel hinzufügen {#adding-a-translation-key}

Wenn du `Text.translatable` für die Methode `displayName` des Itemgruppen Builders verwendet hast, musst du die Übersetzung zu deiner Sprachdatei hinzufügen.

```json
{
    "itemGroup.fabric_docs_reference": "Fabric Docs Reference"
}
```

Wie du sehen kannst, sollte die Itemgruppe nun korrekt benannt sein:

![Vollständig fertiggestellte Itemgruppe mit Übersetzung und Items](/assets/develop/items/itemgroups_1.png)
