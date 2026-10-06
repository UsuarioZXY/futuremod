package com.futuremod.client;

import com.futuremod.FutureMod;
import com.futuremod.entity.AlienKnightEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class AlienKnightRenderer extends MobRenderer<AlienKnightEntity, AlienModel<AlienKnightEntity>> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(FutureMod.MODID, "textures/entity/alien_knight.png");
    private static final ResourceLocation EYES =
            new ResourceLocation(FutureMod.MODID, "textures/entity/alien_knight_eyes.png");

    public AlienKnightRenderer(EntityRendererProvider.Context context) {
        super(context, new AlienModel<>(context.bakeLayer(AlienModel.LAYER_LOCATION)), 0.5F);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new AlienEyesLayer<>(this, EYES));
    }

    @Override
    public ResourceLocation getTextureLocation(AlienKnightEntity entity) {
        return TEXTURE;
    }
}
