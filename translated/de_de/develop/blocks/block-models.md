---
title: Blockmodelle
description: Ein Leitfaden zum Verfassen und Verstehen von Blockmodellen.
authors:
  - Fellteros
  - its-miroma
---

<!-- markdownlint-disable search-replace -->

Diese Seite führt dich durch das Verfassen deiner eigenen Blockmodelle und erklärt dir alle Optionen und Möglichkeiten.

## Was sind Blockmodelle? {#what-are-block-models}

Blockmodelle sind im Kern die Definition des Aussehens und der Optik eines Blocks. Sie legen eine Textur, Modellübersetzung, Rotation, Skalierung und andere Attribute fest.

Modelle werden als JSON-Dateien in deinem `resources`-Ordner gespeichert.

## Dateistruktur {#file-structure}

Jede Blockmodell-Datei hat eine festgelegte Struktur, die eingehalten werden muss. Es beginnt mit leeren geschweiften Klammern, die das **Root-Tag** des Modells darstellen. Hier ist ein kurzer Überblick über die Struktur von Blockmodellen:

```json
{
  "parent": "...",
  "ambientocclusion": "true/false",
  "display": {
    "<position>": {
      "rotation": [0.0, 0.0, 0.0],
      "translation": [0.0, 0.0, 0.0],
      "scale": [0.0, 0.0, 0.0]
    }
  },
  "textures": {
    "particle": "...",
    "<texture_variable>": "..."
  },
  "elements": [
    {
      "from": [0.0, 0.0, 0.0],
      "to": [0.0, 0.0, 0.0],
      "rotation": {
        "origin": [0.0, 0.0, 0.0],
        "axis": "...",
        "angle": "...",
        "rescale": "true/false"
      },
      "shade": "true/false",
      "light_emission": "...",
      "faces": {
        "<key>": {
          "uv": [0, 0, 0, 0],
          "texture": "...",
          "cullface": "...",
          "rotation": "...",
          "tintindex": "..."
        }
      }
    }
  ]
}
```

<!--@include: ../items/item-models.md#parent-->

Setze dieses Tag auf `builtin/generated`, um ein aus dem angegebenen Icon erstelltes Modell zu verwenden. Rotation kann durch [Blockzustände](./blockstates) erreicht werden.

### Umgebungsokklusion {#ambient-occlusion}

```json
{
  "ambientocclusion": "true/false"
}
```

Dieses Tag gibt an, ob [Umgebungsokklusion](https://en.wikipedia.org/wiki/Ambient_occlusion) verwendet werden soll. Der Standardwert ist `true`.

<!--@include: ../items/item-models.md#display-->

### Texturen {#textures}

```json
{
  "textures": {
    "particle": "...",
    "<texture_variable>": "..."
  }
}
```

Das Tag `textures` enthält die Texturen des Modells in Form einer Bezeichnung oder einer Texturvariable. Es beinhaltet drei zusätzliche Objekte:

1. `particle`: _String_. Definiert die Textur, aus der Partikel geladen werden sollen. Diese Textur wird auch als Überlagerung verwendet, wenn du dich in einem Netherportal befindest, und für die Standbilder von Wasser und Lava. Wird auch als Texturvariable betrachtet, auf die als `#particle` verwiesen werden kann.
2. `<texture_variable>`: _String_. Erstellt eine Variable und weist eine Textur zu. Kann später mit dem Präfix `#` referenziert werden (z.B., `"top": "namespace:path"` ⇒ `#top`)

<!--@include: ../items/item-models.md#elements-->

<!--@include: ../items/item-models.md#from-->

`from` gibt den Startpunkt des Quaders gemäß dem Schema `[x, y, z]` relativ zu der unteren linken Ecke an. `to` gibt den Endpunkt an. Ein Quader, der so groß wie ein Standardblock ist, würde bei `[0, 0, 0]` beginnen und bei `[16, 16, 16]` enden.
Die Werte von beiden müssen zwischen **-16** und **32** liegen, was bedeutet das jedes Blockmodell 3×3 Blöcke groß sein kann.

<!--@include: ../items/item-models.md#rotation-->

`rotation` definiert die Rotation von Elementen. Es beinhaltet vier weitere Werte:

1. `origin`: _Drei Fließkommazahlen_. Legt den Punkt der Rotation gemäß dem Schema `[x, y, z]` fest.
2. `axis`: _String_. Legt die Drehrichtung an und muss einer der folgenden Werte sein: `x`, `y` und `z`.
3. `angle`: _Fließkommazahl_. Legt den Winkel der Rotation fest. Reicht von **-45** zu **45**.
4. `rescale`: _Boolean_. Legt fest, ob die Flächen über den gesamten Block skaliert werden sollen. Der Standardwert ist `false`.

<!--@include: ../items/item-models.md#shade-to-faces-->

1. `uv`: _Vier Ganzzahlen_. Definiert den Bereich der zu verwendenden Textur gemäß dem Schema `[x1, y1, x2, y2]`. Wenn nicht festgelegt, wird standardmäßig ein Wert verwendet, der der xyz-Position des Elements entspricht.
   Tauschen der Werte von `x1` und `x2` (zum Beispiel von `0, 0, 16, 16` zu `16, 0, 0, 16`) dreht die Textur um. UV ist optional und wird, wenn nicht angegeben, automatisch anhand der Position des Elements generiert.
2. `texture`: _String_. Gibt die Textur der Fläche in Form einer [Texturvariable](#textures) an, der ein `#` vorangestellt ist.
3. `cullface`: _String_. Kann sein: `down`, `up`, `north`, `south`, `west`, oder `east`. Gibt an, ob eine Fläche nicht gerendert werden muss, wenn sich an der angegebenen Position ein Block befindet, der sie berührt.
   Es bestimmt auch die Seite des Blocks, von der aus das Lichtlevel für die Beleuchtung der Fläche verwendet wird. Wenn diese Option nicht gesetzt ist, wird standardmäßig die Seite verwendet.
4. `rotation`: _Ganzzahl_. Rotiert die Textur im Uhrzeigersinn um den angegebenen Winkel in 90-Grad-Schritten. Die Drehung hat keinen Einfluss darauf, welcher Teil der Textur verwendet wird.
   Stattdessen handelt es sich um eine Permutation der ausgewählten Textur-Eckpunkte (implizit oder explizit über `uv` ausgewählt).
5. `tintidex`: _Ganzzahl_. Färbt die Textur auf dieser Fläche mit einem Farbtonwert ein. Der Standardwert `-1` gibt an, dass die Tönung nicht verwendet werden soll.
   Jede andere Zahl wird an `BlockColors` übergeben, um den diesem Index entsprechenden Farbtonwert zu erhalten (gibt Weiß zurück, wenn für den Block kein Farbtonindex definiert ist).

## Quellen und Links {#sources-and-links}

Du kannst die [Blockmodell Seite](https://minecraft.wiki/w/Model#Block_models) des Minecraft Wiki für eine detailliertere Anleitung besuchen. Viele Informationen hier stammen von dieser Seite.
