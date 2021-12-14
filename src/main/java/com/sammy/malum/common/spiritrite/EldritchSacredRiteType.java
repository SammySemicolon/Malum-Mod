package com.sammy.malum.common.spiritrite;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.packets.particle.BlockMistParticlePacket;
import com.sammy.malum.common.packets.particle.MagicParticlePacket;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;
import static com.sammy.malum.core.registry.misc.PacketRegistry.INSTANCE;

public class EldritchSacredRiteType extends MalumRiteType {
    public EldritchSacredRiteType() {
        super("eldritch_sacred_rite", false, ELDRITCH_SPIRIT, ARCANE_SPIRIT, SACRED_SPIRIT, SACRED_SPIRIT);
    }

    @Override
    public void riteEffect(World world, BlockPos pos) {
        if (MalumHelper.areWeOnServer(world)) {
            getNearbyBlocks(IGrowable.class, world, pos, false).forEach(p -> {
                if (world.rand.nextFloat() <= 0.02f) {
                    BlockState state = world.getBlockState(p);
                    IGrowable growable = (IGrowable) state.getBlock();
                    ServerWorld serverWorld = (ServerWorld) world;
                    if (growable.canGrow(serverWorld, p, state, false)) {
                        growable.grow(serverWorld, world.rand, p, state);
                        BlockPos particlePos = state.isSolid() ? p : p.down();
                        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> world.getChunkAt(pos)), new BlockMistParticlePacket(SACRED_SPIRIT_COLOR, particlePos.getX(), particlePos.getY(), particlePos.getZ()));
                    }
                }
            });
        }
    }

    @Override
    public void corruptedRiteEffect(World world, BlockPos pos) {
        if (MalumHelper.areWeOnServer(world)) {
            ArrayList<AnimalEntity> entities = getNearbyEntities(AnimalEntity.class, world, pos, true);
            entities.removeIf(e -> e.getGrowingAge() < 0);
            if (entities.size() > 30) {
                return;
            }
            entities.forEach(e -> {
                if (e.canFallInLove() && e.getGrowingAge() == 0) {
                    if (world.rand.nextFloat() <= 0.05f) {
                        e.setInLove(600);
                        INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MagicParticlePacket(SACRED_SPIRIT_COLOR, e.getPosition().getX(), e.getPosition().getY()+e.getHeight()/2f, e.getPosition().getZ()));
                    }
                }
            });
        }
    }

    @Override
    public int range(boolean corrupted) {
        return defaultRange()/2;
    }
}
