---
title: Instalowanie oprogramowania Fabric na Linux
description: Instrukcja krok po kroku jak zainstalować Fabric na Linux.
authors:
  - Benonardo
  - ezfe
  - IMB11
  - modmuss50
next: false
---

<!---->

:::info WYMAGANIA WSTĘPNE

Może być konieczne, aby [zainstalować Java](../installing-java/linux) przed otworzeniem pliku `.jar`.

:::

<!-- #region common -->

## 1. Pobierz instalator Fabric {#1-download-the-fabric-installer}

Pobierz plik `.jar` instalatora Fabric ze [strony Fabric](https://fabricmc.net/use/), poprzez kliknięcie `Download installer (Universal/.JAR)`.

## 2. Uruchom instalator Fabric {#2-run-the-fabric-installer}

Przed instalacją należy zamknąć Minecrafta, jak i jego launcher.

Otwórz terminal i uruchom instalator, używając Javy:

```sh
java -jar fabric-installer.jar
```

Po otworzeniu instalatora powinno pojawić się takie okno:

![Instalator Fabric z wyróżnionym tekstem „Zainstaluj”](/assets/players/installing-fabric/installer-screen.png)

<!-- #endregion common -->

Wybierz żądaną wersję Minecraft i kliknij przycisk `Instaluj`. Upewnij się, że opcja `Utwórz profil` jest zaznaczona.

## 3. Ukończ konfigurację

Kiedy instalacja się skończy, otwórz launcher Minecraft, a potem wybierz profil Fabric z listy wersji i kliknij `Graj`.

![Launcher Minecraft z wybranym profilem Fabric](/assets/players/installing-fabric/launcher-screen.png)

Teraz jesteś w stanie dodawać mody do swojej gry. Po więcej informacji sprawdź przewodnik na temat [znajdowania wiarygodnych modów](../finding-mods).

Jeśli napotkasz jakikolwiek problem, śmiało możesz poprosić o pomoc na [Discordzie Fabric](https://discord.fabricmc.net/) na kanale `#player-support`.
