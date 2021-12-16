package com.sammy.malum.common.spiritrite;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.packets.particle.BlockSparkleParticlePacket;
import com.sammy.malum.common.packets.particle.MagicParticlePacket;
import com.sammy.malum.core.registry.misc.EffectRegistry;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.world.level.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.Level.Level;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;
import static com.sammy.malum.core.registry.misc.PacketRegistry.INSTANCE;

public class EldritchAerialRiteType extends MalumRiteType {
    public EldritchAerialRiteType() {
        super("eldritch_aerial_rite", false, ELDRITCH_SPIRIT, ARCANE_SPIRIT, AERIAL_SPIRIT, AERIAL_SPIRIT);
    }

    @Override
    public void riteEffect(Level Level, BlockPos pos) {
        if (MalumHelper.areWeOnServer(Level)) {
            BlockState filter = Level.getBlockState(pos.below());
            ArrayList<BlockPos> positions = getNearbyBlocksUnderBase(Block.class, Level, pos, false);
            positions.removeIf(p -> {
                if (p.getX() == pos.getX() && p.getZ() == pos.getZ()) {
                    return true;
                }
                BlockState state = Level.getBlockState(p);
                if (state.isAir(Level, p)) {
                    return true;
                }
                return !filter.isAir(Level, pos) && !filter.is(state.getBlock());
            });
            positions.forEach(p -> {
                BlockState stateBelow = Level.getBlockState(p.below());
                if (!stateBelow.canOcclude() || stateBelow.is(BlockTags.SLABS)) {
                    BlockState state = Level.getBlockState(p);
                    FallingBlockEntity fallingblockentity = new FallingBlockEntity(Level, (double) p.getX() + 0.5D, p.getY(), (double) p.getZ() + 0.5D, state);
                    Level.addFreshEntity(fallingblockentity);
                    INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> Level.getChunkAt(pos)), new BlockSparkleParticlePacket(AERIAL_SPIRIT_COLOR, p.getX(), p.getY(), p.getZ()));
                }
            });
        }
    }

    @Override
    public void corruptedRiteEffect(Level Level, BlockPos pos) {
        if (MalumHelper.areWeOnServer(Level)) {
            getNearbyEntities(Player.class, Level, pos, false).forEach(e -> {
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