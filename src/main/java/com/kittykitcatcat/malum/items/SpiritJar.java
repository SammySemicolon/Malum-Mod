package com.kittykitcatcat.malum.items;

import com.kittykitcatcat.malum.SpiritDataHelper;
import com.kittykitcatcat.malum.SpiritStorage;
import com.kittykitcatcat.malum.blocks.utility.soulstorage.SpiritStoringTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import static com.kittykitcatcat.malum.SpiritDataHelper.countNBT;
import static com.kittykitcatcat.malum.SpiritDataHelper.typeNBT;

public class SpiritJar extends BlockItem implements SpiritStorage
{

    public SpiritJar(Block blockIn, Properties builder)
    {
        super(blockIn, builder);
    }

    @Override
    protected boolean placeBlock(BlockItemUseContext context, BlockState state)
    {
        boolean success = super.placeBlock(context, state);
        context.getWorld().setBlockState(context.getPos(), state, 11);
        if (success)
        {
            ItemStack stack = context.getItem();
            if (stack.getTag() != null)
            {
                World world = context.getWorld();
                SpiritStoringTileEntity tileEntity = (SpiritStoringTileEntity) world.getTileEntity(context.getPos());
                if (SpiritDataHelper.doesItemHaveSpirit(stack))
                {
                    tileEntity.count = stack.getTag().getInt(countNBT);
                    tileEntity.type = stack.getTag().getString(typeNBT);
                }
            }
        }
        return success;
    }

    @Override
    public int capacity()
    {
        return 20;
    }
}