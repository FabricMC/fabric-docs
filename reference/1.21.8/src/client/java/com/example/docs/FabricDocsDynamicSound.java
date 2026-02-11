package com.example.docs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

import net.fabricmc.api.ClientModInitializer;

import com.example.docs.network.ReceiveS2C;
import com.example.docs.sound.CustomSounds;
import com.example.docs.sound.instance.CustomSoundInstance;

public class FabricDocsDynamicSound implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ReceiveS2C.initialize();
	}

	private void playSimpleSoundInstance() {
		// :::1
		Minecraft client = Minecraft.getInstance();
		client.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
		// :::1
		// :::2
		CustomSoundInstance instance = new CustomSoundInstance(client.player, CustomSounds.ENGINE_LOOP, SoundSource.NEUTRAL);

		// play the sound instance
		client.getSoundManager().play(instance);

		// stop the sound instance
		client.getSoundManager().stop(instance);
		// :::2
	}
}
