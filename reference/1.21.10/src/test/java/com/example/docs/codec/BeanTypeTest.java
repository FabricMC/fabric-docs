package com.example.docs.codec;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

// :::automatic-testing:4
public class BeanTypeTest {
	private static final Gson GSON = new GsonBuilder().create();

	@BeforeAll
	static void beforeAll() {
		// :::automatic-testing:4
		// :::automatic-testing:7
		SharedConstants.tryDetectVersion();
		Bootstrap.bootStrap();
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

		Assertions.assertTrue(diamondStack.is(Items.DIAMOND));
		Assertions.assertEquals(65, diamondStack.getCount());
	}
}
// :::automatic-testing:4
