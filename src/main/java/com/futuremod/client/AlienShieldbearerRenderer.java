package com.futuremod.client;

import com.futuremod.FutureMod;
import com.futuremod.entity.AlienShieldbearerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class AlienShieldbearerRenderer extends MobRenderer<AlienShieldbearerEntity, AlienModel<AlienShieldbearerEntity>> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(FutureMod.MODID, "textures/entity/alien_shieldbearer.png");
    private static final ResourceLocation EYES =
            new ResourceLocation(FutureMod.MODID, "textures/entity/alien_shieldbearer_eyes.png");

    public AlienShieldbearerRenderer(EntityRendererProvider.Context context) {
        super(context, new AlienModel<>(context.bakeLayer(AlienModel.LAYER_LOCATION)), 0.5F);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new AlienEyesLayer<>(this, EYES));
    }

    @Override
    public ResourceLocation getTextureLocation(AlienShieldbearerEntity entity) {
        return TEXTURE;
    }
}
