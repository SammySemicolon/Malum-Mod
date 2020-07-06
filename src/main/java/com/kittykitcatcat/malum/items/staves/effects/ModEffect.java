package com.kittykitcatcat.malum.items.staves.effects;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public abstract class ModEffect
{
    public void effect(PlayerEntity playerEntity, ItemStack stack)
    {

    }
    public boolean requirement(PlayerEntity playerEntity, ItemStack stack)
    {
        return false;
    }
}