package com.futuremod.item;

import com.futuremod.entity.PrimitiveArrow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PrimitiveArrowItem extends ArrowItem {

    public PrimitiveArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter) {
        return new PrimitiveArrow(level, shooter);
    }
}
