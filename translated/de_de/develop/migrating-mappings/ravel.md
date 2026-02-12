---
title: Mappings migrieren unter Verwendung von Ravel
description: Lerne, wie du die verschleierten Mappings deines Mods unter Verwendung von Ravel migrierst.
authors:
  - cassiancc
  - deirn
---

[Ravel](https://github.com/badasintended/ravel) ist ein Plugin für IntelliJ IDEA, um Quelldateien zu Remappen, basierend auf [IntelliJ's PSI](https://plugins.jetbrains.com/docs/intellij/psi.html) und [Mapping-IO](https://github.com/FabricMC/mapping-io). Es unterstützt remapping von Java, Kotlin, Mixins (geschrieben in Java), Class Tweakers und Access Wideners.

Installiere es über den [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/28938-ravel-remapper) oder lade die ZIP-Datei von [GitHub Releases](https://github.com/badasintended/ravel/releases) herunter und installiere es, indem du auf das Zahnrad-Icon in den Plugin-Einstellungen klickst und dann auf **Plugin von Datenträger installieren** klickst.

![IDEA Plugin von Datenträger installieren](/assets/develop/misc/migrating-mappings/idea_local_plugin.png)

## Mappings migrieren {#migrating-mappings}

::: warning

Committe alle Änderungen, bevor du versuchst, deine Quellen zu remappen! **Ändere deine `gradle.properties` oder `build.gradle` noch nicht!**

:::

Klicke zunächst mit der rechten Maustaste auf eine im Editor geöffnete Datei und wähle **Refactor** > **Remap Using Ravel**

![Rechtsklick-Menü](/assets/develop/misc/migrating-mappings/ravel_right_click.png)

Es wird sich ein Dialog, wie dieser, öffnen. Du kannst den Dialog auch öffnen, indem du im oberen Menü auf **Refactor** klickst.

![Ravel Dialog](/assets/develop/misc/migrating-mappings/ravel_dialog.png)

Füge anschließend die Mappings hinzu, indem du auf das +-Icon klickst. Klicke auf die Download-Option, wenn du diese noch nicht hast.

::: info

Wenn du die Download Schaltfläche nicht siehst, aktualisiere Ravel auf Version 0.3 oder höher.

:::

- Für die Migration von Yarn zu Mojang Mappings füge zunächst die Yarn `mappings.tiny` hinzu, wähle `named` als **Quell**-Namensraum und `official` als **Ziel**-Namensraum. Füge dann die Mojang `client.txt` hinzu und wähle `target` als **Quell**-Namensraum und `source` als **Ziel**-Namensraum.
- Für die Migration von Mojang Mappings zu Yarn füge zunächst die Mojang `client.txt` hinzu, wobei du diesmal `source` als **Quelle** und `target` als **Ziel** auswählst. Füge dann die Yarn `mappings.tiny` hinzu und wähle `official` als **Quelle** und `named` als **Ziel**.

Wähle dann die Module aus, die du remappen möchtest, indem du auf das Icon + oder das Icon links davon klickst, um alle Module hinzuzufügen.

Klicke anschließend auf **OK** und warte, bis das Remapping abgeschlossen ist.

### Gradle aktualisieren {#updating-gradle}

Nachdem das Remapping abgeschlossen ist, ersetze deine Mappings in der Datei `build.gradle` deines Mods.

```groovy
dependencies {
    mappings "net.fabricmc:yarn:${project.yarn_mappings}:v2" // [!code --]
    mappings loom.officialMojangMappings() // [!code ++]
    // Or the reverse if you're migrating from Mojang Mappings to Yarn
}
```

Aktualisiere auch deine `gradle.properties`, entferne die Option `yarn_mappings` oder aktualisiere sie entsprechend der von dir verwendeten Option.

```properties
yarn_mappings=1.21.11+build.3 # [!code --]
```

### Finale Änderungen {#final-changes}

Der Großteil der Arbeit ist geschafft! Du solltest nun deinen Quellcode durchgehen, um nach möglicherweise veralteten Mixin-Zielen oder Code zu suchen, der nicht remapped wurde.

Bei Problemen, die von Ravel erkannt werden, kannst du (<kbd>Ctrl</kbd>+<kbd>Shift</kbd>+<kbd>F</kbd>) nach `TODO(Ravel)` suchen.

![Ravel TODO Suche](/assets/develop/misc/migrating-mappings/ravel_todo.png)

Tools wie [mappings.dev](https://mappings.dev/) oder [Linkie](https://linkie.shedaniel.dev/mappings?namespace=yarn&translateMode=ns&translateAs=mojang_raw&search=&version=1.21.11) sind hilfreich, um sich mit deinen neuen Mappings vertraut zu machen.
