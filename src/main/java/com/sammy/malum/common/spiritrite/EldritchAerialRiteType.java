package com.sammy.malum.common.spiritrite;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.packets.particle.BlockSparkleParticlePacket;
import com.sammy.malum.common.packets.particle.MagicParticlePacket;
import com.sammy.malum.core.registry.misc.EffectRegistry;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;
import static com.sammy.malum.core.registry.misc.PacketRegistry.INSTANCE;

public class EldritchAerialRiteType extends MalumRiteType {
    public EldritchAerialRiteType() {
        super("eldritch_aerial_rite", false, ELDRITCH_SPIRIT, ARCANE_SPIRIT, AERIAL_SPIRIT, AERIAL_SPIRIT);
    }

    @Override
    public void riteEffect(World world, BlockPos pos) {
        if (MalumHelper.areWeOnServer(world)) {
            BlockState filter = world.getBlockState(pos.down());
            ArrayList<BlockPos> positions = getNearbyBlocksUnderBase(Block.class, world, pos, false);
            positions.removeIf(p -> {
                if (p.getX() == pos.getX() && p.getZ() == pos.getZ()) {
                    return true;
                }
                BlockState state = world.getBlockState(p);
                if (state.isAir(world, p)) {
                    return true;
                }
                return !filter.isAir(world, pos) && !filter.isIn(state.getBlock());
            });
            positions.forEach(p -> {
                BlockState stateBelow = world.getBlockState(p.down());
                if (!stateBelow.isSolid() || stateBelow.isIn(BlockTags.SLABS)) {
                    BlockState state = world.getBlockState(p);
                    FallingBlockEntity fallingblockentity = new FallingBlockEntity(world, (double) p.getX() + 0.5D, p.getY(), (double) p.getZ() + 0.5D, state);
                    world.addEntity(fallingblockentity);
                    INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> world.getChunkAt(pos)), new BlockSparkleParticlePacket(AERIAL_SPIRIT_COLOR, p.getX(), p.getY(), p.getZ()));
                }
            });
        }
    }

    @Override
    public void corruptedRiteEffect(World world, BlockPos pos) {
        if (MalumHelper.areWeOnServer(world)) {
            getNearbyEntities(PlayerEntity.class, world, pos, false).forEach(e -> {
                if (e.getActivePotionEffect(EffectRegistry.CORRUPTED_AERIAL_AURA.get()) == null) {
                    INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MagicParticlePacket(AERIAL_SPIRIT_COLOR, e.getPosition().getX(), e.getPosition().getY() + e.getHeight() / 2f, e.getPosition().getZ()));
                }
                e.addPotionEffect(new EffectInstance(EffectRegistry.CORRUPTED_AERIAL_AURA.get(), 100, 40));
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