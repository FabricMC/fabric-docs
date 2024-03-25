---
title: Creare suoni personalizzati
description: Impara a usare e aggiungere un nuovo suono con il registro.
authors:
  - JR1811
---

# Creare suoni personalizzati

## Preparare il file audio

I tuoi file audio devono essere formattati in modo specifico. OGG Vorbis è un formato contenitore per dati multimediali, tra cui audio, e viene usato per i file audio di Minecraft. Per evitare problemi nel modo in cui Minecraft gestisce le distanze, il tuo audio deve essere solo su un singolo canale (Mono).

Molti software DAW (Digital Audio Workstation) di oggi riescono a importare ed esportare utilizzando questo formato. Nell'esempio seguente il software open-source gratuito "[Audacity](https://www.audacityteam.org/)" sarà usato per portare i file audio nel giusto formato, tuttavia qualunque altro DAW sarà più che sufficiente.

![file audio improvvisato in Audacity](/assets/develop/sounds/custom_sounds_0.png)

In questo esempio, il suono di un [fischio](https://freesound.org/people/strongbot/sounds/568995/) viene importato in Audacity. Attualmente questo rimane salvato come file `.wav` ed ha due canali audio (Stereo). Modifica il suono come preferisci e assicurati di cancellare uno dei canali utilizzando il menù a tendina in alto a "titolo traccia".

![separare traccia stereo](/assets/develop/sounds/custom_sounds_1.png)

![cancellare uno dei canali](/assets/develop/sounds/custom_sounds_2.png)

Quando devi esportare o renderizzare un file audio, assicurati di scegliere il formato del file OGG. Alcuni DAW, come REAPER, sono in grando di supportare diversi formati audio OGG a più strati. In questo caso OGG Vorbis dovrebbe funzionare senza problemi.

![espostare come file OGG](/assets/develop/sounds/custom_sounds_3.png)

Inoltre tieni a mente che un file audio può aumentare drasticamente le dimensioni del file della tua mod. Se necessario, comprimi l'audio quando stai editando ed esportando il file, per mantenere le sue dimensioni al minimo quando avrai finito il prodotto.

## Caricare un File Audio

Aggiungi un nuovo percorso `resources/assets/<mod id here>/sounds` per i suoni della tua mod, e trasferisci qui l'audio file esportato `metal_whistle.ogg`.

Se ancora non lo vedi, continua con la creazione del file `resources/assets/<mod id here>/sounds.json` e insericilo nella voce suoni.

@[code lang=json](@/reference/latest/src/main/resources/assets/fabric-docs-reference/sounds.json)

La sezione sottotitolo fornisce un contesto più approfondito per il giocatore. Il nome del sottotitolo è utilizzato nei file di lingua nel percorso `resources/assets/<mod id here>/lang` e verrà visualizzato se l'impostazione dei sottotitoli nel gioco è attiva e se questo suono personalizzato viene riprodotto.

## Registrate il suono personalizzato

Per aggiungere il suono personalizzato al mod, registra un SoundEvent nella classe che implementa l'entrypoint `ModInitializer`.

```java
Registry.register(Registries.SOUND_EVENT, new Identifier(MOD_ID, "metal_whistle"),
        SoundEvent.of(new Identifier(MOD_ID, "metal_whistle")));
```

## Ripulire il disordine

A seconda di quante voci del Registro ci sono, le cose possono diventare confuse rapidamente. Per evitare che ciò accada, possiamo fare uso di una nuova classe di supporto.

Aggiungi due nuovi metodi alla classe di supporto appena creata. Uno che registra tutti i suoni, e uno che viene utilizzato per inizializzare questa classe in primo luogo. Dopo di che, puoi comodamente aggiungere nuove variabili di classe statica SoundEvent personalizzate a seconda delle necessità.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/sound/CustomSounds.java)

Così facendo, il `ModInitializer` per essere implementato come entrypoint deve solo implementare una riga per registrare tutti i SoundEvent personalizzati.

@[code lang=java transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/sound/FabricDocsReferenceSounds.java)

## Usare il Custom SounEvent

Utilizza la classe di supporto per accedere al SoundEvent personalizzato. Consulta la pagina [Playing SoundEvents](/develop/sounds/using-sounds) per imparare come riprodurre i suoni.
