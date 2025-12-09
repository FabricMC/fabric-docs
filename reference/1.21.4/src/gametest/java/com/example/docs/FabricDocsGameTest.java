package com.example.docs;

import net.minecraft.block.Blocks;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;

import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;

public class FabricDocsGameTest implements FabricGameTest {
	@GameTest(templateName = EMPTY_STRUCTURE)
	public void test(TestContext context) {
		context.expectBlock(Blocks.AIR, 0, 0, 0);
		context.complete();
	}
}
