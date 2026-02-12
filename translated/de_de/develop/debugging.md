---
title: Mods debuggen
description: Lerne alles über Logging, Haltepunkte und andere nützliche Debugging-Funktionen.
authors:
  - its-miroma
  - JR1811
---

In der Programmierung stoßen selbst die Besten auf Probleme, Fehler und Irrtümer.

Dieser Leitfaden beschreibt einige allgemeine Schritte, die du unternehmen kannst, um diese Probleme möglicherweise selbst zu identifizieren und zu beheben, auch ohne die Hilfe anderer. Probleme selbst zu lösen kann dir viele Dinge beibringen und ist zudem sehr befriedigend.

Wenn du jedoch nicht weiterkommst und selbst keine Lösung findest, ist es kein Problem, andere um Hilfe zu bitten!

## Konsole und LOGGER {#console-and-logger}

Der einfachste und schnellste Weg, um Probleme zu lokalisieren ist das Logging zur der Konsole.

Werte können dort zur Laufzeit ausgegeben werden, wodurch der Entwickler über den aktuellen Status des Codes informiert wird und Änderungen und potenzielle Fehler leicht analysiert werden können.

In der `ModInitializer`-implementierenden Einstiegsklasse des Mods wird standardmäßig ein `LOGGER` definiert, um die gewünschte Ausgabe auf die Konsole zu schreiben.

@[code lang=java transcludeWith=:::problems:basic-logger-definition](@/reference/latest/src/main/java/com/example/docs/debug/ExampleModDebug.java)

Wenn du an irgendeiner Stelle im Code einen Wert für etwas wissen musst, benutze diesen `LOGGER`, indem du einen `String` an seine Methoden übergibst.

@[code lang=java transcludeWith=:::problems:using-logger](@/reference/latest/src/main/java/com/example/docs/debug/TestItem.java)

Der Logger unterstützt mehrere Modi zur Ausgabe von Text auf die Konsole. Je nachdem, welchen Modus du verwendest, wird die protokollierte Zeile in verschiedenen Farben angezeigt.

```java
ExampleModDebug.LOGGER.info("Neutral, informative text...");
ExampleModDebug.LOGGER.warn("Non-critical issues..."); // [!code warning]
ExampleModDebug.LOGGER.error("Critical exceptions, bugs..."); // [!code error]
```

::: info

Alle Logger-Modi unterstützen mehrere Überladungen. Auf diese Weise kannst du weitere Informationen wie einen Stacktrace bereitstellen!

:::

Stellen wir beispielsweise sicher, dass bei der Verwendung des `TestItem` für eine Entität deren aktueller Status in der Konsole ausgegeben wird.

@[code lang=java transcludeWith=:::problems:logger-usage-example](@/reference/latest/src/main/java/com/example/docs/debug/TestItem.java)

![Konsole mit protokollierter Ausgabe](/assets/develop/debugging/debug_01.png)

In der protokollierten Zeile, kannst du folgendes finden:

- `Time` - Die Zeit, wann die protokollierte Information ausgegeben wurde
- `Thread` - Der Thread, in dem es ausgegeben wurde. Oftmals wirst du einen _**server thread**_ und einen _**render thread**_ sehen; dies gibt dir Auskunft darüber, auf welcher Seite der Code ausgeführt wurde
- `Name` - Der Name des Loggers. Es empfiehlt sich, hier deine Mod-ID zu verwenden, damit in Protokollen und Absturzberichten angezeigt wird, welcher Mod protokolliert wurde
- `Message` - Diese sollte prägnant, aber aussagekräftig sein. Beinhaltet Werte oder Kontext
- `Stack Trace` - Wenn ein Stacktrace der Exception bereitgestellt wird, kann der Logger diesen ebenfalls ausgeben

### Die Konsole sauber halten {#clean-console}

Beachte, dass all diese auch ausgegeben wird, wenn der Mod in einer anderen Umgebung verwendet wird.

Wenn die von dir protokollierten Daten nur für die Entwicklung relevant sind, kann es sinnvoll sein, eine benutzerdefinierte `LOGGER`-Methode zu erstellen und diese zu verwenden, um die Ausgabe von Daten in der Produktionsumgebung zu vermeiden.

