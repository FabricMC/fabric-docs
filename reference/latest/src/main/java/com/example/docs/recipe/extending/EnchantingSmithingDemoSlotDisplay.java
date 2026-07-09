package com.example.docs.recipe.extending;

import java.util.function.BinaryOperator;
import java.util.stream.Stream;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.display.DisplayContentsFactory;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.item.enchantment.Enchantment;

import com.example.docs.mixin.accessor.SlotDisplayAccessor;

// #region slot_display
public record EnchantingSmithingDemoSlotDisplay(SlotDisplay base, SlotDisplay material, Object2IntOpenHashMap<Holder<Enchantment>> enchantments) implements SlotDisplay {
	public static final MapCodec<EnchantingSmithingDemoSlotDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec(
			(instance) -> instance.group(
					SlotDisplay.CODEC.fieldOf("base").forGetter(display -> display.base),
					SlotDisplay.CODEC.fieldOf("material").forGetter(display -> display.material),
					EnchantingSmithingRecipe.ENCHANTMENTS_CODEC.fieldOf("enchantments").forGetter(display -> display.enchantments)
			).apply(instance, EnchantingSmithingDemoSlotDisplay::new)
	);
	public static final StreamCodec<RegistryFriendlyByteBuf, EnchantingSmithingDemoSlotDisplay> STREAM_CODEC = StreamCodec.composite(
			SlotDisplay.STREAM_CODEC, display -> display.base,
			SlotDisplay.STREAM_CODEC, display -> display.material,
			EnchantingSmithingRecipe.ENCHANTMENTS_STREAM_CODEC, display -> display.enchantments,
			EnchantingSmithingDemoSlotDisplay::new
	);
	public static final Type<EnchantingSmithingDemoSlotDisplay> TYPE = new Type<>(MAP_CODEC, STREAM_CODEC);

	@Override
	public <T> Stream<T> resolve(ContextMap context, DisplayContentsFactory<T> factory) {
		RandomSource randomSource = RandomSource.createThreadLocalInstance(System.identityHashCode(this));
		BinaryOperator<ItemStack> transformation = (base, material) -> EnchantingSmithingRecipe.applyEnchantments(base, this.enchantments);
		return SlotDisplayAccessor.applyDemoTransformation(context, factory, this.base, this.material, randomSource, transformation);
	}

	@Override
	public Type<? extends SlotDisplay> type() {
		return TYPE;
	}
}
// #endregion slot_display
