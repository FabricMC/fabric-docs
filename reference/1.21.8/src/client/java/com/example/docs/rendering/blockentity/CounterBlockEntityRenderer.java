package com.example.docs.rendering.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec3;

import com.example.docs.block.entity.custom.CounterBlockEntity;

// :::1
public class CounterBlockEntityRenderer implements BlockEntityRenderer<CounterBlockEntity> {
	// :::1

	private final Font textRenderer;

	// :::1
	public CounterBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
		// :::1
		textRenderer = context.getFont();
		// :::1
	}

	@Override
	public void render(CounterBlockEntity entity, float tickProgress, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay, Vec3 cameraPos) {
		// :::1

		// :::2
		matrices.pushPose();
		matrices.translate(0.5, 1, 0.5);
		matrices.mulPose(Axis.XP.rotationDegrees(90));
		matrices.scale(1/18f, 1/18f, 1/18f);
		// :::2

		// :::3
		String text = entity.getClicks() + "";
		float width = textRenderer.width(text);

		// draw the text. params:
		// text, x, y, color, shadow, matrix, vertexConsumers, layerType, backgroundColor, light
		textRenderer.drawInBatch(
				text,
				-width/2, -4f,
				0xffffffff,
				false,
				matrices.last().pose(),
				vertexConsumers,
				Font.DisplayMode.SEE_THROUGH,
				0,
				light
		);
		// :::3

		matrices.popPose();

		// :::1
	}
}
// :::1
