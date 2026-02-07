---
title: Dynamische Sounds
description: Erstelle mehr dynamische und interaktive Sounds
authors:
  - JR1811
---

<!---->

:::info VORAUSSETZUNGEN

Diese Seite baut auf den Seiten [Sounds abspielen](../sounds/using-sounds) und [Benutzerdefinierte Sounds erstellen](../sounds/custom) auf!

:::

## Probleme mit `SoundEvents` {#problems-with-soundevents}

Wie wir auf der Seite [Sounds verwenden](../sounds/using-sounds) gelernt haben, ist es vorzuziehen, `SoundEvent` auf einer logischen Serverseite zu verwenden, auch wenn es ein wenig kontraintuitiv ist. Schließlich muss ein Client den Ton verarbeiten, der an Ihre Kopfhörer übertragen wird, nicht wahr?

Diese Denkweise ist richtig. Technisch gesehen muss clientseitig der Ton verarbeitet werden. Für das einfache Abspielen von `SoundEvent` hat die Serverseite jedoch einen großen Zwischenschritt vorbereitet, der auf den ersten Blick vielleicht nicht offensichtlich ist. Welche Clients sollten diesen Ton hören können?

Die Verwendung des Sounds auf der logischen Serverseite löst das Problem der Übertragung von `SoundEvent`s. Einfach ausgedrückt: Jeder Client (`LocalPlayer`), der sich im Verfolgungsbereich befindet, bekommt ein Netzwerkpaket geschickt, um diesen speziellen Sound abzuspielen. Das Sound-Ereignis wird im Grunde von der logischen Serverseite an jeden teilnehmenden Client übertragen, ohne dass du dich darum kümmern musst. Der Sound wird einmal mit den angegebenen Lautstärke- und Tonhöhenwerten abgespielt.

Was aber, wenn dies nicht ausreicht? Was ist, wenn der Sound in einer Schleife laufen, die Lautstärke und die Tonhöhe während des Abspielens dynamisch ändern muss und all das auf der Grundlage von Werten, die von Dingen wie `Entities` oder `BlockEntities` stammen?

Die einfache Verwendung von `SoundEvent` auf der logischen Serverseite ist für diesen Anwendungsfall nicht ausreichend.

## Vorbereitung der Audio-Datei {#preparing-the-audio-file}

Wir werden eine neue **Wiederholende**-Audio für ein anderes `SoundEvent` erstellen. Wenn du eine Audiodatei findest, die bereits nahtlos in einer Schleife läuft, kannst du einfach die Schritte unter [Benutzerdefinierte Sounds erstellen](../sounds/custom) ausführen. Wenn sich der Sound noch nicht perfekt wiederholt, müssen wir ihn darauf vorbereiten.

