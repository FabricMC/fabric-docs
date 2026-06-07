package com.example.docs;

import static com.example.docs.attachment.ExampleModAttachments.EXAMPLE_BLOCK_POS_ATTACHMENT;
import static com.example.docs.attachment.ExampleModAttachments.EXAMPLE_STRING_ATTACHMENT;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;

import com.example.docs.effect.ExampleModEffects;
import com.example.docs.entity.attribute.ModAttributes;

/**
 * A static-first class, used solely to provide version-aware
 * references to internal methods.
 */
public class ReferenceMethods {
	public static void addTaterEffect(LivingEntity entity) {
		// #region apply_effect
		var instance = new MobEffectInstance(ExampleModEffects.TATER, 5 * 20, 0, false, true, true);
		entity.addEffect(instance);
		// #endregion apply_effect
	}

	public static void entityAttachments(Entity entity) {
		// #region reading_entity_attachments
		// Checks if the given AttachmentType has attached data, returning a boolean.
		entity.hasAttached(EXAMPLE_STRING_ATTACHMENT);

		// Gets the data associated with the given AttachmentType, or `null` if it doesn't exist.
		entity.getAttached(EXAMPLE_STRING_ATTACHMENT);

		// Gets the data associated with the given AttachmentType, throwing a `NullPointerException` if it doesn't exist.
		entity.getAttachedOrThrow(EXAMPLE_STRING_ATTACHMENT);

		// Gets the data associated with the given AttachmentType, setting the value if it doesn't exist.
		entity.getAttachedOrSet(EXAMPLE_STRING_ATTACHMENT, "basic");
		entity.getAttachedOrSet(EXAMPLE_BLOCK_POS_ATTACHMENT, new BlockPos(0, 0, 0));

		// Gets the data associated with the given AttachmentType, returning the provided value if it doesn't exist.
		entity.getAttachedOrElse(EXAMPLE_STRING_ATTACHMENT, "basic");
		entity.getAttachedOrElse(EXAMPLE_BLOCK_POS_ATTACHMENT, new BlockPos(0, 0, 0));
		// #endregion reading_entity_attachments

		// #region writing_entity_attachments
		// Sets the data associated with the given AttachmentType, returning the previous value.
		entity.setAttached(EXAMPLE_STRING_ATTACHMENT, "new value");

		// Modifies the data associated with the given AttachmentType in place, returning the currently attached value. Note that currentValue is null if there is no previously attached data.
		entity.modifyAttached(EXAMPLE_STRING_ATTACHMENT, currentValue -> "The length was " + (currentValue == null ? 0 : currentValue.length()));

		// Removes the data associated with the given AttachmentType, returning the previous value.
		entity.removeAttached(EXAMPLE_STRING_ATTACHMENT);
		// #endregion writing_entity_attachments
	}

	// #region saved_data_example_scenario
	private static int blocksBroken = 0; // keeps track of the number of blocks broken
	// #endregion saved_data_example_scenario

	public static void savedDataExampleScenario() {
		// #region saved_data_example_scenario

		PlayerBlockBreakEvents.AFTER.register((level, player, pos, state, blockEntity) -> {
			blocksBroken++; // increment the counter each time a block is broken
			player.sendSystemMessage(Component.literal("Blocks broken: " + blocksBroken));
		});
		// #endregion saved_data_example_scenario
	}

	// #region applying_entity_attributes
	public static AttributeSupplier.Builder createEntityAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 25.0)
				.add(Attributes.MOVEMENT_SPEED, 0.22)
				.add(Attributes.ATTACK_DAMAGE, 3.0)
				.add(ModAttributes.AGGRO_RANGE, 8.0);
	}
	// #endregion applying_entity_attributes

	public static void readingEntityAttributes(LivingEntity entity) {
		// #region reading_entity_attributes
		entity.getAttribute(ModAttributes.AGGRO_RANGE); // returns an `AttributeInstance`
		entity.getAttributeValue(ModAttributes.AGGRO_RANGE); // returns a double with the current value
		entity.getAttributeBaseValue(ModAttributes.AGGRO_RANGE); // returns a double with the base value
		// #endregion reading_entity_attributes
	}

	public static void modifyingEntityAttributes(AttributeInstance attribute) {
		// #region modifying_entity_attributes
		attribute.addPermanentModifier(
				new AttributeModifier(
						Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "increased_range"), // the ID of your modifier, should be static so it can be removed
						8, // how much to modify it
						AttributeModifier.Operation.ADD_VALUE // what operator to use, see the wiki page linked above
				));
		// #endregion modifying_entity_attributes
	}
}
