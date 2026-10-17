---
title: Überschreiben von Abhängigkeiten
description: Ein Leitfaden zum Überschreiben von Abhängigkeiten, die in der Datei `fabric.mod.json` eines Mods definiert sind.
authors:
  - cassiancc
  - skycatminepokie
  - ytg1234
authors-nogithub:
  - kb1000
resources:
  https://semver.org/: Semantische Versionierung
---

<!---->

::: warning

Das Überschreiben von Abhängigkeiten dient dazu, Modpack-Entwicklern die Kontrolle über ihre Mods zu geben. Dies sollte nicht durch normale Spielern verwendet werden.

Es wird empfohlen, sich zunächst mit [der Struktur der Felder für Mod-Abhängigkeiten](../../develop/loader/fabric-mod-json#semantic-versioning) vertraut zu machen, bevor du fortfährst.

:::

Manchmal wirst du möglicherweise beim Zusammenstellen eines Modpacks auf Mods mit wenig hilfreichen Anforderungen an die Abhändigkeiten stoßen - beispielsweise könnte ein Mod zu strenge Anforderungen stellen und Minecraft `26.1` verlangen, obwohl er auch unter `26.1.2` funktioniert.

Um dem entgegenzuwirken, lässt dich der Fabric Loader Anforderungen an die Abhängigkeiten überschreiben, sodass du versuchen kannst, einen Mod in einer Minecraft-Version zu laden, für die er nicht vorgesehen ist.

::: tip

Das Überschreiben von Abhängigkeiten sollte nach Möglichkeit nur eine vorübergehende Lösung sein. Falls der Mod aktiv gewartet wird, solltest du darüber nachdenken, diese Inkompatibilität im Issue-Tracker zu melden und die Entwickler des Upstreams das Problem beheben zu lassen.

:::

## Einrichtung {#setup}

:::: info

Für dieses Beispiel verwenden wir die folgende Datei `fabric.mod.json` für einen Mod mit der ID `example-mod`. Du kannst jederzeit zwischen den Registerkarten in einem Code-Block wechseln, um zu sehen, wie sich die Überschreibung der Abhängigkeit auf diese `fabric.mod.json` auswirkt.

:::details `fabric.mod.json`

```json
{
  "depends": {
    "fabricloader": ">=0.11.1",
    "fabric-api": ">=0.28.0",
    "minecraft": "26.1"
  },
  "breaks": {
    "optifabric": "*"
  },
  "suggests": {
    "anothermod": "*",
    "flamingo": "*",
    "modupdater": "*"
  }
}
```

:::

::::

Erstelle zunächst eine Datei mit dem Namen `fabric_loader_dependencies.json` im Ordner `.minecraft/config`.

Als Nächstes füllen wir die Datei mit dem folgenden Standardtext:

::: code-group

```json [fabric_loader_dependencies.json]
{
  "version": 1,
  "overrides": {
    "example-mod": {} // [!code highlight]
  }
}
```

```json [fabric.mod.json]
{
  "depends": {
    "fabricloader": ">=0.11.1",
    "fabric-api": ">=0.28.0",
    "minecraft": "26.1"
  },
  "breaks": {
    "optifabric": "*"
  },
  "suggests": {
    "anothermod": "*",
    "flamingo": "*",
    "modupdater": "*"
  }
}
```

:::

Lasst und dies Zeile für Zeile durchgehen.

Zuerst haben wir `version`, womit die Version der Spezifikation für die Überschreibung von Abhängigkeiten angegeben wird, die wir verwenden möchten. Zum Zeitpunkt des Schreiben dieser Seite ist die aktuellste Version die Version `1`.

Als zweites haben wir ein Objekt `overrides`, das alle unsere Überschreibungen von Abhängigkeiten zu verschiedenen Mods enthält. Zunächst enthält es einen leeren Eintrag für `example-mod`, dem wir Überschreibungen von Abhängigkeiten hinzufügen können.

Schlüssel innerhalb des Mod-Objekts können einem der fünf Abhängigkeitstypen entsprechen (`depends`, `recommends`, `suggests`, `conflicts`, `breaks`). Der Wert jedes dieser Schlüssel muss ein JSON-Objekt sein. Dieses JSON-Objekt folgt genau derselben Struktur wie ein [Objekt für eine Abhängigkeit in der `fabric.mod.json`](./fabric-mod-json#semantic-versioning).

Dem Schlüssel kann optional ein `+` oder `-` vorangestellt werden (z. B. `"+depends"`, `"-breaks"`).

::: tabs

== Mit dem Präfix +

Wenn dem Schlüssel ein `+` vorangestellt ist, werden die Einträge in diesem JSON-Objekt dem Mod hinzugefügt (oder überschrieben, falls sie bereits vorhanden sind).

```json{5}
{
  "version": 1,
  "overrides": {
    "example-mod": {
      "+depends": {
        "minecraft": ""
      }
    }
  }
}
```

== Mit dem Präfix -

Wenn dem Schlüssel ein `-` vorangestellt ist, wird der Wert jedes Eintrags vollständig ignoriert und der Fabric Loader entfernt diese Einträge aus der resultierenden Abhängigkeitskarte.

```json{5}
{
  "version": 1,
  "overrides": {
    "example-mod": {
      "-depends": {
        "minecraft": ""
      }
    }
  }
}
```

== Ohne einem Präfix

Wenn der Schlüssel keinen Präfix enthält, wird das Abhängigkeitsobjekt vollständig ersetzt. **Achte darauf, deinen Schlüsseln einen Präfix voranzustellen!**

```json{5}
{
  "version": 1,
  "overrides": {
    "example-mod": {
      "depends": {
        "minecraft": ""
      }
    }
  }
}
```

:::

## Abhängigkeiten überschreiben {#overriding-dependencies}

Lasst uns annehmen, dass ein Mod mit der ID `example-mod` **genau** von der Minecraft-Version `26.1` abhängt, wir möchten aber, dass er auch mit anderen 26.1-Versionen funktioniert. Lasst uns ansehen, wie wir das machen können:

::: code-group

```json{5-6} [fabric_loader_dependencies.json]
{
  "version": 1,
  "overrides": {
    "example-mod": {
      "depends": {
        "minecraft": "26.1.x"
      }
    }
  }
}
```

```json{2,5-6} [fabric.mod.json]
{
  "depends": {
    "fabricloader": ">=0.11.1",
    "fabric-api": ">=0.28.0",
    "minecraft": "26.1.x"
  },
  "breaks": {
    "optifabric": "*"
  },
  "suggests": {
    "anothermod": "*",
    "flamingo": "*",
    "modupdater": "*"
  }
}
```

:::

Eine `"minecraft"`-Abhängigkeit wird nun überschrieben, sofern sie angegeben ist (und wir wissen, dass dies der Fall ist). Es gibt noch eine andere Möglichkeit, dies zu tun:

::: code-group

```json{5-6} [fabric_loader_dependencies.json]
{
  "version": 1,
  "overrides": {
    "example-mod": {
      "-depends": {
        "minecraft": "IGNORED"
      }
    }
  }
}
```

```json{2,5-6} [fabric.mod.json]
{
  "depends": {
    "fabricloader": ">=0.11.1",
    "fabric-api": ">=0.28.0",
    "minecraft": "26.1.x"
  },
  "breaks": {
    "optifabric": "*"
  },
  "suggests": {
    "anothermod": "*",
    "flamingo": "*",
    "modupdater": "*"
  }
}
```

:::

Wie oben angegeben, wird der Wert des Schlüssels `"minecraft"` beim Entfernen von Abhängigkeiten ignoriert. Wird eine Abhängigkeit gefunden, deren Mod-ID-Anforderung `minecraft` lautet, wird sie aus unserem Ziel-Mod `example-mod` entfernt.

Wir können auch den gesamten `depends`-Block überschreiben, doch mit großer Macht geht große Verantwortung einher. Sei vorsichtig.

Neben der Änderung der `minecraft`-Abhängigkeit möchten wir auch alle `suggests`-Abhängigkeiten entfernen. Das erreichen wir, indem wir den Präfix des Schlüssels `suggests` entfernen und ihn durch ein leeres Objekt ersetzen, wodurch er im Grunde genommen gelöscht wird. Dies würde dann wie folgt aussehen:

::: code-group

```json [fabric_loader_dependencies.json]
{
  "version": 1,
  "overrides": {
    "example-mod": {
      "-depends": {
        "minecraft": ""
      },
      "suggests": {} // [!code highlight]
    }
  }
}
```

```json [fabric.mod.json]
{
  "depends": {
    "fabricloader": ">=0.11.1",
    "fabric-api": ">=0.28.0",
    "minecraft": "26.1"
  },
  "breaks": {
    "optifabric": "*"
  },
  "suggests": {} // [!code highlight]
}
```

:::

<!---->
