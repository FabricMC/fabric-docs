package com.example.docs.rendering;

import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class HudRenderingEntrypoint implements ClientModInitializer {
	private float totalTickDelta = 0.0F;
	@Override
	public void onInitializeClient() {
		// :::1
		HudRenderCallback.EVENT.register((context, tickDelta) -> {
			int color = 0xFFFF0000; // Red
			int targetColor = 0xFF00FF00; // Green

			// Total tick delta is stored in a field, so we can use it later.
			totalTickDelta += tickDelta;

			// "lerp" simply means "linear interpolation", which is a fancy way of saying "blend".
			float lerpedAmount = MathHelper.abs(MathHelper.sin(totalTickDelta / 50F));
			int lerpedColor = ColorHelper.Argb.lerp(lerpedAmount, color, targetColor);

			// Draw a square with the lerped color.
			// x1, x2, y1, y2, z, color
			context.fill(0, 0, 100, 100, 0, lerpedColor);
		});
		// :::1
	}
}
