---
title: Events
description: Ein Leitfaden für die Nutzung von Events, welche von der Fabric API bereitgestellt werden.
authors:
  - dicedpixels
  - mkpoli
  - daomephsta
  - solidblock
  - daomephsta
  - jamieswhiteshirt
  - PhoenixVX
  - Juuxel
  - YanisBft
  - liach
  - natanfudge
authors-nogithub:
  - stormyfabric

search: false
---

# Events

Die Fabric API bietet ein System, das es Mods erlaubt, auf Aktionen oder Ereignisse zu reagieren, die auch als _Events_ im Spiel definiert sind.

Events sind Hooks, die gemeinsame Anwendungsfälle erfüllen und/oder die Kompatibilität und Leistung zwischen Mods verbessern, die sich in dieselben Bereiche des Codes einklinken. Die Verwendung von Ereignissen ersetzt oft die Verwendung von Mixins.

Die Fabric API stellt Ereignisse für wichtige Bereiche der Minecraft-Codebasis bereit, an denen mehrere Modder interessiert sein könnten.

Ereignisse werden durch Instanzen von `net.fabricmc.fabric.api.event.Event` dargestellt, die _Callbacks_ speichern und aufrufen. Oft gibt es eine einzige Event-Instanz für einen Callback, die in einem statischen Attribut `EVENT` des Callback-Interfaces gespeichert wird, aber es gibt auch andere Muster. Zum Beispiel fasst `ClientTickEvents` mehrere zusammenhängende Ereignisse zusammen.

## Callbacks

Callbacks sind ein Teil des Codes, der als Argument an ein Event übergeben wird. Wenn das Event vom Spiel ausgelöst wird, wird der übergebene Teil des Codes ausgeführt.

### Callback Interfaces

Jedes Ereignis hat ein entsprechendes Callback-Interface, das üblicherweise `<EventName>Callback` genannt wird. Callbacks werden durch den Aufruf der Methode `register()` für eine Event-Instanz registriert, wobei eine Instanz des Callbacks als Argument angegeben wird.

Alle Event-Callback-Interfaces, die von der Fabric API bereitgestellt werden, sind im Paket `net.fabricmc.fabric.api.event` zu finden.

## Auf Events hören

### Ein einfaches Beispiel

Dieses Beispiel registriert einen `AttackBlockCallback`, um dem Spieler Schaden zuzufügen, wenn er Blöcke trifft, die keinen Gegenstand fallen lassen, wenn sie von Hand abgebaut werden.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/event/FabricDocsReferenceEvents.java)

### Items zu existierenden Beutetabellen hinzufügen

Manchmal willst du vielleicht Gegenstände zu Beutetabellen hinzufügen. Zum Beispiel, indem du deine Drops zu einem Vanille-Block oder einer Entität hinzufügst.

Die einfachste Lösung, das Ersetzen der Beutetabellen-Datei, kann andere Mods zerstören. Was ist, wenn sie diese auch ändern wollen? Wir sehen uns an, wie du Gegenstände zu Beutetabellen hinzufügen kannst, ohne die Tabelle zu überschreiben.

Wir werden die Beutetabelle für Kohleerz um Eier erweitern.

#### Auf das Laden der Beutetabelle hören

Die Fabric API hat ein Event, das ausgelöst wird, wenn Beutetabellen geladen werden, `LootTableEvents.MODIFY`. Du kannst einen Callback dafür in deinem Mod-Initialisierer registrieren. Überprüfen wir auch, ob die aktuelle Beutetabelle die Beutetabelle für Kohleerz ist.

@[code lang=java transclude={38-40}](@/reference/latest/src/main/java/com/example/docs/event/FabricDocsReferenceEvents.java)

#### Hinzufügen von Items zur Beutetabelle

In Beutetabellen werden Gegenstände in _Beutepool-Einträgen_ gespeichert, und Einträge werden in _Beutepools_ gespeichert. Um einen Gegenstand hinzuzufügen, müssen wir der Beutetabelle einen Pool mit einem Eintrag für ein Item hinzufügen.

Wir können einen Pool mit `LootPool#builder` erstellen, und ihn zur Beutetabelle hinzufügen.

Unser Pool hat auch keine Items, also erstellen wir einen Item-Eintrag mit `ItemEntry#builder` und fügen ihn dem Pool hinzu.

@[code highlight={6-7} transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/event/FabricDocsReferenceEvents.java)

