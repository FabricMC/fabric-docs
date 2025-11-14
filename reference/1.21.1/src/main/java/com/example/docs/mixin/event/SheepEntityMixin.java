package com.example.docs.mixin.event;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import com.example.docs.event.SheepShearCallback;

// :::
@Mixin(SheepEntity.class)
public class SheepEntityMixin {
	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/SheepEntity;sheared(Lnet/minecraft/sound/SoundCategory;)V"), method = "interactMob", cancellable = true)
	private void onShear(final PlayerEntity player, final Hand hand, final CallbackInfoReturnable<ActionResult> info) {
		ActionResult result = SheepShearCallback.EVENT.invoker().interact(player, (SheepEntity) (Object) this);

		if (result == ActionResult.FAIL) {
			info.setReturnValue(result);
		}
	}
}
// :::
