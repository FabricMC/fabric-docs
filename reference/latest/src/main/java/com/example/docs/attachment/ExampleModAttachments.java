package com.example.docs.attachment;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;

public class ExampleModAttachments {
	// :::string
	public static final AttachmentType<String> EXAMPLE_STRING_ATTACHMENT = AttachmentRegistry.create(
			Identifier.fromNamespaceAndPath("example-mod", "example_string_attachment") // The ID of your Attachment
	);
	// :::string

	// :::pos
	public static final AttachmentType<BlockPos> EXAMPLE_BLOCK_POS_ATTACHMENT = AttachmentRegistry.create(
			Identifier.fromNamespaceAndPath("example-mod", "example_block_pos_attachment"),
			builder -> builder
				.initializer(() -> new BlockPos(0, 0, 0)) // The default value of the Attachment, if one has not been set.
				.syncWith(
					BlockPos.STREAM_CODEC,  // Dictates how to turn the data into a packet to send to clients.
					AttachmentSyncPredicate.all() // Dictates who to send the data to.
				)
	);
	// :::pos

	// :::persistent
	public static final AttachmentType<BlockPos> EXAMPLE_PERSISTENT_ATTACHMENT = AttachmentRegistry.create(
			Identifier.fromNamespaceAndPath("example-mod", "example_block_pos_attachment"),
			builder -> builder
				.initializer(() -> new BlockPos(0, 0, 0)) // The default value of the Attachment, if one has not been set.
				.persistent(BlockPos.CODEC) // Dictates how this Attachment's data should be saved and loaded.
				.copyOnDeath() // Dictates that this Attachment should persist even after the entity dies or converts.
	);
	// :::persistent
}
