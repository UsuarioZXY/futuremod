package com.futuremod.client;

import com.futuremod.entity.AlienEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

/** Ojos y antenas que brillan en la oscuridad. */
public class AlienEyesLayer<T extends AlienEntity> extends EyesLayer<T, AlienModel<T>> {
    private final RenderType glow;

    public AlienEyesLayer(RenderLayerParent<T, AlienModel<T>> parent, ResourceLocation texture) {
        super(parent);
        this.glow = RenderType.eyes(texture);
    }

    @Override
    public RenderType renderType() {
        return this.glow;
    }
}
