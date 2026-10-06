package com.futuremod.client;

import com.futuremod.FutureMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ShieldModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

/** Dibuja el escudo primitivo con la forma del escudo normal pero con su propia textura. */
public class PrimitiveShieldRenderer extends BlockEntityWithoutLevelRenderer {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(FutureMod.MODID, "textures/entity/primitive_shield.png");
    private static PrimitiveShieldRenderer instance;
    private ShieldModel model;

    private PrimitiveShieldRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    public static PrimitiveShieldRenderer get() {
        if (instance == null) {
            instance = new PrimitiveShieldRenderer();
        }
        return instance;
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack poseStack,
                             MultiBufferSource buffer, int light, int overlay) {
        if (this.model == null) {
            this.model = new ShieldModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.SHIELD));
        }
        poseStack.pushPose();
        poseStack.scale(1.0F, -1.0F, -1.0F);
        VertexConsumer consumer = ItemRenderer.getFoilBufferDirect(buffer, this.model.renderType(TEXTURE), true, stack.hasFoil());
        this.model.handle().render(poseStack, consumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
        this.model.plate().render(poseStack, consumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }
}
