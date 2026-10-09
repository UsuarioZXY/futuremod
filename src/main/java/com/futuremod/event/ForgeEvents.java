package com.futuremod.event;

import com.futuremod.FutureMod;
import com.futuremod.effect.ModEffects;
import com.futuremod.entity.AlienEntity;
import com.futuremod.item.SteelArmorItem;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/** Sangrado, paz entre alienigenas y proteccion contra flechas con la armadura completa. */
@Mod.EventBusSubscriber(modid = FutureMod.MODID)
public class ForgeEvents {

    @SubscribeEvent
    public static void onHeal(LivingHealEvent event) {
        if (event.getEntity().hasEffect(ModEffects.BLEEDING.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onEffectRemove(MobEffectEvent.Remove event) {
        if (event.getEffect() == ModEffects.BLEEDING.get()) {
            event.setCanceled(true);
        }
    }

    /** Un alienigena nunca hace dano a otro (golpes, flechas...), asi no hay represalias entre ellos. */
    @SubscribeEvent
    public static void onAttack(LivingAttackEvent event) {
        if (AlienEntity.isKin(event.getEntity()) && AlienEntity.isKin(event.getSource().getEntity())) {
            event.setCanceled(true);
        }
    }

    /** Con el set completo de acero las flechas no hacen nada (los demas proyectiles si; el tridente tampoco se bloquea). */
    @SubscribeEvent
    public static void onArrowAttack(LivingAttackEvent event) {
        Entity direct = event.getSource().getDirectEntity();
        if (direct instanceof AbstractArrow && !(direct instanceof ThrownTrident)
                && SteelArmorItem.isWearingFullSet(event.getEntity())) {
            event.setCanceled(true);
        }
    }

    /**
     * Set completo de acero: el veneno hace la mitad de dano y todo el dano (menos el que atraviesa
     * todo, como salir del mundo) se reduce un 15%.
     */
    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event) {
        if (!SteelArmorItem.isWearingFullSet(event.getEntity())) {
            return;
        }
        if (event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return;
        }
        float amount = event.getAmount();
        if (event.getSource().is(DamageTypes.MAGIC) && event.getEntity().hasEffect(MobEffects.POISON)) {
            amount *= 0.5F;
        }
        event.setAmount(amount * 0.85F);
    }
}
