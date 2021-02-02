package com.sammy.malum.common.blocks.totems;

import com.sammy.malum.common.blocks.itemstand.ItemStandTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class TotemCoreBlock extends Block
{
    
    public TotemCoreBlock(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (handIn.equals(Hand.MAIN_HAND))
        {
            if (worldIn.getTileEntity(pos) instanceof TotemCoreTileEntity)
            {
                TotemCoreTileEntity tileEntity = (TotemCoreTileEntity) worldIn.getTileEntity(pos);
                if (tileEntity.rite != null)
                {
                    if (player.isSneaking())
                    {
                        tileEntity.fail();
                        return ActionResultType.SUCCESS;
                    }
                    return ActionResultType.FAIL;
                }
                else if (!tileEntity.active)
                {
                    tileEntity.active = true;
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }
    
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new TotemCoreTileEntity();
    }
    
}