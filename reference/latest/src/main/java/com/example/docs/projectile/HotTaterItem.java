package com.example.docs.projectile;

import org.jspecify.annotations.NullMarked;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;

// #region item
@NullMarked
public class HotTaterItem extends Item implements ProjectileItem {
	public HotTaterItem(Properties properties) {
		super(properties);
	}

	// #region as_projectile
	@Override
	public Projectile asProjectile(Level level, Position position, ItemStack itemStack, Direction direction) {
		return new HotTaterEntity(level, position.x(), position.y(), position.z(), itemStack);
	}
	// #endregion as_projectile

	// #region use
	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(hand);
		level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

		// Spawn the projectile on the server only.
		if (level instanceof ServerLevel serverLevel) {
			Projectile.spawnProjectileFromRotation(HotTaterEntity::new,
					serverLevel,
					itemStack,
					player,
					/* yOffset: */ 0.0F,
					/* pow: */ 1.5F,
					/* uncertainty: */ 1.0F);
		}

		player.awardStat(Stats.ITEM_USED.get(this));
		itemStack.consume(1, player);
		return InteractionResult.SUCCESS;
	}
	// #endregion use
}
// #endregion item
