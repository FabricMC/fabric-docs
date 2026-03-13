package com.example.docs.rendering;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;

public class HudRenderingEntrypoint implements ClientModInitializer {
	private float totalTickDelta = 0.0F;
	@Override
	public void onInitializeClient() {
		// :::1
		HudRenderCallback.EVENT.register((context, tickDeltaManager) -> {
			int color = 0xFFFF0000; // Red
			int targetColor = 0xFF00FF00; // Green

			// Total tick delta is stored in a field, so we can use it later.
			totalTickDelta += tickDeltaManager.getGameTimeDeltaPartialTick(true);

			// "lerp" simply means "linear interpolation", which is a fancy way of saying "blend".
			float lerpedAmount = Mth.abs(Mth.sin(totalTickDelta / 50F));
			int lerpedColor = FastColor.ARGB32.lerp(lerpedAmount, color, targetColor);

			// Draw a square with the lerped color.
			// x1, x2, y1, y2, z, color
			context.fill(0, 0, 100, 100, 0, lerpedColor);
		});
		// :::1
	}
}
