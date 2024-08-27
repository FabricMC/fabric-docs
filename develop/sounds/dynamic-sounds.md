---
title: Dynamic Sounds
description: Create more dynamic and interactive sounds
authors:
  - JR1811
---

# Create Dynamic and Interactive Sounds{#create-dynamic-and-interactive-sounds}

::: info
This page builds on top of the [Playing Sounds](../sounds/custom) and the [Creating Custom Sounds](../sounds/custom) pages!
:::

## Problems with `SoundEvents` {#problems-with-soundevents}

As we have learned in the [Using Sounds](../sounds/using-sounds) page, it is preferable to use `SoundEvent`s on a logical server side, even if it is a bit counter intuitive. After all, a client needs to handle the sound, which is transmitted e.g. to your headphones, right?

This way of thinking is correct. Technically the client side needs to handle the audio. However, for simple `SoundEvent` playing, the server side prepared a big step in between which might not be obvious at first glance. Which clients should be able to hear that sound?

Using the sound on the logical server side will solve the issue of broadcasting `SoundEvent`s. To put simple, every client (`ClientPlayerEntity`) in tracking range, gets send a network packet to play this specific sound. The sound event is basically broadcasted from the logical server side, to every participating client, without you having to think about it at all. The sound is played once, with the specified volume and pitch values.

But what if this is not enough for you? What if the sound needs to loop, change volume and pitch dynamically while playing and all that based on values which come from things like `Entities` or `BlockEntities`?

The simple way of using the `SoundEvent` on the logical server side is not enough for this use case.

## Preparing the Audio File {#preparing-the-audio-file}

We are going to create a new **looping** audio for another `SoundEvent`. If you can find an audio file which is looping seamlessly already, you can just follow the steps from [Creating Custom Sounds](../sounds/custom). If the sound is not looping perfectly yet, we will have to prepare it for that.

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

If we listen closely, there is a beeping noise in the background, which could've come from the machine. I think, that this wouldn't sound great in-game, so lets try to remove it.

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

Also other effects can be achieved with a simple EQ filter. For example. cutting high and/or low frequencies can give the impression of radio transmitted sounds.

You can also layer more audio files, change the pitch, add some reverb or use more elaborate sound effects like "bit-crusher". Sound design can be fun, especially if you find good sounding variations of your audio by accident. Experimenting is key and maybe your sound might end up even better than before.

We will only continue with the EQ filter, which we used to cut out the problematic frequency.

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
In Reaper you can split the audio by simply moving the cursor to the position of the cut and pressing <kbd>S</kbd>.

![Cut the end and move it to a new track](/assets/develop/sounds/dynamic-sounds/step_6.png)

You may have to copy the EQ audio effect of the first audio track over to the second one too.

Now let the end piece of the new track fade out and let the start of the first audio track fade in.

![Looping with fading audio tracks](/assets/develop/sounds/dynamic-sounds/step_7.png)

### Exporting {#exporting}

Export the audio with the two audio tracks, but with only one audio channel (Mono) and create a new `SoundEvent` for that `.ogg` file in your mod.
If you are not sure how to do that, take a look at the [Creating Custom Sounds](../sounds/custom) page.

This is now the finished looping engine audio for the `SoundEvent` called `ENGINE_LOOP`.

<audio controls>
    <source src="/assets/develop/sounds/dynamic-sounds/step_8.ogg" type="audio/ogg">
    Your browser does not support the audio element.
</audio>

## Using a `SoundInstance`{#using-a-soundinstance}

To play sounds on the client side a `SoundInstance` is needed. They still make use of `SoundEvent` though.

If you only want to play something like a click on an UI element, there is already the existing `PositionedSoundInstance` class.

Keep in mind that this will only be played on the client, which executed this part of the code.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/FabricDocsDynamicSound.java)

::: warning
Please note that in the `AbstractSoundInstance` class, which `SoundInstance`s inherit from, has the `@Environment(EnvType.CLIENT)` annotation.

This means that this class (and all its sub-classes) will only be available to the client side.

