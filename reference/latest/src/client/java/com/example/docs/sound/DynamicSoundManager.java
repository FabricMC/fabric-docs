package com.example.docs.sound;

import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.List;

public class DynamicSoundManager {

	private static DynamicSoundManager instance;
	private static final MinecraftClient client = MinecraftClient.getInstance();

	private final List<? extends AbstractDynamicSoundInstance> activeSounds = new ArrayList<>();

	private DynamicSoundManager() {
		// private constructor to make sure that the normal
		// instantiation of that object is not used externally
	}

	/**
	 * This "Singleton Design Pattern" makes sure that, at runtime,
	 * only one instance of this class can exist.
	 * <p>
	 * If this class has been used once already, it keeps its instance stored
	 * in the static instance variable and return it.<br>
	 * Otherwise, the instance variable is not initialized yet (null).
	 * It will create a new instance, use it
	 * and store it in the static variable for next uses.
	 */
	public static DynamicSoundManager getInstance() {
		if (instance == null) return new DynamicSoundManager();
		return instance;
	}

	public void play(AbstractDynamicSoundInstance sound) {
		client.getSoundManager().play(sound);
	}

	public void stop(AbstractDynamicSoundInstance sound) {

	}
}
