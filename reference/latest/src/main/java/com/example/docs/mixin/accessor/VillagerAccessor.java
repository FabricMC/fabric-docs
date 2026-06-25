package com.example.docs.mixin.accessor;

import net.minecraft.world.entity.npc.villager.Villager;

import net.minecraft.world.item.Item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

//#region mixin_accessors_mutable_setter_example
@Mixin(Villager.class)
public interface VillagerAccessor {
	@Accessor("FOOD_POINTS")
	@Mutable
	void example_mod$setItemFoodValues(Map<Item, Integer> items);
}
//#endregion mixin_accessors_mutable_setter_example