Auch hier sollten die meisten modernen DAWs (Digital Audio Workstation Software) dazu in der Lage sein, aber ich verwende gerne [Reaper](https://www.reaper.fm/), wenn die Audiobearbeitung etwas aufwendiger ist.

### Einrichten {#set-up}

Unser [Startsound](https://freesound.org/people/el-bee/sounds/644881/) wird von einem Motor kommen.

<audio controls>
    <source src="/assets/develop/sounds/dynamic-sounds/step_0.wav" type="audio/wav">
    
    Dein Browser untersützt das Audioelement nicht.
</audio>

Laden wir die Datei in die DAW unserer Wahl.

![Reaper mit der geladenen Audiodatei](/assets/develop/sounds/dynamic-sounds/step_0.png)

Wir können hören und sehen, dass der Motor am Anfang gestartet und am Ende gestoppt wird, was für wiederholende Sounds nicht gut ist. Schneiden wir diese raus und passen wir die Zeitauswahlgriffe an die neue Länge an. Aktiviere auch den Modus `Toggle Repeat`, damit der Ton in einer Schleife abgespielt wird, während wir ihn anpassen.

![Gekürzte Audiodatei](/assets/develop/sounds/dynamic-sounds/step_1.png)

### Störende Audioelemente entfernen {#removing-disruptive-audio-elements}

Wenn wir genau hinhören, ist im Hintergrund ein Piepen zu hören, das von der Maschine stammen könnte. Ich denke, dass dies im Spiel nicht gut klingen würde, also sollten wir versuchen, es zu entfernen.

Es handelt sich um einen konstanten Klang, der seine Frequenz über die gesamte Dauer des Tons beibehält. Ein einfacher EQ-Filter sollte also ausreichen, um es herauszufiltern.

Reaper ist bereits mit einem EQ-Filter ausgestattet, dem "ReaEQ". In anderen DAWs mag dieser an anderer Stelle liegen und anders benannt sein, aber in den meisten DAWs ist die Verwendung von EQ heutzutage Standard.

Wenn du sicher bist, dass dein DAW keinen EQ-Filter zur Verfügung stellt, suche online nach kostenlosen VST-Alternativen, die du möglicherweise in der DAW deiner Wahl installieren kannst.

Verwende in Reaper das Effektfenster, um den Audioeffekt "ReaEQ" oder einen anderen EQ hinzuzufügen.

![Einen EQ-Filter hinzufügen](/assets/develop/sounds/dynamic-sounds/step_2.png)

Wenn wir die Audio jetzt abspielen, während das EQ-Filter-Fenster geöffnet bleibt, zeigt der EQ-Filter das eingehende Audio in seiner Anzeige an.
Wir können dort viele Unebenheiten sehen.

![Das Problem identifizieren](/assets/develop/sounds/dynamic-sounds/step_3.png)

Wenn du kein ausgebildeter Tontechniker bist, geht es in diesem Teil vor allem um Experimente und "Versuch und Irrtum". Zwischen den Knoten 2 und 3 gibt es einen ziemlich harten Sprung. Verschieben wir die Knoten so, dass wir die Frequenz nur für diesen Teil senken.

![Die schlechte Frequenz gesenkt](/assets/develop/sounds/dynamic-sounds/step_4.png)

Auch andere Effekte können mit einem einfachen EQ-Filter erzielt werden. So kann z. B. durch das Abschneiden hoher und/oder niedriger Frequenzen der Eindruck von über Funk übertragenen Sounds entstehen.

Du kannst auch mehrere Audiodateien übereinanderlegen, die Tonhöhe ändern, etwas Hall hinzufügen oder aufwändigere Soundeffekte wie "Bit-Crusher" verwenden. Sounddesign kann Spaß machen, vor allem, wenn man zufällig gut klingende Variationen seines Tons findet. Experimentieren ist angesagt, und vielleicht wird dein Sound am Ende sogar besser als zuvor.

Wir fahren nur mit dem EQ-Filter fort, mit dem wir die problematische Frequenz herausgeschnitten haben.

### Vergleich {#comparison}

Vergleichen wir nun die Originaldatei mit der bereinigten Version.

Im Originalton ist ein deutliches Brummen und Piepen zu hören, das möglicherweise von einem elektrischen Element des Motors stammt.

<audio controls>
    <source src="/assets/develop/sounds/dynamic-sounds/step_5_first.ogg" type="audio/ogg">
    
    Dein Browser untersützt das Audioelement nicht.
</audio>

Mit einem EQ-Filter konnten wir ihn fast vollständig entfernen. Es ist auf jeden Fall angenehmer anzuhören.

<audio controls>
    <source src="/assets/develop/sounds/dynamic-sounds/step_5_second.ogg" type="audio/ogg">
    
    Dein Browser untersützt das Audioelement nicht.
</audio>

### Eine Audioschleife erstellen {#making-it-loop}

Wenn wir den Sound bis zum Ende abspielen lassen und dann wieder von vorne beginnen, können wir den Übergang deutlich hören. Ziel ist es, dies durch einen sanften Übergang zu beseitigen.

Schneide zunächst ein Stück vom Ende aus, das so groß ist, wie der Übergang sein soll, und setze es an den Anfang einer neuen Audiospur.
In Reaper kannst du das Audiomaterial teilen, indem du einfach den Mauszeiger an die Position des Schnitts bewegst und <kbd>S</kbd> drückst.

![Das Ende abschneiden und zur neuen Spur bewegen](/assets/develop/sounds/dynamic-sounds/step_6.png)

Möglicherweise musst du den EQ-Audioeffekt der ersten Audiospur auch auf die zweite kopieren.

Lass nun das Endstück der neuen Spur ausblenden und den Anfang der ersten Audiospur einblenden.

![Wiederholung mit Überblendung von Audiospuren](/assets/develop/sounds/dynamic-sounds/step_7.png)

### Exportieren {#exporting}

Exportiere die Audio mit den zwei Audiospuren, aber mit nur einem Audiokanal (Mono) und erstelle ein neues `SoundEvent` für diese `.ogg` Datei in deinem Mod.
Wenn du dir nicht sicher bist, wie das geht, sieh dir die Seite [Benutzerdefinierte Sounds erstellen](../sounds/custom) an.

Dies ist nun die fertige Audioschleife des Motors für das `SoundEvent` namens `ENGINE_LOOP`.

<audio controls>
    <source src="/assets/develop/sounds/dynamic-sounds/step_8.ogg" type="audio/ogg">
    
    Dein Browser untersützt das Audioelement nicht.
</audio>

## Eine `SoundInstance` nutzen {#using-a-soundinstance}

Um Sounds auf der Client-Seite abzuspielen, wird eine `SoundInstance` benötigt. Diese verwenden jedoch weiterhin `SoundEvent`.

Wenn du nur so etwas wie einen Klick auf ein UI-Element abspielen willst, gibt es bereits die bestehende Klasse `SimpleSoundInstance`.

Beachte, dass dies nur auf dem spezifischen Client abgespielt wird, der diesen Teil des Codes ausgeführt hat.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/ExampleModDynamicSound.java)

::: warning

Bitte beachte, dass die Klasse `AbstractSoundInstance`, von der `SoundInstance` erbt, die Annotation `@Environment(EnvType.CLIENT)` hat.

Dies bedeutet, dass diese Klasse (und alle ihre Unterklassen) nur auf der Client-Seite zur Verfügung stehen werden.

Wenn du versuchst, das in einem logischen serverseitigen Kontext zu verwenden, wirst du das Problem im Einzelspielermodus vielleicht zunächst nicht bemerken, aber ein Server in einer Multiplayer-Umgebung wird abstürzen, da er diesen Teil des Codes überhaupt nicht finden kann.

Wenn du mit diesen Problemen zu kämpfen hast, ist es empfehlenswert, deinen Mod mit dem [Online Vorlagen-Generator](https://fabricmc.net/develop/template/)
zu erstellen, wobei die Option `Split client and common sources` aktiviert ist.

:::

Eine `SoundInstance` kann mächtiger sein als nur das einmalige Abspielen von Sounds.

Sieh dir die Klasse `AbstractSoundInstance` an und welche Art von Werten sie speichern kann.
Neben den üblichen Lautstärke- und Tonhöhenvariablen enthält sie auch XYZ-Koordinaten und gibt an, ob dieser sich nach Beendigung des `SoundEvent` wiederholen soll.

Wenn wir dann einen Blick auf die Unterklasse `AbstractTickableSoundInstance` werfen, sehen wir, dass auch das Interface `TickableSoundInstance` eingeführt wurde, die der `SoundInstance` eine Tickfunktionalität hinzufügt.

Um diese Hilfsmittel zu nutzen, erstelle einfach eine neue Klasse für deine benutzerdefinierte `SoundInstance` und lass sie von `MovingSoundInstance` erben.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/sound/instance/CustomSoundInstance.java)

Die Verwendung einer eigenen `Entity` oder `BlockEntity` anstelle der grundlegenden `LivingEntity`-Instanz kann dir noch mehr Kontrolle geben, z.B. in der `tick()`-Methode, die auf Accessor-Methoden basiert, aber du benötigst nicht unbedingt einen Verweis auf eine solche Soundquelle. Stattdessen könntest du auch von irgendwo anders auf eine `BlockPos` zugreifen oder sogar nur einmal im Konstruktor von Hand setzen.

Denke nur daran, dass alle referenzierten Objekte in der `SoundInstance` die Versionen von der Client-Seite sind.
In bestimmten Situationen können sich die Eigenschaft einer Entität auf der logischen Serverseite von der ihres Gegenstücks auf der Clientseite unterscheiden.
Wenn du feststellst, dass deine Werte nicht übereinstimmen, stelle sicher, dass deine Werte entweder mit den S2C-Paketen `EntityDataAccessor`, `BlockEntity` der Entität oder mit vollständigen benutzerdefinierten S2C-Netzwerkpaketen synchronisiert werden.

Nachdem du deine benutzerdefinierte `SoundInstance` erstellt hast, kann sie überall verwendet werden, solange sie auf der Client-Seite mit dem Sound-Manager ausgeführt wurde.
Auf die gleiche Weise kannst du die benutzerdefinierte `SoundInstance` auch manuell stoppen, falls erforderlich.

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/ExampleModDynamicSound.java)

