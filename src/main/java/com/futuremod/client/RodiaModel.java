package com.futuremod.client;

import com.futuremod.FutureMod;
import com.futuremod.entity.RodiaEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

/** Rodia: cuadrupedo tipo dinosaurio con gola osea, cuernos, placas en el lomo y cola con pinchos. */
public class RodiaModel extends HierarchicalModel<RodiaEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(FutureMod.MODID, "rodia"), "main");

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart legFR;
    private final ModelPart legFL;
    private final ModelPart legBR;
    private final ModelPart legBL;

    public RodiaModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.neck = this.body.getChild("neck");
        this.head = this.neck.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.tail1 = this.body.getChild("tail1");
        this.tail2 = this.tail1.getChild("tail2");
        this.legFR = root.getChild("leg_fr");
        this.legFL = root.getChild("leg_fl");
        this.legBR = root.getChild("leg_br");
        this.legBL = root.getChild("leg_bl");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -4.0F, -9.0F, 10.0F, 8.0F, 18.0F),
                PartPose.offset(0.0F, 11.0F, 0.0F));

        // placas oseas en el lomo
        float[] plateZ = {-6.0F, -2.0F, 2.0F, 6.0F};
        for (int i = 0; i < plateZ.length; i++) {
            body.addOrReplaceChild("plate_" + i,
                    CubeListBuilder.create().texOffs(36, 26)
                            .addBox(-0.5F, -8.0F, plateZ[i] - 1.5F, 1.0F, 4.0F, 3.0F),
                    PartPose.ZERO);
        }

        // cuello y cabeza
        PartDefinition neck = body.addOrReplaceChild("neck",
                CubeListBuilder.create().texOffs(56, 0).addBox(-2.5F, -2.5F, -8.0F, 5.0F, 5.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, -2.0F, -8.0F, -0.55F, 0.0F, 0.0F));
        PartDefinition head = neck.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(82, 0).addBox(-3.0F, -3.0F, -7.0F, 6.0F, 6.0F, 7.0F)
                        .texOffs(0, 26).addBox(-2.0F, -1.5F, -12.0F, 4.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -8.0F, 0.6F, 0.0F, 0.0F));
        head.addOrReplaceChild("jaw",
                CubeListBuilder.create().texOffs(18, 26).addBox(-2.0F, 0.0F, -5.0F, 4.0F, 2.0F, 5.0F),
                PartPose.offset(0.0F, 0.5F, -7.0F));
        head.addOrReplaceChild("horn_r",
                CubeListBuilder.create().texOffs(44, 26).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 5.0F, 1.0F),
                PartPose.offsetAndRotation(-2.2F, -3.0F, -5.0F, -0.5F, 0.0F, -0.25F));
        head.addOrReplaceChild("horn_l",
                CubeListBuilder.create().texOffs(44, 26).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 5.0F, 1.0F),
                PartPose.offsetAndRotation(2.2F, -3.0F, -5.0F, -0.5F, 0.0F, 0.25F));
        head.addOrReplaceChild("frill",
                CubeListBuilder.create().texOffs(108, 0).addBox(-4.5F, -7.0F, -0.5F, 9.0F, 7.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -1.0F, -0.5F, -0.4F, 0.0F, 0.0F));

        // cola con pinchos
        PartDefinition tail1 = body.addOrReplaceChild("tail1",
                CubeListBuilder.create().texOffs(56, 13).addBox(-2.5F, -2.0F, 0.0F, 5.0F, 4.0F, 8.0F)
                        .texOffs(48, 26).addBox(-0.5F, -5.0F, 3.0F, 1.0F, 3.0F, 1.0F)
                        .texOffs(48, 26).addBox(-0.5F, -5.0F, 6.0F, 1.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -1.0F, 9.0F, -0.1F, 0.0F, 0.0F));
        tail1.addOrReplaceChild("tail2",
                CubeListBuilder.create().texOffs(82, 13).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 8.0F)
                        .texOffs(48, 26).addBox(-0.5F, -4.5F, 3.0F, 1.0F, 3.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 8.0F));

        // patas
        addLeg(root, "leg_fr", -4.0F, -6.5F);
        addLeg(root, "leg_fl", 4.0F, -6.5F);
        addLeg(root, "leg_br", -4.0F, 6.5F);
        addLeg(root, "leg_bl", 4.0F, 6.5F);

        return LayerDefinition.create(mesh, 128, 64);
    }

    private static void addLeg(PartDefinition root, String name, float x, float z) {
        root.addOrReplaceChild(name,
                CubeListBuilder.create().texOffs(104, 13).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F),
                PartPose.offset(x, 15.0F, z));
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(RodiaEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        float pi = (float) Math.PI;
        float walk = limbSwing * 0.6662F;
        float amp = Math.min(1.0F, limbSwingAmount);
        float swing = Mth.cos(walk) * 1.3F * amp;
        float breathe = Mth.sin(ageInTicks * 0.07F);
        float bite = Mth.sin(this.attackTime * pi);
        boolean aggressive = entity.isAggressive();

        // Patas (trote en diagonal) y rebote del cuerpo
        this.legFR.xRot = swing;
        this.legBL.xRot = swing;
        this.legFL.xRot = -swing;
        this.legBR.xRot = -swing;
        this.body.y += -Mth.abs(Mth.cos(walk)) * 1.4F * amp;
        this.body.xRot += Mth.sin(walk * 2.0F) * 0.04F * amp + breathe * 0.01F;
        this.body.zRot = Mth.cos(walk) * 0.04F * amp;

        // Cuello y cabeza: siguen la mirada, cabecean al correr, bajan al estar agresivo y se lanzan al morder
        this.neck.xRot += Mth.cos(walk * 2.0F) * 0.07F * amp + breathe * 0.03F + (aggressive ? 0.2F : 0.0F) - bite * 0.6F;
        this.neck.yRot = netHeadYaw * (pi / 180F) * 0.3F;
        this.head.yRot = netHeadYaw * (pi / 180F) * 0.4F;
        this.head.xRot += headPitch * (pi / 180F) * 0.6F + bite * 0.9F;

        // Mandibula: respira, se abre al estar agresivo y se abre del todo al morder
        this.jaw.xRot = (aggressive ? 0.35F + Mth.sin(ageInTicks * 0.5F) * 0.08F : 0.04F + (breathe + 1.0F) * 0.02F)
                + bite * 0.6F;

        // Cola: ondula en reposo y se agita al correr
        this.tail1.yRot = Mth.cos(ageInTicks * 0.1F) * 0.12F + Mth.cos(walk) * 0.2F * amp;
        this.tail2.yRot = Mth.cos(ageInTicks * 0.1F + 1.0F) * 0.22F + Mth.cos(walk - 0.8F) * 0.3F * amp;
        this.tail1.xRot += aggressive ? -0.2F : 0.0F;
    }
}
