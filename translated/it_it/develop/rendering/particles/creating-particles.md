---
title: Creare Particelle Personalizzate
description: Impara come creare particelle personalizzate usando l'API di Fabric.
authors:
  - Superkat32
---

# Creare Particelle Personalizzate {#creating-custom-particles}

Le particelle sono uno strumento potente. Possono aggiungere atmosfera a una bella scena, o aggiungere tensione durante una battaglia contro il boss. Aggiungiamone una!

## Registrare una particella personalizzata

Aggiungeremo una nuova particella "sparkle" che mimerà il movimento di una particella di una barra dell'End.

Devi prima registrare un `ParticleType` nella classe initializer della tua mod usando l'id della mod.

@[code lang=java transcludeWith=#particle_register_main](@/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java)

La stringa "sparkle_particle" in minuscolo è il percorso JSON per la texture della particella. Dovrai successivamente creare un nuovo file JSON con lo stesso nome.

## Registrazione sul Client

Dopo aver registrato la particella nell'entrypoint `ModInitializer`, dovrai anche registrare la particella nell'entrypoint `ClientModInitializer`.

@[code lang=java transcludeWith=#particle_register_client](@/reference/latest/src/client/java/com/example/docs/FabricDocsReferenceClient.java)

In questo esempio, stiamo registrando la nostra particella dal lato client. Stiamo dando un po' di movimento alla particella usando la fabbrica della particella della barra dell'End. Questo vuol dire che la nostra particella si muoverà proprio come una particella di una barra dell'End.

::: tip
You can see all the particle factories by looking at all the implementations of the `ParticleFactory` interface. This is helpful if you want to use another particle's behaviour for your own particle.

- Combinazione per IntelliJ: Ctrl+Alt+B
- Combinazione per Visual Studio Code: Ctrl+F12
  :::

## Creare un file JSON e aggiungere le texture

Dovrai creare 2 cartelle all'interno della cartella `resources/assets/<mod id here>/`.

| Percorso della Cartella | Spiegazione                                                                                     |
| ----------------------- | ----------------------------------------------------------------------------------------------- |
| `/textures/particle`    | La cartella `particle` conterrà tutte le texture per tutte le tue particelle.   |
| `/particles`            | La cartella `particles` conterrà tutti i file json per tutte le tue particelle. |

Per questo esempio, avremo una sola texture in `textures/particle` chiamata "sparkle_particle_texture.png".

Dopo, crea un nuovo file JSON in `particles` con lo stesso nome del percorso JSON che hai usato quando hai registrato il tuo ParticleType. Per questo esempio, dovremo creare `sparkle_particle.json`. Questo file è importante perché fa conoscere a Minecraft quali texture dovrebbe usare la nostra particella.

@[code lang=json](@/reference/latest/src/main/resources/assets/fabric-docs-reference/particles/sparkle_particle.json)

:::tip
Puoi aggiungere altre texture al vettore `textures` per creare un animazione per la particella. La particella scorrerà attraverso le texture nel vettore, iniziando dalla prima.
:::

## Testare la nuova particella

Una volta completato il file JSON e salvato il tuo lavoro, puoi aprire Minecraft e testare tutto!

Puoi controllare se tutto ha funzionato scrivendo il comando seguente:

```mcfunction
/particle <mod id here>:sparkle_particle ~ ~1 ~
```

![Dimostrazione della particella](/assets/develop/rendering/particles/sparkle-particle-showcase.png)

:::info
La particella comparirà all'interno del giocatore con questo comando. Probabilmente dovrai camminare all'indietro per vederla effettivamente.
:::

In alternativa, puoi anche usare un blocco comandi per far apparire la particella usando lo stesso comando.
