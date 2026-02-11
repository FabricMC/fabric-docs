package com.example.docs.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.CoreShaders;
import net.minecraft.util.Mth;

import org.joml.Matrix4f;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class RenderingConceptsEntrypoint implements ClientModInitializer {
	public float totalTickDelta = 0F;

	@Override
	public void onInitializeClient() {
		// "A Practical Example: Rendering a Triangle Strip"
		// :::1
		HudRenderCallback.EVENT.register((drawContext, tickDeltaManager) -> {
			// :::1
			if (true) {
				return;
			}

			// :::2
			PoseStack matrices = drawContext.pose();

			// Store the total tick delta in a field, so we can use it later.
			totalTickDelta += tickDeltaManager.getGameTimeDeltaPartialTick(true);

			// Push a new matrix onto the stack.
			matrices.pushPose();
			// :::2
			// :::1
			// Get the transformation matrix from the matrix stack, alongside the tessellator instance and a new buffer builder.
			Matrix4f transformationMatrix = drawContext.pose().last().pose();
			Tesselator tessellator = Tesselator.getInstance();

			// :::1
			// :::2
			// Scale the matrix by 0.5 to make the triangle smaller and larger over time.
			float scaleAmount = Mth.sin(totalTickDelta / 10F) / 2F + 1.5F;

			// Apply the scaling amount to the matrix.
			// We don't need to scale the Z axis since it's on the HUD and 2D.
			matrices.scale(scaleAmount, scaleAmount, 1F);
			// :::2
			matrices.scale(1 / scaleAmount, 1 / scaleAmount, 1F);
			matrices.translate(60f, 60f, 0f);
			// :::3
			// Lerp between 0 and 360 degrees over time.
			float rotationAmount = (float) (totalTickDelta / 50F % 360);
			matrices.mulPose(Axis.ZP.rotation(rotationAmount));
			// Shift entire diamond so that it rotates in its center.
			matrices.translate(-20f, -40f, 0f);
			// :::3

			// :::1
			// Begin a triangle strip buffer using the POSITION_COLOR vertex format.
			BufferBuilder buffer = tessellator.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);

			// Write our vertices, Z doesn't really matter since it's on the HUD.
			buffer.addVertex(transformationMatrix, 20, 20, 5).setColor(0xFF414141);
			buffer.addVertex(transformationMatrix, 5, 40, 5).setColor(0xFF000000);
			buffer.addVertex(transformationMatrix, 35, 40, 5).setColor(0xFF000000);
			buffer.addVertex(transformationMatrix, 20, 60, 5).setColor(0xFF414141);

			// Make sure the correct shader for your chosen vertex format is set!
			// You can find all the shaders in the ShaderProgramKeys class.
			RenderSystem.setShader(CoreShaders.POSITION_COLOR);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

			// Draw the buffer onto the screen.
			BufferUploader.drawWithShader(buffer.buildOrThrow());
			// :::1
			// :::2

			// ... write to the buffer.

			// Pop our matrix from the stack.
			matrices.popPose();
			// :::2
			// :::1
		});
		// :::1
	}
}
