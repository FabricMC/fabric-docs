---
title: Automatisiertes Testen
description: Ein Leitfaden zum Schreiben von automatisierten Tests mit Fabric Loader JUnit.
authors:
  - kevinthegreat1
---

Diese Seite erklärt, wie man Code schreibt, um Teile des Mods automatisch zu testen. Es gibt zwei Möglichkeiten, deinen Mod automatisch zu testen: Unit Tests mit Fabric Loader JUnit oder Spieltests mit dem Gametest-Framework von Minecraft.

Unit-Tests sollten verwendet werden, um Komponenten deines Codes zu testen, wie z. B. Methoden und Hilfsklassen, während Spieltests einen tatsächlichen Minecraft-Client und -Server starten, um deine Tests auszuführen, was sie für das Testen von Funktionen und Gameplay geeignet macht.

## Unit-Tests {#unit-testing}

Da Minecraft Modding auf Laufzeit-Bytecode-Modifikationswerkzeuge wie Mixin angewiesen ist, würde das einfache Hinzufügen und Verwenden von JUnit normalerweise nicht funktionieren. Aus diesem Grund bietet Fabric, Fabric Loader JUnit, ein JUnit-Plugin, das Unit-Tests in Minecraft ermöglicht.

### Fabric Loader JUnit reinrichten {#setting-up-fabric-loader-junit}

Zuerst müssen wir Fabric Loader JUnit zu der Entwicklungsumgebung hinzufügen. Füge folgendes zu deinem Dependencies-Block in deiner `build.gradle`-Datei hinzu:

@[code transcludeWith=:::automatic-testing:1](@/reference/build.gradle)

Dann müssen wir Gradle anweisen, Fabric Loader JUnit zum Testen zu verwenden. Du kannst dies tun, indem du den folgenden Code zu deiner `build.gradle`-Datei hinzufügst:

@[code transcludeWith=:::automatic-testing:2](@/reference/latest/build.gradle)

### Tests schreiben {#writing-tests}

Nachdem du Gradle neu geladen hast, bist du bereit, Tests zu schreiben.

