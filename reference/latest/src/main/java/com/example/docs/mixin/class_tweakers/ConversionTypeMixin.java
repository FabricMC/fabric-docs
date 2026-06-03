package com.example.docs.mixin.class_tweakers;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.world.entity.ConversionParams;
import net.minecraft.world.entity.ConversionType;
import net.minecraft.world.entity.Mob;

// #region enum_extension_abstract_method_impls_example_mixin
@Mixin(ConversionType.class)
enum ConversionTypeMixin {
	EXAMPLE_MOD_CONVERSION_TYPE(false) {
		@Override
		void convert(Mob from, Mob to, ConversionParams params) {
			// ...
		}
	};

	@Shadow
	abstract void convert(Mob from, Mob to, ConversionParams params);

	@Shadow
	ConversionTypeMixin(final boolean discardAfterConversion) {
	}
}
// #endregion enum_extension_abstract_method_impls_example_mixin