@[code lang=java transcludeWith=:::problems:dev-logger](@/reference/latest/src/main/java/com/example/docs/debug/ExampleModDebug.java)

Wenn du dir nicht sicher bist, ob du außerhalb einer Debugging-Sitzung protokollieren sollst, gilt als Faustregel, dass du nur protokollieren solltest, wenn etwas schiefgelaufen ist. Modpack-Entwickler und -Nutzer interessieren sich nicht sonderlich für Dinge wie die Initialisierung von Items; sie möchten vielmehr wissen, ob beispielsweise ein Datenpaket nicht korrekt geladen wurde.

### Lokalisieren von Fehlern {#locating-issues}

Der Logger gibt die ID deines Mods vor der Zeile aus. Du kannst danach suchen (<kbd>⌘/CTRL</kbd>+<kbd>F</kbd>), um sie hervorzuheben.

Fehlende Assets und Texturen (der violette und schwarze Platzhalter) werden ebenfalls als Warnung in der Konsole protokolliert, wobei in der Regel die erwarteten Werte erwähnt werden.

![Fehlendes Asset](/assets/develop/debugging/missing_asset.png)

![Logger Ausgabe](/assets/develop/debugging/debug_02.png)

## Haltepunkte {#breakpoints}

Eine ausgefeiltere Methode zum Debuggen ist die Verwendung von Haltepunkten in einer IDE. Wie der Name schon sagt, werden sie verwendet, um den ausgeführten Code an bestimmten Punkten anzuhalten, damit der Zustand der Software überprüft und verwändert werden kann.

::: tip

Um Haltepunkte zu verwenden, musst du die Instanz mit der `Debug`-Option anstelle der `Run`-Option ausführen:

![Debug-Schaltfläche](/assets/develop/debugging/debug_03.png)

:::

Nehmen wir wieder unser benutzerdefiniertes Item als Beispiel. Der Wert von `CUSTOM_NAME` `DataComponentType` des Items soll sich ändern, wenn es auf einem beliebigen Steinblock verwendet wird.
In diesem Beispiel scheint der Gegenstand jedoch immer eine "Erfolgs"-Handanimation auszulösen, ohne dass sich der Name des Bruchstein zu ändern scheint.

Wie können wir diese beiden Probleme lösen? Lasst uns das untersuchen...

```java
// problematic example code:

public class TestItem extends Item {

    public TestItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player user = context.getPlayer();
        BlockPos targetPos = context.getBlockPos();
        ItemStack itemStack = context.getItemInHand();
        BlockState state = level.getBlockState(targetPos);

        if (state.is(ConventionalBlockTags.STONES)) {
            Component newName = Component.literal("[").append(state.getBlock().getName()).append(Component.literal("]"));
            itemStack.set(DataComponents.CUSTOM_NAME, newName);
            if (user != null) {
                user.displayClientMessage(Component.literal("Changed Item Name"), true);
            }
        }

        return InteractionResult.SUCCESS;
    }
}
```

Setze einen Haltepunkt, indem du auf die Zeilennummer klickst. Bei Bedarf kannst du auch mehrere gleichzeitig platzieren. Der Haltepunkt wird unmittelbar vor der Ausführung der ausgewählten Zeile angehalten.

![Haltepunkt gesetzt](/assets/develop/debugging/debug_04.png)

Dann lass die laufende Minecraft-Instanz diesen Teil des Codes ausführen. Du kannst auch während das Spiel läuft Haltepunkte setzen.

In diesem Fall muss das benutzerdefinierte Item an einem Block verwendet werden. Das Minecraft-Fenster sollte einfrieren, und in IntelliJ erscheint direkt neben dem Haltepunkt ein gelber Pfeil, der anzeigt, dass der Debugger diesen Punkt erreicht hat.

Unten sollte sich ein `Debug`-Fenster öffnen, und die Ansicht `Threads & Variables` sollte automatisch ausgewählt sein. Du kannst die Pfeiltasten im Fenster `Debug` verwenden, um den Ausführungspunkt zu verschieben. Diese Art, sich durch den Code zu bewegen, wird als "Stepping" bezeichnet.

