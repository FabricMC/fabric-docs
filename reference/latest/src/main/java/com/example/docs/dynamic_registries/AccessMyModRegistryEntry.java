package com.example.docs.dynamic_registries;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import java.util.Optional;

public class AccessMyModRegistryEntry {

	// #region getMyRegistry
	public Optional<Registry<MyModRegistryEntry>> getMyRegistry(RegistryAccess registryAccess) {
		return registryAccess.lookup(MyRegistries.MY_REGISTRY_KEY);
	}
	// #endregion getMyRegistry

	// #region getSpecificEntry
	public Optional<MyModRegistryEntry> getSpecificRegistryEntry(RegistryAccess registryAccess, Identifier entryId) {
		return registryAccess.get(ResourceKey.create(MyRegistries.MY_REGISTRY_KEY, entryId)).map(Holder::value);
	}
	// #endregion getSpecificEntry

	// #region iterateOverMyRegistry
	public void iterateOverMyRegistry(RegistryAccess registryAccess) {
		registryAccess.lookup(MyRegistries.MY_REGISTRY_KEY).ifPresent(registry -> registry.stream().forEach(entry -> {
			// Do something with the entry.
		}));
	}
	// #endregion iterateOverMyRegistry

}
