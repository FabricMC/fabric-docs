package com.example.docs.conditions;

import java.util.Arrays;
import java.util.List;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jspecify.annotations.Nullable;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryOps;
import net.minecraft.tags.TagKey;

import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.fabricmc.fabric.impl.resource.conditions.ResourceConditionsImpl;

// Copy of TagsPopulatedResourceCondition that inverts the output of test for demonstration purposes.
//#region record
public record TagsEmptyResourceCondition(Identifier registry, List<Identifier> tags) implements ResourceCondition {
	//#endregion record
	//#region codec
	public static final MapCodec<TagsEmptyResourceCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
					Identifier.CODEC.fieldOf("registry").orElse(Registries.ITEM.identifier()).forGetter(TagsEmptyResourceCondition::registry),
					Identifier.CODEC.listOf().fieldOf("values").forGetter(TagsEmptyResourceCondition::tags)
	).apply(instance, TagsEmptyResourceCondition::new));
	//#endregion codec

	@SafeVarargs
	public <T> TagsEmptyResourceCondition(Identifier registry, TagKey<T>... tags) {
		this(registry, Arrays.stream(tags).map(TagKey::location).toList());
	}

	@SafeVarargs
	public <T> TagsEmptyResourceCondition(TagKey<T>... tags) {
		this(tags[0].registry().identifier(), Arrays.stream(tags).map(TagKey::location).toList());
	}

	//#region type
	@Override
	public ResourceConditionType<?> getType() {
		return ModResourceConditions.TAGS_EMPTY;
	}
	//#endregion type

	//#region test
	@Override
	public boolean test(RegistryOps.@Nullable RegistryInfoLookup registryInfo) {
		return !ResourceConditionsImpl.tagsPopulated(registryInfo, this.registry(), this.tags());
	}
	//#endregion test
	//#region record
}
//#endregion record
