package com.kittykitcatcat.malum.items.staves;

import com.kittykitcatcat.malum.init.ModSounds;
import com.kittykitcatcat.malum.items.staves.effects.ModEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
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

    @Override
    public UseAction getUseAction(ItemStack stack)
    {
        return UseAction.BOW;
    }

    public int getUseDuration(ItemStack stack)
    {
        return 72000;
    }
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (effect != null)
        {
            if (playerIn.isSneaking())
            {
                if (effect.requirement(playerIn, itemstack))
                {
                    effect.effect(playerIn, itemstack);
                    playerIn.addStat(Stats.ITEM_USED.get(playerIn.getHeldItemMainhand().getItem()));
                    playerIn.getCooldownTracker().setCooldown(playerIn.getHeldItemMainhand().getItem(), effect.cooldown());
                }
                return ActionResult.resultSuccess(itemstack);
            }
        }
        playerIn.playSound(ModSounds.spirit_harvest_drain, 1,1);
        if (!playerIn.isSneaking())
        {
            playerIn.setActiveHand(handIn);
        }
        return ActionResult.resultSuccess(itemstack);
    }
}