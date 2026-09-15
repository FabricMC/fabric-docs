package com.example.docs.item.shield;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

import com.example.docs.ExampleMod;

public class GuiditeShieldLayers {
	// #region layer
	public static final ModelLayerLocation GUIDITE_SHIELD =
					new ModelLayerLocation(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "guidite_shield"), "main");
					// #endregion layer
}
