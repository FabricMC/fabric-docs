package com.example.docs.dynamic_registries;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

import com.example.docs.ExampleMod;

public class ExampleModTags {
	// #region tag
	public static final TagKey<MagicSkillsRegistryEntry> ATTACKING_SPELLS_TAG_KEY = TagKey.create(ExampleModRegistries.MAGIC_SKILLS_REGISTRY_KEY, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "attacking_spells"));

	public static <T> boolean isPresentInMyTag(RegistryAccess registryAccess, ResourceKey<T> entryId, TagKey<T> tagKey) {
		return registryAccess.get(entryId).map(reference -> reference.is(tagKey)).orElse(false);
	}
	// #endregion tag

	private void tagUsage(RegistryAccess registryAccess, ResourceKey<MagicSkillsRegistryEntry> entryId) {
		// #region tag_usage
		boolean isAttackingSpell = isPresentInMyTag(registryAccess, entryId, ATTACKING_SPELLS_TAG_KEY);
		// #endregion tag_usage
	}
}
