---
title: Instalowanie modów
description: Przewodnik krok po kroku o tym, jak zainstalować mody do Fabric.
authors:
  - IMB11
---

Ten przewodnik pokaże ci, jak zainstalować mody do Fabric dla oficjalnego launchera Minecrafta.

W przypadku innych launcherów należy zapoznać się z ich dokumentacją.

## 1. Pobierz moda {#1-download-the-mod}

:::warning
Mody należy pobierać wyłącznie z zaufanych źródeł. Po więcej informacji sprawdź przewodnik na temat [znajdowania wiarygodnych modów](./finding-mods).
:::

Większość modów wymaga również Fabric API, które można pobrać z [Modrinth](https://modrinth.com/mod/fabric-api) lub [CurseForge](https://curseforge.com/minecraft/mc-mods/fabric-api).

Podczas pobierania modów upewnij się, że:

- Działają na wersji Minecrafta, na której chcesz grać. Dla przykładu mod działający na wersji 1.20 może nie działać na wersji 1.20.2.
- Są przeznaczone dla Fabric, a nie innego mod loadera.
- Są przeznaczone dla właściwej edycji Minecrafta (Java Edition).

## 2. Przenieś moda do folderu `mods` {#2-move-the-mod-to-the-mods-folder}

Folder `mods` znajdziesz w następujących lokalizacjach dla poszczególnych systemów operacyjnych.

Zazwyczaj możesz wkleić te ścieżki do paska adresu swojego eksploratora plików, aby szybko przejść do folderu.

::: code-group

```:no-line-numbers [Windows]
%appdata%\.minecraft\mods
```

```:no-line-numbers [macOS]
~/Library/Application Support/minecraft/mods
```

```:no-line-numbers [Linux]
~/.minecraft/mods
```

:::

Po znalezieniu folderu `mods`, przenieś do niego pliki `.jar` modów.

![Zainstalowane mody w folderze mods](/assets/players/installing-mods.png)

## 3. I to tyle! {#3-you-re-done}

Po przeniesieniu modów do folderu `mods` możesz otworzyć launcher Minecrafta i wybrać z rozwijanej listy w lewym dolnym rogu profil Fabric, a następnie zacząć grać!

![Launcher Minecrafta z wybranym profilem Fabric](/assets/players/installing-fabric/launcher-screen.png)

## Rozwiązywanie problemów {#troubleshooting}

Jeśli napotkasz jakiś problem podczas korzystania z tego przewodnika, możesz poprosić o pomoc na [Discordzie Fabric](https://discord.gg/v6v4pMv) na kanale `#player-support`.

Możesz również spróbować samodzielnie rozwiązać problem, czytając strony poświęcone rozwiązywaniu problemów:

- [Raporty awarii](./troubleshooting/crash-reports)
- [Przesyłanie logów](./troubleshooting/uploading-logs)
