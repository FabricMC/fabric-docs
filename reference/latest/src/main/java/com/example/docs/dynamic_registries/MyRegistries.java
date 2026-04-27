package com.example.docs.dynamic_registries;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

// #region main
// #region simple
// #region synced
// #region doubleCodec
// #region withOption
public class MyRegistries {
	// #region key
	public static final ResourceKey<Registry<MyModRegistryEntry>> MY_REGISTRY_KEY =
					ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath("example-mod", "my_registry_key"));
	// #endregion key

	public static void initialize() {
		// #endregion main
		// #endregion simple
		// #endregion synced
		// #endregion doubleCodec
		// #endregion withOption

		// #region main
		// Register Code Here
		// #endregion main

		// #region simple
		DynamicRegistries.register(MY_REGISTRY_KEY, MyModRegistryEntry.CODEC);
		// #endregion simple

		// #region synced
		DynamicRegistries.registerSynced(MY_REGISTRY_KEY, MyModRegistryEntry.CODEC);
		// #endregion synced

		// #region doubleCodec
		DynamicRegistries.registerSynced(MY_REGISTRY_KEY, MyModRegistryEntry.CODEC, MyModRegistryEntry.CLIENT_CODEC);
		// #endregion doubleCodec

		// #region withOption
		DynamicRegistries.registerSynced(MY_REGISTRY_KEY, MyModRegistryEntry.CODEC, DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);

		// For 2 Codec Overload Use Below Method Instead Of The Above One
		// DynamicRegistries.registerSynced(MY_REGISTRY_KEY, MyModRegistryEntry.CODEC, MyModRegistryEntry.CLIENT_CODEC, DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);
		// #endregion withOption

		// #region main
		// #region simple
		// #region synced
		// #region doubleCodec
		// #region withOption
	}
}
// #endregion main
// #endregion simple
// #endregion synced
// #endregion doubleCodec
// #endregion withOption
