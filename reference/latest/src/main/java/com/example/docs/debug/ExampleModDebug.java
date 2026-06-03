package com.example.docs.debug;

import net.fabricmc.api.ModInitializer;

import com.example.docs.ExampleMod;

// #region problems_basic_logger_definition
public class ExampleModDebug implements ModInitializer {
	// #endregion problems_basic_logger_definition

	@Override
	public void onInitialize() {
		// #region problems_debug_logging
		ExampleMod.LOGGER.debug("Debug logging is enabled");
		// #endregion problems_debug_logging

		// #region problems_log_levels
		ExampleMod.LOGGER.debug("Debug message for development...");
		ExampleMod.LOGGER.info("Neutral, informative text...");
		ExampleMod.LOGGER.warn("Non-critical issues..."); // [!code warning]
		ExampleMod.LOGGER.error("Critical exceptions, bugs..."); // [!code error]
		// #endregion problems_log_levels
	}

	// #region problems_basic_logger_definition
}
// #endregion problems_basic_logger_definition
