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
import com.example.docs.worldgen.ExampleModWorldConfiguredFeatures;
import com.example.docs.worldgen.ExampleModWorldPlacedFeatures;
import com.example.docs.worldgen.ExampleModWorldgenProvider;

// #region datagen_setup__generator
public class ExampleModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		// #endregion datagen_setup__generator
		// #region datagen_setup__pack
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		// #endregion datagen_setup__pack

		// #region datagen_enchantments__register
		pack.addProvider(ExampleModEnchantmentGenerator::new);
		// #endregion datagen_enchantments__register

		// #region datagen_advancements__register
		pack.addProvider(ExampleModAdvancementProvider::new);
		// #endregion datagen_advancements__register

		// #region datagen_translations__register
		pack.addProvider(ExampleModEnglishLangProvider::new);
		// #endregion datagen_translations__register

		// #region datagen_tags__register
		pack.addProvider(ExampleModItemTagProvider::new);
		// #endregion datagen_tags__register
		pack.addProvider(ExampleModEnchantmentTagProvider::new);

		// #region datagen_recipes__register
		pack.addProvider(ExampleModRecipeProvider::new);
		// #endregion datagen_recipes__register

		// #region datagen_loot_tables__register
		pack.addProvider(ExampleModBlockLootTableProvider::new);
		pack.addProvider(ExampleModChestLootTableProvider::new);
		// #endregion datagen_loot_tables__register

		pack.addProvider(ExampleModDamageTypesProvider.TaterDamageTypesGenerator::new);
		pack.addProvider(ExampleModDamageTypesProvider.TaterDamageTypeTagGenerator::new);

		pack.addProvider(ExampleModInternalModelProvider::new);
		pack.addProvider(ExampleModResourceConditionProvider::new);

		// #region datagen_models__register
		pack.addProvider(ExampleModModelProvider::new);
		// #endregion datagen_models__register

		pack.addProvider(ExampleModAppearanceModelProvider::new);

		// #region add_worldgen_provider
		pack.addProvider(ExampleModWorldgenProvider::new);
		// #endregion add_worldgen_provider

		pack.addProvider(ExampleModFluidTagProvider::new);

		pack.addProvider(ExampleModAdvancementRewardLootTableProvider::new);
		pack.addProvider(ExampleModCodecExampleProvider::new);
		// #region datagen_setup__generator
	}
	// #endregion datagen_setup__generator

	// #region datagen_enchantments__bootstrap
	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		// #endregion datagen_enchantments__bootstrap
		registryBuilder.add(Registries.DAMAGE_TYPE, registerable -> {
			registerable.register(ExampleModDamageTypes.TATER_DAMAGE, TATER_DAMAGE_TYPE);
		});

		// #region datagen_world__registries
		registryBuilder.add(Registries.CONFIGURED_FEATURE, ExampleModWorldConfiguredFeatures::configure);
		registryBuilder.add(Registries.PLACED_FEATURE, ExampleModWorldPlacedFeatures::configure);
		// #endregion datagen_world__registries

		// #region datagen_enchantments__bootstrap
		registryBuilder.add(Registries.ENCHANTMENT, ExampleModEnchantmentGenerator::bootstrap);
	}
	// #endregion datagen_enchantments__bootstrap

	// #region datagen_setup__generator
}
// #endregion datagen_setup__generator
