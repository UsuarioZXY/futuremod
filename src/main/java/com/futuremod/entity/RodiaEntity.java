package com.futuremod.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerLevel;
import javax.annotation.Nullable;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

/** Rodia: bestia alienigena parecida a un dinosaurio. Rapida, fuerte y montura de los Verdianos caballeros. */
public class RodiaEntity extends Monster {

    private static final EntityDataAccessor<Boolean> DATA_BABY =
            SynchedEntityData.defineId(RodiaEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Optional<UUID>> DATA_OWNER =
            SynchedEntityData.defineId(RodiaEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final UUID TAME_SPEED_ID = UUID.fromString("3b6f5a60-8c2d-4e4b-9a31-7d0c1f2e4a14");
    /** Ticks que tarda una cria domesticada en crecer (20 minutos). */
    private static final int GROW_TICKS = 24000;
    private int growTimer = 0;

    private static final UUID BABY_HEALTH_ID = UUID.fromString("3b6f5a60-8c2d-4e4b-9a31-7d0c1f2e4a11");
    private static final UUID BABY_DAMAGE_ID = UUID.fromString("3b6f5a60-8c2d-4e4b-9a31-7d0c1f2e4a12");
    private static final UUID BABY_SPEED_ID = UUID.fromString("3b6f5a60-8c2d-4e4b-9a31-7d0c1f2e4a13");

    public RodiaEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.xpReward = 15;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_BABY, false);
        this.entityData.define(DATA_OWNER, Optional.empty());
    }

    @Override
    public boolean isBaby() {
        return this.entityData.get(DATA_BABY);
    }

    @Override
    public void setBaby(boolean baby) {
        this.entityData.set(DATA_BABY, baby);
        if (!this.level().isClientSide) {
            applyBabyModifier(Attributes.MAX_HEALTH, BABY_HEALTH_ID, "Rodia baby health", -0.7D, baby);
            applyBabyModifier(Attributes.ATTACK_DAMAGE, BABY_DAMAGE_ID, "Rodia baby damage", -0.8D, baby);
            applyBabyModifier(Attributes.MOVEMENT_SPEED, BABY_SPEED_ID, "Rodia baby speed", 0.2D, baby);
            this.setHealth(this.getMaxHealth());
        }
    }

    private void applyBabyModifier(net.minecraft.world.entity.ai.attributes.Attribute attr, UUID id, String name,
                                   double value, boolean baby) {
        AttributeInstance inst = this.getAttribute(attr);
        if (inst == null) return;
        inst.removeModifier(id);
        if (baby) {
            inst.addTransientModifier(new AttributeModifier(id, name, value, AttributeModifier.Operation.MULTIPLY_BASE));
        }
    }

    public boolean isTame() {
        return this.entityData.get(DATA_OWNER).isPresent();
    }

    @Nullable
    public UUID getOwnerUUID() {
        return this.entityData.get(DATA_OWNER).orElse(null);
    }

    @Nullable
    public LivingEntity getOwner() {
        UUID id = getOwnerUUID();
        return id == null ? null : this.level().getPlayerByUUID(id);
    }

    public void tameBy(Player player) {
        this.entityData.set(DATA_OWNER, Optional.of(player.getUUID()));
        applyTameModifier();
        this.setPersistenceRequired();
        this.setTarget(null);
        this.setAggressive(false);
    }

    /** Un Rodia amigo es mas lento que uno salvaje. */
    private void applyTameModifier() {
        AttributeInstance inst = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (inst == null || this.level().isClientSide) return;
        inst.removeModifier(TAME_SPEED_ID);
        if (isTame()) {
            inst.addTransientModifier(new AttributeModifier(TAME_SPEED_ID, "Rodia tame speed", -0.3D,
                    AttributeModifier.Operation.MULTIPLY_BASE));
        }
    }

    /** Cualquier tipo de carne domestica a una cria. */
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        boolean meat = stack.isEdible() && stack.getItem().getFoodProperties() != null
                && stack.getItem().getFoodProperties().isMeat();
        if (meat && this.isBaby() && !this.isTame()) {
            if (!this.level().isClientSide) {
                if (!player.getAbilities().instabuild) stack.shrink(1);
                if (this.random.nextInt(3) == 0) {
                    this.tameBy(player);
                    ((ServerLevel) this.level()).sendParticles(ParticleTypes.HEART, getX(), getY() + 0.6D, getZ(), 8, 0.3D, 0.3D, 0.3D, 0.05D);
                } else {
                    ((ServerLevel) this.level()).sendParticles(ParticleTypes.SMOKE, getX(), getY() + 0.6D, getZ(), 6, 0.3D, 0.3D, 0.3D, 0.02D);
                }
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        if (meat && this.isTame() && this.getHealth() < this.getMaxHealth()) {
            if (!this.level().isClientSide) {
                if (!player.getAbilities().instabuild) stack.shrink(1);
                this.heal(6.0F);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide && this.isTame() && this.isBaby() && ++growTimer >= GROW_TICKS) {
            this.setBaby(false);
            growTimer = 0;
        }
    }

    @Override
    public boolean shouldDespawnInPeaceful() {
        return !this.isTame();
    }

    @Override
    public boolean removeWhenFarAway(double distance) {
        return !this.isTame() && super.removeWhenFarAway(distance);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (DATA_BABY.equals(key)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        EntityDimensions dims = super.getDimensions(pose);
        return isBaby() ? dims.scale(0.55F) : dims;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("IsBaby", this.isBaby());
        if (getOwnerUUID() != null) tag.putUUID("Owner", getOwnerUUID());
        tag.putInt("GrowTimer", growTimer);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setBaby(tag.getBoolean("IsBaby"));
        if (tag.hasUUID("Owner")) {
            this.entityData.set(DATA_OWNER, Optional.of(tag.getUUID("Owner")));
            applyTameModifier();
        }
        growTimer = tag.getInt("GrowTimer");
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason,
                                        @Nullable SpawnGroupData data, @Nullable CompoundTag tag) {
        data = super.finalizeSpawn(level, difficulty, reason, data, tag);
        if (reason == MobSpawnType.NATURAL && this.random.nextFloat() < 0.2F) {
            this.setBaby(true);
        }
        return data;
    }

    public static AttributeSupplier.Builder createRodiaAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.38D)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.ARMOR, 4.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6D);
    }

