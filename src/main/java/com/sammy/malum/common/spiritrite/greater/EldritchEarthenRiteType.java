package com.sammy.malum.common.spiritrite.greater;

import com.sammy.malum.common.blockentity.totem.TotemBaseBlockEntity;
import com.sammy.malum.common.packets.particle.block.BlockSparkleParticlePacket;
import com.sammy.malum.core.systems.rites.BlockAffectingRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.ortus.setup.OrtusParticleRegistry;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.PacketDistributor;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static com.sammy.malum.core.setup.content.SpiritTypeRegistry.*;
import static com.sammy.malum.core.setup.server.PacketRegistry.MALUM_CHANNEL;

public class EldritchEarthenRiteType extends MalumRiteType {
    public EldritchEarthenRiteType() {
        super("eldritch_earthen_rite", ELDRITCH_SPIRIT, ARCANE_SPIRIT, EARTHEN_SPIRIT, EARTHEN_SPIRIT);
    }

    @Override
    public MalumRiteEffect getNaturalRiteEffect() {
        return new BlockAffectingRiteEffect() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void riteEffect(TotemBaseBlockEntity totemBase) {
                Level level = totemBase.getLevel();
                getBlocksUnderBase(totemBase, Block.class).forEach(p -> {
                    BlockState state = level.getBlockState(p);
                    boolean canBreak = !state.isAir() && state.getDestroySpeed(level, p) != -1;
                    if (canBreak) {
                        level.destroyBlock(p, true);
                        MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(p)), new BlockSparkleParticlePacket(EARTHEN_SPIRIT.getColor(), p));
                    }
                });
            }

            @Override
            public boolean canAffectBlock(TotemBaseBlockEntity totemBase, HashSet<Block> filters, BlockState state, BlockPos pos) {
                return super.canAffectBlock(totemBase, filters, state, pos) && !state.isAir();
            }
        };
    }

    @Override
    public MalumRiteEffect getCorruptedEffect() {
        return new BlockAffectingRiteEffect() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void riteEffect(TotemBaseBlockEntity totemBase) {
                Level level = totemBase.getLevel();
                getBlocksUnderBase(totemBase, AirBlock.class).forEach(p -> {
                    BlockState cobblestone = Blocks.COBBLESTONE.defaultBlockState();
                    level.setBlockAndUpdate(p, cobblestone);
                    level.levelEvent(2001, p, Block.getId(cobblestone));
                    MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(p)), new BlockSparkleParticlePacket(EARTHEN_SPIRIT.getColor(), p));
                });
            }
        };
    }
}