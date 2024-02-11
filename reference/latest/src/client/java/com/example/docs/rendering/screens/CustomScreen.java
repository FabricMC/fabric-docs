package com.example.docs.rendering.screens;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;

// :::1
public class CustomScreen extends Screen {
	// :::1
	// :::2
	public Screen parent;
	public CustomScreen(Text title, Screen parent) {
		super(title);
		this.parent = parent;
	}

	@Override
	public void close() {
		this.client.setScreen(this.parent);
	}

	// :::2
	public CustomScreen(Text title) {
		super(title);
	}

	@Override
	protected void init() {
		ButtonWidget buttonWidget = ButtonWidget.builder(Text.of("Hello World"), (btn) -> {
			// When the button is clicked, we can display a toast to the screen.
			this.client.getToastManager().add(
					SystemToast.create(this.client, SystemToast.Type.NARRATOR_TOGGLE, Text.of("Hello World!"), Text.of("This is a toast."))
			);
		}).dimensions(40, 40, 120, 20).build();
		// x, y, width, height
		// It's recommended to use the fixed height of 20 to prevent rendering issues with the button
		// textures.

		// Register the button widget.
		this.addDrawableChild(buttonWidget);

		// :::1
		// :::3
		// Add a custom widget to the screen.
		// x, y, width, height
		CustomWidget customWidget = new CustomWidget(40, 80, 120, 20);
		this.addDrawableChild(customWidget);
		// :::3
		// :::1
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		super.render(context, mouseX, mouseY, delta);

		// Minecraft doesn't have a "label" widget, so we'll have to draw our own text.
		// We'll subtract the font height from the Y position to make the text appear above the button.
		// Subtracting an extra 10 pixels will give the text some padding.
		// textRenderer, text, x, y, color, hasShadow
		context.drawText(this.textRenderer, "Special Button", 40, 40 - this.textRenderer.fontHeight - 10, 0xFFFFFFFF, true);
	}
}
// :::1
