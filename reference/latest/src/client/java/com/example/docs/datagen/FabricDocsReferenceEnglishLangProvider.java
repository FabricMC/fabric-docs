package com.example.docs.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import com.example.docs.block.ModBlocks;
import com.example.docs.item.ModItems;

// :::datagen-translations:provider
public class FabricDocsReferenceEnglishLangProvider extends FabricLanguageProvider {
	protected FabricDocsReferenceEnglishLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
		// Specifying en_us is optional, as it's the default language code
		super(dataOutput, "en_us", registryLookup);
	}

	@Override
	public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
		// :::datagen-translations:provider
		// :::datagen-translations:build
		translationBuilder.add("text.fabric_docs_reference.greeting", "Hello there!");
		// :::datagen-translations:build
		translationBuilder.add(ModItems.GUIDITE_HELMET, "Guidite Helmet");
		translationBuilder.add(ModItems.GUIDITE_CHESTPLATE, "Guidite Chestplate");
		translationBuilder.add(ModItems.GUIDITE_LEGGINGS, "Guidite Leggings");
		translationBuilder.add(ModItems.GUIDITE_BOOTS, "Guidite Boots");
		translationBuilder.add(ModItems.GUIDITE_SWORD, "Guidite Sword");
		translationBuilder.add(ModItems.SUSPICIOUS_SUBSTANCE, "Suspicious Substance");

		translationBuilder.add(Util.createTranslationKey("effect", Identifier.of("fabric-docs-reference", "tater")), "Tater");

		// You can alternatively use the translationBuilder.add(Path.of("../existing/language/file.json"));
		// to add translations from an existing language file instead of manually defining them all.
		translationBuilder.add("sound.fabric-docs-reference.metal_whistle", "Metal Whistle");
		translationBuilder.add("item.minecraft.potion.effect.tater", "Tater Potion");
		translationBuilder.add("death.attack.tater", "%1$s died from Tater damage!");
		translationBuilder.add("item.fabric-docs-reference.poisonous_apple", "Poisonous Apple");
		translationBuilder.add("item.fabric-docs-reference.lightning_stick", "Lightning Stick");
		translationBuilder.add("item.fabric-docs-reference.counter", "Counter");
		translationBuilder.add("item.fabric-docs-reference.counter.info", "Used %1$s times");
		translationBuilder.add("itemTooltip.fabric-docs-reference.lightning_stick", "This is an extremely powerful weapon that can summon lightning bolts.");
		translationBuilder.add("itemGroup.fabric_docs_reference", "Fabric Docs Reference");
		translationBuilder.add("enchantment.fabric-docs-reference.thundering", "Thundering");

		translationBuilder.add(ModBlocks.CONDENSED_DIRT, "Condensed Dirt");
		translationBuilder.add(ModBlocks.CONDENSED_OAK_LOG, "Condensed Oak Log");
		translationBuilder.add(ModBlocks.COUNTER_BLOCK, "Counter Block");
		translationBuilder.add(ModBlocks.PRISMARINE_LAMP, "Prismarine Lamp");
		translationBuilder.add(ModBlocks.ENGINE_BLOCK, "Engine Block");
		translationBuilder.add(ModBlocks.CONDENSED_DIRT.asItem(), "Condensed Dirt");
		translationBuilder.add(ModBlocks.CONDENSED_OAK_LOG.asItem(), "Condensed Oak Log");
		translationBuilder.add(ModBlocks.COUNTER_BLOCK.asItem(), "Counter Block");
		translationBuilder.add(ModBlocks.PRISMARINE_LAMP.asItem(), "Prismarine Lamp");
		translationBuilder.add(ModBlocks.ENGINE_BLOCK.asItem(), "Engine Block");

		translationBuilder.add(ModBlocks.STEEL_BLOCK.asItem(), "Steel Block");
		translationBuilder.add(ModBlocks.PIPE_BLOCK.asItem(), "Pipe Block");
		translationBuilder.add(ModBlocks.RUBY_BLOCK.asItem(), "Ruby Block");
		translationBuilder.add(ModBlocks.RUBY_STAIRS.asItem(), "Ruby Stairs");
		translationBuilder.add(ModBlocks.RUBY_SLAB.asItem(), "Ruby Slab");
		translationBuilder.add(ModBlocks.RUBY_FENCE.asItem(), "Ruby Fence");
		translationBuilder.add(ModBlocks.RUBY_DOOR.asItem(), "Ruby Door");
		translationBuilder.add(ModBlocks.RUBY_TRAPDOOR.asItem(), "Ruby Trapdoor");
		translationBuilder.add(ModBlocks.VERTICAL_OAK_LOG_SLAB.asItem(), "Vertical Oak Log Slab");

		translationBuilder.add(ModItems.RUBY, "Ruby");
		translationBuilder.add(ModItems.GUIDITE_AXE, "Guidite Axe");
		translationBuilder.add(ModItems.SUSPICIOUS_EGG, "Suspicious Egg");
		translationBuilder.add(ModItems.LEATHER_GLOVES, "Leather Gloves");
		translationBuilder.add(ModItems.FLASHLIGHT, "Flashlight");
		translationBuilder.add(ModItems.BALLOON, "Balloon");
		// :::datagen-translations:provider
	}
}
// :::datagen-translations:provider
