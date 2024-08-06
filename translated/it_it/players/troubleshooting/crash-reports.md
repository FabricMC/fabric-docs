---
title: Segnalazioni dei Crash
description: Impara cosa fare con le segnalazioni di crash, e come leggerle.
authors:
  - IMB11
---

# Segnalazioni dei Crash {#crash-reports}

:::tip
Se hai difficoltà nel trovare la causa del crash, puoi chiedere aiuto nel [Discord di Fabric](https://discord.gg/v6v4pMv) nei canali `#player-support` o `#server-admin-support`.
:::

Le segnalazioni di crash, o degli arresti anomali, sono una parte molto importante della risoluzione di problemi con il tuo gioco o server. Contengono molte informazioni riguardanti il crash, e ti possono aiutare a trovare la causa del crash.

## Trovare le Segnalazioni di Crash {#finding-crash-reports}

Le segnalazioni di crash sono salvate nella cartella `crash-reports` nella tua cartella del gioco. Se stai usando un server, essi sono salvati nella cartella `crash-reports` nella cartella del server.

Per launcher di terze parti, dovresti affidarti alla loro documentazione per sapere dove trovare le segnalazioni di crash.

Le segnalazioni di crash possono essere trovate nelle seguenti posizioni:

::: code-group

```:no-line-numbers [Windows]
%appdata%\.minecraft\crash-reports
```

```:no-line-numbers [macOS]
~/Library/Application Support/minecraft/crash-reports
```

```:no-line-numbers [Linux]
~/.minecraft/crash-reports
```

:::

## Leggere le Segnalazioni di Crash {#reading-crash-reports}

Le segnalazioni di crash sono molto lunghe, e possono causare confusione nella lettura. Tuttavia, contengono tante informazioni riguardanti il crash, e ti possono aiutare a trovare la causa del crash.

Per questa guida, useremo [questa segnalazione di crash come esempio](https://github.com/FabricMC/fabric-docs/blob/main/public/assets/players/crash-report-example.txt).

:::details Segnalazioni dei Crash

<<< @/public/assets/players/crash-report-example.txt{log}

:::

### Sezioni della Segnalazione di Crash {#crash-report-sections}

Le segnalazioni di crash consistono di varie sezioni, ciascuna separata con un'intestazione:

- `---- Minecraft Crash Report ----`, il riassunto della segnalazione. Questa sezione contiene l'errore principale che ha causato il crash, l'orario al quale è avvenuto, e la stack trace pertinente. Questa è la sezione più importante della segnalazione del crash poiché lo stack trace potrebbe solitamente contenere riferimenti alla mod che ha causato il crash.
- `-- Last Reload --`, questa sezione non è davvero utile tranne se il crash è avvenuto durante un ricaricamento delle risorse (<kbd>F3</kbd>+<kbd>T</kbd>). Questa sezione conterrà probabilmente l'orario dell'ultimo ricaricamento, e lo stack trace pertinente di qualsiasi errore che si sia verificato durante il processo di ricaricamento. Questi errori sono solitamente causati dai pacchetti risorse, e possono essere ignorati tranne se stanno causando problemi con il gioco.
- `-- System Details --`, questa sezione contiene informazioni riguardo al tuo sistema, come il sistema operativo, la versione di Java, e la quantità di memoria allocata al gioco. Questa sezione è utile per determinare se stai usando la versione corretta di Java, e se hai allocato abbastanza memoria al gioco.
  - In questa sezione, Fabric avrà incluso una linea personalizzata che dice `Fabric Mods:`, seguita da una lista di tutte le mod che hai installato. Questa sezione è utile per determinare se possibili conflitti potrebbero essersi verificati tra mod.

### Comprendere la Segnalazione del Crash {#breaking-down-the-crash-report}

Ora che sappiamo cos'è ciascuna sezione della segnalazione di crash, possiamo iniziare a suddividere la segnalazione di crash e trovare la causa del crash.

Usando l'esempio del link sopra, possiamo analizzare la segnalazione di crash e trovare la causa del crash, incluse le mod che l'hanno causato.

Lo stack trace nella sezione `---- Minecraft Crash Report ----` è il più importante in questo caso, poiché contiene l'errore principale che ha causato il crash. In questo caso, l'errore è `java.lang.NullPointerException: Cannot invoke "net.minecraft.class_2248.method_9539()" because "net.minecraft.class_2248.field_10540" is null`.

Con la quantità di mod menzionata nello stack trace, può essere difficile puntare il dito, ma la prima cosa da fare è cercare la mod che ha causato il crash.

<!-- TODO: show part of this file -->

<<< @/public/assets/players/crash-report-example.txt{8-9,14-15 log}

In questo caso, la mod che ha causato il crash è `snownee`, poiché è la prima mod menzionata nello stack trace.

Tuttavia, con la quantità di mod menzionata, potrebbe significare che ci sono problemi di compatibilità tra le mod, e che la mod che ha causato il crash potrebbe non essere la mod colpevole. In questo caso, è meglio segnalare il crash all'autore della mod, e lasciarglielo investigare.

## Crash che Coinvolgono Mixin {#mixin-crashes}

:::info
I mixin sono un modo che hanno le mod per modificare il gioco senza dover modificarne il codice sorgente. Sono usati da varie mod, e sono uno strumento molto potente per gli sviluppatori di mod.
:::

Quando un mixin causa un crash, esso menzionerà solitamente il mixin nello stack trace, e la classe che il mixin sta modificando.

I metodi mixin conterranno `modid$handlerName` nello stack trace, mentre `modid` è l'ID della mod, e `handlerName` è il nome del gestore del mixin.

```:no-line-numbers
... net.minecraft.class_2248.method_3821$$$modid$handlerName() ... // [!code focus]
```

Puoi usare queste informazioni per trovare la mod che ha causato il crash, e segnalare il crash all'autore della mod.

## Cosa Fare delle Segnalazioni di Crash {#what-to-do-with-crash-reports}

La migliore cosa da fare con le segnalazioni di crash è caricarle a un paste site, e poi condividere il link con l'autore della mod, o tramite il suo issue tracker o attraverso qualche mezzo di comunicazione (Discord ecc...).

Questo permetterà all'autore della mod d'investigare il crash, potenzialmente di riprodurlo, e di risolvere il problema che l'ha causato.

Paste site comuni usati frequentemente per le segnalazioni di crash sono:

- [GitHub Gist](https://gist.github.com/)
- [mclo.gs](https://mclo.gs/)
- [Pastebin](https://pastebin.com/)
