package com.sammy.malum.common.spiritrite;

import com.sammy.malum.core.registry.ParticleRegistry;
import com.sammy.malum.core.systems.rendering.RenderUtilities;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;

public class EldritchInfernalRiteType extends MalumRiteType {
    public EldritchInfernalRiteType() {
        super("eldritch_infernal_rite", ELDRITCH_SPIRIT, ARCANE_SPIRIT, INFERNAL_SPIRIT, INFERNAL_SPIRIT);
    }

    @Override
    public int interval(boolean corrupted) {
        return corrupted ? defaultInterval() : defaultInterval() * 3;
    }

    @Override
    public int range(boolean corrupted) {
        return defaultRange() / 4;
    }

    @Override
    public void riteEffect(Level level, BlockPos pos) {
        BlockState filter = level.getBlockState(pos.below());
        Optional<SmeltingRecipe> fillerOptional = level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(new ItemStack(filter.getBlock().asItem(), 1)), level);
        ArrayList<BlockPos> positions = getNearbyBlocksUnderBase(Block.class, level, pos, false);
        positions.removeIf(p -> {
            if (p.getX() == pos.getX() && p.getZ() == pos.getZ()) {
                return true;
            }
            BlockState state = level.getBlockState(p);
            return fillerOptional.isPresent() && !filter.isAir() && !filter.is(state.getBlock());
        });
        positions.forEach(p -> {
            BlockState state = level.getBlockState(p);
            Optional<SmeltingRecipe> optional = level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(new ItemStack(state.getBlock().asItem(), 1)), level);
            if (optional.isPresent()) {
                SmeltingRecipe recipe = optional.get();
                ItemStack output = recipe.getResultItem();
                if (output.getItem() instanceof BlockItem) {
                    if (!level.isClientSide) {
                        Block block = ((BlockItem) output.getItem()).getBlock();
                        BlockState newState = block.defaultBlockState();
                        level.setBlockAndUpdate(p, newState);
                        level.levelEvent(2001, p, Block.getId(newState));
                    } else {
                        particles(level, p);
                    }
                }
            }
        });
    }

    @Override
    public void corruptedRiteEffect(Level level, BlockPos pos) {
        ArrayList<BlockPos> positions = getNearbyBlocksUnderBase(Block.class, level, pos, false);
        positions.removeIf(p -> p.getX() == pos.getX() && p.getZ() == pos.getZ() || !level.getBlockState(p).is(Blocks.STONE));
        positions.forEach(p -> {
            BlockState netherrack = Blocks.NETHERRACK.defaultBlockState();
            if (!level.isClientSide) {
                level.setBlockAndUpdate(p, netherrack);
                level.levelEvent(2001, p, Block.getId(netherrack));
            } else {
                particles(level, p);
            }
        });
    }

    public void particles(Level level, BlockPos pos) {
        Color color = INFERNAL_SPIRIT_COLOR;
        RenderUtilities.create(ParticleRegistry.TWINKLE_PARTICLE)
                .setAlpha(0.4f, 0f)
                .setLifetime(20)
                .setSpin(0.3f)
                .setScale(0.2f, 0)
                .setColor(color, color)
                .enableNoClip()
                .randomOffset(0.1f, 0.1f)
                .randomVelocity(0.001f, 0.001f)
                .evenlyRepeatEdges(level, pos, 4, Direction.UP, Direction.DOWN);
        RenderUtilities.create(ParticleRegistry.WISP_PARTICLE)
                .setAlpha(0.1f, 0f)
                .setLifetime(40)
                .setSpin(0.1f)
                .setScale(0.6f, 0)
                .setColor(color, color)
                .randomOffset(0.2f)
                .enableNoClip()
                .randomVelocity(0.001f, 0.001f)
                .evenlyRepeatEdges(level, pos, 6, Direction.UP, Direction.DOWN);
    }
}