package com.example.docs.rendering;

import org.joml.Matrix3x2fStack;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;

import com.example.docs.FabricDocsReference;

public class RenderingConceptsEntrypoint implements ClientModInitializer {
	public float totalTickProgress = 0F;

	@Override
	public void onInitializeClient() {
		// "A Practical Example: Rendering a Triangle Strip"
		// :::1
		HudElementRegistry.addLast(Identifier.of(FabricDocsReference.MOD_ID, "last_element"), hudLayer());
		// :::1
	}

	private HudElement hudLayer() {
		return (drawContext, tickCounter) -> {
			// :::1
			if (false) {
				return;
			}

			// :::2
			Matrix3x2fStack matrices = drawContext.getMatrices();

			// Store the total tick delta in a field, so we can use it later.
			totalTickProgress += tickCounter.getTickProgress(true);

			// Push a new matrix onto the stack.
			matrices.pushMatrix();
			// :::2

			// :::2
			// Scale the matrix by 0.5 to make the triangle smaller and larger over time.
			float scaleAmount = MathHelper.sin(totalTickProgress / 10F) / 2F + 1.5F;

			// Apply the scaling amount to the matrix.
			// We don't need to scale the Z axis since it's on the HUD and 2D.
			matrices.scale(scaleAmount, scaleAmount);
			// :::2
			matrices.scale(1 / scaleAmount, 1 / scaleAmount);
			matrices.translate(60f, 60f);
			// :::3
			// Lerp between 0 and 360 degrees over time.
			float rotationAmount = totalTickProgress / 50F % 360;
			matrices.rotate(rotationAmount);
			// Shift entire square so that it rotates in its center.
			matrices.translate(-20f, -40f);
			// :::3

			// :::1
			drawContext.fillGradient(5, 20, 35, 60, 0xFF414141, 0xFF000000);
			// :::1
			// :::2

			// We do not need to manually write to the buffer. DrawContext methods write to GUI buffer in `GuiRenderer` at the end of preparation.

			// Pop our matrix from the stack.
			matrices.popMatrix();
			// :::2
			// :::1
		};
	}
}
