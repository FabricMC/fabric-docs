---
title: Events
description: Ein Leitfaden für die Nutzung von Events, welche von der Fabric API bereitgestellt werden.
authors:
  - Daomephsta
  - dicedpixels
  - Draylar
  - JamiesWhiteShirt
  - Jimmy474
  - Juuxel
  - liach
  - mkpoli
  - natanfudge
  - PhoenixVX
  - SolidBlock-cn
  - YanisBft
authors-nogithub:
  - stormyfabric
---

Die Fabric API bietet ein System, das es Mods erlaubt, auf Aktionen oder Ereignisse zu reagieren, die auch als _Events_ im Spiel definiert sind.

Events sind Hooks, die gemeinsame Anwendungsfälle erfüllen und/oder die Kompatibilität und Leistung zwischen Mods verbessern, die sich in dieselben Bereiche des Codes einklinken. Die Verwendung von Ereignissen ersetzt oft die Verwendung von Mixins.

Die Fabric API stellt Ereignisse für wichtige Bereiche der Minecraft-Codebasis bereit, an denen mehrere Modder interessiert sein könnten.

Ereignisse werden durch Instanzen von `net.fabricmc.fabric.api.event.Event` dargestellt, die _Callbacks_ speichern und aufrufen. Oft gibt es eine einzige Event-Instanz für einen Callback, die in einem statischen Attribut `EVENT` des Callback-Interfaces gespeichert wird, aber es gibt auch andere Muster. Zum Beispiel fasst `ClientTickEvents` mehrere zusammenhängende Ereignisse zusammen.

## Callbacks {#callbacks}

Callbacks sind ein Teil des Codes, der als Argument an ein Event übergeben wird. Wenn das Event vom Spiel ausgelöst wird, wird der übergebene Teil des Codes ausgeführt.

### Callback Interfaces {#callback-interfaces}

Jedes Event hat ein dazugehöriges Callback Interface. Callbacks werden durch den Aufruf der Methode `register()` für eine Event-Instanz registriert, wobei eine Instanz des Callbacks als Argument angegeben wird.

## Auf Events hören {#listening-to-events}

Dieses Beispiel registriert einen `AttackBlockCallback`, um dem Spieler Schaden zuzufügen, wenn er Blöcke trifft, die keinen Gegenstand fallen lassen, wenn sie von Hand abgebaut werden.

<<< @/reference/26.1.2/src/main/java/com/example/docs/event/ExampleModEvents.java#attack_block_callback_event

### Items zu existierenden Beutetabellen hinzufügen {#adding-items-to-existing-loot-tables}

Manchmal willst du vielleicht Gegenstände zu Beutetabellen hinzufügen. Zum Beispiel, indem du deine Drops zu einem Vanille-Block oder einer Entität hinzufügst.

Die einfachste Lösung, das Ersetzen der Beutetabellen-Datei, kann andere Mods zerstören. Was ist, wenn sie diese auch ändern wollen? Wir sehen uns an, wie du Gegenstände zu Beutetabellen hinzufügen kannst, ohne die Tabelle zu überschreiben.

Wir werden die Beutetabelle für Kohleerz um Eier erweitern.

#### Auf das Laden der Beutetabelle hören {#listening-to-loot-table-loading}

