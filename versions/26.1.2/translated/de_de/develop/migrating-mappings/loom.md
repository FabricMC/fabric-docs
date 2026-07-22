---
title: Mappings migrieren unter Verwendung von Loom
description: Lerne, wie du die verschleierten Mappings deines Mods unter Verwendung von Fabric Loom migrierst.
authors:
  - asiekierka
  - cassiancc
  - florensie
  - Friendly-Banana
  - IMB11
  - jamierocks
  - JamiesWhiteShirt
  - MildestToucan
  - modmuss50
  - natanfudge
  - Spinoscythe
  - UpcraftLP
authors-nogithub:
  - basil4088
---

<!---->

::: warning

Für beste Ergebnisse wird empfohlen, auf Loom 1.13 oder höher zu aktualisieren, da dies die Migration von Mixins, Access Wideners und Client-Quellensätzen ermöglicht.

:::

## Zu Mojang Mappings migrieren {#migrating-to-mojmap}

Zunächst musst du den Befehl `migrateMappings` ausführen, um deine aktuellen Mappings zu Mojang Mappings zu migrieren. Der folgende Befehl würde beispielsweise zu Mojang Mappings für 1.21.11 migrieren:

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat migrateMappings --mappings "net.minecraft:mappings:1.21.11"
```

```sh:no-line-numbers [macOS/Linux]
./gradlew migrateMappings --mappings "net.minecraft:mappings:1.21.11"
```

```sh:no-line-numbers [IntelliJ]
migrateMappings --mappings "net.minecraft:mappings:1.21.11"
```

:::

Du kannst `1.21.11` durch die Version von Minecraft ersetzen, von der du migrierst. Dies muss dieselbe Version von Minecraft sein, die du derzeit ausführst. **Ändere deine `gradle.properties` oder `build.gradle` noch nicht!**

### Deine Quellen bearbeiten {#editing-sources-mojmap}

Standardmäßig wird der migrierte Quellcode in `remappedSrc` auftauchen, anstatt dein bestehendes Projekt zu überschreiben. Du musst die Quellen aus `remappedSrc` in den ursprünglichen Ordner kopieren. Sichere zur Sicherheit die Originalquellen.

Wenn du Loom 1.13 oder höher verwendest, kannst du das Programmargument `--overrideInputsIHaveABackup` verwenden, um deine Quellen direkt zu ersetzen.

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat migrateMappings --mappings "net.minecraft:mappings:1.21.11" --overrideInputsIHaveABackup
```

```sh:no-line-numbers [macOS/Linux]
./gradlew migrateMappings --mappings "net.minecraft:mappings:1.21.11" --overrideInputsIHaveABackup
```

```sh:no-line-numbers [IntelliJ]
migrateMappings --mappings "net.minecraft:mappings:1.21.11" --overrideInputsIHaveABackup
```

:::

### Gradle aktualisieren {#updating-gradle-mojmap}

Wenn du von Yarn kommst, kannst du nun deine Zuordnungen im Dependencies-Abschnitt deiner `build.gradle` durch Mojang-Mappings ersetzen.

```groovy
dependencies {
    [...]
    mappings "net.fabricmc:yarn:${project.yarn_mappings}:v2" // [!code --]
    mappings loom.officialMojangMappings() // [!code ++]
}
```

Du kannst nun das Gradle-Projekt in deiner IDE aktualisieren, um deine Änderungen zu übernehmen.

### Finale Änderungen {#final-changes-mojmap}

Der Großteil der Arbeit ist geschafft! Du solltest nun deinen Quellcode durchgehen, um nach möglicherweise veralteten Mixin-Zielen oder Code zu suchen, der nicht remapped wurde.

