package com.example.docs.attachment;

import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;

// :::stamina
public class Stamina {
	private static final AttachmentType<Integer> CURRENT_STAMINA = AttachmentRegistry.create(
					ResourceLocation.fromNamespaceAndPath("example-mod", "current_stamina"),
					builder -> builder.syncWith(ByteBufCodecs.INT, AttachmentSyncPredicate.all())
	);
	private static final AttachmentType<Integer> MAX_STAMINA = AttachmentRegistry.create(
					ResourceLocation.fromNamespaceAndPath("example-mod", "max_stamina"),
					builder -> builder.syncWith(ByteBufCodecs.INT, AttachmentSyncPredicate.all())
	);

	public static StaminaData get(AttachmentTarget target) {
		return new StaminaData(target);
	}

	public record StaminaData(AttachmentTarget target) {
		public int getCurrentStamina() {
			return target.getAttachedOrElse(CURRENT_STAMINA, 0);
		}

		public int decrementCurrentStamina() {
			return target.modifyAttached(CURRENT_STAMINA, currentStamina -> currentStamina-1);
		}

		public void setCurrentStamina(int value) {
			target.setAttached(CURRENT_STAMINA, value);
		}

		public int getMaxStamina() {
			return target.getAttachedOrElse(MAX_STAMINA, 0);
		}

		public void setMaxStamina(int value) {
			target.setAttached(MAX_STAMINA, value);
		}
	}
}
// :::stamina
