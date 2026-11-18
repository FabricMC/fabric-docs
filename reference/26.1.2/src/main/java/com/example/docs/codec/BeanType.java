package com.example.docs.codec;

import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;

import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import com.example.docs.ExampleMod;

// #region bean_type_record
// A record to keep information relating to a specific
// subclass of Bean, in this case only holding a Codec.
public record BeanType<T extends Bean>(MapCodec<T> codec) {
	// Create a registry to map identifiers to bean types
	public static final Registry<BeanType<?>> REGISTRY = new MappedRegistry<>(
			ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "bean_types")), Lifecycle.stable());
}
// #endregion bean_type_record