If you try using that in a logical server side context, you may not notice the issue in Singleplayer at first,
but a server in a Multiplayer environment will crash, since it won't be able to find that part of the code at all.

If you struggle with those issues, it is recommended to create your mod from the [Online template generator](https://fabricmc.net/develop/template/)
with the `Split client and common sources` option turned on.
:::

---

A `SoundInstance` can be more powerful then just playing sounds once.

Check out the `AbstractSoundInstance` class and what kind of values it can keep track of.
Besides the usual volume and pitch variables, it also holds XYZ coordinates and if it should repeat itself after finishing the `SoundEvent`.

Then taking a look at its sub class, `MovingSoundInstance` we get the `TickableSoundInstance` interface introduced too, which adds ticking functionality to the `SoundInstance`.

So to make use of those utilities, simply create a new class for your custom `SoundInstance` and extend from `MovingSoundInstance`.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/sound/instance/CustomSoundInstance.java)

Using your custom `Entity` or `BlockEntity` instead of that basic `LivingEntity` instance can give you even more control e.g. in the `tick()` method based
on accessor methods but you don't necessarily need a reference to a sound source like that. Instead you could also access a `BlockPos` from somewhere else
or even set it by hand once in the constructor only.

Just keep in mind that all of the referenced objects in the `SoundInstance` are the versions from the client side.
In specific situations a logical server side entity's properties can differ from its client side counter part.
If you notice that your values don't line up, make sure that your values are synchronized
either with entity's `TrackedData`, `BlockEntity` S2C packets or complete custom S2C network packets.

After you finished creating your custom `SoundInstance` it is ready to be used anywhere as long as it's been executed on the client side using the sound manager.
In the same way you can also stop the custom `SoundInstance` manually, if necessary.

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/FabricDocsDynamicSound.java)

The sound loop will be played now only for the client, which ran that SoundInstance. In this case the sound will follow the `ClientPlayerEntity` itself.

This concludes the explanation of creating and using a simple custom `SoundInstance`.

## Advanced SoundInstances{#advanced-soundinstances}

::: warning
The following content covers an advanced topic.

At this point you should have a solid grasp on Java, object oriented programming, generics and callback systems.

Having knowledge on `Entities`, `BlockEntities` and custom networking will also help a lot in understanding the use case and the applications of advanced sounds.
:::

To show an example of how more elaborate `SoundInstance` systems can be created, we will add extra functionality, abstractions
and utilities to make working with such sounds in a bigger scope, easier and more dynamic and flexible.

### Theory{#theory}

Let's think about what our goal with the `SoundInstance` is.

- The sound should loop as long as the linked custom `EngineBlockEntity` is running
- The `SoundInstance` should move around, following its custom `EngineBlockEntity`'s position _(The `BlockEntity` won't move so this might be more useful on `Entities`)_
- We need smooth transitions. Turning it on or off should never be abrupt.
- Change volume and pitch based on external factors (e.g. from the source of the sound)

To summarize, we need to keep track of an instance of a custom `BlockEntity`,
adjust volume and pitch values while the `SoundInstance` is running based on values from that custom `BlockEntity` and implement "Transition States".

If you plan on making several different `SoundInstances`, which behave in different ways, I would recommend creating a new abstract `AbstractDynamicSoundInstance` class,
which implements the default behavior and let the actual custom `SoundInstance` classes extend from it.

If you just plan on using a single one, you can skip the abstract super class, and instead implement that functionality in your custom `SoundInstance` class directly.

In addition it will be a good idea to have a centralized place, where the `SoundInstance`'s are being tracked, played and stopped. This means that it needs to handle incoming
signals, e.g. from custom S2C Network Packets, list all currently running instances and handle special cases, for example which sounds are allowed to play at the same time and which sounds
could potentially disable other sounds upon activation.
For that a new `DynamicSoundManager` class can be created, to easily interact with this sound system.

Overall our sound system might look like this, when we are done.

