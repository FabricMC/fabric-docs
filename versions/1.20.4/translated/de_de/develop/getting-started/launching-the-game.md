---
title: Starten des Spiels
description: Lerne, wie du die verschiedenen Startprofile verwendest, um deine Mods in einer Live-Spielumgebung zu starten und zu debuggen.
authors:
  - IMB11

search: false
---

# Starten des Spiels

Fabric Loom bietet eine Vielzahl von Startprofilen, die dir helfen, deine Mods in einer Live-Spielumgebung zu starten und zu debuggen. Dieser Leitfaden behandelt die verschiedenen Startprofile und wie man sie zum Debuggen und Testen der Mods verwendet.

## Startprofile

Wenn du IntelliJ IDEA verwendest, findest du die Startprofile in der oberen rechten Ecke des Fensters. Klicke auf das Dropdown-Menü, um die verfügbaren Startprofile anzuzeigen.

Es sollte ein Client- und ein Serverprofil geben, mit der Möglichkeit, es entweder normal oder im Debug-Modus auszuführen:

![Startprofile](/assets/develop/getting-started/launch-profiles.png)

## Gradle Aufgaben

Wenn du die Kommandozeile verwendest, kannst du die folgenden Gradle-Befehle verwenden, um das Spiel zu starten:

- `./gradlew runClient` - Startet das Spiel im Client-Modus.
- `./gradlew runServer` - Startet das Spiel im Server-Modus.

Das einzige Problem bei diesem Ansatz ist, dass Sie Ihren Code nicht einfach debuggen können. Wenn du deinen Code debuggen willst, musst du die Startprofile in IntelliJ IDEA oder über die Gradle-Integration deiner IDE verwenden.

## Hotswapping von Klassen

Wenn du das Spiel im Debug-Modus ausführst, kannst du deine Klassen per Hotswap austauschen, ohne das Spiel neu zu starten. Dies ist nützlich, um Änderungen an deinem Code schnell zu testen.

Du bist aber immer noch ziemlich eingeschränkt:

- Du kannst keine Methoden hinzufügen oder entfernen
- Du kannst die Methodenparameter nicht ändern
- Du kannst keine Attribute hinzufügen oder entfernen

## Hotswapping von Mixins

Wenn du Mixins verwendest, kannst du deine Mixin-Klassen per Hotswap austauschen, ohne das Spiel neu zu starten. Dies ist nützlich, um Änderungen an deinen Mixins schnell zu testen.

Hierzu musst du allerdings den Mixin-Java-Agent installieren, damit das funktioniert.

### 1. Finde die JAR der Mixin Bibliothek

In IntelliJ IDEA findest du die Mixin-Bibliothek JAR im Abschnitt "External Libraries" des Abschnitts "Project":

![Mixin Bibliothek](/assets/develop/getting-started/mixin-library.png)

Für den nächsten Schritt musst du den "Absolute Path" (Absoluten Pfad) der JAR-Datei kopieren.

### 2. Füge das VM-Argument `-javaagent` hinzu

Füge in deiner "Minecraft Client"- und/oder "Minecraft Server"-Ausführungskonfiguration Folgendes zur Option VM-Argumente hinzu:

```:no-line-numbers
-javaagent:"Pfad zur Mixin-Bibliothek JAR hier"
```

![Bildschirmfoto der VM-Argumente](/assets/develop/getting-started/vm-arguments.png)

Jetzt solltest du in der Lage sein, den Inhalt deiner Mixin-Methoden während des Debuggens zu ändern und die Änderungen wirksam werden zu lassen, ohne das Spiel neu zu starten.
