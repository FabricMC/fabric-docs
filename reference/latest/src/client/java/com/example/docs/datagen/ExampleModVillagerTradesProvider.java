package com.example.docs.datagen;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.VillagerTrade;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;

import com.example.docs.ExampleMod;
import com.example.docs.item.ModItems;

public class ExampleModVillagerTradesProvider extends FabricDynamicRegistryProvider {
	public static final ResourceKey<VillagerTrade> ARMORER_1_EMERALD_GUIDITE_HELMET = create("armorer/1/emerald_guidite_helmet");
	public static final ResourceKey<VillagerTrade> ARMORER_1_EMERALD_GUIDITE_CHESTPLATE = create("armorer/1/emerald_guidite_chestplate");
	public static final ResourceKey<VillagerTrade> ARMORER_1_EMERALD_GUIDITE_LEGGINGS = create("armorer/1/emerald_guidite_leggings");
	public static final ResourceKey<VillagerTrade> ARMORER_1_EMERALD_GUIDITE_BOOTS = create("armorer/1/emerald_guidite_boots");

	public ExampleModVillagerTradesProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(HolderLookup.Provider registries, Entries entries) {
		entries.addAll(registries.lookupOrThrow(Registries.VILLAGER_TRADE));
	}

	public static void bootstrap(BootstrapContext<VillagerTrade> context) {
		context.register(ARMORER_1_EMERALD_GUIDITE_HELMET, new VillagerTrade(
				new TradeCost(Items.EMERALD, 4),
				new ItemStackTemplate(ModItems.GUIDITE_HELMET),
				5,
				2,
				0.05f,
				Optional.empty(),
				List.of()
		));

		context.register(ARMORER_1_EMERALD_GUIDITE_CHESTPLATE, new VillagerTrade(
				new TradeCost(Items.EMERALD, 12),
				new ItemStackTemplate(ModItems.GUIDITE_CHESTPLATE, 1),
				5,
				2,
				0.05f,
				Optional.empty(),
				List.of()
		));

		context.register(ARMORER_1_EMERALD_GUIDITE_LEGGINGS, new VillagerTrade(
				new TradeCost(Items.EMERALD, 6),
				new ItemStackTemplate(ModItems.GUIDITE_LEGGINGS),
				5,
				2,
				0.05f,
				Optional.empty(),
				List.of()
		));

		context.register(ARMORER_1_EMERALD_GUIDITE_BOOTS, new VillagerTrade(
				new TradeCost(Items.EMERALD, 2),
				new ItemStackTemplate(ModItems.GUIDITE_BOOTS),
				5,
				2,
				0.05f,
				Optional.empty(),
				List.of()
		));
	}

	public static ResourceKey<VillagerTrade> create(String name) {
		return ResourceKey.create(Registries.VILLAGER_TRADE, ExampleMod.id(name));
	}

	@Override
	public String getName() {
		return "Villager Trades Generation";
	}
}
