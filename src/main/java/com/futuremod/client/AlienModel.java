package com.futuremod.client;

import com.futuremod.FutureMod;
import com.futuremod.entity.AlienArcherEntity;
import com.futuremod.entity.AlienEntity;
import com.futuremod.entity.AlienHealerEntity;
import com.futuremod.entity.AlienKnightEntity;
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

/** Modelo compartido de los Verdianos (guerrero, arquero, escudero, caballero y curandero). */
public class AlienModel<T extends AlienEntity> extends HierarchicalModel<T> implements ArmedModel {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(FutureMod.MODID, "alien"), "main");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart antennaR;
    private final ModelPart antennaL;
    private final ModelPart crest;
    private final ModelPart helmet;
    private final ModelPart brow;
    private final ModelPart rightArm;
    private final ModelPart club;
    private final ModelPart spear;
    private final ModelPart scepter;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public AlienModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.antennaR = this.head.getChild("antenna_r");
        this.antennaL = this.head.getChild("antenna_l");
        this.crest = this.head.getChild("crest");
        this.helmet = this.head.getChild("helmet");
        this.brow = this.head.getChild("brow");
        this.rightArm = root.getChild("right_arm");
        this.club = this.rightArm.getChild("club");
        this.spear = this.rightArm.getChild("spear");
        this.scepter = this.rightArm.getChild("scepter");
        this.leftArm = root.getChild("left_arm");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // Cabeza: craneo + cupula + mandibula + colmillos
        PartDefinition head = root.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.5F, -11.0F, -4.5F, 9.0F, 7.0F, 9.0F)
                        .texOffs(0, 52).addBox(-3.5F, -14.0F, -3.5F, 7.0F, 3.0F, 7.0F)
                        .texOffs(36, 0).addBox(-2.5F, -4.0F, -3.5F, 5.0F, 4.0F, 6.0F)
                        .texOffs(12, 46).addBox(-2.5F, -3.0F, -4.0F, 1.0F, 3.0F, 1.0F)
                        .texOffs(12, 46).addBox(1.5F, -3.0F, -4.0F, 1.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 4.4F, -1.5F, 0.3F, 0.0F, 0.0F));
        // Ceja marcada (se oculta bajo el casco del caballero)
        head.addOrReplaceChild("brow",
                CubeListBuilder.create().texOffs(34, 46).addBox(-4.5F, -10.0F, -5.5F, 9.0F, 1.0F, 1.0F),
                PartPose.ZERO);
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
        // Cresta del caballero, sobre el casco
        head.addOrReplaceChild("crest",
                CubeListBuilder.create().texOffs(40, 52).addBox(-1.0F, -18.0F, -4.0F, 2.0F, 3.0F, 8.0F),
                PartPose.ZERO);
        // Casco del caballero: calota, carrilleras y barra nasal (los ojos se ven por la rendija)
        head.addOrReplaceChild("helmet",
                CubeListBuilder.create()
                        .texOffs(0, 64).addBox(-5.0F, -15.0F, -5.0F, 10.0F, 9.0F, 10.0F)
                        .texOffs(40, 64).addBox(-5.5F, -6.0F, -3.5F, 1.0F, 4.0F, 6.0F)
                        .texOffs(40, 64).addBox(4.5F, -6.0F, -3.5F, 1.0F, 4.0F, 6.0F)
                        .texOffs(54, 64).addBox(-0.5F, -9.0F, -5.6F, 1.0F, 5.0F, 1.0F),
                PartPose.ZERO);

        // Torso encorvado (pivote en la cadera)
        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(0, 16).addBox(-3.5F, -10.0F, -2.0F, 7.0F, 10.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 14.0F, 0.0F, 0.2F, 0.0F, 0.0F));
        body.addOrReplaceChild("loincloth",
                CubeListBuilder.create().texOffs(22, 16).addBox(-3.5F, 0.0F, -2.5F, 7.0F, 5.0F, 5.0F),
                PartPose.offset(0.0F, -0.5F, 0.0F));
        body.addOrReplaceChild("spine_1",
                CubeListBuilder.create().texOffs(24, 46).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, -8.0F, 2.0F, 0.5F, 0.0F, 0.0F));
        body.addOrReplaceChild("spine_2",
                CubeListBuilder.create().texOffs(24, 46).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, -5.0F, 2.0F, 0.5F, 0.0F, 0.0F));
        body.addOrReplaceChild("spine_3",
                CubeListBuilder.create().texOffs(24, 46).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, -2.0F, 2.0F, 0.5F, 0.0F, 0.0F));

        // Brazos largos. Las armas de mano van adelantadas (z = -2.5) para no atravesar el brazo.
        PartDefinition rightArm = root.addOrReplaceChild("right_arm",
                CubeListBuilder.create().texOffs(0, 30).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 13.0F, 2.0F),
                PartPose.offset(-4.5F, 5.5F, -1.0F));
        rightArm.addOrReplaceChild("club",
                CubeListBuilder.create()
                        .texOffs(40, 30).addBox(-0.5F, -9.0F, -0.5F, 1.0F, 10.0F, 1.0F)
                        .texOffs(44, 30).addBox(-1.5F, -13.0F, -1.5F, 3.0F, 5.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 11.0F, 0.0F, 1.2F, 0.0F, 0.0F));
        rightArm.addOrReplaceChild("spear",
                CubeListBuilder.create()
                        .texOffs(58, 0).addBox(-0.5F, -16.0F, -0.5F, 1.0F, 24.0F, 1.0F)
                        .texOffs(30, 52).addBox(-1.0F, -19.0F, -1.0F, 2.0F, 3.0F, 2.0F),
                PartPose.offset(-0.5F, 11.0F, -2.5F));
        rightArm.addOrReplaceChild("scepter",
                CubeListBuilder.create()
                        .texOffs(56, 26).addBox(-0.5F, -18.0F, -0.5F, 1.0F, 22.0F, 1.0F)
                        .texOffs(44, 38).addBox(-1.5F, -21.0F, -1.5F, 3.0F, 3.0F, 3.0F),
                PartPose.offset(-0.5F, 11.0F, -2.5F));
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

        return LayerDefinition.create(mesh, 64, 128);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    /** Curva de un golpe: pose lista, preparacion (arriba/atras), golpe y vuelta. attack va de 0 a 1. */
    private static float strikeCurve(float attack, float ready, float windup, float strike) {
        if (attack < 0.35F) {
            return Mth.lerp(attack / 0.35F, ready, windup);
        }
        if (attack < 0.7F) {
            return Mth.lerp((attack - 0.35F) / 0.35F, windup, strike);
        }
        return Mth.lerp((attack - 0.7F) / 0.3F, strike, ready);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        float pi = (float) Math.PI;
        boolean archer = entity instanceof AlienArcherEntity;
        boolean shielded = entity instanceof AlienShieldbearerEntity;
        boolean knight = entity instanceof AlienKnightEntity;
        boolean healer = entity instanceof AlienHealerEntity;
        boolean warrior = !archer && !shielded && !knight && !healer;
        boolean aggressive = entity.isAggressive();
        float attack = this.attackTime;

        this.club.visible = warrior;
        this.spear.visible = shielded;
        this.scepter.visible = healer;
        this.crest.visible = knight;
        this.helmet.visible = knight;
        this.brow.visible = !knight;
        this.antennaR.visible = !knight;
        this.antennaL.visible = !knight;

        float walk = limbSwing * 0.6662F;
        float amp = Math.min(1.0F, limbSwingAmount);
        float swing = Mth.cos(walk) * amp;
        float bob = -Mth.abs(Mth.cos(walk)) * 0.8F * amp;
        float breathe = Mth.sin(ageInTicks * 0.08F);
        float lean = Mth.sin(attack * pi);

        // Cabeza: sigue la mirada, cabecea al caminar y se adelanta al atacar
        this.head.yRot = netHeadYaw * (pi / 180F);
        this.head.xRot += headPitch * (pi / 180F) + Mth.cos(walk * 2.0F) * 0.05F * amp - breathe * 0.02F + lean * 0.2F;
        this.head.y += bob;

        // Torso: respira, se balancea al caminar y se inclina al atacar
        this.body.y += bob * 0.6F;
        this.body.xRot += breathe * 0.02F + lean * 0.3F;
        this.body.zRot = Mth.cos(walk) * 0.05F * amp;
        this.body.yRot = Mth.cos(walk) * 0.1F * amp;

        // Piernas
        this.rightLeg.xRot = swing * 1.4F;
        this.leftLeg.xRot = -swing * 1.4F;

        // Antenas: se mecen y van un poco por detras del cuerpo
        this.antennaR.zRot += Mth.sin(ageInTicks * 0.1F) * 0.08F + swing * 0.1F;
        this.antennaL.zRot -= Mth.sin(ageInTicks * 0.1F + 1.5F) * 0.08F + swing * 0.1F;
        this.antennaR.xRot += Mth.cos(walk * 2.0F) * 0.06F * amp;
        this.antennaL.xRot += Mth.cos(walk * 2.0F) * 0.06F * amp;

        // Brazos relajados
        this.rightArm.y += bob * 0.7F;
        this.leftArm.y += bob * 0.7F;
        this.rightArm.xRot = -swing;
        this.leftArm.xRot = swing;
        this.rightArm.zRot = 0.1F + Mth.cos(ageInTicks * 0.09F) * 0.04F;
        this.leftArm.zRot = -0.1F - Mth.cos(ageInTicks * 0.09F) * 0.04F;

        // Guerrero: garrote en alto, golpe hacia abajo
        if (warrior && aggressive) {
            this.rightArm.xRot = strikeCurve(attack, -1.6F, -2.7F, -0.3F);
            this.leftArm.xRot = -1.4F + Mth.sin(ageInTicks * 0.3F) * 0.05F;
            this.rightArm.zRot = 0.1F;
            this.leftArm.zRot = -0.1F;
        }

        // Arquero: apunta con los dos brazos
        if (archer && aggressive) {
            this.rightArm.yRot = -0.1F + this.head.yRot;
            this.leftArm.yRot = 0.1F + this.head.yRot + 0.4F;
            this.rightArm.xRot = (-pi / 2F) + this.head.xRot + Mth.sin(ageInTicks * 0.1F) * 0.02F;
            this.leftArm.xRot = (-pi / 2F) + this.head.xRot + Mth.sin(ageInTicks * 0.1F + 1.0F) * 0.02F;
            this.rightArm.zRot = 0.0F;
            this.leftArm.zRot = 0.0F;
        }

        // Escudero: estocada con la lanza (la punta siempre apunta al frente) y escudo adelantado
        if (shielded && aggressive) {
            this.rightArm.xRot = strikeCurve(attack, -0.9F, -0.3F, -1.35F);
            this.rightArm.zRot = 0.0F;
            this.spear.xRot = (pi / 2F) - this.rightArm.xRot + 0.1F;
            this.spear.y += Mth.sin(attack * pi) * 4.0F;
            this.leftArm.xRot = -0.35F;
            this.leftArm.zRot = -0.05F;
        }

        // Caballero: tajo con la espada y escudo al frente
        if (knight && aggressive) {
            this.rightArm.xRot = strikeCurve(attack, -1.1F, -2.3F, -0.2F);
            this.rightArm.zRot = 0.0F;
            this.leftArm.xRot = -0.5F + Mth.sin(ageInTicks * 0.1F) * 0.02F;
            this.leftArm.zRot = -0.15F;
        }

        // Curandero: sostiene el cetro y lo alza cuando cura
        if (healer) {
            float heal = ((AlienHealerEntity) entity).getHealAnim();
            this.rightArm.xRot = Mth.lerp(heal, -0.15F + Mth.sin(ageInTicks * 0.05F) * 0.05F,
                    -2.3F + Mth.sin(ageInTicks * 0.9F) * 0.08F);
            this.rightArm.zRot = 0.05F;
            this.leftArm.xRot = Mth.lerp(heal, this.leftArm.xRot, -1.1F);
            this.scepter.zRot += Mth.sin(ageInTicks * 0.3F) * 0.05F * heal;
        }

        // Compensa la inclinacion del brazo para que lanza y cetro no crucen el cuerpo
        this.spear.zRot = -this.rightArm.zRot - 0.06F;
        this.scepter.zRot += -this.rightArm.zRot - 0.08F;

        // Montado: piernas hacia delante, abiertas
        if (this.riding) {
            this.rightLeg.xRot = -1.4137167F;
            this.leftLeg.xRot = -1.4137167F;
            this.rightLeg.yRot = 0.31415927F;
            this.leftLeg.yRot = -0.31415927F;
        }
    }

    @Override
    public void translateToHand(HumanoidArm arm, PoseStack poseStack) {
        ModelPart part = arm == HumanoidArm.RIGHT ? this.rightArm : this.leftArm;
        part.translateAndRotate(poseStack);
    }
}
