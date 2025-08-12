package com.example.docs.rendering;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;

import com.example.docs.FabricDocsReference;

// :::1
public class HudRenderingEntrypoint implements ClientModInitializer {
	private static final Identifier EXAMPLE_LAYER = Identifier.of(FabricDocsReference.MOD_ID, "hud-example-layer");

	@Override
	public void onInitializeClient() {
		// Attach our rendering code to before the chat hud layer. Our layer will render right before the chat. The API will take care of z spacing.
		HudElementRegistry.attachElementBefore(VanillaHudElements.CHAT, Identifier.of(FabricDocsReference.MOD_ID, "before_chat"), HudRenderingEntrypoint::render);
	}

	private static void render(DrawContext context, RenderTickCounter tickCounter) {
		int color = 0xFFFF0000; // Red
		int targetColor = 0xFF00FF00; // Green

		// You can use the Util.getMeasuringTimeMs() function to get the current time in milliseconds.
		// Divide by 1000 to get seconds.
		double currentTime = Util.getMeasuringTimeMs() / 1000.0;

		// "lerp" simply means "linear interpolation", which is a fancy way of saying "blend".
		float lerpedAmount = MathHelper.abs(MathHelper.sin((float) currentTime));
		int lerpedColor = ColorHelper.lerp(lerpedAmount, color, targetColor);

		// Draw a square with the lerped color.
		// x1, x2, y1, y2, color
		context.fill(0, 0, 10, 10, lerpedColor);
	}
}
// :::1
