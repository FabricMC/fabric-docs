package com.example.docs.rendering;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

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

		// :::5
		Identifier texture = new Identifier("minecraft", "textures/block/deepslate.png");
		// texture, x, y, u, v, width, height, textureWidth, textureHeight
		context.drawTexture(texture, 90, 90, 0, 0, 16, 16, 16, 16);
		// :::5

		// :::6
		Identifier texture2 = new Identifier("fabric-docs-reference", "textures/gui/test-uv-drawing.png");
		int u = 10, v = 13, regionWidth = 14, regionHeight = 14;
		// texture, x, y, width, height, u, v, regionWidth, regionHeight, textureWidth, textureHeight
		context.drawTexture(texture2, 90, 190, 14, 14, u, v, regionWidth, regionHeight, 256, 256);
		// :::6

		// :::7
		// TextRenderer, text (string, or Text object), x, y, color, shadow
		context.drawText(client.textRenderer, "Hello, world!", 10, 200, 0xFFFFFFFF, false);
		// :::7
	}
}
