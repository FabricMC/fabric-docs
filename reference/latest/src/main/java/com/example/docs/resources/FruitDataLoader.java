package com.example.docs.resources;

import com.example.docs.FabricDocsReference;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;

import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class FruitDataLoader extends JsonDataLoader {

	//:::1
	// A Logger we'll use for debugging reasons.
	public static final Logger LOGGER = LoggerFactory.getLogger(BookDataLoader.class);
	// A HashMap we'll use to store our data, feel free to use something more advanced.
	public static final HashMap<Identifier, Fruit> DATA = new HashMap<>();
	// A Gson used in our JsonDataLoader, explicitly set to print multiline.
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	//:::1

	//:::2
	public FruitDataLoader() {
		super(GSON, "fruit");
	}
	//:::2

	//:::3
	@Override
	protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
		int it = 0;
		DATA.clear();
		for(Map.Entry<Identifier, JsonElement> entry : prepared.entrySet()) {
			Identifier identifier = entry.getKey();
			JsonElement json = entry.getValue();
			Optional<Fruit> optional = Fruit.deserialize(json);
			if(optional.isPresent()){
				DATA.put(identifier, optional.get());
				it++;
			}
		}
		LOGGER.info("Loaded {} items.", it);
	}
	//:::3

	//:::4
	public static void register(){
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new IdentifiableResourceReloadListener() {

			@Override
			public Identifier getFabricId() {
				return Identifier.of(FabricDocsReference.MOD_ID, "fruit");
			}

			@Override
			public CompletableFuture<Void> reload(Synchronizer synchronizer, ResourceManager manager, Profiler prepareProfiler, Profiler applyProfiler, Executor prepareExecutor, Executor applyExecutor) {
				return new BookDataLoader().reload(synchronizer, manager, prepareProfiler, applyProfiler, prepareExecutor, applyExecutor);
			}
		});
	}
	//:::4
}
