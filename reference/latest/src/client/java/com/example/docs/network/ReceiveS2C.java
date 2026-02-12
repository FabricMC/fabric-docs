package com.example.docs.network;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.sounds.SoundSource;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import com.example.docs.block.entity.custom.EngineBlockEntity;
import com.example.docs.networking.payload.EngineSoundInstancePacket;
import com.example.docs.sound.AbstractDynamicSoundInstance;
import com.example.docs.sound.CustomSounds;
import com.example.docs.sound.DynamicSoundManager;
import com.example.docs.sound.instance.EngineSoundInstance;

public class ReceiveS2C {
	static {
		ClientPlayNetworking.registerGlobalReceiver(EngineSoundInstancePacket.IDENTIFIER, ReceiveS2C::handleS2CEngineSoundPacket);
	}

	// :::1
	private static void handleS2CEngineSoundPacket(EngineSoundInstancePacket packet, ClientPlayNetworking.Context context) {
		ClientLevel level = context.client().level;
		if (level == null) return;

		DynamicSoundManager soundManager = DynamicSoundManager.getInstance();

		if (level.getBlockEntity(packet.blockEntityPos()) instanceof EngineBlockEntity engineBlockEntity) {
			if (packet.shouldStart()) {
				soundManager.play(new EngineSoundInstance(engineBlockEntity,
						CustomSounds.ENGINE_LOOP, SoundSource.BLOCKS,
						60, 30, 1.2f, 0.8f, 1.4f,
						soundManager)
				);

				return;
			}
		}

		if (!packet.shouldStart()) {
			soundManager.getPlayingSoundInstance(CustomSounds.ENGINE_LOOP).ifPresent(AbstractDynamicSoundInstance::end);
		}
	}

	// :::1

	public static void initialize() {
	}
}
