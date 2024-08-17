---
title: Tipps und Tricks für die Entwicklungsumgebung
description: Nützliche Informationen, um das Bewegen und das Arbeiten im Projekt effizienter zu gestalten.
authors:
  - JR1811
---

# Tipps und Tricks für die Entwicklungsumgebung{#ide-tips-and-tricks}

Diese Seite stellt nützliche Informationen, um das Arbeiten von Entwicklern so schnell und angenehm wie möglich zu gestalten, bereit. Benutze diese je nach Bedarf.
Es kann eine gewisse Einarbeitungszeit brauchen, um sich an die Tastenkombinationen und anderen Optionen zu gewöhnen. Diese Seite kann dafür als eine Hilfe dafür genutzt werden.

:::warning
Tastenkombinationen sind hier spezifisch für Windows ausgelegt. Die meisten beziehen sich auch auf die Standardbelegung von IntelliJ, solange nichts anderes erwähnt wurde.
Tastenkombinationen können auch unter `File > Setings > Keymap` Einstellungen von IntelliJ nachgeschlagen und geändert werden.
:::

## Fortbewegung in Projekten{#traversing-projects}

### Manuell{#manually}

IntelliJ bietet mehrere Möglichkeiten, sich im Projekt fortzubewegen. Falls die Quelldaten mit den `./gradlew genSources` Befehlen oder die `Tasks > fabric > genSources` Gradle Aufgaben im Gradle Fenster benutzt wurden, können die Quelldaten von Minecraft, innerhalb des Projektfensters "External Libnraries", manuell nachgeschlagen werden.

![Gradle Task](/assets/develop/misc/using-the-ide/traversing_01.png)

