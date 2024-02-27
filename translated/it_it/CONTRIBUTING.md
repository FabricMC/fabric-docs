# Linee Guida per la Contribuzione alla Documentazione di Fabric

Questo sito usa [VitePress](https://vitepress.vuejs.org/) per generare HTML statico dai vari file Markdown. Dovresti familiarizzare con le estensioni per Markdown che VitePress supporta [qui.](https://vitepress.vuejs.org/guide/markdown.html#features)

## Indice

- [Linee Guida per la Contribuzione alla Documentazione di Fabric](#linee-guida-per-la-contribuzione-alla-documentazione-di-fabric)
  - [Come Contribuire](#come-contribuire)
  - [Contribuire al Framework](#contribuire-al-framework)
  - [Contribuire con Contenuti](#contribuire-con-contenuti)
    - [Linee Guida per lo Stile](#linee-guida-per-lo-stile)
    - [Guida per l'Espansione](#guida-per-l-espansione)
    - [Verifica dei Contenuti](#verifica-dei-contenuti)
    - [Pulizia](#pulizia)

## Come Contribuire

È consigliato creare un nuovo branch sulla tua fork della repository per ogni pull request che fai. Questo rende più semplice la gestione di pull request multiple allo stesso tempo.

**Se vuoi vedere un'anteprima delle tue modifiche localmente, dovrai installare [Node.js 18+](https://nodejs.org/en/)**

Prima di eseguire qualsiasi comando tra i seguenti, assicurati di eseguire `npm install` per installare tutte le dipendenze.

**Eseguire il server di sviluppo:**

Questo ti permetterà di vedere un'anteprima delle tue modifiche localmente presso `localhost:3000` e ricaricherà automaticamente la pagina quando farai modifiche.

```bash
npm run dev
```

**Costruire il sito:**

Questo compilerà tutti i file Markdown a file HTML statici e li posizionerà in `.vitepress/dist`

```bash
npm run build
```

**Vedere un'anteprima del sito costruito:**

Questo avvierà un server locale in porta 3000 che presenta il contenuto trovato in `.vitepress/dist`

```bash
npm run preview
```

## Contribuire al Framework

Framework si riferisce alla struttura interna del sito, ogni pull request che modifica il framework del sito dovrebbe essere etichettata con l'etichetta `framework`.

Dovresti davvero fare pull request riguardanti il framework solo dopo esserti consultato con il team della documentazione nel [Discord di Fabric](https://discord.gg/v6v4pMv) o tramite un'issue.

**Nota: Modificare i file nella sidebar e la configurazione della barra di navigazione non conta come pull request riguardante il framework.**

## Contribuire con Contenuti

Contribuire con contenuti è il modo principale per contribuire alla Documentazione di Fabric.

Tutti i contenuti devono seguire le nostre linee guida per lo stile.

### Linee Guida per lo Stile

Tutte le pagine sul sito della Documentazione di Fabric devono seguire la guida per lo stile. Se fossi incerto riguardo a qualcosa, puoi chiedere nel [Discord di Fabric](https://discord.gg/v6v4pMv) o tramite GitHub Discussions.

La guida per lo stile è la seguente:

1. Tutte le pagine devono avere un titolo e una descrizione nel frontmatter.

   ```md
   ---
   title: Questo è il titolo della pagina
   description: Questa è la descrizione della pagina
   authors:
     - NomeUtenteGitHubQui
   ---

   # ...
   ```

2. Se crei o modifichi pagine contenenti codice, metti il codice in una posizione appropriata all'interno della mod reference (posizionata nella cartella `/reference` della repository). Dopo di che, utilizza la [funzione code snippet offerta da VitePress](https://vitepress.dev/guide/markdown#import-code-snippets) per incorporare il codice, o se ti serve un controllo più raffinato, puoi usare la [funziona transclude da `markdown-it-vuepress-code-snippet-enhanced`](https://github.com/fabioaanthony/markdown-it-vuepress-code-snippet-enhanced).

   **Esempio:**

   ```md
   <<< @/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java{15-21 java}
   ```

   Questo incorporerà le linee 15-21 del file `FabricDocsReference.java` nella mod reference.

   Lo snippet di codice risultante avrà il seguente aspetto:

   ```java
     @Override
     public void onInitialize() {
       // This code runs as soon as Minecraft is in a mod-load-ready state.
       // However, some things (like resources) may still be uninitialized.
       // Proceed with mild caution.

       LOGGER.info("Hello Fabric world!");
     }
   ```

   **Esempio con transclude:**

   ```md
   @[code transcludeWith=#test_transclude](@/reference/.../blah.java)
   ```

   Questo incorporerà le sezioni di `blah.java` che sono contrassegnate con il tag `#test_transclude`.

   Per esempio:

   ```java
   public final String test = "Bye World!"

   // #test_transclude
   public void test() {
     System.out.println("Hello World!");
   }
   // #test_transclude
   ```

   Solo il codice tra i tag `#test_transclude` verrà incorporato.

   ```java
   public void test() {
     System.out.println("Hello World!");
   }
   ```

3. Seguiamo le regole della grammatica inglese americana. Anche se potresti usare [LanguageTool](https://languagetool.org/) per controllare la tua grammatica mentre scrivi, non preoccupartene troppo. Il nostro team di documentazione revisionerà e correggerà la grammatica durante la fase di pulizia. Ciononostante, fare uno sforzo perché sia giusta già dall'inizio può farci risparmiare del tempo.

4. Se stai creando una nuova sezione, dovresti creare una nuova barra laterale nella cartella `.vitepress/sidebars` e aggiungerla al file `config.mts`. Se hai bisogno di assistenza con questo, per favore chiedi nel canale `#wiki` del [Discord di Fabric](https://discord.gg/v6v4pMv).

5. Quando crei una nuova pagina, dovresti aggiungerla alla sidebar appropriata nella cartella `.vitepress/sidebars`. Di nuoco, se hai bisogno di assistenza, chiedi nel canale `#wiki` del Discord di Fabric.

6. Ogni immagine dovrebbe essere messa in una posizione appropriata nella cartella `/assets`.

7. ⚠️ **Quando metti link ad altre pagine, usa link relativi.** ⚠️

   Questo è dovuto al sistema di versioning in utilizzo, che processerà i link per aggiungerci la versione anticipatamente. Se usassi link assoluti, il numero di versione non verrebbe aggiunto al link.

   Per esempio, per una pagina nella cartella `/players`, per mettere un link alla pagina `installing-fabric` che si trova in `/players/installing-fabric.md`, dovresti fare il seguente:

   ```md
   [Questo è un link ad un'altra pagina](./installing-fabric.md)
   ```

   **NON** dovresti fare il seguente:

   ```md
   [Questo è un link ad un'altra pagina](/player/installing-fabric)
   ```

Tutti i contributi al contenuto passano per tre fasi:

1. Guida per l'espansione (se possibile)
2. Verifica dei Contenuti
3. Pulizia (Grammatica ecc.)

### Guida per l'Espansione

Se il team della documentazione crede che tu possa espandere la tua pull request, un membro del team aggiungerà l'etichetta `expansion` alla tua pull request assieme ad un commento che spiega cosa credono che tu possa espandere. Se sei d'accordo con il consiglio, puoi espandere la tua pull request.

**Non sentirti obbligato ad espandere la tua pull request.** Se non vuoi espandere la tua pull request, puoi semplicemente chiedere che l'etichetta `expansion` venga rimossa.

Se non vuoi espandere la tua pull request, ma sei contento se qualcun altro lo facesse successivamente, sarebbe meglio creare un'issue sulla [pagina Issues](https://github.com/FabricMC/fabric-docs/issues) e spiegare cosa tu creda che si debba espandere.

### Verifica dei Contenuti

Tutte le pull request che aggiungono contenuti sono sottoposte a verifica dei contenuti, questa è la fase più importante poiché assicura che il contenuto sia accurato e segua la guida per lo stile della Documentazione di Fabric.

### Pulizia

Questa fase è quella dove il team della documentazione correggerà ogni errore grammaticale e farà altre modifiche che crede siano necessarie prima di unire la pull request!
