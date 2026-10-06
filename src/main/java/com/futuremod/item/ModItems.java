package com.futuremod.item;

import com.futuremod.FutureMod;
import com.futuremod.entity.ModEntities;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, FutureMod.MODID);

    public static final RegistryObject<Item> RAW_STEEL =
            ITEMS.register("raw_steel", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STEEL_INGOT =
            ITEMS.register("steel_ingot", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STEEL_CHIP =
            ITEMS.register("steel_chip", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STEEL_NUGGET =
            ITEMS.register("steel_nugget", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STEEL_MOTOR =
            ITEMS.register("steel_motor", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STEEL_CORE =
            ITEMS.register("steel_core", () -> new Item(new Item.Properties()));

    // Armadura: casco, pantalones y botas dan +1 corazon (2 HP); el peto da +2 corazones (4 HP).
    public static final RegistryObject<Item> STEEL_HELMET =
            ITEMS.register("steel_helmet",
                    () -> new SteelArmorItem(ModArmorMaterials.STEEL, ArmorItem.Type.HELMET, new Item.Properties(), 2.0D));

    public static final RegistryObject<Item> STEEL_CHESTPLATE =
            ITEMS.register("steel_chestplate",
                    () -> new SteelArmorItem(ModArmorMaterials.STEEL, ArmorItem.Type.CHESTPLATE, new Item.Properties(), 4.0D));

    public static final RegistryObject<Item> STEEL_LEGGINGS =
            ITEMS.register("steel_leggings",
                    () -> new SteelArmorItem(ModArmorMaterials.STEEL, ArmorItem.Type.LEGGINGS, new Item.Properties(), 2.0D));

    public static final RegistryObject<Item> STEEL_BOOTS =
            ITEMS.register("steel_boots",
                    () -> new SteelArmorItem(ModArmorMaterials.STEEL, ArmorItem.Type.BOOTS, new Item.Properties(), 2.0D, 0.10D));

    // Botin del alienigena
    public static final RegistryObject<Item> ALIEN_BONE =
            ITEMS.register("alien_bone", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PRIMITIVE_CLOTH_FRAGMENT =
            ITEMS.register("primitive_cloth_fragment", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PRIMITIVE_CLOTH =
            ITEMS.register("primitive_cloth", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STEEL_ROD =
            ITEMS.register("steel_rod", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PRIMITIVE_THREAD =
            ITEMS.register("primitive_thread", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BROKEN_BONE =
            ITEMS.register("broken_bone", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STEEL_SWORD =
            ITEMS.register("steel_sword", () -> new SwordItem(ModTiers.STEEL, 3, -2.4F, new Item.Properties()));

    public static final RegistryObject<Item> STEEL_PICKAXE =
            ITEMS.register("steel_pickaxe", () -> new PickaxeItem(ModTiers.STEEL, 1, -2.8F, new Item.Properties()));

    public static final RegistryObject<Item> STEEL_AXE =
            ITEMS.register("steel_axe", () -> new AxeItem(ModTiers.STEEL, 5.0F, -3.0F, new Item.Properties()));

    public static final RegistryObject<Item> STEEL_SHOVEL =
            ITEMS.register("steel_shovel", () -> new ShovelItem(ModTiers.STEEL, 1.5F, -3.0F, new Item.Properties()));

    public static final RegistryObject<Item> STEEL_HOE =
            ITEMS.register("steel_hoe", () -> new HoeItem(ModTiers.STEEL, -3, 0.0F, new Item.Properties()));

    public static final RegistryObject<Item> PRIMITIVE_SHIELD =
            ITEMS.register("primitive_shield", () -> new PrimitiveShieldItem(new Item.Properties().durability(450)));

    public static final RegistryObject<Item> ALIEN_SHIELDBEARER_SPAWN_EGG =
            ITEMS.register("alien_shieldbearer_spawn_egg",
                    () -> new ForgeSpawnEggItem(ModEntities.ALIEN_SHIELDBEARER, 0x5C7A4A, 0x9A7048, new Item.Properties()));

    public static final RegistryObject<Item> ALIEN_KNIGHT_SPAWN_EGG =
            ITEMS.register("alien_knight_spawn_egg",
                    () -> new ForgeSpawnEggItem(ModEntities.ALIEN_KNIGHT, 0x6B7F8F, 0xB03A3A, new Item.Properties()));

    public static final RegistryObject<Item> RODIA_SPAWN_EGG =
            ITEMS.register("rodia_spawn_egg",
                    () -> new ForgeSpawnEggItem(ModEntities.RODIA, 0x4E6B55, 0xE0B050, new Item.Properties()));

    public static final RegistryObject<Item> PRIMITIVE_BOW =
            ITEMS.register("primitive_bow", () -> new BowItem(new Item.Properties().durability(400)));

    public static final RegistryObject<Item> PRIMITIVE_ARROW =
            ITEMS.register("primitive_arrow", () -> new PrimitiveArrowItem(new Item.Properties()));

    public static final RegistryObject<Item> ALIEN_ARCHER_SPAWN_EGG =
            ITEMS.register("alien_archer_spawn_egg",
                    () -> new ForgeSpawnEggItem(ModEntities.ALIEN_ARCHER, 0x4E8C8A, 0xE6A050, new Item.Properties()));

    public static final RegistryObject<Item> ALIEN_SPAWN_EGG =
            ITEMS.register("alien_spawn_egg",
                    () -> new ForgeSpawnEggItem(ModEntities.ALIEN, 0x6A8A6E, 0xE6E2C8, new Item.Properties()));
}
