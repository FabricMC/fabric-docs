package com.example.docs.interface_injection;

import java.util.Optional;

import net.minecraft.sounds.SoundEvent;

// #region interface-injection-example-interface
public interface BucketEmptySoundGetter {
    
    default Optional<SoundEvent> modid$getBucketEmptySound() {
        return Optional.empty();
    }

}
// #endregion interface-injection-example-interface
