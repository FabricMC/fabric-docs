---
title: Arbeitsplätze
description: Lerne, wie du Arbeitsplätze erstellst.
authors:
  - cassiancc
  - ekulxam
  - skippyall
---

<!---->

:::info VORAUSSETZUNGEN

Dieser Arbeitsplatz verwendet einen benutzerdefinierten Rezepttyp, der unter [Benutzerdefinierte Rezepttypen](../recipes/custom-recipe-types) zu finden ist.

:::

Dieses Tutorial bietet dir Anleitungen, wie du benutzerdefinierte Arbeitsplätze erstellst. Im Gegensatz zu Truhen müssen Arbeitsplätze ihr Inventar nicht unbedingt beibehalten, nachdem die Benutzeroberfläche geschlossen wurde (Blöcke wie die Werkbank speichern ihren Inhalt nicht, andere Blöcke wie beispielsweise Öfen hingegen schon). Zu Demonstrationszwecken werden wir keine Block Entität verwenden.

## Ein Menü erstellen {#creating-a-menu}

::: info

Weitere Informationen zum Erstellen von Menüs findest du unter unter [Container-Menüs](./container-menus).

:::

Damit wir unser Rezept in dem GUI herstellen können, werden wir einen Block mit einem Menü erstellen. Um das Menü zu öffnen, müssen wir einige Methoden in unserer Klasse `Block` überschreiben:

<<< @/reference/26.2/src/main/java/com/example/docs/block/custom/UpgradingBlock.java#openmenu

Danach sind wir bereit, das Menü zu erstellen.

<<< @/reference/26.2/src/main/java/com/example/docs/menu/custom/UpgradingMenu.java#menu

Passend zu diesem Menü benötigen wir außerdem einen benutzerdefinierten `Slot` für das Ergebnis.

<<< @/reference/26.2/src/main/java/com/example/docs/menu/custom/UpgradingResultSlot.java#slot

Da gibt es eine Menge zu besprechen! Dieses Menü verfügt über zwei Eingabeslots und einen Ausgabeslot `UpgradingResultSlot`.

Der Eingabecontainer ist eine anonyme Unterklasse von `SimpleContainer`, die bei einer Änderung ihrer Items die Methode `slotsChanged` des Menüs aufruft. In `slotsChanged` erstellen wir dann eine Instanz unserer Rezept-Eingabeklasse und füllen sie mit den beiden Eingabeslots.

Um zu prüfen, ob es mit einem Rezept übereinstimmt, stellen wir zunächst sicher, dass wir uns auf der Serverebene befinden, da Clients nicht wissen, welche Rezepte vorhanden sind. Anschließend rufen wir den `RecipeManager` über `serverLevel.recipeAccess()` ab.

:::details Eine Anmerkung am Rande: Rezeptsynchronisierung

> Wenn der Client nicht weiß, welche Rezepte es gibt, wie funktioniert dann das Rezeptbuch?

