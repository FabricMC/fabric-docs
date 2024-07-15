package com.example.docs.codec;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

// :::
// An implementing class of Bean, with its own codec.
public class StringyBean implements Bean {
	public static final Codec<StringyBean> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.STRING.fieldOf("stringy_string").forGetter(StringyBean::getStringyString)
	).apply(instance, StringyBean::new));

	private String stringyString;
	// :::

	public StringyBean(String stringyString) {
		this.stringyString = stringyString;
	}

	public String getStringyString() {
		return stringyString;
	}

	// :::

	// It is important to be able to retrieve the
	// BeanType of a Bean from it's instance.
	@Override
	public BeanType<?> getType() {
		return BeanTypes.STRINGY_BEAN;
	}
}
// :::
