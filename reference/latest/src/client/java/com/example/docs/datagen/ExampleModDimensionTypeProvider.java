package com.example.docs.datagen;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.example.docs.ExampleMod;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TimelineTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.attribute.AmbientSounds;
import net.minecraft.world.attribute.BackgroundMusic;
import net.minecraft.world.attribute.BedRule;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.clock.WorldClocks;
import net.minecraft.world.level.CardinalLighting;
import net.minecraft.world.level.dimension.DimensionType;

// :::datagen-dimension:dimensionType
public class ExampleModDimensionTypeProvider extends FabricDynamicRegistryProvider {
	// :::datagen-dimension:dimensionType

	// :::datagen-dimension:dimensionKey
	public static final ResourceKey<DimensionType> EXAMPLE_DIMENSION_TYPE_KEY = ResourceKey.create(
					Registries.DIMENSION_TYPE,
					Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "example_dimension_type")
	);
	// :::datagen-dimension:dimensionKey

	// :::datagen-dimension:dimensionType
	public ExampleModDimensionTypeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(HolderLookup.Provider registries, Entries entries) {
		entries.addAll(registries.lookupOrThrow(Registries.DIMENSION_TYPE));
	}
	// :::datagen-dimension:dimensionType

	// :::datagen-dimension:bootstrap
	public static void bootstrapDimension(BootstrapContext<DimensionType> context) {
		// :::datagen-dimension:bootstrap

		// :::datagen-dimension:attributes
		EnvironmentAttributeMap attributes = EnvironmentAttributeMap.builder()
						.set(EnvironmentAttributes.FOG_COLOR,           0xC0D8FF)
						.set(EnvironmentAttributes.SKY_COLOR,           0x78A7FF)
						.set(EnvironmentAttributes.CLOUD_COLOR,         0xCCFFFFFF)
						.set(EnvironmentAttributes.CLOUD_HEIGHT,        192.33f)
						.set(EnvironmentAttributes.AMBIENT_LIGHT_COLOR, 0x0A0A0A)
						.set(EnvironmentAttributes.WATER_FOG_COLOR,     0x050533)
						.set(EnvironmentAttributes.AMBIENT_SOUNDS,      AmbientSounds.EMPTY)
						.set(EnvironmentAttributes.BACKGROUND_MUSIC,    BackgroundMusic.EMPTY)
						.set(EnvironmentAttributes.BED_RULE,            BedRule.CAN_SLEEP_WHEN_DARK)
						.set(EnvironmentAttributes.NETHER_PORTAL_SPAWNS_PIGLINS, true)
						.set(EnvironmentAttributes.RESPAWN_ANCHOR_WORKS, false)
						.build();
		// :::datagen-dimension:attributes

		// :::datagen-dimension:customizeDimensionType
		DimensionType dimensionType = new DimensionType(
						false,                                  // hasFixedTime
						true,                                   // hasSkyLight
						false,                                  // hasCeiling
						false,                                  // hasEnderDragonFight
						1.0,                                    // coordinateScale
						-64,                                    // minY (you had 0, overworld is -64)
						384,                                    // height (you had 256, overworld is 384)
						384,                                    // logicalHeight (you had 256)
						BlockTags.INFINIBURN_OVERWORLD,         // infiniburn
						0.0f,                                   // ambientLight
						new DimensionType.MonsterSettings(
										UniformInt.of(0, 7),            // monsterSpawnLightTest
										0                               // monsterSpawnBlockLightLimit
						),
						DimensionType.Skybox.OVERWORLD,         // skybox
						CardinalLighting.Type.DEFAULT,          // cardinalLightType
						attributes,         									  // attributes
						context.lookup(Registries.TIMELINE).getOrThrow(TimelineTags.IN_OVERWORLD),
						Optional.of(context.lookup(Registries.WORLD_CLOCK).getOrThrow(WorldClocks.OVERWORLD))
		);
		// :::datagen-dimension:customizeDimensionType

		// :::datagen-dimension:dimensiontypeContextRegister
		context.register(EXAMPLE_DIMENSION_TYPE_KEY, dimensionType);
		// :::datagen-dimension:dimensiontypeContextRegister

		// :::datagen-dimension:bootstrap
	}
	// :::datagen-dimension:bootstrap

	// :::datagen-dimension:dimensionType

	@Override
	public String getName() {
		return "ExampleMod Dimension Type Provider";
	}
}
// :::datagen-dimension:dimensionType
