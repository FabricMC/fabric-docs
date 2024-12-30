---
title: Beitragsrichtlinen
description: Richtlinien zur Beitragserstellung für die Fabric-Dokumentation

search: false
---

# Wie man beiträgt {#contributing}

Diese Website nutzt [VitePress](https://vitepress.dev/) um statisches HTML von den verschiedenen Markdown-Dateien zu generrieren. Du solltest dich mit den Markdown-Erweiterungen vertraut machen, die VitePress [hier](https://vitepress.dev/guide/markdown#features) unterstützt.

Du hast drei Möglichkeiten, zu dieser Website beizutragen:

- [Übersetzen der Dokumentation](#translating-documentation)
- [Inhalte beitragen](#contributing-content)
- [Zum Framework beitragen](#contributing-framework)

Alle Beiträge müssen unseren [Stilrichtlinien](#stilrichtlinien) entsprechen.

## Übersetzen der Dokumentation {#translating-documentation}

Falls du die Dokumentation in deine Sprache übersetzen möchtest, kannst du dies auf der [Fabric Crowdin-Seite](https://crowdin.com/project/fabricmc) tun.

<!-- markdownlint-disable-next-line titlecase-rule -->

## <Badge type="tip">new-content</Badge> Inhalt beitragen {#contributing-content}

Inhaltliche Beiträge sind die wichtigste Möglichkeit, zur Fabric-Dokumentation beizutragen.

Alle Inhaltsbeiträge durchlaufen die folgenden Stufen, die jeweils mit einem Label versehen sind:

1. <Badge type="tip">locally</Badge> Änderungen vorbereiten und einen Pull Request pushen
2. <Badge type="tip">stage:expansion</Badge>: Anleitung zur Erweiterung, falls erforderlich
3. <Badge type="tip">stage:verification</Badge>: Verifikation des Inhalts
4. <Badge type="tip">stage:cleanup</Badge>: Grammatik, Linting...
5. <Badge type="tip">stage:ready</Badge>: Bereit zum mergen!

Alle Inhalte sollten unseren [Stilrichtlinien](#style-guidelines) entsprechen.

### 1. Deine Änderungen vorbereiten {#1-prepare-your-changes}

Diese Website ist OpenSource und wird in einem GitHub-Repository entwickelt, was bedeutet, dass wir uns auf den GitHub-Ablauf verlassen:

1. eine Seite im Ordner `/players` mit der Seite `installing-fabric` aus `/players/installing-fabric.md` zu verknüpfen, musst du Folgendes tun:
2. Erstelle bei deinem Fork ein neues Branch
3. Mache deine Änderung bei diesem Branch
4. Öffne einen Pull Request bei dem ursprünglichen Repository

Du kannst [hier](https://docs.github.com/en/get-started/using-github/github-flow) mehr über den GitHub-Ablauf lesen.

Du kannst Änderungen entweder durch die Weboberfläche von GitHub machen oder du kannst die Website lokal entwickeln und die Vorschau ansehen.

#### Deinen Fork klonen {#clone-your-fork}

Wenn du lokal entwickeln willst, musst du [Git](https://git-scm.com/) installieren.

Danach klone deinen Fork des Repository mit:

```sh
# make sure to replace "your-username" with your actual username
git clone https://github.com/your-username/fabric-docs.git
```

#### Abhängigkeiten installieren {#install-dependencies}

**Wenn du deine Änderungen lokal ansehen möchtest, musst du [Node.js 18+](https://nodejs.org/en/) installieren.**

Danach stelle sicher, dass du alle Abhängigkeiten installierst:

```sh
npm install
```

#### Den Developmentserver ausführen {#run-the-development-server}

Damit kannst du deine Änderungen lokal auf `localhost:3000` ansehen und die Seite wird automatisch neu geladen, wenn du Änderungen vornimmst.

```sh
npm run dev
```

Jetzt kannst du die Website über den Browser öffnen und durchsuchen, indem du `http://localhost:5173` aufrufst.

#### Die Website bauen {#building-the-website}

Dies wird alle Markdown-Dateien in statische HTML-Dateien kompilieren und diese in `.vitepress/dist` ablegen

```sh
npm run build
```

#### Vorschau der gebauten Website {#previewing-the-built-website}

Dies wird einen lokalen Server auf Port 3000 starten, der den Inhalt in `.vitepress/dist` bereitstellt.

```sh
npm run preview
```

#### Einen Pull Request öffnen {#opening-a-pull-request}

Wenn du mit deinen Änderungen zufrieden bist, kannst du sie \`push´en:

```sh
git add .
git commit -m "Description of your changes"
git push
```

Folge dann dem Link in der Ausgabe von `git push`, um einen PR zu öffnen.

### 2. <Badge type="tip">stage:expansion</Badge> Anleitung zur Erweiterung, falls erforderlich {#2-guidance-for-expansion-if-needed}

Wenn das Dokumentationsteam der Meinung ist, dass du deinen Pull Request erweitern könntest, wird ein Mitglied des Teams das Label <Badge type="tip">stage:expansion</Badge> zu deinem Pull Request hinzufügen, zusammen mit einem Kommentar, der erklärt, was du deren Meinung nach erweitern könnten. Wenn du mit dem Vorschlag einverstanden bist, kannst du deinen Pull-Request erweitern.

Wenn du deinen Pull Request nicht erweitern willst, aber gerne möchtest, dass jemand anderes ihn zu einem späteren Zeitpunkt erweitert, solltest du ein Issue auf der [Issues Seite](https://github.com/FabricMC/fabric-docs/issues) erstellen und erklären, was deiner Meinung nach erweitert werden könnte. Das Dokumentationsteam fügt dann das Label <Badge type="tip">help-wanted</Badge> zu deinem PR hinzu.

### 3. <Badge type="tip">stage:verification</Badge> Verifikation des Inhalts {#3-content-verification}

Dies ist die wichtigste Phase, da sie sicherstellt, dass der Inhalt korrekt ist und den Stilrichtlinien der Fabric Documentation entspricht.

In dieser Phase sollten die folgenden Fragen beantwortet werden:

- Ist der ganze Inhalt korrekt?
- Ist der ganze Inhalt aktuell?
- Deckt der Inhalt alle Fälle ab, z. B. verschiedene Betriebssysteme?

### 4. <Badge type="tip">stage:cleanup</Badge> Aufräumen {#4-cleanup}

In dieser Phase passiert folgendes:

- Beheben aller Grammatikfehler mit [LanguageTool](https://languagetool.org/)
- Linten alle Markdown-Dateien mit [`markdownlint`](https://github.com/DavidAnson/markdownlint)
- Formatierung des ganzen Java-Code mit [Checkstyle](https://checkstyle.sourceforge.io/)
- Andere verschiedene Korrekturen oder Verbesserungen

## <Badge type="tip">framework</Badge> Zum Framework beitragen {#contributing-framework}

Framework bezieht sich auf die interne Struktur der Website. Alle Pull Requests, die das Framework der Website verändern, werden mit dem Label <Badge type="tip">framework</Badge> gekennzeichnet.

Du solltest wirklich nur Pull-Requests für das Framework eröffnen, nachdem du dich mit dem Dokumentationsteam im [Fabric Discord](https://discord.gg/v6v4pMv) oder über ein Issue beraten hast.

:::info
**Hinweis: Das Ändern von Seitenleistendateien und der Konfiguration der Navigationsleiste zählt nicht als Pull-Request für das Framework.**
:::

## Stilrichtlinien {#style-guidelines}

Wenn du unsicher bist, kannst du im [Fabric Discord](https://discord.gg/v6v4pMv) oder über GitHub Diskussionen nachfragen.

### Schreibe das Original in amerikanischem Englisch {#write-the-original-in-american-english}

Die ganze originale Dokumentation ist in englischer Sprache verfasst und folgt den amerikanischen Grammatikregeln.

### Daten zum Frontmatter hinzufügen {#add-data-to-the-frontmatter}

Alle Seiten müssen einen Titel und eine Beschreibung im Frontmatter haben.

Vergiss nicht, auch deinen GitHub-Benutzernamen zu `authors` im Frontmatter der Markdown-Datei hinzuzufügen! Auf diese Weise können wir dir angemessene Anerkennung geben.

```md
---
title: Title of the Page
description: This is the description of the page.
authors:
  - your-username
---

# Title of the Page {#title-of-the-page}

...
```

### Füge Anker zu den Überschriften hinzu {#add-anchors-to-headings}

Jede Überschrift muss einen Anker haben, der als Link zu dieser Überschrift dient:

```md
# This Is a Heading {#this-is-a-heading}
```

Der Anker muss Kleinbuchstaben, Zahlen und Bindestriche enthalten.

### Code innerhalb des `/reference` Mods platzieren {#place-code-within-the-reference-mod}

Wenn du Seiten erstellst oder änderst, die Code enthalten, platziere den Code an einer geeigneten Stelle innerhalb des Referenz-Mod (im Ordner `/reference` des Repository). Verwende dann die [Code-Snippet-Funktion, die von VitePress angeboten wird](https://vitepress.dev/guide/markdown#import-code-snippets), um den Code einzubetten, oder wenn du eine größere Kontrollspanne benötigst, kannst du die [transclude-Funktion von `markdown-it-vuepress-code-snippet-enhanced`](https://github.com/fabioaanthony/markdown-it-vuepress-code-snippet-enhanced) verwenden.

Dies wird die Zeilen 15-21 der Datei `FabricDocsReference.java` des Referenz-Mod einbetten.

::: code-group

```md
<<< @/reference/1.21/src/main/java/com/example/docs/FabricDocsReference.java{15-21}
```

<<< @/reference/1.21/src/main/java/com/example/docs/FabricDocsReference.java{15-21}[java]

:::

Nur der Code zwischen den `#test_transclude`-Tags wird eingebettet.

So werden beispielsweise die Abschnitte der obigen Datei eingebettet, die mit dem Tag `#entrypoint` gekennzeichnet sind:

::: code-group

```md
@[code transcludeWith=#entrypoint](@/reference/1.21/src/main/java/com/example/docs/FabricDocsReference.java)
```

@[code transcludeWith=#entrypoint](@/reference/1.21/src/main/java/com/example/docs/FabricDocsReference.java)

:::

### Erstelle eine Seitenleiste für jeden neuen Abschnitt {#create-a-sidebar-for-each-new-section}

Wenn du einen neuen Abschnitt erstellst, solltest du eine neue Seitenleiste im Ordner `.vitepress/sidebars` anlegen und sie zur Datei `config.mts` hinzufügen.

Wenn du dabei Hilfe benötigst, frage bitte auf dem [Fabric Discord](https://discord.gg/v6v4pMv) im Kanal `#docs` nach.

### Anleitung zur Erweiterung {#add-new-pages-to-the-relevant-sidebars}

Wenn du eine neue Seite erstellst, solltest du sie der entsprechenden Seitenleiste im Ordner `.vitepress/sidebars` hinzufügen.

Auch hier gilt: Wenn du Hilfe benötigst, frage auf dem Fabric Discord im Kanal `#docs` nach.

### Platziere Medien in `/assets` {#place-media-in-assets}

Alle Bilder sollten an einem geeigneten Ort im Ordner `/assets` abgelegt werden.

### Nutze relative Links! {#use-relative-links}

Der Grund dafür ist das vorhandene Versionssystem, das die Links verarbeitet, um die Version vorher hinzuzufügen. Wenn du absolute Links verwendest, wird die Versionsnummer nicht zum Link hinzugefügt.

Du darfst auch nicht die Dateierweiterung zu dem Link hinzufügen.

Um zum Beispiel von der Seite `/players/index.md` auf die Seite `/develop/index.md` zu verlinken, musst du folgendes tun:

::: code-group

```md:no-line-numbers [✅ Correct]
This is a relative link!
[Page](../players/index)
```

```md:no-line-numbers [❌ Wrong]
This is an absolute link.
[Page](/players/index)
```

```md:no-line-numbers [❌ Wrong]
This relative link has the file extension.
[Page](../players/index.md)
```

:::
