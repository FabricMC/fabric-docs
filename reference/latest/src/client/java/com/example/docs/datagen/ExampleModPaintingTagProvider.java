package com.example.docs.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.PaintingVariantTags;
import net.minecraft.world.entity.decoration.painting.PaintingVariant;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;

// region setup
public class ExampleModPaintingTagProvider extends FabricTagsProvider<PaintingVariant> {
	public ExampleModPaintingTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, Registries.PAINTING_VARIANT, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		// endregion setup
		// region builder_entry
		builder(PaintingVariantTags.PLACEABLE)
				.addOptional(ExampleModPaintingVariantProvider.miroma);
		// endregion builder_entry
		// region setup
	}
}
// endregion setup
