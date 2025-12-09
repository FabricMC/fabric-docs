package com.example.docs.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.util.math.BlockPos;

// :::1
public class BlockPosArgumentType implements ArgumentType<BlockPos> {
	/**
	 * Parse the BlockPos from the reader in the {x, y, z} format.
	 */
	@Override
	public BlockPos parse(StringReader reader) throws CommandSyntaxException {
		try {
			// This requires the argument to be surrounded by quotation marks.
			// eg: "{1, 2, 3}"
			String string = reader.readString();

			// Remove the { and } from the string using regex.
			string = string.replace("{", "").replace("}", "");

			// Split the string into the x, y, and z values.
			String[] split = string.split(",");

			// Parse the x, y, and z values from the split string.
			int x = Integer.parseInt(split[0].trim());
			int y = Integer.parseInt(split[1].trim());
			int z = Integer.parseInt(split[2].trim());

			// Return the BlockPos.
			return new BlockPos(x, y, z);
		} catch (Exception e) {
			// Throw an exception if anything fails inside the try block.
			throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherParseException().create("Invalid BlockPos format. Expected {x, y, z}");
		}
	}
}
// :::1