![Debug-Steuerung](/assets/develop/debugging/debug_05.png)

| Aktion                    | Beschreibung                                                                                                                                                                                                                                                                          |
| ------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Schritt hinüber           | Macht einen Schritt zur nächsten ausgeführten Zeile, wodurch die Instanz im Wesentlichen in der Logik weiterbewegt wird                                                                                                                                                               |
| Schritt hinein            | Macht einen Schritt in eine Methode hinein, um zu zeigen, was darin geschieht. Wenn mehrere Methoden in einer Zeile stehen, kannst du durch Anklicken auswählen, welche du aufrufen möchtest. Dies ist auch für Lambdas erforderlich. |
| Ausführen bis zum Cursor  | Durchläuft die Logik, bis sie deinen Cursor im Code erreicht. Dies ist nützlich, um große Codeabschnitte zu überspringen.                                                                                                                             |
| Ausführungspunkt anzeigen | Verschiebt die Ansicht deines Codefensters an die Stelle, an der sich der Debugger gerade befindet. Dies funktioniert auch, wenn du gerade in anderen Dateien und Registerkarten arbeitest.                                                           |

::: info

Die Aktionen "Schritt hinüber" <kbd>F8</kbd> und "Schritt hinein" <kbd>F7</kbd> werden am häufigsten verwendet, mach dich mit den Tastenkombinationen vertraut!

:::

Wenn du mit der aktuellen Inspektion fertig bist, kannst du die grüne Schaltfläche `Programm fortsetzen` (<kbd>F9</kbd>) drücken. Dadurch wird die Minecraft-Instanz wieder freigegeben, und es können weitere Tests durchgeführt werden, bis ein weiterer Haltepunkt erreicht wird. Aber schauen wir uns vorerst weiter das `Debug`-Fenster an.

Oben siehst du alle derzeit laufenden Instanzen. Wenn beide laufen, kannst du zwischen der Client- und der Serverinstanz wechseln. Darunter befinden sich die Debug-Aktionen und -Steuerelemente. Du kannst auch zur `Console`-Ansicht wechseln, wenn du dir die Protokolle ansehen möchtest.

Auf der linken Seite siehst du den aktuell aktiven Thread und darunter den Stacktrace.

Auf der rechten Seite kannst du geladene Werte und Objekte inspizieren und bearbeiten. Du kannst auch mit der Maus über die Werte im Code fahren: Wenn sie im Geltungsbereich liegen und noch geladen sind, werden ihre spezifischen Werte ebenfalls in einem Popup-Fenster angezeigt.

Wenn du dich für den Inhalt eines bestimmten Objekts interessierst, kannst du das kleine Pfeilsymbol daneben verwenden. Dadurch werden alle verschachtelten Daten entfaltet.

Werte werden offensichtlich nicht geladen, wenn der Ausführungspunkt sie nicht passiert hat oder wenn sie sich in einem völlig anderen Kontext befinden.

![Ein geladener Wert](/assets/develop/debugging/debug_06.png)

Die Eingabezeile im `Debug`-Fenster kann für viele verschiedene Zwecke verwendet werden. Sie kann beispielsweise auf aktuell geladene Objekte zugreifen und Methoden darauf anwenden. Dadurch wird unten ein neuer Eintrag hinzugefügt, der die angeforderten Daten anzeigt.

![Objektanalyse](/assets/develop/debugging/debug_07.png)

Gehen wir in unserem Beispiel einen Schritt hinüber, damit die Variable `BlockState` geladen wird. Wir können nun überprüfen, ob sich der `BlockState` des Zielblocks tatsächlich im `Block`-Tag befindet.

::: tip

Drücke auf das `+`-Symbol auf der rechten Seite der Eingabezeile, um das Ergebnis für die aktuelle Debugging-Sitzung anzuheften.

:::

![Boolescher Ausdruck](/assets/develop/debugging/debug_08.png)

Wie wir sehen können, enthält das Tag `ConventionalBlockTags.STONES` keinen Bruchstein, da es dafür ein separates Tag gibt.

### Haltepunkt umschalten und Bedingungen {#breakpoint-condition-toggle}

