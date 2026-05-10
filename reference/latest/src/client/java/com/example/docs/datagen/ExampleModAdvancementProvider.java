package com.example.docs.datagen;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.criterion.BrewedPotionTrigger;
import net.minecraft.advancements.criterion.ConsumeItemTrigger;
import net.minecraft.advancements.criterion.DataComponentMatchers;
import net.minecraft.advancements.criterion.EnchantedItemTrigger;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.ItemUsedOnLocationTrigger;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.predicates.DataComponentPredicates;
import net.minecraft.core.component.predicates.EnchantmentsPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Blocks;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;

import com.example.docs.ExampleMod;
import com.example.docs.ModLootTables;
import com.example.docs.advancement.ModCriteria;
import com.example.docs.advancement.ParameterizedUseToolCriterion;
import com.example.docs.advancement.UseToolCriterion;
import com.example.docs.enchantment.ModEnchantments;

// :::datagen-advancements:provider-start
public class ExampleModAdvancementProvider extends FabricAdvancementProvider {
	protected ExampleModAdvancementProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
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
						Identifier.withDefaultNamespace("gui/advancements/backgrounds/adventure"), // Background image for the tab in the advancements page, if this is a root advancement (has no parent)
						AdvancementType.TASK, // TASK, CHALLENGE, or GOAL
						true, // Show the toast when completing it
						true, // Announce it to chat
						false // Hide it in the advancement tab until it's achieved
				)
				// "got_dirt" is the name referenced by other advancements when they want to have "requirements."
				.addCriterion("got_dirt", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIRT))
				// Give the advancement an id
				.save(consumer, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "get_dirt"));
		// :::datagen-advancements:simple-advancement
		final HolderLookup.RegistryLookup<Item> itemLookup = wrapperLookup.lookupOrThrow(Registries.ITEM);
		// #region multiple-criteria
		Advancement.Builder.advancement()
				.addCriterion("ate_apple", ConsumeItemTrigger.TriggerInstance.usedItem(itemLookup, Items.APPLE))
				.addCriterion("ate_cooked_beef", ConsumeItemTrigger.TriggerInstance.usedItem(itemLookup, Items.COOKED_BEEF))
				// ...
				// #endregion multiple-criteria
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
				.save(consumer, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "apple_and_beef"));
		// #region requirements-strategy
		Advancement.Builder.advancement()
				.addCriterion("brew_mundane", CriteriaTriggers.BREWED_POTION.createCriterion(
								new BrewedPotionTrigger.TriggerInstance(Optional.empty(), Optional.of(Potions.MUNDANE))
				))
				.addCriterion("brew_thick", CriteriaTriggers.BREWED_POTION.createCriterion(
								new BrewedPotionTrigger.TriggerInstance(Optional.empty(), Optional.of(Potions.THICK))
				))
				.requirements(AdvancementRequirements.Strategy.OR)
				// ...
				// #endregion requirements-strategy
				.parent(createPlaceholder(Identifier.withDefaultNamespace("nether/brew_potion")))
				.display(
						Items.POTION,
						Component.literal("Brewing Fail"),
						Component.literal("Brew a useless potion"),
						null,
						AdvancementType.TASK,
						true,
						false,
						false
				)
				.save(consumer, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "brewing_fail"));
		// #region experience-reward
		Advancement.Builder.advancement()
				.rewards(AdvancementRewards.Builder.experience(10))
				// ...
				// #endregion experience-reward
				.parent(createPlaceholder(Identifier.withDefaultNamespace("adventure/root")))
				.display(
						Items.GOLD_BLOCK,
						Component.literal("Too Much Gold!"),
						Component.literal("Collect a gold block"),
						null,
						AdvancementType.GOAL,
						true,
						false,
						false
				)
				.addCriterion("get_gold", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIAMOND))
				.save(consumer, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "collect_gold"));

		// #region reward-types
		Advancement.Builder.advancement()
				.rewards(
						AdvancementRewards.Builder.loot(ModLootTables.ADVANCEMENT_COLLECT_NETHER_STAR) // Give entries from a loot table
								.addRecipe(RecipeBuilder.getDefaultRecipeId(new ItemStackTemplate(Items.BEACON))) // Make recipes available in the recipe book
								.runs(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "got_nether_star")) // Run a .mcfunction
								.addExperience(200) // Give experience points
				)
				// ...
				// #endregion reward-types
				.parent(createPlaceholder(Identifier.withDefaultNamespace("adventure/root")))
				.display(
						Items.NETHER_STAR,
						Component.literal("Celestial body"),
						Component.literal("Get a nether star"),
						null,
						AdvancementType.GOAL,
						true,
						false,
						false
				)
				.addCriterion("get_star", InventoryChangeTrigger.TriggerInstance.hasItems(Items.NETHER_STAR))
				.save(consumer, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "collect_nether_star"));

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
				.save(consumer, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "break_block_with_tool"));
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
				.save(consumer, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "break_block_with_tool_five_times"));
		// :::datagen-advancements:new-custom-criteria-advancement
		// #region reference-parent
		Advancement.Builder.advancement()
						.parent(getDirt)
						// ...
						// #endregion reference-parent
						.display(
										Items.DIRT,
										Component.literal("Create a dirt shack"),
										Component.literal("It's all coming together!"),
										null,
										AdvancementType.TASK,
										false,
										false,
										false
						)
						.addCriterion("place_dirt", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(Blocks.DIRT))
						.save(consumer, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "create_dirt_shack"));
		final HolderLookup<Enchantment> enchantmentsLookup = wrapperLookup.lookupOrThrow(Registries.ENCHANTMENT);
		// #region placeholder-parent
		Advancement.Builder.advancement()
						.parent(createPlaceholder(Identifier.withDefaultNamespace("adventure/root")))
						// ...
						// #endregion placeholder-parent
						.display(
										Items.LIGHTNING_ROD,
										Component.literal("Control the weather"),
										Component.literal("Get the thundering enchantment"),
										null,
										AdvancementType.TASK,
										true,
										true,
										false
						)
						.addCriterion("enchant_thundering",
										CriteriaTriggers.ENCHANTED_ITEM.createCriterion(new EnchantedItemTrigger.TriggerInstance(
														Optional.empty(),
														Optional.of(
																		ItemPredicate.Builder.item().withComponents(DataComponentMatchers.Builder.components()
																						.partial(
																										DataComponentPredicates.ENCHANTMENTS,
																										EnchantmentsPredicate.Enchantments.enchantments(List.of(
																														new EnchantmentPredicate(enchantmentsLookup.getOrThrow(ModEnchantments.THUNDERING), MinMaxBounds.Ints.ANY)
																										))
																						)
																						.build()
																		).build()
														),
														MinMaxBounds.Ints.ANY
										))
						)
						.save(consumer, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "get_thundering_enchantment"));
		// :::datagen-advancements:provider-start
	}
}
// :::datagen-advancements:provider-start
