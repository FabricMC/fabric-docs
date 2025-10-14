package com.example.docs.sound.instance;

import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

import com.example.docs.block.entity.custom.EngineBlockEntity;
import com.example.docs.sound.AbstractDynamicSoundInstance;
import com.example.docs.sound.DynamicSoundSource;

// :::1
public class EngineSoundInstance extends AbstractDynamicSoundInstance {
	// Here we just use the default constructor parameters.
	// If you want to specifically set values here already,
	// you can clean up the constructor parameters a bit
	public EngineSoundInstance(DynamicSoundSource soundSource, SoundEvent soundEvent, SoundCategory soundCategory,
							int startTransitionTicks, int endTransitionTicks, float maxVolume, float minPitch, float maxPitch,
							SoundInstanceCallback callback) {
		super(soundSource, soundEvent, soundCategory, startTransitionTicks, endTransitionTicks, maxVolume, minPitch, maxPitch, callback);
	}

	@Override
	public void tick() {
		// check conditions which set this sound automatically into the ending phase
		if (soundSource instanceof EngineBlockEntity blockEntity && blockEntity.isRemoved()) {
			this.end();
		}

		// apply the default tick behaviour from the parent class
		super.tick();

		// modulate volume and pitch of the SoundInstance
		this.modulateSoundForTransition();
		this.modulateSoundForStress();
	}

	// you can also add sound modulation methods here,
	// which should be only accessible to this
	// specific SoundInstance
}
// :::1
