package com.example.docs.networking.payload;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import com.example.docs.sound.FabricDocsReferenceSounds;

public record EngineSoundInstancePacket(boolean shouldStart, BlockPos blockEntityPos) implements CustomPacketPayload {
	public static final CustomPacketPayload.Type<EngineSoundInstancePacket> IDENTIFIER =
			new CustomPacketPayload.Type<>(FabricDocsReferenceSounds.identifierOf("sound_instance"));

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return IDENTIFIER;
	}

	public static final StreamCodec<RegistryFriendlyByteBuf, EngineSoundInstancePacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.BOOL, EngineSoundInstancePacket::shouldStart,
			BlockPos.STREAM_CODEC, EngineSoundInstancePacket::blockEntityPos,
			EngineSoundInstancePacket::new
	);
}
