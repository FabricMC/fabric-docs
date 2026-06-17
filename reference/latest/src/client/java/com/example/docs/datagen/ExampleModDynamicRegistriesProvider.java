package com.example.docs.datagen;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.jspecify.annotations.NonNull;

import net.minecraft.commands.CacheableFunction;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;

import com.example.docs.ExampleMod;
import com.example.docs.dynamic_registries.ExampleModRegistries;
import com.example.docs.dynamic_registries.MagicSkillsRegistryEntry;

public class ExampleModDynamicRegistriesProvider extends FabricDynamicRegistryProvider {
	public ExampleModDynamicRegistriesProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(HolderLookup.@NonNull Provider registries, @NonNull Entries entries) {
		entries.addAll(registries.lookupOrThrow(ExampleModRegistries.MAGIC_SKILLS_REGISTRY_KEY));
	}

	@Override
	public @NonNull String getName() {
		return "Example Mod Dynamic Registries";
	}

	public static void bootstrap(BootstrapContext<MagicSkillsRegistryEntry> context) {
		context.register(ExampleModRegistries.HEALING_SPELL_ENTRY_ID, new MagicSkillsRegistryEntry("Healing Spell", 4785, Optional.of(new CacheableFunction(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "healing_spell_function")))));
		context.register(ExampleModRegistries.BLAST_SPELL_ENTRY_ID, new MagicSkillsRegistryEntry("Blast Spell", 474, Optional.empty()));
		context.register(ExampleModRegistries.MAGIC_MISSILE_SPELL_ENTRY_ID, new MagicSkillsRegistryEntry("Magic Missile Spell", 853, Optional.empty()));
	}
}
