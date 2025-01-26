---
title: Trucchi Riguardanti l'IDE
description: Informazioni utili su come gestire e navigare con l'IDE nel tuo progetto efficientemente.
authors:
  - JR1811
  - AnAwesomGuy
---

# Trucchi Riguardanti l'IDE {#ide-tips-and-tricks}

Questa pagina fornisce un po' di informazioni utili, per velocizzare e facilitare il workflow degli sviluppatori. Integrale nel tuo lavoro, se desideri.
Potrebbe servire del tempo per imparare e abituarsi alle scorciatoie e alle altre opzioni. Puoi usare questa pagina come riferimento per questo.

:::warning
Le scorciatoie indicate si riferiscono alla mappatura di IntelliJ IDEA predefinita, se non specificato altrimenti.
Fai riferimento alle impostazioni in `File > Settings > Keymap` o cerca per la funzionalità altrove se usi un layout di tastiera diverso.
:::

## Attraversare i Progetti {#traversing-projects}

### Manualmente {#manually}

IntelliJ ha molti modi diversi di navigare i progetti. Se hai generato le fonti con i comandi `./gradlew genSources` nel terminale o hai usato le operazioni Gradie `Tasks > fabric > genSources` nella finestra di Gradle, puoi aprire i file sorgente di Minecraft manualmente nelle Librerie Esterne della Schermata del Progetto.

![Operazioni di Gradle](/assets/develop/misc/using-the-ide/traversing_01.png)

