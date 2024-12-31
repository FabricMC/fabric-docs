package com.example.docs.networking.basic;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

// :::summon_Lightning_payload
public record SummonLightningPayload(BlockPos pos) implements CustomPayload {
	public static final CustomPayload.Id<SummonLightningPayload> ID = new CustomPayload.Id<>(FabricDocsReferenceNetworkingBasic.SUMMON_LIGHTNING_PAYLOAD_ID);
	public static final PacketCodec<RegistryByteBuf, SummonLightningPayload> CODEC = PacketCodec.tuple(BlockPos.PACKET_CODEC, SummonLightningPayload::pos, SummonLightningPayload::new);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
// :::summon_Lightning_payload
