package com.example.docs.debug;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

// :::problems:basic-logger-definition
public class ExampleModDebug implements ModInitializer {
	public static final String MOD_ID = "i-am-your-mod-id";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// ...
	// :::problems:basic-logger-definition

	@Override
	public void onInitialize() {
		Identifier identifier = Identifier.of(MOD_ID, "test_item");
		RegistryKey<Item> testItemKey = RegistryKey.of(RegistryKeys.ITEM, identifier);
		Registry.register(Registries.ITEM, identifier,
				new TestItem(new Item.Settings().registryKey(testItemKey).maxCount(1).rarity(Rarity.EPIC)
						.component(DataComponentTypes.CUSTOM_NAME, Text.literal("[Use on Stone Block]"))));
	}

	// :::problems:dev-logger
	// This method will only log if the Minecraft instance
	// is running in a Development Environment, like your IDE
	public static void devLogger(String loggerInput) {
		if (!FabricLoader.getInstance().isDevelopmentEnvironment()) {
			return;
		}

		LOGGER.info("DEV - [ %s ]".formatted(loggerInput));
	}

	// :::problems:dev-logger
	// :::problems:basic-logger-definition
}
// :::problems:basic-logger-definition
