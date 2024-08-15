---
title: Automatisiertes Testen
description: Ein Leitfaden zum Schreiben von automatisierten Tests mit Fabric Loader JUnit.
authors:
  - kevinthegreat1
---

# Automatisiertes Testen {#automated-testing}

Diese Seite erklärt, wie man Code schreibt, um Teile des Mods automatisch zu testen. Es gibt zwei Möglichkeiten, deinen Mod automatisch zu testen: Unit Tests mit Fabric Loader JUnit oder Spieltests mit dem Gametest-Framework von Minecraft.

Unit-Tests sollten verwendet werden, um Komponenten deines Codes zu testen, wie z. B. Methoden und Hilfsklassen, während Spieltests einen tatsächlichen Minecraft-Client und -Server starten, um deine Tests auszuführen, was sie für das Testen von Funktionen und Gameplay geeignet macht.

:::warning
Aktuell deckt dieser Leitfaden nur Unit Tests ab.
:::

## Unit-Tests {#unit-testing}

Da Minecraft Modding auf Laufzeit-Bytecode-Modifikationswerkzeuge wie Mixin angewiesen ist, würde das einfache Hinzufügen und Verwenden von JUnit normalerweise nicht funktionieren. Aus diesem Grund bietet Fabric, Fabric Loader JUnit, ein JUnit-Plugin, das Unit-Tests in Minecraft ermöglicht.

### Fabric Loader JUnit reinrichten {#setting-up-fabric-loader-junit}

Zuerst müssen wir Fabric Loader JUnit zu der Entwicklungsumgebung hinzufügen. Füge folgendes zu deinem Dependencies-Block in deiner `build.gradle`-Datei hinzu:

@[code lang=groovy transcludeWith=:::automatic-testing:1](@/reference/build.gradle)

Dann müssen wir Gradle anweisen, Fabric Loader JUnit zum Testen zu verwenden. Du kannst dies tun, indem du den folgenden Code zu deiner `build.gradle`-Datei hinzufügst:

@[code lang=groovy transcludeWith=:::automatic-testing:2](@/reference/latest/build.gradle)

#### Aufgeteilte Sources {#split-sources}

