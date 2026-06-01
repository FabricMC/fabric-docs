package com.example.docs.datagen;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;

import com.example.docs.codec.Bean;
import com.example.docs.codec.BeanType;
import com.example.docs.codec.CoolBeansClass;
import com.example.docs.codec.CountingBean;
import com.example.docs.codec.ListNode;
import com.example.docs.codec.StringyBean;
import com.example.docs.item.ModItems;

public class ExampleModCodecExampleProvider extends ExampleModExampleProvider {
	private static final Submitter[] SUBMITTERS = new Submitter[] {
			ExampleModCodecExampleProvider::usingCodecs,
			ExampleModCodecExampleProvider::beanCodec,
			ExampleModCodecExampleProvider::mapCodec,
			ExampleModCodecExampleProvider::listCodec,
			ExampleModCodecExampleProvider::optionalFields,
			ExampleModCodecExampleProvider::unit,
			ExampleModCodecExampleProvider::numericRanges,
			ExampleModCodecExampleProvider::pair,
			ExampleModCodecExampleProvider::map,
			ExampleModCodecExampleProvider::xmap,
			ExampleModCodecExampleProvider::registryDispatch,
			ExampleModCodecExampleProvider::recursive
	};

	protected ExampleModCodecExampleProvider(FabricPackOutput output) {
		super(output, "codec_examples", SUBMITTERS);
	}

	private static void usingCodecs(BiConsumer<String, JsonElement> consumer) {
		// #region encode-blockpos
		BlockPos pos = new BlockPos(1, 2, 3);

		// Serialize the BlockPos to a JsonElement
		DataResult<JsonElement> serializeResult = BlockPos.CODEC.encodeStart(JsonOps.INSTANCE, pos);

		// When actually writing a mod, you'll want to properly handle empty Optionals of course
		JsonElement json = serializeResult.resultOrPartial(LOGGER::error).orElseThrow();

		// Here we have our JSON value, which should correspond to `[1, 2, 3]`,
		// as that's the format used by the BlockPos codec.
		LOGGER.info("Serialized BlockPos: {}", json);
		// #endregion encode-blockpos

		// #region parse-blockpos
		// Now we'll deserialize the JsonElement back into a BlockPos
		DataResult<BlockPos> deserializeResult = BlockPos.CODEC.parse(JsonOps.INSTANCE, json);

		// Again, we'll just grab our value from the deserializeResult
		BlockPos deserializedPos = deserializeResult.resultOrPartial(LOGGER::error).orElseThrow();

		// And we can see that we've successfully serialized and deserialized our BlockPos!
		LOGGER.info("Deserialized BlockPos: {}", deserializedPos);
		// #endregion parse-blockpos

		consumer.accept("serialize_blockpos", json);
	}

	private static void beanCodec(BiConsumer<String, JsonElement> consumer) {
		// #region bean-codec-data
		CoolBeansClass bean = new CoolBeansClass(
				5,
				BuiltInRegistries.ITEM.wrapAsHolder(ModItems.LIGHTNING_TATER),
				List.of(
						new BlockPos(1, 2, 3),
						new BlockPos(4, 5, 6)
				)
		);
		// #endregion bean-codec-data

		final var json = encode(CoolBeansClass.CODEC, bean);
		consumer.accept("cool_beans", json);
	}

	private static void mapCodec(BiConsumer<String, JsonElement> consumer) {
		final var pos = new BlockPos(1, 2, 3);
		consumer.accept("plain_codec", encode(BlockPos.CODEC, pos));
		final var mapCodec = BlockPos.CODEC.fieldOf("pos").codec();
		consumer.accept("map_codec", encode(mapCodec, pos));
	}

	private static void listCodec(BiConsumer<String, JsonElement> consumer) {
		// #region list-codec
		Codec<List<BlockPos>> listCodec = BlockPos.CODEC.listOf();
		// #endregion list-codec

		// #region list-codec-data
		List<BlockPos> data = List.of(new BlockPos(10, 5, 7));
		// #endregion list-codec-data

		final var json = encode(listCodec, data);

		consumer.accept("list_codec", json);
	}

	private static void optionalFields(BiConsumer<String, JsonElement> consumer) {
		// #region optional-field
		MapCodec<Optional<BlockPos>> optionalCodec = BlockPos.CODEC.optionalFieldOf("pos");
		// #endregion optional-field

		// #region default-field
		MapCodec<BlockPos> defaultCodec = BlockPos.CODEC.optionalFieldOf("pos", BlockPos.ZERO);
		// #endregion default-field

		// #region optional-field-data
		Optional<BlockPos> optionalBlockPos = Optional.empty();
		// #endregion optional-field-data

		// #region default-field-data
		BlockPos defaultBlockPos = BlockPos.ZERO;
		// #endregion default-field-data

		consumer.accept("optional_field", encode(optionalCodec.codec(), optionalBlockPos));
		consumer.accept("default_field", encode(defaultCodec.codec(), defaultBlockPos));
	}

