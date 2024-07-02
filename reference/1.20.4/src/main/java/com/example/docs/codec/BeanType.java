package com.example.docs.codec;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;

// :::
// A record to keep information relating to a specific
// subclass of Bean, in this case only holding a Codec.
public record BeanType<T extends Bean>(Codec<T> codec) {
	// Create a registry to map identifiers to bean types
	public static final Registry<BeanType<?>> REGISTRY = new SimpleRegistry<>(
			RegistryKey.ofRegistry(new Identifier("example", "bean_types")), Lifecycle.stable());
}
// :::
