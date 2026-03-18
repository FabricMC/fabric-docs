package com.example.docs.component;

import java.util.function.Consumer;

import com.mojang.serialization.Codec;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

//::1
public record ComponentWithTooltip(int clickCount) implements TooltipProvider {
	//::1
	public static final Codec<ComponentWithTooltip> CODEC = Codec.INT.xmap(ComponentWithTooltip::new, ComponentWithTooltip::clickCount);

	//::1
	@Override
	public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> textConsumer, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter) {
		textConsumer.accept(Component.translatable("item.fabric-docs-reference.counter.info", this.clickCount).withStyle(ChatFormatting.GOLD));
	}
}
//::1
