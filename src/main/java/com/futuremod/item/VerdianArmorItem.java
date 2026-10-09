package com.futuremod.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import java.util.EnumMap;
import java.util.UUID;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

/** Armadura verdiana: cada pieza da +5% de ataque (con cualquier cosa) y las botas +15% de velocidad. */
public class VerdianArmorItem extends ArmorItem {
    private static final EnumMap<ArmorItem.Type, UUID> ATTACK_UUIDS = new EnumMap<>(ArmorItem.Type.class);
    private static final UUID SPEED_UUID = UUID.fromString("5e2b7c10-4a39-4d6e-8f21-9b3c0d1a7e05");

    static {
        ATTACK_UUIDS.put(ArmorItem.Type.BOOTS, UUID.fromString("5e2b7c10-4a39-4d6e-8f21-9b3c0d1a7e01"));
        ATTACK_UUIDS.put(ArmorItem.Type.LEGGINGS, UUID.fromString("5e2b7c10-4a39-4d6e-8f21-9b3c0d1a7e02"));
        ATTACK_UUIDS.put(ArmorItem.Type.CHESTPLATE, UUID.fromString("5e2b7c10-4a39-4d6e-8f21-9b3c0d1a7e03"));
        ATTACK_UUIDS.put(ArmorItem.Type.HELMET, UUID.fromString("5e2b7c10-4a39-4d6e-8f21-9b3c0d1a7e04"));
    }

    private final double speedBonus;

    public VerdianArmorItem(ArmorMaterial material, ArmorItem.Type type, Properties properties) {
        this(material, type, properties, 0.0D);
    }

    /** speedBonus como fraccion de la velocidad base (0.15 = +15%). */
    public VerdianArmorItem(ArmorMaterial material, ArmorItem.Type type, Properties properties, double speedBonus) {
        super(material, type, properties);
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
        // MULTIPLY_TOTAL: multiplica el ataque final, sea con la mano o con cualquier arma
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(
                ATTACK_UUIDS.get(this.getType()), "Verdian armor attack", 0.05D,
                AttributeModifier.Operation.MULTIPLY_TOTAL));
        if (this.speedBonus != 0.0D) {
            builder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(
                    SPEED_UUID, "Verdian boots speed", this.speedBonus,
                    AttributeModifier.Operation.MULTIPLY_BASE));
        }
        return builder.build();
    }
}
