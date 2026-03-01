package com.example.docs.block.entity;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;

import com.example.docs.ExampleMod;
import com.example.docs.block.ModBlocks;
import com.example.docs.block.entity.custom.CounterBlockEntity;
import com.example.docs.block.entity.custom.DuplicatorBlockEntity;
import com.example.docs.block.entity.custom.EngineBlockEntity;

public class ModBlockEntities {
	public static final BlockEntityType<EngineBlockEntity> ENGINE_BLOCK_ENTITY =
			register("engine", EngineBlockEntity::new, ModBlocks.ENGINE_BLOCK);

	public static final BlockEntityType<DuplicatorBlockEntity> DUPLICATOR_BLOCK_ENTITY =
			register("duplicator", DuplicatorBlockEntity::new, ModBlocks.DUPLICATOR_BLOCK);

	// :::1
	public static final BlockEntityType<CounterBlockEntity> COUNTER_BLOCK_ENTITY =
			register("counter", CounterBlockEntity::new, ModBlocks.COUNTER_BLOCK);

	private static <T extends BlockEntity> BlockEntityType<T> register(
			String name,
			FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
			Block... blocks
	) {
		Identifier id = Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, name);
		return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
	}

	// :::1

	public static void initialize() {
	}
}
