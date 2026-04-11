package com.example.docs.advancement;

import java.util.HashMap;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;

// #region datagen-advancements--entrypoint
public class ExampleModDatagenAdvancement implements ModInitializer {
	@Override
	public void onInitialize() {
		// #endregion datagen-advancements--entrypoint
		// #region datagen-advancements--call-init
		ModCriteria.init();
		// #endregion datagen-advancements--call-init
		// #region datagen-advancements--entrypoint
		HashMap<Item, Integer> tools = new HashMap<>();

		PlayerBlockBreakEvents.AFTER.register(((level, player, blockPos, blockState, blockEntity) -> {
			if (player instanceof ServerPlayer serverPlayer) { // Only triggers on the server side
				Item item = player.getMainHandItem().getItem();

				Integer usedCount = tools.getOrDefault(item, 0);
				usedCount++;
				tools.put(item, usedCount);
				// #endregion datagen-advancements--entrypoint
				// #region datagen-advancements--trigger-criterion
				ModCriteria.USE_TOOL.trigger(serverPlayer);
				// #endregion datagen-advancements--trigger-criterion
				// #region datagen-advancements--trigger-new-criterion
				ModCriteria.PARAMETERIZED_USE_TOOL.trigger(serverPlayer, usedCount);
				// #endregion datagen-advancements--trigger-new-criterion
				// #region datagen-advancements--entrypoint

				serverPlayer.sendSystemMessage(Component.nullToEmpty("You've used \"" + item + "\" as a tool " + usedCount + " times!"));
			}
		}));
	}
}
// #endregion datagen-advancements--entrypoint