Die Fabric API hat ein Event, das ausgelöst wird, wenn Beutetabellen geladen werden, `LootTableEvents.MODIFY`. Du kannst hierfür einen Callback in deinem [Mod Initialisierer](./getting-started/project-structure#entrypoints) registrieren. Überprüfen wir auch, ob die aktuelle Beutetabelle die Beutetabelle für Kohleerz ist:

<<< @/reference/26.1.2/src/main/java/com/example/docs/event/ExampleModEvents.java#loot_table_events

#### Hinzufügen von Items zur Beutetabelle {#adding-items-to-the-loot-table}

Um einen Gegenstand hinzuzufügen, müssen wir der Beutetabelle einen Pool mit einem Eintrag für ein Item hinzufügen.

Wir können einen Pool mit `LootPool#lootPool` erstellen, und ihn zur Beutetabelle hinzufügen.

Unser Pool hat auch noch keine Items, also erstellen wir einen Item-Eintrag mit `LootItem#lootTableItem` und fügen ihn dem Pool hinzu.

<<< @/reference/26.1.2/src/main/java/com/example/docs/event/ExampleModEvents.java#loot_pool_builder{5-7}

## Benutzerdefinierte Events {#custom-events}

In einigen Bereichen des Spiels gibt es keine von der Fabric API bereitgestellten Hooks, so dass du entweder ein Mixin verwenden oder dein eigenes Event erstellen kannst.

Wir werden uns ein Event ansehen, das ausgelöst wird, wenn Schafe geschoren werden. Der Prozess der Erstellung eines Events ist wie folgt:

- Erstellen des Interface für einen Event Callback
- Auslösen des Events von einem Mixin
- Erstellen einer Testimplementierung

### Erstellen des Interface für einen Event Callback {#creating-the-event-callback-interface}

Das Callback-Interface beschreibt, was von den Event-Listenern implementiert werden muss, die auf dein Event hören werden. Das Callback-Interface beschreibt auch, wie das Event von unserem Mixin ausgelöst werden soll. Es ist üblich, ein `Event`-Objekt als Attribut in dem Callback-Interface zu platzieren, das unser tatsächliches Event identifiziert.

Für unsere `Event`-Implementierung werden wir uns für ein Array-gestütztes Event entscheiden. Das Array enthält alle Event-Listener, die auf das Event hören.

Unsere Implementierung ruft die Event-Listener der Reihe nach auf, bis einer von ihnen kein `InteractionResult.PASS` zurückgibt. Das bedeutet, dass ein Listener mit Hilfe seines Rückgabewerts "_dies abbrechen_", "_dies genehmigen_" oder "_mir egal, an den nächsten Event-Listener weiterleiten_" sagen kann.

Die Verwendung von `InteractionResult` als Rückgabewert ist eine gängige Methode, um Event-Handler auf diese Weise miteinander zu koordinieren.

Du musst ein Interface erstellen, das eine `Event`-Instanz und eine Methode zur Implementierung der Antwort hat. Ein Grundaufbau für unseren Schafschur-Callback ist:

<<< @/reference/26.1.2/src/main/java/com/example/docs/event/SheepShearCallback.java#sheep_shear_callback

Schauen wir uns das genauer an. Wenn der Invoker aufgerufen wird, wird über alle Listener iteriert:

<<< @/reference/26.1.2/src/main/java/com/example/docs/event/SheepShearCallback.java#listener_iterator

Bei jedem Listener rufen wir dann `interact` auf, um die Antwort des Listeners abzurufen. Hier ist die Signatur von `interact`, die wir in diesem Interface deklariert haben:

<<< @/reference/26.1.2/src/main/java/com/example/docs/event/SheepShearCallback.java#interact_method

Wenn der Listener sagt, dass wir abbrechen (durch die Rückgabe von `FAIL`) oder vollständig beenden (`SUCCESS`) müssen, gibt der Callback das Ergebnis zurück und beendet die Schleife.

`InteractionResult.PASS` wird an den nächsten Listener weitergeleitet, bis alle Listener aufgerufen wurden und schließlich `PASS` zurückgegeben wird:

<<< @/reference/26.1.2/src/main/java/com/example/docs/event/SheepShearCallback.java#return_value

Wir können Javadoc-Kommentare an der obersten Stelle der Callback-Klassen hinzufügen, um zu dokumentieren, was jedes `InteractionResult` macht. In unserem Fall könnte das wie folgt sein:

<<< @/reference/26.1.2/src/main/java/com/example/docs/event/SheepShearCallback.java#javadoc_comment

### Auslösen des Events von einem Mixin {#triggering-the-event-from-a-mixin}

Jetzt haben wir das Grundgerüst für ein Event, aber wir müssen es auslösen. Da wir das Event auslösen wollen, wenn ein Spieler versucht, ein Schaf zu scheren, rufen wir den `invoker` des Event in `Sheep#mobInteract` auf, wenn `shear()` aufgerufen wird (d. h. Schafe können geschoren werden, und der Spieler hält eine Schere):

<<< @/reference/26.1.2/src/main/java/com/example/docs/mixin/event/SheepMixin.java#sheep_mixin

### Erstellen einer Testimplementierung {#creating-a-test-implementation}

Jetzt müssen wir unser Event testen. Du kannst einen Listener in deiner Initialisierungsmethode (oder in einem anderen Bereich, wenn du das bevorzugst) registrieren und dort benutzerdefinierte Logik hinzufügen.

Hier ist ein Beispiel, bei dem dem ein Diamant anstelle von Wolle auf die Füße des Schafs fällt:

<<< @/reference/26.1.2/src/main/java/com/example/docs/event/ExampleModEvents.java#sheep_shear_callback_event

Wenn du im Spiel ein Schaf scherst, sollte anstelle von Wolle ein Diamant fallen.

<!-- TODO: maybe adding a video of a sheep dropping diamonds? -->
