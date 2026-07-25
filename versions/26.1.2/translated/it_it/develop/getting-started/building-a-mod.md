---
title: Costruire una mod
description: Impara come costruire una mod per Minecraft che possa essere condivisa o testata in un ambiente di produzione.
authors:
  - cassiancc
  - cputnam-a11y
  - gdude2002
  - Scotsguy
---

Appena la tua mod sarà pronta per il testing, potrai esportarla come file JAR, che potrà essere condiviso su siti di gestione di mod, o usato per testare la tua mod in produzione assieme alle altre.

## Scegli il tuo IDE {#choose-your-ide}

<ChoiceComponent :choices="[
{
 name: 'IntelliJ IDEA',
 href: './intellij-idea/building-a-mod',
 icon: 'simple-icons:intellijidea',
 color: '#FE2857',
},
{
 name: 'Visual Studio Code',
 icon: 'codicon:vscode',
 color: '#007ACC',
},
]" />

## Costruirla nel terminale {#terminal}

::: warning

Usare il terminale per costruire la mod, invece che un IDE, potrebbe causare problemi se la tua installazione predefinita di Java non corrisponde a quella che si aspetta il progetto. Per build più affidabili, potresti usare un IDE che ti permetta di specificare la versione corretta di Java.

:::

Apri un terminale nella stessa cartella della mod, ed esegui questo comando:

::: code-group

```powershell:no-line-numbers [Windows]
./gradlew.bat build
```

```sh:no-line-numbers [macOS/Linux]
./gradlew build
```

:::

I JAR dovrebbero apparire nella cartella `build/libs` nel tuo progetto. Fuori dall'ambiente di sviluppo usa il file JAR con il nome più breve.

## Installare e condividere {#installing-and-sharing}

Dopodiché la mod potrà essere [installata come il solito](../../players/installing-mods), o caricata su siti di gestione di mod affidabili come [CurseForge](https://www.curseforge.com/minecraft) o [Modrinth](https://modrinth.com/discover/mods).
