package com.example.docs.network;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.sound.SoundCategory;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import com.example.docs.block.entity.custom.EngineBlockEntity;
import com.example.docs.networking.payload.EngineSoundInstancePacket;
import com.example.docs.sound.AbstractDynamicSoundInstance;
import com.example.docs.sound.CustomSounds;
import com.example.docs.sound.DynamicSoundManager;
import com.example.docs.sound.instance.EngineSoundInstance;

public class ReceiveS2C {
	static {
		ClientPlayNetworking.registerGlobalReceiver(EngineSoundInstancePacket.IDENTIFIER, ReceiveS2C::handleEngineSound);
	}

	// ::: 1
	private static void handleEngineSound(EngineSoundInstancePacket packet, ClientPlayNetworking.Context context) {
		ClientWorld world = context.client().world;
		if (world == null) return;
		if (!(world.getBlockEntity(packet.blockEntityPos()) instanceof EngineBlockEntity engineBlockEntity)) return;

		DynamicSoundManager soundManager = DynamicSoundManager.getInstance();

		if (packet.shouldStart()) {
			soundManager.play(new EngineSoundInstance(engineBlockEntity,
					CustomSounds.ENGINE_LOOP, SoundCategory.BLOCKS,
					60, 30, 1.2f, 0.8f, 1.4f,
					soundManager)
			);
		} else {
			soundManager.getPlayingSoundInstance(CustomSounds.ENGINE_LOOP).ifPresent(AbstractDynamicSoundInstance::end);
		}
	}
	// ::: 1

	public static void initialize() {
	}
}