	private static void unit(BiConsumer<String, JsonElement> consumer) {
		// #region unit-codec
		Codec<Integer> theMeaningOfCodec = MapCodec.unitCodec(42);
		// #endregion unit-codec

		consumer.accept("unit", encode(theMeaningOfCodec, 42));
	}

	private static void numericRanges(BiConsumer<String, JsonElement> consumer) {
		// #region numeric-range
		// Can't be more than 2
		Codec<Integer> amountOfFriendsYouHave = Codec.intRange(0, 2);
		// #endregion numeric-range

		// #region numeric-range-data
		int amount = 2;
		// #endregion numeric-range-data

		consumer.accept("numeric_range", encode(amountOfFriendsYouHave, amount));
	}

	private static void pair(BiConsumer<String, JsonElement> consumer) {
		// #region pair-codec
		// Create two separate boxed codecs
		Codec<Integer> firstCodec = Codec.INT.fieldOf("i_am_number").codec();
		Codec<Boolean> secondCodec = Codec.BOOL.fieldOf("this_statement_is_false").codec();

		// And merge them into a pair codec
		Codec<Pair<Integer, Boolean>> pairCodec = Codec.pair(firstCodec, secondCodec);
		// #endregion pair-codec

		// #region pair-codec-data
		Pair<Integer, Boolean> pair = Pair.of(23, true);
		// #endregion pair-codec-data

		DataResult<JsonElement> result = pairCodec.encodeStart(JsonOps.INSTANCE, pair);

		consumer.accept("pair", result.getOrThrow());
	}

	private static void map(BiConsumer<String, JsonElement> consumer) {
		// #region map-codec
		// Create a codec for a map of Identifiers to integers
		Codec<Map<Identifier, Integer>> mapCodec = Codec.unboundedMap(Identifier.CODEC, Codec.INT);
		// #endregion map-codec

		// #region map-codec-data
		Map<Identifier, Integer> map = Map.of(
				Identifier.fromNamespaceAndPath("example", "number"), 23,
				Identifier.fromNamespaceAndPath("example", "the_cooler_number"), 42
		);
		// #endregion map-codec-data

		consumer.accept("map", encode(mapCodec, map));
	}

	private static void xmap(BiConsumer<String, JsonElement> consumer) {
		// #region convert-xmap
		Codec<BlockPos> blockPosCodec = Vec3i.CODEC.xmap(
				// Convert Vec3i to BlockPos
				vec -> new BlockPos(vec.getX(), vec.getY(), vec.getZ()),
				// Convert BlockPos to Vec3i
				pos -> new Vec3i(pos.getX(), pos.getY(), pos.getZ())
		);

		// When converting an existing class (`X` for example)
		// to your own class (`Y`) this way, it may be nice to
		// add `toX` and static `fromX` methods to `Y` and use
		// method references in your `xmap` call.
		// #endregion convert-xmap

		// #region convert-xmap-data
		BlockPos pos = new BlockPos(1, 2, 3);
		// #endregion convert-xmap-data

		consumer.accept("xmap", encode(blockPosCodec, pos));
	}

	private static void registryDispatch(BiConsumer<String, JsonElement> consumer) {
		// #region registry-dispatch
		// Now we can create a codec for bean types
		// based on the previously created registry
		Codec<BeanType<?>> beanTypeCodec = BeanType.REGISTRY.byNameCodec();

		// And based on that, here's our registry dispatch codec for beans!
		// The first argument is the field name for the bean type.
		// When left out, it will default to "type".
		Codec<Bean> beanCodec = beanTypeCodec.dispatch("type", Bean::getType, BeanType::codec);
		// #endregion registry-dispatch

		final var stringyBean = new StringyBean("This bean is stringy!");

		final var countingBean = new CountingBean(42);

		consumer.accept("stringy_bean", encode(beanCodec, stringyBean));
		consumer.accept("counting_bean", encode(beanCodec, countingBean));
	}

	private static void recursive(BiConsumer<String, JsonElement> consumer) {
		// #region recursive-codec
		Codec<ListNode> codec = Codec.recursive(
				"ListNode", // a name for the codec
				selfCodec -> {
					// Here, `selfCodec` represents the `Codec<ListNode>`, as if it was already constructed
					// This lambda should return the codec we wanted to use from the start,
					// that refers to itself through `selfCodec`
					return RecordCodecBuilder.create(instance ->
							instance.group(
									Codec.INT.fieldOf("value").forGetter(ListNode::value),
									// the `next` field will be handled recursively with the self-codec
									selfCodec.optionalFieldOf("next").forGetter(ListNode::next)
							).apply(instance, ListNode::new)
					);
				}
		);
		// #endregion recursive-codec

		// #region recursive-codec-data
		ListNode linkedList = new ListNode(
				2,
				Optional.of(
						new ListNode(
								3,
								Optional.of(
										new ListNode(
														5,
														Optional.empty()
										)
								)
						)
				)
		);
		// #endregion recursive-codec-data

		consumer.accept("recursive", encode(codec, linkedList));
	}

	@Override
	public String getName() {
		return "Codec examples";
	}
}
