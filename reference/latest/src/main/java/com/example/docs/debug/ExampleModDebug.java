package com.example.docs.debug;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;

// #region problems--basic-logger-definition
public class ExampleModDebug implements ModInitializer {
	public static final String MOD_ID = "example-mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// ...
	// #endregion problems--basic-logger-definition

	@Override
	public void onInitialize() {
		// #region problems--debug-logging
		LOGGER.debug("Debug logging is enabled");
		// #endregion problems--debug-logging

		// #region problems--log-levels
		ExampleModDebug.LOGGER.debug("Debug message for development...");
		ExampleModDebug.LOGGER.info("Neutral, informative text...");
		ExampleModDebug.LOGGER.warn("Non-critical issues..."); // [!code warning]
		ExampleModDebug.LOGGER.error("Critical exceptions, bugs..."); // [!code error]
		// #endregion problems--log-levels
	}

	// #region problems--basic-logger-definition
}
// #endregion problems--basic-logger-definition