:::info
Es ist geplant, dass dieser Abschnitt nach der Veröffentlichung von Loom 1.8 irrelevant wird. Für mehr Informationen, folge [diesem Issue](https://github.com/FabricMC/fabric-loom/issues/1060).
:::

Wenn du geteilte Sources verwendest, musst du auch entweder den Client- oder das Server-Sourceset zum Testsourceset hinzufügen. Fabric Loader JUnit ist standardmäßig auf Client eingestellt, also fügen wir das Client-Sourceset zu unserer Testumgebung mit folgendem Eintrag in der `build.gradle`-Datei hinzu:

@[code lang=groovy transcludeWith=:::automatic-testing:3](@/reference/build.gradle)

### Tests schreiben {#writing-tests}

Nachdem du Gradle neu geladen hast, bist du bereit, Tests zu schreiben.

Diese Tests werden genau wie normale JUnit-Tests geschrieben, mit ein paar zusätzlichen Einstellungen, wenn du auf eine von einer Registry abhängigen Klasse wie `ItemStack` zugreifen willst. Wenn du mit JUnit vertraut bist, kannst du zu [Registries einrichten](#setting-up-registries) übergehen.

#### Deine erste Testklasse erstellen {#setting-up-your-first-test-class}

Tests werden im Verzeichnis `src/test/java` geschrieben.

Eine Namenskonvention besteht darin, die Paketstruktur der Klasse, die du testest, widerzuspiegeln. Zum Beispiel, um `src/main/java/com/example/docs/codec/BeanType.java` zu testen, würdest du eine Klasse `src/test/java/com/example/docs/codec/BeanTypeTest.java` erstellen. Beachte, dass wir `Test` an das Ende des Klassennamens angefügt haben. Dies ermöglicht dir den einfachen Zugang zu Paket-Privaten Methoden und Feldern.

Eine andere Namenskonvention ist ein `test`-Paket, wie `src/test/java/com/example/docs/test/codec/BeanTypeTest.java`. Dies verhindert einige Probleme, die bei der Verwendung desselben Pakets auftreten können, wenn du Java-Module verwendest.

Nachdem du die Testklasse erstellt hast, verwende <kbd>⌘/CTRL</kbd><kbd>N</kbd>, um das Generate-Menü aufzurufen. Wähle Test und beginne mit der Eingabe deines Methodennamens, der normalerweise mit `test` beginnt. Drücke <kbd>ENTER</kbd>, wenn du fertig bist. Weitere Tipps und Tricks zur Verwendung der IDE findest du unter [IDE Tipps und Tricks](ide-tips-and-tricks#code-generation).

![Eine Testmethode generieren](/assets/develop/misc/automatic-testing/unit_testing_01.png)

Du kannst die Methodensignatur natürlich auch von Hand schreiben, und jede Instanzmethode ohne Parameter und mit einem ungültigen Rückgabetyp wird als Testmethode identifiziert. Das Ergebnis sollte wie folgt aussehen:

![Eine leere Testmethode mit Testindikatoren](/assets/develop/misc/automatic-testing/unit_testing_02.png)

Beachte die grünen Pfeilindikatoren in dem Zwischenraum: Du kannst ganz einfach einen Test ausführen, indem du sie anklickst. Alternativ dazu werden deine Tests automatisch bei jedem Build ausgeführt, einschließlich CI-Builds wie GitHub Actions. Wenn du GitHub Actions verwendest, solltest du unbedingt [GitHub Actions einrichten](#setting-up-github-actions) lesen.

Nun ist es an der Zeit, den eigentlichen Testcode zu schreiben. Du kannst Bedingungen mit Hilfe von `org.junit.jupiter.api.Assertions` prüfen. Siehe dazu den folgenden Test an:

@[code lang=java transcludeWith=:::automatic-testing:4](@/reference/latest/src/test/java/com/example/docs/codec/BeanTypeTest.java)

Für eine Erklärung, was dieser Code tatsächlich tut, siehe [Codecs](codecs#registry-dispatch).

#### Registries einrichten {#setting-up-registries}

Großartig, der erste Test war erfolgreich! Aber Moment, der zweite Test ist fehlgeschlagen? In den Logs wird einer der folgenden Fehler angezeigt.

@[code lang=java transcludeWith=:::automatic-testing:5](@/reference/latest/src/test/java/com/example/docs/codec/BeanTypeTest.java)

Dies liegt daran, dass wir versuchen, auf die Registry oder eine Klasse zuzugreifen, die von der Registry abhängt (oder, in seltenen Fällen, von anderen Minecraft-Klassen wie `SharedConstants`), aber Minecraft wurde noch nicht initialisiert. Wir müssen es nur ein wenig initialisieren, damit die Registries funktionieren. Fügen einfach den folgenden Code an den Anfang deiner Methode `beforeAll`.

@[code lang=java transcludeWith=:::automatic-testing:7](@/reference/latest/src/test/java/com/example/docs/codec/BeanTypeTest.java)

### Github Actions einrichten {#setting-up-github-actions}

:::info
In diesem Abschnitt wird davon ausgegangen, dass du den standardmäßigen GitHub Action-Workflow verwendest, der in dem Beispielmod und in der Mod-Vorlage enthalten ist.
:::

Deine Tests werden nun bei jedem Build ausgeführt, auch bei denen von CI-Anbietern wie GitHub Actions. Was aber, wenn ein Build fehlschlägt? Wir müssen die Logs als Artefakt hochladen, damit wir die Testberichte ansehen können.

Füge dies zu deiner `.github/workflows/build.yml`-Datei hinzu, unterhalb des `./gradlew build` Schrittes.

```yaml
      - name: Store reports
        if: failure()
        uses: actions/upload-artifact@v4
        with:
          name: reports
          path: |
            **/build/reports/
            **/build/test-results/
```
