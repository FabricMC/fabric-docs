package com.example.docs.debug;

import net.fabricmc.api.ModInitializer;

import com.example.docs.ExampleMod;

// :::problems:basic-logger-definition
public class ExampleModDebug implements ModInitializer {
	// :::problems:basic-logger-definition

	@Override
	public void onInitialize() {
		// :::problems:debug-logging
		ExampleMod.LOGGER.debug("Debug logging is enabled");
		// :::problems:debug-logging

		// :::problems:log-levels
		ExampleMod.LOGGER.debug("Debug message for development...");
		ExampleMod.LOGGER.info("Neutral, informative text...");
		ExampleMod.LOGGER.warn("Non-critical issues..."); // [!code warning]
		ExampleMod.LOGGER.error("Critical exceptions, bugs..."); // [!code error]
		// :::problems:log-levels
	}

	// :::problems:basic-logger-definition
}
// :::problems:basic-logger-definition
