package com.example.docs.client.command;

import net.minecraft.text.Text;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

// Class to contain all mod client command registrations.
public class FabricDocsReferenceClientCommands implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// :::1
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("clienttater").executes(context -> {
				context.getSource().sendFeedback(Text.literal("Called /clienttater with no arguments."));
				return 1;
			}));
		});
		// :::1
	}
}
