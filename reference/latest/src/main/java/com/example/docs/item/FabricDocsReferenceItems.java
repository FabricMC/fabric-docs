package com.example.docs.item;

import com.example.docs.FabricDocsReference;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class FabricDocsReferenceItems {
    private FabricDocsReferenceItems() {
    }

    public static final CustomSoundItem CUSTOM_SOUND_ITEM = register("custom-sound-item",
            new CustomSoundItem(new FabricItemSettings().maxCount(1)));

    private static <T extends Item> T register(String name, T item) {
        Identifier identifier = new Identifier(FabricDocsReference.MOD_ID, name);
        return Registry.register(Registries.ITEM, identifier, item);
    }

    public static void initialize() {
        FabricDocsReference.LOGGER.info("initializing items");
    }
}