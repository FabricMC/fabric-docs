package com.example.docs.resources;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

//:::1
public record Book(String name, String author) {

	public static final Logger LOGGER = LoggerFactory.getLogger(Book.class);
	public static final Codec<Book> CODEC = RecordCodecBuilder.create(instance -> {
		return instance.group(Codec.STRING.fieldOf("name").forGetter(Book::name),
				Codec.STRING.fieldOf("author").forGetter(Book::author)
		).apply(instance, Book::new);
	});

	public static Optional<Book> deserialize(JsonElement json) {
		DataResult<Book> result = CODEC.parse(JsonOps.INSTANCE, json);
		return result.resultOrPartial(LOGGER::error);
	}
}
//:::1