Die Audioschleife wird nun nur noch für den Client abgespielt, der diese SoundInstance ausgeführt hat. In diesem Fall folgt der Ton dem `LocalPlayer` selbst.

Damit ist die Erklärung zur Erstellung und Verwendung einer einfachen benutzerdefinierten `SoundInstance` abgeschlossen.

## Fortgeschrittene Sound Instanzen {#advanced-sound-instances}

::: warning

Der folgende Inhalt behandelt ein fortgeschrittenes Thema.

Zu diesem Zeitpunkt solltest du über ein solides Verständnis von Java, objektorientierter Programmierung, generischen Typen und Callback-Systemen verfügen.

Kenntnisse über `Entities`, `BlockEntities` und benutzerdefinierte Vernetzung sind ebenfalls sehr hilfreich für das Verständnis des Anwendungsfalls und der Anwendungen von fortgeschrittenen Sounds.

:::

Um ein Beispiel dafür zu geben, wie komplexere `SoundInstance`-Systeme erstellt werden können, werden wir zusätzliche Funktionen, Abstraktionen und Hilfsmethoden hinzufügen, um die Arbeit mit solchen Sounds in einem größeren Rahmen einfacher, dynamischer und flexibler zu gestalten.

### Theorie {#theory}

Lass uns darüber nachdenken, was unser Ziel mit der `SoundInstance` ist.

