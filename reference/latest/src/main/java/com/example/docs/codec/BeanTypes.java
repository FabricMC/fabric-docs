package com.example.docs.codec;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;

// :::
// An empty class to hold static references to all BeanTypes
public class BeanTypes {
	// Make sure to register the bean types and leave them accessible to
	// the getType method in their respective subclasses.
	public static final BeanType<StringyBean> STRINGY_BEAN = register("stringy_bean", new BeanType<>(StringyBean.CODEC));
	public static final BeanType<CountingBean> COUNTING_BEAN = register("counting_bean", new BeanType<>(CountingBean.CODEC));

	//:::
	public static void register() { }

	//:::
	public static <T extends Bean> BeanType<T> register(String id, BeanType<T> beanType) {
		return Registry.register(BeanType.REGISTRY, Identifier.fromNamespaceAndPath("example", id), beanType);
	}
}
// :::
