package com.sammy.malum.common.spiritrite;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.packets.particle.BlockMistParticlePacket;
import com.sammy.malum.common.packets.particle.MagicParticlePacket;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.Level.Level;
import net.minecraft.Level.server.ServerLevel;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;
import static com.sammy.malum.core.registry.misc.PacketRegistry.INSTANCE;

public class EldritchSacredRiteType extends MalumRiteType {
    public EldritchSacredRiteType() {
        super("eldritch_sacred_rite", false, ELDRITCH_SPIRIT, ARCANE_SPIRIT, SACRED_SPIRIT, SACRED_SPIRIT);
    }

    @Override
    public void riteEffect(Level Level, BlockPos pos) {
        if (MalumHelper.areWeOnServer(Level)) {
            getNearbyBlocks(IGrowable.class, Level, pos, false).forEach(p -> {
                if (Level.random.nextFloat() <= 0.02f) {
                    BlockState state = Level.getBlockState(p);
                    IGrowable growable = (IGrowable) state.getBlock();
                    ServerLevel serverLevel = (ServerLevel) Level;
                    if (growable.isValidBonemealTarget(serverLevel, p, state, false)) {
                        growable.performBonemeal(serverLevel, Level.random, p, state);
                        BlockPos particlePos = state.canOcclude() ? p : p.below();
                        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> Level.getChunkAt(pos)), new BlockMistParticlePacket(SACRED_SPIRIT_COLOR, particlePos.getX(), particlePos.getY(), particlePos.getZ()));
                    }
                }
            });
        }
    }

    @Override
    public void corruptedRiteEffect(Level Level, BlockPos pos) {
        if (MalumHelper.areWeOnServer(Level)) {
            ArrayList<AnimalEntity> entities = getNearbyEntities(AnimalEntity.class, Level, pos, true);
            entities.removeIf(e -> e.getAge() < 0);
            if (entities.size() > 30) {
                return;
            }
            entities.forEach(e -> {
                if (e.canFallInLove() && e.getAge() == 0) {
                    if (Level.random.nextFloat() <= 0.05f) {
                        e.setInLoveTime(600);
                        INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MagicParticlePacket(SACRED_SPIRIT_COLOR, e.blockPosition().getX(), e.blockPosition().getY()+e.getBbHeight()/2f, e.blockPosition().getZ()));
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
