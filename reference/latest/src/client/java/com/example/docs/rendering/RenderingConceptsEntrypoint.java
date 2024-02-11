package com.example.docs.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import org.joml.Matrix4f;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class RenderingConceptsEntrypoint implements ClientModInitializer {
	public float totalTickDelta = 0F;

	@Override
	public void onInitializeClient() {
		// "A Practical Example: Rendering a Triangle Strip"
		// :::1
		HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
			// :::1
			if (true) {
				return;
			}

			// :::2
			MatrixStack matrices = drawContext.getMatrices();

			// Store the total tick delta in a field, so we can use it later.
			totalTickDelta += tickDelta;

			// Push a new matrix onto the stack.
			matrices.push();
			// :::2
			// :::1
			// Get the transformation matrix from the matrix stack, alongside the tessellator instance and a new buffer builder.
			Matrix4f transformationMatrix = drawContext.getMatrices().peek().getPositionMatrix();
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder buffer = tessellator.getBuffer();

			// :::1
			// :::2
			// Scale the matrix by 0.5 to make the triangle smaller and larger over time.
			float scaleAmount = MathHelper.sin(totalTickDelta / 10F) / 2F + 1.5F;

			// Apply the scaling amount to the matrix.
			// We don't need to scale the Z axis since it's on the HUD and 2D.
			matrices.scale(scaleAmount, scaleAmount, 1F);
			// :::2
			matrices.scale(1 / scaleAmount, 1 / scaleAmount, 1F);
			matrices.translate(60f, 60f, 0f);
			// :::3
			// Lerp between 0 and 360 degrees over time.
			float rotationAmount = (float) (totalTickDelta / 50F % 360);
			matrices.multiply(RotationAxis.POSITIVE_Z.rotation(rotationAmount));
			// Shift entire diamond so that it rotates in its center.
			matrices.translate(-20f, -40f, 0f);
			// :::3

			// :::1
			// Initialize the buffer using the specified format and draw mode.
			buffer.begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);

			// Write our vertices, Z doesn't really matter since it's on the HUD.
			buffer.vertex(transformationMatrix, 20, 20, 5).color(0xFF414141).next();
			buffer.vertex(transformationMatrix, 5, 40, 5).color(0xFF000000).next();
			buffer.vertex(transformationMatrix, 35, 40, 5).color(0xFF000000).next();
			buffer.vertex(transformationMatrix, 20, 60, 5).color(0xFF414141).next();

			// We'll get to this bit in the next section.
			RenderSystem.setShader(GameRenderer::getPositionColorProgram);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

			// Draw the buffer onto the screen.
			tessellator.draw();
			// :::1
			// :::2

			// ... write to the buffer.

			// Pop our matrix from the stack.
			matrices.pop();
			// :::2
			// :::1
		});
		// :::1
	}
}
