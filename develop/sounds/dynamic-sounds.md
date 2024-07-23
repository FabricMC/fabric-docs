---
title: Dynamic Sounds
description: Create more dynamic and interactive sounds
authors:
  - JR1811
---

# Create Dynamic and Interactive Sounds{#create-dynamic-and-interactive-sounds}

::: warning
This page covers an advanced topic and builds on top of the [Playing Sounds](../sounds/custom) and the [Creating Custom Sounds](../sounds/custom) pages!

At this point you should have a solid grasp on Java basics and object oriented programming.

Having knowledge on Entities, BlockEntities and custom networking will also help a lot in understanding the use case and the applications of advanced sounds.
:::

## The Issue with Normal `SoundEvents` {#the-issue-with-normal-soundevents}

As we have learned in the [Using Sounds](../sounds/using-sounds) page, it is preferable to use `SoundEvent`s on a logical server side, even if it is a bit counter intuitive. After all, a client needs to handle the sound, which is transmitted e.g. to your headphones, right?

This way of thinking is correct. Technically the client side needs to handle the audio. However, for simple `SoundEvent` playing, the server side prepared a pretty big step in between which might not be obvious at the first glance. Which clients should be able to hear that sound?

Using the sound on the logical server side, it will take the task of broadcasting `SoundEvent`s away from you. To simplify it, every client (`ClientPlayerEntity`) in tracking range, gets send a network packet to play this specific sound. The sound event is basically broadcasted from the logical server side, to every participating client, without you having to think about it at all. The sound is played once, with the specified volume and pitch values.

But what if this is not enough for you? What if the sound needs to loop, change volume and pitch dynamically while playing and all that based on values which come from things like `Entities` or `BlockEntities`?

The simple way of using the `SoundEvent` on the logical server side is not enough for this use case anymore.

## Preparing the Audio File {#preparing-the-audio-file}

We are going to create a new **looping** audio for another `SoundEvent`. If you can find an audio file which is looping seamlessly already, you can just follow the steps from [Creating Custom Sounds](../sounds/custom). If your sound is not looping perfectly yet, we will have to prepare it for that a little bit.

Again, most modern DAWs (Digital Audio Workstation Software) should be capable of this, but i like to use [Reaper](https://www.reaper.fm/) if the audio editing is a bit more involved.

### Set Up {#set-up}

Our [starting sound](https://freesound.org/people/el-bee/sounds/644881/) will be coming from an engine.

<audio controls>
    <source src="/assets/develop/sounds/dynamic-sounds/step_0.wav" type="audio/wav">
    Your browser does not support the audio element.
</audio>

Let's load the file into our DAW of choice.

![Reaper loaded with the audio file](/assets/develop/sounds/dynamic-sounds/step_0.png)

We can hear and see, that the engine gets started in the beginning and stopped at the end which is not great for looping sounds. Let's cut those out and adjust the time selection handles to match the new length. Also enable the `Toggle Repeat` mode to let the audio loop, while we adjust it.

![Trimmed audio file](/assets/develop/sounds/dynamic-sounds/step_1.png)

### Removing Disruptive Audio Elements {#removing-disruptive-audio-elements}

If we listen closely, there is a small beeping noise in the background, which could've come from the machine. I think, that this would not sound great in-game, so lets try to remove it.

It is a constant sound which keeps its frequency over the length of the audio. So a simple EQ filter should be enough to filter it out.

Reaper comes with an EQ filter equipped already, called "ReaEQ". This might be located somewhere else and named differently in other DAWs but using EQ is standard in most DAWs nowadays.

If you are sure that your DAW doesn't have an EQ filter available, check for free VST alternatives online which you may be able to install in your DAW of choice.

In Reaper use the Effects Window to add the "ReaEQ" audio effect, or any other EQ.

![Adding an EQ filter](/assets/develop/sounds/dynamic-sounds/step_2.png)

If we play the audio now, while keeping the EQ filter window open, the EQ filter will show the incoming audio in its display.
We can see many bumps there.

![Identifying the problem](/assets/develop/sounds/dynamic-sounds/step_3.png)

If you are not a trained audio engineer, this part is mostly about experimenting and "trial and error". There is a pretty harsh bump between node 2 and 3. Lets move the nodes so, that we lower the frequency only for that part.

![Lowered the bad frequency](/assets/develop/sounds/dynamic-sounds/step_4.png)

### Comparison {#comparison}

Lets compare the original file with the cleaned up version.

You can hear a distinct humming and beeping sound from maybe an electrical element of the engine, in the original sound.

<audio controls>
    <source src="/assets/develop/sounds/dynamic-sounds/step_5_first.ogg" type="audio/ogg">
    Your browser does not support the audio element.
</audio>

With an EQ filter we were able to remove it almost completely. It is definitely more pleasant to listen to.

<audio controls>
    <source src="/assets/develop/sounds/dynamic-sounds/step_5_second.ogg" type="audio/ogg">
    Your browser does not support the audio element.
</audio>

### Making It Loop {#making-it-loop}

If we let the sound play to the end and let it start from the beginning again, we can clearly hear the transition happening. The goal is to get rid of this by applying a smooth transition.

Start by cutting a piece from the end, which is as big as you want the transition to be and place it on the beginning of a new audio track.

In Reaper, simply move the cursor to the position of the cut, select the audio and press <kbd>S</kbd>.

![Cut the end and move it to a new track](/assets/develop/sounds/dynamic-sounds/step_6.png)

You may have to copy the audio effect of the first audio track over to the second one too.

Now let the end piece of the new track fade out and let the start of the first audio track fade in.

![Looping with fading audio tracks](/assets/develop/sounds/dynamic-sounds/step_7.png)

### Exporting {#exporting}

Now export the audio with the two audio tracks, but with only one audio channel (Mono) and create a new `SoundEvent` for that `.ogg` file in your mod.
If you are not sure how to do that, take a look at the [Creating Custom Sounds](../sounds/custom) page.

This is now the finished looping engine audio for the `SoundEvent`.

<audio controls>
    <source src="/assets/develop/sounds/dynamic-sounds/step_8.ogg" type="audio/ogg">
    Your browser does not support the audio element.
</audio>

## Using the `SoundEvent` on the Client Side{#using-the-sound-event-on-the-client-side}

Sounds on the client side use a `SoundInstance`. make sure to use the `SoundManager` for it only on the client side.

If you only want to play something like a click on an UI element, you can make use of the already existing `PositionedSoundInstance`.

This will only be played on the client, which executed this part of the code.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/FabricDocsDynamicSound.java)

A `SoundInstance` can be more powerful then just playing Sounds once.

Check out the `AbstractSoundInstance` class and what kind of values it can keep track of.
Besides the usual volume and pitch variables, it also holds XYZ coordinates and if it should repeat itself after finishing the `SoundEvent`.

Then taking a look at its sub class, `MovingSoundInstance` we get the `TickableSoundInstance` interface too, which keeps track of the `SoundInstance` ticks.

Simply create a new class for your custom `SoundInstance` and extend from `MovingSoundInstance`.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/sound/instance/CustomSoundInstance.java)

