package com.sammy.malum.common.spiritrite.greater;

import com.sammy.malum.common.blockentity.totem.TotemBaseBlockEntity;
import com.sammy.malum.common.packets.particle.block.BlockDownwardSparkleParticlePacket;
import com.sammy.malum.common.packets.particle.block.BlockSparkleParticlePacket;
import com.sammy.malum.core.setup.content.SoundRegistry;
import com.sammy.malum.core.systems.rites.BlockAffectingRiteEffect;
import com.sammy.malum.core.systems.rites.EntityAffectingRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static com.sammy.malum.core.setup.content.SpiritTypeRegistry.*;
import static com.sammy.malum.core.setup.server.PacketRegistry.MALUM_CHANNEL;

public class EldritchAerialRiteType extends MalumRiteType {
    public EldritchAerialRiteType() {
        super("greater_aerial_rite", ELDRITCH_SPIRIT, ARCANE_SPIRIT, AERIAL_SPIRIT, AERIAL_SPIRIT);
    }

    @Override
    public MalumRiteEffect getNaturalRiteEffect() {
        return new BlockAffectingRiteEffect() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void riteEffect(TotemBaseBlockEntity totemBase) {
                Level level = totemBase.getLevel();
                BlockPos pos = totemBase.getBlockPos();
                getBlocksUnderBase(totemBase, Block.class).forEach(p -> {
                    BlockState stateBelow = level.getBlockState(p.below());
                    if (!stateBelow.canOcclude() || stateBelow.is(BlockTags.SLABS)) {
                        BlockState state = level.getBlockState(p);
                        if (!state.isAir()) {
                            FallingBlockEntity.fall(level, p, state);
                            level.playSound(null, p, SoundRegistry.AERIAL_FALL.get(), SoundSource.BLOCKS, 0.5f, 2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F);
                            MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(pos)), new BlockDownwardSparkleParticlePacket(AERIAL_SPIRIT.getColor(), p));
                        }
                    }
                });
            }
        };
    }

    @Override
    public MalumRiteEffect getCorruptedEffect() {
        return new EntityAffectingRiteEffect() {
            @Override
            public void riteEffect(TotemBaseBlockEntity totemBase) {

            }
        };
    }
}