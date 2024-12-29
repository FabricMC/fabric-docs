package com.example.docs.networking;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

import com.example.docs.networking.payload.EngineSoundInstancePacket;

@SuppressWarnings("SameParameterValue")
public class NetworkPayloads {
	static {
		registerS2C(EngineSoundInstancePacket.IDENTIFIER, EngineSoundInstancePacket.CODEC);
	}

	private static <T extends CustomPayload> void registerS2C(CustomPayload.Id<T> packetIdentifier, PacketCodec<RegistryByteBuf, T> codec) {
		PayloadTypeRegistry.playS2C().register(packetIdentifier, codec);
	}

	private static <T extends CustomPayload> void registerC2S(CustomPayload.Id<T> packetIdentifier, PacketCodec<RegistryByteBuf, T> codec) {
		PayloadTypeRegistry.playC2S().register(packetIdentifier, codec);
	}

	public static void initialize() {
	}
}
