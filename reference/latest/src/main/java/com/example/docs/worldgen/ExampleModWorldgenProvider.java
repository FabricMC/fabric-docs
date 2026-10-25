package com.example.docs.worldgen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;

// #region datagen_world_provider
public class ExampleModWorldgenProvider extends FabricDynamicRegistryProvider {
	public ExampleModWorldgenProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(HolderLookup.Provider registries, Entries entries) {
		// #region worldgen_add_entries
		entries.addAll(registries.lookupOrThrow(Registries.CONFIGURED_FEATURE));
		entries.addAll(registries.lookupOrThrow(Registries.PLACED_FEATURE));
		// #endregion worldgen_add_entries
	}

	@Override
	public String getName() {
		return "World Generation";
	}
}
// #endregion datagen_world_provider
