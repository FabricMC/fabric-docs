package com.example.docs.datagen;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import net.minecraft.advancements.criterion.EntityFlagsPredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AllOf;
import net.minecraft.world.item.enchantment.effects.ApplyEntityImpulse;
import net.minecraft.world.item.enchantment.effects.PlaySoundEffect;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.phys.Vec3;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;

import com.example.docs.enchantment.ModEnchantments;
import com.example.docs.enchantment.effect.LightningEnchantmentEffect;

// :::provider
public class ExampleModEnchantmentGenerator extends FabricDynamicRegistryProvider {
	public ExampleModEnchantmentGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(HolderLookup.Provider registries, Entries entries) {
		entries.addAll(registries.lookupOrThrow(Registries.ENCHANTMENT)); // Add all bootstrapped enchantments for the current mod id
	}

	@Override
	public String getName() {
		return "Enchantments";
	}
	// :::provider
	// :::bootstrap
	public static void bootstrap(BootstrapContext<Enchantment> context) {
		// ...
		// :::bootstrap
		// :::register-enchantment
		register(context, ModEnchantments.THUNDERING,
				Enchantment.enchantment(
						Enchantment.definition(
								context.lookup(Registries.ITEM).getOrThrow(ItemTags.WEAPON_ENCHANTABLE), // The items this enchantment can be applied to
								10, // The weight / probability of our enchantment being available in the enchantment table
								3, // The max level of the enchantment
								Enchantment.dynamicCost(1, 10), // The base minimum cost of the enchantment, and the additional cost for every level
								Enchantment.dynamicCost(1, 15), // Same as the other dynamic cost, but for the maximum instead
								5, // The cost to apply the enchantment in an anvil, in levels
								EquipmentSlotGroup.HAND // The slot types in which this enchantment will be able to apply its effects
						)
				)
				.withEffect(
						EnchantmentEffectComponents.POST_ATTACK, // The type of effect to be applied
						EnchantmentTarget.ATTACKER, // The target to be checked for the enchantment
						EnchantmentTarget.VICTIM, // The target to apply the enchantment effect to
						new LightningEnchantmentEffect(LevelBasedValue.perLevel(0.4f, 0.2f))
				)
		);
		// :::register-enchantment
		register(context, ModEnchantments.REBOUNDING_CURSE,
				Enchantment.enchantment(
						Enchantment.definition(
								context.lookup(Registries.ITEM).getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
								10,
								3,
								Enchantment.dynamicCost(1, 10),
								Enchantment.dynamicCost(1, 15),
								5,
								EquipmentSlotGroup.HAND
						)
				)
				// :::effect-conditions
				.withEffect(
						// ...
						// :::effect-conditions
						EnchantmentEffectComponents.POST_ATTACK,
						EnchantmentTarget.ATTACKER,
						EnchantmentTarget.ATTACKER,
						// :::multiple-effects
						AllOf.entityEffects(
								new ApplyEntityImpulse(new Vec3(0, 0.2, -1), new Vec3(1, 1, 1), LevelBasedValue.perLevel(0.7f, 0.2f)),
								new PlaySoundEffect(List.of(SoundEvents.LUNGE_1), ConstantFloat.of(5), ConstantFloat.of(1))
						),
						// :::multiple-effects
						// :::effect-conditions
						LootItemEntityPropertyCondition.hasProperties(
								LootContext.EntityTarget.ATTACKER,
								EntityPredicate.Builder.entity().flags(
										EntityFlagsPredicate.Builder.flags().setIsFlying(false)
								)
						)
				)
		// :::effect-conditions
		);
		// :::bootstrap
	}
	// :::bootstrap
	// :::provider

	// :::provider
	// :::register-helper
	private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
		context.register(key, builder.build(key.identifier()));
	}
	// :::register-helper
	// :::provider
}
// :::provider
