package com.example.docs.dynamic_registries.screens;

import org.jspecify.annotations.NonNull;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import com.example.docs.dynamic_registries.MagicSkillsRegistryEntry;

public class MagicSkillWidget extends AbstractWidget {
	public MagicSkillWidget(MagicSkillsRegistryEntry skill, int x, int y, int width, int height) {
		super(x, y, width, height, Component.empty());
	}

	@Override
	protected void extractWidgetRenderState(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
	}

	@Override
	protected void updateWidgetNarration(@NonNull NarrationElementOutput output) {
	}
}
