package com.futuremod.event;

import com.futuremod.FutureMod;
import com.futuremod.effect.ModEffects;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/** Reglas del sangrado: no se puede curar (ni con leche) y no deja regenerar vida. */
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
}
