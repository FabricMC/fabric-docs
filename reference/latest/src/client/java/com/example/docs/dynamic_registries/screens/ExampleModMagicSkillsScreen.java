package com.example.docs.dynamic_registries.screens;

import java.util.Optional;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;

import com.example.docs.dynamic_registries.ExampleModRegistries;
import com.example.docs.dynamic_registries.MagicSkillsRegistryEntry;

public class ExampleModMagicSkillsScreen extends Screen {
	public ExampleModMagicSkillsScreen(Minecraft minecraft, Font font, Component title) {
		super(minecraft, font, title);
	}

	@Override
	protected void init() {
		assert minecraft.level != null;
		Optional<Registry<MagicSkillsRegistryEntry>> registry = minecraft.level.registryAccess().lookup(ExampleModRegistries.MAGIC_SKILLS_SYNCED_REGISTRY_KEY);

		// #region iterate_over_registry_entries
		registry.ifPresent(reg -> {
			int x = 0, y = 0;

			for (MagicSkillsRegistryEntry skill : reg) {
				MagicSkillWidget widget = new MagicSkillWidget(skill, x, y, 40, 60);
				this.addRenderableWidget(widget);
				x += 40;
				y += 60;
			}
		});
		// #endregion iterate_over_registry_entries
	}
}
