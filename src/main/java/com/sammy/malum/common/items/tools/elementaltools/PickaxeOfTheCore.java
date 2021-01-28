package com.sammy.malum.common.items.tools.elementaltools;

import com.sammy.malum.core.init.MalumItems;
import net.minecraft.block.BlockState;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraftforge.common.Tags;

public class PickaxeOfTheCore extends PickaxeItem
{
    public PickaxeOfTheCore(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder)
    {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }
    
    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state)
    {
        if (state.getBlock().getTags().contains(Tags.Blocks.ORES.getName()))
        {
            return super.getDestroySpeed(stack, state) * 3f;
        }
        return super.getDestroySpeed(stack, state);
    }
}
