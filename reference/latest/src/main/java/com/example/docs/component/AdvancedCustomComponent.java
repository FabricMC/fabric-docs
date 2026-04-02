package com.example.docs.component;

import java.util.function.Consumer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

//::1
public record AdvancedCustomComponent(float temperature, boolean burnt) implements TooltipProvider {
	//::1
	//::codec
	public static final Codec<AdvancedCustomComponent> CODEC = RecordCodecBuilder.create(builder -> {
		return builder.group(
			Codec.FLOAT.fieldOf("temperature").forGetter(AdvancedCustomComponent::temperature),
			Codec.BOOL.optionalFieldOf("burnt", false).forGetter(AdvancedCustomComponent::burnt)
		).apply(builder, AdvancedCustomComponent::new);
	});
	//::codec

	//::1
	@Override
	public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltip, TooltipFlag flag, DataComponentGetter components) {
		tooltip.accept(Component.translatable("item.example-mod.temperature.info", temperature).withStyle(ChatFormatting.GOLD));
		tooltip.accept(Component.translatable("item.example-mod.burnt.info", temperature).withStyle(ChatFormatting.GOLD));
	}
	//::1
	//::1
}
//::1
