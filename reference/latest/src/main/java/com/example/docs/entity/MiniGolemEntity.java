package com.example.docs.entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

//:::registerclass
public class MiniGolemEntity extends PathfinderMob {
	//:::registerclass
	private static final EntityDataAccessor<Boolean> DANCING = SynchedEntityData.defineId(MiniGolemEntity.class, EntityDataSerializers.BOOLEAN);
	public final AnimationState dancingAnimationState = new AnimationState();
	private int dancingTimeLeft;
	//:::registerclass
	public MiniGolemEntity(Level world) {
		this(ModEntityTypes.MINI_GOLEM, world);
	}

	public MiniGolemEntity(EntityType<? extends MiniGolemEntity> entityType, Level world) {
		super(entityType, world);

	}

	public static AttributeSupplier.Builder createCubeAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 5)
				.add(Attributes.TEMPT_RANGE, 10)
				.add(Attributes.MOVEMENT_SPEED, 0.3);
	}
//:::registerclass

//:::goals
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new TemptGoal(this, 1, Ingredient.of(Items.WHEAT), false));
		this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1));
		this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Cow.class, 4));
		this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
	}
//:::goals


	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(DANCING, false);
	}

	public boolean isDancing() {
		return entityData.get(DANCING);
	}

	private void setDancing(boolean dancing) {
		entityData.set(DANCING, dancing);
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> data) {
		super.onSyncedDataUpdated(data);

		if (data == DANCING) {
			dancingAnimationState.animateWhen(isDancing(), this.tickCount);
		}
	}

	@Override
	public void tick() {
		super.tick();

		if (!level().isClientSide()) {
			if (isDancing()) {
				if (dancingTimeLeft-- <= 0) {
					setDancing(false);
				}
			} else {
				if (this.random.nextInt(1000) == 0) {
					setDancing(true);
					dancingTimeLeft = 100 + this.random.nextInt(100);
				}
			}
		}
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput valueOutput) {
		super.addAdditionalSaveData(valueOutput);
		valueOutput.putInt("dancing_time_left", dancingTimeLeft);
	}

	@Override
	protected void readAdditionalSaveData(ValueInput valueInput) {
		super.readAdditionalSaveData(valueInput);
		dancingTimeLeft = valueInput.getInt("dancing_time_left").orElse(0);
		setDancing(dancingTimeLeft > 0);
	}
}
