package com.example.docs.rendering.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import org.jetbrains.annotations.Nullable;

import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;

import com.example.docs.block.entity.custom.CounterBlockEntity;

// #region renderer-structure
public class CounterBlockEntityRenderer implements BlockEntityRenderer<CounterBlockEntity, CounterBlockEntityRenderState> {
	// #endregion renderer-structure

	private final Font font;

	// #region renderer-structure
	public CounterBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
		// #endregion renderer-structure
		font = context.font();
		// #region renderer-structure
	}

	// #region create-render-state
	@Override
	public CounterBlockEntityRenderState createRenderState() {
		return new CounterBlockEntityRenderState();
	}
	// #endregion create-render-state

	// #region extract-render-state
	@Override
	public void extractRenderState(CounterBlockEntity blockEntity, CounterBlockEntityRenderState state, float tickProgress, Vec3 cameraPos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
		// #endregion renderer-structure
		BlockEntityRenderer.super.extractRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);
		state.setClicks(blockEntity.getClicks());
		// #region renderer-structure
	}
	// #endregion extract-render-state

	@Override
	public void submit(CounterBlockEntityRenderState state, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState) {
		// #endregion renderer-structure

		// #region transformations
		matrices.pushPose();
		// #region translate
		matrices.translate(0.5, 1, 0.5);
		// #endregion translate
		// #region rotate
		matrices.mulPose(Axis.XP.rotationDegrees(90));
		// #endregion rotate
		// #region scale
		matrices.scale(1/18f, 1/18f, 1/18f);
		// #endregion scale
		// #endregion transformations

		// #region drawing-text
		String text = String.valueOf(state.getClicks());
		float width = font.width(text);

		// draw the text. params:
		// text, x, y, color, ordered text, shadow, text layer type, light, color, background color, outline color
		queue.submitText(
				matrices,
				-width / 2, -4f,
				Component.literal(text).getVisualOrderText(),
				false,
				Font.DisplayMode.SEE_THROUGH,
				state.lightCoords,
				0xffffffff,
				0,
				0
		);
		// #endregion drawing-text

		matrices.popPose();

		// #region renderer-structure
	}
}
// #endregion renderer-structure
