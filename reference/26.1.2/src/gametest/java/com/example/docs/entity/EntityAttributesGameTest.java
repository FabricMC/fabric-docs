package com.example.docs.entity;

import java.lang.reflect.Method;

import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.level.Level;

import net.fabricmc.fabric.api.gametest.v1.CustomTestMethodInvoker;
import net.fabricmc.fabric.api.gametest.v1.GameTest;

import com.example.docs.ExampleMod;
import com.example.docs.entity.attribute.ModAttributes;

public class EntityAttributesGameTest implements CustomTestMethodInvoker {
	@GameTest
	void testReadingAttributes(GameTestHelper helper, LivingEntity entity) {
		// #region reading_entity_attributes
		AttributeInstance attribute = entity.getAttribute(ModAttributes.AGGRO_RANGE); // returns an `AttributeInstance`
		// #endregion reading_entity_attributes

		assertNotNull(attribute, helper, Component.literal("Expected AGGRO_RANGE attribute to be present"));

		// #region reading_entity_attributes
		double value = attribute.getValue(); // returns a double with the current value
		double baseValue = attribute.getBaseValue(); // returns a double with the base value
		// #endregion reading_entity_attributes

		helper.assertValueEqual(8.0, value, "Expected AGGRO_RANGE attribute to have a value of 8.0");
		helper.assertValueEqual(8.0, baseValue, "Expected AGGRO_RANGE attribute to have a base value of 8.0");
		helper.succeed();
	}

	@GameTest
	void testModifyingAttributes(GameTestHelper helper, LivingEntity entity) {
		var attribute = entity.getAttribute(ModAttributes.AGGRO_RANGE);
		assertNotNull(attribute, helper, Component.literal("Expected AGGRO_RANGE attribute to be present"));

		// #region modifying_entity_attributes
		attribute.addPermanentModifier(
				new AttributeModifier(
						Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "increased_range"), // the ID of your modifier, should be static so it can be removed
						8, // how much to modify it
						AttributeModifier.Operation.ADD_VALUE // what operator to use, see the wiki page linked above
				)
		);
		// #endregion modifying_entity_attributes

		helper.assertValueEqual(16.0, attribute.getValue(), "Expected AGGRO_RANGE attribute to have a value of 16.0 after modifier was added");
		helper.succeed();
	}

	private static void assertNotNull(Object object, GameTestHelper helper, Component message) {
		if (object == null) {
			throw new GameTestAssertException(message, (int) helper.getTick());
		}
	}

	@Override
	public void invokeTestMethod(GameTestHelper helper, Method method) throws ReflectiveOperationException {
		Level level = helper.getLevel();
		MiniGolemEntity golem = new MiniGolemEntity(ModEntityTypes.MINI_GOLEM, level);
		level.addFreshEntity(golem);

		method.invoke(this, helper, golem);
	}
}
