package com.sammy.malum.blocks.machines.ignisfurnace;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.blocks.machines.spiritfurnace.SpiritFurnaceBottomTileEntity;
import com.sammy.malum.blocks.utility.multiblock.MultiblockBlock;
import com.sammy.malum.blocks.utility.multiblock.MultiblockStructure;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class SpiritFurnaceBlock extends MultiblockBlock
{
    //region structure
    public static final MultiblockStructure structure = new MultiblockStructure(
            new BlockPos(0,1,0)
    );
    //endregion
    
    public SpiritFurnaceBlock(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public boolean hasTileEntity(final BlockState state)
    {
        return true;
    }
    
    @Override
    public TileEntity createTileEntity(final BlockState state, final IBlockReader world)
    {
        return new SpiritFurnaceTileEntity();
    }
    
    @Override
    public ActionResultType activateBlock(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit, BlockPos boundingBlockSource)
    {
        if (worldIn.getTileEntity(pos) instanceof SpiritFurnaceTileEntity)
        {
            SpiritFurnaceTileEntity furnaceTileEntity = (SpiritFurnaceTileEntity) worldIn.getTileEntity(pos);
    
            if (boundingBlockSource.equals(pos.up())) //smeltable inventory access
            {
                boolean success = MalumHelper.singleItemTEHandling(player, handIn, player.getHeldItemMainhand(), furnaceTileEntity.smeltableInventory, 0);
                if (success)
                {
                    player.world.notifyBlockUpdate(pos, state, state, 3);
                    player.swingArm(handIn);
                    return ActionResultType.SUCCESS;
                }
            }
            if (boundingBlockSource.equals(pos)) //fuel inv access
            {
                boolean success = MalumHelper.singleItemTEHandling(player, handIn, player.getHeldItemMainhand(), furnaceTileEntity.fuelInventory, 0);
                if (success)
                {
                    player.world.notifyBlockUpdate(pos, state, state, 3);
                    player.swingArm(handIn);
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return super.activateBlock(state, worldIn, pos, player, handIn, hit, boundingBlockSource);
    }
}