package com.example.docs.networking.basic;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import com.example.docs.ExampleMod;

// #region summon-lightning-payload
public record ClientboundSummonLightningPayload(BlockPos pos) implements CustomPacketPayload {
	// #region identifier
	public static final Identifier SUMMON_LIGHTNING_PAYLOAD_ID = Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "summon_lightning");
	// #endregion identifier

	// #region payload-type
	public static final CustomPacketPayload.Type<ClientboundSummonLightningPayload> TYPE = new CustomPacketPayload.Type<>(SUMMON_LIGHTNING_PAYLOAD_ID);
	// #endregion payload-type

	// #region stream-codec
	public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundSummonLightningPayload> CODEC = StreamCodec.composite(BlockPos.STREAM_CODEC, ClientboundSummonLightningPayload::pos, ClientboundSummonLightningPayload::new);
	// #endregion stream-codec

	// #region type
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
	// #endregion type
}
// #endregion summon-lightning-payload
