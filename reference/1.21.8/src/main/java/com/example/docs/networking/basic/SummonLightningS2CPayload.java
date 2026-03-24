package com.example.docs.networking.basic;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import com.example.docs.FabricDocsReference;

// :::summon_Lightning_payload
public record SummonLightningS2CPayload(BlockPos pos) implements CustomPacketPayload {
	public static final ResourceLocation SUMMON_LIGHTNING_PAYLOAD_ID = ResourceLocation.fromNamespaceAndPath(FabricDocsReference.MOD_ID, "summon_lightning");
	public static final CustomPacketPayload.Type<SummonLightningS2CPayload> ID = new CustomPacketPayload.Type<>(SUMMON_LIGHTNING_PAYLOAD_ID);
	public static final StreamCodec<RegistryFriendlyByteBuf, SummonLightningS2CPayload> CODEC = StreamCodec.composite(BlockPos.STREAM_CODEC, SummonLightningS2CPayload::pos, SummonLightningS2CPayload::new);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
// :::summon_Lightning_payload
