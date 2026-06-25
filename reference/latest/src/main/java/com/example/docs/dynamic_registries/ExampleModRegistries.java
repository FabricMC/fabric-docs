package com.example.docs.dynamic_registries;

import java.util.Optional;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;

import com.example.docs.ExampleMod;

// #region main
public class ExampleModRegistries {
	public static final ResourceKey<Registry<MagicSkillsRegistryEntry>> MAGIC_SKILLS_REGISTRY_KEY =
					ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "magic_skills_registry"));

	// #endregion main
	public static final ResourceKey<Registry<MagicSkillsRegistryEntry>> MAGIC_SKILLS_SYNCED_REGISTRY_KEY =
					ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "magic_skills_synced_registry"));
	public static final ResourceKey<Registry<MagicSkillsRegistryEntry>> MAGIC_SKILLS_DOUBLE_CODEC_REGISTRY_KEY =
					ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "magic_skills_double_codec_registry"));
	public static final ResourceKey<Registry<MagicSkillsRegistryEntry>> MAGIC_SKILLS_WITH_OPTION_REGISTRY_KEY =
					ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "magic_skills_with_option_registry"));

	public static final ResourceKey<MagicSkillsRegistryEntry> HEALING_SKILL_ENTRY_ID =
			ResourceKey.create(ExampleModRegistries.MAGIC_SKILLS_REGISTRY_KEY, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "healing_skill"));
	public static final ResourceKey<MagicSkillsRegistryEntry> BLAST_SKILL_ENTRY_ID =
			ResourceKey.create(ExampleModRegistries.MAGIC_SKILLS_REGISTRY_KEY, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "blast_skill"));
	public static final ResourceKey<MagicSkillsRegistryEntry> MAGIC_MISSILE_SKILL_ENTRY_ID =
			ResourceKey.create(ExampleModRegistries.MAGIC_SKILLS_REGISTRY_KEY, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "magic_missile_skill"));

	// #region main
	public static void initialize() {
		// Register Code Here
		// #endregion main
		// #region simple
		DynamicRegistries.register(MAGIC_SKILLS_REGISTRY_KEY, MagicSkillsRegistryEntry.CODEC);
		// #endregion simple

		// #region synced
		DynamicRegistries.registerSynced(MAGIC_SKILLS_SYNCED_REGISTRY_KEY, MagicSkillsRegistryEntry.CODEC);
		// #endregion synced

		// #region double_codec
		DynamicRegistries.registerSynced(MAGIC_SKILLS_DOUBLE_CODEC_REGISTRY_KEY, MagicSkillsRegistryEntry.CODEC, MagicSkillsRegistryEntry.CLIENT_CODEC);
		// #endregion double_codec

		// #region with_option
		DynamicRegistries.registerSynced(MAGIC_SKILLS_WITH_OPTION_REGISTRY_KEY, MagicSkillsRegistryEntry.CODEC, DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);
		// #endregion with_option
		// #region main
	}
	// #endregion main

	private void methodUsageExamples(RegistryAccess registryAccess, ServerPlayer serverPlayer) {
		// #region get_registry
		Optional<Registry<MagicSkillsRegistryEntry>> registry = registryAccess.lookup(ExampleModRegistries.MAGIC_SKILLS_REGISTRY_KEY);
		// #endregion get_registry

		// #region get_specific_registry_entry
		// #region entry_id
		ResourceKey<MagicSkillsRegistryEntry> HEALING_SKILL_ENTRY_ID = ResourceKey.create(ExampleModRegistries.MAGIC_SKILLS_REGISTRY_KEY, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "healing_skill"));
		// #endregion entry_id
		Optional<Holder.Reference<MagicSkillsRegistryEntry>> entry = registryAccess.get(HEALING_SKILL_ENTRY_ID);
		entry.ifPresent(magicSkillRef -> {
			MagicSkillsRegistryEntry magicSkill = magicSkillRef.value();
			// Other logic to reduce player's mana and running the function
			serverPlayer.sendOverlayMessage(Component.literal("Used %s Magical Skill, Mana Reduced By %d".formatted(magicSkill.name(), magicSkill.manaCost())));
		});
		// #endregion get_specific_registry_entry
	}
	// #region main
}
// #endregion main
