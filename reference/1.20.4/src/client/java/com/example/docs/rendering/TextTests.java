package com.example.docs.rendering;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class TextTests {
	public void test() {
		// :::1
		MutableComponent mutable = Component.translatable("my_mod.text.bye");
		String json = Component.Serializer.toJson(mutable);
		// :::1

		// :::2
		String jsonString = Component.Serializer.toJson(mutable);
		MutableComponent deserialized = Component.Serializer.fromJson(jsonString);
		// :::2
	}
}
