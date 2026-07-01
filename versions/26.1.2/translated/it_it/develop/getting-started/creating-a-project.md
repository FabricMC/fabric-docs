---
title: Creare un Progetto
description: Una guida passo per passo su come creare un nuovo progetto per una mod con il generatore di mod modello di Fabric.
authors:
  - Cactooz
  - IMB11
  - radstevee
  - Thomas1034
resources:
  https://fabricmc.net/develop/template/: Generatore di mod modello Fabric
  https://fabricmc.net/develop/: Pagina web Sviluppa con Fabric
---

Fabric offre un modo facile per creare un nuovo progetto per una mod attraverso il Generatore di Mod Modello di Fabric - se vuoi, puoi creare un nuovo progetto manualmente usando la repository della mod esempio, dovresti riferirti alla sezione [Creazione Manuale del Progetto](#creazione-manuale-del-progetto).

## Generare un Progetto {#generating-a-project}

Puoi usare il [Generatore di Mod Modello di Fabric](https://fabricmc.net/develop/template/) per generare un nuovo progetto per la tua mod - dovresti compilare i campi richiesti, come il nome della mod, quello del package, e la versione di Minecraft per la quale vuoi sviluppare.

Il nome del package dovrebbe essere minuscolo, separato da punti, e unico per evitare conflitti con package di altri programmatori. Di solito viene formattato come un domain internet invertito, per esempio `com.example.example-mod`.

:::warning IMPORTANTE

Prendi nota dell'ID della tua mod! Se trovi `example-mod` in questa documentazione, soprattutto nei percorsi dei file, dovrai sostituirlo con il tuo ID.

Se, per esempio, l'ID della tua mod fosse **`la-mia-mod`**, invece di _`resources/assets/example-mod`_ usa **`resources/assets/la-mia-mod`**.

:::

![Anteprima del generatore](/assets/develop/getting-started/template-generator.png)

Se vuoi usare Kotlin, buildscript in Kotlin, o vuoi aggiungere generatori di dati, puoi selezionare le opzioni apposite nella sezione `Opzioni Avanzate`.

::: info

Gli esempi in codice presenti su questo sito usano i [nomi del gioco non offuscato](../porting/mappings/#whats-going-on-with-mappings). Se hai una mod esistente che usa mapping diversi da quelli forniti da Mojang, leggi la pagina [Aggiornamento a 26.1](../porting/index) per maggiori informazioni.

:::

![Sezione Opzioni Avanzate](/assets/develop/getting-started/template-generator-advanced.png)

Una volta che hai compilato i campi richiesti, premi il pulsante `Generate`, e il generatore creerà un nuovo progetto per te sotto forma di file zip.

Dovresti estrarre questo file zip a una posizione che scegli tu, e poi aprire la cartella estratta nel tuo IDE.

::: tip

È buona prassi seguire queste regole nel scegliere il percorso del tuo progetto:

- Evita cartelle sincronizzate sul cloud (per esempio Microsoft OneDrive)
- Evita caratteri non in ASCII (per esempio emoji, lettere con l'accento)
- Evita spazi

Per esempio, un "buon" percorso potrebbe essere: `C:\Progetti\NomeDelProgetto`

:::

## Creazione Manuale del Progetto {#manual-project-creation}

:::info PREREQUISITI

Ti servirà che [Git](https://git-scm.com/) sia installato per clonare la repository della mod esempio.

:::

Se non puoi usare il Generatore di Mod Modello di Fabric, dovresti creare un nuovo progetto manualmente seguendo questi passaggi.

Anzitutto, clona la repository della mod esempio tramite Git:

```sh
git clone https://github.com/FabricMC/fabric-example-mod.git example-mod
```

Questo clonerà la repository in una nuova cartella chiamata `example-mod`.

Dovresti poi eliminare la cartella `.git` dalla repository clonata, e poi aprire il progetto. Se la cartella `.git` dovesse non apparire, dovresti attivare la visualizzazione dei file nascosti nel tuo gestore file.

### Impostare il Tuo IDE {#setting-up}

Quando avrai aperto il progetto nel tuo IDE, esso dovrebbe automaticamente caricare la configurazione Gradle del progetto ed effettuare le operazioni di setup necessarie.

Se ricevi una notifica riguardo a uno script di build Gradle, dovresti cliccare il pulsante `Importa Progetto Gradle`.

Per maggiori informazioni, dai un'occhiata alla pagina [Impostare il tuo IDE](./setting-up).

### Modificare il Template {#modifying-the-template}

Una volta che il progetto sarà importato, dovresti modificare i dettagli del progetto per corrispondere a quelli della tua mod:

- Modifica il file `gradle.properties` del tuo progetto per cambiare le proprietà `maven_group` e `archive_base_name` e farle corrispondere con i dettagli della tua mod.
- Modifica il file `fabric.mod.json` per cambiare le proprietà `id`, `name`, e `descrizione` per farle corrispondere ai dettagli della tua mod.
- Assicurati di aggiornare le versioni di Minecraft, i mapping, il Loader e il Loom - tutte queste possono essere trovate attraverso [Fabric per sviluppatori](https://fabricmc.net/develop/) - per farle corrispondere alle versioni che vorresti prendere di mira.

Poi puoi cambiare il nome del package e la classe principale della mod per farli corrispondere ai dettagli della tua mod.
