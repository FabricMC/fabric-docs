package com.example.docs.rendering.screens.inventory;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

import com.example.docs.menu.custom.UpgradingMenu;

public class UpgradingScreen extends AbstractContainerScreen<UpgradingMenu> {
	private final Identifier screenTexture = Identifier.withDefaultNamespace("textures/gui/container/anvil.png");

	public UpgradingScreen(UpgradingMenu abstractContainerMenu, Inventory inventory, Component component) {
		super(abstractContainerMenu, inventory, component);
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float delta) {
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, this.screenTexture, this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
	}
}
