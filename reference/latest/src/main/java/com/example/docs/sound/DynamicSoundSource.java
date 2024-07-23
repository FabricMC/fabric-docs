package com.example.docs.sound;

import net.minecraft.util.math.BlockPos;

// :::1
public interface DynamicSoundSource {
	// gets access to how many ticks have passed for that instance
	int getTicks();

	// gets access to where this instance is placed in the world
	BlockPos getPosition();

	// holds a normalized value (range of 0-1) showing, how much stress this instance is experiencing
	// Tt is more or less just an arbitrary value, which will cause the sound to change its pitch while playing.
	float getNormalizedStress();
}
// :::1

