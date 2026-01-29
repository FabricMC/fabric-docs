---
title: Tipps und Tricks für IntelliJ IDEA
description: Nützliche Informationen, um dein Projekt mit der IDE effizient zu bearbeiten und zu durchsuchen.
authors:
  - AnAwesomGuy
  - Earthcomputer
  - JR1811
prev:
  text: Quellcode in IntelliJ IDEA generieren
  link: ./generating-sources
next: false
---

Diese Seite stellt nützliche Informationen, um das Arbeiten von Entwicklern so schnell und angenehm wie möglich zu gestalten, bereit. Benutze diese je nach Bedarf.
Es kann eine gewisse Einarbeitungszeit brauchen, um sich an die Tastenkombinationen und anderen Optionen zu gewöhnen. Diese Seite kann dafür als eine Hilfe dafür genutzt werden.

::: warning

Tastenkombinationen im Text beziehen sich auf die Standardtastaturbelegung von IntelliJ IDEA, wenn nicht anders angegeben.
Schaue unter `Datei > Einstellungen > Tastaturbelegung` nach oder suche woanders nach dieser Funktion, wenn du ein anderes Tastaturlayout verwendest.

:::

## Fortbewegung in Projekten {#traversing-projects}

### Manuell {#manually}

IntelliJ bietet mehrere Möglichkeiten, sich im Projekt fortzubewegen. Falls die Quelldaten mit den `./gradlew genSources` Befehlen oder über die `Tasks > fabric > genSources` Gradle Tasks im Gradle Fenster generiert wurden, können die Quelldaten von Minecraft, innerhalb des Projektfensters `Externe Bibliotheken`, manuell nachgeschlagen werden.

![Gradle Tasks](/assets/develop/misc/using-the-ide/traversing_01.png)

