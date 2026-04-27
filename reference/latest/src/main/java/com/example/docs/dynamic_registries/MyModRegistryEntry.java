package com.example.docs.dynamic_registries;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

// #region main
// #region clientCodec
public record MyModRegistryEntry(String name, int number) {
		// #endregion clientCodec
	  public static final Codec<MyModRegistryEntry> CODEC = RecordCodecBuilder.create(instance ->
				instance.group(
						Codec.STRING.fieldOf("name").forGetter(MyModRegistryEntry::name),
						Codec.INT.fieldOf("number").forGetter(MyModRegistryEntry::number)
				).apply(instance, MyModRegistryEntry::new)
	  );
		// #endregion main

		// #region clientCodec
		// Other Variables and Methods

		public static final Codec<MyModRegistryEntry> CLIENT_CODEC = RecordCodecBuilder.create(instance ->
				instance.group(
						Codec.STRING.fieldOf("name").forGetter(MyModRegistryEntry::name)
				).apply(instance, (name -> new MyModRegistryEntry(name, 0)))
				// See how we skipped the number field.
		);
		// #region main
}
// #endregion clientCodec
// #endregion main
