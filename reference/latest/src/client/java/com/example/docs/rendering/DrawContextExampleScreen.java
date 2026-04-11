package com.example.docs.rendering;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import com.example.docs.ExampleMod;

public class DrawContextExampleScreen extends Screen {
	public DrawContextExampleScreen() {
		super(Component.empty());
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
		super.extractRenderState(graphics, mouseX, mouseY, delta);

		// #region draw-rectangle
		int rectangleX = 10;
		int rectangleY = 10;
		int rectangleWidth = 100;
		int rectangleHeight = 50;
		// x1, y1, x2, y2, color
		graphics.fill(rectangleX, rectangleY, rectangleX + rectangleWidth, rectangleY + rectangleHeight, 0xFF0000FF);
		// #endregion draw-rectangle

		// #region draw-outline
		// x, y, width, height, color
		graphics.outline(rectangleX, rectangleY, rectangleWidth, rectangleHeight, 0xFFFF0000);
		// #endregion draw-outline

		// #region draw-line
		// Let's split the rectangle in half using a green line.
		// x, y1, y2, color
		graphics.verticalLine(rectangleX + rectangleWidth / 2, rectangleY, rectangleY + rectangleHeight, 0xFF00FF00);
		// #endregion draw-line

		// #region scissor
		// Let's create a scissor region that covers a middle bar section of the screen.
		int scissorRegionX = 200;
		int scissorRegionY = 20;
		int scissorRegionWidth = 100;

		// The height of the scissor region is the height of the screen minus the height of the top and bottom bars.
		int scissorRegionHeight = this.height - 40;

		// x1, y1, x2, y2
		graphics.enableScissor(scissorRegionX, scissorRegionY, scissorRegionX + scissorRegionWidth, scissorRegionY + scissorRegionHeight);

		// Let's fill the entire screen with a color gradient, it should only be visible in the scissor region.
		// x1, y1, x2, y2, color1, color2
		graphics.fillGradient(0, 0, this.width, this.height, 0xFFFF0000, 0xFF0000FF);

		// Disable the scissor region.
		graphics.disableScissor();
		// #endregion scissor

		// #region draw-entire-texture
		Identifier texture = Identifier.fromNamespaceAndPath("minecraft", "textures/block/deepslate.png");
		// renderLayer, texture, x, y, u, v, width, height, textureWidth, textureHeight
		graphics.blit(RenderPipelines.GUI_TEXTURED, texture, 90, 90, 0, 0, 16, 16, 16, 16);
		// #endregion draw-entire-texture

		// #region draw-portion-of-texture
		Identifier texture2 = Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "textures/gui/test-uv-drawing.png");
		int u = 10, v = 13, regionWidth = 14, regionHeight = 14;
		// renderLayer, texture, x, y, u, v, width, height, regionWidth, regionHeight, textureWidth, textureHeight
		graphics.blit(RenderPipelines.GUI_TEXTURED, texture2, 90, 190, u, v, 14, 14, regionWidth, regionHeight, 256, 256);
		// #endregion draw-portion-of-texture

		// #region draw-text
		// Font, text (string, or Component object), x, y, color, shadow
		graphics.text(minecraft.font, "Hello, world!", 10, 200, 0xFFFFFFFF, false);
		// #endregion draw-text
	}
}
