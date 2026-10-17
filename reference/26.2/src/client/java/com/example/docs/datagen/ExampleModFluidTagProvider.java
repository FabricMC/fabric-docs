package com.example.docs.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.tags.FluidTags;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;

import com.example.docs.fluid.ModFluidIds;
import com.example.docs.fluid.ModFluidTags;

public class ExampleModFluidTagProvider extends FabricTagsProvider.FluidTagsProvider {
	public ExampleModFluidTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider wrapperLookup) {
		builder(ModFluidTags.ACID).add(ModFluidIds.ACID_STILL, ModFluidIds.ACID_FLOWING);
		builder(FluidTags.WATER).addTag(ModFluidTags.ACID);
	}
}
