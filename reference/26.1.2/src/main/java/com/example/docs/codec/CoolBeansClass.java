package com.example.docs.codec;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;

// #region bean_class
public class CoolBeansClass {
	// #endregion bean_class
	// #region bean_codec
	public static final Codec<CoolBeansClass> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			// Up to 16 fields can be declared here
			Codec.INT.fieldOf("beans_amount").forGetter(CoolBeansClass::getBeansAmount),
			Item.CODEC.fieldOf("bean_type").forGetter(CoolBeansClass::getBeanType),
			BlockPos.CODEC.listOf().fieldOf("bean_positions").forGetter(CoolBeansClass::getBeanPositions)
	)
			.apply(instance, CoolBeansClass::new));
	// #endregion bean_codec
	// #region bean_class
	private final int beansAmount;
	private final Holder<Item> beanType;
	private final List<BlockPos> beanPositions;

	public CoolBeansClass(int beansAmount, Holder<Item> beanType, List<BlockPos> beanPositions) {
		// ...
		// #endregion bean_class
		this.beansAmount = beansAmount;
		this.beanType = beanType;
		this.beanPositions = beanPositions;
		// #region bean_class
	}

	public int getBeansAmount() {
		return this.beansAmount;
	}

	public Holder<Item> getBeanType() {
		return this.beanType;
	}

	public List<BlockPos> getBeanPositions() {
		return this.beanPositions;
	}
}
// #endregion bean_class
