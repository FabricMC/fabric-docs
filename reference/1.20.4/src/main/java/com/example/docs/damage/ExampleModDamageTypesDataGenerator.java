package com.example.docs.damage;

import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

public class ExampleModDamageTypesDataGenerator implements DataGeneratorEntrypoint {
	public static final DamageType TATER_DAMAGE_TYPE = new DamageType("tater", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0.1f);

	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		registryBuilder.add(Registries.DAMAGE_TYPE, registerable -> {
			registerable.register(ExampleModDamageTypes.TATER_DAMAGE, TATER_DAMAGE_TYPE);
		});
	}

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(TaterDamageTypesGenerator::new);
		pack.addProvider(TaterDamageTypeTagGenerator::new);
	}

	private static class TaterDamageTypeTagGenerator extends FabricTagProvider<DamageType> {
		TaterDamageTypeTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
			super(output, Registries.DAMAGE_TYPE, registriesFuture);
		}

		@Override
		protected void addTags(HolderLookup.Provider arg) {
			tag(TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("minecraft:bypasses_armor"))).add(ExampleModDamageTypes.TATER_DAMAGE);
		}
	}

	private static class TaterDamageTypesGenerator implements DataProvider {
		private final PackOutput.PathProvider path;

		TaterDamageTypesGenerator(FabricDataOutput fabricDataOutput) {
			path = fabricDataOutput.createPathProvider(PackOutput.Target.DATA_PACK, "damage_type/");
		}

		@Override
		public CompletableFuture<?> run(CachedOutput writer) {
			JsonObject damageTypeObject = new JsonObject();

			damageTypeObject.addProperty("exhaustion", TATER_DAMAGE_TYPE.exhaustion());
			damageTypeObject.addProperty("message_id", TATER_DAMAGE_TYPE.msgId());
			damageTypeObject.addProperty("scaling", TATER_DAMAGE_TYPE.scaling().getSerializedName());

			return DataProvider.saveStable(writer, damageTypeObject, path.json(new ResourceLocation("example-mod", "tater")));
		}

		@Override
		public String getName() {
			return "Damage Type";
		}
	}
}
