package com.futuremod.entity;

import com.futuremod.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

/** Verdiano caballero: armadura y espada primitivas y escudo. Aparece montado sobre un Rodia. */
public class AlienKnightEntity extends AlienEntity {

    public AlienKnightEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.xpReward = 12;
    }

    public static AttributeSupplier.Builder createKnightAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 24.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.ARMOR, 8.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.4D);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        MobSpawnType reason, SpawnGroupData spawnData, CompoundTag tag) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, reason, spawnData, tag);
        this.populateDefaultEquipmentSlots(level.getRandom(), difficulty);
        // Aparece montado sobre un Rodia
        if (this.getVehicle() == null) {
            RodiaEntity rodia = ModEntities.RODIA.get().create(level.getLevel());
            if (rodia != null) {
                rodia.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                rodia.finalizeSpawn(level, difficulty, MobSpawnType.JOCKEY, null, null);
                this.startRiding(rodia);
                level.addFreshEntity(rodia);
            }
        }
        return result;
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.PRIMITIVE_SWORD.get()));
        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(ModItems.PRIMITIVE_SHIELD.get()));
    }

    /** 30% de probabilidad de bloquear un golpe o una flecha con el escudo. */
    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!this.level().isClientSide
                && source.getDirectEntity() != null
                && !source.is(DamageTypeTags.BYPASSES_SHIELD)
                && this.getOffhandItem().is(ModItems.PRIMITIVE_SHIELD.get())
                && this.random.nextFloat() < 0.30F) {
            this.level().broadcastEntityEvent(this, (byte) 29);
            return false;
        }
        return super.hurt(source, amount);
    }
}
