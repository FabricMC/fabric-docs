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

// :::datagen-setup:generator
public class ExampleModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		// :::datagen-setup:generator
		// :::datagen-setup:pack
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		// :::datagen-setup:pack

		// :::custom-enchantments:register-generator
		pack.addProvider(ExampleModEnchantmentGenerator::new);
		// :::custom-enchantments:register-generator

		// :::datagen-advancements:register
		pack.addProvider(ExampleModAdvancementProvider::new);
		// :::datagen-advancements:register

		// :::datagen-translations:register
		pack.addProvider(ExampleModEnglishLangProvider::new);
		// :::datagen-translations:register

		// :::datagen-tags:register
		pack.addProvider(ExampleModItemTagProvider::new);
		// :::datagen-tags:register

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

		// :::datagen-setup:generator
	}

	// :::datagen-setup:generator
	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		registryBuilder.add(Registries.DAMAGE_TYPE, registerable -> {
			registerable.register(ExampleModDamageTypes.TATER_DAMAGE, TATER_DAMAGE_TYPE);
		});
	}

	// :::datagen-setup:generator
}
// :::datagen-setup:generator
