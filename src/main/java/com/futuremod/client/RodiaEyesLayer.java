package com.futuremod.client;

import com.futuremod.FutureMod;
import com.futuremod.entity.RodiaEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

/** Ojos, gola y cuernos del Rodia que brillan en la oscuridad (version adulta y de cria). */
public class RodiaEyesLayer extends RenderLayer<RodiaEntity, RodiaModel> {
    private static final RenderType GLOW =
            RenderType.eyes(new ResourceLocation(FutureMod.MODID, "textures/entity/rodia_eyes.png"));
    private static final RenderType BABY_GLOW =
            RenderType.eyes(new ResourceLocation(FutureMod.MODID, "textures/entity/rodia_baby_eyes.png"));

    public RodiaEyesLayer(RenderLayerParent<RodiaEntity, RodiaModel> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, RodiaEntity entity,
                       float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks,
                       float netHeadYaw, float headPitch) {
        VertexConsumer consumer = buffer.getBuffer(entity.isBaby() ? BABY_GLOW : GLOW);
        this.getParentModel().renderToBuffer(poseStack, consumer, 15728640, OverlayTexture.NO_OVERLAY,
                1.0F, 1.0F, 1.0F, 1.0F);
    }
}
