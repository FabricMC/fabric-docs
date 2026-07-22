package com.example.docs.dynamic_registries.screens;

import java.util.Optional;

import org.jspecify.annotations.NonNull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;

import com.example.docs.dynamic_registries.ExampleModRegistries;
import com.example.docs.dynamic_registries.MagicSkillsRegistryEntry;

public class ExampleModMagicSkillsScreen extends Screen {
	RegistryAccess registryAccess;
	public ExampleModMagicSkillsScreen(Minecraft minecraft, RegistryAccess registryAccess) {
		super(minecraft, minecraft.font, Component.literal("Magic Skills"));
		this.registryAccess = registryAccess;
	}

	@Override
	protected void init() {
		Optional<Registry<MagicSkillsRegistryEntry>> registry = this.registryAccess.lookup(ExampleModRegistries.MAGIC_SKILLS_SYNCED_REGISTRY_KEY);

		// #region iterate_over_registry_entries
		registry.ifPresent(reg -> {
			int y = 50;

			for (MagicSkillsRegistryEntry skill : reg) {
				MagicSkillWidget widget = new MagicSkillWidget(skill, font, 40, y, 80, 20);
				this.addRenderableWidget(widget);
				y += 30;
			}
		});
		// #endregion iterate_over_registry_entries
	}

	@Override
	public void extractRenderState(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
		super.extractRenderState(graphics, mouseX, mouseY, a);
		graphics.text(this.font, "Magic Skills", 40, 40 - this.font.lineHeight - 10, 0xFFFFFFFF, true);
	}
}
