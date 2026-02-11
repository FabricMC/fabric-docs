package com.example.docs.networking;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import com.example.docs.networking.payload.EngineSoundInstancePacket;

@SuppressWarnings("SameParameterValue")
public class NetworkPayloads {
	static {
		registerS2C(EngineSoundInstancePacket.IDENTIFIER, EngineSoundInstancePacket.CODEC);
	}

	private static <T extends CustomPacketPayload> void registerS2C(CustomPacketPayload.Type<T> packetIdentifier, StreamCodec<RegistryFriendlyByteBuf, T> codec) {
		PayloadTypeRegistry.playS2C().register(packetIdentifier, codec);
	}

	private static <T extends CustomPacketPayload> void registerC2S(CustomPacketPayload.Type<T> packetIdentifier, StreamCodec<RegistryFriendlyByteBuf, T> codec) {
		PayloadTypeRegistry.playC2S().register(packetIdentifier, codec);
	}

	public static void initialize() {
	}
}
