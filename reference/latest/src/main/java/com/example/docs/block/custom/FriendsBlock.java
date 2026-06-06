package com.example.docs.block.custom;

import org.jspecify.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import com.example.docs.stats.ModStats;

// #region friends_block
public class FriendsBlock extends Block {
	// #endregion friends_block
	public FriendsBlock(Properties properties) {
		super(properties);
	}

	// #region friends_block
	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity by, ItemStack itemStack) {
		super.setPlacedBy(level, pos, state, by, itemStack);

		if (!(by instanceof Player player)) return;

		int neighborCount = 0;

		for (Direction dir : Direction.values()) {
			if (!level.isEmptyBlock(pos.relative(dir))) {
				neighborCount++;
			}
		}

		player.awardStat(ModStats.FRIENDSHIPS_MADE, neighborCount);
	}
	// #endregion friends_block

	// #region break_friendships
	@Override
	public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
		player.resetStat(Stats.CUSTOM.get(ModStats.FRIENDSHIPS_MADE));

		return super.playerWillDestroy(level, pos, state, player);
	}
	// #endregion break_friendships


	// #region friends_block
}
// #endregion friends_block
