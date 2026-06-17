
package com.example.docs.rendering;

import java.util.Optional;
import java.util.OptionalDouble;

import com.mojang.blaze3d.IndexType;
import com.mojang.blaze3d.PrimitiveTopology;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector4f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MappableRingBuffer;
import net.minecraft.client.renderer.RenderPipelines;
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
	private static CustomRenderPipeline instance;
	// #region custom_pipelines_define_pipeline
	private static final RenderPipeline FILLED_THROUGH_WALLS = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.DEBUG_FILLED_SNIPPET)
			.withLocation(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "pipeline/debug_filled_box_through_walls"))
			.withDepthStencilState(Optional.empty())
			.build()
	);
	// #endregion custom_pipelines_define_pipeline
	// #region custom_pipelines_extraction_phase
	private static WaypointRenderState waypointState;

	// #endregion custom_pipelines_extraction_phase
	// #region custom_pipelines_drawing_phase
	private static final ByteBufferBuilder ALLOCATOR = new ByteBufferBuilder(RenderType.SMALL_BUFFER_SIZE);
	private static final Vector4f COLOR_MODULATOR = new Vector4f(1f, 1f, 1f, 1f);
	private static final Vector3f MODEL_OFFSET = new Vector3f();
	private static final Matrix4f TEXTURE_MATRIX = new Matrix4f();
	private BufferBuilder buffer;
	private MappableRingBuffer vertexBuffer;

	// #endregion custom_pipelines_drawing_phase
	public static CustomRenderPipeline getInstance() {
		return instance;
	}

	@Override
	public void onInitializeClient() {
		instance = this;
		LevelExtractionEvents.END_EXTRACTION.register(this::extractWaypoint);
		LevelRenderEvents.AFTER_TRANSLUCENT_TERRAIN.register(this::renderAndDrawWaypoint);
	}

	// #region custom_pipelines_extraction_phase
	private void extractWaypoint(LevelExtractionContext context) {
		// Access data from the world or anything here in the extraction phase.
		// You can only access the (immutable and thread safe) render state in the drawing phase.
		waypointState = new WaypointRenderState(0, 100, 0, 0f, 1f, 0f, 0.5f);
	}

	// #endregion custom_pipelines_extraction_phase
	// #region custom_pipelines_drawing_phase
	private void renderAndDrawWaypoint(LevelRenderContext context) {
		this.renderWaypoint(context);
		this.drawFilledThroughWalls(Minecraft.getInstance(), FILLED_THROUGH_WALLS);
	}

	private void renderWaypoint(LevelRenderContext context) {
		PoseStack matrices = context.poseStack();
		Vec3 camera = context.levelState().cameraRenderState.pos;

		matrices.pushPose();
		matrices.translate(-camera.x, -camera.y, -camera.z);

		if (this.buffer == null) {
			VertexFormat vertexFormat = FILLED_THROUGH_WALLS.getVertexFormatBinding(0);
			if(vertexFormat == null) throw new IllegalStateException("Vertex format is null");
			this.buffer = new BufferBuilder(ALLOCATOR, FILLED_THROUGH_WALLS.getPrimitiveTopology(), vertexFormat);
		}

		this.renderFilledBox(matrices.last().pose(), this.buffer, waypointState.x(), waypointState.y(), waypointState.z(), waypointState.x() + 1, waypointState.y() + 1, waypointState.z() + 1, waypointState.r(), waypointState.g(), waypointState.b(), waypointState.a());

		matrices.popPose();
	}

	private void renderFilledBox(Matrix4fc positionMatrix, BufferBuilder buffer, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, float red, float green, float blue, float alpha) {
		// Front Face
		addFace(positionMatrix, buffer, minX, minY, maxZ, maxX, minY, maxZ, maxX, maxY, maxZ, minX, maxY, maxZ, red, green, blue, alpha);

		// Back face
		addFace(positionMatrix, buffer, maxX, minY, minZ, minX, minY, minZ, minX, maxY, minZ, maxX, maxY, minZ, red, green, blue, alpha);

		// Left face
		addFace(positionMatrix, buffer, minX, minY, minZ, minX, minY, maxZ, minX, maxY, maxZ, minX, maxY, minZ, red, green, blue, alpha);

		// Right face
		addFace(positionMatrix, buffer, maxX, minY, maxZ, maxX, minY, minZ, maxX, maxY, minZ, maxX, maxY, maxZ, red, green, blue, alpha);

		// Top face
		addFace(positionMatrix, buffer, minX, maxY, maxZ, maxX, maxY, maxZ, maxX, maxY, minZ, minX, maxY, minZ, red, green, blue, alpha);

		// Bottom face
		addFace(positionMatrix, buffer, minX, minY, minZ, maxX, minY, minZ, maxX, minY, maxZ, minX, minY, maxZ, red, green, blue, alpha);
	}

	private void addFace(Matrix4fc matrix, BufferBuilder buffer, float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4, float red, float green, float blue, float alpha) {
		buffer.addVertex(matrix, x1, y1, z1).setColor(red, green, blue, alpha);
		buffer.addVertex(matrix, x2, y2, z2).setColor(red, green, blue, alpha);
		buffer.addVertex(matrix, x3, y3, z3).setColor(red, green, blue, alpha);
		buffer.addVertex(matrix, x4, y4, z4).setColor(red, green, blue, alpha);
	}

	private void drawFilledThroughWalls(Minecraft client, @SuppressWarnings("SameParameterValue") RenderPipeline pipeline) {
		// Build the buffer
		MeshData builtBuffer = this.buffer.buildOrThrow();
		MeshData.DrawState drawParameters = builtBuffer.drawState();
		VertexFormat format = drawParameters.format();

		GpuBufferSlice vertices = this.upload(drawParameters, format, builtBuffer).slice();

		draw(client, pipeline, builtBuffer, drawParameters, vertices, format);

		// Rotate the vertex buffer so we are less likely to use buffers that the GPU is using
		this.vertexBuffer.rotate();
		this.buffer = null;
	}

	private GpuBuffer upload(MeshData.DrawState drawParameters, VertexFormat format, MeshData builtBuffer) {
		// Calculate the size needed for the vertex buffer
		int vertexBufferSize = drawParameters.vertexCount() * format.getVertexSize();

		// Initialize or resize the vertex buffer as needed
		if (this.vertexBuffer == null || this.vertexBuffer.size() < vertexBufferSize) {
			if (this.vertexBuffer != null) {
				this.vertexBuffer.close();
			}

			this.vertexBuffer = new MappableRingBuffer(() -> ExampleMod.MOD_ID + " example render pipeline", GpuBuffer.USAGE_VERTEX | GpuBuffer.USAGE_MAP_WRITE, vertexBufferSize);
		}

		// Copy vertex data into the vertex buffer
		CommandEncoder commandEncoder = RenderSystem.getDevice().createCommandEncoder();

		/*
			FIXME: mapBuffer doesnt exists anymore
		try (GpuBufferSlice.MappedView mappedView = commandEncoder.mapBuffer(this.vertexBuffer.currentBuffer().slice(0, builtBuffer.vertexBuffer().remaining()), false, true)) {
			MemoryUtil.memCopy(builtBuffer.vertexBuffer(), mappedView.data());
		}
		*/

		return this.vertexBuffer.currentBuffer();
	}

	private static void draw(Minecraft client, RenderPipeline pipeline, MeshData builtBuffer, MeshData.DrawState drawParameters, GpuBufferSlice vertices, VertexFormat format) {
		GpuBuffer indices;
		IndexType indexType;

		if (pipeline.getPrimitiveTopology() == PrimitiveTopology.QUADS) {
			// Sort the quads if there is translucency
			builtBuffer.sortQuads(ALLOCATOR, RenderSystem.getProjectionType().vertexSorting());
			// Upload the index buffer
			/*
			FIXME: uploadImmediateIndexBuffer doesnt exists anymore
			indices = pipeline.getVertexFormatBinding(0).uploadImmediateIndexBuffer(builtBuffer.indexBuffer());
			*/
			indexType = builtBuffer.drawState().indexType();
		} else {
			// Use the general shape index buffer for non-quad draw modes
			RenderSystem.AutoStorageIndexBuffer shapeIndexBuffer = RenderSystem.getSequentialBuffer(pipeline.getPrimitiveTopology());
			indices = shapeIndexBuffer.getBuffer(drawParameters.indexCount());
			indexType = shapeIndexBuffer.type();
		}

		// Actually execute the draw
		GpuBufferSlice dynamicTransforms = RenderSystem.getDynamicUniforms()
				.writeTransform(RenderSystem.getModelViewMatrixCopy(), COLOR_MODULATOR, MODEL_OFFSET, TEXTURE_MATRIX);
		try (RenderPass renderPass = RenderSystem.getDevice()
				.createCommandEncoder()
				.createRenderPass(() -> ExampleMod.MOD_ID + " example render pipeline rendering", client.gameRenderer.mainRenderTarget().getColorTextureView(), Optional.empty(), client.gameRenderer.mainRenderTarget().getDepthTextureView(), OptionalDouble.empty())) {
			renderPass.setPipeline(pipeline);

			RenderSystem.bindDefaultUniforms(renderPass);
			renderPass.setUniform("DynamicTransforms", dynamicTransforms);

			// Bind texture if applicable:
			// Sampler0 is used for texture inputs in vertices
			// renderPass.bindTexture("Sampler0", textureSetup.texure0(), textureSetup.sampler0());

			renderPass.setVertexBuffer(0, vertices);
			// renderPass.setIndexBuffer(indices, indexType); FIXME: uncomment after indices is fixed

			// The base vertex is the starting index when we copied the data into the vertex buffer divided by vertex size
			renderPass.drawIndexed(drawParameters.indexCount(), 1, 0, 0, 0);
		}

		builtBuffer.close();
	}
	// #endregion custom_pipelines_drawing_phase

	// #region custom_pipelines_clean_up
	public void close() {
		ALLOCATOR.close();

		if (this.vertexBuffer != null) {
			this.vertexBuffer.close();
			this.vertexBuffer = null;
		}
	}
	// #endregion custom_pipelines_clean_up

	// #region custom_pipelines_extraction_phase
	// Render states should be immutable, thread safe, and fast to create.
	private record WaypointRenderState(int x, int y, int z, float r, float g, float b, float a) { }
	// #endregion custom_pipelines_extraction_phase
}
