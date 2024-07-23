package com.example.docs.sound.instance;

import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.random.Random;

import com.example.docs.sound.AbstractDynamicSoundInstance;
// :::1
public class CustomSoundInstance extends MovingSoundInstance {

	private final LivingEntity entity;

	// Here we pass over the sound source of the SoundInstance and store it in the instance.
	public CustomSoundInstance(LivingEntity entity, SoundEvent soundEvent, SoundCategory soundCategory) {
		super(soundEvent, soundCategory, SoundInstance.createRandom());

		// here we can set up values when the sound is about to start.
		this.repeat = true;
		this.entity = entity;
		setPositionToEntity();
	}

	@Override
	public void tick() {
		// stop sound instantly if sound source does not exist anymore
		if (this.entity == null || this.entity.isRemoved() || this.entity.isDead()) {
			this.setDone();
			return;
		}

		// move sound position over to the new position for every tick
		setPositionToEntity();
	}

	// small utility method to move the sound instance position
	// to the sound source's position
	private void setPositionToEntity() {
		this.x = entity.getX();
		this.y = entity.getY();
		this.z = entity.getZ();
	}
}
// :::1