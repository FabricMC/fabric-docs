package com.example.docs.event;

import java.util.Optional;

import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.predicates.entity.EntityEquipmentPredicate;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;

import com.example.docs.ExampleMod;

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
			// If the loot table is for a white sheep, and it is not overridden by a user:
			if (source.isBuiltin() && BuiltInLootTables.SHEEP.white().equals(key)) {
				// Create a new loot pool that will hold the diamonds.
				LootPool.Builder pool = LootPool.lootPool()
						// Add diamonds...
						.add(LootItem.lootTableItem(Items.DIAMOND))
						// ...only if the sheep was killed with a diamond sword.
						.when(LootItemEntityPropertyCondition.hasProperties(
								LootContext.EntityTarget.ATTACKER,
								EntityPredicate.Builder.entity().equipment(
										EntityEquipmentPredicate.Builder.equipment().mainhand(
												ItemPredicate.Builder.item().of(
														registries.lookupOrThrow(Registries.ITEM),
														Items.DIAMOND_SWORD
												)
										)
								)
						));
				// Add the loot pool to the loot table
				tableBuilder.withPool(pool);
			}
		});
		// #endregion loot_table_modify_event

		// #region loot_table_replace_event
		LootTableEvents.REPLACE.register((key, original, source, holder) -> {
			// If the loot table is for a brown sheep, and it is not overridden by a user:
			if (source.isBuiltin() && BuiltInLootTables.SHEEP.brown().equals(key)) {
				// Create a new loot pool that will hold the gold ingot.
				LootPool.Builder pool = LootPool.lootPool()
						// Add gold ingot...
						.add(LootItem.lootTableItem(Items.GOLD_INGOT))
						// ...only if the sheep was killed with a golden sword.
						.when(LootItemEntityPropertyCondition.hasProperties(
								LootContext.EntityTarget.ATTACKER,
								EntityPredicate.Builder.entity().equipment(
										EntityEquipmentPredicate.Builder.equipment().mainhand(
												ItemPredicate.Builder.item().of(
														holder.lookupOrThrow(Registries.ITEM),
														Items.GOLDEN_SWORD
												)
										)
								)
						));
				// Create a new loot table with the loot pool
				return LootTable.lootTable().withPool(pool).build();
			}

			return null;
		});
		// #endregion loot_table_replace_event

		// #region loot_table_modify_drops_event
		LootTableEvents.MODIFY_DROPS.register((holder, context, drops) -> {
			// Replace cobblestone drop in stone loot table with stone in the drops.
			if (holder.is(Blocks.STONE.getLootTable().orElseThrow())) {
				// Only replace if cobblestone is actually present, so we don't override already modified drops.
				if (drops.removeIf(stack -> stack.is(Items.COBBLESTONE))) {
					drops.add(new ItemStack(Items.STONE, 2));
				}
			}
		});
		// #endregion loot_table_modify_drops_event

		// #region loot_table_all_loaded_event
		LootTableEvents.ALL_LOADED.register((resourceManager, lootRegistry) -> {
			Optional<LootTable> blueWoolTable = lootRegistry.getOptional(Blocks.WOOL.blue().getLootTable().orElse(null));

			// Log a warning if the blue wool loot table is empty or missing.
			if (blueWoolTable.isEmpty() || blueWoolTable.get() == LootTable.EMPTY) {
				ExampleMod.LOGGER.warn("blue wool loot table should not be empty");
			}
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
