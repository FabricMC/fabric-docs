---
title: Creare Suoni Personalizzati
description: Impara a usare e aggiungere un nuovo suono con la registry.
authors:
  - JR1811
---

# Creare Suoni Personalizzati {#creating-custom-sounds}

## Preparare il File Audio {#preparing-the-audio-file}

I tuoi file audio devono essere formattati in un modo specifico. OGG Vorbis è un formato container aperto per dati multimediali, tra cui audio, e viene usato per i file audio di Minecraft. Per evitare problemi nel modo in cui Minecraft gestisce le distanze, il tuo audio deve essere solo su un singolo canale (Mono).

Molti software DAW (Digital Audio Workstation) odierni riescono a importare ed esportare usando questo formato. Nell'esempio seguente il software open-source gratuito "[Audacity](https://www.audacityteam.org/)" sarà usato per portare i file audio al formato corretto, tuttavia qualunque altro DAW sarà più che sufficiente.

![File audio non preparato in Audacity](/assets/develop/sounds/custom_sounds_0.png)

In questo esempio, il suono di un [fischio](https://freesound.org/people/strongbot/sounds/568995/) viene importato in Audacity. Attualmente questo è salvato come file `.wav` e ha due canali audio (Stereo). Modifica il suono come preferisci e assicurati di cancellare uno dei canali usando il menu a tendina in cima alla "testina".

![Separare traccia Stereo](/assets/develop/sounds/custom_sounds_1.png)

![Eliminare uno dei canali](/assets/develop/sounds/custom_sounds_2.png)

Quando devi esportare o renderizzare un file audio, assicurati di scegliere il formato del file OGG. Alcuni DAW, come REAPER, potrebbero supportare formati audio OGG a più strati. In questo caso OGG Vorbis dovrebbe funzionare senza problemi.

![Esportare come file OGG](/assets/develop/sounds/custom_sounds_3.png)

Inoltre tieni a mente che un file audio può aumentare drasticamente le dimensioni del file della tua mod. Se necessario, comprimi l'audio quando stai modificando ed esportando il file, per mantenere le sue dimensioni al minimo.

## Caricare il File Audio {#loading-the-audio-file}

Aggiungi un nuovo percorso `resources/assets/<mod id here>/sounds` per i suoni della tua mod, e trasferisci qui il file audio esportato `metal_whistle.ogg`.

Se non esiste ancora, crea il file `resources/assets/<mod id here>/sounds.json` e aggiungici i tuoi suoni.

@[code lang=json](@/reference/latest/src/main/resources/assets/fabric-docs-reference/sounds.json)

La voce subtitle fornisce un contesto più approfondito per il giocatore. Il nome del sottotitolo è usato nei file di lingua nel percorso `resources/assets/<mod id here>/lang` e verrà visualizzato se l'impostazione dei sottotitoli nel gioco è attiva e se questo suono personalizzato viene riprodotto.

## Registrare il Suono Personalizzato {#registering-the-custom-sound}

Per aggiungere il suono personalizzato alla mod, registra un SoundEvent nella classe che implementa l'entrypoint `ModInitializer`.

```java
Registry.register(Registries.SOUND_EVENT, new Identifier(MOD_ID, "metal_whistle"),
        SoundEvent.of(new Identifier(MOD_ID, "metal_whistle")));
```

## Ripulire il Disordine {#cleaning-up-the-mess}

A seconda di quante voci ci sono nella Registry, le cose potrebbero presto sfuggire di mano. Per evitare che ciò accada, possiamo fare uso di una nuova classe di supporto.

Aggiungi due nuovi metodi alla classe di supporto appena creata. Uno che registra tutti i suoni, e uno che viene usato per inizializzare questa classe in primo luogo. Dopo di che, puoi comodamente aggiungere nuovi attributi `SoundEvent` statici personalizzati a seconda delle necessità.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/sound/CustomSounds.java)

Facendo così, la classe entrypoint che implementa `ModInitializer` deve solo implementare una riga per registrare tutti i SoundEvent personalizzati.

@[code lang=java transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/sound/FabricDocsReferenceSounds.java)

## Usare il SoundEvent Personalizzato {#using-the-custom-soundevent}

Usa la classe di supporto per accedere al SoundEvent personalizzato. Consulta la pagina [Riprodurre i SoundEvents](./using-sounds) per imparare come riprodurre i suoni.
