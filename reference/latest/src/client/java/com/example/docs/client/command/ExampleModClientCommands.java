package com.example.docs.client.command;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;

import com.example.docs.dynamic_registries.screens.ExampleModMagicSkillsScreen;

// Class to contain all mod client command registrations.
public class ExampleModClientCommands implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// #region register_command
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, commandBuildContext) -> {
			dispatcher.register(ClientCommands.literal("clienttater").executes(context -> {
				context.getSource().sendFeedback(Component.literal("Called /clienttater with no arguments."));
				return 1;
			}));
		});
		// #endregion register_command

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, commandBuildContext) -> {
			dispatcher.register(ClientCommands.literal("magic_skills_screen").executes(context -> {
				Minecraft instance = Minecraft.getInstance();
				instance.execute(() -> {
					instance.setScreen(new ExampleModMagicSkillsScreen(instance, context.getSource().registryAccess()));
				});
				return 1;
			}));
		});
	}
}
