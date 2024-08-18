package com.example.docs.resources;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.Map;

// An empty class, used exclusively for guidance.
// :::1
public class EmptyDataLoader extends JsonDataLoader {

	public EmptyDataLoader(Gson gson, String dataType) {
		super(gson, dataType);
	}

	@Override
	protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {

	}
}
//:::1