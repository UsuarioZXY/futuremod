package com.futuremod.client;

import com.futuremod.FutureMod;
import com.futuremod.entity.AlienArcherEntity;
import com.futuremod.entity.AlienEntity;
import com.futuremod.entity.AlienShieldbearerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ArmedModel;
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
import net.minecraft.world.entity.HumanoidArm;

/** Alienigena primitivo: craneo en cupula con antenas, colmillos, espinas de hueso, taparrabos y garrote. */
public class AlienModel<T extends AlienEntity> extends HierarchicalModel<T> implements ArmedModel {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(FutureMod.MODID, "alien"), "main");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart antennaR;
    private final ModelPart antennaL;
    private final ModelPart rightArm;
    private final ModelPart club;
    private final ModelPart spear;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public AlienModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.antennaR = this.head.getChild("antenna_r");
        this.antennaL = this.head.getChild("antenna_l");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
        this.club = this.rightArm.getChild("club");
        this.spear = this.rightArm.getChild("spear");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // Cabeza: craneo + cupula + mandibula + colmillos + ceja marcada
        PartDefinition head = root.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.5F, -11.0F, -4.5F, 9.0F, 7.0F, 9.0F)
                        .texOffs(0, 52).addBox(-3.5F, -14.0F, -3.5F, 7.0F, 3.0F, 7.0F)
                        .texOffs(36, 0).addBox(-2.5F, -4.0F, -3.5F, 5.0F, 4.0F, 6.0F)
                        .texOffs(12, 46).addBox(-2.5F, -3.0F, -4.0F, 1.0F, 3.0F, 1.0F)
                        .texOffs(12, 46).addBox(1.5F, -3.0F, -4.0F, 1.0F, 3.0F, 1.0F)
                        .texOffs(34, 46).addBox(-4.5F, -10.0F, -5.5F, 9.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 4.4F, -1.5F, 0.3F, 0.0F, 0.0F));
        // Antenas con bulbo brillante
        head.addOrReplaceChild("antenna_r",
                CubeListBuilder.create()
                        .texOffs(0, 46).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 5.0F, 1.0F)
                        .texOffs(4, 46).addBox(-1.0F, -7.0F, -1.0F, 2.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(-2.5F, -13.0F, -1.0F, -0.15F, 0.0F, -0.35F));
        head.addOrReplaceChild("antenna_l",
                CubeListBuilder.create()
                        .texOffs(0, 46).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 5.0F, 1.0F)
                        .texOffs(4, 46).addBox(-1.0F, -7.0F, -1.0F, 2.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(2.5F, -13.0F, -1.0F, -0.15F, 0.0F, 0.35F));

        // Torso encorvado (pivote en la cadera)
        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(0, 16).addBox(-3.5F, -10.0F, -2.0F, 7.0F, 10.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 14.0F, 0.0F, 0.2F, 0.0F, 0.0F));
        body.addOrReplaceChild("loincloth",
                CubeListBuilder.create().texOffs(22, 16).addBox(-3.5F, 0.0F, -2.5F, 7.0F, 5.0F, 5.0F),
                PartPose.offset(0.0F, -0.5F, 0.0F));
        // Espinas de hueso en la espalda
        body.addOrReplaceChild("spine_1",
                CubeListBuilder.create().texOffs(24, 46).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, -8.0F, 2.0F, 0.5F, 0.0F, 0.0F));
        body.addOrReplaceChild("spine_2",
                CubeListBuilder.create().texOffs(24, 46).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, -5.0F, 2.0F, 0.5F, 0.0F, 0.0F));
        body.addOrReplaceChild("spine_3",
                CubeListBuilder.create().texOffs(24, 46).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, -2.0F, 2.0F, 0.5F, 0.0F, 0.0F));

        // Brazos largos con hombreras de hueso; el derecho lleva un garrote
        PartDefinition rightArm = root.addOrReplaceChild("right_arm",
                CubeListBuilder.create().texOffs(0, 30).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 13.0F, 2.0F),
                PartPose.offset(-4.5F, 5.5F, -1.0F));
        rightArm.addOrReplaceChild("club",
                CubeListBuilder.create()
                        .texOffs(40, 30).addBox(-0.5F, -9.0F, -0.5F, 1.0F, 10.0F, 1.0F)
                        .texOffs(44, 30).addBox(-1.5F, -13.0F, -1.5F, 3.0F, 5.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 11.0F, 0.0F, 1.2F, 0.0F, 0.0F));
        // Lanza (solo la usa el Verdiano escudero): astil largo con punta de piedra
        rightArm.addOrReplaceChild("spear",
                CubeListBuilder.create()
                        .texOffs(58, 0).addBox(-0.5F, -16.0F, -0.5F, 1.0F, 24.0F, 1.0F)
                        .texOffs(30, 52).addBox(-1.0F, -19.0F, -1.0F, 2.0F, 3.0F, 2.0F),
                PartPose.offset(0.0F, 11.0F, 0.0F));
        rightArm.addOrReplaceChild("pauldron_r",
                CubeListBuilder.create().texOffs(16, 46).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F),
                PartPose.offsetAndRotation(-0.5F, -0.5F, 0.0F, 0.0F, 0.0F, -0.5F));
        PartDefinition leftArm = root.addOrReplaceChild("left_arm",
                CubeListBuilder.create().texOffs(8, 30).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 13.0F, 2.0F),
                PartPose.offset(4.5F, 5.5F, -1.0F));
        leftArm.addOrReplaceChild("pauldron_l",
                CubeListBuilder.create().texOffs(16, 46).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F),
                PartPose.offsetAndRotation(0.5F, -0.5F, 0.0F, 0.0F, 0.0F, 0.5F));

        // Piernas
        root.addOrReplaceChild("right_leg",
                CubeListBuilder.create().texOffs(16, 30).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 10.0F, 3.0F),
                PartPose.offset(-1.8F, 14.0F, 0.0F));
        root.addOrReplaceChild("left_leg",
                CubeListBuilder.create().texOffs(28, 30).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 10.0F, 3.0F),
                PartPose.offset(1.8F, 14.0F, 0.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.head.xRot += headPitch * ((float) Math.PI / 180F);

        // Las antenas se mecen solas
        this.antennaR.zRot += Mth.sin(ageInTicks * 0.1F) * 0.08F;
        this.antennaL.zRot -= Mth.sin(ageInTicks * 0.1F + 1.5F) * 0.08F;

        float swing = Mth.cos(limbSwing * 0.6662F) * limbSwingAmount;
        this.rightLeg.xRot = swing * 1.4F;
        this.leftLeg.xRot = -swing * 1.4F;

        if (entity.isAggressive()) {
            // Brazos en alto; el derecho golpea con el garrote
            float hit = Mth.sin(this.attackTime * (float) Math.PI);
            this.rightArm.xRot = -1.6F + hit * 1.5F;
            this.leftArm.xRot = -1.4F + Mth.sin(ageInTicks * 0.3F) * 0.05F;
            this.rightArm.zRot = 0.1F;
            this.leftArm.zRot = -0.1F;
        } else {
            this.rightArm.xRot = -swing * 1.0F;
            this.leftArm.xRot = swing * 1.0F;
            this.rightArm.zRot = 0.1F + Mth.cos(ageInTicks * 0.09F) * 0.04F;
            this.leftArm.zRot = -0.1F - Mth.cos(ageInTicks * 0.09F) * 0.04F;
        }

        // El arquero no lleva garrote: sostiene un arco y apunta con ambos brazos
        boolean archer = entity instanceof AlienArcherEntity;
        boolean shielded = entity instanceof AlienShieldbearerEntity;
        this.club.visible = !archer && !shielded;
        this.spear.visible = shielded;
        if (archer && entity.isAggressive()) {
            this.rightArm.yRot = -0.1F + this.head.yRot;
            this.leftArm.yRot = 0.1F + this.head.yRot + 0.4F;
            this.rightArm.xRot = (-(float) Math.PI / 2F) + this.head.xRot;
            this.leftArm.xRot = (-(float) Math.PI / 2F) + this.head.xRot;
            this.rightArm.zRot = 0.0F;
            this.leftArm.zRot = 0.0F;
        }

        // El escudero embiste con la lanza hacia delante y lleva el escudo al frente
        if (shielded && entity.isAggressive()) {
            float thrust = Mth.sin(this.attackTime * (float) Math.PI);
            this.rightArm.xRot = -0.9F - thrust * 0.4F;
            this.rightArm.zRot = 0.0F;
            this.spear.xRot = ((float) Math.PI / 2F) - this.rightArm.xRot + 0.1F;
            this.spear.y += thrust * 4.0F;
            this.leftArm.xRot = -0.35F;
            this.leftArm.zRot = -0.05F;
        }
    }

    @Override
    public void translateToHand(HumanoidArm arm, PoseStack poseStack) {
        ModelPart part = arm == HumanoidArm.RIGHT ? this.rightArm : this.leftArm;
        part.translateAndRotate(poseStack);
    }
}
