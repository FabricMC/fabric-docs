---
title: fabric.mod.json
description: Ein Leitfaden zur `fabric.mod.json` Spezifikation.
authors:
  - cassiancc
  - Deximus-Maximus
  - falseresync
  - jamieswhiteshirt
  - IMB11
  - SolidBlock-cn
authors-nogithub:
  - skyland1a
resources:
  https://github.com/FabricMC/fabric-loom/blob/dev/1.16/src/main/java/net/fabricmc/loom/api/fmj/FabricModJsonV1Spec.java: Quellcode für die fabric.mod.json v1 Spezifikation
  https://github.com/FabricMC/fabric-language-kotlin: Fabric Language Kotlin Sprachprovider
  https://spdx.org/licenses/: SPDX-Lizenzbezeichnungen
  https://semver.org/: Semantische Versionierung
  https://dexman545.github.io/outlet-database/floaderValidator: Fabric Loader Werkzeug zum Vergleich von Versionen
---

Die Datei `fabric.mod.json` ist eine Metadatendatei, die vom Fabric Loader zum Laden von Mods verwendet wird. Die Datei muss im Stammverzeichnis der JAR abgelegt werden, damit der Mod geladen wird.

Eine vordefinierte `fabric.mod.json`-Datei ist im Vorlagen-Mod enthalten oder kann [mit Fabric Loom generiert werden](../loom/tasks#fabric-mod-json).

Du kannst den [Quellcode der Spezifikation für `fabric.mod.json` v1](https://github.com/FabricMC/fabric-loom/blob/-/src/main/java/net/fabricmc/loom/api/fmj/FabricModJsonV1Spec.java) einsehen. Nachstehend findest du auch die Datei `fabric.mod.json`, die vom Beispiel-Mod dieser Website verwendet wird.

:::details `fabric.mod.json` des Beispiel-Mods

<<< @/reference/latest/src/main/resources/fabric.mod.json

:::

## Verpflichtende Felder {#mandatory-fields}

Die folgenden Felder sind verpflichtend, damit Fabric deinen Mod laden kann.

- **`schemaVersion`** Muss der erste Eintrag sein und der Wert muss immer `1` sein. Ist erforderlich, damit der Fabric Loader die Datei korrekt verarbeiten kann.
- **`id`** Ein String Wert, der die Bezeichnung des Mods definiert. Muss mit einem Buchstaben beginnen. Darf nur ASCII-Buchstaben, Ziffern, Unterstriche oder Bindestriche enthalten. 2 bis 64 Zeichen.
- **`version`** Ein String-Wert, der die Version des Mods definiert; er sollte vorzugsweise einer Übermenge der Spezifikation [Semantic Versioning 2.0.0](https://semver.org/) entsprechen und eine beliebige Anzahl von Komponenten sowie beliebige `build`-Metadaten unterstützen.
  Wenn dies keine Übereinstimmung ergibt, wird es als einfache Zeichenfolge behandelt, wobei Versionsbereiche nicht unterstützt werden.

```json
"schemaVersion": 1,
"id": "example-mod",
"version": "1.0.0"
```

## Metadaten {#metadata}

- **`name`**: Ein String, der den nutzerfreundlichen Namen des Mods definiert. Falls nicht angegeben, wird standardmäßig die **id** verwendet.
- **`description`**: Ein String, der die Beschreibung des Mods definiert. Falls nicht angegeben, wird standardmäßig ein leerer String verwendet.

```json
"name": "Example Mod",
"description": "This is an example description! Tell everyone what your mod is about!",
```

### Kontakt {#contact}

**`contact`**: Ein Verzeichnis, das die Kontaktdaten für das Projekt enthält. Zu den gängigen Feldern gehören:

- **`email`**: Ein String, der die Kontakt-E-Mail-Adresse für den Mod angibt. Muss eine gültige E-Mail-Adresse sein.
- **`homepage`**: Ein String, der die Website des Projekts oder Nutzers definiert. Muss eine gültige HTTP-/HTTPS-Adresse sein.
- **`irc`**: Ein String, der den zu dem Mod gehörenden IRC-Kanal angibt. Muss ein gültiges URL-Format sein - zum Beispiel: `irc://irc.esper.net:6667/charset` für `#charset` bei EsperNet - der Port ist optional und wird, falls nicht angegeben, mit 6667 angenommen.
- **`issues`**: Ein String, der den Issue-Tracker des Projekts definiert. Muss eine gültige HTTP-/HTTPS-Adresse sein.
- **`sources`**: Ein String, der das Quellcode-Repository des Projekts definiert. Muss eine gültige URL sein - es kann sich jedoch auch um eine spezielle URL für ein bestimmtes VCS (wie Git oder Mercurial) handeln.

Die Liste ist nicht vollständig - Mods können zusätzliche nicht standardmäßige Schlüssel angeben, wie beispielsweise **`discord`**, **`slack`**, **`twitter`**... Wenn möglich, sollten diese gültige URLs sein.

```json
"contact": {
    "homepage": "https://fabricmc.net",
    "sources": "https://github.com/FabricMC/fabric-example-mod"
}
```

### Autoren und Mitwirkende {#authors-contributors}

- **`authors`** Ein Array mit den Autoren des Mods. Einträge können entweder ein String oder ein Objekt mit den unten aufgeführten Feldern sein.
- **`contributors`** Ein Array von Mitwirkenden an dem Mod. Einträge können entweder ein String oder ein Objekt mit den unten aufgeführten Feldern sein.

Felder:

- **`name`** Ein verpflichtender String, für den echten Namen oder Nutzername der Person.
- **`contact`** Ein optionales Objekt für die Kontaktinformationen der Person. Das gleiche wie auf der übergeordneten Ebene [**`contact`**](#contact).

```json
"authors": [
    "Me!",
    {
        "name": "Tiny Potato",
        "contact": {
          "homepage": "https://fabricmc.net",
          "sources": "https://github.com/FabricMC/fabric-example-mod"
        }
    }
]
```

### Lizenz {#license}

- **`license`** Ein String oder Array, das die Lizenzinformationen definiert. Zur Unterstützung automatisierter Tools wird empfohlen, für Open-Source-Lizenzen [SPDX-Lizenzbezeichnungen](https://spdx.org/licenses/) zu verwenden.

Dies sollte die Mindestanzahl an Lizenzen abdecken, die für das gesamte Mod-Paket erforderlich sind. Mit anderen Worten: Wer deinen Mod nutzen oder weiterverbreiten möchte, muss lediglich die hier aufgeführten Lizenzen einhalten.
Falls Teile deines Codes unter zwei Lizenzen stehen, wähle die bevorzugte Lizenz aus.
Diese Liste verbietet dir nicht unbedingt, bestimmten Personen im Einzelfall zusätzliche Rechte zu gewähren oder andere Lizenzen anzuwenden.

```json
"license": "CC0-1.0"
```

### Symbol {#icon}

- **`icon`** Ein String oder Verzeichnis, dass das Symbol des Mods definiert. Symbole sind quadratische PNG-Dateien. Minecraft-Ressourcenpakete verwenden eine Auflösung von 128×128, doch das ist keine zwingende Voraussetzung - eine Potenz von zwei wird jedoch empfohlen. Kann auf zwei Arten angegeben werden:
  - Ein Pfad zu einer einzelnen PNG-Datei.
  - Ein Verzeichnis mit Bildbreiten und deren Dateipfaden.

```json
"icon": "assets/example-mod/icon.png"
```

## Laden von Mods {#mod-loading}

### Umgebung {#environment}

- **`environment`**: Ein String-Wert, der angibt, in welchen Umgebungen der Mod ausgeführt werden sollte:
  - **`*`**: Läuft in allen Umgebungen. Standard.
  - **`client`**: Läuft auf der physischen Client-Seite. Wenn gesetzt, wird dein Mod auf dedizierten Servern nicht geladen.
  - **`server`**: Läuft auf der physischen Server-Seite. Wenn gesetzt, wird dein Mod auf den Clients nicht geladen, dies schließt auch den Einzelspielermodus und LAN ein.

```json
"environment": "*"
```

### Einstiegspunkte {#entrypoints}

- **`entrypoints`** Ein Objekt, das die Hauptklassen definiert, die dein Mod laden wird.
  - **`main`** Ein Array von String Klassennamen, die `ModInitializer` implementieren.
  - **`client`** Ein Array von String Klassennamen, die `ClientModInitializer` implementieren. Dieser Einstiegspunkt wird nach `main` ausgeführt, und zwar ausschließlich auf der physischen Client-Seite.
  - **`server`** Ein Array von String Klassennamen, die `DedicatedServerModInitializer` implementieren. Dieser Einstiegspunkt wird nach `main` ausgeführt, und zwar ausschließlich auf der physischen Server-Seite.

Fabric Loader bietet diese drei Haupteinstiegspunkte, aber andere Mods können ihre eigenen bereitstellen. Beispielsweise stellt die Fabric-API `fabric-datagen` Einstiegspunkte für die Datengenerierung bereit.

Jeder Einstiegspunkt kann mehrere Klassen und sogar Methoden oder statische Felder laden. Zum Beispiel:

```json
"main": [
    "net.fabricmc.example.ExampleMod",
    "net.fabricmc.example.ExampleMod::handle"
]
```

::: tip

Wenn du Java nicht verwendest, solltest du die Dokumentation des Sprachadapters konsultieren. Für Kotlin ist es die [Fabric Language Kotlin's README](https://github.com/FabricMC/fabric-language-kotlin/blob/master/README.md).

:::

### JARs {#jars}

- **`jars`**: Ein Array von JARs, die in deinem Mod eingebettet werden sollen. Wenn du bei deinen Abhängigkeiten `include` verwendest, füllt Loom dieses Feld automatisch aus. Jeder Eintrag ist ein Objekt, das einen Schlüssel `file` enthält, der den Pfad zu der in deinem Mod enthaltenen verschachtelten JAR angibt. Zum Beispiel:

```json
"jars": [
   {
      "file": "nested/vendor/dependency.jar"
   }
]
```

### Sprachadapter {#language-adapters}

- **`languageAdapters`**: Ein Verzeichnis an Klassen, die `LanguageAdapter` implementieren.

```json
"languageAdapters": {
   "kotlin": "net.fabricmc.language.kotlin.KotlinAdapter"
}
```

### Mixins {#mixins}

- **`mixins`**: Eine Liste an Mixin-Konfigurationsdateien. Jeder Eintrag ist entweder ein Pfad zu einer Mixin-Konfigurationsdatei innerhalb der JAR deines Mods oder ein Objekt, das die folgenden Felder enthält:
  - **`config`**: Der Pfad zu der Mixin-Konfigurationsdatei innerhalb der JAR deines Mods.
  - **`environment`**: Verhält sich genau wie das [obige **environment** Feld](#environment). Zum Beispiel:

```json
"mixins": [
   "example-mod.mixins.json",
   {
      "config": "example-mod.client-mixins.json",
      "environment": "client"
   }
]
```

### Zugriffserweiterer {#accesswideners}

- **`accessWidener`**: Ein String, der eine [Zugriffserweiterer- oder Klassenoptimierer-Datei](../class-tweakers/) identifiziert.

```json
"accessWidener": "example-mod.classtweaker"
```

### Provides {#provides}

- **`provides`**： Ein Array an Mod-IDs, die als Aliase für den Mod dienen. Der Fabric Loader behandelt diese IDs so, als ob die entsprechenden Mods vorhanden wären. Wenn ein anderer Mod eines davon verwendet, wird er nicht geladen.

```json
"provides": [
   "example_mod"
]
```

## Auflösung von Abhängigkeiten {#dependency-resolution}

Die folgenden Schlüssel akzeptieren ein Verzeichnis mit Abhängigkeiten. Weitere Informationen zum Aufbau des Verzeichnis findest du weiter unten:

- **`depends`**: Für die zur Ausführung erforderlichen Abhängigkeiten. **Wenn irgendwelche fehlen, wird der Fabric Loader einen Absturz auslösen**.
- **`recommends`**: Für die zur Ausführung nicht erforderlichen Abhängigkeiten. Für jede fehlende Abhängigkeit, wird der Fabric Loader eine Warnung loggen.
- **`suggests`**: Für die zur Ausführung nicht erforderlichen Abhängigkeiten. Verwende dies als eine Art Metadaten.
- **`breaks`**: Für Mods, die in Kombination mit deinen zu einem Spielabsturz führen könnten. **Wenn irgendwelche vorhanden sind, wird der Fabric Loader einen Absturz auslösen**.
- **`conflicts`**: Für Mods, die in Kombination mit deinem zu Fehlern oder Ähnlichem führen. Für jeden vorhandenen Mod, der zu Konflikten führt, loggt der Fabric Loader eine Warnung.

### Semantische Versionierung {#semantic-versioning}

Der Schlüssel jedes Eintrags ist die Mod-ID der Abhängigkeit.

Der Wert jedes Schlüssels ist ein String oder ein Array von Strings, die die unterstützten Versionsbereiche der Abhängigkeit angeben. Wenn es sich um ein Array handelt, muss nur einer der Bereiche übereinstimmen, damit die Bedingung erfüllt ist.

Fabric nutzt eine Erweiterung des Semantic Versioning, um eine beliebige Anzahl von Komponenten zu unterstützen; so werden beispielsweise Versionen wie `1.2.3.4` unterstützt. Beim Vergleich von Versionen mit unterschiedlicher Anzahl von Komponenten werden diese rechts mit `0` aufgefüllt; zum Beispiel `26.1` = `26.1.0`

Um alle Vorschauversionen innerhalb eines Bereichs zu erfassen, füge am Ende des Bereichs ein `-` hinzu; um beispielsweise nur die `26.2`-Snapshots zu erhalten, verwende den Bereich `>26.2- <26.2`.

Build-Metadaten, d. h. alles, was in einer Version auf das Zeichen `+` folgt, werden beim Versionsvergleich ignoriert. z.B. `0.154+26.3` und `0.154+26.2` sind gleich.

Hier sind einige Beispiele für Bereiche und was sie aussagen. Probiere [Outlet's Fabric Loader Verification](https://dexman545.github.io/outlet-database/floaderValidator) aus, um zu testen, welche Werte die Einschränkung erfüllen.

:::details Beispiel für semantische Versionierung

**Hinweis:** Minecraft hält sich nicht an die Regeln der semantischen Versionierung. Wenn nötig, wandelt Fabric eine Minecraft-Version in die entsprechende Version nach der semantischen Versionierung um. Beispiele schließen ein `26.1`->`26.1.0`, `26.1-snapshot-1`->`26.1-alpha.1`, `26w14a`->`26.1.1-alpha.26.14.a`.
Dieser Muster änderte sich mit dem Relase von `1.16-rc1`. Zuvor hat Fabric Vorschauversionen wie `1.16-pre1` auf `1.16-rc.1` normalisiert, was nun zu Konflikten führte. Ab der Loader-Version `0.8.8` wird `1.16-rc1` zu `1.16-rc.9` normalisiert und zukünftige Vorschauversionen werden zu `-beta.n` normalisiert.

| Bereich                    | Beschreibung                                                                                                                                                                                           | Stimmen überein                                                                                                                                               | Nicht kompatibel                                                                                                    |
| -------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------- |
| <Range r="*" />            | Jegliche Version (nicht empfohlen)                                                                                                                                                  | `26.1.2`, `24w14potato`...                                                                                    | _Keine_                                                                                                             |
| <Range r="26.1.2" />       | Nur die genaue Version                                                                                                                                                                                 | `26.1.2`                                                                                                                                                      | `26.1`, `26.1.1`, `26.2`...                                         |
| <Range r=">26" />          | Über einer Version (exklusiv)                                                                                                                                                       | 26.1.2`, `26.2\`...                                           | `26`, `25.x`...                                                     |
| <Range r=">=26.1" />       | Diese Version oder höher (inklusiv)                                                                                                                                                 | `26.1`, `26.1.2`, `26.2`...                                                                                   | `26.0`, `25.x`...                                                   |
| <Range r="<=26.1" />       | Diese Version oder niedriger (inklusiv)                                                                                                                                             | `26.1`, `26.0`, `25.x`...                                                                                     | 26.1.2`, `26.2\`... |
| <Range r=">26 <26.2" />    | Zwischen zwei Versionen (beide exklusiv)                                                                                                                                            | `26.1`, `26.1.2`, Snapshots...                                                                                | `26`, `26.2`...                                                     |
| <Range r=">=26.1 <26.2" /> | Zwischen zwei Versionen (inklusive der unteren Grenze)                                                                                                                              | `26.1`, `26.1.2`, Snapshots...                                                                                | `26.0`, `26.2`...                                                   |
| <Range r="~26.1-rc.2" />   | Gilt für die nächste Minor-Version, ausgenommen frühere Versionen (entspricht `>=26.1-rc.2 <26.2-`). Prüft nur die Komponenten `major` und `minor`. | `26.1-rc.2`, `26.1`, `26.1.2`, Snapshots, ...                                                                 | `26.1-rc.1`, `26.2`, `27.x`...                                      |
| <Range r="^26.2" />        | Gilt für die nächste Major-Version, ausgenommen frühere Versionen (entspricht `>=26.2 <27-`). Prüft nur die Komponente `major`.                     | `26.2`, `26.3`...                                                                                             | `26.1`, `25.x`, `27.x`...                                           |
| <Range r="26.1.x" />       | Entspricht `~26,1-`.                                                                                                                                                                   | 26.1-rc-3`, `26.1`, `26.1.2\`, Snapshots, ... | `26.2`, `27.x`...                                                   |
| <Range r="1.x" />          | Entspricht `^1-`.                                                                                                                                                                      | `1.0.0-beta.4`, `1.0.0`, Snapshots, ...                                                                       | `26.x`                                                                                                              |
| <Range r=">26.2- <26.2" /> | Alle Snapshots von `26.2`, mit Ausnahme von `26.2`                                                                                                                                                     | `26.2-pre-1`, `26.2-rc-1`                                                                                                                                     | `26.2`                                                                                                              |

**Hinweis:** Der Operator `.x` funktioniert nicht, wenn eine Komponente für eine Vorschauversion (z.B. `26.1.x-rc.1`) enthalten ist; stattdessen wird der Bereich als einfache Zeichenfolge behandelt (siehe Andere Versionsformate weiter unten). `.*`, `.x` oder `.X` können austauschbar verwendet werden.
Ab Fabric Loader `0.19.3` tritt ein Fehler auf, bei dem der Operator `.x` bei Versionen mit mehr als drei Komponenten nicht korrekt funktioniert. Dieser wird [in einer zukünftigen Version behoben](https://github.com/FabricMC/fabric-loader/pull/1157).

:::

:::warning Andere Versionsformate

Wenn eine Version nicht dem von Fabric unterstützten erweiterten SemVer entspricht, wird sie als Zeichenkette behandelt und gemäß Java [`String#compareTo`](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html#compareTo-java.lang.String-) lexikografisch zum Sortieren verglichen, z.B. während des Laden von Mods, wenn Fabric entscheiden muss, welche Version geladen werden soll.

In diesem Modus sind die Bereichsoperatoren `<` und `>` nicht erlaubt. Die anderen Bereichsoperatoren verhalten sich wie `=`, mit Ausnahme von `*`, das weiterhin mit jeder Version übereinstimmt.

Siehe dir das [Beispiel für eine Version mit einer einfachen Zeichenkette](https://dexman545.github.io/outlet-database/floaderValidator?p=~alpha&mode=custom&c=alpha&c=beta&c=theta&c=0&c=alpha4&c=1.2.3) an.

**Hinweis:** Es wird empfohlen, nach Möglichkeit das erweiterte SemVer zu verwenden, um den Vergleich von Versionen und Versionsbereichen zu unterstützen!

:::

```json
"depends": {
    "example-mod": "*",
    "minecraft": [
        "26.1",
        "26.1.1"
    ]
}
"suggests": {
    "another-mod": ">1.0.0"
}
```

## Benutzerdefinierte Felder {#custom-fields}

Du kannst jedes beliebige Feld in das Feld `custom` einfügen. Der Loader würde diese ignorieren. Es wird jedoch dringend empfohlen, deinen Feldern einen Namensraum zuzuweisen, um Konflikte mit anderen Mods zu vermeiden.
