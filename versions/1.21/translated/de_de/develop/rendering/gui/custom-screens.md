---
title: Benutzerdefinierte Oberflächen
description: Lerne, wie man benutzerdefinierte Oberflächen für deinen Mod erstellt.
authors:
  - IMB11
---

# Benutzerdefinierte Oberflächen {#custom-screens}

:::info
Diese Seite bezieht sich auf normale Oberflächen, nicht auf solche, die vom Spieler auf dem Client geöffnet werden, und nicht auf solche, die vom Server bearbeitet werden.
:::

Oberflächen sind im Wesentlichen die grafischen Oberflächen, mit denen der Spieler interagiert, zum Beispiel der Titelbildschirm, der Pausenbildschirm usw.

Du kannst deine eigenen Oberflächen erstellen, um benutzerdefinierte Inhalte, ein benutzerdefiniertes Einstellungsmenü und vieles mehr anzuzeigen.

## Eine Oberfläche erstellen {#creating-a-screen}

Um eine Oberfläche zu erstellen, musst du die `Screen`-Klasse erweitern und die `init`-Methode überschreiben.

Folgendes solltest du beachten:

- Widgets werden nicht im Konstruktor erstellt, weil die Oberfläche zu diesem Zeitpunkt noch nicht initialisiert ist - und bestimmte Variablen, wie `width` (Breite) und `height` (Höhe), sind noch nicht verfügbar oder noch nicht genau.
- Die `init`-Methode wird aufgerufen, wenn die Oberfläche initialisiert wird, und sie ist der beste Ort, um Widgets zu erstellen.
  - Du kannst Widgets mit der Methode `addDrawableChild` hinzufügen, die jedes zeichenbare Widget akzeptiert.
- Die Methode `render` wird bei jedem Frame aufgerufen - du kannst von dieser Methode aus auf den Zeichenkontext und die Mausposition zugreifen.

Als Beispiel können wir eine einfache Oberfläche erstellen, der eine Schaltfläche und eine Beschriftung darüber enthält.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomScreen.java)

![Benutzerdefinierte Oberfläche 1](/assets/develop/rendering/gui/custom-1-example.png)

## Die Oberfläche öffnen {#opening-the-screen}

Du kannst die Oberfläche mit der `setScreen`-Methode des `MinecraftClient` öffnen - du kannst dies von vielen Stellen aus tun, wie zum Beispiel einer Tastenbindung, einem Befehl oder einem Client-Paket-Handler.

```java
MinecraftClient.getInstance().setScreen(
  new CustomScreen(Text.empty())
);
```

## Die Oberfläche schließen {#closing-the-screen}

Wenn du eine Oberfläche schließen möchtest, setze die Oberfläche einfach auf `null`:

```java
MinecraftClient.getInstance().setScreen(null);
```

Wenn du ausgefallen sein und zum vorherigen Bildschirm zurückkehren willst, kannst du die aktuelle Oberfläche an den `CustomScreen`-Konstruktor übergeben und ihn in einem Attribut speichern und ihn dann verwenden, um zum vorherigen Bildschirm zurückzukehren, wenn die Methode `close` aufgerufen wird.

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/screens/CustomScreen.java)

Jetzt kannst du beim Öffnen der benutzerdefinierten Oberfläche den aktuellen Bildschirm als zweites Argument übergeben - wenn du also `CustomScreen#close` aufrufst, wird er zur vorherigen Oberfläche zurückkehren.

```java
Screen currentScreen = MinecraftClient.getInstance().currentScreen;
MinecraftClient.getInstance().setScreen(
  new CustomScreen(Text.empty(), currentScreen)
);
```
