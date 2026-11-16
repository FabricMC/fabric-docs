package com.example.docs.rendering;

import java.util.Optional;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.renderpearl.api.buffers.GpuBufferSlice;
import com.mojang.renderpearl.api.commands.RenderPass;
import com.mojang.renderpearl.api.pipeline.PrimitiveTopology;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import com.mojang.renderpearl.api.vertex.VertexFormat;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector4f;

import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.StagedVertexBuffer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.level.CameraRenderState;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelExtractionContext;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelExtractionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;

import com.example.docs.ExampleMod;

public class CustomRenderPipeline implements ClientModInitializer {
	private static CustomRenderPipeline instance;
	// #region custom_pipelines_define_pipeline
	private static final RenderPipeline FILLED_THROUGH_WALLS = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.DEBUG_FILLED_SNIPPET)
			.withLocation(ExampleMod.id("pipeline/debug_filled_box_through_walls"))
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
	private static final StagedVertexBuffer STAGED_BUFFER = new StagedVertexBuffer(() -> "Waypoints Buffer", RenderType.SMALL_BUFFER_SIZE);
	private StagedVertexBuffer.Draw draw;

	// #endregion custom_pipelines_drawing_phase

	public static CustomRenderPipeline getInstance() {
		return instance;
	}

	@Override
	public void onInitializeClient() {
		instance = this;
		LevelExtractionEvents.END_EXTRACTION.register(this::extractWaypoint);
		LevelRenderEvents.END_MAIN.register(_ -> STAGED_BUFFER.endFrame());
	}

	// #region custom_pipelines_extraction_phase
	private void extractWaypoint(LevelExtractionContext context) {
		// Access data from the world or anything here in the extraction phase.
		// You can only access the (immutable and thread safe) render state in the drawing phase.
		this.waypointState = new WaypointRenderState(0, 100, 0, 0f, 1f, 0f, 0.5f);
	}

	// #endregion custom_pipelines_extraction_phase
	// #region custom_pipelines_drawing_phase
	public void prepare(CameraRenderState camera) {
		RenderPipeline renderPipeline = CustomRenderPipeline.FILLED_THROUGH_WALLS;
		VertexFormat formatBinding = renderPipeline.getVertexFormatBinding(0);

		assert formatBinding != null;

		PrimitiveTopology primitive = renderPipeline.getPrimitiveTopology();
		StagedVertexBuffer.Draw draw = STAGED_BUFFER.appendDraw(formatBinding, primitive, primitive == PrimitiveTopology.QUADS ? RenderSystem.getProjectionType().vertexSorting() : null);

		this.renderWaypoint(camera, draw);

		STAGED_BUFFER.upload();

		this.draw = draw;
	}

	private void renderWaypoint(CameraRenderState camera, StagedVertexBuffer.Draw draw) {
		Matrix4f matrix = new Matrix4f();
		matrix.translate(-(float) camera.pos.x, -(float) camera.pos.y, -(float) camera.pos.z);

		final VertexConsumer builder = STAGED_BUFFER.getVertexBuilder(draw);

		this.renderFilledBox(matrix, builder, this.waypointState.x(), this.waypointState.y(), this.waypointState.z(), this.waypointState.x() + 1, this.waypointState.y() + 1, this.waypointState.z() + 1, this.waypointState.r(), this.waypointState.g(), this.waypointState.b(), this.waypointState.a());
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

	public void draw(RenderPass renderPass) {
		if (this.draw == null) {
			return;
		}

		StagedVertexBuffer.ExecuteInfo info = STAGED_BUFFER.getExecuteInfo(this.draw);

		if (info == null) {
			return;
		}

		GpuBufferSlice dynamicTransforms = RenderSystem.getDynamicUniforms()
				.writeTransform(RenderSystem.getModelViewMatrixCopy(), COLOR_MODULATOR, MODEL_OFFSET, TEXTURE_MATRIX);

		renderPass.pushDebugGroup(() -> "Fabric Docs Example Pipeline Rendering");
		renderPass.setPipeline(RenderSystem.getCompiledPipeline(FILLED_THROUGH_WALLS));
		RenderSystem.bindDefaultUniforms(renderPass);
		renderPass.setUniform("DynamicTransforms", dynamicTransforms);
		// Bind texture if applicable:
		// Sampler0 is used for texture inputs in vertices
		// renderPass.setUniform("Sampler0", draw.textureSetup.texure0(), draw.textureSetup.sampler0());
		renderPass.setVertexBuffer(0, info.vertexBuffer().slice());
		renderPass.setIndexBuffer(info.indexBuffer(), info.indexType());
		// The base vertex is the starting index when we copied the data into the vertex buffer divided by vertex size
		renderPass.drawIndexed(info.indexCount(), 1, info.firstIndex(), info.baseVertex(), 0);
		renderPass.popDebugGroup();
	}
	// #endregion custom_pipelines_drawing_phase

	// #region custom_pipelines_clean_up
	public static void close() {
		STAGED_BUFFER.close();
	}
	// #endregion custom_pipelines_clean_up

	// #region custom_pipelines_extraction_phase
	// Render states should be immutable, thread safe, and fast to create.
	private record WaypointRenderState(int x, int y, int z, float r, float g, float b, float a) { }
	// #endregion custom_pipelines_extraction_phase
}
