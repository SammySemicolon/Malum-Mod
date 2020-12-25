package com.sammy.malum.common.items;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.recipes.TaintTransfusion;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.server.ServerWorld;

import static net.minecraft.util.SoundCategory.BLOCKS;

public class TaintRudimentItem extends Item
{
    public TaintRudimentItem(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        BlockState state = context.getWorld().getBlockState(context.getPos());
        PlayerEntity player = context.getPlayer();
        TaintTransfusion conversion = TaintTransfusion.getTransfusion(state.getBlock());
        if (conversion != null)
        {
            player.swingArm(context.getHand());
            if (MalumHelper.areWeOnServer(player.world))
            {
                player.getHeldItem(context.getHand()).shrink(1);
                TaintTransfusion.spread(player.world, context.getPos(), conversion);
                player.swingArm(context.getHand());
                return ActionResultType.SUCCESS;
            }
            player.playSound(MalumSounds.TAINT_SPREAD,BLOCKS,0.5f,1f + player.world.rand.nextFloat() * 0.5f);
        }
        return super.onItemUse(context);
    }
}