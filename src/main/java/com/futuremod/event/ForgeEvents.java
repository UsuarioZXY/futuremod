package com.futuremod.event;

import com.futuremod.FutureMod;
import com.futuremod.effect.ModEffects;
import com.futuremod.entity.AlienEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/** Reglas del sangrado (no se cura, no deja regenerar) y paz entre alienigenas. */
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
        if (event.getEntity() instanceof AlienEntity && event.getSource().getEntity() instanceof AlienEntity) {
            event.setCanceled(true);
        }
    }
}
