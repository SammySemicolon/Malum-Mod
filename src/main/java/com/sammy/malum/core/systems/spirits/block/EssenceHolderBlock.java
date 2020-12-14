package com.sammy.malum.core.systems.spirits.block;

import com.mojang.datafixers.util.Pair;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class EssenceHolderBlock extends Block
{
    public EssenceHolderBlock(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (worldIn instanceof ServerWorld)
        {
            if (handIn.equals(Hand.MAIN_HAND))
            {
                ItemStack stack = player.getHeldItemMainhand();
                Pair<String, Integer> itemSpirit = SpiritHelper.itemSpirit(stack,0);
                if (worldIn.getTileEntity(pos) instanceof ISpiritHolderTileEntity)
                {
                    ISpiritHolderTileEntity tileEntity = (ISpiritHolderTileEntity) worldIn.getTileEntity(pos);
                    tileEntity.setType(itemSpirit.getFirst());
                    tileEntity.setCount(itemSpirit.getSecond());
                }
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}
