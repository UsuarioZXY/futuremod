package com.futuremod;

import com.futuremod.block.ModBlocks;
import com.futuremod.effect.ModEffects;
import com.futuremod.entity.ModEntities;
import com.futuremod.item.ModCreativeTabs;
import com.futuremod.item.ModItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FutureMod.MODID)
public class FutureMod {
    public static final String MODID = "futuremod";

    public FutureMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.ITEMS.register(bus);
        ModBlocks.BLOCKS.register(bus);
        ModEntities.ENTITIES.register(bus);
        ModEffects.EFFECTS.register(bus);
        ModCreativeTabs.TABS.register(bus);
    }
}
