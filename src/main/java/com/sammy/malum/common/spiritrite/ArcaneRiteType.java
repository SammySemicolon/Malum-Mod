package com.sammy.malum.common.spiritrite;

import com.sammy.malum.common.blockentity.totem.TotemBaseBlockEntity;
import com.sammy.malum.common.recipe.BlockTransmutationRecipe;
import com.sammy.malum.common.worldevent.TotemCreatedBlightEvent;
import com.sammy.malum.core.systems.rites.BlockAffectingRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.ortus.handlers.WorldEventHandler;
import com.sammy.ortus.helpers.BlockHelper;
import com.sammy.ortus.setup.OrtusParticleRegistry;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static com.sammy.malum.core.setup.content.SpiritTypeRegistry.ARCANE_SPIRIT;

public class ArcaneRiteType extends MalumRiteType {
    public ArcaneRiteType() {
        super("arcane_rite", ARCANE_SPIRIT, ARCANE_SPIRIT, ARCANE_SPIRIT, ARCANE_SPIRIT, ARCANE_SPIRIT);
    }

    @Override
    public MalumRiteEffect getNaturalRiteEffect() {
        return new MalumRiteEffect() {
            @Override
            public boolean isOneAndDone() {
                return true;
            }

            @SuppressWarnings("ConstantConditions")
            @Override
            public void riteEffect(TotemBaseBlockEntity totemBase) {
                WorldEventHandler.addWorldEvent(totemBase.getLevel(), new TotemCreatedBlightEvent().setPosition(totemBase.getBlockPos()).setBlightData(1, 4, 4));
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
                BlockPos pos = totemBase.getBlockPos();

                getNearbyBlocks(totemBase, Block.class).forEach(p -> {
                    BlockState state = level.getBlockState(p);
                    BlockTransmutationRecipe recipe = BlockTransmutationRecipe.getRecipe(level, state.getBlock());
                    if (recipe != null) {
                        Block block = recipe.output;
                        BlockEntity entity = level.getBlockEntity(p);
                        BlockState newState = BlockHelper.setBlockStateWithExistingProperties(level, p, block.defaultBlockState(), 3);
                        level.levelEvent(2001, p, Block.getId(newState));
                        if (block instanceof EntityBlock entityBlock) {
                            if (entity != null) {
                                BlockEntity newEntity = entityBlock.newBlockEntity(pos, newState);
                                if (newEntity != null) {
                                    if (newEntity.getClass().equals(entity.getClass())) {
                                        level.setBlockEntity(entity);
                                    }
                                }
                            }
                        }
                    }
                });
            }
        };
    }
}