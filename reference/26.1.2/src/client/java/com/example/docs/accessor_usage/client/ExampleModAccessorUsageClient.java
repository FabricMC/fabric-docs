package com.example.docs.accessor_usage.client;

import net.minecraft.client.gui.Gui;

import com.example.docs.mixin.client.accessor.GuiAccessor;

/*
Class to hold example code for using the example accessor mixins,
which happen to be in the client sourceset
*/
@SuppressWarnings("unused")
final class ExampleModAccessorUsageClient {
	private ExampleModAccessorUsageClient() {
	}

	//#region mixin_accessors_instance_field_accessor_example_usage
	void exampleInstanceFieldAccessorUsage(Gui gui, int newMessageTime) {
		int oldMessageTime = ((GuiAccessor) gui).example_mod$getOverlayMessageTime();

		((GuiAccessor) gui).example_mod$setOverlayMessageTime(newMessageTime);
	}
	//#endregion mixin_accessors_instance_field_accessor_example_usage
}
