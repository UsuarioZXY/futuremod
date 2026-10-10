package com.futuremod.item;

import com.futuremod.FutureMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;

/** Cohete: viaja entre el mundo normal y el planeta Verdia. Cada viaje gasta un uso. */
public class RocketItem extends Item {
    public static final ResourceKey<Level> VERDIA =
            ResourceKey.create(Registries.DIMENSION, new ResourceLocation(FutureMod.MODID, "verdia"));

    public RocketItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide || !(player instanceof ServerPlayer sp) || sp.getServer() == null) {
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
        }
        boolean toVerdia = level.dimension() != VERDIA;
        ServerLevel dest = sp.getServer().getLevel(toVerdia ? VERDIA : Level.OVERWORLD);
        if (dest == null) {
            sp.displayClientMessage(Component.literal("No se puede viajar: el planeta Verdia no est\u00e1 disponible."), true);
            return InteractionResultHolder.fail(stack);
        }
        // despegue: fuego y humo donde estabas
        ServerLevel from = sp.serverLevel();
        from.sendParticles(ParticleTypes.FLAME, sp.getX(), sp.getY(), sp.getZ(), 40, 0.4D, 0.1D, 0.4D, 0.15D);
        from.sendParticles(ParticleTypes.LARGE_SMOKE, sp.getX(), sp.getY(), sp.getZ(), 30, 0.5D, 0.2D, 0.5D, 0.05D);
        from.playSound(null, sp.blockPosition(), SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.PLAYERS, 1.5F, 0.6F);

        BlockPos target = findLanding(dest, sp, toVerdia);
        sp.teleportTo(dest, target.getX() + 0.5D, target.getY(), target.getZ() + 0.5D, sp.getYRot(), sp.getXRot());
        sp.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 200, 0));
        sp.displayClientMessage(Component.literal(toVerdia ? "Has llegado a Verdia." : "Has vuelto a casa."), true);
        sp.getCooldowns().addCooldown(this, 100);
        stack.hurtAndBreak(1, sp, p -> p.broadcastBreakEvent(hand));
        return InteractionResultHolder.success(stack);
    }

    /** Busca un lugar firme: en Verdia, sobre tu misma posicion; en el mundo normal, tu cama o el spawn. */
    private BlockPos findLanding(ServerLevel dest, ServerPlayer sp, boolean toVerdia) {
        BlockPos base;
        if (toVerdia) {
            base = sp.blockPosition();
        } else {
            BlockPos respawn = sp.getRespawnPosition();
            base = (respawn != null && sp.getRespawnDimension() == Level.OVERWORLD) ? respawn : dest.getSharedSpawnPos();
        }
        for (int i = 0; i < 24; i++) {
            int x = base.getX() + (i == 0 ? 0 : ((i * 7) % 17) - 8);
            int z = base.getZ() + (i == 0 ? 0 : ((i * 11) % 17) - 8);
            dest.getChunk(x >> 4, z >> 4);
            int y = dest.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
            BlockPos pos = new BlockPos(x, y, z);
            if (dest.getFluidState(pos.below()).isEmpty() && dest.getFluidState(pos).isEmpty()) {
                return pos;
            }
        }
        int y = dest.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, base.getX(), base.getZ());
        return new BlockPos(base.getX(), y + 1, base.getZ());
    }
}
