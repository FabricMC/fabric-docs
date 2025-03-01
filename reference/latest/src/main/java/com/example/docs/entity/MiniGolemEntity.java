package com.example.docs.entity;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.world.World;

public class MiniGolemEntity extends PathAwareEntity {
	private static final TrackedData<Boolean> DANCING = DataTracker.registerData(MiniGolemEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	public final AnimationState dancingAnimationState = new AnimationState();
	private int dancingTimeLeft;

	public MiniGolemEntity(World world) {
		this(ModEntityTypes.MINI_GOLEM, world);
	}

	public MiniGolemEntity(EntityType<? extends MiniGolemEntity> entityType, World world) {
		super(entityType, world);
	}

	public static DefaultAttributeContainer.Builder createCubeAttributes() {
		return PathAwareEntity.createMobAttributes()
				.add(EntityAttributes.MAX_HEALTH, 5)
				.add(EntityAttributes.TEMPT_RANGE, 10)
				.add(EntityAttributes.MOVEMENT_SPEED, 0.3);
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(0, new TemptGoal(this, 1, Ingredient.ofItems(Items.WHEAT), false));
		this.goalSelector.add(1, new WanderAroundGoal(this, 1));
		this.goalSelector.add(2, new LookAtEntityGoal(this, CowEntity.class, 4));
		this.goalSelector.add(3, new LookAroundGoal(this));
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);
		builder.add(DANCING, false);
	}

	public boolean isDancing() {
		return dataTracker.get(DANCING);
	}

	private void setDancing(boolean dancing) {
		dataTracker.set(DANCING, dancing);
	}

	@Override
	public void onTrackedDataSet(TrackedData<?> data) {
		super.onTrackedDataSet(data);

		if (data == DANCING) {
			dancingAnimationState.setRunning(isDancing(), this.age);
		}
	}

	@Override
	public void tick() {
		super.tick();

		if (!getWorld().isClient) {
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
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putInt("dancing_time_left", dancingTimeLeft);
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		dancingTimeLeft = nbt.getInt("dancing_time_left");
		setDancing(dancingTimeLeft > 0);
	}
}
