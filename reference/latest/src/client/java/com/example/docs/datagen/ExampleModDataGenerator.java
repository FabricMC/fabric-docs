package com.example.docs.datagen;

import static com.example.docs.datagen.ExampleModDamageTypesProvider.TATER_DAMAGE_TYPE;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import com.example.docs.appearance.ExampleModAppearanceModelProvider;
import com.example.docs.damage.ExampleModDamageTypes;
import com.example.docs.datagen.internal.ExampleModInternalModelProvider;
import com.example.docs.worldgen.ExampleModWorldConfiguredFeatures;
import com.example.docs.worldgen.ExampleModWorldPlacedFeatures;
import com.example.docs.worldgen.ExampleModWorldgenProvider;

// #region datagen-setup--generator
public class ExampleModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		// #endregion datagen-setup--generator
		// #region datagen-setup--pack
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		// #endregion datagen-setup--pack

		// #region datagen-enchantments--register
		pack.addProvider(ExampleModEnchantmentGenerator::new);
		// #endregion datagen-enchantments--register

		// #region datagen-advancements--register
		pack.addProvider(ExampleModAdvancementProvider::new);
		// #endregion datagen-advancements--register

		// #region datagen-translations--register
		pack.addProvider(ExampleModEnglishLangProvider::new);
		// #endregion datagen-translations--register

		// #region datagen-tags--register
		pack.addProvider(ExampleModItemTagProvider::new);
		// #endregion datagen-tags--register
		pack.addProvider(ExampleModEnchantmentTagProvider::new);

		// #region datagen-recipes--register
		pack.addProvider(ExampleModRecipeProvider::new);
		// #endregion datagen-recipes--register

		// #region datagen-loot-tables--register
		pack.addProvider(ExampleModBlockLootTableProvider::new);
		pack.addProvider(ExampleModChestLootTableProvider::new);
		// #endregion datagen-loot-tables--register

		pack.addProvider(ExampleModDamageTypesProvider.TaterDamageTypesGenerator::new);
		pack.addProvider(ExampleModDamageTypesProvider.TaterDamageTypeTagGenerator::new);

		pack.addProvider(ExampleModInternalModelProvider::new);

		// #region datagen-models--register
		pack.addProvider(ExampleModModelProvider::new);
		// #endregion datagen-models--register

		pack.addProvider(ExampleModAppearanceModelProvider::new);

		// #region register-worldgen-provider
		pack.addProvider(ExampleModWorldgenProvider::new);
		// #endregion register-worldgen-provider

		pack.addProvider(ExampleModFluidTagProvider::new);

		// #region datagen-setup--generator
	}
	// #endregion datagen-setup--generator

	// #region datagen-enchantments--bootstrap
	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		// #endregion datagen-enchantments--bootstrap
		registryBuilder.add(Registries.DAMAGE_TYPE, registerable -> {
			registerable.register(ExampleModDamageTypes.TATER_DAMAGE, TATER_DAMAGE_TYPE);
		});

		// #region datagen-world--registries
		registryBuilder.add(Registries.CONFIGURED_FEATURE, ExampleModWorldConfiguredFeatures::configure);
		registryBuilder.add(Registries.PLACED_FEATURE, ExampleModWorldPlacedFeatures::configure);
		// #endregion datagen-world--registries

		// #region datagen-enchantments--bootstrap
		registryBuilder.add(Registries.ENCHANTMENT, ExampleModEnchantmentGenerator::bootstrap);
	}
	// #endregion datagen-enchantments--bootstrap

	// #region datagen-setup--generator
}
// #endregion datagen-setup--generator
