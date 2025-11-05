package com.example.docs.datagen;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.ConsumeItemTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;

import com.example.docs.ExampleMod;
import com.example.docs.advancement.ModCriteria;
import com.example.docs.advancement.ParameterizedUseToolCriterion;
import com.example.docs.advancement.UseToolCriterion;

// :::datagen-advancements:provider-start
public class ExampleModAdvancementProvider extends FabricAdvancementProvider {
	protected ExampleModAdvancementProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
		super(output, registryLookup);
	}

	@Override
	public void generateAdvancement(HolderLookup.Provider wrapperLookup, Consumer<AdvancementHolder> consumer) {
		// :::datagen-advancements:provider-start
		// :::datagen-advancements:simple-advancement
		AdvancementHolder getDirt = Advancement.Builder.advancement()
				.display(
						Items.DIRT, // The display icon
						Component.literal("Your First Dirt Block"), // The title
						Component.literal("Now make a house from it"), // The description
						ResourceLocation.withDefaultNamespace("textures/gui/advancements/backgrounds/adventure.png"), // Background image for the tab in the advancements page, if this is a root advancement (has no parent)
						AdvancementType.TASK, // TASK, CHALLENGE, or GOAL
						true, // Show the toast when completing it
						true, // Announce it to chat
						false // Hide it in the advancement tab until it's achieved
				)
				// "got_dirt" is the name referenced by other advancements when they want to have "requirements."
				.addCriterion("got_dirt", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIRT))
				// Give the advancement an id
				.save(consumer, ExampleMod.MOD_ID + ":get_dirt");
		// :::datagen-advancements:simple-advancement
		// :::datagen-advancements:second-advancement
		final HolderLookup.RegistryLookup<Item> itemLookup = wrapperLookup.lookupOrThrow(Registries.ITEM);
		AdvancementHolder appleAndBeef = Advancement.Builder.advancement()
				.parent(getDirt)
				.display(
						Items.APPLE,
						Component.literal("Apple and Beef"),
						Component.literal("Ate an apple and beef"),
						null, // Children don't need a background, the root advancement takes care of that
						AdvancementType.CHALLENGE,
						true,
						true,
						false
				)
				.addCriterion("ate_apple", ConsumeItemTrigger.TriggerInstance.usedItem(itemLookup, Items.APPLE))
				.addCriterion("ate_cooked_beef", ConsumeItemTrigger.TriggerInstance.usedItem(itemLookup, Items.COOKED_BEEF))
				.save(consumer, ExampleMod.MOD_ID + ":apple_and_beef");
		// :::datagen-advancements:second-advancement
		// :::datagen-advancements:custom-criteria-advancement
		AdvancementHolder breakBlockWithTool = Advancement.Builder.advancement()
				.parent(getDirt)
				.display(
						Items.DIAMOND_SHOVEL,
						Component.literal("Not a Shovel"),
						Component.literal("That's not a shovel (probably)"),
						null,
						AdvancementType.GOAL,
						true,
						true,
						false
				)
				.addCriterion("break_block_with_tool", ModCriteria.USE_TOOL.createCriterion(new UseToolCriterion.Conditions(Optional.empty())))
				.save(consumer, ExampleMod.MOD_ID + ":break_block_with_tool");
		// :::datagen-advancements:custom-criteria-advancement
		// :::datagen-advancements:new-custom-criteria-advancement
		AdvancementHolder breakBlockWithToolFiveTimes = Advancement.Builder.advancement()
				.parent(breakBlockWithTool)
				.display(
						Items.GOLDEN_SHOVEL,
						Component.literal("Not a Shovel Still"),
						Component.literal("That's still not a shovel (probably)"),
						null,
						AdvancementType.GOAL,
						true,
						true,
						false
				)
				.addCriterion("break_block_with_tool_five_times", ModCriteria.PARAMETERIZED_USE_TOOL.createCriterion(new ParameterizedUseToolCriterion.Conditions(Optional.empty(), 5)))
				.save(consumer, ExampleMod.MOD_ID + ":break_block_with_tool_five_times");
		// :::datagen-advancements:new-custom-criteria-advancement
		// :::datagen-advancements:provider-start
	}
}
// :::datagen-advancements:provider-start
