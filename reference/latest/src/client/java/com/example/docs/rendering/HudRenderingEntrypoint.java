package com.example.docs.rendering;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;

import com.example.docs.FabricDocsReference;

// :::1
public class HudRenderingEntrypoint implements ClientModInitializer {
	private static final Identifier EXAMPLE_LAYER = Identifier.of(FabricDocsReference.MOD_ID, "hud-example-layer");

	@Override
	public void onInitializeClient() {
		// Attach our rendering code to before the chat hud layer. Our layer will render right before the chat. The API will take care of z spacing and automatically add 200 after every layer.
		HudLayerRegistrationCallback.EVENT.register(layeredDrawer -> layeredDrawer.attachLayerBefore(IdentifiedLayer.CHAT, EXAMPLE_LAYER, HudRenderingEntrypoint::render));
	}

	private static void render(DrawContext context, RenderTickCounter tickCounter) {
		int color = 0xFFFF0000; // Red
		int targetColor = 0xFF00FF00; // Green

		// You can use the Util.getMeasuringTimeMs(); function to get the current time in seconds.
		double currentTime = Util.getMeasuringTimeMs();

		// "lerp" simply means "linear interpolation", which is a fancy way of saying "blend".
		float lerpedAmount = MathHelper.abs(MathHelper.sin((float) (currentTime / 50.0f)));
		int lerpedColor = ColorHelper.lerp(lerpedAmount, color, targetColor);

		// Draw a square with the lerped color.
		// x1, x2, y1, y2, z, color
		context.fill(0, 0, 100, 100, 0, lerpedColor);
	}
}
// :::1
