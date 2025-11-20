package com.example.docs.datagen;

import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonObject;

import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.entity.damage.DamageScaling;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

import com.example.docs.damage.FabricDocsReferenceDamageTypes;

public class FabricDocsReferenceDamageTypesProvider {
	public static final DamageType TATER_DAMAGE_TYPE = new DamageType("tater", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0.1f);

	public static class TaterDamageTypeTagGenerator extends FabricTagProvider<DamageType> {
		TaterDamageTypeTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
			super(output, RegistryKeys.DAMAGE_TYPE, registriesFuture);
		}

		@Override
		protected void configure(RegistryWrapper.WrapperLookup arg) {
			builder(TagKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of("minecraft:bypasses_armor"))).add(FabricDocsReferenceDamageTypes.TATER_DAMAGE);
		}
	}

	public static class TaterDamageTypesGenerator implements DataProvider {
		private final DataOutput.PathResolver path;

		TaterDamageTypesGenerator(FabricDataOutput fabricDataOutput) {
			path = fabricDataOutput.getResolver(DataOutput.OutputType.DATA_PACK, "damage_type/");
		}

		@Override
		public CompletableFuture<?> run(DataWriter writer) {
			JsonObject damageTypeObject = new JsonObject();

			damageTypeObject.addProperty("exhaustion", TATER_DAMAGE_TYPE.exhaustion());
			damageTypeObject.addProperty("message_id", TATER_DAMAGE_TYPE.msgId());
			damageTypeObject.addProperty("scaling", TATER_DAMAGE_TYPE.scaling().asString());

			return DataProvider.writeToPath(writer, damageTypeObject, path.resolveJson(Identifier.of("fabric-docs-reference", "tater")));
		}

		@Override
		public String getName() {
			return "Damage Type";
		}
	}
}
