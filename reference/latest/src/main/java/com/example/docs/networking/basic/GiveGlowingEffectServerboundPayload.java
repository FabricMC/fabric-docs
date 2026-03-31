package com.example.docs.networking.basic;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import com.example.docs.ExampleMod;

// :::give_glowing_effect_payload
public record GiveGlowingEffectServerboundPayload(int entityId) implements CustomPacketPayload {
	public static final Identifier GIVE_GLOWING_EFFECT_PAYLOAD_ID = Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "give_glowing_effect");
	public static final CustomPacketPayload.Type<GiveGlowingEffectServerboundPayload> TYPE = new CustomPacketPayload.Type<>(GIVE_GLOWING_EFFECT_PAYLOAD_ID);
	public static final StreamCodec<RegistryFriendlyByteBuf, GiveGlowingEffectServerboundPayload> CODEC = StreamCodec.composite(ByteBufCodecs.INT, GiveGlowingEffectServerboundPayload::entityId, GiveGlowingEffectServerboundPayload::new);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
// :::give_glowing_effect_payload
