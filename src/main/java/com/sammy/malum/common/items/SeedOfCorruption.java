package com.sammy.malum.common.items;

import com.sammy.malum.core.recipes.TaintConversion;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.server.ServerWorld;

public class SeedOfCorruption extends Item
{
    public SeedOfCorruption(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        BlockState state = context.getWorld().getBlockState(context.getPos());
        PlayerEntity player = context.getPlayer();
        TaintConversion conversion = TaintConversion.getConversion(state.getBlock());
        if (conversion != null)
        {
            player.swingArm(context.getHand());
            if (player.world instanceof ServerWorld)
            {
                player.getHeldItem(context.getHand()).shrink(1);
                TaintConversion.spread(player.world, context.getPos(), conversion);
                player.swingArm(context.getHand());
                return ActionResultType.SUCCESS;
            }
        }
        return super.onItemUse(context);
    }
}