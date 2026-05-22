package com.example.docs.dynamic_registries;

import com.example.docs.ExampleMod;

import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

public class ExampleModTags {

	// #region tag
	public static final TagKey<MagicSkillsRegistryEntry> ATTACKING_SPELLS_TAG_KEY = TagKey.create(ExampleModRegistries.MAGIC_SKILLS_REGISTRY_KEY, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "attacking_spells"));

	public static <T> boolean isPresentInMyTag(RegistryAccess registryAccess, ResourceKey<Registry<T>> registryKey, TagKey<T> tagKey, Identifier entryId) {
		Registry<T> registry = ExampleModRegistries.getRegistry(registryAccess, registryKey).orElse(null);
		if (registry == null) return false;

		var reference = registry.get(entryId).orElse(null);

		return reference != null && reference.is(tagKey);
	}
	// #endregion tag

	private void tagUsage(RegistryAccess registryAccess, Identifier entryId) {
		// #region tag_usage
		boolean isAttackingSpell = isPresentInMyTag(registryAccess, ExampleModRegistries.MAGIC_SKILLS_REGISTRY_KEY, ATTACKING_SPELLS_TAG_KEY, entryId);
		// #endregion tag_usage
	}
}