![Structure of the custom Sound System](/assets/develop/sounds/dynamic-sounds/custom-dynamic-sound-handling.jpg)

::: info
All of those enums, interfaces and classes will be newly created. Adjust the system and utilities to your specific use case as you see fit. 
This is only an example of how you can approach such topics.
:::

### `DynamicSoundSource` Interface {#dynamicsoundsource-interface}

If you choose to create a new, more modular, custom `AbstractDynamicSoundInstance` class as a super class,
you may want to reference not only a single type of Entity but different ones, or even BlockEntities too.

In that case, making use of abstraction is the key.
Instead of referencing e.g. a custom `BlockEntity` directly, only keeping track of an Interface, which provides the data, solves that problem.

Going forward we will make use of a custom Interface called `DynamicSoundSource`. It is implemented in all classes which want to make use of that dynamic sound functionality,
like your custom `BlockEntity`, Entities or even, using Mixins, on already existing classes, like `ZombieEntity`.

It basically represents only the necessary data of the sound source.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/sound/DynamicSoundSource.java)

After creating this interface, make sure to implement it in the necessary classes too.

::: info
This is a utility, which may be used on both the client and the logical server side.

So this Interface should be stored in the common packages instead of the client only packages, if you make use of the
"split sources" option
:::

### `TransitionState` Enum {#transitionstate-enum}

As mentioned earlier, you could stop running `SoundInstance`s with the client's `SoundManager` directly, but this will cause the SoundInstance to go silent instantly.
Our goal is, when a stopping signal comes in, to not stop the sound but to execute an ending phase of its "Transition State". Only after the ending phase is finished
the custom `SoundInstance` should be stopped.

A `TransitionState` is a newly created enum, which contains three values. They will be used to keep track on what phase the sound should be in.

- `STARTING` Phase: sound starts silent, but slowly increases in volume
- `RUNNING` Phase: sound is running normally
- `ENDING` Phase: sound starts at the original volume and slowly decreases until it is silent

Technically a simple enum with the phases can be enough.

```java
public enum TransitionState {
	STARTING, RUNNING, ENDING
}
```

But when those values are sent over the network you might want to define an `Identifier` for them or even add other custom values.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/sound/TransitionState.java)

::: info
Again, if you make use of "split sources" you need to think about where you will be using this enum.
Technically, only the custom `SoundInstance`s which are only available on the client side, will use those enum values.

But if this enum is used anywhere else, e.g. in custom network packets, you may have to put this enum also into the common packages
instead of the client only packages.
:::

### `SoundInstanceCallback` Interface {#soundinstancecallback-interface}

This interface is used as a callback. For now we only need a `onFinished` method but you can add your own methods if you need to send
other signals too.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/sound/instance/SoundInstanceCallback.java)

Implement this interface on any class, which should be able to handle the incoming signals, for example the [newly created `AbstractDynamicSoundInstance`](#abstractdynamicsoundinstance-class),
and actually create the functionality in the custom `SoundInstance` itself.

### `AbstractDynamicSoundInstance` Class {#abstractdynamicsoundinstance-class}

Let's finally get started on the core of the dynamic sound instances system. The `AbstractDynamicSoundInstance` is a newly created `abstract` class.
It implements the default defining features and utilities of our custom `SoundInstances`, which will inherit from it.

We can take the `CustomSoundInstance` from [earlier](#using-a-soundinstance) and improve on that.
Instead of the `LivingEntity` we will now reference our `DynamicSoundSource`.
In addition we will define more properties.

- `TransitionState` to keep track of the currently phase
- tick durations of how long the start and the end phases should last
- minimum and maximum values for volume and pitch
- boolean value to notify if this instance has been finished and can be cleaned up
- tick holders to keep track of the current sound's progress.
- a callback which sends a signal back to the `DynamicSoundManager` for the final clean up, when the `SoundInstance` is actually finished

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/sound/AbstractDynamicSoundInstance.java)

Then set up the default starting values for the custom `SoundInstance` in the constructor of the abstract class.

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/sound/AbstractDynamicSoundInstance.java)

