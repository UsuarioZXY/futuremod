package com.futuremod.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ModTiers {
    // nivel de mineria 3 (como diamante), 2200 usos, velocidad 8, +3.5 de dano, encantabilidad 12
    public static final Tier STEEL = new ForgeTier(3, 2200, 8.0F, 3.5F, 12,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(ModItems.STEEL_INGOT.get()));
}
