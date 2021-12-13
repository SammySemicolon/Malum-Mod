package com.sammy.malum.common.block.totem;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.tile.TotemBaseTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class TotemBaseBlock extends Block
{
    public final boolean corrupted;
    public TotemBaseBlock(Properties properties, boolean corrupted)
    {
        super(properties);
        this.corrupted = corrupted;
    }
    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new TotemBaseTileEntity(((TotemBaseBlock)state.getBlock()).corrupted);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (worldIn.getTileEntity(pos) instanceof TotemBaseTileEntity)
        {
            TotemBaseTileEntity totemBaseTileEntity = (TotemBaseTileEntity) worldIn.getTileEntity(pos);

            if (MalumHelper.areWeOnClient(worldIn) || handIn == Hand.OFF_HAND)
            {
                return ActionResultType.SUCCESS;
            }
            if (totemBaseTileEntity.active && totemBaseTileEntity.rite != null)
            {
                totemBaseTileEntity.endRite();
            }
            else
            {
                totemBaseTileEntity.startRite();
            }
            player.swing(Hand.MAIN_HAND, true);
            return ActionResultType.SUCCESS;
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

}
