package com.example.docs.entity;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

import com.example.docs.ExampleMod;

//:::types
public class ModEntityTypes {
	public static final EntityType<MiniGolemEntity> MINI_GOLEM = register(
			"mini_golem",
			EntityType.Builder.<MiniGolemEntity>of(MiniGolemEntity::new, MobCategory.MISC)
					.sized(0.75f, 1.75f)
	);

	private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
		ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, name));
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
	}

	public static void registerModEntityTypes() {
		ExampleMod.LOGGER.info("Registering EntityTypes for " + ExampleMod.MOD_ID);
	}

	public static void registerAttributes() {
		FabricDefaultAttributeRegistry.register(MINI_GOLEM, MiniGolemEntity.createCubeAttributes());
	}
}
//:::types
