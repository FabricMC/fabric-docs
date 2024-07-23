package com.example.docs.sound.instance;

import com.example.docs.sound.AbstractDynamicSoundInstance;

import com.example.docs.sound.DynamicSoundSource;

import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.random.Random;

public class EngineSoundInstance extends AbstractDynamicSoundInstance {
	protected EngineSoundInstance(DynamicSoundSource source, SoundEvent soundEvent, SoundCategory soundCategory, Random random) {
		super(source, soundEvent, soundCategory);
	}


}
