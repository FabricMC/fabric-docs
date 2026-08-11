package com.example.docs.datagen;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;

import com.example.docs.ExampleMod;

public abstract class ExampleModExampleProvider implements DataProvider {
	private final FabricPackOutput output;
	private final String directory;
	private final Submitter[] submitters;

	public ExampleModExampleProvider(FabricPackOutput output, String directory, Submitter... submitters) {
		this.output = output;
		this.directory = directory;
		this.submitters = submitters;
	}

	protected static <T> JsonElement encode(Codec<T> codec, T value) {
		return codec.encodeStart(JsonOps.INSTANCE, value).getOrThrow();
	}

	private Map<String, JsonElement> collect() {
		final var elements = new HashMap<String, JsonElement>();

		for (final Submitter submitter : this.submitters) {
			submitter.submit((name, elem) -> {
				if (elements.put(name, elem) != null) {
					throw new IllegalArgumentException("An element with directory " + name + " has already been added.");
				}
			});
		}

		return elements;
	}

	@Override
	public CompletableFuture<?> run(CachedOutput cache) {
		final var elements = this.collect();

		final var paths = this.output.createPathProvider(PackOutput.Target.REPORTS, this.directory);

		return CompletableFuture.allOf(
						elements.entrySet().stream().map(x ->
										DataProvider.saveStable(
														cache,
														x.getValue(),
														paths.json(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, x.getKey()))
										))
										.toArray(CompletableFuture[]::new)
		);
	}

	public interface Submitter {
		void submit(BiConsumer<String, JsonElement> consumer);
	}
}
