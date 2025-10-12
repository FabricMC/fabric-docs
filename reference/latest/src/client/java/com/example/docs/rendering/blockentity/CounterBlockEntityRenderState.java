package com.example.docs.rendering.blockentity;

import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;

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
