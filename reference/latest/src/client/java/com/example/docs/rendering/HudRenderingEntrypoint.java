package com.example.docs.rendering;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;

import com.example.docs.ExampleMod;

// :::1
public class HudRenderingEntrypoint implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Attach our rendering code to before the chat hud layer. Our layer will render right before the chat. The API will take care of z spacing.
		HudElementRegistry.attachElementBefore(VanillaHudElements.CHAT, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "before_chat"), HudRenderingEntrypoint::render);
	}

	private static void render(GuiGraphics graphics, DeltaTracker tickCounter) {
		int color = 0xFFFF0000; // Red
		int targetColor = 0xFF00FF00; // Green

		// You can use the Util.getMillis() function to get the current time in milliseconds.
		// Divide by 1000 to get seconds.
		double currentTime = Util.getMillis() / 1000.0;

		// "lerp" simply means "linear interpolation", which is a fancy way of saying "blend".
		float lerpedAmount = Mth.abs(Mth.sin((float) currentTime));
		int lerpedColor = ARGB.linearLerp(lerpedAmount, color, targetColor);

		// Draw a square with the lerped color.
		// x1, x2, y1, y2, color
		graphics.fill(0, 0, 10, 10, lerpedColor);
	}
}

// :::1
