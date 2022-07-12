package com.sammy.malum.common.spiritrite;

import com.sammy.malum.common.block.blight.BlightedSoilBlock;
import com.sammy.malum.common.blockentity.altar.IAltarProvider;
import com.sammy.malum.common.blockentity.totem.TotemBaseBlockEntity;
import com.sammy.malum.common.recipe.BlockTransmutationRecipe;
import com.sammy.malum.common.worldevent.TotemCreatedBlightEvent;
import com.sammy.malum.core.systems.rites.BlockAffectingRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.ortus.handlers.WorldEventHandler;
import com.sammy.ortus.helpers.BlockHelper;
import com.sammy.ortus.setup.OrtusParticleRegistry;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntityInventory;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        return new MalumRiteEffect() {

            @Override
            public int getRiteEffectRadius() {
                return BASE_RADIUS * 2;
            }

            @Override
            public int getRiteEffectTickRate() {
                return BASE_TICK_RATE * 5;
            }

            @SuppressWarnings("ConstantConditions")
            @Override
            public void riteEffect(TotemBaseBlockEntity totemBase) {
                Level level = totemBase.getLevel();
                BlockPos pos = totemBase.getBlockPos();

                List<BlockPos> nearbyBlocks = getNearbyBlocks(totemBase, BlightedSoilBlock.class).collect(Collectors.toList());

                for (BlockPos p : nearbyBlocks) {
                    BlockPos posToTransmute = p.above();
                    BlockState stateToTransmute = level.getBlockState(posToTransmute);
                    if (level.getBlockEntity(posToTransmute) instanceof IAltarProvider iAltarProvider) {
                        OrtusBlockEntityInventory inventoryForAltar = iAltarProvider.getInventoryForAltar();
                        ItemStack stack = inventoryForAltar.getStackInSlot(0);

                        continue;
                    }
                    BlockTransmutationRecipe recipe = BlockTransmutationRecipe.getRecipe(level, stateToTransmute.getBlock());
                    if (recipe != null) {
                        Block block = recipe.output;
                        BlockEntity entity = level.getBlockEntity(posToTransmute);
                        BlockState newState = BlockHelper.setBlockStateWithExistingProperties(level, posToTransmute, block.defaultBlockState(), 3);
                        level.levelEvent(2001, posToTransmute, Block.getId(newState));
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
                }
            }
        };
    }
}