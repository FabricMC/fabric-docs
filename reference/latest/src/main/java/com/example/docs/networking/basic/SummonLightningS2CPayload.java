package com.example.docs.networking.basic;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import com.example.docs.ExampleMod;

// :::summon_Lightning_payload
public record SummonLightningS2CPayload(BlockPos pos) implements CustomPayload {
	public static final Identifier SUMMON_LIGHTNING_PAYLOAD_ID = Identifier.of(ExampleMod.MOD_ID, "summon_lightning");
	public static final CustomPayload.Id<SummonLightningS2CPayload> ID = new CustomPayload.Id<>(SUMMON_LIGHTNING_PAYLOAD_ID);
	public static final PacketCodec<RegistryByteBuf, SummonLightningS2CPayload> CODEC = PacketCodec.tuple(BlockPos.PACKET_CODEC, SummonLightningS2CPayload::pos, SummonLightningS2CPayload::new);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
// :::summon_Lightning_payload