## Benutzerdefinierte Events

In einigen Bereichen des Spiels gibt es keine von der Fabric API bereitgestellten Hooks, so dass du entweder ein Mixin verwenden oder dein eigenes Event erstellen kannst.

Wir werden uns ein Event ansehen, das ausgelöst wird, wenn Schafe geschoren werden. Der Prozess der Erstellung eines Events ist wie folgt:

- Erstellen des Interface für einen Event Callback
- Auslösen des Events von einem Mixin
- Erstellen einer Testimplementierung

### Erstellen des Interface für einen Event Callback

Das Callback-Interface beschreibt, was von den Event-Listenern implementiert werden muss, die auf dein Event hören werden. Das Callback-Interface beschreibt auch, wie das Event von unserem Mixin ausgelöst werden soll. Es ist üblich, ein `Event`-Objekt als Attribut in dem Callback-Interface zu platzieren, das unser tatsächliches Event identifiziert.

Für unsere `Event`-Implementierung werden wir uns für ein Array-gestütztes Event entscheiden. Das Array enthält alle Event-Listener, die auf das Event hören.

Unsere Implementierung ruft die Event-Listener der Reihe nach auf, bis einer von ihnen kein `ActionResult.PASS` zurückgibt. Das bedeutet, dass ein Listener mit Hilfe seines Rückgabewerts sagen kann: "_Abbrechen_", "_Zustimmen_" oder "_Egal, überlasse es dem nächsten Event-Listener_".

Die Verwendung von `ActionResult` als Rückgabewert ist ein konventioneller Weg, um Event-Handler auf diese Weise zusammenarbeiten zu lassen.

Du musst ein Interface erstellen, das eine `Event`-Instanz und eine Methode zur Implementierung der Antwort hat. Ein Grundaufbau für unseren Schafschur-Callback ist:

@[code lang=java transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/event/SheepShearCallback.java)

Schauen wir uns das genauer an. Wenn der Invoker aufgerufen wird, wird über alle Listener iteriert:

@[code lang=java transclude={21-22}](@/reference/latest/src/main/java/com/example/docs/event/SheepShearCallback.java)

Dann rufen wir unsere Methode (in diesem Fall `interact`) auf dem Listener auf, um seine Antwort zu erhalten:

@[code lang=java transclude={33-33}](@/reference/latest/src/main/java/com/example/docs/event/SheepShearCallback.java)

Wenn der Listener sagt, dass wir abbrechen (`ActionResult.FAIL`) oder vollständig beenden (`ActionResult.SUCCESS`) müssen, gibt der Callback das Ergebnis zurück und beendet die Schleife. `ActionResult.PASS` geht zum nächsten Listener über und sollte in den meisten Fällen zum Erfolg führen, wenn keine weiteren Listener registriert sind:

@[code lang=java transclude={25-30}](@/reference/latest/src/main/java/com/example/docs/event/SheepShearCallback.java)

Wir können Javadoc-Kommentare an die oberste Stelle der Callback-Klassen setzen, um zu dokumentieren, was jedes `ActionResult` macht. In unserem Fall könnte das wie folgt sein:

@[code lang=java transclude={9-16}](@/reference/latest/src/main/java/com/example/docs/event/SheepShearCallback.java)

### Auslösen des Events von einem Mixin

Jetzt haben wir das Grundgerüst für ein Event, aber wir müssen es auslösen. Da wir das Ereignis auslösen wollen, wenn ein Spieler versucht, ein Schaf zu scheren, rufen wir das Ereignis `invoker` in `SheepEntity#interactMob` auf, wenn `sheared()` aufgerufen wird (d.h.

@[code lang=java transcludeWith=:::](@/reference/latest/src/main/java/com/example/docs/mixin/event/SheepEntityMixin.java)

### Erstellen einer Testimplementierung

Jetzt müssen wir unser Event testen. Du kannst einen Listener in deiner Initialisierungsmethode (oder in einem anderen Bereich, wenn du das bevorzugst) registrieren und dort benutzerdefinierte Logik hinzufügen. Hier ist ein Beispiel, bei dem dem ein Diamant anstelle von Wolle auf die Füße des Schafs fällt:

@[code lang=java transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/event/FabricDocsReferenceEvents.java)

Wenn du im Spiel ein Schaf scherst, sollte anstelle von Wolle ein Diamant fallen.
