---
title: Loom
description: Documentazione per Loom, il plugin Gradle di Fabric.
authors:
  - Atakku
  - caoimhebyrne
  - Daomephsta
  - JamiesWhiteShirt
  - Juuxel
  - kb-1000
  - modmuss50
  - SolidBlock-cn
---

Loom di Fabric, o semplicemente Loom, è un plugin [Gradle](https://gradle.org/) per lo sviluppo di mod nell'ecosistema Fabric.

Loom fornisce utilità per l'installazione di Minecraft e mod in un ambiente di sviluppo, cosicché si possa far riferimento a essi per quanto riguarda l'offuscamento di Minecraft e nelle sue differenze tra distribuzioni e versioni. Fornisce inoltre configurazioni di avvio da usare con il Loader di Fabric, compile processing di Mixin e utilità per il sistema jar-in-jar del Loader di Fabric.

Loom supporta _tutte_ le versioni di Minecraft, anche quelle non supportate ufficialmente dall'API di Fabric, poiché non dipende dalle versioni.

Questa pagina è uno schema di tutte le opzioni e funzioni di Loom. Se stai solo ora iniziando, controlla la pagina [Per Iniziare](getting-started/setting-up-a-development-environment).

## Dipendere da Sotto-progetti {#subprojects}

Nell'impostare una build con progetti multipli, che dipenda da un altro progetto Loom, dovresti usare la configurazione `namedElements` per dipendere dall'altro progetto. Gli output di un progetto vengono automaticamente rimappati a nomi intermediari. La configurazione `namedElements` contiene gli output del progetto non ancora rimappati.

```groovy
dependencies {
 implementation project(path: ":name", configuration: "namedElements")
}
```

Se stai usando set di sorgenti divise in una build con progetti multipli, dovrai anche aggiungere una dipendenza al set di sorgenti clienti dell'altro progetto.

```groovy
dependencies {
 clientImplementation project(":name").sourceSets.client.output
}
```

## Dividere Codice Client & Comune {#split-sources}

Per anni, una fonte comune di crash di server fu che le mod chiamavano accidentalmente codice esclusivo del client mentre erano installate su un server. Versioni di Loom e Loader più recenti forniscono un'opzione perché sia necessario spostare tutto il codice client in un proprio set di sorgenti. Questo per fermare il problema già alla compilazione, ma la build risulterà comunque in un file jar singolo che funziona in entrambi i lati.

Lo snippet di un file `build.gradle` che segue mostra come attivare l'opzione per la tua mod. Poiché la tua mod sarà ora divisa tra due set di sorgenti, dovrai usare il nuovo DSL (Linguaggio Specifico del Dominio) per definire i set sorgente della tua mod. Questo permette al Loader di Fabric di raggruppare insieme il classpath della tua mod. Questo è anche utile per altre configurazioni con progetti multipli più complesse.

Sono necessari Minecraft 1.18 (si consiglia 1.19), Loader 0.14 e Loom 1.0 o seguenti per dividere il codice client e comune.

```groovy
loom {
 splitEnvironmentSourceSets()

 mods {
   modid {
     sourceSet sourceSets.main
     sourceSet sourceSets.client
   }
 }
 }
```

## Risolvere Problemi {#issues}

Loom e/o Gradle potrebbero a volte fallire a causa di file cache corrotti. Eseguire `./gradlew build --refresh-dependencies` obbligherà Gradle e Loom a riscaricare e ricreare tutti i file. Potrebbero essere necessari alcuni minuti, ma di solito basta questo per risolvere problematiche legate alla cache.

## Configurazione dell'Ambiente di Sviluppo {#setup}

Loom è progettato per funzionare appena configurato nell'ambiente di lavoro del tuo IDE. Compie un bel po' di azioni dietro le quinte per integrare l'ambiente di sviluppo con Minecraft:

- Scarica i jar client e server dai canali ufficiali per la versione configurata di Minecraft
- Unisce i jar di client e server per produrre un jar unito con annotazioni `@Environment` e `@EnvironmentInterface`
- Scarica i mapping configurati
- Rimappa i jar uniti con mapping intermediari per produrre un jar intermediario
- Rimappa i jar uniti con mapping Yarn per produrre un jar mappato
- Facoltativo: Decompila il jar mappato per produrre un jar di sorgenti mappate e una linemap, e applica la linemap al jar mappato
- Aggiunge le dipendenze Minecraft
- Scarica gli asset Minecraft
- Processa e include le dipendenze aumentate delle mod

## Cache {#caches}

- `${GRADLE_HOME}/caches/fabric-loom`: La cache dell'utente, condivisa da tutti i progetti Loom dell'utente. Usata per mettere in cache asset di Minecraft, jar, jar uniti, jar intermediari e jar mappati
- `.gradle/loom-cache`: La cache persistente alla base del progetto, una cache condivisa dal progetto e dai suoi sotto-progetti. Usata per mettere in cache mod rimappate, così come jar generati di mod incluse
- `**/build/loom-cache`: La cache di build del (sotto-)progetto

## Configurazioni di Dipendenze {#configurations}

- `minecraft`: Definisce la versione di Minecraft da usare nell'ambiente di sviluppo
- `mappings`: Definisce i mapping da usare nell'ambiente di sviluppo
- `modImplementation`, `modApi` e `modRuntime`: Varianti aumentate di `implementation`, `api` e `runtime` per le dipendenze di mod. Verranno rimappate perché corrispondano ai mapping nell'ambiente di sviluppo, e ne verranno rimossi tutti i jar annidati
- `include`: Dichiara una dipendenza che dovrebbe essere inclusa come jar-in-jar nell'output `remapJar`. Questa configurazione di dipendenze non è transitiva. Per dipendenze che non siano mod, Loom genererà un jar della mod con un `fabric.mod.json` che usi l'ID della mod ID come nome, e la stessa versione

## Configurazioni Predefinite {#configuration}

- Applica i seguenti plugin: `java`, `eclipse`
- Aggiunge le repository Maven seguenti: [Fabric](https://maven.fabricmc.net/), [Mojang](https://libraries.minecraft.net/) e Maven Central
- Configura l'attività `eclipse` perché sia conclusa dall'attività `genEclipseRuns`
- Se esiste una cartella `.idea` alla base del progetto, scarica gli asset (quando non aggiornati) e installa le configurazioni d'avvio in `.idea/runConfigurations`
- Aggiunge `net.fabricmc:fabric-mixin-compile-extensions` e le sue dipendenze con la configurazione delle dipendenze `annotationProcessor`
- Configura tutte le attività JavaCompile esclusi i test con configurazioni per il processore di annotazioni Maven
- Configura l'attività `remapJar` in modo che produca un JAR con lo stesso nome dell'output dell'attività `jar`, poi aggiunge un classifier "dev" all'attività `jar`
- Configura l'attività `remapSourcesJar` perché processi l'output dell'attività `sourcesJar` se questa esiste
- Aggiunge le attività `remapJar` e `remapSourcesJar` come dipendenze dell'attività `build`
- Configura le attività `remapJar` e `remapSourcesJar` perché aggiungano i propri outpu come artifatti `archives` all'esecuzione
- Per ogni MavenPublication (dal plugin `maven-publish`), aggiunge manualmente le dipendenze al POM per configurazioni di dipendenze di mod aumentate, sempre che la configurazione di dipendenze abbia uno scope Maven

Tutte le configurazioni d'avvio hanno come cartella d'avvio `${projectDir}/run` e l'argomento di VM `-Dfabric.development=true`. Le classi principali per le configurazioni d'avvio vengono solitamente definite da un file `fabric-installer.json` alla base del file JAR del Loader di fabric quando questo è incluso come dipendenza di mod, ma il file può essere definito da qualsiasi dipendenza di mod. Se non esiste nessun file del genere, le classi principali hanno `net.fabricmc.loader.launch.knot.KnotClient` e `net.fabricmc.loader.launch.knot.KnotServer` come valori predefiniti.
