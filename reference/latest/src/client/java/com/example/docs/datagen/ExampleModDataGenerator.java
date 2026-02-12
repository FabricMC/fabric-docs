package com.example.docs.datagen;

import static com.example.docs.datagen.ExampleModDamageTypesProvider.TATER_DAMAGE_TYPE;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import com.example.docs.appearance.ExampleModAppearanceModelProvider;
import com.example.docs.damage.ExampleModDamageTypes;
import com.example.docs.datagen.internal.ExampleModInternalModelProvider;
import com.example.docs.network.basic.ExampleModNetworkingBasicModelProvider;
import com.example.docs.worldgen.ExampleModWorldConfiguredFeatures;
import com.example.docs.worldgen.ExampleModWorldPlacedFeatures;
import com.example.docs.worldgen.ExampleModWorldgenProvider;

// :::datagen-setup:generator
public class ExampleModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		// :::datagen-setup:generator
		// :::datagen-setup:pack
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		// :::datagen-setup:pack

		// :::datagen-enchantments:register
		pack.addProvider(ExampleModEnchantmentGenerator::new);
		// :::datagen-enchantments:register

		// :::datagen-advancements:register
		pack.addProvider(ExampleModAdvancementProvider::new);
		// :::datagen-advancements:register

		// :::datagen-translations:register
		pack.addProvider(ExampleModEnglishLangProvider::new);
		// :::datagen-translations:register

		// :::datagen-tags:register
		pack.addProvider(ExampleModItemTagProvider::new);
		// :::datagen-tags:register
		pack.addProvider(ExampleModEnchantmentTagProvider::new);

		// :::datagen-recipes:register
		pack.addProvider(ExampleModRecipeProvider::new);
		// :::datagen-recipes:register

		// :::datagen-loot-tables:register
		pack.addProvider(ExampleModBlockLootTableProvider::new);
		pack.addProvider(ExampleModChestLootTableProvider::new);
		// :::datagen-loot-tables:register

		pack.addProvider(ExampleModDamageTypesProvider.TaterDamageTypesGenerator::new);
		pack.addProvider(ExampleModDamageTypesProvider.TaterDamageTypeTagGenerator::new);

		pack.addProvider(ExampleModInternalModelProvider::new);

		// :::datagen-models:register
		pack.addProvider(ExampleModModelProvider::new);
		// :::datagen-models:register

		pack.addProvider(ExampleModNetworkingBasicModelProvider::new);

		pack.addProvider(ExampleModAppearanceModelProvider::new);

		pack.addProvider(ExampleModWorldgenProvider::new);

		// :::datagen-setup:generator
	}

	// :::datagen-setup:generator
	// :::datagen-enchantments:bootstrap
	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		// :::datagen-enchantments:bootstrap
		registryBuilder.add(Registries.DAMAGE_TYPE, registerable -> {
			registerable.register(ExampleModDamageTypes.TATER_DAMAGE, TATER_DAMAGE_TYPE);
		});

		// :::datagen-world:registries
		registryBuilder.add(Registries.CONFIGURED_FEATURE, ExampleModWorldConfiguredFeatures::configure);
		registryBuilder.add(Registries.PLACED_FEATURE, ExampleModWorldPlacedFeatures::configure);
		// :::datagen-world:registries
		
		// :::datagen-enchantments:bootstrap
		registryBuilder.add(Registries.ENCHANTMENT, ExampleModEnchantmentGenerator::bootstrap);
	}
	// :::datagen-enchantments:bootstrap

	// :::datagen-setup:generator
}
// :::datagen-setup:generator
