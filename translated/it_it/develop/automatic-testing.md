---
title: Testing Automatizzato
description: Una guida per scrivere test automatici con Fabric Loader JUnit.
authors:
  - kevinthegreat1
---

Questa pagina spiega come scrivere codice che testi parti della tua mod automaticamente. Ci sono due modi per testare la tua mod automaticamente: unit test con Fabric Loader JUnit o test nel gioco con il framework Gametest di Minecraft.

Gli unit test dovrebbero essere usato per testare le componenti del tuo codice, come i metodi e le classi ausiliarie, mentre test nel gioco avviano un client e un server Minecraft reali per eseguire i tuoi test, il che li rende appropriati per testare funzionalità e gameplay.

## Unit Testing {#unit-testing}

Poiché il modding in Minecraft si affida a strumenti di modifica del byte-code da eseguire come i Mixin, aggiungere e usare JUnit normalmente non funzionerebbe. Questo è il motivo per cui Fabric fornisce Fabric Loader JUnit, un plugin JUnit che attiva lo unit testing in Minecraft.

### Configurare Fabric Loader JUnit {#setting-up-fabric-loader-junit}

Anzitutto, dobbiamo aggiungere Fabric Loader JUnit all'ambiente di sviluppo. Aggiungi il seguente blocco di dipendenze al tuo `build.gradle`:

@[code transcludeWith=:::automatic-testing:1](@/reference/build.gradle)

Poi, dobbiamo informare Gradle su come usare Fabric Loader JUnit per il testing. Puoi fare ciò aggiungendo il codice seguente al tuo `build.gradle`:

@[code transcludeWith=:::automatic-testing:2](@/reference/latest/build.gradle)

### Scrivere Test {#writing-tests}

Appena ricaricato Gradle, sei pronto a scrivere test.

Questi test si scrivono proprio come altri test JUnit regolari, con un po' di configurazione aggiuntiva se vuoi accedere a classi che dipendono dalle registry, come `ItemStack`. Se JUnit ti è familiare, puoi saltare a [Impostare le Registry](#setting-up-registries).

#### Impostare la Tua Prima Classe di Test {#setting-up-your-first-test-class}

I test si scrivono nella cartella `src/test/java`.

Una convenzione per i nomi è quella di rispecchiare la struttura del package della classe che stai testando. Per esempio, per testare `src/main/java/com/example/docs/codec/BeanType.java`, dovresti creare la classe `src/test/java/com/example/docs/codec/BeanTypeTest.java`. Nota che abbiamo aggiunto `Test` alla fine del nome della classe. Questo ti permette anche di accedere facilmente a metodi e attributi privati al package.

Un'altra convenzione è avere un package `test`, quindi `src/test/java/com/example/docs/test/codec/BeanTypeTest.java`. Questo previene alcuni problemi dovuti all'uso dello stesso package se usi i moduli Java.

