package com.sammy.malum.common.block;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.block.generic.MalumLogBlock;
import com.sammy.malum.common.tile.TotemPoleTileEntity;
import com.sammy.malum.common.item.SpiritItem;
import com.sammy.malum.core.init.block.MalumBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class RunewoodLogBlock extends MalumLogBlock
{

    public RunewoodLogBlock(Properties properties, Supplier<Block> stripped)
    {
        super(properties, stripped);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        ItemStack stack = player.getHeldItem(handIn);
        if (MalumHelper.areWeOnClient(worldIn))
        {
            if (stack.getItem() instanceof SpiritItem)
            {
                return ActionResultType.SUCCESS;
            }
        }
        if (stack.getItem() instanceof SpiritItem)
        {
            SpiritItem item = (SpiritItem) stack.getItem();
            worldIn.setBlockState(pos, MalumBlocks.TOTEM_POLE.get().getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, player.getHorizontalFacing().getOpposite()));
            if (worldIn.getTileEntity(pos) instanceof TotemPoleTileEntity)
            {
                TotemPoleTileEntity tileEntity = (TotemPoleTileEntity) worldIn.getTileEntity(pos);
                tileEntity.create(item.type);
            }
            MalumHelper.updateAndNotifyState(worldIn,pos);
            if (!player.isCreative())
            {
                stack.shrink(1);
            }
            player.swing(handIn, true);
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}
