package com.example.docs.datagen;

import com.example.docs.FabricDocsReference;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.Optional;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.ConsumeItemCriterion;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;

import com.example.docs.advancement.ModCriteria;
import com.example.docs.advancement.ParameterizedUseToolCriterion;
import com.example.docs.advancement.UseToolCriterion;

// :::datagen-advancements:provider-start
public class FabricDocsReferenceAdvancementProvider extends FabricAdvancementProvider {
	protected FabricDocsReferenceAdvancementProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
		super(output, registryLookup);
	}

	@Override
	public void generateAdvancement(RegistryWrapper.WrapperLookup wrapperLookup, Consumer<AdvancementEntry> consumer) {
		// :::datagen-advancements:provider-start
		// :::datagen-advancements:simple-advancement
		AdvancementEntry getDirt = Advancement.Builder.create()
				.display(
						Items.DIRT, // The display icon
						Text.literal("Your First Dirt Block"), // The title
						Text.literal("Now make a house from it"), // The description
						Identifier.ofVanilla("textures/gui/advancements/backgrounds/adventure.png"), // Background image for the tab in the advancements page, if this is a root advancement (has no parent)
						AdvancementFrame.TASK, // TASK, CHALLENGE, or GOAL
						true, // Show the toast when completing it
						true, // Announce it to chat
						false // Hide it in the advancement tab until it's achieved
				)
				// "got_dirt" is the name referenced by other advancements when they want to have "requirements."
				.criterion("got_dirt", InventoryChangedCriterion.Conditions.items(Items.DIRT))
				// Give the advancement an id
				.build(consumer, FabricDocsReference.MOD_ID + "/get_dirt");
		// :::datagen-advancements:simple-advancement
		// :::datagen-advancements:second-advancement
		AdvancementEntry appleAndBeef = Advancement.Builder.create()
				.parent(getDirt)
				.display(
						Items.APPLE,
						Text.literal("Apple and Beef"),
						Text.literal("Ate an apple and beef"),
						null, // Children don't need a background, the root advancement takes care of that
						AdvancementFrame.CHALLENGE,
						true,
						true,
						false
				)
				.criterion("ate_apple", ConsumeItemCriterion.Conditions.item(Items.APPLE))
				.criterion("ate_cooked_beef", ConsumeItemCriterion.Conditions.item(Items.COOKED_BEEF))
				.build(consumer, FabricDocsReference.MOD_ID + "/apple_and_beef");
		// :::datagen-advancements:second-advancement
		// :::datagen-advancements:custom-criteria-advancement
		AdvancementEntry breakBlockWithTool = Advancement.Builder.create()
				.parent(getDirt)
				.display(
						Items.DIAMOND_SHOVEL,
						Text.literal("Not a Shovel"),
						Text.literal("That's not a shovel (probably)"),
						null,
						AdvancementFrame.GOAL,
						true,
						true,
						false
				)
				.criterion("break_block_with_tool", ModCriteria.USE_TOOL.create(new UseToolCriterion.Conditions(Optional.empty())))
				.build(consumer, FabricDocsReference.MOD_ID + "/break_block_with_tool");
		// :::datagen-advancements:custom-criteria-advancement
		// :::datagen-advancements:new-custom-criteria-advancement
		AdvancementEntry breakBlockWithToolFiveTimes = Advancement.Builder.create()
				.parent(breakBlockWithTool)
				.display(
						Items.GOLDEN_SHOVEL,
						Text.literal("Not a Shovel Still"),
						Text.literal("That's still not a shovel (probably)"),
						null,
						AdvancementFrame.GOAL,
						true,
						true,
						false
				)
				.criterion("break_block_with_tool_five_times", ModCriteria.PARAMETERIZED_USE_TOOL.create(new ParameterizedUseToolCriterion.Conditions(Optional.empty(), 5)))
				.build(consumer, FabricDocsReference.MOD_ID + "/break_block_with_tool_five_times");
		// :::datagen-advancements:new-custom-criteria-advancement
		// :::datagen-advancements:provider-start
	}
}
// :::datagen-advancements:provider-start