Tools wie [mappings.dev](https://mappings.dev/) oder [Linkie](https://linkie.shedaniel.dev/mappings?namespace=yarn&translateMode=ns&translateAs=mojang_raw&search=) sind hilfreich, um sich mit deinen neuen Mappings vertraut zu machen.

## Zu Yarn migrieren {#migrating-to-yarn}

::: warning

1.21.11 ist die letzte Version, in der Yarn Mappings verfügbar sein werden. Wenn du planst, deinen Mod auf 26.1 oder höher zu aktualisieren, solltest dein Mod auf Mojangs Mappings sein.

:::

Zuerst musst du den Befehl `migrateMappings` ausführen, der deine aktuellen Mappings in Yarn-Mappings konvertiert. Dies kann auf [der Develop-Website](https://fabricmc.net/develop) unter Mappings Migration gefunden werden. Zum Beispiel:

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat migrateMappings --mappings "1.21.11+build.3"
```

```sh:no-line-numbers [macOS/Linux]
./gradlew migrateMappings --mappings "1.21.11+build.3"
```

```sh:no-line-numbers [IntelliJ]
migrateMappings --mappings "1.21.11+build.3"
```

:::

Du kannst `1.21.11` durch die Version von Minecraft ersetzen, von der du migrierst. Dies muss dieselbe Version von Minecraft sein, die du derzeit ausführst. **Ändere deine `gradle.properties` oder `build.gradle` noch nicht!**

### Deine Quellen bearbeiten {#editing-sources-yarn}

Standardmäßig wird der migrierte Quellcode in `remappedSrc` auftauchen, anstatt dein bestehendes Projekt zu überschreiben. Du musst die Quellen aus `remappedSrc` in den ursprünglichen Ordner kopieren. Sichere zur Sicherheit die Originalquellen.

Wenn du Loom 1.13 oder höher verwendest, kannst du das Programmargument `--overrideInputsIHaveABackup` verwenden, um deine Quellen direkt zu ersetzen.

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat migrateMappings --mappings "1.21.11+build.3" --overrideInputsIHaveABackup
```

```sh:no-line-numbers [macOS/Linux]
./gradlew migrateMappings --mappings "1.21.11+build.3" --overrideInputsIHaveABackup
```

```sh:no-line-numbers [IntelliJ]
migrateMappings --mappings "1.21.11+build.3" --overrideInputsIHaveABackup
```

:::

### Gradle aktualisieren {#updating-gradle-yarn}

Wenn du von Mojang Mappings migrierst, kannst du nun deine Mappings im Abschnitt `dependencies` in deiner `build.gradle` durch Yarn Mappings ersetzen. Stelle sicher, dass du auch deine Datei `gradle.properties` mit der auf [der Develop-Website](https://fabricmc.net/develop) angegebenen Yarn-Version aktualisierst.

**`gradle.properties`**

```properties
yarn_mappings=1.21.11+build.3
```

**`build.gradle`**

```groovy
dependencies {
    [...]
    mappings loom.officialMojangMappings() // [!code --]
    mappings "net.fabricmc:yarn:${project.yarn_mappings}:v2" // [!code ++]
}
```

Du kannst nun das Gradle-Projekt in deiner IDE aktualisieren, um deine Änderungen zu übernehmen.

### Finale Änderungen {#final-changes-yarn}

Der Großteil der Arbeit ist geschafft! Du solltest nun deinen Quellcode durchgehen, um nach möglicherweise veralteten Mixin-Zielen oder Code zu suchen, der nicht remapped wurde.

Tools wie [mappings.dev](https://mappings.dev/) oder [Linkie](https://linkie.shedaniel.dev/mappings?namespace=mojang_raw&translateMode=ns&translateAs=yarn&search=) sind hilfreich, um sich mit deinen neuen Mappings vertraut zu machen.

## Zusätzliche Konfigurationen {#additional-configurations}

### Geteilte Quellen migrieren {#migrating-split-sources}

Loom 1.13 fügt eine neue Aufgabe namens `migrateClientMappings` hinzu, mit der du deine Zugriffserweiterungen auf deine neuen Mappings migrieren kannst. Zum Beispiel, um zu Mojang Mappings zu migrieren:

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat migrateClientMappings --mappings "net.minecraft:mappings:1.21.11"
```

```sh:no-line-numbers [macOS/Linux]
./gradlew migrateClientMappings --mappings "net.minecraft:mappings:1.21.11"
```

```sh:no-line-numbers [IntelliJ]
migrateClientMappings --mappings "net.minecraft:mappings:1.21.11"
```

:::

Wenn du eine ältere Version von Loom verwendest, lies bitte [andere Konfigurationen](#other-configurations).

### Zugriffserweiterungen migrieren {#migrating-access-wideners}

Loom 1.13 fügt eine neue Aufgabe namens `migrateClassTweakerMappings` hinzu, mit der du deine Zugriffserweiterungen auf deine neuen Mappings migrieren kannst. Zum Beispiel, um zu Mojang Mappings zu migrieren:

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat migrateClassTweakerMappings --mappings "net.minecraft:mappings:1.21.11"
```

```sh:no-line-numbers [macOS/Linux]
./gradlew migrateClassTweakerMappings --mappings "net.minecraft:mappings:1.21.11"
```

```sh:no-line-numbers [IntelliJ]
migrateClassTweakerMappings --mappings "net.minecraft:mappings:1.21.11"
```

:::

### Andere Konfigurationen {#other-configurations}

- Gib mit `--input path/to/source` an, woher deine Java-Quelldateien genommen werden sollen. Standard: `src/main/java`. Du kannst dies verwenden, um ein Client-Quellensatz zu migrieren, indem du `--input src/client/java` übergibst.
- Gib mit `--output path/to/output` an, wo die neu zugeordnete Quelle ausgegeben werden soll. Standard: `remappedSrc`. Du kannst hier `src/main/java` verwenden, um vorhandene Quellen zu überschreiben, aber stelle sicher, dass du ein Backup hast.
- Gib mit `--mappings some_group:some_artifact:some_version:some_qualifier` einen benutzerdefinierten Speicherort an, aus dem die Mappings abgerufen werden sollen. Standard: `net.fabricmc:yarn:<version-you-inputted>:v2`. Verwende `net.minecraft:mappings:<minecraft-version>`, um zu den offiziellen Mojang-Mappings zu migrieren.

Zum Beispiel, um einen Client-Quellensatz direkt in Mojang Mappings zu migrieren (wobei die vorhandenen Quellen überschrieben werden):

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat migrateMappings --input "src/client/java" --output "src/client/java" --mappings "net.minecraft:mappings:1.21.11"
```

```sh:no-line-numbers [macOS/Linux]
./gradlew migrateMappings --input "src/client/java" --output "src/client/java" --mappings "net.minecraft:mappings:1.21.11"
```

```sh:no-line-numbers [IntelliJ]
migrateMappings --input "src/client/java" --output "src/client/java" --mappings "net.minecraft:mappings:1.21.11"
```

:::

<!---->
