package com.example.docs.stats;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;

import com.example.docs.ExampleMod;

// #region register
public class ModStats {
	// #region stat
	public static final Identifier FRIENDSHIPS_MADE = register("friendships_made", StatFormatter.DEFAULT);
	// #endregion stat

	private static Identifier register(String name, StatFormatter formatter) {
		Identifier id = Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, name);
		Registry.register(BuiltInRegistries.CUSTOM_STAT, name, id);
		Stats.CUSTOM.get(id, formatter);
		return id;
	}
	// #endregion register

	public static void initialize() {
	}

	// #region register
}
// #endregion register
