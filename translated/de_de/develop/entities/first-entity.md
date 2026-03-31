---
title: Deine erste Entität erstellen
description: Lerne, wie man eine einfache Entität registriert, ihr Ziele gibt, sie rendert, modelliert und animiert.
authors:
  - cassiancc
  - Earthcomputer
  - JaaiDead
  - skycatminepokie
  - SzczurekYT
  - voidedaries
resources:
  https://docs.neoforged.net/docs/entities/: Entitäten - NeoForge Docs
  https://www.desmos.com/calculator/9r6lh5knfu: Laufanimation einer Entität - Desmos
---

Entitäten sind im Spiel dynamische, interaktive Objekte, die nicht Teil des Terrain (wie Blöcke) sind. Entitäten können sich bewegen und auf unterschiedliche Wege mit der Welt interagieren. Einige Beispiele sind folgende:

- `Villager`, `Pig`, und `Goat` sind alles Beispiele eines `Mob`, der häufigste Typ einer Entität - etwas das lebt.
- `Zombie` und `Skeleton` sind Beispiele für ein `Monster`, eine Variante einer `Entity`, die feindlich zu einem `Player` ist.
- `Minecart` und `Boat` sind Beispiele für eine `VehicleEntity`, die eine spezielle Logik zur Akzeptierung von Spielereingaben besitzt.

Dieses Tutorial führt dich Schritt für Schritt durch die Erstellung eines benutzerdefinierten _Mini-Golem_. Diese Entität wird lustige Animationen haben. Es wird ein `PathfinderMob`, die Klasse, die von den meisten Mobs mit Wegfindung verwendet wird, wie beispielsweise `Zombie` und `Villager`, sein.

## Deine erste Entität vorbereiten {#preparing-your-first-entity}

Der erste Schritt bei der Erstellung einer benutzerdefinierten Entität besteht darin, dessen Klasse zu definieren und sie im Spiel zu registrieren.

Wir werden die Klasse `MiniGolemEntity` für unsere Entität erstellen und beginnen damit, ihr Attribute zu geben. [Attribute](attributes) bestimmen verschiedene Eigenschaften, darunter die maximale Gesundheit, die Geschwindigkeit der Bewegung und die Reichweite der Entität.

@[code transcludeWith=:::registerclass](@/reference/latest/src/main/java/com/example/docs/entity/MiniGolemEntity.java)

Um deine Entitäten zu registrieren, empfiehlt es sich, eine eigene Klasse mit dem Namen `ModEntityTypes` zu erstellen, in der du alle Entitätstypen registrierst, deren Größe festlegst und deren Attribute registrierst.

@[code transcludeWith=:::types](@/reference/latest/src/main/java/com/example/docs/entity/ModEntityTypes.java)

## Ziele hinzufügen {#adding-goals}

Ziele sind das System, das die Ziele einer Entität regelt und ihr ein definiertes Verhaltensmuster vorgibt. Ziele haben eine bestimmte Priorität: Ziele mit einem geringeren Wert werden gegenüber den Zielen mit einem höheren Wert priorisiert.

Um der Entität Ziele hinzuzufügen, musst du in der Klasse deiner Entität eine Methode `registerGoals` erstellen, die die Ziele für die Entität definiert.

@[code transcludeWith=:::goals](@/reference/latest/src/main/java/com/example/docs/entity/MiniGolemEntity.java)

::: info

1. `TemptGoal` - Die Entität wird von einem Spieler angezogen, der ein Item in der Hand hält.
2. `RandomStrollGoal` - Geht/wandert in der Welt.
3. `LookAtPlayerGoal` - Trotz des Namens akzeptiert dieses jede beliebige Entität. Wird hier verwendet, um die Entität `Cow` anzusehen.
4. `RandomLookAroundGoal` - Schaut in zufällige Richtungen.

:::

## Das Rendering erstellen {#creating-rendering}

Unter Rendering versteht man den Prozess, bei dem Spieldaten wie Blöcke, Entitäten und Umgebungen in visuelle Darstellungen umgewandelt werden, die auf dem Bildschirm des Spielers angezeigt werden. Dabei geht es darum, festzulegen, wie Objekte beleuchtet, schattiert und texturiert werden.

::: info

Das Rendering von Entitäten erfolgt immer clientseitig. Der Server steuert die Logik und das Verhalten der Entität, während der Client für die Darstellung des Modells, der Textur und der Animationen der Entität zuständig ist.

