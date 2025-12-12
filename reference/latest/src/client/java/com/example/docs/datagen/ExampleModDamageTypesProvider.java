package com.example.docs.datagen;

import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonObject;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

import com.example.docs.ExampleMod;
import com.example.docs.damage.ExampleModDamageTypes;

public class ExampleModDamageTypesProvider {
	public static final DamageType TATER_DAMAGE_TYPE = new DamageType("tater", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0.1f);

	public static class TaterDamageTypeTagGenerator extends FabricTagProvider<DamageType> {
		TaterDamageTypeTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
			super(output, Registries.DAMAGE_TYPE, registriesFuture);
		}

		@Override
		protected void addTags(HolderLookup.Provider arg) {
			builder(TagKey.create(Registries.DAMAGE_TYPE, Identifier.parse("minecraft:bypasses_armor"))).add(ExampleModDamageTypes.TATER_DAMAGE);
		}
	}

	public static class TaterDamageTypesGenerator implements DataProvider {
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

			return DataProvider.saveStable(writer, damageTypeObject, path.json(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "tater")));
		}

		@Override
		public String getName() {
			return "ExampleModDamageTypesGenerator";
		}
	}
}
