package com.example.docs.advancement;

import java.util.HashMap;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;

// #region datagen_advancements_entrypoint
public class ExampleModDatagenAdvancement implements ModInitializer {
	@Override
	public void onInitialize() {
		// #endregion datagen_advancements_entrypoint
		// #region datagen_advancements_call_init
		ModCriteria.init();
		// #endregion datagen_advancements_call_init
		// #region datagen_advancements_entrypoint
		HashMap<Item, Integer> tools = new HashMap<>();

		PlayerBlockBreakEvents.AFTER.register(((level, player, blockPos, blockState, blockEntity) -> {
			if (player instanceof ServerPlayer serverPlayer) { // Only triggers on the server side
				Item item = player.getMainHandItem().getItem();

				Integer usedCount = tools.getOrDefault(item, 0);
				usedCount++;
				tools.put(item, usedCount);
				// #endregion datagen_advancements_entrypoint
				// #region datagen_advancements_trigger_criterion
				ModCriteria.USE_TOOL.trigger(serverPlayer);
				// #endregion datagen_advancements_trigger_criterion
				// #region datagen_advancements_trigger_new_criterion
				ModCriteria.PARAMETERIZED_USE_TOOL.trigger(serverPlayer, usedCount);
				// #endregion datagen_advancements_trigger_new_criterion
				// #region datagen_advancements_entrypoint

				serverPlayer.sendSystemMessage(Component.nullToEmpty("You've used \"" + item + "\" as a tool " + usedCount + " times!"));
			}
		}));
	}
}
// #endregion datagen_advancements_entrypoint
