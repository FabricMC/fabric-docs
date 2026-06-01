package com.example.docs.datagen.internal;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Blocks;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;

import com.example.docs.ExampleMod;
import com.example.docs.conditions.DateMatchesResourceCondition;
import com.example.docs.datagen.ExampleModItemTagProvider;

public class ExampleModResourceConditionProvider implements DataProvider {
	private final FabricPackOutput output;

	public ExampleModResourceConditionProvider(FabricPackOutput output) {
		this.output = output;
	}

	private static final List<Consumer<BiConsumer<String, JsonElement>>> SUBMITTERS = List.of(
			ExampleModResourceConditionProvider::encodeResourceConditions
	);

	private static void encodeResourceConditions(BiConsumer<String, JsonElement> consumer) {
		acceptCondition(consumer, ResourceConditions.alwaysTrue());
		acceptCondition(consumer, ResourceConditions.not(ResourceConditions.alwaysTrue()));
		acceptCondition(consumer, ResourceConditions.or(ResourceConditions.alwaysTrue(), ResourceConditions.not(ResourceConditions.alwaysTrue())));
		acceptCondition(consumer, ResourceConditions.and(ResourceConditions.alwaysTrue(), ResourceConditions.not(ResourceConditions.alwaysTrue())));
		acceptCondition(consumer, ResourceConditions.anyModsLoaded("example-mod", "another-mod"));
		acceptCondition(consumer, ResourceConditions.allModsLoaded("example-mod", "another-mod"));
		acceptCondition(consumer, ResourceConditions.tagsPopulated(ExampleModItemTagProvider.SMELLY_ITEMS));
		acceptCondition(consumer, ResourceConditions.featuresEnabled(FeatureFlags.VANILLA, FeatureFlags.MINECART_IMPROVEMENTS));
		acceptCondition(consumer, ResourceConditions.registryContains(BuiltInRegistries.BLOCK.getResourceKey(Blocks.COBBLESTONE).orElseThrow()));
		acceptCondition(consumer, new DateMatchesResourceCondition(4, 1));
	}

	private static void acceptCondition(BiConsumer<String, JsonElement> consumer, ResourceCondition condition) {
		consumer.accept(condition.getType().id().getPath(), encodeConditions(condition));
	}

	private static JsonElement encodeConditions(ResourceCondition condition) {
		JsonObject finalConditions = new JsonObject();
		JsonElement encodedCondition = encodeCondition(condition);
		finalConditions.add(ResourceConditions.CONDITIONS_KEY, encodedCondition);

		return finalConditions;
	}

	private static JsonElement encodeCondition(ResourceCondition condition) {
		return ResourceCondition.LIST_CODEC.encodeStart(JsonOps.INSTANCE, List.of(condition)).getOrThrow();
	}

	private static void collect(BiConsumer<String, JsonElement> consumer) {
		for (final var submitter : SUBMITTERS) {
			submitter.accept(consumer);
		}
	}

	@Override
	public CompletableFuture<?> run(CachedOutput cache) {
		final var elements = new HashMap<String, JsonElement>();

		collect((name, elem) -> {
			if (elements.put(name, elem) != null) {
				throw new IllegalArgumentException("An element with name " + name + " has already been added.");
			}
		});

		final var paths = this.output.createPathProvider(PackOutput.Target.REPORTS, "resource_condition_examples");

		return CompletableFuture.allOf(
				elements.entrySet().stream().map(x ->
								DataProvider.saveStable(
										cache,
										x.getValue(),
										paths.json(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, x.getKey()))
								)
						)
						.toArray(CompletableFuture[]::new)
		);
	}

	@Override
	public String getName() {
		return "Resource condition examples";
	}
}
