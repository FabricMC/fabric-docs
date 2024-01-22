package com.example.docs.event;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;

// Class to contain all mod events.
public class Events {
	private static final Identifier COAL_ORE_LOOT_TABLE_ID = Blocks.COAL_ORE.getLootTableId();

	// Call this method in your initializer.
	public static void registerModEvents() {
		// :::1
		AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
			BlockState state = world.getBlockState(pos);

			// Manual spectator check is necessary because AttackBlockCallbacks fire before the spectator check
			if (!player.isSpectator() && player.getMainHandStack().isEmpty() && state.isToolRequired()) {
				player.damage(world.getDamageSources().generic(), 1.0F);
			}

			return ActionResult.PASS;
		});
		// :::1

		// :::2
		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (source.isBuiltin() && COAL_ORE_LOOT_TABLE_ID.equals(id)) {
				// We make the pool and add an item
				LootPool.Builder poolBuilder = LootPool.builder().with(ItemEntry.builder(Items.EGG));
				tableBuilder.pool(poolBuilder);
			}
		});
		// :::2

		// :::3
		SheepShearCallback.EVENT.register((player, sheep) -> {
			sheep.setSheared(true);

			// Create diamond item entity at sheep's position.
			ItemStack stack = new ItemStack(Items.DIAMOND);
			ItemEntity itemEntity = new ItemEntity(player.getWorld(), sheep.getX(), sheep.getY(), sheep.getZ(), stack);
			player.getWorld().spawnEntity(itemEntity);

			return ActionResult.FAIL;
		});
		// :::3
	}
}
