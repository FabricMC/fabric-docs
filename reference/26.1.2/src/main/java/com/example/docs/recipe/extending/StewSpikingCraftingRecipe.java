package com.example.docs.recipe.extending;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

// #region stew_spiking
// Thank you to lynndova for the recipe name
public class StewSpikingCraftingRecipe extends CustomRecipe {
	public static final StewSpikingCraftingRecipe INSTANCE = new StewSpikingCraftingRecipe();
	public static final MapCodec<StewSpikingCraftingRecipe> CODEC = MapCodec.unit(INSTANCE);
	public static final StreamCodec<RegistryFriendlyByteBuf, StewSpikingCraftingRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);
	public static final RecipeSerializer<StewSpikingCraftingRecipe> SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);

	@Override
	public boolean matches(CraftingInput input, Level level) {
		return getItemsFromInput(input) != null;
	}

	@Override
	public ItemStack assemble(CraftingInput input) {
		StewAndPotions ingredients = getItemsFromInput(input);

		if (ingredients == null) {
			return ItemStack.EMPTY;
		}

		ItemStack suspiciousStew = ingredients.stew().copy();
		SuspiciousStewEffects originalEffects = suspiciousStew.getOrDefault(DataComponents.SUSPICIOUS_STEW_EFFECTS, SuspiciousStewEffects.EMPTY);

		// We use a TreeMap to hopefully correctly compare Holder<MobEffect>s.
		Map<Holder<MobEffect>, Integer> effects = new TreeMap<>(
				(one, two) -> {
					//noinspection deprecation
					if (one == two || one.is(two)) {
						return 0;
					}

					return one.toString().compareTo(two.toString());
				}
		);

		originalEffects.effects().forEach(entry -> effects.put(entry.effect(), entry.duration()));

		for (ItemStack potion : ingredients.potions()) {
			float durationScale = potion.getOrDefault(DataComponents.POTION_DURATION_SCALE, 1F);
			PotionContents potionContents = potion.get(DataComponents.POTION_CONTENTS);

			// This is fine because we checked for the presence of the component in getItemsFromInput.
			//noinspection DataFlowIssue
			potionContents.getAllEffects().forEach(instance -> {
				Holder<MobEffect> effect = instance.getEffect();
				int duration = effects.getOrDefault(effect, 0);

				if (duration == MobEffectInstance.INFINITE_DURATION) {
					return;
				}

				duration = Math.max(Mth.floor(instance.getDuration() * (instance.getAmplifier() + 1) * durationScale), duration);
				effects.put(effect, duration);
			});
		}

		suspiciousStew.set(
				DataComponents.SUSPICIOUS_STEW_EFFECTS,
				new SuspiciousStewEffects(
						effects.entrySet()
								.stream()
								.map(entry ->
										new SuspiciousStewEffects.Entry(
												entry.getKey(), entry.getValue()
										)
								)
								.toList()
				)
		);
		return suspiciousStew;
	}

	@Nullable
	public static StewAndPotions getItemsFromInput(CraftingInput input) {
		List<ItemStack> items = input.items();

		if (items.size() <= 1) {
			return null;
		}

		ItemStack stew = ItemStack.EMPTY;
		ImmutableList.Builder<ItemStack> builder = ImmutableList.builder();

		for (ItemStack stack : input.items()) {
			if (stack.is(Items.SUSPICIOUS_STEW)) {
				if (!stew.isEmpty()) {
					/*
					If the stew is not empty, then there are two suspicious stews in our input.
					Therefore, we return null, as this is no longer a valid input.
					 */
					return null;
				}

				stew = stack;
			} else if (stack.has(DataComponents.POTION_CONTENTS)) {
				builder.add(stack);
			} else {
				return null;
			}
		}

		if (stew.isEmpty()) {
			return null;
		}

		List<ItemStack> potions = builder.build();

		if (potions.isEmpty()) {
			return null;
		}

		return new StewAndPotions(stew, potions);
	}

	@Override
	public RecipeSerializer<? extends CustomRecipe> getSerializer() {
		return SERIALIZER;
	}

	public record StewAndPotions(ItemStack stew, List<ItemStack> potions) {
	}
}
// #endregion stew_spiking
