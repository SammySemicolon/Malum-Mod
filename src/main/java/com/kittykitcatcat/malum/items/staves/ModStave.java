package com.kittykitcatcat.malum.items.staves;

import com.kittykitcatcat.malum.items.staves.effects.ModEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public abstract class ModStave extends Item
{
    ModEffect effect;
    public ModStave(Properties builder, ModEffect effect)
    {
        super(builder);
        this.effect = effect;
    }


    @Override
    public boolean doesSneakBypassUse(ItemStack stack, IWorldReader world, BlockPos pos, PlayerEntity player)
    {
        return true;
    }

    public int getUseDuration(ItemStack stack)
    {
        return 72000;
    }
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (playerIn.isSneaking())
        {
            if (effect.requirement(playerIn, itemstack))
            {
                effect.effect(playerIn, itemstack);
                playerIn
            }
            return ActionResult.resultSuccess(itemstack);
        }
        playerIn.setActiveHand(handIn);
        return ActionResult.resultSuccess(itemstack);
    }
}