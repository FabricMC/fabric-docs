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
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;

// #region automatic-testing--4
public class BeanTypeTest {
	private static final Gson GSON = new GsonBuilder().create();

	@BeforeAll
	static void beforeAll() {
		// #endregion automatic-testing--4
		// #region automatic-testing--7
		SharedConstants.tryDetectVersion();
		Bootstrap.bootStrap();
		// #endregion automatic-testing--7
		// #region automatic-testing--4
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
		ItemStackTemplate diamondStack = new ItemStackTemplate(Items.DIAMOND, 65);

		Assertions.assertTrue(diamondStack.is(Items.DIAMOND));
		Assertions.assertEquals(65, diamondStack.count());
	}
}
// #endregion automatic-testing--4
