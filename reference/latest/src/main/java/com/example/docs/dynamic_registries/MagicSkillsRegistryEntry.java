package com.example.docs.dynamic_registries;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.commands.CacheableFunction;

import java.util.Optional;

// #region main
// #region client_codec
public record MagicSkillsRegistryEntry(String name, int manaCost, Optional<CacheableFunction> onUseMcFunction) {
	// #endregion client_codec
	public static final Codec<MagicSkillsRegistryEntry> CODEC = RecordCodecBuilder.create(instance ->
		instance.group(
			Codec.STRING.fieldOf("name").forGetter(MagicSkillsRegistryEntry::name),
			Codec.INT.fieldOf("manaCost").forGetter(MagicSkillsRegistryEntry::manaCost),
			CacheableFunction.CODEC.optionalFieldOf("onUseMcFunction").forGetter(MagicSkillsRegistryEntry::onUseMcFunction)
		).apply(instance, MagicSkillsRegistryEntry::new)
	);
	// #endregion main

	// #region client_codec
	// Other Variables and Methods

	public static final Codec<MagicSkillsRegistryEntry> CLIENT_CODEC = RecordCodecBuilder.create(instance ->
		instance.group(
			Codec.STRING.fieldOf("name").forGetter(MagicSkillsRegistryEntry::name),
			Codec.INT.fieldOf("manaCost").forGetter(MagicSkillsRegistryEntry::manaCost)
		).apply(instance, (name,manaCost) -> new MagicSkillsRegistryEntry(name, manaCost, Optional.empty()))
	);
	// #region main
}
// #endregion client_codec
// #endregion main
