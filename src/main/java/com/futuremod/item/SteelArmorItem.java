package com.futuremod.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import java.util.EnumMap;
import java.util.UUID;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

/** Pieza de armadura de acero: ademas de la defensa, da vida maxima extra (y velocidad en las botas). */
public class SteelArmorItem extends ArmorItem {
    private static final EnumMap<ArmorItem.Type, UUID> HEALTH_UUIDS = new EnumMap<>(ArmorItem.Type.class);
    private static final UUID SPEED_UUID = UUID.fromString("7c1d9a52-3b0e-4f6a-9d11-5a2e8c4f0b05");

    static {
        HEALTH_UUIDS.put(ArmorItem.Type.BOOTS, UUID.fromString("7c1d9a52-3b0e-4f6a-9d11-5a2e8c4f0b01"));
        HEALTH_UUIDS.put(ArmorItem.Type.LEGGINGS, UUID.fromString("7c1d9a52-3b0e-4f6a-9d11-5a2e8c4f0b02"));
        HEALTH_UUIDS.put(ArmorItem.Type.CHESTPLATE, UUID.fromString("7c1d9a52-3b0e-4f6a-9d11-5a2e8c4f0b03"));
        HEALTH_UUIDS.put(ArmorItem.Type.HELMET, UUID.fromString("7c1d9a52-3b0e-4f6a-9d11-5a2e8c4f0b04"));
    }

    private final double bonusHealth;
    private final double speedBonus;

    /** bonusHealth en puntos de vida (2.0 = 1 corazon). */
    public SteelArmorItem(ArmorMaterial material, ArmorItem.Type type, Properties properties, double bonusHealth) {
        this(material, type, properties, bonusHealth, 0.0D);
    }

    /** speedBonus como fraccion de la velocidad base (0.10 = +10%). */
    public SteelArmorItem(ArmorMaterial material, ArmorItem.Type type, Properties properties,
                          double bonusHealth, double speedBonus) {
        super(material, type, properties);
        this.bonusHealth = bonusHealth;
        this.speedBonus = speedBonus;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        Multimap<Attribute, AttributeModifier> base = super.getDefaultAttributeModifiers(slot);
        if (slot != this.getEquipmentSlot()) {
            return base;
        }
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(base);
        builder.put(Attributes.MAX_HEALTH, new AttributeModifier(
                HEALTH_UUIDS.get(this.getType()), "Steel armor health", this.bonusHealth,
                AttributeModifier.Operation.ADDITION));
        if (this.speedBonus != 0.0D) {
            builder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(
                    SPEED_UUID, "Steel boots speed", this.speedBonus,
                    AttributeModifier.Operation.MULTIPLY_BASE));
        }
        return builder.build();
    }

    /** true si lleva casco, peto, pantalones y botas de acero. */
    public static boolean isWearingFullSet(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof SteelArmorItem
                && entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof SteelArmorItem
                && entity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof SteelArmorItem
                && entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof SteelArmorItem;
    }
}
