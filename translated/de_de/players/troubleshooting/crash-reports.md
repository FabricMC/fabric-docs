---
title: Absturzberichte
description: Erfahre, wie mit Absturzberichten umzugehen ist, und wie man sie liest.
authors:
  - IMB11
---

<!---->

::: tip

Falls du Schwierigkeiten hast, den Grund für einen Absturz herauszufinden, frage im [Fabric Discord](https://discord.fabricmc.net/) im Kanal `#player-support` oder `#server-admin-support` nach Hilfe.

:::

Absturzberichte sind ein sehr wichtiger Teil, um Probleme mit deinem Spiel oder Server zu beheben. Sie enthalten viele Informationen über den Absturz und können beim Finden der Ursache für den Absturz hilfreich sein.

## Absturzberichte Absturzberichte finden {#finding-crash-reports}

Absturzberichte werden im `crash-reports`-Verzeichnis in deinem Spiel-Verzeichnis gespeichert. Falls du einen Server nutzt, sind sie im `crash-reports`-Verzeichnis im Server-Verzeichnis.

Für Launcher von Drittanbietern solltest du deren Dokumentation verwenden, um den Ort der Absturzberichte zu finden.

Absturzberichte befinden sich an den folgenden Orten:

::: code-group

```text:no-line-numbers [Windows]
%appdata%\.minecraft\crash-reports
```

```text:no-line-numbers [macOS]
~/Library/Application Support/minecraft/crash-reports
```

```text:no-line-numbers [Linux]
~/.minecraft/crash-reports
```

:::

## Absturzberichte lesen {#reading-crash-reports}

Absturzberichte sind sehr lang und können verwirrend zu lesen sein. Allerdings enthalten sie viele Informationen über den Absturz, die beim Finden der Ursache sehr hilfreich sind.

Für diesen Leitfaden werden wir [diesen Crash-Report](/assets/players/crash-report-example.log) verwenden.

:::details Absturzbericht anzeigen

<<< @/public/assets/players/crash-report-example.log

:::

### Abschnitte des Absturzberichts {#crash-report-sections}

Absturzberichte bestehen aus mehreren Abschnitten, jeder ist mit einer Überschrift getrennt:

- `---- Minecraft Crash Report ----`, die Zusammenfassung des Berichts. Dieser Abschnitt enthält den Hauptfehler, der den Absturz verursacht hat, den Zeitpunkt an dem das Problem aufgetreten ist und den relevanten Stacktrace. Dies ist der wichtigste Abschnitt des Absturzberichts, da der Stacktrace normalerweise Referenzen zu der Mod enthält, die den Absturz verursacht hat.
- `-- Last Reload --`, dieser Abschnitt ist nicht wirklich hilfreich, es sei denn, der Absturz ist während dem Neu-Laden von Resourcen (<kbd>F3</kbd>+<kbd>T</kbd>) aufgetreten. Dieser Abschnitt enthält den Zeitpunkt des letzten Neu-Ladens, den relevanten Stacktrace und alle Fehler, die währenddessen aufgetreten sind. Diese Fehler werden normalerweise durch Ressourcenpakete ausgelöst und können ignoriert werden, wenn sie keine Probleme mit dem Spiel verursachen.
- `-- System Details --`, dieser Abschnitt enthält Informationen über dein System, wie das Betriebssystem, die Java-Version, und die Speichermenge, die dem Spiel zugewiesen wurde. Dieser Abschnitt ist nützlich, um festzustellen, ob du die korrekte Java-Version verwendest und ob du dem Spiel genug Speicher zugewiesen hast.
  - In diesem Abschnitt fügt Fabric eine eigene Zeile ein, die mit `Fabric Mods:` beginnt und darauf folgend alle installierten Mods auflistet. Dieser Abschnitt ist nützlich, um festzustellen, ob Konflikte zwischen Mods aufgetreten sein könnten.

### Den Absturzbericht herunterbrechen {#breaking-down-the-crash-report}

Da wir jetzt alle Abschnitte des Absturzberichts kennengelernt haben, können wir nun beginnen, den Absturzbericht herunterzubrechen und die Ursache des Absturzes zu finden.

Mit dem obigen verwiesenen Beispiel können wir den Absturzbericht analysieren und die Ursache für den Absturz herausfinden und welche Mods den Absturz verursachen.

Der Stack-Trace im Abschnitt `---- Minecraft Crash Report ----` ist in diesem Fall am wichtigsten, da er den Hauptfehler enthält, der den Absturz verursacht hat.

:::details Fehler anzeigen

<<< @/public/assets/players/crash-report-example.log{7}

:::

Mit der Anzahl an Mods, die sich in diesem Stacktrace befinden, kann es schwierig sein, den Schuldigen zu finden, aber das Erste, was zu tun ist, ist die Mod zu finden, die den Absturz verursacht.

In diesem Fall ist der Mod, der den Absturz verursacht hat, `snownee`, da es der erste Mod ist, der im Stack-Trace erwähnt wird.

Bei der Menge an Mods, die erwähnt wurden, könnte es jedoch bedeuten, dass es Kompatibilitätsprobleme zwischen den Mods gibt, und dass der Mod, der den Absturz verursacht hat, nicht unbedingt derjenige ist, der den Fehler verursacht hat. In diesem Fall ist es am besten, den Absturz dem Mod-Autor zu melden und ihn den Absturz untersuchen zu lassen.

## Mixin Abstürze {#mixin-crashes}

::: info

Mixins sind eine Möglichkeit für Mods, das Spiel zu verändern, ohne den Quellcode des Spiels ändern zu müssen. Sie werden von vielen Mods verwendet und sind ein sehr mächtiges Werkzeug für Mod-Entwickler.

:::

Wenn ein Mixin abstürzt, wird in der Regel das Mixin im Stacktrace erwähnt und die Klasse, die das Mixin ändert.

Methoden-Mixins enthalten `mod-id$handlerName` in dem Stacktrace, wobei `mod-id` die ID des Mods und `handlerName` der Name des Mixin-Handlers ist.

```text:no-line-numbers
... net.minecraft.class_2248.method_3821$$$mod-id$handlerName() ... // [!code focus]
```

Anhand dieser Informationen kannst du den Mod, der den Absturz verursacht hat, ausfindig machen und den Absturz an den Autor des Mods melden.

## Was macht man mit Absturzberichten {#what-to-do-with-crash-reports}

Am besten ist es, Absturzberichte auf eine Paste-Seite hochzuladen und dann den Link mit dem Mod-Autor zu teilen, entweder auf dessen Issue-Tracker oder über eine andere Form der Kommunikation (Discord usw.).

Dies ermöglicht es dem Mod-Autor, den Absturz zu untersuchen, ihn möglicherweise zu reproduzieren und das Problem zu lösen, das ihn verursacht hat.

Häufig genutzte Websites für Absturzberichte sind die folgenden:

- [GitHub Gist](https://gist.github.com/)
- [mclo.gs](https://mclo.gs/)
- [Pastebin](https://pastebin.com/)
