package com.example.docs.component;

import java.util.function.Consumer;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

// #region component_with_tooltip
public record ComponentWithTooltip(int clickCount) implements TooltipProvider {
	// #endregion component_with_tooltip
	public static final Codec<ComponentWithTooltip> CODEC = ExtraCodecs.NON_NEGATIVE_INT.xmap(ComponentWithTooltip::new, ComponentWithTooltip::clickCount);
	public static final StreamCodec<ByteBuf, ComponentWithTooltip> STREAM_CODEC = ByteBufCodecs.VAR_INT.map(ComponentWithTooltip::new, ComponentWithTooltip::clickCount);

	// #region component_with_tooltip
	@Override
	public void addToTooltip(TooltipContext tooltip, Consumer<Component> textConsumer, TooltipFlag type, DataComponentGetter components) {
		textConsumer.accept(Component.translatable("item.example-mod.counter.info", this.clickCount).withStyle(ChatFormatting.GOLD));
	}
}
// #endregion component_with_tooltip
