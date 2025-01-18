package com.example.docs.entity;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CustomEntity extends AnimalEntity {

    public final AnimationState idelAnimationState = new AnimationState();
    private int idelAnimationTimeout= 0;

    public CustomEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }


    @Override
    protected void initGoals(){
        this.goalSelector.add(0, new TemptGoal(this,1D,Ingredient.ofItems(Items.WHEAT),false));

        this.goalSelector.add(1, new WanderAroundGoal(this,1D));

        this.goalSelector.add(2, new LookAtEntityGoal(this, CowEntity.class, 4.0F));

        this.goalSelector.add(3, new LookAroundGoal(this));

    }

    /*
    protected void initCustomGoals(){
       // this.goalSelector.add(2,new ZombieAttackGoal(this,1D,false)); //TODO:Custom Goals?
    }*/

    public static DefaultAttributeContainer.Builder createAttribute(){
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH,20)
                .add(EntityAttributes.MOVEMENT_SPEED,1)
                .add(EntityAttributes.FOLLOW_RANGE,10);
    }

    private void setupAAnimationStates(){
        if (this.idelAnimationTimeout <= 0) {
            this.idelAnimationTimeout = 50;
            this.idelAnimationState.start(this.age);
        }
        else {
            --this.idelAnimationTimeout;
        }
    }
    @Override
    public void tick(){
        super.tick();

        if(this.getWorld().isClient()){
            this.setupAAnimationStates();
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.WHEAT);
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return FabricDocsReferanceCustomEntity.CustomEntity.create(world, SpawnReason.MOB_SUMMONED);
    }
}
