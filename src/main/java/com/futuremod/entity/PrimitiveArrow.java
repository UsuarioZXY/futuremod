package com.futuremod.entity;

import com.futuremod.effect.ModEffects;
import com.futuremod.item.ModItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/** Flecha primitiva: al herir causa 5 segundos de sangrado. */
public class PrimitiveArrow extends AbstractArrow {

    public PrimitiveArrow(EntityType<? extends PrimitiveArrow> type, Level level) {
        super(type, level);
    }

    public PrimitiveArrow(Level level, LivingEntity shooter) {
        super(ModEntities.PRIMITIVE_ARROW.get(), shooter, level);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.PRIMITIVE_ARROW.get());
    }

    @Override
    protected void doPostHurtEffects(LivingEntity target) {
        super.doPostHurtEffects(target);
        target.addEffect(new MobEffectInstance(ModEffects.BLEEDING.get(), 100, 0), this.getOwner());
    }
}
