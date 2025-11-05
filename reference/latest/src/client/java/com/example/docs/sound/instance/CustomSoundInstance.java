package com.example.docs.sound.instance;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;

// :::1
public class CustomSoundInstance extends MovingSoundInstance {
	private final LivingEntity entity;

	public CustomSoundInstance(LivingEntity entity, SoundEvent soundEvent, SoundSource soundCategory) {
		super(soundEvent, soundCategory, SoundInstance.createUnseededRandom());
		// In this constructor we also add the sound source (LivingEntity) of
		// the SoundInstance and store it in the current object
		this.entity = entity;
		// set up default values when the sound is about to start
		this.volume = 1.0f;
		this.pitch = 1.0f;
		this.repeat = true;
		this.setPositionToEntity();
	}

	@Override
	public void tick() {
		// stop sound instantly if sound source does not exist anymore
		if (this.entity == null || this.entity.isRemoved() || this.entity.isDeadOrDying()) {
			this.setDone();
			return;
		}

		// move sound position over to the new position for every tick
		this.setPositionToEntity();
	}

	@Override
	public boolean shouldAlwaysPlay() {
		// override to true, so that the SoundInstance can start
		// or add your own condition to the SoundInstance, if necessary
		return true;
	}

	// small utility method to move the sound instance position
	// to the sound source's position
	private void setPositionToEntity() {
		this.x = this.entity.getX();
		this.y = this.entity.getY();
		this.z = this.entity.getZ();
	}
}
// :::1
