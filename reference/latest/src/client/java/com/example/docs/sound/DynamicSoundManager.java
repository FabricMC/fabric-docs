package com.example.docs.sound;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundEvent;

import com.example.docs.sound.instance.SoundInstanceCallback;

// :::1
public class DynamicSoundManager implements SoundInstanceCallback {
	// An instance of the client to use Minecraft's default SoundManager
	private static final MinecraftClient client = MinecraftClient.getInstance();
	// static field to store the current instance for the Singleton Design Pattern
	private static DynamicSoundManager instance;
	// The list which keeps track of all currently playing dynamic SoundInstances
	private final List<AbstractDynamicSoundInstance> activeSounds = new ArrayList<>();

	private DynamicSoundManager() {
		// private constructor to make sure that the normal
		// instantiation of that object is not used externally
	}

	// when accessing this class for the first time a new instance
	// is created and stored. If this is called again only the already
	// existing instance will be returned, instead of creating a new instance
	public static DynamicSoundManager getInstance() {
		if (instance == null) {
			instance = new DynamicSoundManager();
		}

		return instance;
	}

	// :::1

	// :::2
	// Plays a sound instance, if it doesn't already exist in the list
	public <T extends AbstractDynamicSoundInstance> void play(T soundInstance) {
		if (this.activeSounds.contains(soundInstance)) return;

		client.getSoundManager().play(soundInstance);
		this.activeSounds.add(soundInstance);
	}

	// Stops a sound immediately. in most cases it is preferred to use
	// the sound's ending phase, which will clean it up after completion
	public <T extends AbstractDynamicSoundInstance> void stop(T soundInstance) {
		client.getSoundManager().stop(soundInstance);
		this.activeSounds.remove(soundInstance);
	}

	// Finds a SoundInstance from a SoundEvent, if it exists and is currently playing
	public Optional<AbstractDynamicSoundInstance> getPlayingSoundInstance(SoundEvent soundEvent) {
		for (var activeSound : this.activeSounds) {
			// SoundInstances use their SoundEvent's id by default
			if (activeSound.getId().equals(soundEvent.id())) {
				return Optional.of(activeSound);
			}
		}

		return Optional.empty();
	}

	// :::2

	// :::1

	// This is where the callback signal of a finished custom SoundInstance will arrive.
	// For now, we can just stop and remove the sound from the list, but you can add
	// your own functionality too
	@Override
	public <T extends AbstractDynamicSoundInstance> void onFinished(T soundInstance) {
		this.stop(soundInstance);
	}
}
// :::1
