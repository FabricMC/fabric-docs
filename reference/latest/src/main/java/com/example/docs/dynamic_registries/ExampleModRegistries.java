package com.example.docs.dynamic_registries;

import com.example.docs.ExampleMod;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import java.util.Optional;
import java.util.function.Consumer;

// #region main
public class ExampleModRegistries {
	// #region key
	public static final ResourceKey<Registry<MagicSkillsRegistryEntry>> MAGIC_SKILLS_REGISTRY_KEY =
					ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "magic_skills_registry"));
	// #endregion key
	// #endregion main
	// #region entry_id
	ResourceKey<MagicSkillsRegistryEntry> HEALING_SPELL_ENTRY_ID = ResourceKey.create(ExampleModRegistries.MAGIC_SKILLS_REGISTRY_KEY,Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "healing_spell"));
	// #endregion entry_id
	// #region main

	// #region simple
	// #region synced
	// #region double_codec
	// #region with_option
	public static void initialize() {
		// #endregion simple
		// #endregion synced
		// #endregion double_codec
		// #endregion with_option
		// Register Code Here
		// #endregion main

		// #region simple
		DynamicRegistries.register(MAGIC_SKILLS_REGISTRY_KEY, MagicSkillsRegistryEntry.CODEC);
		// #endregion simple

		// #region synced
		DynamicRegistries.registerSynced(MAGIC_SKILLS_REGISTRY_KEY, MagicSkillsRegistryEntry.CODEC);
		// #endregion synced

		// #region double_codec
		DynamicRegistries.registerSynced(MAGIC_SKILLS_REGISTRY_KEY, MagicSkillsRegistryEntry.CODEC, MagicSkillsRegistryEntry.CLIENT_CODEC);
		// #endregion double_codec

		// #region with_option
		DynamicRegistries.registerSynced(MAGIC_SKILLS_REGISTRY_KEY, MagicSkillsRegistryEntry.CODEC, DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);
		// Or For 2 Codec Overload
		DynamicRegistries.registerSynced(MAGIC_SKILLS_REGISTRY_KEY, MagicSkillsRegistryEntry.CODEC, MagicSkillsRegistryEntry.CLIENT_CODEC, DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);
		// #endregion with_option

		// #region main
		// #region simple
		// #region synced
		// #region double_codec
		// #region with_option
	}
	// #endregion main
	// #endregion simple
	// #endregion synced
	// #endregion double_codec
	// #endregion with_option

	// #region get_registry
	public static <T> Optional<Registry<T>> getRegistry(RegistryAccess registryAccess, ResourceKey<Registry<T>> registryKey) {
		return registryAccess.lookup(registryKey);
	}
	// #endregion get_registry

	// #region get_specific_registry_entry
	public static <T> Optional<Holder.Reference<T>> getSpecificRegistryEntry(RegistryAccess registryAccess, ResourceKey<T>  entryId) {
		return registryAccess.get(entryId);
	}
	// #endregion get_specific_registry_entry

	// #region iterate_over_registry_entries
	public static <T> void iterateOverRegistryEntries(RegistryAccess registryAccess, ResourceKey<Registry<T>> registryKey, Consumer<T> consumer) {
		getRegistry(registryAccess, registryKey).ifPresent(registry -> registry.stream().forEach(consumer));
	}
	// #endregion iterate_over_registry_entries

	private void methodUsageExamples(RegistryAccess registryAccess) {
		// #region get_registry_usage
		Optional<Registry<MagicSkillsRegistryEntry>> registry = getRegistry(registryAccess, ExampleModRegistries.MAGIC_SKILLS_REGISTRY_KEY);
		// #endregion get_registry_usage

		// #region get_specific_registry_entry_usage
		Optional<Holder.Reference<MagicSkillsRegistryEntry>> entry = getSpecificRegistryEntry(registryAccess, HEALING_SPELL_ENTRY_ID);
		entry.ifPresent(magicSkillRef -> {
			System.out.println(magicSkillRef.value().name());
		});
		// #endregion get_specific_registry_entry_usage

		// #region iterate_over_registry_entries_usage
		iterateOverRegistryEntries(registryAccess, ExampleModRegistries.MAGIC_SKILLS_REGISTRY_KEY, (MagicSkillsRegistryEntry magicSkill) -> {
			System.out.println(magicSkill.name());
		});
		// #endregion iterate_over_registry_entries_usage
	}
	// #region main
}
// #endregion main
