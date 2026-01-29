---
title: Item Modelle
description: Ein Leitfaden zum Verfassen und Verstehen von Item Modellen.
authors:
  - Fellteros
  - its-miroma
  - VatinMc
---

<!-- markdownlint-disable search-replace -->

Diese Seite führt dich durch das Verfassen deiner eigenen Item Modelle und erklärt dir alle Optionen und Möglichkeiten.

## Was sind Item Modelle? {#what-are-item-models}

Item Modelle sind im Kern die Definition des Aussehens und der Optik eines Items. Sie legen eine Textur, Modellübersetzung, Rotation, Skalierung und andere Attribute fest.

Modelle werden als JSON-Dateien in deinem `resources`-Ordner gespeichert.

## Dateistruktur {#file-structure}

Jede Item Modell-Datei hat eine festgelegte Struktur, die eingehalten werden muss. Es beginnt mit leeren geschweiften Klammern, die das **Root-Tag** des Modells darstellen. Hier ist ein kurzer Überblick über die Struktur von Item Modellen:

```json
{
  "parent": "...",
  "display": {
    "<position>": {
      "rotation": [0.0, 0.0, 0.0],
      "translation": [0.0, 0.0, 0.0],
      "scale": [0.0, 0.0, 0.0]
    }
  },
  "textures": {
    "<layerN>": "...",
    "particle": "...",
    "<texture_variable>": "..."
  },
  "gui_light": "...",
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

<!-- #region parent -->

### Übergeordnet {#parent}

```json
{
  "parent": "..."
}
```

Lädt ein anderes Modell mit all seinen Attributen aus dem angegebenen Pfad als Bezeichner (`namespace:path`).

<!-- #endregion parent -->

Setzt das Tag auf `item/generated`, um ein aus dem angegebenen Icon erstelltes Modell zu verwenden, oder setze es auf `builtin/generated`, um das Modell ohne Übersetzung, Drehung oder Skalierung zu verwenden.

<!-- #region display -->

### Anzeige {#display}

```json
{
  "display": {
    "<position>": {
      "rotation": [0.0, 0.0, 0.0],
      "translation": [0.0, 0.0, 0.0],
      "scale": [0.0, 0.0, 0.0]
    }
  }
}
```

Dieses Tag ist für das Setzen der Modell Übersetzung, Rotation und Skalierung an einer bestimmten Position verantwortlich.

Das Positionsobjekt kann eine der folgenden Zeichenfolgen sein, die definieren, wie das Modell in verschiedenen Positionen aussehen wird:

| Wert                    | Beschreibung                                                                                   |
| ----------------------- | ---------------------------------------------------------------------------------------------- |
| `firstperson_righthand` | Rechte Hand, aus der Perspektive der ersten Person gesehen                                     |
| `firstperson_lefthand`  | Linke Hand, aus der Perspektive der ersten Person gesehen                                      |
| `thirdperson_righthand` | Rechte Hand, aus der Perspektive der dritten Person gesehen (<kbd>F5</kbd>) |
| `thirdperson_lefthand`  | Linke Hand, aus der Perspektive der dritten Person gesehen (<kbd>F5</kbd>)  |
| `gui`                   | Wenn in einem GUI, zum Beispiel das Inventar                                                   |
| `head`                  | Wenn es auf den Kopf des Spielers gesetzt wird, zum Beispiel ein Banner                        |
| `ground`                | Wenn auf dem Boden                                                                             |
| `fixed`                 | Wenn in einen Rahmen platziert                                                                 |

Weiterhin kann jede Position diese drei Werte in Form eines Arrays von Fließkommazahlen enthalten:

```json
{
  "rotation": [0.0, 0.0, 0.0],
  "translation": [0.0, 0.0, 0.0],
  "scale": [0.0, 0.0, 0.0]
}
```

1. `rotation`: _Drei Fließkommazahlen_. Legt die Rotation des Modells gemäß dem Schema `[x, y, z]` fest.
2. `translation`: _Drei Fließkommazahlen_. Legt die Übersetzung des Modells gemäß dem Schema `[x, y, z]` fest. Werte müssen zwischen `-80` und `80`; Werte außerhalb dieses Bereichs werden auf den nächstgelegenen Extremwert gesetzt.
3. `scale`: _Drei Fließkommazahlen_. Legt die Skalierung des Modells gemäß dem Schema `[x, y, z]` fest. Der maximale Wert ist `4`, größere Werte werden als `4` gesehen.

<!-- #endregion display -->

### Texturen {#textures}

```json
{
  "textures": {
    "<layerN>": "...",
    "particle": "...",
    "<texture_variable>": "..."
  }
}
```

Das Tag `textures` enthält die Texturen des Modells in Form einer Bezeichnung oder einer Texturvariable. Es beinhaltet drei zusätzliche Objekte:

1. `<layerN>`: _String_. Es gibt das Icon des im Inventar verwendeten Items an. Es kann mehr als eine Ebene geben (z.B. Spawn-Eier), maximal jedoch 3.
2. `particle`: _String_. Definiert die Textur, aus der Partikel geladen werden sollen. Wenn nicht definiert, wird `layer0` verwendet.
3. `<texture_variable>`: _String_. Es erstellt eine Variable und weist eine Textur zu. Kann später mit dem Präfix `#` referenziert werden (z.B., `"top": "namespace:path"` ⇒ `#top`)

:::warning WICHTIG
`<layerN>` funktioniert nur, wenn `parent` als `item/generated` gesetzt ist!
:::

