package com.example.docs.codec;

import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;

// :::
// A record to keep information relating to a specific
// subclass of Bean, in this case only holding a Codec.
public record BeanType<T extends Bean>(MapCodec<T> codec) {
	// Create a registry to map identifiers to bean types
	public static final Registry<BeanType<?>> REGISTRY = new SimpleRegistry<>(
			RegistryKey.ofRegistry(Identifier.of("example", "bean_types")), Lifecycle.stable());
}
// :::
