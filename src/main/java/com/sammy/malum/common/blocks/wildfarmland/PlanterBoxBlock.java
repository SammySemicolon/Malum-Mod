package com.sammy.malum.common.blocks.wildfarmland;

import com.sammy.malum.MalumHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BoneMealItem;
import net.minecraft.tags.ITag;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

public class PlanterBoxBlock extends WildFarmlandBlock
{
    public PlanterBoxBlock(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public boolean matchesBlock(Block block)
    {
        if (block== Blocks.FARMLAND)
        {
            return true;
        }
        if (block== Blocks.SOUL_SAND)
        {
            return true;
        }
        if (block== Blocks.GRASS_PATH)
        {
            return true;
        }
        if (block== Blocks.GRASS_BLOCK)
        {
            return true;
        }
        if (block== Blocks.COARSE_DIRT)
        {
            return true;
        }
        if (block== Blocks.DIRT)
        {
            return true;
        }
        if (block== Blocks.CONDUIT)
        {
            return true;
        }
        if (block== Blocks.WARPED_NYLIUM)
        {
            return true;
        }
        if (block== Blocks.CRIMSON_NYLIUM)
        {
            return true;
        }
        return super.matchesBlock(block);
    }
}
