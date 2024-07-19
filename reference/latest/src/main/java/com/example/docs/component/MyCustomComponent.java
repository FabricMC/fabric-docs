package com.example.docs.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

//::1
public record MyCustomComponent(float temperature, boolean burnt) {
	//::1
	//::2
	public static final Codec<MyCustomComponent> CODEC = RecordCodecBuilder.create(builder -> {
		return builder.group(
			Codec.FLOAT.fieldOf("temperature").forGetter(MyCustomComponent::temperature),
			Codec.BOOL.optionalFieldOf("burnt", false).forGetter(MyCustomComponent::burnt)
		).apply(builder, MyCustomComponent::new);
	});
	//::2
	//::1
}
//::1
