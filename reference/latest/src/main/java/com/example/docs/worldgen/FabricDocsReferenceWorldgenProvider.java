package com.example.docs.worldgen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

// :::datagen-world:provider
public class FabricDocsReferenceWorldgenProvider extends FabricDynamicRegistryProvider {
    public FabricDocsReferenceWorldgenProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {        
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        // :::datagen-world:provider
		entries.addAll(registries.getOrThrow(RegistryKeys.CONFIGURED_FEATURE));
		entries.addAll(registries.getOrThrow(RegistryKeys.PLACED_FEATURE));
        // :::datagen-world:provider 
    }

    @Override
    public String getName() {
        return "";
    }
}
// :::datagen-world:provider
