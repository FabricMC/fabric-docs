package com.example.docs.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;


public class FabricDocsReferanceCustomEntity {

    public static final RegistryKey<EntityType<?>> CUSTOM_ENTITY_KEY =
            RegistryKey.of(RegistryKeys.ENTITY_TYPE,Identifier.of(FabricDocsReferenceEntity.MOD_ID,"customentity"));

    public static final EntityType<CustomEntity> CustomEntity = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(FabricDocsReferenceEntity.MOD_ID,"customentity"),
            EntityType.Builder.create(CustomEntity::new, SpawnGroup.MONSTER)
                    .dimensions(2f,3f).build(CUSTOM_ENTITY_KEY));
                    
    //DOBULE CHECK THIS !! 

    public static void registerCustomEntity(){

        FabricDocsReferenceEntity.LOGGER.info("Registering Custom Entities" + FabricDocsReferenceEntity.MOD_ID);
    }

}