- Der Sound sollte in einer Schleife laufen, solange die verknüpfte benutzerdefinierte `EngineBlockEntity` läuft
- Die `SoundInstance` sollte sich bewegen, indem sie der Position ihres benutzerdefinierten `EngineBlockEntity` folgt _(Die `BlockEntity` wird sich nicht bewegen, daher könnte dies bei `Entities` nützlicher sein)_
- Wir brauchen sanfte Übergänge. Das Ein- und Ausschalten sollte so gut wie nie sofort erfolgen.
- Änderung von Lautstärke und Tonhöhe in Abhängigkeit von externen Faktoren (z. B. von der Soundquelle)

Zusammenfassend lässt sich sagen, dass wir eine Instanz einer benutzerdefinierten `BlockEntity` verfolgen, Lautstärke- und Tonhöhenwerte anpassen müssen, während die `SoundInstance` auf der Grundlage von Werten aus dieser benutzerdefinierten `BlockEntity` läuft, und "Übergangszustände" implementieren müssen.

Wenn du vorhast, mehrere verschiedene `SoundInstances` zu erstellen, die sich unterschiedlich verhalten, würde ich empfehlen, eine neue abstrakte `AbstractDynamicSoundInstance`-Klasse zu erstellen, die das Standardverhalten implementiert und die eigentlichen benutzerdefinierten `SoundInstance`-Klassen von dieser Klasse erben zu lassen.

Wenn du nur eine einzige verwenden willst, kannst du die abstrakte Superklasse weglassen und stattdessen diese Funktionalität direkt in deiner eigenen `SoundInstance`-Klasse implementieren.

Außerdem ist es eine gute Idee, einen zentralen Ort zu haben, an dem die `SoundInstance`'s verfolgt, abgespielt und gestoppt werden. Das bedeutet, dass es eingehende Signale, z.B. von benutzerdefinierten S2C-Netzwerkpaketen, verarbeiten muss, alle aktuell laufenden Instanzen auflisten und Sonderfälle behandeln muss, z.B. welche Sounds gleichzeitig abgespielt werden dürfen und welche Sounds bei Aktivierung möglicherweise andere Sounds deaktivieren könnten.
Dazu kann eine neue Klasse `DynamicSoundManager` erstellt werden, um einfach mit diesem Soundsystem zu interagieren.

