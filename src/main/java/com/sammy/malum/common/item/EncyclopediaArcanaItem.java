package com.sammy.malum.common.item;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.client.screen.old_book.BookScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class EncyclopediaArcanaItem extends Item
{
    public EncyclopediaArcanaItem(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        if (MalumHelper.areWeOnClient(worldIn))
        {
            BookScreen.openScreen(false);
            playerIn.swingArm(handIn);
            return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
