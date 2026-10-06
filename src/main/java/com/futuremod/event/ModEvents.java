package com.futuremod.event;

import com.futuremod.FutureMod;
import com.futuremod.entity.AlienArcherEntity;
import com.futuremod.entity.AlienEntity;
import com.futuremod.entity.ModEntities;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FutureMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @SubscribeEvent
    public static void onAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.ALIEN.get(), AlienEntity.createAttributes().build());
        event.put(ModEntities.ALIEN_ARCHER.get(), AlienArcherEntity.createArcherAttributes().build());
    }

    @SubscribeEvent
    public static void onSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(ModEntities.ALIEN.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AlienEntity::checkAlienSpawnRules,
                SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ModEntities.ALIEN_ARCHER.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AlienEntity::checkAlienSpawnRules,
                SpawnPlacementRegisterEvent.Operation.OR);
    }
}
