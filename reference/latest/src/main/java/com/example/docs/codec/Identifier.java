package com.example.docs.codec;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

import net.minecraft.IdentifierException;

// #region identifier-flatmap
public class Identifier {
	public static final Codec<Identifier> CODEC = Codec.STRING.comapFlatMap(
					Identifier::read, Identifier::toString
	);

	// ...

	public static DataResult<Identifier> read(String input) {
		try {
			return DataResult.success(Identifier.parse(input));
		} catch (IdentifierException e) {
			return DataResult.error(() -> "Not a valid identifier: " + input + " " + e.getMessage());
		}
	}

	// ...

	// #endregion identifier-flatmap

	private final net.minecraft.resources.Identifier real;

	public Identifier(net.minecraft.resources.Identifier real) {
		this.real = real;
	}

	@Override
	public String toString() {
		return real.toString();
	}

	private static Identifier parse(String input) {
		return new Identifier(net.minecraft.resources.Identifier.parse(input));
	}
	// #region identifier-flatmap
}
// #endregion identifier-flatmap
