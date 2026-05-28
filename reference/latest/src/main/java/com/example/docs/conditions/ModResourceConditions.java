package com.example.docs.conditions;

import com.example.docs.ExampleMod;

import com.mojang.serialization.MapCodec;

import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;

import net.minecraft.resources.Identifier;

//#region create
public class ModResourceConditions {
	//#endregion create
	//#region register
	public static final ResourceConditionType<TagsEmptyResourceCondition> TAGS_EMPTY =
					createResourceConditionType("tags_empty", TagsEmptyResourceCondition.CODEC);

	//#endregion register

	//#region create
	private static <T extends ResourceCondition> ResourceConditionType<T> createResourceConditionType(String name, MapCodec<T> codec) {
		return ResourceConditionType.create(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, name), codec);
	}
	//#endregion create

	//#region register
	public static void register() {
		ResourceConditions.register(TAGS_EMPTY);
	}
	//#endregion register

	//#region create
}
//#endregion create

