---
title: Zugriffserweiterung
description: Lerne, wie man Zugriffserweiterungen aus Klassenoptimierer-Dateien verwendet.
authors-nogithub:
  - lightningtow
  - siglong
authors:
  - Ayutac
  - cassiancc
  - CelDaemon
  - cootshk
  - Earthcomputer
  - florensie
  - froyo4u
  - haykam821
  - hYdos
  - its-miroma
  - kb-1000
  - kcrca
  - liach
  - lmvdz
  - matjojo
  - MildestToucan
  - modmuss50
  - octylFractal
  - OroArmor
  - T3sT3ro
  - Technici4n
  - TheGlitch76
  - UpcraftLP
  - YTG1234
---

Die Zugriffserweiterung ist eine Art von [Klassenoptimierung](../class-tweakers), mit der die Zugriffsbeschränkungen von Klassen, Methoden und Feldern gelockert und diese Änderung im dekompilierten Quellcode widergespiegelt werden.
Dazu gehört, dass sie öffentlich, erweiterbar und/oder veränderbar sind.

Einträge in Zugriffserweiterern können [transitiv](../class-tweakers/index#transitive-entries) sein, damit Änderungen auch für Mods sichtbar werden, die von deinem Mod abhängen.

Um auf Felder oder Methoden zuzugreifen, ist es oft sicherer und einfacher, [Zugriffs-Mixins](https://wiki.fabricmc.net/tutorial:mixin_accessors) zu verwenden,
doch gibt es zwei Situationen, in denen diese Art des Zugriffs nicht ausreicht und eine Zugriffserweiterung erforderlich ist:

- Wenn du auf eine `private`, `protected` oder package-private Klasse zugreifen musst.
- Wenn du eine `final`-Methode, eine Unterklasse oder eine `final`-Klasse überschreiben musst.

Im Gegensatz zu [Zugriffs-Mixins](https://wiki.fabricmc.net/tutorial:mixin_accessors) funktioniert die [Klassenoptimierung](../class-tweakers) jedoch nur bei den Standardklassen von Minecraft und nicht bei anderen Mods.

## Zugriffsrichtlinien {#access-directives}

Einträge von Zugriffserweiterern beginnen mit einem von drei richtlinienbasierten Schlüsselwörtern, um die Art der vorzunehmenden Änderung anzugeben.

### Zugänglich {#accessible}

`accessible` kann auf Klassen, Methoden oder Felder abzielen:

- Felder und Klassen werden öffentlich gemacht.
- Methoden werden öffentlich gemacht und sind final, wenn sie ursprünglich privat waren.

Wenn man eine Methode oder ein Feld zugänglich macht, wird auch die zugehörige Klasse zugänglich.

### Erweiterbar {#extendable}

`extendable` kann auf Klassen oder Methoden abzielen:

- Klassen werden öffentlich und nicht-final gemacht.
- Methoden werden protected und nicht-final gemacht.

Wenn man eine Methode erweiterbar macht, wird auch die zugehörige Klasse erweiterbar.

### Veränderbar {#mutable}

`mutable` kann Felder nicht-final machen.

Um ein privates, finales Feld sowohl zugänglich als auch veränderbar zu machen, musst du zwei getrennte Einträge in der Datei machen.

## Ziele spezifizieren {#specifying-targets}

Bei der Optimierung von Klassen verwenden Klassen und Interfaces ihre [internen Namen](../mixins/bytecode#class-names). Für Felder und Methoden musst du ihren Klassennamen, ihren Namen und ihre [Bytecode-Bezeichnung](../mixins/bytecode#field-and-method-descriptors) angeben.

::: tabs

== Klassen

Format:

```:no-line-numbers
<accessible / extendable>    class    <className>
```

Beispiel:

@[code lang=classtweaker:no-line-numbers transcludeWith=:::accesswidening-examples:classes:::](@/reference/latest/src/main/resources/example-mod.classtweaker)

== Methoden

Format:

```:no-line-numbers
<accessible / extendable>    method    <className>    <methodName>    <methodDescriptor>
```

Beispiel:

@[code lang=classtweaker:no-line-numbers transcludeWith=:::accesswidening-examples:methods:::](@/reference/latest/src/main/resources/example-mod.classtweaker)

== Felder

Format:

```:no-line-numbers
<accessible / mutable>    field    <className>    <fieldName>    <fieldDescriptor>
```

Beispiel:

@[code lang=classtweaker:no-line-numbers transcludeWith=:::accesswidening-examples:fields:::](@/reference/latest/src/main/resources/example-mod.classtweaker)

:::

## Einträge generieren {#generating-entries}

Das manuelle Schreiben von Einträgen von Zurgriffserweiterern ist zeitaufwendig und anfällig für menschliche Fehler. Schauen wir uns nun Werkzeuge an, die einen Teil des Prozesses vereinfachen, indem sie es ermöglichen, Einträge zu erstellen und zu kopieren.

### mcsrc.dev {#mcsrc-dev}

Verfügbar für alle Versionen mit einer [unverschleierten JAR-Datei](../migrating-mappings/index#whats-going-on-with-mappings), nämlich 1.21.11 und höher,
[mcsrc](https://mcsrc.dev) ermöglicht es dir, den Minecraft-Quellcode im Browser zu dekompilieren und zu durchsuchen sowie Mixin-, Zugriffserweiterer- oder Zugriffstransformatoren-Ziele in die Zwischenablage zu kopieren.

Um einen Zugriffserweiterer Eintrag zu kopieren, navigiere zunächst zu der Klasse, die du bearbeiten möchtest, und klicke mit der rechten Maustaste auf das gewünschte Ziel, um das Kontextmenü zu öffnen.

![Rechtsklick auf ein Ziel in mcsrc](/assets/develop/class-tweakers/access-widening/mcsrc-right-click-on-aw-target.png)

Klicke anschließend auf `Copy Class Tweaker / Access Widener` und daraufhin sollte oben auf der Seite eine Bestätigungsmeldung erscheinen.

![AW Besätigung zur Kopie in mcsrc](/assets/develop/class-tweakers/access-widening/mcsrc-aw-copy-confirmation.png)

Du kannst dann den Eintrag in deine Klassenoptimierer Datei einfügen.

### Minecraft Development Plugin (IntelliJ IDEA) {#mcdev-plugin}

Das [Minecraft Development Plugin](../getting-started/intellij-idea/setting-up#installing-idea-plugins), auch bekannt als MCDev, ist ein IntelliJ IDEA-Plugin, das bei verschiedenen Aspekten der Entwicklung von Minecraft-Mods hilft.
Du kannst beispielsweise Einträge für Zugriffserweiterer aus dem dekompilierten Quellziel in die Zwischenablage kopieren.

Um einen Zugriffserweiterer Eintrag zu kopieren, navigiere zunächst zu der Klasse, die du bearbeiten möchtest, und klicke mit der rechten Maustaste auf das gewünschte Ziel, um das Kontextmenü zu öffnen.

![Rechtsklick auf ein Ziel Mit MCDev](/assets/develop/class-tweakers/access-widening/mcdev-right-click-on-aw-target.png)

Dann, klicke auf `Copy / Paste Special` und `AW Entry`.

![Kopieren/Einfügen Besonderheit mit MCDev](/assets/develop/class-tweakers/access-widening/mcdev-copy-paste-special-menu.png)

Es sollte nun eine Bestätigungsmeldung auf dem Element erscheinen, auf das du mit der rechten Maustaste geklickt hast.

![AW Bestätigung zur Kopie mit MCDev](/assets/develop/class-tweakers/access-widening/mcdev-aw-copy-confirmation.png)

Du kannst dann den Eintrag in deine Klassenoptimierer Datei einfügen.

## Änderungen anwenden {#applying-changes}

Damit deine Änderungen übernommen werden, musst du dein Gradle-Projekt aktualisieren und die [Quellen neu generieren](../getting-started/generating-sources). Die von dir ausgewählten Elemente sollten
entsprechend deren Zugriffsbeschränkungen angepasst werden. Wenn die Änderungen nicht angezeigt werden, kannst du versuchen, [die Datei zu validieren](../class-tweakers/index#validating-the-file) und zu prüfen, ob irgendwelche Fehler auftreten.
