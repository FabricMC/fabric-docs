---
title: Creare un Progetto
description: Una guida passo per passo su come creare un nuovo progetto per una mod con il generatore di mod modello di Fabric.
authors:
  - IMB11
---

# Creare un Progetto {#creating-a-project}

Fabric offre un modo facile per creare un nuovo progetto per una mod attraverso il Generatore di Mod Modello di Fabric - se vuoi, puoi creare un nuovo progetto manualmente usando la repository della mod esempio, dovresti riferirti alla sezione [Creazione Manuale del Progetto](#creazione-manuale-del-progetto).

## Generare un Progetto {#generating-a-project}

Puoi usare il [Generatore di Mod Modello di Fabric](https://fabricmc.net/develop/template/) per generare un nuovo progetto per la tua mod - dovresti compilare i campi richiesti, come il nome del package e quello della mod, e la versione di Minecraft per la quale vuoi sviluppare.

![Anteprima del generatore](/assets/develop/getting-started/template-generator.png)

Se vuoi usare Kotlin, o vuoi aggiungere generatori di dati, puoi selezionare le opzioni apposite nella sezione `Opzioni Avanzate`.

![Sezione Opzioni Avanzate](/assets/develop/getting-started/template-generator-advanced.png)

Una volta che hai compilato i campi richiesti, premi il pulsante `Generate`, e il generatore creerà un nuovo progetto per te sotto forma di file zip.

Dovresti estrarre questo file zip a una posizione che scegli tu, e poi aprire la cartella estratta in IntelliJ IDEA:

![Prompt Apri Progetto](/assets/develop/getting-started/open-project.png)

## Importare il Progetto {#importing-the-project}

Non appena hai aperto il progetto in IntelliJ IDEA, l'ambiente di sviluppo dovrebbe automaticamente caricare la configurazione Gradle del progetto ed effettuare le operazioni di setup necessarie.

Se ricevi una notifica riguardo a uno script di build Gradle, dovresti cliccare il pulsante `Importa Progetto Gradle`:

![Prompt di Gradle](/assets/develop/getting-started/gradle-prompt.png)

Quando il progetto sarà importato, dovresti vedere i file del progetto nell'explorer di progetto, e dovresti poter cominciare a sviluppare la tua mod.

## Creazione Manuale del Progetto {#manual-project-creation}

:::warning
Ti servirà che [Git](https://git-scm.com/) sia installato per clonare la repository della mod esempio.
:::

Se non puoi usare il Generatore di Mod Modello di Fabric, dovresti creare un nuovo progetto manualmente seguendo questi passaggi.

Anzitutto, clona la repository della mod esempio tramite Git:

```sh
git clone https://github.com/FabricMC/fabric-example-mod/ mio-progetto-mod
```

Questo clonerà la repository in una nuova cartella chiamata `mio-progetto-mod`.

Dovresti poi eliminare la cartella `.git` dalla repository clonata, e poi aprire il progetto in IntelliJ IDEA. Se la cartella `.git` dovesse non apparire, dovresti attivare la visualizzazione dei file nascosti nel tuo gestore file.

Quando avrai aperto il progetto in IntelliJ IDEA, esso dovrebbe automaticamente caricare la configurazione Gradle del progetto ed effettuare le operazioni di setup necessarie.

Di nuovo, come già detto in precedenza, se ricevi una notifica riguardo a uno script di build Gradle, dovresti cliccare il pulsante `Importa Progetto Gradle`.

### Modificare il Template {#modifying-the-template}

Una volta che il progetto sarà importato, dovresti modificare i dettagli del progetto per corrispondere a quelli della tua mod:

- Modifica il file `gradle.properties` del tuo progetto per cambiare le proprietà `maven_group` e `archive_base_name` e farle corrispondere con i dettagli della tua mod.
- Modifica il file `fabric.mod.json` per cambiare le proprietà `id`, `name`, e `descrizione` per farle corrispondere ai dettagli della tua mod.
- Assicurati di aggiornare le versioni di Minecraft, i mapping, il Loader e il Loom - tutte queste possono essere trovate attraverso https://fabricmc.net/develop/ - per farle corrispondere alle versioni che vorresti prendere di mira.

Ovviamente puoi cambiare il nome del package e la classe principale della mod per farli corrispondere ai dettagli della tua mod.
