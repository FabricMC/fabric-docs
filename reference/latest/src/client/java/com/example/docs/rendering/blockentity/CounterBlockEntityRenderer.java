package com.example.docs.rendering.blockentity;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;

import com.example.docs.block.entity.custom.CounterBlockEntity;

// :::1
public class CounterBlockEntityRenderer implements BlockEntityRenderer<CounterBlockEntity> {
	// :::1

	private final TextRenderer textRenderer;

	// :::1
	public CounterBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
		// :::1
		textRenderer = context.getTextRenderer();
		// :::1
	}

	@Override
	public void render(CounterBlockEntity entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
		// :::1

		// :::2
		matrices.push();
		matrices.translate(0.5, 1, 0.5);
		matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
		matrices.scale(1/18f, 1/18f, 1/18f);
		// :::2

		// :::3
		String text = entity.getClicks() + "";
		float width = textRenderer.getWidth(text);

		// draw the text. params:
		// text, x, y, color, shadow, matrix, vertexConsumers, layerType, backgroundColor, light
		textRenderer.draw(
				text,
				-width/2, -4f,
				0xffffff,
				false,
				matrices.peek().getPositionMatrix(),
				vertexConsumers,
				TextRenderer.TextLayerType.SEE_THROUGH,
				0,
				light
		);
		// :::3

		matrices.pop();

		// :::1
	}
}
// :::1
