package com.example.docs.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

import com.example.docs.FabricDocsReference;


public class ModEntityTypes {
	public static final EntityType<MiniGolemEntity> MINI_GOLEM = register(
			"mini_golem",
			EntityType.Builder.<MiniGolemEntity>create(MiniGolemEntity::new, SpawnGroup.MISC)
					.dimensions(0.75f, 1.75f)
	);

	private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
		RegistryKey<EntityType<?>> key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(FabricDocsReference.MOD_ID, name));
		return Registry.register(Registries.ENTITY_TYPE, key, builder.build(key));
	}

	public static void registerModEntityTypes(){
		FabricDocsReference.LOGGER.info("Registering EntityTypes for " + FabricDocsReference.MOD_ID);
	}

	public static void registerAttributes() {
		FabricDefaultAttributeRegistry.register(MINI_GOLEM, MiniGolemEntity.createCubeAttributes());
	}
}
