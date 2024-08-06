---
title: Logs hochladen
description: Wie man Logs zur Fehlerbehebung hochlädt.
authors:
  - IMB11
---

# Logs hochladen

Wenn man versucht, Fehler zu beheben, ist es oft nötig, Logs bereitzustellen, die beim Identifizieren der Ursache des Fehlers helfen.

## Warum sollte ich Logs hochladen? {#why-should-i-upload-logs}

Das Hochladen von Logs ermöglicht es anderen, dir bei der Fehlersuche schneller zu helfen, als wenn die Logs in den Chat oder Forenbeiträge eingefügt werden. Es ermöglicht dir auch, die Logs mit anderen zu teilen, ohne sie zu Kopieren oder Einfügen zu müssen.

Manche Paste-Seiten stellen Syntaxhervorhebung für Logs bereit, was sie deutlich einfacher zu lesen macht und zensieren sensible Daten, wie Benutzernamen oder Systeminformationen.

## Absturzberichte

Absturzberichte werden automatisch generiert, wenn das Spiel abstürzt. Sie enthalten nur Absturzinformationen, aber nicht die eigentlichen Logs des Spiels. Sie befinden sich im `crash-reports`-Verzeichnis im Spiel-Verzeichnis.

Für weitere Informationen über Absturzberichte, siehe [Absturzberichte](./crash-reports).

## Logs finden

Diese Anleitung behandelt den offiziellen Minecraft-Launcher (oft auch "Vanilla-Launcher" genannt) - für Launcher von Drittanbietern solltest du deren Dokumentation verwenden.

Logs befinden sich im `logs`-Verzeichnis innerhalb des Spiel-Verzeichnisses, das Spiel-Verzeichnis kann sich, abhängig von deinem Betriebssystem, an den folgenden Orten befinden:

::: code-group

```:no-line-numbers [Windows]
%appdata%\.minecraft
```

```:no-line-numbers [macOS]
~/Library/Application Support/minecraft
```

```:no-line-numbers [Linux]
~/.minecraft
```

:::

Das neueste Log hat den Dateinamen `latest.log` und vorherige Logs nutzen das Muster `JJJJ-MM-TT_Nummer.log.gz` zur Benennung.

## Logs hochladen

Logs können bei einer Vielzahl von Diensten hochgeladen werden, zum Beispiel:

- [Pastebin](https://pastebin.com/)
- [GitHub Gist](https://gist.github.com/)
- [mclo.gs](https://mclo.gs/)
