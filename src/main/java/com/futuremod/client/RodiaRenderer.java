package com.futuremod.client;

import com.futuremod.FutureMod;
import com.futuremod.entity.RodiaEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RodiaRenderer extends MobRenderer<RodiaEntity, RodiaModel> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(FutureMod.MODID, "textures/entity/rodia.png");

    private static final ResourceLocation BABY_TEXTURE =
            new ResourceLocation(FutureMod.MODID, "textures/entity/rodia_baby.png");

    @Override
    protected void scale(RodiaEntity entity, com.mojang.blaze3d.vertex.PoseStack poseStack, float partialTick) {
        if (entity.isBaby()) {
            poseStack.scale(0.55F, 0.55F, 0.55F);
            this.shadowRadius = 0.55F;
        } else {
            this.shadowRadius = 1.0F;
        }
    }

    public RodiaRenderer(EntityRendererProvider.Context context) {
        super(context, new RodiaModel(context.bakeLayer(RodiaModel.LAYER_LOCATION)), 1.0F);
        this.addLayer(new RodiaEyesLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(RodiaEntity entity) {
        return entity.isBaby() ? BABY_TEXTURE : TEXTURE;
    }
}