Insgesamt könnte unser Soundsystem so aussehen, wenn wir fertig sind.

![Struktur des benutzerdefinierten Sound Systems](/assets/develop/sounds/dynamic-sounds/custom-dynamic-sound-handling.jpg)

::: info

Alle diese Enums, interfaces und Klassen werden neu erstellt. Passe das System und die Hilfsmittel an deinen speziellen Anwendungsfall an, wie du es für richtig hältst.
Dies ist nur ein Beispiel dafür, wie du dich solchen Themen annähern kannst.

:::

### `DynamicSoundSource` Interface {#dynamicsoundsource-interface}

Wenn du dich dafür entscheidest, eine neue, modularer, benutzerdefinierte `AbstractDynamicSoundInstance`-Klasse als Superklasse zu erstellen, möchtest du vielleicht nicht nur auf einen einzigen Typ von `Entity` verweisen, sondern auf verschiedene, oder sogar auch auf eine `BlockEntity`.

In diesem Fall ist das Nutzen von Abstraktion der Schlüssel.
eine benutzerdefinierte `BlockEntity` direkt zu referenzieren, reicht es aus, ein Interface zu verfolgen, das die Daten bereitstellt, um dieses Problem zu lösen.

In Zukunft werden wir ein benutzerdefiniertes Interface namens `DynamicSoundSource` verwenden. Es wird in allen Klassen implementiert, die diese dynamische Soundfunktionalität nutzen wollen, wie z. B. deine benutzerdefinierte `BlockEntity`, Entities oder sogar, unter Verwendung von Mixins, auf bereits existierenden Klassen, wie `Zombie`. Sie stellt im Grunde nur die notwendigen Daten der Soundquelle dar.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/sound/DynamicSoundSource.java)

Nachdem du dieses Interface erstellt hast, stelle sicher, dass du es auch in den notwendigen Klassen implementierst.

::: info

Dies ist ein Hilfsmittel, das sowohl auf der Client- als auch auf der logischen Serverseite verwendet werden kann.

Daher sollte dieses Interface in den allgemeinen Paketen statt in den reinen Client-Paketen gespeichert werden, wenn du die
Option "split sources" verwendest.

:::

### `TransitionState` Enum {#transitionstate-enum}

Wie bereits erwähnt, könnte man mit dem `SoundManager` des Clients die Ausführung von `SoundInstance` stoppen, aber das führt dazu, dass die SoundInstance sofort verstummt.
Unser Ziel ist es, bei einem Stoppsignal den Sound nicht zu stoppen, sondern eine Endphase seines "Übergangszustands" auszuführen. Erst wenn die Endphase abgeschlossen ist, sollte die benutzerdefinierte `SoundInstance` beendet werden.

Ein `TransitionState` ist ein neu erstelltes Enum, das drei Werte enthält. Sie werden verwendet, um zu verfolgen, in welcher Phase sich der Ton befinden sollte.

- `STARTING` Phase: Der Sound startet leiste, aber steigert langsam seine Lautstärke
- `RUNNING` Phase: Sound läuft normal
- `ENDING` Phase: Der Sound startet mit der ursprünglichen Lautstärke und wird langsam leister, bis er verstummt

Technisch gesehen kann ein einfaches Enum mit den Phasen ausreichen.

```java
public enum TransitionState {
    STARTING, RUNNING, ENDING
}
```

Aber wenn diese Werte über das Netzwerk gesendet werden, möchtest du vielleicht einen `Identifier` für sie definieren oder sogar andere benutzerdefinierte Werte hinzufügen.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/sound/TransitionState.java)

::: info

Auch hier gilt: Wenn du "geteilte Quellen" verwendest, musst du überlegen, wo du dieses Enum einsetzen willst.
Technisch gesehen verwenden nur die benutzerdefinierten `SoundInstance`s, die nur auf der Client-Seite verfügbar sind, diese Enum-Werte.

Wenn dieses Enum jedoch an anderer Stelle verwendet wird, z. B. in benutzerdefinierten Netzwerkpaketen, musst du dieses Enum möglicherweise auch in die allgemeinen Pakete anstelle der reinen Client-Pakete aufnehmen.

