package com.sammy.malum.common.blocks.arcaneassembler;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.systems.recipes.SimpleItemIngredient;
import com.sammy.malum.core.systems.tileentities.SimpleInventoryBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static com.sammy.malum.common.blocks.arcaneassembler.ArcaneAssemblerTileEntity.itemPos;

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
            if (tileEntity.activity > 0)
            {
                MalumHelper.updateState(worldIn, pos);
                return ActionResultType.SUCCESS;
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