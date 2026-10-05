package com.futuremod.client;

import com.futuremod.FutureMod;
import com.futuremod.entity.AlienEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class AlienRenderer extends MobRenderer<AlienEntity, AlienModel<AlienEntity>> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(FutureMod.MODID, "textures/entity/alien.png");

    public AlienRenderer(EntityRendererProvider.Context context) {
        super(context, new AlienModel<>(context.bakeLayer(AlienModel.LAYER_LOCATION)), 0.5F);
        this.addLayer(new AlienEyesLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(AlienEntity entity) {
        return TEXTURE;
    }
}
