package com.example.docs.mixin.accessor;

import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

//#region mixin_accessors_static_field_accessor_example
@Mixin(ClientboundCustomPayloadPacket.class)
public interface ClientboundCustomPayloadPacketAccessor {
	@Accessor("MAX_PAYLOAD_SIZE")
	static int getMaxPayloadSize() {
		throw new AssertionError("Untransformed @Accessor");
	}

	@Accessor("MAX_PAYLOAD_SIZE")
	static void setMaxPayloadSize(int size) {
		throw new AssertionError("Untransformed @Accessor");
	}
}
//#endregion mixin_accessors_static_field_accessor_example
