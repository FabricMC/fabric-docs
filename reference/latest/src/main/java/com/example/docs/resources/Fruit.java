package com.example.docs.resources;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

//:::1
public record Fruit(String name) {

	public static final Logger LOGGER = LoggerFactory.getLogger(Fruit.class);
	public static final Codec<Fruit> CODEC = Codec.STRING.fieldOf("name").xmap(Fruit::new, Fruit::name).codec();

	public static Optional<Fruit> deserialize(JsonElement json) {
		DataResult<Fruit> result = CODEC.parse(JsonOps.INSTANCE, json);
		return result.resultOrPartial(LOGGER::error);
	}
}
//:::1
