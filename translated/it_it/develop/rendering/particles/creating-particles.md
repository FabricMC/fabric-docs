---
title: Creare Particelle Personalizzate
description: Impare come creare particelle personalizzate usando Fabric API.
authors:
  - Superkat32
---

# Creare Particelle Personalizzate

Le particelle sono uno strumento potente. Possono aggiungere atmosfera ad una bella scena, o aggiungere tensione una battaglia contro il boss al cardiopalma. Aggiungiamone una!

## Registrare una particella personalizzata

Per aggiungere una particella personalizzata (custom), dovremo prima registrare un `ParticleType` nella classe del mod initializer usando il tuo mod id.

@[code lang=java transcludeWith=#particle_register_main](@/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java)

Il "my_particle" in minuscolo è il percorso JSON per la texture della particella. Dovrai creare un nuovo file JSON con lo stesso nome dopo.

## Registrazione sul Client

Dopo aver registrato la particella nell'entrypoint `ModInitializer`, dovrai anche registrare la particella nell'entrypoint `ClientModInitializer`.

@[code lang=java transcludeWith=#particle_register_client](@/reference/latest/src/client/java/com/example/docs/FabricDocsReferenceClient.java)

In questo esempio, stiamo registrando la nostra particella lato client. Stiamo dando un po' di movimento alla particella usando la particle factory della barra dell'end (end rod).

Puoi sostituire la factory con un'altra particle factory, o anche la tua.

## Creare un file JSON e aggiungere le texture

Dovrai creare 3 cartelle all'interno della cartella `resources`.

Iniziamo creando le cartelle necessarie per la/le texture della particella. Aggiungi le nuove cartelle `resources/assets/<mod id here>/textures/particle` alla tua cartella principale. Posiziona la texture della particella che vuoi usare nella cartella `particle`.

In questo esempio, aggiungeremo solo una texture, chiamata "myparticletexture.png".

Dopodiché crea la cartella `particles` dentro la cartella `resources/assets/<mod id here>/particles`, e crea un nuovo file json chiamato `my_particle.json` - Questo è il file che permetterà a Minecraft di sapere quale texture deve usare per la tua particella.

@[code lang=json](@/reference/latest/src/main/resources/assets/fabric-docs-reference/particles/my_particle.json)

Puoi aggiungere altre texture al vettore `textures` per creare un animazione per la particella. La particella scorrerà attraverso le texture nel vettore, iniziando dalla prima.

## Testare la nuova particella

Una volta completato il file json e salvato il tuo lavoro, puoi avviare Minecraft e testare tutto il necessario.

Puoi controllare se tutto ha funzionato scrivendo il comando seguente:

```
/particle <mod id here>:my_particle ~ ~1 ~
```

La particella dovrebbe comparire all'interno del giocatore con questo comando quindi potresti dover spostarti indietro per vederla effettivamente. Puoi anche usare un command block per far apparire la particella usando lo stesso comando.
