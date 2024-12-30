package com.example.docs.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import com.example.docs.rendering.DrawContextExampleScreen;
import com.example.docs.rendering.screens.CustomScreen;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
	protected TitleScreenMixin(Text title) {
		super(title);
	}

	@Inject(method = "init", at = @At("TAIL"), cancellable = false)
	private void addTestWidgets(CallbackInfo ci) {
		this.addDrawableChild(ButtonWidget.builder(Text.of("DrawContext Test"), (btn) -> this.client.setScreen(new DrawContextExampleScreen())).dimensions(5, 5, 60, 20).build());
		this.addDrawableChild(ButtonWidget.builder(Text.of("CustomScreen 1"), (btn) -> this.client.setScreen(new CustomScreen(Text.empty()))).dimensions(5, 5+30, 60, 20).build());
	}
}
