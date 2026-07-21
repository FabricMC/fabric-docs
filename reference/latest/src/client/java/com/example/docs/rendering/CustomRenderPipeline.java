package com.example.docs.rendering;

import java.util.Optional;
import java.util.OptionalDouble;

import com.mojang.blaze3d.PrimitiveTopology;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector4f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.StagedVertexBuffer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelExtractionContext;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelExtractionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;

import com.example.docs.ExampleMod;

public class CustomRenderPipeline implements ClientModInitializer {
	// #region custom_pipelines_define_pipeline
	private static final RenderPipeline FILLED_THROUGH_WALLS = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.DEBUG_FILLED_SNIPPET)
			.withLocation(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "pipeline/debug_filled_box_through_walls"))
			.withDepthStencilState(Optional.empty())
			.build()
	);
	// #endregion custom_pipelines_define_pipeline
	// #region custom_pipelines_extraction_phase
	private WaypointRenderState waypointState;

	// #endregion custom_pipelines_extraction_phase
	// #region custom_pipelines_drawing_phase
	private static final Vector4f COLOR_MODULATOR = new Vector4f(1f, 1f, 1f, 1f);
	private static final Vector3f MODEL_OFFSET = new Vector3f();
	private static final Matrix4f TEXTURE_MATRIX = new Matrix4f();
	private static final StagedVertexBuffer stagedBuffer = new StagedVertexBuffer(() -> "Waypoints Buffer", RenderType.SMALL_BUFFER_SIZE);

	// #endregion custom_pipelines_drawing_phase

	@Override
	public void onInitializeClient() {
		LevelExtractionEvents.END_EXTRACTION.register(this::extractWaypoint);
		LevelRenderEvents.AFTER_TRANSLUCENT_TERRAIN.register(this::renderAndDrawWaypoint);
	}

	// #region custom_pipelines_extraction_phase
	private void extractWaypoint(LevelExtractionContext context) {
		// Access data from the world or anything here in the extraction phase.
		// You can only access the (immutable and thread safe) render state in the drawing phase.
		this.waypointState = new WaypointRenderState(0, 100, 0, 0f, 1f, 0f, 0.5f);
	}

	// #endregion custom_pipelines_extraction_phase
	// #region custom_pipelines_drawing_phase
	private void renderAndDrawWaypoint(LevelRenderContext context) {
		RenderPipeline renderPipeline = CustomRenderPipeline.FILLED_THROUGH_WALLS;
		VertexFormat formatBinding = renderPipeline.getVertexFormatBinding(0);

		assert formatBinding != null;

		PrimitiveTopology primitive = renderPipeline.getPrimitiveTopology();
		StagedVertexBuffer.Draw draw = stagedBuffer.appendDraw(formatBinding, primitive, primitive == PrimitiveTopology.QUADS ? RenderSystem.getProjectionType().vertexSorting() : null);

		this.renderWaypoint(context, draw);

		stagedBuffer.upload();

		StagedVertexBuffer.ExecuteInfo info = stagedBuffer.getExecuteInfo(draw);

		if (info != null) {
			draw(Minecraft.getInstance(), info, renderPipeline);
		}

		stagedBuffer.endFrame();
	}

	private void renderWaypoint(LevelRenderContext context, StagedVertexBuffer.Draw draw) {
		PoseStack matrices = context.poseStack();
		Vec3 camera = context.levelState().cameraRenderState.pos;

		matrices.pushPose();
		matrices.translate(-camera.x, -camera.y, -camera.z);

		final var builder = stagedBuffer.getVertexBuilder(draw);

		this.renderFilledBox(matrices.last().pose(), builder, this.waypointState.x(), this.waypointState.y(), this.waypointState.z(), this.waypointState.x() + 1, this.waypointState.y() + 1, this.waypointState.z() + 1, this.waypointState.r(), this.waypointState.g(), this.waypointState.b(), this.waypointState.a());

		matrices.popPose();
	}

	private void renderFilledBox(Matrix4fc positionMatrix, VertexConsumer buffer, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, float red, float green, float blue, float alpha) {
		// Front Face
		buffer.addVertex(positionMatrix, minX, minY, maxZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, maxX, minY, maxZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, maxX, maxY, maxZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, minX, maxY, maxZ).setColor(red, green, blue, alpha);

		// Back face
		buffer.addVertex(positionMatrix, maxX, minY, minZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, minX, minY, minZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, minX, maxY, minZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, maxX, maxY, minZ).setColor(red, green, blue, alpha);

		// Left face
		buffer.addVertex(positionMatrix, minX, minY, minZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, minX, minY, maxZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, minX, maxY, maxZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, minX, maxY, minZ).setColor(red, green, blue, alpha);

		// Right face
		buffer.addVertex(positionMatrix, maxX, minY, maxZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, maxX, minY, minZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, maxX, maxY, minZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, maxX, maxY, maxZ).setColor(red, green, blue, alpha);

		// Top face
		buffer.addVertex(positionMatrix, minX, maxY, maxZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, maxX, maxY, maxZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, maxX, maxY, minZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, minX, maxY, minZ).setColor(red, green, blue, alpha);

		// Bottom face
		buffer.addVertex(positionMatrix, minX, minY, minZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, maxX, minY, minZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, maxX, minY, maxZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, minX, minY, maxZ).setColor(red, green, blue, alpha);
	}

	private static void draw(Minecraft client, StagedVertexBuffer.ExecuteInfo info, RenderPipeline pipeline) {
		GpuBufferSlice dynamicTransforms = RenderSystem.getDynamicUniforms()
				.writeTransform(RenderSystem.getModelViewMatrixCopy(), COLOR_MODULATOR, MODEL_OFFSET, TEXTURE_MATRIX);

		RenderTarget mainTarget = client.gameRenderer.mainRenderTarget();
		GpuTextureView colorTexture = mainTarget.getColorTextureView();

		assert colorTexture != null;

		try (RenderPass renderPass = RenderSystem.getDevice()
				.createCommandEncoder()
				.createRenderPass(() -> ExampleMod.MOD_ID + " example render pipeline rendering", colorTexture, Optional.empty(), mainTarget.getDepthTextureView(), OptionalDouble.empty())) {
			renderPass.setPipeline(pipeline);

			RenderSystem.bindDefaultUniforms(renderPass);
			renderPass.setUniform("DynamicTransforms", dynamicTransforms);

			// Bind texture if applicable:
			// Sampler0 is used for texture inputs in vertices
			// renderPass.bindTexture("Sampler0", textureSetup.texure0(), textureSetup.sampler0());

			renderPass.setVertexBuffer(0, info.vertexBuffer().slice());
			renderPass.setIndexBuffer(info.indexBuffer(), info.indexType());

			// The base vertex is the starting index when we copied the data into the vertex buffer divided by vertex size
			renderPass.drawIndexed(info.indexCount(), 1, info.firstIndex(), info.baseVertex(), 0);
		}
	}
	// #endregion custom_pipelines_drawing_phase

	// #region custom_pipelines_clean_up
	public static void close() {
		stagedBuffer.close();
	}
	// #endregion custom_pipelines_clean_up

	// #region custom_pipelines_extraction_phase
	// Render states should be immutable, thread safe, and fast to create.
	private record WaypointRenderState(int x, int y, int z, float r, float g, float b, float a) { }
	// #endregion custom_pipelines_extraction_phase
}
