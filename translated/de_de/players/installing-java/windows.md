---
title: Java auf Windows installieren
description: Eine Schritt-für-Schritt-Anleitung zur Installation von Java auf Windows.
authors:
  - IMB11
---

# Java auf Windows installieren

Diese Anleitung führt Sie durch die Installation von Java 21 auf Windows.

Der Minecraft Launcher kommt bereits mit seiner eigenen Java Installation, diese Sektion ist also nur relevant, wenn Sie den Fabric `.jar` basierten Installer verwenden möchten oder wenn Sie die Minecraft Server `.jar` verwenden möchten.

## 1. Überprüfen, ob Java bereits installiert ist

Um zu überprüfen, ob Java bereits installiert ist, öffnen Sie die Kommandozeile.

Drücken Sie <kbd>Win</kbd> + <kbd>R</kbd> und geben Sie `cmd.exe` in das Feld ein.

![Windows-Ausführungsdialog mit "cmd.exe" in der Ausführungsleiste](/assets/players/installing-java/windows-run-dialog.png)

Wenn Sie die Kommandozeile geöffnet haben, geben Sie `java -version` ein und drücken <kbd>Enter</kbd>.

Wenn der Befehl erfolgreich ausgeführt wird, sollten Sie folgendes sehen. Wenn der Befehl fehlgeschlagen ist, fahren Sie mit dem nächsten Schritt fort.

![Kommandozeile mit "java -version"](/assets/players/installing-java/windows-java-version.png)

:::warning
Um Minecraft 1.21 zu nutzen, muss mindestens Java 21 installiert sein. Wenn der Befehl eine Version niedriger als 21 anzeigt, musst du deine bestehende Java-Installation aktualisieren.
:::

## 2. Herunterladen des Java 21 Installer {#2-download-the-java-installer}

Um Java 21 zu installieren, musst du das Installationsprogramm von [Adoptium](https://adoptium.net/en-GB/temurin/releases/?os=windows\&package=jdk\&version=21) herunterladen.

Sie müssen die Version "Windows Installer (.msi)" herunterladen:

![Adoptium Download-Seite mit hervorgehobenem Windows Installer (.msi)](/assets/players/installing-java/windows-download-java.png)

Sie sollten `x86` wählen, wenn Sie ein 32-Bit-Betriebssystem haben, oder `x64`, wenn Sie ein 64-Bit-Betriebssystem haben.

Die meisten modernen Computer sind mit einem 64-Bit-Betriebssystem ausgestattet. Wenn Sie sich unsicher sind, versuchen Sie es mit dem 64-Bit-Download.

## 3. Installer starten! {#3-run-the-installer}

Folge den Schritten des Installationsprogramms, um Java 21 zu installieren. Wenn Sie diese Seite erreichen, sollten Sie die folgenden Funktionen auf "Die gesamte Funktion wird auf der lokalen Festplatte installiert" einstellen:

- `JAVA_HOME Umgebungsvariable setzen` - Diese wird zu Ihrem PATH hinzugefügt.
- JavaSoft (Oracle)-Registrierungsschlüssel

![Java 21-Installationsprogramm mit "JAVA\_HOME-Variable setzen" und "JavaSoft (Oracle) Registrierungsschlüssel" hervorgehoben](/assets/players/installing-java/windows-wizard-screenshot.png)

Wenn Sie das getan haben, können Sie `Weiter` klicken und mit der Installation fortfahren.

## 4. Verifiziere, dass Java 21 installiert ist {#4-verify-that-java-is-installed}

Sobald die Installation abgeschlossen ist, kannst du überprüfen, ob Java 21 installiert ist, indem du die Kommandozeile erneut öffnest und `java -version` eingibst.

Wenn der Befehl erfolgreich ausgeführt wird, wird die Java-Version wie zuvor gezeigt angezeigt:

![Kommandozeile mit "java -version"](/assets/players/installing-java/windows-java-version.png)

---

Sollten Sie auf Probleme stoßen, können Sie im [Fabric Discord](https://discord.gg/v6v4pMv) im Channel `#player-support` um Hilfe bitten.
