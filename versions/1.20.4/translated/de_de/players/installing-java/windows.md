---
title: Java auf Windows installieren
description: Eine Schritt-für-Schritt-Anleitung zur Installation von Java auf Windows.
authors:
  - IMB11

search: false
---

# Java auf Windows installieren

Diese Anleitung führt Sie durch die Installation von Java 17 auf Windows.

Der Minecraft Launcher kommt bereits mit seiner eigenen Java Installation, diese Sektion ist also nur relevant, wenn Sie den Fabric `.jar` basierten Installer verwenden möchten oder wenn Sie die Minecraft Server `.jar` verwenden möchten.

## 1. Überprüfen, ob Java bereits installiert ist

Um zu überprüfen, ob Java bereits installiert ist, öffnen Sie die Kommandozeile.

Drücken Sie <kbd>Win</kbd> + <kbd>R</kbd> und geben Sie `cmd.exe` in das Feld ein.

![Windows-Ausführungsdialog mit "cmd.exe" in der Ausführungsleiste](/assets/players/installing-java/windows-run-dialog.png)

Wenn Sie die Kommandozeile geöffnet haben, geben Sie `java -version` ein und drücken <kbd>Enter</kbd>.

Wenn der Befehl erfolgreich ausgeführt wird, sollten Sie folgendes sehen. Wenn der Befehl fehlgeschlagen ist, fahren Sie mit dem nächsten Schritt fort.

![Kommandozeile mit "java -version"](/assets/players/installing-java/windows-java-version.png)

:::warning
Um den Großteil der modernen Minecraft-Versionen nutzen zu können, musst du Java 17 installiert haben. Wenn der Befehl eine Version niedriger als 17 anzeigt, musst du deine bestehende Java-Installation aktualisieren.
:::

## 2. Herunterladen des Java 17 Installer

Um Java 17 zu installieren, laden Sie bitte den Installer von [Adoptium](https://adoptium.net/en-GB/temurin/releases/?os=windows&package=jdk&version=17) herunter.

Sie müssen die Version "Windows Installer (.msi)" herunterladen:

![Adoptium Download-Seite mit hervorgehobenem Windows Installer (.msi)](/assets/players/installing-java/windows-download-java.png)

Sie sollten `x86` wählen, wenn Sie ein 32-Bit-Betriebssystem haben, oder `x64`, wenn Sie ein 64-Bit-Betriebssystem haben.

Die meisten modernen Computer sind mit einem 64-Bit-Betriebssystem ausgestattet. Wenn Sie sich unsicher sind, versuchen Sie es mit dem 64-Bit-Download.

## 3. Installer starten!

Folgen Sie den Schritten des Installationsprogramms, um Java 17 zu installieren. Wenn Sie diese Seite erreichen, sollten Sie die folgenden Funktionen auf "Die gesamte Funktion wird auf der lokalen Festplatte installiert" einstellen:

- `JAVA_HOME Umgebungsvariable setzen` - Diese wird zu Ihrem PATH hinzugefügt.
- JavaSoft (Oracle)-Registrierungsschlüssel

![Java 17-Installationsprogramm mit "JAVA_HOME-Variable setzen" und "JavaSoft (Oracle) Registrierungsschlüssel" hervorgehoben](/assets/players/installing-java/windows-wizard-screenshot.png)

Wenn Sie das getan haben, können Sie `Weiter` klicken und mit der Installation fortfahren.

## 4. Verifizieren, dass Java 17 installiert ist

Sobald die Installation abgeschlossen ist, können Sie überprüfen, ob Java 17 installiert ist, indem Sie die Kommandozeile erneut öffnen und "java -version" eingeben.

Wenn der Befehl erfolgreich ausgeführt wird, wird die Java-Version wie zuvor gezeigt angezeigt:

![Kommandozeile mit "java -version"](/assets/players/installing-java/windows-java-version.png)

---

Sollten Sie auf Probleme stoßen, können Sie im [Fabric Discord](https://discord.gg/v6v4pMv) im Channel `#player-support` um Hilfe bitten.
