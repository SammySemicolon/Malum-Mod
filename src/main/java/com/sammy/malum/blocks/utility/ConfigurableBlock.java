package com.sammy.malum.blocks.utility;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import static com.sammy.malum.items.staves.BasicStave.getEnum;
import static com.sammy.malum.items.staves.BasicStave.staveOptionEnum;

public class ConfigurableBlock extends Block
{
    public ConfigurableBlock(Properties properties)
    {
        super(properties);
    }
    public void configureTileEntity(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit, ConfigurableTileEntity tileEntity, int option, boolean isSneaking)
    {
    
    }
    public ActionResultType blockInteraction(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        return ActionResultType.FAIL;
    }
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (!worldIn.isRemote())
        {
            if (worldIn.getTileEntity(pos) instanceof ConfigurableTileEntity)
            {
                ItemStack stack = player.getHeldItem(handIn);
                if (!getEnum(stack).equals(staveOptionEnum.none))
                {
                    ConfigurableTileEntity tileEntity = (ConfigurableTileEntity) worldIn.getTileEntity(pos);
                    configureTileEntity(state, worldIn, pos, player, handIn, hit, tileEntity, tileEntity.option, player.isSneaking());
                    return ActionResultType.SUCCESS;
                }
                else
                {
                    return blockInteraction(state, worldIn, pos, player, handIn, hit);
                }
            }
        }
        return ActionResultType.SUCCESS;
    }
}