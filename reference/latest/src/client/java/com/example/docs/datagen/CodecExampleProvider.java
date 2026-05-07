package com.example.docs.datagen;

import com.example.docs.ExampleMod;

import com.example.docs.item.ModItems;

import com.google.gson.JsonElement;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

import com.mojang.serialization.JsonOps;

import com.mojang.serialization.MapCodec;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.fish.Cod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class CodecExampleProvider implements DataProvider {
	private final FabricPackOutput output;
	private final CompletableFuture<HolderLookup.Provider> registryLookup;

	protected CodecExampleProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
		this.output = output;
		this.registryLookup = registryLookup;
	}
	record Entry(String name, JsonElement element) {}

	private static void usingCodecs(BiConsumer<String, JsonElement> consumer) {
		// #region encode-blockpos
		BlockPos pos = new BlockPos(1, 2, 3);

		// Serialize the BlockPos to a JsonElement
		DataResult<JsonElement> serializeResult = BlockPos.CODEC.encodeStart(JsonOps.INSTANCE, pos);
		// #endregion encode-blockpos

		// #region serialize-deserialize-blockpos
		// When actually writing a mod, you'll want to properly handle empty Optionals of course
		JsonElement json = serializeResult.resultOrPartial(LOGGER::error).orElseThrow();

		// Here we have our JSON value, which should correspond to `[1, 2, 3]`,
		// as that's the format used by the BlockPos codec.
		LOGGER.info("Serialized BlockPos: {}", json);

		// Now we'll deserialize the JsonElement back into a BlockPos
		DataResult<BlockPos> deserializeResult = BlockPos.CODEC.parse(JsonOps.INSTANCE, json);

		// Again, we'll just grab our value from the deserializeResult
		BlockPos deserializedPos = deserializeResult.resultOrPartial(LOGGER::error).orElseThrow();

		// And we can see that we've successfully serialized and deserialized our BlockPos!
		LOGGER.info("Deserialized BlockPos: {}", deserializedPos);
		// #endregion serialize-deserialize-blockpos

		consumer.accept("serialize_blockpos", json);
	}

	private static void beanCodec(BiConsumer<String, JsonElement> consumer) {

		final CoolBeansClass bean = new CoolBeansClass(
						5,
						BuiltInRegistries.ITEM.wrapAsHolder(ModItems.LIGHTNING_TATER),
						List.of(
										new BlockPos(1, 2, 3),
										new BlockPos(4, 5, 6)
						)
		);

		final JsonElement json = CoolBeansClass.CODEC.encodeStart(JsonOps.INSTANCE, bean).getOrThrow();
		consumer.accept("cool_beans", json);
	}

	private static void mapCodec(BiConsumer<String, JsonElement> consumer) {
		final BlockPos pos = new BlockPos(1, 2, 3);
		consumer.accept("plain_codec", BlockPos.CODEC.encodeStart(JsonOps.INSTANCE, pos).getOrThrow());
		final Codec<BlockPos> mapCodec = BlockPos.CODEC.fieldOf("pos").codec();
		consumer.accept("map_codec", mapCodec.encodeStart(JsonOps.INSTANCE, pos).getOrThrow());
	}

	private static void listCodec(BiConsumer<String, JsonElement> consumer) {
		// #region list-codec
		Codec<List<BlockPos>> listCodec = BlockPos.CODEC.listOf();
		// #endregion list-codec
		final JsonElement json = listCodec.encodeStart(JsonOps.INSTANCE, List.of(new BlockPos(10, 5, 7))).getOrThrow();

		consumer.accept("list_codec", json);
	}

	private static void optionalFields(BiConsumer<String, JsonElement> consumer) {
		// #region optional-fields
		// Without a default value
		MapCodec<Optional<BlockPos>> optionalCodec = BlockPos.CODEC.optionalFieldOf("pos");

		// With a default value
		MapCodec<BlockPos> defaultCodec = BlockPos.CODEC.optionalFieldOf("pos", BlockPos.ZERO);
		// #endregion optional-fields

		final Codec<Pair<Optional<BlockPos>, BlockPos>> codec = Codec.pair(optionalCodec.codec(), defaultCodec.codec());

		final Pair<Optional<BlockPos>, BlockPos> value = new Pair<>(
						Optional.empty(),
						BlockPos.ZERO
		);

		consumer.accept("optional_fields", codec.encodeStart(JsonOps.INSTANCE, value).getOrThrow());
	}

	private static void unit(BiConsumer<String, JsonElement> consumer) {
		// #region unit-codec
		Codec<Integer> theMeaningOfCodec = MapCodec.unitCodec(42);
		// #endregion unit-codec

		consumer.accept("unit", theMeaningOfCodec.encodeStart(JsonOps.INSTANCE, 42).getOrThrow());
	}

	private static void numericRanges(BiConsumer<String, JsonElement> consumer) {
		// #region numeric-ranges
		// Can't be more than 2
		Codec<Integer> amountOfFriendsYouHave = Codec.intRange(0, 2);
		// #endregion numeric-ranges

		consumer.accept("numeric_ranges", amountOfFriendsYouHave.encodeStart(JsonOps.INSTANCE, 2).getOrThrow());
	}

	private static void pair(BiConsumer<String, JsonElement> consumer) {
		// #region pair-codec
		// Create two separate boxed codecs
		Codec<Integer> firstCodec = Codec.INT.fieldOf("i_am_number").codec();
		Codec<Boolean> secondCodec = Codec.BOOL.fieldOf("this_statement_is_false").codec();

		// Merge them into a pair codec
		Codec<Pair<Integer, Boolean>> pairCodec = Codec.pair(firstCodec, secondCodec);

		// Use it to serialize data
		DataResult<JsonElement> result = pairCodec.encodeStart(JsonOps.INSTANCE, Pair.of(23, true));
		// #endregion pair-codec

		consumer.accept("pair", result.getOrThrow());
	}

	private static void map(BiConsumer<String, JsonElement> consumer) {
		// #region map-codec
		// Create a codec for a map of Identifiers to integers
		Codec<Map<Identifier, Integer>> mapCodec = Codec.unboundedMap(Identifier.CODEC, Codec.INT);

		// Use it to serialize data
		DataResult<JsonElement> result = mapCodec.encodeStart(JsonOps.INSTANCE, Map.of(
						Identifier.fromNamespaceAndPath("example", "number"), 23,
						Identifier.fromNamespaceAndPath("example", "the_cooler_number"), 42
		));
		// #endregion map-codec

		consumer.accept("map", result.getOrThrow());
	}

	private static void collect(BiConsumer<String, JsonElement> consumer) {
		usingCodecs(consumer);
		beanCodec(consumer);
		listCodec(consumer);
		mapCodec(consumer);
		optionalFields(consumer);
		unit(consumer);
		numericRanges(consumer);
		pair(consumer);
		map(consumer);
	}

	@Override
	public CompletableFuture<?> run(CachedOutput cache) {
		final List<Entry> entries = new ArrayList<>();

		collect((name, elem) -> entries.addLast(new Entry(name, elem)));

		final var paths = output.createPathProvider(PackOutput.Target.REPORTS, "codec_examples");

		return CompletableFuture.allOf(
						entries.stream().map(x ->
										DataProvider.saveStable(
														cache,
														x.element,
														paths.json(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, x.name))
										)
						)
						.toArray(CompletableFuture[]::new)
		);
	}

	@Override
	public String getName() {
		return "Funny codecs";
	}
}
