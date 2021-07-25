package com.sammy.malum.common.block.arcane_assembler;

import com.sammy.malum.common.tile.ArcaneAssemblerTileEntity;
import com.sammy.malum.core.mod_systems.tile.SimpleInventoryBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ArcaneAssemblerBlock extends SimpleInventoryBlock
{
    public ArcaneAssemblerBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (worldIn.getTileEntity(pos) instanceof ArcaneAssemblerTileEntity)
        {
            ArcaneAssemblerTileEntity tileEntity = (ArcaneAssemblerTileEntity) worldIn.getTileEntity(pos);
            if (tileEntity.active)
            {
                return ActionResultType.FAIL;
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new ArcaneAssemblerTileEntity();
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

}