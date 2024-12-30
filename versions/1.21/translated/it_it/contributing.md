---
title: Linee Guida per la Contribuzione
description: Linee guida per le contribuzioni alla Documentazione di Fabric.

search: false
---

# Linee Guida per la Contribuzione {#contributing}

Questo sito usa [VitePress](https://vitepress.dev/) per generare HTML statico da vari file Markdown. Dovresti familiarizzare con le estensioni per Markdown che VitePress supporta [qui](https://vitepress.dev/guide/markdown#features).

Ci sono tre modi per contribuire a questo sito:

- [Tradurre la Documentazione](#translating-documentation)
- [Contribuire con Contenuti](#contributing-content)
- [Contribuire al Framework](#contributing-framework)

Tutte le contribuzioni devono seguire le nostre [linee guida per lo stile](#style-guidelines).

## Tradurre la Documentazione {#translating-documentation}

Se vuoi tradurre la documentazione nella tua lingua, puoi farlo nella [pagina Crowdin di Fabric](https://crowdin.com/project/fabricmc).

<!-- markdownlint-disable-next-line titlecase-rule -->

## <Badge type="tip">new-content</Badge> Contribuire con Contenuti {#contributing-content}

Le contribuzioni con contenuti sono il modo principale per contribuire alla Documentazione di Fabric.

Tutte le contribuzioni con contenuti passano per le seguenti fasi, ciascuna delle quali è associata ad un'etichetta:

1. <Badge type="tip">localmente</Badge> Prepara le tue modifiche e apri una PR
2. <Badge type="tip">stage:expansion</Badge> Indicazioni per l'espansione se necessaria
3. <Badge type="tip">stage:verification</Badge>: Verifica dei contenuti
4. <Badge type="tip">stage:cleanup</Badge>: Grammatica, Linting...
5. <Badge type="tip">stage:ready</Badge>: Pronta per il merge!

Tutto il contenuto deve rispettare le nostre [linee guida per lo stile](#style-guidelines).

### 1. Prepara le Tue Modifiche {#1-prepare-your-changes}

Il sito è open-source, ed è sviluppato in una repository su GitHub, il che significa che ci affidiamo al flow GitHub:

1. [Crea una fork della Repository su GitHub](https://github.com/FabricMC/fabric-docs/fork)
2. Crea un nuovo branch sulla tua fork
3. Aggiungi le tue modifiche su quel branch
4. Apri una Pull Request alla repository originale

Puoi leggere di più riguardo al flow GitHub [qui](https://docs.github.com/en/get-started/using-github/github-flow).

È possibile fare modifiche dall'interfaccia web su GitHub, oppure puoi sviluppare e ottenere un'anteprima del sito localmente.

#### Clonare la Tua Fork {#clone-your-fork}

Se vuoi sviluppare localmente, dovrai installare [Git](https://git-scm.com/).

Dopo di che, clona la tua fork della repository con:

```sh
# make sure to replace "your-username" with your actual username
git clone https://github.com/your-username/fabric-docs.git
```

#### Installare le Dipendenze {#install-dependencies}

Se vuoi ottenere un'anteprima locale delle tue modifiche, dovrai installare [Node.js 18+](https://nodejs.org/en/).

Dopo di che, assicurati di installare tutte le dipendenze con:

```sh
npm install
```

#### Eseguire il Server di Sviluppo {#run-the-development-server}

Questo di permetterà di ottenere un'anteprima locale delle tue modifiche presso `localhost:5173` e ricaricherà automaticamente la pagina quando farai modifiche.

```sh
npm run dev
```

Ora puoi aprire e navigare sul sito dal browser visitando `http://localhost:5173`.

#### Costruire il Sito {#building-the-website}

Questo compilerà tutti i file Markdown in HTML statico e li posizionerà in `.vitepress/dist`:

```sh
npm run build
```

#### Ottenere un'Anteprima del Sito Costruito {#previewing-the-built-website}

Questo avvierà un server locale in porta `4173` che servirà il contenuto trovato in `.vitepress/dist`:

```sh
npm run preview
```

#### Aprire una Pull Request {#opening-a-pull-request}

Quando sarai felice delle tue modifiche, puoi fare `push` delle tue modifiche:

```sh
git add .
git commit -m "Description of your changes"
git push
```

Dopo di che segui il link nell'output di `git push` per aprire una PR.

### 2. <Badge type="tip">stage:expansion</Badge> Indicazioni per l'Espansione Se Necessaria {#2-guidance-for-expansion-if-needed}

Se il team della documentazione crede che tu possa espandere la tua pull request, un membro del team aggiungerà l'etichetta <Badge type="tip">stage:expansion</Badge> alla tua pull request assieme a un commento che spiega cosa credono che tu possa espandere. Se sei d'accordo con il consiglio, puoi espandere la tua pull request.

Se non vuoi espandere la tua pull request, ma ti va bene che qualcun altro lo faccia successivamente, dovresti creare un'issue sulla [pagina Issues](https://github.com/FabricMC/fabric-docs/issues) e spiegare cosa credi che si possa espandere. Il team di documentazione aggiungerà quindi l'etichetta <Badge type="tip">help-wanted</Badge> alla tua PR.

### 3. <Badge type="tip">stage:verification</Badge> Verifica dei Contenuti {#3-content-verification}

Questa è la fase più importante poiché assicura che il contenuto sia accurato e segua le linee guida per lo stile della Documentazione di Fabric.

In questa fase, bisognerebbe rispondere alle seguenti domande:

- Il contenuto è tutto corretto?
- Il contenuto è tutto aggiornato?
- Il contenuto copre tutti i casi possibili, per esempio i vari sistemi operativi?

### 4. <Badge type="tip">stage:cleanup</Badge>: Pulizia {#4-cleanup}

In questa fase, avviene ciò che segue:

- Correzione di eventuali errori grammaticali tramite [LanguageTool](https://languagetool.org/)
- Linting di tutti i file Markdown tramite [`markdownlint`](https://github.com/DavidAnson/markdownlint)
- Formattazione di tutto il codice Java tramite [Checkstyle](https://checkstyle.sourceforge.io/)
- Altri cambiamenti o miglioramenti vari

## <Badge type="tip">framework</Badge> Contribuire al Framework {#contributing-framework}

Framework si riferisce alla struttura interna del sito, ogni pull request che modifica il framework del sito dovrebbe essere etichettata con l'etichetta <Badge type="tip">framework</Badge>.

Dovresti davvero fare pull request riguardanti il framework solo dopo esserti consultato con il team della documentazione nel [Discord di Fabric](https://discord.gg/v6v4pMv) o tramite un'issue.

:::info
Modificare i file nella sidebar e la configurazione della barra di navigazione non conta come pull request riguardante il framework.
:::

## Linee Guida per lo Stile {#style-guidelines}

Se fossi incerto riguardo a qualsiasi cosa, puoi chiedere nel [Discord di Fabric](https://discord.gg/v6v4pMv) o tramite GitHub Discussions.

### Scrivi l'Originale in Inglese Americano {#write-the-original-in-american-english}

Tutta la documentazione originale è scritta in inglese, seguendo le regole grammaticali americane.

### Aggiungi i Dati al Frontmatter {#add-data-to-the-frontmatter}

Ogni pagina deve avere un titolo (`title`) e una descrizione (`description`) nel frontmatter.

Ricordati anche di aggiungere il tuo nome utente GitHub ad `authors` nel frontmatter del file Markdown! In questo modo possiamo darti l'attribuzione che meriti.

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

### Aggiungi Ancore alle Intestazioni {#add-anchors-to-headings}

Ogni intestazione deve avere un'ancora, che viene usata per collegarsi a quell'intestazione:

```md
# This Is a Heading {#this-is-a-heading}
```

L'ancora deve usare caratteri minuscoli, numeri e trattini.

### Posiziona il Codice Nella Mod `/reference` {#place-code-within-the-reference-mod}

Se crei o modifichi pagine contenenti codice, metti il codice in una posizione appropriata nella mod reference (presente nella cartella `/reference` della repository). Dopo di che, usa la [funzione code snippet fornita da VitePress](https://vitepress.dev/guide/markdown#import-code-snippets) per incorporare il codice.

Per esempio, per evidenziare le linee 15-21 del file `FabricDocsReference.java` dalla mod reference:

::: code-group

```md
<<< @/reference/1.21/src/main/java/com/example/docs/FabricDocsReference.java{15-21}
```

<<< @/reference/1.21/src/main/java/com/example/docs/FabricDocsReference.java{15-21}[java]

:::

Se ti serve un controllo più raffinato, puoi usare la [funzione transclude da `markdown-it-vuepress-code-snippet-enhanced`](https://github.com/fabioaanthony/markdown-it-vuepress-code-snippet-enhanced).

Per esempio, questo incorporerà le sezioni del suddetto file che sono contrassegnate con il tag `#entrypoint`:

::: code-group

```md
@[code transcludeWith=#entrypoint](@/reference/1.21/src/main/java/com/example/docs/FabricDocsReference.java)
```

@[code transcludeWith=#entrypoint](@/reference/1.21/src/main/java/com/example/docs/FabricDocsReference.java)

:::

### Crea una Barra Laterale per Ogni Nuova Sezione {#create-a-sidebar-for-each-new-section}

Se stai creando una nuova sezione, dovresti creare una nuova barra laterale nella cartella `.vitepress/sidebars` ed aggiungerla al file `i18n.ts`.

Se hai bisogno di assistenza con questo, chiedi per favore nel canale `#docs` del [Discord di Fabric](https://discord.gg/v6v4pMv).

### Aggiungi Nuove Pagine alle Barre Laterali Appropriate {#add-new-pages-to-the-relevant-sidebars}

Quando crei una nuova pagina, dovresti aggiungerla alla barra laterale appropriata nella cartella `.vitepress/sidebars`.

Di nuovo, se hai bisogno di assistenza, chiedi nel canale `#docs` del Discord di Fabric.

### Posiziona i Contenuti Multimediali in `/assets` {#place-media-in-assets}

Ogni immagine dovrebbe essere messa in una posizione appropriata nella cartella `/public/assets/`.

### Usa Link Relativi! {#use-relative-links}

Questo è dovuto al sistema di gestione delle versioni in uso, che processerà i link per aggiungerci la versione anticipatamente. Se usassi link assoluti, il numero di versione non verrebbe aggiunto al link.

Devi anche non aggiungere l'estensione del file al link.

Per esempio, per inserire un link alla pagina `/players/index.md` dalla pagina `/develop/index.md`, dovresti fare il seguente:

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
