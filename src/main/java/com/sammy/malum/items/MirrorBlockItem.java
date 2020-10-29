package com.sammy.malum.items;

import com.sammy.malum.blocks.machines.mirror.LinkableMirrorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class MirrorBlockItem extends BlockItem
{
    public MirrorBlockItem(Block blockIn, Properties builder)
    {
        super(blockIn, builder);
    }
    
    @Override
    protected boolean placeBlock(BlockItemUseContext context, BlockState state)
    {
        boolean success = super.placeBlock(context, state);
        if (success)
        {
            World world = context.getWorld();
            if (world instanceof ServerWorld)
            {
                ItemStack stack = context.getItem();
                CompoundNBT nbt = stack.getOrCreateTag();
                if (nbt.contains("linked"))
                {
                    BlockPos pos = new BlockPos(nbt.getInt("blockPosX"), nbt.getInt("blockPosY"), nbt.getInt("blockPosZ"));
                    if (world.getTileEntity(pos) instanceof LinkableMirrorTileEntity)
                    {
                        LinkableMirrorTileEntity tileEntity = (LinkableMirrorTileEntity) world.getTileEntity(pos);
                        tileEntity.link(context.getPos());
                    }
                }
            }
        }
        return success;
    }
}
