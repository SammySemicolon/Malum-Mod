package com.sammy.malum.common.items;

import com.sammy.malum.core.systems.events.EventSubscriberItem;
import com.sammy.malum.core.recipes.TaintConversion;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class SeedOfCorruption extends Item implements EventSubscriberItem
{
    public SeedOfCorruption(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public boolean hasBlockRightClick()
    {
        return true;
    }
    
    public void onBlockRightClick(ItemStack stack, PlayerEntity player, BlockState state, Hand hand, BlockPos pos)
    {
        if (player.world instanceof ServerWorld)
        {
            TaintConversion conversion = TaintConversion.getConversion(state.getBlock());
            if (conversion != null)
            {
                stack.shrink(1);
                player.swingArm(hand);
                TaintConversion.spread(player.world, pos, conversion);
            }
        }
    }
}