Die Quelldaten von Minecraft können mit dem Suchbegriff `net.minecraft` im Projektfensters Externe Bibliotheken gefunden werden.
Wenn dein Projekt geteilte Quellen aus dem Online [Template Modgenerator](https://fabricmc.net/develop/template) verwendet, gibt es zwei Quellen, wie durch den Namen (client/common) angegeben.
Zusätzlich werden auch andere Quellen von Projekten, Bibliotheken und Abhängigkeiten, die über die Datei `build.gradle` importiert werden, verfügbar sein.
Diese Methode wird häufig bei der Suche nach Assets, Tags und anderen Dateien verwendet.

![Externe Bibliothek](/assets/develop/misc/using-the-ide/traversing_02_1.png)

![Geteilte Quellen](/assets/develop/misc/using-the-ide/traversing_02_2.png)

### Suchen {#search}

Durch zweimaliges Drücken von <kbd>Shift</kbd> öffnet sich ein Suchfenster. Dort kannst du nach den Dateien und Klassen deines Projekts suchen. Durch Aktivieren des Kontrollkästchens `include non-project items` oder durch zweimaliges Drücken von <kbd>Shift</kbd> wird nicht nur im eigenen Projekt gesucht, sondern auch in anderen, z. B. in den Externen Bibliotheken.

Du kannst auch die Tastenkombination <kbd>⌘/STRG</kbd>+<kbd>N</kbd> zur Suche von Klassen und <kbd>⌘/STRG</kbd>+<kbd>Shift</kbd>+<kbd>N</kbd> zur Suche aller _Dateien_ nutzen.

![Suchfenster](/assets/develop/misc/using-the-ide/traversing_03.png)

### Letzte Fenster {#recent-window}

Ein weiteres nützliches Werkzeug in IntelliJ ist das Fenster `Recent`. Du kannst es mit der Tastenkombination <kbd>⌘/STRG</kbd>+<kbd>E</kbd> öffnen.
Dort kannst du zu den Dateien springen, die du bereits besucht hast, und Werkzeugfenster öffnen, z. B. das Fenster [Struktur](#structure-of-a-class) oder [Lesezeichen](#bookmarks).

![Letzte Fenster](/assets/develop/misc/using-the-ide/traversing_04.png)

## Fortbewegung in Code {#traversing-code}

### Zur Definition / Verwendung springen {#jump-to-definition-usage}

Wenn du die Definition oder Verwendung von Variablen, Methoden, Klassen und anderen Elementen überprüfen möchtest, kannst du <kbd>⌘/STRG</kbd>+<kbd>Linksklick / B</kbd> drücken oder <kbd>Mittlerer Mausklick</kbd> (Mausrad drücken) auf deren Namen verwenden. Auf diese Weise vermeidest du langes Scrollen oder eine manuelle Suche nach einer Definition, die sich in einer anderen Datei befindet.

Du kannst außerdem <kbd>⌘/STRG</kbd>+<kbd>⌥/Shift</kbd>+<kbd>Linklsklick / B</kbd> verwenden, um alle Implementationen einer Klasse oder eines Interfaces zu sehen.

### Lesezeichen {#bookmarks}

Du kannst Codezeilen, Dateien oder sogar geöffnete Editor-Registerkarten mit Lesezeichen versehen.
Besonders bei der Recherche von Quellcodes kann es helfen, Stellen zu markieren, die man in Zukunft schnell wiederfinden möchte.

Klicke entweder mit der rechten Maustaste auf eine Datei im Projektfenster, auf eine Registerkarte im Editor oder auf die Zeilennummer in einer Datei.
Das Anlegen von `Mnemonic Bookmarks` ermöglicht es dur, schnell zu diesen Bookmarks zurück zu wechseln, indem du die Tastenkombination <kbd>⌘/STRG</kbd> und die von dir gewählte Ziffer verwendest.

![Lesezeichen setzen](/assets/develop/misc/using-the-ide/traversing_05.png)

Es ist möglich, im Fenster `Bookmarks` mehrere Lesezeichenlisten gleichzeitig zu erstellen, wenn du sie trennen oder ordnen möchtest.
[Haltepunkte](../../debugging#breakpoints) werden ebenfalls dort angezeigt.

![Lesezeichen Fenster](/assets/develop/misc/using-the-ide/traversing_06.png)

## Klassen analysieren {#analyzing-classes}

### Klassenstruktur {#structure-of-a-class}

Durch das Öffnen des Fensters `Struktur` (<kbd>⌘/Alt</kbd>+<kbd>7</kbd>) erhältst du einen Überblick über deine aktuell aktive Klasse. Du kannst sehen, welche Klassen und Enums sich in dieser Datei befinden, welche Methoden implementiert wurden und welche Felder und Variablen deklariert sind.

Manchmal kann es auch hilfreich sein, die Option `Inherited` oben in den Ansichtsoptionen zu aktivieren, wenn man nach potenziellen Methoden sucht, die man überschreiben kann.

![Struktur Fenster](/assets/develop/misc/using-the-ide/analyzing_01.png)

### Typenhierarchie einer Klasse {#type-hierarchy-of-a-class}

Indem du den Cursor auf einen Klassennamen setzt und <kbd>⌘/STRG</kbd>+<kbd>H</kbd> drückst, kannst du ein neues Typenhierarchie-Fenster öffnen, das alle Eltern- und Kindklassen anzeigt.

![Typenhierachie Fenster](/assets/develop/misc/using-the-ide/analyzing_02.png)

## Code-Utility {#code-utility}

### Code Vervollständigung {#code-completion}

Die Code-Vervollständigung sollte standardmäßig aktiviert sein. Du bekommst die Empfehlungen automatisch beim Schreiben deines Codes.
Wenn du sie versehentlich geschlossen oder den Mauszeiger an eine neue Stelle bewegt hast, kannst du sie mit <kbd>⌘/STRG</kbd>+<kbd>Leertaste</kbd> wieder öffnen.

Wenn du zum Beispiel Lambdas verwendest, kannst du sie mit dieser Methode schnell schreiben.

![Lambda mit vielen Parametern](/assets/develop/misc/using-the-ide/util_01.png)

### Code Generierung {#code-generation}

Das Menü Generieren kannst du schnell durch <kbd>Alt</kbd>+<kbd>Einfügen</kbd> (<kbd>⌘ Befehl</kbd>+<kbd>N</kbd> auf macOS) aufgerufen werden oder indem du oben auf `Code` gehst und `Generieren` auswählst.
In einer Java-Datei kannst du Konstruktoren, Getter, Setter, überschreibende oder implementierende Methoden und vieles mehr generrieren.
Du kannst auch Zugriffs- und Aufrufmethoden generieren, wenn du das [Minecraft Development-Plugin](./setting-up#installing-idea-plugins) installiert hast.

Zusätzlich kannst du Methoden mit <kbd>⌘/STRG</kbd>+<kbd>O</kbd> überschreiben und mit <kbd>⌘/STRG</kbd>+<kbd>I</kbd> implementieren.

![Menü zur Codegenerierung in einer Java-Datei](/assets/develop/misc/using-the-ide/generate_01.png)

In einer Java-Testdatei erhälst du Optionen, um die entsprechenden Testmethoden zu generieren, wie folgt:

![Menü zur Codegenerierung in einer Java-Testdatei](/assets/develop/misc/using-the-ide/generate_02.png)

### Parameter anzeigen {#displaying-parameters}

Die Anzeige der Parameter sollte standardmäßig aktiviert sein. Du bekommst automatisch die Typen und Namen der Parameter, während du deinen Code schreibst.
Wenn du sie versehentlich geschlossen oder den Cursor an eine neue Stelle bewegt hast, kannst du sie mit <kbd>⌘/STRG</kbd>+<kbd>P</kbd> wieder öffnen.

Methoden und Klassen können mehrere Implementationen mit unterschiedlichen Parametern haben, was auch als Überladen bezeichnet wird. Auf diese Weise kannst du beim Schreiben des Methodenaufrufs entscheiden, welche Implementation du verwenden möchtest.

![Methodenparameter anzeigen](/assets/develop/misc/using-the-ide/util_02.png)

### Refactoring {#refactoring}

Refactoring ist der Prozess der Umstrukturierung von Code, ohne dessen Laufzeitfunktionalität zu verändern. Das sichere Umbenennen und Löschen von Teilen des Codes ist ein Teil davon, aber Dinge wie das Extrahieren von Teilen des Codes in separate Methoden und die Einführung neuer Variablen für wiederholte Code-Anweisungen werden auch als "Refactoring" bezeichnet.

Viele IDEs verfügen über ein umfangreiches Toolkit, das bei diesem Prozess hilft. In IntelliJ klicke einfach mit der rechten Maustaste auf Dateien oder Teile des Codes, um Zugriff auf die verfügbaren Refactoring-Tools zu erhalten.

![Refactoring](/assets/develop/misc/using-the-ide/refactoring_01.png)

Es ist besonders nützlich, sich an die Tastenkombination <kbd>Shift</kbd>+<kbd>F6</kbd> zu gewöhnen, da du in der Zukunft wahrscheinlich viele Dinge umbenennen wirst.
Mit dieser Funktion wird jedes Code-Vorkommen des umbenannten Codes umbenannt und bleibt funktionell gleich.

Du kannst den Code auch entsprechend deinem Codestil umformatieren.
Wähle dazu den Code aus, den du neu formatieren möchtest (wenn nichts ausgewählt ist, wird die gesamte Datei neu formatiert) und drücke <kbd>⌘/STRG</kbd>+<kbd>⌥/ALT</kbd>+<kbd>L</kbd>.
Um zu ändern, wie IntelliJ Code formatiert, siehst du die Einstellungen unter `Datei > Einstellungen > Editor > Codestil > Java`.

#### Kontextaktionen {#context-actions}

Mit Hilfe von Kontextaktionen können bestimmte Codeabschnitte kontextabhängig umgestaltet werden.
Bewege dazu einfach den Mauszeiger auf den Bereich, den du überarbeiten möchtest, und drücke <kbd>⌥/ALT</kbd>+<kbd>Enter</kbd> oder klicke Sie auf die Glühbirne auf der linken Seite.
Es erscheint ein Popup-Fenster mit Kontextaktionen, die für den ausgewählten Code verwendet werden können.

![Beispiel von Kontextaktionen](/assets/develop/misc/using-the-ide/context_actions_01.png)

![Beispiel von Kontextaktionen](/assets/develop/misc/using-the-ide/context_actions_02.png)

### Suchen und Ersetzen von Dateiinhalten {#search-and-replace-file-content}

Manchmal sind einfachere Werkzeuge erforderlich, um Code-Vorkommen zu bearbeiten.

| Tastenkombination                               | Funktion                                                                                         |
| ----------------------------------------------- | ------------------------------------------------------------------------------------------------ |
| <kbd>⌘/STRG</kbd>+<kbd>F</kbd>                  | Finde in der aktuellen Datei                                                                     |
| <kbd>⌘/STRG</kbd>+<kbd>R</kbd>                  | Ersetze in der aktuellen Datei                                                                   |
| <kbd>⌘/STRG</kbd>+<kbd>Shift</kbd>+<kbd>F</kbd> | Finde in einem größeren Bereich (kann spezifische Dateitypmaske einstellen)   |
| <kbd>⌘/STRG</kbd>+<kbd>Shift</kbd>+<kbd>R</kbd> | Ersetze in einem größeren Bereich (kann spezifische Dateitypmaske einstellen) |

Falls verwendet, ermöglichen alle diese Werkzeuge einen spezifischeren Mustervergleich durch [Regex](https://en.wikipedia.org/wiki/Regular_expression).

![Regex Ersetzung](/assets/develop/misc/using-the-ide/search_and_replace_01.png)

### Andere nützliche Tastenkombinationen {#other-keybinds}

Wenn du einen Text markierst und <kbd>⌘/STRG</kbd>+<kbd>Shift</kbd>+<kbd>↑ Nach oben / ↓ Nach unten</kbd> verwendest, kannst du den markierten Text nach oben oder unten verschieben.

In IntelliJ ist die Tastenkombination für `Redo` nicht unbedingt die übliche <kbd>⌘/STRG</kbd>+<kbd>Y</kbd> (Zeile löschen).
Stattdessen kann es <kbd>⌘/STRG</kbd>+<kbd>Shift</kbd>+<kbd>Z</kbd> sein. Du kannst dies in der **Tastaturbelegung** ändern.

Weitere Tastenkombinationen findest du in der [IntelliJ-Dokumentation](https://www.jetbrains.com/help/idea/mastering-keyboard-shortcuts.html).

## Kommentare {#comments}

Guter Code sollte leicht lesbar und [selbst-dokumentierend](https://bytedev.medium.com/code-comment-anti-patterns-and-why-the-comment-you-just-wrote-is-probably-not-needed-919a92cf6758) sein.
Die Wahl aussagekräftiger Namen für Variablen, Klassen und Methoden kann sehr hilfreich sein, aber manchmal sind Kommentare notwendig, um Notizen zu hinterlassen oder **vorübergehend** Code zum Testen zu deaktivieren.

Um Code schneller auszukommentieren, kannst du einen Text markieren und die Tastenkombinationen <kbd>⌘/STRG</kbd>+<kbd>/</kbd> (Zeilenkommentar) und <kbd>⌘/STRG</kbd>+<kbd>⌥/Shift</kbd>+<kbd>/</kbd> (Blockkommentar) verwenden.

Jetzt kannst du den erforderlichen Code markieren (oder einfach den Mauszeiger darauf halten) und die Tastenkombinationen verwenden, um den Abschnitt auszukommentieren.

```java
// private static final int PROTECTION_BOOTS = 2;
private static final int PROTECTION_LEGGINGS = 5;
// private static final int PROTECTION_CHESTPLATE = 6;
private static final int PROTECTION_HELMET = 1;
```

```java
/*
ModItems.initialize();
ModSounds.initializeSounds();
ModParticles.initialize();
*/

private static int secondsToTicks(float seconds) {
    return (int) (seconds * 20 /*+ 69*/);
}
```

### Code-Faltung {#code-folding}

In IntelliJ kannst du neben den Zeilennummern kleine Pfeil-Symbole sehen.
Diese können verwendet werden, um Methoden, if-Anweisungen, Klassen und viele andere Dinge vorübergehend auszublenden, wenn du nicht aktiv an ihnen arbeitest.
Um einen benutzerdefinierten Block zu erstellen, der eingeklappt werden kann, verwende die Kommentare `region` und `endregion`.

```java
// region collapse block name
    ModBlocks.initialize();
    ModBlockEntities.registerBlockEntityTypes();
    ModItems.initialize();
    ModSounds.initializeSounds();
    ModParticles.initialize();
// endregion
```

![Einklappen von Regionen](/assets/develop/misc/using-the-ide/comments_02.png)

::: warning

Wenn du feststellst, dass du zu viele davon verwendest, solltest du deinen Code überarbeiten, um ihn lesbarer zu machen!

:::

### Den Formatierer deaktivieren {#disabling-formatter}

Kommentare können den Formatierer auch während der oben erwähnten Codeumstrukturierung deaktivieren, indem sie ein Codestück wie folgt umschließen:

```java
//formatter:off (disable formatter)
    public static void disgustingMethod() { /* ew this code sucks */ }
//formatter:on (re-enable the formatter)
```

### Unterdrückung von Inspektionen {#noinspection}

`//noinspection` Kommentare können außerdem auch dazu genutzt werden um Inspektionen und Warnungen zu unterdrücken.
Sie sind funktionell identisch mit der Annotation `@SuppressWarnings`, jedoch ohne die Einschränkung, dass es sich um eine Annotation handelt, und können für Anweisungen verwendet werden.

```java
// below is bad code and IntelliJ knows that

@SuppressWarnings("rawtypes") // annotations can be used here
List list = new ArrayList();

//noinspection unchecked (annotations cannot be here so we use the comment)
this.processList((List<String>)list);

//noinspection rawtypes,unchecked,WriteOnlyObject (you can even suppress multiple!)
new ArrayList().add("bananas");
```

::: warning

Wenn du merkst, dass du zu viele Warnungen unterdrückst, solltest du deinen Code so umschreiben, dass er nicht so viele Warnungen erzeugt!

:::

### TODO und FIXME Notizen {#todo-and-fixme-notes}

Bei der Arbeit am Code kann es hilfreich sein, Notizen zu hinterlassen, was noch zu erledigen ist. Manchmal entdeckst du auch ein potenzielles Problem im Code, aber du willst nicht aufhören, dich auf das aktuelle Problem zu konzentrieren. Verwende in diesem Fall die Kommentare `TODO` oder `FIXME`.

![TODO und FIXME Kommentare](/assets/develop/misc/using-the-ide/comments_03.png)

IntelliJ behält sie im `TODO`-Fenster im Auge und kann dich benachrichtigen, wenn du Code committen willst, der diese Art von Kommentaren verwendet.

![TODO und FIXME Kommentar](/assets/develop/misc/using-the-ide/comments_04.png)

![Kommentar mit TODO](/assets/develop/misc/using-the-ide/comments_05.png)

### JavaDocs {#javadocs}

Eine gute Möglichkeit, deinen Code zu dokumentieren, ist die Verwendung von JavaDoc.
JavaDocs liefern nicht nur nützliche Informationen zur Implementation von Methoden und Klassen, sondern sind auch tief in IntelliJ integriert.

Wenn du mit dem Mauszeiger über Methoden- oder Klassennamen fährst, die mit JavaDoc-Kommentaren versehen sind, werden diese Informationen in deren Informationsfenster angezeigt.

![JavaDoc](/assets/develop/misc/using-the-ide/comments_06.png)

Um zu beginnen, schreibe einfach `/**` über die Methoden- oder Klassendefinition und drücke die <kbd>Enter</kbd>. IntelliJ erzeugt automatisch Zeilen für den Rückgabewert und die Parameter, aber du kannst sie nach Belieben ändern. Es sind viele benutzerdefinierte Funktionen verfügbar, und du kannst bei Bedarf auch HTML verwenden.

Minecraft's `ScreenHandler` Klasse hat einige Beispiele. Um die Renderansicht umzuschalten, verwende die Stifttaste neben den Zeilennummern.

![JavaDoc Bearbeitung](/assets/develop/misc/using-the-ide/comments_07.png)

## Bytecode ansehen {#viewing-bytecode}

Das Anzeigen von Bytecode ist beim Schreiben von Mixins erforderlich. Du kannst den Bytecode einer Bibliotheksklasse (z. B. einer Minecraft-Klasse) anzeigen, indem du die Klasse im Editor öffnest und dann im Menü `Show` die Option `Show Bytecode` auswählst.

!["Show bytecode" Schaltfläche im "View" Menü](/assets/develop/getting-started/intellij/show_bytecode.png)

![Bytecode von java/lang/String.class](/assets/develop/getting-started/intellij/bytecode_example.png)

## IntelliJ weiter optimieren {#optimizing-intellij-further}

Es gibt noch viele weitere Abkürzungen und praktische kleine Tricks, die den Rahmen dieser Seite sprengen würden.
Jetbrains hat viele gute Vorträge, Videos und Dokumentationsseiten darüber, wie du deinen Arbeitsbereich weiter anpassen kannst.

### PostFix Vervollständigung {#postfix-completion}

Verwende die PostFix Vervollständigung, um Code nach dem Schreiben schnell zu ändern. Häufig verwendete Beispiele sind `.not`, `.if`, `.var`, `.null`, `.nn`, `.for`, `.fori`, `.return` und `.new`.
Neben den bereits vorhandenen kannst du in den Einstellungen von IntelliJ auch eigene erstellen.

<VideoPlayer src="https://youtu.be/wvo9aXbzvy4">IntelliJ IDEA Profi-Tipps: Postfix-Vervollständigung auf YouTube</VideoPlayer>

### Live-Vorlagen {#live-templates}

Verwende Live-Vorlagen, um deinen eigenen Vorlagen-Code schneller zu generieren.

<VideoPlayer src="https://youtu.be/XhCNoN40QTU">IntelliJ IDEA Profi-Tipps: Live-Templates auf YouTube</VideoPlayer>

### Mehr Tipps und Tricks {#more-tips}

Anton Arhipov von Jetbrains hielt auch einen ausführlichen Vortrag über Regex Matching, Code Vervollständigung, Debugging und viele andere Themen in IntelliJ.

<VideoPlayer src="https://youtu.be/V8lss58zBPI">IntelliJ Vortrag von Anton Arhipov auf YouTube</VideoPlayer>

Für weitere Informationen, sieh dir [Jetbrains Tips & Tricks Seite](https://blog.jetbrains.com/idea/category/tips-tricks) und [IntelliJ's Dokumentation](https://www.jetbrains.com/help/idea/getting-started) an.
Die meisten ihrer Beiträge sind auch auf das Ökosystem von Fabric anwendbar.
