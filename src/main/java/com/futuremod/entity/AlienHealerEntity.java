package com.futuremod.entity;

import java.util.List;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/** Verdiano curandero: lleva un cetro, cura a los aliados cercanos y huye del jugador. No ataca. */
public class AlienHealerEntity extends AlienEntity {
    private static final double HEAL_RADIUS = 20.0D;
    private static final float HEAL_AMOUNT = 2.0F;
    private static final int HEAL_INTERVAL = 40;
    private static final int HEAL_ANIM_TICKS = 24;
    private static final byte HEAL_EVENT = 71;

    private int healAnimTicks;

    public AlienHealerEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.xpReward = 10;
    }

    public static AttributeSupplier.Builder createHealerAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.26D)
                .add(Attributes.FOLLOW_RANGE, 24.0D);
    }

    /** Camina, mira y huye del jugador cuando se le acerca; no tiene objetivos de ataque. */
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Player.class, 14.0F, 1.0D, 1.35D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.9D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    /** 0 a 1: cuanto queda de la animacion de curar (solo se usa en el cliente). */
    public float getHealAnim() {
        return this.healAnimTicks / (float) HEAL_ANIM_TICKS;
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == HEAL_EVENT) {
            this.healAnimTicks = HEAL_ANIM_TICKS;
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.healAnimTicks > 0) {
            this.healAnimTicks--;
        }
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.tickCount % HEAL_INTERVAL == 0) {
            this.healAllies();
        }
    }

    private void healAllies() {
        List<LivingEntity> hurtAllies = this.level().getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(HEAL_RADIUS),
                e -> e != this && e.isAlive() && isKin(e) && e.getHealth() < e.getMaxHealth());
        if (hurtAllies.isEmpty()) {
            return;
        }
        for (LivingEntity ally : hurtAllies) {
            ally.heal(HEAL_AMOUNT);
            if (this.level() instanceof ServerLevel server) {
                server.sendParticles(ParticleTypes.HEART, ally.getX(), ally.getY() + ally.getBbHeight() + 0.3D,
                        ally.getZ(), 3, 0.3D, 0.2D, 0.3D, 0.0D);
            }
        }
        this.level().broadcastEntityEvent(this, HEAL_EVENT);
        this.playSound(SoundEvents.AMETHYST_BLOCK_CHIME, 1.0F, 1.2F);
    }
}
