package com.sammy.malum.common.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class BoneNeedleItem extends Item
{
    public BoneNeedleItem(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        playerIn.attackEntityFrom(DamageSource.causeIndirectDamage(playerIn,playerIn), 1);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
