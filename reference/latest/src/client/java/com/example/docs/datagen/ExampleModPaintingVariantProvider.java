package com.example.docs.datagen;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.decoration.painting.PaintingVariant;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;

import com.example.docs.ExampleMod;

// region setup
public class ExampleModPaintingVariantProvider extends FabricDynamicRegistryProvider {
	// endregion setup
	// region resource_key
	public static final ResourceKey<PaintingVariant> miroma = ResourceKey.create(
			Registries.PAINTING_VARIANT,
			ExampleMod.id("miroma")
	);
	// endregion resource_key
	// region setup
	public ExampleModPaintingVariantProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(HolderLookup.Provider registries, FabricDynamicRegistryProvider.Entries entries) {
		// endregion setup
		// region variant
		PaintingVariant variant = new PaintingVariant(
				1,
				1,
				ExampleMod.id("miroma"),
				Optional.of(Component.literal("A Wise Man").withColor(TextColor.YELLOW)),
				Optional.of(Component.literal("NotNightSky").withColor(TextColor.GRAY))
		);
		// endregion variant
		// region entry
		entries.add(miroma, variant);
		// endregion entry
		// region setup
	}

	@Override
	public String getName() {
		return "Painting Variants Provider";
	}
}
// endregion setup
