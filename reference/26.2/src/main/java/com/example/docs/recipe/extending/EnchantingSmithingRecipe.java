package com.example.docs.recipe.extending;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleSmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.item.crafting.display.SmithingRecipeDisplay;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

// #region enchanting_smithing
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class EnchantingSmithingRecipe extends SimpleSmithingRecipe {
	private static final Codec<Integer> ENCHANTMENT_LEVEL_CODEC = Codec.intRange(1, 255);
	public static final Codec<Object2IntOpenHashMap<Holder<Enchantment>>> ENCHANTMENTS_CODEC = Codec.unboundedMap(Enchantment.CODEC, ENCHANTMENT_LEVEL_CODEC)
			.xmap(Object2IntOpenHashMap::new, Function.identity()
	);
	public static final StreamCodec<RegistryFriendlyByteBuf, Object2IntOpenHashMap<Holder<Enchantment>>> ENCHANTMENTS_STREAM_CODEC = ByteBufCodecs.map(
			Object2IntOpenHashMap::new,
			Enchantment.STREAM_CODEC,
			ByteBufCodecs.VAR_INT
	);
	public static final MapCodec<EnchantingSmithingRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
			(instance) -> instance.group(
					CommonInfo.MAP_CODEC.forGetter((o) -> o.commonInfo),
					Ingredient.CODEC.optionalFieldOf("template").forGetter(recipe -> recipe.template),
					Ingredient.CODEC.fieldOf("base").forGetter(recipe -> recipe.base),
					Ingredient.CODEC.optionalFieldOf("addition").forGetter(recipe -> recipe.addition),
					ENCHANTMENTS_CODEC.fieldOf("enchantments").forGetter(recipe -> recipe.enchantments)
			).apply(instance, EnchantingSmithingRecipe::new)
	);
	public static final StreamCodec<RegistryFriendlyByteBuf, EnchantingSmithingRecipe> STREAM_CODEC = StreamCodec.composite(
			CommonInfo.STREAM_CODEC, recipe -> recipe.commonInfo,
			Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC, recipe -> recipe.template,
			Ingredient.CONTENTS_STREAM_CODEC, recipe -> recipe.base,
			Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC, recipe -> recipe.addition,
			ENCHANTMENTS_STREAM_CODEC, recipe -> recipe.enchantments,
			EnchantingSmithingRecipe::new
	);
	public static final RecipeSerializer<EnchantingSmithingRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);
	private final Optional<Ingredient> template;
	private final Ingredient base;
	private final Optional<Ingredient> addition;
	private final Object2IntOpenHashMap<Holder<Enchantment>> enchantments;

	public EnchantingSmithingRecipe(final Recipe.CommonInfo commonInfo, final Optional<Ingredient> template, final Ingredient base, final Optional<Ingredient> addition, final Object2IntOpenHashMap<Holder<Enchantment>> enchantments) {
		super(commonInfo);
		this.template = template;
		this.base = base;
		this.addition = addition;
		this.enchantments = enchantments;
	}

	@Override
	public ItemStack assemble(SmithingRecipeInput input) {
		return applyEnchantments(input.base(), this.enchantments);
	}

	public static ItemStack applyEnchantments(ItemStack base, Object2IntOpenHashMap<Holder<Enchantment>> enchantments) {
		ItemStack result = base.copy();
		EnchantmentHelper.updateEnchantments(result, mutable -> enchantments.forEach(mutable::upgrade));
		return result;
	}

	@Override
	public RecipeSerializer<? extends SimpleSmithingRecipe> getSerializer() {
		return SERIALIZER;
	}

	@Override
	public Optional<Ingredient> templateIngredient() {
		return this.template;
	}

	@Override
	public Ingredient baseIngredient() {
		return this.base;
	}

	@Override
	public Optional<Ingredient> additionIngredient() {
		return this.addition;
	}

	@Override
	protected PlacementInfo createPlacementInfo() {
		return PlacementInfo.createFromOptionals(List.of(this.template, Optional.of(this.base), this.addition));
	}

	@Override
	public List<RecipeDisplay> display() {
		SlotDisplay base = this.base.display();
		SlotDisplay material = Ingredient.optionalIngredientToDisplay(this.addition);
		SlotDisplay template = Ingredient.optionalIngredientToDisplay(this.template);
		return List.of(new SmithingRecipeDisplay(
				template,
				base,
				material,
				new EnchantingSmithingDemoSlotDisplay(base, material, this.enchantments),
				new SlotDisplay.ItemSlotDisplay(Items.SMITHING_TABLE)
		));
	}
}
// #endregion enchanting_smithing
