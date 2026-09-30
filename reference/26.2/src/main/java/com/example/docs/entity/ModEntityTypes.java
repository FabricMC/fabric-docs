package com.example.docs.entity;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

import com.example.docs.ExampleMod;
import com.example.docs.projectile.HotTaterEntity;

// #region types
public class ModEntityTypes {
	public static final EntityType<MiniGolemEntity> MINI_GOLEM = register(
			ModEntityTypeIds.MINI_GOLEM,
			EntityType.Builder.<MiniGolemEntity>of(MiniGolemEntity::new, MobCategory.MISC)
					.sized(0.75f, 1.75f)
	);
	// #endregion types
	// #region hot_tater
	public static final EntityType<HotTaterEntity> HOT_TATER = register(
			ModEntityTypeIds.HOT_TATER,
			EntityType.Builder.<HotTaterEntity>of(HotTaterEntity::new, MobCategory.MISC)
					.sized(0.25f, 0.25f) // Hitbox width and height.
					.clientTrackingRange(4) // How far (in chunks) clients see the entity.
					.updateInterval(10) // Ticks between position updates sent to clients.
	);
	// #endregion hot_tater
	// #region types

	private static <T extends Entity> EntityType<T> register(ResourceKey<EntityType<?>> key, EntityType.Builder<T> builder) {
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
	}

	public static void registerModEntityTypes() {
		ExampleMod.LOGGER.info("Registering EntityTypes for " + ExampleMod.MOD_ID);
	}

	public static void registerAttributes() {
		FabricDefaultAttributeRegistry.register(MINI_GOLEM, MiniGolemEntity.createMiniGolemAttributes());
	}
}
// #endregion types
