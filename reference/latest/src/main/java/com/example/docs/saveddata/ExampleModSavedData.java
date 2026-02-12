package com.example.docs.saveddata;

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;

public class ExampleModSavedData implements ModInitializer {
	@Override
	public void onInitialize() {
		// :::event_registration
		PlayerBlockBreakEvents.AFTER.register((level, player, pos, state, blockEntity) -> {
			MinecraftServer server = level.getServer();

			if (server == null) {
				return;
			}

			// Retrieve the saved block data from the server.
			SavedBlockData savedData = SavedBlockData.getSavedBlockData(server);

			savedData.incrementBlocksBroken(); // Increment the counter each time a block is broken.
			player.displayClientMessage(Component.literal("Blocks broken: " + savedData.getBlocksBroken()), false);
		});
		// :::event_registration
	}
}
