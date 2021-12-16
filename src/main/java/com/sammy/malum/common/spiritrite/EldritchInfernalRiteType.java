package com.sammy.malum.common.spiritrite;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.registry.misc.ParticleRegistry;
import com.sammy.malum.core.systems.particle.ParticleManager;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.world.level.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.inventory.Inventory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.Level.Level;

import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;

public class EldritchInfernalRiteType extends MalumRiteType {
    public EldritchInfernalRiteType() {
        super("eldritch_infernal_rite", false, ELDRITCH_SPIRIT, ARCANE_SPIRIT, INFERNAL_SPIRIT, INFERNAL_SPIRIT);
    }

    @Override
    public void riteEffect(Level Level, BlockPos pos) {
        BlockState filter = Level.getBlockState(pos.below());
        ArrayList<BlockPos> positions = getNearbyBlocksUnderBase(Block.class, Level, pos, false);
        positions.removeIf(p -> {
            if (p.getX() == pos.getX() && p.getZ() == pos.getZ()) {
                return true;
            }
            BlockState state = Level.getBlockState(p);
            return !filter.isAir(Level, pos) && !filter.is(state.getBlock());
        });
        positions.forEach(p -> {
            BlockState state = Level.getBlockState(p);
            Optional<FurnaceRecipe> optional = Level.getRecipeManager().getRecipeFor(IRecipeType.SMELTING, new Inventory(new ItemStack(state.getBlock().asItem(), 1)), Level);
            if (optional.isPresent()) {
                FurnaceRecipe recipe = optional.get();
                ItemStack output = recipe.getResultItem();
                if (output.getItem() instanceof BlockItem) {
                    if (MalumHelper.areWeOnServer(Level)) {
                        Block block = ((BlockItem) output.getItem()).getBlock();
                        BlockState newState = block.defaultBlockState();
                        Level.setBlockAndUpdate(p, newState);
                        Level.levelEvent(2001, p, Block.getId(newState));
                    } else {
                        particles(Level, p);
                    }
                }
            }
        });
    }

    @Override
    public void corruptedRiteEffect(Level Level, BlockPos pos) {
        ArrayList<BlockPos> positions = getNearbyBlocksUnderBase(Block.class, Level, pos, false);
        positions.removeIf(p -> p.getX() == pos.getX() && p.getZ() == pos.getZ() || !Level.getBlockState(p).is(Blocks.STONE));
        positions.forEach(p -> {
            BlockState netherrack = Blocks.NETHERRACK.defaultBlockState();
            if (MalumHelper.areWeOnServer(Level)) {
                Level.setBlockAndUpdate(p, netherrack);
                Level.levelEvent(2001, p, Block.getId(netherrack));
            } else {
                particles(Level, p);
            }
        });
    }

    @Override
    public int interval(boolean corrupted) {
        return defaultInterval() * 5;
    }

    @Override
    public int range(boolean corrupted) {
        return defaultRange() / 2;
    }

    public void particles(Level Level, BlockPos pos) {
        Color color = INFERNAL_SPIRIT_COLOR;
        ParticleManager.create(ParticleRegistry.TWINKLE_PARTICLE)
                .setAlpha(0.4f, 0f)
                .setLifetime(20)
                .setSpin(0.3f)
                .setScale(0.2f, 0)
                .setColor(color, color)
                .enableNoClip()
                .randomOffset(0.1f, 0.1f)
                .randomVelocity(0.001f, 0.001f)
                .evenlyRepeatEdges(Level, pos, 4, Direction.UP, Direction.DOWN);
        ParticleManager.create(ParticleRegistry.WISP_PARTICLE)
                .setAlpha(0.1f, 0f)
                .setLifetime(40)
                .setSpin(0.1f)
                .setScale(0.6f, 0)
                .setColor(color, color)
                .randomOffset(0.2f)
                .enableNoClip()
                .randomVelocity(0.001f, 0.001f)
                .evenlyRepeatEdges(Level, pos, 6, Direction.UP, Direction.DOWN);
    }
}