package com.example.docs.datagen.internal;

import java.util.List;
import java.util.function.BiConsumer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Blocks;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;

import com.example.docs.conditions.DateMatchesResourceCondition;
import com.example.docs.datagen.ExampleModExampleProvider;
import com.example.docs.datagen.ExampleModItemTagProvider;

public class ExampleModResourceConditionProvider extends ExampleModExampleProvider {
	public ExampleModResourceConditionProvider(FabricPackOutput output) {
		super(output, "resource_condition_examples", ExampleModResourceConditionProvider::encodeResourceConditions);
	}

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
		JsonElement encodedCondition = encode(ResourceCondition.LIST_CODEC, List.of(condition));
		finalConditions.add(ResourceConditions.CONDITIONS_KEY, encodedCondition);

		return finalConditions;
	}

	@Override
	public String getName() {
		return "Resource condition examples";
	}
}
