package com.futuremod.item;

import com.futuremod.client.PrimitiveShieldRenderer;
import java.util.function.Consumer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

/** Escudo primitivo: bloquea como el escudo normal y se repara con trozos de tela primitiva. */
public class PrimitiveShieldItem extends ShieldItem {

    public PrimitiveShieldItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.is(ModItems.PRIMITIVE_CLOTH.get()) || super.isValidRepairItem(toRepair, repair);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return PrimitiveShieldRenderer.get();
            }
        });
    }
}
