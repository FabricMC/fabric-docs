# Richtlinien zur Beitragserstellung für die Fabric-Dokumentation

Diese Website verwendet [VitePress](https://vitepress.vuejs.org/), um statisches HTML aus den verschiedenen Markdown-Dateien zu generieren. Du solltest dich mit den Markdown-Erweiterungen vertraut machen, die von VitePress unterstützt werden.

## Inhaltsverzeichnis

- [Richtlinien zur Beitragserstellung für die Fabric-Dokumentation](#fabric-documentation-contribution-guidelines)
  - [Wie man beiträgt](#how-to-contribute)
  - [Zum Framework beitragen](#contributing-framework)
  - [Inhalte beitragen](#contributing-content)
    - [Stilrichtlinien](#style-guidelines)
    - [Anleitung zur Erweiterung](#guidance-for-expansion)
    - [Überprüfung des Inhalts](#content-verification)
    - [Aufräumen](#cleanup)

## Wie man beiträgt

Es wird empfohlen, dass du für jeden Pull-Request ein neues Branch in deinem Fork des Repositorys erstellst. Das macht es einfacher, mehrere Pull-Requests gleichzeitig zu verwalten.

**Wenn du deine Änderungen lokal ansehen möchtest, musst du [Node.js 18+](https://nodejs.org/en/) installieren.**

Bevor du einen dieser Befehle ausführst, solltest du `npm install` ausführen, um alle Abhängigkeiten zu installieren.

**Ausführung des Entwicklungsservers:**

Damit kannst du deine Änderungen lokal auf `localhost:3000` ansehen und die Seite wird automatisch neu geladen, wenn du Änderungen vornimmst.

```bash
npm run dev
```

**Erstellung der Website:**

Dies wird alle Markdown-Dateien in statische HTML-Dateien kompilieren und in `.vitepress/dist` ablegen

```bash
npm run build
```

**Vorschau auf die erstellte Website:**

Dies wird einen lokalen Server auf Port 3000 starten, der den Inhalt in `.vitepress/dist` bereitstellt.

```bash
npm run preview
```

## Zum Framework beitragen

Alle Pull-Requests, die das Framework der Website verändern, sollten mit dem Label `framework` gekennzeichnet werden.

Du solltest wirklich nur Pull-Requests für das Framework eröffnen, nachdem du dich mit dem Dokumentationsteam im [Fabric Discord](https://discord.gg/v6v4pMv) oder über ein Issue beraten hast.

**Hinweis: Das Ändern von Seitenleistendateien und der Konfiguration der Navigationsleiste zählt nicht als Pull-Request für das Framework.**

## Inhalte beitragen

Inhaltliche Beiträge sind die wichtigste Möglichkeit, zur Fabric-Dokumentation beizutragen.

Alle Inhalte sollten unseren Stilrichtlinien entsprechen.

### Stilrichtlinien

Alle Seiten auf der Website der Fabric-Dokumentation sollten den Stilrichtlinien entsprechen. Wenn du unsicher bist, kannst du im [Fabric Discord](https://discord.gg/v6v4pMv) oder über GitHub Diskussionen nachfragen.

Die Stilrichtlinien lauten wie folgt:

1. Alle Seiten müssen einen Titel und eine Beschreibung im Frontmatter haben.

   ```md
   ---
   title: Dies ist der Titel der Seite
   description: Dies ist die Beschreibung der Seite
   authors:
     - GitHubUsernameHier
   ---

   # ...
   ```

2. Wenn du Seiten erstellst oder änderst, die Code enthalten, platziere den Code an einer geeigneten Stelle innerhalb des Referenz-Mod (im Ordner `/reference` des Repository). Verwende dann die [Code-Snippet-Funktion, die von VitePress angeboten wird](https://vitepress.dev/guide/markdown#import-code-snippets), um den Code einzubetten, oder wenn du eine größere Kontrollspanne benötigst, kannst du die [transclude-Funktion von `markdown-it-vuepress-code-snippet-enhanced`](https://github.com/fabioaanthony/markdown-it-vuepress-code-snippet-enhanced) verwenden.

   **Beispiel:**

   ```md
   <<< @/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java{15-21 java}
   ```

   Dies wird die Zeilen 15-21 der Datei `FabricDocsReference.java` des Referenz-Mod einbetten.

   Der resultierende Codeschnipsel sieht wie folgt aus:

   ```java
     @Override
     public void onInitialize() {
       // This code runs as soon as Minecraft is in a mod-load-ready state.
       // However, some things (like resources) may still be uninitialized.
       // Proceed with mild caution.

       LOGGER.info("Hello Fabric world!");
     }
   ```

   **Transclude-Beispiel:**

   ```md
   @[code transcludeWith=#test_transclude](@/reference/.../blah.java)
   ```

   Dies wird die Abschnitte von `blah.java` eingebetten, die mit dem Tag `#test_transclude` markiert sind.

   Zum Beispiel:

   ```java
   public final String test = "Bye World!"

   // #test_transclude
   public void test() {
     System.out.println("Hello World!");
   }
   // #test_transclude
   ```

   Nur der Code zwischen den `#test_transclude`-Tags wird eingebettet.

   ```java
   public void test() {
     System.out.println("Hello World!");
   }
   ```

3. Wir halten uns an die Grammatikregeln des amerikanischen Englisch. Du kannst zwar [LanguageTool](https://languagetool.org/) verwenden, um deine Grammatik während der Eingabe zu überprüfen, aber mache dir nicht zu viele Gedanken darüber. Unser Dokumentationsteam überprüft und korrigiert die Grammatik während der Bereinigungsphase. Wenn man sich jedoch von Anfang an bemüht, es richtig zu machen, können wir Zeit sparen.

4. Wenn du einen neuen Abschnitt erstellst, solltest du eine neue Seitenleiste im Ordner `.vitepress/sidebars` anlegen und sie zur Datei `config.mts` hinzufügen. Wenn du dabei Hilfe benötigst, frage bitte auf dem [Fabric Discord](https://discord.gg/v6v4pMv) im Kanal `#wiki` nach.

5. Wenn du eine neue Seite erstellst, solltest du sie der entsprechenden Seitenleiste im Ordner `.vitepress/sidebars` hinzufügen. Auch hier gilt: Wenn du Hilfe benötigst, frage auf dem Fabric Discord im Kanal `#wiki` nach.

6. Alle Bilder sollten an einem geeigneten Ort im Ordner `/assets` abgelegt werden.

7. ⚠️ **Wenn du andere Seiten verlinkst, verwende relative Links.** ⚠️

   Der Grund dafür ist das vorhandene Versionssystem, das die Links verarbeitet, um die Version vorher hinzuzufügen. Wenn du absolute Links verwendest, wird die Versionsnummer nicht zum Link hinzugefügt.

   Um zum Beispiel eine Seite im Ordner `/players` mit der Seite `installing-fabric` aus `/players/installing-fabric.md` zu verknüpfen, musst du Folgendes tun:

   ```md
   [Dies ist ein Link zu einer anderen Seite](./installing-fabric.md)
   ```

   Du solltest **NICHT** Folgendes tun:

   ```md
   [Dies ist ein Link zu einer anderen Seite](/player/installing-fabric)
   ```

Alle inhaltlichen Beiträge durchlaufen drei Stufen:

1. Anleitung zur Erweiterung (falls möglich)
2. Überprüfung des Inhalts
3. Bereinigung (Grammatik etc.)

### Anleitung zur Erweiterung

Wenn das Dokumentationsteam der Meinung ist, dass du deinen Pull-Request erweitern kannst, wird ein Mitglied des Teams das Label `expansion` zu deinem Pull-Request hinzufügen, zusammen mit einem Kommentar, der erklärt, was du ihrer Meinung nach erweitern könntest. Wenn du mit dem Vorschlag einverstanden bist, kannst du deinen Pull-Request erweitern.

**Fühle dich nicht unter Druck gesetzt deinen Pull-Request zu erweitern.** Wenn du deinen Pull-Request nicht erweitern willst, kannst du einfach darum bitten, dass das Label `expansion` entfernt wird.

Wenn du deinen Pull-Request nicht erweitern willst, aber gerne möchtest, dass jemand anderes ihn zu einem späteren Zeitpunkt erweitert, ist es am besten, ein Issue auf der [Issues-Seite](https://github.com/FabricMC/fabric-docs/issues) zu erstellen und zu erklären, was deiner Meinung nach erweitert werden könnte.

### Überprüfung des Inhalts

Dies ist die wichtigste Phase, da sie sicherstellt, dass der Inhalt korrekt ist und den Fabric Stilrichtlinien der Dokumentation entspricht.

### Aufräumen

In dieser Phase behebt das Dokumentationsteam Grammatikfehler und nimmt andere Änderungen vor, die es für notwendig hält, bevor es den Pull-Request mergt!
