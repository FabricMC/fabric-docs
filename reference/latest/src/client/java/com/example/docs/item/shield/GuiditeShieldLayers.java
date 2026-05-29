package com.example.docs.item.shield;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.resources.Identifier;

import com.example.docs.ExampleMod;

public class GuiditeShieldLayers {
	// #region layer
	public static final ModelLayerLocation GUIDITE_SHIELD =
					new ModelLayerLocation(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "guidite_shield"), "main");
	public static final SpriteId GUIDITE_SHIELD_BASE =
					Sheets.SHIELD_MAPPER.apply(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "guidite_shield_base"));
	public static final SpriteId GUIDITE_SHIELD_BASE_NO_PATTERN =
					Sheets.SHIELD_MAPPER.apply(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "guidite_shield_base_nopattern"));
					// #endregion layer
}
