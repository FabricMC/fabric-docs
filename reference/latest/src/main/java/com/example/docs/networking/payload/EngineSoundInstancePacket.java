package com.example.docs.networking.payload;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

import com.example.docs.sound.FabricDocsReferenceSounds;

public record EngineSoundInstancePacket(boolean shouldStart, BlockPos blockEntityPos) implements CustomPayload {
	public static final CustomPayload.Id<EngineSoundInstancePacket> IDENTIFIER =
			new CustomPayload.Id<>(FabricDocsReferenceSounds.identifierOf("sound_instance"));

	@Override
	public Id<? extends CustomPayload> getId() {
		return IDENTIFIER;
	}

	public static final PacketCodec<RegistryByteBuf, EngineSoundInstancePacket> CODEC = PacketCodec.tuple(
			PacketCodecs.BOOL, EngineSoundInstancePacket::shouldStart,
			BlockPos.PACKET_CODEC, EngineSoundInstancePacket::blockEntityPos,
			EngineSoundInstancePacket::new
	);
}
