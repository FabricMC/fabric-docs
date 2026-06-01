package com.example.docs.conditions;

import java.time.DateTimeException;
import java.time.MonthDay;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jspecify.annotations.Nullable;

import net.minecraft.resources.RegistryOps;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.SpecialDates;

import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;

//#region record
public record DateMatchesResourceCondition(int month, int day) implements ResourceCondition {
	//#endregion record
	//#region codec
	public static final MapCodec<DateMatchesResourceCondition> CODEC = RecordCodecBuilder.<DateMatchesResourceCondition>mapCodec(instance -> instance.group(
					ExtraCodecs.POSITIVE_INT.fieldOf("month").forGetter(DateMatchesResourceCondition::month),
					ExtraCodecs.POSITIVE_INT.fieldOf("day").forGetter(DateMatchesResourceCondition::day)
	).apply(instance, DateMatchesResourceCondition::new)).validate(DateMatchesResourceCondition::validate);
	//#endregion codec

	//#region validate
	private static DataResult<DateMatchesResourceCondition> validate(DateMatchesResourceCondition o) {
		try {
			MonthDay.of(o.month(), o.day());
		} catch (DateTimeException e) {
			return DataResult.error(e::getMessage);
		}

		return DataResult.success(o);
	}
	//#endregion validate

	@Override
	//#region type
	public ResourceConditionType<?> getType() {
		return ModResourceConditions.DATE_MATCHES;
	}
	//#endregion type

	@Override
	//#region test
	public boolean test(RegistryOps.@Nullable RegistryInfoLookup registryInfo) {
		var monthDay = MonthDay.of(this.month, this.day);
		return SpecialDates.dayNow().equals(monthDay);
	}
	//#endregion test
	//#region record
}
//#endregion record
