package com.sammy.malum.common.spiritrite;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.registry.misc.ParticleRegistry;
import com.sammy.malum.core.systems.particle.ParticleManager;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;

public class EldritchInfernalRiteType extends MalumRiteType {
    public EldritchInfernalRiteType() {
        super("eldritch_infernal_rite", false, ELDRITCH_SPIRIT, ARCANE_SPIRIT, INFERNAL_SPIRIT, INFERNAL_SPIRIT);
    }

    @Override
    public void riteEffect(World world, BlockPos pos) {
        BlockState filter = world.getBlockState(pos.down());
        ArrayList<BlockPos> positions = getNearbyBlocksUnderBase(Block.class, world, pos, false);
        positions.removeIf(p -> {
            if (p.getX() == pos.getX() && p.getZ() == pos.getZ()) {
                return true;
            }
            BlockState state = world.getBlockState(p);
            return !filter.isAir(world, pos) && !filter.isIn(state.getBlock());
        });
        positions.forEach(p -> {
            BlockState state = world.getBlockState(p);
            Optional<FurnaceRecipe> optional = world.getRecipeManager().getRecipe(IRecipeType.SMELTING, new Inventory(new ItemStack(state.getBlock().asItem(), 1)), world);
            if (optional.isPresent()) {
                FurnaceRecipe recipe = optional.get();
                ItemStack output = recipe.getRecipeOutput();
                if (output.getItem() instanceof BlockItem) {
                    if (MalumHelper.areWeOnServer(world)) {
                        Block block = ((BlockItem) output.getItem()).getBlock();
                        BlockState newState = block.getDefaultState();
                        world.setBlockState(p, newState);
                        world.playEvent(2001, p, Block.getStateId(newState));
                    } else {
                        particles(world, p);
                    }
                }
            }
        });
    }

    @Override
    public void corruptedRiteEffect(World world, BlockPos pos) {
        ArrayList<BlockPos> positions = getNearbyBlocksUnderBase(Block.class, world, pos, false);
        positions.removeIf(p -> p.getX() == pos.getX() && p.getZ() == pos.getZ() || !world.getBlockState(p).isIn(Blocks.STONE));
        positions.forEach(p -> {
            BlockState netherrack = Blocks.NETHERRACK.getDefaultState();
            if (MalumHelper.areWeOnServer(world)) {
                world.setBlockState(p, netherrack);
                world.playEvent(2001, p, Block.getStateId(netherrack));
            } else {
                particles(world, p);
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

    public void particles(World world, BlockPos pos) {
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
                .evenlyRepeatEdges(world, pos, 4, Direction.UP, Direction.DOWN);
        ParticleManager.create(ParticleRegistry.WISP_PARTICLE)
                .setAlpha(0.1f, 0f)
                .setLifetime(40)
                .setSpin(0.1f)
                .setScale(0.6f, 0)
                .setColor(color, color)
                .randomOffset(0.2f)
                .enableNoClip()
                .randomVelocity(0.001f, 0.001f)
                .evenlyRepeatEdges(world, pos, 6, Direction.UP, Direction.DOWN);
    }
}