package com.example.docs.rendering;

import org.joml.Matrix3x2fStack;

import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;

import com.example.docs.ExampleMod;

public class RenderingConceptsEntrypoint implements ClientModInitializer {
	public float totalTickProgress = 0F;

	@Override
	public void onInitializeClient() {
		// "A Practical Example: Rendering a Triangle Strip"
		// #region registration
		HudElementRegistry.addLast(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "last_element"), hudLayer());
		// #endregion registration
	}

	// #region hud-layer
	private HudElement hudLayer() {
		return (graphics, deltaTracker) -> {
			// #endregion hud-layer

			if (false) {
				return;
			}

			// #region hud-layer
			// #region scaling-square
			Matrix3x2fStack matrices = graphics.pose();

			// Store the total tick delta in a field, so we can use it later.
			totalTickProgress += deltaTracker.getGameTimeDeltaPartialTick(true);

			// Push a new matrix onto the stack.
			matrices.pushMatrix();
			// #endregion scaling-square

			// #region scaling-square
			// Scale the matrix by 0.5 to make the triangle smaller and larger over time.
			float scaleAmount = Mth.sin(totalTickProgress / 10F) / 2F + 1.5F;

			// Apply the scaling amount to the matrix.
			// We don't need to scale the Z axis since it's on the HUD and 2D.
			matrices.scale(scaleAmount, scaleAmount);
			// #endregion scaling-square
			matrices.scale(1 / scaleAmount, 1 / scaleAmount);
			matrices.translate(60f, 60f);
			// #region rotating-square
			// Lerp between 0 and 360 degrees over time.
			float rotationAmount = totalTickProgress / 50F % 360;
			matrices.rotate(rotationAmount);
			// Shift entire square so that it rotates in its center.
			matrices.translate(-20f, -40f);
			// #endregion rotating-square
			// #endregion hud-layer
			graphics.fillGradient(5, 20, 35, 60, 0xFF414141, 0xFF000000);

			// #region hud-layer
			// #region scaling-square
			// We do not need to manually write to the buffer. GuiGraphics methods write to GUI buffer in `GuiRenderer` at the end of preparation.

			// Pop our matrix from the stack.
			matrices.popMatrix();
			// #endregion scaling-square
		};
	}
	// #endregion hud-layer
}
