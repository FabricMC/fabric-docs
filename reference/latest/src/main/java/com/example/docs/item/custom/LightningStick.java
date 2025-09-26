package com.example.docs.item.custom;

import java.util.function.Consumer;

import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// :::1
public class LightningStick extends Item {
	public LightningStick(Settings settings) {
		super(settings);
	}

	// :::1
	// :::2
	@Override
	public ActionResult use(World world, PlayerEntity user, Hand hand) {
		// Ensure we don't spawn the lightning only on the client.
		// This is to prevent desync.
		if (world.isClient) {
			return ActionResult.PASS;
		}

		BlockPos frontOfPlayer = user.getBlockPos().offset(user.getHorizontalFacing(), 10);

		// Spawn the lightning bolt.
		LightningEntity lightningBolt = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
		lightningBolt.setPosition(frontOfPlayer.toCenterPos());
		world.spawnEntity(lightningBolt);

		return ActionResult.SUCCESS;
	}

	// :::2
	// :::3
	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
		textConsumer.accept(Text.translatable("itemTooltip.example-mod.lightning_stick").formatted(Formatting.GOLD));
	}

	// :::3
	// :::1
}
// :::1
