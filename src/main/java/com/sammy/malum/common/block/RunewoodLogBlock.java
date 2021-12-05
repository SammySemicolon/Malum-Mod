package com.sammy.malum.common.block;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.block.misc.MalumLogBlock;
import com.sammy.malum.common.tile.TotemPoleTileEntity;
import com.sammy.malum.common.item.misc.MalumSpiritItem;
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
    public final Supplier<Block> totemPole;
    public RunewoodLogBlock(Properties properties, Supplier<Block> stripped, Supplier<Block> totemPole)
    {
        super(properties, stripped);
        this.totemPole = totemPole;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        ItemStack stack = player.getHeldItem(handIn);
        if (MalumHelper.areWeOnClient(worldIn))
        {
            if (stack.getItem() instanceof MalumSpiritItem)
            {
                return ActionResultType.SUCCESS;
            }
        }
        if (stack.getItem() instanceof MalumSpiritItem)
        {
            MalumSpiritItem item = (MalumSpiritItem) stack.getItem();
            worldIn.setBlockState(pos, totemPole.get().getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, hit.getFace()));
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
