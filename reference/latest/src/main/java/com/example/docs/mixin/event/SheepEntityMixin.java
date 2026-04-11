package com.example.docs.mixin.event;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.player.Player;

import com.example.docs.event.SheepShearCallback;

// #region mixin
@Mixin(Sheep.class)
public class SheepEntityMixin {
	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/sheep/Sheep;shear(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/sounds/SoundSource;Lnet/minecraft/world/item/ItemStack;)V"), method = "mobInteract", cancellable = true)
	private void onShear(final Player player, final InteractionHand hand, final CallbackInfoReturnable<InteractionResult> info) {
		InteractionResult result = SheepShearCallback.EVENT.invoker().interact(player, (Sheep) (Object) this);

		if (result == InteractionResult.FAIL) {
			info.setReturnValue(result);
		}
	}
}
// #endregion mixin
