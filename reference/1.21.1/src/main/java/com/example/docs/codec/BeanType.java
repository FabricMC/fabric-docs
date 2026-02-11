package com.example.docs.codec;

import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

// :::
// A record to keep information relating to a specific
// subclass of Bean, in this case only holding a Codec.
public record BeanType<T extends Bean>(MapCodec<T> codec) {
	// Create a registry to map identifiers to bean types
	public static final Registry<BeanType<?>> REGISTRY = new MappedRegistry<>(
			ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath("example", "bean_types")), Lifecycle.stable());
}
// :::
