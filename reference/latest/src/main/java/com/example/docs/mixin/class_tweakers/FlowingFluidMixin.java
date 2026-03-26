package com.example.docs.mixin.class_tweakers;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

import com.example.docs.interface_injection.BucketEmptySoundGetter;

// #region interface-injection-example-mixin
@Mixin(FlowingFluid.class)
abstract class FlowingFluidMixin extends Fluid implements BucketEmptySoundGetter {
	@Override
	public Optional<SoundEvent> example_mod$getBucketEmptySound() {
		return Optional.of(this.is(FluidTags.LAVA) ? SoundEvents.BUCKET_EMPTY_LAVA : SoundEvents.BUCKET_EMPTY);
	}
}
// #endregion interface-injection-example-mixin
