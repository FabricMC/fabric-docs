package com.example.docs.sound;

import net.minecraft.util.math.Vec3d;

// :::1
public interface DynamicSoundSource {
	// gets access to how many ticks have passed for e.g. a BlockEntity instance
	int getTick();

	// gets access to where currently this instance is placed in the world
	Vec3d getPosition();

	// holds a normalized (range of 0-1) value, showing how much stress this instance is currently experiencing
	// It is more or less just an arbitrary value, which will cause the sound to change its pitch while playing.
	float getNormalizedStress();
}
// :::1
