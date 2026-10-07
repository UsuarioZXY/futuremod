package com.futuremod.client;

import com.futuremod.FutureMod;
import com.futuremod.entity.AlienHealerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class AlienHealerRenderer extends MobRenderer<AlienHealerEntity, AlienModel<AlienHealerEntity>> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(FutureMod.MODID, "textures/entity/alien_healer.png");
    private static final ResourceLocation EYES =
            new ResourceLocation(FutureMod.MODID, "textures/entity/alien_healer_eyes.png");

    public AlienHealerRenderer(EntityRendererProvider.Context context) {
        super(context, new AlienModel<>(context.bakeLayer(AlienModel.LAYER_LOCATION)), 0.5F);
        this.addLayer(new AlienEyesLayer<>(this, EYES));
    }

    @Override
    public ResourceLocation getTextureLocation(AlienHealerEntity entity) {
        return TEXTURE;
    }
}
