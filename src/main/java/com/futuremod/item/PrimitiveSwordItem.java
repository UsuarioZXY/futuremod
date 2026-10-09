package com.futuremod.item;

import com.futuremod.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

/** Espada primitiva: 6.5 de dano y cada golpe deja sangrado en la victima. */
public class PrimitiveSwordItem extends SwordItem {
    public PrimitiveSwordItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean result = super.hurtEnemy(stack, target, attacker);
        target.addEffect(new MobEffectInstance(ModEffects.BLEEDING.get(), 100, 0), attacker);
        return result;
    }
}