:::

Das Rendern umfasst mehrere Schritte, die jeweils eigene Klassen beinhalten, aber wir beginnen mit der Klasse `EntityRenderState`.

@[code transcludeWith=:::entitystate](@/reference/latest/src/client/java/com/example/docs/entity/state/MiniGolemEntityRenderState.java)

Die im Renderzustand gespeicherten Daten dienen dazu, zu bestimmen, wie die Entität visuell dargestellt wird, einschließlich Animationszuständen wie Bewegungs- und Ruheverhalten.

### Das Modell einrichten {#setting-up-model}

Die Klasse `MiniGolemEntityModel` definiert, wie deine Entität aussieht indem sie dessen Form udn Teile beschreibt. Modelle werden in der Regel mit Tools von Drittanbietern wie [Blockbench](https://blockbench.net/) erstellt und nicht von Hand geschrieben. Dennoch wird in dieser Anleitung anhand eines praktischen Beispiels gezeigt, wie das funktioniert.

::: warning

Blockbench unterstützt mehrere [Mappings](../migrating-mappings/#mappings) (wie beispielsweise Mojang Mappings, Yarn und andere). Achte darauf, dass du die richtigen Mappings für deine Entwicklungsumgebung auswählst - in diesem Tutorial werden Mojang-Mappings verwendet.

Nicht übereinstimmende Mappings können bei der Integration von durch Blockbench generiertem Code zu Fehlern führen.

:::

@[code transcludeWith=:::model1](@/reference/latest/src/client/java/com/example/docs/entity/model/MiniGolemEntityModel.java)

Die Klasse `MiniGolemEntityModel` definiert die visuellen Modelle für unsere Mini-Golem Entität. Sie erbt von `EntityModel` und legt fest, wie die Körperteile der Entität (Körper, Kopf, linkes und rechtes Bein) benannt werden.

@[code transcludeWith=:::model_texture_data](@/reference/latest/src/client/java/com/example/docs/entity/model/MiniGolemEntityModel.java)

Diese Methode definiert das 3D-Modell des Mini-Golems, indem sie dessen Körper, Kopf und Beine als Quader erstellt, deren Positionen und Texturzuordnungen festlegt und eine `LayerDefinition` für das Rendering zurückgibt.

Jedes Teil wird mit einem Versatzpunkt versehen, der als Ursprung für alle auf dieses Teil angewendeten Transformationen dient. Alle anderen Koordinaten im Teil des Modells werden relativ zu diesem Versatzpunkt gemessen.

::: warning

Höhere Y-Werte im Modell entsprechen der **Unterseite** der Entität. Dies ist das Gegenteil im Vergleich zu den Koordinaten im Spiel.

:::

Wir müssen nun im Client-Paket eine Klasse mit dem Namen `ModEntityModelLayers` erstellen. Diese Entität hat nur eine einzige Textur-Ebene, andere Entitäten können jedoch mehrere verwenden - denke an die zweite Haut-Ebene bei Entitäten wie dem `Player` oder an die Augen einer `Spider`.

@[code transcludeWith=:::model_layer](@/reference/latest/src/client/java/com/example/docs/entity/model/ModEntityModelLayers.java)

Diese Klasse muss dann im Client-Initialisierer des Mods initialisiert werden.

@[code transcludeWith=::register_client](@/reference/latest/src/client/java/com/example/docs/entity/ExampleModCustomEntityClient.java)

### Die Textur einrichten {#setting-up-texture}

::: tip

Die Größe der Textur sollte mit den Werten in `LayerDefinition.create(modelData, 64, 32)` übereinstimmen: 64 Pixel breit und 32 Pixel hoch. Wenn du eine Textur in einer anderen Größe benötigst, vergiss nicht, die Größe in `LayerDefinition.create` entsprechend anzupassen.

:::

Jedes Teil des Modells bzw. jede Box erwartet an einer bestimmten Stelle ein Netz auf der Textur. Standardmäßig wird die Position `0, 0` (oben links) erwartet, dies lässt sich jedoch durch Aufruf der Funktion `texOffs` in `CubeListBuilder` ändern.

Als Beispiel kannst du diese Textur für `assets/example-mod/textures/entity/mini_golem.png` verwenden.

<DownloadEntry visualURL="/assets/develop/entity/mini_golem.png" downloadURL="/assets/develop/entity/mini_golem_small.png">Textur</DownloadEntry>

### Den Renderer erstellen {#creating-the-renderer}

Ein Renderer einer Entität erlaubt es dir, deine Entität im Spiel anzusehen. Wir erstellen eine neue Klasse `MiniGolemEntityRenderer`, die Minecraft mitteilen wird, welche Textur, welches Modell und welchen Renderzustand für diese Entität verwendet werden sollen.

@[code transcludeWith=:::renderer](@/reference/latest/src/client/java/com/example/docs/entity/renderer/MiniGolemEntityRenderer.java)

Hier wird auch der Schattenradius festgelegt; für diese Entität wird er `0.375f` sein.

Dieser Renderer muss dann in dem Client-Initialisierer des Mods registriert werden.

@[code transcludeWith=::register_renderer](@/reference/latest/src/client/java/com/example/docs/entity/ExampleModCustomEntityClient.java)

### Laufanimationen hinzufügen {#walking-animations}

Der folgende Code kann der Klasse `MiniGolemEntityModel` hinzugefügt werden, um der Entität eine Laufanimation zu geben.

@[code transcludeWith=:::model_animation](@/reference/latest/src/client/java/com/example/docs/entity/model/MiniGolemEntityModel.java)

Um zu starten, wende zunächst die Drehung (Yaw) und Neigung (Pitch) auf das Kopfmodellteil an.

Anschließend wenden wir die Laufanimation auf die Teile des Beinmodells an. Wir verwenden die Funktion `cos`, um den allgemeinen Beinschwung-Effekt zu erzeugen, und transformieren dann die Kosinuswelle, um die richtige Schwunggeschwindigkeit und Amplitude zu erhalten.

- Die Konstante `0.2` in der Formel bestimmt die Frequenz der Kosinuswelle (wie schnell die Beine schwingen). Höhere Werte führen zu einer höheren Frequenz.
- Die Konstante `0.4` in der Formel bestimmt die Amplitude der Kosinuswelle (wie weit die Beine schwingen). Höhere Werte führen zu einer höheren Amplitude.
- Die Variable `limbSwingAmplitude` beeinflusst die Amplitude auf dieselbe Weise wie die Konstante `1.4`. Diese Variable ändert sich in Abhängigkeit von der Geschwindigkeit der Entität, sodass die Beine stärker schwingen, wenn sich die Entität schneller bewegt, und weniger stark oder gar nicht schwingen, wenn sich die Entität langsamer bewegt oder stillsteht.
- Die Konstante `Mth.PI` für den linken Schenkel verschiebt die Kosinuswelle um eine halbe Phase, sodass das linke Bein in die entgegengesetzte Richtung zum rechten Bein schwingt.

Du kannst diese Werte in einem Diagramm darstellen, um zu sehen, wie sie aussehen:

![Diagramm](/assets/develop/entity/graphs/dark_graph.png){.dark-only}
![Diagramm](/assets/develop/entity/graphs/light_graph.png){.light-only}

Die blaue Kurve steht für das linke Bein und die braune für das rechte. Die horizontale x-Achse stellt die Zeit dar, und die y-Achse gibt den Winkel der Beinglieder an.

Probier ruhig mal aus, [mit den Konstanten in Desmos zu experimentieren](https://www.desmos.com/calculator/9r6lh5knfu), um zu sehen, wie sie sich auf die Kurve auswirken.

Wenn du dir das im Spiel ansiehst, hast du nun alles, was du brauchst, um die Entität mit `/summon example-mod:mini_golem` zu erschaffen!

![Erzeugter Golem](/assets/develop/entity/mini_golem_summoned.png)

## Daten zu der Entität hinzufügen {#adding-data-to-an-entity}

Um Daten an einer Entität zu speichern, ist der normale Weg einfach ein Feld der Klasse der Entität hinzuzufügen.

Manchmal müssen Daten aus der serverseitigen Entität mit der clientseitigen Entität synchronisiert werden. Weitere Informationen zur Client-Server-Architektur findest du auf der [Netzwerkseite](../networking). Dazu können wir _synchronisierte Daten_ \[sic] verwenden, indem wir dafür einen `EntityDataAccessor` definieren.

In unserem Fall soll unsere Entität von Zeit zu Zeit tanzen; daher müssen wir einen Tanzzustand erstellen, der zwischen den Clients synchronisiert ist, damit er später animiert werden kann. Die Abklingzeit für das Tanzen muss jedoch nicht mit dem Client synchronisiert werden, da die Animation vom Server ausgelöst wird.

@[code transcludeWith=:::datatracker](@/reference/latest/src/main/java/com/example/docs/entity/MiniGolemEntity.java)

Wie du sehen kannst, haben wir eine Tick-Methode hinzugefügt, um den Tanzzustand zu steuern.

## Daten in NBT speichern {#storing-data-to-nbt}

Für persistente Daten, die nach dem Schließen des Spiels gespeichert werden können, werden wir die Methoden `addAdditionalSaveData` und `readAdditionalSaveData` in `MiniGolemEntity` überschreiben. Wir können dies verwenden, um die übrige Zeit der Tanzanimation zu speichern.

@[code transcludeWith=:::savedata](@/reference/latest/src/main/java/com/example/docs/entity/MiniGolemEntity.java)

Wenn die Entität nun geladen wird, stellt sie den Zustand wieder her.

## Animationen hinzufügen {#adding-an-animation}

Der erste Schritt, um der Entität eine Animation hinzuzufügen, besteht darin, den Animationszustand in der Klasse der Entität hinzuzufügen. Wir werden einen Animationszustand erstellen, der verwendet werden wird, um die Entität zum Tanzen zu bringen.

@[code transcludeWith=:::dancing_animation](@/reference/latest/src/main/java/com/example/docs/entity/MiniGolemEntity.java)

Wir haben die Methode `onSyncedDataUpdated` überschrieben. Dies wird immer dann aufgerufen, wenn synchronisierte Daten sowohl auf dem Server als auch auf dem Client aktualisiert werden. Die if-Anweisung prüft, ob es sich bei den aktualisierten synchronisierten Daten um die tanzenden synchronisierten Daten handelt.

Jetzt werden wir mit der Animation selbst fortfahren. Wir werden die Klasse `MiniGolemAnimations` erstellen und eine `AnimationDefinition` hinzufügen, um festzulegen, wie die Animation auf die Entität angewendet wird.

@[code transcludeWith=:::dancing_animation](@/reference/latest/src/client/java/com/example/docs/entity/animation/MiniGolemAnimations.java)

Hier geschieht einiges; beachte die folgenden Schlüsselpunkte:

- `withLength(1)` sorgt dafür, dass die Animation eine Sekunde dauert.
- `looping()` sorgt dafür, dass die Animation fortlaufend wiederholt wird.
- Es folgt eine Reihe von `addAnimation`-Aufrufen, die individuelle Animationen für einzelne Modellteile hinzufügen. Hier haben wir verschiedene Animationen, die auf den Kopf, das linke Bein und das rechte Bein abzielen.
  - Jede Animation zielt auf eine bestimmte Eigenschaft dieses Modellteils ab; in unserem Fall ändern wir in jedem Fall die Drehung des Modellteils.
  - Eine Animation besteht aus einer Liste von Keyframes. Wenn der Zeitpunkt (die verstrichene Zeit in Sekunden) der Animation mit einem dieser Keyframes übereinstimmt, entspricht der Wert der betreffenden Eigenschaft dem Wert, den wir für diesen Keyframe festgelegt haben (in unserem Fall die Drehung).
  - Liegt der Zeitpunkt zwischen unseren Keyframes, wird der Wert zwischen den beiden benachbarten Keyframes interpoliert (überblendet).
  - Wir haben lineare Interpolation verwendet, die die einfachste Methode ist und den Wert (in unserem Fall die Drehung des Modellteils) von einem Keyframe zum nächsten mit konstanter Geschwindigkeit ändert. Vanilla bietet außerdem Catmull-Rom-Spline-Interpolation, wodurch ein flüssigerer Übergang zwischen den Keyframes erzielt wird.
  - Modder können auch benutzerdefinierte Interpolationstypen erstellen.

Zum Schluss, lasst uns die Animation mit dem Modell verbinden:

@[code transcludeWith=:::dancing_animation](@/reference/latest/src/client/java/com/example/docs/entity/model/MiniGolemEntityModel.java)

Während die Animation läuft, wenden wir die Animation an, andernfalls verwenden wir den alten Code für die Beinanimation.

## Das Spawn-Ei hinzufügen {#adding-spawn-egg}

Um ein Spawn-Ei für die Mini-Golem Entität hinzuzufügen, lies den vollständigen Artikel unter [ein Spawn-Ei erstellen](../items/spawn-egg).
