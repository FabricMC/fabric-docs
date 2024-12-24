package com.example.docs.datagen;

import com.example.docs.item.ModItems;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;

import java.util.concurrent.CompletableFuture;
// :::datagen-translations:1
public class FabricDocsReferenceEnglishLangProvider extends FabricLanguageProvider {
	protected FabricDocsReferenceEnglishLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
		// Specifying en_us is optional, as it's the default language code
		super(dataOutput, "en_us", registryLookup);
	}

	@Override
	public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
		// :::datagen-translations:1
		// :::datagen-translations:3
		translationBuilder.add("text.fabric-docs-reference.greeting", "Hello there!");
		// :::datagen-translations:3
		// :::datagen-translations:1
	}
}
// :::datagen-translations:1