Using your custom `Entity` or `BlockEntity` instead of that basic `LivingEntity` instance can give you even more control e.g. in the `tick()` method based
on getter methods.

After you finished creating your custom `SoundInstance`, just call it from the client side place, where you wan't to call it from.
In the same way you can also stop the custom `SoundInstance` manually, if necessary.

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/FabricDocsDynamicSound.java)

The sound loop will be played now only for the client, which ran that SoundInstance. In this case the sound will follow the `ClientPlayerEntity` itself.

## Dynamic and Interactive Sounds{#dynamic-and-interactive-sounds}

If you plan on making several different `SoundInstances`, which behave in different ways, I would recommend to create a new `AbstractDynamicSoundInstance` class,
which implements the default behavior and let the actual custom `SoundInstance` classes extend from it.

If you just plan on using a single one, then you can skip the abstract super class, and instead implement that functionality in your custom `SoundInstance` class directly.

### Theory{#theory}

Let's think about what our goal with the `SoundInstance` is.

1. The sound should loop as long as the linked custom "EngineBlockEntity" is running
2. The sound position should move around, following its custom "EngineBlockEntity" position (more useful on Entities)
3. The engine sound should have smooth transitions. Turning it on or off should never be abrupt.

To summarize, we need to keep track of a (client sided) instance of a custom BlockEntity,
adjust volume and pitch values, while the `SoundInstance` is running based on values from that custom BlockEntity and implement "Transition States".

Technically you could stop running `SoundInstance`s with the client's `SoundManager` directly too, but this will cause the SoundInstance to go silent instantly.
Our goal is, when a stopping signal comes in, to not stop the sound instantly but to execute an ending phase of its "Transition State".

A "Transition State" is a newly created enum, which contains three values. They will be used to keep track on what phase the sound should be in.

- `STARTING` phase: sound starts silent, but slowly increases in volume
- `RUNNING` phase: sound is running normally
- `ENDING` phase: sound starts at the original volume and slowly decreases until it is silent

Only after `ENDING` phase finished, the custom `SoundInstance` is allowed to be stopped.

Putting all of those building blocks together also raises the question, where we even start and stop sounds,
and where the signals e.g. from custom S2C Network Packets get processed.

For that a new DynamicSoundManager class can be created, to easily interact with this sound system.

Overall our sound system might look like this, when we are done.

![Structure of the custom Sound System](/assets/develop/sounds/dynamic-sounds/custom-dynamic-sound-handling.jpg)

### Creating the New `SoundInstance` {#creating-the-new-soundinstance}

Start of by passing over the necessary sound source over the constructor. Your `SoundInstance` can keep track of client sided objects, so having e.g. your
custom Entity stored in there is not a bad idea.

```java
public class EngineSoundInstance extends MovingSoundInstance {
    private final EngineBlockEntity EngineBlockEntity;

    public EngineSoundInstance(EngineBlockEntity EngineBlockEntity, SoundEvent soundEvent, SoundCategory soundCategory) {
        super(soundEvent, soundCategory, SoundInstance.createRandom());
        this.EngineBlockEntity = EngineBlockEntity;
    }
    //...
}
```

If you choose to make use of a new, more modular, custom `AbstractDynamicSoundInstance` class as a super class,
you may want to use that class not only on a single type of Entity but on different ones, or even on BlockEntities too. In that case making use of abstraction is the key.

Instead of referencing a custom BlockEntity directly, only keeping track of an Interface, which provides the data, might be better in this case.
Going forward we will make use of a custom Interface called `DynamicSoundSource`. It is implemented in all classes which want to make use of that dynamic sound functionality.
It basically represents the Entity, BlockEntity or other objects which define the sound behavior.

If you don't want to do that, just use your custom BlockEntity directly instead.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/sound/DynamicSoundSource.java)

In the custom abstract `SoundInstance` parent class we will now use that Interface instead of the direct BlockEntity.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/sound/AbstractDynamicSoundInstance.java)

### 