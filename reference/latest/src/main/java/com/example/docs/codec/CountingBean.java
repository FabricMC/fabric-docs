package com.example.docs.codec;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

// #region counting-bean
// Another implementation
public class CountingBean implements Bean {
	public static final MapCodec<CountingBean> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			Codec.INT.fieldOf("counting_number").forGetter(CountingBean::getCountingNumber)
	).apply(instance, CountingBean::new));

	private int countingNumber;
	// #endregion counting-bean

	public CountingBean(int countingNumber) {
		this.countingNumber = countingNumber;
	}

	public int getCountingNumber() {
		return countingNumber;
	}

	// #region counting-bean

	@Override
	public BeanType<?> getType() {
		return BeanTypes.COUNTING_BEAN;
	}
}
// #endregion counting-bean
