package com.example.docs.data;

import java.util.concurrent.CompletableFuture;

import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;

import com.example.docs.FabricDocsReference;
import com.example.docs.enchantment.effect.LightningEnchantmentEffect;

//#entrypoint
public class EnchantmentGenerator extends FabricDynamicRegistryProvider {
	public static final RegistryKey<Enchantment> THUNDERING = EnchantmentGenerator.of("thundering");

	public EnchantmentGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
		System.out.println("REGISTERING ENCHANTS");
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
		// Our new enchantment, "Thundering."
		register(
				entries,
				THUNDERING,
				Enchantment.builder(
								Enchantment.definition(
										registries.getWrapperOrThrow(RegistryKeys.ITEM).getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
										10, // this is the "weight" or probability of our enchantment showing up in the table
										3, // the maximum level of the enchantment
										Enchantment.leveledCost(1, 10), // base cost for level 1 of the enchantment, and min levels required for something higher
										Enchantment.leveledCost(1, 15), // same fields as above but for max cost
										5, // anvil cost
										AttributeModifierSlot.HAND // valid slots
								)
						)
						.addEffect(
								EnchantmentEffectComponentTypes.POST_ATTACK, // enchantment occurs POST_ATTACK
								EnchantmentEffectTarget.ATTACKER,
								EnchantmentEffectTarget.VICTIM,
								new LightningEnchantmentEffect(EnchantmentLevelBasedValue.linear(0.4f, 0.2f)) // scale the enchantment linearly.
						)
		);
	}

	private void register(Entries entries, RegistryKey<Enchantment> key, Enchantment.Builder builder, ResourceCondition... resourceConditions) {
		entries.add(key, builder.build(key.getValue()), resourceConditions);
	}

	private static RegistryKey<Enchantment> of(String path) {
		Identifier id = Identifier.of(FabricDocsReference.MOD_ID, path);
		return RegistryKey.of(RegistryKeys.ENCHANTMENT, id);
	}

	@Override
	public String getName() {
		return null;
	}
}
