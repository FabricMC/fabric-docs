package com.example.docs.dynamic_registries;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;

import java.util.Optional;

public class AccessMyModRegistryEntry {

		// #region getMyRegistry
		public Optional<Registry<MyModRegistryEntry>> getMyRegistry(MinecraftServer server) {
				return server.registryAccess().lookup(MyRegistries.MY_REGISTRY_KEY);
		}
		// #endregion getMyRegistry

		// #region getSpecificEntry
		public Optional<MyModRegistryEntry> getSpecificRegistryEntry(MinecraftServer server, Identifier entryId) {
				var registry = server.registryAccess().lookup(MyRegistries.MY_REGISTRY_KEY).orElse(null);
				if (registry == null) return Optional.empty();

				return registry.get(entryId).map(Holder.Reference::value);
		}
		// #endregion getSpecificEntry

		// #region iterateOverMyRegistry
		public void iterateOverMyRegistry(MinecraftServer server) {
				var registry = server.registryAccess().lookup(MyRegistries.MY_REGISTRY_KEY).orElse(null);
				if (registry == null) return;

				registry.stream().forEach(entry -> {
					// Do something with the entry.
				});
		}
		// #endregion iterateOverMyRegistry

}