:::

### `SoundInstanceCallback` Interface {#soundinstancecallback-interface}

Dieses Interface wird als Callback genutzt. Im Moment brauchen wir nur eine `onFinished`-Methode, aber du kannst deine eigenen Methoden hinzufügen, wenn du auch andere Signale vom `SoundInstance`-Objekt senden musst.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/sound/instance/SoundInstanceCallback.java)

Implementiere dieses Interface in jeder Klasse, die in der Lage sein sollte, die eingehenden Signale zu verarbeiten, z. B. in der `AbstractDynamicSoundInstance`, die wir in Kürze erstellen werden, um die Funktionalität in der benutzerdefinierten `SoundInstance` selbst zu erzeugen.

### `AbstractDynamicSoundInstance` Klasse {#abstractdynamicsoundinstance-class}

Fangen wir nun endlich mit dem Kern des dynamischen `SoundInstance`-Systems an. `AbstractDynamicSoundInstance` ist eine kürzlich neu erstellte Klasse des Typs `abstract`.
Es implementiert die standardmäßigen Funktionen und Hilfsmittel unserer benutzerdefinierten `SoundInstances`, die von ihr erben werden.

Wir können die `CustomSoundInstance` von [früher](#using-a-soundinstance) verwenden und diese verbessern.
Anstelle der `LivingEntity` verweisen wir nun auf unsere `DynamicSoundSource`.
Zusätzlich, werden wir mehr Eigenschaften definieren.

- `TransitionState`, um die aktuelle Phase zu verfolgen
- Tick-Dauer, wie lange die Start- und Endphasen dauern sollen
- minimum und maximum Werte für die Lautstärke und Tonhöhe
- boolescher Wert, um mitzuteilen, ob diese Instanz beendet wurde und aufgeräumt werden kann
- Tick-Holder, um den Fortschritt des aktuellen Sounds zu verfolgen.
- ein Callback, welcher für das finale Aufräumen ein Signal zurück zu dem `DynamicSoundManager` sendet, wenn die `SoundInstance` tatsächlich fertig ist

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/sound/AbstractDynamicSoundInstance.java)

Lege dann die Standard-Startwerte für die benutzerdefinierte `SoundInstance` im Konstruktor der abstrakten Klasse fest.

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/sound/AbstractDynamicSoundInstance.java)

Nachdem der Konstruktor fertig ist, musst du der `SoundInstance` erlauben, zu spielen.

@[code lang=java transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/sound/AbstractDynamicSoundInstance.java)

Jetzt kommt der wichtige Teil für diese dynamische `SoundInstance`. Je nachdem, wie die Instanz gerade tickt, kann sie verschiedene Werte und Verhaltensweisen anwenden.

@[code lang=java transcludeWith=:::4](@/reference/latest/src/client/java/com/example/docs/sound/AbstractDynamicSoundInstance.java)

Wie du sehen kannst, haben wir die Lautstärke- und Tonhöhenmodulation hier noch nicht angewendet. Wir wenden nur das gemeinsame Verhalten an.
In dieser `AbstractDynamicSoundInstance` Klasse stellen wir also nur die Grundstruktur und die Werkzeuge für die Unterklassen zur Verfügung, die selbst entscheiden können, welche Art der Klangmodulation sie tatsächlich anwenden wollen.

Werfen wir also einen Blick auf einige Beispiele für solche Klangmodulationsmethoden.

@[code lang=java transcludeWith=:::5](@/reference/latest/src/client/java/com/example/docs/sound/AbstractDynamicSoundInstance.java)

Wie du sehen kannst, helfen normalisierte Werte in Kombination mit linearer Interpolation (lerp) dabei, die Werte an die bevorzugten Audio-Grenzwerte anzupassen.
Denk daran, dass du, wenn du mehrere Methoden hinzufügst, die denselben Wert ändern, beobachten und anpassen musst, wie sie miteinander zusammenarbeiten.

Jetzt müssen wir nur noch die verbleibenden Hilfsmethoden hinzufügen und wir sind fertig mit der Klasse `AbstractDynamicSoundInstance`.

@[code lang=java transcludeWith=:::6](@/reference/latest/src/client/java/com/example/docs/sound/AbstractDynamicSoundInstance.java)

