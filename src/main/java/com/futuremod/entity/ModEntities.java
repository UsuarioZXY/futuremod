package com.futuremod.entity;

import com.futuremod.FutureMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, FutureMod.MODID);

    public static final RegistryObject<EntityType<AlienEntity>> ALIEN = ENTITIES.register("alien",
            () -> EntityType.Builder.of(AlienEntity::new, MobCategory.MONSTER)
                    .sized(0.7F, 1.95F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(FutureMod.MODID, "alien").toString()));

    public static final RegistryObject<EntityType<AlienArcherEntity>> ALIEN_ARCHER = ENTITIES.register("alien_archer",
            () -> EntityType.Builder.of(AlienArcherEntity::new, MobCategory.MONSTER)
                    .sized(0.7F, 1.95F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(FutureMod.MODID, "alien_archer").toString()));

    public static final RegistryObject<EntityType<AlienShieldbearerEntity>> ALIEN_SHIELDBEARER = ENTITIES.register("alien_shieldbearer",
            () -> EntityType.Builder.of(AlienShieldbearerEntity::new, MobCategory.MONSTER)
                    .sized(0.7F, 1.95F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(FutureMod.MODID, "alien_shieldbearer").toString()));

    public static final RegistryObject<EntityType<PrimitiveArrow>> PRIMITIVE_ARROW = ENTITIES.register("primitive_arrow",
            () -> EntityType.Builder.<PrimitiveArrow>of(PrimitiveArrow::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build(new ResourceLocation(FutureMod.MODID, "primitive_arrow").toString()));
}
