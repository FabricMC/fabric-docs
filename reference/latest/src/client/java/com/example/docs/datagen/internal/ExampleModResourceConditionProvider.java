package com.example.docs.datagen.internal;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Blocks;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.fabricmc.fabric.impl.resource.conditions.DefaultResourceConditionTypes;
import net.fabricmc.fabric.impl.resource.conditions.conditions.AllModsLoadedResourceCondition;
import net.fabricmc.fabric.impl.resource.conditions.conditions.AndResourceCondition;
import net.fabricmc.fabric.impl.resource.conditions.conditions.AnyModsLoadedResourceCondition;
import net.fabricmc.fabric.impl.resource.conditions.conditions.FeaturesEnabledResourceCondition;
import net.fabricmc.fabric.impl.resource.conditions.conditions.NotResourceCondition;
import net.fabricmc.fabric.impl.resource.conditions.conditions.OrResourceCondition;
import net.fabricmc.fabric.impl.resource.conditions.conditions.RegistryContainsResourceCondition;
import net.fabricmc.fabric.impl.resource.conditions.conditions.TagsPopulatedResourceCondition;
import net.fabricmc.fabric.impl.resource.conditions.conditions.TrueResourceCondition;

import com.example.docs.ExampleMod;
import com.example.docs.conditions.ModResourceConditions;
import com.example.docs.conditions.TagsEmptyResourceCondition;
import com.example.docs.datagen.ExampleModItemTagProvider;

@SuppressWarnings("all")
public class ExampleModResourceConditionProvider implements DataProvider {
	private final FabricPackOutput output;

	public ExampleModResourceConditionProvider(FabricPackOutput output) {
		this.output = output;
	}

	private static final List<Consumer<BiConsumer<String, JsonElement>>> SUBMITTERS = List.of(
			ExampleModResourceConditionProvider::encodeResourceConditions
	);

	private static void encodeResourceConditions(BiConsumer<String, JsonElement> consumer) {
		acceptCondition(consumer, DefaultResourceConditionTypes.TRUE, new TrueResourceCondition());
		acceptCondition(consumer, DefaultResourceConditionTypes.NOT, new NotResourceCondition(ResourceConditions.alwaysTrue()));
		acceptCondition(consumer, DefaultResourceConditionTypes.OR, new OrResourceCondition(List.of(ResourceConditions.alwaysTrue(), new NotResourceCondition(ResourceConditions.alwaysTrue()))));
		acceptCondition(consumer, DefaultResourceConditionTypes.AND, new AndResourceCondition(List.of(ResourceConditions.alwaysTrue(), new NotResourceCondition(ResourceConditions.alwaysTrue()))));
		acceptCondition(consumer, DefaultResourceConditionTypes.ANY_MODS_LOADED, new AnyModsLoadedResourceCondition(List.of("example-mod", "another-mod")));
		acceptCondition(consumer, DefaultResourceConditionTypes.ALL_MODS_LOADED, new AllModsLoadedResourceCondition(List.of("example-mod", "another-mod")));
		acceptCondition(consumer, DefaultResourceConditionTypes.TAGS_POPULATED, new TagsPopulatedResourceCondition(ExampleModItemTagProvider.SMELLY_ITEMS));
		acceptCondition(consumer, DefaultResourceConditionTypes.FEATURES_ENABLED, new FeaturesEnabledResourceCondition(FeatureFlags.VANILLA, FeatureFlags.MINECART_IMPROVEMENTS));
		acceptCondition(consumer, DefaultResourceConditionTypes.REGISTRY_CONTAINS, new RegistryContainsResourceCondition(Blocks.COBBLESTONE.builtInRegistryHolder().key()));
		acceptCondition(consumer, ModResourceConditions.TAGS_EMPTY, new TagsEmptyResourceCondition(ExampleModItemTagProvider.SMELLY_ITEMS));
	}

	private static <T extends ResourceCondition> void acceptCondition(BiConsumer<String, JsonElement> consumer, ResourceConditionType<T> type, T condition) {
		consumer.accept(type.id().getPath(), encodeConditions(type, condition));
	}

	private static <T extends ResourceCondition> JsonElement encodeConditions(ResourceConditionType<T> type, T condition) {
		JsonObject finalConditions = new JsonObject();
		JsonArray conditions = new JsonArray();
		JsonObject encodedCondition = encodeCondition(type, condition);
		conditions.add(encodedCondition);
		finalConditions.add(ResourceConditions.CONDITIONS_KEY, conditions);

		return finalConditions;
	}

	private static <T extends ResourceCondition> JsonObject encodeCondition(ResourceConditionType<T> type, T trueResourceCondition) {
		JsonObject encodedCondition = type.codec().codec().encodeStart(JsonOps.INSTANCE, trueResourceCondition).getOrThrow().getAsJsonObject();
		encodedCondition.add("condition", new JsonPrimitive(type.id().toString()));
		return encodedCondition;
	}

	private static void collect(BiConsumer<String, JsonElement> consumer) {
		for (final var submitter : SUBMITTERS) {
			submitter.accept(consumer);
		}
	}

	private static <T> JsonElement encode(Codec<T> codec, T value) {
		return codec.encodeStart(JsonOps.INSTANCE, value).getOrThrow();
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
