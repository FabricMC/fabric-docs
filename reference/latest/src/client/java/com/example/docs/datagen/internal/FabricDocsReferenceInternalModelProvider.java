package com.example.docs.datagen.internal;

import com.example.docs.FabricDocsReference;
import com.example.docs.block.ModBlocks;

import com.example.docs.block.custom.PrismarineLampBlock;

import com.example.docs.item.ModItems;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

import net.minecraft.block.Block;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.BlockStateVariant;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.ItemModels;
import net.minecraft.client.data.Model;
import net.minecraft.client.data.ModelIds;
import net.minecraft.client.data.Models;
import net.minecraft.client.data.MultipartBlockStateSupplier;
import net.minecraft.client.data.TextureKey;
import net.minecraft.client.data.TextureMap;
import net.minecraft.client.data.TexturedModel;
import net.minecraft.client.data.VariantSettings;
import net.minecraft.client.data.When;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.Optional;

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
		// TODO: Actually make the model for the prismarine lamp - not sure how to do it via datagen.
		blockStateModelGenerator.blockStateCollector.accept(MultipartBlockStateSupplier.create(ModBlocks.PRISMARINE_LAMP)
				.with(When.create().set(PrismarineLampBlock.ACTIVATED, true),
						BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(ModBlocks.PRISMARINE_LAMP, "_on")))
				.with(When.create().set(PrismarineLampBlock.ACTIVATED, false),
						BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockModelId(ModBlocks.PRISMARINE_LAMP)))
		);

		blockStateModelGenerator.registerLog(ModBlocks.CONDENSED_OAK_LOG).log(ModBlocks.CONDENSED_OAK_LOG);
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		itemModelGenerator.register(ModItems.COUNTER, Models.GENERATED);
		itemModelGenerator.register(ModItems.LIGHTNING_STICK, Models.GENERATED);
		itemModelGenerator.register(ModItems.GUIDITE_BOOTS, Models.GENERATED);
		itemModelGenerator.register(ModItems.GUIDITE_CHESTPLATE, Models.GENERATED);
		itemModelGenerator.register(ModItems.GUIDITE_HELMET, Models.GENERATED);
		itemModelGenerator.register(ModItems.GUIDITE_LEGGINGS, Models.GENERATED);
		itemModelGenerator.register(ModItems.POISONOUS_APPLE, Models.GENERATED);
		itemModelGenerator.register(ModItems.SUSPICIOUS_SUBSTANCE, Models.GENERATED);
		itemModelGenerator.registerWithInHandModel(ModItems.GUIDITE_SWORD);
	}

	@Override
	public String getName() {
		return "FabricDocsReference Internal Model Provider";
	}
}
