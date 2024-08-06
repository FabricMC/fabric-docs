---
title: Creating Custom Sounds
description: Learn how to add and use a new sound with the registry.
authors:
  - JR1811

search: false
---

# Creating Custom Sounds {#creating-custom-sounds}

## Preparing the Audio File {#preparing-the-audio-file}

Your audio files need to be formatted in a specific way. OGG Vorbis is an open container format for multimedia data, such as audio, and is used in case of Minecraft's sound files. To avoid problems with how Minecraft handles distancing, your audio needs to have only a single channel (Mono).

Many modern DAWs (Digital Audio Workstation) software can import and export using this file format. In the following example the free and open-source software "[Audacity](https://www.audacityteam.org/)" will be used to bring the audio file into the right format, however any other DAW should suffice as well.

![Unprepared audio file in Audacity](/assets/develop/sounds/custom_sounds_0.png)

In this example, a sound of a [whistle](https://freesound.org/people/strongbot/sounds/568995/) is imported into Audacity. It currently is saved as a `.wav` file and has two audio channels (Stereo). Edit the sound to your liking and make sure to delete one of the channels using the drop-down element at the top of the "track head".

![Splitting Stereo track](/assets/develop/sounds/custom_sounds_1.png)

![Deleting one of the channels](/assets/develop/sounds/custom_sounds_2.png)

When exporting or rendering the audio file, make sure to choose the OGG file format. Some DAWs, like REAPER, might support multiple OGG audio layer formats. In this case OGG Vorbis should work just fine.

![Exporting as OGG file](/assets/develop/sounds/custom_sounds_3.png)

Also keep in mind that audio files can increase the file size of your mod drastically. If needed, compress the audio when editing and exporting the file to keep the file size of your finished product to a minimum.

## Loading the Audio File {#loading-the-audio-file}

Add the new `resources/assets/<mod id here>/sounds` directory for the sounds in your mod, and put the exported audio file `metal_whistle.ogg` in there.

Continue with creating the `resources/assets/<mod id here>/sounds.json` file if it doesn't exist yet and add your sound to the sound entries.

@[code lang=json](@/reference/1.20.4/src/main/resources/assets/fabric-docs-reference/sounds.json)

The subtitle entry provides more context for the player. The subtitle name is used in the language files in the `resources/assets/<mod id here>/lang` directory and will be displayed if the in-game subtitle setting is turned on and this custom sound is being played.

## Registering the Custom Sound {#registering-the-custom-sound}

To add the custom sound to the mod, register a SoundEvent in the class which implements the `ModInitializer` entrypoint.

```java
Registry.register(Registries.SOUND_EVENT, new Identifier(MOD_ID, "metal_whistle"),
        SoundEvent.of(new Identifier(MOD_ID, "metal_whistle")));
```

## Cleaning up the Mess {#cleaning-up-the-mess}

Depending on how many Registry entries there are, this can get messy quickly. To avoid that, we can make use of a new helper class.

Add two new methods to the newly created helper class. One, which registers all the sounds and one which is used to initialize this class in the first place. After that you can comfortably add new custom `SoundEvent` static class variables as needed.

@[code lang=java transcludeWith=:::1](@/reference/1.20.4/src/main/java/com/example/docs/sound/CustomSounds.java)

This way, the `ModInitializer` implementing entrypoint class needs to only implement one line to register all custom SoundEvents.

@[code lang=java transcludeWith=:::2](@/reference/1.20.4/src/main/java/com/example/docs/sound/FabricDocsReferenceSounds.java)

## Using the Custom SoundEvent {#using-the-custom-soundevent}

Use the helper class to access the custom SoundEvent. Checkout the [Playing SoundEvents](./using-sounds) page to learn how to play sounds.
