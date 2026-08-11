package com.example.docs.mixin.class_tweakers;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.MixinIntrinsics;
import org.spongepowered.asm.mixin.Shadow;

//#region enum_extension_current_enum_ordinal_example_mixin
@Mixin(targets = "net.minecraft.world.entity.monster.illager.SpellcasterIllager$IllagerSpell")
enum IllagerSpellMixin {
	EXAMPLE_MOD_ILLAGER_SPELL(MixinIntrinsics.currentEnumOrdinal(), 0.8, 0.8, 0.2);

	@Shadow
	IllagerSpellMixin(final int id, final double red, final double green, final double blue) {
	}
}
//#endregion enum_extension_current_enum_ordinal_example_mixin
