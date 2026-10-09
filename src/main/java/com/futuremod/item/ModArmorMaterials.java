package com.futuremod.item;

import com.futuremod.FutureMod;
import java.util.function.Supplier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public enum ModArmorMaterials implements ArmorMaterial {
    // nombre, multiplicador de durabilidad (hierro 15, diamante 33), defensa casco/peto/pantalon/botas,
    // encantabilidad, sonido, dureza, resistencia al empuje, material de reparacion
    STEEL("steel", 140, 3, 7, 5, 3, 12, SoundEvents.ARMOR_EQUIP_IRON, 1.0F, 0.0F,
            () -> Ingredient.of(ModItems.STEEL_INGOT.get())),
    // Armadura verdiana: hueso alienigena y tela primitiva (algo menos que el hierro, pero dura mas)
    VERDIAN("verdian", 22, 2, 5, 4, 2, 9, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F,
            () -> Ingredient.of(ModItems.ALIEN_BONE.get()));

    private final String name;
    private final int durabilityMultiplier;
    private final int helmet;
    private final int chestplate;
    private final int leggings;
    private final int boots;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    ModArmorMaterials(String name, int durabilityMultiplier, int helmet, int chestplate, int leggings,
                      int boots, int enchantability, SoundEvent equipSound, float toughness,
                      float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        int base = switch (type) {
            case HELMET -> 11;
            case CHESTPLATE -> 16;
            case LEGGINGS -> 15;
            case BOOTS -> 13;
        };
        return base * durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return switch (type) {
            case HELMET -> helmet;
            case CHESTPLATE -> chestplate;
            case LEGGINGS -> leggings;
            case BOOTS -> boots;
        };
    }

    @Override
    public int getEnchantmentValue() { return enchantability; }

    @Override
    public SoundEvent getEquipSound() { return equipSound; }

    @Override
    public Ingredient getRepairIngredient() { return repairIngredient.get(); }

    @Override
    public String getName() { return FutureMod.MODID + ":" + name; }

    @Override
    public float getToughness() { return toughness; }

    @Override
    public float getKnockbackResistance() { return knockbackResistance; }
}
