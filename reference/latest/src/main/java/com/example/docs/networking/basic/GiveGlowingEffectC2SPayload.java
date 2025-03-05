package com.example.docs.networking.basic;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import com.example.docs.FabricDocsReference;

// :::give_glowing_effect_payload
public record GiveGlowingEffectC2SPayload(int entityId) implements CustomPayload {
	public static final PacketCodec<RegistryByteBuf, GiveGlowingEffectC2SPayload> CODEC = PacketCodec.tuple(PacketCodecs.INTEGER, GiveGlowingEffectC2SPayload::entityId, GiveGlowingEffectC2SPayload::new);
	public static final Identifier GIVE_GLOWING_EFFECT_PAYLOAD_ID = Identifier.of(FabricDocsReference.MOD_ID, "give_glowing_effect");
	public static final CustomPayload.Id<GiveGlowingEffectC2SPayload> ID = new CustomPayload.Id<>(GIVE_GLOWING_EFFECT_PAYLOAD_ID);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
// :::give_glowing_effect_payload
