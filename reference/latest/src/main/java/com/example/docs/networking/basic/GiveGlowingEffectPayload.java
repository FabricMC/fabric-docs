package com.example.docs.networking.basic;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

// :::give_glowing_effect_payload
public record GiveGlowingEffectPayload(int entityId) implements CustomPayload {
	public static final CustomPayload.Id<GiveGlowingEffectPayload> ID = new CustomPayload.Id<>(FabricDocsReferenceNetworkingBasic.GIVE_GLOWING_EFFECT_PAYLOAD_ID);
	public static final PacketCodec<RegistryByteBuf, GiveGlowingEffectPayload> CODEC = PacketCodec.tuple(PacketCodecs.INTEGER, GiveGlowingEffectPayload::entityId, GiveGlowingEffectPayload::new);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
// :::give_glowing_effect_payload
