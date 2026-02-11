package com.example.docs.component;

import java.util.function.Consumer;

import com.mojang.serialization.Codec;

import net.minecraft.component.ComponentsAccess;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

//::1
public record ComponentWithTooltip(int clickCount) implements TooltipAppender {
	//::1
	public static final Codec<ComponentWithTooltip> CODEC = Codec.INT.xmap(ComponentWithTooltip::new, ComponentWithTooltip::clickCount);

	//::1
	@Override
	public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
			textConsumer.accept(Text.translatable("item.fabric-docs-reference.counter.info", this.clickCount).formatted(Formatting.GOLD));
	}
}
//::1