Manchmal muss man den Code nur anhalten, wenn bestimmte Bedingungen erfüllt sind. Erstelle hierzu einen Haltepunkt und klicke mit der rechten Maustaste darauf, um dessen Einstellungen zu öffnen. Dort kannst du eine boolesche Aussage als Bedingung festlegen.

Hohle Haltepunkt-Symbole zeigen inaktive Haltepunkte an, die die aktive Minecraft-Instanz nicht anhalten. Du kannst Haltepunkte entweder im Popup-Fenster mit den Haltepunkt-Einstellungen oder einfach durch einen Klick mit der mittleren Maustaste auf den Haltepunkt selbst aktivieren oder deaktivieren.

Alle Haltepunkte werden im `Bookmarks`-Fenster von IntelliJ aufgelistet.

![Haltepunkt-Menü](/assets/develop/debugging/debug_09.png)

![Haltepunkte in Lesezeichen](/assets/develop/debugging/debug_10.png)

### Eine aktive Instanz Hotswappen {#hotswapping}

Du kannst begrenzte Änderungen am Code vornehmen, während eine Instanz läuft, indem du die Aktion `Build > Build Project` mit dem Hammer-Symbol verwendest. Du kannst das Symbol auch neben dem Dropdown-Element `Run Configuration` platzieren, indem du mit der rechten Maustaste auf den leeren Bereich in der oberen Menüleiste von IntelliJ klickst.

![Die Build-Schaltefläche zu der oberen Leiste hinzufügen](/assets/develop/debugging/debug_11.png)

Dieser Vorgang, auch als "Hotswapping" bezeichnet, erfordert, dass die Minecraft-Instanz im `Debug`-Modus statt im `Run`-Modus gestartet wird (siehe [oben](#breakpoints)).

Damit musst du die Minecraft-Instanz nicht neustarten. Außerdem lassen sich damit die Ausrichtung von Bildschirmelementen und der Ausgleich von Funktionen schneller testen.
IntelliJ wird dich benachrichtigt, wenn der "Hotswap" erfolgreich war.

![Hotswap Status Benachrichtigungen](/assets/develop/debugging/debug_12.png)

Mixins sind eine Ausnahme. Du kannst deine Laufkonfiguration so einrichten, dass sie auch zur Laufzeit geändert werden können.
Weitere Informationen findest du unter [Hotswapping Mixins](./getting-started/launching-the-game#hotswapping-mixins).

Andere Änderungen können im Spiel neu geladen werden.

- Änderungen des `assets/`-Ordner -> drücke <kbd>F3</kbd>+<kbd>T</kbd>
- changes to the `data/` folder -> nutze den `/reload`-Befehl

Um das Beispiel von vorhin abzuschließen, lasst uns der Anweisung eine Bedingung hinzufügen. Sobald wir den Haltepunkt erreichen, sehen wir, dass wir immer eine "Erfolg"-Handanimation erhalten, da wir nie etwas anderes zurückgegeben haben.

Wende die Korrekturen an und nutze Hotswapping, um die Änderungen im Spiel sofort zu sehen.

@[code lang=java transcludeWith=:::problems:breakpoints](@/reference/latest/src/main/java/com/example/docs/debug/TestItem.java)

## Logs und Abstürze {#logs-and-crashes}

Konsolen zuvor ausgeführter Instanzen werden als Protokolldateien exportiert, die sich im `logs`-Verzeichnis der Minecraft-Instanz befinden. Das neueste Protokoll wird in der Regel als `latest.log` bezeichnet.

Benutzer können diese Datei zur weiteren Überprüfung an den Entwickler des Mods weiterleiten, wie unter [Logs hochladen](../players/troubleshooting/uploading-logs) erläutert.

In der Entwicklungsumgebung findest du frühere Protokolle in dem `Project` window's `run > logs`-Ordner und Absturzberichte im `run > crash-reports`-Ordner.

## Frag die Community! {#ask-the-community}

Du weißt immer noch nicht, was los ist? Du kannst dem [Fabric Discord Server](https://discord.fabricmc.net/) beitreten und mit der Community schreiben!

Für weiterführende Fragen empfehlen wir dir auch das [offizielle Fabric-Wiki](https://wiki.fabricmc.net/start).
