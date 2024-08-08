package com.example.docs.codec;

import com.mojang.serialization.Codec;

// :::
// The abstract type we want to create a codec for
public interface Bean {
	// Now we can create a codec for bean types based on the previously created registry.
	Codec<Bean> BEAN_CODEC = BeanType.REGISTRY.getCodec()
			// And based on that, here's our registry dispatch codec for beans!
			// The first argument is the field name for the bean type.
			// When left out, it will default to "type".
			.dispatch("type", Bean::getType, BeanType::codec);

	BeanType<?> getType();
}
// :::
