---
title: Statistiken
description: Lerne, wie man benutzerdefinierte Spielerstatistiken erstellt und verwendet.
authors:
  - bemoty
  - cassiancc
  - CelDaemon
  - its-miroma
  - jummit
  - Tenneb22
---

Statistiken (oder Stats) dienen dazu, bestimmte Aktionen oder die Zeit, die der Spieler in der Welt verbringt, zu erfassen. Vanilla erfasst Statistiken zu gängigen Aktionen wie Springen, Fahren mit Booten, Interaktionen mit Blöcken, der Verwendung von Items und vielem mehr. Es ist auch möglich deine eigenen Staistiken hinzuzufügen, um benutzerdefinierte Interaktionen zu erfassen.

## EIne Statistik erstellen {#creating-a-statistic}

Um eine benutzerdefinierte Statistik hinzuzufügen, erstelle einen `Identifier`, der zur Registrierung und zum Erhöhen der Statistik verwendet wird:

<<< @/reference/latest/src/main/java/com/example/docs/stats/ModStats.java#stat

Dann registriere die Statistik:

<<< @/reference/latest/src/main/java/com/example/docs/stats/ModStats.java#register

Wenn du die Statistik über `Stats.CUSTOM.get()` zum Statistikbildschirm hinzufügst, kannst du auch den Statistikformatierer festlegen, der bestimmt, wie die Zahl in der Statistikliste angezeigt wird. Vanilla bietet die folgenden Formatierer:

- `DEFAULT`: Zeigt die Zahl direkt an.
- `DIVIDE_BY_TEN`: Zeigt die Zahl als Dezimalzahl, geteilt durch zehn, an.
- `DISTANCE`: Zeigt die Zahl als Distanz an: Basierend auf der Größe der Zahl, wird dies in Zentimeter, Meter oder Kilometer angezeigt werden.
- `TIME`: Zeigt die Zahl als Zeit an. Basierend auf der Größe der Zahl, wird dies als Sekunden, Minuten, Stunden oder Tage angezeigt werden.

Vergiss nicht die Klasse `ModStats` in deinem [Mod-Initialisierer](./getting-started/project-structure#entrypoints) zu initialisieren:

<<< @/reference/latest/src/main/java/com/example/docs/stats/ModStats.java#initialize

<<< @/reference/latest/src/main/java/com/example/docs/stats/ExampleModStats.java#initialize

## Verwenden der Statistik {#using-the-statistic}

In diesem Beispiel erstellen wir einen Freundeblock, der sich mit seinen Nachbarn anfreundet. Wir werden verfolgen, wie viele Freundschaften der Spieler mit dem Block geschlossen hat.

Um dies zu tun, werden wir die Methode `Player#awardStat(stat, amount)` verwenden, um den Wert um die Anzahl der Nachbarn zu erhöhen, die der Block beim Platzieren hat:

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/FriendsBlock.java#friends_block

Du kannst auch `Player#awardStat(stat)` verwenden, um die Statistik um 1 zu erhöhen.

Da die Freundeblöcke sehr eng miteinander verbunden sind, bedeutet das Zerstören eines einzigen Blocks das Ende aller Freundschaften. Lasst uns dafür sorgen, dass das Zerstören eines Freundeblocks die Statistik des Spielers auf 0 zurücksetzt, indem wir `Player#resetStat()` verwenden:

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/FriendsBlock.java#break_friendships
