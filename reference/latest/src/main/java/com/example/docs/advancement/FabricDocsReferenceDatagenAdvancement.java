package com.example.docs.advancement;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;

import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.HashMap;

// :::datagen-advancements:5
public class FabricDocsReferenceDatagenAdvancement implements ModInitializer {
	@Override
	public void onInitialize() {
		HashMap<Item, Integer> tools = new HashMap<>();

		PlayerBlockBreakEvents.AFTER.register(((world, player, blockPos, blockState, blockEntity) -> {
			if (player instanceof ServerPlayerEntity serverPlayer) { // Only triggers on the server side
				Item item = player.getMainHandStack().getItem();

				Integer usedCount = tools.getOrDefault(item, 0);
				usedCount++;
				tools.put(item, usedCount);
				// :::datagen-advancements:5
				// :::datagen-advancements:trigger-criterion
				ModCriteria.USE_TOOL.trigger(serverPlayer);
				// :::datagen-advancements:trigger-criterion
				// :::datagen-advancements:trigger-new-criterion
				ModCriteria.PARAMETERIZED_USE_TOOL.trigger(serverPlayer, usedCount);
				// :::datagen-advancements:trigger-new-criterion
				// :::datagen-advancements:5

				serverPlayer.sendMessage(Text.of("You've used \"" + item + "\" as a tool " + usedCount + " times!"));
			}
		}));
	}
}
// :::datagen-advancements:5