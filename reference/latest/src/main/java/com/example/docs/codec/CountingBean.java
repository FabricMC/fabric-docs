package com.example.docs.codec;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

// #region counting-bean-class
// Another implementation
public class CountingBean implements Bean {
	public static final MapCodec<CountingBean> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			Codec.INT.fieldOf("counting_number").forGetter(CountingBean::getCountingNumber)
	).apply(instance, CountingBean::new));

	private int countingNumber;
	// #endregion counting-bean-class

	public CountingBean(int countingNumber) {
		this.countingNumber = countingNumber;
	}

	public int getCountingNumber() {
		return this.countingNumber;
	}

	// #region counting-bean-class

	@Override
	public BeanType<?> getType() {
		return BeanTypes.COUNTING_BEAN;
	}
}
// #endregion counting-bean-class