### GUI Licht {#gui-light}

```json
{
  "gui_light": "..."
}
```

Dieses Tag definiert die Richtung, aus der das Item Modell beleuchtet wird. Kann entweder `front` oder `side` sein, wobei das letztere der Standard ist. Wenn auf `front` gesetzt, wird das Modell wie ein flaches Item gerendert, wenn auf `side` gesetzt, wird das Element wie ein Block gerendert.

<!-- #region elements -->

### Elemente {#elements}

```json
{
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
        "<face>": {
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

Beinhaltet alle Elemente eines Modells, welches nur würfelförmig sein kann. Wenn sowohl das `parent`- als auch das `elements`-Tag gesetzt sind, überschreibt das `elements`-Tag dieser Datei das des übergeordneten Tags.

<!-- #endregion elements -->

<!-- #region from -->

```json
{
  "from": [0.0, 0.0, 0.0],
  "to": [0.0, 0.0, 0.0]
}
```

<!-- #endregion from -->

`from` gibt den Startpunkt des Quaders gemäß dem Schema `[x, y, z]` relativ zu der unteren linken Ecke an. `to` gibt den Endpunkt an. Ein Quader, der so groß wie ein Standardblock ist, würde bei `[0, 0, 0]` beginnen und bei `[16, 16, 16]` enden.
Die Werte von beiden müssen zwischen **-16** und **32** liegen, was bedeutet das jedes Item Modell 3×3 Blöcke groß sein kann.

<!-- #region rotation -->

```json
{
  "rotation": {
    "origin": [0.0, 0.0, 0.0],
    "axis": "...",
    "angle": "...",
    "rescale": "true/false"
  }
}
```

<!-- #endregion rotation -->

`rotation` definiert die Rotation von Elementen. Es beinhaltet vier weitere Werte:

1. `origin`: _Drei Fließkommazahlen_. Legt den Punkt der Rotation gemäß dem Schema `[x, y, z]` fest.
2. `axis`: _String_. Legt die Drehrichtung an und muss einer der folgenden Werte sein: `x`, `y` und `z`.
3. `angle`: _Fließkommazahl_. Legt den Winkel der Rotation fest. Reicht von **-45** bis **45** in Schritten von 22,5 Grad.
4. `rescale`: _Boolean_. Legt fest, ob die Flächen über den gesamten Block skaliert werden sollen. Der Standardwert ist `false`.

<!-- #region shade-to-faces -->

```json
{
  "shade": "true/false"
}
```

`shade` definiert, ob Schatten gerendert werden sollen. Der Standardwert ist `true`.

```json
{
  "light_emission": "..."
}
```

`light_emission` definiert das minimale Licht-Level, das das Element empfangen kann. Der Wert kann zwischen **0** und **15** liegen. Der Standardwert ist 0.

```json
{
  "faces": {
    "<key>": {
      "uv": [0, 0, 0, 0],
      "texture": "...",
      "cullface": "...",
      "rotation": 0,
      "tintindex": 0
    }
  }
}
```

`faces` enthält alle Flächen eines Quaders. Wenn eine Fläche nicht gesetzt wird, wird sie nicht gerendert. Dessen Schlüssel (`<key>`) können einer der folgenden sein: `down`, `up`, `north`, `south`, `west` oder `east`. Jeder Schlüssel enthält die Eigenschaften für diese Fläche:

<!-- #endregion shade-to-faces -->

1. `uv`: _Vier Ganzzahlen_. Definiert den Bereich der zu verwendenden Textur gemäß dem Schema `[x1, y1, x2, y2]`. Wenn nicht festgelegt, wird standardmäßig ein Wert verwendet, der der xyz-Position des Elements entspricht.
   Tauschen der Werte von `x1` und `x2` (zum Beispiel von `0, 0, 16, 16` zu `16, 0, 0, 16`) dreht die Textur um. UV ist optional und wird, wenn nicht angegeben, automatisch anhand der Position des Elements generiert.
2. `texture`: _String_. Gibt die Textur der Fläche in Form einer [Texturvariable](#textures) an, der ein `#` vorangestellt ist.
3. `cullface`: _String_. Kann sein: `down`, `up`, `north`, `south`, `west`, oder `east`. Gibt an, ob eine Fläche nicht gerendert werden muss, wenn sich an der angegebenen Position ein Block befindet, der sie berührt.
   Es bestimmt auch die Seite des Blocks, von der aus das Lichtlevel für die Beleuchtung der Fläche verwendet wird. Wenn diese Option nicht gesetzt ist, wird standardmäßig die Seite verwendet.
4. `rotation`: _Ganzzahl_. Rotiert die Textur um den angegebenen Winkel in 90-Grad-Schritten. Die Drehung hat keinen Einfluss darauf, welcher Teil der Textur verwendet wird.
   Stattdessen handelt es sich um eine Permutation der ausgewählten Textur-Eckpunkte (implizit oder explizit über `uv` ausgewählt).
5. `tintidex`: _Ganzzahl_. Färbt die Textur auf dieser Fläche unter Verwendung eines Farbwerts, der aus der Definition des Client Items referenziert wird. Wenn keine Farbe für die Färbung (oder Weiß) angegeben ist, wird die Textur nicht gefärbt.

## Quellen und Links {#sources-and-links}

Du kannst die [Item Modell Seite](https://minecraft.wiki/w/Model#Item_models) des Minecraft Wiki für eine detailliertere Anleitung besuchen. Viele Informationen hier stammen von dieser Seite.
