package com.example.docs.datagen.util;

import com.example.docs.FabricDocsReference;
import com.example.docs.block.ModBlocks;

import com.example.docs.block.custom.PrismarineLampBlock;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;

import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.BlockStateVariant;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.MultipartBlockStateSupplier;
import net.minecraft.data.client.VariantSettings;
import net.minecraft.data.client.When;
import net.minecraft.util.Identifier;

/**
 * This generator is just for the reference item and block models.
 * Not for describing how to use the model provider.
 */
public class FabricDocsReferenceInternalModelProvider extends FabricModelProvider {
	public FabricDocsReferenceInternalModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CONDENSED_DIRT);
		blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.COUNTER_BLOCK);

		// TODO: This would be a good example for the model generation page. Move when needed.
		blockStateModelGenerator.blockStateCollector.accept(MultipartBlockStateSupplier.create(ModBlocks.PRISMARINE_LAMP)
				.with(When.create().set(PrismarineLampBlock.ACTIVATED, true),
						BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(ModBlocks.PRISMARINE_LAMP, "on")))
				.with(When.create().set(PrismarineLampBlock.ACTIVATED, false),
						BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockModelId(ModBlocks.PRISMARINE_LAMP)))
		);

		blockStateModelGenerator.registerAxisRotated(ModBlocks.CONDENSED_OAK_LOG, Identifier.of(FabricDocsReference.MOD_ID, "block/condensed_oak_log"));
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {

	}

	@Override
	public String getName() {
		return "FabricDocsReference Internal Model Provider";
	}
}
