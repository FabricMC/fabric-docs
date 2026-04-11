package com.example.docs.appearance;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.ARGB;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

// #region tint-source-class
public record RainTintSource(int color) implements ItemTintSource {
	// #endregion tint-source-class
	// #region map-codec
	public static final MapCodec<RainTintSource> MAP_CODEC = RecordCodecBuilder.mapCodec(
					instance -> instance.group(
									ExtraCodecs.RGB_COLOR_CODEC.fieldOf("color").forGetter(RainTintSource::color)).apply(instance, RainTintSource::new)
	);
	// #endregion map-codec
	// #region tint-source-class
	public RainTintSource() {
		this(ARGB.opaque(0x00BFFF)); // Color code in hex format
	}
	// #endregion tint-source-class

	// #region calculate
	@Override
	public int calculate(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity) {
		if (clientLevel != null && clientLevel.isRaining()) {
			return ARGB.opaque(color);
		}

		return ARGB.opaque(0xFFEFD5); // Color code in hex format
	}
	// #endregion calculate
	// #region return-codec
	@Override
	public @NotNull MapCodec<? extends ItemTintSource> type() {
		return MAP_CODEC;
	}
	// #endregion return-codec
	// #region tint-source-class
}
// #endregion tint-source-class
