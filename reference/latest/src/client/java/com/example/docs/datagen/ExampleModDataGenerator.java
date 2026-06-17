package com.example.docs.datagen;

import static com.example.docs.datagen.ExampleModDamageTypesProvider.TATER_DAMAGE_TYPE;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import com.example.docs.appearance.ExampleModAppearanceModelProvider;
import com.example.docs.damage.ExampleModDamageTypes;
import com.example.docs.datagen.internal.ExampleModInternalModelProvider;
import com.example.docs.datagen.internal.ExampleModResourceConditionProvider;
import com.example.docs.dynamic_registries.ExampleModRegistries;
import com.example.docs.worldgen.ExampleModWorldConfiguredFeatures;
import com.example.docs.worldgen.ExampleModWorldPlacedFeatures;
import com.example.docs.worldgen.ExampleModWorldgenProvider;

// #region datagen_setup_generator
public class ExampleModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		// #endregion datagen_setup_generator
		// #region datagen_setup_pack
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		// #endregion datagen_setup_pack

		// #region datagen_enchantments_register
		pack.addProvider(ExampleModEnchantmentGenerator::new);
		// #endregion datagen_enchantments_register

		// #region datagen_advancements_register
		pack.addProvider(ExampleModAdvancementProvider::new);
		// #endregion datagen_advancements_register

		// #region datagen_translations_register
		pack.addProvider(ExampleModEnglishLangProvider::new);
		// #endregion datagen_translations_register

		// #region datagen_tags_register
		pack.addProvider(ExampleModItemTagProvider::new);
		// #endregion datagen_tags_register
		pack.addProvider(ExampleModEnchantmentTagProvider::new);

		// #region datagen_recipes_register
		pack.addProvider(ExampleModRecipeProvider::new);
		// #endregion datagen_recipes_register

		// #region datagen_loot_tables_register
		pack.addProvider(ExampleModBlockLootTableProvider::new);
		pack.addProvider(ExampleModChestLootTableProvider::new);
		// #endregion datagen_loot_tables_register

		pack.addProvider(ExampleModDamageTypesProvider.TaterDamageTypesGenerator::new);
		pack.addProvider(ExampleModDamageTypesProvider.TaterDamageTypeTagGenerator::new);

		pack.addProvider(ExampleModInternalModelProvider::new);
		pack.addProvider(ExampleModResourceConditionProvider::new);

		// #region datagen_models_register
		pack.addProvider(ExampleModModelProvider::new);
		// #endregion datagen_models_register

		pack.addProvider(ExampleModAppearanceModelProvider::new);

		// #region add_worldgen_provider
		pack.addProvider(ExampleModWorldgenProvider::new);
		// #endregion add_worldgen_provider

		// #region datagen_dynamic_registries
		pack.addProvider(ExampleModDynamicRegistriesProvider::new);
		// #endregion datagen_dynamic_registries

		// #region magic_skills_tag_provider
		pack.addProvider(ExampleModMagicSkillsTagProvider::new);
		// #endregion magic_skills_tag_provider

		pack.addProvider(ExampleModFluidTagProvider::new);

		pack.addProvider(ExampleModAdvancementRewardLootTableProvider::new);
		pack.addProvider(ExampleModCodecExampleProvider::new);
		// #region datagen_setup_generator
	}
	// #endregion datagen_setup_generator

	// #region datagen_enchantments_bootstrap
	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		// #endregion datagen_enchantments_bootstrap
		registryBuilder.add(Registries.DAMAGE_TYPE, registerable -> {
			registerable.register(ExampleModDamageTypes.TATER_DAMAGE, TATER_DAMAGE_TYPE);
		});

		// #region datagen_world_registries
		registryBuilder.add(Registries.CONFIGURED_FEATURE, ExampleModWorldConfiguredFeatures::configure);
		registryBuilder.add(Registries.PLACED_FEATURE, ExampleModWorldPlacedFeatures::configure);
		// #endregion datagen_world_registries

		// #region datagen_magic_skills_dynamic_registries_bootstrap
		registryBuilder.add(ExampleModRegistries.MAGIC_SKILLS_REGISTRY_KEY, ExampleModDynamicRegistriesProvider::bootstrap);
		// #endregion datagen_magic_skills_dynamic_registries_bootstrap

		// #region datagen_enchantments_bootstrap
		registryBuilder.add(Registries.ENCHANTMENT, ExampleModEnchantmentGenerator::bootstrap);
	}
	// #endregion datagen_enchantments_bootstrap

	// #region datagen_setup_generator
}
// #endregion datagen_setup_generator
