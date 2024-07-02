package com.example.docs.rendering;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;

public class TextTests {
	public void test() {
		// :::1
		Gson gson = new Gson();
		MutableText mutable = Text.translatable("my_mod.text.bye");
		String json = gson.toJson(TextCodecs.CODEC.encodeStart(JsonOps.INSTANCE, mutable).getOrThrow());
		// :::1

		// :::2
		String jsonString = "...";
		Text deserialized = TextCodecs.CODEC
				.decode(JsonOps.INSTANCE, gson.fromJson(jsonString, JsonElement.class))
				.getOrThrow()
				.getFirst();
		// :::2
	}
}
