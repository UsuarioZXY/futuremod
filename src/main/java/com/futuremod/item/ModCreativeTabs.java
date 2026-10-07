package com.futuremod.item;

import com.futuremod.FutureMod;
import com.futuremod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FutureMod.MODID);

    public static final RegistryObject<CreativeModeTab> FUTURE_TAB = TABS.register("future_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("creativetab.futuremod.future_tab"))
                    .icon(() -> new ItemStack(ModItems.STEEL_INGOT.get()))
                    .displayItems((params, output) -> {
                        output.accept(GuideBook.create());
                        output.accept(ModBlocks.STEEL_ORE.get());
                        output.accept(ModBlocks.DEEPSLATE_STEEL_ORE.get());
                        output.accept(ModItems.RAW_STEEL.get());
                        output.accept(ModItems.STEEL_INGOT.get());
                        output.accept(ModItems.STEEL_NUGGET.get());
                        output.accept(ModItems.STEEL_ROD.get());
                        output.accept(ModItems.STEEL_CHIP.get());
                        output.accept(ModItems.STEEL_MOTOR.get());
                        output.accept(ModItems.STEEL_CORE.get());
                        output.accept(ModBlocks.REINFORCED_GLASS.get());
                        output.accept(ModItems.STEEL_HELMET.get());
                        output.accept(ModItems.STEEL_CHESTPLATE.get());
                        output.accept(ModItems.STEEL_LEGGINGS.get());
                        output.accept(ModItems.STEEL_BOOTS.get());
                        output.accept(ModItems.STEEL_SWORD.get());
                        output.accept(ModItems.STEEL_PICKAXE.get());
                        output.accept(ModItems.STEEL_AXE.get());
                        output.accept(ModItems.STEEL_SHOVEL.get());
                        output.accept(ModItems.STEEL_HOE.get());
                        output.accept(ModItems.ALIEN_BONE.get());
                        output.accept(ModItems.BROKEN_BONE.get());
                        output.accept(ModItems.PRIMITIVE_CLOTH_FRAGMENT.get());
                        output.accept(ModItems.PRIMITIVE_CLOTH.get());
                        output.accept(ModItems.PRIMITIVE_THREAD.get());
                        output.accept(ModItems.PRIMITIVE_SWORD.get());
                        output.accept(ModItems.PRIMITIVE_BOW.get());
                        output.accept(ModItems.PRIMITIVE_ARROW.get());
                        output.accept(ModItems.PRIMITIVE_SHIELD.get());
                        output.accept(ModItems.ALIEN_SPAWN_EGG.get());
                        output.accept(ModItems.ALIEN_ARCHER_SPAWN_EGG.get());
                        output.accept(ModItems.ALIEN_SHIELDBEARER_SPAWN_EGG.get());
                        output.accept(ModItems.ALIEN_KNIGHT_SPAWN_EGG.get());
                        output.accept(ModItems.RODIA_SPAWN_EGG.get());
                        output.accept(ModItems.ALIEN_HEALER_SPAWN_EGG.get());
                    })
                    .build());
}
