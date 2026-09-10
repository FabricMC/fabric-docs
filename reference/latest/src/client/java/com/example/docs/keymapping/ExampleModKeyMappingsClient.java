package com.example.docs.keymapping;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenKeyboardEvents;

import com.example.docs.ExampleMod;

public class ExampleModKeyMappingsClient implements ClientModInitializer {
	// #region category
	KeyMapping.Category CATEGORY = KeyMapping.Category.register(
			Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "custom_category")
	);
	// #endregion category

	// #region key_mapping
	KeyMapping sendToChatKey = KeyMappingHelper.registerKeyMapping(
		new KeyMapping(
				"key.example-mod.send_to_chat", // The translation key for the key mapping.
				InputConstants.Type.KEYSYM, // The type of the keybinding; KEYSYM for keyboard, MOUSE for mouse.
				InputConstants.KEY_J, // The keycode of the key.
				this.CATEGORY // The category of the mapping.
		));
	// #endregion key_mapping

	@Override
	public void onInitializeClient() {
		// #region client_tick_event
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (this.sendToChatKey.consumeClick()) {
				if (client.player == null) return;

				client.player.sendSystemMessage(Component.literal("Key press detected in the world"));
			}
		});
		// #endregion client_tick_event

		// #region screen_before_init_event
		ScreenEvents.BEFORE_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
			if (!(screen instanceof CreativeModeInventoryScreen) && !(screen instanceof TitleScreen)) {
				return;
			}

			ScreenKeyboardEvents.beforeKeyPress(screen).register((s, keyEvent) -> {
				if (!this.sendToChatKey.matches(keyEvent)) return;

				this.handleKeyPressInMainScreen(client);
				this.handleKeyPressInGameScreen(client);
			});
		});
		// #endregion screen_before_init_event
	}

	// #region helper_methods
	private void handleKeyPressInMainScreen(Minecraft client) {
		if (client.player != null) return;

		ExampleMod.LOGGER.info("Key press detected in the title screen");
	}

	private void handleKeyPressInGameScreen(Minecraft client) {
		if (client.player == null) return;

		client.player.sendSystemMessage(Component.literal("Key press detected in the GUI with a world open, closing screen"));
		client.gui.setScreen(null);
	}
	// #endregion helper_methods
}
