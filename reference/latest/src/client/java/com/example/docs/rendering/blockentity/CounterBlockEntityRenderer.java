package com.example.docs.rendering.blockentity;

import org.jetbrains.annotations.Nullable;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;

import com.example.docs.block.entity.custom.CounterBlockEntity;

// :::1
public class CounterBlockEntityRenderer implements BlockEntityRenderer<CounterBlockEntity, CounterBlockEntityRenderState> {
	// :::1

	private final TextRenderer textRenderer;

	// :::1
	public CounterBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
		// :::1
		textRenderer = context.textRenderer();
		// :::1
	}

	@Override
	public CounterBlockEntityRenderState createRenderState() {
		return new CounterBlockEntityRenderState();
	}

	@Override
	public void updateRenderState(CounterBlockEntity blockEntity, CounterBlockEntityRenderState state, float tickProgress, Vec3d cameraPos, @Nullable ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlay) {
		// :::1
		BlockEntityRenderer.super.updateRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);
		state.setClicks(blockEntity.getClicks());
		// :::1
	}

	@Override
	public void render(CounterBlockEntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
		// :::1

		// :::2
		matrices.push();
		matrices.translate(0.5, 1, 0.5);
		matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
		matrices.scale(1/18f, 1/18f, 1/18f);
		// :::2

		// :::3
		String text = state.getClicks() + "";
		float width = textRenderer.getWidth(text);

		// draw the text. params:
		// text, x, y, color, ordered text, shadow, text layer type, light, color, background color, outline color
		queue.submitText(
				matrices,
				-width / 2, -4f,
				Text.literal(text).asOrderedText(),
				false,
				TextRenderer.TextLayerType.SEE_THROUGH,
				state.lightmapCoordinates,
				0xffffffff,
				0,
				0
		);
		// :::3

		matrices.pop();

		// :::1
	}
}
// :::1
