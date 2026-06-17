package com.example.docs.saveddata;

import com.mojang.serialization.Codec;

import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import com.example.docs.ExampleMod;

// #region basic_structure
public class SavedBlockData extends SavedData {
	private int blocksBroken = 0;

	// #endregion basic_structure
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
	// #region basic_structure
	public SavedBlockData() {
	}
	// #endregion basic_structure

	// #region ctor
	public SavedBlockData(int count) {
		this.blocksBroken = count;
	}
	// #endregion ctor
	// #region basic_structure

	public int getBlocksBroken() {
		return this.blocksBroken;
	}

	// #region set_dirty
	public void incrementBlocksBroken() {
		this.blocksBroken++;

		// If saved data is not marked dirty, nothing will be saved when Minecraft closes.
		setDirty();
	}
	// #endregion set_dirty
	// #endregion basic_structure
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
	// #region basic_structure
}
// #endregion basic_structure
