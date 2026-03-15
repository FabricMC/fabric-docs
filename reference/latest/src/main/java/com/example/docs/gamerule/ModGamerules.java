package com.example.docs.gamerule;

import com.example.docs.ExampleMod;
import com.example.docs.gamerule.custom.BadVisionGamerule;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;

import net.minecraft.resources.Identifier;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
// :::gameruleClass
public class ModGamerules {
	// Create and register a boolean gamerule, disabled by default
	public static final Identifier BADVISION_GAMERULE_IDENTIFIER = Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "bad_vision");
	public static final GameRule<Boolean> BADVISION_BOOLEAN_GAMERULE = GameRuleBuilder
					.forBoolean(false) //default value declaration
					.category(GameRuleCategory.MISC)
					.buildAndRegister(BADVISION_GAMERULE_IDENTIFIER);
	// :::gameruleClass
	public static void bootstrap() {
		BadVisionGamerule.initialize();
	}

	public static final Identifier DOUBLE_GAMERULE_IDENTIFIER = Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "double_example");
	// :::double
	public static final GameRule<Double> DOUBLE_GAMERULE = GameRuleBuilder
					.forDouble(6.7) //default value declaration
					.category(GameRuleCategory.MISC)
					.buildAndRegister(DOUBLE_GAMERULE_IDENTIFIER);
	// :::double
	// :::gameruleClass
}
// :::gameruleClass
