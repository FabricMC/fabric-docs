package com.example.docs;

import java.lang.reflect.Method;

import net.minecraft.block.Blocks;
import net.minecraft.test.TestContext;

import net.fabricmc.fabric.api.gametest.v1.CustomTestMethodInvoker;
import net.fabricmc.fabric.api.gametest.v1.GameTest;

public class FabricDocsGameTest implements CustomTestMethodInvoker {
	@GameTest
	public void test(TestContext context) {
		context.expectBlock(Blocks.AIR, 0, 0, 0);
		context.complete();
	}

	@Override
	public void invokeTestMethod(TestContext context, Method method) throws ReflectiveOperationException {
		context.setBlockState(0, 0, 0, Blocks.AIR);
		method.invoke(this, context);
	}
}
