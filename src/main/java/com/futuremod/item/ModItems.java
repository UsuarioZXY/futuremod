package com.futuremod.item;

import com.futuremod.FutureMod;
import com.futuremod.entity.ModEntities;
import net.minecraft.world.item.ArmorItem;
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

    public static final RegistryObject<Item> ALIEN_SPAWN_EGG =
            ITEMS.register("alien_spawn_egg",
                    () -> new ForgeSpawnEggItem(ModEntities.ALIEN, 0x6A8A6E, 0xE6E2C8, new Item.Properties()));
}
