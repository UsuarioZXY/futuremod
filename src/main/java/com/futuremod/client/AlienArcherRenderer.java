package com.futuremod.client;

import com.futuremod.FutureMod;
import com.futuremod.entity.AlienArcherEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class AlienArcherRenderer extends MobRenderer<AlienArcherEntity, AlienModel<AlienArcherEntity>> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(FutureMod.MODID, "textures/entity/alien_archer.png");
    private static final ResourceLocation EYES =
            new ResourceLocation(FutureMod.MODID, "textures/entity/alien_archer_eyes.png");

    public AlienArcherRenderer(EntityRendererProvider.Context context) {
        super(context, new AlienModel<>(context.bakeLayer(AlienModel.LAYER_LOCATION)), 0.5F);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new AlienEyesLayer<>(this, EYES));
    }

    @Override
    public ResourceLocation getTextureLocation(AlienArcherEntity entity) {
        return TEXTURE;
    }
}