Dopo aver creato la classe di test, usa <kbd>⌘/CTRL</kbd>+<kbd>N</kbd> per aprire il menu Generate. Seleziona Test e comincia a scrivere il nome del tuo metodo, che di solito inizia con `test`. Premi <kbd>Invio</kbd> quando hai fatto. Per altri trucchi riguardo all'ambiente di sviluppo, vedi [Trucchi Riguardanti l'IDE](./getting-started/tips-and-tricks#code-generation).

![Generare un metodo di test](/assets/develop/misc/automatic-testing/unit_testing_01.png)

Puoi ovviamente scrivere la firma del metodo manualmente, e qualsiasi istanza del metodo senza parametri e come tipo restituito void sarà identificato come metodo di test. Dovresti alla fine avere il seguente:

![Un metodo di test vuoto con indicatori di test](/assets/develop/misc/automatic-testing/unit_testing_02.png)

Nota gli indicatori a freccia verde nel margine: puoi facilmente eseguire un test cliccandoci sopra. In alternativa, i tuoi test si eseguiranno in automatico ad ogni build, incluse le build di CI come GitHub Actions. Se stai usando GitHub Actions, non dimenticare di leggere [Configurare le GitHub Actions](#setting-up-github-actions).

Ora è il tempo di scrivere il tuo codice di test effettivo. Puoi assicurare condizioni con `org.junit.jupiter.api.Assertions`. Dai un'occhiata ai test seguenti:

@[code lang=java transcludeWith=:::automatic-testing:4](@/reference/latest/src/test/java/com/example/docs/codec/BeanTypeTest.java)

Per una spiegazione di cosa fa questo codice, consulta la pagina [Codec](./codecs#registry-dispatch).

#### Impostare le Registry {#setting-up-registries}

Ottimo, il primo test è funzionato! Ma aspetta, il secondo test è fallito? Nei log, otteniamo uno dei seguenti errori.

<<< @/public/assets/develop/automatic-testing/crash-report.log

Questo è perché stiamo provando ad accedere alla registry o a una classe che dipende su queste (o, in casi rari, dipende su altre classi Minecraft come `SharedConstants`), ma Minecraft non è stato inizializzato. Dobbiamo solo inizializzarlo un po' perché funzionino le registry. Ti basta aggiungere il codice seguente all'inizio del tuo metodo `beforeAll`.

@[code lang=java transcludeWith=:::automatic-testing:7](@/reference/latest/src/test/java/com/example/docs/codec/BeanTypeTest.java)

### Configurare le GitHub Actions {#setting-up-github-actions}

::: info

Questa sezione suppone che stia usando il workflow GitHub Action standard incluso con la mod di esempio e con la mod modello.

:::

I tuoi test ora verranno eseguiti ad ogni build, incluse quelle di fornitori CI come GitHub Actions. E se una build fallisce? Dovrai caricare i log come artifact per permetterci di vedere i report del test.

Aggiungi questo al tuo file `.github/workflows/build.yaml`, sotto alla fase `./gradlew build`.

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

## Test di Gioco {#game-tests}

Minecraft fornisce il framework di test del gioco per testare le funzionalità lato server. Fabric fornisce inoltre test di gioco client per testare le funzionalità lato client, simili a un test end-to-end.

### Impostazione dei test di gioco con Fabric Loom {#setting-up-game-tests-with-fabric-loom}

Entrambi i test di gioco server e client possono essere impostati manualmente o con Fabric Loom. Questa guida utilizzerà Loom.

Per aggiungere test di gioco alla tua mod, aggiungi quanto segue al tuo `build.gradle`:

@[code transcludeWith=:::automatic-testing:game-test:1](@/reference/latest/build.gradle)

Per vedere tutte le opzioni disponibili, vedere [la documentazione di Loom sui test](./loom/fabric-api#tests).

#### Impostazione della directory di test del gioco {#setting-up-game-test-directory}

::: info

Questa sezione è necessaria solo se hai abilitato `createSourceSet`, opzione consigliata. Naturalmente, puoi fare ciò che vuoi con Gradle, ma dovrai farlo da solo.

:::

Se hai abilitato `createSourceSet` come nell'esempio sopra, il tuo gametest sarà in un set sorgente separato con un file `fabric.mod.json` separato. Il nome predefinito del modulo è `gametest`. Crea un nuovo `fabric.mod.json` in `src/gametest/resources/` come mostrato:

<<< @/reference/latest/src/gametest/resources/fabric.mod.json

Si noti che `fabric.mod.json` si aspetta un test di gioco del server in `src/gametest/java/com/example/docs/ExampleModGameTest` e un test di gioco del client in `src/gametest/java/com/example/docs/ExampleModClientGameTest`.

### Scrittura di test di gioco {#writing-game-tests}

Ora puoi creare test di gioco per server e client nella directory `src/gametest/java`. Ecco un esempio basilare per ciascuno:

::: code-group

<<< @/reference/latest/src/gametest/java/com/example/docs/ExampleModGameTest.java [Server]

<<< @/reference/latest/src/gametest/java/com/example/docs/ExampleModClientGameTest.java [Client]

:::

Per maggiori informazioni, consultare i rispettivi Javadoc nella Fabric API.

### Eseguire i test di gioco {#running-game-tests}

I test di gioco del server saranno eseguiti automaticamente come parte dell'attività Gradle `build`. Puoi eseguire i test di gioco del client con l'attività Gradle `runClientGameTest`.

### Eseguire i test di gioco su GitHub Actions {#run-game-tests-on-github-actions}

Le workflow GitHub Action che già eseguono `build`, eseguiranno anche i test di gioco del server in automatico. Per eseguire i test di gioco del client tramite GitHub Actions, aggiungi lo snippet seguente al tuo file `build.gradle`, e il job seguente alla workflow. Lo snippet di Gradle eseguirà i test di gioco del client tramite le [attività eseguibili in production di Loom](./loom/production-run-tasks), e il job eseguirà quell'attività nel sistema CI.

::: warning

Per ora, i test di gioco potrebbero fallire su GitHub Actions a causa di un errore nella sincronizzazione della rete. Se incontrassi questo errore, basta aggiungere `-Dfabric.client.gametest.disableNetworkSynchronizer=true` agli argomenti della JVM nella dichiarazione della tua attività eseguibile in production.

:::

@[code transcludeWith=:::automatic-testing:game-test:2](@/reference/latest/build.gradle)

@[code transcludeWith=:::automatic-testing:game-test:3](@/.github/workflows/build.yaml)
