package com.example.docs.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.renderpearl.api.commands.RenderPass;
import com.mojang.renderpearl.api.textures.GpuSampler;
import com.mojang.renderpearl.api.textures.GpuTextureView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.chunk.ChunkSectionLayerGroup;
import net.minecraft.client.renderer.chunk.ChunkSectionsToRender;
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
import net.minecraft.client.renderer.state.level.CameraRenderState;

import com.example.docs.rendering.CustomRenderPipeline;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
	@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/feature/FeatureRenderDispatcher;prepareFrame(Lnet/minecraft/client/renderer/SubmitNodeStorage;)Lnet/minecraft/client/renderer/feature/FeatureRenderDispatcher$PreparedFrame;"))
	private FeatureRenderDispatcher.PreparedFrame prepare(FeatureRenderDispatcher instance, SubmitNodeStorage submitNodeStorage, Operation<FeatureRenderDispatcher.PreparedFrame> original, @Local(name = "cameraState", argsOnly = true) CameraRenderState cameraState) {
		FeatureRenderDispatcher.PreparedFrame frame = original.call(instance, submitNodeStorage);
		CustomRenderPipeline.getInstance().prepare(cameraState);
		return frame;
	}

	@WrapOperation(method = "executeClassicTransparency", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/chunk/ChunkSectionsToRender;renderGroup(Lnet/minecraft/client/renderer/chunk/ChunkSectionLayerGroup;Lcom/mojang/renderpearl/api/commands/RenderPass;Lcom/mojang/renderpearl/api/textures/GpuSampler;Lcom/mojang/renderpearl/api/textures/GpuTextureView;Z)V"))
	private void renderTranslucentTerrain(ChunkSectionsToRender instance, ChunkSectionLayerGroup group, RenderPass renderPass, GpuSampler sampler, GpuTextureView atlas, boolean renderWireframeTerrain, Operation<Void> original) {
		original.call(instance, group, renderPass, sampler, atlas, renderWireframeTerrain);
		CustomRenderPipeline.getInstance().draw(renderPass);
	}
}
