package com.example.docs.codec;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

// :::
// Another implementation
public class CountingBean implements Bean {
	public static final Codec<CountingBean> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.INT.fieldOf("counting_number").forGetter(CountingBean::getCountingNumber)
	).apply(instance, CountingBean::new));

	private int countingNumber;
	// :::

	public CountingBean(int countingNumber) {
		this.countingNumber = countingNumber;
	}

	public int getCountingNumber() {
		return countingNumber;
	}

	// :::

	@Override
	public BeanType<?> getType() {
		return BeanTypes.COUNTING_BEAN;
	}
}
// :::
