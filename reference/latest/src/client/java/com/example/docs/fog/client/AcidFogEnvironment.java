package com.example.docs.fog.client;

import org.jetbrains.annotations.Nullable;

import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.FogType;

import com.example.docs.fog.ExampleModFogTypes;

// TODO: Fabric Fog API
// :::environment
public class AcidFogEnvironment extends FogEnvironment {
	@Override
	public void setupFog(FogData data, Camera camera, ClientLevel level, float renderDistance, DeltaTracker delta) {
		data.environmentalStart = -8.0F;
		data.environmentalEnd = 6.0F;
	}

	@Override
	public boolean isApplicable(@Nullable FogType submersionType, Entity cameraEntity) {
		return submersionType == ExampleModFogTypes.ACID;
	}

	@Override
	public int getBaseColor(ClientLevel level, Camera camera, int viewDistance, float tickDelta) {
		return 0x075800;
	}
}
// :::environment