Diese Tests werden genau wie normale JUnit-Tests geschrieben, mit ein paar zusätzlichen Einstellungen, wenn du auf eine von einer Registry abhängigen Klasse wie `ItemStack` zugreifen willst. Wenn du mit JUnit vertraut bist, kannst du zu [Registries einrichten](#setting-up-registries) übergehen.

#### Deine erste Testklasse erstellen {#setting-up-your-first-test-class}

Tests werden im Verzeichnis `src/test/java` geschrieben.

Eine Namenskonvention besteht darin, die Paketstruktur der Klasse, die du testest, widerzuspiegeln. Zum Beispiel, um `src/main/java/com/example/docs/codec/BeanType.java` zu testen, würdest du eine Klasse `src/test/java/com/example/docs/codec/BeanTypeTest.java` erstellen. Beachte, dass wir `Test` an das Ende des Klassennamens angefügt haben. Dies ermöglicht dir den einfachen Zugang zu Paket-Privaten Methoden und Feldern.

Eine andere Namenskonvention ist ein `test`-Paket, wie `src/test/java/com/example/docs/test/codec/BeanTypeTest.java`. Dies verhindert einige Probleme, die bei der Verwendung desselben Pakets auftreten können, wenn du Java-Module verwendest.

Nachdem du die Testklasse erstellt hast, verwende <kbd>⌘/CTRL</kbd>+<kbd>N</kbd>, um das Generate-Menü aufzurufen. Wähle Test und beginne mit der Eingabe deines Methodennamens, der normalerweise mit `test` beginnt. Drücke <kbd>ENTER</kbd>, wenn du fertig bist. Weitere Tipps und Tricks zur Verwendung der IDE findest du unter [IDE Tipps und Tricks](./getting-started/tips-and-tricks#code-generation).

![Eine Testmethode generieren](/assets/develop/misc/automatic-testing/unit_testing_01.png)

Du kannst die Methodensignatur natürlich auch von Hand schreiben, und jede Instanzmethode ohne Parameter und mit einem ungültigen Rückgabetyp wird als Testmethode identifiziert. Das Ergebnis sollte wie folgt aussehen:

![Eine leere Testmethode mit Testindikatoren](/assets/develop/misc/automatic-testing/unit_testing_02.png)

Beachte die grünen Pfeilindikatoren in dem Zwischenraum: Du kannst ganz einfach einen Test ausführen, indem du sie anklickst. Alternativ dazu werden deine Tests automatisch bei jedem Build ausgeführt, einschließlich CI-Builds wie GitHub Actions. Wenn du GitHub Actions verwendest, solltest du unbedingt [GitHub Actions einrichten](#setting-up-github-actions) lesen.

Nun ist es an der Zeit, den eigentlichen Testcode zu schreiben. Du kannst Bedingungen mit Hilfe von `org.junit.jupiter.api.Assertions` prüfen. Siehe dazu den folgenden Test an:

@[code lang=java transcludeWith=:::automatic-testing:4](@/reference/latest/src/test/java/com/example/docs/codec/BeanTypeTest.java)

Für eine Erklärung, was dieser Code tatsächlich tut, siehe [Codecs](./codecs#registry-dispatch).

#### Registries einrichten {#setting-up-registries}

Großartig, der erste Test war erfolgreich! Aber Moment, der zweite Test ist fehlgeschlagen? In den Logs wird einer der folgenden Fehler angezeigt.

<<< @/public/assets/develop/automatic-testing/crash-report.log

Dies liegt daran, dass wir versuchen, auf die Registry oder eine Klasse zuzugreifen, die von der Registry abhängt (oder, in seltenen Fällen, von anderen Minecraft-Klassen wie `SharedConstants`), aber Minecraft wurde noch nicht initialisiert. Wir müssen es nur ein wenig initialisieren, damit die Registries funktionieren. Fügen einfach den folgenden Code an den Anfang deiner Methode `beforeAll`.

@[code lang=java transcludeWith=:::automatic-testing:7](@/reference/latest/src/test/java/com/example/docs/codec/BeanTypeTest.java)

### Github Actions einrichten {#setting-up-github-actions}

::: info

In diesem Abschnitt wird davon ausgegangen, dass du den standardmäßigen GitHub Action-Workflow verwendest, der in dem Beispielmod und in der Mod-Vorlage enthalten ist.

:::

Deine Tests werden nun bei jedem Build ausgeführt, auch bei denen von CI-Anbietern wie GitHub Actions. Was aber, wenn ein Build fehlschlägt? Wir müssen die Logs als Artefakt hochladen, damit wir die Testberichte ansehen können.

Füge dies, unterhalb des Schrittes `./gradlew build`, zu deiner Datei `.github/workflows/build.yaml` hinzu.

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

## Spiel-Tests {#game-tests}

Minecraft bietet ein Test-Framework für das Testen serverseitiger Funktionen. Fabric bietet zusätzlich Client-Spiel-Tests zum Testen clientseitiger Funktionen, ähnlich einem Ende-zu-Ende-Test.

### Spiel-Tests mit Fabric Loom einrichten {#setting-up-game-tests-with-fabric-loom}

Sowohl Server- als auch Client-Spiel-Tests können manuell oder mit Fabric Loom eingerichtet werden. Dieser Leitfaden wird Loom nutzen.

Um Spiel-Tests zu deinem Mod hinzuzufügen, füge das Folgende zu deiner `build.gradle` hinzu:

@[code transcludeWith=:::automatic-testing:game-test:1](@/reference/latest/build.gradle)

Um alle verfügbaren Optionen zu sehen, siehe [die Loom-Dokumentation zu Tests](./loom/fabric-api#tests).

#### Spiel-Tests-Verzeichnis einrichten {#setting-up-game-test-directory}

::: info

Du benötigst diesen Abschnitt nur, wenn du die Option `createSourceSet` aktiviert hast, was empfohlen ist. Du kannst natürlich auch deine eigene Gradle-Magie anwenden, aber dann bist du auf dich allein gestellt.

:::

Wenn du `createSourceSet` wie im obigen Beispiel aktiviert hast, wird dein Spiel-Test in einem separaten Quellensatz mit einer separaten `fabric.mod.json` sein. Der Modulname ist standardmäßig `gametest`. Erstelle eine neue `fabric.mod.json` in `src/gametest/resources/` wie gezeigt:

<<< @/reference/latest/src/gametest/resources/fabric.mod.json

Beachte, dass diese `fabric.mod.json` einen Server-Spiel-Test unter `src/gametest/java/com/example/docs/ExampleModGameTest` erwartet, und einen Client-Spiel-Test unter `src/gametest/java/com/example/docs/ExampleModClientGameTest`.

### Spiel-Tests schreiben {#writing-game-tests}

Du kannst jetzt Server- und Client-Tests im Verzeichnis `src/gametest/java` erstellen. Hier ist ein grundlegendes Beispiel für jedes:

::: code-group

<<< @/reference/latest/src/gametest/java/com/example/docs/ExampleModGameTest.java [Server]

<<< @/reference/latest/src/gametest/java/com/example/docs/ExampleModClientGameTest.java [Client]

:::

Für weitere Informationen siehe die entsprechenden Javadocs in der Fabric API.

### Spieltests ausführen {#running-game-tests}

Server-Spieltests werden automatisch mit dem `build` Gradle Task ausgeführt. Du kannst Client Spiel-Tests mit dem `runClientGameTest` Gradle Task ausführen.

### Spieltests bei GitHub Actions ausführen {#run-game-tests-on-github-actions}

Bestehende GitHub Action-Workflows, die `build` verwenden, führen automatisch die Server-Spieltests durch. Um Client-Spieltests mit GitHub Actions auszuführen, füge den folgenden Ausschnitt zu deiner `build.gradle`-Datei und den folgenden Job zu deinem Workflow hinzu. Das Gradle-Snippet führt Client-Spieletests mit [Looms Produktionslauf-Aufgaben](./loom/production-run-tasks) durch, und der Job führt die Produktionslauf-Aufgabe in dem CI aus.

::: warning

Derzeit kann der Spieltest aufgrund eines Fehlers im Netzwerksynchronisierer auf GitHub Actions fehlschlagen. Wenn dieser Fehler auftritt, kannst du `-Dfabric.client.gametest.disableNetworkSynchronizer=true` zu den JVM-Argumenten in deiner Deklaration Produktionslauf-Aufgaben hinzufügen.

:::

@[code transcludeWith=:::automatic-testing:game-test:2](@/reference/latest/build.gradle)

@[code transcludeWith=:::automatic-testing:game-test:3](@/.github/workflows/build.yaml)
