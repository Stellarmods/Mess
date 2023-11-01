package com.stellermods.mess.entity.entity;

import com.stellermods.mess.entity.EntityTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ManulEntity extends Animal implements GeoEntity, NeutralMob {

    @javax.annotation.Nullable
    private UUID persistentAngerTarget;
    private int remainingPersistentAngerTime;
    private List<LivingEntity> entities = new ArrayList<>();

    AnimatableInstanceCache  factory = GeckoLibUtil.createInstanceCache(this);

    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);

    public ManulEntity(EntityType<? extends Animal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }

    @Override
    public boolean hurt(DamageSource p_27567_, float p_27568_) {
        entities.add((LivingEntity) p_27567_.getDirectEntity());
        return super.hurt(p_27567_, p_27568_);
    }

    @Override
    public boolean isAngryAt(LivingEntity p_21675_) {
        if (entities.contains(p_21675_)){
            return true;
        }
        return false;
    }

    @Override
    public void playerDied(Player p_21677_) {
        entities.remove(p_21677_);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.0D, false));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(1, new ResetUniversalAngerTargetGoal<>(this, true));
        this.goalSelector.addGoal(2, new FloatGoal(this));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.1D, Ingredient.of(Items.RABBIT), false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        super.registerGoals();
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.25f).build();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return EntityTypes.MANUL.get().create(p_146743_);
    }

    @Override
    public boolean canMate(Animal p_27569_) {
        return true;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_146746_, DifficultyInstance p_146747_, MobSpawnType p_146748_, @Nullable SpawnGroupData p_146749_, @Nullable CompoundTag p_146750_) {
        return super.finalizeSpawn(p_146746_, p_146747_, p_146748_, p_146749_, p_146750_);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    public boolean canBreed() {
        return super.canBreed();
    }

    @Override
    public int getAge() {
        return super.getAge();
    }

    @Override
    public void ageUp(int p_146741_, boolean p_146742_) {
        super.ageUp(p_146741_, p_146742_);
    }

    @Override
    public void ageUp(int p_146759_) {
        super.ageUp(p_146759_);
    }

    @Override
    public void setAge(int p_146763_) {
        super.setAge(p_146763_);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> p_146754_) {
        super.onSyncedDataUpdated(p_146754_);
    }

    @Override
    protected void ageBoundaryReached() {
        super.ageBoundaryReached();
    }

    @Override
    public boolean isBaby() {
        return super.isBaby();
    }

    @Override
    public void setBaby(boolean p_146756_) {
        super.setBaby(p_146756_);
    }

    @Override
    public boolean isFood(ItemStack p_27600_) {
        if (p_27600_.getItem() == Items.RABBIT){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    @Override
    public void setRemainingPersistentAngerTime(int p_21673_) {
        this.remainingPersistentAngerTime = p_21673_;
    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID p_21672_) {
        this.persistentAngerTarget = p_21672_;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    private <E extends ManulEntity> PlayState predicate(AnimationState<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay("Walk"));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(RawAnimation.begin().thenPlay("Idle"));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controllerMove",
                0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }
}
