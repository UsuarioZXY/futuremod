package com.futuremod.client;

import com.futuremod.FutureMod;
import com.futuremod.entity.PrimitiveArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class PrimitiveArrowRenderer extends ArrowRenderer<PrimitiveArrow> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(FutureMod.MODID, "textures/entity/projectiles/primitive_arrow.png");

    public PrimitiveArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(PrimitiveArrow entity) {
        return TEXTURE;
    }
}
