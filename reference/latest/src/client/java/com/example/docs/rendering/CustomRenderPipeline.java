package com.example.docs.rendering;

import java.util.OptionalDouble;
import java.util.OptionalInt;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MappableRingBuffer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShapeRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import com.example.docs.ExampleMod;

public class CustomRenderPipeline implements ClientModInitializer {
	private static CustomRenderPipeline instance;
	// :::custom-pipelines:define-pipeline
	private static final RenderPipeline FILLED_THROUGH_WALLS = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.DEBUG_FILLED_SNIPPET)
			.withLocation(ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "pipeline/debug_filled_box_through_walls"))
			.withVertexFormat(DefaultVertexFormat.POSITION_COLOR, VertexFormat.DrawMode.TRIANGLE_STRIP)
			.withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
			.build()
	);
	// :::custom-pipelines:define-pipeline
	// :::custom-pipelines:extraction-phase
	private static final ByteBufferBuilder allocator = new ByteBufferBuilder(RenderType.SMALL_BUFFER_SIZE);
	private BufferBuilder buffer;

	// :::custom-pipelines:extraction-phase
	// :::custom-pipelines:drawing-phase
	private static final Vector4f COLOR_MODULATOR = new Vector4f(1f, 1f, 1f, 1f);
	private MappableRingBuffer vertexBuffer;

	// :::custom-pipelines:drawing-phase
	public static CustomRenderPipeline getInstance() {
		return instance;
	}

	@Override
	public void onInitializeClient() {
		instance = this;
		WorldRenderEvents.BEFORE_TRANSLUCENT.register(this::extractAndDrawWaypoint);
	}

	private void extractAndDrawWaypoint(WorldRenderContext context) {
		renderWaypoint(context);
		drawFilledThroughWalls(Minecraft.getInstance(), FILLED_THROUGH_WALLS);
	}

	// :::custom-pipelines:extraction-phase
	private void renderWaypoint(WorldRenderContext context) {
		PoseStack matrices = context.matrices();
		Vec3 camera = context.worldState().cameraRenderState.pos;

		assert matrices != null;
		matrices.pushPose();
		matrices.translate(-camera.x, -camera.y, -camera.z);

		if (buffer == null) {
			buffer = new BufferBuilder(allocator, FILLED_THROUGH_WALLS.getVertexFormatMode(), FILLED_THROUGH_WALLS.getVertexFormat());
		}

		ShapeRenderer.addChainedFilledBoxVertices(matrices, buffer, 0f, 100f, 0f, 1f, 101f, 1f, 0f, 1f, 0f, 0.5f);

		matrices.popPose();
	}
	// :::custom-pipelines:extraction-phase

	// :::custom-pipelines:drawing-phase
	private void drawFilledThroughWalls(Minecraft client, @SuppressWarnings("SameParameterValue") RenderPipeline pipeline) {
		// Build the buffer
		MeshData builtBuffer = buffer.buildOrThrow();
		MeshData.DrawState drawParameters = builtBuffer.drawState();
		VertexFormat format = drawParameters.format();

		GpuBuffer vertices = upload(drawParameters, format, builtBuffer);

		draw(client, pipeline, builtBuffer, drawParameters, vertices, format);

		// Rotate the vertex buffer so we are less likely to use buffers that the GPU is using
		vertexBuffer.rotate();
		buffer = null;
	}

	private GpuBuffer upload(MeshData.DrawState drawParameters, VertexFormat format, MeshData builtBuffer) {
		// Calculate the size needed for the vertex buffer
		int vertexBufferSize = drawParameters.vertexCount() * format.getVertexSize();

		// Initialize or resize the vertex buffer as needed
		if (vertexBuffer == null || vertexBuffer.size() < vertexBufferSize) {
			vertexBuffer = new MappableRingBuffer(() -> ExampleMod.MOD_ID + " example render pipeline", GpuBuffer.USAGE_VERTEX | GpuBuffer.USAGE_MAP_WRITE, vertexBufferSize);
		}

		// Copy vertex data into the vertex buffer
		CommandEncoder commandEncoder = RenderSystem.getDevice().createCommandEncoder();

		try (GpuBuffer.MappedView mappedView = commandEncoder.mapBuffer(vertexBuffer.currentBuffer().slice(0, builtBuffer.vertexBuffer().remaining()), false, true)) {
			MemoryUtil.memCopy(builtBuffer.vertexBuffer(), mappedView.data());
		}

		return vertexBuffer.currentBuffer();
	}

	private static void draw(Minecraft client, RenderPipeline pipeline, MeshData builtBuffer, MeshData.DrawState drawParameters, GpuBuffer vertices, VertexFormat format) {
		GpuBuffer indices;
		VertexFormat.IndexType indexType;

		if (pipeline.getVertexFormatMode() == VertexFormat.DrawMode.QUADS) {
			// Sort the quads if there is translucency
			builtBuffer.sortQuads(allocator, RenderSystem.getProjectionType().getVertexSorter());
			// Upload the index buffer
			indices = pipeline.getVertexFormat().uploadImmediateIndexBuffer(builtBuffer.indexBuffer());
			indexType = builtBuffer.drawState().indexType();
		} else {
			// Use the general shape index buffer for non-quad draw modes
			RenderSystem.AutoStorageIndexBuffer shapeIndexBuffer = RenderSystem.getSequentialBuffer(pipeline.getVertexFormatMode());
			indices = shapeIndexBuffer.getIndexBuffer(drawParameters.indexCount());
			indexType = shapeIndexBuffer.getIndexType();
		}

		// Actually execute the draw
		GpuBufferSlice dynamicTransforms = RenderSystem.getDynamicUniforms()
				.write(RenderSystem.getModelViewMatrix(), COLOR_MODULATOR, new Vector3f(), RenderSystem.getTextureMatrix(), 1f);
		try (RenderPass renderPass = RenderSystem.getDevice()
				.createCommandEncoder()
				.createRenderPass(() -> ExampleMod.MOD_ID + " example render pipeline rendering", client.getMainRenderTarget().getColorTextureView(), OptionalInt.empty(), client.getMainRenderTarget().getDepthTextureView(), OptionalDouble.empty())) {
			renderPass.setPipeline(pipeline);

			RenderSystem.bindDefaultUniforms(renderPass);
			renderPass.setUniform("DynamicTransforms", dynamicTransforms);

			// Bind texture if applicable:
			// Sampler0 is used for texture inputs in vertices
			// renderPass.bindSampler("Sampler0", textureView);

			renderPass.setVertexBuffer(0, vertices);
			renderPass.setIndexBuffer(indices, indexType);

			// The base vertex is the starting index when we copied the data into the vertex buffer divided by vertex size
			//noinspection ConstantValue
			renderPass.drawIndexed(0 / format.getVertexSize(), 0, drawParameters.indexCount(), 1);
		}

		builtBuffer.close();
	}
	// :::custom-pipelines:drawing-phase

	// :::custom-pipelines:clean-up
	public void close() {
		allocator.close();

		if (vertexBuffer != null) {
			vertexBuffer.close();
			vertexBuffer = null;
		}
	}
	// :::custom-pipelines:clean-up
}
