package com.example.docs.codec;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import net.minecraft.Bootstrap;
import net.minecraft.SharedConstants;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

// :::automatic-testing:4
public class BeanTypeTest {
	private static final Gson GSON = new GsonBuilder().create();

	@BeforeAll
	static void beforeAll() {
		// :::automatic-testing:4
		// :::automatic-testing:7
		SharedConstants.createGameVersion();
		Bootstrap.initialize();
		// :::automatic-testing:7
		// :::automatic-testing:4
		BeanTypes.register();
	}

	@Test
	void testBeanCodec() {
		StringyBean expectedBean = new StringyBean("This bean is stringy!");
		Bean actualBean = Bean.BEAN_CODEC.parse(JsonOps.INSTANCE, GSON.fromJson("{\"type\":\"example:stringy_bean\",\"stringy_string\":\"This bean is stringy!\"}", JsonObject.class)).getOrThrow();

		Assertions.assertInstanceOf(StringyBean.class, actualBean);
		Assertions.assertEquals(expectedBean.getType(), actualBean.getType());
		Assertions.assertEquals(expectedBean.getStringyString(), ((StringyBean) actualBean).getStringyString());
	}

	@Test
	void testDiamondItemStack() {
		// I know this isn't related to beans, but I need an example :)
		ItemStack diamondStack = new ItemStack(Items.DIAMOND, 65);

		Assertions.assertTrue(diamondStack.isOf(Items.DIAMOND));
		Assertions.assertEquals(65, diamondStack.getCount());
	}
}
// :::automatic-testing:4

/*
// :::automatic-testing:5
java.lang.ExceptionInInitializerError
	at net.minecraft.item.ItemStack.<clinit>(ItemStack.java:94)
	at com.example.docs.codec.BeanTypeTest.testBeanCodec(BeanTypeTest.java:20)
	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
Caused by: java.lang.IllegalArgumentException: Not bootstrapped (called from registry ResourceKey[minecraft:root / minecraft:game_event])
	at net.minecraft.Bootstrap.createNotBootstrappedException(Bootstrap.java:118)
	at net.minecraft.Bootstrap.ensureBootstrapped(Bootstrap.java:111)
	at net.minecraft.registry.Registries.create(Registries.java:238)
	at net.minecraft.registry.Registries.create(Registries.java:229)
	at net.minecraft.registry.Registries.<clinit>(Registries.java:139)
	... 5 more

Not bootstrapped (called from registry ResourceKey[minecraft:root / minecraft:game_event])
java.lang.IllegalArgumentException: Not bootstrapped (called from registry ResourceKey[minecraft:root / minecraft:game_event])
	at net.minecraft.Bootstrap.createNotBootstrappedException(Bootstrap.java:118)
	at net.minecraft.Bootstrap.ensureBootstrapped(Bootstrap.java:111)
	at net.minecraft.registry.Registries.create(Registries.java:238)
	at net.minecraft.registry.Registries.create(Registries.java:229)
	at net.minecraft.registry.Registries.<clinit>(Registries.java:139)
	at net.minecraft.item.ItemStack.<clinit>(ItemStack.java:94)
	at com.example.docs.codec.BeanTypeTest.testBeanCodec(BeanTypeTest.java:20)
	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
// :::automatic-testing:5
 */
