package com.example.docs.rendering;

import net.minecraft.util.Util;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class HudRenderingEntrypoint implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// :::1
		HudRenderCallback.EVENT.register((context, renderTickCounter) -> {
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
		});
		// :::1
	}
}
