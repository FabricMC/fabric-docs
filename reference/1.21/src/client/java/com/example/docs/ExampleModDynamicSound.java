package com.example.docs;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

import net.fabricmc.api.ClientModInitializer;

import com.example.docs.network.ReceiveS2C;
import com.example.docs.sound.CustomSounds;
import com.example.docs.sound.instance.CustomSoundInstance;

public class ExampleModDynamicSound implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ReceiveS2C.initialize();
	}

	private void playSimpleSoundInstance() {
		// :::1
		MinecraftClient client = MinecraftClient.getInstance();
		client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
		// :::1
		// :::2
		CustomSoundInstance instance = new CustomSoundInstance(client.player, CustomSounds.ENGINE_LOOP, SoundCategory.NEUTRAL);

		// play the sound instance
		client.getSoundManager().play(instance);

		// stop the sound instance
		client.getSoundManager().stop(instance);
		// :::2
	}
}
