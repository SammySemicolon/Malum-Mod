package com.sammy.malum.common.blocks.arcanecraftingtable;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.systems.tileentities.SimpleInventoryBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ArcaneCraftingTableBlock extends SimpleInventoryBlock
{
    public ArcaneCraftingTableBlock(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (handIn.equals(Hand.MAIN_HAND))
        {
            if (worldIn instanceof ServerWorld)
            {
                if (worldIn.getTileEntity(pos) instanceof ArcaneCraftingTableTileEntity)
                {
                    ArcaneCraftingTableTileEntity tileEntity = (ArcaneCraftingTableTileEntity) worldIn.getTileEntity(pos);
                    tileEntity.issueRequest(worldIn, pos);
                    for (BlockPos cachedPos : tileEntity.getCachedHolders())
                    {
                        MalumMod.LOGGER.info(cachedPos);
                    }
                }
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
//    @Override
//    public TileEntity createTileEntity(BlockState state, IBlockReader world)
//    {
//        return new ArcaneCraftingTableTileEntity();
//    }
}
