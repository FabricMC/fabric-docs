package com.example.docs.networking.basic;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import com.example.docs.FabricDocsReference;

public class FabricDocsReferenceNetworkingBasic implements ModInitializer {
	public static final Identifier SUMMON_LIGHTNING_PAYLOAD_ID = Identifier.of(FabricDocsReference.MOD_ID, "summon_lightning");
	public static final Identifier USE_POISONOUS_POTATO_PAYLOAD_ID = Identifier.of(FabricDocsReference.MOD_ID, "use_poisonous_potato");

	public static final Item LIGHTNING_TATER = Registry.register(Registries.ITEM, Identifier.of(FabricDocsReference.MOD_ID, "lightning_tater"), new LightningTaterItem(new Item.Settings()));

	public void onInitialize() {
		PayloadTypeRegistry.playS2C().register(SummonLightningPayload.ID, SummonLightningPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(UsePoisonousPotatoPayload.ID, UsePoisonousPotatoPayload.CODEC);

		// :::server_global_receiver
		ServerPlayNetworking.registerGlobalReceiver(UsePoisonousPotatoPayload.ID, (payload, context) -> {
			Text message = Text.literal(String.format("%s tried to use a poisonous potato at %s", payload.playerName(), payload.pos().toShortString()));
			context.server().getPlayerManager().broadcast(message, false);
		});
		// :::server_global_receiver
	}
}
