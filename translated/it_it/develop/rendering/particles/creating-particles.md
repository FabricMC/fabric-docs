---
title: Creare Particelle Personalizzate
description: Impare come creare particelle personalizzate usando Fabric API.
authors:
  - Superkat32
---

# Creare Particelle Personalizzate

Le particelle sono uno strumento potente. Possono aggiungere atmosfera ad una bella scena, o aggiungere tensione una battaglia contro il boss al cardiopalma. Aggiungiamone una!

## Registrare una particella personalizzata

Aggiungeremo una nuova particella "sparkle" che mimerà il movimento di una particella di barra dell'end (End Rod).

Devi prima registrare un `ParticleType` nella classe mod initializer usando il tuo mod id.

@[code lang=java transcludeWith=#particle_register_main](@/reference/latest/src/main/java/com/example/docs/FabricDocsReference.java)

La stringa "sparkle_particle" in minuscolo è il percorso JSON per la texture della particella. Dovrai creare un nuovo file JSON con lo stesso nome dopo.

## Registrazione sul Client

Dopo aver registrato la particella nell'entrypoint `ModInitializer`, dovrai anche registrare la particella nell'entrypoint `ClientModInitializer`.

@[code lang=java transcludeWith=#particle_register_client](@/reference/latest/src/client/java/com/example/docs/FabricDocsReferenceClient.java)

In questo esempio, stiamo registrando la nostra particella lato client. Stiamo dando un po' di movimento alla particella usando la particle factory della barra dell'end (end rod). Questo vuol dire che la nostra particella di muoverà proprio come una particella di una end rod.

::: tip
You can see all the particle factories by looking at all the implementations of the `ParticleFactory` interface. This is helpful if you want to use another particle's behaviour for your own particle.

Hotkey per IntelliJ: Ctrl+Alt+B\
Hotkey per Visual Studio Code's: Ctrl+F12
:::

## Creare un file JSON e aggiungere le texture

Dovrai creare 2 cartelle all'interno della cartella `resources/assets/<mod id here>/`.

| Percorso della Cartella | Spiegazione                                                                     |
| ----------------------- | ------------------------------------------------------------------------------- |
| `/textures/particle`    | La cartella `particle` conterrà tutte le texture per tutte le tue particelle.   |
| `/particles`            | La cartella `particles` conterrà tutti i file json per tutte le tue particelle. |

Per questo esempio, avremo una sola texture dentro `textures/particle` chiamata "sparkle_particle_texture.png".

Dopo, crea un nuovo file JSON dentro `particles` con lo stesso nome del percorso JSON che hai usato quando hai registrato il tuo ParticoeType. Per questo esempio, dovremo creare `sparkle_particle.json`. Questo file è importante perché fa conoscere a Minecraft quali texture dovebbe usare la nostra particella.

@[code lang=json](@/reference/latest/src/main/resources/assets/fabric-docs-reference/particles/sparkle_particle.json)

:::tip
Puoi aggiungere altre texture al vettore `textures` per creare un animazione per la particella. La particella scorrerà attraverso le texture nel vettore, iniziando dalla prima.
:::

## Testare la nuova particella

Una volta completato il file JSON e salvato il tuo lavoro, puoi aprire Minecraft e testare tutto!

Puoi controllare se tutto ha funzionato scrivendo il comando seguente:

```
/particle <mod id here>:sparkle_particle ~ ~1 ~
```

![Dimostrazione della particella](/assets/develop/rendering/particles/sparkle-particle-showcase.png)

:::info
La particlella comparirà all'interno del giocatore con questo comando. Probabilmente dovrai camminare all'indietro per vederla effettivamente.
:::

Puoi anche usare un command block per far apparire la particella usando lo stesso comando.