    public static boolean checkRodiaSpawnRules(EntityType<RodiaEntity> type, ServerLevelAccessor level,
                                               MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getDifficulty() != Difficulty.PEACEFUL
                && Mob.checkMobSpawnRules(type, level, spawnType, pos, random);
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 2;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, true) {
            @Override
            public boolean canUse() {
                return !RodiaEntity.this.isBaby() && super.canUse();
            }

            @Override
            public boolean canContinueToUse() {
                return !RodiaEntity.this.isBaby() && super.canContinueToUse();
            }
        });
        this.goalSelector.addGoal(4, new FollowOwnerGoal());
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.9D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new DefendOwnerGoal());
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this) {
            @Override
            public boolean canUse() {
                return !RodiaEntity.this.isBaby() && super.canUse();
            }
        }.setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true) {
            @Override
            public boolean canUse() {
                return !RodiaEntity.this.isBaby() && !RodiaEntity.this.isTame() && super.canUse();
            }
        });
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false,
                target -> !AlienEntity.isKin(target) && !(target instanceof ArmorStand)) {
            @Override
            public boolean canUse() {
                return !RodiaEntity.this.isBaby() && !RodiaEntity.this.isTame() && super.canUse();
            }
        });
    }

    /** Los Verdianos y los Rodia son aliados: no se atacan entre si. */
    @Override
    public boolean canAttack(LivingEntity target) {
        if (target.getUUID().equals(getOwnerUUID()) || (target instanceof RodiaEntity r && r.isTame() && this.isTame())) {
            return false;
        }
        return !AlienEntity.isKin(target) && super.canAttack(target);
    }

    /** Altura a la que se sienta el jinete (el Verdiano caballero). */
    @Override
    public double getPassengersRidingOffset() {
        return 0.5D;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.RAVAGER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.RAVAGER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.RAVAGER_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.RAVAGER_STEP, 0.15F, 1.0F);
    }

    /** El Rodia amigo sigue a su dueno y se teletransporta si se queda muy lejos. */
    private class FollowOwnerGoal extends Goal {
        private LivingEntity owner;

        FollowOwnerGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (!isTame() || getTarget() != null) return false;
            LivingEntity o = getOwner();
            if (o == null || o.isSpectator() || distanceToSqr(o) < 100.0D) return false;
            this.owner = o;
            return true;
        }

        @Override
        public boolean canContinueToUse() {
            return owner != null && owner.isAlive() && getTarget() == null && distanceToSqr(owner) > 9.0D;
        }

        @Override
        public void tick() {
            if (distanceToSqr(owner) > 1600.0D) {
                getNavigation().stop();
                moveTo(owner.getX(), owner.getY(), owner.getZ(), getYRot(), getXRot());
            } else if (tickCount % 10 == 0) {
                getNavigation().moveTo(owner, 1.0D);
            }
        }

        @Override
        public void stop() {
            getNavigation().stop();
        }
    }

    /** El Rodia amigo ataca a quien dane a su dueno (o a quien su dueno ataque). */
    private class DefendOwnerGoal extends TargetGoal {
        private LivingEntity victim;

        DefendOwnerGoal() {
            super(RodiaEntity.this, false);
            this.setFlags(EnumSet.of(Flag.TARGET));
        }

        @Override
        public boolean canUse() {
            if (!isTame() || isBaby()) return false;
            LivingEntity o = getOwner();
            if (o == null) return false;
            LivingEntity t = o.getLastHurtByMob();
            if (t == null || o.getLastHurtByMobTimestamp() + 100 < o.tickCount) {
                t = o.getLastHurtMob();
                if (t == null || o.getLastHurtMobTimestamp() + 100 < o.tickCount) return false;
            }
            if (t == RodiaEntity.this || !canAttack(t) || t instanceof Player) return false;
            this.victim = t;
            return true;
        }

        @Override
        public void start() {
            this.targetMob = victim;
            super.start();
        }
    }
}
