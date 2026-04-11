package com.example.docs.saveddata;

import com.mojang.serialization.Codec;

import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import com.example.docs.ExampleMod;

// #region basic-structure
public class SavedBlockData extends SavedData {
	private int blocksBroken = 0;

	// #endregion basic-structure
	// #region codec
	private static final Codec<SavedBlockData> CODEC = Codec.INT.xmap(
					SavedBlockData::new, // Create a new 'SavedBlockData' from the stored number.
					SavedBlockData::getBlocksBroken // Return the number from the 'SavedBlockData' to be saved/
	);
	// #endregion codec
	// #region type
	private static final SavedDataType<SavedBlockData> TYPE = new SavedDataType<>(
					Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "saved_block_data"), // The unique name for this saved data.
					SavedBlockData::new, // If there's no 'SavedBlockData', yet create one and refresh fields.
					CODEC, // The codec used for serialization/deserialization.
					null // A data fixer, which is not needed here.
	);
	// #endregion type
	// #region basic-structure
	public SavedBlockData() {
	}
	// #endregion basic-structure

	// #region ctor
	public SavedBlockData(int count) {
		blocksBroken = count;
	}
	// #endregion ctor
	// #region basic-structure

	public int getBlocksBroken() {
		return blocksBroken;
	}

	// #region set-dirty
	public void incrementBlocksBroken() {
		blocksBroken++;

		// If saved data is not marked dirty, nothing will be saved when Minecraft closes.
		setDirty();
	}
	// #endregion set-dirty
	// #endregion basic-structure
	// #region method
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
		return level.getDataStorage().computeIfAbsent(TYPE);
	}
	// #endregion method
	// #region basic-structure
}
// #endregion basic-structure
