package com.example.docs.block.entity.custom;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import com.example.docs.block.entity.ModBlockEntities;
import com.example.docs.networking.payload.EngineSoundInstancePacket;
import com.example.docs.sound.DynamicSoundSource;

public class EngineBlockEntity extends BlockEntity implements DynamicSoundSource {
	public static final int MAX_FUEL = 200;
	private int tick = -1;    // starts turned off
	private int fuel = 0;
	private float normalizedStress = 0;

	public EngineBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.ENGINE_BLOCK_ENTITY, pos, state);
	}

	public void setTick(int tick) {
		this.tick = tick;
	}

	@Override
	public int getTick() {
		return this.tick;
	}

	public static void tick(World world, BlockPos pos, BlockState state, EngineBlockEntity engineBlockEntity) {
		if (engineBlockEntity.getTick() < 0) return;

		engineBlockEntity.setTick(engineBlockEntity.getTick() + 1);
		engineBlockEntity.setFuelIfPossible(engineBlockEntity.getFuel() - 1);
		engineBlockEntity.setNormalizedStress(engineBlockEntity.getNormalizedStress() - 0.02f);

		if (!world.isClient() && engineBlockEntity.getFuel() > 0) {
			PlayerLookup.tracking(engineBlockEntity).forEach(player -> {
				String engineState = "Engine Fuel: %s  | Stress: %s".formatted(
						engineBlockEntity.getFuel(),
						String.format("%.02f", engineBlockEntity.getNormalizedStress())
				);
				player.sendMessage(Text.literal(engineState), true);
			});
		}

		if (engineBlockEntity.getFuel() <= 0) {
			engineBlockEntity.turnOff();
			engineBlockEntity.setFuelIfPossible(0);
		}
	}

	@Override
	public float getNormalizedStress() {
		return MathHelper.clamp(normalizedStress, 0, 1);
	}

	public void setNormalizedStress(float normalizedStress) {
		this.normalizedStress = Math.clamp(normalizedStress, 0, 1);
	}

	public int getFuel() {
		return MathHelper.clamp(this.fuel, 0, MAX_FUEL);
	}

	public boolean setFuelIfPossible(int fuel) {
		boolean consumeItem = this.getFuel() != MAX_FUEL;
		this.fuel = MathHelper.clamp(fuel, 0, MAX_FUEL);
		return consumeItem;
	}

	@Override
	public Vec3d getPosition() {
		return this.getPos().toCenterPos();
	}

	public void turnOn() {
		if (this.getFuel() > 0) {
			this.setTick(0);
			this.setNormalizedStress(0);
			this.sendPacketToTrackingClients(new EngineSoundInstancePacket(true, this.getPos()));
			this.syncToChunk();
		}
	}

	public void turnOff() {
		this.tick = -1;
		this.sendPacketToTrackingClients(new EngineSoundInstancePacket(false, this.getPos()));
		this.syncToChunk();
	}

	public boolean isRunning() {
		return this.getTick() > -1;
	}

	public void sendPacketToTrackingClients(CustomPayload payload) {
		if (payload == null || !(this.getWorld() instanceof ServerWorld)) return;
		PlayerLookup.tracking(this).forEach(player -> ServerPlayNetworking.send(player, payload));
	}

	// S2C BlockEntity sync boilerplate
	public void syncToChunk() {
		if (!(getWorld() instanceof ServerWorld serverWorld)) return;
		serverWorld.getChunkManager().markForUpdate(this.getPos());
	}

	@Nullable
	@Override
	public Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}

	@Override
	public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
		return createNbt(registryLookup);
	}
}
