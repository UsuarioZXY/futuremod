package com.futuremod.client;

import com.futuremod.FutureMod;
import com.futuremod.entity.RodiaEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

/** Ojos, gola y cuernos del Rodia que brillan en la oscuridad. */
public class RodiaEyesLayer extends EyesLayer<RodiaEntity, RodiaModel> {
    private static final RenderType GLOW =
            RenderType.eyes(new ResourceLocation(FutureMod.MODID, "textures/entity/rodia_eyes.png"));

    public RodiaEyesLayer(RenderLayerParent<RodiaEntity, RodiaModel> parent) {
        super(parent);
    }

    @Override
    public RenderType renderType() {
        return GLOW;
    }
}
