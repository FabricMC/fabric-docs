package com.example.docs.block.entity;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import com.example.docs.FabricDocsReference;
import com.example.docs.block.ModBlocks;
import com.example.docs.block.entity.custom.CounterBlockEntity;
import com.example.docs.block.entity.custom.EngineBlockEntity;

public class ModBlockEntities {
	public static final BlockEntityType<EngineBlockEntity> ENGINE_BLOCK_ENTITY =
			register("engine", EngineBlockEntity::new, ModBlocks.ENGINE_BLOCK);

	// :::1
	public static final BlockEntityType<CounterBlockEntity> COUNTER_BLOCK_ENTITY =
			register("counter", CounterBlockEntity::new, ModBlocks.COUNTER_BLOCK);

	private static <T extends BlockEntity> BlockEntityType<T> register(String name,
																	BlockEntityType.BlockEntityFactory<? extends T> entityFactory,
																	Block... blocks) {
		Identifier id = Identifier.of(FabricDocsReference.MOD_ID, name);
		return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, BlockEntityType.Builder.<T>create(entityFactory, blocks).build());
	}

	// :::1

	public static void initialize() {
	}
}