After the constructor is finished you need to allow the `SoundInstance` to be able to play.

@[code lang=java transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/sound/AbstractDynamicSoundInstance.java)

Now comes the important part for this dynamic `SoundInstance`. Based on the current tick of the instance it can apply different values and behaviors.

@[code lang=java transcludeWith=:::4](@/reference/latest/src/client/java/com/example/docs/sound/AbstractDynamicSoundInstance.java)

As you can see, we haven't applied the volume and pitch modulation here yet. We only apply the shared behavior.
So in this `AbstractDynamicSoundInstance` class we only provide the basic structure and the tools for the
sub classes which can decide themselves, which kind of sound modulation they actually wan't to apply.

So let's take a look at some examples of such sound modulation methods.

@[code lang=java transcludeWith=:::5](@/reference/latest/src/client/java/com/example/docs/sound/AbstractDynamicSoundInstance.java)

As you can see, normalized values in combination with (linear) interpolation help out to shape the values to the preferred audio limits.
Keep in mind that if you add multiple methods, which change the same value, you will need to observe and adjust how they work together with each other.

Now we just need to add the remaining utility methods and we are done with the `AbstractDynamicSoundInstance` class.

@[code lang=java transcludeWith=:::6](@/reference/latest/src/client/java/com/example/docs/sound/AbstractDynamicSoundInstance.java)

### Example `SoundInstance` Implementation {#example-soundinstance-implementation}

If we take a look at the actual custom `SoundInstance` class, which extends from the newly created `AbstractDynamicSoundInstance`, we only need to think about
what conditions would bring the sound to a stop and what sound modulation we want to apply.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/sound/instance/EngineSoundInstance.java)

### `DynamicSoundManager` Class {#dynamicsoundmanager-class}

We discussed [earlier](#using-a-soundinstance) how to play and stop a `SoundInstance`. To clean up, centralize and manage those interactions you can create your own
`SoundInstance` handler which builds on top of that.

This new `DynamicSoundManager` class will manage the custom `SoundInstances` so it will also only be available to the client side (single thread). On top of that a client should only ever allow one instance of this class to exist. Multiple sound managers for a single client wouldn't make much sense and complicate the interactions even more.
So lets use a "Singleton Design Pattern".

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/sound/DynamicSoundManager.java)

After getting the basic structure right, you can add the methods, which are needed to interact with the sound system.

- playing sounds
- stopping sounds
- checking if a sound is currently playing

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/sound/DynamicSoundManager.java)

Instead of only having a list of all currently playing `SoundInstances` you could also keep track of which sound sources are playing which sounds.
For example an engine having two engine sounds at the same time would make no sense, while multiple engines playing their respective engine sounds
is a valid edge case. For the sake of simplicity we just created a `List<AbstractDynamicSoundInstance>` but in many cases a `HashMap` of `DynamicSoundSource` and a `AbstractDynamicSoundInstance` might be a better choice.

### Using the Advanced Sound System {#using-the-advanced-sound-system}

To use this sound system simply make use of either the `DynamicSoundManager` methods or the `SoundInstance` methods. Using `onStartedTrackingBy` and `onStoppedTrackingBy`
from entities or just custom S2C networking you can now start and stop your custom dynamic `SoundInstance`s.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/network/ReceiveS2C.java)

The final product can adjust its volume based on the sound phase to smoothen out the transitions and change the pitch based on a stress value, which is
coming straight from the sound source.

<VideoPlayer src="/assets/develop/sounds/dynamic-sounds/engine-block-sound.webm" title="Engine BlockEntity with dynamic sound changes" />

You could add another value to your sound source, which keeps track of an "overheat" value and, in addition, let a hissing `SoundInstance` slowly fade in if the value is above 0
or add a new interface to your custom dynamic `SoundInstance`s which assigns a priority value to the sound types, which helps out choosing which sound to play, if they
collide with each other.

With the current system you can easily handle multiple `SoundInstance`s at once and design the audio to your needs.
