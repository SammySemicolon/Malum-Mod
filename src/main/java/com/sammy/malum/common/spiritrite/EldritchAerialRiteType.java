package com.sammy.malum.common.spiritrite;

import com.sammy.malum.common.packets.particle.BlockSparkleParticlePacket;
import com.sammy.malum.common.packets.particle.MagicParticlePacket;
import com.sammy.malum.core.registry.misc.EffectRegistry;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.PacketDistributor;

import java.util.ArrayList;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;
import static com.sammy.malum.core.registry.misc.PacketRegistry.INSTANCE;

public class EldritchAerialRiteType extends MalumRiteType {
    public EldritchAerialRiteType() {
        super("eldritch_aerial_rite", false, ELDRITCH_SPIRIT, ARCANE_SPIRIT, AERIAL_SPIRIT, AERIAL_SPIRIT);
    }

    @Override
    public void riteEffect(Level level, BlockPos pos) {
        if (!level.isClientSide) {
            BlockState filter = level.getBlockState(pos.below());
            ArrayList<BlockPos> positions = getNearbyBlocksUnderBase(Block.class, level, pos, false);
            positions.removeIf(p -> {
                if (p.getX() == pos.getX() && p.getZ() == pos.getZ()) {
                    return true;
                }
                BlockState state = level.getBlockState(p);
                if (state.isAir()) {
                    return true;
                }
                return !filter.isAir() && !filter.is(state.getBlock());
            });
            positions.forEach(p -> {
                BlockState stateBelow = level.getBlockState(p.below());
                if (!stateBelow.canOcclude() || stateBelow.is(BlockTags.SLABS)) {
                    BlockState state = level.getBlockState(p);
                    FallingBlockEntity fallingblockentity = new FallingBlockEntity(level, (double) p.getX() + 0.5D, p.getY(), (double) p.getZ() + 0.5D, state);
                    level.addFreshEntity(fallingblockentity);
                    INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(pos)), new BlockSparkleParticlePacket(AERIAL_SPIRIT_COLOR, p.getX(), p.getY(), p.getZ()));
                }
            });
        }
    }

    @Override
    public void corruptedRiteEffect(Level level, BlockPos pos) {
        if (!level.isClientSide) {
            getNearbyEntities(Player.class, level, pos, false).forEach(e -> {
                if (e.getEffect(EffectRegistry.CORRUPTED_AERIAL_AURA.get()) == null) {
                    INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MagicParticlePacket(AERIAL_SPIRIT_COLOR, e.blockPosition().getX(), e.blockPosition().getY() + e.getBbHeight() / 2f, e.blockPosition().getZ()));
                }
                e.addEffect(new MobEffectInstance(EffectRegistry.CORRUPTED_AERIAL_AURA.get(), 100, 40));
            });
        }
    }

    @Override
    public int interval(boolean corrupted) {
        return corrupted ? defaultInterval() : defaultInterval() * 5;
    }

    @Override
    public int range(boolean corrupted) {
        return defaultRange() / 2;
    }
}