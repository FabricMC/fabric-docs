---
title: Linee Guida per la Contribuzione
description: Linee guida per le contribuzioni alla Documentazione di Fabric.
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

## Contribuire con Contenuti {#contributing-content}

Le contribuzioni con contenuti sono il modo principale per contribuire alla Documentazione di Fabric.

Tutte le contribuzioni con contenuti passano per tre fasi:

1. Indicazioni per l'Espansione (se necessaria)
2. Verifica dei Contenuti
3. Pulizia (Grammatica ecc.)

Tutto il contenuto deve rispettare le nostre [linee guida per lo stile](#style-guidelines).

### 1. Prepara le Tue Modifiche {#1-prepare-your-changes}

Il sito è open-source, ed è sviluppato in una repository su GitHub, il che significa che ci affidiamo al flow GitHub:

1. [Crea una fork della Repository su GitHub](https://github.com/FabricMC/fabric-docs/fork)
2. Crea un nuovo branch sulla tua fork
3. Aggiungi le tue modifiche su quel branch
4. Apri una Pull Request alla repository originale

Puoi leggere di più riguardo al flow GitHub [qui](https://docs.github.com/en/get-started/using-github/github-flow).

È possibile fare modifiche dall'interfaccia web su GitHub, oppure puoi sviluppare e ottenere un'anteprima del sito localmente.

#### <Badge type="tip">localmente</Badge> Clonare la Tua Fork {#clone-your-fork}

Se vuoi sviluppare localmente, dovrai installare [Git](https://git-scm.com/).

Dopo di che, clona la tua fork della repository con:

```sh
# make sure to replace "your-username" with your actual username
git clone https://github.com/your-username/fabric-docs.git
```

#### <Badge type="tip">localmente</Badge> Installare le Dipendenze {#install-dependencies}

Se vuoi ottenere un'anteprima locale delle tue modifiche, dovrai installare [Node.js 18+](https://nodejs.org/en/).

Dopo di che, assicurati di installare tutte le dipendenze con:

```sh
npm install
```

#### <Badge type="tip">localmente</Badge> Eseguire il Server di Sviluppo {#run-the-development-server}

Questo di permetterà di ottenere un'anteprima locale delle tue modifiche presso `localhost:5173` e ricaricherà automaticamente la pagina quando farai modifiche.

```sh
npm run dev
```

Ora puoi aprire e navigare sul sito dal browser visitando `http://localhost:5173`.

#### <Badge type="tip">localmente</Badge> Costruire il Sito {#building-the-website}

Questo compilerà tutti i file Markdown in HTML statico e li posizionerà in `.vitepress/dist`:

```sh
npm run build
```

#### <Badge type="tip">localmente</Badge> Ottenere un'Anteprima del Sito Costruito {#previewing-the-built-website}

Questo avvierà un server locale in porta `4173` che servirà il contenuto trovato in `.vitepress/dist`:

```sh
npm run preview
```

#### <Badge type="tip">localmente</Badge> Aprire una Pull Request {#opening-a-pull-request}

Quando sarai felice delle tue modifiche, puoi fare `push` delle tue modifiche:

```sh
git add .
git commit -m "Description of your changes"
git push
```

Dopo di che segui il link nell'output di `git push` per aprire una PR.

### 2. Indicazioni per l'Espansione Se Necessaria {#2-guidance-for-expansion-if-needed}

Se il team della documentazione crede che tu possa espandere la tua pull request, un membro del team aggiungerà l'etichetta `can-expand` alla tua pull request assieme a un commento che spiega cosa credono che tu possa espandere. Se sei d'accordo con il consiglio, puoi espandere la tua pull request.

Non sentirti obbligato a espandere la tua pull request. Se non vuoi espandere la tua pull request, puoi semplicemente chiedere che l'etichetta `can-expand` venga rimossa.

Se non vuoi espandere la tua pull request, ma ti va bene che qualcun altro lo faccia successivamente, sarebbe meglio creare un'issue sulla [pagina Issues](https://github.com/FabricMC/fabric-docs/issues) e spiegare cosa credi che si possa espandere.

### 3. Verifica dei Contenuti {#3-content-verification}

Tutte le pull request che aggiungono contenuti sono sottoposte a verifica dei contenuti, questa è la fase più importante poiché assicura che il contenuto sia accurato e segua le linee guida per lo stile della Documentazione di Fabric.

### 4. Pulizia {#4-cleanup}

Questa fase è quella dove il team della documentazione correggerà ogni errore grammaticale e farà altre modifiche che crede siano necessarie prima di unire la pull request!

## Contribuire al Framework {#contributing-framework}

Framework si riferisce alla struttura interna del sito, ogni pull request che modifica il framework del sito dovrebbe essere etichettata con l'etichetta `framework`.

Dovresti davvero fare pull request riguardanti il framework solo dopo esserti consultato con il team della documentazione nel [Discord di Fabric](https://discord.gg/v6v4pMv) o tramite un'issue.

:::info
Modificare i file nella sidebar e la configurazione della barra di navigazione non conta come pull request riguardante il framework.
:::

## Linee Guida per lo Stile {#style-guidelines}

Se fossi incerto riguardo a qualsiasi cosa, puoi chiedere nel [Discord di Fabric](https://discord.gg/v6v4pMv) o tramite GitHub Discussions.

### Scrivi l'Originale in Inglese Americano {#write-the-original-in-american-english}

Tutta la documentazione originale è scritta in inglese, seguendo le regole grammaticali americane.

Anche se potresti usare [LanguageTool](https://languagetool.org/) per controllare la tua grammatica mentre scrivi, non preoccupartene troppo. Il nostro team di documentazione revisionerà e correggerà la grammatica durante la fase di pulizia. Tuttavia, fare uno sforzo perché sia corretta già dall'inizio può farci risparmiare del tempo.

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

Ogni intestazione deve avere un'ancora, che viene utilizzata per collegarsi a quell'intestazione:

```md
# This Is a Heading {#this-is-a-heading}
```

L'ancora deve usare caratteri minuscoli, numeri e trattini.

### Posiziona il Codice Nella Mod `/reference` {#place-code-within-the-reference-mod}

Se crei o modifichi pagine contenenti codice, metti il codice in una posizione appropriata nella mod reference (presente nella cartella `/reference` della repository). Dopo di che, usa la [funzione code snippet fornita da VitePress](https://vitepress.dev/guide/markdown#import-code-snippets) per incorporare il codice.

Per esempio, per evidenziare le linee 15-21 del file `FabricDocsReference.java` dalla mod reference:

::: code-group

```md
<<< @/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java{15-21}
```

<<< @/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java{15-21}[java]

:::

Se ti serve un controllo più raffinato, puoi usare la [funzione transclude da `markdown-it-vuepress-code-snippet-enhanced`](https://github.com/fabioaanthony/markdown-it-vuepress-code-snippet-enhanced).

Per esempio, questo incorporerà le sezioni del suddetto file che sono contrassegnate con il tag `#entrypoint`:

::: code-group

```md
@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java)
```

@[code transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java)

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
