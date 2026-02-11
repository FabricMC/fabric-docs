package com.example.docs.datagen.internal;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.blockstates.Condition;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.Variant;
import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import com.example.docs.block.ModBlocks;
import com.example.docs.block.custom.PrismarineLampBlock;
import com.example.docs.item.ModItems;

/**
 * This generator is just for the reference item and block models.
 * Not for describing how to use the model provider.
 */
public class FabricDocsReferenceInternalModelProvider extends FabricModelProvider {
	public FabricDocsReferenceInternalModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
		blockStateModelGenerator.createTrivialCube(ModBlocks.CONDENSED_DIRT);
		blockStateModelGenerator.createTrivialCube(ModBlocks.COUNTER_BLOCK);

		// TODO: This would be a good example for the model generation page. Move when needed.
		// TODO: Actually make the model for the prismarine lamp - not sure how to do it via datagen.
		blockStateModelGenerator.blockStateOutput.accept(MultiPartGenerator.multiPart(ModBlocks.PRISMARINE_LAMP)
				.with(Condition.condition().term(PrismarineLampBlock.ACTIVATED, true),
						Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(ModBlocks.PRISMARINE_LAMP, "_on")))
				.with(Condition.condition().term(PrismarineLampBlock.ACTIVATED, false),
						Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(ModBlocks.PRISMARINE_LAMP)))
		);

		blockStateModelGenerator.woodProvider(ModBlocks.CONDENSED_OAK_LOG).log(ModBlocks.CONDENSED_OAK_LOG);
	}

	@Override
	public void generateItemModels(ItemModelGenerators itemModelGenerator) {
		itemModelGenerator.generateFlatItem(ModItems.COUNTER, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(ModItems.LIGHTNING_STICK, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(ModItems.GUIDITE_BOOTS, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(ModItems.GUIDITE_CHESTPLATE, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(ModItems.GUIDITE_HELMET, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(ModItems.GUIDITE_LEGGINGS, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(ModItems.POISONOUS_APPLE, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(ModItems.SUSPICIOUS_SUBSTANCE, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(ModItems.GUIDITE_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM);
	}

	@Override
	public String getName() {
		return "FabricDocsReference Internal Model Provider";
	}
}
