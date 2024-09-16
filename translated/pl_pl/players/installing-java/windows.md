---
title: Instalowanie oprogramowania Java na Windows
description: Przewodnik krok po kroku o tym, jak zainstalować oprogramowanie Java na Windows.
authors:
  - IMB11
---

# Instalowanie oprogramowania Java na Windows {#installing-java-on-windows}

Ten przewodnik pokaże ci, jak zainstalować Javę 21 na systemie Windows.

Launcher Minecrafta zawiera własną instalację Javy, więc ta sekcja jest istotna tylko, jeśli chcesz użyć instalatora Fabric w formacie `.jar` lub jeśli chcesz użyć pliku `.jar` serwera Minecraft.

## 1. Sprawdź, czy nie masz już zainstalowanej Javy {#1-check-if-java-is-already-installed}

Aby sprawdzić, czy masz już zainstalowaną Javę, musisz najpierw otworzyć wiersz poleceń.

Możesz to zrobić poprzez wciśnięcie klawiszy <kbd>Win</kbd> + <kbd>R</kbd> i wpisanie `cmd.exe` w wyświetlonym oknie.

![Okno dialogowe Uruchamiania w systemie Windows z wpisanym tekstem "cmd.exe" w pasku uruchamiania](/assets/players/installing-java/windows-run-dialog.png)

Po otwarciu wiersza poleceń wpisz `java -version` i naciśnij <kbd>Enter</kbd>.

Jeśli polecenie zostanie uruchomione pomyślnie, to zobaczysz coś takiego. Jeśli komenda się nie powiedzie, przejdź do następnego kroku.

![Wiersz poleceń z wpisanym poleceniem "java -version"](/assets/players/installing-java/windows-java-version.png)

:::warning
Aby używać Minecrafta w wersji 1.21, musisz mieć zainstalowaną co najmniej Javę 21. Jeśli to polecenie wyświetla wersję mniejszą niż 21, wymagane będzie zaktualizowanie istniejącej instalacji Javy.
:::

## 2. Pobierz instalator Javy 21 {#2-download-the-java-installer}

Do zainstalowania Javy 21 wymagane jest pobranie instalatora ze strony [Adoptium](https://adoptium.net/en-GB/temurin/releases/?os=windows\&package=jdk\&version=21).

Pobierz wersję `Windows Installer (.msi)`:

![Strona pobierania Adoptium z wyróżnioną wersją Windows Installer (.msi)](/assets/players/installing-java/windows-download-java.png)

Jeżeli masz 32-bitowy system operacyjny wybierz `x86`, natomiast jeśli masz 64-bitowy system wybierz `x64`.

Większość nowoczesnych komputerów posiada 64-bitowy system operacyjny. Jeśli nie masz pewności, spróbuj pobrać wersję 64-bitową.

## 3. Uruchom instalator! {#3-run-the-installer}

Aby zainstalować Javę 21, postępuj zgodnie z instrukcjami w instalatorze. Gdy dotrzesz do tej strony, ustaw następujące funkcje na "Cała funkcja zostanie zainstalowana na lokalnym dysku twardym":

- `Set JAVA_HOME environment variable` - zostanie to dodane do PATH.
- `JavaSoft (Oracle) registry keys`

![Instalator Javy 21 wyróżnionymi polami "Set JAVA\_HOME variable" i "JavaSoft (Oracle) registry keys"](/assets/players/installing-java/windows-wizard-screenshot.png)

Po zrobieniu tego kliknij `Next` i kontynuuj instalację.

## 4. Zweryfikuj czy Java 21 została zainstalowana {#4-verify-that-java-is-installed}

Po zakończeniu instalacji możesz zweryfikować czy Java 21 została zainstalowana, ponownie otwierając wiersz poleceń i wpisując `java -version`.

Jeśli polecenie zostanie uruchomione pomyślnie, to zobaczysz coś takiego, gdzie wyświetlana jest wersja Javy:

![Wiersz poleceń z wpisanym poleceniem "java -version"](/assets/players/installing-java/windows-java-version.png)

---

Jeśli napotkasz jakiś problem, śmiało możesz poprosić o pomoc na [Discordzie Fabric](https://discord.gg/v6v4pMv) na kanale `#player-support`.
