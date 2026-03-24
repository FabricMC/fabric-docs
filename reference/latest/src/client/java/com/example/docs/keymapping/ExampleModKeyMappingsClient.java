package com.example.docs.keymapping;

import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import com.example.docs.ExampleMod;

public class ExampleModKeyMappingsClient implements ClientModInitializer {
	// :::category
	KeyMapping.Category CATEGORY = new KeyMapping.Category(
			Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "custom_category")
	);
	// :::category

	// :::key_mapping
	KeyMapping sendToChatKey = KeyBindingHelper.registerKeyBinding(
		new KeyMapping(
			"key.example-mod.send_to_chat", // The translation key for the key mapping.
			InputConstants.Type.KEYSYM, // // The type of the keybinding; KEYSYM for keyboard, MOUSE for mouse.
			GLFW.GLFW_KEY_J, // The GLFW keycode of the key.
			CATEGORY // The category of the mapping.
		));
	// :::key_mapping

	@Override
	public void onInitializeClient() {
		// :::client_tick_event
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (sendToChatKey.consumeClick()) {
				if (client.player != null) {
					client.player.displayClientMessage(Component.literal("Key Pressed!"), false);
				}
			}
		});
		// :::client_tick_event
	}
}
