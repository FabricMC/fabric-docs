---
title: Playing Sounds
description: Learn how to play sound events.

search: false
---

# Playing Sounds {#playing-sounds}

Minecraft has a big selection of sounds which you can choose from. Check out the `SoundEvents` class to view all the vanilla sound event instances that Mojang has provided.

## Using Sounds in Your Mod {#using-sounds}

Make sure to execute the `playSound()` method on the logical server side when using sounds!

In this example, the `useOnEntity()` and `useOnBlock()` methods for a custom interactive item are used to play a "placing copper block" and a pillager sound.

@[code lang=java transcludeWith=:::1](@/reference/1.20.4/src/main/java/com/example/docs/item/custom/CustomSoundItem.java)

The `playSound()` method is used with the `LivingEntity` object. Only the SoundEvent, the volume and the pitch need to be specified. You can also use the `playSound()` method from the world instance to have a higher level of control.

@[code lang=java transcludeWith=:::2](@/reference/1.20.4/src/main/java/com/example/docs/item/custom/CustomSoundItem.java)

### SoundEvent and SoundCategory {#soundevent-and-soundcategory}

The SoundEvent defines which sound will be played. You can also [register your own SoundEvents](./custom) to include your own sound.

Minecraft has several audio sliders in the in-game settings. The `SoundCategory` enum is used to determine which slider will adjust your sound's volume.

### Volume and Pitch {#volume-and-pitch}

The volume parameter can be a bit misleading. In the range of `0.0f - 1.0f` the actual volume of the sound can be changed. If the number gets bigger than that, the volume of `1.0f` will be used and only the distance, in which your sound can be heard, gets adjusted. The block distance can be roughly calculated by `volume * 16`.

The pitch parameter increases or decreases the pitch value and also changes the duration of the sound. In the range of `(0.5f - 1.0f)` the pitch and the speed gets decreased, while bigger numbers will increase the pitch and the speed. Numbers below `0.5f` will stay at the pitch value of `0.5f`.
