package com.futuremod.client;

import com.futuremod.FutureMod;
import com.futuremod.entity.ModEntities;
import com.futuremod.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = FutureMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    /** Animaciones del arco al tensarlo y del escudo al bloquear (igual que los normales). */
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(ModItems.PRIMITIVE_BOW.get(), new ResourceLocation("pull"),
                    (stack, level, entity, seed) -> {
                        if (entity == null) {
                            return 0.0F;
                        }
                        return entity.getUseItem() != stack ? 0.0F
                                : (float) (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F;
                    });
            ItemProperties.register(ModItems.PRIMITIVE_BOW.get(), new ResourceLocation("pulling"),
                    (stack, level, entity, seed) ->
                            entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
            ItemProperties.register(ModItems.PRIMITIVE_SHIELD.get(), new ResourceLocation("blocking"),
                    (stack, level, entity, seed) ->
                            entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
        });
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(AlienModel.LAYER_LOCATION, AlienModel::createBodyLayer);
        event.registerLayerDefinition(RodiaModel.LAYER_LOCATION, RodiaModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.ALIEN.get(), AlienRenderer::new);
        event.registerEntityRenderer(ModEntities.ALIEN_ARCHER.get(), AlienArcherRenderer::new);
        event.registerEntityRenderer(ModEntities.ALIEN_SHIELDBEARER.get(), AlienShieldbearerRenderer::new);
        event.registerEntityRenderer(ModEntities.ALIEN_KNIGHT.get(), AlienKnightRenderer::new);
        event.registerEntityRenderer(ModEntities.ALIEN_HEALER.get(), AlienHealerRenderer::new);
        event.registerEntityRenderer(ModEntities.RODIA.get(), RodiaRenderer::new);
        event.registerEntityRenderer(ModEntities.PRIMITIVE_ARROW.get(), PrimitiveArrowRenderer::new);
    }
}
