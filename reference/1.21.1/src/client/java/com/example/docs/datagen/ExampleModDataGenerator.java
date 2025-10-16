package com.example.docs.datagen;

import static com.example.docs.datagen.ExampleModDamageTypesProvider.TATER_DAMAGE_TYPE;

import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import com.example.docs.damage.ExampleModDamageTypes;

// :::datagen-setup:generator
public class ExampleModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		// :::datagen-setup:generator
		// :::datagen-setup:pack
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		// :::datagen-setup:pack

		pack.addProvider(EnchantmentGenerator::new);

		pack.addProvider(ExampleModAdvancementProvider::new);

		pack.addProvider(ExampleModEnglishLangProvider::new);

		pack.addProvider(ExampleModItemTagProvider::new);

		pack.addProvider(ExampleModRecipeProvider::new);

		pack.addProvider(ExampleModBlockLootTableProvider::new);
		pack.addProvider(ExampleModChestLootTableProvider::new);

		pack.addProvider(ExampleModDamageTypesProvider.TaterDamageTypesGenerator::new);
		pack.addProvider(ExampleModDamageTypesProvider.TaterDamageTypeTagGenerator::new);

		// :::datagen-setup:generator
	}

	// :::datagen-setup:generator
	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.DAMAGE_TYPE, registerable -> {
			registerable.register(ExampleModDamageTypes.TATER_DAMAGE, TATER_DAMAGE_TYPE);
		});
	}

	// :::datagen-setup:generator
}
// :::datagen-setup:generator
