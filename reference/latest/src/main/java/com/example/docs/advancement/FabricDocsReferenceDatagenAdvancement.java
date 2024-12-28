package com.example.docs.advancement;

import java.util.HashMap;

import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;

// :::datagen-advancements:entrypoint
public class FabricDocsReferenceDatagenAdvancement implements ModInitializer {
	@Override
	public void onInitialize() {
		// :::datagen-advancements:entrypoint
		// :::datagen-advancements:call-init
		ModCriteria.init();
		// :::datagen-advancements:call-init
		// :::datagen-advancements:entrypoint
		HashMap<Item, Integer> tools = new HashMap<>();

		PlayerBlockBreakEvents.AFTER.register(((world, player, blockPos, blockState, blockEntity) -> {
			if (player instanceof ServerPlayerEntity serverPlayer) { // Only triggers on the server side
				Item item = player.getMainHandStack().getItem();

				Integer usedCount = tools.getOrDefault(item, 0);
				usedCount++;
				tools.put(item, usedCount);
				// :::datagen-advancements:entrypoint
				// :::datagen-advancements:trigger-criterion
				ModCriteria.USE_TOOL.trigger(serverPlayer);
				// :::datagen-advancements:trigger-criterion
				// :::datagen-advancements:trigger-new-criterion
				ModCriteria.PARAMETERIZED_USE_TOOL.trigger(serverPlayer, usedCount);
				// :::datagen-advancements:trigger-new-criterion
				// :::datagen-advancements:entrypoint

				serverPlayer.sendMessage(Text.of("You've used \"" + item + "\" as a tool " + usedCount + " times!"));
			}
		}));
	}
}
// :::datagen-advancements:entrypoint