Ich bin froh, dass du gefragt hast. Der Server teilt dem Client mit, welche Rezepte verfügbar sind, je nachdem, welche Rezepte du freigeschaltet hast (dies geschieht durch das Erfüllen bestimmter Kriterien, die in der JSON-Datei zum Fortschritt des jeweiligen Rezepts beschrieben sind, wie beispielsweise das Erlangen eines Items oder das Betreten von Wasser (bei Booten)). Das ist jedoch ziemlich ärgerlich für Mods zur Anzeige von Rezepten, die im Idealfall alle verfügbaren Rezepte anzeigen möchten, nun aber nur noch die Rezepte sehen können, die der Client vom Server erhält. Um dies zu umgehen, können wir [die Fabric-API zur Synchronisierung unserer Rezepte verwenden](../recipes/custom-recipe-types#recipe-synchronization).

:::

Wir rufen `serverLevel.recipeAccess().getRecipeFor` mit unseren Rezept-Eingaben auf, um ein Rezept zu erhalten, das den Eingaben entspricht. Wenn ein Rezept gefunden wurde, können wir das Ergebnis zum Ergebniscontainer hinzufügen oder daraus entfernen.

Um zu erkennen, wann der Benutzer das Ergebnis entnimmt, verwenden wir die Überschreibung von `onTake`des `UpgradingResultSlot`. Die Methode `onTake` unseres Menüs verringert dann die Anzahl der Eingabeitems.

Um sicherzustellen, dass sich der Spieler innerhalb der Interaktionsreichweite des Blocks befindet, überschreiben wir `stillValid`.

::: warning

Stelle sicher, dass der `Block`, den du als Argument für `stillValid` übergibst, der Block ist, der das Menü öffnet! Wenn du das nicht tust, werden das Menü und die Oberfläche möglicherweise geöffnet und schließen sich anschließend sofort wieder.

:::

Um schließlich zu verhindern, dass Items gelöscht werden, ist es wichtig, die Eingabeslots beim Schließen der Oberfläche wieder zurückzusetzen, wie in der Methode `removed` gezeigt.

::: info

Möglicherweise ist dir aufgefallen, dass mehrere Methoden einen Aufruf von `ContainerLevelAccess#execute` enthalten. Dies ist eine Wrapper-Klasse, die von Mojang verwendet wird, um sicherzustellen, dass bei Interaktionen das richtige `Level` und die richtige Position verwendet werden und um zu verhindern, dass Spieler auf Container zugreifen, auf die sie keinen Zugriff haben sollten. Beachte, dass der spezielle `NULL`-`ContainerLevelAccess` keine Aktion ausführt, wenn die Methode `execute` auf ihm aufgerufen wird.

:::

Die Methode `mayPlace` des `Slot` gibt `false` zurück, sodass Spieler keine Items in den Ergebnis-Slot einfügen können, und die Methode `isFake` teilt dem `Screen` mit, dass der darin enthaltene Stack (noch) keinen Besitzer hat.

Du musst auch das Menü zur Registry hinzufügen:

<<< @/reference/26.2/src/main/java/com/example/docs/menu/ModMenuTypes.java#upgrading_menu_registration

Schließlich müssen wir unseren Block registrieren:

<<< @/reference/26.2/src/main/java/com/example/docs/block/ModBlockItemIds.java#workstation

<<< @/reference/26.2/src/main/java/com/example/docs/block/ModBlocks.java#workstation

### Implementieren von `quickMoveStack` {#implementing-quick-move-stack}

::: info

Siehe auch: [Container Menüs: Das Menü erstellen](./container-menus#creating-the-menu)

:::

Quick Move wird immer dann aufgerufen, wenn in einem Menü ein Shift-Klick ausgeführt wird.

<<< @/reference/26.2/src/main/java/com/example/docs/menu/custom/SuperiorUpgradingMenu.java#quickMove

Wow, das ist wieder viel Code. Lasst uns einmal versuchen, die Situation zu durchdenken.

Wenn ein Stack aus dem Inventarbereich schnell verschoben wird, prüft das Menü in der Regel zunächst, ob es sich bei dem angeklickten Slot um den Ergebnisslot (mit Index 0) handelt. Wenn dies der Fall ist, versucht das Menü, den Ergebnisstack in das Inventar zu verschieben, aber sollte dies fehlschlagen, geschieht nichts.

Anschließend prüft das Menü, ob der angeklickte Slot zum Inventar gehört. Wenn dies so ist, versucht das Menü, den Stack in die Eingaben zu verschieben. Sollte dies fehlschlagen, versuchen wir, den Stack innerhalb des Inventars zu verschieben (durch Anklicken eines Slots in der Schnellzugriffsleiste werden die darin enthaltenen Stacks in die anderen 27 Slots des Inventars verschoben und umgekehrt).

Wenn der angeklickte Slot weder der Ergebnisslot noch ein Slot im Inventar war, handelt es sich bei diesem Slot mit ziemlicher Sicherheit um einen unserer beiden Eingabeslots, sodass wir den dortigen Stack wieder ins Inventar verschieben möchten.

### Die Oberfläche {#screen}

::: info

Siehe auch: [Container Menüs: Die Oberfläche erstellen](./container-menus#creating-the-screen)

:::

Für den Moment können wir einfach die Hintergrundtextur des Vanilla Amboss übernehmen.

<<< @/reference/26.2/src/client/java/com/example/docs/rendering/screens/inventory/UpgradingScreen.java#screen

Vergiss nicht, deinen Menütyp in deinem `ClientModInitializer` wie folgt an die Oberfläche zu binden:

<<< @/reference/26.2/src/client/java/com/example/docs/ExampleModRecipesClient.java#register_with_menu

## Reste aus Rezepten {#recipe-remainders}

Möchtest du ein Rezept erstellen, das Reste berücksichtigt? Wir empfehlen, einen Blick auf `net.minecraft.world.inventory.ResultSlot#getRemainingItems` zu werfen. Die Werkbank verwendet dies als dessen Ergebnislot, sodass viele Ähnlichkeiten zur Dokumentation zu finden sind, es gibt jedoch auch einige Unterschiede.
