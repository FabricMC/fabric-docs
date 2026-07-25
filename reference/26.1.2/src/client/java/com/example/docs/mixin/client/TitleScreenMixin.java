package com.example.docs.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;

import com.example.docs.rendering.DrawContextExampleScreen;
import com.example.docs.rendering.screens.CustomScreen;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
	protected TitleScreenMixin(Component title) {
		super(title);
	}

	@Inject(method = "init", at = @At("TAIL"), cancellable = false)
	private void addTestWidgets(CallbackInfo ci) {
		this.addRenderableWidget(Button.builder(Component.nullToEmpty("DrawContext Test"), (btn) -> this.minecraft.setScreen(new DrawContextExampleScreen())).bounds(5, 5, 60, 20).build());
		this.addRenderableWidget(Button.builder(Component.nullToEmpty("CustomScreen 1"), (btn) -> this.minecraft.setScreen(new CustomScreen(Component.empty()))).bounds(5, 5+30, 60, 20).build());
	}
}
