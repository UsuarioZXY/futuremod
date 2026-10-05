package com.futuremod.client;

import com.futuremod.FutureMod;
import com.futuremod.entity.AlienEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

/** Ojos y antenas que brillan en la oscuridad. */
public class AlienEyesLayer extends EyesLayer<AlienEntity, AlienModel<AlienEntity>> {
    private static final RenderType GLOW =
            RenderType.eyes(new ResourceLocation(FutureMod.MODID, "textures/entity/alien_eyes.png"));

    public AlienEyesLayer(RenderLayerParent<AlienEntity, AlienModel<AlienEntity>> parent) {
        super(parent);
    }

    @Override
    public RenderType renderType() {
        return GLOW;
    }
}