Il codice sorgente di Minecraft si può trovare se cerchi `net.minecraft` nella schermata External Libraries della finestra del progetto.
Se il tuo progetto usa sorgenti suddivise dal [Generatore di Mod Modello](https://fabricmc.net/develop/template) online, troverai due sorgenti, indicate dal nome (client/common).
Inoltre altre sorgenti di progetti, librerie e dipendenze, importate con il file `build.gradle`, saranno anche disponibili.
Questo metodo si usa spesso mentre si cercano media, tag e altri file.

![Libreria Esterna](/assets/develop/misc/using-the-ide/traversing_02_1.png)

![Sorgenti Suddivise](/assets/develop/misc/using-the-ide/traversing_02_2.png)

### Ricerca {#search}

Premere <kbd>Maiusc</kbd> due volte apre una finestra Cerca. Lì puoi cercare i file e le classe del tuo progetto. Attivando la casella di controllo `include non-project items` o premendo <kbd>Maiusc</kbd> due volte di nuovo, la ricerca non includerà solo il tuo progetto, ma anche altri, come le Librerie Esterne.

Puoi anche usare le scorciatoie <kbd>⌘/CTRL</kbd>+<kbd>N</kbd> per cercare classi e <kbd>⌘/CTRL</kbd>+<kbd>Maiusc</kbd>+<kbd>N</kbd> per cercare in tutti i _file_.

![Finestra cerca](/assets/develop/misc/using-the-ide/traversing_03.png)

### Finestra Recenti {#recent-window}

Un altro strumento utile in IntelliJ è la finestra `Recent`. Puoi aprirla con la scorciatoia <kbd>⌘/CTRL</kbd>+<kbd>E</kbd>.
Lì puoi saltare ai file che hai già visitato, e aprire finestre degli strumenti, come [Struttura](#structure-of-a-class) o [Preferiti](#bookmarks).

![Finestra Recenti](/assets/develop/misc/using-the-ide/traversing_04.png)

## Navigare il Codice {#traversing-code}

### Salta alla Definizione / Utilizzo {#jump-to-definition-usage}

Se devi controllare la definizione o gli utilizzi di variabili, metodi, classi e altre cose, puoi premere <kbd>⌘/CTRL</kbd>+<kbd>Clic Sinistro / B</kbd>
o usare il <kbd>Pulsante Centrale del Mouse</kbd> (premere la rotellina) sul nome. Così eviterai lunghe sessioni di scorrimento o una ricerca manuale della definizione che troveresti in un altro file.

Puoi anche usare <kbd>⌘/CTRL</kbd>+<kbd>⌥/Maiusc</kbd>+<kbd>Clic Sinistro / B</kbd> per vedere tutte le implementazioni di una classe o di un'interfaccia.

### Preferiti {#bookmarks}

Puoi aggiungere linee di codice, file o anche schede aperte dell'Editor ai preferiti.
Specialmente se cerchi nel codice sorgente, aiuta a segnare posizioni che vuoi ritrovare velocemente nel futuro.

Clicca con il tasto destro un file nella finestra `Project`, una scheda dell'editor o il numero di una linea in un file.
Creare `Preferiti Mnemonici` ti permette di passare velocemente a quei preferiti con le scorciatoie appropriate, <kbd>⌘/CTRL</kbd> e il numero che hai scelto.

![Imposta preferito](/assets/develop/misc/using-the-ide/traversing_05.png)

È possibile creare più liste di preferiti nella finestra `Preferiti` contemporaneamente se vuoi separarli o ordinarli.
I [Punti di Interruzione](./basic-problem-solving#breakpoint) saranno anche presenti lì.

![Finestra preferiti](/assets/develop/misc/using-the-ide/traversing_06.png)

## Analizzare le Classi {#analyzing-classes}

### Struttura di una Classe {#structure-of-a-class}

Aprendo la finestra `Structure` (<kbd>⌘/Alt</kbd>+<kbd>7</kbd>) otterrai una panoramica della classe attualmente attiva. Puoi vedere quali Classi e quali Enum si trovano in quel file, quali metodi sono stati implementati e quali attributi e variabili sono dichiarate.

A volte può essere utile attivare anche l'opzione `Inherited` in cima alle opzioni Visualizza, quando si cercano metodi specifici di cui fare override.

![Finestra Structure](/assets/develop/misc/using-the-ide/analyzing_01.png)

### Gerarchia dei Tipi di una Classe {#type-hierarchy-of-a-class}

Posizionando il cursore sul nome di una classe e premendo <kbd>⌘/CTRL</kbd>+<kbd>H</kbd> puoi aprire una nuova finestra Type Hierarchy, che mostra tutte le classi genitore e figlie.

![Finestra Type Hierarchy](/assets/develop/misc/using-the-ide/analyzing_02.png)

## Utilità del Codice {#code-utility}

### Completamento del Codice {#code-completion}

Il completamento del codice dovrebbe essere attivo in maniera predefinita. Otterrai consigli mentre scrivi il tuo codice in automatico.
Se l'hai per errore chiuso o hai spostato il cursore ad un altro punto, puoi usare <kbd>⌘/CTRL</kbd>+<kbd>Spazio</kbd> per riaprirli nuovamente.

Per esempio, usando espressioni lambda, puoi scriverli velocemente usando questo metodo.

![Lambda con molti parametri](/assets/develop/misc/using-the-ide/util_01.png)

### Generazione del Codice {#code-generation}

Si può accedere velocemente al menu Generate con <kbd>Alt</kbd>+<kbd>Ins</kbd> (<kbd>⌘ Comando</kbd>+<kbd>N</kbd> su Mac) o andando su `Code` in cima e selezionando `Generate`.
In un file Java, potrai generare costruttori, getter, setter, fare override o implementare metodi, e molto altro.
Puoi anche generare accessori e invocatori se hai installato il [plugin per Sviluppo Minecraft](./getting-started/setting-up-a-development-environment#minecraft-development).

Inoltre puoi fare velocemente override di metodi con <kbd>⌘/CTRL</kbd>+<kbd>O</kbd> e implementare metodi con <kbd>⌘/CTRL</kbd>+<kbd>I</kbd>.

![Menu generazione di codice in un file Java](/assets/develop/misc/using-the-ide/generate_01.png)

In un file di test Java, ti saranno fornite opzioni per generare metodi di test correlati, come segue:

![Menu generazione di codice in un file di test Java](/assets/develop/misc/using-the-ide/generate_02.png)

### Mostrare i Parametri {#displaying-parameters}

La visualizzazione dei parametri dovrebbe essere attiva in maniera predefinita. Otterrai automaticamente i tipi e i nomi dei parametri mentre scrivi il tuo codice.
Se li hai per errore chiusi o hai spostato il cursore ad un altro punto, puoi usare <kbd>⌘/CTRL</kbd>+<kbd>P</kbd> per riaprirli nuovamente.

Metodi e classi possono avere più implementazioni con parametri diversi, fenomeno detto Overloading. In questo modo puoi decidere quale implementazione vuoi usare, mentre scrivi la chiamata al metodo.

![Visualizzazione dei parametri del metodo](/assets/develop/misc/using-the-ide/util_02.png)

### Refactoring {#refactoring}

La riscrittura è il processo di ristrutturazione del codice senza cambiarne la funzionalità durante l'esecuzione. Rinonimare o eliminare in sicurezza parte del codice è un esempio di ciò, ma anche l'estrazione di codice in più metodi separati e l'introduzione di nuove variabili per parti di codice ripetute è detto "refactoring".

Tanti ambienti di sviluppo hanno una vasta gamma di strumenti per assistere questo processo. In intelliJ basta cliccare con il tasto destro file o parti del codice per accedere agli strumenti di refactoring disponibili.

![Refactoring](/assets/develop/misc/using-the-ide/refactoring_01.png)

È soprattutto utile abituarsi alla scorciatoia dello strumento di refactoring `Rename`, ovvero <kbd>Maiusc</kbd>+<kbd>F6</kbd>, poiché probabilmente rinonimerai molte cose nel futuro.
Usando questa funzione, ogni occorrenza del codice rinonimato sarà rinonimata e la sua funzionalità rimarrà intatta.

Puoi anche riformattare il codice secondo il tuo stile di codice.
Per fare questo, seleziona il codice che vuoi riformattare (se non selezioni niente, l'intero file sarà riformattato) e premi <kbd>⌘/CTRL</kbd>+<kbd>⌥/ALT</kbd>+<kbd>L</kbd>.
Per cambiare il modo in cui IntelliJ formatta il codice, controlla le impostazioni presso `File > Settings > Editor > Code Style > Java`.

#### Azioni Contestuali {#context-actions}

Le azioni contestuali permettono di effettuare refactoring di sezioni di codice specifiche in base al contesto.
Per usarle, basta muovere il cursore sull'area di cui si vuol fare refactoring, e premere <kbd>⌥/ALT</kbd>+<kbd>Invio</kbd> o cliccare la lampadina a sinistra.
Ci sarà un popup che mostrerà azioni contestuali che si possono usare per il codice selezionato.

![Esempi di azioni contestuali](/assets/develop/misc/using-the-ide/context_actions_01.png)

![Esempi di azioni contestuali](/assets/develop/misc/using-the-ide/context_actions_02.png)

### Trova e Sostituisci i Contenuti di un File {#search-and-replace-file-content}

A volte sono necessari strumenti più semplici per modificare le occorrenze del codice.

| Scorciatoie                                      | Funzione                                                                                     |
| ------------------------------------------------ | -------------------------------------------------------------------------------------------- |
| <kbd>⌘/CTRL</kbd>+<kbd>F</kbd>                   | Trova nel file corrente                                                                      |
| <kbd>⌘/CTRL</kbd>+<kbd>R</kbd>                   | Sostituisci nel file corrente                                                                |
| <kbd>⌘/CTRL</kbd>+<kbd>Maiusc</kbd>+<kbd>F</kbd> | Trova in uno scope più grande (e imposta un filtro di tipo di file)       |
| <kbd>⌘/CTRL</kbd>+<kbd>Maiusc</kbd>+<kbd>R</kbd> | Sostituisci in uno scope più grande (e imposta un filtro di tipo di file) |

Quando ben sfruttati, tutti questi strumenti permettono una ricerca più specifica grazie alle [Regex](https://en.wikipedia.org/wiki/Regular_expression).

![Sostituzione regex](/assets/develop/misc/using-the-ide/search_and_replace_01.png)

### Altre Scorciatoie Utili {#other-keybinds}

Selezionare del testo e usare <kbd>⌘/CTRL</kbd>+<kbd>Maiusc</kbd>+<kbd>↑ Su / ↓ Giù</kbd> muoverà il codice selezionato in alto o in basso.

In IntelliJ, la scorciatoia per `Ripeti` potrebbe non essere quella solita, ovvero <kbd>⌘/CTRL</kbd>+<kbd>Y</kbd> (Elimina Linea).
Potrebbe invece essere <kbd>⌘/CTRL</kbd>+<kbd>Maiusc</kbd>+<kbd>Z</kbd>. Puoi cambiarla in **Keymap**.

<!-- markdownlint-disable-next-line search-replace -->

Per altre scorciatoie da tastiera, consulta la [documentazione di IntelliJ](https://www.jetbrains.com/help/idea/mastering-keyboard-shortcuts.html).

## Commenti {#comments}

Il buon codice dovrebbe essere facilmente leggibile e dovrebbe [documentarsi "da solo"](https://bytedev.medium.com/code-comment-anti-patterns-and-why-the-comment-you-just-wrote-is-probably-not-needed-919a92cf6758).
Scegliere nomi espressivi per variabili, classi e metodi aiuta molto, ma a volte i commenti sono necessari per lasciare note o per disattivare codice **temporaneamente** a fini di testing.

Per commentare del codice velocemente, puoi selezionare del testo e usare le scorciatoie <kbd>⌘/CTRL</kbd>+<kbd>/</kbd> (commento linea) e <kbd>⌘/CTRL</kbd>+<kbd>⌥/Maiusc</kbd>+<kbd>/</kbd> (commento blocco).

Ora puoi evidenziare il codice necessario (o basta che il cursore sia sopra ad esso) e usare le scorciatoie per commentare una sezione.

```java
// private static final int PROTECTION_BOOTS = 2;
private static final int PROTECTION_LEGGINGS = 5;
// private static final int PROTECTION_CHESTPLATE = 6;
private static final int PROTECTION_HELMET = 1;
```

```java
/*
ModItems.initialize();
ModSounds.initializeSounds();
ModParticles.initialize();
*/

private static int secondsToTicks(float seconds) {
    return (int) (seconds * 20 /*+ 69*/);
}
```

### Compressione del Codice {#code-folding}

In IntelliJ, vicino ai numeri di linea, potresti vedere delle piccole freccie.
Possono essere usate per comprimere temporaneamente metodi, istruzioni if, classi e molte altre cose su cui non stai lavorando attivamente.
Per creare un blocco personalizzato che possa essere compresso, usa i commenti `region` e `endregion`.

```java
// region collapse block name
    ModBlocks.initialize();
    ModBlockEntities.registerBlockEntityTypes();
    ModItems.initialize();
    ModSounds.initializeSounds();
    ModParticles.initialize();
// endregion
```

![Compressione di una regione](/assets/develop/misc/using-the-ide/comments_02.png)

:::warning
Se noti che ne stai usando troppi, considera una riscrittura del codice per renderlo più leggibile!
:::

### Disattivare il Formattatore {#disabling-formatter}

Si possono inoltre usare dei commenti per disattivare il formattatore durante il refactoring del codice menzionato sopra, rinchiudendo una sezione di codice così:

```java
//formatter:off (disable formatter)
    public static void disgustingMethod() { /* ew this code sucks */ }
//formatter:on (re-enable the formatter)
```

### Sopprimere le Ispezioni {#noinspection}

I commenti `//noinspection` possono essere usati per sopprimere ispezioni e avvisi.
Funzionalmente sono identici all'annotazione `@SuppressWarnings`, ma non hanno la limitazione di essere un'annotazione e possono quindi essere usati per le istruzioni.

```java
// below is bad code and IntelliJ knows that

@SuppressWarnings("rawtypes") // annotations can be used here
List list = new ArrayList();

//noinspection unchecked (annotations cannot be here so we use the comment)
this.processList((List<String>)list);

//noinspection rawtypes,unchecked,WriteOnlyObject (you can even suppress multiple!)
new ArrayList().add("bananas");
```

:::warning
Se noti che stai sopprimendo troppi avvisi, considera una riscrittura del codice per non produrne così tanti!
:::

### Note TODO e FIXME {#todo-and-fixme-notes}

Lavorando sul codice, può tornarti utile lasciare note su ciò che devi ancora completare. A volte potresti notare un problema nel codice, ma vuoi continuare a lavorare su ciò che stai facendo. In questi casi, usa i commenti `TODO` o `FIXME`.

![Commenti TODO e FIXME](/assets/develop/misc/using-the-ide/comments_03.png)

IntelliJ terrà conto di essi nella finestra `TODO` e ti notificherà se stai per fare commit di codice che usa questo tipo di commento.

![Commenti TODO e FIXME](/assets/develop/misc/using-the-ide/comments_04.png)

![Commit con TODO](/assets/develop/misc/using-the-ide/comments_05.png)

### Javadocs {#javadocs}

Un ottimo modo di documentare il tuo codice è usare i JavaDoc.
I JavaDoc non solo forniscono informazioni utili per l'implementazione di metodi e classi, ma sono anche profondamente integrati in IntelliJ.

Passando il mouse sopra ai nomi di metodi o classi ai quali sono stati aggiunti commenti JavaDoc, essi mostreranno queste informazioni nella finestra apposita.

![JavaDoc](/assets/develop/misc/using-the-ide/comments_06.png)

Per iniziare, ti basta scrivere `/**` sopra al metodo o alla definizione della classe e premere Invio. IntelliJ genererà automaticamente linee per il valore restituito e per i parametri, ma le puoi modificare come ti pare. Ci sono parecchie funzioni personalizzate disponibili e puoi addirittura usare HTML se ti serve.

La classe `ScreenHandler` di Minecraft ti mostra alcuni esempi. Per attivare o disattivare la finestra di rendering, usa il pulsante penna vicino ai numeri di linea.

![Modifica dei JavaDoc](/assets/develop/misc/using-the-ide/comments_07.png)

## Ottimizzare IntelliJ Ulteriormente {#optimizing-intellij-further}

Ci sono parecchie altre scorciatoie e tanti trucchi utili, che però oltrepasserebbero gli obiettivi di questa pagina.
Jetbrains ha tanti buoni discorsi, video e pagine di documentazione riguardo a come personalizzare il tuo ambiente di lavoro ancora di più.

### Completamento PostFix {#postfix-completion}

Usa il Completamento PostFix per alterare il codice velocemente dopo averlo scritto. Esempi spesso citati comprendono `.not`, `.if`, `.var`, `.null`, `.nn`, `.for`, `.fori`, `.return` e `.new`.
Oltre a quelli già esistenti, puoi anche aggiungerne di tuoi nelle Impostazioni di IntelliJ.

<VideoPlayer src="https://youtu.be/wvo9aXbzvy4">"IntelliJ IDEA Pro Tips: Postfix Completion" su YouTube</VideoPlayer>

### Modelli Live {#live-templates}

Usa i Modelli Live per generare il tuo codice ripetitivo personalizzato più velocemente.

<VideoPlayer src="https://youtu.be/XhCNoN40QTU">"IntelliJ IDEA Pro Tips: Live Templates" su YouTube</VideoPlayer>

### Altri Trucchi {#more-tips}

Anton Arhipov di Jetbrains ha anche avuto un discorso approfondito riguardo a Corrispondenza Regex, Completamento di Codice, Debugging e molti altri argomenti legati a IntelliJ.

<VideoPlayer src="https://youtu.be/V8lss58zBPI">"IntelliJ talk" di Anton Arhipov su YouTube</VideoPlayer>

Per ulteriori informazioni, controlla il [sito di Trucchi e Consigli di Jetbrains](https://blog.jetbrains.com/idea/category/tips-tricks) e la [documentazione di IntelliJ](https://www.jetbrains.com/help/idea/getting-started).
Tanti dei loro post possono essere applicati anche all'ecosistema Fabric.
