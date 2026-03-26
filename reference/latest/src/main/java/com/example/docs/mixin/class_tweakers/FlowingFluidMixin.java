package com.example.docs.mixin.class_tweakers;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;

import com.example.docs.interface_injection.BucketEmptySoundGetter;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

// #region interface-injection-example-mixin
@Mixin(FlowingFluid.class)
abstract class FlowingFluidMixin extends Fluid implements BucketEmptySoundGetter {

    @Override
    public Optional<SoundEvent> modid$getBucketEmptySound() {
        return Optional.of(this.is(FluidTags.LAVA) ? SoundEvents.BUCKET_EMPTY_LAVA : SoundEvents.BUCKET_EMPTY);
    }

}
// #endregion interface-injection-example-mixin
