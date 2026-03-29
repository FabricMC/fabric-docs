package com.example.docs.debug;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;

// :::problems:basic-logger-definition
public class ExampleModDebug implements ModInitializer {
	public static final String MOD_ID = "example-mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// ...
	// :::problems:basic-logger-definition

	@Override
	public void onInitialize() {
		// :::problems:debug-logging
		LOGGER.debug("Debug logging is enabled");
		// :::problems:debug-logging

		// :::problems:log-levels
		ExampleModDebug.LOGGER.debug("Debug message for development...");
		ExampleModDebug.LOGGER.info("Neutral, informative text...");
		ExampleModDebug.LOGGER.warn("Non-critical issues..."); // [!code warning]
		ExampleModDebug.LOGGER.error("Critical exceptions, bugs..."); // [!code error]
		// :::problems:log-levels
	}

	// :::problems:basic-logger-definition
}
// :::problems:basic-logger-definition
