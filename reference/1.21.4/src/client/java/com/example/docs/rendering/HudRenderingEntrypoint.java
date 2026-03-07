package com.example.docs.rendering;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.Util;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import com.example.docs.FabricDocsReference;

// :::1
public class HudRenderingEntrypoint implements ClientModInitializer {
	private static final ResourceLocation EXAMPLE_LAYER = ResourceLocation.fromNamespaceAndPath(FabricDocsReference.MOD_ID, "hud-example-layer");

	@Override
	public void onInitializeClient() {
		// Attach our rendering code to before the chat hud layer. Our layer will render right before the chat. The API will take care of z spacing and automatically add 200 after every layer.
		HudLayerRegistrationCallback.EVENT.register(layeredDrawer -> layeredDrawer.attachLayerBefore(IdentifiedLayer.CHAT, EXAMPLE_LAYER, HudRenderingEntrypoint::render));
	}

	private static void render(GuiGraphics context, DeltaTracker tickCounter) {
		int color = 0xFFFF0000; // Red
		int targetColor = 0xFF00FF00; // Green

		// You can use the Util.getMeasuringTimeMs() function to get the current time in milliseconds.
		// Divide by 1000 to get seconds.
		double currentTime = Util.getMillis() / 1000.0;

		// "lerp" simply means "linear interpolation", which is a fancy way of saying "blend".
		float lerpedAmount = Mth.abs(Mth.sin((float) currentTime));
		int lerpedColor = ARGB.lerp(lerpedAmount, color, targetColor);

		// Draw a square with the lerped color.
		// x1, x2, y1, y2, z, color
		context.fill(0, 0, 10, 10, 0, lerpedColor);
	}
}
// :::1