### Beispiel `SoundInstance` Implementation {#example-soundinstance-implementation}

Wenn wir uns die eigentliche benutzerdefinierte Klasse `SoundInstance` ansehen, die von der neu erstellten Klasse `AbstractDynamicSoundInstance` abgeleitet ist, müssen wir uns nur überlegen, unter welchen Bedingungen der Klang zum Stillstand kommt und welche Klangmodulation wir anwenden wollen.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/sound/instance/EngineSoundInstance.java)

### `DynamicSoundManager` Klasse {#dynamicsoundmanager-class}

Wir haben [früher](#using-a-soundinstance) besprochen, wie man eine `SoundInstance` abspielt und stoppt. Um diese Interaktionen zu bereinigen, zu zentralisieren und zu verwalten, kannst du deinen eigenen `SoundInstance`-Handler erstellen, der auf dieser aufbaut.

Diese neue Klasse `DynamicSoundManager` wird die benutzerdefinierten `SoundInstances` verwalten, so dass sie auch nur auf der Client-Seite zur Verfügung stehen wird. Darüber hinaus sollte ein Client immer nur eine Instanz dieser Klasse zulassen. Mehrere Soundmanager für einen einzigen Client wären nicht sehr sinnvoll und würden die Interaktionen noch komplizierter machen.
Lasst uns ein ["Singleton Design Pattern"](https://refactoring.guru/design-patterns/singleton/java/example) nutzen.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/sound/DynamicSoundManager.java)

Wenn die Grundstruktur stimmt, kannst du die Methoden hinzufügen, die für die Interaktion mit dem Soundsystem erforderlich sind.

- Sounds abspielen
- Sounds stoppen
- Prüfen, ob ein Sound gerade abgespielt wird

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/sound/DynamicSoundManager.java)

Anstatt nur eine Liste aller aktuell spielenden `SoundInstances` zu haben, könnte man auch verfolgen, welche Soundquellen welche Sounds spielen.
So würde es beispielsweise keinen Sinn machen, wenn ein Motor zwei Motorengeräusche gleichzeitig abspielt, während mehrere Motoren, die ihre jeweiligen Motorengeräusche abspielen, einen zulässigen Sonderfall darstellen. Der Einfachheit halber haben wir nur eine `List<AbstractDynamicSoundInstance>` erstellt, aber in vielen Fällen könnte eine `HashMap` aus `DynamicSoundSource` und einer `AbstractDynamicSoundInstance` eine bessere Wahl sein.

### Ein fortgeschrittenes Sound System nutzen {#using-the-advanced-sound-system}

Um dieses Soundsystem zu verwenden, musst du entweder die Methoden von `DynamicSoundManager` oder die Methoden von `SoundInstance` verwenden. Mit Hilfe von `onStartedTrackingBy` und `onStoppedTrackingBy` von Entitäten oder einfach nur benutzerdefinierten S2C-Netzwerkpacketen kannst du jetzt deine benutzerdefinierten dynamischen `SoundInstance`s starten und stoppen.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/network/ReceiveS2C.java)

Das Endprodukt kann seine Lautstärke auf der Grundlage der Soundphase anpassen, um die Übergänge zu glätten, und die Tonhöhe auf der Grundlage eines Stresswertes ändern, der von der Soundquelle stammt.

<VideoPlayer src="/assets/develop/sounds/dynamic-sounds/engine-block-sound.webm">Motor Block Entität mit dynamischen Sound Änderungen</VideoPlayer>

Du könntest einen weiteren Wert zu deiner Soundquelle hinzufügen, der einen "Überhitzungs"-Wert verfolgt und zusätzlich eine zischende `SoundInstance` langsam einblenden lässt, wenn der Wert über 0 liegt, oder ein neues Interface zu deiner benutzerdefinierten dynamischen `SoundInstance` hinzufügen, die den Soundtypen einen Prioritätswert zuweist, der bei der Auswahl des abzuspielenden Sounds hilft, wenn sie miteinander kollidieren.

Mit dem aktuellen System kannst du problemlos mehrere `SoundInstance`s auf einmal verwalten und den Ton nach deinen Bedürfnissen gestalten.
