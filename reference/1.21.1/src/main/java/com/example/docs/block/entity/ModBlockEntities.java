package com.example.docs.block.entity;

import com.example.docs.ExampleMod;
import com.example.docs.block.ModBlocks;
import com.example.docs.block.entity.custom.CounterBlockEntity;
import com.example.docs.block.entity.custom.EngineBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {
	public static final BlockEntityType<EngineBlockEntity> ENGINE_BLOCK_ENTITY =
			register("engine", EngineBlockEntity::new, ModBlocks.ENGINE_BLOCK);

	// :::1
	public static final BlockEntityType<CounterBlockEntity> COUNTER_BLOCK_ENTITY =
			register("counter", CounterBlockEntity::new, ModBlocks.COUNTER_BLOCK);

	private static <T extends BlockEntity> BlockEntityType<T> register(String name,
																	BlockEntityType.BlockEntitySupplier<? extends T> entityFactory,
																	Block... blocks) {
		ResourceLocation id = ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, name);
		return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, BlockEntityType.Builder.<T>of(entityFactory, blocks).build());
	}

	// :::1

	public static void initialize() {
	}
}
