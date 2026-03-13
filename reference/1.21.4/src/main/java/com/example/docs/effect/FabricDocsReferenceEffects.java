package com.example.docs.effect;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import com.example.docs.FabricDocsReference;

// :::1
public class FabricDocsReferenceEffects implements ModInitializer {
	public static final Holder<MobEffect> TATER =
			Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, ResourceLocation.fromNamespaceAndPath(FabricDocsReference.MOD_ID, "tater"), new TaterEffect());

	@Override
	public void onInitialize() {
		// ...
	}
}
// :::1
