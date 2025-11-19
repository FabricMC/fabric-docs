package com.example.docs.saveddata;

import com.mojang.serialization.Codec;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

// :::class
// :::basic_structure
public class SavedBlockData extends SavedData {
	private int blocksBroken = 0;

	// :::basic_structure
	// :::codec
	private static final Codec<SavedBlockData> CODEC = Codec.INT.xmap(
					SavedBlockData::new, // Create a new 'SavedBlockData' from the stored number.
					SavedBlockData::getBlocksBroken // Return the number from the 'SavedBlockData' to be saved/
	);
	// :::codec
	// :::type
	private static final SavedDataType<SavedBlockData> TYPE = new SavedDataType<>(
					"saved_block_data", // A Unique name for this saved data.
					SavedBlockData::new, // If there's no 'SavedBlockData', yet create one and refresh fields.
					CODEC, // The codec used serialization/deserialization.
					null // A data fixer, which is not needed here.
	);
	// :::type
	// :::basic_structure
	public SavedBlockData() {
	}
	// :::basic_structure

	// :::ctor
	public SavedBlockData(int count) {
		blocksBroken = count;
	}
	// :::ctor
	// :::basic_structure

	public int getBlocksBroken() {
		return blocksBroken;
	}

	public void incrementBlocksBroken() {
		blocksBroken++;
	}
	// :::basic_structure
	// :::method
	public static SavedBlockData getSavedBlockData(MinecraftServer server) {
		// This could be either the overworld or another dimension.
		ServerLevel level = server.getLevel(ServerLevel.OVERWORLD);

		if (level == null) {
			return new SavedBlockData(); // Return a new instance if the level is null.
		}

		// The first time the following 'computeIfAbsent' function is called, it creates a new 'SavedBlockData'
		// instance and stores it inside the 'DimensionDataStorage'.
		// Subsequent calls to 'computeIfAbsent' returns the saved 'SavedBlockData' NBT on disk to the Codec in our type,
		// using the Codec to decode the NBT into our saved data.
		SavedBlockData savedData = level.getDataStorage().computeIfAbsent(TYPE);

		// If saved data is not marked dirty, nothing will be saved when Minecraft closes.
		// Technically it's 'cleaner' if you only mark saved data as dirty when there was actually a change,
		// but the vast majority of mod developers are just going to be confused when their data isn't being saved,
		// and so it's best just to 'setDirty' for them.
		savedData.setDirty();

		return savedData;
	}
	// :::method
	// :::basic_structure
}
// :::basic_structure
// :::class
