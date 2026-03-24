package com.example.docs.event;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;

// Class to contain all mod events.
public class ExampleModEvents implements ModInitializer {
	private static final ResourceKey<LootTable> COAL_ORE_LOOT_TABLE_ID = Blocks.COAL_ORE.getLootTable().orElseThrow();

	@Override
	public void onInitialize() {
		// :::1
		AttackBlockCallback.EVENT.register((player, level, hand, pos, direction) -> {
			BlockState state = level.getBlockState(pos);

			// Manual spectator check is necessary because AttackBlockCallbacks fire before the spectator check
			if (!player.isSpectator() && player.getMainHandItem().isEmpty() && state.requiresCorrectToolForDrops() && level instanceof ServerLevel serverLevel) {
				player.hurtServer(serverLevel, level.damageSources().generic(), 1.0F);
			}

			return InteractionResult.PASS;
		});
		// :::1

		// :::2
		LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
			// Let's only modify built-in loot tables and leave data pack loot tables untouched by checking the source.
			// We also check that the loot table ID is equal to the ID we want.
			if (source.isBuiltin() && COAL_ORE_LOOT_TABLE_ID.equals(key)) {
				// We make the pool and add an item
				LootPool.Builder poolBuilder = LootPool.lootPool().add(LootItem.lootTableItem(Items.EGG));
				tableBuilder.withPool(poolBuilder);
			}
		});
		// :::2

		// :::3
		SheepShearCallback.EVENT.register((player, sheep) -> {
			sheep.setSheared(true);

			// Create diamond item entity at sheep's position.
			ItemStack stack = new ItemStack(Items.DIAMOND);
			ItemEntity itemEntity = new ItemEntity(player.level(), sheep.getX(), sheep.getY(), sheep.getZ(), stack);
			player.level().addFreshEntity(itemEntity);

			return InteractionResult.FAIL;
		});
		// :::3
	}
}
