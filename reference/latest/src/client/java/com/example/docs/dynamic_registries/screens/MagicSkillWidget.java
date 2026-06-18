package com.example.docs.dynamic_registries.screens;

import org.jspecify.annotations.NonNull;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import com.example.docs.dynamic_registries.MagicSkillsRegistryEntry;

public class MagicSkillWidget extends AbstractWidget {
	private final MagicSkillsRegistryEntry skill;
	private final Font font;
	public MagicSkillWidget(MagicSkillsRegistryEntry skill, Font font, int x, int y, int width, int height) {
		this.skill = skill;
		this.font = font;
		System.out.printf("Magic Skills Widget created! %s%n", skill.name());
		super(x, y, width, height, Component.empty());
	}

	@Override
	protected void extractWidgetRenderState(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
		graphics.text(this.font, this.skill.name(), this.getX(), this.getY(), 0xFFFFFFFF, true);
		graphics.text(this.font, "Mana : %d".formatted(this.skill.manaCost()), this.getX(), this.getY() + this.font.lineHeight, 0xFFFFFFFF, true);
	}

	@Override
	protected void updateWidgetNarration(@NonNull NarrationElementOutput output) {
	}
}
