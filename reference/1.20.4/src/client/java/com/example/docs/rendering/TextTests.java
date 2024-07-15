package com.example.docs.rendering;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class TextTests {
	public void test() {
		// :::1
		MutableText mutable = Text.translatable("my_mod.text.bye");
		String json = Text.Serialization.toJsonString(mutable);
		// :::1

		// :::2
		String jsonString = Text.Serialization.toJsonString(mutable);
		MutableText deserialized = Text.Serialization.fromJson(jsonString);
		// :::2
	}
}
