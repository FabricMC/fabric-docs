package com.example.docs.debug;

import net.minecraft.component.DataComponentTypes;

import net.minecraft.text.Text;

import net.minecraft.util.Rarity;

// :::problems:basic-logger-definition
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// :::problems:basic-logger-definition

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

// :::problems:basic-logger-definition
public class FabricDocsReferenceDebug implements ModInitializer {
	public static final String MOD_ID = "i-am-your-mod-id";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// ...
	// :::problems:basic-logger-definition

	@Override
	public void onInitialize() {
		Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "test_item"),
				new TestItem(new Item.Settings().maxCount(1).rarity(Rarity.EPIC)
						.component(DataComponentTypes.CUSTOM_NAME, Text.literal("[Use on Stone Block]"))
				)
		);
	}

	// :::problems:dev-logger
	// This basic logger method will only print information text,
	// if the Minecraft instance is running in a
	// Development Environment (for example in your IDE)

	public static void devLogger(String loggerInput) {
		if (!FabricLoader.getInstance().isDevelopmentEnvironment()) return;

		// customize that message however you want...
		LOGGER.info("DEV - [ %s ]".formatted(loggerInput));
	}
	// :::problems:dev-logger
	// :::problems:basic-logger-definition
}
// :::problems:basic-logger-definition