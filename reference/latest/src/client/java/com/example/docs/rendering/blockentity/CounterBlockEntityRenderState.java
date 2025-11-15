package com.example.docs.rendering.blockentity;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;

// :::render-state
public class CounterBlockEntityRenderState extends BlockEntityRenderState {
	private int clicks = 0;

	public int getClicks() {
		return clicks;
	}

	public void setClicks(int clicks) {
		this.clicks = clicks;
	}
}
// :::render-state
