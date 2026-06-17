package com.example.docs.datagen;

import java.util.concurrent.CompletableFuture;

import org.jspecify.annotations.NonNull;

import net.minecraft.core.HolderLookup;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;

import com.example.docs.dynamic_registries.ExampleModRegistries;
import com.example.docs.dynamic_registries.ExampleModTags;
import com.example.docs.dynamic_registries.MagicSkillsRegistryEntry;

public class ExampleModMagicSkillsTagProvider extends FabricTagsProvider<MagicSkillsRegistryEntry> {
	public ExampleModMagicSkillsTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
		super(output, ExampleModRegistries.MAGIC_SKILLS_REGISTRY_KEY, registryLookupFuture);
	}

	@Override
	protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {
		builder(ExampleModTags.ATTACKING_SKILLS_TAG_KEY)
				.add(ExampleModRegistries.BLAST_SKILL_ENTRY_ID)
				.add(ExampleModRegistries.MAGIC_MISSILE_SKILL_ENTRY_ID);
	}
}
