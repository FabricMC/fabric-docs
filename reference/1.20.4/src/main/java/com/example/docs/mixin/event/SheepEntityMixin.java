package com.example.docs.mixin.event;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.example.docs.event.SheepShearCallback;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;

// :::
@Mixin(Sheep.class)
public class SheepEntityMixin {
	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Sheep;shear(Lnet/minecraft/sounds/SoundSource;)V"), method = "mobInteract", cancellable = true)
	private void onShear(final Player player, final InteractionHand hand, final CallbackInfoReturnable<InteractionResult> info) {
		InteractionResult result = SheepShearCallback.EVENT.invoker().interact(player, (Sheep) (Object) this);

		if (result == InteractionResult.FAIL) {
			info.setReturnValue(result);
		}
	}
}
// :::