Die Quelldaten von Minecraft können mit dem Suchbegriff `net.minecraft` im Projektfensters "External Libraries" gefunden werden. Wenn dein Projekt geteilte Quellen aus dem Online-[Template Modgenerator](https://fabricmc.net/develop/template/) verwendet, gibt es zwei Quellen, wie durch den Namen (client/common) angegeben. Zusätzlich werden auch andere Quellen von Projekten, Bibliotheken und Abhängigkeiten, die über die Datei `build.gradle` importiert werden, verfügbar sein. Diese Methode wird häufig bei der Suche nach Assets, Tags und anderen Dateien verwendet.

![Externe Bibliothek](/assets/develop/misc/using-the-ide/traversing_02_1.png)

![Geteilte Quellen](/assets/develop/misc/using-the-ide/traversing_02_2.png)

### Suche {#search}

Durch zweimaliges Drücken von <kbd>Shift</kbd> öffnet sich ein Suchfenster. Dort kannst du nach den Dateien und Klassen deines Projekts suchen. Durch Aktivieren des Kontrollkästchens `include non-project items` oder durch zweimaliges Drücken von <kbd>Shift</kbd> wird nicht nur im eigenen Projekt gesucht, sondern auch in anderen, z. B. in den Externen Bibliotheken.

![Suchfenster](/assets/develop/misc/using-the-ide/traversing_03.png)

### Letzte Fenster {#recent-window}

Ein weiteres nützliches Werkzeug in IntelliJ ist das Fenster `Recent`. Du kannst es mit dem Shortcut <kbd>STRG</kbd> + <kbd>E</kbd> öffnen. Dort kannst du zu den Dateien springen, die du bereits besucht hast, und Werkzeugfenster öffnen, z. B. das Fenster [Struktur](#structure-of-a-class) oder [Lesezeichen](#bookmarks).

![Letzte Fenster](/assets/develop/misc/using-the-ide/traversing_04.png)

## Fortbewegung in Code {#traversing-code}

### Zur Definition / Verwendung springen {#jump-to-definition-usage}

Wenn du entweder die Definition oder die Verwendung von Variablen, Methoden, Klassen und anderen Dingen überprüfen musst, kannst du <kbd>STRG</kbd> + <kbd>Linksklick</kbd> drücken oder <kbd>Mittlere Maustaste</kbd> (Mausrad drücken) auf dessen Namen verwenden. Auf diese Weise vermeidest du langes Scrollen oder eine manuelle Suche nach einer Definition, die sich in einer anderen Datei befindet.

### Lesezeichen {#bookmarks}

Du kannst Codezeilen, Dateien oder sogar geöffnete Editor-Registerkarten mit Lesezeichen versehen.
Besonders bei der Recherche von Quellcodes kann es helfen, Stellen zu markieren, die man in Zukunft schnell wiederfinden möchte.

Klicke entweder mit der rechten Maustaste auf eine Datei im Projektfenster, auf eine Registerkarte im Editor oder auf die Zeilennummer in einer Datei.
Das Anlegen von `Mnemonic Bookmarks` ermöglicht es dur, schnell zu diesen Bookmarks zurück zu wechseln, indem du die Tasenkombination <kbd>CTRL</kbd> und die von dir gewählte Ziffer verwendest.

![Lesezeichen setzen](/assets/develop/misc/using-the-ide/traversing_05.png)

Es ist möglich, im Fenster `Bookmarks` mehrere Lesezeichenlisten gleichzeitig zu erstellen, wenn du sie trennen oder ordnen möchtest.
Die [Haltepunkte](./basic-problem-solving#breakpoint) werden dort ebenfalls angezeigt.

![Lesezeichen Fenster](/assets/develop/misc/using-the-ide/traversing_06.png)

## Klassen analysieren {#analyzing-classes}

### Struktur einer Klasse {#structure-of-a-class}

Durch das Öffnen des Fensters `Structure` (<kbd>Alt</kbd> + <kbd>7</kbd>) erhaltest du einen Überblick über deine derzeit aktive Klasse. Du kannst sehen, welche Klassen und Enums sich in dieser Datei befinden, welche Methoden implementiert wurden und welche Felder und Variablen deklariert sind.

Manchmal kann es auch hilfreich sein, die Option `Inherited` oben in den Ansichtsoptionen zu aktivieren, wenn man nach potenziellen Methoden sucht, die man überschreiben kann.

![Struktur Fenster](/assets/develop/misc/using-the-ide/analyzing_01.png)

### Typenhierarchie einer Klasse {#type-hierarchy-of-a-class}

Indem du den Cursor auf einen Klassennamen setzt und <kbd>STRG</kbd> + <kbd>H</kbd> drückSt, kannst du ein neues Typenhierarchie-Fenster öffnen, das alle Eltern- und Kindklassen anzeigt.

![Typenhierachie Fenster](/assets/develop/misc/using-the-ide/analyzing_02.png)

## Code Utility {#code-utility}

### Code Vervollständigung {#code-completion}

Die Code-Vervollständigung sollte standardmäßig aktiviert sein. Du bekommst die Empfehlungen automatisch beim Schreiben deines Codes. Wenn du sie versehentlich geschlossen oder den Cursor an eine neue Stelle bewegt hast, kannst du sie mit <kbd>STRG</kbd> + <kbd>Leertaste</kbd> wieder öffnen.

Wenn du zum Beispiel Lambdas verwendest, kannst du sie mit dieser Methode schnell schreiben.

![Lambda mit vielen Parametern](/assets/develop/misc/using-the-ide/util_01.png)

### Code Generierung {#code-generation}

Das Menü Generate kann mit <kbd>⌘/CTRL</kbd><kbd>N</kbd> schnell aufgerufen werden.
In einer Java-Datei kannst du Konstruktoren, Getter, Setter, überschreibende oder implementierende Methoden und vieles mehr generrieren.
Du kannst auch Accessors und Invokers erzeugen, wenn du das [Minecraft Development Plugin](./getting-started/setting-up-a-development-environment#minecraft-development) installiert hast.

Zusätzlich kannst du Methoden mit <kbd>⌘/CTRL</kbd><kbd>O</kbd> überschreiben und mit <kbd>⌘/CTRL</kbd><kbd>I</kbd> implementieren.

![Menü zur Codegenerierung in einer Java-Datei](/assets/develop/misc/using-the-ide/generate_01.png)

In einer Java-Testdatei erhälst du Optionen, um die entsprechenden Testmethoden zu generieren, wie folgt:

![Menü zur Codegenerierung in einer Java-Testdatei](/assets/develop/misc/using-the-ide/generate_02.png)

### Parameter darstellen {#displaying-parameters}

Die Anzeige der Parameter sollte standardmäßig aktiviert sein. Du bekommst automatisch die Typen und Namen der Parameter, während du deinen Code schreibst.
Wenn du sie versehentlich geschlossen oder den Cursor an eine neue Stelle bewegt hast, kannst du sie mit <kbd>STRG</kbd> + <kbd>P</kbd> wieder öffnen.

Methoden und Klassen können mehrere Implementationen mit unterschiedlichen Parametern haben, was auch als Überladen bezeichnet wird. Auf diese Weise kannst du beim Schreiben des Methodenaufrufs entscheiden, welche Implementation du verwenden möchtest.

![Methodenparameter anzeigen](/assets/develop/misc/using-the-ide/util_02.png)

### Refactoring {#refactoring}

Refactoring ist der Prozess der Umstrukturierung von Code, ohne dessen Laufzeitfunktionalität zu verändern. Das sichere Umbenennen und Löschen von Teilen des Codes ist ein Teil davon, aber auch Dinge wie das Extrahieren von Teilen des Codes in separate Methoden und die Einführung neuer Variablen für wiederholte Codeanweisungen werden als "Refactoring" bezeichnet.

Viele IDEs verfügen über ein umfangreiches Toolkit, das bei diesem Prozess hilft. In IntelliJ klicke einfach mit der rechten Maustaste auf Dateien oder Teile des Codes, um Zugriff auf die verfügbaren Refactoring-Tools zu erhalten.

![Refactoring](/assets/develop/misc/using-the-ide/refactoring_01.png)

Es ist besonders nützlich, sich an die Tastenkombination <kbd>Shift</kbd> + <kbd>F6</kbd> zu gewöhnen, da du in der Zukunft viele Dinge umbenennen wirst. Mit dieser Funktion wird jedes Code-Vorkommen des umbenannten Codes umbenannt und bleibt funktionell gleich.

### Suchen und Ersetzen von Dateiinhalten {#search-and-replace-file-content}

Manchmal sind einfachere Werkzeuge erforderlich, um Code-Vorkommen zu bearbeiten.

| Tastenkombination                                 | Funktion                                                                                         |
| ------------------------------------------------- | ------------------------------------------------------------------------------------------------ |
| <kbd>STRG</kbd> + <kbd>F</kbd>                    | Finde in der aktuellen Datei                                                                     |
| <kbd>STRG</kbd> + <kbd>R</kbd>                    | Ersetze in der aktuellen Datei                                                                   |
| <kbd>STRG</kbd> + <kbd>Shift</kbd> + <kbd>F</kbd> | Finde in einem größeren Bereich (kann spezifische Dateitypmaske einstellen)   |
| <kbd>STRG</kbd> + <kbd>Shift</kbd> + <kbd>R</kbd> | Ersetze in einem größeren Bereich (kann spezifische Dateitypmaske einstellen) |

Wenn sie aktiviert sind, ermöglichen alle diese Werkzeuge einen spezifischeren Mustervergleich mit "[Regex](https://en.wikipedia.org/wiki/Regular_expression)".

![Regex Ersetzung](/assets/develop/misc/using-the-ide/search_and_replace_01.png)

## Kommentare {#comments}

Guter Code sollte leicht lesbar und [selbst-dokumentierend](https://bytedev.medium.com/code-comment-anti-patterns-and-why-the-comment-you-just-wrote-is-probably-not-needed-919a92cf6758) sein. Die Wahl aussagekräftiger Namen für Variablen, Klassen und Methoden kann sehr hilfreich sein, aber manchmal sind Kommentare notwendig, um Notizen zu hinterlassen oder **vorübergehend** Code zum Testen zu deaktivieren.

### Tasenkombinationen vorbereiten {#prepare-shortcuts}

Um Code schneller auszukommentieren, öffne die Einstellungen von IntelliJ, suche nach den Einträgen `Comment with Line Comment` und `Comment with Block Comment` und stelle die Tastenbelegung nach deinen Wünschen ein.

![Tastenkombinationseinstellungen](/assets/develop/misc/using-the-ide/comments_01.png)

Jetzt kannst du den erforderlichen Code markieren und die Tastenkombinationen verwenden, um den Abschnitt auszukommentieren.

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

### Regionkommentare {#region-comments}

In IntelliJ kannst du direkt neben den Zeilennummern kleine [+]- und [-]-Symbole sehen. Diese können verwendet werden, um Methoden, if-Anweisungen, Klassen und viele andere Dinge vorübergehend auszublenden, wenn du nicht aktiv an ihnen arbeitest. Um einen benutzerdefinierten Block zu erstellen, der eingeklappt werden kann, verwende die Kommentare `region` und `endregion`.

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

:::warning
Wenn du feststellst, dass du zu viele davon verwendest, solltest du deinen Code überarbeiten, um ihn lesbarer zu machen!
:::

### TODO und FIXME Notizen {#todo-and-fixme-notes}

Bei der Arbeit am Code kann es hilfreich sein, Notizen zu hinterlassen, was noch zu erledigen ist. Manchmal entdeckst du auch ein potenzielles Problem im Code, aber du willst nicht aufhören, dich auf das aktuelle Problem zu konzentrieren. Verwenden in diesem Fall die Kommentare `TODO` oder `FIXME`.

![TODO und FIXME Kommentare](/assets/develop/misc/using-the-ide/comments_03.png)

IntelliJ behält sie im `TODO`-Fenster im Auge und kann dich benachrichtigen, wenn du Code committen willst, der diese Art von Kommentaren verwendet.

![TODO und FIXME Kommentare](/assets/develop/misc/using-the-ide/comments_04.png)

![Kommentar mit TODO](/assets/develop/misc/using-the-ide/comments_05.png)

### JavaDocs {#javadocs}

Eine gute Möglichkeit, deinen Code zu dokumentieren, ist die Verwendung von JavaDoc. JavaDocs liefern nicht nur nützliche Informationen zur Implementierung von Methoden und Klassen, sondern sind auch tief in IntelliJ integriert.

Wenn du mit dem Mauszeiger über Methoden- oder Klassennamen fährst, die mit JavaDoc-Kommentaren versehen sind, werden diese Informationen in ihrem Informationsfenster angezeigt.

![JavaDoc](/assets/develop/misc/using-the-ide/comments_06.png)

Um zu beginnen, schreibe einfach `/**` über die Methoden- oder Klassendefinition und drücke die Eingabetaste. IntelliJ erzeugt automatisch Zeilen für den Rückgabewert und die Parameter, aber du kannst sie nach Belieben ändern. Es sind viele benutzerdefinierte Funktionen verfügbar, und du kannst bei Bedarf auch HTML verwenden.

Minecraft's `ScreenHandler` Klasse hat einige Beispiele. Um die Renderansicht umzuschalten, verwende die Stifttaste neben den Zeilennummern.

![JavaDoc Bearbeitung](/assets/develop/misc/using-the-ide/comments_07.png)

## IntelliJ weiter optimieren {#optimizing-intellij-further}

Es gibt noch viele weitere Abkürzungen und praktische kleine Tricks, die den Rahmen dieser Seite sprengen würden.
Jetbrains hat viele gute Vorträge, Videos und Dokumentationsseiten darüber, wie du deinen Arbeitsbereich weiter anpassen kannst.

### PostFix Vervollständigung {#postfix-completion}

Verwende die PostFix Vervollständigung, um Code nach dem Schreiben schnell zu ändern. Häufig verwendete Beispiele sind `.not`, `.if`, `.var`, `.null`, `.nn`, `.for`, `.fori`, `.return` und `.new`. Neben den bereits vorhandenen kannst du in den Einstellungen von IntelliJ auch eigene erstellen.

<VideoPlayer src="https://youtu.be/wvo9aXbzvy4?si=oSI1NVuOKtYI1wev" title="IntelliJ IDEA Pro Tips: Postfix Completion on YouTube"/>

### Live-Vorlagen {#live-templates}

Verwende Live-Vorlagen, um deinen eigenen Vorlagen-Code schneller zu generieren.

<VideoPlayer src="https://youtu.be/XhCNoN40QTU?si=dGYFr2hY7lPJ6Wge" title="IntelliJ IDEA Pro Tips: Live Templates on YouTube"/>

### Mehr Tipps und Tricks {#more-tips}

Anton Arhipov von Jetbrains hielt auch einen ausführlichen Vortrag über Regex Matching, Code Vervollständigung, Debugging und viele andere Themen in IntelliJ.

<VideoPlayer src="https://youtu.be/V8lss58zBPI?si=XKl5tuUN-hCG_bTG" title="IntelliJ talk by Anton Arhipov on YouTube"/>

Weitere Informationen findest du auf der Website [Jetbrains' Tipps & Tricks](https://blog.jetbrains.com/idea/category/tips-tricks/). Die meisten ihrer Beiträge sind auch auf das Ökosystem von Fabric anwendbar.

---
