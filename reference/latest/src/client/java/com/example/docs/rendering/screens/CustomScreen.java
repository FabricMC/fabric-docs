package com.example.docs.rendering.screens;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

// #region screen
public class CustomScreen extends Screen {
	// #endregion screen
	// #region return-to-previous-screen
	public Screen parent;
	public CustomScreen(Component title, Screen parent) {
		super(title);
		this.parent = parent;
	}

	@Override
	public void onClose() {
		this.minecraft.setScreen(this.parent);
	}

	// #endregion return-to-previous-screen
	// #region screen
	public CustomScreen(Component title) {
		super(title);
	}

	@Override
	protected void init() {
		Button buttonWidget = Button.builder(Component.literal("Hello World"), (btn) -> {
			// When the button is clicked, we can display a toast to the screen.
			this.minecraft.getToastManager().addToast(
					SystemToast.multiline(this.minecraft, SystemToast.SystemToastId.NARRATOR_TOGGLE, Component.nullToEmpty("Hello World!"), Component.nullToEmpty("This is a toast."))
			);
		}).bounds(40, 40, 120, 20).build();
		// x, y, width, height
		// It's recommended to use the fixed height of 20 to prevent rendering issues with the button
		// textures.

		// Register the button widget.
		this.addRenderableWidget(buttonWidget);

		// #endregion screen
		// #region add-custom-widget
		// Add a custom widget to the screen.
		// x, y, width, height
		CustomWidget customWidget = new CustomWidget(40, 80, 120, 20);
		this.addRenderableWidget(customWidget);
		// #endregion add-custom-widget
		// #region screen
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
		super.extractRenderState(graphics, mouseX, mouseY, delta);

		// Minecraft doesn't have a "label" widget, so we'll have to draw our own text.
		// We'll subtract the font height from the Y position to make the text appear above the button.
		// Subtracting an extra 10 pixels will give the text some padding.
		// font, text, x, y, color, hasShadow
		graphics.text(this.font, "Special Button", 40, 40 - this.font.lineHeight - 10, 0xFFFFFFFF, true);
	}
}
// #endregion screen
