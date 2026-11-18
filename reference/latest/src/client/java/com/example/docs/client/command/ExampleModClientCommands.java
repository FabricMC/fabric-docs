package com.example.docs.client.command;

import com.mojang.brigadier.Command;

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
				return Command.SINGLE_SUCCESS;
			}));
		});
		// #endregion register_command

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, _) -> dispatcher.register(ClientCommands.literal("magic_skills_screen").executes(context -> {
			Minecraft instance = Minecraft.getInstance();
			instance.execute(() -> instance.gui.setScreen(new ExampleModMagicSkillsScreen(instance, context.getSource().registryAccess())));
			return Command.SINGLE_SUCCESS;
		})));
	}
}
