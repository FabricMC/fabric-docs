package com.example.docs.mixin.client.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.Hud;

//#region mixin_accessors_instance_field_accessor_example
@Mixin(Hud.class)
public interface HudAccessor {
	@Accessor("overlayMessageTime")
	int example_mod$getOverlayMessageTime();

	@Accessor("overlayMessageTime")
	void example_mod$setOverlayMessageTime(int messageTime);
}
//#endregion mixin_accessors_instance_field_accessor_example
