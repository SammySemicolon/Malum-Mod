package com.sammy.malum.common.item;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.client.screen.codex.ProgressionBookScreen;
import net.minecraft.entity.player.Player;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.Level.Level;

import net.minecraft.item.Item.Properties;

public class EncyclopediaArcanaItem extends Item
{
    public EncyclopediaArcanaItem(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public ActionResult<ItemStack> use(Level LevelIn, Player playerIn, Hand handIn)
    {
        if (MalumHelper.areWeOnClient(LevelIn))
        {
            ProgressionBookScreen.openScreen(true);
            playerIn.swing(handIn);
            return ActionResult.success(playerIn.getItemInHand(handIn));
        }
        return super.use(LevelIn, playerIn, handIn);
    }
}
