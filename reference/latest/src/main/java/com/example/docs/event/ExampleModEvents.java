package com.example.docs.event;

import net.minecraft.core.registries.Registries;
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

import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

import java.util.Optional;

// Class to contain all mod events.
public class ExampleModEvents implements ModInitializer {

	@Override
	public void onInitialize() {
		// #region attack_block_callback_event
		AttackBlockCallback.EVENT.register((player, level, hand, pos, direction) -> {
			BlockState state = level.getBlockState(pos);

			// Manual spectator check is necessary because AttackBlockCallbacks fire before the spectator check
			if (!player.isSpectator() && player.getMainHandItem().isEmpty() && state.requiresCorrectToolForDrops() && level instanceof ServerLevel serverLevel) {
				player.hurtServer(serverLevel, level.damageSources().generic(), 1.0F);
			}

			return InteractionResult.PASS;
		});
		// #endregion attack_block_callback_event

		// #region loot_table_modify_event
		LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
					// If the loot table is for the diamond block, and it is not overridden by a user:
					if (source.isBuiltin() && Blocks.DIAMOND_ORE.getLootTable().equals(Optional.of(key))) {
						// Create a new loot pool that will hold the diamonds.
						LootPool.Builder pool = LootPool.lootPool()
								// Add diamonds...
								.add(LootItem.lootTableItem(Items.COAL))
								// ...only if the block would survive a potential explosion.
								.when(ExplosionCondition.survivesExplosion());
						// Add the loot pool to the loot table
						tableBuilder.withPool(pool);
					}
				}
		);
		// #endregion loot_table_modify_event

		// #region loot_table_replace_event
		LootTableEvents.REPLACE.register((key, original, source, holder) -> {
			// If the loot table is for the coal ore block, and it is not overridden by a user:
			if (source.isBuiltin() && Blocks.COAL_ORE.getLootTable().equals(Optional.of(key))) {
				// Create a new loot pool that will hold the diamonds.
				LootPool.Builder pool = LootPool.lootPool()
						// Add diamonds...
						.add(LootItem.lootTableItem(Items.DIAMOND))
						// ...only if the block would survive a potential explosion.
						.when(ExplosionCondition.survivesExplosion());
				// Create a new loot table with the loot pool
				return LootTable.lootTable().withPool(pool).build();
			}
			return original;
		});
		// #endregion loot_table_replace_event

		// #region loot_table_modify_drops_event
		LootTableEvents.MODIFY_DROPS.register((holder, context, drops) -> {
			// Replace a diamond with coal in the drops.
			if (drops.getFirst().getItem() == Items.COBBLESTONE) {
				ItemStack coalStack = new ItemStack(Items.COAL, 2);
				drops.clear();
				drops.add(coalStack);
			}
		});
		// #endregion loot_table_modify_drops_event

		// #region loot_table_all_loaded_event
		LootTableEvents.ALL_LOADED.register((resourceManager, lootRegistry) -> {
			// Example: inspect a loot table after all tables have been loaded.
			lootRegistry.get(Blocks.COAL_ORE.getLootTable().orElseThrow()).ifPresent(table -> {
				// At this point, you can read or post-process the table.
			});
		});
		// #endregion loot_table_all_loaded_event

		// #region sheep_shear_callback_event
		SheepShearCallback.EVENT.register((player, sheep) -> {
			sheep.setSheared(true);

			// Create diamond item entity at sheep's position.
			ItemStack stack = new ItemStack(Items.DIAMOND);
			ItemEntity itemEntity = new ItemEntity(player.level(), sheep.getX(), sheep.getY(), sheep.getZ(), stack);
			player.level().addFreshEntity(itemEntity);

			return InteractionResult.FAIL;
		});
		// #endregion sheep_shear_callback_event
	}
}
