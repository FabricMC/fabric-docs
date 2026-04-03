package com.example.docs.networking.basic;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import com.example.docs.ExampleMod;

// :::summon_Lightning_payload
public record ClientboundSummonLightningPayload(BlockPos pos) implements CustomPacketPayload {
	public static final Identifier SUMMON_LIGHTNING_PAYLOAD_ID = Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "summon_lightning");
	public static final CustomPacketPayload.Type<ClientboundSummonLightningPayload> TYPE = new CustomPacketPayload.Type<>(SUMMON_LIGHTNING_PAYLOAD_ID);
	public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundSummonLightningPayload> CODEC = StreamCodec.composite(BlockPos.STREAM_CODEC, ClientboundSummonLightningPayload::pos, ClientboundSummonLightningPayload::new);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
// :::summon_Lightning_payload
