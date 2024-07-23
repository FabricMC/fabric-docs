package com.example.docs.sound;

import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

// :::1
public abstract class AbstractDynamicSoundInstance extends MovingSoundInstance {
	private final DynamicSoundSource soundSource;

	protected AbstractDynamicSoundInstance(DynamicSoundSource soundSource, SoundEvent soundEvent, SoundCategory soundCategory) {
		super(soundEvent, soundCategory, SoundInstance.createRandom());
		this.soundSource = soundSource;


	}
	// ...
	// :::1

	@Override
	public void tick() {

	}
}
