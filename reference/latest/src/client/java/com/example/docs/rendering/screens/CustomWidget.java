package com.example.docs.rendering.screens;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

// :::1
public class CustomWidget extends AbstractWidget {
	public CustomWidget(int x, int y, int width, int height) {
		super(x, y, width, height, Component.empty());
	}

	@Override
	protected void renderWidget(GuiGraphics context, int mouseX, int mouseY, float delta) {
		// We'll just draw a simple rectangle for now.
		// x1, y1, x2, y2, startColor, endColor
		int startColor = 0xFF00FF00; // Green
		int endColor = 0xFF0000FF; // Blue

		// :::1
		// :::2
		// This is in the "renderWidget" method, so we can check if the mouse is hovering over the widget.
		if (isHovered()) {
			startColor = 0xFFFF0000; // Red
			endColor = 0xFF00FFFF; // Cyan
		}

		// :::2
		// :::1
		context.fillGradient(getX(), getY(), getX() + this.width, getY() + this.height, startColor, endColor);
	}

	@Override
	protected void updateWidgetNarration(NarrationElementOutput builder) {
		// For brevity, we'll just skip this for now - if you want to add narration to your widget, you can do so here.
		return;
	}
}
// :::1
