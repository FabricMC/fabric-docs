package com.example.docs.rendering.screens;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

// :::1
public class CustomScreen extends Screen {
	// :::1
	// :::2
	public Screen parent;
	public CustomScreen(Component title, Screen parent) {
		super(title);
		this.parent = parent;
	}

	@Override
	public void onClose() {
		this.minecraft.setScreen(this.parent);
	}

	// :::2
	// :::1
	public CustomScreen(Component title) {
		super(title);
	}

	@Override
	protected void init() {
		Button buttonWidget = Button.builder(Component.nullToEmpty("Hello World"), (btn) -> {
			// When the button is clicked, we can display a toast to the screen.
			this.minecraft.getToasts().addToast(
					SystemToast.multiline(this.minecraft, SystemToast.SystemToastId.NARRATOR_TOGGLE, Component.nullToEmpty("Hello World!"), Component.nullToEmpty("This is a toast."))
			);
		}).bounds(40, 40, 120, 20).build();
		// x, y, width, height
		// It's recommended to use the fixed height of 20 to prevent rendering issues with the button
		// textures.

		// Register the button widget.
		this.addRenderableWidget(buttonWidget);

		// :::1
		// :::3
		// Add a custom widget to the screen.
		// x, y, width, height
		CustomWidget customWidget = new CustomWidget(40, 80, 120, 20);
		this.addRenderableWidget(customWidget);
		// :::3
		// :::1
	}

	@Override
	public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
		super.render(context, mouseX, mouseY, delta);

		// Minecraft doesn't have a "label" widget, so we'll have to draw our own text.
		// We'll subtract the font height from the Y position to make the text appear above the button.
		// Subtracting an extra 10 pixels will give the text some padding.
		// textRenderer, text, x, y, color, hasShadow
		context.drawString(this.font, "Special Button", 40, 40 - this.font.lineHeight - 10, 0xFFFFFFFF, true);
	}
}
// :::1
