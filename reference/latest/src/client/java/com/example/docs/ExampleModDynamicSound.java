package com.example.docs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

import net.fabricmc.api.ClientModInitializer;

import com.example.docs.network.ClientboundSoundReceiver;
import com.example.docs.sound.CustomSounds;
import com.example.docs.sound.instance.CustomSoundInstance;

public class ExampleModDynamicSound implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientboundSoundReceiver.initialize();
	}

	private void playSimpleSoundInstance() {
		// #region simple-sound-instance
		Minecraft client = Minecraft.getInstance();
		client.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
		// #endregion simple-sound-instance

		// #region custom-sound-instance
		CustomSoundInstance instance = new CustomSoundInstance(client.player, CustomSounds.ENGINE_LOOP, SoundSource.NEUTRAL);

		// play the sound instance
		client.getSoundManager().play(instance);

		// stop the sound instance
		client.getSoundManager().stop(instance);
		// #endregion custom-sound-instance
	}
}
