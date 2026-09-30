package com.example.docs.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.resources.Identifier;

//#region mixin_accessors_constructor_invoker_example
@Mixin(Identifier.class)
public interface IdentifierAccessor {
	@Invoker("<init>")
	static Identifier newIdentifier(String namespace, String path) {
		throw new AssertionError("Untransformed @Accessor");
	}
}
//#endregion mixin_accessors_constructor_invoker_example
