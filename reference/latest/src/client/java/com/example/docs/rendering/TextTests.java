package com.example.docs.rendering;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.chat.MutableComponent;

public class TextTests {
	public void test() {
		// :::1
		Gson gson = new Gson();
		MutableComponent mutable = Component.translatable("my_mod.text.bye");
		String json = gson.toJson(ComponentSerialization.CODEC.encodeStart(JsonOps.INSTANCE, mutable).getOrThrow());
		// :::1

		// :::2
		String jsonString = "...";
		Component deserialized = ComponentSerialization.CODEC
				.decode(JsonOps.INSTANCE, gson.fromJson(jsonString, JsonElement.class))
				.getOrThrow()
				.getFirst();
		// :::2
	}
}
