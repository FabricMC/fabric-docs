package com.example.docs.networking.basic;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

// :::use_poisonous_potato_payload
public record UsePoisonousPotatoPayload(String playerName, BlockPos pos) implements CustomPayload {
	public static final CustomPayload.Id<UsePoisonousPotatoPayload> ID = new CustomPayload.Id<>(FabricDocsReferenceNetworkingBasic.USE_POISONOUS_POTATO_PAYLOAD_ID);
	public static final PacketCodec<RegistryByteBuf, UsePoisonousPotatoPayload> CODEC = PacketCodec.tuple(
			PacketCodecs.STRING, UsePoisonousPotatoPayload::playerName,
			BlockPos.PACKET_CODEC, UsePoisonousPotatoPayload::pos,
			UsePoisonousPotatoPayload::new);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
// :::use_poisonous_potato_payload
