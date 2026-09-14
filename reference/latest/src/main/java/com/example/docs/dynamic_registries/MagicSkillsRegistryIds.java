package com.example.docs.dynamic_registries;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import com.example.docs.ExampleMod;

public class MagicSkillsRegistryIds {
	public static final ResourceKey<MagicSkillsRegistryEntry> HEALING_SKILL_ENTRY_ID =
			ResourceKey.create(ExampleModRegistries.MAGIC_SKILLS_SYNCED_REGISTRY_KEY, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "healing_skill"));
	public static final ResourceKey<MagicSkillsRegistryEntry> BLAST_SKILL_ENTRY_ID =
			ResourceKey.create(ExampleModRegistries.MAGIC_SKILLS_SYNCED_REGISTRY_KEY, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "blast_skill"));
	public static final ResourceKey<MagicSkillsRegistryEntry> MAGIC_MISSILE_SKILL_ENTRY_ID =
			ResourceKey.create(ExampleModRegistries.MAGIC_SKILLS_SYNCED_REGISTRY_KEY, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "magic_missile_skill"));
}
