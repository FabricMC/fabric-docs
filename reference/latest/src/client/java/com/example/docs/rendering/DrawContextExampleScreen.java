package com.example.docs.rendering;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class DrawContextExampleScreen extends Screen {
	public DrawContextExampleScreen() {
		super(Text.empty());
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		super.render(context, mouseX, mouseY, delta);

		// :::1
		int rectangleX = 10;
		int rectangleY = 10;
		int rectangleWidth = 100;
		int rectangleHeight = 50;
		// x1, y1, x2, y2, color
		context.fill(rectangleX, rectangleY, rectangleX + rectangleWidth, rectangleY + rectangleHeight, 0xFF0000FF);
		// :::1

		// :::2
		// x, y, width, height, color
		context.drawBorder(rectangleX, rectangleY, rectangleWidth, rectangleHeight, 0xFFFF0000);
		// :::2

		// :::3
		// Let's split the rectangle in half using a green line.
		// x, y1, y2, color
		context.drawVerticalLine(rectangleX + rectangleWidth / 2, rectangleY, rectangleY + rectangleHeight, 0xFF00FF00);
		// :::3

		// :::4
		// Let's create a scissor region that covers a middle bar section of the screen.
		int scissorRegionX = 200;
		int scissorRegionY = 20;
		int scissorRegionWidth = 100;

		// The height of the scissor region is the height of the screen minus the height of the top and bottom bars.
		int scissorRegionHeight = this.height - 40;

		// x1, y1, x2, y2
		context.enableScissor(scissorRegionX, scissorRegionY, scissorRegionX + scissorRegionWidth, scissorRegionY + scissorRegionHeight);

		// Let's fill the entire screen with a color gradient, it should only be visible in the scissor region.
		// x1, y1, x2, y2, color1, color2
		context.fillGradient(0, 0, this.width, this.height, 0xFFFF0000, 0xFF0000FF);

		// Disable the scissor region.
		context.disableScissor();
		// :::4
	}
}
